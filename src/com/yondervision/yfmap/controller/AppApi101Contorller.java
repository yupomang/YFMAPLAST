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
import com.yondervision.yfmap.form.AppApi10101Form;
import com.yondervision.yfmap.form.AppApi10106Form;
import com.yondervision.yfmap.form.AppApi10107Form;
import com.yondervision.yfmap.form.AppApi10108Form;
import com.yondervision.yfmap.form.AppApi10109Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/**
 * @author gq
 *
 */
@Controller
public class AppApi101Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	/**
	 * 获取网点排队信息
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10106.{ext}")
	public String appApi10106(AppApi10106Form form, ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10106 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10106_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10106 end.");
		return "";
	}
	
	/**
	 * 排队取号
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10107.{ext}")
	public String appApi10107(AppApi10107Form form, ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10107 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10107_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10107 end.");
		return "";
	}
	
	/**
	 * 获取我的排号记录
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10108.{ext}")
	public String appApi10108(AppApi10108Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10108 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10108_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10108 end.");
		return "";
	}
	
	/**
	 * 获取当前排队号码状态
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10109.{ext}")
	public String appApi10109(AppApi10109Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10109 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10109_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10109 end.");
		return "";
	}
	
	/**
	 * 查询核心网点信息
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10111.{ext}")
	public String appApi10111(AppApi10101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10111 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10111_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10111 end.");
		return "";
	}
	
	/**
	 * 银行网点排队信息
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi10112.{ext}")
	public String appApi10112(AppApi10101Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi10112 begin.");
		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle10112_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi10112 end.");
		return "";
	}
}
