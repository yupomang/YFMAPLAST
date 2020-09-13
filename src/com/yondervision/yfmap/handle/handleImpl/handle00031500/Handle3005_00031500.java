package com.yondervision.yfmap.handle.handleImpl.handle00031500;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.FtpUtil_DownFiles;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.jinan.JiNanAppApi3005Result;
import com.yondervision.yfmap.socket.AbsBusinessServer;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle3005_00031500 extends AbsBusinessServer {
	Logger log = Logger.getLogger("YFMAP");	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	JiNanAppApi3005Result appapi3005result;
	File fileName;
	static String url ="";
	@Override
	public String acction(String xml, OutputStream out, String centerid, String seq)
			throws CenterRuntimeException {
		String PROPERTIES_FILE_NAME = "properties.properties";
		String PROPERTIES_FTP_NAME = "ftp.properties";
		log.debug("Handle3005_00031500:开始****处理短消息批量推送逻辑");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004Send"+centerid).trim();
		/* 模拟返回开始  */
		appapi3005result	= new JiNanAppApi3005Result();
		String remsg = "";
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004MsgType"+centerid).trim();
			HashMap resultMap;
			try {
				log.debug("Handle3005_00031500 接收报文: "+xml);
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REQ_HX_PL_MESSAGE_SEND.xml", xml, seq);
				//resultMap = MessageCtrMain.analysisPacket(msgType, "D:/Workspaces/workspaces_bg/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00031500/"+"REQ_HX_PL_MESSAGE_SEND.xml", xml, seq);
			} catch (Exception e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","读取前置报文模板异常");
				throw cre;
			}
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appapi3005result);
			log.debug("MAP封装成!BEAN："+appapi3005result);
			url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3006.json";
			log.debug("filename!!："+appapi3005result.getFileName());
			if(!appapi3005result.getFileName().equals("")){
//				FtpUtil_DownFiles fd = new FtpUtil_DownFiles("bsp");
//				FTPClient ftpClient = new FTPClient();
//				fd.connectServer(ftpClient,fd.getUrl(), fd.getUsername(), fd.getPassword(),fd.getPort());
//				String localPath = fd.getLocalPath();
//				log.debug("getFilePath："+appapi3005result.getFilePath());
//				log.debug("filename："+appapi3005result.getFileName());
//				boolean success = fd.ftpDownFiles(ftpClient,appapi3005result.getFileName(),appapi3005result.getFilePath()+File.separator,localPath); 
//				appapi3005result.setFilePath(localPath);
//				log.debug("getFile："+success);
			   
			    try {
					//========================================================================== 
					Thread _t = new Thread(new Runnable() {
						public void run() {
							try {
								sendfileToServer(appapi3005result);
							} catch (ClientProtocolException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					_t.start();
			    } catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			log.debug("Handle3005_00031500:YBMAP url ："+url);
			resultMap.put("centerId", centerid);
			resultMap.put("recode", "000000");
			resultMap.put("msg", "接收成功");
			resultMap.put("precode", "000000");
			resultMap.put("pmsg", "接收成功");
			resultMap.put("sendSeqno", appapi3005result.getSendSeqno());
			resultMap.put("sendDate",appapi3005result.getSendDate());
			resultMap.put("sendTime",appapi3005result.getSendTime());
			try {
				remsg = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+centerid)+"REP_HX_PL_MESSAGE_RETURN.xml", resultMap, seq);				
//				remsg = MessageCtrMain.encapsulatedPackets(msgType, "D:/Workspaces/workspaces_bg/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00031500/"+"REP_HX_PL_MESSAGE_RETURN.xml", resultMap, seq);				
				log.debug("Handle3005_00031500:acction返回报文："+remsg);
			} catch (Exception e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("999999","封装前置返回报文模板异常");
				throw cre;
			}
		}		
		log.debug("Handle3005_00031500:结束****处理短消息批量推送逻辑");
		return remsg;
	}
	
	public static void sendfileToServer(JiNanAppApi3005Result appapi3005result) throws ClientProtocolException, IOException {
		  //File localFile = new File("D:/home/abc/2016-10-11/10_2016-09-13-114609.382_111812");
		  //File localFile1 = new File("D:/home/abc/2016-10-11/20_2016-09-13-114609.382_111812");
		  File fileName = new File(appapi3005result.getFilePath()+File.separator+appapi3005result.getFileName());
		  //File fileName = new File("D:/home/abc/2016-10-13/"+File.separator+appapi3005result.getFileName());
		  PostMethod filePost = new PostMethod(url);
		  FilePart fp = new FilePart("file", fileName);
		  //FilePart fp1 = new FilePart("file1", localFile1);
		  // StringPart:普通的文本参数
		  StringPart smssource = new StringPart("smssource", appapi3005result.getSmssource());
		  StringPart num = new StringPart("num", appapi3005result.getNum());
		  StringPart filename = new StringPart("fileName", appapi3005result.getFileName(), "UTF-8");
		  StringPart filePath = new StringPart("filePath", appapi3005result.getFilePath());
		  StringPart sendType = new StringPart("sendType", appapi3005result.getSendType());
		  StringPart timer = new StringPart("timer", appapi3005result.getTimer());
		  StringPart chanel = new StringPart("chanel", appapi3005result.getChanel());
		  StringPart title = new StringPart("title", appapi3005result.getTitle(), "UTF-8");
		  StringPart detail = new StringPart("detail", appapi3005result.getDetail(), "UTF-8");
		  StringPart theme = new StringPart("theme", appapi3005result.getTheme());
		  StringPart sendDate1 = new StringPart("sendDate1", appapi3005result.getSendDate1());
		  StringPart sendTime1 = new StringPart("sendTime1", appapi3005result.getSendTime1());
		  StringPart centerId = new StringPart("centerId", appapi3005result.getCenterId());
		  StringPart freeuse1 = new StringPart("freeuse1", appapi3005result.getFreeuse1(), "UTF-8");
		  StringPart freeuse2 = new StringPart("freeuse2", appapi3005result.getFreeuse2(), "UTF-8");
		  StringPart freeuse3 = new StringPart("freeuse3", appapi3005result.getFreeuse3(), "UTF-8");
		  Part[] parts = { fp,smssource,num,filename,filePath,sendType,timer,chanel,title,detail,theme,sendDate1,sendTime1
				  ,centerId,freeuse1,freeuse2,freeuse3};
		  // 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
		  MultipartRequestEntity mre = new MultipartRequestEntity(parts, filePost.getParams());
		  filePost.setRequestEntity(mre);
		  HttpClient client = new HttpClient();
		  client.getHttpConnectionManager(). getParams().setConnectionTimeout(5000); 
		  int status = client.executeMethod(filePost);
		  System.out.println(status + "--------------");
	}
	
	public static void main(String[] args){
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><reqflag>0</reqflag><msgtype>96</msgtype><transCode>3005</transCode><corp_no>00031500</corp_no><user_no>8888</user_no><req_no>2017063000006645</req_no><oreq_no></oreq_no><tr_acdt>2017-06-23</tr_acdt><tr_time>15:22:44</tr_time><channel>28</channel><sign>28</sign><key></key><reserved></reserved></head><body>" +
				"<smssource>1001</smssource><num>2</num><filepath>d:/app</filepath>" +
				"<fileName>WX-20170630-00006708.txt</fileName><sendType>1</sendType>" +
				"<timer>true</timer><chanel>20</chanel><title>批量推送</title><detail>批量推送</detail>" +
				"<theme>批量推送</theme><sendDate1>2017-06-23</sendDate1><sendTime1>15:22:44</sendTime1>" +
				"<centerId>00031500</centerId><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></root>";
		Handle3005_00031500 hand = new Handle3005_00031500();
		
		try {
			OutputStream out = null;
			hand.acction(xml,out,"00031500","");
			//hand.sendfileToServer(new File("D:/home/abc/2016-10-11/10_2016-09-13-114609.382_111812"),new JiNanAppApi3005Result());
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
