package com.yondervision.yfmap.handle.handleImpl.handle00091200;

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
import com.yondervision.yfmap.result.baoshan.AppApi40102Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle500_00091200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi40102Form form = (AppApi40102Form)form1;

		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
		System.out.println("idcardNumber==="+form.getIdcardNumber());
		form.setBodyCardNumber(aes.decrypt(form.getIdcardNumber()));
		System.out.println("bodycardnumber==="+form.getBodyCardNumber());
		System.out.println("newspassword==="+form.getNewpassword());
		form.setNewpassword(aes.decrypt(form.getNewpassword()));
		System.out.println("newspassword===after=="+form.getNewpassword());
		
		// TODO
		System.out.println("surplusAccount==="+form.getSurplusAccount());
		form.setAccnum(form.getSurplusAccount());
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi40102Result app40102Result = new AppApi40102Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			if(Constants.CHANNELTYPE_APP.equals(form.getChannel())){
				form.setChannel("7");
			}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
				form.setChannel("9");
			}
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DL.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430401");
			//String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>430401</tr_code><req_no></req_no><ans_no></ans_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>00</ans_code><ans_info>成功</ans_info><reserved></reserved></head><body><certinum>1234567890</certinum><accnum>98765432101</accnum><accname>测试员</accname></body></root>";
			//rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("前置YFMAP接收中心报文："+rexml);
			rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?>"+rexml;
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DL.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);
			if(!"00".equals(app40102Result.getRecode())){
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", app40102Result);
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi40102Form form1 = new AppApi40102Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00087500");
		form1.setUserId("test");
		form1.setSendDate("2016-01-25");
		form1.setSendTime("171213");
		form1.setBodyCardNumber("533001197304050045");
		
		
		Handle500_00091200 hand = new Handle500_00091200();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
