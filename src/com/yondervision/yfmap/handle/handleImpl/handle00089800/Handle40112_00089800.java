package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi40112Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 请求验证码
 */
public class Handle40112_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi40112Result app40112Result= new AppApi40112Result();
		AES aes = new AES();
		System.out.println(form.getIdcardNumber());
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		System.out.println(form.getIdcardNumber());
		if(!CommonUtil.isEmpty(form.getCardno()))
		{
			form.setPubaccnum(form.getCardno());
		}else if(!CommonUtil.isEmpty(form.getIdcardNumber()))
		{
			form.setPubaccnum(form.getIdcardNumber());
		}
		form.setFullName(aes.decrypt(form.getFullName()));
		form.setLogintype("1");
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			//form.setSendSeqno(req);
			form.setSendSeqno(CommonUtil.getDate()+" "+CommonUtil.getTime());
			form.setSendTime(CommonUtil.getTime());
			if(CommonUtil.isEmpty(form.getMobileNumber()))form.setMobileNumber("null");
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			if("1".equals(form.getIscheck()))
			{
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_JYSFXX.txt", map, req);
				log.debug("发往中心报文："+xml);
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142808");
				
				log.debug("中心下传报文："+rexml);
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_JYSFXX.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				if(!"0".equals(resultMap.get("recode"))){
					modelMap.clear();
					modelMap.put("recode", resultMap.get("recode"));
					modelMap.put("msg", resultMap.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap.get("recode")+"  ,  描述msg : "+resultMap.get("msg"));
					return modelMap;
				}
				String sjhm = resultMap.get("sjhm").toString();
//				String pwd = resultMap.get("pwd").toString();
//				String idcardNumber = resultMap.get("certinum").toString();
//				if(!CommonUtil.isEmpty(pwd))
//				{
//					if(pwd.equals(idcardNumber.substring(idcardNumber.length()-6,idcardNumber.length())))
//					{
//						modelMap.clear();
//						modelMap.put("recode", "999999");
//						modelMap.put("msg", "密码为初始密码，请修改！");
//						modelMap.put("isCheck", "0");
//						return modelMap;
//					}
//				}
				if(CommonUtil.isEmpty(sjhm))
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "手机号不能为空，请修改！");
					modelMap.put("isCheck", "0");
					return modelMap;
				}else
				{
					if(sjhm.length()!=11)
					{
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "手机号不合法，请修改！");
						modelMap.put("isCheck", "0");
						return modelMap;
					}
				}
			}else if("2".equals(form.getIscheck()))
			{
				
			}
			if("20".equals(form.getChannel()))
			{
				map.put("flag", "2");
			}else if("10".equals(form.getChannel()))
			{
				map.put("flag", "3");
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_FSYZM.txt", map, req);
			log.debug("发往中心报文："+xml);
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149364");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_FSYZM.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			
			
			BeanUtil.transMap2Bean(resultMap, app40112Result);
			
			
			if(!"0".equals(app40112Result.getRecode())){
				modelMap.put("recode", app40112Result.getRecode());
				modelMap.put("msg", app40112Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40112Result.getRecode()+"  ,  描述msg : "+app40112Result.getMsg());
				return modelMap;
			}
		}		
		
		List<TitleInfoBean> appapi40112Result = null;
		appapi40112Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi40112"+form.getCenterId()+".result", app40112Result);
		Iterator<TitleInfoBean> it1 = appapi40112Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("transcode", app40112Result.getTransCode());
		modelMap.put("result", appapi40112Result);
		log.info(Constants.LOG_HEAD+"appApi40112 end.");
		return modelMap;
	}
}
