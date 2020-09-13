package com.yondervision.yfmap.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;

/** 
* @ClassName: SocketServer 
* @Description: TODO
* @author Caozhongyan
* @date Oct 26, 2013 9:36:42 AM   
* 
*/ 
public class SocketServer extends Thread {
	Logger log = Logger.getLogger("YFMAP");
	private AbsSocketServerService socketService;
	private ServerSocket serverSocket;
	private ExecutorService executorService;// 线程池
	private static final int POOL_SIZE = 20;// 单个CPU线程池大小
	private String className;
	public SocketServer(String className,int port) throws IOException {
		try {
			log.info(Constants.LOG_HEAD+"SOCKET服务名:["+className+"]");
			this.className = className;
			this.serverSocket = new ServerSocket(port);
			this.serverSocket.setReuseAddress(true);
		} catch (IOException e) {
			e.printStackTrace();
			log.error(Constants.LOG_ERROR+"socket start error IOException");
			log.error(e, e.fillInStackTrace());
		}	
		// Runtime的availableProcessor()方法返回当前系统的CPU数目.
		this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
				.availableProcessors()
				* POOL_SIZE);
		log.info(Constants.LOG_HEAD+"GJJ Server computer port:"+port);
	}

	public void run() {
		log.info(Constants.LOG_HEAD+"SOCKET服务run开始");
		while (true) {
			Socket socket = null;
			try {
				// 接收客户连接,只要客户进行了连接,就会触发accept();从而建立连接
				if(this.serverSocket==null)break;
				socket = this.serverSocket.accept();
				this.socketService = AbsSocketServerService.getService(this.className);
				this.socketService.setSocket(socket);
				executorService.execute(this.socketService);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(Constants.LOG_ERROR+"SOCKET RUN ERROR "+this.socketService.getServiceName());
				log.error(e, e.fillInStackTrace());
			}
		}
		log.info(Constants.LOG_HEAD+"SOCKET服务run结束");
	}
}

