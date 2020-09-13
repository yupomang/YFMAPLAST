package com.yondervision.yfmap.result.baogang;


/**
 * @author Caozhongyan
 *
 */
public class BaoGangAppApi00703Result {
	/** 标志*/					
	private String seqnum1 = "";					
	/** 还款止日 */					
	private String enddate = "";					
	/** 实际还款日期*/					
	private String infactdate = "";					
	/** 期初余额 */					
	private String firstloanbal = "";
	/** 本期应还金额 */					
	private String amt = "";
	/** 本期应还本金 */					
	private String planprin = "";
	/** 本期应还利息 */					
	private String planint = "";
	/** 本期未还本金 */					
	private String oweprin = "";
	/** 本期未还利息*/					
	private String oweint = "";
	/** 本期状态 */					
	private String curseqstate = "";
	/** 交易代码 */					
	private String acTransCode = "";
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

	public String getSeqnum1() {
		return seqnum1;
	}

	public void setSeqnum1(String seqnum1) {
		this.seqnum1 = seqnum1;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getInfactdate() {
		return infactdate;
	}

	public void setInfactdate(String infactdate) {
		this.infactdate = infactdate;
	}

	public String getFirstloanbal() {
		return firstloanbal;
	}

	public void setFirstloanbal(String firstloanbal) {
		this.firstloanbal = firstloanbal;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
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

	public String getAcTransCode() {
		return acTransCode;
	}

	public void setAcTransCode(String acTransCode) {
		this.acTransCode = acTransCode;
	}
	
}
