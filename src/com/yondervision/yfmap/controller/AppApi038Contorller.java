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
public class AppApi038Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi03801.{ext}")
	public String appApi03801(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03801 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03801_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03801 end.");
		return "/index";
	}	
	@RequestMapping("/appapi03803.{ext}")
	public String appApi03803(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03803 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03803_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03803 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03804.{ext}")
	public String appApi03804(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03804 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03804_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03804 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03805.{ext}")
	public String appApi03805(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03805 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03805_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03805 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03806.{ext}")
	public String appApi03806(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03806 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03806_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03806 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03807.{ext}")
	public String appApi03807(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03807 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03807_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03807 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03808.{ext}")
	public String appApi03808(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03808 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03808_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03808 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03809.{ext}")
	public String appApi03809(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03809 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03809_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03809 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03811.{ext}")
	public String appApi03811(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03811 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03811_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03811 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03820.{ext}")
	public String appApi03820(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03820 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03820_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03820 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03821.{ext}")
	public String appApi03821(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03821 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03821_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03821 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03822.{ext}")
	public String appApi03822(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03822 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03822_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03822 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03823.{ext}")
	public String appApi03823(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03823 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03823_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03823 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03824.{ext}")
	public String appApi03824(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03824 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03824_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03824 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03825.{ext}")
	public String appApi03825(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03825 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03825_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03825 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03826.{ext}")
	public String appApi03826(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03826 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03826_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03826 end.");
		return "/index";
	}	
	@RequestMapping("/appapi03810.{ext}")
	public String appApi03810(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03810 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03810_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03810 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03827.{ext}")
	public String appApi03827(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03827 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03827_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03827 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03828.{ext}")
	public String appApi03828(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03828 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03828_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03828 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi03829.{ext}")
	public String appApi03829(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03829 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03829_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03829 end.");
		return "/index";
	}	
}
