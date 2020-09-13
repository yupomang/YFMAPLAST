package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi03822ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";
	
	private String 	oweprin;//应还未还本金
	private String 	owepun;//未还罚息
	private String 	oweint;//应还未还利息
	private String 	owetotalamt;//应还未还总金额
	private String 	undueprin;//未到期本金
	private String 	newint;//新产生利息
	private String 	ahdrepayallamt;//提前全部还款金额
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
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getOweprin() {
		return oweprin;
	}
	public void setOweprin(String oweprin) {
		this.oweprin = oweprin;
	}
	public String getOwepun() {
		return owepun;
	}
	public void setOwepun(String owepun) {
		this.owepun = owepun;
	}
	public String getOweint() {
		return oweint;
	}
	public void setOweint(String oweint) {
		this.oweint = oweint;
	}
	public String getOwetotalamt() {
		return owetotalamt;
	}
	public void setOwetotalamt(String owetotalamt) {
		this.owetotalamt = owetotalamt;
	}
	public String getUndueprin() {
		return undueprin;
	}
	public void setUndueprin(String undueprin) {
		this.undueprin = undueprin;
	}
	public String getNewint() {
		return newint;
	}
	public void setNewint(String newint) {
		this.newint = newint;
	}
	public String getAhdrepayallamt() {
		return ahdrepayallamt;
	}
	public void setAhdrepayallamt(String ahdrepayallamt) {
		this.ahdrepayallamt = ahdrepayallamt;
	}

}

