package com.yondervision.yfmap.handle.handleImpl.handle00087100;

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
import com.yondervision.yfmap.result.kunming.AppApi02401ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi024_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02401_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi02401ZHResult app02401ZHResult = new AppApi02401ZHResult();
		 
		List<AppApi024_01ZHResult> list = new ArrayList<AppApi024_01ZHResult>();

		AppApi020Form form = (AppApi020Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
				
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
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			form.setIspaging("1");
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DW_JCDJXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_DW_JCDJXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314022");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>0</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314022</><TranDate>5</><TranIP>2</><TranSeq>9</><filename>F:/aaa.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DW_JCDJXXCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_DW_JCDJXXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02401ZHResult);
			log.debug("MAP封装成BEAN："+app02401ZHResult);
			if(!"0".equals(app02401ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02401ZHResult.getRecode());
				modelMap.put("msg", app02401ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02401ZHResult.getRecode()+"  ,  描述msg : "+app02401ZHResult.getMsg());
				return modelMap;
			}
			
			
		}
		
		List<List<TitleInfoNameFormatBean>> result = new ArrayList();
		List<TitleInfoNameFormatBean> appapi02401Result = null;
		for(int i=0;i<list.size();i++){
			appapi02401Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02401"+form.getCenterId()+".result", list.get(i));
			result.add(appapi02401Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		modelMap.put("totalpage", app02401ZHResult.getTotalpage());
		modelMap.put("totalnum", app02401ZHResult.getTotalnum());
		modelMap.put("filename", app02401ZHResult.getFilename());
		return modelMap;
	}
	
}
