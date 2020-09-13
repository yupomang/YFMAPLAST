package com.yondervision.yfmap.result.hulunbuir;

/** 
* @ClassName: AppApi00601 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class HulunbuirAppApi00601Result {
	/**
	 * 借款合同号
	 */
	private String loancontrnum = "";				
			
	/**
	 * 借款人姓名
	 */
	private String accname = "";				
	/**
	 * 个人账号
	 */
	private String accnum = "";		
	/**
	 * 贷款申请期限
	 */
	private String apploanterm = "";	
	/**
	 * 开始日期
	 */
	private String begdate = "";	
	/**
	 * 证件号码
	 */
	private String certinum = "";	
	/**
	 * 当前余额
	 */
	private String curbal = "";	
	/**
	 * 当期利息金额
	 */
	private String currintamt = "";	
	/**
	 * 终止日期
	 */
	private String enddate = "";	
	/**
	 * 标志
	 */
	private String flag = "";	
	/**
	 * 期初余额
	 */
	private String initialbal = "";	
	/**
	 * 机构代码
	 */
	private String instcode = "";	
	/**
	 * 联系人电话
	 */
	private String linkmanphone = "";	
	/**
	 * 贷款账号
	 */
	private String loanaccnum = "";	
	/**
	 * 贷款金额
	 */
	private String loanamt = "";	
	/**
	 * 第N套房贷款
	 */
	private String loanhousenum = "";	
	/**
	 * 贷款利率
	 */
	private String loanrate = "";	
	/**
	 * 贷款月还款额
	 */
	private String monthrepayamt = "";	
	
	/**
	 * 逾期利息
	 */
	private String overdueint = "";	
	
	/**
	 * 已还利息
	 */
	private String overoweint = "";	
	
	/**
	 * 应还未还利息
	 */
	private String oweint = "";	
	
	/**
	 * 应还未还本金
	 */
	private String oweprin = "";	
	
	/**
	 * 未还罚息
	 */
	private String owepun = "";	
	
	/**
	 * 收款人名称
	 */
	private String payeename = "";	
	
	/**
	 * 本期应代扣本金
	 */
	private String plandedprin = "";	
	
	/**
	 * 应还本金
	 */
	private String planprin = "";	
	
	/**
	 * 剩余期限（月）
	 */
	private String remainterm = "";	
	
	/**
	 * 还款日期
	 */
	private String repaydate = "";	
	
	/**
	 * 可用余额
	 */
	private String usebal = "";	
	
	/**
	 * 
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
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
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
	public String getLinkmanphone() {
		return linkmanphone;
	}
	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
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
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
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
	public String getRemainterm() {
		return remainterm;
	}
	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
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
	public String getFreeuse4() {
		return freeuse4;
	}
	public void setFreeuse4(String freeuse4) {
		this.freeuse4 = freeuse4;
	}
}
