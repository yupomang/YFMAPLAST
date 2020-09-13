/**
 * 
 */
package com.yondervision.yfmap.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

/**
 * @author CaoZhongYan
 *	
 */
@Controller
public class AppApi012Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 个人（项目）维修基金利息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01201.{ext}")
	public String appApi01201(AppApi00201Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01201 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle012_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01201 end.");
		return "/index";
	}	
}
