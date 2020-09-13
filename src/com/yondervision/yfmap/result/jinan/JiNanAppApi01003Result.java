package com.yondervision.yfmap.result.jinan;

/** 
* @ClassName: AppApi010003
* @Description: 提取信息查询接口返回参数
* @author syw
* 
*/ 
public class JiNanAppApi01003Result {
	/**公积金帐号*/
	private String surplusAccount;
	
	/**姓名*/
	private String fullName;
	
	/**证件类型*/
	private String idcardType;
	
	/**证件号码*/
	private String idcardNumber;
	
	/**账户状态*/
	private String accountStatus;
	
	/**冻结标志*/
	private String freezingMark;
	
	/**账户余额*/
	private String balance;
	
	/**贷款帐号*/
	private String loannum;
	
	/**贷款角色*/
	private String loanflag;
	
	/**公积金开户行*/
	private String gjjBankName;
	
	/**手机号*/
	private String phoneNumber;
	
	/**银行卡开户行*/
	private String bankName;
	
	/**银行卡姓名*/
	private String bankAccname;
	
	/**银行卡身份证号*/
	private String bankIdcard;
	
	/**签约银行卡*/
	private String bankNumber;
	
	/**签约状态*/
	private String signStatus;
	
	/**提取签约类型*/
	private String drawSignType;
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSurplusAccount() {
		return surplusAccount;
	}
	public void setSurplusAccount(String surplusAccount) {
		this.surplusAccount = surplusAccount;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdcardType() {
		return idcardType;
	}
	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getFreezingMark() {
		return freezingMark;
	}
	public void setFreezingMark(String freezingMark) {
		this.freezingMark = freezingMark;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getGjjBankName() {
		return gjjBankName;
	}
	public void setGjjBankName(String gjjBankName) {
		this.gjjBankName = gjjBankName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccname() {
		return bankAccname;
	}
	public void setBankAccname(String bankAccname) {
		this.bankAccname = bankAccname;
	}
	public String getBankIdcard() {
		return bankIdcard;
	}
	public void setBankIdcard(String bankIdcard) {
		this.bankIdcard = bankIdcard;
	}
	public String getBankNumber() {
		return bankNumber;
	}
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getDrawSignType() {
		return drawSignType;
	}
	public void setDrawSignType(String drawSignType) {
		this.drawSignType = drawSignType;
	}
	public String getLoannum() {
		return loannum;
	}
	public void setLoannum(String loannum) {
		this.loannum = loannum;
	}
	public String getLoanflag() {
		return loanflag;
	}
	public void setLoanflag(String loanflag) {
		this.loanflag = loanflag;
	}
	
}
