package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.FtpUtil2;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi02502ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02502_00057400 implements CtrlHandleInter {
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
		log.debug("00057400请求02502参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi02502ZHResult app02502ZHResult = new AppApi02502ZHResult();
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
			if(!CommonUtil.isEmpty(form.getClientIp())
					&& !"1".equals(form.getAESFlag())){
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
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			form.setIspaging("1");
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWZHCXUDXX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110012");			
			log.debug("前置YFMAP接收中心报文——根据单位账号查询U盾信息："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWZHCXUDXX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWZHCXUDXX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app02502ZHResult);
			log.debug("MAP封装成BEAN："+app02502ZHResult);
			if(!"0".equals(app02502ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02502ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02502ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02502ZHResult.getRecode()+"  ,  描述msg : "+app02502ZHResult.getMsg());
				return modelMap;
			}
		}
		
		FtpUtil2 f=new FtpUtil2("bsp");
		f.getFileByServerForNB2(app02502ZHResult.getRsufilename());	   
		File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app02502ZHResult.getRsufilename());
		FileInputStream ffis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(ffis, "GBK");
		BufferedReader breader = new BufferedReader(isr);
		String line2 = breader.readLine(); 
		String unitaccname=null;
		String orgcode=null;
		String unitcertitype=null;
		String unitcertinum=null;
		String hhflag=null;
		HashMap obj = new HashMap();                        
		int i=-4;
		while (line2 != null) {// 判断读取到的字符串是否不为空
			log.debug("读取文件  ："+line2);
			if(isContain(line2, "unitaccname")){
				unitaccname= line2.replace("unitaccname:", "");
			}
			if(isContain(line2, "orgcode")){
				orgcode=line2.replace("orgcode:", "");
			}
			if(isContain(line2, "unitcertitype")){
				unitcertitype=line2.replace("unitcertitype:", "");
			}
			if(isContain(line2, "unitcertinum")){
				unitcertinum=line2.replace("unitcertinum:", "");
			}

			if(isContain(line2, "|")){
				String[] trs = line2.split("\\|");
				System.out.println(trs.length);
				if(i==0){
					if(trs.length>0){
					      obj.put("ukeynum", trs[0].trim());
					}else{
					      obj.put("ukeynum","");
					}
					if(trs.length>1){
					      obj.put("ukeyflag", trs[1].trim()); 
					}else{
					      obj.put("ukeyflag","");
					}
					if(trs.length>2){
					      obj.put("ukeystate", trs[2].trim());
					}else{
					      obj.put("ukeystate","");
					}
					if(trs.length>3){
					      obj.put("header", trs[3].trim());
					}else{
					      obj.put("header","");
					}
					if(trs.length>4){
					      obj.put("headerphone", trs[4].trim());
					}else{
					      obj.put("headerphone","");
					}
				}else{
					if(trs.length>0){
					      obj.put("ukeynum"+i, trs[0].trim());
					}else{
					      obj.put("ukeynum"+i,"");
					}
					if(trs.length>1){
					      obj.put("ukeyflag"+i, trs[1].trim()); 
					}else{
					      obj.put("ukeyflag"+i,"");
					}
					if(trs.length>2){
					      obj.put("ukeystate"+i, trs[2].trim());
					}else{
					      obj.put("ukeystate"+i,"");
					}
					if(trs.length>3){
					      obj.put("header"+i, trs[3].trim());
					}else{
					      obj.put("header"+i,"");
					}
					if(trs.length>4){
					      obj.put("headerphone"+i, trs[4].trim());
					}else{
					      obj.put("headerphone"+i,"");
					}
				}
			
			}
		
			i++;
			if(i==7){
				hhflag="1";
				break;
			}
			line2 = breader.readLine();
			log.debug("做了一遍");
		}
		breader.close();
		isr.close();
		ffis.close();
		file.delete();
		modelMap.clear();
		if(hhflag=="1"){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "错误信息：该单位U盾多于6个");
		}else{
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("unitaccname", unitaccname);
			modelMap.put("orgcode", orgcode);
			modelMap.put("unitcertitype", unitcertitype);
			modelMap.put("unitcertinum", unitcertinum);
			modelMap.put("ukeynum1", obj.get("ukeynum1"));
			modelMap.put("ukeystate1", obj.get("ukeystate1"));
			modelMap.put("header1", obj.get("header1"));
			modelMap.put("headerphone1", obj.get("headerphone1"));
			modelMap.put("ukeyflag1", obj.get("ukeyflag1"));
			modelMap.put("ukeynum2", obj.get("ukeynum2"));
			modelMap.put("ukeystate2", obj.get("ukeystate2"));
			modelMap.put("header2", obj.get("header2"));
			modelMap.put("headerphone2", obj.get("headerphone2"));
			modelMap.put("ukeyflag2", obj.get("ukeyflag2"));
			modelMap.put("ukeynum3", obj.get("ukeynum3"));
			modelMap.put("ukeystate3", obj.get("ukeystate3"));
			modelMap.put("header3", obj.get("header3"));
			modelMap.put("headerphone3", obj.get("headerphone3"));
			modelMap.put("ukeyflag3", obj.get("ukeyflag3"));
			modelMap.put("ukeynum4", obj.get("ukeynum4"));
			modelMap.put("ukeystate4", obj.get("ukeystate4"));
			modelMap.put("header4", obj.get("header4"));
			modelMap.put("headerphone4", obj.get("headerphone4"));
			modelMap.put("ukeyflag4", obj.get("ukeyflag4"));
			modelMap.put("ukeynum5", obj.get("ukeynum5"));
			modelMap.put("ukeystate5", obj.get("ukeystate5"));
			modelMap.put("header5", obj.get("header5"));
			modelMap.put("headerphone5", obj.get("headerphone5"));
			modelMap.put("ukeyflag5", obj.get("ukeyflag5"));
			modelMap.put("ukeynum", obj.get("ukeynum"));
			modelMap.put("ukeystate", obj.get("ukeystate"));
			modelMap.put("header", obj.get("header"));
			modelMap.put("headerphone", obj.get("headerphone"));
			modelMap.put("ukeyflag", obj.get("ukeyflag"));
		}
	/*	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("unitaccname", app02502ZHResult.getUnitaccname());
		modelMap.put("ukeynum1", app02502ZHResult.getUkeynum1());
		modelMap.put("ukeystate1", app02502ZHResult.getUkeystate1());
		modelMap.put("header1", app02502ZHResult.getHeader1());
		modelMap.put("headerphone1", app02502ZHResult.getHeaderphone1());
		modelMap.put("ukeyflag1", app02502ZHResult.getUkeyflag1());
		modelMap.put("ukeynum2", app02502ZHResult.getUkeynum2());
		modelMap.put("ukeystate2", app02502ZHResult.getUkeystate2());
		modelMap.put("header2", app02502ZHResult.getHeader2());
		modelMap.put("headerphone2", app02502ZHResult.getHeaderphone2());
		modelMap.put("ukeyflag2", app02502ZHResult.getUkeyflag2());
		modelMap.put("ukeynum3", app02502ZHResult.getUkeynum3());
		modelMap.put("ukeystate3", app02502ZHResult.getUkeystate3());
		modelMap.put("header3", app02502ZHResult.getHeader3());
		modelMap.put("headerphone3", app02502ZHResult.getHeaderphone3());
		modelMap.put("ukeyflag3", app02502ZHResult.getUkeyflag3());
		modelMap.put("ukeynum4", app02502ZHResult.getUkeynum4());
		modelMap.put("ukeystate4", app02502ZHResult.getUkeystate4());
		modelMap.put("header4", app02502ZHResult.getHeader4());
		modelMap.put("headerphone4", app02502ZHResult.getHeaderphone4());
		modelMap.put("ukeyflag4", app02502ZHResult.getUkeyflag4());
		modelMap.put("ukeynum5", app02502ZHResult.getUkeynum5());
		modelMap.put("ukeystate5", app02502ZHResult.getUkeystate5());
		modelMap.put("header5", app02502ZHResult.getHeader5());
		modelMap.put("headerphone5", app02502ZHResult.getHeaderphone5());
		modelMap.put("ukeyflag5", app02502ZHResult.getUkeyflag5());
		modelMap.put("ukeynum", app02502ZHResult.getUkeynum());
		modelMap.put("ukeystate", app02502ZHResult.getUkeystate());
		modelMap.put("header", app02502ZHResult.getHeader());
		modelMap.put("headerphone", app02502ZHResult.getHeaderphone());
		modelMap.put("ukeyflag", app02502ZHResult.getUkeyflag());
		modelMap.put("ukeyflag", app02502ZHResult.getUkeyflag());
		modelMap.put("unitcertinum", app02502ZHResult.getUnitcertinum());*/
		return modelMap;
	}
	
/*	public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("aaaaa");
		form1.setIndiacctype("1");
		Handle02502_00057400 hand = new Handle02502_00057400();
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
	
    public static boolean isContain(String s1,String s2) {
    	return s1.contains(s2);
    	}
}
