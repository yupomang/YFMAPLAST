package com.yondervision.yfmap.result.ningbo;

public class AppApi80006ZHResult {

	private String 	certinum;//身份证号码
	private String 	accname;//单位账号
	private String 	instcode;
	private String 	certitype;
	private String cleardate;
	private String curbal;
	private String tolowecnt;
	private String lnundtkbcode;
	private String loanamt;
	private String loanterm;
	private String owecnt;
	private String remainterm;

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(final String accname) {
		this.accname = accname;
	}

	public String getInstcode() {
		return this.instcode;
	}

	public void setInstcode(final String instcode) {
		this.instcode = instcode;
	}

	public String getCertitype() {
		return this.certitype;
	}

	public void setCertitype(final String certitype) {
		this.certitype = certitype;
	}

	public String getCleardate() {
		return this.cleardate;
	}

	public void setCleardate(final String cleardate) {
		this.cleardate = cleardate;
	}

	public String getCurbal() {
		return this.curbal;
	}

	public void setCurbal(final String curbal) {
		this.curbal = curbal;
	}

	public String getTolowecnt() {
		return this.tolowecnt;
	}

	public void setTolowecnt(final String tolowecnt) {
		this.tolowecnt = tolowecnt;
	}

	public String getLnundtkbcode() {
		return this.lnundtkbcode;
	}

	public void setLnundtkbcode(final String lnundtkbcode) {
		this.lnundtkbcode = lnundtkbcode;
	}

	public String getLoanamt() {
		return this.loanamt;
	}

	public void setLoanamt(final String loanamt) {
		this.loanamt = loanamt;
	}

	public String getLoanterm() {
		return this.loanterm;
	}

	public void setLoanterm(final String loanterm) {
		this.loanterm = loanterm;
	}

	public String getOwecnt() {
		return this.owecnt;
	}

	public void setOwecnt(final String owecnt) {
		this.owecnt = owecnt;
	}

	public String getRemainterm() {
		return this.remainterm;
	}

	public void setRemainterm(final String remainterm) {
		this.remainterm = remainterm;
	}

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
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
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}

	

}
