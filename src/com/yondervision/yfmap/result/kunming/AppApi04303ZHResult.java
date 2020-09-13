package com.yondervision.yfmap.result.kunming;
/**
 * 网厅列表反显个人信息并验证
 * @author qinxla
 *
 */
public class AppApi04303ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 个人姓名*/
	private String accname = "";
	/** 身份证号*/
	private String certinum = "";
	/** 单位账号*/
	private String unitaccnum = "";
	
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
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	
}
