package com.yondervision.yfmap.socket;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: MainServlet 
* @Description: TODO
* @author Caozhongyan
* @date Oct 26, 2013 9:36:35 AM   
* 
*/ 
public class MainServlet extends HttpServlet {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 
	 */
	private static final long serialVersionUID = 8294702809252827823L;

	/**
	 * Constructor of the object.
	 */
	public MainServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		
//		ServletContext sc = getServletContext();
//		String startStatus = getInitParameter("StartStatus").trim();
//		System.out.println(" MainServlet 启动  "+startStatus);
//		if("true".equals(startStatus)){}
		
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		String startStatus = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageOpen").trim();
		log.debug("MainServlet 是否开启SOCKET服务: "+startStatus);
		
		if("true".equals(startStatus)){
			System.out.println("web.xml SOCKET POEN :"+startStatus);
			try {
				SocketServer kmServer = new SocketServer("YFMAPServerSendMessage",AbsSocketServerService.getService("YFMAPServerSendMessage").getPort());
				kmServer.start();
				log.info(Constants.LOG_HEAD+"【YFMAP socket server is start】");
			} catch (IOException e) {
				e.printStackTrace();
				log.error(Constants.LOG_ERROR+"【YFMAP socket server is not start】");
				log.error(e, e.fillInStackTrace());
			}
			System.out.println("开启SOCKET服务");
		}		
	}
}
