package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00915Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00916Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.IdCard;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author ljd
 * 贷款还款能力试算
 */
public class Handle00920_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00915Form form = (AppApi00915Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00920 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		AES aes = new AES();
		form.setJkrxm(aes.decrypt(form.getJkrxm()));
		if(!CommonUtil.isEmpty(form.getXingming()))
		{
			form.setXingming(aes.decrypt(form.getXingming()));
		}else
		{
			form.setXingming(form.getJkrxm());
		}
		form.setGaccname(aes.decrypt(form.getGaccname()));
		form.setBodyCardNumber(form.getLoaneecertinum());
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009MsgType"+form.getCenterId()).trim();			
		form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Key"+form.getCenterId()).trim());
		
		AppApi00916Result app00916Result= new AppApi00916Result();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			map.put("flag", "1");
			map.put("kkrzjhm", form.getMatecertinum());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap1);
			
			if(!"0".equals(resultMap1.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
				return modelMap;
			}
			
			
			
			map.put("flag", "3");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap3);
			if(!"0".equals(resultMap3.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap3.get("recode")+"  ,  描述msg : "+resultMap3.get("msg"));
				return modelMap;
			}
			
			map.put("flag", "4");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap4);
			if(!"0".equals(resultMap4.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap4.get("recode")+"  ,  描述msg : "+resultMap4.get("msg"));
				return modelMap;
			}
			
			map.put("flag", "5");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap5);
			if(!"0".equals(resultMap5.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap5.get("recode"));
				modelMap.put("msg", resultMap5.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap5.get("recode")+"  ,  描述msg : "+resultMap5.get("msg"));
				return modelMap;
			}
			
			if(map.containsKey("matecertinum") && map.get("matecertinum")!=null){
				map.put("flag", "2");
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
				log.debug("试算反显1 发往中心报文："+xml);
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
				HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
				log.debug("试算反显1 解析报文MAP："+resultMap2);
				if(!"0".equals(resultMap2.get("recode"))){
					modelMap.clear();
					modelMap.put("recode", resultMap2.get("recode"));
					modelMap.put("msg", resultMap2.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap2.get("recode")+"  ,  描述msg : "+resultMap2.get("msg"));
					return modelMap;
				}
				
				map.put("flag", "5");
				map.put("loaneecertinum", map.get("matecertinum"));
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
				log.debug("试算反显1 发往中心报文："+xml);
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
				HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
				log.debug("试算反显1 解析报文MAP："+resultMap6);
				if(!"0".equals(resultMap6.get("recode"))){
					modelMap.clear();
					modelMap.put("recode", resultMap6.get("recode"));
					modelMap.put("msg", resultMap6.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap6.get("recode")+"  ,  描述msg : "+resultMap6.get("msg"));
					return modelMap;
				}
				
				app00916Result.setPoaccnum((String)resultMap2.get("accnum"));
				app00916Result.setPogaccname((String)resultMap2.get("gaccname"));
				app00916Result.setPogryjce((String)resultMap2.get("gryjce")); 
				app00916Result.setPozjlx((String)resultMap6.get("zjlx"));
			}
			
			app00916Result.setGaccname((String)resultMap1.get("gaccname"));
			app00916Result.setAccnum((String)resultMap1.get("accnum"));
			app00916Result.setGryjce((String)resultMap1.get("gryjce"));
			app00916Result.setParamretval1((String)resultMap3.get("paramretval")); // 标志(3)	flag	参数返回值(家庭保证性支出)
			app00916Result.setZjlx((String)resultMap5.get("zjlx"));
			app00916Result.setParamretval2((String)resultMap4.get("paramretval")); // 标志(4)	flag	参数返回值(最大退休年龄)	paramretval	paramretval2
			
		}
		String home = app00916Result.getParamretval1();
		String age = app00916Result.getParamretval2();
		List<TitleInfoBean> homelist = new ArrayList<TitleInfoBean>();
		String h[] = home.split(",");
		for(int i=0;i<h.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String yhbm_t[] = h[i].split("=");
			if("1".equals(yhbm_t[0]))
			{
				t.setTitle("海口三亚档");
				t.setInfo(yhbm_t[1]);
			}else if("2".equals(yhbm_t[0]))
			{
				t.setTitle("其他");
				t.setInfo(yhbm_t[1]);
			}
			homelist.add(t);
		}
		String a[] = age.split(",");
		String maxage1 =  "60";
		String maxage2 =  "55";
		String maxage = "30";
		String s = IdCard.getGenderByIdCard(form.getLoaneecertinum());
		int nowage = IdCard.getAgeByIdCard(form.getLoaneecertinum());
		for(int i=0;i<a.length;i++)
		{
			String yhbm_t[] = a[i].split("=");
			if("1".equals(yhbm_t[0]))
			{
				maxage1 = yhbm_t[1];
			}else if("2".equals(yhbm_t[0]))
			{
				maxage2 = yhbm_t[1];
			}
		}
		if("1".equals(s))
		{
			maxage = Integer.parseInt(maxage1) -nowage+5+"";
		}else if("2".equals(s))
		{
			maxage = Integer.parseInt(maxage2) -nowage+5+"";
		}
		System.out.println("&&&&&&&&&&&maxage"+maxage);
		if(Integer.parseInt(maxage)>30)maxage = "30";
		List<TitleInfoBean> appapi00916Result = null;
		app00916Result.setPogryjce(Double.parseDouble(app00916Result.getPogryjce())*2+""); 
		app00916Result.setGryjce(Double.parseDouble(app00916Result.getGryjce())*2+""); 
		appapi00916Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00916"+form.getCenterId()+".result", app00916Result);
		Iterator<TitleInfoBean> it1 = appapi00916Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		//modelMap.put("result", appapi00916Result);
		modelMap.put("gryjce", app00916Result.getGryjce());
		modelMap.put("homelist", homelist);
		modelMap.put("maxage", maxage);
		log.info(Constants.LOG_HEAD+"appApi00920 end.");
		return modelMap;
	}
	
}
