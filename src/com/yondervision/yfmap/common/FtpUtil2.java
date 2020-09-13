package com.yondervision.yfmap.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtil2 {
	Logger log = Logger.getLogger("YFMAP");
	String url=null;
	int port=0;
	String username=null;
	String password=null;
	String remotePath2=null;
	String localPath=null;
	String uploadPath="";
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
    public FtpUtil2(){
    	this(null);
    }
    
	public void setremotePath2(String remotePath2) {
		this.remotePath2 = remotePath2;
	}
	
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	
	public String getLocalPath() {
		return localPath;
	}

	public FtpUtil2(String groupname){		
		if(groupname==null){			
			this.url = ReadProperties.getString("ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString("ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString("ftp_user");// FTP登录账号
			this.password = ReadProperties.getString("ftp_password");// FTP登录密码
			this.remotePath2 = ReadProperties.getString("ftp_path");		
			this.localPath = ReadProperties.getString("local_path");
		}else{
			this.url = ReadProperties.getString(groupname+"_ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString(groupname+"_ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString(groupname+"_ftp_user");// FTP登录账号
			this.password = ReadProperties.getString(groupname+"_ftp_password");// FTP登录密码
			this.remotePath2 = ReadProperties.getString(groupname+"_ftp_path");
			this.localPath = ReadProperties.getString(groupname.replace("00089800", "")+"_local_path");
		}		
		
		
		this.localPath=this.localPath.replaceAll("\\\\", "/");
		this.remotePath2=this.remotePath2.replaceAll("\\\\", "/");
		
		if(!this.localPath.endsWith("/"))
			this.localPath+="/";
		
		if(!this.remotePath2.endsWith("/"))
			this.remotePath2+="/";
		
	}
	public FtpUtil2(String groupname,String uploadPath){		
		if(groupname==null){			
			this.url = ReadProperties.getString("ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString("ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString("ftp_user");// FTP登录账号
			this.password = ReadProperties.getString("ftp_password");// FTP登录密码
			this.remotePath2 = ReadProperties.getString("ftp_path");	
			this.uploadPath = ReadProperties.getString("ftp_upload_path")+uploadPath;
			this.localPath = ReadProperties.getString("local_path");
		}else{
			this.url = ReadProperties.getString(groupname+"_ftp_url");// FTP服务器hostname
			this.port = Integer.parseInt(ReadProperties.getString(groupname+"_ftp_port"));// FTP服务器端口
			this.username = ReadProperties.getString(groupname+"_ftp_user");// FTP登录账号
			this.password = ReadProperties.getString(groupname+"_ftp_password");// FTP登录密码
			this.remotePath2 = ReadProperties.getString(groupname+"_ftp_path");
			this.uploadPath = ReadProperties.getString(groupname+"_ftp_upload_path")+uploadPath;
			this.localPath = ReadProperties.getString(groupname.replace("00089800", "")+"_local_path");
		}		
		
		
		this.localPath=this.localPath.replaceAll("\\\\", "/");
		this.remotePath2=this.remotePath2.replaceAll("\\\\", "/");
		
		if(!this.localPath.endsWith("/"))
			this.localPath+="/";
		
		if(!this.remotePath2.endsWith("/"))
			this.remotePath2+="/";
		
	}
	private boolean uploadFile(String filename, InputStream input) {
		 
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			String realpath =this.remotePath2;// ReadProperties.getString("ftp_path");
			if (filename.indexOf("/") != -1) {
				String[] dname = filename.split("/");
				// dname 把带路径的文件名按照/拆分成数组

				if (realpath.endsWith("/"))
					realpath = realpath.substring(0, realpath.length() - 1);
				for (int n = 0; n < dname.length - 1; n++) {
					realpath += "/" + dname[n];
					ftp.makeDirectory(realpath);
					ftp.changeWorkingDirectory(realpath); 
				}
				filename = dname[dname.length - 1];
			} 
			ftp.changeWorkingDirectory(realpath);
			// 将上传文件存储到指定目录
			ftp.storeFile(filename, input);
			// 关闭输入流
			input.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					throw new RuntimeException(ioe.getMessage());
				}
			}
		} 
		return success;
	}
	
	private boolean uploadFileName(String filename, InputStream input) {
		 
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			String realpath =this.uploadPath;// ReadProperties.getString("ftp_path");
			if (filename.indexOf("/") != -1) {
				String[] dname = filename.split("/");
				// dname 把带路径的文件名按照/拆分成数组

				if (realpath.endsWith("/"))
					realpath = realpath.substring(0, realpath.length() - 1);
				for (int n = 0; n < dname.length - 1; n++) {
					realpath += "/" + dname[n];
					ftp.makeDirectory(realpath);
					ftp.changeWorkingDirectory(realpath); 
				}
				filename = dname[dname.length - 1];
			} 
			if (realpath.indexOf("/") != -1) {
				String[] dname = realpath.split("/");
				String temp = "";
				// dname 把带路径的文件名按照/拆分成数组
				for (int n = 0; n < dname.length; n++) {
					temp += "/" + dname[n];
					ftp.makeDirectory(temp);
					ftp.changeWorkingDirectory(temp); 
				}
			} 
			ftp.changeWorkingDirectory(realpath);
			//不加上传文件失败--重要
			ftp.enterLocalPassiveMode();
			
			//设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
			//ftp.makeDirectory(realpath);
	        System.out.println("在目标服务器上成功建立了文件夹: " + realpath);
			// 将上传文件存储到指定目录
			ftp.storeFile(filename, input);
			// 关闭输入流
			input.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					throw new RuntimeException(ioe.getMessage());
				}
			}
		} 
		return success;
	}
	
	private boolean uploadFileToServer(String filename, BufferedInputStream input) {
		 
		// 初始表示上传失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 登录ftp
			ftp.login(username, password);
			// 看返回的值是不是230，如果是，表示登陆成功
			reply = ftp.getReplyCode();
			// 以2开头的返回值就会为真
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			String realpath =this.uploadPath;// ReadProperties.getString("ftp_path");
			if (filename.indexOf("/") != -1) {
				String[] dname = filename.split("/");
				// dname 把带路径的文件名按照/拆分成数组

				if (realpath.endsWith("/"))
					realpath = realpath.substring(0, realpath.length() - 1);
				for (int n = 0; n < dname.length - 1; n++) {
					realpath += "/" + dname[n];
					ftp.makeDirectory(realpath);
					ftp.changeWorkingDirectory(realpath); 
				}
				filename = dname[dname.length - 1];
			} 
			ftp.changeWorkingDirectory(realpath);
			
			//不加上传文件失败--重要
			ftp.enterLocalPassiveMode();
			
			//设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
           boolean t =  ftp.storeFile(new String(filename.getBytes("GBK"), "iso-8859-1"), input);
			// 将上传文件存储到指定目录
			//ftp.storeFile(filename, input);
			// 关闭输入流
			input.close();
			// 退出ftp
			ftp.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					ioe.printStackTrace();
					throw new RuntimeException(ioe.getMessage());
				}
			}
		} 
		return success;
	}

	private  boolean downFileFromFTP(String remotePath2, String fileName, String localPath) throws RuntimeException {
		// 初始表示下载失败
		boolean success = false;
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		FTPClientConfig conf = new FTPClientConfig( FTPClientConfig.SYST_UNIX);
        ftp.configure(conf);
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
//			ftp.enterLocalActiveMode();    //主动模式
			ftp.enterLocalPassiveMode(); 	//被动模式
			ftp.setRemoteVerificationEnabled(false);
			ftp.setFileType(ftp.BINARY_FILE_TYPE);
			ftp.setFileTransferMode(ftp.STREAM_TRANSFER_MODE); ///传输文件为流的形式
			// 登录ftp
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			log.debug("远程文件路径："+remotePath2);
			boolean falg = ftp.changeWorkingDirectory(remotePath2);	
			log.debug("切换目录："+falg);
			// 转到指定下载目录
			// 2010 12 10 hzy 压力测试中，文件过多浪费时间

			File localFile = new File(localPath + "/" + fileName);
			// 输出流
			OutputStream is = new FileOutputStream(localFile);
			// 下载文件
			log.debug("下载文件fileName："+fileName);
			boolean i = ftp.retrieveFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), is); 
			log.debug("下载文件是否成功："+i);
			is.close();
			if (fileName.toLowerCase().indexOf("tmp") > -1)// 2010 12 02
															// 下传文件中包含tmp下载完毕后就清除掉
															// hzy
				ftp.deleteFile(remotePath2 + "/" + fileName);
			// 退出ftp
			ftp.logout();
			// 下载成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace(); 
			throw new RuntimeException(e.getMessage());
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					throw new RuntimeException(ioe.getMessage());
				}
			}
		}
		return success;
	}

	public  void deletefromFTP(String deletefile) {
		// 创建FTPClient对象
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.connect(url, port);
			// 登录ftp
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				 
			} else {
				ftp.deleteFile(deletefile);
				// 退出ftp
				ftp.logout();
				// 下载成功
			 
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			 
			throw new RuntimeException(e.getMessage());
		}
	}
 
    public String  putFileToServer(File file){
    	InputStream input = null;// 输入流
    	String fileName =null;
    	String qlj=file.getAbsolutePath().replaceAll("\\\\", "/"); 
    	String localPathQlj=new File(this.localPath).getAbsolutePath().replaceAll("\\\\", "/");
    	if(!localPathQlj.endsWith("/")){
    		localPathQlj+="/";
    	}
    	try{ 
    	   if(qlj.startsWith(localPathQlj)){ 
    		   input = new FileInputStream(file);
    		   fileName=qlj.substring(localPathQlj.length());
    	   }else{ 
    		  
    		   fileName=file.getName();
    		   input = new FileInputStream(file); 
    	   }    	  
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		 
			throw new RuntimeException(e.getMessage());
		} 
        uploadFile(fileName, input);
        return fileName;
        
    }
    
    public String  putFileToUploadServer(File file){
    	InputStream input = null;// 输入流
    	String fileName =null;
    	BufferedInputStream bis = null;

    	String qlj=file.getAbsolutePath().replaceAll("\\\\", "/"); 
    	String localPathQlj=new File(this.localPath).getAbsolutePath().replaceAll("\\\\", "/");
    	if(!localPathQlj.endsWith("/")){
    		localPathQlj+="/";
    	}
    	try{ 
    	   if(qlj.startsWith(localPathQlj)){ 
    		   input = new FileInputStream(file);
    		   fileName=qlj.substring(localPathQlj.length());
    	   }else{ 
    		  
    		   fileName=file.getName();
    		   input = new FileInputStream(file); 
    	   }    	  
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		 
			throw new RuntimeException(e.getMessage());
		} 
        bis = new BufferedInputStream(input);
        uploadFileToServer(fileName, bis);
        return fileName;
        
    }
	public  void putFileToServer(String fileName) { 
		String filepathname = this.localPath + "/" + fileName;
		InputStream input = null;// 输入流
		try {
			input = new FileInputStream(filepathname);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 
			throw new RuntimeException(e.getMessage());
		}
		boolean issucc = uploadFile(fileName, input);
		if (!issucc) {
			throw new RuntimeException("连接FTP失败！");
		}
	}
	
	public  void putFileToServerName(String fileName) { 
		String filepathname = this.localPath + "/" + fileName;
		InputStream input = null;// 输入流
		try {
			input = new FileInputStream(filepathname);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			 
			throw new RuntimeException(e.getMessage());
		}
		boolean issucc = uploadFileName(fileName, input);
		if (!issucc) {
			throw new RuntimeException("连接FTP失败！");
		}
	}

	public   File getFileByServer(String fileName) throws RuntimeException { 
		String pathName =this.localPath;
		if (pathName.endsWith("/"))
			pathName = pathName.substring(0, pathName.length() - 1);
		String filepathname = fileName.startsWith("/") ?  pathName + fileName : pathName + "/" + fileName;
		String localPath = pathName;
		String remotePath2 =this.remotePath2;
		log.debug("getFileByServer  : "+remotePath2);
		if (remotePath2.endsWith("/"))
			remotePath2 = remotePath2.substring(0, remotePath2.length() - 1);
		String[] dname = fileName.split("/");
		for (int n = 0; n < dname.length - 1; n++) {
			remotePath2 += dname[n].length() > 0 ? "/" + dname[n] : "";
			localPath += dname[n].length() > 0 ? "/" + dname[n] : "";
		}
//		remotePath2 = fileName.substring(0,fileName.lastIndexOf("/"));
		File thefile = new File(localPath);
		if (!thefile.exists()) {
			if(!thefile.mkdirs())
				log.debug("创建文件或文件夹失败:"+thefile);
		}
		String filename = dname[dname.length - 1];
		 
	    this.downFileFromFTP(remotePath2, filename, localPath);
  
		return new File(filepathname);
	}
	
	public   File getFileByServerForNB2(String fileName) throws RuntimeException { 
		String pathName =this.localPath;
		if (pathName.endsWith("/"))
			pathName = pathName.substring(0, pathName.length() - 1);
		String filepathname = fileName.startsWith("/") ?  pathName + fileName : pathName + "/" + fileName;
		String localPath = pathName;
//		String remotePath2 =this.remotePath2;
//		String remotePath2 ="/00000000/fil/"; //测试
		String remotePath2 ="/share/fil/";//生产
		log.debug("getFileByServer  : "+remotePath2);
		if (remotePath2.endsWith("/"))
			remotePath2 = remotePath2.substring(0, remotePath2.length() - 1);
		String[] dname = fileName.split("/");
		for (int n = 2; n < dname.length - 1; n++) {
			remotePath2 += dname[n].length() > 0 ? "/" + dname[n] : "";
		}
		for (int n = 0; n < dname.length - 1; n++) {
			localPath += dname[n].length() > 0 ? "/" + dname[n] : "";
		}
//		remotePath2 = fileName.substring(0,fileName.lastIndexOf("/"));
		File thefile = new File(localPath);
		if (!thefile.exists()) {
			if(!thefile.mkdirs())
				log.debug("创建文件或文件夹失败:"+thefile);
		}
		String filename = dname[dname.length - 1];
		 
	    this.downFileFromFTP(remotePath2, filename, localPath);
  
		return new File(filepathname);
	}
	
	public   File getFileByUploadServer(String fileName) throws RuntimeException { 
		String pathName =this.localPath;
		if (pathName.endsWith("/"))
			pathName = pathName.substring(0, pathName.length() - 1);
		String filepathname = fileName.startsWith("/") ?  pathName + fileName : pathName + "/" + fileName;
		String localPath = pathName;
		String remotePath2 =this.uploadPath;
		log.debug("getFileByServer  : "+remotePath2);
		if (remotePath2.endsWith("/"))
			remotePath2 = remotePath2.substring(0, remotePath2.length() - 1);
		String[] dname = fileName.split("/");
		for (int n = 0; n < dname.length - 1; n++) {
			remotePath2 += dname[n].length() > 0 ? "/" + dname[n] : "";
			localPath += dname[n].length() > 0 ? "/" + dname[n] : "";
		}
//		remotePath2 = fileName.substring(0,fileName.lastIndexOf("/"));
		File thefile = new File(localPath);
		if (!thefile.exists()) {
			if(!thefile.mkdirs())
				log.debug("创建文件或文件夹失败:"+thefile);
		}
		String filename = dname[dname.length - 1];
		 
	    this.downFileFromFTP(remotePath2, filename, localPath);
  
		return new File(filepathname);
	}
	
	public static void main(String[] args) throws IOException 
	{
		File file=new File("d:/ftplocalpath/repayeelist_1020170419221339211.txt");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream out=new FileOutputStream(file,false);        
       // sb.append(sb.toString()+"\r\n");
        out.write("123123132123".getBytes());
        out.close();
        
        //TODO  上传 ftp---具体路径，用户名密码需确定
        FtpUtil2 f=new FtpUtil2("bsp00089800","");
		f.putFileToUploadServer(file);
	 
	}
}

