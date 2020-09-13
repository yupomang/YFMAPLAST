package com.yondervision.yfmap.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi62500Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

@Controller
public class AppApi625Contorller {
	Logger log = Logger.getLogger("YFMAP");	
    /**
     * 查询个人预约信息
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi62591.{ext}")
 	public String appapi62591(AppApi62500Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
	   Logger log = Logger.getLogger("YFMAP");
	   
	   log.debug("【柜面预约查询——appapi62591.json 请求参数："+CommonUtil.getStringParams(form));
	   System.out.println("【柜面预约查询——开始appapi62591】");
//	   String aa = java.net.URLDecoder.decode(request.getParameter("elements"), "GBK");
	   System.out.println("【柜面预约查询——城市代码："+form.getCenterId()+"】");
	   System.out.println("【柜面预约查询——预约凭证号："+form.getApponum()+"】");
	   System.out.println("【柜面预约查询——个人证件号："+form.getCertinum()+"】");
	   System.out.println("【柜面预约查询——开始时间："+form.getStartdate()+"】");
	   System.out.println("【柜面预约查询——开始时间："+form.getEnddate()+"】");
	   
	   if(CommonUtil.isEmpty(form.getApponum())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "预约任证号为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getCenterId())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "城市编码为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getCertinum())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "证件号码为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getCenterId())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "城市编码为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getStartdate())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "开始日期为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getEnddate())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "结束日期为空！");
		   return "";
	   }
	   
	   
	   request.setAttribute("centerId", form.getCenterId());
	   HashMap result = new HashMap();
	   try{	   		
		   String PROPERTIES_FILE_NAME = "properties.properties";
		   YbmapMessageUtil post = new YbmapMessageUtil();
		   String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi62591.json";
		   log.debug("【柜面预约查询——YBMAP url ："+url+"】");
		   HashMap map = BeanUtil.transBean2Map(form);
		   String rm = post.post(url, map);
		   log.debug("【柜面预约查询——返回结果["+rm+"]】");
//		   Gson gson = new Gson();
//		   Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
//		   if(!CommonUtil.isEmpty(result.get("recode"))){
//			   modelMap.clear();
//			   modelMap.put("recode", result.get("recode"));
//			   modelMap.put("msg", "处理成功");
//		   }else{
//			   modelMap.clear();
//			   modelMap.put("recode", "999999");
//			   modelMap.put("msg", ybmapmsg.get("msg"));
//		   }
		   modelMap.clear();
		   String encoding = "";
		   if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			   encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(rm.getBytes(encoding));
	   }catch(Exception e){
		   modelMap.clear();
		   modelMap.put("recode", "999998");
		   modelMap.put("remsg", "");
		   return "";
	   }
	   log.debug("【柜面预约查询——结束】");
	   System.out.println("【柜面预约查询——结束appapi62591】");
	   return "";
    }
    
    
    /**
     * 查询个人预约信息
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi62592.{ext}")
 	public String appapi62592(AppApi62500Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
	   Logger log = Logger.getLogger("YFMAP");
	   
	   log.debug("【柜面预约查询——appapi62592.json 请求参数："+CommonUtil.getStringParams(form));
	   System.out.println("【柜面预约查询——开始appapi62592】");
//	   String aa = java.net.URLDecoder.decode(request.getParameter("elements"), "GBK");
	   System.out.println("【柜面预约查询——城市代码："+form.getCenterId()+"】");
	   System.out.println("【柜面预约查询——预约凭证号："+form.getApponum()+"】");
	   System.out.println("【柜面预约查询——个人证件号："+form.getAppid()+"】");
	   
	   if(CommonUtil.isEmpty(form.getCenterId())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "城市编码为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getAppostate())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "状态为空！");
		   return "";
	   }
	   if(CommonUtil.isEmpty(form.getAppoid())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "预约ID为空！");
		   return "";
	   }
	   
	   
	   request.setAttribute("centerId", form.getCenterId());
	   HashMap result = new HashMap();
	   try{	   		
		   String PROPERTIES_FILE_NAME = "properties.properties";
		   YbmapMessageUtil post = new YbmapMessageUtil();
		   String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi62592.json";
		   log.debug("【柜面预约查询——YBMAP url ："+url+"】");
		   HashMap map = BeanUtil.transBean2Map(form);
		   String rm = post.post(url, map);
		   log.debug("【柜面预约查询——返回结果["+rm+"]】");
//		   Gson gson = new Gson();
//		   Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
//		   if(!CommonUtil.isEmpty(result.get("recode"))){
//			   modelMap.clear();
//			   modelMap.put("recode", result.get("recode"));
//			   modelMap.put("msg", "处理成功");
//		   }else{
//			   modelMap.clear();
//			   modelMap.put("recode", "999999");
//			   modelMap.put("msg", ybmapmsg.get("msg"));
//		   }
		   modelMap.clear();
		   String encoding = "";
		   if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			   encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(rm.getBytes(encoding));
	   }catch(Exception e){
		   modelMap.clear();
		   modelMap.put("recode", "999998");
		   modelMap.put("remsg", "");
		   return "";
	   }
	   log.debug("【柜面预约查询——结束】");
	   System.out.println("【柜面预约查询——结束appapi62592】");
	   return "";
    }
    
    
    /**
     * 查询个人预约信息
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi62593.{ext}")
 	public String appapi62593(AppApi62500Form form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
	   Logger log = Logger.getLogger("YFMAP");
	   
	   log.debug("【柜面预约查询——appapi62593.json 请求参数："+CommonUtil.getStringParams(form));
	   System.out.println("【柜面预约查询——开始appapi62593】");
//	   String aa = java.net.URLDecoder.decode(request.getParameter("elements"), "GBK");
	   System.out.println("【柜面预约查询——城市代码："+form.getCenterId()+"】");
	   
	   
	   if(CommonUtil.isEmpty(form.getCenterId())){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("remsg", "城市编码为空！");
		   return "";
	   }
	   
	   
	   request.setAttribute("centerId", form.getCenterId());
	   HashMap result = new HashMap();
	   try{	   		
		   String PROPERTIES_FILE_NAME = "properties.properties";
		   YbmapMessageUtil post = new YbmapMessageUtil();
		   String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi62593.json";
		   log.debug("【柜面预约查询——YBMAP url ："+url+"】");
		   HashMap map = BeanUtil.transBean2Map(form);
		   String rm = post.post(url, map);
		   log.debug("【柜面预约查询——返回结果["+rm+"]】");
//		   Gson gson = new Gson();
//		   Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
//		   if(!CommonUtil.isEmpty(result.get("recode"))){
//			   modelMap.clear();
//			   modelMap.put("recode", result.get("recode"));
//			   modelMap.put("msg", "处理成功");
//		   }else{
//			   modelMap.clear();
//			   modelMap.put("recode", "999999");
//			   modelMap.put("msg", ybmapmsg.get("msg"));
//		   }
		   modelMap.clear();
		   String encoding = "";
		   if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
			   encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(rm.getBytes(encoding));
	   }catch(Exception e){
		   modelMap.clear();
		   modelMap.put("recode", "999998");
		   modelMap.put("remsg", "");
		   return "";
	   }
	   log.debug("【柜面预约查询——结束】");
	   System.out.println("【柜面预约查询——结束appapi62593】");
	   return "";
    }
}
