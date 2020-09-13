package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.result.kunming.AppApi03602ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03602_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00066800请求03602参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi03602ZHResult app03602ZHResult = new AppApi03602ZHResult();
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);             	
		if(!CommonUtil.isEmpty(form.getRetamt())){
			log.debug("form.getRetamt():"+form.getRetamt());
			form.setRetamt(aes.decrypt(form.getRetamt()));
		}
		if(!CommonUtil.isEmpty(form.getTranflag())){
			log.debug("form.getTranflag():"+form.getTranflag());
			form.setTranflag(aes.decrypt(form.getTranflag()));
		}
		
		if(!CommonUtil.isEmpty(form.getDedaccnum())){
			log.debug("form.getDedaccnum():"+form.getDedaccnum());
			form.setDedaccnum(aes.decrypt(form.getDedaccnum()));
		}
		
		if(!CommonUtil.isEmpty(form.getLncontrnum())){
			log.debug("form.getDedaccnum():"+form.getLncontrnum());
			form.setLncontrnum(aes.decrypt(form.getLncontrnum()));
		}
		
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
			

			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_WTDZHKCL.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_WTDZHKCL.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168091");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>2</><AuthFlag>1</><BrcCode>2</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>168091</><TranDate>1</><TranIP>1</><TranSeq>23</><allamt>3</><lnaccnum>6</><newenddate>4</><newint>3</><newprin>3</><oldenddate>4</><oldint>3</><oldprin>3</><oldpunint>3</><oldsum>3</><paybankcode>3</><paybankname>4</><preamt>3</><rembal>4</><remterm>4</><retmode>4</><retzq>4</><revbankcode>4</><revbankname>4</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_WTDZHKCL.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_WTDZHKCL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03602ZHResult);
			log.debug("MAP封装成BEAN："+app03602ZHResult);
			if(!"0".equals(app03602ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03602ZHResult.getRecode());
				modelMap.put("msg", app03602ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03602ZHResult.getRecode()+"  ,  描述msg : "+app03602ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi03602ZHResult = null;
		
		appapi03602ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03602"+form.getCenterId()+".result", app03602ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi03602ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi03602ZHResult);
		return modelMap;
	}
	
}
