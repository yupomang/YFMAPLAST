package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00101Result;
import com.yondervision.yfmap.result.haikou.AppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00089800 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101Result app00101Result = new AppApi00101Result();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			//2017-07-31 精确个人查询信息
			map.put("summarycode", "0001");
			map.put("grzh", "");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YECX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YECX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			if(!"0".equals(app00101Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return modelMap;
			}			
		}
		
		//app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
		app00101Result.setGrzhzt(PropertiesReader.getProperty("yingshe.properties","grzhzt"+app00101Result.getGrzhzt()+form.getCenterId()));
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
	
}
