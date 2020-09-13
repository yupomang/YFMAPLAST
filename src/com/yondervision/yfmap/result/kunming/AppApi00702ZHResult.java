package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author 
 *  个人 -- 还款计划查询  / 个人贷款还款计划查询
 */
public class AppApi00702ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 下传批量文件名*/
	private String filename = "";
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
