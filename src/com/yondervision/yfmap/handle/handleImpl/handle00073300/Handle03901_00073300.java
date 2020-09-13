package com.yondervision.yfmap.handle.handleImpl.handle00073300;

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
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi03901ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle03901_00073300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("00073300请求03901参数："+CommonUtil.getStringParams(form));
		AppApi03901ZHResult app03901ZHResult = new AppApi03901ZHResult();
		             	

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
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWYW_GRFC.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DWYW_GRFC.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110066");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>110066</><TranDate>1</><TranIP>1</><TranSeq>1</><accinstcode>2</><agentinstcode>3</><hostsernum>1</><sealnum>1</><TranDate>1</><TranSeq>1</><unitaccnum>134645676565234</><unitaccname>1</><unitbasenumber>1</><unitmonpaysum>1</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWYW_GRFC.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DWYW_GRFC.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03901ZHResult);
			log.debug("MAP封装成BEAN："+app03901ZHResult);
			if(!"0".equals(app03901ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03901ZHResult.getRecode());
				modelMap.put("msg", app03901ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03901ZHResult.getRecode()+"  ,  描述msg : "+app03901ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi03901ZHResult = null;
		MoneyNumberTran mnTran =new MoneyNumberTran();
		if(!form.getChannel().trim().equals("40")){
		app03901ZHResult.setAccinstcode(Tools.getMessage(app03901ZHResult.getAccinstcode()));
		app03901ZHResult.setAgentinstcode(Tools.getMessage(app03901ZHResult.getAgentinstcode()));
		app03901ZHResult.getHostsernum();
		app03901ZHResult.getSealnum();
		app03901ZHResult.getTranDate();
		app03901ZHResult.getTranSeq();
		app03901ZHResult.setUnitaccnum(Tools.getDesensitizationUnitaccnum(app03901ZHResult.getUnitaccnum()));
		app03901ZHResult.getUnitaccname();
		app03901ZHResult.setUnitbasenumber(mnTran.moneyTran(app03901ZHResult.getUnitbasenumber()));
		app03901ZHResult.setUnitmonpaysum(mnTran.moneyTran(app03901ZHResult.getUnitmonpaysum()));
		}
		else{
			app03901ZHResult.getAccinstcode();
			app03901ZHResult.getAgentinstcode();
			app03901ZHResult.getHostsernum();
			app03901ZHResult.getSealnum();
			app03901ZHResult.getTranDate();
			app03901ZHResult.getTranSeq();
			app03901ZHResult.getUnitaccnum();
			app03901ZHResult.getUnitaccname();
			app03901ZHResult.setUnitbasenumber(mnTran.moneyTran(app03901ZHResult.getUnitbasenumber()));
			app03901ZHResult.setUnitmonpaysum(mnTran.moneyTran(app03901ZHResult.getUnitmonpaysum()));
		}
		
		appapi03901ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03901"+form.getCenterId()+".result", app03901ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi03901ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi03901ZHResult);
		return modelMap;
	}
	
}
