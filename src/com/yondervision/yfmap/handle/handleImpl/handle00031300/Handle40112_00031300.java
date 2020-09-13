package com.yondervision.yfmap.handle.handleImpl.handle00031300;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi40112Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 请求短信验证码
 */
public class Handle40112_00031300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		
		AES aes = new AES();
		//form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		ZhangJiaKouAppApi40112Result app40112Result= new ZhangJiaKouAppApi40112Result();
		String req_xml = "REQ_YZ.xml";
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			HashMap<String,String> map = BeanUtil.transBean2Map(form);

			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+req_xml, map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_SQYZM.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00009");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>75</msgtype><tr_code>X00005</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><xm>夏洛</xm><zjlx>身份证</zjlx><zjhm>220001197701011234</zjhm><gfdz>梧桐花园88号</gfdz><dkje>500000.00</dkje><nx>30</nx><hkfs>等额本金</hkfs><dkye>400000.00</dkye><zkyh>建行</zkyh><yhhkzh>2012748300917887</yhhkzh><htdqrq>2025-12-31</htdqrq><cqzbz>未入库</cqzbz><czrq>2015-01-01</czrq></body></root>";
			
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>84</msgtype><tr_code>X00014</tr_code><corp_no>00053100</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2016-05-17</tr_acdt>" +
//					"<tr_time>10:00:00</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>成功</ans_info>" +
//					"<particular_code></particular_code><particular_info></particular_info><reserved></reserved></head>" +
//					"<body>" +
//					"</body></root>";

			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YZ.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_SQYZM.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app40112Result);
			
			
			if(!Constants.sucess_ts.equals(app40112Result.getRecode())){
				modelMap.put("recode", app40112Result.getRecode());
				modelMap.put("msg", app40112Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40112Result.getRecode()+"  ,  描述msg : "+app40112Result.getMsg());
				return modelMap;
			}
		}		
		
		//app41201Result.setLoanamt(String.format("%,.2f",Double.valueOf(app41201Result.getLoanamt())));
		//app41201Result.setLoanbal(String.format("%,.2f",Double.valueOf(app41201Result.getLoanbal())));
		
		//List<TitleInfoBean> appapi01003Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01003"+form.getCenterId()+".result", app01003Result);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("currseq", app40112Result.getTransCode());
		log.info(Constants.LOG_HEAD+"appApi40112 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi40102Form form1 = new AppApi40102Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		Handle40112_00031300 hand = new Handle40112_00031300();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
