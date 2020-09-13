package com.yondervision.yfmap.result.weihai;


public class WeiHaiAppApi00701Result {
	/** 期数 */						
	private String seqNum = "";						
	/** 还款年月 */						
	private String endDate = "";						
	/** 还款日 */						
	private String infactDate = "";	
	/** 本金 */						
	private String planPrin = "";
	/** 利息 */						
	private String planInt = "";							
	/** 罚息 */						
	private String factRetPun = "";
	/** 合计 */						
	private String planSum = "";
	/** 是否已还 */						
	private String curSeqStateP = "";
	/** 是否逾期 */						
	private String curSeqStateD = "";
	/** 余额 */						
	private String endLoanBal = "";
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getInfactDate() {
		return infactDate;
	}
	public void setInfactDate(String infactDate) {
		this.infactDate = infactDate;
	}
	public String getPlanPrin() {
		return planPrin;
	}
	public void setPlanPrin(String planPrin) {
		this.planPrin = planPrin;
	}
	public String getPlanInt() {
		return planInt;
	}
	public void setPlanInt(String planInt) {
		this.planInt = planInt;
	}
	public String getFactRetPun() {
		return factRetPun;
	}
	public void setFactRetPun(String factRetPun) {
		this.factRetPun = factRetPun;
	}
	public String getPlanSum() {
		return planSum;
	}
	public void setPlanSum(String planSum) {
		this.planSum = planSum;
	}
	public String getCurSeqStateP() {
		return curSeqStateP;
	}
	public void setCurSeqStateP(String curSeqStateP) {
		this.curSeqStateP = curSeqStateP;
	}
	public String getCurSeqStateD() {
		return curSeqStateD;
	}
	public void setCurSeqStateD(String curSeqStateD) {
		this.curSeqStateD = curSeqStateD;
	}
	public String getEndLoanBal() {
		return endLoanBal;
	}
	public void setEndLoanBal(String endLoanBal) {
		this.endLoanBal = endLoanBal;
	}
}
