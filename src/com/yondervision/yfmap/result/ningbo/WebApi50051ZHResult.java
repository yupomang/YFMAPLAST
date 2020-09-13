package com.yondervision.yfmap.result.ningbo;

public class WebApi50051ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	private String totalnum;//总比数
	private String failsum;//失败笔数
	private String rate;//投诉率
	
	
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
	public String getFailsum() {
		return failsum;
	}
	public void setFailsum(String failsum) {
		this.failsum = failsum;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	

}
