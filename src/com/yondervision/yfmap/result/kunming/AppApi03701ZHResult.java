package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author 
 *  单位业务--单位资料变更  / 修改单位信息
 */
public class AppApi03701ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	
	/** 流水号*/
	private String tranSeq = "";
	
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	/** 审批号*/
	private String appnum = "";
	/** 单位公积金账号*/
	private String unitaccnum = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 单位客户号*/
	private String unitcustid = "";
	
	private String filename = "";
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getAppnum() {
		return appnum;
	}
	public void setAppnum(String appnum) {
		this.appnum = appnum;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getUnitcustid() {
		return unitcustid;
	}
	public void setUnitcustid(String unitcustid) {
		this.unitcustid = unitcustid;
	}
	
	
}
