/**
 * 
 */
package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi00228Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;

import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

/**
 * @author luolin
 *
 */
@Controller
public class AppApi00228Contorller {
	Logger log = Logger.getLogger("YFMAP");

	/**
	 *  宁波公积金注销申请接收
	 * @param form
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping("/appapi00228.{ext}")
	public String appapi00228(AppApi00228Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi00228 begin.");
		log.debug("form:"+form);
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle00228_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi00228 end.");
		return "";
	}
	
	@RequestMapping("/appapi00229.{ext}")
	    public void appapi00229(AppApi00228Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        form.setBusinName("宁波公积金注销结果接收");
	        log.info(Constants.LOG_HEAD+"api/appApi00229 begin.");
			//连接市级公共数据平台获取数据
			String requestSecret =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
			log.info(requestSecret);
			Date date = new Date();
			String requestTime = String.valueOf(date.getTime());
			log.info("requestTime1111:" + requestTime);
			String appKey = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
			String sjpublicDatasUrl = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
			String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		
			long starTime=System.currentTimeMillis();
			HashMap<String, String> params = new HashMap<String, String>();
			String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/W7c37X3di5fS08O8.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			params.put("recordtime", form.getRecordtime());
			String auditmind = form.getAuditmind();
			auditmind = URLEncoder.encode(auditmind,"UTF-8");
			params.put("auditmind", auditmind);
			params.put("uniscid", form.getUniscid());
			params.put("state", form.getState());
			params.put("accepttime", form.getAccepttime());
			log.info("recordtime===="+form.getRecordtime());
			log.info("auditmind===="+form.getAuditmind());
			log.info("uniscid===="+form.getUniscid());
			log.info("state===="+form.getState());
			log.info("accepttime===="+ form.getAccepttime());
		
			params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
					+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
					+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
					+"\",\"projectId\":\"\"}");
		    System.out.println("params========"+params);
			SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
			String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			System.out.println(result);
		
			log.info("宁波公积金注销结果接收"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
			JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
			json.remove("dataCount");
			json.remove("data");
			if(json.get("code").equals("00")){
				json.put("recode", "000000");
			}
			json.remove("code");
		
			log.info(Constants.LOG_HEAD+"appApi00229 end.");
			log.info("form.getChannel()="+form.getChannel());
		
			if(form.getChannel().trim().equals(""))
			{
				log.info("gbk");
				log.info("json.toString()=========="+json.toString().replace("'", "\""));
				response.getOutputStream().write(json.toString().replace("'", "\"").getBytes(request.getCharacterEncoding()));
			}else{
				log.info("utf-8");
				response.getOutputStream().write(json.toString().replace("'", "\"").getBytes("utf-8"));
			}
				return ;
			}
	
	
}
