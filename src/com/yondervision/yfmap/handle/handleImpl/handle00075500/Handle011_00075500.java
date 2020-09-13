package com.yondervision.yfmap.handle.handleImpl.handle00075500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle011_00075500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi01101Result appapi011 = new AppApi01101Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101Result app011Result = new AppApi01101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKJDCX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKJDCX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			if(!Constants.sucess.equals(app011Result.getRecode())){
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
		}
		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		String num = "";
		String val = "";
		for(int i=0;i<16;i++){
			if(i<10){
				num = "0"+i;
			}else{
				num = ""+i;
			}
			if(PropertiesReader.getProperty("yingshe.properties", "Apprflag"+num)==null)
				continue;
			val = PropertiesReader.getProperty("yingshe.properties", "Apprflag"+num).trim();
			if(list.size()==0){				
				AppApi011Result app011 = new AppApi011Result();
				app011.setApprflagID(num);
				app011.setApprflagMSG(val);
				app011.setApprflagDate("2013-01-03");
				list.add(app011);				
				System.out.println("状态码:"+num+"   描述:"+val);
			}
			boolean isflag = false;
			if(!val.equals("")){
				for(int j=0;j<list.size();j++){
					System.out.println("list.get(j).getApprflagMSG():"+list.get(j).getApprflagMSG());
					System.out.println("val:"+val);
					if(list.get(j).getApprflagMSG().equals(val)){						
						isflag = true;
						break;
					}
				}
				if(isflag==false){
					AppApi011Result app011 = new AppApi011Result();
					app011.setApprflagID(num);
					app011.setApprflagMSG(val);
					app011.setApprflagDate("2013-01-03");
					list.add(app011);					
					System.out.println("状态码:"+num+"   描述:"+val);
				}
			}			
		}
		/********* 20140703  标准结束  *****************************************************/
		
		
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", app011Result.getApprflag());
		modelMap.put("apprflagMSG", PropertiesReader.getProperty("yingshe.properties", "Apprflag"+app011Result.getApprflag()).trim());
		modelMap.put("apprflagDate", "2013-11-22");
		modelMap.put("result", list);	
		return modelMap;
	}

}
