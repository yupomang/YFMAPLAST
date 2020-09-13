package com.yondervision.yfmap.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.PublicDatasTokenUtil;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;

/**
 * 接下来我们实现Callable接口，然后实现任务逻辑。 
 * by hadoop on 2016/11/2.
 */
public class Task implements Callable<Result> {
	Logger log = Logger.getLogger("YFMAP");
	String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase("00057400");
	String requestSecret1 =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase("00057400");
	private String name;
	private AppApi50001Form form;
	private ModelMap modelMap;

	public Task(String name,AppApi50001Form form,ModelMap modelMap) {
		this.name = name;
        this.form = form;
        this.modelMap = modelMap;
	}

	public Result call() throws Exception {
		String value = null;
		log.info("zfqlrzjh2=" + form.getQlrzjh());
		if (this.name.endsWith("1")) {
			long starTime=System.currentTimeMillis();
			value = getHuji(form,modelMap);
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("线程1请求大数据平台耗时"+Time+"毫秒");
		}
		if (this.name.endsWith("2")) {
			long starTime=System.currentTimeMillis();
			value =getHunyin(form,modelMap);
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("线程2请求大数据平台耗时"+Time+"毫秒");
		}
		if (this.name.endsWith("3") ) {
			long starTime=System.currentTimeMillis();
			value =getZhufang(form,modelMap);
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("线程3请求大数据平台耗时"+Time+"毫秒");
		}
		return new Result(this.name, value,this.form,this.modelMap );
	}

	public String  getHuji(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		form.setBusinName("人口信息查询");
		log.info(Constants.LOG_HEAD+"getHuji begin.");

		//连接浙江省公共数据平台获取数据
		log.info("requestSecret1========"+requestSecret);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
				"appKey").trim().toLowerCase();
		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
				"publicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

		HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/popInfo.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getBodyCardNumber());
        params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()
				+"\",\"accesscardId\":\""+form.getBodyCardNumber()+"\",\"materialName\":\""+form.getMaterialName()
				+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getBodyCardNumber()
				+"\",\"projectId\":\"\"}");
        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");

		log.info("人口信息结果"+result);
		JSONObject json = JSONObject.fromObject(result);
		if("00".equals(String.valueOf(json.get("code")))){
			json.put("recode", "000000");
		}else{
			json.put("recode",json.get("code"));
		}
		log.info(Constants.LOG_HEAD+"getHuji end.");
		return json.toString();
	}

	public String getHunyin(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		form.setBusinName("婚姻登记信息查询");
		log.info(Constants.LOG_HEAD+"getHunyin begin.");
		//连接浙江省公共数据平台获取数据
		log.info("requestSecret2========"+requestSecret);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
				"appKey").trim().toLowerCase();
		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
				"publicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

		HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/marryInfo.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getBodyCardNumber());
        params.put("sex", form.getSex());
        params.put("name", form.getFullName());
        params.put("birthday", form.getBirthday());
        params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()
				+"\",\"accesscardId\":\""+form.getBodyCardNumber()+"\",\"materialName\":\""+form.getMaterialName()
				+"\",\"sponsorName\":\""+form.getFullName()+"\",\"sponsorCode\":\""+form.getBodyCardNumber()
				+"\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		JSONObject json = JSONObject.fromObject(result);
		if("00".equals(String.valueOf(json.get("code")))){
			json.put("recode", "000000");
			if (!"0".equals(String.valueOf(json.get("dataCount")))) {
				String DashujuJson = json.toString();
				log.info("大数据平台直接返回的DashujuJson=" + DashujuJson);
				JSONArray ary = (JSONArray) json.get("datas");
				log.info("datas婚姻记录数组ary=" + ary);
				String lastData = "1000-01-01";
				String tempDate = "";
				JSONArray ary1 = new JSONArray();
				for (int j = 0; j < ary.size(); j++) {//ary 循环
					JSONObject obj = ary.getJSONObject(j);
					tempDate = (String) obj.get("registrationDate");//获取时间字段 lastDate与tempDate比较，把大的赋值给lastDate
					if (Long.valueOf(tempDate.replaceAll("[-]", "")) > Long
							.valueOf(lastData.replaceAll("[-]", ""))) {
						lastData = tempDate;
						ary1.clear();
						ary1.add(obj);
					}
				}
				log.info("最新婚姻信息记录的登记日期=" + lastData);
				log.info("最新婚姻信息记录的ary1=" + ary1);
				log.info("json.getdatas=" + json.get("datas"));
				json.remove("data");//把原来的datas删掉
				json.put("datas", ary1);//把新的datas放进去
			}
		}else{
			json.put("recode",json.get("code"));
		}
		json.remove("code");//把原来的code删掉
		json.remove("dataCount");//把原来的dataCount删掉
		log.info("婚姻查询结果="+json.toString());

		log.info(Constants.LOG_HEAD+"getHunyin end.");
		return json.toString();
	}


	public String getZhufang(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		form.setBusinName("有无住房查询");
		log.info(Constants.LOG_HEAD+"getZhufang begin.");
		log.info("zfqlrzjh3 =" + form.getQlrzjh());
		//连接宁波市数据接口共享平台
		log.info("requestSecret3========"+requestSecret1);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
		String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey + requestSecret1.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		HashMap<String, String> params = new HashMap<String, String>();
//		String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/A6cNbfw1I308dzt3.htm";
		//新有无房屋接口地址 王文举 修改于2020-8-18
		String url = sjpublicDatasUrl + "api/001008002016013/dataSharing/fL2vfl4I42q4aM08.htm";
		log.info("zufang jiekou new url="+url);
		params.put("appKey", appKey);
		params.put("sign", sign);
		params.put("requestTime", requestTime);
		params.put("clientusercid",form.getClientusercid());
		params.put("qlrzjh",form.getQlrzjh());
		params.put("computername",form.getComputername());
		params.put("cxfw",form.getCxfw());
		params.put("clientusername",form.getClientusername());
		params.put("qlrmc",form.getQlrmc());

		params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
				+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
				+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
				+"\",\"projectId\":\"\"}");
		log.info("zufang params ========"+params);
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		long starTime=System.currentTimeMillis();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		long endTime=System.currentTimeMillis();
		long Time=endTime-starTime;
		System.out.println("getZhufang请求大数据平台耗时"+Time+"毫秒");
		log.info("有无住房查询结果="+result);
		log.info("有无住房查询结果="+result .replace("\\", ""));
		String json=result .replace("\\", "");
		return json;
	}

}
