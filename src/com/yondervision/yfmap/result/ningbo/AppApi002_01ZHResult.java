package com.yondervision.yfmap.result.ningbo;

/**
 * 批量——个人明细查询
 *
 */
public class AppApi002_01ZHResult {

	/**	 交易日期*/
	private String jzrq = "";
	/**	单位名称 */
	private String dwmc = "";
	/**	交易名称 */
	private String jymc = "";
	/**	发生额 */
	private String fse = "";
	/**	个人账户余额 */
	private String bal = "";
	
	public String getJzrq() {
		return jzrq;
	}
	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getFse() {
		return fse;
	}
	public void setFse(String fse) {
		this.fse = fse;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getJymc() {
		return jymc;
	}
	public void setJymc(String jymc) {
		this.jymc = jymc;
	}
	
}
