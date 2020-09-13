package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi00120ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";
	
	private String 	instcode;//机构代码
	private String 	apprstate;//合同状态
	private String 	accname;//借款人姓名
	private String 	apploanamt;//公积金申请金额
	private String 	commloanamt;//商业贷款金额
	private String 	apploanterm;//申请贷款期限
	private String 	appdate;//申请日期
	private String 	houseaddr;//房屋地址
	private String 	approvedate;//核准日期
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
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getApploanamt() {
		return apploanamt;
	}
	public void setApploanamt(String apploanamt) {
		this.apploanamt = apploanamt;
	}
	public String getCommloanamt() {
		return commloanamt;
	}
	public void setCommloanamt(String commloanamt) {
		this.commloanamt = commloanamt;
	}
	public String getApploanterm() {
		return apploanterm;
	}
	public void setApploanterm(String apploanterm) {
		this.apploanterm = apploanterm;
	}
	public String getAppdate() {
		return appdate;
	}
	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	public String getHouseaddr() {
		return houseaddr;
	}
	public void setHouseaddr(String houseaddr) {
		this.houseaddr = houseaddr;
	}
	public String getApprovedate() {
		return approvedate;
	}
	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}
	public String getApprstate() {
		return apprstate;
	}
	public void setApprstate(String apprstate) {
		this.apprstate = apprstate;
	}

}

