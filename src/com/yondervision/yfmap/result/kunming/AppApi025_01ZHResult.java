package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 *  批量下传  ---  单位结息对账信息查询
 */
public class AppApi025_01ZHResult {

	/** 个人公积金账号*/
	private String accnum = "";
	/** 个人姓名*/
	private String accname = "";
	/** 身份证号*/
	private String  certinum = "";
	/** 上年转结*/
	private String newbala = "";
	/** 本年归集*/
	private String payamt = "";
	/** 定期利息*/
	private String interest = "";
	/** 活期利息*/
	private String punintint = "";
	/** 利息合计*/
	private String toamt = "";
	/** 余额*/
	private String balance = "";
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
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
	public String getNewbala() {
		return newbala;
	}
	public void setNewbala(String newbala) {
		this.newbala = newbala;
	}
	public String getPayamt() {
		return payamt;
	}
	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getPunintint() {
		return punintint;
	}
	public void setPunintint(String punintint) {
		this.punintint = punintint;
	}
	public String getToamt() {
		return toamt;
	}
	public void setToamt(String toamt) {
		this.toamt = toamt;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
