package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 *  批量下传  ---   个人贷款    还款计划查询
 */
public class AppApi007_02ZHResult {

	/** 应还日期*/
	private String enddate = "";
	/** 应还合计*/
	private String plansum = "";
	/** 应还本金*/
	private String planprin = "";
	/** 应还利息*/
	private String planint = "";
	
	/** 期数 */
	private String seqnum = "";
	/** 贷款余额 */
	private String loanbal = "";
	
	public String getSeqnum() {
		return seqnum;
	}
	public String getLoanbal() {
		return loanbal;
	}
	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
	public void setLoanbal(String loanbal) {
		this.loanbal = loanbal;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPlansum() {
		return plansum;
	}
	public void setPlansum(String plansum) {
		this.plansum = plansum;
	}
	public String getPlanprin() {
		return planprin;
	}
	public void setPlanprin(String planprin) {
		this.planprin = planprin;
	}
	public String getPlanint() {
		return planint;
	}
	public void setPlanint(String planint) {
		this.planint = planint;
	}
	
	
}
