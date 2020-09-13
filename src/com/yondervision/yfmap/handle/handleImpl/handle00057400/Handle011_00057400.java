package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi01101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle011_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {

		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00057400请求01101参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		AppApi01101ZHResult app01101ZHResult = new AppApi01101ZHResult();	
		
		HashMap resultMap = null;
		if(Constants.method_BSP.equals(send)){//xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
//			form.setClientIp(aes.decrypt(form.getClientIp()));
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91"))&&(!form.getChannel().trim().equals("53")))
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
			if(!CommonUtil.isEmpty(form.getPwd())){
				String pwd = aes.decrypt(form.getPwd());
				if(!CommonUtil.isEmpty(pwd)){
					byte[] c = DESForJava.encryption(pwd,"12345678");
					c = DESForJava.bcd_to_asc(c);
					form.setPwd(new String(c));
				}else{
					form.setPwd("");
				}
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
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			if (form.getChannel().trim().equals("50")) {
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCX.txt", map, req);
				//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DKJDCX.txt", map, req);
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();			
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142815");
				
				//String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</>"
				//	+ "<appdate_wb>20170101</><approvedate_wb>20170101</><bankname>aaaaa</><loaneename>bbbbb</>";
				
				log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
				
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKJDCX.txt", rexml, req);
				//resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DKJDCX.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);	
				
				BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
				log.debug("MAP封装成BEAN："+app01101ZHResult);
				
				if(!"0".equals(app01101ZHResult.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app01101ZHResult.getRecode());
					modelMap.put("msg", app01101ZHResult.getMsg());			
					log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
					return modelMap;
				}					
				List<TitleInfoNameFormatBean> appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result", app01101ZHResult);
				Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
				while (it1.hasNext()) {
					TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
					log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
				}	
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", appapi01101ZHResult);
			} else {
				
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCX.txt", map, req);
				//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DKJDCX.txt", map, req);
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();			
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142815");
				
				//String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</>"
				//		+ "<appdate_wb>20170101</><approvedate_wb>20170101</><bankname>aaaaa</><loaneename>bbbbb</>";
				
				log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
				
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKJDCX.txt", rexml, req);
				//resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DKJDCX.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				
				BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
				log.debug("MAP封装成BEAN："+app01101ZHResult);
				
				if(!"0".equals(app01101ZHResult.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app01101ZHResult.getRecode());
					modelMap.put("msg", app01101ZHResult.getMsg());			
					log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
					return modelMap;
				}	
				List<TitleInfoNameFormatBean> appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result", app01101ZHResult);
				Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
				while (it1.hasNext()) {
					TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
					log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
				}	
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", appapi01101ZHResult);
			}
			
		}
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi00501Form form1 = new AppApi00501Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBodyCardNumber("999999888888");
		form1.setPwd("123456");
		try{
			AES aes = new AES("00073300" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle011_00057400 hand = new Handle011_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
