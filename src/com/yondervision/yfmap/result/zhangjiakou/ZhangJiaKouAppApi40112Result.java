package com.yondervision.yfmap.result.zhangjiakou;

/** 
* @ClassName: AppApi40112
* @Description: 验证码返回
* @author syw
* 
*/ 
public class ZhangJiaKouAppApi40112Result {

	/**
	 * 验证码序号
	 */
	private String transCode="";
	
	/**
	 * 手机号后四位
	 */
	private String telphone="";
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
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
}
