package com.yondervision.yfmap.handle.handleImpl.handle00047201;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00601Result;
import com.yondervision.yfmap.result.mengdian.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00047201 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00601 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		BaoGangAppApi00601Result app00601Result= new BaoGangAppApi00601Result();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "123440"); 
			
//			String reqxml = "<RspCode>E87007</><RspMsg>合同代码[]不存在</><TranSeq>726</>";
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKYECX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!Constants.sucess_ts.equals(app00601Result.getRecode())){
				String msg = app00601Result.getMsg();
				if(!CommonUtil.isEmpty(app00601Result.getMsg()))
				{
					if(app00601Result.getMsg().contains("系统日终"))
					{
						msg = "温馨提示：由于公积金业务需每日进行日终结账，结账期间微信系统将无法查询，每日结账时间为17:30—18:30，望大家避开系统结账时间查询，敬请谅解！";
					}
				}
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", msg);
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		if("1".equals(form.getBusitype()))
		{
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result01", app00601Result);
		}
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}
}
