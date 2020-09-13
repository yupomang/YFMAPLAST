package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80005ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80005_00057400 implements CtrlHandleInter {
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
		AppApi80005ZHResult app80005ZHResult = new AppApi80005ZHResult();
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
			form.setTellCode("cweb");
			form.setBrcCode("05740008");
			form.setClientMAC("");

			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", "cweb");
			System.out.println(map.toString());
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80005_142900.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142900");
			log.debug("前置YFMAP接收中心报文——zwfw个人账户余额查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_80005_142900.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app80005ZHResult);
			log.debug("MAP封装成BEAN："+app80005ZHResult);
			if(!"0".equals(app80005ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app80005ZHResult.getMsg())){
					if("/".equals(app80005ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app80005ZHResult.getMsg());	    
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app80005ZHResult.getMsg());
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
						modelMap.put("recode", app80005ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app80005ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app80005ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app80005ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app80005ZHResult.getRecode()+"  ,  描述msg : "+app80005ZHResult.getMsg());
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

//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");
		modelMap.put("result", "1");
		modelMap.put("description", "查询成功");
		modelMap.put("time", app80005ZHResult.getTimestamp());
		JSONArray array=new JSONArray();
		addList(array,app80005ZHResult.getPerinfo1());
		addList(array,app80005ZHResult.getPerinfo2());
		addList(array,app80005ZHResult.getPerinfo3());
		addList(array,app80005ZHResult.getPerinfo4());
		addList(array,app80005ZHResult.getPerinfo5());
		addList(array,app80005ZHResult.getPerinfo6());
		addList(array,app80005ZHResult.getPerinfo7());
		addList(array,app80005ZHResult.getPerinfo8());
		addList(array,app80005ZHResult.getPerinfo9());
		addList(array,app80005ZHResult.getPerinfo10());
		addList(array,app80005ZHResult.getPerinfo11());
		addList(array,app80005ZHResult.getPerinfo12());
		addList(array,app80005ZHResult.getPerinfo13());
		addList(array,app80005ZHResult.getPerinfo14());
		addList(array,app80005ZHResult.getPerinfo15());
		addList(array,app80005ZHResult.getPerinfo16());
		addList(array,app80005ZHResult.getPerinfo17());
		addList(array,app80005ZHResult.getPerinfo18());
		addList(array,app80005ZHResult.getPerinfo19());
		addList(array,app80005ZHResult.getPerinfo20());
		addList(array,app80005ZHResult.getPerinfo21());
		addList(array,app80005ZHResult.getPerinfo22());
		addList(array,app80005ZHResult.getPerinfo23());
		addList(array,app80005ZHResult.getPerinfo24());
		addList(array,app80005ZHResult.getPerinfo25());
		addList(array,app80005ZHResult.getPerinfo26());
		addList(array,app80005ZHResult.getPerinfo27());
		addList(array,app80005ZHResult.getPerinfo28());
		addList(array,app80005ZHResult.getPerinfo29());
		addList(array,app80005ZHResult.getPerinfo30());
		addList(array,app80005ZHResult.getPerinfo31());
		addList(array,app80005ZHResult.getPerinfo32());
		addList(array,app80005ZHResult.getPerinfo33());
		addList(array,app80005ZHResult.getPerinfo34());
		addList(array,app80005ZHResult.getPerinfo35());
		addList(array,app80005ZHResult.getPerinfo36());
		addList(array,app80005ZHResult.getPerinfo37());
		addList(array,app80005ZHResult.getPerinfo38());
		addList(array,app80005ZHResult.getPerinfo39());
		addList(array,app80005ZHResult.getPerinfo40());
		modelMap.put("list", array);
		return modelMap;
	}
	
	
	private void addList(JSONArray array,String perinfo) {
		if(perinfo!=null&&perinfo.length()>0){
			JSONObject tmp=new JSONObject();
			String[] infos=perinfo.split(",");
			tmp.put("grzh", infos[0].trim());
			tmp.put("zhzt", infos[1].trim());
			tmp.put("zhlx", infos[2].trim());
			tmp.put("gryjce", infos[4].trim());
			tmp.put("dwyjce", infos[5].trim());
			tmp.put("gjjye", infos[6].trim());
			array.add(tmp);
		}
	}
}
