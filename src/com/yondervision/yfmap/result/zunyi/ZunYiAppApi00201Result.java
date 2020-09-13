package com.yondervision.yfmap.result.zunyi;

public class ZunYiAppApi00201Result {
	/** 返回码 */						
	private String recode = "";
	/** 返回描述 */						
	private String msg = "";
	/** 文件名 */						
	private String fileName = "";
	private String isloanflag = "";
	private String accinstname = "";
	private String nextpage = "";
	public String getNextpage() {
		return nextpage;
	}
	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getAccinstname() {
		return accinstname;
	}
	public void setAccinstname(String accinstname) {
		this.accinstname = accinstname;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
