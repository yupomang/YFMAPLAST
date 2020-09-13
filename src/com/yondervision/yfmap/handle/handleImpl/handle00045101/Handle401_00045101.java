package com.yondervision.yfmap.handle.handleImpl.handle00045101;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle401_00045101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi40102Form form = (AppApi40102Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
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
			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REQ_DL.txt", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DL.txt", map, req);
			
			log.debug("前置YFMAP发往中心CSP报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP_"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT_"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "160054");
			
//			String reqxml = "<AuthCode1>0000</><AuthCode2>0000</><AuthCode3>0000</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>2644244</><ChannelSeq>398243</><ChkCode>0000</><MTimeStamp>11:05:32</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>11:05:32</><TellCode>0000</><TranChannel>00</><TranCode>112108</><TranDate>2015-12-16</><TranIP>10.22.21.26</><TranSeq>2644244</>";
			
			log.debug("前置YFMAP接收中心CSP报文："+reqxml);
			
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REP_DL.txt", reqxml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DL.txt", reqxml, req);
			
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);
			if(!"0".equals(app40102Result.getRecode())){
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}
		}		
		
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		System.out.println(jsonObj);
		
		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}
	
	
//	public static void main(String[] args){
//		AppApi40102Form form1 = new AppApi40102Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("P04130000358");
//		form1.setCenterId("00045101");
//		form1.setPassword("273624");
//		form1.setCardno("231084198808273624");
//		Handle401_00045101 hand = new Handle401_00045101();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block			
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
