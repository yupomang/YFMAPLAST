package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author 
 * 单位业务--缴存基数变更
 */
public class AppApi04101ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	
	/** 总页数*/
	private String totalpage = "";
	/** 总记录条数*/
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
	/** 经办机构*/
	private String brcCode = "";
	/** 变更人数*/
	private String changePeople = "";
	/** 变更前汇缴额*/
	private String monPaySum1 = "";
	/** 变更后汇缴额*/
	private String monPaySum2 = "";
	/** 变更后基数*/
	private String newBaseNum = "";
	/** 交易日期*/
	private String tranDate ="";
	/** 流水号*/
	private String tranSeq = "";
	/** 操作员*/
	private String tellCode = "";
	/** 单位缴存基数*/
	private String unitBaseNumber1 = "";
	/** 打印文件名*/
	private String filename = "";
	
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
	public String getBrcCode() {
		return brcCode;
	}
	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}
	public String getChangePeople() {
		return changePeople;
	}
	public void setChangePeople(String changePeople) {
		this.changePeople = changePeople;
	}
	public String getMonPaySum1() {
		return monPaySum1;
	}
	public void setMonPaySum1(String monPaySum1) {
		this.monPaySum1 = monPaySum1;
	}
	public String getMonPaySum2() {
		return monPaySum2;
	}
	public void setMonPaySum2(String monPaySum2) {
		this.monPaySum2 = monPaySum2;
	}
	public String getNewBaseNum() {
		return newBaseNum;
	}
	public void setNewBaseNum(String newBaseNum) {
		this.newBaseNum = newBaseNum;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	public String getTellCode() {
		return tellCode;
	}
	public void setTellCode(String tellCode) {
		this.tellCode = tellCode;
	}
	public String getUnitBaseNumber1() {
		return unitBaseNumber1;
	}
	public void setUnitBaseNumber1(String unitBaseNumber1) {
		this.unitBaseNumber1 = unitBaseNumber1;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
