package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi40115Form 
* 
*/ 
public class AppApi40115Form extends AppApiCommonForm{
	
	/**
	 * 短信验证码
	 */
	private String checkid="";
	/**
	 * 姓名
	 */
	private String fullName="";
	/**
	 * 收款行账户名
	 */
	private String bankaccnm="";
	/**
	 * 收款行卡号
	 */
	private String bankaccnum="";
	/**
	 * 收款银行代码
	 */
	private String bankcode="";
	/**
	 * 证件号码 
	 */
	private String idcardNumber="";
	/**
	 * 手机号码
	 */
	private String mobileNumber="";
	/**
	 * 单位账号
	 */
	private String unitaccnum="";
	/**
	 * 校验类型
	 */
	private String 	buschktype="";
	
	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}

	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getBankaccnm() {
		return bankaccnm;
	}
	public void setBankaccnm(String bankaccnm) {
		this.bankaccnm = bankaccnm;
	}

	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getBuschktype() {
		return buschktype;
	}
	public void setBuschktype(String buschktype) {
		this.buschktype = buschktype;
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
	public String getBuzhType() {
		return buzhType;
	}
	public void setBuzhType(String buzhType) {
		this.buzhType = buzhType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
