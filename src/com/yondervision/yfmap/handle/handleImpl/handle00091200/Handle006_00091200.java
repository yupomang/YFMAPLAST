package com.yondervision.yfmap.handle.handleImpl.handle00091200;

import java.text.NumberFormat;
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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baoshan.AppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00091200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi00601Result app00601Result= new AppApi00601Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());	
			if(Constants.CHANNELTYPE_APP.equals(form.getChannel())){
				form.setChannel("7");
			}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
				form.setChannel("9");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);

			//String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml", map, req);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430405");
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?>"+rexml;
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"00".equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
		}
		
		app00601Result.setLoanbal(moneyTran(app00601Result.getLoanbal()));
		app00601Result.setLoanamt(moneyTran(app00601Result.getLoanamt()));
		app00601Result.setRetint(moneyTran(app00601Result.getRetint()));
		app00601Result.setLoanrate(numberTran(app00601Result.getLoanrate()));
		
		List<TitleInfoBean> appapi00601Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		List<TitleInfoBean> appapi00601Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00601Result);
		modelMap.put("detail", appapi00601Detail);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

	private String moneyTran(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = NumberFormat.getCurrencyInstance().format(new Double(money)).substring(1);
		return result;
	}
	private String numberTran(String number){
		if(number==null||"".equals(number)){
			number = "0.00";
		}
		String result = new Double(number)+"%";
		return result;
	}
	
	public static void main(String[] args){
		String number = "3.250000";
		if(number==null||"".equals(number)){
			number = "0.00";
		}
		String result = new Double(number)+"%";
		System.out.println("result=="+result);
		Handle006_00091200 hand = new Handle006_00091200();
		System.out.println("formatStr=="+hand.numberTran("3.250000"));
	}
}
