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
public class AppApi021Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi02101.{ext}")
	public String appApi02101(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02101 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02101_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02101 end.");
		return "/index";
	}	
	@RequestMapping("/appapi02102.{ext}")
	public String appApi02102(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02102 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02102_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02102 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02103.{ext}")
	public String appApi02103(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02103 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02103_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02103 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02104.{ext}")
	public String appApi02104(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02104 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02104_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02104 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02105.{ext}")
	public String appApi02105(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02105 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02105_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02105 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02106.{ext}")
	public String appApi02106(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02106 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02106_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02106 end.");
		return "/index";
	}	
}
