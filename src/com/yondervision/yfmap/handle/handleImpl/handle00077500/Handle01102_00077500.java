package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi01101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle01102_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {

//		AppApi00501Form form = (AppApi00501Form)form1;
//		String PROPERTIES_FILE_NAME = "properties.properties";
//		log.debug("form:"+form);
//		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
//		AppApi01101ZHResult app01101ZHResult = new AppApi01101ZHResult();	
		
//		if(Constants.method_BSP.equals(send)){////xml通信处理
//			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
//			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
//			form.setSendDate(CommonUtil.getDate());
//			form.setSendTime(CommonUtil.getTime());
//			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
//			form.setSendSeqno(req);
//			
//			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
//			if(!form.getChannel().trim().equals("40"))
//			{
//				form.setChannelSeq(form.getSendSeqno());
//				form.setTellCode("8888");
//				form.setBrcCode("88888888");
//				form.setTranDate(form.getSendDate());
//			}
//			form.setFlag(Channel.getChannel(form.getChannel()));
//			HashMap map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_DKJDCX.txt", map, req);
//			log.debug("发往中心报文："+xml);
//			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
//			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
//			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314018");
//			log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GR_DKJDCX.txt", rexml, req);
//			log.debug("解析报文MAP："+resultMap);	
//			BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
//			log.debug("MAP封装成BEAN："+app01101ZHResult);
//			if(!"0".equals(app01101ZHResult.getRecode())){
//				modelMap.clear();
//				modelMap.put("recode", app01101ZHResult.getRecode());
//				modelMap.put("msg", app01101ZHResult.getMsg());			
//				log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
//				return modelMap;
//			}
//		}
		

		modelMap.clear();
		modelMap.put("recode", "999999");
		modelMap.put("msg", "该功能暂不可用！");
		return modelMap;
		
	}

}
