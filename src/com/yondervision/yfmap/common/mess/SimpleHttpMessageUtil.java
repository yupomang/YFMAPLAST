package com.yondervision.yfmap.common.mess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.security.Base64;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.common.security.RSAEncrypt;
import com.yondervision.yfmap.util.Base64Encoder;
import com.yondervision.yfmap.util.KeySignature;
import com.yondervision.yfmap.util.PropertiesReader;


/**
 * 移动互联应用服务平台同第三方http通信实现类
 *
 */
public class SimpleHttpMessageUtil implements SimpleHttpMessageI{
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	public String sendGet(String url, String charset)throws Exception{
		Logger log = Logger.getLogger("YFMAP");
		
		GetMethod myget = new GetMethod(url);
		myget.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		StringBuffer response = new StringBuffer();
		try {
			int re_code = httpClient.executeMethod(myget);
			if (re_code == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(myget.getResponseBodyAsStream(), charset)); 
                String line; 
                while ((line = reader.readLine()) != null) { 
                	response.append(line);
                } 
                reader.close();
//				log.info(LOG.REV_INFO.getLogText(response.toString()));
				
			} else {
				throw new Exception("http错误码" + re_code);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			myget.releaseConnection();
		}
		return response.toString();
	}
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	@SuppressWarnings("unchecked")
	public String sendPost(String url, HashMap propsMap, String charset)throws Exception{
				
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		//mypost.setRequestHeader("headpara", request.getHeader("headpara"));
		//mypost.setRequestHeader("headparaMD5", request.getHeader("headparaMD5"));
		//byte[] data = request.getHeader("headparaMD5").getBytes();		
		//String keysig = Base64Encoder.encode(KeySignature.keySign(data));	
		//mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,(String)propsMap.get(key));
		}
		mypost.addParameters(postData);
	
//		if(request.getAttribute("DL_LOG_SEQ_NO")!=null){
//			NameValuePair[] postData1= new NameValuePair[1];
//			postData1[0] = new NameValuePair("logid",String.valueOf(request.getAttribute("DL_LOG_SEQ_NO")));
//			mypost.addParameters(postData1);
//		}
		

		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
			} else {
				throw new Exception("http错误码" + re_code);
			}
			
//			response.getOutputStream().write(repMsg.getBytes(encoding));
			req = repMsg;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			mypost.releaseConnection();
		}
		return req;
	}

	
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	@SuppressWarnings("unchecked")
	public String sendPost2(String url, HashMap propsMap, String charset)throws Exception{
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/json; charset=UTF-8");
		//参数设置
		Set<String> keySet = propsMap.keySet();
		NameValuePair[] postData = new NameValuePair[keySet.size()];
		int index=0;
		for(String key:keySet){
			postData[index++] = new NameValuePair(key,(String)propsMap.get(key));
		}
		mypost.addParameters(postData);
		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time").trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time").trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
			} else {
				throw new Exception("http错误码" + re_code);
			}
//			response.getOutputStream().write(repMsg.getBytes(encoding));
			req = repMsg;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			mypost.releaseConnection();
		}
		return req;
	}
	
	/**
     * 同步内容导出到第三方平台，发送post方法的请求,有签名认证
     * 
     * @param url
     *            发送请求的URL
     * @param params
     *            参数        
     * @param charset
     *            编码格式        
     * @return URL 所代表远程资源的响应结果
     */
	public String synchroExportContentPostBySign(String url, String param, String centerid)throws Exception{
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		PostMethod mypost = new PostMethod(url);
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		//头部请求信息
		mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		mypost.setRequestHeader("headpara", "paramdata");
		String headparaMD5 = "";
		try {
			headparaMD5 = Base64.encode(
//				rsaEncrypt.encryptPrivateSignature(rsaEncrypt.loadPrivateKeyReturn(RSAEncrypt.DEFAULT_PRIVATE_KEY),
							EncryptionByMD5.getMD5(("paramdata="+ param).getBytes("UTF-8")).getBytes()).replaceAll("\n", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mypost.setRequestHeader("headparaMD5", headparaMD5);
		byte[] data = headparaMD5.getBytes();
		String keysig = Base64Encoder.encode(KeySignature.keySign(data));
		mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));
		
		
		//参数设置
		mypost.addParameter("paramdata", param);

		HttpClient httpClient = new HttpClient();		
		String connectTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_connection_time_"+centerid).trim();
		String readTimeout = PropertiesReader.getProperty(
				Constants.PROPERTIES_FILE_NAME, "http_read_time_"+centerid).trim();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(Integer.valueOf(connectTimeout));
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(Integer.valueOf(readTimeout));
		String req = "";
		try {
			int re_code = httpClient.executeMethod(mypost);
			String repMsg = null;
			if (re_code == 200) {
				repMsg = mypost.getResponseBodyAsString();
			} else {
				throw new Exception("http错误码" + re_code);
			}
			
			req = repMsg;
		} catch (Exception e) {
			throw e;
		} finally{
			mypost.releaseConnection();
		}
		return req;
	}
}
