package com.yondervision.yfmap.handle.handleImpl.handle00066800;

import java.io.File;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.ibm.db2.jcc.b.n;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00605Result;
import com.yondervision.yfmap.result.kunming.AppApi00606ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi00608ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00608_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("00066800请求00608参数："+CommonUtil.getStringParams(form));
		/* 模拟返回开始  */
		AppApi00608ZHResult app00608ZHResult= new AppApi00608ZHResult();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			
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
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_QDKSS.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_QDKSS.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168023");
			
//			String rexml ="<AuthCode1>2</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>2</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>168023</><TranDate>1</><TranIP>1</><TranSeq>1</><accname1>2344</><accname2>fgretr</><allamt>3436453532</><attermdate>2</><beginintdate>3</><cerid1>3</><cerid2>3423</><dqrq_hkjh>342</><instcode>32423</><loanbal>32432</><loansum>96</><loanterm>678</><loantype>674</><minamt>5465</><newcint>45</><oweall>345</><oweint>23543</><oweprin>87867</><owepun>567</><o_remainmonth>356</><rate>456</><retcycle>4</><retloanmode>1</>";
			
//			rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_QDKSS.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_QDKSS.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00608ZHResult);
			log.debug("MAP封装成BEAN："+app00608ZHResult);
			if(!"0".equals(app00608ZHResult.getRecode())){
				modelMap.put("recode", app00608ZHResult.getRecode());
				modelMap.put("msg", app00608ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00608ZHResult.getRecode()+"  ,  描述msg : "+app00608ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00608Result = null;
		MoneyNumberTran mnTran = new MoneyNumberTran();
		
		app00608ZHResult.getAccname1();
		app00608ZHResult.getAccname2();
		app00608ZHResult.setAllamt(mnTran.moneyTran(app00608ZHResult.getAllamt()));
		app00608ZHResult.getAttermdate();
		app00608ZHResult.getBeginintdate();
		app00608ZHResult.getCerid1();
		app00608ZHResult.getCerid2();
		app00608ZHResult.getDqrq_hkjh();
		app00608ZHResult.getInstcode();
		app00608ZHResult.setLoanbal(mnTran.moneyTran(app00608ZHResult.getLoanbal()));
		app00608ZHResult.setLoansum(mnTran.moneyTran(app00608ZHResult.getLoansum()));
		app00608ZHResult.getLoanterm();
		app00608ZHResult.getLoantype();
		app00608ZHResult.setMinamt(mnTran.moneyTran(app00608ZHResult.getMinamt()));
		app00608ZHResult.setNewcint(mnTran.moneyTran(app00608ZHResult.getNewcint()));
		app00608ZHResult.setOweall(mnTran.moneyTran(app00608ZHResult.getOweall()));
		app00608ZHResult.setOweint(mnTran.moneyTran(app00608ZHResult.getOweint()));
		app00608ZHResult.setOweprin(mnTran.moneyTran(app00608ZHResult.getOweprin()));
		app00608ZHResult.setOwepun(mnTran.moneyTran(app00608ZHResult.getOwepun()));
		app00608ZHResult.getO_remainmonth();
		app00608ZHResult.setRate(mnTran.numberTran(app00608ZHResult.getRate()));
		app00608ZHResult.getRetcycle();
		app00608ZHResult.getRetloanmode();
		
		appapi00608Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00608"+form.getCenterId()+".result", app00608ZHResult);
	
		Iterator<TitleInfoNameFormatBean> it1 = appapi00608Result.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00608Result);
//		log.info(Constants.LOG_HEAD+"appApi00605 end.");
		return modelMap;
	}

}
