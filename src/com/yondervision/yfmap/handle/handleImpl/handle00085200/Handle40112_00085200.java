package com.yondervision.yfmap.handle.handleImpl.handle00085200;

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
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40101Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40103Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle40109_00085200 
* @Description: 遵义手机验证码请求
* @author Caozhongyan
* @date Apr 14, 2016 2:58:15 PM   
* 
*/ 
public class Handle40112_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40112 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		AES aes = new AES();
		log.info("Handle40112_00085200 遵义登录请求参,form.getBodyCardNumber():"+form.getBodyCardNumber());
		log.info("Handle40112_00085200 遵义登录请求参,form.getUserId():"+aes.decrypt(form.getUserId()));
		log.info("Handle40112_00085200 遵义登录请求参,form.getCardnoNumber():"+form.getCardnoNumber());
		log.info("Handle40112_00085200 遵义登录请求参,form.getIdcardNumber():"+form.getIdcardNumber());
		log.info("Handle40112_00085200 遵义登录请求参,form.getCertinum():"+form.getCertinum());
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		ZunYiAppApi40101Result app40101Result = new ZunYiAppApi40101Result();
		ZunYiAppApi40103Result appapi40103 = new ZunYiAppApi40103Result();
		
//		if(!CommonUtil.isEmpty(form.getSurplusAccount())){
			log.info(Constants.LOG_HEAD+"appApi40109 form.getAccnum():"+form.getAccnum());
			form.setSurplusAccount(aes.decrypt(form.getAccnum()));
			log.info(Constants.LOG_HEAD+"appApi40109 form.getSurplusAccount():"+form.getSurplusAccount());
//		}
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DXX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149365");//116004
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DXX.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
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
