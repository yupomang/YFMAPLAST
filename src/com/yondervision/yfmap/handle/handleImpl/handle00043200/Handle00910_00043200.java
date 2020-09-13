package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00910Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zhongshan.AppApi00910Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00910_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	String PROPERTIES_FILE_NAME = "properties.properties";

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00910Form form = (AppApi00910Form)form1;
		log.debug("form:"+form);
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		String freeuse2 = form.getType();
		if(freeuse2!=null&&("1").equals(freeuse2)){
			modelMap = JCSS( form,  modelMap);
		}else if(freeuse2!=null&&("2").equals(freeuse2)){
			modelMap = DKSS( form,  modelMap);
		}else if(freeuse2!=null&&("3").equals(freeuse2)){
			modelMap = HKSS( form,  modelMap);
		}else if(freeuse2!=null&&("4").equals(freeuse2)){
			modelMap = TQHKSS( form,  modelMap);
		}else if(freeuse2!=null&&("5").equals(freeuse2)){
			modelMap = GSDDB( form,  modelMap);
		
		}
		
		
		return modelMap;
	}
	private ModelMap JCSS(AppApi00910Form form, ModelMap modelMap)throws IOException {
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00910Result appApi00910Result	= new AppApi00910Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_JCSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "C040006");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_JCSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00910Result);
			log.debug("MAP封装成BEAN："+appApi00910Result);
			
			
			if(!"00".equals(appApi00910Result.getRecode())){
				modelMap.put("recode", appApi00910Result.getRecode());
				modelMap.put("msg", appApi00910Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00910Result.getRecode()+"  ,  描述msg : "+appApi00910Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091001"+form.getCenterId()+".result", appApi00910Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		return modelMap;
	}
	private ModelMap DKSS(AppApi00910Form form, ModelMap modelMap)throws IOException {
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00910Result appApi00910Result	= new AppApi00910Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129818");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00910Result);
			log.debug("MAP封装成BEAN："+appApi00910Result);
			
			
			if(!"00".equals(appApi00910Result.getRecode())){
				modelMap.put("recode", appApi00910Result.getRecode());
				modelMap.put("msg", appApi00910Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00910Result.getRecode()+"  ,  描述msg : "+appApi00910Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091002"+form.getCenterId()+".result", appApi00910Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		return modelMap;
	}
	private ModelMap HKSS(AppApi00910Form form, ModelMap modelMap)throws IOException {
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00910Result appApi00910Result	= new AppApi00910Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_HKJHSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129818");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_HKJHSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00910Result);
			log.debug("MAP封装成BEAN："+appApi00910Result);
			
			
			if(!"00".equals(appApi00910Result.getRecode())){
				modelMap.put("recode", appApi00910Result.getRecode());
				modelMap.put("msg", appApi00910Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00910Result.getRecode()+"  ,  描述msg : "+appApi00910Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091003"+form.getCenterId()+".result", appApi00910Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		return modelMap;
	}
	private ModelMap TQHKSS(AppApi00910Form form, ModelMap modelMap)throws IOException {
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00910Result appApi00910Result	= new AppApi00910Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_TQHKSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129818");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_TQHKSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00910Result);
			log.debug("MAP封装成BEAN："+appApi00910Result);
			
			
			if(!"00".equals(appApi00910Result.getRecode())){
				modelMap.put("recode", appApi00910Result.getRecode());
				modelMap.put("msg", appApi00910Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00910Result.getRecode()+"  ,  描述msg : "+appApi00910Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091004"+form.getCenterId()+".result", appApi00910Result);		
		List<TitleInfoBean> detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091004"+form.getCenterId()+".detail", appApi00910Result);		
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		modelMap.put("detail", detail);	
		return modelMap;
	}
	private ModelMap GSDDB(AppApi00910Form form, ModelMap modelMap)throws IOException {
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00910Result appApi00910Result	= new AppApi00910Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GSDBSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129818");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_GSDBSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00910Result);
			log.debug("MAP封装成BEAN："+appApi00910Result);
			
			
			if(!"00".equals(appApi00910Result.getRecode())){
				modelMap.put("recode", appApi00910Result.getRecode());
				modelMap.put("msg", appApi00910Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00910Result.getRecode()+"  ,  描述msg : "+appApi00910Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0091005"+form.getCenterId()+".result", appApi00910Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		return modelMap;
	}
}
