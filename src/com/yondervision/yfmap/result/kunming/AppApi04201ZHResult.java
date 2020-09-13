package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;

/**
 * 
 * @author 
 *单位业务--缴存比例变更
 */
public class AppApi04201ZHResult {
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
	
	/** 申请调整原因*/
	private String appadjustreason = "";
	/** 批准文号*/
	private String authfilenum = "";
	/** 启用年月*/
	private String beginpaydate = "";
	/** 变更人数*/
	private String changepeople = "";
	/** 终止年月*/
	private String enddate = "";
	/** 调整后个人比例*/
	private String newperprop = "";
	/** 调整后单位比例*/
	private String newunitprop = "";
	/** 单位公积金账号*/
	private String unitaccnum = "";
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
	public String getAppadjustreason() {
		return appadjustreason;
	}
	public void setAppadjustreason(String appadjustreason) {
		this.appadjustreason = appadjustreason;
	}
	public String getAuthfilenum() {
		return authfilenum;
	}
	public void setAuthfilenum(String authfilenum) {
		this.authfilenum = authfilenum;
	}
	public String getBeginpaydate() {
		return beginpaydate;
	}
	public void setBeginpaydate(String beginpaydate) {
		this.beginpaydate = beginpaydate;
	}
	
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getChangepeople() {
		return changepeople;
	}
	public void setChangepeople(String changepeople) {
		this.changepeople = changepeople;
	}
	public String getNewperprop() {
		return newperprop;
	}
	public void setNewperprop(String newperprop) {
		this.newperprop = newperprop;
	}
	public String getNewunitprop() {
		return newunitprop;
	}
	public void setNewunitprop(String newunitprop) {
		this.newunitprop = newunitprop;
	}
	
}
