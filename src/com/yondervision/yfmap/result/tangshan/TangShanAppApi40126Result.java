package com.yondervision.yfmap.result.tangshan;

/** 
* @ClassName: TangShanAppApi40126Result
* @Description: 个人通讯信息变更查询
* @author syw
* 
*/ 
public class TangShanAppApi40126Result {

	/**
	 * 机构码
	 */
	private String brcCode = "";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 返回日期
	 */
	private String sendDate="";
	/**
	 * 返回时间
	 */
	private String sendTime="";
	/**
	 * 响应码
	 */
	private String recode="";
	/**
	 * 响应信息
	 */
	private String msg="";
	
	/**
	 * 职工原手机号
	 */
	private String sjh="";

	public String getBrcCode() {
		return brcCode;
	}

	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}

	public String getSendSeqno() {
		return sendSeqno;
	}

	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
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

	public String getSjh() {
		return sjh;
	}

	public void setSjh(String sjh) {
		this.sjh = sjh;
	}

	
	
}
