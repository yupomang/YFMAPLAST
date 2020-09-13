package com.yondervision.yfmap.result.hulunbuir;


/**
 * @author Caozhongyan
 *
 */
public class HulunbuirAppApi00703Result {
	/** 期数*/					
	private String termnum = "";					
	/** 开始日期 */					
	private String begdate = "";					
	/** 终止日期*/					
	private String enddate = "";					
	/** 未还罚息 */					
	private String owepun = "";
	/** 本期未还本金*/					
	private String iAmt1 = "";
	/** 应还本金 */					
	private String planprin = "";
	/** 应还利息 */					
	private String planint = "";
	/** 未还本金 */					
	private String oweprin = "";
	/** 未还利息*/					
	private String oweint = "";
	/** 逾期 */					
	private String curseqstate = "";
	/** 本金金额 */					
	private String initialbal = "";
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


	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
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

	public String getOweprin() {
		return oweprin;
	}

	public void setOweprin(String oweprin) {
		this.oweprin = oweprin;
	}

	public String getOweint() {
		return oweint;
	}

	public void setOweint(String oweint) {
		this.oweint = oweint;
	}

	public String getCurseqstate() {
		return curseqstate;
	}

	public void setCurseqstate(String curseqstate) {
		this.curseqstate = curseqstate;
	}

	public String getTermnum() {
		return termnum;
	}

	public void setTermnum(String termnum) {
		this.termnum = termnum;
	}

	public String getBegdate() {
		return begdate;
	}

	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}

	public String getOwepun() {
		return owepun;
	}

	public void setOwepun(String owepun) {
		this.owepun = owepun;
	}

	public String getiAmt1() {
		return iAmt1;
	}

	public void setiAmt1(String iAmt1) {
		this.iAmt1 = iAmt1;
	}

	public String getInitialbal() {
		return initialbal;
	}

	public void setInitialbal(String initialbal) {
		this.initialbal = initialbal;
	}
	
}
