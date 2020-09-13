package com.yondervision.yfmap.result.baogang;

/**
 *
 */
public class BaoGangAppApi00918Result {
	/** 还款后剩余本金*/						
	private String ccurdaybal = "";						
	/** 提前部分还款最小额*/						
	private String ctrlamt = "";						
	/** 利息*/						
	private String dInt1 = "";						
	/** 应还总利息 */						
	private String loanint = "";						
	/** 未还利息总额 */						
	private String loanintbal = "";						
	/** 贷款期限(月)*/						
	private String loanterm = "";						
	/**新产生利息 */						
	private String newint = "";						
	/** 对冲后月还款额*/						
	private String nmonthpay = "";						
	/** 未到期本金 */						
	private String nowprin = "";						
	/** 欠款合计 */						
	private String oweamt = "";		
	/** 欠利息 */						
	private String oweint = "";	
	/** 欠本金 */						
	private String oweprin = "";	
	/** 部分还款后月还款额 */						
	private String pmonthpay = "";	
	/** 欠罚息*/						
	private String pun = "";
	/** 利率 */						
	private String rate = "";
	/** 借款人姓名*/						
	private String accname1 = "";
	/** 借款人身份证号 */						
	private String cerid1 = "";
	/** 贷款余额 */						
	private String loanbal = "";
	/** 缩期后月还款额 */						
	private String monthpay = "";
	/** 缩期后还款能力 */						
	private String percent = "";
	/** 对冲后月还款额 */						
	private String dMonthPay = "";
	/** 可对冲金额 */						
	private String newmonth = "";
	/** 还款前月还款额 */						
	private String paymont = "";
	/** 还款类型*/						
	private String rettype = "";
	/** 还款后剩余利息*/						
	private String wnewint = "";
	/** 剩余利息*/						
	private String lloanintbal = "";
	/** 提前全部还款应还金额*/						
	private String tolamt = "";
	/** 变更前贷款剩余期限*/	
	private String aaremainterm = "";
	/** 变更前贷款期限*/	
	private String aloanterm = "";
	/** 缩期后贷款期限*/	
	private String aloanterm1 = "";
	/** 变更前月还款额*/	
	private String amonthpay = "";
	/** 变更前剩余利息*/	
	private String dPreInt = "";
	/** 对冲后月还款额*/	
	private String hegdepay = "";
	/** 缩期后剩余利息*/	
	private String newloanint = "";
	/** 缩期后贷款剩余期限*/	
	private String aloanterm2 = "";
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
	
	
	public String getTransCode() {
		return transCode;
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
	public String getCcurdaybal() {
		return ccurdaybal;
	}
	public void setCcurdaybal(String ccurdaybal) {
		this.ccurdaybal = ccurdaybal;
	}
	public String getCtrlamt() {
		return ctrlamt;
	}
	public void setCtrlamt(String ctrlamt) {
		this.ctrlamt = ctrlamt;
	}
	public String getdInt1() {
		return dInt1;
	}
	public void setdInt1(String dInt1) {
		this.dInt1 = dInt1;
	}
	public String getLoanint() {
		return loanint;
	}
	public void setLoanint(String loanint) {
		this.loanint = loanint;
	}
	public String getLoanintbal() {
		return loanintbal;
	}
	public void setLoanintbal(String loanintbal) {
		this.loanintbal = loanintbal;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getNewint() {
		return newint;
	}
	public void setNewint(String newint) {
		this.newint = newint;
	}
	public String getNmonthpay() {
		return nmonthpay;
	}
	public void setNmonthpay(String nmonthpay) {
		this.nmonthpay = nmonthpay;
	}
	public String getNowprin() {
		return nowprin;
	}
	public void setNowprin(String nowprin) {
		this.nowprin = nowprin;
	}
	public String getOweamt() {
		return oweamt;
	}
	public void setOweamt(String oweamt) {
		this.oweamt = oweamt;
	}
	public String getOweint() {
		return oweint;
	}
	public void setOweint(String oweint) {
		this.oweint = oweint;
	}
	public String getOweprin() {
		return oweprin;
	}
	public void setOweprin(String oweprin) {
		this.oweprin = oweprin;
	}
	public String getPmonthpay() {
		return pmonthpay;
	}
	public void setPmonthpay(String pmonthpay) {
		this.pmonthpay = pmonthpay;
	}
	public String getPun() {
		return pun;
	}
	public void setPun(String pun) {
		this.pun = pun;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAccname1() {
		return accname1;
	}
	public void setAccname1(String accname1) {
		this.accname1 = accname1;
	}
	public String getCerid1() {
		return cerid1;
	}
	public void setCerid1(String cerid1) {
		this.cerid1 = cerid1;
	}
	public String getLoanbal() {
		return loanbal;
	}
	public void setLoanbal(String loanbal) {
		this.loanbal = loanbal;
	}
	public String getMonthpay() {
		return monthpay;
	}
	public void setMonthpay(String monthpay) {
		this.monthpay = monthpay;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getdMonthPay() {
		return dMonthPay;
	}
	public void setdMonthPay(String dMonthPay) {
		this.dMonthPay = dMonthPay;
	}
	public String getNewmonth() {
		return newmonth;
	}
	public void setNewmonth(String newmonth) {
		this.newmonth = newmonth;
	}
	public String getPaymont() {
		return paymont;
	}
	public void setPaymont(String paymont) {
		this.paymont = paymont;
	}
	public String getRettype() {
		return rettype;
	}
	public void setRettype(String rettype) {
		this.rettype = rettype;
	}
	public String getWnewint() {
		return wnewint;
	}
	public void setWnewint(String wnewint) {
		this.wnewint = wnewint;
	}
	public String getLloanintbal() {
		return lloanintbal;
	}
	public void setLloanintbal(String lloanintbal) {
		this.lloanintbal = lloanintbal;
	}
	public String getTolamt() {
		return tolamt;
	}
	public void setTolamt(String tolamt) {
		this.tolamt = tolamt;
	}
	public String getAaremainterm() {
		return aaremainterm;
	}
	public void setAaremainterm(String aaremainterm) {
		this.aaremainterm = aaremainterm;
	}
	public String getAloanterm() {
		return aloanterm;
	}
	public void setAloanterm(String aloanterm) {
		this.aloanterm = aloanterm;
	}
	public String getAloanterm1() {
		return aloanterm1;
	}
	public void setAloanterm1(String aloanterm1) {
		this.aloanterm1 = aloanterm1;
	}
	public String getAmonthpay() {
		return amonthpay;
	}
	public void setAmonthpay(String amonthpay) {
		this.amonthpay = amonthpay;
	}
	public String getdPreInt() {
		return dPreInt;
	}
	public void setdPreInt(String dPreInt) {
		this.dPreInt = dPreInt;
	}
	public String getHegdepay() {
		return hegdepay;
	}
	public void setHegdepay(String hegdepay) {
		this.hegdepay = hegdepay;
	}
	public String getNewloanint() {
		return newloanint;
	}
	public void setNewloanint(String newloanint) {
		this.newloanint = newloanint;
	}
	public String getAloanterm2() {
		return aloanterm2;
	}
	public void setAloanterm2(String aloanterm2) {
		this.aloanterm2 = aloanterm2;
	}
	
}
