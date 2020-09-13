package com.yondervision.yfmap.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi80007Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import org.apache.log4j.Logger;
import org.apache.pdfbox.multipdf.Splitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

@Controller
public class WebApi800Contorller {
	Logger log = Logger.getLogger("YFMAP");

	/**
	 *  发布在大数据平台供房管局调用的接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80001.{ext}")
	public String webapi80001(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80001 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80001_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi80001 end.");
		return "";
	}

	/**
	 *  发布在大数据平台的查询接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80009.{ext}")
	public String webapi80009(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+" webapi80009 begin");
		log.debug("form:"+form);

		log.debug("转码前 form 里dwmc："+form.getSpt_dwmc());
		log.debug("request 里dwmc=："+ request.getParameter("spt_dwmc"));

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80009_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"webapi80009 end.");
		return "";
	}
	/**
	 * 解析出url参数中的键值对
	 * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * @param URL  url地址
	 * @return  url请求参数部分
	 * @author lzf
	 */
	public static Map<String, String> urlSplit(String URL){
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit=null;
		String strUrlParam = URL;
		if(strUrlParam==null){
			return mapRequest;
		}
		arrSplit=strUrlParam.split("[&]");
		for(String strSplit:arrSplit){
			String[] arrSplitEqual=null;
			arrSplitEqual= strSplit.split("[=]");
			//解析出键值
			if(arrSplitEqual.length>1){
				//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			}else{
				if(arrSplitEqual[0]!=""){
					//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}


	/**
	 *  发布在大数据平台的个人查询接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80002.{ext}")
	public String webapi80002(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80002 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80002_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi80002 end.");
		return "";
	}

	/**
	 *  发布在大数据平台的个人明细查询接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80003.{ext}")
	public String webapi80003(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80003 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80003_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi80003 end.");
		return "";
	}
	
	/**
	 *  住建委业务受理号上传
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80004.{ext}")
	public String webapi80004(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80004 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80004_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi80004 end.");
		return "";
	}
	
	
	/**
	 *  zwfw个人账户余额查询
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80005.{ext}")
	public String webapi80005(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80005 begin.");
		log.debug("form:"+form);		
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80005_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);	
		log.info(Constants.LOG_HEAD+"webapi80005 end.");
		return "";
	}


	/**
	 *  ll贷款信息查询接口
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80006.{ext}")
	public String webapi80006(AppApi030Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80006 begin.");
		log.debug("form:"+form);
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80006_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"webapi80006 end.");
		return "";
	}

	/**
	 *  住建委办件中心-收件服务
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80007.{ext}")
	public String webapi80007(AppApi80007Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80007 begin.");
		log.debug("form:"+form);
		//获取请求json
		StringBuffer sb = new StringBuffer() ;
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String s ;
		while((s=br.readLine())!=null){
			sb.append(s) ;
		}
		String str =sb.toString();
		String[] strArr = str.split("&");
		int i = strArr[1].length();
		str = strArr[1].substring(6,i);
		System.out.println("请求json=====" + str);
		System.out.println("URLDecoder.decode(str,\"UTF-8\")=====" + URLDecoder.decode(str,"UTF-8"));
		form.setParam(URLDecoder.decode(str,"UTF-8"));

		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80007_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"webapi80007 end.");
		return "";
	}

	/**
	 *  住房公积金个人缴存明细(省平台)
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/webapi80008.{ext}")
	public String webapi80008(AppApi030Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"webapi80008 begin.");
		log.debug("form:"+form);
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle80008_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info("modelMap"+modelMap);
		log.info(Constants.LOG_HEAD+"webapi80008 end.");
		return "";
	}




}
