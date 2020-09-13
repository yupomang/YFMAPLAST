package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *  根据U盾序列号查询U盾信息
 */
public class AppApi02503ZHResult {
	
	
	private String unitcertinum;//单位证件号码"，
	private String unitcertitype;//单位证件类型"

	private String ukeyflag;
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	private String unitaccnum;//单位账号
	private String unitaccname;//单位名称
	private String ukeystate;//U盾状态
	private String unitlinkman;//单位联系人
	private String unitlinkphone;//单位联系电话private String 
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
	public String getUkeystate() {
		return ukeystate;
	}
	public void setUkeystate(String ukeystate) {
		this.ukeystate = ukeystate;
	}
	public String getUnitlinkman() {
		return unitlinkman;
	}
	public void setUnitlinkman(String unitlinkman) {
		this.unitlinkman = unitlinkman;
	}
	public String getUnitlinkphone() {
		return unitlinkphone;
	}
	public void setUnitlinkphone(String unitlinkphone) {
		this.unitlinkphone = unitlinkphone;
	}
	public String getUkeyflag() {
		return ukeyflag;
	}
	public void setUkeyflag(String ukeyflag) {
		this.ukeyflag = ukeyflag;
	}
	public String getUnitcertinum() {
		return unitcertinum;
	}
	public void setUnitcertinum(String unitcertinum) {
		this.unitcertinum = unitcertinum;
	}
	public String getUnitcertitype() {
		return unitcertitype;
	}
	public void setUnitcertitype(String unitcertitype) {
		this.unitcertitype = unitcertitype;
	}
}
