package com.yondervision.yfmap.result.baogang;


/**
 * @author Caozhongyan
 *
 */
public class BaoGangAppApi00201Result {
	/** 交易日期 */					
	private String transdate = "";					
	/** 单位名称 */					
	private String unitaccname = "";					
	/** 单位账号*/					
	private String unitaccnum = "";					
	/** 摘要 */					
	private String summary = "";
	/** 汇缴开始年月 */					
	private String paybegindate = "";
	/** 汇缴截止年月*/					
	private String payenddate = "";
	/** 发生额*/					
	private String busiamt = "";
	/** 余额 */					
	private String balance = "";
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

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getUnitaccname() {
		return unitaccname;
	}

	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getUnitaccnum() {
		return unitaccnum;
	}

	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getPaybegindate() {
		return paybegindate;
	}

	public void setPaybegindate(String paybegindate) {
		this.paybegindate = paybegindate;
	}

	public String getPayenddate() {
		return payenddate;
	}

	public void setPayenddate(String payenddate) {
		this.payenddate = payenddate;
	}

	public String getBusiamt() {
		return busiamt;
	}

	public void setBusiamt(String busiamt) {
		this.busiamt = busiamt;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}				
	
}
