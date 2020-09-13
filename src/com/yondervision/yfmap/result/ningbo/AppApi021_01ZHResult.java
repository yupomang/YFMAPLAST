package com.yondervision.yfmap.result.ningbo;
/**
 * 批量——单位明细查询
 * @author HYD-HSP
 *
 */
public class AppApi021_01ZHResult {
	/** 交易日期 */
	private String jzrq = "";
	/** 备注 */
	private String remark = "";
	/**	发生额 */
	private String fse = "";
	/** 余额 */
	private String bal = "";
	/** 摘要描述 */
	private String summary = "";
	
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getJzrq() {
		return jzrq;
	}
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	public String getFse() {
		return fse;
	}
	public void setFse(String fse) {
		this.fse = fse;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	
}
