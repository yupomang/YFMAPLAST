/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi045Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi04501.{ext}")
	public String appApi04501(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi04501 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle04501_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi04501 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi04502.{ext}")
	public String appApi04502(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi04502 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle04502_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi04502 end.");
		return "/index";
	}
	
	@RequestMapping("/appapi04503.{ext}")
	public String appApi04503(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi04503 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle04503_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi04503 end.");
		return "/index";
	}
}
