package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author 
 *  个人 -- 贷款基本信息查询
 */
import java.math.BigDecimal;
import java.util.Date;

public class AppApi00601ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 机构名称*/
	private String instcode = "";
	/** 经办银行*/
	private String agencybank = "";
	/** 合同号*/
	private String loancontrnum = "";
	/** 贷款金额*/
	private String loansum = "";
	/** 当前金额*/
	private String curdaybal = "";
	/** 执行利率*/
	private String currate = "";
	/** 贷款期限*/
	private String loanterm = "";
	/** 放款日期*/
	private String beginintdate = "";
	/** 到期日期*/
	private String attermdate = "";
	/** 还款方式*/
	private String retloanmode = "";
	/** 代扣银行账号*/
	private String bankaccnum = "";
	/** 代扣银行户名*/
	private String bankaccname = "";
	/** 剩余期次*/
	private String leavterm = "";
	/** 提前还款次数*/
	private String plncnt = "";
	/** 未还本金*/
	private String oweprin = "";
	/** 未还利息*/
	private String oweint = "";
	/** 未还罚息*/
	private String spedealint = "";
	/** 欠还总额*/
	private String tolamt = "";
	/** 借款人1姓名*/
	private String accname1 = "";
	/** 借款人1省份证号*/
	private String cerid1 = "";
	/** 借款人1单位公积金账号*/
	private String unitaccnum1 = "";
	/** 借款人2姓名*/
	private String accname2 = "";
	/** 借款人2省份证号*/
	private String cerid2 = "";
	/** 借款人2单位公积金账号*/
	private String unitaccnum2 = "";
	/** 担保方式*/
	private String assuretype = "";
	/** 状态标志*/
	private String state = "";
	/** 累计欠还次数*/
	private String tolowecnt = "";
	/** 应还本金合计*/
	private String tolplanprin = "";
	/** 应还利息合计*/
	private String tolplanint = "";
	/** 累计实还本金*/
	private String tolinfactprin = "";
	/** 累计实还利息*/
	private String tolinfactint = "";
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
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getAgencybank() {
		return agencybank;
	}
	public void setAgencybank(String agencybank) {
		this.agencybank = agencybank;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getLoansum() {
		return loansum;
	}
	public void setLoansum(String loansum) {
		this.loansum = loansum;
	}
	public String getCurdaybal() {
		return curdaybal;
	}
	public void setCurdaybal(String curdaybal) {
		this.curdaybal = curdaybal;
	}
	public String getCurrate() {
		return currate;
	}
	public void setCurrate(String currate) {
		this.currate = currate;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getBeginintdate() {
		return beginintdate;
	}
	public void setBeginintdate(String beginintdate) {
		this.beginintdate = beginintdate;
	}
	public String getAttermdate() {
		return attermdate;
	}
	public void setAttermdate(String attermdate) {
		this.attermdate = attermdate;
	}
	public String getRetloanmode() {
		return retloanmode;
	}
	public void setRetloanmode(String retloanmode) {
		this.retloanmode = retloanmode;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getBankaccname() {
		return bankaccname;
	}
	public void setBankaccname(String bankaccname) {
		this.bankaccname = bankaccname;
	}
	public String getLeavterm() {
		return leavterm;
	}
	public void setLeavterm(String leavterm) {
		this.leavterm = leavterm;
	}
	public String getPlncnt() {
		return plncnt;
	}
	public void setPlncnt(String plncnt) {
		this.plncnt = plncnt;
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
	public String getSpedealint() {
		return spedealint;
	}
	public void setSpedealint(String spedealint) {
		this.spedealint = spedealint;
	}
	public String getTolamt() {
		return tolamt;
	}
	public void setTolamt(String tolamt) {
		this.tolamt = tolamt;
	}
	public String getAccname1() {
		return accname1;
	}
	public void setAccname1(String accname1) {
		this.accname1 = accname1;
	}
	public String getCerid1() {
		return cerid1;
	}
	public void setCerid1(String cerid1) {
		this.cerid1 = cerid1;
	}
	public String getUnitaccnum1() {
		return unitaccnum1;
	}
	public void setUnitaccnum1(String unitaccnum1) {
		this.unitaccnum1 = unitaccnum1;
	}
	public String getAccname2() {
		return accname2;
	}
	public void setAccname2(String accname2) {
		this.accname2 = accname2;
	}
	public String getCerid2() {
		return cerid2;
	}
	public void setCerid2(String cerid2) {
		this.cerid2 = cerid2;
	}
	public String getUnitaccnum2() {
		return unitaccnum2;
	}
	public void setUnitaccnum2(String unitaccnum2) {
		this.unitaccnum2 = unitaccnum2;
	}
	public String getAssuretype() {
		return assuretype;
	}
	public void setAssuretype(String assuretype) {
		this.assuretype = assuretype;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getTolowecnt() {
		return tolowecnt;
	}
	public void setTolowecnt(String tolowecnt) {
		this.tolowecnt = tolowecnt;
	}
	public String getTolplanprin() {
		return tolplanprin;
	}
	public void setTolplanprin(String tolplanprin) {
		this.tolplanprin = tolplanprin;
	}
	public String getTolplanint() {
		return tolplanint;
	}
	public void setTolplanint(String tolplanint) {
		this.tolplanint = tolplanint;
	}
	public String getTolinfactprin() {
		return tolinfactprin;
	}
	public void setTolinfactprin(String tolinfactprin) {
		this.tolinfactprin = tolinfactprin;
	}
	public String getTolinfactint() {
		return tolinfactint;
	}
	public void setTolinfactint(String tolinfactint) {
		this.tolinfactint = tolinfactint;
	}
	
	
}
