package com.yondervision.yfmap.handle.handleImpl.handle00075500;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00401Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle004_00075500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00401Form form = (AppApi00401Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi004Send"+form.getCenterId()).trim();		
		log.debug("form:"+form);		
		AppApi00401Result appApi00401Result	= new AppApi00401Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi004MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi004Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi004Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_TQJECX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_TQJECX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00401Result);
			log.debug("MAP封装成BEAN："+appApi00401Result);
			if(!Constants.sucess.equals(appApi00401Result.getRecode())){
				modelMap.put("recode", appApi00401Result.getRecode());
				modelMap.put("msg", appApi00401Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00401Result.getRecode()+"  ,  描述msg : "+appApi00401Result.getMsg());
				return modelMap;
			}
		}
		
		
//		Random rand = new Random();  
//		int m = rand.nextInt(3); 
//		if(m==1){
//			appApi00401Result.setAccountState("已冻结");
//			appApi00401Result.setAmount("30000.00");
//			appApi00401Result.setFreezeDate("2013-10-01");
//			appApi00401Result.setFreezeMoney("10000.00");
//			appApi00401Result.setOperationDate("2013-09-28");
//			appApi00401Result.setSurplusAccount("1234567890");
//		}else if(m==2){
//			appApi00401Result.setAccountState("正常");
//			appApi00401Result.setAmount("20000.00");
//			appApi00401Result.setFreezeDate("2013-10-01");
//			appApi00401Result.setFreezeMoney("10000.00");
//			appApi00401Result.setOperationDate("2013-09-28");
//			appApi00401Result.setSurplusAccount("1234567891");
//		}else{
//			appApi00401Result.setAccountState("部分冻结");
//			appApi00401Result.setAmount("25000.00");
//			appApi00401Result.setFreezeDate("2013-10-01");
//			appApi00401Result.setFreezeMoney("10000.00");
//			appApi00401Result.setOperationDate("2013-09-28");
//			appApi00401Result.setSurplusAccount("1234567892");
//		}
							
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00401"+form.getCenterId()+".result", appApi00401Result);			
		
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}

}
