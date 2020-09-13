package com.yondervision.yfmap.result.hulunbuir;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class HulunbuirAppApi00701Result {
	/**
	 * 借款合同号
	 */
	private String loanaccnum = "";		
	/**
	 * 借款人姓名
	 */
	private String accname = "";	
	/**
	 * 借款人身份证
	 */
	private String certinum = "";	
	/**
	 * 贷款金额
	 */
	private String loanamt = "";	
	/**
	 * 剩余期数
	 */
	private String remainterm = "";	
	/**
	 * 贷款余额
	 */
	private String curbal = "";	
	/**
	 * 资金类型
	 */
	private String loanfundtype = "";	
	/**
	 * 放款日期
	 */
	private String loandate = "";	
	/**
	 * 贷款终止日期
	 */
	private String enddate = "";	
	/**
	 * 交易日期
	 */
	private String transdate = "";	
	/**
	 * 贷款期数
	 */
	private String termnum = "";	
	/**
	 * 摘要
	 */
	private String summarycode = "";	
	/**
	 * 状态
	 */
	private String transstate = "";	
	/**
	 * 借方发生额
	 */
	private String debitamt = "";	
	/**
	 * 贷方发生额
	 */
	private String creditamt = "";	
	/**
	 * 贷款余额
	 */
	private String acLoanamt = "";	
			
	public String getLoanaccnum() {
		return loanaccnum;
	}

	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getLoanamt() {
		return loanamt;
	}

	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}

	public String getRemainterm() {
		return remainterm;
	}

	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
	}

	public String getCurbal() {
		return curbal;
	}

	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}

	public String getLoandate() {
		return loandate;
	}

	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getTermnum() {
		return termnum;
	}

	public void setTermnum(String termnum) {
		this.termnum = termnum;
	}

	public String getSummarycode() {
		return summarycode;
	}

	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}

	public String getTransstate() {
		return transstate;
	}

	public void setTransstate(String transstate) {
		this.transstate = transstate;
	}

	public String getDebitamt() {
		return debitamt;
	}

	public void setDebitamt(String debitamt) {
		this.debitamt = debitamt;
	}

	public String getCreditamt() {
		return creditamt;
	}

	public void setCreditamt(String creditamt) {
		this.creditamt = creditamt;
	}

	public String getAcLoanamt() {
		return acLoanamt;
	}

	public void setAcLoanamt(String acLoanamt) {
		this.acLoanamt = acLoanamt;
	}

	/**
	 * 响应码
	 */
	private String recode="";
	
	/**
	 * 响应信息
	 */
	private String msg="";
	private String fileName = "";	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}

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

	public String getLoanfundtype() {
		return loanfundtype;
	}

	public void setLoanfundtype(String loanfundtype) {
		this.loanfundtype = loanfundtype;
	}
	
}
