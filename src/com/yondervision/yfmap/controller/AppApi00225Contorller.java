/**
 * 
 */
package com.yondervision.yfmap.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lowagie.text.pdf.PdfReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.common.security.Base64Decoder;
import com.yondervision.yfmap.form.AppApi00225Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.RSAUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

/**
 * @author luolin
 *
 */
@Controller
public class AppApi00225Contorller {
	Logger log = Logger.getLogger("YFMAP");


	@RequestMapping("/appapi00225.{ext}")
	public void appapi00225(AppApi00225Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("浙江省公积金个人账户基本信息查询接口");
		log.info(Constants.LOG_HEAD+"api/appApi00225 begin.");

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/sjgxService/doQuery.do";

		String jgh = form.getBrccode();
		String jkbm = "gjjzh";
		String para = "{\"ZJHM\":\""+form.getZJHM()+"\"}";


		/** 指定公钥存放文件 */
		String PUBLIC_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "public_key_shengting").trim();

		/** 指定私钥存放文件 */
		String PRIVATE_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "private_key_shengting").trim();

		params.put("jgh", jgh);
		params.put("jkbm", jkbm);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");

		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appApi00225 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}
	@RequestMapping("/appapi00226.{ext}")
	public void appapi00226(AppApi00225Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("浙江省公积金个人贷款信息查询接口");
		log.info(Constants.LOG_HEAD+"api/appApi00226 begin.");

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/sjgxService/doQuery.do";

		String jgh = form.getBrccode();
		String jkbm = "gjjdk";
		String para = "{\"ZJHM\":\""+form.getZJHM()+"\"}";

		/** 指定公钥存放文件 */
		String PUBLIC_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "public_key_shengting").trim();

		/** 指定私钥存放文件 */
		String PRIVATE_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "private_key_shengting").trim();

		params.put("jgh", jgh);
		params.put("jkbm", jkbm);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");

		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);

		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");

		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appApi00226 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));

		return ;
	}


	@RequestMapping("/appapi00227.{ext}")
	public void appapi00227(AppApi00225Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("异地贷款缴存使用证明打印");
		log.info(Constants.LOG_HEAD+"api/appApi00227 begin.");

		HashMap map = BeanUtil.transBean2Map(form);
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00227.json";
		System.out.println("YBMAP url ：" + url);
		String rm = post.post(url, map);
		System.out.println("AppApi401ServiceImpl:开始异地贷款缴存使用证明打印接口：" + rm);
		/*Gson gson = new Gson();
		Map ybmapmsg = gson.fromJson(rm, new TypeToken<Map<String, String>>() {
		}.getType());*/

			int index = rm.lastIndexOf("}{");
			System.out.println("index ：" + index);
			for(int i = rm.length();i>-1;i--){
				if(index == i){
					rm = rm.substring(0,i+1);
					System.out.println(rm+"-----"+i);

				}
			}

		System.out.println("rm ：" + rm);
		//HashMap resultMap = new HashMap();
		/*resultMap.put("recode", ybmapmsg.get("recode"));
		resultMap.put("filepath", ybmapmsg.get("filepath"));
		resultMap.put("filename", ybmapmsg.get("filename"));*/

		/*resultMap.put("accnum", form.getAccnum());
		resultMap.put("certinum", form.getCertinum());
		resultMap.put("centername", form.getCentername());
		resultMap.put("userid", form.getUserid());
		resultMap.put("brccode", form.getBrccode());
		resultMap.put("channel", "");
		resultMap.put("projectname", form.getProjectname());
		resultMap.put("centerId", "00057400");
		resultMap.put("buzType", "6021");*/

		/*log.info("form.getAccnum()======"+form.getAccnum());
		log.info("form.getCertinum()======"+form.getCertinum());
		log.info("form.getCentername()======"+form.getCentername());
		log.info("form.getUserid()======"+form.getUserid());
		log.info("form.getBrccode()======"+form.getBrccode());
		log.info("form.getChannel()======"+form.getChannel());
		log.info("form.getProjectname()======"+form.getProjectname());
		log.info("form.getCenterId()======"+form.getCenterId());*/


		//System.out.println("异地贷款缴存使用证明打印===============" + resultMap.toString());
		JSONObject json = JSONObject.fromObject(rm);
		log.info(Constants.LOG_HEAD + "appApi00227 end.");
		log.info("gbk");
		log.info("json.toString()==========" + json.toString());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));

		return;
	}


}
