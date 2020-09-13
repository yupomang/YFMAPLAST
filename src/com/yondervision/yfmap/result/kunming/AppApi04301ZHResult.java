package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;

/**
 * 
 * @author 
 *单位业务--个人转移
 */
public class AppApi04301ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	
	/** 流水号*/
	private String tranSeq = "";
	
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	
	/** 转移总金额*/
	private String totbalance = "";
	/** 转移总人数*/
	private String totpeoplenum = "";
	/** 打印文件名*/
	private String filename = "";
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
	
	public String getTotbalance() {
		return totbalance;
	}
	public void setTotbalance(String totbalance) {
		this.totbalance = totbalance;
	}
	public String getTotpeoplenum() {
		return totpeoplenum;
	}
	public void setTotpeoplenum(String totpeoplenum) {
		this.totpeoplenum = totpeoplenum;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
