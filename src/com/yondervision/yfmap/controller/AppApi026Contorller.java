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
public class AppApi026Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi02601.{ext}")
	public String appApi02601(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02601 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02601_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02601 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02602.{ext}")
	public String appApi02602(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02602 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02602_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02602 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02603.{ext}")
	public String appApi02603(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02603 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02603_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02603 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02604.{ext}")
	public String appApi02604(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02604 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02604_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02604 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02605.{ext}")
	public String appApi02605(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02605 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02605_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02605 end.");
		return "/index";
	}	
	@RequestMapping("/appapi02606.{ext}")
	public String appApi02606(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02606 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02606_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02606 end.");
		return "/index";
	}	
}
