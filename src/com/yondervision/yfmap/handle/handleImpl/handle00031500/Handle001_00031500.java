package com.yondervision.yfmap.handle.handleImpl.handle00031500;

import java.util.HashMap;
import java.util.Iterator;
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
import com.yondervision.yfmap.result.dalian.DaLianAppApi00101Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00031500 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		TangShanAppApi00101Result app00101Result = new TangShanAppApi00101Result();
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YECX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_YECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00002");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>72</msgtype><tr_code>X00002</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><dwmc>山东鲁能</dwmc><dwzh>212211</dwzh><grzh>112215</grzh><zhzt>正常</zhzt><kyye>201199.00</kyye><zxywrq>2015-04-05</zxywrq></body></root>";
			
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YECX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,"C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_YECX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			if(!Constants.sucess_ts.equals(app00101Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return modelMap;
			}			
		}
		
		app00101Result.setAccountBalance(String.format("%.2f",Double.valueOf(app00101Result.getAccountBalance()))+"元");
		app00101Result.setAnnualPaymentSum(String.format("%.2f",Double.valueOf(app00101Result.getAnnualPaymentSum()))+"元");
		app00101Result.setFrozenAmount(String.format("%.2f",Double.valueOf(app00101Result.getFrozenAmount()))+"元");
		app00101Result.setGzsr(String.format("%.2f",Double.valueOf(app00101Result.getGzsr()))+"元");
		app00101Result.setCompanyAccount(form.getCardno());
		double ppr = Double.valueOf(app00101Result.getPersonalPaymentRate())*100;
		double cpr = Double.valueOf(app00101Result.getCompanyPaymentRate())*100;
		app00101Result.setPersonalPaymentRate((int)ppr+"%");
		app00101Result.setCompanyPaymentRate((int)cpr+"%");
//		app00101Result.setCompanyPaymentRate(String.format("%,.2f",Double.valueOf(app00101Result.getCompanyPaymentRate())*100)+"%");
//		app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
//		app00101Result.setPaymentBase(String.format("%,.2f",Double.valueOf(app00101Result.getPaymentBase())));
//		app00101Result.setAnnualPaymentSum(String.format("%,.2f",Double.valueOf(app00101Result.getAnnualPaymentSum())));
		
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app00101Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00101Form form1 = new AppApi00101Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setLogid("113");
		Handle001_00031500 hand = new Handle001_00031500();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
