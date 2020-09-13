package com.yondervision.yfmap.handle.handleImpl.handle00031400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi40101Result;
import com.yondervision.yfmap.result.dalian.DaLianAppApi40101Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle401_00031400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
//		form.setBodyCardNumber(aes.decrypt(form.getIdcardNumber()));
//		form.setNewpassword(aes.decrypt(form.getNewpassword()));
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		ChengDeAppApi40101Result app40101Result = new ChengDeAppApi40101Result();//
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			form.setFullName(aes.decrypt(form.getFullName()));
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
			
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());
			
			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YZ.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/tomcat/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REQ_YZ.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00001");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><transCode>appapi401</transCode><recvDate>111</recvDate><recvTime>222</recvTime><sendSeqno>333</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg></head><body><grxm>1</grxm><jyrq>1</jyrq><zxlmkkzt>1</zxlmkkzt></body></root>";
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>" +
//					"<root><head>" +
//					"<reqflag>1</reqflag>" +
//					"<msgtype>71</msgtype>" +
//					"<tr_code>X00001</tr_code>" +
//					"<corp_no>00031400</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2015-09-29</tr_acdt>" +
//					"<tr_time>10:25:15</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>交易成功完成</ans_info>" +
//					"<particular_code>000000</particular_code>" +
//					"<particular_info>交易成功完成</particular_info>" +
//					"<reserved></reserved></head>" +
//					"<body>" +
//					"</body></root>";	
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YZ.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_YZ.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!Constants.sucess_ts.equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
		}
		
//		Handle_Check_00053100 check = new Handle_Check_00053100();
//		check.YzCheck(form1, modelMap, "appapi40102");
		modelMap.clear();
		modelMap.put("recode", "000000");
//		modelMap.put("grzh", app40101Result.getGrzh());
//		modelMap.put("psword", app40101Result.getPsword());
		modelMap.put("msg", "成功");					
		return modelMap;
	}
	
	public static void main(String[] args) throws CenterRuntimeException, Exception{
		AppApi40102Form form = new AppApi40102Form();
		Handle401_00031400 hand = new Handle401_00031400();
		ModelMap modelMap = new ModelMap();
		form.setCenterId("00031400");
		form.setFullName("成颖");
		form.setSurplusAccount("314000194377");
		form.setNewpassword("");
		form.setPassword("");
		form.setChannel("10");
		form.setUserId("abc");
		form.setIdcardNumber("130102196902251824");
		hand.action(form, modelMap);
	}

}
