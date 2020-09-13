package com.yondervision.yfmap.result.kunming;
/**
 * 查询缴存撤销金额
 * @author HYD-HSP
 *
 */
public class AppApi04503ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 撤销金额*/
	private String busipayamt = "";
	/** 单位公积金账号*/
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
	public String getBusipayamt() {
		return busipayamt;
	}
	public void setBusipayamt(String busipayamt) {
		this.busipayamt = busipayamt;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	
}
