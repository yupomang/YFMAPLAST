package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 *  提前部分还款试算
 */
public class AppApi00609ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
	/** 起始日期*/
	private String begdate = null;
	/** 到期日期*/
	private String enddate = null;
	/** 文件格式*/
	private String filename = null;
	/** 还款日*/
	private String intday = null;
	/** 贷款期限*/
	private String O_remainterm = null;
	/** 剩余期限1*/
	private String O_remainterm1 = null;
	/** 还款方式_出口*/
	private String O_retloanmode = null;
	/** 利率*/
	private String rate = null;
	/** 还总利息*/
	private String tolLoanInt_1 = null;
	/** 还总本金*/
	private String tolLoanSum_1 = null;
	
	
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
	public String getBegdate() {
		return begdate;
	}
	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getIntday() {
		return intday;
	}
	public void setIntday(String intday) {
		this.intday = intday;
	}
	public String getO_remainterm() {
		return O_remainterm;
	}
	public void setO_remainterm(String o_remainterm) {
		O_remainterm = o_remainterm;
	}
	public String getO_remainterm1() {
		return O_remainterm1;
	}
	public void setO_remainterm1(String o_remainterm1) {
		O_remainterm1 = o_remainterm1;
	}
	public String getO_retloanmode() {
		return O_retloanmode;
	}
	public void setO_retloanmode(String o_retloanmode) {
		O_retloanmode = o_retloanmode;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getTolLoanInt_1() {
		return tolLoanInt_1;
	}
	public void setTolLoanInt_1(String tolLoanInt_1) {
		this.tolLoanInt_1 = tolLoanInt_1;
	}
	public String getTolLoanSum_1() {
		return tolLoanSum_1;
	}
	public void setTolLoanSum_1(String tolLoanSum_1) {
		this.tolLoanSum_1 = tolLoanSum_1;
	}
	
	
}
