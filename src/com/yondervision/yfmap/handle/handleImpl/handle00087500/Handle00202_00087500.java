package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.text.NumberFormat;
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
import com.yondervision.yfmap.form.AppApi00202Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baoshan.AppApi00202Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00202_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00202Form form = (AppApi00202Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		System.out.println("surplusAccount==="+form.getSurplusAccount());
		System.out.println("transseqno==="+form.getTransseqno());
		
		String zhmxTqServer = PropertiesReader.getProperty("properties.properties", "ZHMX_00087500_TQ_SERVER");
		String zhmxHdServer = PropertiesReader.getProperty("properties.properties", "ZHMX_00087500_HD_SERVER");
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		AppApi00202Result app00202Result = new AppApi00202Result();
		HashMap resultMap =null;
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());	
			
			String transcode = "";
			String rexml = "";
			System.out.println("摘要编码==="+form.getSummarycode());

			// 提取类
			if(zhmxTqServer.indexOf(form.getSummarycode())>=0){
				transcode = "430408";
			// 还贷类
			}else if (zhmxHdServer.indexOf(form.getSummarycode())>=0){
				transcode = "430410";
			}else{
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该笔业务没有详细信息！");
				return modelMap;
			}

			form.setTranscode(transcode);
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);	
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YEMXCX_CHILD.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();

			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, transcode);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			
			if(zhmxTqServer.indexOf(form.getSummarycode())>=0){
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YEMXCX_TQ_CHILD.xml", rexml, req);
			}else {
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YEMXCX_HD_CHILD.xml", rexml, req);
			}
			
			System.out.println("解析报文MAP输出："+resultMap);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00202Result);
			log.debug("MAP封装成BEAN："+app00202Result);
			if(!"0".equals(app00202Result.getRecode())){
				modelMap.put("recode", app00202Result.getRecode());
				modelMap.put("msg", app00202Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00202Result.getRecode()+"  ,  描述msg : "+app00202Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> appapi00202Result = new ArrayList<TitleInfoBean>();
		if(zhmxTqServer.indexOf(form.getSummarycode())>=0){
			app00202Result.setFreeuse2(moneyTran(app00202Result.getFreeuse2()));
			app00202Result.setFreeuse3(moneyTran(app00202Result.getFreeuse3()));
			appapi00202Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00202"+form.getCenterId()+".result", app00202Result);

		}else{
			app00202Result.setFreeuse3(moneyTran(app00202Result.getFreeuse3()));
			appapi00202Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00203"+form.getCenterId()+".result", app00202Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00202Result);
		return modelMap;
	}

	private String moneyTran(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = NumberFormat.getCurrencyInstance().format(new Double(money)).substring(1);
		return result;
	}
	
}
