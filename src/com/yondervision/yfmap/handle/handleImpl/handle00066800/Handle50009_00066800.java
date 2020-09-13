package com.yondervision.yfmap.handle.handleImpl.handle00066800;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi50009ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
* 密码修改
* @ClassName: Handle50006_00066800 
* @Description: TODO
* @author Caozhongyan
* @date 2016年8月25日 上午11:43:59   
* 
*/ 
public class Handle50009_00066800 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
	
		System.out.println("YFMAP发往中心——个人密码修改");
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
//		
		if(!CommonUtil.isEmpty(form.getCheckcode())){
			log.debug("form.getCheckcode():"+form.getCheckcode());
			form.setCheckcode(aes.decrypt(form.getCheckcode()));
		}
		if(!CommonUtil.isEmpty(form.getPassword())){
			log.debug("form.getPassword():"+form.getPassword());
			form.setPassword(aes.decrypt(form.getPassword()));
		}
		if(!CommonUtil.isEmpty(form.getNewpassword())){
			log.debug("form.getNewpassword():"+form.getNewpassword());
			form.setNewpassword(aes.decrypt(form.getNewpassword()));
		}
		if(!CommonUtil.isEmpty(form.getConfirmnewpassword())){
			log.debug("form.getConfirmnewpassword():"+form.getConfirmnewpassword());
			form.setConfirmnewpassword(aes.decrypt(form.getConfirmnewpassword()));
		}
		if(!CommonUtil.isEmpty(form.getUnitaccnum())){
			log.debug("form.getUnitaccnum():"+form.getUnitaccnum());
			form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
		}
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
		
		log.debug("00066800请求50009参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50009ZHResult app50009ZHResult = new AppApi50009ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
//			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			
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
			
			/**
			 * 贷款进度查询
			 * 查询机渠道调用交易码为111734的交易
			 * 其他渠道调用交易码为110023的交易
			 */
			if (form.getChannel().trim().equals("50")) {
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRMMXGCXJ.txt", map, req);
//				String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRMMXGCXJ.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111734");
				
//				String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>111734</><TranDate>1</><TranIP>1</><TranSeq>1</>";
				
				log.debug("前置YFMAP接收中心报文："+rexml);
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRMMXGCXJ.txt", rexml, req);
//				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRMMXGCXJ.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app50009ZHResult);
				log.debug("MAP封装成BEAN："+app50009ZHResult);
			}else {
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRMMXG.txt", map, req);
//				String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRMMXG.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110023");
				
//				String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110023</><TranDate>1</><TranIP>1</><TranSeq>1</>";
				
				log.debug("前置YFMAP接收中心报文："+rexml);
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRMMXG.txt", rexml, req);
//				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRMMXG.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app50009ZHResult);
				log.debug("MAP封装成BEAN："+app50009ZHResult);				
			}
			
			if(!"0".equals(app50009ZHResult.getRecode())){
				modelMap.put("recode", app50009ZHResult.getRecode());
				modelMap.put("msg", app50009ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50009ZHResult.getRecode()+"  ,  描述msg : "+app50009ZHResult.getMsg());
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return modelMap;
	}

}
