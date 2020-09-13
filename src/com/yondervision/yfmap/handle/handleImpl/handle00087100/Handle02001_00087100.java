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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi02001ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle02001_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi02001ZHResult app02001ZHResult = new AppApi02001ZHResult();		             	

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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DW_DWJBXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_DW_DWJBXXCX.txt", map, req);			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314019");
			
//			String rexml ="<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag></><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode></><FinancialDate></><HostBank></><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>OK</><STimeStamp></><SubBank></><TellCode></><TranChannel></><TranCode>314019</><TranDate></><TranIP></><TranSeq></><unitaccname>1</><unitaccnum>1</><unitcustid>1</><unitaddr>1</><accbankcode>1</><accinstcode>1</><accstate>1</><balance>1</><basacc>1</><basaccbank>1</><basenumber>1</><existdate>1</><lastpaydate>1</><licensenum>1</><lpaym>1</><opendate>1</><orgcode>1</><perprop>1</><unitprop>1</><postcode>1</><preaccnum>1</><prebalance>1</><salarydate>1</><sealnum>1</><selfintype>1</><selsum>1</><subrel>1</><unitchar>1</><unitcardid>1</><unitjurdeputy>1</><unitlinkman>1</><unitphone>1</><unittrade>1</>";
			
			log.debug("前置YFMAP接收中心报文——单位基本信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DW_DWJBXXCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_DW_DWJBXXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02001ZHResult);
			log.debug("MAP封装成BEAN："+app02001ZHResult);
			if(!"0".equals(app02001ZHResult.getRecode() )){
				modelMap.clear();
				modelMap.put("recode", app02001ZHResult.getRecode());
				modelMap.put("msg", app02001ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02001ZHResult.getRecode()+"  ,  描述msg : "+app02001ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi02001ZHResult = null;
		MoneyNumberTran mnTran = new MoneyNumberTran();
		app02001ZHResult.getUnitaccname();
		app02001ZHResult.getUnitaccnum();
		app02001ZHResult.getUnitcustid();
		app02001ZHResult.getUnitaddr();
		app02001ZHResult.setAccbankcode(Tools.getMessage(app02001ZHResult.getAccbankcode()));
		app02001ZHResult.setAccinstcode(Tools.getMessage(app02001ZHResult.getAccinstcode()));
		app02001ZHResult.setAccstate(Tools.getMessage(app02001ZHResult.getAccstate()));
		app02001ZHResult.setBalance(mnTran.moneyTran(app02001ZHResult.getBalance()));		
		app02001ZHResult.getBasacc();
		app02001ZHResult.getBasaccbank();
		app02001ZHResult.setBasenumber(mnTran.moneyTran(app02001ZHResult.getBasenumber()));
		app02001ZHResult.getExistdate();
		app02001ZHResult.getLastpaydate();
		app02001ZHResult.getLicensenum();
		app02001ZHResult.getLpaym();
		app02001ZHResult.getOpendate();
		app02001ZHResult.getOrgcode();
		app02001ZHResult.setPerprop(app02001ZHResult.getPerprop());
		app02001ZHResult.setUnitprop(app02001ZHResult.getUnitprop());
		app02001ZHResult.getPostcode();
		app02001ZHResult.getPreaccnum();
		app02001ZHResult.setPrebalance(mnTran.moneyTran(app02001ZHResult.getPrebalance()));
		app02001ZHResult.getSalarydate();
		app02001ZHResult.getSealnum();
		app02001ZHResult.setSelfintype(Tools.getMessage(app02001ZHResult.getSelfintype()));
		app02001ZHResult.setSelsum(mnTran.moneyTran(app02001ZHResult.getSelsum()));
		app02001ZHResult.setSubrel(Tools.getMessage(app02001ZHResult.getSubrel()));
		app02001ZHResult.setUnitchar(Tools.getMessage(app02001ZHResult.getUnitchar()));
		app02001ZHResult.getUnitcardid();
		app02001ZHResult.getUnitjurdeputy();
		app02001ZHResult.getUnitlinkman();
		app02001ZHResult.getUnitphone();
		app02001ZHResult.setUnittrade(Tools.getMessage(app02001ZHResult.getUnittrade()));
		
		appapi02001ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02001"+form.getCenterId()+".result", app02001ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi02001ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi02001ZHResult);
		return modelMap;
	}
	
}
