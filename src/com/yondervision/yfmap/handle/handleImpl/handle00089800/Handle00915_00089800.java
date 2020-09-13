package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00915Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00611Result;
import com.yondervision.yfmap.result.haikou.AppApi00915Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author ljd
 * 贷款还款能力试算
 */
public class Handle00915_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00915Form form = (AppApi00915Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00915 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		
		AppApi00915Result app00915Result= new AppApi00915Result();
		AES aes = new AES();
		form.setGaccname(aes.decrypt(form.getGaccname()));
		//form.setXingming(aes.decrypt(form.getXingming()));
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Type"+form.getCenterId()).trim());			
			String xml = "";
			String rexml = "";
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			HashMap map = BeanUtil.transBean2Map(form);
			if(map.containsKey("matecertinum") && map.get("matecertinum")!=null){
				map.put("flag", "2");
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
				log.debug("试算反显1 发往中心报文："+xml);
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
				HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
				log.debug("试算反显1 解析报文MAP："+resultMap2);
				if(!"0".equals(resultMap2.get("recode"))){
					modelMap.clear();
					modelMap.put("recode", resultMap2.get("recode"));
					modelMap.put("msg", resultMap2.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap2.get("recode")+"  ,  描述msg : "+resultMap2.get("msg"));
					return modelMap;
				}
				//map.put("amt2", (String)resultMap2.get("gryjce"));
				map.put("amt2", Double.parseDouble(resultMap2.get("gryjce").toString())*2+"");
			}
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSS.txt", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "122213");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSS.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00915Result);
			
			if(!"0".equals(app00915Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00915Result.getRecode());
				modelMap.put("msg", app00915Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00915Result.getRecode()+"  ,  描述msg : "+app00915Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> appapi00915Result = null;
		appapi00915Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00915"+form.getCenterId()+".result", app00915Result);
		Iterator<TitleInfoBean> it1 = appapi00915Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00915Result);
		log.info(Constants.LOG_HEAD+"appApi00915 end.");
		return modelMap;
	}
	
}
