package com.yondervision.yfmap.handle.handleImpl.handle00087500;

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
import com.yondervision.yfmap.result.baoshan.AppApi40112Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle40112_00087500
* @Description: 手机验证码请求
* @date Mar 10, 2016 2:58:15 PM   
* 
*/ 
public class Handle40112_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40112 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("手机验证码获取请求参"+form);
		String surplusAccountTmp = "";
		surplusAccountTmp = new AES().decrypt(form.getSurplusAccount());
		form.setSurplusAccount(surplusAccountTmp);
		System.out.println("accnum=="+form.getSurplusAccount()+";phone=="+form.getMobileNumber());
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi40112Result app40112Result = new AppApi40112Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getSurplusAccount()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GET_MSGCHECKID.xml", map, req);
			log.debug("手机验证码获取-前置YFMAP发往中心报文："+xml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "160098");
			//String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>430401</tr_code><req_no></req_no><ans_no></ans_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>00</ans_code><ans_info>成功</ans_info><reserved></reserved></head><body><certinum>1234567890</certinum><accnum>98765432101</accnum><accname>测试员</accname></body></root>";
			//rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("手机验证码获取-前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_GET_MSGCHECKID.xml", rexml, req);
			log.debug("手机验证码获取-解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40112Result);
			log.debug("手机验证码获取-MAP封装成BEAN："+app40112Result);
			if(!"0".equals(app40112Result.getRecode())){
				modelMap.put("recode", app40112Result.getRecode());
				modelMap.put("msg", app40112Result.getMsg());
				log.error("手机验证码获取-中心返回报文 状态recode :"+app40112Result.getRecode()+"  ,  描述msg : "+app40112Result.getMsg());
				return modelMap;
			}			
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi40112 end.");
		return modelMap;
	}

}
