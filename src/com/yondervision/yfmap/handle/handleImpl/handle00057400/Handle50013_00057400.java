package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.yfmap.common.util.SecurityUtil;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi50013ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50013_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50002Form form = (AppApi50002Form)form1;
		System.out.println("YFMAP发往中心——单位登录");
		log.debug("00057400请求50013参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		//针对住建委网站加密问题修改 xzw
		if(!"1".equals(form.getAESFlag())&&!"30".equals(form.getChannel())){
			if(!CommonUtil.isEmpty(form.getPassword())){
				log.debug("form.getPassword():"+form.getPassword());
				form.setPassword(aes.decrypt(form.getPassword()));
			}
			if(!CommonUtil.isEmpty(form.getUnitaccnum())){
				log.debug("form.getUnitaccnum():"+form.getUnitaccnum());
				form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
			}
			if(!CommonUtil.isEmpty(form.getClientIp())){
				log.debug("form.getClientIp():"+form.getClientIp());
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
		}
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("60")){
			log.debug("form.getClientIp(): " +form.getClientIp());
			form.setClientIp("10.33.11.35");
		}
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi50013ZHResult app50013ZHResult = new AppApi50013ZHResult();
		
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
			byte[] c = DESForJava.encryption(form.getPassword(),"12345678");
			c = DESForJava.bcd_to_asc(c);
			form.setPassword(new String(c));
			//单位登录要求加盐加密登录 ll 2019.11.4
			/*String oppwd = form.getPassword();
			//String salt = SecurityUtil.genUUID();
			String newpwd = SecurityUtil.HMAC("12345678", oppwd);
			form.setPassword(newpwd);*/

			String transcode = null;
/*			if(!CommonUtil.isEmpty(form.getDlfs())){
				if(form.getDlfs().equals("0")){
					log.debug("form.getDlfs():"+form.getDlfs());
					transcode="142506";
				}else if(form.getDlfs().equals("1")){
					log.debug("form.getDlfs():"+form.getDlfs());
					transcode="142200";
				}
			}*/
			
			if(form.getChannel().trim().equals("40")){
				log.debug("网厅专用登录");
				transcode="142200";		
			}else {
					log.debug("其他渠道登录");
					transcode="142506";
			}
			log.debug("交易号transcode："+transcode);
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("transcode",transcode);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWDL.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWDL.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, transcode);
			//String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</><unitaccnum>123456</><unitaccname>abc</><flag>01</><webbusistat>1</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWDL.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWDL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50013ZHResult);
			log.debug("MAP封装成BEAN："+app50013ZHResult);
			if(!"0".equals(app50013ZHResult.getRecode())){
				modelMap.put("recode", app50013ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app50013ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50013ZHResult.getRecode()+"  ,  描述msg : "+app50013ZHResult.getMsg());
				return modelMap;
			}
		}
		
		modelMap.clear();		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		if(form.getChannel().trim().equals("40")){
			log.debug("网厅专用登录");
			modelMap.put("unitaccnum", app50013ZHResult.getUnitaccnum());
			modelMap.put("unitaccname",app50013ZHResult.getUnitaccname());
			modelMap.put("initialpwdflag",app50013ZHResult.getInitialpwdflag());
			modelMap.put("webbusistat",app50013ZHResult.getWebbusistat());
			modelMap.put("brcCode", app50013ZHResult.getBrcCode());
			modelMap.put("unitcertitype", app50013ZHResult.getUnitcertitype());
			modelMap.put("unitcertinum", app50013ZHResult.getUnitcertinum());
			modelMap.put("flag1", app50013ZHResult.getFlag1());
			modelMap.put("ukeystate", app50013ZHResult.getUkeystate());
			modelMap.put("unitaccname_eb", app50013ZHResult.getUnitaccname_eb());
			modelMap.put("unioncode", app50013ZHResult.getUnioncode());
			modelMap.put("unitkind", app50013ZHResult.getUnitkind());
			
		}else {
			log.debug("其他渠道登录");
		    modelMap.put("unitaccnum", app50013ZHResult.getUnitaccnum());
		    modelMap.put("unitaccname", app50013ZHResult.getUnitaccname());
		    modelMap.put("flag", app50013ZHResult.getFlag());
		}

		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi50002Form form1 = new AppApi50002Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setDlfs("1");
		
		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPassword(aes.encrypt("123456".getBytes()));
			form1.setUnitaccnum(aes.encrypt("222333".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle50013_00057400 hand = new Handle50013_00057400();
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
