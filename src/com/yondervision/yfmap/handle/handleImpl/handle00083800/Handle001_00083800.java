package com.yondervision.yfmap.handle.handleImpl.handle00083800;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00083800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101Result app40101Result = new AppApi00101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/REQ_YECX.txt", map, req);
			log.debug("前置3发往中心报文："+xml);
			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430004");
			
			
//			String rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><accnum>801078393844</accnum><accname>陈倩敏</accname><indiaccstate>正常</indiaccstate><bal>0.00</bal><lpaym>189912</lpaym><basenum>3000</basenum><prop>12%</prop><unitaccnum>12345678</unitaccnum><unitaccname>as东方时代</unitaccname><instname>123456</instname><monpaysum>1234.12</monpaysum><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></root>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/REP_YECX.txt", rexml, req);
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
		if("1899-12-31".equals(app40101Result.getNewOperationDate())){
			app40101Result.setNewOperationDate("--");
		}
		List<TitleInfoBean> appapi40101Result = null;
		
		appapi40101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app40101Result);
		List<TitleInfoBean> appapi40101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app40101Result);
		
		
		Iterator<TitleInfoBean> it1 = appapi40101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40101Result);
		modelMap.put("detail", appapi40101Detail);
		return modelMap;
	}
	public static void main(String[] args) throws CenterRuntimeException, IOException{
//		HashMap resultMap = MessageCtrMain.analysisPacket("2", "/Users/sunxl/Documents/REP_YECX.xml", "00005480<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode>119012</transCode><recvDate>2015-01-07</recvDate><recvTime></recvTime><sendSeqno>0007600040001</sendSeqno><key>1</key><centerSeqno>8081</centerSeqno><recode>000000</recode><msg>交易处理成功...</msg></head><body><accnum>801007023997</accnum><accname>王海茵</accname><statename>正常</statename><certinum>442000198706173289</certinum><bal>13558.08</bal><lpaym>201412</lpaym><lasttransdate>2014-12-26</lasttransdate><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></root>   ", "");
		String reqxml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><mi><head><transCode>appapi001</transCode><sendDate>2015-07-30</sendDate><sendTime>14:30:30</sendTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><detail><transdate>2015-07-01</transdate><amt>123</amt><remark>adf</remark><bal>12345</bal></detail><detail><transdate>2015-06-01</transdate><amt>123</amt><remark>adf</remark><bal>12345</bal></detail><detail><transdate>2015-05-01</transdate><amt>123</amt><remark>adf</remark><bal>12345</bal></detail></body></mi>		";
		HashMap resultMap = MessageCtrMain.analysisPacket("2", "/Users/sunxl/Documents/REP_YEMXCX.xml", reqxml, "");
		
	}
}
