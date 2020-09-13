package com.yondervision.yfmap.result.baogang;

/** 
* @ClassName: AppApi00601 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class BaoGangAppApi00601Result {
	/**
	 * 借款合同号
	 */
	private String loancontrnum = "";				
			
	/**
	 * 借款人姓名
	 */
	private String accname1 = "";				
	/**
	 * 个人账号
	 */
	private String accnum = "";				
	/**
	 * 查询人类型
	 */
	private String accleix = "";				
	/**
	 * 贷款中角色
	 */
	private String accname2 = "";				
	/**
	 * 可对冲金额
	 */
	private String newmonth = "";				
	/**
	 * 对冲后月还款额
	 */
	private String dMonthPay = "";				
	/**
	 * 剩余利息
	 */
	private String apprloanint = "";				
	/**
	 * 剩余金额
	 */
	private String apprloansum = "";				
	/**
	 * 剩余期限
	 */
	private String apprloanterm = "";				
	/**
	 * 到期日期
	 */
	private String attermdate = "";				
	/**
	 * 还款账号
	 */
	private String bankaccnum = "";				
	/**
	 * 还款银行
	 */
	private String bankname = "";				
	/**
	 * 起始日期
	 */
	private String beginintdate = "";				
	/**
	 * 现行月利率
	 */
	private String currate = "";	
	/**
	 * 贷款金额
	 */
	private String loansum = "";				
	/**
	 * 贷款期限
	 */
	private String loanterm = "";	
	/**
	 * 年度月应还款额
	 */
	private String monthpay = "";				
	/**
	 * 月缴存额
	 */
	private String pmonthpay = "";	
	/**
	 * 单位名称
	 */
	private String unitaccname = "";	
	/**
	 * 已还本金
	 */
	private String infactprin = "";	
	/**
	 * 已还利息
	 */
	private String infactint = "";	
	/**
	 * 已还期数
	 */
	private String freeuse4 = "";	
	/**
	 * 
	 */
	private String freeuse1 = "";				
	/**
	 * 
	 */
	private String freeuse2 = "";
	/**
	 * 
	 */
	private String freeuse3 = "";

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
	
	private String isloanflag = "";
	private String accinstname = "";
	/**
	 * 响应信息
	 */
	private String msg="";
	
	public String getFreeuse1() {
		return freeuse1;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	public String getFreeuse2() {
		return freeuse2;
	}
	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}
	public String getFreeuse3() {
		return freeuse3;
	}
	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}
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
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getAccinstname() {
		return accinstname;
	}
	public void setAccinstname(String accinstname) {
		this.accinstname = accinstname;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getAccname1() {
		return accname1;
	}
	public void setAccname1(String accname1) {
		this.accname1 = accname1;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAccleix() {
		return accleix;
	}
	public void setAccleix(String accleix) {
		this.accleix = accleix;
	}
	public String getAccname2() {
		return accname2;
	}
	public void setAccname2(String accname2) {
		this.accname2 = accname2;
	}
	public String getNewmonth() {
		return newmonth;
	}
	public void setNewmonth(String newmonth) {
		this.newmonth = newmonth;
	}
	public String getdMonthPay() {
		return dMonthPay;
	}
	public void setdMonthPay(String dMonthPay) {
		this.dMonthPay = dMonthPay;
	}
	public String getApprloanint() {
		return apprloanint;
	}
	public void setApprloanint(String apprloanint) {
		this.apprloanint = apprloanint;
	}
	public String getApprloansum() {
		return apprloansum;
	}
	public void setApprloansum(String apprloansum) {
		this.apprloansum = apprloansum;
	}
	public String getApprloanterm() {
		return apprloanterm;
	}
	public void setApprloanterm(String apprloanterm) {
		this.apprloanterm = apprloanterm;
	}
	public String getAttermdate() {
		return attermdate;
	}
	public void setAttermdate(String attermdate) {
		this.attermdate = attermdate;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBeginintdate() {
		return beginintdate;
	}
	public void setBeginintdate(String beginintdate) {
		this.beginintdate = beginintdate;
	}
	public String getCurrate() {
		return currate;
	}
	public void setCurrate(String currate) {
		this.currate = currate;
	}
	public String getLoansum() {
		return loansum;
	}
	public void setLoansum(String loansum) {
		this.loansum = loansum;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getMonthpay() {
		return monthpay;
	}
	public void setMonthpay(String monthpay) {
		this.monthpay = monthpay;
	}
	public String getPmonthpay() {
		return pmonthpay;
	}
	public void setPmonthpay(String pmonthpay) {
		this.pmonthpay = pmonthpay;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getFreeuse4() {
		return freeuse4;
	}
	public void setFreeuse4(String freeuse4) {
		this.freeuse4 = freeuse4;
	}
	public String getInfactprin() {
		return infactprin;
	}
	public void setInfactprin(String infactprin) {
		this.infactprin = infactprin;
	}
	public String getInfactint() {
		return infactint;
	}
	public void setInfactint(String infactint) {
		this.infactint = infactint;
	}
				
}
