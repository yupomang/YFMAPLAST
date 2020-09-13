package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi3004Result;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3004_00089800 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	private HashMap resultMap;
	private String centerId ="";
	String PROPERTIES_FILE_NAME = "properties.properties";
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		centerId = centerid;
		log.debug("Handle3004_00089800:开始****处理模板信息单笔推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3004Result appapi3004result	= new AppApi3004Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004MsgType"+centerid).trim();
			try {
				log.debug("Handle3004_00089800 接收报文: "+xml);
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"BSP_REQ_MB_MESSAGE_SEND.txt", xml, seq);
			} catch (IOException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置模板报文模板异常");
				throw cre;
			}
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, appapi3004result);
			log.debug("MAP封装成BEAN："+appapi3004result);
			String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
			appapi3004result.setCenterName(centerName);
			String param = JsonUtil.getGson().toJson(appapi3004result);
			log.debug("Handle3004_00089800:acction封装移动平台JSON数据："+param);
			if("30".equals(appapi3004result.getChanel()))
			{
				Thread _t = new Thread(new Runnable() {
					public void run() {
						sendUrl1020();
					}
				});
				_t.start();
			}else
			{
				Thread _t1 = new Thread(new Runnable() {
					public void run() {
						sendUrl();
					}
				});
				_t1.start();
			}
			resultMap.put("recode", "000000");
			resultMap.put("msg", "接收成功");
			resultMap.put("precode", "000000");
			resultMap.put("pmsg", "接收成功");
//			resultMap.put("miSeqno", "123456"); 
//			resultMap.put("recvTime",appapi3004result.getSendSeqno());
			resultMap.put("sendSeqno", appapi3004result.getSendSeqno());
			resultMap.put("sendDate",appapi3004result.getSendDate());
			resultMap.put("sendTime",appapi3004result.getSendTime());
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"BSP_REP_MB_MESSAGE_SEND.txt", resultMap, seq);				
				log.debug("Handle3004_00089800:acction返回报文："+remsg);
				System.out.println("Handle3004_00089800:acction返回报文："+remsg);
			} catch (IOException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
		}		
		log.debug("Handle3004_00089800:结束****处理模板信息单笔推送逻辑");
		return remsg;
	}
	

	private void sendUrl() {
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3004.json";
		log.debug("Handle3004_00089800:YBMAP url ："+url);
		System.out.println("Handle3004_00089800:YBMAP url ："+url);
		resultMap.put("centerId", centerId);
		String rm1 = post.post(url, resultMap);
		System.out.println("Handle3004_00089800:YBMAP 返回信息l ："+rm1);
		Gson gson1 = new Gson();
		Map ybmapmsg1 = gson1.fromJson(rm1,new TypeToken<Map<String, Object>>() {}.getType());  
		resultMap.put("recode", ybmapmsg1.get("recode"));
		resultMap.put("msg", ybmapmsg1.get("remsg"));
		resultMap.put("precode", ybmapmsg1.get("recode"));
		resultMap.put("pmsg", ybmapmsg1.get("remsg"));
	}
	
	private void sendUrl1020() {
		resultMap.put("chanel","10");
		YbmapMessageUtil post1 = new YbmapMessageUtil();
		String url1 = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3004.json";
		log.debug("Handle3004_00089800:YBMAP url ："+url1);
		System.out.println("Handle3004_00089800:YBMAP url ："+url1);
		resultMap.put("centerId", centerId);
		String rm1 = post1.post(url1, resultMap);
		System.out.println("Handle3004_00089800:YBMAP 返回信息l ："+rm1);
		Gson gson1 = new Gson();
		Map ybmapmsg1 = gson1.fromJson(rm1,new TypeToken<Map<String, Object>>() {}.getType());  
		resultMap.put("recode", ybmapmsg1.get("recode"));
		resultMap.put("msg", ybmapmsg1.get("remsg"));
		resultMap.put("precode", ybmapmsg1.get("recode"));
		resultMap.put("pmsg", ybmapmsg1.get("remsg"));
		resultMap.put("chanel","20");
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3004.json";
		log.debug("Handle3004_00089800:YBMAP url ："+url);
		System.out.println("Handle3004_00089800:YBMAP url ："+url);
		resultMap.put("centerId", centerId);
		String rm = post.post(url, resultMap);
		System.out.println("Handle3004_00089800:YBMAP 返回信息l ："+rm);
		Gson gson = new Gson();
		Map ybmapmsg = gson1.fromJson(rm,new TypeToken<Map<String, Object>>() {}.getType());  
		resultMap.put("recode", ybmapmsg.get("recode"));
		resultMap.put("msg", ybmapmsg.get("remsg"));
		resultMap.put("precode", ybmapmsg.get("recode"));
		resultMap.put("pmsg", ybmapmsg.get("remsg"));
	}
	
	public static void main(String[] args){
		String ss = "111812@#$202070704060@#$刘森@#$物业费批扣失败提醒@#$";
		String[] yyy = ss.split("\\@\\#\\$");
		System.out.println(yyy.length);
	}
}
