package com.yondervision.yfmap.result.baoshan;

/**
 * 业务类型为提取，公积金明细下一级信息获取
 * @author gq
 *
 */
public class AppApi00202Result {
	/** 交易日期 */					
	private String transdate = "";
	/** 业务类型(摘要)*/					
	private String summary = "";
//	/** 提取原因*/					
//	private String tqyy = "";
//	/** 提取本金*/					
//	private String tqbj = "";
//	/** 提取利息 */					
//	private String tqlx = "";
	private String freeuse1 = "";
	private String freeuse2 = "";
	private String freeuse3 = "";
	
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
	
//	public String getTqyy() {
//		return tqyy;
//	}
//	public void setTqyy(String tqyy) {
//		this.tqyy = tqyy;
//	}
//	public String getTqbj() {
//		return tqbj;
//	}
//	public void setTqbj(String tqbj) {
//		this.tqbj = tqbj;
//	}
//	public String getTqlx() {
//		return tqlx;
//	}
//	public void setTqlx(String tqlx) {
//		this.tqlx = tqlx;
//	}
	
	public String getTransCode() {
		return transCode;
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
	
}
