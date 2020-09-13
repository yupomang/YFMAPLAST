/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/** 
* @ClassName: AppApi005Contorller 
* @Description: 提取明细查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi005Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 提取明细查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00501.{ext}")
	public String appApi00501(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00501 begin.");		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle005_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00501 end.");
		return "";
	}	
}
