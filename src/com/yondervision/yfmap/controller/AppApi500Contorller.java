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
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApi50003Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.CtrlHandleInter1;

/** 
* @ClassName: AppApi500Contorller 
* @author Caozhongyan
* @date Jan 20, 2014 2:06:59 PM   
* 
*/ 
@Controller
public class AppApi500Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 绑定
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50001.{ext}")
	public String appApi50001(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50001 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle500_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50001 end.");
			
		return "";
	}
	
	/**
	 * 绑定取消
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50002.{ext}")
	public String appApi50002(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50002 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50002_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50002 end.");
			
		return "";
	}
	
	/**
	 * 微信用户密码验证
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50003.{ext}")
	public String appApi50003(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50003 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50003_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		
		log.info(Constants.LOG_HEAD+"appApi50003 end.");
			
		return "";
	}	
	/**
	 * 微信用户短信验证码发送
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi50004.{ext}")
	public String appapi50004(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50004 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50004_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50004 end.");
			
		return "";
	}	
	
	@RequestMapping("/appapi50006.{ext}")
	public String appapi50006(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50006 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50006_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50006 end.");
		return "";
	}
	
	@RequestMapping("/appapi50007.{ext}")
	public String appapi50007(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50007 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50007_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50007 end.");
		return "";
	}
	
	@RequestMapping("/appapi50008.{ext}")
	public String appapi50008(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50006 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50008_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50006 end.");
		return "";
	}
	
	@RequestMapping("/appapi50009.{ext}")
	public String appapi50009(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50009 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50009_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50009 end.");
		return "";
	}
	
	@RequestMapping("/appapi50010.{ext}")
	public String appapi50010(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50010 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50010_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50010 end.");
		return "";
	}
	
	@RequestMapping("/appapi50011.{ext}")
	public String appapi50011(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50011 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50011_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50011 end.");
		return "";
	}
	
	@RequestMapping("/appapi50012.{ext}")
	public String appapi50012(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50012 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50012_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50012 end.");
		return "";
	}
	
	@RequestMapping("/appapi50013.{ext}")
	public String appapi50013(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50013 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50013_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50013 end.");
		return "";
	}
	
	@RequestMapping("/appapi50014.{ext}")
	public String appapi50014(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50014 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50014_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50014 end.");
		return "";
	}
	
	@RequestMapping("/appapi50015.{ext}")
	public String appapi50015(AppApi50002Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50015 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50015_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50015 end.");
		return "";
	}
	
	@RequestMapping("/appapi50016.{ext}")
	public String appapi50016(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50016 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50016_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50016 end.");
		return "";
	}
	
	@RequestMapping("/appapi50017.{ext}")
	public String appapi50017(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50017 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50017_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50017 end.");
		return "";
	}
	
	@RequestMapping("/appapi50018.{ext}")
	public String appapi50018(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50018 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50018_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50018 end.");
		return "";
	}
	
	@RequestMapping("/appapi50019.{ext}")
	public String appapi50019(AppApi50003Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50019 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50019_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50019 end.");
		return "";
	}
	
	@RequestMapping("/appapi50022.{ext}")
	public String appapi50022(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50022 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50022 end.");
		return "";
	}
	
	@RequestMapping("/appapi50023.{ext}")
	public String appapi50023(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50023 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50023 end.");
		return "";
	}
	
	@RequestMapping("/appapi50024.{ext}")
	public String appapi50024(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50024 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50024_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50024 end.");
		return "";
	}
	
	@RequestMapping("/appapi50025.{ext}")
	public String appapi50025(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50025 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50025app_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50025 end.");
		return "";
	}
	
	@RequestMapping("/appapi50026.{ext}")
	public String appapi50026(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50026 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50026_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50026 end.");
		return "";
	}
	
	@RequestMapping("/appapi50027.{ext}")
	public String appapi50027(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50027 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50027_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50027 end.");
		return "";
	}
	
	@RequestMapping("/appapi50028.{ext}")
	public String appapi50028(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50028 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50028_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50028 end.");
		return "";
	}
	
	@RequestMapping("/appapi50029.{ext}")
	public String appapi50029(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50029 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50029_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50029 end.");
		return "";
	}
	
	@RequestMapping("/appapi50030.{ext}")
	public String appapi50030(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50030 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50030_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50030 end.");
		return "";
	}
	
	@RequestMapping("/appapi50031.{ext}")
	public String appapi50031(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50031 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50031_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50031 end.");
		return "";
	}
	
	@RequestMapping("/appapi50032.{ext}")
	public String appapi50032(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50032 begin.");
		log.debug("form:"+form);		

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50032_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		log.info(Constants.LOG_HEAD+"appApi50032 end.");
		return "";
	}
	
	@RequestMapping("/appapi50033.{ext}")
	public String appApi50033(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50033 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50033_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi50033 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi50034.{ext}")
	public String appApi50034(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50034 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50034_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi50034 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi50035.{ext}")
	public String appApi50035(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50035 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50035_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi50035 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi50036.{ext}")
	public String appApi50036(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50036 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50036_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi50036 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi50037.{ext}")
	public String appApi50037(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi50037 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50037_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi50037 end.");
		return "/index";
	}	
}
