package com.yondervision.yfmap.result.haikou;

/**
 * 业务办理    正常提取-反显信息
 * @author ljd
 *
 */
public class AppApi01029Result {


	
	private String counts = ""; // <counts>笔数</counts>
	private String amt1 = ""; // <amt1>金额1</amt1>
	private String unitprop = ""; // <unitprop>单位比例</unitprop>
	
	private String isloanflag = ""; // 1-是，0-否

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public String getAmt1() {
		return amt1;
	}

	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}

	public String getUnitprop() {
		return unitprop;
	}

	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}

	public String getIsloanflag() {
		return isloanflag;
	}

	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
}
