package com.yondervision.yfmap.handle.handleImpl.handle00031300;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.handle.handleImpl.handle00031400.Handle500_00031400;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.kunming.AppApi50010ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi50025ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 获取手机短信验证码
 * @author FengJing
 *
 */
public class Handle50004_00031300 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("00031300请求50004参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi50025ZHResult app50025ZHResult = new AppApi50025ZHResult();
	
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
//			form.setFullName(form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));   //姓名aes加密
		}else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "姓名为空");
			return modelMap;
		}
		if(!CommonUtil.isEmpty(form.getSurplusAccount())){
			log.debug("form.getSurplusAccount():"+form.getSurplusAccount());
			form.setSurplusAccount(form.getSurplusAccount());
			//form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		}else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "公积金账号为空");
			return modelMap;
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			log.debug("form.getIdcardNumber():"+form.getIdcardNumber());
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));  //证件号码aes加密
			//form.setIdcardNumber(aes.decrypt(form.getBodyCardNumber()));
			//form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "证件号码为空");
			return modelMap;
		}
		if(!CommonUtil.isEmpty(form.getMobileNumber())){
			log.debug("form.getMobileNumber():"+form.getMobileNumber());
			form.setMobileNumber(form.getMobileNumber());
			//form.setMobileNumber(aes.decrypt(form.getMobileNumber()));
			
		}else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "手机号码为空");
			return modelMap;
		}
		form.setCardno("00011001");
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("28"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00031300");//实例号
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YZ.XML", map, req);
//          String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/tools/tomcat6.0.14/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300//REQ_YZ.XML", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00009");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110097</><TranDate>1</><TranIP>1</><TranSeq>1</>";
			
			log.debug("前置YFMAP接收中心报文——获取手机验证码："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YZ.XML", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/tools/tomcat6.0.14/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300//REP_YZ.XML", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app50025ZHResult);
			log.debug("MAP封装成BEAN："+app50025ZHResult);
			if(!"0".equals(app50025ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50025ZHResult.getRecode());
				modelMap.put("msg", app50025ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50025ZHResult.getRecode()+"  ,  描述msg : "+app50025ZHResult.getMsg());
				return modelMap;
			}
		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}
	
	public static void main(String[] args) throws CenterRuntimeException, Exception{
		HttpServletRequest request=null;
		HttpServletResponse response=null;
		
		AppApi40102Form form = new AppApi40102Form();
		
		Handle50004_00031300 hand = new Handle50004_00031300();
		ModelMap modelMap = new ModelMap();
		form.setCenterId("00031300");
		form.setFullName("&#x6D4B;&#x8BD5;name");
		form.setSurplusAccount("&#x6D4B;&#x8BD5;name");
		
		//form.setSurplusAccount("123");
		//form.setNewpassword("123");
		//form.setPassword("123");
		form.setChannel("28");
		form.setUserId("abc");
		form.setIdcardNumber("&#x6D4B;&#x8BD5;name");
		form.setMobileNumber("11111111111");
		form.setAppid("111");
		hand.action(form, modelMap,request,response);
	}
}
