package com.yondervision.yfmap.handle.handleImpl.handle00047102;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi40101Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40101Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40103Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.DESForJava_NMQZ;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle500_00047102
* @Description: 微信绑定
* 
*/ 
public class Handle500_00047102 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40109 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("登录请求参:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		QvZhiAppApi40101Result app40101Result = new QvZhiAppApi40101Result();
		AES aes = new AES();
		System.out.println("getPassword"+form.getPassword());
		form.setNewpassword(aes.decrypt(form.getPassword()));
		System.out.println("getNewpassword"+form.getPassword());
		byte[] b = DESForJava_NMQZ.encryption(form.getNewpassword(), "12345678");
		byte[] c = DESForJava_NMQZ.bcd_to_asc(b);
		form.setNewpassword(new String(c));
		System.out.println(">>>>>加密后密码："+new String(c));
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DL.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129123");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><accnum>123456</><accname>甲六</><certinum></><validflag>1</><handset>13500991199</><msgsendflag>0</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DL.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
			
			app40101Result.setAccnum(aes.encrypt(app40101Result.getAccnum().getBytes("UTF-8")));//公积金号
			log.info(app40101Result.getAccnum());
			app40101Result.setAccname(aes.encrypt(app40101Result.getAccname().getBytes("UTF-8")));//姓名
			log.info(app40101Result.getAccname());
			app40101Result.setCertinum(aes.encrypt(app40101Result.getCertinum().getBytes("UTF-8")));//身份证号
			log.info(app40101Result.getCertinum());
			app40101Result.setLoanaccnum(aes.encrypt(app40101Result.getLoanaccnum().getBytes("UTF-8")));//贷款账号
			log.info(app40101Result.getLoanaccnum());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", app40101Result);
		//modelMap.put("wkfdata","{}");
		log.info(Constants.LOG_HEAD+"appApi40109 end.");
		return modelMap;
	}

}
