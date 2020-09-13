package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.yondervision.yfmap.result.ningbo.AppApi03701ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03701_00057400 implements CtrlHandleInter {
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
		log.debug("00057400请求03701参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi03701ZHResult app03701ZHResult = new AppApi03701ZHResult();

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
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWZLBG.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/works/workspace/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWZLBG.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168000");
			
			//String rexml ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag></><BrcCode>05740008</><BusiSeq>89650</><ChannelSeq>12357777</><ChkCode>7710</><MTimeStamp>2017-08-05 17:15:14:851000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-05 17:15:14:849000</><TellCode>7710</><TranChannel>00</><TranCode>168000</><TranDate>2017-08-10</><TranIP>172.16.8.100</><TranSeq>89650</><apprnum>20170810089650</><rsufilename>cmprtf0002_10.2017081000089650</><rsufilepath>/bsptest/00000000/fil/</>";
			//String rexml ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag></><BrcCode>05740008</><BusiSeq>89650</><ChannelSeq>12357777</><ChkCode>7710</><MTimeStamp>2017-08-05 17:15:14:851000</><NoteMsg></><RspCode>000001</><RspMsg>交易处理成功...</><STimeStamp>2017-08-05 17:15:14:849000</><TellCode>7710</><TranChannel>00</><TranCode>168000</><TranDate>2017-08-10</><TranIP>172.16.8.100</><TranSeq>89650</><apprnum>20170810089650</><rsufilename>cmprtf0002_10.2017081000089650</><rsufilepath>/bsptest/00000000/fil/</>";

			log.debug("前置YFMAP接收中心报文——单位资料变更："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWZLBG.txt", rexml, req);
		    //HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/works/workspace/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWZLBG.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03701ZHResult);
			log.debug("MAP封装成BEAN："+app03701ZHResult);
			if(!"0".equals(app03701ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app03701ZHResult.getMsg())){
					if("/".equals(app03701ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app03701ZHResult.getMsg());	    
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app03701ZHResult.getMsg());
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
						modelMap.put("recode", app03701ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app03701ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app03701ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app03701ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app03701ZHResult.getRecode()+"  ,  描述msg : "+app03701ZHResult.getMsg());
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
		modelMap.put("apprnum", app03701ZHResult.getApprnum());
		modelMap.put("rsufilename", app03701ZHResult.getRsufilename());
		modelMap.put("rsufilepath", app03701ZHResult.getRsufilepath());
		return modelMap;
	}
	
	
	/*public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("1");
		form1.setUnitaccname("2");   
		form1.setUnitaddr("3");
		form1.setUnitareacode("4");
		form1.setZip("5");
		form1.setMngdept("6");
		form1.setUnitkind("7");
		form1.setEconomytype("8");
		form1.setSupsubrelation("9");
		form1.setTradetype("10");
		form1.setLeglaccname("11");
		form1.setUnitsoicode("12");
		form1.setAgentdept("13");
		form1.setUnitlinkman("14");
		form1.setUnitlinkphone("15");
		form1.setUnitlinkphone2("16");
		form1.setLinkmancertitype("17");
		form1.setLinkmancertinum("18");
		form1.setLinkmanemail("19");
		form1.setUnitcustid("20");
		form1.setQdapprnum("21");
	
		Handle03701_00057400 hand = new Handle03701_00057400();
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
