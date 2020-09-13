package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.text.DecimalFormat;
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

public class Handle006_00087500 implements CtrlHandleInter {
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
			HashMap map = BeanUtil.transBean2Map(form);

			//String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml", map, req);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430405");
			
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430405</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>成功</ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><loanbal>23456.25</loanbal><loanaccnum>7382929390234</loanaccnum><loansum>57389</loansum><loanrate>4.5%</loanrate><freeuse1>等额本金</freeuse1><freeuse7>已签订</freeuse7><amt1>0.00</amt1></body></root>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"0".equals(app00601Result.getRecode())){
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
		
		/* 模拟返回结束  */	
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
		Handle006_00087500 hand = new Handle006_00087500();
		System.out.println("formatStr=="+hand.numberTran("3.250000"));
	}
}
