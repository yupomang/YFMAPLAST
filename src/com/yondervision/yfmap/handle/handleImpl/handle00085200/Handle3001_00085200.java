package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi3001Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.socket.YFMAPServerSendMessage;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3001_00085200 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("Handle3001_00085200:开始****处理短消息单笔推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3001Result appapi3001result	= new AppApi3001Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HXMESSAGE_SEND.xml", xml, seq);
			} catch (IOException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置报文模板异常");
				throw cre;
			}
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appapi3001result);
			log.debug("MAP封装成BEAN："+appapi3001result);
			appapi3001result.setCenterId(centerid);
			String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
			appapi3001result.setCentreName(centerName);
			String param = JsonUtil.getGson().toJson(appapi3001result);
			log.debug("Handle3001_00085200:acction封装移动平台JSON数据："+param);
//			byte[] bResXML = com.yondervision.yfmap.socket.Util.getSendMessage(param, false);;
//			sendXML(out, bResXML);
			YbmapMessageUtil post = new YbmapMessageUtil();
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3001.json";
			log.debug("Handle3001_00085200:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
			String rm = post.post(url, resultMap);
			Gson gson = new Gson();
			Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
			resultMap.put("recode", ybmapmsg.get("recode"));
			resultMap.put("msg", ybmapmsg.get("remsg"));
			resultMap.put("miSeqno", ybmapmsg.get("miSeqno"));
			
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REP_HX_MESSAGE_RETURN.xml", resultMap, seq);				
			} catch (IOException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
			
		}		
		log.debug("Handle3001_00085200:结束****处理短消息单笔推送逻辑");
		return remsg;
	}
	
}
