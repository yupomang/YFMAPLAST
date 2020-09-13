package com.yondervision.yfmap.common.mess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.yondervision.yfmap.util.CommonUtil;


public class HTTPSendMessage {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 通信转发
	 */
	@SuppressWarnings("unchecked")
	public String send(HttpServletRequest request, HttpServletResponse response, String url,Map<String,String> mapdata)throws Exception{
//		Map propsMap = request.getParameterMap();
		
		//TODO
		System.out.println("****************requestUrl********start**************");
		System.out.println("requestUrl:"+url);
		System.out.println("****************requestUrl********end**************");
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//参数设置
		Set<String> keySet = mapdata.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,mapdata.get(key));
			System.out.println(key+"==="+mapdata.get(key));
		}
		mypost.addParameters(postData);
				
		HttpClient httpClient = new HttpClient();		
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			log.info(re_code);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
				log.info(repMsg);
			} else {
				throw new RuntimeException("请求错误，http返回码为："+re_code);
			}
			
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
//			response.getOutputStream().write(repMsg.getBytes(encoding));
			req = repMsg;
			System.out.println(req);
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			mypost.releaseConnection();
		}
		return req;
	}
	@SuppressWarnings("unchecked")
	public String sendget(HttpServletRequest request, HttpServletResponse response, String url)throws Exception{
//		Map propsMap = request.getParameterMap();
		
		//TODO
		log.info("****************requestUrl********start**************");
		log.info("requestUrl:"+url);
		log.info("****************requestUrl********end**************");
		GetMethod myget = new GetMethod(url);
		HttpClient httpClient = new HttpClient();		
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
		StringBuffer responsestr = new StringBuffer();
		try {
			int re_code = httpClient.executeMethod(myget);
			log.info("re_code===="+re_code);
			if (re_code == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(myget.getResponseBodyAsStream(), "UTF-8")); 
                String line; 
                while ((line = reader.readLine()) != null) { 
                	responsestr.append(line);
                } 
                reader.close();
				log.info(responsestr.toString());
				
			} else {
				throw new RuntimeException("请求错误，http返回码为："+re_code);
			}
			String encoding = null;
			if (CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
		} catch (Exception e) {
			log.info("e.getMessage()===="+e.getMessage());
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			myget.releaseConnection();
		}
		return responsestr.toString();
	}
}
