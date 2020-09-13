package com.yondervision.yfmap.handle.handleImpl.handle00085200;

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
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40101Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40103Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle40109_00085200 
* @Description: 遵义登录处理
* @author Caozhongyan
* @date Apr 14, 2016 2:58:15 PM   
* 
*/ 
public class Handle40109_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40109 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("遵义登录请求参:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		ZunYiAppApi40101Result app40101Result = new ZunYiAppApi40101Result();
		ZunYiAppApi40103Result appapi40103 = new ZunYiAppApi40103Result();
		AES aes = new AES();
		form.setNewpassword(aes.decrypt(form.getNewpassword()));
		
		byte[] b = DESForJava.encryption(form.getNewpassword(), "12345678");
		byte[] c = DESForJava.bcd_to_asc(b);
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
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "216001");
			
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
			
			appapi40103.setAccnum(aes.encrypt(app40101Result.getAccnum().getBytes("UTF-8")));//公积金号
			appapi40103.setAccname(aes.encrypt(app40101Result.getAccname().getBytes("UTF-8")));//姓名
			appapi40103.setPhone(app40101Result.getHandset());//手机号
			appapi40103.setValidflag(app40101Result.getValidflag());//是否短信验证：0-不验证，1-验证
			appapi40103.setMsgsendflag(app40101Result.getMsgsendflag());//是否手机短信验证
			if("0".equals(app40101Result.getValidflag())){//首次登录
				modelMap.put("recode", "991111");
				modelMap.put("msg", "修改初始密码");
				modelMap.put("result", appapi40103);
				modelMap.put("wkfdata","");
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40103);
		modelMap.put("accnum", aes.encrypt(app40101Result.getAccnum().getBytes("UTF-8")));
		modelMap.put("accname", aes.encrypt(app40101Result.getAccname().getBytes("UTF-8")));
		modelMap.put("handset", app40101Result.getHandset());
		modelMap.put("validflag", app40101Result.getValidflag());
		modelMap.put("msgsendflag", app40101Result.getMsgsendflag());
		//modelMap.put("wkfdata","{}");
		log.info(Constants.LOG_HEAD+"appApi40109 end.");
		return modelMap;
	}

}
