/**
 * 
 */
package com.yondervision.yfmap.controller;

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

import com.alibaba.fastjson.JSON;

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
public class AppApi00210Contorller {
	Logger log = Logger.getLogger("YFMAP");

	@RequestMapping("/appapi00217.{ext}")
	    public void appapi00217(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        form.setBusinName("生成工单");
	        log.info(Constants.LOG_HEAD+"api/appApi00217 begin.");
			HashMap<String, String> params = new HashMap<String, String>();
			String url = "http://172.16.10.96:8055/feedback/exposedInterface/productWorkSheet.htm";
			params.put("WorkSheetLogVo", "{\"appCode\": \"28e2767d507b430da5a3111b4d1f645f\"," +
								"\"feedbackMan\":\"" + form.getFullName() + "\"," +
								"\"feedbackPhone\":\"" + form.getMobileNumber() +  "\"," +
								"\"generallyOneName\":\"" + form.getAccname() +  "\"," +
								"\"generallyOnePhone\":\"" + form.getTel() +  "\"," +
								"\"interfaceName\": \"" + form.getBusinName() +  "\"," +
								"\"outputColumns\": [{" +
								"\"codeName\": \"" + form.getCsmc() +  "\"," +
								"\"desc\":\"" + form.getMs() +  "\"," +
								"\"type\":\"" + form.getCslbmc() + "\"" +
								"}]," +
								"\"requestId\": \"b0a2d1765e144dd78f1136e0a6606346\"," +
								"\"source\": \"creditSource\"" +
								"}");
			log.info( "form.getWorkSheetLogVo()==========="+ form.getWorkSheetLogVo());
			log.info("params================="+params);
			SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
			log.info("url"+url);
			String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			System.out.println("result==================="+result);
			JSONObject json = JSONObject.fromObject(result);
			response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
			return ;
			}
			
	@RequestMapping("/appapi00218.{ext}")
	    public void appapi00218(AppApi00210Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        form.setBusinName("家庭住房信息查询");
	        log.info(Constants.LOG_HEAD+"api/appApi00218 begin.");
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
			String url = sjpublicDatasUrl + "api/001008002016015001/dataSharing/Radc7kH7Xfd02xd0.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			//json数组形式传参
			List<FamilyMembersForm> list = new ArrayList<FamilyMembersForm>();
			/*System.out.println("JSON.toJSONString(list)old====="+JSON.toJSONString(list));
			System.out.println("form.getName()====="+form.getName());
			System.out.println("form.getName().length()====="+form.getName().length());*/
			FamilyMembersForm family = new FamilyMembersForm(form.getName(),form.getIdNo());
			list.add(family);
			/*System.out.println("family====="+family);
			System.out.println("form.getName1()====="+form.getName1());
			System.out.println("form.getName1().length()====="+form.getName1().length());*/

			if (form.getName1().length() != 0){
				FamilyMembersForm family1 = new FamilyMembersForm(form.getName1(),form.getIdNo1());
				list.add(family1);
			} else if (form.getName2().length() != 0){
				FamilyMembersForm family2 = new FamilyMembersForm(form.getName2(),form.getIdNo2());
				list.add(family2);
			} else if (form.getName3() .length() != 0) {
				FamilyMembersForm family3 = new FamilyMembersForm(form.getName3(), form.getIdNo3());
				list.add(family3);
			} else if (form.getName4() .length() != 0) {
				FamilyMembersForm family4 = new FamilyMembersForm(form.getName4(), form.getIdNo4());
				list.add(family4);
			} else if (form.getName5() .length() != 0) {
				FamilyMembersForm family5 = new FamilyMembersForm(form.getName5(), form.getIdNo5());
				list.add(family5);
			} else if (form.getName6() .length() != 0) {
				FamilyMembersForm family6 = new FamilyMembersForm(form.getName6(), form.getIdNo6());
				list.add(family6);
			} else if (form.getName7() .length() != 0) {
				FamilyMembersForm family7 = new FamilyMembersForm(form.getName7(), form.getIdNo7());
				list.add(family7);
			} else if (form.getName8() .length() != 0) {
				FamilyMembersForm family8 = new FamilyMembersForm(form.getName8(), form.getIdNo8());
				list.add(family8);
			}

			System.out.println("JSON.toJSONString(list)====="+JSON.toJSONString(list));
			String familyMembers = JSON.toJSONString(list);
			System.out.println("*******javaBean  to jsonString*******");
			//String familyMembers = "[{\"name\":\""+form.getAccname()+"\",\"idNo\":\""+form.getIdNo()+"\"}]";
			params.put("familyMembers", familyMembers);
			//log.info("familyMembers==========="+form.getAccname()+form.getIdNo());
			/*params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
					+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
					+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
					+"\",\"projectId\":\"\"}");*/
			SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
			System.out.println("params================"+params);
			String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			System.out.println("大数据平台返回result================"+result);
		
			log.info("result"+result.replace("\\r\\n", ""));
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台-家庭住房信息查询-耗时"+Time+"毫秒");
			JSONObject json = JSONObject.fromObject(result.replace("\\r\\n", "").replace(" ",""));
			json.remove("dataCount");
			json.remove("data");
			if(json.get("code").equals("00")){
				json.put("recode", "000000");
			}
			json.remove("code");

			String datas=json.get("datas").toString();
			JSONObject datasjson = JSONObject.fromObject(datas.replace("\\",""));
			String resultString=datasjson.get("result").toString();
			JSONObject resultjson = JSONObject.fromObject(resultString);
			//log.info("resultjson="+resultjson);
		//add by ll 新增房屋套数housesNum和合同数contractsNum 2019/8/21
		String houses= resultjson.get("houses").toString();
		//log.info("houses===================="+houses);
		houses = houses.replace(" ","").substring(1,houses.length()-1);// 去掉头尾[]
		int housesNum = 0;
		if (houses.length()!=0){
		int index = houses.lastIndexOf("},{");
		housesNum = 1;
		for(int i = houses.length();i>-1;i--){
			if(index == i){
				houses = houses.substring(0,i);
				//System.out.println(houses+"-----"+i);
				housesNum++;
				String houses1 = houses.substring(0, i);
				//System.out.println("housesNum===" + housesNum);
				index = houses1.lastIndexOf("},{");
				//System.out.println("index222=====" + index);
			}
		}
		}

		String contracts= resultjson.get("contracts").toString();
		log.info("contracts===================="+contracts);
		contracts = contracts.replace(" ","").substring(1,contracts.length()-1);// 去掉头尾[]
		int contractsNum = 0;
		if (contracts.length()!=0){
		int index1 = contracts.lastIndexOf("},{");
		contractsNum = 1;
		for(int i = contracts.length();i>-1;i--){
			if(index1 == i){
				contracts = contracts.substring(0,i);
				System.out.println(contracts+"-----"+i);
				contractsNum++;
				String contracts1 = contracts.substring(0, i);
				index1 = contracts1.lastIndexOf("},{");
			}
		}
		}
		json.put("housesNum",housesNum);
		json.put("contractsNum",contractsNum);


			json.remove("datas");
			json.put("datas",datasjson);
			log.info(Constants.LOG_HEAD+"appApi00218 end.");
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
			
			
	@RequestMapping("/appapi00219.{ext}")
	    public void appapi00219(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        form.setBusinName("宁波市房管中心商品房合同查询");
	        log.info(Constants.LOG_HEAD+"api/appApi00219 begin.");
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
			String url = sjpublicDatasUrl + "api/001008002016015001/dataSharing/9edc603D294Yb2Rd.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			params.put("ContractNo", form.getContractNo());
		
			params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
					+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
					+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
					+"\",\"projectId\":\"\"}");
		
			SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
			String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			System.out.println(result);
		
			log.info("宁波市房管中心商品房合同查询"+result.replace("\\\"", "\"").replace("[", "").replace("]", "").replace("\\r\\n", "").replace(" ",""));
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
			JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", "").replace("\\r\\n", "").replace(" ",""));
			json.remove("dataCount");
			json.remove("data");
			if(json.get("code").equals("00")){
				json.put("recode", "000000");
			}
			json.remove("code");
		
				log.info(Constants.LOG_HEAD+"appApi00219 end.");
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

	@RequestMapping("/appapi00220.{ext}")
	    public void appapi00220(AppApi50001Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
	        form.setBusinName("宁波市房管中心二手房合同查询");
	        log.info(Constants.LOG_HEAD+"api/appApi00220 begin.");
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
			String url = sjpublicDatasUrl + "api/001008002016015001/dataSharing/fh1Gb9CfM4Y4WYhe.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			params.put("ContractNo", form.getContractNo());
		
			params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
					+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
					+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
					+"\",\"projectId\":\"\"}");
		
			SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
			String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
			System.out.println(result);
		
			log.info("宁波市房管中心二手房合同查询"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
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
		
				log.info(Constants.LOG_HEAD+"appApi00220 end.");
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
