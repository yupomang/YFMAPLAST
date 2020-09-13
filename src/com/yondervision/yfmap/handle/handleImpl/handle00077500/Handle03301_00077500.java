package com.yondervision.yfmap.handle.handleImpl.handle00077500;

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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApi03301Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi03301ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03301_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi03301Form form = (AppApi03301Form)form1;
		log.debug("00077500请求03301参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi03301ZHResult app03301ZHResult = new AppApi03301ZHResult();
		
		
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
		if(!CommonUtil.isEmpty(form.getAccnum1())){
			log.debug("form.getAccnum1():"+form.getAccnum1());
			form.setAccnum1(aes.decrypt(form.getAccnum1()));
		}
		if(!CommonUtil.isEmpty(form.getAccnum2())){
			log.debug("form.getAccnum2():"+form.getAccnum2());
			form.setAccnum2(aes.decrypt(form.getAccnum2()));
		}
		if(!CommonUtil.isEmpty(form.getMultiloan())){
			log.debug("form.getMultiloan():"+form.getMultiloan());
			form.setMultiloan(aes.decrypt(form.getMultiloan()));
		}
		if(!CommonUtil.isEmpty(form.getLoantype())){
			log.debug("form.getLoantype():"+form.getLoantype());
			form.setLoantype(aes.decrypt(form.getLoantype()));
		}
		if(!CommonUtil.isEmpty(form.getFamilynum())){
			log.debug("form.getFamilynum():"+form.getFamilynum());
			form.setFamilynum(aes.decrypt(form.getFamilynum()));
		}
		if(!CommonUtil.isEmpty(form.getFamincome())){
			log.debug("form.getFamincome():"+form.getFamincome());
			form.setFamincome(aes.decrypt(form.getFamincome()));
		}
		if(!CommonUtil.isEmpty(form.getSecloanflag())){
			log.debug("form.getSecloanflag:"+form.getSecloanflag());
			form.setSecloanflag(aes.decrypt(form.getSecloanflag()));
		}
		if(!CommonUtil.isEmpty(form.getHousetype())){
			log.debug("form.getHousetype():"+form.getHousetype());
			form.setHousetype(aes.decrypt(form.getHousetype()));
		}
		if(!CommonUtil.isEmpty(form.getHouseamt())){
			log.debug("form.getHouseamt():"+form.getHouseamt());
			form.setHouseamt(aes.decrypt(form.getHouseamt()));
		}
		if(!CommonUtil.isEmpty(form.getGuartolworth())){
			log.debug("form.getGuartolworth():"+form.getGuartolworth());
			form.setGuartolworth(aes.decrypt(form.getGuartolworth()));
		}
		if(!CommonUtil.isEmpty(form.getFirstamt())){
			log.debug("form.getFirstamt():"+form.getFirstamt());
//			form.setFirstamt(new String(DESForJava.encryption(aes.decrypt(form.getFirstamt()),"12345678")));
			form.setFirstamt(aes.decrypt(form.getFirstamt()));
		}
		
//		log.debug("form.getFirstamt():"+form.getFirstamt());
		

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
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKYWZGJY.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_DKYWZGJY.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314013");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>000000</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314016</><TranDate>5</><TranIP>2</><TranSeq>9</><accname1>张三</><loanyear>23</><loanmoney>5673</><rate>0.3</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKYWZGJY.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_DKYWZGJY.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03301ZHResult);
			log.debug("MAP封装成BEAN："+app03301ZHResult);
			if(!"0".equals(app03301ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03301ZHResult.getRecode());
				modelMap.put("msg", app03301ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03301ZHResult.getRecode()+"  ,  描述msg : "+app03301ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi03301ZHResult = null;
		
		appapi03301ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03301"+form.getCenterId()+".result", app03301ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi03301ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi03301ZHResult);
		return modelMap;
	}
	
}
