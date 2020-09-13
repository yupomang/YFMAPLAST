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
public class AppApi037Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi03701.{ext}")
	public String appApi03701(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03701 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03701_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03701 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03702.{ext}")
	public String appApi03702(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03702 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03702_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03702 end.");
		return "/index";
	}	
}
