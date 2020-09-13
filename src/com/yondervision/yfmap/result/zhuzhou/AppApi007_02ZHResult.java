package com.yondervision.yfmap.result.zhuzhou;
/**
 * 批量——还款计划查询
 * @author HYD-HSP
 *
 */
public class AppApi007_02ZHResult {

	/**	 序号 */
	private String iCount = "";
	/**	 应还日期*/
	private String acEnddate = "";
	/**	 期数*/
	private String dqqc = "";
	/**	 贷款余额*/
	private String initialbal = "";
	/**	 应还本金*/
	private String planprin = "";
	/**	 应还利息*/
	private String planint = "";
	/**	 月还款额*/
	private String dMonthrepayamt = "";
	
	
	public String getiCount() {
		return iCount;
	}
	public void setiCount(String iCount) {
		this.iCount = iCount;
	}
	public String getAcEnddate() {
		return acEnddate;
	}
	public void setAcEnddate(String acEnddate) {
		this.acEnddate = acEnddate;
	}
	public String getDqqc() {
		return dqqc;
	}
	public void setDqqc(String dqqc) {
		this.dqqc = dqqc;
	}
	public String getInitialbal() {
		return initialbal;
	}
	public void setInitialbal(String initialbal) {
		this.initialbal = initialbal;
	}
	public String getPlanprin() {
		return planprin;
	}
	public void setPlanprin(String planprin) {
		this.planprin = planprin;
	}
	public String getPlanint() {
		return planint;
	}
	public void setPlanint(String planint) {
		this.planint = planint;
	}
	public String getdMonthrepayamt() {
		return dMonthrepayamt;
	}
	public void setdMonthrepayamt(String dMonthrepayamt) {
		this.dMonthrepayamt = dMonthrepayamt;
	}
	
}
