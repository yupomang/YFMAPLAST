package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi50009ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50009_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
	
		System.out.println("YFMAP发往中心——个人密码修改");
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
		
		/*if(!CommonUtil.isEmpty(form.getCheckcode())){
			log.debug("form.getCheckcode():"+form.getCheckcode());
			form.setCheckcode(aes.decrypt(form.getCheckcode()));
		}*/
		if(!CommonUtil.isEmpty(form.getPassword())){
			log.debug("form.getPassword():"+form.getPassword());
			String pwd = "";
			if(!"1".equals(form.getAESFlag())){
				pwd = aes.decrypt(form.getPassword());
			}else{
				pwd = form.getPassword();
			}
			byte[] c = DESForJava.encryption(pwd,"12345678");
			c = DESForJava.bcd_to_asc(c);
			form.setPassword(new String(c));
		}
		if(!CommonUtil.isEmpty(form.getNewpassword())){
			log.debug("form.getNewpassword():"+form.getNewpassword());
			String pwd = "";
			if(!"1".equals(form.getAESFlag())){
				pwd = aes.decrypt(form.getNewpassword());
			}else{
				pwd = form.getNewpassword();
			}
			byte[] c = DESForJava.encryption(pwd,"12345678");
			c = DESForJava.bcd_to_asc(c);
			form.setNewpassword(new String(c));
		}
		if(!CommonUtil.isEmpty(form.getConfirmnewpassword())){
			log.debug("form.getConfirmnewpassword():"+form.getConfirmnewpassword());
			String pwd = "";
			if(!"1".equals(form.getAESFlag())){
				pwd = aes.decrypt(form.getConfirmnewpassword());
			}else{
				pwd = form.getConfirmnewpassword();
			}
			byte[] c = DESForJava.encryption(pwd,"12345678");
			c = DESForJava.bcd_to_asc(c);
			form.setConfirmnewpassword(new String(c));
		}
		
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("50")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("60")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("53")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		/*if(!CommonUtil.isEmpty(form.getUnitaccnum())){
			log.debug("form.getUnitaccnum():"+form.getUnitaccnum());
			form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
		}*/
		
		log.debug("00057400请求50009参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi50009ZHResult app50009ZHResult = new AppApi50009ZHResult();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());				
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))){
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRMMXG.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRMMXG.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142505");
			
			//String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRMMXG.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRMMXG.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50009ZHResult);
			log.debug("MAP封装成BEAN："+app50009ZHResult);
			
			if(!"0".equals(app50009ZHResult.getRecode())){
				modelMap.put("recode", app50009ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app50009ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50009ZHResult.getRecode()+"  ,  描述msg : "+app50009ZHResult.getMsg());
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return modelMap;
	}
	/*public static void main(String[] args){
		AppApi50001Form form1 = new AppApi50001Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00073300");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBodyCardNumber("999999888888");
		form1.setAccnum("1234567890");
		try{
			AES aes = new AES("00073300" ,"40" ,null ,null);
			form1.setPassword(aes.encrypt("123456".getBytes()));
			form1.setUnitaccnum(aes.encrypt("222333".getBytes()));
			form1.setNewpassword(aes.encrypt("111111".getBytes()));
			form1.setConfirmnewpassword(aes.encrypt("111111".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle50009_00057400 hand = new Handle50009_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap, null, null)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
