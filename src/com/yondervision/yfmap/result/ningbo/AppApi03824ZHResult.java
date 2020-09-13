package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi03824ZHResult {
	private String stdata;
	private String stdata1;
	private String stdata2;
	private String stdata3;
	private String stdata4;

	public String getStdata() {
		return this.stdata;
	}

	public void setStdata(final String stdata) {
		this.stdata = stdata;
	}

	public String getStdata1() {
		return this.stdata1;
	}

	public void setStdata1(final String stdata1) {
		this.stdata1 = stdata1;
	}

	public String getStdata2() {
		return this.stdata2;
	}

	public void setStdata2(final String stdata2) {
		this.stdata2 = stdata2;
	}

	public String getStdata3() {
		return this.stdata3;
	}

	public void setStdata3(final String stdata3) {
		this.stdata3 = stdata3;
	}

	public String getStdata4() {
		return this.stdata4;
	}

	public void setStdata4(final String stdata4) {
		this.stdata4 = stdata4;
	}

	private String projectname_wb;//楼盘名称
	private String cocustname_wb;//房产商名称
	private String actmp1;//结顶幢号
	private String actmp2;//签约银行
	private String acTransdate;//签约日期
	
	private String actmp100;//下传文件路径	下传
	private String num_web3;//总记录数	下传
	private String num_web1;//当前页数	上下传
	private String num_web2;//当前页面记录数	上下传
	private String num_web4;//总页数	下传
	
	/* 网厅证明出具时间*/
	private String transdate = "";
	/* 联系人*/
	private String linkman = "";
	/* 联系电话*/
	private String linkphone = "";	
	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/*受理号*/
	private String apprnum = "";

	private String 	accname;//职工姓名
	private String 	accnum;//个人公积金帐号
	private String 	addr;//公积金贷款城市
	private String 	amt;//贷款金额
	private String 	bal;//缴存余额
	private String 	basenum;//缴存基数
	private String 	certinum;//身份证号
	private String 	flag;//该职工公积金贷款记录情况
	private String 	indiaccstate;//账户状态
	private String 	indipaysum;//月缴存额
	private String 	indiprop;//个人缴存比例
	private String 	loancontrnum;//编号
	private String 	month;//开始月
	private String 	month1;//结束月
	private String 	opnaccdate;//开户时间
	private String 	projectname;//缴存地名称
	private String 	unitaccname;//单位名称
	private String 	unitprop;//单位缴存比例
	private String 	year;//开始年
	private String 	year1;//结束年
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
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getBasenum() {
		return basenum;
	}
	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getIndiaccstate() {
		return indiaccstate;
	}
	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}
	public String getIndipaysum() {
		return indipaysum;
	}
	public void setIndipaysum(String indipaysum) {
		this.indipaysum = indipaysum;
	}
	public String getIndiprop() {
		return indiprop;
	}
	public void setIndiprop(String indiprop) {
		this.indiprop = indiprop;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonth1() {
		return month1;
	}
	public void setMonth1(String month1) {
		this.month1 = month1;
	}
	public String getOpnaccdate() {
		return opnaccdate;
	}
	public void setOpnaccdate(String opnaccdate) {
		this.opnaccdate = opnaccdate;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getUnitprop() {
		return unitprop;
	}
	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear1() {
		return year1;
	}
	public void setYear1(String year1) {
		this.year1 = year1;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getActmp100() {
		return actmp100;
	}
	public void setActmp100(String actmp100) {
		this.actmp100 = actmp100;
	}
	public String getNum_web3() {
		return num_web3;
	}
	public void setNum_web3(String num_web3) {
		this.num_web3 = num_web3;
	}
	public String getNum_web1() {
		return num_web1;
	}
	public void setNum_web1(String num_web1) {
		this.num_web1 = num_web1;
	}
	public String getNum_web2() {
		return num_web2;
	}
	public void setNum_web2(String num_web2) {
		this.num_web2 = num_web2;
	}
	public String getNum_web4() {
		return num_web4;
	}
	public void setNum_web4(String num_web4) {
		this.num_web4 = num_web4;
	}
	public String getProjectname_wb() {
		return projectname_wb;
	}
	public void setProjectname_wb(String projectname_wb) {
		this.projectname_wb = projectname_wb;
	}
	public String getCocustname_wb() {
		return cocustname_wb;
	}
	public void setCocustname_wb(String cocustname_wb) {
		this.cocustname_wb = cocustname_wb;
	}
	public String getActmp1() {
		return actmp1;
	}
	public void setActmp1(String actmp1) {
		this.actmp1 = actmp1;
	}
	public String getActmp2() {
		return actmp2;
	}
	public void setActmp2(String actmp2) {
		this.actmp2 = actmp2;
	}
	public String getAcTransdate() {
		return acTransdate;
	}
	public void setAcTransdate(String acTransdate) {
		this.acTransdate = acTransdate;
	}


	
}

