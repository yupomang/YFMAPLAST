package com.yondervision.yfmap.result.haikou;

/**
 * @author ljd
 *
 */
public class AppApi01032Result {
	
	private String transdate = ""; // 交易日期
//	private String certinum = ""; // 证件号码
	private String unitaccname = ""; // 姓名
	private String amt1 = ""; //	金额1
	private String amt2 = ""; //	金额2
	private String payvouamt = ""; //	金额3
	private String freeuse1 = ""; //	月 日
	private String begym = ""; // 开始年月
	private String endym = ""; // 结束年月
	private String reason = ""; //	原因
	private String dpbusitype = ""; //	<item fdname="dpbusitype" col="10" />
	
	/** 证件号码 */
	private String certinum = "";
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
	public String getAmt1() {
		return amt1;
	}
	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}
	public String getAmt2() {
		return amt2;
	}
	public void setAmt2(String amt2) {
		this.amt2 = amt2;
	}
	public String getPayvouamt() {
		return payvouamt;
	}
	public void setPayvouamt(String payvouamt) {
		this.payvouamt = payvouamt;
	}
	public String getBegym() {
		return begym;
	}
	public void setBegym(String begym) {
		this.begym = begym;
	}
	public String getEndym() {
		return endym;
	}
	public void setEndym(String endym) {
		this.endym = endym;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDpbusitype() {
		return dpbusitype;
	}
	public void setDpbusitype(String dpbusitype) {
		this.dpbusitype = dpbusitype;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
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
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getFreeuse1() {
		return freeuse1;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	
}
