/**
 * 
 */
package com.yondervision.yfmap.controller;

import com.alibaba.fastjson.JSON;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi00210Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.FamilyMembersForm;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author luolin
 *
 */
@Controller
public class AppApi00211Contorller {
	Logger log = Logger.getLogger("YFMAP");
	@RequestMapping("/appapi00221.{ext}")
	public void appapi00221(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("组织机构代码信息");
		log.info(Constants.LOG_HEAD+"api/appApi00221 begin.");
		//连接市级公共数据平台获取数据
		String requestSecret =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
		log.info(requestSecret);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		log.info("requestTime1111:" + requestTime);
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
		String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/YboP5vp8kgc8eu29.htm";
		params.put("appKey", appKey);
		params.put("sign", sign);
		params.put("requestTime", requestTime);
		params.put("organCode", form.getOrganCode());

		params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
				+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
				+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
				+"\",\"projectId\":\"\"}");

		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		System.out.println(result);

		log.info("组织机构代码信息"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
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

		log.info(Constants.LOG_HEAD+"appApi00221 end.");
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
