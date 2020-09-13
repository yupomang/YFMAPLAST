package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author 
 *  开发商--保证金账户查询
 */

import java.math.BigDecimal;
import java.util.Date;

public class AppApi02701ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	
	/** 账户状态*/
	private String accstate = "";
	/** 担保公司代码*/
	private String assurecomp = "";
	/** 当前余额*/
	private String curbal = "";

	/** 是否中心保证金户*/
	private String fundflag = "";
	/** 增量余额*/
	private String increbal = "";
	/** 机构代码*/
	private String instcode = "";
	/** 存量余额*/
	private String keepbal = "";
	/** 最后交易日期*/
	private String lasttrandate = "";
	/** 开户日期*/
	private String opendate = "";
	/** 预存标志*/
	private String preflag = "";
	/** 保证金账号*/
	private String empaccnum = "";
	
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
	
	
	public String getAccstate() {
		return accstate;
	}
	public void setAccstate(String accstate) {
		this.accstate = accstate;
	}
	public String getAssurecomp() {
		return assurecomp;
	}
	public void setAssurecomp(String assurecomp) {
		this.assurecomp = assurecomp;
	}
	
	
	public String getEmpaccnum() {
		return empaccnum;
	}
	public void setEmpaccnum(String empaccnum) {
		this.empaccnum = empaccnum;
	}
	public String getFundflag() {
		return fundflag;
	}
	public void setFundflag(String fundflag) {
		this.fundflag = fundflag;
	}
	
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	
	public String getCurbal() {
		return curbal;
	}
	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}
	public String getIncrebal() {
		return increbal;
	}
	public void setIncrebal(String increbal) {
		this.increbal = increbal;
	}
	public String getKeepbal() {
		return keepbal;
	}
	public void setKeepbal(String keepbal) {
		this.keepbal = keepbal;
	}
	public String getLasttrandate() {
		return lasttrandate;
	}
	public void setLasttrandate(String lasttrandate) {
		this.lasttrandate = lasttrandate;
	}
	public String getOpendate() {
		return opendate;
	}
	public void setOpendate(String opendate) {
		this.opendate = opendate;
	}
	public String getPreflag() {
		return preflag;
	}
	public void setPreflag(String preflag) {
		this.preflag = preflag;
	}
	
	
}
