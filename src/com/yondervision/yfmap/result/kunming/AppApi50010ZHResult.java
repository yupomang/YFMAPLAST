package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author 
 * 个人公积金账户身份认证
 */
public class AppApi50010ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";		

	/** 成功标志*/
	private String rspFlag = "";
	/** 返回信息*/
	private String rspInfo = "";
	/** 是否存在查询密码  */
	private String haveSPwd = "";
	
	public String getHaveSPwd() {
		return haveSPwd;
	}
	public void setHaveSPwd(String haveSPwd) {
		this.haveSPwd = haveSPwd;
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
	public String getRspFlag() {
		return rspFlag;
	}
	public void setRspFlag(String rspFlag) {
		this.rspFlag = rspFlag;
	}
	public String getRspInfo() {
		return rspInfo;
	}
	public void setRspInfo(String rspInfo) {
		this.rspInfo = rspInfo;
	}
	
	
}
