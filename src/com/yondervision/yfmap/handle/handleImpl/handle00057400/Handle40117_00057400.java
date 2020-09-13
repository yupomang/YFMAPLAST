package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.HTTPSendMessage;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle40117_00057400 implements CtrlHandleInter1 {
	static Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setFullName(aes.decrypt(form.getFullName()));
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/GJJQuery";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142502");
		params.put("task", "");
		params.put("transtype_eb", form.getFreeuse1());
		params.put("certinum", form.getIdcardNumber());
		params.put("accname", form.getFullName());
		params.put("linkphone", form.getMobileNumber());
		params.put("cagorcode_eb", form.getFreeuse2());
//		log.debug("params:"+params.toString()+"===="+form.getIdcardNumber()+"==="+form.getFullName());
		String json = "tranCode=142502&task=&transtype_eb="+form.getFreeuse1()+"&certinum="+form.getIdcardNumber()+"&accname="+URLEncoder.encode(form.getFullName())
				+"&cagorcode_eb="+form.getFreeuse2()+"&linkphone="+form.getMobileNumber();
//		url = "http://172.16.10.91:7001/GJJQuery";
		log.debug("url======"+url);
		log.debug("json======"+json);
		Map<String,String> prop=new HashMap<String,String>();
		prop.put("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
		String result=send(url,"GET",json,prop);
		JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("msg").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.getString("msg"));
		 }else{
			 modelMap.put("recode", "000000");
			 modelMap.put("msg", "成功");
		 }
		
		return modelMap;
	}
	public static String send(String urlString,String method,String param,Map<String,String> prop){
		if (method.equalsIgnoreCase("GET") && param != null) {
            urlString += "?" + param;
        }
		log.debug("urlString======"+urlString);
		URL url=null;
		HttpURLConnection urlConnection=null;
		try {
			url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod(method);
	        urlConnection.setDoOutput(true);
	        urlConnection.setDoInput(true);
	        urlConnection.setUseCaches(false);
	        if( prop!=null ){
	        	for(String key : prop.keySet()){
	        		urlConnection.setRequestProperty(key, prop.get(key));
	        	}
	        }
	        if (method.equalsIgnoreCase("POST") && param != null) {
	            urlConnection.getOutputStream().write(param.getBytes());
	            urlConnection.getOutputStream().flush();
	            urlConnection.getOutputStream().close();
	        }
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        StringBuffer temp = new StringBuffer();
	        String line = bufferedReader.readLine();
	        while (line != null) {  
	            temp.append(line).append("\r\n");
	            line = bufferedReader.readLine();
	        }
	        log.debug(temp);
	        bufferedReader.close();
	        String ecod = urlConnection.getContentEncoding();
	        if(ecod==null){
	        	ecod="gbk";
	        }
	        log.debug(new String(temp.toString().getBytes(),ecod));
	        return new String(temp.toString().getBytes(),ecod);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.debug("e.getMessage()==="+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("e.getMessage()==="+e.getMessage());
		} finally{
			if(urlConnection!=null){
				urlConnection.disconnect();
			}
		}
		return null;
	}
}
