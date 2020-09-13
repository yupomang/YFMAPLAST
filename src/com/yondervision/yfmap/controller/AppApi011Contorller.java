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
import com.yondervision.yfmap.form.AppApi01103Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;


@Controller
public class AppApi011Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 贷款进度查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01101.{ext}")
	public String appApi01101(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01101 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle011_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01101 end.");
		return "";
	}
	
	/**
	 * 提取进度查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01102.{ext}")
	public String appApi01102(AppApi00501Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01102 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01102_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01102 end.");
		return "";
	}	
	
	/**
	 * 接受消息渠道设置
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01103.{ext}")
	public String appApi01103(AppApi01103Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01103 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01103_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01103 end.");
		return "";
	}	
	/**
	 * 资格审查
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01104.{ext}")
	public String appApi01104(AppApi01103Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01104 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01104_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01104 end.");
		return "";
	}	
}
