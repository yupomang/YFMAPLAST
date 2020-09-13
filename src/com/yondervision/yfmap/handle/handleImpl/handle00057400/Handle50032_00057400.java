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
import com.yondervision.yfmap.result.ningbo.AppApi00101ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi50006ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50032_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00057400请求50032参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
		if(!CommonUtil.isEmpty(form.getCertinumType()) && !"1".equals(form.getAESFlag())){
			log.debug("form.getCertinumType():"+form.getCertinumType());
			form.setCertinumType(aes.decrypt(form.getCertinumType()));
		}
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi50006ZHResult app50006ZHResult = new AppApi50006ZHResult();
		
		if(Constants.method_BSP.equals(send)){
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
			log.debug("form.getBodyCardNumber(): " +form.getBodyCardNumber());
			if(!"1".equals(form.getAESFlag())){
				if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
					form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
				}
				log.debug("form.getFullName(): " +form.getFullName());
				if(!CommonUtil.isEmpty(form.getFullName())){
					form.setFullName(aes.decrypt(form.getFullName()));
				}
				log.debug("form.getTel(): " +form.getTel());
				if(!CommonUtil.isEmpty(form.getTel())){
					form.setTel(aes.decrypt(form.getTel()));
				}
				
				if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("50")){
					log.debug("form.getClientIp(): " +form.getClientIp());
					form.setClientIp("10.33.11.35");
				}
			}
			
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_MMCZYZM.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_MMCZYZM.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142502");
			
			/*String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>442856</><ChannelSeq>398243</>"
						+ "<ChkCode>cweb</><MTimeStamp>2017-03-20 15:53:30</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</>"
						+ "<STimeStamp>2017-03-20 15:53:30</><TellCode>cweb</><TranChannel>00</><TranCode>142502</><TranDate>2017-03-20</>"
						+ "<TranIP>172.16.0.161</><TranSeq>442856</>";
			*/
			log.debug("前置YFMAP接收中心报文-发送短信验证码(用于个人账号密码重置)："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_MMCZYZM.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_MMCZYZM.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50006ZHResult);
			log.debug("MAP封装成BEAN："+app50006ZHResult);
			if(!"0".equals(app50006ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50006ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app50006ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50006ZHResult.getRecode()+"  ,  描述msg : "+app50006ZHResult.getMsg());
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
		
		try{
			AES aes = new AES("00073300" ,"40" ,null ,null);
			form1.setTel(aes.encrypt("12345678901".getBytes()));
			form1.setBodyCardNumber(aes.encrypt("111111111111111111".getBytes()));
			form1.setFullName(aes.encrypt("123321".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle50032_00057400 hand = new Handle50032_00057400();
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
