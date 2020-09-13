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
import com.yondervision.yfmap.form.AppApi034Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi03403ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi03501ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle03403_00066800 implements CtrlHandleInter{

	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi034Form form = (AppApi034Form)form1;
		log.debug("00066800请求03403参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi03403ZHResult app03403ZHResult = new AppApi03403ZHResult();
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
//		form.setAccnum(aes.decrypt(form.getAccnum()));
		form.setBusitype(aes.decrypt(form.getBusitype()));
		form.setDrawamt(aes.decrypt(form.getDrawamt()));             	
		form.setLoanaccnum(aes.decrypt(form.getLoanaccnum()));
		form.setLoanbank(aes.decrypt(form.getLoanbank()));             	
		form.setLoancontrnum(aes.decrypt(form.getLoancontrnum()));
		form.setRetprin(aes.decrypt(form.getRetprin()));             	
		form.setRetint(aes.decrypt(form.getRetint()));
		form.setTranpass(aes.decrypt(form.getTranpass()));             	

		if(Constants.method_BSP.equals(send)){

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
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_CHSDTQ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_CHSDTQ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168063");
			
//			String rexml ="<AuthCode1></><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode></><FinancialDate>1</><HostBank>1</><MTimeStamp></><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>168063</><TranDate>1</><TranIP>1</><TranSeq>1</><accname>上官策</><certinum>325317452564564567</><unitaccnum>3452</><unitaccname>啦啦啦集团</><balance>485756</><usebalance>45674</><hasgjjloan>0###否</><peraccstate>2###初审通过</><transdate>2016-5-20</><hostsernum>20160520111134332</><bankcode>2###中国建设银行</><bankaccnum>409530859036</><bankaccname>建行</>";
						
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_CHSDTQ.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_CHSDTQ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03403ZHResult);
			log.debug("MAP封装成BEAN："+app03403ZHResult);
			if(!"0".equals(app03403ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03403ZHResult.getRecode());
				modelMap.put("msg", app03403ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03403ZHResult.getRecode()+"  ,  描述msg : "+app03403ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi03403ZHResult = null;
		
		if(!form.getChannel().trim().equals("40")){
		app03403ZHResult.setHasgjjloan(Tools.getCode(app03403ZHResult.getHasgjjloan()));
		app03403ZHResult.setPeraccstate(Tools.getMessage(app03403ZHResult.getPeraccstate()));
		app03403ZHResult.setBankcode(Tools.getMessage(app03403ZHResult.getBankcode()));	
		}
		
		appapi03403ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03403"+form.getCenterId()+".result", app03403ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi03403ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi03403ZHResult);
		modelMap.put("banklist", app03403ZHResult.getBanklist());
		modelMap.put("notemsg", app03403ZHResult.getNotemsg());
		return modelMap;
	}
	
}
