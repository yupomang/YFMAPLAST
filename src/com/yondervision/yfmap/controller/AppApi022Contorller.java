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
public class AppApi022Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi02201.{ext}")
	public String appApi02201(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02201 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02201_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02201 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02202.{ext}")
	public String appApi02202(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02202 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02202_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02202 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02203.{ext}")
	public String appApi02203(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02203 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02203_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02203 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02204.{ext}")
	public String appApi02204(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02204 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02204_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02204 end.");
		return "/index";
	}	
	
	
	@RequestMapping("/appapi02205.{ext}")
	public String appApi02205(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02204 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02205_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02205 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02206.{ext}")
	public String appApi02206(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02206 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02206_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02206 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02207.{ext}")
	public String appApi02207(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02207 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02207_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02207 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02208.{ext}")
	public String appApi02208(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02208 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02208_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02208 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02209.{ext}")
	public String appApi02209(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02209 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02209_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02209 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02210.{ext}")
	public String appApi02210(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02210 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02210_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02210 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02211.{ext}")
	public String appApi02211(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02211 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02211_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02211 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02212.{ext}")
	public String appApi02212(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02212 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02212_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02212 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi02213.{ext}")
	public String appApi02213(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02213 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02213_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02213 end.");
		return "/index";
	}	
}
