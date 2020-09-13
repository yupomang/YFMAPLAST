package com.yondervision.yfmap.handle.handleImpl.handle00063100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi007Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle401_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi40102Form form = (AppApi40102Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form channel:"+form.getChannel());
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
		log.debug("form.getIdcardNumber() : "+form.getIdcardNumber());
		form.setBodyCardNumber(aes.decrypt(form.getIdcardNumber()));
		log.debug("form.getNewpassword() : "+form.getNewpassword());
		form.setNewpassword(aes.decrypt(form.getNewpassword()));
		form.setAppid("1");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);				
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DL.txt", map, req);
			log.debug("40101前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600002");
			log.debug("40101前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);
			if(!"0".equals(app40102Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}else{
				//执行绑定交易，通知核心，记录用户信息
				map.put("QyBind", "1");
				WeiHaiAppApi40101Result app40102Result1 = new WeiHaiAppApi40101Result();
				String xmlBD = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DL_BD.txt", map, req);
				log.debug("绑定交易401前置YFMAP发往中心报文："+xmlBD);			
				String rexmlBD = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xmlBD, "111055");
				log.debug("绑定交易401前置YFMAP接收中心报文："+rexmlBD);
				HashMap resultMapBD = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DL_BD.txt", rexmlBD, req);
				log.debug("401解析报文MAP："+resultMapBD);
				BeanUtil.transMap2Bean(resultMapBD, app40102Result1);
				if(!"0".equals(app40102Result1.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app40102Result1.getRecode());
					modelMap.put("msg", app40102Result1.getMsg());
					log.error("中心返回报文 状态recode :"+app40102Result1.getRecode()+"  ,  描述msg : "+app40102Result1.getMsg());
					return modelMap;
				}
				
			}
		}		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}

}
