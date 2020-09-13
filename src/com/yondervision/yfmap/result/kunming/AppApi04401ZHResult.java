package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;

/**
 * 
 * @author 
 *单位业务--汇缴登记   / 汇缴校验
 */
public class AppApi04401ZHResult {
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
	
	/** 起始年月*/
	private String begym = "";
	/** 汇缴金额*/
	private String payamt = "";
	/** 支付方式*/
	private String paymode = "";
	/** 汇缴人数*/
	private String paynum = "";
	/** 单位公积金预缴户账号*/
	private String preaccnum = "";
	/** 预缴户余额*/
	private String prebal = "";
	/** 登记号*/
	private String regnum = "";
	/** 流水号*/
	private String transeq = "";
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
	public String getBegym() {
		return begym;
	}
	public void setBegym(String begym) {
		this.begym = begym;
	}
	public String getPayamt() {
		return payamt;
	}
	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getPaynum() {
		return paynum;
	}
	public void setPaynum(String paynum) {
		this.paynum = paynum;
	}
	public String getPreaccnum() {
		return preaccnum;
	}
	public void setPreaccnum(String preaccnum) {
		this.preaccnum = preaccnum;
	}
	public String getPrebal() {
		return prebal;
	}
	public void setPrebal(String prebal) {
		this.prebal = prebal;
	}
	public String getRegnum() {
		return regnum;
	}
	public void setRegnum(String regnum) {
		this.regnum = regnum;
	}
	public String getTranseq() {
		return transeq;
	}
	public void setTranseq(String transeq) {
		this.transeq = transeq;
	}
	
	
	
}
