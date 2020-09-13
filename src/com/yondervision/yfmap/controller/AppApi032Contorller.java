/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi032Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi03201.{ext}")
	public String appApi03201(AppApiCommonForm form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi03201 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle03201_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi03201 end.");
		return "/index";
	}	
}
