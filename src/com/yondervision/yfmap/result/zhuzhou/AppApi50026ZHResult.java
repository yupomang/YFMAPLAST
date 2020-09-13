package com.yondervision.yfmap.result.zhuzhou;
/**
 * 银行账号有效性验证
 */
public class AppApi50026ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 银行结算返回描述*/
	private String bankresdinfo = "";
	/** 消息*/
	private String message = "";
	/** 状态*/
	private String state = "";
	/** 系统状态*/
	private String sysstate = "";
	
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
	public String getBankresdinfo() {
		return bankresdinfo;
	}
	public void setBankresdinfo(String bankresdinfo) {
		this.bankresdinfo = bankresdinfo;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSysstate() {
		return sysstate;
	}
	public void setSysstate(String sysstate) {
		this.sysstate = sysstate;
	}
	
}
