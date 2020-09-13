package com.yondervision.yfmap.handle.handleImpl.handle00085200;

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
import com.yondervision.yfmap.result.AppApi3002Result;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3002_00085200 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		String PROPERTIES_FTP_NAME = "ftp.properties";
		log.debug("Handle3001_00085200:开始****处理短消息批量推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3002Result appapi3002result	= new AppApi3002Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HX_PL_MESSAGE_SEND.xml", xml, seq);
			} catch (IOException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置报文模板异常");
				throw cre;
			}
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appapi3002result);
			log.debug("MAP封装成BEAN："+appapi3002result);
			appapi3002result.setCenterId(centerid);
			String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
			appapi3002result.setCentreName(centerName);
			
			List<AppApi300201Result> list300201 = new ArrayList<AppApi300201Result>();
			
			
			if(!appapi3002result.getFilename().equals("")){
				/****
				 *	此处应加入FTP下载 
				 **/
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+appapi3002result.getFilename());				    
			    File filerename = new File(PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3002result.getFilename());
			    String newname = PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3002result.getSendDate()+"/"+appapi3002result.getFilename();
			    try {
					FileUtils.copyFile(filerename, new File(newname));
					File file = new File(newname);
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					int sum = 0;
					if(line != null){
						sum=1;
					}
					
					while (line != null) {// 判断读取到的字符串是否不为空					
						AppApi300201Result api300201Result = new AppApi300201Result();					
						try {
							String[] param = line.split("\\@\\|\\$");
							
							api300201Result.setAccnum(param[0]);
							api300201Result.setTitle(param[1]);
							api300201Result.setDetail(param[2]);							
							list300201.add(api300201Result);
							line = breader.readLine();						
							log.debug("读取文件  ："+line);
						} catch (IOException e) {
							e.printStackTrace();						
						}
					}
					breader.close();
					isr.close();
					ffis.close();
					appapi3002result.setList(list300201);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//========================================================================== 
				
				
			}
			
			
			
			
			
			
			
			
			
			
			String param = JsonUtil.getGson().toJson(list300201);
			log.debug("Handle3002_00085200:acction封装移动平台明细JSON数据："+param);
//			byte[] bResXML = com.yondervision.yfmap.socket.Util.getSendMessage(param, false);;
//			sendXML(out, bResXML);
			YbmapMessageUtil post = new YbmapMessageUtil();
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3002.json";
			log.debug("Handle3002_00085200:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
			resultMap.put("mslist",param);
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
		log.debug("Handle3001_00085200:结束****处理短消息批量推送逻辑");
		return remsg;
	}

}
