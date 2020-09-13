package com.yondervision.yfmap.handle.handleImpl.handle00087100;

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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00612Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00613ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 贷款基本信息查询-314066
 * @author fxliu
 *
 */
public class Handle00613_00087100 implements CtrlHandleInter{

	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00612Form form = (AppApi00612Form)form1;
		AES aes = new AES(form.getCenterId(),form.getChannel(),form.getAppid(),form.getAppkey());	
		form.setLoancontrnum(aes.decrypt(form.getLoancontrnum()));
		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi00613ZHResult app00613ZHResult= new AppApi00613ZHResult();
		if(Constants.method_BSP.equals(send)){////xml通信处理
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
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
				form.setTranDate(form.getSendDate());
			}
			
			HashMap map = BeanUtil.transBean2Map(form);
			//TODO 待删除
//			String path ="E:\\\\workspace\\\\Eclipse_workspace\\\\YonderVision\\\\YFMAP\\\\src\\\\com\\\\yondervision\\\\yfmap\\\\config\\\\messagetemp\\\\00087100\\\\";
			String path = CommonUtil.getFullURL(Constants.msgPath
					 + form.getCenterId());
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, path+"/BSP_REQ_OffSITE_lOAN_INFO.txt", map, req);
			log.debug("贷款基本信息查询-前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314066");
			
			
			log.debug("贷款基本信息查询-前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, path+"/BSP_REP_OffSITE_lOAN_INFO.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00613ZHResult);
			log.debug("MAP封装成BEAN："+app00613ZHResult);
			if(!"0".equals(app00613ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00613ZHResult.getRecode());
				modelMap.put("msg", app00613ZHResult.getMsg());
				log.error("贷款基本信息查询-中心返回报文 状态recode :"+app00613ZHResult.getRecode()+"  ,  描述msg : "+app00613ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00613Result = null;
					
		appapi00613Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00613"+form.getCenterId()+".result", app00613ZHResult);
	
		Iterator<TitleInfoNameFormatBean> it1 = appapi00613Result.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00613Result);
		return modelMap;
	}
public static void main(String[] args) throws Exception {
		
		AppApi00612Form form = new AppApi00612Form();
		form.setCenterId("00087100");
		form.setChannel("20");
		AES aes = new AES(form.getCenterId(),form.getChannel(),null,null);
		form.setBodyCardNumber(aes.encrypt("130111198009091739".getBytes()));
		form.setLoancontrnum(aes.encrypt("02201711355".getBytes()));
		form.setTel("17777777777");
		
		Handle00613_00087100 handle = new Handle00613_00087100();
		handle.action(form, new ModelMap());
	}
}
