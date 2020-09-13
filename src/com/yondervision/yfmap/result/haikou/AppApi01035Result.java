package com.yondervision.yfmap.result.haikou;

/**
 * 业务办理    
 * @author ljd
 *
 */
public class AppApi01035Result {

	private String loancontrcode = "";	// <loancontrcode>合同代码</loancontrcode>
//	private String dkhkfs = "";	// 贷款还款方式   下面交易还会查出
	
	private String counts = ""; // <counts>笔数</counts>
	private String amt1 = ""; // <amt1>金额1</amt1>
	private String unitprop = ""; // <unitprop>单位比例</unitprop>
	private String isloanflag = ""; // 1-是，0-否
	
	private String loaneenum;                            // 借款人人数</loaneenum
	private String loanrate;                            // 贷款利率</loanrate;   
	private String loansort;                            // 贷款类别</loansort;   
	private String loanstate;                            // 贷款账户状态</loansta
	private String oenddate;                            // 原到期日期</oenddate; 
	private String monthrepayamt;                            // 贷款月还款额</mon
	private String mortlinkphone;                            // 抵押人联系电话</m
	private String mortname;                            // 抵押人姓名</mortname; 
	private String newint;                            // 新产生利息</newint;     
	private String oldrepayaccnum;                            // 原还款账号</oldr
	private String overdueint;                            // 逾期利息</overdueint
	private String overdueprin;                            // 逾期本金</overduepr
	private String oweamt;                            // 应还未还金额</oweamt;   
	private String owecnt;                            // 欠还次数</owecnt;       
	private String oweint;                            // 欠还利息</oweint;       
	private String oweprin;                            // 欠还本金</oweprin;     
	private String owepun;                            // 欠还罚息</owepun;       
	private String plantotalamt;                            // 应还总金额</planto
	private String plenum;                            // 质押物个数</plenum;     
	private String pun;                            // 罚息</pun;                 
	private String ratetype;                            // 利率类型</ratetype;   
	private String remainterm;                            // 剩余年限（月）</rema
	private String remainterms;                            // 剩余未还期数</remai
	private String repaycycle;                            // 还款周期</repaycycle
	private String repayday;                            // 还款日</repayday;     
	private String repaymodechgcnt;                            // 还款方式转换次数
	private String shttermcnt;                            // 缩期次数</shttermcnt
	private String sjhm;                            // 手机号码</sjhm;           
	private String terms;                            // 期数</terms;             
	private String tolowecnt;                            // 累计欠还次数</tolowec
	private String transacttype;                            // 办理类型</transact
	private String undueprin;                            // 未到期本金</undueprin
	private String unitlinkphone;                            // 单位联系电话</uni
	private String workunit;                            // 工作单位</workunit;   
	private String loanamt;                            // 贷款金额</loanamt;     
	private String accnum;                            // 个人账号</accnum;       
	private String accstate;                            // 账户状态</accstate;   
	private String agentbankcode;                            // 经办银行</agentba
	private String agentcompcode;                            // 代理公司</agentco
	private String agentcomptype;                            // 代理公司类型</age
	private String ahdpartrepaylow;                            // 提前部分还款额最 // 
	private String apprloanamt;                            // 审批贷款金额</apprl
	private String apprloanterm;                            // 审批贷款年限</appr
	private String attermdate;                            // 到期日期</attermdate
	private String bal;                            // 余额</bal;                 
	private String begdate;                            // 开始日期</begdate;     
	private String begintdate;                            // 起息日期</begintdate
	private String begterm;                            // 起始期数</begterm;     
	private String cleardate;                            // 结清日期</cleardate; 
	private String cocustname;                            // 贷款合作客户名称</co
	private String currate;                            // 执行利率</currate;     
	private String custid;                            // 个人客户号</custid;     
	private String dedbankaccnum;                            // 银行扣款账号</ded
	private String dedtype;                            // 代扣类型</dedtype;     
	private String department;                            // 经办部门</department
	private String dkdblx;                            // 贷款担保类型</dkdblx;   
	private String dkffe;                            // 贷款发放额</dkffe;       
	private String dkhkfs;                            // 贷款还款方式</dkhkfs;   
	private String dkqs;                            // 贷款期数</dkqs;           
	private String dkye;                            // 贷款余额</dkye;           
	private String dwmc;                            // 单位名称</dwmc;           
	private String email;                            // 电子邮箱</email;         
	private String enddate;                            // 终止日期</enddate;     
	private String exploandate;                            // 预放款日期</exploan
	private String exttermcnt;                            // 展期次数</exttermcnt
	private String famlinkphone;                            // 家庭联系电话</faml
	private String fammonthinc;                            // 家庭月收入</fammont
	private String fifthflag;                            // 五级分类标志</fifthfl
	private String fwxz;                            // 房屋性质</fwxz;           
	private String fwzl;                            // 房屋坐落</fwzl;           
	private String guarnum;                            // 抵押物个数</guarnum;   
	private String instcode;                            // 机构代码</instcode;   
	private String jkhtbh;                            // 借款合同编号</jkhtbh;   
	private String jkrxm;                            // 借款人姓名</jkrxm;       
	private String jkrzjh;                            // 借款人证件号</jkrzjh;   
	private String linkaddr;                            // 联系住址</linkaddr;   
	private String linkman;                            // 联系人</linkman;       
	private String linkmanphone;                            // 联系人电话</linkma
	private String loanaccnum;                            // 贷款账号</loanaccnum
	private String loancontrstate;                            // 合同状态</loanco
	private String loandate;                            // 放款日期</loandate;  
	private String dedbankcode;
	private String loanterm;
	private String ahdrepaylowamt;	//提前最低还款额
	private String ahdrepayallamt;	//提取全部还款额
	private String amt;	//还款后最低保留余额
	private String owetotalamt;	//应还未还总额
	private String apprnum;	//应还未还总额
	private String ahdrepayamt;	//提前还本金额
	private String dedaccnum;	//扣款账号
	
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getAmt1() {
		return amt1;
	}
	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}
	public String getUnitprop() {
		return unitprop;
	}
	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getLoaneenum() {
		return loaneenum;
	}
	public void setLoaneenum(String loaneenum) {
		this.loaneenum = loaneenum;
	}
	public String getLoanrate() {
		return loanrate;
	}
	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
	}
	public String getLoansort() {
		return loansort;
	}
	public void setLoansort(String loansort) {
		this.loansort = loansort;
	}
	public String getLoanstate() {
		return loanstate;
	}
	public void setLoanstate(String loanstate) {
		this.loanstate = loanstate;
	}
	public String getOenddate() {
		return oenddate;
	}
	public void setOenddate(String oenddate) {
		this.oenddate = oenddate;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getMortlinkphone() {
		return mortlinkphone;
	}
	public void setMortlinkphone(String mortlinkphone) {
		this.mortlinkphone = mortlinkphone;
	}
	public String getMortname() {
		return mortname;
	}
	public void setMortname(String mortname) {
		this.mortname = mortname;
	}
	public String getNewint() {
		return newint;
	}
	public void setNewint(String newint) {
		this.newint = newint;
	}
	public String getOldrepayaccnum() {
		return oldrepayaccnum;
	}
	public void setOldrepayaccnum(String oldrepayaccnum) {
		this.oldrepayaccnum = oldrepayaccnum;
	}
	public String getOverdueint() {
		return overdueint;
	}
	public void setOverdueint(String overdueint) {
		this.overdueint = overdueint;
	}
	public String getOverdueprin() {
		return overdueprin;
	}
	public void setOverdueprin(String overdueprin) {
		this.overdueprin = overdueprin;
	}
	public String getOweamt() {
		return oweamt;
	}
	public void setOweamt(String oweamt) {
		this.oweamt = oweamt;
	}
	public String getOwecnt() {
		return owecnt;
	}
	public void setOwecnt(String owecnt) {
		this.owecnt = owecnt;
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
	public String getPlantotalamt() {
		return plantotalamt;
	}
	public void setPlantotalamt(String plantotalamt) {
		this.plantotalamt = plantotalamt;
	}
	public String getPlenum() {
		return plenum;
	}
	public void setPlenum(String plenum) {
		this.plenum = plenum;
	}
	public String getPun() {
		return pun;
	}
	public void setPun(String pun) {
		this.pun = pun;
	}
	public String getRatetype() {
		return ratetype;
	}
	public void setRatetype(String ratetype) {
		this.ratetype = ratetype;
	}
	public String getRemainterm() {
		return remainterm;
	}
	public void setRemainterm(String remainterm) {
		this.remainterm = remainterm;
	}
	public String getRemainterms() {
		return remainterms;
	}
	public void setRemainterms(String remainterms) {
		this.remainterms = remainterms;
	}
	public String getRepaycycle() {
		return repaycycle;
	}
	public void setRepaycycle(String repaycycle) {
		this.repaycycle = repaycycle;
	}
	public String getRepayday() {
		return repayday;
	}
	public void setRepayday(String repayday) {
		this.repayday = repayday;
	}
	public String getRepaymodechgcnt() {
		return repaymodechgcnt;
	}
	public void setRepaymodechgcnt(String repaymodechgcnt) {
		this.repaymodechgcnt = repaymodechgcnt;
	}
	public String getShttermcnt() {
		return shttermcnt;
	}
	public void setShttermcnt(String shttermcnt) {
		this.shttermcnt = shttermcnt;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
	public String getTolowecnt() {
		return tolowecnt;
	}
	public void setTolowecnt(String tolowecnt) {
		this.tolowecnt = tolowecnt;
	}
	public String getTransacttype() {
		return transacttype;
	}
	public void setTransacttype(String transacttype) {
		this.transacttype = transacttype;
	}
	public String getUndueprin() {
		return undueprin;
	}
	public void setUndueprin(String undueprin) {
		this.undueprin = undueprin;
	}
	public String getUnitlinkphone() {
		return unitlinkphone;
	}
	public void setUnitlinkphone(String unitlinkphone) {
		this.unitlinkphone = unitlinkphone;
	}
	public String getWorkunit() {
		return workunit;
	}
	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}
	public String getLoanamt() {
		return loanamt;
	}
	public void setLoanamt(String loanamt) {
		this.loanamt = loanamt;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getAccstate() {
		return accstate;
	}
	public void setAccstate(String accstate) {
		this.accstate = accstate;
	}
	public String getAgentbankcode() {
		return agentbankcode;
	}
	public void setAgentbankcode(String agentbankcode) {
		this.agentbankcode = agentbankcode;
	}
	public String getAgentcompcode() {
		return agentcompcode;
	}
	public void setAgentcompcode(String agentcompcode) {
		this.agentcompcode = agentcompcode;
	}
	public String getAgentcomptype() {
		return agentcomptype;
	}
	public void setAgentcomptype(String agentcomptype) {
		this.agentcomptype = agentcomptype;
	}
	public String getAhdpartrepaylow() {
		return ahdpartrepaylow;
	}
	public void setAhdpartrepaylow(String ahdpartrepaylow) {
		this.ahdpartrepaylow = ahdpartrepaylow;
	}
	public String getApprloanamt() {
		return apprloanamt;
	}
	public void setApprloanamt(String apprloanamt) {
		this.apprloanamt = apprloanamt;
	}
	public String getApprloanterm() {
		return apprloanterm;
	}
	public void setApprloanterm(String apprloanterm) {
		this.apprloanterm = apprloanterm;
	}
	public String getAttermdate() {
		return attermdate;
	}
	public void setAttermdate(String attermdate) {
		this.attermdate = attermdate;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getBegdate() {
		return begdate;
	}
	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}
	public String getBegintdate() {
		return begintdate;
	}
	public void setBegintdate(String begintdate) {
		this.begintdate = begintdate;
	}
	public String getBegterm() {
		return begterm;
	}
	public void setBegterm(String begterm) {
		this.begterm = begterm;
	}
	public String getCleardate() {
		return cleardate;
	}
	public void setCleardate(String cleardate) {
		this.cleardate = cleardate;
	}
	public String getCocustname() {
		return cocustname;
	}
	public void setCocustname(String cocustname) {
		this.cocustname = cocustname;
	}
	public String getCurrate() {
		return currate;
	}
	public void setCurrate(String currate) {
		this.currate = currate;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getDedbankaccnum() {
		return dedbankaccnum;
	}
	public void setDedbankaccnum(String dedbankaccnum) {
		this.dedbankaccnum = dedbankaccnum;
	}
	public String getDedtype() {
		return dedtype;
	}
	public void setDedtype(String dedtype) {
		this.dedtype = dedtype;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDkdblx() {
		return dkdblx;
	}
	public void setDkdblx(String dkdblx) {
		this.dkdblx = dkdblx;
	}
	public String getDkffe() {
		return dkffe;
	}
	public void setDkffe(String dkffe) {
		this.dkffe = dkffe;
	}
	public String getDkqs() {
		return dkqs;
	}
	public void setDkqs(String dkqs) {
		this.dkqs = dkqs;
	}
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getExploandate() {
		return exploandate;
	}
	public void setExploandate(String exploandate) {
		this.exploandate = exploandate;
	}
	public String getExttermcnt() {
		return exttermcnt;
	}
	public void setExttermcnt(String exttermcnt) {
		this.exttermcnt = exttermcnt;
	}
	public String getFamlinkphone() {
		return famlinkphone;
	}
	public void setFamlinkphone(String famlinkphone) {
		this.famlinkphone = famlinkphone;
	}
	public String getFammonthinc() {
		return fammonthinc;
	}
	public void setFammonthinc(String fammonthinc) {
		this.fammonthinc = fammonthinc;
	}
	public String getFifthflag() {
		return fifthflag;
	}
	public void setFifthflag(String fifthflag) {
		this.fifthflag = fifthflag;
	}
	public String getFwxz() {
		return fwxz;
	}
	public void setFwxz(String fwxz) {
		this.fwxz = fwxz;
	}
	public String getFwzl() {
		return fwzl;
	}
	public void setFwzl(String fwzl) {
		this.fwzl = fwzl;
	}
	public String getGuarnum() {
		return guarnum;
	}
	public void setGuarnum(String guarnum) {
		this.guarnum = guarnum;
	}
	public String getInstcode() {
		return instcode;
	}
	public void setInstcode(String instcode) {
		this.instcode = instcode;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
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
	public String getLinkaddr() {
		return linkaddr;
	}
	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmanphone() {
		return linkmanphone;
	}
	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
	}
	public String getLoanaccnum() {
		return loanaccnum;
	}
	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}
	public String getLoancontrstate() {
		return loancontrstate;
	}
	public void setLoancontrstate(String loancontrstate) {
		this.loancontrstate = loancontrstate;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getLoancontrcode() {
		return loancontrcode;
	}
	public void setLoancontrcode(String loancontrcode) {
		this.loancontrcode = loancontrcode;
	}
	public String getDkhkfs() {
		return dkhkfs;
	}
	public void setDkhkfs(String dkhkfs) {
		this.dkhkfs = dkhkfs;
	}
	public String getDedbankcode() {
		return dedbankcode;
	}
	public void setDedbankcode(String dedbankcode) {
		this.dedbankcode = dedbankcode;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getAhdrepaylowamt() {
		return ahdrepaylowamt;
	}
	public void setAhdrepaylowamt(String ahdrepaylowamt) {
		this.ahdrepaylowamt = ahdrepaylowamt;
	}
	public String getAhdrepayallamt() {
		return ahdrepayallamt;
	}
	public void setAhdrepayallamt(String ahdrepayallamt) {
		this.ahdrepayallamt = ahdrepayallamt;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getOwetotalamt() {
		return owetotalamt;
	}
	public void setOwetotalamt(String owetotalamt) {
		this.owetotalamt = owetotalamt;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getAhdrepayamt() {
		return ahdrepayamt;
	}
	public void setAhdrepayamt(String ahdrepayamt) {
		this.ahdrepayamt = ahdrepayamt;
	}
	public String getDedaccnum() {
		return dedaccnum;
	}
	public void setDedaccnum(String dedaccnum) {
		this.dedaccnum = dedaccnum;
	}
	
}
