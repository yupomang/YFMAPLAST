/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/**
 * @author CaoZhongYan
 *
 */
@Controller
public class AppApi008Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 楼盘查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00802.{ext}")
	public String appApi00802(AppApi00802Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00802 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00802_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00802 end.");
		return "/index";
	}
	
	/**
	 * 楼盘栋号查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00803.{ext}")
	public String appApi00803(AppApi00802Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00803 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00803_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00803 end.");
		return "/index";
	}
	
	/**
	 * 楼盘区域查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00804.{ext}")
	public String appApi00804(AppApi00802Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00804 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00804_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00804 end.");
		return "/index";
	}
}
