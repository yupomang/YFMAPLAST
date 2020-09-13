package com.yondervision.yfmap.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80102Form;
import com.yondervision.yfmap.form.AppApi80103Form;
import com.yondervision.yfmap.form.AppApi80202Form;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.service.AppApi801Service;
import com.yondervision.yfmap.util.JavaBeanUtil;

@Controller
public class AppApi801Contorller {
	Logger log = Logger.getLogger("YFMAP");
	private AppApi801Service appApi801ServiceImpl = null;
	public void setAppApi801ServiceImpl(AppApi801Service appApi801ServiceImpl) {
		this.appApi801ServiceImpl = appApi801ServiceImpl;
	}

	//单位账户查询
	@RequestMapping("/appapi80101.{ext}")
	public String appApi80001(AppApi40102Form form,  ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setFullName(aes.decrypt(form.getFullName()));
		Map map = appApi801ServiceImpl.appApi00801Select(form);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("result", map);
		return "";
	}	
	
//	@RequestMapping("/appapi80101.{ext}")
//	public String appApi80001(AppApi00201Form form, String dwzh,  ModelMap modelMap) throws Exception{
////		Map map = appApi800ServiceImpl.appApi00801Select(dwzh);
////		modelMap.put("recode", "000000");
////		modelMap.put("result", map);
//		
//		log.info(Constants.LOG_HEAD+"appapi80101 begin.");			
//		
//		CtrlDBHandleInter business = (CtrlDBHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle801_db_"+form.getCenterId()).newInstance();
//		business.action(form, modelMap, appApi800ServiceImpl);
//		log.info(Constants.LOG_HEAD+"appapi80101 end.");
//		
//		
//		return "";
//		
//		
//	}	
	
	//账户对账
	@RequestMapping("/appapi80102.{ext}")
	public String appApi80002(AppApi80102Form form,ModelMap modelMap) throws Exception{
		List list = appApi801ServiceImpl.appApi00802Select(form);
		modelMap.clear();
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("result", "");
		}else{
			List<List<TitleInfoBean>> result = new ArrayList();
			List<TitleInfoBean> GjDwzhmxResult = null;
			for(int i=0;i<list.size();i++){
				GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi8000200057400.result", list.get(i));			
				result.add(GjDwzhmxResult);
			}
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", result);
		}
		return "";
	}	
	
	//单位利息凭证
	@RequestMapping("/appapi80103.{ext}")
	public String appApi80003(AppApi80103Form form, ModelMap modelMap) throws Exception{
		
		List list = appApi801ServiceImpl.appApi00803Select(form);
		modelMap.clear();
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("result", "");
		}else{
			List<List<TitleInfoBean>> result = new ArrayList();
			List<TitleInfoBean> GjDwzhmxResult = null;
			for(int i=0;i<list.size();i++){
				GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi8000300057400.result", list.get(i));			
				result.add(GjDwzhmxResult);
			}
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", result);
		}
		return "";
	}	
	
	//单位用户登陆验证
	@RequestMapping("/appapi80104.{ext}")
	public String appApi80004(AppApi40102Form form, ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setFullName(aes.decrypt(form.getFullName()));
		List list = appApi801ServiceImpl.appApi00804Select(form);
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "单位账号不存在或密码错误");
		}else{
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
		}
		return "";
	}	
	//单位账户信息查询
		@RequestMapping("/appapi80105.{ext}")
		public String appapi80105(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
			AES aes = new AES();
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
			form.setFullName(aes.decrypt(form.getFullName()));
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80105_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
			log.info(Constants.LOG_HEAD+"appApi50001 end.");
			return "";
		}	
		//单位账户对账
			@RequestMapping("/appapi80106.{ext}")
		public String appapi80106(AppApi80102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
			AES aes = new AES();
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80106_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
			log.info(Constants.LOG_HEAD+"appApi50001 end.");
			return "";
		}	
		//单位利息凭证
		@RequestMapping("/appapi80107.{ext}")	
		public String appapi80107(AppApi80103Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
			AES aes = new AES();
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80107_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
			log.info(Constants.LOG_HEAD+"appApi50001 end.");
			return "";
		}	
		//单位用户登陆验证
			@RequestMapping("/appapi80108.{ext}")
			public String appapi80108(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
				AES aes = new AES();
				form.setFullName(aes.decrypt(form.getFullName()));
//				form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
//				form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
				CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80108_"+form.getCenterId()).newInstance();
				business.action(form, modelMap,request,response);		
				log.info(Constants.LOG_HEAD+"appapi80108 end.");
				return "";
			}
}
