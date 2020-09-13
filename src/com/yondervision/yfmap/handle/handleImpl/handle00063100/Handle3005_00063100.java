package com.yondervision.yfmap.handle.handleImpl.handle00063100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi300201Result;
import com.yondervision.yfmap.result.AppApi3005Result;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3005_00063100 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		String PROPERTIES_FTP_NAME = "ftp.properties";
		log.debug("Handle3005_00063100:开始****处理短消息批量推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3005Result appapi3005result	= new AppApi3005Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				log.debug("Handle3005_00063100 接收报文: "+xml);
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HX_PL_MESSAGE_SEND.xml", xml, seq);
			} catch (IOException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置报文模板异常");
				throw cre;
			}
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appapi3005result);
			log.debug("MAP封装成BEAN："+appapi3005result);
			//appapi3005result.setCenterId(centerid);
			String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
			
//			List<AppApi300201Result> list300201 = new ArrayList<AppApi300201Result>();
			
			
//			if(!appapi3005result.getFileName().equals("")){
//				appapi3005result.setFileName(appapi3005result.getFileName().substring(appapi3005result.getFileName().lastIndexOf("/"))+1);
//				FtpUtil f=new FtpUtil("bsp");
//			    f.getFileByServer("/"+appapi3005result.getFileName());				    
//			    File filerename = new File(PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3005result.getFilename());
//			    String newname = PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3005result.getSendDate()+"/"+appapi3002result.getFilename();
//			    try {
//					FileUtils.copyFile(filerename, new File(newname));
//					File file = new File(newname);
//					FileInputStream ffis = new FileInputStream(file);
//					InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
//					BufferedReader breader = new BufferedReader(isr);
//					String line = breader.readLine();
//					int sum = 0;
//					if(line != null){
//						sum=1;
//					}
//					while (line != null) {// 判断读取到的字符串是否不为空					
//						AppApi300201Result api300201Result = new AppApi300201Result();					
//						try {
//							String[] param = line.split("\\@\\|\\$");
//							api300201Result.setAccnum(param[0]);
//							api300201Result.setTitle(param[1]);
//							api300201Result.setDetail(param[2]);							
//							list300201.add(api300201Result);
//							line = breader.readLine();						
//							log.debug("读取文件  ："+line);
//						} catch (IOException e) {
//							e.printStackTrace();						
//						}
//					}
//					breader.close();
//					isr.close();
//					ffis.close();
//					appapi3002result.setList(list300201);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			
			
			
			
			
			
			
			
			appapi3005result.setFileName(appapi3005result.getFileName().substring(appapi3005result.getFileName().lastIndexOf("/")+1));
			String param = JsonUtil.getGson().toJson(appapi3005result);
			log.debug("Handle3005_00063100:acction封装移动平台明细JSON数据："+param);
			YbmapMessageUtil post = new YbmapMessageUtil();
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3005.json";
			log.debug("Handle3005_00063100:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
			resultMap.put("mslist",param);
			resultMap.put("fileName", appapi3005result.getFileName());
			String rm = post.post(url, resultMap);
			Gson gson = new Gson();
			Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, Object>>() {}.getType());  
			resultMap.put("recode", ybmapmsg.get("recode"));
			resultMap.put("msg", ybmapmsg.get("remsg"));
			resultMap.put("miSeqno", ybmapmsg.get("miSeqno")==null?System.getSecurityManager():ybmapmsg.get("miSeqno"));
			resultMap.put("key", appapi3005result.getKey());
			resultMap.put("recvTime",appapi3005result.getSendTime());
			
//			resultMap.put("recode", "000000");
//			resultMap.put("msg", "接收成功"); 
//			resultMap.put("miSeqno", "123456");			
//			resultMap.put("recvTime",appapi3005result.getSendSeqno());
			
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REP_HX_MESSAGE_RETURN.xml", resultMap, seq);				
				log.debug("Handle3005_00063100:acction返回报文："+remsg);
			} catch (IOException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
		}		
		log.debug("Handle3005_00063100:结束****处理短消息批量推送逻辑");
		return remsg;
	}
	
//	public static void main(String[] args){
//		String aa="/bspdev/sms/2016-05-23-13:09:13.042".substring("/bspdev/sms/2016-05-23-13:09:13.042".lastIndexOf("/")+1);
//		System.out.println(aa);
//	}

}
