package com.yondervision.yfmap.result.jinan;

/** 
* @ClassName: AppApi00703Result 
* @Description: 还款计划查询
* @author syw
* 
*/ 
public class JiNanAppApi00703Result {
	/** 贷款账号 */						
	private String loancontrcode = "";						
	/** 还款方式 */						
	private String repaymode = "";						
	/** 期号 */						
	private String termnum = "";	
	/** 开始日期 */						
	private String begdate = "";
	/** 终止日期 */						
	private String enddate = "";
	/** 应还日期 */						
	private String repaydate = "";
	/** 应还本金*/						
	private String planprin = "";
	/** 应还利息 */						
	private String planint = "";
	public String getLoancontrcode() {
		return loancontrcode;
	}
	public void setLoancontrcode(String loancontrcode) {
		this.loancontrcode = loancontrcode;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
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
	public String getRepaydate() {
		return repaydate;
	}
	public void setRepaydate(String repaydate) {
		this.repaydate = repaydate;
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
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}
