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
import com.yondervision.yfmap.form.AppApi40115Form;
import com.yondervision.yfmap.form.AppApi40116Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.CtrlHandleInter1;

@Controller
public class AppApi401Contorller {
	Logger log = Logger.getLogger("YFMAP");

	/**
	 * @deprecated 注册验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40102.{ext}")
	public String appApi40102(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40102 begin.");
		if(form.getCenterId().equals("00057400")){
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle401_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
		}else{
			CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle401_"+form.getCenterId()).newInstance();
			business.action(form, modelMap);		
			
		}
		log.info(Constants.LOG_HEAD+"appApi40102 end.");
			
		return "/index";
	}
	
	/**
	 * @deprecated 登录验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40103.{ext}")
	public String appapi40103(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40103 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40102_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40103 end.");
			
		return "/index";
	}	
	
	/**
	 * @deprecated 登录验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40104.{ext}")
	public String appapi40104(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40104 begin.");
		if(form.getCenterId().equals("00057400")){
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40104_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
		}else{
			CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40104_"+form.getCenterId()).newInstance();
			business.action(form, modelMap);		
		}	
		log.info(Constants.LOG_HEAD+"appApi40104 end.");
			
		return "/index";
	}	
	
	
	/**
	 * @deprecated 用户名找回验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40107.{ext}")
	public String appapi40107(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40107 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40107_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40107 end.");
			
		return "/index";
	}	
	
	/**
	 * @deprecated 用户名找回验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40109.{ext}")
	public String appapi40109(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40109 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40109_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40109 end.");
			
		return "/index";
	}	
	
	
	
	/**
	 * @deprecated  手机APP用户登录并重新绑定接口
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40111.{ext}")
	public String appapi40111(AppApi40102Form form, ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40107 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40111_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40107 end.");
			
		return "/index";
	}
	
	/**
	 * @deprecated 发送验证码
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40112.{ext}")
	public String appapi40112(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40112 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40112_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40112 end.");
			
		return "/index";
	}	
	/**
	 * @deprecated 验证码验证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40113.{ext}")
	public String appapi40113(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40113 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40113_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40113 end.");
			
		return "/index";
	}
	
	/**
	 * @deprecated 修改核心密码
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40114.{ext}")
	public String appapi40114(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40114 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40114_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40114 end.");
			
		return "/index";
	}	
	
	/**
	 * @deprecated 实名资格校验（校验用户是否满足做实名身份认证，满足会直接发送短信验证码）(保山)
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40115.{ext}")
	public String appapi40115(AppApi40115Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40115 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40115_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40115 end.");
			
		return "";
	}
	
	/**
	 * @deprecated 实名身份认证
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40116.{ext}")
	public String appapi40116(AppApi40116Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40116 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40116_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40116 end.");
			
		return "";
	}
	
	@RequestMapping("/appapi40117.{ext}")
	public String appapi40117(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40117 begin.");
		if(form.getCenterId().equals("00057400")){
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40117_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
		}else{
			CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40117_"+form.getCenterId()).newInstance();
			business.action(form, modelMap);		
		}	
		log.info(Constants.LOG_HEAD+"appApi40117 end.");
			
		return "/index";
	}
	
	@RequestMapping("/appapi40118.{ext}")
	public String appapi40118(AppApi40116Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40118 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40118_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40118 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi40119.{ext}")
	public String appapi40119(AppApi40116Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40119 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40119_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40119 end.");
		return "/index";
	}	
	
	@RequestMapping("/appapi40120.{ext}")
	public String appapi40120(AppApi40116Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40120 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40120_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40120 end.");
		return "/index";
	}
	
	@RequestMapping("/appapi40121.{ext}")
	public String appapi40121(AppApi40116Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40121 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40121_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40121 end.");
		return "/index";
	}
	@RequestMapping("/appapi40122.{ext}")
	public String appapi40122(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi40122 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40122_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appapi40122 end.");
		return "/index";
	}
	@RequestMapping("/appapi40124.{ext}")
	public String appapi40124(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi40124 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40124_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appapi40124 end.");
		return "/index";
	}
	/**
	 * 个人通讯信息变更信息查询
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40126.{ext}")
	public String appapi40126(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40126 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40126_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40126 end.");
		return "";
	}
	
	/**
	 * 个人通讯信息变更
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi40127.{ext}")
	public String appapi40127(AppApi40102Form form,  ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi40127 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle40127_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);		
		log.info(Constants.LOG_HEAD+"appApi40127 end.");
		return "";
	}
}
