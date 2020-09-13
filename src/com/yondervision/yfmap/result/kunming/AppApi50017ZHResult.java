package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 * 开发商账户登录验证
 */
public class AppApi50017ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 成功标志*/
	private String rspFlag = "";
	/** 返回信息*/
	private String rspInfo = "";
	
	private String wt_transdate = "";
	private String wt_instcode = "";
	private String wt_op = "";
	private String opname = "";
	private String empnum = "";
	private String empunitname = "";
	
	public String getWt_transdate() {
		return wt_transdate;
	}
	public String getWt_instcode() {
		return wt_instcode;
	}
	public String getWt_op() {
		return wt_op;
	}
	public String getOpname() {
		return opname;
	}
	public String getEmpnum() {
		return empnum;
	}
	public String getEmpunitname() {
		return empunitname;
	}
	public void setWt_transdate(String wt_transdate) {
		this.wt_transdate = wt_transdate;
	}
	public void setWt_instcode(String wt_instcode) {
		this.wt_instcode = wt_instcode;
	}
	public void setWt_op(String wt_op) {
		this.wt_op = wt_op;
	}
	public void setOpname(String opname) {
		this.opname = opname;
	}
	public void setEmpnum(String empnum) {
		this.empnum = empnum;
	}
	public void setEmpunitname(String empunitname) {
		this.empunitname = empunitname;
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
	public String getRspFlag() {
		return rspFlag;
	}
	public void setRspFlag(String rspFlag) {
		this.rspFlag = rspFlag;
	}
	public String getRspInfo() {
		return rspInfo;
	}
	public void setRspInfo(String rspInfo) {
		this.rspInfo = rspInfo;
	}
	
}
