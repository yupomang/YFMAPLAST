package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi00301Form 
* @Description: 结息对账单请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi00301Form extends AppApiCommonForm {
	
	/** 公积金年度 */				
	private String year = "";					
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
	/**				
	 *<pre> 执行获取公积金年度处理 </pre>				
	 * @return year 公积金年度				
	 */				
	public String getYear() {				
	    return year;				
	}				
					
	/**				
	 *<pre> 执行设定公积金年度处理 </pre>				
	 * @param year 公积金年度				
	 */				
	public void setYear(String year) {				
	    this.year = year;				
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
}
