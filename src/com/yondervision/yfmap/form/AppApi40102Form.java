package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi40102Form 
* @Description: TODO
* @author Caozhongyan
* @date Oct 12, 2013 3:31:37 PM   
* 
*/ 
public class AppApi40102Form extends AppApiCommonForm{
	
	/**
	 * 公共账号
	 */
	private String pubaccnum="";
	/**
	 * 类型（0-单位,1-个人,2-开发商,app4, 微信5）
	 */
	private String logintype="";
	/**
	 * 短信验证码
	 */
	private String smstmplcode="";

	/**
	 * 姓名
	 */
	private String fullName="";
	/**
	 * 证件号码 
	 */
	private String idcardNumber;
	/**
	 * 手机号码
	 */
	private String mobileNumber;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 新密码
	 */
	private String newpassword;
	/**
	 * 验证码
	 */
	private String checkid;
	/**
	 * 设备devtoken
	 */
	private String devtoken;
	/**
	 * 联名卡卡号
	 */
	private String cardno;
	/**
	 * 
	 */
	private String appid="";
	/**
	 * 绑定通知类型
	 */
	private String bindingnotice = "";
	/**
	 * 正常登录标识(0或者没有此参数:正常，1（修改+登录）)
	 */
	private String nomalLogin = "";
	/**
	 * 应用类型  海南 1校验是否第一次登录 2银联校验完成后获取验证码
	 */
	private String ischeck="";
	/**
	 * 手机号
	 */
	private String tel="";
	
	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	private String accnum="";
	private String certinum="";	
	private String freeuse1="";
	private String freeuse2="";
	private String freeuse3="";
	private String transCode="";
	
	public String getSmstmplcode() {
		return smstmplcode;
	}
	public void setSmstmplcode(String smstmplcode) {
		this.smstmplcode = smstmplcode;
	}
	public String getPubaccnum() {
		return pubaccnum;
	}
	public void setPubaccnum(String pubaccnum) {
		this.pubaccnum = pubaccnum;
	}
	public String getLogintype() {
		return logintype;
	}
	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
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
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getDevtoken() {
		return devtoken;
	}
	public void setDevtoken(String devtoken) {
		this.devtoken = devtoken;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getBindingnotice() {
		return bindingnotice;
	}
	public void setBindingnotice(String bindingnotice) {
		this.bindingnotice = bindingnotice;
	}
	public String getNomalLogin() {
		return nomalLogin;
	}
	public void setNomalLogin(String nomalLogin) {
		this.nomalLogin = nomalLogin;
	}
	public String getIscheck() {
		return ischeck;
	}
	public void setIscheck(String ischeck) {
		this.ischeck = ischeck;
	}	
}
