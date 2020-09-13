package com.yondervision.yfmap.result.ningbo;
/**
 * 单位登录
 * @author HYD-HSP
 *
 */
public class AppApi50013ZHResult {
	/**单位性质*/
	private String 	unitkind = "";
	
	/**统一社会信用代码*/
	private String 	unioncode = "";
	
	/**建行的单位名称*/
	private String 	unitaccname_eb = "";
	/**U盾状态：ukeystate  1-初始化,2-开启,3-停止,4-挂失,5-删除*/
	private String 	ukeystate = "";
	
	/** 是否主管盾标志*/
	private String 	flag1 = "";
	/** 单位证件类型*/
	private String unitcertitype = "";
	/** 单位证件号码*/
	private String unitcertinum = "";
	/** 机构号*/
	private String brcCode = "";
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 单位账号*/
	private String unitaccnum = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 标志*/
	private String flag = "";

	/** U盾标志*/
	private String webbusistat = "";
	
	/** 是否初始密码标志*/
	private String initialpwdflag = "";
	public String getWebbusistat() {
		return webbusistat;
	}
	public void setWebbusistat(String webbusistat) {
		this.webbusistat = webbusistat;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBrcCode() {
		return brcCode;
	}
	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}
	public String getInitialpwdflag() {
		return initialpwdflag;
	}
	public void setInitialpwdflag(String initialpwdflag) {
		this.initialpwdflag = initialpwdflag;
	}
	public String getUnitcertitype() {
		return unitcertitype;
	}
	public void setUnitcertitype(String unitcertitype) {
		this.unitcertitype = unitcertitype;
	}
	public String getUnitcertinum() {
		return unitcertinum;
	}
	public void setUnitcertinum(String unitcertinum) {
		this.unitcertinum = unitcertinum;
	}
	public String getFlag1() {
		return flag1;
	}
	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}
	public String getUkeystate() {
		return ukeystate;
	}
	public void setUkeystate(String ukeystate) {
		this.ukeystate = ukeystate;
	}
	public String getUnitaccname_eb() {
		return unitaccname_eb;
	}
	public void setUnitaccname_eb(String unitaccname_eb) {
		this.unitaccname_eb = unitaccname_eb;
	}
	public String getUnioncode() {
		return unioncode;
	}
	public void setUnioncode(String unioncode) {
		this.unioncode = unioncode;
	}
	public String getUnitkind() {
		return unitkind;
	}
	public void setUnitkind(String unitkind) {
		this.unitkind = unitkind;
	}
}
