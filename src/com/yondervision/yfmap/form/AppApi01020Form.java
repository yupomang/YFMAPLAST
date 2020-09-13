package com.yondervision.yfmap.form;

public class AppApi01020Form extends AppApiCommonForm {

	//	证件号码	bodyCardNumber
	private String tqyy="";	// 提取原因
	
	private String relation="";	//	关系
	private String dwzh="";	//	单位账号的值
	private String drawreasoncode1="";	// 提取依据号
	private String drawreason="";	// 核心返回返回提取原因
	private String xingming = "";
	private String grzh="";	//个人账号
	private String jkhtbh = "";//借款合同编号
	private String zjlx = "";//证件类型
	
	private String sqje = "";//提取申请金额
	private String skzhmc = "";//收款账号名称
	private String skdwzh = "";//收款单位账号
	private String skyh = "";//收款账户开户银行
	private String payerbankcode;
	private String paperrelation  = "";//方式 1-联名卡4其他收款账户
	private String buyhousename = ""; //购房人姓名
	private String owncertinum = ""; //所有权人证件号码
	private String owncertitype = ""; //所有权人证件类型
	private String matename = ""; //配偶姓名
	private String matecertinum = ""; //配偶证件号码
	private String matecertitype = ""; //配偶证件类型

	private String applyid;
	private String startdate;
	private String enddate;
	private String state;
	private String sjhm;
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getDrawreasoncode1() {
		return drawreasoncode1;
	}
	public void setDrawreasoncode1(String drawreasoncode1) {
		this.drawreasoncode1 = drawreasoncode1;
	}
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public String getTqyy() {
		return tqyy;
	}
	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getSqje() {
		return sqje;
	}
	public void setSqje(String sqje) {
		this.sqje = sqje;
	}
	public String getSkzhmc() {
		return skzhmc;
	}
	public void setSkzhmc(String skzhmc) {
		this.skzhmc = skzhmc;
	}
	public String getSkdwzh() {
		return skdwzh;
	}
	public void setSkdwzh(String skdwzh) {
		this.skdwzh = skdwzh;
	}
	public String getSkyh() {
		return skyh;
	}
	public void setSkyh(String skyh) {
		this.skyh = skyh;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPayerbankcode() {
		return payerbankcode;
	}
	public void setPayerbankcode(String payerbankcode) {
		this.payerbankcode = payerbankcode;
	}
	public String getPaperrelation() {
		return paperrelation;
	}
	public void setPaperrelation(String paperrelation) {
		this.paperrelation = paperrelation;
	}
	public String getBuyhousename() {
		return buyhousename;
	}
	public void setBuyhousename(String buyhousename) {
		this.buyhousename = buyhousename;
	}
	public String getOwncertinum() {
		return owncertinum;
	}
	public void setOwncertinum(String owncertinum) {
		this.owncertinum = owncertinum;
	}
	public String getOwncertitype() {
		return owncertitype;
	}
	public void setOwncertitype(String owncertitype) {
		this.owncertitype = owncertitype;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatecertitype() {
		return matecertitype;
	}
	public void setMatecertitype(String matecertitype) {
		this.matecertitype = matecertitype;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	
}
