package com.yondervision.yfmap.result.kunming;

/**
 * 还款明细查询
 * @author fxliu
 *
 */
public class AppApi00703_1ZHResult {

	/**
	 * 业务日期
	 */
	private String busDate = "";
	/**
	 * 期数
	 */
	private String terms = "";
	/**
	 * 业务类型
	 */
	private String busType = "";
	/**
	 * 本金金额
	 */
	private String principal = "";
	/**
	 * 利息金额
	 */
	private String interest = "";
	/**
	 * 罚息金额
	 */
	private String penaltyInterest = "";
	/**
	 * 合计金额
	 */
	private String totalMoney = "";
	public String getBusDate() {
		return busDate;
	}
	public void setBusDate(String busDate) {
		this.busDate = busDate;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPenaltyInterest() {
		return penaltyInterest;
	}
	public void setPenaltyInterest(String penaltyInterest) {
		this.penaltyInterest = penaltyInterest;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
		
}
