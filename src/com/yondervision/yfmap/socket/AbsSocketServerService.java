/**
 * 
 */
package com.yondervision.yfmap.socket;

import java.net.Socket;
/** 
* @ClassName: AbsSocketServerService 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 9:02:03 AM   
* 
*/ 
public abstract class AbsSocketServerService implements Runnable {
	
	public static final String classnameString = "GJJClient";
	
	public static final int TIME_OUT = 60*1000;
	
	private static final String SERVICE_PACKAGE = "com.yondervision.yfmap.socket";

	protected Socket socket;
	
	public static AbsSocketServerService getService(String serviceName) {
		String serviceClassName = new StringBuffer().append(SERVICE_PACKAGE)
				.append(".").append(serviceName).toString();
		AbsSocketServerService socketService = null;
		try {
			socketService = (AbsSocketServerService) Class.forName(serviceClassName).newInstance();
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

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public abstract void run();
	
	
	public abstract int getPort();

	public abstract String getServiceName();

}
