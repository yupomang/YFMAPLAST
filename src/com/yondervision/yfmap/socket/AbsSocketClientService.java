package com.yondervision.yfmap.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/** 
* @ClassName: AbsSocketClientService 
* @Description: TODO
* @author Caozhongyan
* @date Oct 26, 2013 9:35:21 AM   
* 
*/ 
public abstract class AbsSocketClientService {

	private static final int TIME_OUT = 60 * 1000;
	
	private static final String SERVICE_PACKAGE = "com.yondervison.socketservice.entitry";
	
	protected InputStream in;

	protected OutputStream out;
	
	protected String message;

	protected abstract String getIP();

	protected abstract int getPort();

	protected abstract String acction()throws Exception ;
	
	protected abstract String acction(String jym)throws Exception ;
	
	/**
	* <pre> 获得SOCKET客户端处理类 </pre>
	* @param @param serviceName
	* @param @return    
	*/
	public static AbsSocketClientService getService(String serviceName) {
		String serviceClassName = new StringBuffer().append(SERVICE_PACKAGE)
				.append(".").append(serviceName).toString();
		AbsSocketClientService socketService = null;
		try {
			socketService = (AbsSocketClientService) Class.forName(serviceClassName).newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return socketService;
	}

	/**
	* <pre> 发送 </pre>
	* @param message
	* @return result   
	*/
	public String send(String message,String jym) {
		this.message = message;
		Socket socket = null;
		String result = "";
		try {
			socket = new Socket(this.getIP(), this.getPort());
			socket.setSoTimeout(TIME_OUT);
			this.in = socket.getInputStream();
			this.out = socket.getOutputStream();
			result = this.acction(jym);//调用实现类中action方法取得返回结果
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
