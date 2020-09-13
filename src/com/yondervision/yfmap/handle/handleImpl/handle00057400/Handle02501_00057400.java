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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi02501ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02501_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;

		log.debug("00057400请求02501参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi02501ZHResult app02501ZHResult = new AppApi02501ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
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

			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("50")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			form.setIspaging("1");
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWLXPZ.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWLXPZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142509");
			
			//String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>31505049</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-12-13 10:53:24</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-13 10:53:24</><TellCode>cweb</><TranChannel>00</><TranCode>142509</><TranDate>2016-12-13</><TranIP>172.16.0.161</><TranSeq>31505049</><bal>1300.48</><begdate>2015-07-01</><enddate>2016-06-30</><increbal>0.00</><increintaccu>0.00</><increrate>1.500000</><indiacctype>1</><keepbal>19.53</><keepintaccu>468827.70</><keeprate>1.500000</><unitaccname>宁波市海曙区饮食服务有限责任公司南站饭店</><unitaccnum>010100000052</>";
			
			log.debug("前置YFMAP接收中心报文——年度结息数据下载："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWLXPZ.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWLXPZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app02501ZHResult);
			log.debug("MAP封装成BEAN："+app02501ZHResult);
			if(!"0".equals(app02501ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02501ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02501ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02501ZHResult.getRecode()+"  ,  描述msg : "+app02501ZHResult.getMsg());
				return modelMap;
			}
		}
		
		String indiacctype = PropertiesReader.getProperty("yingshe.properties", "indiacctypeType"+app02501ZHResult.getIndiacctype()+form.getCenterId());
		if(indiacctype == null||indiacctype.equals("")){
			app02501ZHResult.setIndiacctype("--");
		}else {
			app02501ZHResult.setIndiacctype(indiacctype);
		}
		
		List<TitleInfoNameFormatBean> appapi02501Result = null;		
		appapi02501Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02502"+form.getCenterId()+".result", app02501ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi02501Result.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		

		
		
		if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
		{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi02501Result);
		}else{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");			
			modelMap.put("unitaccname", app02501ZHResult.getUnitaccname());
			modelMap.put("unitaccnum", app02501ZHResult.getUnitaccnum());
			modelMap.put("bal", app02501ZHResult.getBal());
			modelMap.put("begdate", app02501ZHResult.getBegdate());
			modelMap.put("enddate", app02501ZHResult.getEnddate());
			modelMap.put("increbal", app02501ZHResult.getIncrebal());
			modelMap.put("increintaccu", app02501ZHResult.getIncreintaccu());
			modelMap.put("increrate", app02501ZHResult.getIncrerate());
			modelMap.put("indiacctype", app02501ZHResult.getIndiacctype());
			modelMap.put("keepbal", app02501ZHResult.getKeepbal());
			modelMap.put("keepintaccu", app02501ZHResult.getKeepintaccu());
			modelMap.put("keeprate", app02501ZHResult.getKeeprate());	
		}

		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("aaaaa");
		form1.setIndiacctype("1");
		Handle02501_00057400 hand = new Handle02501_00057400();
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
