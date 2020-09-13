package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.text.DecimalFormat;
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
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baoshan.AppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101Result app00101Result = new AppApi00101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YECX.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);		
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430402");
			
//			String rexml ="<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430402</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>成功</ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><accname>杨春艳</accname><accnum>533000004732</accnum><bal>12776.98</bal><dac>已开通</dac><freeuse1>正常</freeuse1><lasttransdate>2015-12-04</lasttransdate><lpaym>201512</lpaym><monpaysum>6000.00</monpaysum><unitaccname>保山市金建星科技有限公司</unitaccname><unitaccnum>002473878394</unitaccnum></body></root>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			log.debug("MAP封装成BEAN："+app00101Result);
			if(!"0".equals(app00101Result.getRecode())){
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> appapi00101Result = new ArrayList<TitleInfoBean>();
		app00101Result.setAccountBalance(moneyTran(app00101Result.getAccountBalance()));
		app00101Result.setMonpaysum(moneyTran(app00101Result.getMonpaysum()));

		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app00101Result);
		/*List<TitleInfoBean> appapi00101Detail = null;
		appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app00101Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}*/
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		//modelMap.put("detail", appapi00101Detail);
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
		String result = new Double(number)*100+"%";
		return result;
	}
	
	public static void main(String[] args){
//		AppApi00101Form form1 = new AppApi00101Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setCenterId("00087500");
//		form1.setUserId("test");
//		form1.setSendDate("2016-01-25");
//		form1.setSendTime("171213");
//		form1.setBodyCardNumber("533001197304050045");
//		
//		
//		Handle001_bs00087500 hand = new Handle001_bs00087500();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String money = "12345.12";
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		DecimalFormat df = new DecimalFormat("#,###.00"); 
		String formatStr = df.format(Double.valueOf(money));
		System.out.println("formatStr=="+formatStr);
		Handle001_00087500 hand = new Handle001_00087500();
		System.out.println("formatStr=="+hand.moneyTran("12345.12"));
	}
}
