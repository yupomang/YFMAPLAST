/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi020Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi02001.{ext}")
	public String appApi02001(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00201 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02001_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00201 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02002.{ext}")
	public String appApi02002(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00202 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02002_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00202 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02003.{ext}")
	public String appApi02003(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02003 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02003_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02003 end.");
		return "/index";
	}	
}
