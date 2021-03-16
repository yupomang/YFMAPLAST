package com.yondervision.yfmap.common.mess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.common.security.RSASignature;

import com.yondervision.yfmap.util.Base64Encoder;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.KeySignature;
import com.yondervision.yfmap.util.PropertiesReader;
/** 
* @ClassName: YbmapMessageUtil 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 5:06:56 PM   
* 
*/ 
public class YbmapMessageUtil {
	
	public void toYbmapServer(String urlString,String value,HttpServletRequest request, HttpServletResponse response) throws CenterRuntimeException{
		StringBuffer buffer = new StringBuffer();
		try {			
			URL url = new URL(urlString.trim());
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();			
			System.out.println("调用移动互联服务通信接口:"+urlString);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);			
			String connectTimeout = PropertiesReader.getProperty(
					"properties.properties", "YBMAPTmeout").trim();
			String readTimeout = PropertiesReader.getProperty(
					"properties.properties", "YBMAPTmeout").trim();
			httpUrlConn.setConnectTimeout(Integer.valueOf(connectTimeout));  
			httpUrlConn.setReadTimeout(Integer.valueOf(readTimeout));			 
			httpUrlConn.setRequestMethod("POST");
			
			System.out.println("调用移动互联服务通信参数:"+value);
			if (null != value) {
				OutputStream outputStream = httpUrlConn.getOutputStream();				 
				outputStream.write(value.getBytes("UTF-8"));
				outputStream.close();
			}			
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);			
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}			
			bufferedReader.close();
			inputStreamReader.close();			 
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			if(buffer.toString().indexOf("\"errcode\":0")<0){
				throw new CenterRuntimeException("400001","移动互联服务端异常返回信息不正确");
			}
			String encoding = null;
			if (com.yondervision.yfmap.util.CommonUtil.isEmpty(request.getCharacterEncoding())) {
				encoding = "UTF-8";
			}else {
				encoding = request.getCharacterEncoding();
			}
			response.getOutputStream().write(buffer.toString().getBytes(encoding));
		}catch (MalformedURLException me){
			me.printStackTrace();
			throw new CenterRuntimeException("400002","移动互联服务地址异常");
		}catch (ProtocolException pe){
			pe.printStackTrace();
			throw new CenterRuntimeException("400003","数据传输协议不匹配导致无法与移动互联服务进行通信");
		}catch (UnsupportedEncodingException ue){
			ue.printStackTrace();
			throw new CenterRuntimeException("400004","移动互联服务字符编码异常");
		}catch (IOException ie) {
			ie.printStackTrace();
			throw new CenterRuntimeException("400005","移动互联服务通信异常");
		}		
	}
	
	
	//Inner class for UTF-8 support   
	public static class UTF8PostMethod extends PostMethod{   
		public UTF8PostMethod(String url){   
			super(url);   
		}   
		@Override   
		public String getRequestCharSet() {   
		//return super.getRequestCharSet();   
			return "UTF-8";   
		}
	}
	
	public String post(String url, Map data) throws CenterRuntimeException{
//		HttpPost post = new HttpPost(url);		
//		StringEntity entity;
		String msg="";
		try {
			PostMethod mypost = new PostMethod(url);//post方法
			mypost.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");//添加请求头
			//参数设置
			Set<String> keySet = data.keySet();//返回map中所有key值的列表
			NameValuePair[] postData = new NameValuePair[keySet.size()];//设置长度为size的数组
			int index=0;
			
			StringBuffer headpara = new StringBuffer();
			StringBuffer headparaMD5 = new StringBuffer();
			int num=0;
			
			for(String key:keySet){	//取出所有key值
				postData[index++] = new NameValuePair(key,(CommonUtil.isEmpty((String)data.get(key))?"":(String)data.get(key)));//设置postData所有键值对
				if(num==0){
					headpara.append(key);//储存所有key值
					headparaMD5.append(key+"="+(CommonUtil.isEmpty((String)data.get(key))?"":(String)data.get(key)));//键值作为数组存进headparaMD5字符串里
				}else{
					headpara.append(","+key);
					headparaMD5.append("&"+key+"="+(CommonUtil.isEmpty((String)data.get(key))?"":(String)data.get(key)));
				}
				num++;
			}
			
			//把值的String headparaMD5进行RSA签名加密
			String paraMD5 = RSASignature.sign(EncryptionByMD5.getMD5(headparaMD5.toString().getBytes("UTF-8")), RSASignature.RSA_PRIVATE);//EncryptionByMD5.getMD5(headparaMD5.toString().getBytes("UTF-8"));
			
			mypost.setRequestHeader("headpara", headpara.toString());//把健String headpara设置进请求头
			mypost.setRequestHeader("headparaMD5", paraMD5);//把加密后的值String paraMD5设置进请求头
			System.out.println("#########发往YBMAPZH headpara :"+headpara.toString());
			System.out.println("#########发往YBMAPZH headparaMD5 :"+paraMD5);
			byte[] keySigndata = paraMD5.getBytes();//把加密后的值String paraMD5转变为数组
			String keysig = Base64Encoder.encode(KeySignature.keySign(keySigndata));//Base64加密
			mypost.setRequestHeader("KeySignature", keysig.replaceAll("\n", ""));//替换keysig里\n为空字符串
			
			
			mypost.addParameters(postData);//请求参数里添加所有键值对数组为参数
			HttpClient httpClient = new HttpClient();		
			
			String req = "";
			System.out.println(url);
			int re_code = httpClient.executeMethod(mypost);//执行post请求
			System.out.println("re_code"+re_code);
			if (re_code == 200) {
				msg = mypost.getResponseBodyAsString();
				System.out.println("************ 200 **********"+msg);
//				System.out.println("&&&&&&&&&&&&&&&&&");
//				System.out.println("response-encoding:"+mypost.getResponseCharSet());
//				System.out.println("repMsg:"+repMsg);
//				System.out.println("repMsg-byte"+mypost.getResponseBody());
//				System.out.println("repMsg---byte"+repMsg.getBytes(request.getCharacterEncoding()));
//				System.out.println("repMsg---string"+new String(repMsg.getBytes(request.getCharacterEncoding()),request.getCharacterEncoding()));
//				System.out.println("&&&&&&&&&&&&&&&&&");
				
			} else {
				throw new CenterRuntimeException("400005","与移动互联服务通讯异常"+re_code);
			}
			
			
			
			
			
			
			/*
			entity = new StringEntity(data, "UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response1;
			response1 = getClient().execute(post);
			if (response1.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.out.println();
			}
			String encoding = "UTF-8";
//			if (com.yondervision.yfmap.util.CommonUtil.isEmpty(request.getCharacterEncoding())) {
//				encoding = "UTF-8";
//			}else {
//				encoding = request.getCharacterEncoding();
//			}
			msg = EntityUtils.toString(response1.getEntity(),encoding);
			System.out.println("********  移动互联服务通信返回信息："+msg);
//			if(request.getServletPath().startsWith("/sendapi")){
//				System.out.println("********  response.getOutputStream().write(msg.getBytes(encoding)) ："+msg);
//				response.getOutputStream().write(msg.getBytes(encoding));
//			}else{
//				System.out.println("********  NO Print 'response.getOutputStream().write(msg.getBytes(encoding))' :"+msg);
//			}	*/		 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();			
			CenterRuntimeException cre = new CenterRuntimeException("400004","移动互联服务字符编码异常");
			throw cre;
		} catch (IOException e) {
			e.printStackTrace();
			CenterRuntimeException cre = new CenterRuntimeException("400006","与移动互联服务IO异常");
			throw cre;
		}catch (Exception e){
			e.printStackTrace();
			CenterRuntimeException cre = new CenterRuntimeException("400005","与移动互联服务通讯异常");
			throw cre;
		}finally{
//			post.releaseConnection();
			return msg;
		}
	}
	
	private static TrustManager truseAllManager = new X509TrustManager(){
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {              
        }  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
              
        }  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            return null;  
        }  
    }; 
//    public String post(String url, String data,HttpServletRequest request, HttpServletResponse response){
//		HttpPost post = new HttpPost(url);		
//		StringEntity entity;
//		String msg="";
//		
//		try {			
//			entity = new StringEntity(data, "utf-8");
//			post.setEntity(entity);
//			HttpResponse response1;
//			response1 = new YbmapMessageUtil().getClient().execute(post);
//			if (response1.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//				System.out.println();
//			}
//			String encoding = null;
//			if (com.yondervision.yfmap.util.CommonUtil.isEmpty(request.getCharacterEncoding())) {
//				encoding = "UTF-8";
//			}else {
//				encoding = request.getCharacterEncoding();
//			}
//			msg = EntityUtils.toString(response1.getEntity(),encoding);
//			if(request.getServletPath().startsWith("/weixinapi")){
//				System.out.println("********  response.getOutputStream().write(msg.getBytes(encoding)) ："+msg);
//				//response.getOutputStream().write(msg.getBytes(encoding));
//			}else{
//				System.out.println("********  NO Print 'response.getOutputStream().write(msg.getBytes(encoding))' :"+msg);
//			}
//			return msg;
//		} catch (Exception e){
//			e.printStackTrace();
//		}finally{
//			post.releaseConnection();
//			return msg;
//		}
//	}
	
//	private HttpClient getClient() throws NoSuchAlgorithmException, KeyManagementException{
//		HttpClient client = new DefaultHttpClient();		
//		SSLContext sslcontext = SSLContext.getInstance("TLS");
//		sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
//		SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
//		sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//		Scheme https = new Scheme("https", sf, 443);
//		client.getConnectionManager().getSchemeRegistry().register(https);		
//		return client;
//		
//	}
	
	public static void main(String[] args){
		System.out.println("errorcode:0alsdfjasldfjlaskdfjlsakdfj".indexOf("errorcode:0"));	
		YbmapMessageUtil ybmap = new YbmapMessageUtil();
//		ModelMap modelMap = new ModelMap();
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
		Map data = new HashMap();
		data.put("centerId", "00075500");
		data.put("accnum", "中国");
		//"{\"accnum\":\"000010010000001\",\"title\":\"公积金短消息\",\"detail\":\"公积金短消息\",\"theme\":\"1\",\"chanel\":\"10\",\"freeuse1\":\"1\",\"freeuse2\":\"1\",\"freeuse3\":\"1\",\"centerId\":\"00075500\",\"centreName\":\"深圳公积金中心\",\"username\":\"\",\"userId\":\"\",\"transCode\":\"3001\",\"sendDate\":\"20150211\",\"sendTime\":\"9:24\",\"sendSeqno\":\"1234567\",\"key\":\"1\",\"longinip\":\"127.0.0.1\",\"buzType\":\"3001\"}"
		ybmap.post("http://127.0.0.1:8080/YBMAP/yfmapapi3001.json", data);
	}
}
