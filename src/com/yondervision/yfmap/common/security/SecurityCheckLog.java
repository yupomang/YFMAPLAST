/**
 * 文件名：securityCheckLog.java
 * 功能：安全检查过滤器日志类
 * */
package com.yondervision.yfmap.common.security;

import java.io.File;
import java.io.FileWriter;  
public class SecurityCheckLog {
	 
	public synchronized static void writeLog(String log){
		if(SecurityCheckManager.IS_LOG){
			String path=SecurityCheckManager.LOGPATH;
			File f=new File(path);
			if(!f.exists()){
				f.mkdirs();
			}
			if(!path.endsWith("/"))
				path+="/";
			String rq=(new java.sql.Date(System.currentTimeMillis())).toString();
			path+=rq+".log";
			try{
			   FileWriter LogFileWrite = new FileWriter(path, true); 
			   LogFileWrite.write(log);
			   LogFileWrite.write(System.getProperty("line.separator"));
			   LogFileWrite.close();			   
			}catch(Exception e){
				e.printStackTrace();
			}
			
		} 
	}
 
}
