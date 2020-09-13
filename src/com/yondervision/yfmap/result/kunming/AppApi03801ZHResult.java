package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;

/**
 * 
 * @author 
 * 单位业务 --个人开户   
 */
public class AppApi03801ZHResult {

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
	
	/** 本期开户人数*/
	private String opennum = "";
	/** 月缴额*/
	private String opensum = "";
	/** 单位公积金账号*/
	private String unitaccnum = "";
	/** 单位名称*/
	private String unitaccname = "";
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
	
	public String getOpennum() {
		return opennum;
	}
	public void setOpennum(String opennum) {
		this.opennum = opennum;
	}
	public String getOpensum() {
		return opensum;
	}
	public void setOpensum(String opensum) {
		this.opensum = opensum;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
