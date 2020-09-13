package com.yondervision.yfmap.socket;

/** 
* @ClassName: ReqHeadbean 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 9:45:06 AM   
* 
*/ 
public class ReqHeadbean {
	/** 交易代码 */					
	private String transCode = "";					
	/** 发送日期 */					
	private String sendDate = "";					
	/** 发送时间 */					
	private String sendTime = "";					
	/** 发送方流水号 */					
	private String sendSeqno = "";					
	/** 安全标识 */					
	private String key = "";					
	/** 操作员IP */					
	private String longinip = "";					
	/** 业务类型 */					
	private String buzType = "";
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
	public String getLonginip() {
		return longinip;
	}
	public void setLonginip(String longinip) {
		this.longinip = longinip;
	}
	public String getBuzType() {
		return buzType;
	}
	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}					
					
	
}
