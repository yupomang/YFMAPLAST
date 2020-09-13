/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi41201Form;
import com.yondervision.yfmap.form.AppApi41202Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/** 
* @ClassName: AppApi412Contorller 
* @Description: 短信签约Controller
* @author syw
* 
*/ 
@Controller
public class AppApi412Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 短信签约状态查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41201.{ext}")
	public String appApi42101(AppApi41201Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi41201 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle41201_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi41201 end.");
		return "";
	}
	
	/**
	 * 短信签约
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41202.{ext}")
	public String appApi41202(AppApi41202Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi41202 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle41202_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi41202 end.");
		return "";
	}
	/**
	 * 蒙电短信签约
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41203.{ext}")
	public String appApi41203(AppApi41202Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi41202 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle41203_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi41202 end.");
		return "";
	}
	/**
	 * 接收信息签约状态查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41204.{ext}")
	public String appApi41204(AppApi41201Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi41204 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle41204_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi41204 end.");
		return "";
	}
	
	/**
	 * 接受信息签约
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi41205.{ext}")
	public String appApi41205(AppApi41202Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi41205 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle41205_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi41205 end.");
		return "";
	}
	
}
