package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil2;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00125ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi002_01ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;



public class Handle00125_00057400 implements CtrlHandleInter {
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
		log.debug("买房人姓名："+form.getBuyhousename());
		//List<AppApi00125ZHResult> list = new ArrayList<AppApi00125ZHResult>();
		log.debug("00057400请求00125参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi00125ZHResult app00125ZHResult = new AppApi00125ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM4-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			//AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);

			
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("53")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			
			log.debug("中文："+form.getBuyhousename());
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91"))&&(!form.getChannel().trim().equals("93")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			form.setIspaging("1");
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_SDJBXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,  "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "154116");
						
			log.debug("前置YFMAP接收中心报文——商贷基本信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_SDJBXXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app00125ZHResult);
			log.debug("MAP封装成BEAN："+app00125ZHResult);
			if(!"0".equals(app00125ZHResult.getRecode())){
				modelMap.clear();
				JSONArray result = new JSONArray();
				JSONObject obj = new JSONObject();
				obj.put("errCode", app00125ZHResult.getRecode());
				obj.put("msg",  "错误信息："+app00125ZHResult.getMsg());
				result.add(obj);
				modelMap.put("recode", app00125ZHResult.getRecode());
				modelMap.put("msg", result); 
				log.error("中心返回报文 状态recode :"+app00125ZHResult.getRecode()+"  ,  描述msg : "+app00125ZHResult.getMsg());
				return modelMap;
			}
		}
		System.out.println("form.getChannel().trim()==============="+form.getChannel().trim());
		if(form.getChannel().trim().equals("40"))
		{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("stdata", app00125ZHResult.getStdata());
			//modelMap.put("rsufilename", app00125ZHResult.getRsufilename());
			//modelMap.put("rsufilepath", app00125ZHResult.getRsufilepath());
		}else if(form.getChannel().trim().equals("91")||form.getChannel().trim().equals("53")||form.getChannel().trim().equals("96")||form.getChannel().trim().equals("92")||form.getChannel().trim().equals("52")||form.getChannel().trim().equals("93")){				
			/*FtpUtil2 f=new FtpUtil2("bsp");
			f.getFileByServerForNB2(app00125ZHResult.getRsufilename());
			File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app00125ZHResult.getRsufilename());
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line2 = breader.readLine(); */
			String stdata = app00125ZHResult.getStdata();
			String[] str = stdata.split("&&");
			for (int i = 0; i < str.length; i++) {
				System.out.println(i + " --->" + str[i]);
			JSONArray result = new JSONArray();
			int count = 0;
			if (str[i] != null) {// 判断读取到的字符串是否不为空
				JSONObject obj = new JSONObject();
				log.debug("读取文件  ："+str[i]);
				String[] trs = str[i].split("\\|");
				System.out.println(trs.length);
				if(trs.length>0){
				      obj.put("loancontrnum", trs[0].trim());
				}else{
				      obj.put("loancontrnum","");
				}
				
				if(trs.length>1){
				      obj.put("buyhousename", trs[1].trim());
				}else{
				      obj.put("buyhousename","");
				}
				if(trs.length>2){
				      obj.put("certitype", trs[2].trim());
				}else{
				      obj.put("certitype","");
				}
				if(trs.length>3){
				      obj.put("buyhousecerid", trs[3].trim());
				}else{
				      obj.put("buyhousecerid","");
				}
				if(trs.length>4){
				      obj.put("bankcode", trs[4].trim());
				}else{
				      obj.put("bankcode","");
				}
				if(trs.length>5){
				      obj.put("houseaddr", trs[5].trim());
				}else{
				      obj.put("houseaddr","");
				}
				if(trs.length>6){
				      obj.put("buyhousedate", trs[6].trim());
				}else{
				      obj.put("buyhousedate","");
				}
				if(trs.length>7){
				      obj.put("contrsigndate", trs[7].trim());
				}else{
				      obj.put("contrsigndate","");
				}
				if(trs.length>8){
				      obj.put("buyhouseamt", trs[8].trim());
				}else{
				      obj.put("buyhouseamt","");
				}
				if(trs.length>9){
				      obj.put("loanterm", trs[9].trim());
				}else{
				      obj.put("loanterm","");
				}
				if(trs.length>10){
				      obj.put("loansum", trs[10].trim());
				}else{
				      obj.put("loansum","");
				}
				if(trs.length>11){
				      obj.put("cleardate", trs[11].trim());
				}else{
				      obj.put("cleardate","");
				}
				if(trs.length>12){
				      obj.put("state", trs[12].trim());
				}else{
				      obj.put("state","");
				}
				result.add(obj);
				count++;
				//line2 = breader.readLine();
				log.debug("做了"+i+"遍");
			}

			/*while (line2 != null) {// 判断读取到的字符串是否不为空
				JSONObject obj = new JSONObject();
				log.debug("读取文件  ："+line2);
				String[] trs = line2.split("\\|");
				System.out.println(trs.length);
				if(trs.length>0){
					obj.put("loancontrnum", trs[0].trim());
				}else{
					obj.put("loancontrnum","");
				}

				if(trs.length>1){
					obj.put("buyhousename", trs[1].trim());
				}else{
					obj.put("buyhousename","");
				}
				if(trs.length>2){
					obj.put("certitype", trs[2].trim());
				}else{
					obj.put("certitype","");
				}
				if(trs.length>3){
					obj.put("buyhousecerid", trs[3].trim());
				}else{
					obj.put("buyhousecerid","");
				}
				if(trs.length>4){
					obj.put("bankcode", trs[4].trim());
				}else{
					obj.put("bankcode","");
				}
				if(trs.length>5){
					obj.put("houseaddr", trs[5].trim());
				}else{
					obj.put("houseaddr","");
				}
				if(trs.length>6){
					obj.put("buyhousedate", trs[6].trim());
				}else{
					obj.put("buyhousedate","");
				}
				if(trs.length>7){
					obj.put("contrsigndate", trs[7].trim());
				}else{
					obj.put("contrsigndate","");
				}
				if(trs.length>8){
					obj.put("buyhouseamt", trs[8].trim());
				}else{
					obj.put("buyhouseamt","");
				}
				if(trs.length>9){
					obj.put("loanterm", trs[9].trim());
				}else{
					obj.put("loanterm","");
				}
				if(trs.length>10){
					obj.put("loansum", trs[10].trim());
				}else{
					obj.put("loansum","");
				}
				if(trs.length>11){
					obj.put("cleardate", trs[11].trim());
				}else{
					obj.put("cleardate","");
				}
				if(trs.length>12){
					obj.put("state", trs[12].trim());
				}else{
					obj.put("state","");
				}
				result.add(obj);
				count++;
				line2 = breader.readLine();
				log.debug("做了一遍");
			}*/
			//breader.close();
			//isr.close();
			//ffis.close();
			//file.delete();
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result);
				modelMap.put("count", count);
			}

		}

		return modelMap;
	}

}
