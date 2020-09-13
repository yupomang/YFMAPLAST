package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.bocom.netpay.infosec.NetSignServer;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50003_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi40102Form form = (AppApi40102Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		
		log.debug("form:"+form);
		NetSignServer tmp = new NetSignServer();
		form.setPassword(tmp.NSHashAndBase64Encode(form.getPassword()));
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			log.debug(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_WXDL.xml", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);			
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "C020001");
				//String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root>	<head>		<tr_code>C020010</tr_code>		<corp_no>1001</corp_no>		<req_no>1</req_no>		<serial_no>1</serial_no>		<ans_no></ans_no>		<next_no></next_no>		<tr_acdt></tr_acdt>		<tr_time></tr_time>		<ans_code>00</ans_code>		<ans_info></ans_info>		<particular_code></particular_code>		<atom_tr_count>1</atom_tr_count>		<reserved></reserved>		<particular_info></particular_info>	</head>	<body>		<AccNum>01110500475</AccNum>		<CertiNum>220222197506255513</CertiNum>		<Name>冯忠成</Name>		</body></root>";
				log.debug("前置YFMAP接收中心报文："+rexml);
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_WXDL.xml", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app40102Result);
				log.debug("MAP封装成BEAN："+app40102Result);
				if(!"00".equals(app40102Result.getRecode())){
					modelMap.put("recode", app40102Result.getRecode());
					modelMap.put("msg", app40102Result.getMsg());
					log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
					return modelMap;
				}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", app40102Result);
		}
		return modelMap;
	}
}
