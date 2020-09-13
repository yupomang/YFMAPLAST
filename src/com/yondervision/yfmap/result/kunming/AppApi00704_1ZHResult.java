package com.yondervision.yfmap.result.kunming;

/**
 * 异地还款计划查询
 * @author fxliu
 *
 */
public class AppApi00704_1ZHResult {

	/**
	 * 应还日期
	 */
	private String payDate = "";
	/**
	 * 应还本金
	 */
	private String principal = "";
	/**
	 * 应还利息
	 */
	private String interest = "";
	/**
	 * 应还合计
	 */
	private String totalMoney = "";
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
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
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	
}
