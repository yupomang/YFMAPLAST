package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 * 网厅冲还贷处理
 */
public class AppApi03603ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	
	/** 流水号*/
	private String tranSeq = "";
	
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	
	/** 个人账号*/
	private String accnum = "";
	/** 提前全部还款金额*/
	private String allamt = "";	
	/** 贷款账号*/
	private String lnaccnum = "";
	/** 还款后到期日*/
	private String newenddate = "";
	/** 还款后欠息*/
	private String newint = "";	
	/** 还款后欠本*/
	private String newprin = "";
	/** 原始到期日*/
	private String oldenddate = "";
	/** 欠息*/
	private String oldint = "";	
	/** 欠本金*/
	private String oldprin = "";
	/** 欠罚息*/
	private String oldpunint = "";
	/** 欠款合计*/
	private String oldsum = "";	
	/** 提前部分还款临界额*/
	private String preamt = "";
	/** 本次提前部分还款后贷款金额*/
	private String rembal = "";
	/** 剩余还款期数*/
	private String remterm = "";	
	/** 还款方式*/
	private String retmode = "";
	/** 还款周期*/
	private String retzq = "";
	/** 收款账号*/
	private String revbankcode = "";	
	/** 收款开户行*/
	private String revbankname = "";
	/** 余额 */
	private String balance = "";
	/** 记账标记 ，0-否，1-是*/
	private String acc_flag = "";
	/** 记账金额 */
	private String acc_amt = "";
	
	public String getAcc_flag() {
		return acc_flag;
	}
	public String getAcc_amt() {
		return acc_amt;
	}
	public void setAcc_flag(String acc_flag) {
		this.acc_flag = acc_flag;
	}
	public void setAcc_amt(String acc_amt) {
		this.acc_amt = acc_amt;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
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
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAllamt() {
		return allamt;
	}
	public void setAllamt(String allamt) {
		this.allamt = allamt;
	}
	public String getLnaccnum() {
		return lnaccnum;
	}
	public void setLnaccnum(String lnaccnum) {
		this.lnaccnum = lnaccnum;
	}
	public String getNewenddate() {
		return newenddate;
	}
	public void setNewenddate(String newenddate) {
		this.newenddate = newenddate;
	}
	public String getNewint() {
		return newint;
	}
	public void setNewint(String newint) {
		this.newint = newint;
	}
	public String getNewprin() {
		return newprin;
	}
	public void setNewprin(String newprin) {
		this.newprin = newprin;
	}
	public String getOldenddate() {
		return oldenddate;
	}
	public void setOldenddate(String oldenddate) {
		this.oldenddate = oldenddate;
	}
	public String getOldint() {
		return oldint;
	}
	public void setOldint(String oldint) {
		this.oldint = oldint;
	}
	public String getOldprin() {
		return oldprin;
	}
	public void setOldprin(String oldprin) {
		this.oldprin = oldprin;
	}
	public String getOldpunint() {
		return oldpunint;
	}
	public void setOldpunint(String oldpunint) {
		this.oldpunint = oldpunint;
	}
	public String getOldsum() {
		return oldsum;
	}
	public void setOldsum(String oldsum) {
		this.oldsum = oldsum;
	}
	public String getPreamt() {
		return preamt;
	}
	public void setPreamt(String preamt) {
		this.preamt = preamt;
	}
	public String getRembal() {
		return rembal;
	}
	public void setRembal(String rembal) {
		this.rembal = rembal;
	}
	public String getRemterm() {
		return remterm;
	}
	public void setRemterm(String remterm) {
		this.remterm = remterm;
	}
	public String getRetmode() {
		return retmode;
	}
	public void setRetmode(String retmode) {
		this.retmode = retmode;
	}
	public String getRetzq() {
		return retzq;
	}
	public void setRetzq(String retzq) {
		this.retzq = retzq;
	}
	public String getRevbankcode() {
		return revbankcode;
	}
	public void setRevbankcode(String revbankcode) {
		this.revbankcode = revbankcode;
	}
	public String getRevbankname() {
		return revbankname;
	}
	public void setRevbankname(String revbankname) {
		this.revbankname = revbankname;
	}
	
	
	
}
