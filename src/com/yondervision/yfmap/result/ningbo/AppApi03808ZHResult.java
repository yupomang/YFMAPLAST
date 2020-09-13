package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 * 单位业务 --个人开户   
 */
public class AppApi03808ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";
	
	private String calbal = "";//计算公积金提取金额
	private String calmonbal = "";//计算按月补贴户提取金额
	private String calsupbal = "";//计算住房补贴户提取金额
	private String drawbal = "";//公积金提取金额
	private String drawmonbal = "";//按月补贴提取金额
	private String drawsupbal = "";//住房货币补贴户提取金额
	private String inputamt = "";//计算可提取金额
	
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
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
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getCalbal() {
		return calbal;
	}
	public void setCalbal(String calbal) {
		this.calbal = calbal;
	}
	public String getCalmonbal() {
		return calmonbal;
	}
	public void setCalmonbal(String calmonbal) {
		this.calmonbal = calmonbal;
	}
	public String getCalsupbal() {
		return calsupbal;
	}
	public void setCalsupbal(String calsupbal) {
		this.calsupbal = calsupbal;
	}
	public String getDrawbal() {
		return drawbal;
	}
	public void setDrawbal(String drawbal) {
		this.drawbal = drawbal;
	}
	public String getDrawmonbal() {
		return drawmonbal;
	}
	public void setDrawmonbal(String drawmonbal) {
		this.drawmonbal = drawmonbal;
	}
	public String getDrawsupbal() {
		return drawsupbal;
	}
	public void setDrawsupbal(String drawsupbal) {
		this.drawsupbal = drawsupbal;
	}
	public String getInputamt() {
		return inputamt;
	}
	public void setInputamt(String inputamt) {
		this.inputamt = inputamt;
	}
}
