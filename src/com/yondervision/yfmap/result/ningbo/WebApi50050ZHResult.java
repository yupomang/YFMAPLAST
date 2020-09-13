package com.yondervision.yfmap.result.ningbo;

public class WebApi50050ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	private String totalnum;//总比数
	private String succsum;//非常满意笔数
	private String counts;//满意笔数
	private String failsum;//不满意笔数
	
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
	public String getSuccsum() {
		return succsum;
	}
	public void setSuccsum(String succsum) {
		this.succsum = succsum;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getFailsum() {
		return failsum;
	}
	public void setFailsum(String failsum) {
		this.failsum = failsum;
	}
	
}
