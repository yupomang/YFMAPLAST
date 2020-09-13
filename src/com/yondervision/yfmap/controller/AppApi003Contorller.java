/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00301Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/** 
* @ClassName: AppApi003Contorller 
* @Description: 结息对账Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi003Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 结息对账查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00301.{ext}")
	public String appApi00301(AppApi00301Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00301 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle003_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00301 end.");
		return "";
	}	
}
