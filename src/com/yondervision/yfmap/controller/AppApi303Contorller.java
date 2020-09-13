/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/**
 * @author CaoZhongYan
 *
 */
@Controller
public class AppApi303Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 网点及人数查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30309.{ext}")
	public String appApi30309(AppApi30309Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi30309 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle30309_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi30309 end.");
		return "/index";
	}
	
	/**
	 * 网点时间段查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30310.{ext}")
	public String appApi30310(AppApi30309Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi30310 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle30310_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi30310 end.");
		return "/index";
	}
	
	/**
	 * 预约添加
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30311.{ext}")
	public String appApi30311(AppApi30309Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi30311 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle30311_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi30311 end.");
		return "/index";
	}
	
	/**
	 * 预约查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30312.{ext}")
	public String appApi30312(AppApi30309Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi30312 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle30312_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi30312 end.");
		return "/index";
	}
	
	/**
	 * 预约查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi30313.{ext}")
	public String appApi30313(AppApi30309Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi30313 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle30313_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi30313 end.");
		return "/index";
	}
}
