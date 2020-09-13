package com.yondervision.yfmap.result.baogang;

/** 
* @ClassName: AppApi00501Result 
* @Description: 提取明细查询
* 
*/ 
public class BaoGangAppApi01101Result {
	/** 借款人合同号*/						
	private String loancontrnum = "";
	/** 身份证号码*/						
	private String cerid1 = "";
	/** 审批状态 */						
	private String apprflag = "";
	private String apprflagMSG = "";
	private String apprfDate = "";
	/** 返回码 */						
	private String recode = "";
	/** 返回描述 */						
	private String msg = "";
	private String isloanflag = "";
	private String accinstname = "";
	private String sendSeqno="";
	private String sendDate="";
	private String sendTime="";
	public String getApprflag() {
		return apprflag;
	}
	public void setApprflag(String apprflag) {
		this.apprflag = apprflag;
	}
	public String getApprflagMSG() {
		return apprflagMSG;
	}
	public void setApprflagMSG(String apprflagMSG) {
		this.apprflagMSG = apprflagMSG;
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
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getAccinstname() {
		return accinstname;
	}
	public void setAccinstname(String accinstname) {
		this.accinstname = accinstname;
	}
	public String getApprfDate() {
		return apprfDate;
	}
	public void setApprfDate(String apprfDate) {
		this.apprfDate = apprfDate;
	}
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getCerid1() {
		return cerid1;
	}
	public void setCerid1(String cerid1) {
		this.cerid1 = cerid1;
	}
	
}
