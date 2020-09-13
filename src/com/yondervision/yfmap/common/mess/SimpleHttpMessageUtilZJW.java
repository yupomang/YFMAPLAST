package com.yondervision.yfmap.common.mess;

import java.util.HashMap;
import java.util.Set;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 移动互联应用服务平台同第三方http通信实现类
 *
 */
public class SimpleHttpMessageUtilZJW implements SimpleHttpMessageI{
	

	public String sendGet(String url, String charset)throws Exception{
		return null;
	}
	
    /**
     * 向指定URL发送POST方法的请求
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
		mypost.setRequestHeader("Authorization", "Basic bmJ6andfZ2pqOm5iZ2pqMTIzNDU=");
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
			req = repMsg;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			mypost.releaseConnection();
		}
		return req;
	}


	public String synchroExportContentPostBySign(String url, String param, String centerid)throws Exception{
		return null;
	}
}
