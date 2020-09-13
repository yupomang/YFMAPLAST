/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApi00603Form;
import com.yondervision.yfmap.form.AppApi00609Form;
import com.yondervision.yfmap.form.AppApi00611Form;
import com.yondervision.yfmap.form.AppApi00612Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/** 
* @ClassName: AppApi006Contorller 
* @Description: 贷款余额查询Controller
* @author Caozhongyan
* @date Sep 27, 2013 9:17:04 AM 
* 
*/ 
@Controller
public class AppApi006Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 贷款余额查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00601.{ext}")
	public String appApi00601(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00601 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle006_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return "";
	}
	
	/**
	 * 贷款信息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00602.{ext}")
	public String appApi00602(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00602 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00602_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00602 end.");
		return "";
	}
	
	/**
	 * 贷款信息修改
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00603.{ext}")
	public String appApi00603(AppApi00603Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00603 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00603_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00603 end.");
		return "";
	}
	
	/**
	 * 取借款合同号
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00604.{ext}")
	public String appApi00604(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00604 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00604_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00604 end.");
		return "";
	}
	/**
	 * 贷款欠款情况查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00605.{ext}")
	public String appApi00605(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00605 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00605_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00605 end.");
		return "";
	}
	
	/**
	 * 提前还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00606.{ext}")
	public String appApi00606(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00606 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00606_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00606 end.");
		return "";
	}
	
	/**
	 * 贷款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00607.{ext}")
	public String appApi00607(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00607 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00607_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00607 end.");
		return "";
	}
	
	/**
	 * 提前还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00608.{ext}")
	public String appApi00608(AppApi00601Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00608 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00608_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00608 end.");
		return "";
	}
	
	/**
	 * 提前部分还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00609.{ext}")
	public String appApi00609(AppApi00609Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00609 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00609_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00609 end.");
		return "";
	}
	
	/**
	 * 提前部分还款试算
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00610.{ext}")
	public String appApi00610(AppApi00609Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00610 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00610_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00610 end.");
		return "";
	}
	
	/**
	 * 贷款进度查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00611.{ext}")
	public String appApi00611(AppApi00611Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00611 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00611_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00611 end.");
		return "";
	}
	
	/**
	 * 异地贷款信息查询-登录-314065
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00612.{ext}")
	public String appApi00612(AppApi00612Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00612 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00612_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00612 end.");
		return "";
	}
	
	/**
	 * 贷款基本信息查询-314066
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00613.{ext}")
	public String appApi00611(AppApi00612Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi006113 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00613_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00613 end.");
		return "";
	}
	
	/**
	 * 贷款欠款查询-314068
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi00614.{ext}")
	public String appApi00614(AppApi00612Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi00614 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00614_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi00614 end.");
		return "";
	}
}
