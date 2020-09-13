package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 * 多级字典及摘要 
 */
public class AppApi50033ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";
	/*下传文件名*/
	private String rsufilename = "";
	/*下传文件路径*/
	private String rsufilepath = "";
	
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
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
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getRsufilename() {
		return rsufilename;
	}
	public void setRsufilename(String rsufilename) {
		this.rsufilename = rsufilename;
	}
	public String getRsufilepath() {
		return rsufilepath;
	}
	public void setRsufilepath(String rsufilepath) {
		this.rsufilepath = rsufilepath;
	}
}
