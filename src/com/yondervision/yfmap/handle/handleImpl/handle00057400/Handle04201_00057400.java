package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.ningbo.AppApi04201ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle04201_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("00077500请求04201参数："+CommonUtil.getStringParams(form));
		AppApi04201ZHResult app04201ZHResult = new AppApi04201ZHResult();

		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))){
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
				form.setUKseq("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			String isfinished =form.getIsfinished();
			if ("0".equals(isfinished)) {
				System.out.println("111410");
				form.setTranscode("111410");
			}else{
				System.out.println("111502");
				form.setTranscode("111502");
			}

			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWYW_JCBLBG.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip,Integer.parseInt(port), xml, form.getTranscode());

						
			log.debug("前置YFMAP接收中心报文——单位比例变更："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWYW_JCBLBG.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app04201ZHResult);
			log.debug("MAP封装成BEAN："+app04201ZHResult);
			if(!"0".equals(app04201ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app04201ZHResult.getMsg())){
					if("/".equals(app04201ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app04201ZHResult.getMsg());	    
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app04201ZHResult.getMsg());
						//File file = new File("D:/err001.txt");
						FileInputStream ffis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
						BufferedReader breader = new BufferedReader(isr);
						String line1 = breader.readLine(); 
						JSONArray result = new JSONArray();
						while (line1 != null) {// 判断读取到的字符串是否不为空
							JSONObject obj = new JSONObject();
							log.debug("读取文件  ："+line1);
							String[] trs = line1.split("\\|");
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < trs.length; i++) {
								if (i == 0) {
									obj.put("errCode", trs[i].trim());
									continue;
								} 
								sb.append(trs[i].trim()+",");
							}
							String msg = sb.toString();
							msg = msg.substring(0, msg.length()-1);
							obj.put("msg", "错误信息："+msg);
							result.add(obj);
							line1 = breader.readLine();
						}
						breader.close();
						isr.close();
						ffis.close();
						file.delete();
						modelMap.clear();
						modelMap.put("recode", app04201ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app04201ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app04201ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app04201ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app04201ZHResult.getRecode()+"  ,  描述msg : "+app04201ZHResult.getMsg());
					}					
					return modelMap;
				}else{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据");
					return modelMap;
				}
			}
		}
		
		List<TitleInfoNameFormatBean> appapi04201ZHResult = null;
		MoneyNumberTran mnTran = new MoneyNumberTran();
		
		app04201ZHResult.getAppadjustreason();
		app04201ZHResult.getAuthfilenum();
		app04201ZHResult.getBeginpaydate();
		app04201ZHResult.getChangepeople();
		app04201ZHResult.getEnddate();
		app04201ZHResult.setNewperprop(mnTran.numberTran(app04201ZHResult.getNewperprop()));
		app04201ZHResult.setNewunitprop(mnTran.numberTran(app04201ZHResult.getNewunitprop()));
		app04201ZHResult.getUnitaccnum();
		app04201ZHResult.getFilename();
		appapi04201ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi04201"+form.getCenterId()+".result", app04201ZHResult);
		
		Iterator<TitleInfoNameFormatBean> it1 = appapi04201ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("rsufilename", app04201ZHResult.getRsufilename());
		modelMap.put("rsufilepath", app04201ZHResult.getRsufilepath());
		return modelMap;
	}
}
