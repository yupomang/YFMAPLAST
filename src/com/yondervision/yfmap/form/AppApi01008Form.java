package com.yondervision.yfmap.form;

public class AppApi01008Form extends AppApiCommonForm {
	
	/**
	 * 提取原因
	 */
	private String drawreason="";
	
	/**
	 * 变更登记类型
	 */
	private String drawSignType="";
	
	/**
	 * 合同号
	 */
	private String loancontrnum="";
	
	/**
	 * 还款方式
	 */
	private String repaymentStatus="";
	
	/**
	 * 还款金额
	 */
	private String repayment="";
	
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public String getDrawSignType() {
		return drawSignType;
	}
	public void setDrawSignType(String drawSignType) {
		this.drawSignType = drawSignType;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	
}
