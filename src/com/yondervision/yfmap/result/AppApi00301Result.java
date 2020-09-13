package com.yondervision.yfmap.result;

/** 
* @ClassName: AppApi00301Result 
* @Description: 接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 9:14:36 AM 
* 
*/ 
public class AppApi00301Result {
	/** 公积金账户 */						
	private String accnum = "";						
	/** 姓名 */						
	private String accname = "";						
	/** 结息日期 */						
	private String intdate = "";						
	/** 上期结转 */						
	private String keepamt = "";						
	/** 本期缴存（含转入） */						
	private String incramt = "";						
	/** 本期提取 */						
	private String drawamt = "";						
	/** 利息 */						
	private String intamt = "";
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
	
	
	/** 本息合计 */						
	private String bal = "";
	/** 温馨提示 */						
	private String tips = "";
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getIntdate() {
		return intdate;
	}
	public void setIntdate(String intdate) {
		this.intdate = intdate;
	}
	public String getKeepamt() {
		return keepamt;
	}
	public void setKeepamt(String keepamt) {
		this.keepamt = keepamt;
	}
	public String getIncramt() {
		return incramt;
	}
	public void setIncramt(String incramt) {
		this.incramt = incramt;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
	}
	public String getIntamt() {
		return intamt;
	}
	public void setIntamt(String intamt) {
		this.intamt = intamt;
	}	
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
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

}
