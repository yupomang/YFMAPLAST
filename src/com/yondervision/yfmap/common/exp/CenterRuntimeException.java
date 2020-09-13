package com.yondervision.yfmap.common.exp;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ResourceBundle;
 

public class CenterRuntimeException extends RuntimeException {
	 
	  private String errcode=null;
	  private String message=null;
	  //属性文件
	  private static final ResourceBundle bundle;		
	  static{
			//读取属性文件
			bundle = ResourceBundle.getBundle("errcode");
	  }
		
	  public String getMessage(){ 
			return message;
	  } 
	  public CenterRuntimeException(String errcode,String... mes){
		  this.errcode=errcode;	 
		  String text="错误码="+errcode;
		  if(bundle.getString("err"+errcode)!=null){
			  text=bundle.getString("err"+errcode);			  
		  } 
		  for (int i = 0; i < mes.length; i++) {	 
				text=text.replaceAll("\\{" + String.valueOf(i) + "\\}", mes[i]);
		  } 
		  message=text;
		  
	  }
	  public void printStackTrace(){
		  //System.out.println(">>>"+this.getMessage());
	  }
	  public void printStackTrace(PrintStream s) {
		  //System.out.println(">>>"+this.getMessage());
	  }
	  public void printStackTrace(PrintWriter s){
		  //s.println(">>>"+this.getMessage());
	  }
	  public String getErrcode() {
		return errcode;
	  }
}
