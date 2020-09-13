/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi034Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi035Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi03501.{ext}")
	public String appApi03501(AppApi034Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03501 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03501_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03501 end.");
		return "/index";
	}	
}
