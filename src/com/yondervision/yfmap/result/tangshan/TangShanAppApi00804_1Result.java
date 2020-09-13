package com.yondervision.yfmap.result.tangshan;

/** 
* @ClassName: AppApi00802 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class TangShanAppApi00804_1Result {
		
	/**
	 * 当前页数
	 */
	private String dqys = "";	
	/**
	 * 当前页显示记录数
	 */
	private String dqysjls = "";
	/**
	 * 总记录数
	 */
	private String zjls = "";
	/**
	 * 总页数
	 */
	private String zys = "";
	
	
	
	
	
	
	
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
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public String getDqys() {
		return dqys;
	}
	public void setDqys(String dqys) {
		this.dqys = dqys;
	}
	public String getDqysjls() {
		return dqysjls;
	}
	public void setDqysjls(String dqysjls) {
		this.dqysjls = dqysjls;
	}
	public String getZjls() {
		return zjls;
	}
	public void setZjls(String zjls) {
		this.zjls = zjls;
	}
	public String getZys() {
		return zys;
	}
	public void setZys(String zys) {
		this.zys = zys;
	}		
	
	
}
