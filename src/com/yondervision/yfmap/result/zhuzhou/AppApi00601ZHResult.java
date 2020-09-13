package com.yondervision.yfmap.result.zhuzhou;
/**
 * 个人贷款基本信息查询
 * @author qinxla
 *
 */
public class AppApi00601ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 借款人姓名 */
	private String jkrxm = "";	
	/** 借款人证件号 */
	private String jkrzjh = "";	
	/** 受托银行名称 */
	private String styhmc = "";	
	/** 借款合同编号 */
	private String jkhtbh = "";	
	/** 合同贷款金额 */
	private String htdkje = "";	
	/** 贷款余额 */
	private String dkye = "";	
	/** 贷款期数 */
	private String dkqs = "";	
	/** 当期计划还款金额 */
	private String dqjhhkje = "";	
	/** 执行利率 */
	private String currate = "";	
	/** 约定放款日期 */
	private String ydfkrq = "";	
	/** 约定还款日 */
	private String ydhkr = "";	
	/** 是否公积金对冲 */
	private String gjjflag = "";
	/** 逾期本金总额 */
	private String yqbjze = "";	
	/** 逾期利息总额 */
	private String yqlxze = "";	
	/** 罚息总额 */
	private String fxze = "";	
	/** 累计逾期期数 */
	private String ljyqqs = "";	
	/** 房屋坐落 */
	private String fwzl = "";	
	/** 还款账号 */
	private String hkzh = "";
	//=======补充  开始=========
	
	private String loancontrstate = "";
	
	private String grzh = "";
	/** 文件名*/
	private String filename = "";
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
	private String apprnum = "";

	//=======补充  结束=========
	
	/**	 贷款发放日期*/
	private String dkffrq = "";
	/** 借款人姓名 */
	private String xingming = "";
	/** 证件号码 */
	private String zjhm = "";
	/** 经办银行 */
	private String swtyhmc = "";
	/** 借款日期 */
	private String appdate = "";
	/** 执行利率 */
	private String dkll = "";
	/** 逾期罚息 */
	private String owepun = "";
	
	public String getLoancontrstate() {
		return loancontrstate;
	}
	public void setLoancontrstate(String loancontrstate) {
		this.loancontrstate = loancontrstate;
	}
	public String getAppdate() {
		return appdate;
	}
	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	public String getOwepun() {
		return owepun;
	}
	public void setOwepun(String owepun) {
		this.owepun = owepun;
	}
	public String getSwtyhmc() {
		return swtyhmc;
	}
	public void setSwtyhmc(String swtyhmc) {
		this.swtyhmc = swtyhmc;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getDkll() {
		return dkll;
	}
	public void setDkll(String dkll) {
		this.dkll = dkll;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
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
	public String getJkrxm() {
		return jkrxm;
	}
	public void setJkrxm(String jkrxm) {
		this.jkrxm = jkrxm;
	}
	public String getJkrzjh() {
		return jkrzjh;
	}
	public void setJkrzjh(String jkrzjh) {
		this.jkrzjh = jkrzjh;
	}
	public String getStyhmc() {
		return styhmc;
	}
	public void setStyhmc(String styhmc) {
		this.styhmc = styhmc;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}
	public String getHtdkje() {
		return htdkje;
	}
	public void setHtdkje(String htdkje) {
		this.htdkje = htdkje;
	}
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getDkqs() {
		return dkqs;
	}
	public void setDkqs(String dkqs) {
		this.dkqs = dkqs;
	}
	public String getDqjhhkje() {
		return dqjhhkje;
	}
	public void setDqjhhkje(String dqjhhkje) {
		this.dqjhhkje = dqjhhkje;
	}
	public String getCurrate() {
		return currate;
	}
	public void setCurrate(String currate) {
		this.currate = currate;
	}
	public String getYdfkrq() {
		return ydfkrq;
	}
	public void setYdfkrq(String ydfkrq) {
		this.ydfkrq = ydfkrq;
	}
	public String getYdhkr() {
		return ydhkr;
	}
	public void setYdhkr(String ydhkr) {
		this.ydhkr = ydhkr;
	}
	public String getGjjflag() {
		return gjjflag;
	}
	public void setGjjflag(String gjjflag) {
		this.gjjflag = gjjflag;
	}
	public String getYqbjze() {
		return yqbjze;
	}
	public void setYqbjze(String yqbjze) {
		this.yqbjze = yqbjze;
	}
	public String getYqlxze() {
		return yqlxze;
	}
	public void setYqlxze(String yqlxze) {
		this.yqlxze = yqlxze;
	}
	public String getFxze() {
		return fxze;
	}
	public void setFxze(String fxze) {
		this.fxze = fxze;
	}
	public String getLjyqqs() {
		return ljyqqs;
	}
	public void setLjyqqs(String ljyqqs) {
		this.ljyqqs = ljyqqs;
	}
	public String getFwzl() {
		return fwzl;
	}
	public void setFwzl(String fwzl) {
		this.fwzl = fwzl;
	}
	public String getHkzh() {
		return hkzh;
	}
	public void setHkzh(String hkzh) {
		this.hkzh = hkzh;
	}
	public String getDkffrq() {
		return dkffrq;
	}
	public void setDkffrq(String dkffrq) {
		this.dkffrq = dkffrq;
	}	
	
}
