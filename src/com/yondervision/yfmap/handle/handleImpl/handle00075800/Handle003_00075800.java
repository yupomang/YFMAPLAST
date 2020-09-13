package com.yondervision.yfmap.handle.handleImpl.handle00075800;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00301Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00201Result;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00301Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle003_00075800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00301Form form = (AppApi00301Form)form1;
		log.debug("form:"+form);
		
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00301Result appApi00301Result	= new AppApi00301Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_JXDZCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "A00018");
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_JXDZCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00301Result);
			log.debug("MAP封装成BEAN："+appApi00301Result);
			
			
			if(!"0".equals(appApi00301Result.getIsloanflag())){
				modelMap.put("recode", appApi00301Result.getIsloanflag());
				modelMap.put("msg", appApi00301Result.getAccinstname());
				log.error("中心返回报文 状态recode :"+appApi00301Result.getIsloanflag()+"  ,  描述msg : "+appApi00301Result.getAccinstname());
				return modelMap;
			}else{
				if(!"0000".equals(appApi00301Result.getRecode())){
					modelMap.put("recode", appApi00301Result.getRecode());
					modelMap.put("msg", appApi00301Result.getMsg());
					log.error("中心返回报文 状态recode :"+appApi00301Result.getRecode()+"  ,  描述msg : "+appApi00301Result.getMsg());
					return modelMap;
				}
			}
		}
		
		
		
		appApi00301Result.setIntdate(form.getYear()+appApi00301Result.getIntdate().substring(4));
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00301"+form.getCenterId()+".result", appApi00301Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		modelMap.put("remark", appApi00301Result.getTips());
		
		return modelMap;
	}

}
