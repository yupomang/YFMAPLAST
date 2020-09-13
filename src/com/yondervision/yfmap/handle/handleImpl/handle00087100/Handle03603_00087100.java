package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi03603ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03603_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.info("【资金类业务03603】-------【start】");
		AppApi03603ZHResult app03603ZHResult = new AppApi03603ZHResult();
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getLncontrnum())){
			log.debug("form.getDedaccnum():"+form.getLncontrnum());
			form.setLncontrnum(aes.decrypt(form.getLncontrnum()));
		} 
		if(!CommonUtil.isEmpty(form.getRetamt())){
			log.debug("form.getDedaccnum():"+form.getRetamt());
			form.setRetamt(aes.decrypt(form.getRetamt()));
		} 
		if(!CommonUtil.isEmpty(form.getTranflag())){
			log.debug("form.getDedaccnum():"+form.getTranflag());
			form.setTranflag(aes.decrypt(form.getTranflag()));
		} 

		if(Constants.method_BSP.equals(send)){

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			

			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_WTCHDCL.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_WTCHDCL.txt", map, req);
			log.info("request 前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168092");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>2</><AuthFlag>1</><BrcCode>2</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>168092</><TranDate>1</><TranIP>1</><TranSeq>23</><accnum>1</><allamt>23</><lnaccnum>3</><newenddate>3</><newint>2</><newprin>3</><oldenddate>2</><oldint>3</><oldprin>3</><oldpunint>3</><oldsum>3</><preamt>3</><rembal>3</><remterm>3</><retmode>3</><retzq>3</><revbankcode>3</><revbankname>3</>";
			
			log.info("response 前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_WTCHDCL.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_WTCHDCL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03603ZHResult);
			log.debug("MAP封装成BEAN："+app03603ZHResult);
			if(!"0".equals(app03603ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03603ZHResult.getRecode());
				modelMap.put("msg", app03603ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03603ZHResult.getRecode()+"  ,  描述msg : "+app03603ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi03603ZHResult = null;
		
		appapi03603ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03603"+form.getCenterId()+".result", app03603ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi03603ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi03603ZHResult);
		modelMap.put("acc_flag", app03603ZHResult.getAcc_flag());
		modelMap.put("acc_amt", app03603ZHResult.getAcc_amt());
		log.info("【资金类业务03603】-------【end】");
		return modelMap;
	}
	
}
