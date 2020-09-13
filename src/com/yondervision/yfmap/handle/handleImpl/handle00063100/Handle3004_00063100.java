package com.yondervision.yfmap.handle.handleImpl.handle00063100;

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

public class Handle3004_00063100 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("Handle3004_00063100:开始****处理模板信息单笔推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3004Result appapi3004result	= new AppApi3004Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				log.debug("Handle3004_00063100 接收报文: "+xml);
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HX_MB_MESSAGE_SEND.xml", xml, seq);
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
			log.debug("Handle3004_00063100:acction封装移动平台JSON数据："+param);

			YbmapMessageUtil post = new YbmapMessageUtil();
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3004.json";
			log.debug("Handle3004_00063100:YBMAP url ："+url);
			System.out.println("Handle3004_00063100:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
			String rm = post.post(url, resultMap);
			System.out.println("Handle3004_00063100:YBMAP 返回信息l ："+rm);
			Gson gson = new Gson();
			Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, Object>>() {}.getType());  
			resultMap.put("recode", ybmapmsg.get("recode"));
			resultMap.put("msg", ybmapmsg.get("remsg"));
			resultMap.put("miSeqno", ybmapmsg.get("miSeqno"));
			resultMap.put("key", appapi3004result.getKey());
			resultMap.put("recvTime",appapi3004result.getSendTime());
			
//			resultMap.put("recode", "000000");
//			resultMap.put("msg", "接收成功");
//			resultMap.put("miSeqno", "123456"); 
//			resultMap.put("recvTime",appapi3004result.getSendSeqno());
			
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REP_HX_MESSAGE_RETURN.xml", resultMap, seq);				
				log.debug("Handle3004_00063100:acction返回报文："+remsg);
				System.out.println("Handle3004_00063100:acction返回报文："+remsg);
			} catch (IOException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
			
		}		
		log.debug("Handle3004_00063100:结束****处理模板信息单笔推送逻辑");
		return remsg;
	}
	
	public static void main(String[] args){
		String ss = "2016-06-03-15:33:44.506;04965857;赵先平;;;01;;;;;;;;9001;;;;;;;;1";
		String[] yyy = ss.split(";");
		System.out.println(yyy.length);
	}
}
