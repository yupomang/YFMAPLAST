package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi04501ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle04502_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00057400请求04501参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi04501ZHResult app04501ZHResult = new AppApi04501ZHResult();		             	
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))){
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
				form.setUKseq("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("0");
			}

			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_BJDJCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_BJDJCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111420");			
			//String rexml ="<AuthCode1>1111</><AuthCode2>1111</><AuthCode3>1111</><AuthFlag></><BrcCode>05740008</><BusiSeq>107155</><ChannelSeq>-283</><ChkCode>1111</><MTimeStamp>2017-09-21 11:40:02:356543</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-05 17:15:14:849000</><TellCode>1111</><TranChannel>00</><TranCode>111420</><TranDate>2017-09-02</><TranIP>172.16.8.100</><TranSeq>107155</>";
			
			log.debug("前置YFMAP接收中心报文——补缴登记撤销："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_BJDJCX.txt", rexml, req);
		    //HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_BJDJCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app04501ZHResult);
			log.debug("MAP封装成BEAN："+app04501ZHResult);
			if(!"0".equals(app04501ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app04501ZHResult.getMsg())){
					if("/".equals(app04501ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app04501ZHResult.getMsg());	    
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app04501ZHResult.getMsg());
						//File file = new File("D:/err001.txt");
						FileInputStream ffis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
						BufferedReader breader = new BufferedReader(isr);
						String line1 = breader.readLine(); 
						JSONArray result = new JSONArray();
						while (line1 != null) {// 判断读取到的字符串是否不为空
							JSONObject obj = new JSONObject();
							log.debug("读取文件  ："+line1);
							String[] trs = line1.split("\\|");
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < trs.length; i++) {
								if (i == 0) {
									obj.put("errCode", trs[i].trim());
									continue;
								} 
								sb.append(trs[i].trim()+",");
							}
							String msg = sb.toString();
							msg = msg.substring(0, msg.length()-1);
							obj.put("msg", "错误信息："+msg);
							result.add(obj);
							line1 = breader.readLine();
						}
						breader.close();
						isr.close();
						ffis.close();
						file.delete();
						modelMap.clear();
						modelMap.put("recode", app04501ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app04501ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app04501ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app04501ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app04501ZHResult.getRecode()+"  ,  描述msg : "+app04501ZHResult.getMsg());
					}					
					return modelMap;
				}else{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据");
					return modelMap;
				}
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setIspaging("1");
		form1.setUnitaccnum("123");
		form1.setPayamt("12222222");
		form1.setPaymode("13");
		form1.setSpaytype("13");
		form1.setUnitacctype("1");
		form1.setBrcCode("22222");
		form1.setQdapprnum("123111111");
		form1.setQdfilepath("122222222222223");
		form1.setQdfilename("123");
		form1.setRegnum("1w23");
		
		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
		Handle04501_00057400 hand = new Handle04501_00057400();
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
