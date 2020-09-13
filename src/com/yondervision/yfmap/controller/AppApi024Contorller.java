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
public class AppApi024Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi02401.{ext}")
	public String appApi02401(AppApi020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi02401 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02401_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi02401 end.");
		return "/index";
	}	
}
