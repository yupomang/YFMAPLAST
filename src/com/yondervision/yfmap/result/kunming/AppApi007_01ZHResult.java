package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 * 批量下传  --  个人贷款还款明细查询
 */
public class AppApi007_01ZHResult {

	/** 期数*/
	private String seqnum = "";
	/** 业务类型*/
	private String transtype = "";
	/** 应还日期*/
	private String enddate = "";
	/** 应还合计*/
	private String plansum = "";
	/** 应还本金*/
	private String planprin = "";
	/** 应还利息*/
	private String planint = "";
	/** 实还日期*/
	private String infactdate = "";
	/** 实还合计*/
	private String infactsum = "";
	/** 实还本金*/
	private String infactprin = "";
	/** 实还利息*/
	private String infactint = "";
	/** 本期状态*/
	private String curseqstate = "";
	/** 罚息*/
	private String infactpun = "";
	
	public String getInfactpun() {
		return infactpun;
	}
	public void setInfactpun(String infactpun) {
		this.infactpun = infactpun;
	}
	public String getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(String seqnum) {
		this.seqnum = seqnum;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getPlansum() {
		return plansum;
	}
	public void setPlansum(String plansum) {
		this.plansum = plansum;
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
	public String getInfactdate() {
		return infactdate;
	}
	public void setInfactdate(String infactdate) {
		this.infactdate = infactdate;
	}
	public String getInfactsum() {
		return infactsum;
	}
	public void setInfactsum(String infactsum) {
		this.infactsum = infactsum;
	}
	public String getInfactprin() {
		return infactprin;
	}
	public void setInfactprin(String infactprin) {
		this.infactprin = infactprin;
	}
	public String getInfactint() {
		return infactint;
	}
	public void setInfactint(String infactint) {
		this.infactint = infactint;
	}
	public String getCurseqstate() {
		return curseqstate;
	}
	public void setCurseqstate(String curseqstate) {
		this.curseqstate = curseqstate;
	}
	
	
}
