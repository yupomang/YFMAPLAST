package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;

/**
 * 
 * @author 
 *单位业务--补缴登记    /补缴校验
 */
public class AppApi04501ZHResult {
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
	
	/** 汇缴开始年月*/
	private String begym = "";
	/** 汇缴截止年月*/
	private String endym = "";
	/** 预缴户余额*/
	private String prebal = "";
	/** 缴存登记号*/
	private String regnum = "";
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
	public String getBegym() {
		return begym;
	}
	public void setBegym(String begym) {
		this.begym = begym;
	}
	public String getEndym() {
		return endym;
	}
	public void setEndym(String endym) {
		this.endym = endym;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
