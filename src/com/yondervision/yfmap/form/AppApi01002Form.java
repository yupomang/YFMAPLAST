package com.yondervision.yfmap.form;

/** 
* 物业费提取
* @ClassName: AppApi01002Form
*/ 
public class AppApi01002Form extends AppApiCommonForm{
	/**
	 * 姓名
	 */
	private String fullName="";
	/**
	 * 证件号码 
	 */
	private String idcardNumber="";
	/**
	 * 单位账号
	 */
	private String unitaccnum = "";
	/**
	 * 银行账户名称
	 */
	private String bankaccnm = "";
	/**
	 * 银行账号
	 */
	private String bankaccnum = "";
	/**
	 * 银行代码
	 */
	private String bankcode = "";
	/**
	 * 手机号码  
	 */
	private String mobileNumber = "";
	/**
	 * 结算方式（固定值5,电子结算）
	 */
	private String settlemode = "";
	/**
	 * 提取金额
	 */
	private String drawamt = "";
	/**
	 * 短信验证码
	 */
	private String checkid = "";
	
	/**
	 * 校验类型
	 */
	private String buschktype="";
	
	/**
	 * 提取原因
	 */
	private String drawreason="";
	/**
	 * 公积金查询密码
	 */
	private String newpassword="";

	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getBankaccnm() {
		return bankaccnm;
	}
	public void setBankaccnm(String bankaccnm) {
		this.bankaccnm = bankaccnm;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getSettlemode() {
		return settlemode;
	}
	public void setSettlemode(String settlemode) {
		this.settlemode = settlemode;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
	}

	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getBuschktype() {
		return buschktype;
	}
	public void setBuschktype(String buschktype) {
		this.buschktype = buschktype;
	}
	
	public String getDrawreason() {
		return drawreason;
	}
	
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
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
