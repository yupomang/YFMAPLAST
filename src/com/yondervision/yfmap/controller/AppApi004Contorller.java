/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/** 
* @ClassName: AppApi004Contorller 
* @Description: 提取金额查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi004Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 提取金额查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00401.{ext}")
	public String appApi00401(AppApi00401Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00401 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle004_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00401 end.");
		return "";
	}	
	/**
	 * 个人资料变更查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00402.{ext}")
	public String appApi00402(AppApi00401Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00402 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00402_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00402 end.");
		return "";
	}	
}
