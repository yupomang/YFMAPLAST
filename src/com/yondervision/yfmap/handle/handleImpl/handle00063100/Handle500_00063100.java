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

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle500_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi40102Form form = (AppApi40102Form)form1;		
		
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心注册验证");
		AES aes = new AES();
		form.setBodyCardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setNewpassword("".equals(form.getNewpassword())?"":aes.decrypt(form.getNewpassword()));//公积金密码
		//mobileNumber手机号
		//form.getCheckid()验证码
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
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600002");
			log.debug("前置YFMAP接收中心报文："+xml);
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
			}
				
			/**
			 * 短信验证
			 */
			resultMap.put("mobileNumber", form.getMobileNumber());
			resultMap.put("checkid", form.getCheckid());
			//以下为威海新改动，通过公积金号，取得短信验证码？
			String xmlYZ = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DL_DXYZ.txt", resultMap, req);
			log.debug("50001前置YFMAP接收中心报文取验证码步聚2发送报文："+xmlYZ);
			String rexmlYZ = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xmlYZ, "pb0013");
			WeiHaiAppApi40101Result app40102Result1 = new WeiHaiAppApi40101Result();
			log.debug("50001前置YFMAP接收中心报文取验证码步聚2接收报文："+rexmlYZ);
			HashMap resultMapYZ = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DL_DXYZ.txt", rexmlYZ, req);
			log.debug("50001解析报文MAP："+resultMapYZ);
			BeanUtil.transMap2Bean(resultMapYZ, app40102Result1);
			if(!"0".equals(app40102Result1.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app40102Result1.getRecode());
				modelMap.put("msg", app40102Result1.getMsg());
				log.error("50001中心返回报文 状态recode :"+app40102Result1.getRecode()+"  ,  描述msg : "+app40102Result1.getMsg());
				return modelMap;
			}
			
			/**
			 * 执行绑定交易，通知核心，记录用户信息
			 */
			map.put("QyBind", "1");
			WeiHaiAppApi40101Result app40102Result2 = new WeiHaiAppApi40101Result();
			String xmlBD = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DL_BD.txt", map, req);
			log.debug("绑定交易401前置YFMAP发往中心报文："+xmlBD);			
			String rexmlBD = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xmlBD, "111055");
			log.debug("绑定交易401前置YFMAP接收中心报文："+rexmlBD);
			HashMap resultMapBD = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DL_BD.txt", rexmlBD, req);
			log.debug("401解析报文MAP："+resultMapBD);
			BeanUtil.transMap2Bean(resultMapBD, app40102Result2);
			if(!"0".equals(app40102Result2.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app40102Result2.getRecode());
				modelMap.put("msg", app40102Result2.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result2.getRecode()+"  ,  描述msg : "+app40102Result2.getMsg());
				return modelMap;
			}
			
			
			
		}		
		
		if("".equals(form.getAppid())){
			modelMap.put("appid", "");
		}else{
			modelMap.put("appid", form.getAppid());
		}
		System.out.println("#############################威海公积金新绑定功能成功！！！！");
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");					
		return modelMap;
	}

}
