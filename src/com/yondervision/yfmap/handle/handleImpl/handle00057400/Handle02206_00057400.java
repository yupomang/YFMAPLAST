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

import com.ibm.db2.jcc.a.a;
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
import com.yondervision.yfmap.result.ningbo.AppApi02101ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi021_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02206_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00057400请求02102参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		AppApi02101ZHResult app02101ZHResult = new AppApi02101ZHResult();
		 
		List<AppApi021_01ZHResult> list = new ArrayList<AppApi021_01ZHResult>();
		log.debug("form:"+form);
		HashMap resultMap = null;
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
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("0");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_BJXXCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_BJXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111411");
			
			//String rexml ="<AuthCode1>9711</><AuthCode2>9711</><AuthCode3>9711</><AuthFlag></><BrcCode>05740008</><BusiSeq>89663</><ChannelSeq>12351771</><ChkCode>9711</><MTimeStamp>2017-08-05 17:15:14:851000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-05 17:15:14:849000</><TellCode>9711</><TranChannel>00</><TranCode>111411</><TranDate>2017-08-10</><TranIP>172.16.8.100</><TranSeq>89663</><consum>99</><rsufilename>BJXXCX_010100000075_20170810</><rsufilepath>/bsptest/</>";
			
			log.debug("前置YFMAP接收中心报文——单位账户发生额明细批量查询下载："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_BJXXCX.txt", rexml, req);
			//resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_BJXXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02101ZHResult);
			log.debug("MAP封装成BEAN："+app02101ZHResult);
			if(!"0".equals(app02101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02101ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02101ZHResult.getRecode()+"  ,  描述msg : "+app02101ZHResult.getMsg());
				return modelMap;
			}
		}
		System.out.println("查询批量文件："+app02101ZHResult.getFilename());
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("rsufilename", app02101ZHResult.getFilename());
		modelMap.put("rsufilepath", app02101ZHResult.getFilepath());
		modelMap.put("consum", app02101ZHResult.getTotalnum());
		modelMap.put("sendsum", app02101ZHResult.getSendsum());
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setQuerytype("1");
		form1.setChgtype("1001");
		form1.setBegdate("2017-01-01");
		form1.setEnddate("2017-07-01");
		form1.setIspaging("1");
		form1.setPagenum("1");
		form1.setPagerows("20");
		form1.setUnitaccnum("123");
		form1.setAccnum("2222222222");
		form1.setIndiacctype("3");
		form1.setAccname("sadsadwew");
		form1.setTranstype("2");
		form1.setRegnum("222233333333332");
		form1.setDptype("8");
		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
		Handle02206_00057400 hand = new Handle02206_00057400();
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
