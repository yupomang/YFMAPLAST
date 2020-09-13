package com.yondervision.yfmap.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogBack {
	private static Logger log = LoggerFactory.getLogger(LogBack.class);
	
//	public static void config(){
//		System.out.println("日志输出########*********1");
//		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
//        try {
//        	System.out.println("日志输出########*********2");
//             JoranConfigurator configurator = new JoranConfigurator();
//             System.out.println("日志输出########*********3");
//             configurator.setContext(lc);
//             lc.reset();   
//          configurator.doConfigure("WebRoot/WEB-INF/logback-log.xml");
//          System.out.println("日志输出########*********4");
//        }catch (JoranException e) {     
//        	System.out.println("日志输出########*********5"); 
//        	e.printStackTrace();
//           log.error(e.getMessage());
//        }    
//	}
	
	public static void logInfo(LogPara para){
		
//		config();
		String ip = "";
		try {
			ip = InetAddress.getLocalHost().getHostAddress().toString();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
			ip = "127.0.0.1";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");	
		Date date1=null;
		Date date2=null;
		try {
			date1 = format.parse(para.getStartdate());
			date2 = format.parse(para.getSendEndTime());
		} catch (ParseException e) {
			//e.printStackTrace();
		}
		//计算差值，分钟数
		long millisecond=(date2.getTime()-date1.getTime());
//		System.out.println(millisecond);
		para.setRetime(String.valueOf(millisecond)+"毫秒");
		
		///was/applogs/ydhlxt/%d{yyyy-MM-dd}/log/${fileName}
		String PROPERTIES_FILE_NAME = "properties.properties";
		String logpath = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "dllogbackaddress").trim();
		String fileName = logpath+format1.format(System.currentTimeMillis())+"/log/"+ip+"_YDHLXT";
		System.out.println("********* 日志输入文件信息："+fileName);
		MDC.put("fileName", fileName);
		
		
//		MDC.put("fileName", ip+"_YDHLXT");		
        log.info("【交易开始】【"+para.getCode()+"】【"+para.getName()+"】【"+para.getStartdate()+"】####"+para.getKey()+"####\r");
        log.info("【业务处理】【"+para.getStartdate()+"】"+para.getName()+"处理开始"+"####"+para.getKey()+"####\r");
//        log.info("【组报文】【"+para.getStartXml()+"】####"+para.getKey()+"####\n");
        log.info("【发送报文】【"+para.getSendStartTime()+"】"+para.getReqmsg()+"####"+para.getKey()+"####\r");   
        log.info("【接收报文】【"+para.getSendEndTime()+"】"+para.getRepmsg()+"####"+para.getKey()+"####\r");
//      log.info("【拆报文】【"+para.getEndXml()+"】####"+para.getKey()+"####\n");
        log.info("【业务处理】【"+para.getEnddate()+"】"+para.getName()+"处理结束"+"####"+para.getKey()+"####\r");
        log.info("【交易结束】【"+para.getCode()+"】【"+para.getName()+"】【"+para.getEnddate()+"】【"+para.getRecode()+"】【"+para.getRemsg()+"】【"+para.getRetime()+"】####"+para.getKey()+"####\r");
	}
	/**
	 * @param args
	 * @throws UnknownHostException 
	 */
//	public static void main(String[] args) throws UnknownHostException {
//		LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
//        try {
//             JoranConfigurator configurator = new JoranConfigurator();
//             configurator.setContext(lc);
//             lc.reset();
//          configurator.doConfigure("WebRoot/logback-log.xml");
//        }catch (JoranException e) {
//          log.error(e.getMessage());
//        }

        //打印logback加载过程

       // StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        //log.info("flyfox server daemon run.");
        //log.info("test info");
//        try {
//                 Thread.sleep(100);
//          } catch (InterruptedException e) {
//                 // TODO Auto-generated catch block
//                 e.printStackTrace();
//          }
        //log.debug("test debug");
        //log.error("test error");
        //for(int i=0;i<99999;i++)
        	//log.info("flyfox server daemon run.");
//		config();
		
//		for(int i=0;i<9;i++){
//			log.info("LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();");
//			log.info("=====================================================================");
//			log.info("LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();");
//			log.info("=====================================================================");
//		}
//		
//		
//		try {
//			Enumeration enumeration =NetworkInterface.getNetworkInterfaces();
//			InetAddress in = null;
//			while(enumeration.hasMoreElements()){
//				NetworkInterface ni = (NetworkInterface)enumeration.nextElement();
//				System.out.println(ni.getName());
//				InetAddress ip = (InetAddress)ni.getInetAddresses().nextElement();
//				if(!ip.isSiteLocalAddress()&&!ip.isLoopbackAddress()&&!(ip.getHostAddress().indexOf(":")==-1)){
//					System.out.println("IP:  "+ip.getHostAddress());
//					break;
//				}
//			}
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
////		System.out.println(new String(InetAddress.getLocalHost().getAddress()));
//	}
}
