package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.haikou.AppApi40109Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava_haikou;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 个人登录
 */
public class Handle40109_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		AES aes = new AES();
		if(!CommonUtil.isEmpty(form.getFullName())){
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getIdcardNumber())){
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));			
		}
		if(!CommonUtil.isEmpty(form.getCardno())){
			form.setCardno(aes.decrypt(form.getCardno()));			
		}
		if(!CommonUtil.isEmpty(form.getCardno()))
		{
			form.setPubaccnum(form.getCardno());
		}else if(!CommonUtil.isEmpty(form.getIdcardNumber()))
		{
			form.setPubaccnum(form.getIdcardNumber());
		}
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		log.info(Constants.LOG_HEAD+"appApi40109 start.");
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi40109Result app40109Result= new AppApi40109Result();
		form.setLogintype("4");
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			String miyao = form.getPassword();
			if(miyao!=null){
				byte[] my = DESForJava_haikou.encryption(miyao, "12345678");
				byte[] my2 = DESForJava_haikou.bcd_to_asc(my);
				form.setPassword(new String(my2));
			}
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			String miyao2 = form.getNewpassword();
			String xml;
			String rexml;
			if(!CommonUtil.isEmpty(miyao2)){
				map.put("newpassword", form.getNewpassword());
				map.put("type", "2");
				map.put("password", miyao);
				//map.put("mobileNumber", "18143001572");
				map.put("mobileNumber", form.getMobileNumber());
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_XGXX.txt", map, req);
				log.debug("发往中心报文："+xml);
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149370");
				
				log.debug("中心下传报文："+rexml);
				HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_XGXX.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap1);
				if(!"0".equals(resultMap1.get("recode"))){
					modelMap.clear();
					modelMap.put("recode", resultMap1.get("recode"));
					modelMap.put("msg", resultMap1.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
					return modelMap;
				}
				byte[] my = DESForJava_haikou.encryption(miyao2, "12345678");
				byte[] my2 = DESForJava_haikou.bcd_to_asc(my);
				form.setNewpassword(new String(my2));
				map.put("password", form.getNewpassword());
			}else
			{
				boolean jumpCheck = true;
				//苹果审核暂时不验证验证码
				if("10".equals(form.getChannel()))
				{
					if("1".equals(form.getDeviceType()))
					{
						jumpCheck = false;
					}
				}
				if(jumpCheck)
				{
					xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YZYZM.txt", map, req);
					log.debug("发往中心报文："+xml);
					rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149366");
					
					log.debug("中心下传报文："+rexml);
					HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YZYZM.txt", rexml, req);
					log.debug("解析报文MAP："+resultMap);			
					BeanUtil.transMap2Bean(resultMap, app40109Result);
					
					if(!"0".equals(app40109Result.getRecode())){
						modelMap.put("recode", app40109Result.getRecode());
						modelMap.put("msg", app40109Result.getMsg());
						log.error("中心返回报文 状态recode :"+app40109Result.getRecode()+"  ,  描述msg : "+app40109Result.getMsg());
						return modelMap;
					}
				}
			}
			
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_GRDL.txt", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142805");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_GRDL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app40109Result);
			app40109Result.setAccnum(aes.encrypt(app40109Result.getAccnum().getBytes("UTF-8")));
			if(!CommonUtil.isEmpty(app40109Result.getCertinum()))
				app40109Result.setCertinum(aes.encrypt(app40109Result.getCertinum().getBytes("UTF-8")));
			app40109Result.setAccname(aes.encrypt(app40109Result.getAccname().getBytes("UTF-8")));
			if(!CommonUtil.isEmpty(form.getCardno()))
				app40109Result.setCardno(aes.encrypt(form.getCardno().getBytes("UTF-8")));
			
			if(!"0".equals(app40109Result.getRecode())){
				modelMap.put("recode", app40109Result.getRecode());
				modelMap.put("msg", app40109Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40109Result.getRecode()+"  ,  描述msg : "+app40109Result.getMsg());
				return modelMap;
			}
		}		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", app40109Result);
		modelMap.put("accnum", app40109Result.getAccnum());	// 个人账号	accnum
		modelMap.put("accname", app40109Result.getAccname());	// 个人姓名	accname
		modelMap.put("certinum", app40109Result.getCertinum());	// 身份证号	certinum
		if(!CommonUtil.isEmpty(form.getCardno()))modelMap.put("cardno", app40109Result.getCardno());	// 联名卡号
		
		log.info(Constants.LOG_HEAD+"appApi40109 end.");
		return modelMap;
	}

}
