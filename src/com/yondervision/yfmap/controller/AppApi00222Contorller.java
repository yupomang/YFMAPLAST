/**
 * 
 */
package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.form.AppApi00222Form;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;

/**
 * @author luolin
 *
 */
@Controller
public class AppApi00222Contorller {
	Logger log = Logger.getLogger("YFMAP");
	@RequestMapping("/appapi00222.{ext}")
	public void appapi00222(AppApi00222Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("评价器信息确认接口");
		log.info(Constants.LOG_HEAD+"api/appApi00222 begin.");

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://10.33.11.41:8090/amq-tomcat7/servlet/sendMessage";
/*{"app":{"address":"","cardnum":"330204196402062053","cardtype":"03","objname":"施跃明","people":"","postcode":"","userType":"0"},"matlist":[{"matname":"","recmethod":"","sl":"","state":"","xh":""}],"person":{"concardnum":"","concardtype":"","phone":"","telephone":"","xm":""},"sjinfo":{"info_unid":"PJXT2019092900000382","inprojid":"330201190929512759161","original":"5562ffa9-6016-4b2d-99f6-b0512f9fee05","proname":"","qlsxbm":"","receivedeptid":"2002082700000002","receivedeptname":"宁波市城镇医疗保险管理中心","receiveusername":"蒋留峰","reiveuserid":"h03162","servcieid":"0004","servicename":"基本医疗保险参保人员转外就医备案"}}
 */
		/*String check_info = "{\"app\":{\"address\":\""+form.getAddress()+"\",\"cardnum\":\""+form.getCardnum()+"\","+
				"\"cardtype\":\""+form.getCardtype()+"\",\"objname\":\""+form.getObjname()+"\",\"people\":\""+form.getPeople()+"\",\"postcode\":\""+form.getPostcode()+"\",\"userType\":\""+form.getUserType()+"\"}," +
				"\"matlist\":[{\"matname\":\"\",\"recmethod\":\"\",\"sl\":\"\",\"state\":\"\",\"xh\":\"\"}]," +
				"\"person\":{\"concardnum\":\""+form.getConcardnum()+"\",\"concardtype\":\""+form.getConcardtype()+"\",\"phone\":\""+form.getPhone()+"\",\"telephone\":\""+form.getTelephone()+"\",\"xm\":\""+form.getXm()+"\"}," +
				"\"sjinfo\":{\"info_unid\":\""+form.getInfo_unid()+"\",\"inprojid\":\""+form.getInprojid()+"\"," +
				"\"original\":\""+form.getOriginal()+"\",\"proname\":\""+form.getProname()+"\",\"qlsxbm\":\""+form.getQlsxbm()+"\"," +
				"\"receivedeptid\":\""+form.getReceivedeptid()+"\",\"receivedeptname\":\""+form.getReceivedeptname()+"\"," +
				"\"receiveusername\":\""+form.getReceiveusername()+"\",\"reiveuserid\":\""+form.getReiveuserid()+"\",\"servcieid\":\""+form.getServcieid()+"\"," +
				"\"servicename\":\""+form.getServicename()+"\"}}";*/
		log.info("check_info=========="+form.getCheck_info());
		log.info("istype=========="+form.getIstype());
		log.info("sender_userid=========="+form.getSender_userid());
		params.put("check_info", form.getCheck_info());
		params.put("istype", form.getIstype());
		params.put("sender_userid", form.getSender_userid());
		log.info("params=========="+params);
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		System.out.println(result);

		log.info("评价器信息确认接口"+result);
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);
	/*	json.remove("dataCount");
		json.remove("data");
		if(json.get("code").equals("00")){
			json.put("recode", "000000");
		}
		json.remove("code");*/

		log.info(Constants.LOG_HEAD+"appApi00222 end.");
		log.info("form.getChannel()="+form.getChannel());

		log.info("gbk");
		log.info("json.toString()=========="+json);
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}


	@RequestMapping("/appapi00223.{ext}")
	public void appapi00223(AppApi00222Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("获取自建系统人员信息接口");
		log.info(Constants.LOG_HEAD+"api/appApi00223 begin.");

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();

		String url = "http://10.33.11.41:8090/amq-tomcat7/servlet/sendMessage";

		params.put("user_unid", form.getUser_unid());
		params.put("user_name", form.getUser_name());
		params.put("user_dept", form.getUser_dept());
		params.put("user_deptId", form.getUser_deptId());
		params.put("systemName", form.getSystemName());
		params.put("sex", form.getSex());
		params.put("work_code", form.getWork_code());
		params.put("work_post", form.getWork_post());

		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		System.out.println(result);

		log.info("获取自建系统人员信息接口"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
		/*json.remove("dataCount");
		json.remove("data");
		if(json.get("code").equals("00")){
			json.put("recode", "000000");
		}
		json.remove("code");*/

		log.info(Constants.LOG_HEAD+"appApi00223 end.");
		log.info("form.getChannel()="+form.getChannel());

		log.info("gbk");
		log.info("json.toString()=========="+json);
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}


	@RequestMapping("/appapi00224.{ext}")
	public void appapi00224(AppApi00222Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("评价器用户登录功能");
		log.info(Constants.LOG_HEAD+"api/appApi00224 begin.");

		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();

		String url = "http://10.33.11.41:8090/amq-tomcat7/servlet/sendMessage";
		/*评价器用户登录功能
		 *user_info 人员信息
		 *  	type  1
		 *  	serial 设备ID
		 *  	sender_userid 登录人ID
		 * istype 人员登录类型为1
		 * */
		String user_info= "{\"type\":\"1\",\"serial\":\"\",\"sender_userid\":\""+form.getSender_userid()+"\"}";
		params.put("user_info", "{\"type\":\"1\",\"serial\":\"\",\"sender_userid\":\""+form.getSender_userid()+"\"}");
		params.put("istype", "1");

		log.info("user_info"+user_info);
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		System.out.println(result);

		log.info("评价器用户登录功能"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
		//json.remove("dataCount");
		//json.remove("data");
		//if(json.get("code").equals("00")){
		//	json.put("recode", "000000");
		//}
		//json.remove("code");
		System.out.println("json:::"+json);
		log.info(Constants.LOG_HEAD+"appApi00224 end.");
		log.info("form.getChannel()="+form.getChannel());

		log.info("gbk");
		log.info("json.toString()=========="+json);
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}
}
