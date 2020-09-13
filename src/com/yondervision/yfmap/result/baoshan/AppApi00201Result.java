package com.yondervision.yfmap.result.baoshan;

/**
 * @author gq
 *
 */
public class AppApi00201Result {
	/** 交易日期 */					
	private String transdate = "";	
	/** 业务类型(摘要)*/					
	private String summary = "";
	/** 业务类型(摘要码)*/					
	private String summarycode = "";
	/** 发生额*/					
	private String transmon = "";					
	/** 余额 */					
	private String balance = "";
	/** 交易流水号 */					
	private String transseq = "";
	/** 允许下一级标记 */					
	private String nextflg = "";// 1:允许；0不允许
	
	/**
	 * 交易代码
	 */
	private String transCode = "";
	/**
	 * 返回日期
	 */
	private String sendDate = "";
	/**
	 * 返回时间
	 */
	private String sendTime = "";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno = "";
	/**
	 * 安全标识
	 */
	private String key = "";
	/**
	 * 中心流水号
	 */
	private String centerSeqno = "";
	/**
	 * 响应码
	 */
	private String recode = "";
	/**
	 * 响应信息
	 */
	private String msg = "";
	
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getSummarycode() {
		return summarycode;
	}
	public void setSummaryCode(String summarycode) {
		this.summarycode = summarycode;
	}
	public String getTransmon() {
		return transmon;
	}
	public void setTransmon(String transmon) {
		this.transmon = transmon;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
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
	public String getTransseq() {
		return transseq;
	}
	public void setTransseq(String transseq) {
		this.transseq = transseq;
	}
	public String getNextflg() {
		return nextflg;
	}
	public void setNextflg(String nextflg) {
		this.nextflg = nextflg;
	}
	
}
