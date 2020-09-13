package com.yondervision.yfmap.result.ningbo;

public class AppApi80001ZHResult {

	private String 	certinum;//身份证号码
	private String 	housecontrnum;//购房合同号
	private String 	accname;//姓名
	private String 	owner;//所属人
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
	public String getHousecontrnum() {
		return housecontrnum;
	}
	public void setHousecontrnum(String housecontrnum) {
		this.housecontrnum = housecontrnum;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
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
