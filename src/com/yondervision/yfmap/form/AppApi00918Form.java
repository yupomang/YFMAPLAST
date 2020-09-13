package com.yondervision.yfmap.form;


/** 
 * 贷后变更试算——包钢
* @ClassName: AppApi00918Form 
* @Description: TODO
* 
*/ 
public class AppApi00918Form extends AppApiCommonForm{
	
	/**
	 * 01：提前部分还款
	 * 02：提前全部还款
	 * 03：缩期
	 */
	private String ssType;
	
	/**
	 * 合同号
	 */
	private String loancontrnum;
	
	/**
	 * 预计开始日期
	 */
	private String begindate;
	
	/**
	 * 缩期后贷款年限
	 */
	private String loanterm1;
	
	/**
	 * 家庭月收入
	 */
	private String monthsal;
	

	/**
	 * 提前部分还款金额
	 */
	private String realamt;
	
	private String trancode;
	
	public String getSsType() {
		return ssType;
	}

	public void setSsType(String ssType) {
		this.ssType = ssType;
	}

	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getBegindate() {
		return begindate;
	}

	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	public String getLoanterm1() {
		return loanterm1;
	}

	public void setLoanterm1(String loanterm1) {
		this.loanterm1 = loanterm1;
	}

	public String getMonthsal() {
		return monthsal;
	}

	public void setMonthsal(String monthsal) {
		this.monthsal = monthsal;
	}

	public String getRealamt() {
		return realamt;
	}

	public void setRealamt(String realamt) {
		this.realamt = realamt;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}
	
	
}
