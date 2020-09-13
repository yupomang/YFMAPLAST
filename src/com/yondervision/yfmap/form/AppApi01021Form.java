package com.yondervision.yfmap.form;

public class AppApi01021Form extends AppApiCommonForm {
	/*
	 	// 字段不知道哪来的
	 	<ahdrepayamt>提前还本金</ahdrepayamt>
		<cardno>卡号</cardno>
		<chkamt>复审金额</chkamt>
		<custid>个人客户号</custid>
		<drawcanflag>提取/销户标志</drawcanflag>
		<famt1>临时金额1</famt1>
		<firstflag>提取依据是否首次提取</firstflag>
		<firstpayamt>首付款</firstpayamt>
		<inputamt>初审金额</inputamt>
		<lastdrawdate>上笔提取日期</lastdrawdate>
		<lasttrsdate>上笔转账提取日期</lasttrsdate>
		<orelation>提取人与提取依据2所有人关系</orelation>
		<owncertitype>所有权人证件类型</owncertitype>
		<paperrelation>收款账号与提取人关系</paperrelation>
		<payeebankacc0>收款单位银行账号</payeebankacc0>
		<payeebankaccnm0>收款单位名称</payeebankaccnm0>
		<payeebankname>收款开户银行</payeebankname>
		<procertinum>代领人证件号码</procertinum>
		<procode>提取依据编码</procode>
		<proxcertitype>代领人证件类型</proxcertitype>
		<proxname>代领人姓名</proxname>
		<relation>关系</relation>
		<rentdate>租房起始日期</rentdate>
		<rentmonths>租房月数</rentmonths>
		<settlemode>租房月数</settlemode>
	 */
	
	private String ahdrepayamt="";	// <ahdrepayamt>提前还本金</ahdrepayamt>
	private String area="";	// <area>面积</area>
	private String billamt="";	// <billamt>票据金额</billamt>
	private String billdate="";	// <billdate>票据日期</billdate>
	private String buyhouseamt="";	// <buyhouseamt>购房款总额</buyhouseamt>
	private String buyhousedate="";	// <buyhousedate>购房日期</buyhousedate>
	private String buyhousename="";	// <buyhousename>购房人姓名</buyhousename>
	private String buyhousetype="";	// <buyhousetype>购房类型</buyhousetype>
	private String cardno="";	// <cardno>卡号</cardno>
	private String chkamt="";	// <chkamt>复审金额</chkamt>
	private String ctlflag="";	// <ctlflag>控制金额</ctlflag>
	private String custid="";	// <custid>个人客户号</custid>
	private String drawcanflag="";	// <drawcanflag>提取/销户标志</drawcanflag>
	private String drawreason="";	// <drawreason>提取原因</drawreason>
	private String drawreasoncode1="";	// <drawreasoncode1>提取依据号1</drawreasoncode1>
	private String drawreasoncode2="";	// <drawreasoncode1>提取依据号1</drawreasoncode1>
	private String dwzh="";	// <dwzh>单位账号</dwzh>
	private String fammonthinc="";	// <fammonthinc>家庭收入</fammonthinc>
	private String famt1="";	// <famt1>临时金额1</famt1>
	private String firstflag="";	// <firstflag>提取依据是否首次提取</firstflag>
	private String firstpayamt="";	// <firstpayamt>首付款</firstpayamt>
	private String grzh="";	// <grzh>个人账号</grzh>
	private String grzhye="";	// <grzhye>个人账户余额</grzhye>
	private String houseaddr="";	// <houseaddr>房屋坐落</houseaddr>
	private String illstatetype="";	// <illstatetype>病情类型</illstatetype>
	private String inputamt="";	// <inputamt>初审金额</inputamt>
	private String lastdrawdate="";	// <lastdrawdate>上笔提取日期</lastdrawdate>
	private String lasttrsdate="";	// <lasttrsdate>上笔转账提取日期</lasttrsdate>
	private String loandate="";	// <loandate>放款日期</loandate>
	private String loansum="";	// <loansum>贷款额度</loansum>
	private String loanterm="";	// <loanterm>贷款期限</loanterm>
	private String matecertinum="";	// <matecertinum>配偶证件号码</matecertinum>
	private String matecertitype="";	// <matecertitype>配偶证件类型</matecertitype>
	private String matename="";	// <matename>配偶姓名</matename>
	private String monthrepayamt="";	// <monthrepayamt>贷款月还款额</monthrepayamt>
	private String orelation="";	// <orelation>提取人与提取依据2所有人关系</orelation>
	private String owncertinum="";	// <owncertinum>所有权人证件号码</owncertinum>
	private String owncertinum1="";	// <owncertinum1>共有权人一证件号码</owncertinum1>
	private String owncertinum2="";	// <owncertinum2>共有权人二证件号码</owncertinum2>
	private String owncertinum3="";	// <owncertinum3>共有权人三证件号码</owncertinum3>
	private String owncertinum4="";	// <owncertinum4>共有权人四证件号码</owncertinum4>
	private String owncertinum5="";	// <owncertinum5>共有权人五证件号码</owncertinum5>
	private String owncertitype="";	// <owncertitype>所有权人证件类型</owncertitype>
	private String owncertitype1="";	// <owncertitype1>共有权人一证件类型</owncertitype1>
	private String owncertitype2="";	// <owncertitype2>共有权人二证件类型</owncertitype2>
	private String owncertitype3="";	// <owncertitype3>共有权人三证件类型</owncertitype3>
	private String owncertitype4="";	// <owncertitype4>共有权人四证件类型</owncertitype4>
	private String owncertitype5="";	// <owncertitype5>共有权人五证件类型</owncertitype5>
	private String ownname1="";	// <ownname1>共有权人一姓名</ownname1>
	private String ownname2="";	// <ownname2>共有权人二姓名</ownname2>
	private String ownname3="";	// <ownname3>共有权人三姓名</ownname3>
	private String ownname4="";	// <ownname4>共有权人四姓名</ownname4>
	private String ownname5="";	// <ownname5>共有权人五姓名</ownname5>
	private String paperrelation="";	// <paperrelation>收款账号与提取人关系</paperrelation>
	private String pataccname="";	// <pataccname>病人姓名</pataccname>
	private String patcertinum="";	// <patcertinum>病人证件号码</patcertinum>
	private String patdrawrelation="";	// <patdrawrelation>病人与提取人关系</patdrawrelation>
	private String payeebankacc0="";	// <payeebankacc0>收款单位银行账号</payeebankacc0>
	private String payeebankaccnm0="";	// <payeebankaccnm0>收款单位名称</payeebankaccnm0>
	private String payeebankname="";	// <payeebankname>收款开户银行</payeebankname>
	private String procertinum="";	// <procertinum>代领人证件号码</procertinum>
	private String procode="";	// <procode>提取依据编码</procode>
	private String proxcertitype="";	// <proxcertitype>代领人证件类型</proxcertitype>
	private String proxname="";	// <proxname>代领人姓名</proxname>
	private String relation="";	// <relation>关系</relation>
	private String rentamt="";	// <rentamt>月房租</rentamt>
	private String rentdate="";	// <rentdate>租房起始日期</rentdate>
	private String rentmonths="";	// <rentmonths>租房月数</rentmonths>
	private String repaymode="";	// <repaymode>还款方式</repaymode>
	private String repaymonths="";	// <repaymonths>还款月数</repaymonths>
	private String settlemode="";	// <settlemode>租房月数</settlemode>
	private String tqyy="";	// <tqyy>提取原因</tqyy>
	private String xingming="";	// <xingming>姓名</xingming>
	private String yrepayamt="";	// <yrepayamt>年还款额</yrepayamt>
	private String zjhm="";	// <zjhm>证件号码</zjhm>
	private String zjlx="";	// <zjlx>证件类型</zjlx>
	private String housecity="";	// <zjlx>证件类型</zjlx>
	private String buyhousedate1="";	// <zjlx>证件类型</zjlx>
	
	private String paramObj;
	public String getAhdrepayamt() {
		return ahdrepayamt;
	}
	public void setAhdrepayamt(String ahdrepayamt) {
		this.ahdrepayamt = ahdrepayamt;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBillamt() {
		return billamt;
	}
	public void setBillamt(String billamt) {
		this.billamt = billamt;
	}
	public String getBilldate() {
		return billdate;
	}
	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}
	public String getBuyhouseamt() {
		return buyhouseamt;
	}
	public void setBuyhouseamt(String buyhouseamt) {
		this.buyhouseamt = buyhouseamt;
	}
	public String getBuyhousedate() {
		return buyhousedate;
	}
	public void setBuyhousedate(String buyhousedate) {
		this.buyhousedate = buyhousedate;
	}
	public String getBuyhousename() {
		return buyhousename;
	}
	public void setBuyhousename(String buyhousename) {
		this.buyhousename = buyhousename;
	}
	public String getBuyhousetype() {
		return buyhousetype;
	}
	public void setBuyhousetype(String buyhousetype) {
		this.buyhousetype = buyhousetype;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getChkamt() {
		return chkamt;
	}
	public void setChkamt(String chkamt) {
		this.chkamt = chkamt;
	}
	public String getCtlflag() {
		return ctlflag;
	}
	public void setCtlflag(String ctlflag) {
		this.ctlflag = ctlflag;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getDrawcanflag() {
		return drawcanflag;
	}
	public void setDrawcanflag(String drawcanflag) {
		this.drawcanflag = drawcanflag;
	}
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public String getDrawreasoncode1() {
		return drawreasoncode1;
	}
	public void setDrawreasoncode1(String drawreasoncode1) {
		this.drawreasoncode1 = drawreasoncode1;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getFammonthinc() {
		return fammonthinc;
	}
	public void setFammonthinc(String fammonthinc) {
		this.fammonthinc = fammonthinc;
	}
	public String getFamt1() {
		return famt1;
	}
	public void setFamt1(String famt1) {
		this.famt1 = famt1;
	}
	public String getFirstflag() {
		return firstflag;
	}
	public void setFirstflag(String firstflag) {
		this.firstflag = firstflag;
	}
	public String getFirstpayamt() {
		return firstpayamt;
	}
	public void setFirstpayamt(String firstpayamt) {
		this.firstpayamt = firstpayamt;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getGrzhye() {
		return grzhye;
	}
	public void setGrzhye(String grzhye) {
		this.grzhye = grzhye;
	}
	public String getHouseaddr() {
		return houseaddr;
	}
	public void setHouseaddr(String houseaddr) {
		this.houseaddr = houseaddr;
	}
	public String getIllstatetype() {
		return illstatetype;
	}
	public void setIllstatetype(String illstatetype) {
		this.illstatetype = illstatetype;
	}
	public String getInputamt() {
		return inputamt;
	}
	public void setInputamt(String inputamt) {
		this.inputamt = inputamt;
	}
	public String getLastdrawdate() {
		return lastdrawdate;
	}
	public void setLastdrawdate(String lastdrawdate) {
		this.lastdrawdate = lastdrawdate;
	}
	public String getLasttrsdate() {
		return lasttrsdate;
	}
	public void setLasttrsdate(String lasttrsdate) {
		this.lasttrsdate = lasttrsdate;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getLoansum() {
		return loansum;
	}
	public void setLoansum(String loansum) {
		this.loansum = loansum;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatecertitype() {
		return matecertitype;
	}
	public void setMatecertitype(String matecertitype) {
		this.matecertitype = matecertitype;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getOrelation() {
		return orelation;
	}
	public void setOrelation(String orelation) {
		this.orelation = orelation;
	}
	public String getOwncertinum() {
		return owncertinum;
	}
	public void setOwncertinum(String owncertinum) {
		this.owncertinum = owncertinum;
	}
	public String getOwncertinum1() {
		return owncertinum1;
	}
	public void setOwncertinum1(String owncertinum1) {
		this.owncertinum1 = owncertinum1;
	}
	public String getOwncertinum2() {
		return owncertinum2;
	}
	public void setOwncertinum2(String owncertinum2) {
		this.owncertinum2 = owncertinum2;
	}
	public String getOwncertinum3() {
		return owncertinum3;
	}
	public void setOwncertinum3(String owncertinum3) {
		this.owncertinum3 = owncertinum3;
	}
	public String getOwncertinum4() {
		return owncertinum4;
	}
	public void setOwncertinum4(String owncertinum4) {
		this.owncertinum4 = owncertinum4;
	}
	public String getOwncertinum5() {
		return owncertinum5;
	}
	public void setOwncertinum5(String owncertinum5) {
		this.owncertinum5 = owncertinum5;
	}
	public String getOwncertitype() {
		return owncertitype;
	}
	public void setOwncertitype(String owncertitype) {
		this.owncertitype = owncertitype;
	}
	public String getOwncertitype1() {
		return owncertitype1;
	}
	public void setOwncertitype1(String owncertitype1) {
		this.owncertitype1 = owncertitype1;
	}
	public String getOwncertitype2() {
		return owncertitype2;
	}
	public void setOwncertitype2(String owncertitype2) {
		this.owncertitype2 = owncertitype2;
	}
	public String getOwncertitype3() {
		return owncertitype3;
	}
	public void setOwncertitype3(String owncertitype3) {
		this.owncertitype3 = owncertitype3;
	}
	public String getOwncertitype4() {
		return owncertitype4;
	}
	public void setOwncertitype4(String owncertitype4) {
		this.owncertitype4 = owncertitype4;
	}
	public String getOwncertitype5() {
		return owncertitype5;
	}
	public void setOwncertitype5(String owncertitype5) {
		this.owncertitype5 = owncertitype5;
	}
	public String getOwnname1() {
		return ownname1;
	}
	public void setOwnname1(String ownname1) {
		this.ownname1 = ownname1;
	}
	public String getOwnname2() {
		return ownname2;
	}
	public void setOwnname2(String ownname2) {
		this.ownname2 = ownname2;
	}
	public String getOwnname3() {
		return ownname3;
	}
	public void setOwnname3(String ownname3) {
		this.ownname3 = ownname3;
	}
	public String getOwnname4() {
		return ownname4;
	}
	public void setOwnname4(String ownname4) {
		this.ownname4 = ownname4;
	}
	public String getOwnname5() {
		return ownname5;
	}
	public void setOwnname5(String ownname5) {
		this.ownname5 = ownname5;
	}
	public String getPaperrelation() {
		return paperrelation;
	}
	public void setPaperrelation(String paperrelation) {
		this.paperrelation = paperrelation;
	}
	public String getPataccname() {
		return pataccname;
	}
	public void setPataccname(String pataccname) {
		this.pataccname = pataccname;
	}
	public String getPatcertinum() {
		return patcertinum;
	}
	public void setPatcertinum(String patcertinum) {
		this.patcertinum = patcertinum;
	}
	public String getPatdrawrelation() {
		return patdrawrelation;
	}
	public void setPatdrawrelation(String patdrawrelation) {
		this.patdrawrelation = patdrawrelation;
	}
	public String getPayeebankacc0() {
		return payeebankacc0;
	}
	public void setPayeebankacc0(String payeebankacc0) {
		this.payeebankacc0 = payeebankacc0;
	}
	public String getPayeebankaccnm0() {
		return payeebankaccnm0;
	}
	public void setPayeebankaccnm0(String payeebankaccnm0) {
		this.payeebankaccnm0 = payeebankaccnm0;
	}
	public String getPayeebankname() {
		return payeebankname;
	}
	public void setPayeebankname(String payeebankname) {
		this.payeebankname = payeebankname;
	}
	public String getProcertinum() {
		return procertinum;
	}
	public void setProcertinum(String procertinum) {
		this.procertinum = procertinum;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getProxcertitype() {
		return proxcertitype;
	}
	public void setProxcertitype(String proxcertitype) {
		this.proxcertitype = proxcertitype;
	}
	public String getProxname() {
		return proxname;
	}
	public void setProxname(String proxname) {
		this.proxname = proxname;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getRentamt() {
		return rentamt;
	}
	public void setRentamt(String rentamt) {
		this.rentamt = rentamt;
	}
	public String getRentdate() {
		return rentdate;
	}
	public void setRentdate(String rentdate) {
		this.rentdate = rentdate;
	}
	public String getRentmonths() {
		return rentmonths;
	}
	public void setRentmonths(String rentmonths) {
		this.rentmonths = rentmonths;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}
	public String getRepaymonths() {
		return repaymonths;
	}
	public void setRepaymonths(String repaymonths) {
		this.repaymonths = repaymonths;
	}
	public String getSettlemode() {
		return settlemode;
	}
	public void setSettlemode(String settlemode) {
		this.settlemode = settlemode;
	}
	public String getTqyy() {
		return tqyy;
	}
	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getYrepayamt() {
		return yrepayamt;
	}
	public void setYrepayamt(String yrepayamt) {
		this.yrepayamt = yrepayamt;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getParamObj() {
		return paramObj;
	}
	public void setParamObj(String paramObj) {
		this.paramObj = paramObj;
	}
	public String getDrawreasoncode2() {
		return drawreasoncode2;
	}
	public void setDrawreasoncode2(String drawreasoncode2) {
		this.drawreasoncode2 = drawreasoncode2;
	}
	public String getHousecity() {
		return housecity;
	}
	public void setHousecity(String housecity) {
		this.housecity = housecity;
	}
	public String getBuyhousedate1() {
		return buyhousedate1;
	}
	public void setBuyhousedate1(String buyhousedate1) {
		this.buyhousedate1 = buyhousedate1;
	}
	
}
