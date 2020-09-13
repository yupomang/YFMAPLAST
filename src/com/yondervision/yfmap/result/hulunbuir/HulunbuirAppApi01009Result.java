package com.yondervision.yfmap.result.hulunbuir;

/**
 *
 */
public class HulunbuirAppApi01009Result {
	/** 经办银行 */						
	private String agentbankcode = "";						
	/** 产权证号 */						
	private String cerrightnum = "";
	/** 证件号码 */						
	private String certinum = "";
	/** 证件类型 */						
	private String certitype = "";						
	/** 结清日期 */						
	private String cleardate = "";						
	/** 合同签订日期 */						
	private String contrsigndate = "";						
	/** 抵押物他项权证号 */						
	private String elsrightnum = "";						
	/** 贷款金额 */						
	private String loanamt = "";	
	/** 借款人姓名 */						
	private String loaneename = "";	
	/** 贷款期限 */						
	private String loanterm = "";		
	/** 配偶证件号码 */						
	private String matecertinum = "";						
	/** 配偶证件类型 */						
	private String matecertitype = "";						
	/** 配偶姓名 */						
	private String matename = "";						
	/** 抵押物地址 */						
	private String mortaddr = "";						
	/** 抵押人姓名 */						
	private String mortname = "";						
	/** 抵押物类型 */						
	private String mortproperty = "";	
	/** 个人账号 */						
	private String accnum = "";
	/** 金额 */						
	private String amt = "";
	/** 银行账号 */						
	private String bankaccnum = "";
	/** 银行代码 */						
	private String bankcode = "";
	/** 控制标志 */						
	private String ctlflag = "";
	/** 委托代扣协议标志 */						
	private String dedprotoflag = "";
	/** 联系电话 */						
	private String linkphone = "";
	/** 贷款账号 */						
	private String loanaccnum = "";
	/** 配偶个人账号 */						
	private String mateaccnum = "";
	/** 总月还款额 */						
	private String totmonrepamt = "";
	/** 收款人名称 */						
	private String payeename = "";
	/** 回款金额 */						
	private String repayamt = "";
	/** 变更项目 */						
	private String acDescfieldname  = "";
	/** 变更前值 */						
	private String bfchgval  = "";
	/** 变更后值 */						
	private String afchgval  = "";
	/** 实还本金 */						
	private String repayprin  = "";
	/** 实还利息 */						
	private String repayint  = "";
	/** 实还罚息 */						
	private String repaypun  = "";
	/**
	 * 交易代码
	 */
	private String transCode="";
	/**
	 * 返回日期
	 */
	private String sendDate="";
	/**
	 * 返回时间
	 */
	private String sendTime="";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 安全标识
	 */
	private String key="";
	/**
	 * 中心流水号
	 */
	private String centerSeqno="";
	/**
	 * 响应码
	 */
	private String recode="";
	/**
	 * 响应信息
	 */
	private String msg="";
	
	private String fileName = "";	
	private String apprnum="";
	
	private String inacchostsernum = "";	
	
	public String getTransCode() {
		return transCode;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getInacchostsernum() {
		return inacchostsernum;
	}
	public void setInacchostsernum(String inacchostsernum) {
		this.inacchostsernum = inacchostsernum;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCenterSeqno() {
		return centerSeqno;
	}
	public void setCenterSeqno(String centerSeqno) {
		this.centerSeqno = centerSeqno;
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
	public String getAgentbankcode() {
		return agentbankcode;
	}
	public void setAgentbankcode(String agentbankcode) {
		this.agentbankcode = agentbankcode;
	}
	public String getCerrightnum() {
		return cerrightnum;
	}
	public void setCerrightnum(String cerrightnum) {
		this.cerrightnum = cerrightnum;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getCertitype() {
		return certitype;
	}
	public void setCertitype(String certitype) {
		this.certitype = certitype;
	}
	public String getCleardate() {
		return cleardate;
	}
	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}
	public String getContrsigndate() {
		return contrsigndate;
	}
	public void setContrsigndate(String contrsigndate) {
		this.contrsigndate = contrsigndate;
	}
	public String getElsrightnum() {
		return elsrightnum;
	}
	public void setElsrightnum(String elsrightnum) {
		this.elsrightnum = elsrightnum;
	}
	public String getLoanamt() {
		return loanamt;
	}
	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}
	public String getLoaneename() {
		return loaneename;
	}
	public void setLoaneename(String loaneename) {
		this.loaneename = loaneename;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatecertitype() {
		return matecertitype;
	}
	public void setMatecertitype(String matecertitype) {
		this.matecertitype = matecertitype;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMortaddr() {
		return mortaddr;
	}
	public void setMortaddr(String mortaddr) {
		this.mortaddr = mortaddr;
	}
	public String getMortname() {
		return mortname;
	}
	public void setMortname(String mortname) {
		this.mortname = mortname;
	}
	public String getMortproperty() {
		return mortproperty;
	}
	public void setMortproperty(String mortproperty) {
		this.mortproperty = mortproperty;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getCtlflag() {
		return ctlflag;
	}
	public void setCtlflag(String ctlflag) {
		this.ctlflag = ctlflag;
	}
	public String getDedprotoflag() {
		return dedprotoflag;
	}
	public void setDedprotoflag(String dedprotoflag) {
		this.dedprotoflag = dedprotoflag;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public String getLoanaccnum() {
		return loanaccnum;
	}
	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}
	public String getMateaccnum() {
		return mateaccnum;
	}
	public void setMateaccnum(String mateaccnum) {
		this.mateaccnum = mateaccnum;
	}
	public String getTotmonrepamt() {
		return totmonrepamt;
	}
	public void setTotmonrepamt(String totmonrepamt) {
		this.totmonrepamt = totmonrepamt;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	public String getRepayamt() {
		return repayamt;
	}
	public void setRepayamt(String repayamt) {
		this.repayamt = repayamt;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAcDescfieldname() {
		return acDescfieldname;
	}
	public void setAcDescfieldname(String acDescfieldname) {
		this.acDescfieldname = acDescfieldname;
	}
	public String getBfchgval() {
		return bfchgval;
	}
	public void setBfchgval(String bfchgval) {
		this.bfchgval = bfchgval;
	}
	public String getAfchgval() {
		return afchgval;
	}
	public void setAfchgval(String afchgval) {
		this.afchgval = afchgval;
	}
	public String getRepayprin() {
		return repayprin;
	}
	public void setRepayprin(String repayprin) {
		this.repayprin = repayprin;
	}
	public String getRepayint() {
		return repayint;
	}
	public void setRepayint(String repayint) {
		this.repayint = repayint;
	}
	public String getRepaypun() {
		return repaypun;
	}
	public void setRepaypun(String repaypun) {
		this.repaypun = repaypun;
	}
	
}
