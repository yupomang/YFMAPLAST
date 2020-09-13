package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.kunming.AppApi50010ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50004_00087100 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi50010ZHResult app50010ZHResult = new AppApi50010ZHResult();
		
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));
			log.debug("form.getFullName():"+form.getFullName());
		}
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
//		if(!CommonUtil.isEmpty(form.getTel())){
//			log.debug("form.getTel():"+form.getTel());
//			form.setTel(aes.decrypt(form.getTel()));
//		}
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Type"+form.getCenterId()).trim());			
			
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRDXZHSFRZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GRGJJZHSFRZ.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314064");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314001</><TranDate>1</><TranIP>1</><TranSeq>1</>";
		
			log.debug("前置YFMAP接收中心报文——个人公积金身份认证："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_GRDXZHSFRZ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GRGJJZHSFRZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app50010ZHResult);
			log.debug("MAP封装成BEAN："+app50010ZHResult);
			if(!"0".equals(app50010ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50010ZHResult.getRecode());
				modelMap.put("msg", app50010ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50010ZHResult.getRecode()+"  ,  描述msg : "+app50010ZHResult.getMsg());
				return modelMap;
			}else{
				if("0".equals(app50010ZHResult.getHaveSPwd())){
					//要是姓名和身份证都对，但是没有这个手机号 isOK 是 0
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "个人信息不正确无法发送短信验证码！");
					return modelMap;
				}
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("haveSPwd", app50010ZHResult.getHaveSPwd());
		return modelMap;

	}
	public static void main(String[] args) throws UnsupportedEncodingException{
//		GetMethod myget = new GetMethod(url);
//		HttpClient httpClient = new HttpClient();		
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
//		StringBuffer responsestr = new StringBuffer();
//		int re_code = httpClient.executeMethod(myget);
		String url="http://www.baidu.com?ds=22 gg";
		System.out.println( URLEncoder.encode(url,"UTF-8"));
	}
}
