package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi03825ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";

	
	private String 	loanamt;//最高可贷额度
	private String 	monthrepayamt;//首月还款金额
	private String 	loanterm;//最短贷款年限
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
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getLoanamt() {
		return loanamt;
	}
	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
}

