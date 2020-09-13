package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApi80007Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

@Controller
public class AppApi00240Contorller {
	Logger log = Logger.getLogger("YFMAP");

	/**
	 *  返回申请开具证明信息接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00240.{ext}")
	public String appapi00240(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00240 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle00240_"+"05740000").newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"appapi00240 end.");
		return "";
	}
	
	/**
	 *  返回查询缴存证明信息接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00241.{ext}")
	public String appapi00241(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00241 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle00241_"+"05740000").newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"appapi00241 end.");
		return "";
	}

	/**
	 *  接收申请注销证明接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00242.{ext}")
	public String appapi00242(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00242 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle000242_"+"05740000").newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"appapi00242 end.");
		return "";
	}
	
	/**
	 *  接收回执信息接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00243.{ext}")
	public String appapi00243(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00243 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle00243_"+"05740000").newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"appapi00243 end.");
		return "";
	}
	
	
	/**
	 *  接收结清信息接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00244.{ext}")
	public String appapi00244(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00244 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle00244_"+"05740000").newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"appapi00244 end.");
		return "";
	}


	/**
	 *  返回贷款台账查询接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00245.{ext}")
	public String appapi00245(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00245 begin.");
		log.debug("form:"+form);
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+"05740000"+".Handle00245_"+"05740000").newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi00245 end.");
		return "";
	}


}
