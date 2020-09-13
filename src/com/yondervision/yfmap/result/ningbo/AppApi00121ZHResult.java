package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi00121ZHResult {
	
	private String 	resuflag;//  当前合同是否存在未办结的柜台还款  0：存在   1：不存在
	private String 	enddate;//代扣银行
	private String 	dedbankcode;//代扣银行
	private String 	dedaccnum;//代扣账号
	private String	oweint;//应还未还利息
	private String	oweprin;//应还未还本金
	private String	owepun;//未还罚息
	private String	undueprin;//未到期本金
	private String	newint;//新产生利息
	private String	currenddate;//当期终止日期
	private String	ahdrepaylowamt;//提前还款最低限额
	private String	instcode;//贷款机构
	private String	cleardate;//结清日期
	
	/* 贷款日期*/
	private String loandate = "";
	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	
	private String 	bankname;//受理支行名
	private String 	handset;//借款人手机号
	private String 	loanaccnum;//贷款账号
	private String 	loanamt;//贷款发放额
	private String 	curbal;//未还本金
	private String 	loanterm;//贷款期数
	private String 	monthrepayamt;//当前应还金额
	private String 	loanclosedate;//贷款结清日期
	private String 	loanrate;// 利率
	private String 	repaymode;//还款方式
	private String 	commloanflag;//组合贷款标志
	private String 	loancontrcode;//贷款合同号
	private String 	apprnum;//贷款受理号
	private String 	accname;//姓名
	private String 	comcialflag;//公转商标志
	private String 	guarmode;//担保方式
	private String 	housetype;//房屋类型
	private String 	lnundtkbcode;//贷款承办分行
	private String 	lnundtksubbcode;//贷款承办支行
	private String 	bankcode;//受理分行
	private String 	loancontrstate;//合同状态
	private String 	loanhousenum;//第N套房贷款
	private String 	planprin;//应还本金
	private String 	planint;//应还利息
	private String 	remainterm;//剩余期限
	private String 	repaycycle;//还款周期
	private String 	flag;//逾期标志
	private String 	loantype;//贷款类型
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
	public String getLoanaccnum() {
		return loanaccnum;
	}
	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}
	public String getLoanamt() {
		return loanamt;
	}
	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}
	public String getCurbal() {
		return curbal;
	}
	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getLoanclosedate() {
		return loanclosedate;
	}
	public void setLoanclosedate(String loanclosedate) {
		this.loanclosedate = loanclosedate;
	}
	public String getLoanrate() {
		return loanrate;
	}
	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}
	public String getCommloanflag() {
		return commloanflag;
	}
	public void setCommloanflag(String commloanflag) {
		this.commloanflag = commloanflag;
	}
	public String getLoancontrcode() {
		return loancontrcode;
	}
	public void setLoancontrcode(String loancontrcode) {
		this.loancontrcode = loancontrcode;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getGuarmode() {
		return guarmode;
	}
	public void setGuarmode(String guarmode) {
		this.guarmode = guarmode;
	}
	public String getHousetype() {
		return housetype;
	}
	public void setHousetype(String housetype) {
		this.housetype = housetype;
	}
	public String getLnundtkbcode() {
		return lnundtkbcode;
	}
	public void setLnundtkbcode(String lnundtkbcode) {
		this.lnundtkbcode = lnundtkbcode;
	}
	public String getLnundtksubbcode() {
		return lnundtksubbcode;
	}
	public void setLnundtksubbcode(String lnundtksubbcode) {
		this.lnundtksubbcode = lnundtksubbcode;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getLoancontrstate() {
		return loancontrstate;
	}
	public void setLoancontrstate(String loancontrstate) {
		this.loancontrstate = loancontrstate;
	}
	public String getLoanhousenum() {
		return loanhousenum;
	}
	public void setLoanhousenum(String loanhousenum) {
		this.loanhousenum = loanhousenum;
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
	public String getRemainterm() {
		return remainterm;
	}
	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
	}
	public String getRepaycycle() {
		return repaycycle;
	}
	public void setRepaycycle(String repaycycle) {
		this.repaycycle = repaycycle;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
	}
	public String getComcialflag() {
		return comcialflag;
	}
	public void setComcialflag(String comcialflag) {
		this.comcialflag = comcialflag;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public String getOweint() {
		return oweint;
	}
	public void setOweint(String oweint) {
		this.oweint = oweint;
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
	public String getCurrenddate() {
		return currenddate;
	}
	public void setCurrenddate(String currenddate) {
		this.currenddate = currenddate;
	}
	public String getAhdrepaylowamt() {
		return ahdrepaylowamt;
	}
	public void setAhdrepaylowamt(String ahdrepaylowamt) {
		this.ahdrepaylowamt = ahdrepaylowamt;
	}
	public String getDedbankcode() {
		return dedbankcode;
	}
	public void setDedbankcode(String dedbankcode) {
		this.dedbankcode = dedbankcode;
	}
	public String getDedaccnum() {
		return dedaccnum;
	}
	public void setDedaccnum(String dedaccnum) {
		this.dedaccnum = dedaccnum;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getResuflag() {
		return resuflag;
	}
	public void setResuflag(String resuflag) {
		this.resuflag = resuflag;
	}
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getCleardate() {
		return cleardate;
	}
	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}
}

