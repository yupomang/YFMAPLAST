package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.bocom.netpay.infosec.NetSignServer;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle40122_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi40102Form form = (AppApi40102Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setFullName(aes.decrypt(form.getFullName()));
		log.debug("form:"+form);
		NetSignServer tmp = new NetSignServer();
		form.setPassword(tmp.NSHashAndBase64Encode(aes.decrypt(form.getPassword())));
		
//		byte[] b = DESForJava.encryption(form.getPassword(), "12345678");
//		byte[] c = DESForJava.bcd_to_asc(b);
//		form.setPassword(new String(c));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			log.debug(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);
			
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_ZCYZ.xml", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);			
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "A00014").trim();
	//			String rexml ="<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode></transCode><recvDate></recvDate><recvTime></recvTime><sendSeqno>zsgjiapp20141021172832745</sendSeqno><key>1</key><centerSeqno></centerSeqno><recode>000000</recode><msg>success</msg></head><body><accnum>6217280504100023429</accnum><accname>陈倩敏</accname><cardno>6217280504100023429</cardno><certinum>442000199107280546</certinum><keepbal></keepbal><incrbal></incrbal><lasttransdate></lasttransdate><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></root>";
				log.debug("前置YFMAP接收中心报文："+rexml);
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_ZCYZ.xml", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app40102Result);
				log.debug("MAP封装成BEAN："+app40102Result);
				if(!"00".equals(app40102Result.getRecode())){
					modelMap.put("recode", app40102Result.getRecode());
					modelMap.put("msg", app40102Result.getMsg());
					log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
					return modelMap;
				}
				
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("gjjzh", app40102Result.getAccnum());
		}
		return modelMap;
	}

}
