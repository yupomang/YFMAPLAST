package com.yondervision.yfmap.form;

public class AppApi00202Form extends AppApiCommonForm {
	/**
	 * 个人账号/联名卡号
	 */
	private String accnum = "";
	/**
	 * 证件号码
	 */
	private String certinum = "";	
	
	private String summarycode = "";
	
	private String transdate = "";
	
	private String transseqno = "";
	
	/**
	 * 发送日期
	 */
	private String sendDate="";
	/**
	 * 发送时间
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
	 * 查询方式
	 */
	private String type="";
	
	private String transcode = "";

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

	public String getSummarycode() {
		return summarycode;
	}

	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTranscode() {
		return transcode;
	}

	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}

	public String getTransseqno() {
		return transseqno;
	}

	public void setTransseqno(String transseqno) {
		this.transseqno = transseqno;
	}
	
	
}
