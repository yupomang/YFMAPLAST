package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 
 *  单位业务--个人封存
 */
public class AppApi03901ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	
	/** 账号机构代码*/
	private String accinstcode = "";
	/** 经办机构代码*/
	private String agentinstcode = "";
	/** 主机流水号*/
	private String hostsernum = "";
	/** 封存人数*/
	private String sealnum = "";
	/** 交易日期*/
	private String tranDate = "";
	/** 流水号*/
	private String tranSeq = "";
	/** 单位公积金账号*/
	private String unitaccnum = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 单位缴存基数*/
	private String unitbasenumber = "";
	/** 单位月汇缴额*/
	private String unitmonpaysum = "";
		
	private String filename = "";
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getAccinstcode() {
		return accinstcode;
	}
	public void setAccinstcode(String accinstcode) {
		this.accinstcode = accinstcode;
	}
	public String getAgentinstcode() {
		return agentinstcode;
	}
	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}
	public String getHostsernum() {
		return hostsernum;
	}
	public void setHostsernum(String hostsernum) {
		this.hostsernum = hostsernum;
	}
	public String getSealnum() {
		return sealnum;
	}
	public void setSealnum(String sealnum) {
		this.sealnum = sealnum;
	}
	
	
	
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
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
	public String getUnitbasenumber() {
		return unitbasenumber;
	}
	public void setUnitbasenumber(String unitbasenumber) {
		this.unitbasenumber = unitbasenumber;
	}
	public String getUnitmonpaysum() {
		return unitmonpaysum;
	}
	public void setUnitmonpaysum(String unitmonpaysum) {
		this.unitmonpaysum = unitmonpaysum;
	}
	
}
