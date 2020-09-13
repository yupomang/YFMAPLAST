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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi02209ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02209_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi02209ZHResult app02209ZHResult = new AppApi02209ZHResult();
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
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWYW_DWJBXXCXFX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWYW_DWJBXXCXFX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168001");
			
			//String rexml ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag></><BrcCode>05740008</><BusiSeq>97899</><ChannelSeq>12367222</><ChkCode>7710</><MTimeStamp>2017-08-08 21:03:46:556000</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-08 21:03:46:556000</><TellCode>7710</><TranChannel>00</><TranCode>168001</><TranDate>2017-08-26</><TranIP>172.16.8.100</><TranSeq>97899</><agentdept></><economytype></><leglaccname>陈志明</><linkmancertinum>341226199208125799</><linkmancertitype>1</><linkmanemail></><mngdept>01</><supsubrelation>1</><tradetype>S</><unitaccname>宁波市住房公积金管理中中</><unitaddr>宁波市解放南路1666号</><unitareacode>04</><unitcustid>AU00012202</><unitkind>6</><unitlinkman>豪小打</><unitlinkphone>87978803</><unitlinkphone2>87978803</><unitsoicode></><zip>315000</>";
			log.debug("前置YFMAP接收中心报文——单位基本信息查询（用于单位资料变更反显）："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWYW_DWJBXXCXFX.txt", rexml, req);
		    //HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWYW_DWJBXXCXFX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02209ZHResult);
			log.debug("MAP封装成BEAN："+app02209ZHResult);
			if(!"0".equals(app02209ZHResult.getRecode())){
				modelMap.put("recode", app02209ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02209ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02209ZHResult.getRecode()+"  ,  描述msg : "+app02209ZHResult.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("agentdept", app02209ZHResult.getAgentdept());
		modelMap.put("economytype", app02209ZHResult.getEconomytype());
		modelMap.put("leglaccname", app02209ZHResult.getLeglaccname());
		modelMap.put("linkmancertinum", app02209ZHResult.getLinkmancertinum());
		modelMap.put("linkmancertitype", app02209ZHResult.getLinkmancertitype());
		modelMap.put("linkmanemail", app02209ZHResult.getLinkmanemail());
		modelMap.put("mngdept", app02209ZHResult.getMngdept());
		modelMap.put("supsubrelation", app02209ZHResult.getSupsubrelation());
		modelMap.put("tradetype", app02209ZHResult.getTradetype());
		modelMap.put("unitaccname", app02209ZHResult.getUnitaccname());
		modelMap.put("unitaddr", app02209ZHResult.getUnitaddr());
		modelMap.put("unitareacode", app02209ZHResult.getUnitareacode());
		modelMap.put("unitcustid", app02209ZHResult.getUnitcustid());
		modelMap.put("unitkind", app02209ZHResult.getUnitkind());
		modelMap.put("unitlinkman", app02209ZHResult.getUnitlinkman());
		modelMap.put("unitlinkphone", app02209ZHResult.getUnitlinkphone());
		modelMap.put("unitlinkphone2", app02209ZHResult.getUnitlinkphone2());
		modelMap.put("unitsoicode", app02209ZHResult.getUnitsoicode());
		modelMap.put("zip", app02209ZHResult.getZip());
		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setUnitaccnum("123456");
		form1.setChannel("40");
		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle02209_00057400 hand = new Handle02209_00057400();
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
