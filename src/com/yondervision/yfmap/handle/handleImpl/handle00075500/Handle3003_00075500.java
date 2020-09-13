package com.yondervision.yfmap.handle.handleImpl.handle00075500;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi300301Result;
import com.yondervision.yfmap.result.AppApi300302Result;
import com.yondervision.yfmap.result.AppApi3003Result;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3003_00075500 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		String PROPERTIES_FTP_NAME = "ftp.properties";
		log.debug("Handle3003_00085101:开始****处理短消息批量推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001Send"+centerid).trim();
		/* 模拟返回开始  */
		AppApi3003Result appapi3003result	= new AppApi3003Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3001MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HX_MESSAGE_SELECT.xml", xml, seq);
			} catch (IOException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置报文模板异常");
				throw cre;
			}
//			log.debug("解析报文MAP："+resultMap);			
//			BeanUtil.transMap2Bean(resultMap, appapi3003result);
//			log.debug("MAP封装成BEAN："+appapi3003result);
//			appapi3003result.setCenterId(centerid);
			String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
//			appapi3003result.setCentreName(centerName);
			resultMap.put("centerId", centerid);
			resultMap.put("centerName", centerName);
			
//			List<AppApi300201Result> list300201 = new ArrayList<AppApi300201Result>();
//			
//			
//			if(!appapi3003result.getFilename().equals("")){
//				/****
//				 *	此处应加入FTP下载 
//				 **/
//				FtpUtil f=new FtpUtil("bsp");
//			    f.getFileByServer("/"+appapi3002result.getFilename());				    
//			    File filerename = new File(PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3002result.getFilename());
//			    String newname = PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+appapi3002result.getSendDate()+"/"+appapi3002result.getFilename();
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
//					
//					while (line != null) {// 判断读取到的字符串是否不为空					
//						AppApi300201Result api300201Result = new AppApi300201Result();					
//						try {
//							String[] param = line.split("\\@\\|\\$");
//							
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
//					
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				//========================================================================== 
				
				
//			}
			
			
			
			
			
			YbmapMessageUtil post = new YbmapMessageUtil();
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3003.json";
			log.debug("Handle3003_00085101:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
//			resultMap.put("mslist",param);
			String rm = post.post(url, resultMap);
			log.debug("Handle3003_00085101:acction封装移动平台明细JSON数据："+rm);
			Gson gson = new Gson();
			AppApi300301Result api3003Result = gson.fromJson(rm,new TypeToken<AppApi300301Result>() {}.getType());  
			
			if("000000".equals(api3003Result.getRecode())){
				FtpUtil f=new FtpUtil("bsp");
				String name = System.currentTimeMillis()+(String)resultMap.get("sendSeqno")+".txt";
				String fileName = PropertiesReader.getProperty(PROPERTIES_FTP_NAME,"bsp_local_path")+name;
				File file = new File(fileName);
				
				FileOutputStream fileout;
				try {
					fileout = new FileOutputStream(file);
				
					if (!file.exists()) {					
						file.createNewFile();					
					}
					StringBuffer message = new StringBuffer();
					for(int i=0;i<api3003Result.getList().size();i++){
						AppApi300302Result api300302 = api3003Result.getList().get(i);						
						message.append(api300302.getAccnum()+"@#$"+api300302.getTitle()+"@#$"+api300302.getDetail()+"@#$"+api300302.getChanel()+"@#$"+api300302.getTheme()+"@#$"+api300302.getSendDate()+"@#$"+api300302.getStatus()+"\n");						
					}
					byte[] contentInBytes = message.toString().getBytes("UTF-8");
					fileout.write(contentInBytes);
					fileout.flush();
					fileout.close();
					f.putFileToServer(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					CenterRuntimeException ce = new CenterRuntimeException("999999","查询结果封装文件未找到");
					throw ce;
				} catch (IOException e) {
					e.printStackTrace();
					CenterRuntimeException ce = new CenterRuntimeException("999999","处理文件异常");
					throw ce;
				}				
				resultMap.put("fileName", name);
			}else{
				resultMap.put("fileName", "");
			}
			resultMap.put("recode", api3003Result.getRecode());
			resultMap.put("remsg", api3003Result.getRemsg());
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REP_HX_MESSAGE_SELECT_RETURN.xml", resultMap, seq);				
			} catch (IOException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
		}		
		log.debug("Handle3001_00085101:结束****处理短消息批量推送逻辑");
		return remsg;
	}

}
