package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi02204ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02204_00057400 implements CtrlHandleInter{
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form) form1;
		log.debug("00077500请求02204参数：" + CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send" + form.getCenterId()).trim();
		AppApi02204ZHResult app02204ZHResult = new AppApi02204ZHResult();

		if (Constants.method_BSP.equals(send)) {//xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType" + form.getCenterId()).trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key" + form.getCenterId()).trim());
			String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type" + form.getCenterId()).trim());
			AES aes = new AES(form.getCenterId(), form.getChannel(), form.getAppid(), null);
			if (!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))) {
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
			if ((!form.getChannel().trim().equals("40")) && (!form.getChannel().trim().equals("92")) && (!form.getChannel().trim().equals("96")) && (!form.getChannel().trim().equals("52")) && (!form.getChannel().trim().equals("91"))) {
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));

			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel" + form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REQ_GRXXGGCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRXXGGCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文：" + xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId()).trim();
			String rexml;
			if (form.getIspaging().equals("0")){
				 rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111400");
			}else{
				 rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111450");
			}

			System.out.println("form.getIspaging();"+form.getIspaging());


//			String rexml ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag></><BrcCode>05740008</><BusiSeq>89300</><ChannelSeq>12357788</><ChkCode>7710</><MTimeStamp>2017-08-05 17:15:14:851000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-05 17:15:14:849000</><TellCode>7710</><TranChannel>00</><TranCode>111400</><TranDate>2017-08-10</><TranIP>172.16.8.100</><TranSeq>89300</><consum>59</><rsufilename>GRZHCX_011500001167_20170810</><rsufilepath>/bsptest/</>";
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询：" + rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REP_GRXXGGCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRXXGGCX.txt", rexml, req);
			log.debug("解析报文MAP：" + resultMap);
			BeanUtil.transMap2Bean(resultMap, app02204ZHResult);
			log.debug("MAP封装成BEAN：" + app02204ZHResult);
			if (!"0".equals(app02204ZHResult.getRecode())) {
				modelMap.clear();
				modelMap.put("recode", app02204ZHResult.getRecode());
				modelMap.put("msg", "错误信息：" + app02204ZHResult.getMsg());
				log.error("中心返回报文 状态recode :" + app02204ZHResult.getRecode() + "  ,  描述msg : " + app02204ZHResult.getMsg());
				return modelMap;
			}

		}
		if ("40".equals(form.getChannel())) {
			String stdata0 = app02204ZHResult.getStdata();
			String stdata1 = app02204ZHResult.getStdata1();
			String stdata2 = app02204ZHResult.getStdata2();
			String stdata3 = app02204ZHResult.getStdata3();
			String stdata4 = app02204ZHResult.getStdata4();
			String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("consum", app02204ZHResult.getConsum());
			if (form.getIspaging().equals("0")){
				modelMap.put("rsufilename", app02204ZHResult.getRsufilename());
				modelMap.put("rsufilepath", app02204ZHResult.getRsufilepath());
			}else {
				modelMap.put("stdata", stdata);
			}
			return modelMap;
		} else{

			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("consum", app02204ZHResult.getConsum());
			modelMap.put("rsufilename", app02204ZHResult.getRsufilename());
			modelMap.put("rsufilepath", app02204ZHResult.getRsufilepath());
			return modelMap;
		}
	}
/*	public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		Handle02204_00057400 hand = new Handle02204_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
