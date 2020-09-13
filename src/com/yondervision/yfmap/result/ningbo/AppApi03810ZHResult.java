package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi03810ZHResult {

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
	/*结果总条数*/
	private String consum = "";
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
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
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
	public String getConsum() {
		return consum;
	}
	public void setConsum(String consum) {
		this.consum = consum;
	}
}
