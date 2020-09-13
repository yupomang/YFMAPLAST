package com.yondervision.yfmap.result.ningbo;
/**
 *  单位信息查询
 */
public class AppApi80009ZHResult {
	
	/** 单位账号 */
	private String dwzh;

	/** 单位名称*/
	private String unitaccname = "";

	/** 本月缴存金额 */
	private String cmpayamt = "";

	/** 本月缴存人数 */
	private String cmpaynum = "";

	/** 缴至年月*/
	private String lpaym = "";

	/** 账户机构*/
	private String accinstcode = "";

	/** 单位统一信用代码 */
	private String spt_tyxyydm;

	/** 机构号 */
	private String brcCode = "";

	/** 状态码*/
	private String recode = "";

	/** 描述*/
	private String msg = "";

	/** 多月存缴信息*/
	private String stdata = "";

	public String getStdata() {
		return this.stdata;
	}

	public void setStdata(final String stdata) {
		this.stdata = stdata;
	}

	public String getDwzh() {
		return this.dwzh;
	}

	public void setDwzh(final String dwzh) {
		this.dwzh = dwzh;
	}

	public String getUnitaccname() {
		return this.unitaccname;
	}

	public void setUnitaccname(final String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getCmpayamt() {
		return this.cmpayamt;
	}

	public void setCmpayamt(final String cmpayamt) {
		this.cmpayamt = cmpayamt;
	}

	public String getCmpaynum() {
		return this.cmpaynum;
	}

	public void setCmpaynum(final String cmpaynum) {
		this.cmpaynum = cmpaynum;
	}

	public String getLpaym() {
		return this.lpaym;
	}

	public void setLpaym(final String lpaym) {
		this.lpaym = lpaym;
	}

	public String getAccinstcode() {
		return this.accinstcode;
	}

	public void setAccinstcode(final String accinstcode) {
		this.accinstcode = accinstcode;
	}

	public String getSpt_tyxyydm() {
		return this.spt_tyxyydm;
	}

	public void setSpt_tyxyydm(final String spt_tyxyydm) {
		this.spt_tyxyydm = spt_tyxyydm;
	}

	public String getBrcCode() {
		return this.brcCode;
	}

	public void setBrcCode(final String brcCode) {
		this.brcCode = brcCode;
	}

	public String getRecode() {
		return this.recode;
	}

	public void setRecode(final String recode) {
		this.recode = recode;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}
}
