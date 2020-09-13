package com.yondervision.yfmap.handle.handleImpl.handle00047100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.mengdian.AppApi40101Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50007_00047100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40114 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		AES aes =new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		byte[] b = DESForJava.encryption(form.getPassword(), "12345678");
		byte[] c = DESForJava.bcd_to_asc(b);
		form.setPassword(new String(c));
		log.debug(""+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi40101Result app40101Result = new AppApi40101Result();
		Map<String,String> result_map = new HashMap<String,String>();
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DL.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142805");
//			String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>12345678</><BusiSeq>543</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-08-09 11:38:12</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-08-09 11:38:12</><TellCode>cweb</><TranChannel>00</><TranCode>112805</><TranDate>2016-08-09</><TranIP>192.168.10.20</><TranSeq>543</><actmp1024>/bsprun/00000000/fil/150104197207150539_543</>";
			log.debug("中心返回前置YFMAP报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DL.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}else{
				result_map.put("loancontrnum", app40101Result.getLoancontrnum());
				result_map.put("fullName", app40101Result.getAccname());
				result_map.put("flag", app40101Result.getFlag());
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result_map);
		log.info(Constants.LOG_HEAD+"appApi40114 end.");
		return modelMap;
	}

public static void main(String[] args) throws UnsupportedEncodingException{
	byte[] b = DESForJava.encryption("111111", "123456");
	byte[] c = DESForJava.bcd_to_asc(b);
	System.out.println(new String(c));
	System.out.println(DESForJava.decryption(DESForJava.bcd_to_asc("5F368625B6EB9FF8".getBytes()), "12345678"));
}
}
