package com.yondervision.yfmap.result.ningbo;
/**
 *  个人账户基本信息查询
 */
public class AppApi00101ZHResult {
	/** 关联银行卡号*/
	private String cardno;
	
	/** 原始密码标志 1-是 0-否*/
	private String unitaccnum;
	/** 原始密码标志 1-是 0-否*/
	private String initialpwdflag;
	/** 面签标志，0非面签，1是面签*/
	private String signer = "";

	/** 机构号 */
	private String brcCode = "";
	
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 个人公积金账号*/
	private String accnum = "";
	/** 个人姓名*/
	private String accname = "";
	/** 证件号码*/
	private String certinum = "";
	/** 临时变量100*/
	private String actmp100 = "";
	/** 临时变量1024*/
	private String actmp1024 = "";
	/** 临时变量200*/
	private String actmp200 = "";
	/** 金额*/
	private String amt = "";
	/** 金额3*/
	private String amt3 = "";
	/** 金额7*/
	private String amt7 = "";
	/** 金额8*/
	private String amt8 = "";
	/** 余额*/
	private String bal = "";
	/** 银行名称*/
	private String bankname = "";
	/** 银行代码*/
	private String bankcode = "";
	/** 缴存基数*/
	private String basenum = "";
	/** 开始日期*/
	private String begdate = "";
	/** 卡状态*/
	private String cardstate = "";
	/** 证件类型*/
	private String certitype = "";
	/** 备用字符*/
	private String freeuse1 = "";
	/** 冻结金额*/
	private String frzamt = "";
	/** 冻结标志*/
	private String frzflag = "";
	/** 手机号*/
	private String handset = "";
	/** 个人账户状态*/
	private String indiaccstate = "";
	/** 缴至年月*/
	private String lpaym = "";
	/** 按月补贴利息*/
	private String monintamt = "";
	/** 月应汇缴额*/
	private String monpaysum = "";
	/** 一次性住房补贴利息*/
	private String subintamt = "";
	/** 补充户利息*/
	private String supintamt = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 账户机构*/
	private String accinstcode = "";
	/** 账户机构描述*/
	private String accinstcodedes = "";
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
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getActmp100() {
		return actmp100;
	}
	public void setActmp100(String actmp100) {
		this.actmp100 = actmp100;
	}
	public String getActmp1024() {
		return actmp1024;
	}
	public void setActmp1024(String actmp1024) {
		this.actmp1024 = actmp1024;
	}
	public String getActmp200() {
		return actmp200;
	}
	public void setActmp200(String actmp200) {
		this.actmp200 = actmp200;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getAmt3() {
		return amt3;
	}
	public void setAmt3(String amt3) {
		this.amt3 = amt3;
	}
	public String getAmt7() {
		return amt7;
	}
	public void setAmt7(String amt7) {
		this.amt7 = amt7;
	}
	public String getAmt8() {
		return amt8;
	}
	public void setAmt8(String amt8) {
		this.amt8 = amt8;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBasenum() {
		return basenum;
	}
	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}
	public String getBegdate() {
		return begdate;
	}
	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}
	public String getCardstate() {
		return cardstate;
	}
	public void setCardstate(String cardstate) {
		this.cardstate = cardstate;
	}
	public String getCertitype() {
		return certitype;
	}
	public void setCertitype(String certitype) {
		this.certitype = certitype;
	}
	public String getFreeuse1() {
		return freeuse1;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	public String getFrzamt() {
		return frzamt;
	}
	public void setFrzamt(String frzamt) {
		this.frzamt = frzamt;
	}
	public String getFrzflag() {
		return frzflag;
	}
	public void setFrzflag(String frzflag) {
		this.frzflag = frzflag;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public String getIndiaccstate() {
		return indiaccstate;
	}
	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}
	public String getLpaym() {
		return lpaym;
	}
	public void setLpaym(String lpaym) {
		this.lpaym = lpaym;
	}
	public String getMonintamt() {
		return monintamt;
	}
	public void setMonintamt(String monintamt) {
		this.monintamt = monintamt;
	}
	public String getMonpaysum() {
		return monpaysum;
	}
	public void setMonpaysum(String monpaysum) {
		this.monpaysum = monpaysum;
	}
	public String getSubintamt() {
		return subintamt;
	}
	public void setSubintamt(String subintamt) {
		this.subintamt = subintamt;
	}
	public String getSupintamt() {
		return supintamt;
	}
	public void setSupintamt(String supintamt) {
		this.supintamt = supintamt;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getAccinstcode() {
		return accinstcode;
	}
	public void setAccinstcode(String accinstcode) {
		this.accinstcode = accinstcode;
	}
	public String getAccinstcodedes() {
		return accinstcodedes;
	}
	public void setAccinstcodedes(String accinstcodedes) {
		this.accinstcodedes = accinstcodedes;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getBrcCode() {
		return brcCode;
	}
	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}
	public String getInitialpwdflag() {
		return initialpwdflag;
	}
	public void setInitialpwdflag(String initialpwdflag) {
		this.initialpwdflag = initialpwdflag;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
}
