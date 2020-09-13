package com.yondervision.yfmap.handle.handleImpl.handle00075800;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00075800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(new Date()));
		form.setSendTime(formatter2.format(new Date()));
		/* 模拟返回开始  */
		AppApi00601Result app00601Result= new AppApi00601Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml");
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "A00016");
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYE.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			
			
			if(!"0".equals(app00601Result.getIsloanflag())){
				modelMap.clear();
				modelMap.put("recode", app00601Result.getIsloanflag());
				modelMap.put("msg", app00601Result.getAccinstname());
				log.error("中心返回报文 状态recode :"+app00601Result.getIsloanflag()+"  ,  描述msg : "+app00601Result.getAccinstname());
				return modelMap;
			}else{
				if(!"0000".equals(app00601Result.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app00601Result.getRecode());
					modelMap.put("msg", app00601Result.getMsg());
					log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
					return modelMap;
				}
			}	
			
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
//		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
//		modelMap.put("detail", appapi00101Detail);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

}
