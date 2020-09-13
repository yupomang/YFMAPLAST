package com.yondervision.yfmap.result.mengdian;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class AppApi00201Result {
	/**
	 * 个人公积金帐号
	 */
	private String accnum = "";				
			
	/**
	 *帐号姓名
	 */
	private String accname = "";				
	/**
	 * 单位帐号
	 */
	private String unitaccnum = "";				
	/**
	 * 单位名称
	 */
	private String unitaccname = "";				
	/**
	 * 交易日期
	 */
	private String transdate = "";				
	/**
	 * 摘要
	 */
	private String summarycode = "";				
	/**
	 * 发生额
	 */
	private String amt = "";				
	/**
	 * 余额
	 */
	private String bal = "";	
	public String getAccnum() {
		return accnum;
	}

	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
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

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getSummarycode() {
		return summarycode;
	}

	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	/**
	 * 响应码
	 */
	private String recode="";
	
	/**
	 * 响应信息
	 */
	private String msg="";
	private String fileName = "";	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
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
	
}
