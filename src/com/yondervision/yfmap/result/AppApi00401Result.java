package com.yondervision.yfmap.result;

/** 
* @ClassName: AppApi00401 
* @Description: 接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class AppApi00401Result {
	/** 公积金账户 */						
	private String accnum = "";						
	/** 当年累计提取金额 */						
	private String thiscumulativesumamt = "";						
	/** 历年累计提取金额 */						
	private String pastcumulativesumamt = "";						
	/** 最后提取日期 */						
	private String lastdrawdate = "";					
						
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
	
	private String isloanflag = "";
	private String accinstname = "";
	
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

	public String getAccnum() {
		return accnum;
	}

	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	public String getThiscumulativesumamt() {
		return thiscumulativesumamt;
	}

	public void setThiscumulativesumamt(String thiscumulativesumamt) {
		this.thiscumulativesumamt = thiscumulativesumamt;
	}

	public String getPastcumulativesumamt() {
		return pastcumulativesumamt;
	}

	public void setPastcumulativesumamt(String pastcumulativesumamt) {
		this.pastcumulativesumamt = pastcumulativesumamt;
	}

	public String getLastdrawdate() {
		return lastdrawdate;
	}

	public void setLastdrawdate(String lastdrawdate) {
		this.lastdrawdate = lastdrawdate;
	}
}
