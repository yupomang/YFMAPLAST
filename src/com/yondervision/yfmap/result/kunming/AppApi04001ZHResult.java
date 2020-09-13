package com.yondervision.yfmap.result.kunming;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author 
 *  单位业务--个人启封
 */
public class AppApi04001ZHResult {
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
	
	/** 经办机构代码*/
	private String agentinstcode = "";
	/** 经办柜员*/
	private String agentop = "";
	/** 主机流水号*/
	private String hostsernum = "";
	/** 启封人数*/
	private String qfnum = "";
	/** 交易日期*/
	private String tranDate = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 单位缴存基数*/
	private String unitbasenumber = "";
	/** 单位月汇缴额*/
	private String unitmonpaysum = "";
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
	public String getAgentinstcode() {
		return agentinstcode;
	}
	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}
	public String getAgentop() {
		return agentop;
	}
	public void setAgentop(String agentop) {
		this.agentop = agentop;
	}
	public String getHostsernum() {
		return hostsernum;
	}
	public void setHostsernum(String hostsernum) {
		this.hostsernum = hostsernum;
	}
	public String getQfnum() {
		return qfnum;
	}
	public void setQfnum(String qfnum) {
		this.qfnum = qfnum;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
}
