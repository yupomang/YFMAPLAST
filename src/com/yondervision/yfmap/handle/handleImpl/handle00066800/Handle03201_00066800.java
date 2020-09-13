package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03201_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApiCommonForm form = (AppApiCommonForm)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();
		             	

		if(Constants.method_BSP.equals(send)){

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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GRGJJYECX.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314013");
			
//			String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>A020101</tr_code><corp_no></corp_no><req_no></req_no><serial_no></serial_no><ans_no>12355123</ans_no><next_no></next_no><tr_acdt>1233213</tr_acdt><tr_time>12334</tr_time><ans_code>00</ans_code><ans_info>234324</ans_info><particular_code></particular_code><particular_info></particular_info><atom_tr_count></atom_tr_count><reserved></reserved></head><body><accnum>234234</accnum><accname>2345</accname><birthday>wqeqwe</birthday><ceridnum>ewqeqw</ceridnum><basenum>qwewqe</basenum><totalunitprop>aweqwew</totalunitprop><totalperprop>qweqwe</totalperprop><totalprop>qweqwe</totalprop><monpaysum>23423</monpaysum><balance>werwer</balance><lastybal>wrtwe</lastybal><ydayamt>werwer</ydayamt><ydrawamt>werewb</ydrawamt><ypatchamt>werewr</ypatchamt><peraccstate>tewrwrwe</peraccstate><opendate>rgfdg</opendate><lastpay>234234</lastpay><unitaccnum>rwerew</unitaccnum><unitaccname>342343</unitaccname><instcode>q234</instcode></body></root>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_GRGJJYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00101ZHResult);
			log.debug("MAP封装成BEAN："+app00101ZHResult);
			if(!"000000".equals(app00101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00101ZHResult.getRecode());
				modelMap.put("msg", app00101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00101ZHResult.getRecode()+"  ,  描述msg : "+app00101ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00101ZHResult = null;
		
		appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result", app00101ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101ZHResult);
		return modelMap;
	}
	
}
