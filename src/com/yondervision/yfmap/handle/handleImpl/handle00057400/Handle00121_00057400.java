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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00121ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00121_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00057400请求00121参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi00121ZHResult app00121ZHResult = new AppApi00121ZHResult();
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
			
			if(!CommonUtil.isEmpty(form.getClientIp())
					&& !"1".equals(form.getAESFlag())){
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}

			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")) && !form.getChannel().trim().equals("53"))
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
			form.setIspaging("1");

			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRDKXXCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRDKXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128104");
			
			//String rexml ="<AuthCode1>1200</><AuthCode2>1200</><AuthCode3>1200</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>106112</><ChannelSeq>12373581</><ChkCode>1200</><MTimeStamp>2017-09-05 18:12:47:745000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-09-05 18:12:47:745000</><TellCode>1200</><TranChannel>0</><TranCode>128104</><TranDate>2017-08-27</><TranIP>172.16.8.101</><TranSeq>106112</><accname>郁琳琼</><apprnum>2015008681</><comcialflag>0</><commloanflag>0</><curbal>348706.13</><flag>0</><guarmode>05</><housetype>03</><lnundtkbcode>0004</><lnundtksubbcode>0401</><loanaccnum>0008140006679</><loanamt>364763.85</><loancontrcode>2015008681</><loancontrstate>09</><loanhousenum>1</><loanrate>3.250000</><loanterm>192</><loantype>0</><monthrepayamt>2574.20</><planint>944.41</><planprin>1629.79</><remainterm>169</><repaycycle>1</><repaymode>1</>";
			
			log.debug("前置YFMAP接收中心报文——个人贷款信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRDKXXCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRDKXXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app00121ZHResult);
			log.debug("MAP封装成BEAN："+app00121ZHResult);
			if(!"0".equals(app00121ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00121ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app00121ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00121ZHResult.getRecode()+"  ,  描述msg : "+app00121ZHResult.getMsg());
				return modelMap;
			}
		}

		//脱敏app 微信
		if("10".equals(form.getChannel()) || "20".equals(form.getChannel())
				|| "30".equals(form.getChannel()) || "50".equals(form.getChannel())){
			app00121ZHResult.setAccname("*"+app00121ZHResult.getAccname().substring(1));
			String handset = app00121ZHResult.getHandset();
			int len = handset.length();
			String star = "";
			if(len>=7){
				for(int i=0; i<len-7; i++){
					star += "*";
				}
				app00121ZHResult.setHandset(handset.substring(0, 3)+star+handset.substring(len-4));
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("loanaccnum", app00121ZHResult.getLoanaccnum());
		modelMap.put("loanamt", app00121ZHResult.getLoanamt());
		modelMap.put("curbal", app00121ZHResult.getCurbal());
		modelMap.put("loanterm", app00121ZHResult.getLoanterm());
		modelMap.put("monthrepayamt", app00121ZHResult.getMonthrepayamt());
		modelMap.put("loanrate", app00121ZHResult.getLoanrate());
		modelMap.put("repaymode", app00121ZHResult.getRepaymode());
		modelMap.put("commloanflag", app00121ZHResult.getCommloanflag());
		modelMap.put("loancontrcode", app00121ZHResult.getLoancontrcode());
		modelMap.put("apprnum", app00121ZHResult.getApprnum());
		modelMap.put("accname", app00121ZHResult.getAccname());
		modelMap.put("comcialflag", app00121ZHResult.getComcialflag());
		modelMap.put("guarmode", app00121ZHResult.getGuarmode());
		modelMap.put("housetype", app00121ZHResult.getHousetype());
		modelMap.put("lnundtkbcode", app00121ZHResult.getLnundtkbcode());
		modelMap.put("lnundtksubbcode", app00121ZHResult.getLnundtksubbcode());
		modelMap.put("bankcode", app00121ZHResult.getBankcode());
		modelMap.put("loancontrstate", app00121ZHResult.getLoancontrstate());
		modelMap.put("loanhousenum", app00121ZHResult.getLoanhousenum());
		modelMap.put("planprin", app00121ZHResult.getPlanprin());
		modelMap.put("planint", app00121ZHResult.getPlanint());
		modelMap.put("remainterm", app00121ZHResult.getRemainterm());
		modelMap.put("repaycycle", app00121ZHResult.getRepaycycle());
		modelMap.put("flag", app00121ZHResult.getFlag());
		modelMap.put("loantype", app00121ZHResult.getLoantype());
		modelMap.put("loandate", app00121ZHResult.getLoandate());
		modelMap.put("bankname", app00121ZHResult.getBankname());
		modelMap.put("handset", app00121ZHResult.getHandset());
		modelMap.put("oweint", app00121ZHResult.getOweint());
		modelMap.put("oweprin", app00121ZHResult.getOweprin());
		modelMap.put("owepun", app00121ZHResult.getOwepun());
		modelMap.put("undueprin", app00121ZHResult.getUndueprin());
		modelMap.put("newint", app00121ZHResult.getNewint());
		modelMap.put("currenddate", app00121ZHResult.getCurrenddate());
		modelMap.put("ahdrepaylowamt", app00121ZHResult.getAhdrepaylowamt());
		modelMap.put("dedbankcode", app00121ZHResult.getDedbankcode());
		modelMap.put("dedaccnum", app00121ZHResult.getDedaccnum());
		modelMap.put("enddate", app00121ZHResult.getEnddate());
		modelMap.put("resuflag", app00121ZHResult.getResuflag());
		modelMap.put("instcode", app00121ZHResult.getInstcode());
		modelMap.put("cleardate", app00121ZHResult.getCleardate());
		return modelMap;
	}
/*	
	public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("aaaaa");
		Handle00121_00057400 hand = new Handle00121_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
