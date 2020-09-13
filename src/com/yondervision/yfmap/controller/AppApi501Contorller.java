/**
 * 
 */
package com.yondervision.yfmap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApi50003Form;
import com.yondervision.yfmap.form.AppApi50102Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.CtrlHandleInter1;

/** 
* @ClassName: AppApi501Contorller 
* 
*/ 
@Controller
public class AppApi501Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 创建会话
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50101.{ext}")
	public String appApi50101(AppApi50102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50101 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50101_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50101 end.");
			
		return "";
	}
	
	/**
	 * 问答会话
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50102.{ext}")
	public String appApi50102(AppApi50102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50102 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50102_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50102 end.");
			
		return "";
	}
	
	/**
	 * 获取输入提示
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50103.{ext}")
	public String appApi50103(AppApi50102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50103 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50103_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50103 end.");
			
		return "";
	}	
	/**
	 * 评价
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50104.{ext}")
	public String appapi50104(AppApi50102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50104 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50104_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"appApi50104 end.");
			
		return "";
	}
	/**
	 * 留言
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50105.{ext}")
	public String appapi50105(AppApi50102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50105 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50105_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"appApi50105 end.");
			
		return "";
	}	
}
