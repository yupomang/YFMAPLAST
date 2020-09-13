package com.yondervision.yfmap.result.zhangjiakou;

/** 
* @ClassName: ZhangJiaKouAppApi00201Result 
* @Description: 账户余额明细返回报文封装类
* 
*/ 
public class ZhangJiaKouAppApi002Result {
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
	/**
	 * 响应信息
	 */
	private String msg="";
	
	private String thisys = "";//当前页数
	
	private String thisnum = "";//当前页记录数
	
	private String sumnum = "";//总记录数
	
	private String sumys = "";//总页数
	
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

	public String getThisys() {
		return thisys;
	}

	public void setThisys(String thisys) {
		this.thisys = thisys;
	}

	public String getThisnum() {
		return thisnum;
	}

	public void setThisnum(String thisnum) {
		this.thisnum = thisnum;
	}

	public String getSumnum() {
		return sumnum;
	}

	public void setSumnum(String sumnum) {
		this.sumnum = sumnum;
	}

	public String getSumys() {
		return sumys;
	}

	public void setSumys(String sumys) {
		this.sumys = sumys;
	}
		

}
