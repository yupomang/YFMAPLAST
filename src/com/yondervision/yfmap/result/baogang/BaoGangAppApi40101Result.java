package com.yondervision.yfmap.result.baogang;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class BaoGangAppApi40101Result {
	/**
	 * 个人公积金帐号
	 */
	private String accnum = "";				
			
	/**
	 *帐号姓名
	 */
	private String accname = "";	
	/**
	 * 身份证号码
	 */
	private String certinum = "";	
	/**
	 * 联名卡号
	 */
	private String cardno = "";				
	/**
	 * 单位名称
	 */
	private String unitaccnum = "";				
	/**
	 * 贷款账号
	 */
	private String loancontrnum = "";
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
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}	
	
}
