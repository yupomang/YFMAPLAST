package com.yondervision.yfmap.result;

import java.util.List;

import com.yondervision.yfmap.socket.ReqHeadbean;


/** 
* @ClassName: AppApi3002Result 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 10:57:51 AM   
* 
*/ 
public class AppApi300301Result{
	/** 关键字 */						
	private String recode = "";						
	/** 姓名 */						
	private String remsg = "";
	
	private List<AppApi300302Result> list = null;

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getRemsg() {
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

	public List<AppApi300302Result> getList() {
		return list;
	}

	public void setList(List<AppApi300302Result> list) {
		this.list = list;
	}	
	
}
