/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi03402Form;
import com.yondervision.yfmap.form.AppApi034Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi034Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi03401.{ext}")
	public String appApi03401(AppApi034Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03401 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03401_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03401 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03402.{ext}")
	public String appApi03402(AppApi03402Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03402 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03402_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03402 end.");
		return "/index";
	}
	
	@RequestMapping("/appapi03403.{ext}")
	public String appApi03403(AppApi034Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03403 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03403_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03403 end.");
		return "/index";
	}
}
