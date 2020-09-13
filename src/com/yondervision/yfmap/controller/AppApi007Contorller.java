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
* @ClassName: AppApi007Contorller 
* @Description: 贷款明细查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi007Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	/**
	 * 贷款明细查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00701.{ext}")
	public String appApi00701(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00701 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle007_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return "";
	}	
	
	/**
	 * 贷款明细查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00702.{ext}")
	public String appApi00702(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00702 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00702_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00702 end.");
		return "";
	}	
	
	/**
	 * 还款计划查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00703.{ext}")
	public String appApi00703(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00703 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00703_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00703 end.");
		return "";
	}
	
	/**
	 * 还款计划查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00704.{ext}")
	public String appApi00704(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00704 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00704_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00704 end.");
		return "";
	}
}
