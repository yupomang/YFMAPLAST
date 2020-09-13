package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.io.File;
import java.text.NumberFormat;
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
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00601ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi00605Result;
import com.yondervision.yfmap.result.kunming.AppApi00605ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00605_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.debug("00077500请求00605参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		
		/* 模拟返回开始  */
		AppApi00605ZHResult app00605ZHResult= new AppApi00605ZHResult();
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
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_DKQKCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_GR_DKQKCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314035");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode></><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>0</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>314035</><TranDate>1</><TranIP>1</><TranSeq>1</><oweprin>1</><oweint>1</><owepun>1</><oweamt>1</><owenum>1</>";
//			rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GR_DKQKCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_GR_DKQKCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00605ZHResult);
			log.debug("MAP封装成BEAN："+app00605ZHResult);
			if(!"0".equals(app00605ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00605ZHResult.getRecode());
				modelMap.put("msg", app00605ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00605ZHResult.getRecode()+"  ,  描述msg : "+app00605ZHResult.getMsg());
				return modelMap;
			}
		}
		List<TitleInfoNameFormatBean> appapi00605Result = null;
		String str = "";
		MoneyNumberTran muTran = new MoneyNumberTran();
		if(form.getChannel().trim().equals("60")){
			str = "本期未还本金"+app00605ZHResult.getOweprin()+"元,"+"本期未还利息"+app00605ZHResult.getOweint()+"元,"+"欠还罚息"+app00605ZHResult.getOwepun()+"元,"+"欠还总额"+app00605ZHResult.getOweamt()+"元,"+"欠还期数"+app00605ZHResult.getOwenum()+"期";
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", str);
			log.info(Constants.LOG_HEAD+"appApi00606 end.");
			return modelMap;
		}else{		
			app00605ZHResult.setOweprin(muTran.moneyTran(app00605ZHResult.getOweprin()));
			app00605ZHResult.setOweint(muTran.moneyTran(app00605ZHResult.getOweint()));
			app00605ZHResult.setOwepun(muTran.moneyTran(app00605ZHResult.getOwepun()));
			app00605ZHResult.setOweamt(muTran.moneyTran(app00605ZHResult.getOweamt()));
			app00605ZHResult.getOwenum();
		}
		
		appapi00605Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00605"+form.getCenterId()+".result", app00605ZHResult);
	
		/* 模拟返回结束  */	
		Iterator<TitleInfoNameFormatBean> it1 = appapi00605Result.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00605Result);
		log.info(Constants.LOG_HEAD+"appApi00606 end.");
		return modelMap;
	}

}
