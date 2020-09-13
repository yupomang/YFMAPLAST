/**
 * 
 */
package com.yondervision.yfmap.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApi07030Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.ExchangeHttpClient;
import com.yondervision.yfmap.util.PropertiesReader;


@Controller
public class AppApi070Contorller {
	Logger log = Logger.getLogger("YFMAP");
	
	@RequestMapping("/appapi07010.{ext}")
	public String appApi07010(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07010 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07010_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07010 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07011.{ext}")
	public String appApi07011(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07011 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07011_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07011 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07012.{ext}")
	public String appApi07012(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07012 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07012_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07012 end.");
		return "/index";
	}	

	
	@RequestMapping("/appapi07020.{ext}")
	public String appApi07020(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07020 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07020_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07020 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07021.{ext}")
	public String appApi07021(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07021 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07021_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07021 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07022.{ext}")
	public String appApi07022(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07022 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07022 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07023.{ext}")
	public String appApi07023(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07023 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07023_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07023 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07024.{ext}")
	public String appApi07024(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07024 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07024_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07024 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi07025.{ext}")
	public String appApi07025(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07025 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07025_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07025 end.");
		return "/index";
	}	
	
	
	@RequestMapping("/appapi07026.{ext}")
	public String appApi07026(AppApi030Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi07026 begin.");				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle07026_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi07026 end.");
		return "/index";
	}	
	
	
	/**
	 * 退休信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi07030.{ext}")
	public void  appapi07030(AppApi07030Form form,  ModelMap modelMap, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi07030.json";
		System.out.println("form.getPubaccnum()="+form.getPubaccnum()+"form.getLpstate()="+form.getLpstate());
		System.out.println("YBMAPZH url ："+url);
		HashMap map = BeanUtil.transBean2Map(form);
		String rm = post.post(url, map);
		System.out.println("YB返回结果 ："+rm);
		JSONObject json = JSONObject.fromObject(rm);
		System.out.println("YB返回结果1 ："+ json.get("recode"));
		System.out.println("YB返回结果2 ："+ json.get("msg"));
		json.remove("appApi07030Form");
		System.out.println("返回结果json ："+json.toString());
		response.getOutputStream().write(json.toString().getBytes("gb18030"));
		return ;
	}
	
}
