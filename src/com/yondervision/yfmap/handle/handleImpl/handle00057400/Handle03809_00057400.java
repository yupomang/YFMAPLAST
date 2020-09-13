package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi03809ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03809_00057400 implements CtrlHandleInter {
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
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi03809ZHResult app03809ZHResult = new AppApi03809ZHResult();
		if(Constants.method_BSP.equals(send)){

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
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
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("53")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_XHTQKTQJEJS.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_XHTQKTQJEJS.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "154002");
			
			//String rexml ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag></><BrcCode>05740008</><BusiSeq>99352</><ChannelSeq>12363378</><ChkCode>7710</><MTimeStamp>2017-09-01 20:52:44:908000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-22 20:52:44:905000</><TellCode>7710</><TranChannel>00</><TranCode>154002</><TranDate>2017-08-25</><TranIP>172.16.8.100</><TranSeq>99352</><calbal>2527.91</><calintamt>5.79</><calmonbal>0.00</><drawbal>2527.91</><drawmonbal>0.00</><drawsubbal>10766.56</><drawsupbal>0.00</><increbal>0.00</><increbal2>0.00</><increbal3>0.00</><increbal4>0.00</><inputamt>13324.93</><keepbal>2527.91</><keepbal2>10766.56</><keepbal3>0.00</><keepbal4>0.00</><monintamt>0.00</><remnamt>0.00</><subintamt>24.67</><supintamt>0.00</>";
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_XHTQKTQJEJS.txt", rexml, req);
		    //HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_XHTQKTQJEJS.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03809ZHResult);
			log.debug("MAP封装成BEAN："+app03809ZHResult);
			if(!"0".equals(app03809ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app03809ZHResult.getMsg())){
					if("/".equals(app03809ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app03809ZHResult.getMsg());	    
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app03809ZHResult.getMsg());
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
						modelMap.put("recode", app03809ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app03809ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app03809ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app03809ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app03809ZHResult.getRecode()+"  ,  描述msg : "+app03809ZHResult.getMsg());
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
		modelMap.put("calbal", app03809ZHResult.getCalbal());
		modelMap.put("calmonbal", app03809ZHResult.getCalmonbal());
		modelMap.put("calsupbal", app03809ZHResult.getCalsupbal());
		modelMap.put("drawbal", app03809ZHResult.getDrawbal());
		modelMap.put("drawmonbal", app03809ZHResult.getDrawmonbal());
		modelMap.put("drawsupbal", app03809ZHResult.getDrawsupbal());
		modelMap.put("inputamt", app03809ZHResult.getInputamt());
		modelMap.put("drawsubbal", app03809ZHResult.getDrawsubbal());
		modelMap.put("increbal", app03809ZHResult.getIncrebal());
		modelMap.put("increbal2", app03809ZHResult.getIncrebal2());
		modelMap.put("increbal3", app03809ZHResult.getIncrebal3());
		modelMap.put("increbal4", app03809ZHResult.getIncrebal4());
		modelMap.put("keepbal", app03809ZHResult.getKeepbal());
		modelMap.put("keepbal2", app03809ZHResult.getKeepbal2());
		modelMap.put("keepbal3", app03809ZHResult.getKeepbal3());
		modelMap.put("keepbal4", app03809ZHResult.getKeepbal4());
		modelMap.put("calintamt", app03809ZHResult.getCalintamt());
		modelMap.put("subintamt", app03809ZHResult.getSubintamt());
		modelMap.put("monintamt", app03809ZHResult.getMonintamt());
		modelMap.put("supintamt", app03809ZHResult.getSupintamt());
		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("11111");
	
		Handle03809_00057400 hand = new Handle03809_00057400();
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
