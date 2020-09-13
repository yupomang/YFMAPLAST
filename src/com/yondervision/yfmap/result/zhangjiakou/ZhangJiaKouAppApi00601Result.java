package com.yondervision.yfmap.result.zhangjiakou;

/** 
* @ClassName: AppApi00601 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class ZhangJiaKouAppApi00601Result {
	
	/**
	 * 贷款账号
	 */
	private String loanaccnum = "";	
	/**
	 * 贷款余额
	 */
	private String loanabaltotle = "";	

	/**
	 * 还款方式
	 */
	private String repaymode = "";
	/**
	 * 贷款利率
	 */
	private String loanrate = "";	
	/**
	 * 合同状态标志
	 */
	private String htztbz = "";
	/**
	 * 贷款状态标志
	 */
	private String dkztbz = "";
	/**
	 * 结清标志
	 */
	private String jqbz = "";
	/**
	 * 放款日期
	 */
	private String fkrq = "";
	/**
	 * 结清日期
	 */
	private String jqrq = "";
	/**
	 * 到期日期
	 */
	private String dqrq = "";
	/**
	 * 还款日期
	 */
	private String repaydate = "";
	/**
	 * 贷款金额
	 */
	private String loanamt = "";	
	/**
	 * 贷款本金余额
	 */
	private String loanbal = "";
	/**
	 * 逾期金额
	 */
	private String overdueamt = "";	
	/**
	 * 逾期本金
	 */
	private String yqbj = "";		
	/**
	 * 逾期利息
	 */
	private String yqlx = "";		
	/**
	 * 逾期罚息
	 */
	private String yqfx = "";		
	/**
	 * 逾期期数
	 */
	private String overdueterms = "";
	/**
	 * 贷款年限
	 */
	private String terms = "";		
	/**
	 * 剩余期限
	 */
	private String syqx = "";
	/**
	 * 购房地址
	 */
	private String gfdz = "";
	/**
	 * 借款人姓名
	 */
	private String loanaccname = "";
	/**
	 * 证件号码
	 */
	private String zjhm = "";
	/**
	 * 证件类型
	 */
	private String zjlx = "";	
	/**
	 * 共同借款人姓名
	 */
	private String gtjkrxm = "";
	/**
	 * 共同借款人证件号
	 */
	private String gtjkrzjh = "";
	/**
	 * 银行还款帐号
	 */
	private String yhhkzh = "";
	/**
	 * 合同到期日期
	 */
	private String htdqrq = "";
	/**
	 * 产权证标志
	 */
	private String cqzbz = "";
	/**
	 * 出证日期
	 */
	private String czrq = "";
	/**
	 * 已还本金
	 */
	private String retprin = "";				
	/**
	 * 月还款额
	 */
	private String monthrepayment = "";				
					
	/**
	 * 还款银行
	 */
	private String repaybank = "";				
	/**
	 * 已还利息
	 */
	private String retint = "";				
			
	/**
	 * 已还期数
	 */
	private String retterms = "";				
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
	public String getLoanaccnum() {
		return loanaccnum;
	}
	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}
	public String getLoanaccname() {
		return loanaccname;
	}
	public void setLoanaccname(String loanaccname) {
		this.loanaccname = loanaccname;
	}
	public String getLoanamt() {
		return loanamt;
	}
	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}
	public String getRetprin() {
		return retprin;
	}
	public void setRetprin(String retprin) {
		this.retprin = retprin;
	}
	public String getLoanbal() {
		return loanbal;
	}
	public void setLoanbal(String loanbal) {
		this.loanbal = loanbal;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getLoanrate() {
		return loanrate;
	}
	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}
	public String getMonthrepayment() {
		return monthrepayment;
	}
	public void setMonthrepayment(String monthrepayment) {
		this.monthrepayment = monthrepayment;
	}
	public String getRepaydate() {
		return repaydate;
	}
	public void setRepaydate(String repaydate) {
		this.repaydate = repaydate;
	}
	public String getRepaybank() {
		return repaybank;
	}
	public void setRepaybank(String repaybank) {
		this.repaybank = repaybank;
	}
	public String getRetint() {
		return retint;
	}
	public void setRetint(String retint) {
		this.retint = retint;
	}
	public String getOverdueamt() {
		return overdueamt;
	}
	public void setOverdueamt(String overdueamt) {
		this.overdueamt = overdueamt;
	}
	public String getOverdueterms() {
		return overdueterms;
	}
	public void setOverdueterms(String overdueterms) {
		this.overdueterms = overdueterms;
	}
	public String getRetterms() {
		return retterms;
	}
	public void setRetterms(String retterms) {
		this.retterms = retterms;
	}
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
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getGfdz() {
		return gfdz;
	}
	public void setGfdz(String gfdz) {
		this.gfdz = gfdz;
	}
	public String getYhhkzh() {
		return yhhkzh;
	}
	public void setYhhkzh(String yhhkzh) {
		this.yhhkzh = yhhkzh;
	}
	public String getHtdqrq() {
		return htdqrq;
	}
	public void setHtdqrq(String htdqrq) {
		this.htdqrq = htdqrq;
	}
	public String getCqzbz() {
		return cqzbz;
	}
	public void setCqzbz(String cqzbz) {
		this.cqzbz = cqzbz;
	}
	public String getCzrq() {
		return czrq;
	}
	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}
	public String getJqbz() {
		return jqbz;
	}
	public void setJqbz(String jqbz) {
		this.jqbz = jqbz;
	}
	public String getFkrq() {
		return fkrq;
	}
	public void setFkrq(String fkrq) {
		this.fkrq = fkrq;
	}
	public String getJqrq() {
		return jqrq;
	}
	public void setJqrq(String jqrq) {
		this.jqrq = jqrq;
	}
	public String getGtjkrxm() {
		return gtjkrxm;
	}
	public void setGtjkrxm(String gtjkrxm) {
		this.gtjkrxm = gtjkrxm;
	}
	public String getGtjkrzjh() {
		return gtjkrzjh;
	}
	public void setGtjkrzjh(String gtjkrzjh) {
		this.gtjkrzjh = gtjkrzjh;
	}
	public String getHtztbz() {
		return htztbz;
	}
	public void setHtztbz(String htztbz) {
		this.htztbz = htztbz;
	}
	public String getDkztbz() {
		return dkztbz;
	}
	public void setDkztbz(String dkztbz) {
		this.dkztbz = dkztbz;
	}
	public String getDqrq() {
		return dqrq;
	}
	public void setDqrq(String dqrq) {
		this.dqrq = dqrq;
	}
	public String getSyqx() {
		return syqx;
	}
	public void setSyqx(String syqx) {
		this.syqx = syqx;
	}
	public String getYqbj() {
		return yqbj;
	}
	public void setYqbj(String yqbj) {
		this.yqbj = yqbj;
	}
	public String getYqlx() {
		return yqlx;
	}
	public void setYqlx(String yqlx) {
		this.yqlx = yqlx;
	}
	public String getYqfx() {
		return yqfx;
	}
	public void setYqfx(String yqfx) {
		this.yqfx = yqfx;
	}
	public String getLoanabaltotle() {
		return loanabaltotle;
	}
	public void setLoanabaltotle(String loanabaltotle) {
		this.loanabaltotle = loanabaltotle;
	}
}
