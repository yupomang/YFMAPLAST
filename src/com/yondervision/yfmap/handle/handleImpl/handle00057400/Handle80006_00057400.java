package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80006ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Handle80006_00057400 implements CtrlHandleInter {
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
		AppApi80006ZHResult app80006ZHResult = new AppApi80006ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			form.setTellCode("1994");
			form.setBrcCode("05740008");
			form.setClientMAC("");

			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", "1994");
			System.out.println(map.toString());
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80006_128187.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129998");
			log.debug("前置YFMAP接收中心报文——贷款信息查询接口："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_80006_128187.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app80006ZHResult);
			log.debug("MAP封装成BEAN："+app80006ZHResult);
			if(!"0".equals(app80006ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app80006ZHResult.getMsg())){
					if("/".equals(app80006ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app80006ZHResult.getMsg());
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app80006ZHResult.getMsg());
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
						modelMap.put("recode", app80006ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app80006ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app80006ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app80006ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app80006ZHResult.getRecode()+"  ,  描述msg : "+app80006ZHResult.getMsg());
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
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("certinum", app80006ZHResult.getCertinum());
		return modelMap;
	}
}
