package com.yondervision.yfmap.result.baoshan;

/**
 * 贷款明细查询
 * @author gq
 *
 */
public class AppApi00701Result {
	private String filename = "";	
	/** 还款总额 */						
	private String totalrepay = "";						
	/** 剩余期次 */						
	private String remainper = "";	
	/**实还本金合计 */						
	private String infactprin_z = "";	
	/** 实还利息合计 */						
	private String infactint_z = "";					

	/** 应还款日 */						
	private String plandate = "";						
	/** 应还本金 */						
	private String planretprin = "";						
	/** 应还利息 */						
	private String planretint = "";
	/** 实际还款日 */						
	private String infactdate = "";
	/** 实还本金 */						
	private String infactretprin = "";						
	/** 实还利息 */						
	private String infactretint = "";
	/** 实还罚息 */						
	private String infactpun = "";
	
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
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getTotalrepay() {
		return totalrepay;
	}
	public void setTotalrepay(String totalrepay) {
		this.totalrepay = totalrepay;
	}
	public String getRemainper() {
		return remainper;
	}
	public void setRemainper(String remainper) {
		this.remainper = remainper;
	}
	public String getInfactprin_z() {
		return infactprin_z;
	}
	public void setInfactprin_z(String infactprinZ) {
		infactprin_z = infactprinZ;
	}
	public String getInfactint_z() {
		return infactint_z;
	}
	public void setInfactint_z(String infactintZ) {
		infactint_z = infactintZ;
	}
	public String getPlandate() {
		return plandate;
	}
	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}
	public String getPlanretprin() {
		return planretprin;
	}
	public void setPlanretprin(String planretprin) {
		this.planretprin = planretprin;
	}
	public String getPlanretint() {
		return planretint;
	}
	public void setPlanretint(String planretint) {
		this.planretint = planretint;
	}
	public String getInfactdate() {
		return infactdate;
	}
	public void setInfactdate(String infactdate) {
		this.infactdate = infactdate;
	}
	public String getInfactretprin() {
		return infactretprin;
	}
	public void setInfactretprin(String infactretprin) {
		this.infactretprin = infactretprin;
	}
	public String getInfactretint() {
		return infactretint;
	}
	public void setInfactretint(String infactretint) {
		this.infactretint = infactretint;
	}
	public String getInfactpun() {
		return infactpun;
	}
	public void setInfactpun(String infactpun) {
		this.infactpun = infactpun;
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

}
