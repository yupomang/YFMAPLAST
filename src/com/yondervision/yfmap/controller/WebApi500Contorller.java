package com.yondervision.yfmap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.CtrlHandleInter1;

@Controller
public class WebApi500Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 获取单位基本信息-单位公积金信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50003.{ext}")
	public String webapi50003(AppApi020Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50003 begin.");
		log.debug("YFMAP统一用户视图查询——单位公积金基本信息:");		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02001_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		
		log.info(Constants.LOG_HEAD+"webapi50003 end.");
		return "";
	}
	
	/**
	 * 获取单位账户明细-单位公积金明细信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50004.{ext}")
	public String webapi50004(AppApi020Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50004 begin.");
		log.debug("YFMAP统一用户视图查询——单位公积金明细信息"+form);

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle02101_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"webapi50004 end.");
		return "";
	}
	
	/**
	 * 公积金账户基本信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50008.{ext}")
	public String webapi50008(AppApi00101Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50008 begin.");
		System.out.println("YFMAP统一用户视图查询webapi50008——公积金账户基本信息"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle001_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		
		log.info(Constants.LOG_HEAD+"webapi50008 end.");
		return "";
	}
	
	
	/**
	 * 账户明细（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50009.{ext}")
	public String webapi50009(AppApi00201Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50009 begin.");
		System.out.println("YFMAP统一用户视图查询webapi50009——个人公积金账户明细信息查询"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle002_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"webapi50009 end.");
		return "";
	}
	
	/**
	 * 贷款基本信息（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50010.{ext}")
	public String webapi50010(AppApi00601Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50010 begin.");
		log.debug("YFMAP统一用户视图查询webapi50010——个人贷款基本信息查询"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle006_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"webapi50010 end.");
		return "";
	}
	
	
	/**
	 * 还款明细（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50011.{ext}")
	public String webapi50011(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50011 begin.");
		log.debug("YFMAP统一用户视图查询webapi50011——个人公积金贷款还款明细"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle007_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"webapi50011 end.");
		return "";
	}
	
	
	/**
	 * 还款计划（核心）
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50012.{ext}")
	public String webapi50012(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50012 begin.");
		log.debug("YFMAP统一用户视图查询webapi50012——个人公积金贷款还款计划查询"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00702_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		
		log.info(Constants.LOG_HEAD+"webapi50012 end.");
		return "";
	}
	
	/**
	 * 在办业务-提取进度
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50014.{ext}")
	public String webapi50014(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50014 begin.");
		log.debug("YFMAP统一用户视图查询webapi50014——公积金提取进度查询"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01102_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		
		log.info(Constants.LOG_HEAD+"webapi50014 end.");
		return "";
	}	
	
	/**
	 * 在办业务-贷款进度
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50015.{ext}")
	public String webapi50015(AppApi00501Form form , ModelMap modelMap ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50015 begin.");
		log.debug("YFMAP统一用户视图查询webapi50015——个人贷款进度查询"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle011_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		
		log.info(Constants.LOG_HEAD+"webapi50015 end.");
		return "";
	}
	
	/**
	 * 在办业务-贷款进度
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50019.{ext}")
	public String webapi50019(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50019 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"webapi50019 end.");
		return "";
	}
	
	/**
	 * 资金类业务统计接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50025.{ext}")
	public String webapi50025(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50025 begin.");
		log.debug("form:"+form);		
		//channel , startdate , enddate
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50025_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"webapi50025 end.");
		return "";
	}
	
	/**
	 *  业务办理统计
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50026.{ext}")
	public String webapi50026(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50026 begin.");
		log.debug("form:"+form);		
		//channel , startdate , enddate
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50026_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"webapi50026 end.");
		return "";
	}
	
	
	/**
	 *  柜面业务办理统计
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50027.{ext}")
	public String webapi50027(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50027 begin.");
		log.debug("form:"+form);		
		//channel , startdate , enddate
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50027_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"webapi50027 end.");
		return "";
	}
	
	/**
	 * 资金类业务统计接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50028.{ext}")
	public String webapi50028(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50028 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50028_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		log.info(Constants.LOG_HEAD+"webapi50028 end.");
		return "";
	}
	
	/**
	 * 满意度调查
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50050.{ext}")
	public String webapi50050(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50050 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50050_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi50050 end.");
		return "";
	}
	
	/**
	 * 接通量统计
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50051.{ext}")
	public String webapi50051(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50051 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50051_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi50051 end.");
		return "";
	}
	
	/**
	 * 问题排序
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi50052.{ext}")
	public String webapi50052(AppApi00201Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi50052 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50052_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi50052 end.");
		return "";
	}
}
