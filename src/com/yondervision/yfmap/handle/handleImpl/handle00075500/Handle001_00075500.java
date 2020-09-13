package com.yondervision.yfmap.handle.handleImpl.handle00075500;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00075500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101Result app00101Result = new AppApi00101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_YECX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YECX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			log.debug("MAP封装成BEAN："+app00101Result);
			if(!Constants.sucess.equals(app00101Result.getRecode())){
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return modelMap;
			}
		}
		
		
		
//		AppApi00101Result app00101Result= new AppApi00101Result();			
//		Random rand = new Random();  
//		int m = rand.nextInt(3); 
//		if(m==1){
//			app00101Result.setAccountBalance("1111.00");
//			app00101Result.setAccountOpenDate("2010-01-01");
//			app00101Result.setAccountOrganization("深圳罗湖管理部");
//			app00101Result.setAccountStatus("正常");
//			app00101Result.setAnnualPaymentSum("2111.00");
//			app00101Result.setCarryoverAmount("3111.00");
//			app00101Result.setCompanyAccount("12345");
//			app00101Result.setCompanyName("中海石油公司");
//			app00101Result.setCompanyPaymentRate("0.08");
//			app00101Result.setFrozenAmount("4000.00");
//			app00101Result.setFrozenDate("2010-02-02");
//			app00101Result.setLastestPaymentDate("2013-09-10");
//			app00101Result.setNewOperationDate("2013-10-01");
//			app00101Result.setPaymentBase("5000.00");
//			app00101Result.setPersonalPaymentRate("0.08");
//			app00101Result.setSurplusAccount("543211");
//		}else if(m==2){
//			app00101Result.setAccountBalance("2222.00");
//			app00101Result.setAccountOpenDate("2010-01-01");
//			app00101Result.setAccountOrganization("深圳盐田管理部");
//			app00101Result.setAccountStatus("正常");
//			app00101Result.setAnnualPaymentSum("2555.00");
//			app00101Result.setCarryoverAmount("3555.00");
//			app00101Result.setCompanyAccount("12345");
//			app00101Result.setCompanyName("中海石油公司");
//			app00101Result.setCompanyPaymentRate("0.10");
//			app00101Result.setFrozenAmount("4000.00");
//			app00101Result.setFrozenDate("2010-02-02");
//			app00101Result.setLastestPaymentDate("2013-09-10");
//			app00101Result.setNewOperationDate("2013-10-01");
//			app00101Result.setPaymentBase("5000.00");
//			app00101Result.setPersonalPaymentRate("0.10");
//			app00101Result.setSurplusAccount("54321");
//		}else{
//			app00101Result.setAccountBalance("3333.00");
//			app00101Result.setAccountOpenDate("2010-01-01");
//			app00101Result.setAccountOrganization("深圳福田管理部");
//			app00101Result.setAccountStatus("正常");
//			app00101Result.setAnnualPaymentSum("2222.00");
//			app00101Result.setCarryoverAmount("3333.00");
//			app00101Result.setCompanyAccount("12345");
//			app00101Result.setCompanyName("中海石油公司");
//			app00101Result.setCompanyPaymentRate("0.07");
//			app00101Result.setFrozenAmount("4000.00");
//			app00101Result.setFrozenDate("2010-02-02");
//			app00101Result.setLastestPaymentDate("2013-09-10");
//			app00101Result.setNewOperationDate("2013-10-01");
//			app00101Result.setPaymentBase("5000.00");
//			app00101Result.setPersonalPaymentRate("0.08");
//			app00101Result.setSurplusAccount("54321");
//		}
		
		List<TitleInfoBean> appapi00101Result = null;
		if(app00101Result.getFrozenAmount().equals("")||app00101Result.getFrozenDate().equals("")){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".resultNoF", app00101Result);
		}else{
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app00101Result);
		}
		
		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app00101Result);
		
		
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		Iterator<TitleInfoBean> it2 = appapi00101Detail.iterator();
		while (it2.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it2.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("detail", appapi00101Detail);
		return modelMap;
	}

}
