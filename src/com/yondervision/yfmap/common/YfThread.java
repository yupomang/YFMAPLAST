package com.yondervision.yfmap.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.dto.CMi401;
import com.yondervision.yfmap.service.AppApi401Service;

public class YfThread implements Runnable {
 
	@Autowired
	private AppApi401Service appApi401Service;
	
	private String filename; 
	private String msg;
	private int seqno;
		   
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno){
		this.seqno = seqno;			
	}
		
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}		

	public void run() {
		// TODO Auto-generated method stub
		System.out.println(filename);
		int sum = 0;
		for(int i=0;i<90000;i++){
			sum++;				   
		}
		System.out.println("线程执行完毕："+sum+"    msg:"+filename);
	    Gson gson=new Gson();
	    CMi401 api3002 = gson.fromJson(msg, new TypeToken<CMi401>(){}.getType());   		
	    try {
		    appApi401Service.acction1(api3002, null, msg, msg);   
		    
//	    	appApi401Service.acction3(api3002, null, msg, msg);
	    } catch (Exception e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	    }
		   
	}
	 

}
