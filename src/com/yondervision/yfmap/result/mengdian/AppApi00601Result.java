package com.yondervision.yfmap.result.mengdian;

/** 
* @ClassName: AppApi00601 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class AppApi00601Result {
	/**
	 * 贷款账号
	 */
	private String accname = "";				
			
	/**
	 * 借款人姓名
	 */
	private String apploanterm = "";				
	/**
	 * 贷款金额
	 */
	private String begdate = "";				
	/**
	 * 已还本金
	 */
	private String certinum = "";				
	/**
	 * 贷款本金余额
	 */
	private String curbal = "";				
	/**
	 * 贷款年限
	 */
	private String currintamt = "";				
	/**
	 * 贷款利率
	 */
	private String enddate = "";				
	/**
	 * 还款方式
	 */
	private String flag = "";				
	/**
	 * 月还款额
	 */
	private String initialbal = "";				
	/**
	 * 还款日期
	 */
	private String instcode = "";	
	private String linkmanphone = "";			
	/**
	 * 还款银行
	 */
	private String loanaccnum = "";				
	/**
	 * 已还利息
	 */
	private String loanamt = "";
	/**
	 * 已还利息
	 */
	private String loancontrnum = "";
	/**
	 * 逾期金额
	 */
	private String loanhousenum = "";				
	/**
	 * 逾期期数
	 */
	private String loanrate = "";	
	private String monthrepayamt = "";
	/**
	 * 已还期数
	 */
	private String overdueint = "";				
	/**
	 * 
	 */
	private String overoweint = "";				
	/**
	 * 
	 */
	private String oweint = "";
	/**
	 * 
	 */
	private String oweprin = "";
	private String owepun = "";
	/**
	 * 
	 */
	private String payeename = "";
	private String plandedprin = "";
	/**
	 * 交易代码
	 */
	private String planprin="";
	/**
	 * 返回时间
	 */
	private String repaydate="";
	/**
	 * 发送方流水号
	 */
	private String usebal="";
	/**
	 * 安全标识
	 */
	private String remainterm="";
	/**
	 * 中心流水号
	 */
	private String centerSeqno="";
	private String isloanflag = "";
	private String accinstname = "";
	/**
	 * 响应码
	 */
	private String recode="";
	
	/**
	 * 响应信息
	 */
	private String msg="";

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getApploanterm() {
		return apploanterm;
	}

	public void setApploanterm(String apploanterm) {
		this.apploanterm = apploanterm;
	}

	public String getBegdate() {
		return begdate;
	}

	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getCurbal() {
		return curbal;
	}

	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}

	public String getCurrintamt() {
		return currintamt;
	}

	public void setCurrintamt(String currintamt) {
		this.currintamt = currintamt;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getInitialbal() {
		return initialbal;
	}

	public void setInitialbal(String initialbal) {
		this.initialbal = initialbal;
	}

	public String getInstcode() {
		return instcode;
	}

	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}

	public String getLoanaccnum() {
		return loanaccnum;
	}

	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}

	public String getLoanamt() {
		return loanamt;
	}

	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}

	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getLoanhousenum() {
		return loanhousenum;
	}

	public void setLoanhousenum(String loanhousenum) {
		this.loanhousenum = loanhousenum;
	}

	public String getLoanrate() {
		return loanrate;
	}

	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}

	public String getOverdueint() {
		return overdueint;
	}

	public void setOverdueint(String overdueint) {
		this.overdueint = overdueint;
	}

	public String getOveroweint() {
		return overoweint;
	}

	public void setOveroweint(String overoweint) {
		this.overoweint = overoweint;
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

	public String getOwepun() {
		return owepun;
	}

	public void setOwepun(String owepun) {
		this.owepun = owepun;
	}

	public String getPayeename() {
		return payeename;
	}

	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}

	public String getPlandedprin() {
		return plandedprin;
	}

	public void setPlandedprin(String plandedprin) {
		this.plandedprin = plandedprin;
	}

	public String getPlanprin() {
		return planprin;
	}

	public void setPlanprin(String planprin) {
		this.planprin = planprin;
	}

	public String getRepaydate() {
		return repaydate;
	}

	public void setRepaydate(String repaydate) {
		this.repaydate = repaydate;
	}

	public String getUsebal() {
		return usebal;
	}

	public void setUsebal(String usebal) {
		this.usebal = usebal;
	}

	public String getRemainterm() {
		return remainterm;
	}

	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
	}

	public String getCenterSeqno() {
		return centerSeqno;
	}

	public void setCenterSeqno(String centerSeqno) {
		this.centerSeqno = centerSeqno;
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

	public String getLinkmanphone() {
		return linkmanphone;
	}

	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
	}

	public String getMonthrepayamt() {
		return monthrepayamt;
	}

	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}

}
