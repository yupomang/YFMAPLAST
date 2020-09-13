package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.haikou.AppApi40109Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi40101Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.DESForJava_NMQZ;
import com.yondervision.yfmap.util.DESForJava_haikou;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle40114_00089800
* @Description: 核心密码修改
* 
*/ 
public class Handle40114_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40114 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi40109Result app40101Result = new AppApi40109Result();
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
		form.setPubaccnum(form.getIdcardNumber());
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("newpassword", form.getNewpassword());
			map.put("type", "2");
			map.put("password", form.getPassword());
			//map.put("mobileNumber", "18143001572");
			map.put("mobileNumber", form.getMobileNumber());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_XGXX.txt", map, req);
			log.debug("发往中心报文："+xml);
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149370");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_XGXX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}			
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi40114 end.");
		return modelMap;
	}

}
