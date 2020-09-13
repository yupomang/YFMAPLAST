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
import com.yondervision.yfmap.dto.DaGrjb;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80202Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.service.AppApi802Service;
import com.yondervision.yfmap.util.JavaBeanUtil;

@Controller
public class AppApi802Contorller {
	Logger log = Logger.getLogger("YFMAP");
	private AppApi802Service appApi802ServiceImpl = null;
	public void setAppApi802ServiceImpl(AppApi802Service appApi802ServiceImpl) {
		this.appApi802ServiceImpl = appApi802ServiceImpl;
	}
	//个人账户查询
	@RequestMapping("/appapi80201.{ext}")
	public String appApi80201(AppApiCommonForm form,  ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		Map map = appApi802ServiceImpl.appApi00801Select(form);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", map);
		return "";
	}	
	//个人账户对账
	@RequestMapping("/appapi80202.{ext}")
	public String appApi80202(AppApi80202Form form, ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		List list = appApi802ServiceImpl.appApi00802Select(form);
		modelMap.clear();
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("result", "");
		}else{
			List<List<TitleInfoBean>> result = new ArrayList();
			List<TitleInfoBean> GjDwzhmxResult = null;
			for(int i=0;i<list.size();i++){
				GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi8020200057400.result", list.get(i));			
				result.add(GjDwzhmxResult);
			}
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", result);
		}
		return "";
	}	
	
	//贷款审批情况查询
	@RequestMapping("/appapi80203.{ext}")
	public String appApi80203(AppApiCommonForm form, ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		List list = appApi802ServiceImpl.appApi00803Select(form);
		modelMap.clear();
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查无此人");
			modelMap.put("result", "");
		}else{
			List<List<TitleInfoBean>> result = new ArrayList();
			List<TitleInfoBean> GjDwzhmxResult = null;
			for(int i=0;i<list.size();i++){
				GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi8020300057400.result", list.get(i));			
				result.add(GjDwzhmxResult);
			}
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", result);
		}
		return "";
	}	
	
	//个人用户登陆验证
	@RequestMapping("/appapi80204.{ext}")
	public String appApi80204(AppApi40102Form form, ModelMap modelMap) throws Exception{
		AES aes = new AES();
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		List list = appApi802ServiceImpl.appApi00804Select(form);
		modelMap.clear();
		if(list == null || list.size() == 0){
			modelMap.put("recode", "999999");
			modelMap.put("msg", "身份证号码有误或密码不正确");
		}else{
			DaGrjb daGrjb = (DaGrjb) list.get(0);
			String gjjzh = daGrjb.getGrsb();
			modelMap.put("recode", "000000");
			modelMap.put("gjjzh", gjjzh);
			modelMap.put("msg", "成功");
		}
//		if("1".equals(str)){
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "公积金账号有误或密码不正确");
//		}
//		if("2".equals(str)){
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "查无此人，请核对身份证号码");
//		}
//		if("3".equals(str)){
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "姓名有误，请输入真实姓名");
//		}
		return "";
	}	
	//个人账户信息查询
	@RequestMapping("/appapi80205.{ext}")
	public String appapi80205(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi80205 start.");
		AES aes = new AES();
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
//		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80205_"+form.getCenterId()).newInstance();
		business.action(form, modelMap,request,response);		
		log.info(Constants.LOG_HEAD+"appApi50001 end.");
		return "";
	}	
	//个人账户对账
		@RequestMapping("/appapi80206.{ext}")
	public String appapi80206(AppApi80202Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		AES aes = new AES();
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80206_"+form.getCenterId()).newInstance();
		business.action(form, modelMap,request,response);		
		log.info(Constants.LOG_HEAD+"appApi50001 end.");
		return "";
	}	
	//个人贷款审批
	@RequestMapping("/appapi80207.{ext}")	
	public String appapi80207(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		AES aes = new AES();
//		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80207_"+form.getCenterId()).newInstance();
		business.action(form, modelMap,request,response);		
		log.info(Constants.LOG_HEAD+"appApi50001 end.");
		return "";
	}	
	//个人用户登陆验证
		@RequestMapping("/appapi80208.{ext}")
		public String appapi80208(AppApi40102Form form, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
			AES aes = new AES();
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
			CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80208_"+form.getCenterId()).newInstance();
			business.action(form, modelMap,request,response);		
			log.info(Constants.LOG_HEAD+"appApi50001 end.");
			return "";
		}	
}
