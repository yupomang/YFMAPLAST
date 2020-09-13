package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00915Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00916Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author ljd
 * 贷款还款能力试算 配偶信息反显
 */
public class Handle00921_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00915Form form = (AppApi00915Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00921 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009MsgType"+form.getCenterId()).trim();			
		form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Key"+form.getCenterId()).trim());
		
		AppApi00916Result app00916Result= new AppApi00916Result();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			
			map.put("flag", "2");
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap2);
			if(!"0".equals(resultMap2.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap2.get("recode")+"  ,  描述msg : "+resultMap2.get("msg"));
				return modelMap;
			}
			
			
			map.put("flag", "5");
			map.put("loaneecertinum", map.get("matecertinum"));
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显2 发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显2 解析报文MAP："+resultMap6);
			if(!"0".equals(resultMap6.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap6.get("recode"));
				modelMap.put("msg", resultMap6.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap6.get("recode")+"  ,  描述msg : "+resultMap6.get("msg"));
				return modelMap;
			}
			
			app00916Result.setPoaccnum((String)resultMap2.get("accnum"));
			app00916Result.setPogaccname((String)resultMap2.get("gaccname"));
			app00916Result.setPogryjce((String)resultMap2.get("gryjce")); 
			app00916Result.setPozjlx((String)resultMap6.get("zjlx"));
		}
		
		List<TitleInfoBean> appapi00916Result = null;
		appapi00916Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00916"+form.getCenterId()+".result", app00916Result);
		Iterator<TitleInfoBean> it1 = appapi00916Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00916Result);
		log.info(Constants.LOG_HEAD+"appApi00921 end.");
		return modelMap;
	}
	
}
