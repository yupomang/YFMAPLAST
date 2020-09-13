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
import com.yondervision.yfmap.form.AppApi01005Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baoshan.AppApi01005Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle01005_00087500 
* @Description: 提取规则检查（校验提取规则(允许提取次数等)，满足会直接发送短信验证码）
* @date Mar 10, 2016 2:58:15 PM   
* 
*/ 
public class Handle01005_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi01005 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01005Form form = (AppApi01005Form)form1;
		log.debug("提取规则检查获取请求参"+form);
		AES aes = new AES();
		System.out.println("surplusAccount=="+form.getSurplusAccount());
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		AppApi01005Result app01005Result = new AppApi01005Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());			
			String req = form.getSurplusAccount()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());			
			System.out.println(form.getSurplusAccount()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_WYFTQ_RULE.xml", map, req);
			log.debug("提取规则检查-前置YFMAP发往中心报文："+xml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "150113");
			//String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>430401</tr_code><req_no></req_no><ans_no></ans_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>00</ans_code><ans_info>成功</ans_info><reserved></reserved></head><body><certinum>1234567890</certinum><accnum>98765432101</accnum><accname>测试员</accname></body></root>";
			//rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("提取规则检查-前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_WYFTQ_RULE.xml", rexml, req);
			log.debug("提取规则检查-解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app01005Result);
			log.debug("提取规则检查-MAP封装成BEAN："+app01005Result);
			if(!"0".equals(app01005Result.getRecode())){
				modelMap.put("recode", app01005Result.getRecode());
				modelMap.put("msg", app01005Result.getMsg());
				log.error("提取规则检查-中心返回报文 状态recode :"+app01005Result.getRecode()+"  ,  描述msg : "+app01005Result.getMsg());
				return modelMap;
			}
			app01005Result = new AppApi01005Result();
			String getMsgCheckidXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GET_MSGCHECKID.xml", map, req);
			String getMsgCheckidResXml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), getMsgCheckidXml, "160098");
			log.debug("提取规则检查-获取短信验证码-前置YFMAP接收中心报文："+getMsgCheckidResXml);
			HashMap getMsgCheckidResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_GET_MSGCHECKID.xml", getMsgCheckidResXml, req);
			log.debug("提取规则检查-获取短信验证码-解析报文MAP："+getMsgCheckidResultMap);
			BeanUtil.transMap2Bean(getMsgCheckidResultMap, app01005Result);
			log.debug("提取规则检查-获取短信验证码-MAP封装成BEAN："+app01005Result);
			if(!"0".equals(app01005Result.getRecode())){
				modelMap.put("recode", app01005Result.getRecode());
				modelMap.put("msg", app01005Result.getMsg());
				log.error("提取规则检查-获取短信验证码-中心返回报文 状态recode :"+app01005Result.getRecode()+"  ,  描述msg : "+app01005Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi01005 end.");
		return modelMap;
	}

}
