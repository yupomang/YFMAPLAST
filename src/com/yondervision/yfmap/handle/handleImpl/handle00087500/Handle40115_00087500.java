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
import com.yondervision.yfmap.form.AppApi40115Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baoshan.AppApi40115Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle40115_00087500 
* @Description: 实名资格校验（校验用户是否满足做实名身份认证，满足会直接发送短信验证码）
* @date Mar 10, 2016 2:58:15 PM   
* 
*/ 
public class Handle40115_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40115 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40115Form form = (AppApi40115Form)form1;
//		String surplusAccountTmp = "";
//		surplusAccountTmp = new AES().decrypt(form.getSurplusAccount());
//		form.setSurplusAccount(surplusAccountTmp);
		log.debug("身份实名资格校验获取请求参"+form);
		// TODO 目前一些参数都是加密后的没有解密的才对，同时关注下报文看是否准确
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi40115Result app40115Result = new AppApi40115Result();
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
			System.out.println(form.getSurplusAccount()+"   "+form.getSendDate()+"   "+form.getSendTime());
			//buschktype[0] = ‘1’ ， 校验个人账户状态
			//buschktype[0] = ‘2’  校验个人账户状态，身份证号码，个人姓名，单位账号
			//buschktype[1] = ‘1’， 校验个人收款信息是否存在，并将大行转小行
			//buschktype[1] = ‘2’， 校验个人收款信息是否存在，并将大行转小行，传入的收款信息与系统中的收款信息校验。
			//buschktype[2] = ‘1’， 校验是否签约手机号码
			//buschktype[2] = ‘2’， 校验是否签约手机号码，传入的手机号与系统中的是否一致
			//buschktype[3] = ‘1’，校验是否生成验证码
			//buschktype[3] = ‘2’，校验是否生成验证码，校验验证码超时和是否一致
			form.setBuschktype("1120");

			System.out.println("surplusAccount=="+form.getSurplusAccount());
			System.out.println("checkid=="+form.getCheckid());
			System.out.println("fullName=="+form.getFullName());
			System.out.println("bankaccnm=="+form.getBankaccnm());
			System.out.println("bankaccnum=="+form.getBankaccnum());
			System.out.println("bankcode=="+form.getBankcode());
			System.out.println("idcardNumber=="+form.getIdcardNumber());
			System.out.println("mobileNumber=="+form.getMobileNumber());
			System.out.println("unitaccnum=="+form.getUnitaccnum());
			System.out.println("buschktype=="+form.getBuschktype());
			form.setCheckid("");
			form.setFullName("");
			form.setBankaccnm("");
			form.setBankaccnum("");
			form.setBankcode("");
			form.setIdcardNumber("");
			form.setUnitaccnum("");
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_IDENTITY_AUTH.xml", map, req);
			log.debug("身份实名资格校验-前置YFMAP发往中心报文："+xml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "160096");
			//String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>430401</tr_code><req_no></req_no><ans_no></ans_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>00</ans_code><ans_info>成功</ans_info><reserved></reserved></head><body><certinum>1234567890</certinum><accnum>98765432101</accnum><accname>测试员</accname></body></root>";
			//rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("身份实名资格校验-前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_IDENTITY_AUTH.xml", rexml, req);
			log.debug("身份实名资格校验-解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40115Result);
			log.debug("身份实名资格校验-MAP封装成BEAN："+app40115Result);
			if(!"0".equals(app40115Result.getRecode())){
				modelMap.put("recode", app40115Result.getRecode());
				modelMap.put("msg", app40115Result.getMsg());
				log.error("身份实名资格校验-中心返回报文 状态recode :"+app40115Result.getRecode()+"  ,  描述msg : "+app40115Result.getMsg());
				return modelMap;
			}
			app40115Result = new AppApi40115Result();
			String getMsgCheckidXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GET_MSGCHECKID.xml", map, req);
			String getMsgCheckidResXml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), getMsgCheckidXml, "160098");
			log.debug("身份实名资格校验-获取短信验证码-前置YFMAP接收中心报文："+getMsgCheckidResXml);
			HashMap getMsgCheckidResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_GET_MSGCHECKID.xml", getMsgCheckidResXml, req);
			log.debug("身份实名资格校验-获取短信验证码-解析报文MAP："+getMsgCheckidResultMap);
			BeanUtil.transMap2Bean(getMsgCheckidResultMap, app40115Result);
			log.debug("身份实名资格校验-获取短信验证码-MAP封装成BEAN："+app40115Result);
			if(!"0".equals(app40115Result.getRecode())){
				modelMap.put("recode", app40115Result.getRecode());
				modelMap.put("msg", app40115Result.getMsg());
				log.error("身份实名资格校验-获取短信验证码-中心返回报文 状态recode :"+app40115Result.getRecode()+"  ,  描述msg : "+app40115Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi40115 end.");
		return modelMap;
	}

}
