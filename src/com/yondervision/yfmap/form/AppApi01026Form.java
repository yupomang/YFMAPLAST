package com.yondervision.yfmap.form;

public class AppApi01026Form extends AppApiCommonForm {
	
	private String filenames = "";
	private String filess = "";
	
	private String relation=""; // 关系                            
	private String rentamt=""; // 月房租                           
	private String rentcertinum=""; // 租房人身份证号码            
	private String rentdate=""; // 租房起始日期                    
	private String rentmonths=""; // 租房月数                      
	private String rentname=""; // 租房人姓名                      
	private String repaymode=""; // 还款方式                       
	private String repaymonths=""; // 还款月数                     
	private String settlemode=""; // 结算方式                      
	private String stepseqno=""; // 步骤号                         
	private String tqyy=""; // 提取原因                            
	private String transchannel=""; // 交易渠道                    
	private String marrydate=""; // 结婚日期                       
	private String matecertinum=""; // 配偶证件号码                
	private String matename=""; // 配偶姓名                        
	private String materate=""; // 配偶比例                        
	private String trscommlnflag=""; // 是否做过转账还商贷提取     
	private String xingming=""; // 姓名                            
	private String zjhm=""; // 证件号码                            
	private String zjlx=""; // 证件类型                            
	private String conamt=""; // 政府补贴金额                      
	private String custid=""; // 个人客户号                        
	private String drawamt=""; // 提取金额                         
	private String drawcanflag=""; // 提取/销户标志                
	private String drawreason=""; // 提取原因                      
	private String drawreasoncode1=""; // 提取依据号码1            
	private String drawreasoncode2=""; // 提取依据号码2            
	private String dwzh=""; // 单位账号                            
	private String fammonthinc=""; // 家庭月收入                   
	private String firstflag=""; // 提取依据是否首次办理           
	private String firstpayamt=""; // 首付款                       
	private String grzh=""; // 个人账号                            
	private String houseaddr=""; // 房屋坐落                       
	private String houseusage=""; // 房屋用途                      
	private String increbal=""; // 增量余额                        
	private String increintaccu=""; // 增量利息积数                
	private String inputamt=""; // 初审金额                        
	private String keepbal=""; // 存量余额                         
	private String keepintaccu=""; // 存量利息积数                 
	private String lastdrawdate=""; // 上笔提取日期                
	private String lasttransdate=""; // 上笔交易日期               
	private String ahdrepayamt=""; // 提前还本金                   
	private String ahdrepaydate=""; // 商贷提前还款日期            
	private String agentop=""; // 经办柜员                         
	private String apprnum=""; // 审批号                           
	private String approveop=""; // 审批柜员                       
	private String authop=""; // 授权柜员                          
	private String grzhye=""; // 个人账户余额                      
	private String buyhouseamt=""; // 购房房款总额                 
	private String monpaysum=""; // 月应汇缴额                     
	private String monthrepayamt=""; // 贷款月还款额               
	private String oprole=""; // 柜员角色                          
	private String buyhousedate=""; // 购房日期                    
	private String buyhousename=""; // 购房人姓名                  
	private String buyhousetype=""; // 购房类型                    
	private String calintamt=""; // 结息金额                       
	private String cardno=""; // 卡号                              
	private String cashinvoice=""; // 现金购房发票号               
	private String chkamt=""; // 复审金额                          
	private String chkchgreason=""; // 复核额度变更原因            
	private String chkop=""; // 复核柜员                           
	private String commlncontrnum=""; // 商贷合同号                
	private String commlndate=""; // 借款日期                      
	private String commlnrate=""; // 商贷贷款利率                  
	private String commlnterm=""; // 商贷借款期限                  
	private String commlnyear=""; // 商贷年度                      
	private String lasttrsdate=""; // 上笔转账提取日期             
	private String linkrow=""; // 联行号                           
	private String lnrepaymonthamt=""; // 商贷贷款月还款额         
	private String loancity=""; // 公贷所属城市代码                
	private String loancontrnum=""; // 借款合同号                  
	private String loandate=""; // 放款日期                        
	private String loanrate=""; // 贷款利率                        
	private String loansum=""; // 贷款额度                         
	private String loanterm=""; // 贷款期限                        
	private String orelation=""; // 提取人与提取依据2所有人关系    
	private String othcenaccnum=""; // 转入异地职工账号            
	private String othcenunitacc=""; // 转入异地单位账号           
	private String othcenunitname=""; // 转入异地单位名称          
	private String owncertinum=""; // 所有权人证件号码             
	private String owncertinum1=""; // 共有权人一证件号码          
	private String owncertinum2=""; // 共有权人二证件号码          
	private String owncertinum3=""; // 共有权人三证件号码          
	private String owncertinum4=""; // 共有权人四证件号码          
	private String owncertinum5=""; // 共有权人五证件号码          
	private String ownname1=""; // 共有权人一姓名                  
	private String ownname2=""; // xingming                        
	private String ownname3=""; // 共有权人三姓名                  
	private String ownname4=""; // 共有权人四姓名                  
	private String ownname5=""; // 共有权人五姓名                  
	private String ownprop=""; // 所有权人占比                     
	private String ownprop1=""; // 所有权人一比例                  
	private String ownprop2=""; // 所有权人二比例                  
	private String ownprop3=""; // 所有权人三比例                  
	private String ownprop4=""; // 所有权人四比例                  
	private String ownprop5=""; // 所有权人五比例                  
	private String paperrelation=""; // 收款账号与提取人关系       
	private String payeebankacc0=""; // 收款单位银行账号           
	private String payeebankaccnm0=""; // 收款单位名称             
	private String payeebankcode=""; // 收款账号开户银行代码       
	private String payeebankname=""; // 收款开户银行               
	private String payeeflag=""; // 收款方                         
	private String payerbankcode=""; // 付款银行代码               
	private String porttype=""; // 接口类型                        
	private String procertinum=""; // 代领人证件号码               
	private String procode=""; // 提取依据编码                     
	private String procode2=""; // 提取原因的编号2                 
	private String prodcode=""; // 产品代码                        
	private String proxcertitype=""; // 代领人证件类型             
	private String proxname=""; // 代领人姓名                      
	private String pubhourentnum=""; // 公有住房租赁证号       

	
	public String getFilenames() {
		return filenames;
	}
	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}
	public String getFiless() {
		return filess;
	}
	public void setFiless(String filess) {
		this.filess = filess;
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
	public String getRentcertinum() {
		return rentcertinum;
	}
	public void setRentcertinum(String rentcertinum) {
		this.rentcertinum = rentcertinum;
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
	public String getRentname() {
		return rentname;
	}
	public void setRentname(String rentname) {
		this.rentname = rentname;
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
	public String getStepseqno() {
		return stepseqno;
	}
	public void setStepseqno(String stepseqno) {
		this.stepseqno = stepseqno;
	}
	public String getTqyy() {
		return tqyy;
	}
	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}
	public String getTranschannel() {
		return transchannel;
	}
	public void setTranschannel(String transchannel) {
		this.transchannel = transchannel;
	}
	public String getMarrydate() {
		return marrydate;
	}
	public void setMarrydate(String marrydate) {
		this.marrydate = marrydate;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMaterate() {
		return materate;
	}
	public void setMaterate(String materate) {
		this.materate = materate;
	}
	public String getTrscommlnflag() {
		return trscommlnflag;
	}
	public void setTrscommlnflag(String trscommlnflag) {
		this.trscommlnflag = trscommlnflag;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
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
	public String getConamt() {
		return conamt;
	}
	public void setConamt(String conamt) {
		this.conamt = conamt;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
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
	public String getDrawreasoncode2() {
		return drawreasoncode2;
	}
	public void setDrawreasoncode2(String drawreasoncode2) {
		this.drawreasoncode2 = drawreasoncode2;
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
	public String getHouseaddr() {
		return houseaddr;
	}
	public void setHouseaddr(String houseaddr) {
		this.houseaddr = houseaddr;
	}
	public String getHouseusage() {
		return houseusage;
	}
	public void setHouseusage(String houseusage) {
		this.houseusage = houseusage;
	}
	public String getIncrebal() {
		return increbal;
	}
	public void setIncrebal(String increbal) {
		this.increbal = increbal;
	}
	public String getIncreintaccu() {
		return increintaccu;
	}
	public void setIncreintaccu(String increintaccu) {
		this.increintaccu = increintaccu;
	}
	public String getInputamt() {
		return inputamt;
	}
	public void setInputamt(String inputamt) {
		this.inputamt = inputamt;
	}
	public String getKeepbal() {
		return keepbal;
	}
	public void setKeepbal(String keepbal) {
		this.keepbal = keepbal;
	}
	public String getKeepintaccu() {
		return keepintaccu;
	}
	public void setKeepintaccu(String keepintaccu) {
		this.keepintaccu = keepintaccu;
	}
	public String getLastdrawdate() {
		return lastdrawdate;
	}
	public void setLastdrawdate(String lastdrawdate) {
		this.lastdrawdate = lastdrawdate;
	}
	public String getLasttransdate() {
		return lasttransdate;
	}
	public void setLasttransdate(String lasttransdate) {
		this.lasttransdate = lasttransdate;
	}
	public String getAhdrepayamt() {
		return ahdrepayamt;
	}
	public void setAhdrepayamt(String ahdrepayamt) {
		this.ahdrepayamt = ahdrepayamt;
	}
	public String getAhdrepaydate() {
		return ahdrepaydate;
	}
	public void setAhdrepaydate(String ahdrepaydate) {
		this.ahdrepaydate = ahdrepaydate;
	}
	public String getAgentop() {
		return agentop;
	}
	public void setAgentop(String agentop) {
		this.agentop = agentop;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getApproveop() {
		return approveop;
	}
	public void setApproveop(String approveop) {
		this.approveop = approveop;
	}
	public String getAuthop() {
		return authop;
	}
	public void setAuthop(String authop) {
		this.authop = authop;
	}
	public String getGrzhye() {
		return grzhye;
	}
	public void setGrzhye(String grzhye) {
		this.grzhye = grzhye;
	}
	public String getBuyhouseamt() {
		return buyhouseamt;
	}
	public void setBuyhouseamt(String buyhouseamt) {
		this.buyhouseamt = buyhouseamt;
	}
	public String getMonpaysum() {
		return monpaysum;
	}
	public void setMonpaysum(String monpaysum) {
		this.monpaysum = monpaysum;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getOprole() {
		return oprole;
	}
	public void setOprole(String oprole) {
		this.oprole = oprole;
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
	public String getCalintamt() {
		return calintamt;
	}
	public void setCalintamt(String calintamt) {
		this.calintamt = calintamt;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCashinvoice() {
		return cashinvoice;
	}
	public void setCashinvoice(String cashinvoice) {
		this.cashinvoice = cashinvoice;
	}
	public String getChkamt() {
		return chkamt;
	}
	public void setChkamt(String chkamt) {
		this.chkamt = chkamt;
	}
	public String getChkchgreason() {
		return chkchgreason;
	}
	public void setChkchgreason(String chkchgreason) {
		this.chkchgreason = chkchgreason;
	}
	public String getChkop() {
		return chkop;
	}
	public void setChkop(String chkop) {
		this.chkop = chkop;
	}
	public String getCommlncontrnum() {
		return commlncontrnum;
	}
	public void setCommlncontrnum(String commlncontrnum) {
		this.commlncontrnum = commlncontrnum;
	}
	public String getCommlndate() {
		return commlndate;
	}
	public void setCommlndate(String commlndate) {
		this.commlndate = commlndate;
	}
	public String getCommlnrate() {
		return commlnrate;
	}
	public void setCommlnrate(String commlnrate) {
		this.commlnrate = commlnrate;
	}
	public String getCommlnterm() {
		return commlnterm;
	}
	public void setCommlnterm(String commlnterm) {
		this.commlnterm = commlnterm;
	}
	public String getCommlnyear() {
		return commlnyear;
	}
	public void setCommlnyear(String commlnyear) {
		this.commlnyear = commlnyear;
	}
	public String getLasttrsdate() {
		return lasttrsdate;
	}
	public void setLasttrsdate(String lasttrsdate) {
		this.lasttrsdate = lasttrsdate;
	}
	public String getLinkrow() {
		return linkrow;
	}
	public void setLinkrow(String linkrow) {
		this.linkrow = linkrow;
	}
	public String getLnrepaymonthamt() {
		return lnrepaymonthamt;
	}
	public void setLnrepaymonthamt(String lnrepaymonthamt) {
		this.lnrepaymonthamt = lnrepaymonthamt;
	}
	public String getLoancity() {
		return loancity;
	}
	public void setLoancity(String loancity) {
		this.loancity = loancity;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getLoanrate() {
		return loanrate;
	}
	public void setLoanrate(String loanrate) {
		this.loanrate = loanrate;
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
	public String getOrelation() {
		return orelation;
	}
	public void setOrelation(String orelation) {
		this.orelation = orelation;
	}
	public String getOthcenaccnum() {
		return othcenaccnum;
	}
	public void setOthcenaccnum(String othcenaccnum) {
		this.othcenaccnum = othcenaccnum;
	}
	public String getOthcenunitacc() {
		return othcenunitacc;
	}
	public void setOthcenunitacc(String othcenunitacc) {
		this.othcenunitacc = othcenunitacc;
	}
	public String getOthcenunitname() {
		return othcenunitname;
	}
	public void setOthcenunitname(String othcenunitname) {
		this.othcenunitname = othcenunitname;
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
	public String getOwnprop() {
		return ownprop;
	}
	public void setOwnprop(String ownprop) {
		this.ownprop = ownprop;
	}
	public String getOwnprop1() {
		return ownprop1;
	}
	public void setOwnprop1(String ownprop1) {
		this.ownprop1 = ownprop1;
	}
	public String getOwnprop2() {
		return ownprop2;
	}
	public void setOwnprop2(String ownprop2) {
		this.ownprop2 = ownprop2;
	}
	public String getOwnprop3() {
		return ownprop3;
	}
	public void setOwnprop3(String ownprop3) {
		this.ownprop3 = ownprop3;
	}
	public String getOwnprop4() {
		return ownprop4;
	}
	public void setOwnprop4(String ownprop4) {
		this.ownprop4 = ownprop4;
	}
	public String getOwnprop5() {
		return ownprop5;
	}
	public void setOwnprop5(String ownprop5) {
		this.ownprop5 = ownprop5;
	}
	public String getPaperrelation() {
		return paperrelation;
	}
	public void setPaperrelation(String paperrelation) {
		this.paperrelation = paperrelation;
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
	public String getPayeebankcode() {
		return payeebankcode;
	}
	public void setPayeebankcode(String payeebankcode) {
		this.payeebankcode = payeebankcode;
	}
	public String getPayeebankname() {
		return payeebankname;
	}
	public void setPayeebankname(String payeebankname) {
		this.payeebankname = payeebankname;
	}
	public String getPayeeflag() {
		return payeeflag;
	}
	public void setPayeeflag(String payeeflag) {
		this.payeeflag = payeeflag;
	}
	public String getPayerbankcode() {
		return payerbankcode;
	}
	public void setPayerbankcode(String payerbankcode) {
		this.payerbankcode = payerbankcode;
	}
	public String getPorttype() {
		return porttype;
	}
	public void setPorttype(String porttype) {
		this.porttype = porttype;
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
	public String getProcode2() {
		return procode2;
	}
	public void setProcode2(String procode2) {
		this.procode2 = procode2;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
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
	public String getPubhourentnum() {
		return pubhourentnum;
	}
	public void setPubhourentnum(String pubhourentnum) {
		this.pubhourentnum = pubhourentnum;
	}
	
}
