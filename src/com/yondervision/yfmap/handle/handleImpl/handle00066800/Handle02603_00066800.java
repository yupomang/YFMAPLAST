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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi02603ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02603_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00066800请求02603参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi02603ZHResult app02603ZHResult = new AppApi02603ZHResult();
		             	

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
			
			form.setIspaging("1");
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DW_DWXGRJSBGXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DW_DWXGRJSBGXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314032");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>0</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314032</><TranDate>5</><TranIP>2</><TranSeq>9</><filename>F:/aaa.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DW_DWXGRJSBGXXCX.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DW_DWXGRJSBGXXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02603ZHResult);
			log.debug("MAP封装成BEAN："+app02603ZHResult);
			if(!"0".equals(app02603ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02603ZHResult.getRecode());
				modelMap.put("msg", app02603ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02603ZHResult.getRecode()+"  ,  描述msg : "+app02603ZHResult.getMsg());
				return modelMap;
			}
		}
		
//		List<TitleInfoNameFormatBean> appapi02603ZHResult = null;
//		
//		appapi02603ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02603"+form.getCenterId()+".result", app02603ZHResult);
//		Iterator<TitleInfoNameFormatBean> it1 = appapi02603ZHResult.iterator();
//		while (it1.hasNext()) {
//			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
//			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
//		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
//		modelMap.put("result", appapi02603ZHResult);
		modelMap.put("totalpage", app02603ZHResult.getTotalpage());
		modelMap.put("totalnum", app02603ZHResult.getTotalnum());
		modelMap.put("filename", app02603ZHResult.getFilename());
		return modelMap;
	}
	
}
