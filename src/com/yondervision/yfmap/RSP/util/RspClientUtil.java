package com.yondervision.yfmap.RSP.util;
/**
 * 向rsp服务器发送服务
 * nbp专用
 * */
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.ydyd.pool.DataPool;
import com.yondervision.yfmap.RSP.bean.IRspBean;
import com.yondervision.yfmap.RSP.bean.Svr802bean;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class RspClientUtil {

	public static RspResult send(IRspBean bean) throws Exception {
		long start=System.currentTimeMillis();
		//NbpLog.debugError("ylcs"+Thread.currentThread().getId(),Thread.currentThread().getName());
		//NbpLog.debugError("ylcs"+Thread.currentThread().getId(),"=====send rsp ======="+bean.getTranCode()+"======beg");
		String strurl = PropertiesReader.getProperty("properties.properties", "rsp_server_url00089800").trim();
		RspResult res=send(strurl, bean,null);
		//NbpLog.debugError("ylcs"+Thread.currentThread().getId(),"=====send rsp =============end"+(System.currentTimeMillis()-start)+"ms");
		return res;
		
	}
	/**
	 * 2011 08 04 hzy  给银行登录发rsp请求用的
	 * */
	public static RspResult sendWithLoginType(IRspBean bean,String logintype) throws Exception {
		long start=System.currentTimeMillis();
 
		String strurl = PropertiesReader.getProperty("properties.properties", "rsp_server_url00089800").trim();
		RspResult res=send(strurl, bean,logintype); 
		return res;
		
	}
	
	public static RspResult send(String strurl, IRspBean bean,String logintype) throws Exception {
		// 
		if (!strurl.endsWith("/"))
			strurl = strurl + "/";
		String trancode = bean.getTranCode();
		strurl = strurl + trancode + ".svr";
		System.out.println(strurl);
		URL url = new URL(strurl);
		
		HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
		urlconn.setRequestMethod("POST");
		urlconn.addRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		urlconn.setRequestProperty("Connection", "Keep-Alive");
		urlconn.setDoOutput(true);
		urlconn.setUseCaches(false);
		Field[] fields = bean.getClass().getDeclaredFields();
		Field.setAccessible(fields, true);
		StringBuffer message = new StringBuffer("");
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			try {
				if (field.get(bean) != null) {
					if (field.get(bean) instanceof DataPool) {
						DataPool pool = (DataPool) field.get(bean);
						StringBuffer sb = new StringBuffer("");
						for (Object key : pool.keySet()) {
							if (pool.get(key) != null) {
								Element keyNode = new Element(key.toString());
								keyNode.setText(pool.get(key).toString());
								sb.append("<" + key + ">" + pool.get(key)
										+ "</>");
							}
						}
						message.append("&" + field.getName().toLowerCase() + "="
								+ URLEncoder.encode(sb.toString(), "GBK"));
						/*urlconn.addRequestProperty(field.getName(), sb
								.toString());*/
					} else if (field.get(bean) instanceof File) {// 判断传入的信息是否为文件类型
						File file = (File) field.get(bean);
						// 是文件类型则先将其上传到FTP服务上
						FtpUtil myFTP = new FtpUtil();// 新建一个FTP工具类型 
						String filename = myFTP.putFileToServer(file); 
						message.append("&" + field.getName().toLowerCase() + "="
								+ URLEncoder.encode(filename, "GBK"));//调用上传方法
					} else
						message.append("&"
								+ field.getName().toLowerCase()
								+ "="
								+ URLEncoder.encode(field.get(bean).toString(),
										"GBK"));
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		String tmpString=message.toString();
		if(tmpString.length()!=0)
			tmpString=tmpString.substring(1);
		urlconn.addRequestProperty("Content-Length", Integer.toString(tmpString.getBytes("GBK").length));
		OutputStream os = urlconn.getOutputStream(); // 输出流，写数据
		if (message.length() != 0)
			os.write(tmpString.getBytes("GBK")); 
		os.flush();
		os.close();
		if (urlconn.getResponseCode() == 200) {
			if (urlconn.getHeaderField("retcode").equals("1")) {
				InputStream instream = urlconn.getInputStream();
				int len = Integer.parseInt(urlconn
						.getHeaderField("Content-Length"));
				byte data[] = new byte[len];
				instream.read(data);
				String mes = new String(data);
			}
			InputStream instream = urlconn.getInputStream();
			SAXBuilder builder = new SAXBuilder();
			builder.setIgnoringElementContentWhitespace(true);
			Document doc = builder.build(instream);
			Element root = doc.getRootElement();
			RspResult result = new RspResult(root);
			return result;
		} else {
			throw new Exception("错误码：" + urlconn.getResponseCode());
		}
	}
	
	public static void main(String[] args) throws Exception{
		Svr802bean bean=new Svr802bean();
		RspClientUtil.send(bean);
		
	}
}
