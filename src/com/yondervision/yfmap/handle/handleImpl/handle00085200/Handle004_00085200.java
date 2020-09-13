package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00401Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle004_00085200 
* @Description: 遵义提取金额查询
* @author Caozhongyan
* @date Apr 8, 2016 10:18:46 AM   
* 
*/ 
public class Handle004_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00401 start.");
		AppApi00401Form form = (AppApi00401Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		log.debug("form:"+form);		
		ZunYiAppApi00401Result appApi00401Result	= new ZunYiAppApi00401Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_TQJECX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			//String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156001");
			
			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>OK</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>156001</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><accnum>123456789</><drawdate>20151021</><drawtime>101010</><drawreason>1</><drawamt>20100.00</><bal>2010.00</><amt1>20100.00</><amt2>201000.00</><reason>购房贷款提取</>";
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_TQJECX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00401Result);
			log.debug("MAP封装成BEAN："+appApi00401Result);
			if(!"0".equals(appApi00401Result.getRecode())){
				modelMap.put("recode", appApi00401Result.getRecode());
				modelMap.put("msg", appApi00401Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00401Result.getRecode()+"  ,  描述msg : "+appApi00401Result.getMsg());
				return modelMap;
			}
		}							
		
		appApi00401Result.setAmt1(String.format("%,.2f",Double.valueOf(appApi00401Result.getAmt1())));
		appApi00401Result.setAmt2(String.format("%,.2f",Double.valueOf(appApi00401Result.getAmt2())));
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00401"+form.getCenterId()+".result", appApi00401Result);			
		
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi00401 end.");
		return modelMap;
	}

}
