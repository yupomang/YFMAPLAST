package com.yondervision.yfmap.handle.handleImpl.handle00047000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi303Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi30303Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi30305Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30311_00047201
* @Description: 预约确认
* 
*/ 
public class Handle30311_00047000 implements CtrlHandleInter {
Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30311 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约确认 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		//String fname = formatter.format(date)+"api30311WDQRGJJ.txt";//预约网点确认
		BaoGangAppApi303Result app00802Result = new BaoGangAppApi303Result();
		List<QvZhiAppApi30305Result> result = new ArrayList<QvZhiAppApi30305Result>();
		if(Constants.method_BSP.equals(send)){
			if("10".equals(form.getChannel()))
			{
				form.setChannel("21");
			}else if("20".equals(form.getChannel()))
			{
				form.setChannel("31");
			}
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			//form.setAppobranchid("00004711");
			AES aes = new AES();
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
			HashMap map = BeanUtil.transBean2Map(form);
			HashMap resultMap1 = new HashMap();
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/tomcat/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00047201/BSP_REQ_YYQR.txt", map, req);
			String xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YYQR01.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149379");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><applyid>2800001282101</>";
			
			log.debug("中心返回YFMAP报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYQR01.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			resultMap1.put("vicecontent", app00802Result.getVicecontent());
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}
			
			xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YYQR02.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149369");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><applyid>2800001282101</>";
			
			log.debug("中心返回YFMAP报文："+rexml);
			
			resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYQR.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}
			
			
			
			xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YYQR.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149375");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><applyid>2800001282101</>";
			
			log.debug("中心返回YFMAP报文："+rexml);
			
			resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYQR.txt", rexml, req);
			resultMap.put("vicecontent", resultMap1.get("vicecontent"));
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("counts", app00802Result.getVicecontent());//已预约人数
		modelMap.put("applyno", app00802Result.getMatname());//预约号
		log.info(Constants.LOG_HEAD+"appApi30311 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi30309Form form1 = new AppApi30309Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00047201");
		form1.setLogid("113");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle30311_00047000 hand = new Handle30311_00047000();
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
