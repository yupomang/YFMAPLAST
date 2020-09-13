package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.PublicDatasTokenUtil;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * 接下来我们实现Callable接口，然后实现任务逻辑。 
 * by hadoop on 2016/11/2.
 */
public class Taskappapi10001 implements Callable<Result> {
	Logger log = Logger.getLogger("YFMAP");
	String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase("00057400");
	String requestSecret1 =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase("00057400");
	private String name;
	private AppApi50001Form form;
	private ModelMap modelMap;


	public Taskappapi10001(String name, AppApi50001Form form, ModelMap modelMap) {
		this.name = name;
        this.form = form;
        this.modelMap = modelMap;

	}

	public Result call() throws Exception {
		String value = null;
		if (this.name.endsWith("1") ) {
			long starTime=System.currentTimeMillis();
			value =getBudong(form,modelMap);
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("线程1请求大数据平台耗时"+Time+"毫秒");
		}
		if (this.name.endsWith("2") ) {
			long starTime=System.currentTimeMillis();
			value =getQisui(form,modelMap);
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("线程2请求大数据平台耗时"+Time+"毫秒");
		}
		return new Result(this.name, value,this.form,this.modelMap );
	}

	public String getBudong(AppApi50001Form form,  ModelMap modelMap) throws Exception {
		form.setBusinName("不动产房屋登记信息");
		log.info(Constants.LOG_HEAD + "getBudongappapi00113  begin.");
		//连接浙江省公共数据平台获取数据
		log.info("requestSecret1========"+requestSecret1);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
//		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + requestSecret1.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		HashMap<String, String> params = new HashMap<String, String>();
//		String url = publicDatasUrl + "api/001008002016013/dataSharing/0pc9cFD2PctMdGa1.htm";
		//王文举 修改成新的接口地址 2020-8-18
		String url = publicDatasUrl + "api/001008002016013/dataSharing/ceRVQJeB1ecrZme5.htm";
		log.info("budongchan jiekou new url="+url);
		params.put("appKey", appKey);
		params.put("sign", sign);
		params.put("requestTime", requestTime);

		params.put("clientusercid", form.getClientusercid());
		params.put("clientusername", form.getClientusername());
		params.put("cxfw", form.getCxfw());
		params.put("qlrzjh", form.getQlrzjh());
		params.put("computername", form.getComputername());
		params.put("qlrmc", form.getQlrmc());
		params.put("zl", form.getZl());

		params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
				+ "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
				+ "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
				+ "\",\"projectId\":\"\"}");

		log.info("budongchan jiekou params =" + params);
		SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

		log.info("不动产房屋登记信息结果result=" + result);
		log.info("不动产房屋登记信息结果result=" + result.replace("\\", ""));
		JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));

		if (json.get("datas") != null && (!json.get("datas").toString().equals("null"))) {
			String datas = json.get("datas").toString();
			log.info("不动产房屋登记信息结果datas=" + datas);

			JSONObject datasjson = JSONObject.fromObject(datas.replace("'", "\""));

			String date1 = datasjson.get("date").toString();
			log.info("不动产房屋登记信息结果date1=" + date1);

			JSONArray ary = (JSONArray) datasjson.get("date");
			log.info("ary.size()=" + ary.size());
			if (ary.size() > 0) {
				String date2 = ary.get(0).toString();

				JSONObject date2json = JSONObject.fromObject(date2);
				String zlmodified = date2json.getString("zl").replace(",", " ").replace("，", " ");
				date2json.remove("zl");//应贷款要求，将坐落中的逗号换成空格
				date2json.put("zl", zlmodified);

				json.remove("datas");
				json.put("datas", date2json);

			}

		}
		return json.toString();
	}

	public String getQisui(AppApi50001Form form,  ModelMap modelMap) throws Exception{
		form.setBusinName("契税完税信息接口");
		log.info(Constants.LOG_HEAD+"api/appApi00143getQisui begin.");
		//连接宁波市数据接口共享平台
		//String requestSecret =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
		log.info("requestSecret2========"+requestSecret1);
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
		String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
		String sign = EncryptionByMD5.getMD5((appKey + requestSecret1.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = sjpublicDatasUrl + "api/001008002016288/dataSharing/dc7s3B05Xdhx8580.htm";
		params.put("appKey", appKey);
		params.put("sign", sign);
		params.put("requestTime", requestTime);
		params.put("nsrqz", form.getNsrqz());
		params.put("nsrqq", form.getNsrqq());
		params.put("nsrsbh", form.getNsrsbh());
		params.put("qshm", form.getQshm());
		params.put("nsrmc", form.getNsrmc());
		params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
				+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
				+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
				+"\",\"projectId\":\"\"}");

		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		String json=result .replace("\\", "");
		log.info("契税完税信息接口url="+url);
		log.info("契税完税信息接口"+result);
		log.info(Constants.LOG_HEAD+"appApi00143 end.");
		return json;
	}


	public static class Taskappapi10002 implements Callable<Result> {
		Logger log = Logger.getLogger("YFMAP");
		private String name;
		private AppApi50001Form form;
		private ModelMap modelMap;


		public Taskappapi10002(String name, AppApi50001Form form, ModelMap modelMap) {
			this.name = name;
			this.form = form;
			this.modelMap = modelMap;

		}

		public Result call() throws Exception {
			String value = null;
			if (this.name.endsWith("1")) {
				long starTime = System.currentTimeMillis();
				value = getGeren(form, modelMap);
				long endTime = System.currentTimeMillis();
				long Time = endTime - starTime;
				System.out.println("线程1请求大数据平台耗时" + Time + "毫秒");
			}
			if (this.name.endsWith("2")) {
				long starTime = System.currentTimeMillis();
				value = getShuangfang(form, modelMap);
				long endTime = System.currentTimeMillis();
				long Time = endTime - starTime;
				System.out.println("线程2请求大数据平台耗时" + Time + "毫秒");
			}
			return new Result(this.name, value, this.form, this.modelMap);
		}

		public String getGeren(AppApi50001Form form, ModelMap modelMap) throws Exception {
			form.setBusinName("民政部_婚姻登记信息核验(个人)");
			log.info(Constants.LOG_HEAD+"api/appApi00206 begin.");
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
			String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/nke28OEX80C290S6.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			params.put("cert_num_man", form.getCert_num_man());
			params.put("name_man", form.getName_man());
			System.out.println("cert_num_man========"+form.getCert_num_man());
			System.out.println("name_man========== "+form.getName_man());

			//新方法
			String xxx=url+"?requestTime="+requestTime+"&appKey="+appKey+"&sign="+sign
					+"&cert_num_man="+form.getCert_num_man()+"&name_man="+ URLEncoder.encode(form.getName_man(),"utf-8");
			System.out.println("url="+xxx);

			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String result = msm.sendGet(xxx, "UTF-8");
			log.info("新结果"+result);
			log.info("民政部_婚姻登记信息核验(个人)"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
			System.out.println("Dashuju cost "+Time+"milliseconds");
			JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
			json.remove("dataCount");
			json.remove("data");
			if(json.get("code").equals("00")){
				json.put("recode", "000000");
			}
			json.remove("code");

			log.info(Constants.LOG_HEAD+"appApi00206 end.");

			return json.toString();
		}

		public String getShuangfang(AppApi50001Form form, ModelMap modelMap) throws Exception {
			form.setBusinName("民政部_婚姻登记信息核验(双方)");
			log.info(Constants.LOG_HEAD+"api/appApi00207 begin.");
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
			String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/74Pe8a8I98EfaFe9.htm";
			params.put("appKey", appKey);
			params.put("sign", sign);
			params.put("requestTime", requestTime);
			params.put("cert_num_man", form.getCert_num_man());
			params.put("name_man", form.getName_man());
			params.put("cert_num_woman", form.getCert_num_woman());
			params.put("name_woman", form.getName_woman());

			//新方法
			String xxx=url+"?requestTime="+requestTime+"&appKey="+appKey+"&sign="+sign
					+"&cert_num_man="+form.getCert_num_man()+"&name_man="+URLEncoder.encode(form.getName_man(),"utf-8")
					+"&cert_num_woman="+form.getCert_num_woman()+"&name_woman="+URLEncoder.encode(form.getName_woman(),"utf-8");

			log.info("url"+xxx);
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			String result = msm.sendGet(xxx, "UTF-8");
			log.info("新结果"+result);

			log.info("民政部_婚姻登记信息核验(双方)"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
			long endTime=System.currentTimeMillis();
			long Time=endTime-starTime;
			System.out.println("请求大数据平台耗时"+Time+"毫秒");
			System.out.println("Dashuju cost "+Time+"milliseconds");
			JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
			json.remove("dataCount");
			json.remove("data");
			if(json.get("code").equals("00")){
				json.put("recode", "000000");
			}
			json.remove("code");

			log.info(Constants.LOG_HEAD+"appApi00207 end.");
			log.info("form.getChannel()="+form.getChannel());


			return json.toString();
		}
	}
}
