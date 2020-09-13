package com.yondervision.yfmap.form;

public class AppApi01003Form extends AppApiCommonForm {
	
	/**
	 * 提取原因
	 */
	private String drawreason="";
	
	/**
	 * 提取签约类型
	 */
	private String drawSignType="";
	
	/**
	 * 合同号
	 */
	private String loancontrnum="";
	
	/**
	 * 还款方式
	 */
	private String repaymentStatus="";
	
	/**
	 * 还款金额
	 */
	private String repayment="";
	
	/**
	 * 新手机号
	 */
	private String newphone="";
	

	/**
	 * 验证码序号
	 */
	private String transcode="";
	

	/**
	 * 验证码
	 */
	private String checkid="";

	/**
	 * 新银行卡号
	 */
	private String bankaccnum="";
	
	/**
	 * 扣款人类型
	 */
	private String dedoptype="";
	
	/**
	 * 原还款账号
	 */
	private String oldrepayaccnum="";
	
	/**
	 * 扣款人姓名
	 */
	private String dedaccname="";
	
	/**
	 * 银行扣款账号
	 */
	private String dedbankaccnum="";
	
	/**
	 * 代扣银行
	 */
	private String dedbankcode="";
	
	/**
	 * 合同号
	 */
	private String loancontrno="";
	
	/**
	 * 协议类型
	 */
	private String accdedtype="";
	
	/**
	 * 协议签订状态
	 */
	private String dedflag="";
	
	/**
	 * 手机
	 */
	private String handset="";
	
	/**
	 * 电话
	 */
	private String phone="";
	
	/**
	 * 婚姻状况
	 */
	private String marstatus="";
	
	/**
	 * 职业
	 */
	private String occupation="";
	
	/**
	 * 职位
	 */
	private String headship="";
	
	/**
	 * 学历
	 */
	private String eduexp="";
	
	/**
	 * 家庭地址
	 */
	private String famaddr="";
	
	/**
	 * 家庭月收入
	 */
	private String fammonthinc="";
	
	/**
	 * 付款银行
	 */
	private String payerbankcode="";
	/**
	 * 收款银行
	 */
	private String payeebankname="";
	/**
	 * 收款账号
	 */
	private String payeebankaccnum="";
	/**
	 * 收款人名称
	 */
	private String payeename="";
	/**
	 * 余额
	 */
	private String bal="";
	/**
	 * 单位帐号
	 */
	private String unitaccnum="";
	/**
	 * 封存类型
	 */
	private String sealtype="";
	
	
	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	private String accnum="";
	private String certinum="";	
	private String freeuse1="";
	private String freeuse2="";
	private String freeuse3="";
	private String transCode="";
	/** 经办银行 */						
	private String apprnum = "";						
	/** 产权证号 */						
	private String firstflag = "";
	/** 证件号码 */						
	private String monpaysum = "";
	/** 证件类型 */						
	private String drawamt = "";						
	/** 结清日期 */						
	private String procode2 = "";						
	/** 合同签订日期 */						
	private String oprole = "";						
	/** 抵押物他项权证号 */						
	private String calintamt = "";						
	/** 贷款金额 */						
	private String increbal = "";	
	/** 借款人姓名 */						
	private String keepintaccu = "";	
	/** 贷款期限 */						
	private String increintaccu = "";		
	/** 配偶证件号码 */						
	private String inputamt = "";						
	/** 配偶证件类型 */						
	private String prominfo = "";						
	/** 配偶姓名 */						
	private String lasttransdate = "";						
	/** 抵押物地址 */						
	private String indipaymap = "";						
	/** 抵押人姓名 */						
	private String keepbal = "";						
	/** 抵押物类型 */						
	private String procode = "";	
	/** 个人账号 */						
	private String prodcode = "";
	/** 金额 */						
	private String intamt = "";
	/** 银行账号 */						
	private String prin = "";
	/** 银行代码 */						
	private String amt1 = "";
	/** 控制标志 */						
	private String amt2 = "";
	/** 委托代扣协议标志 */						
	private String basenum = "";
	/** 联系电话 */						
	private String unitprop = "";
	/** 贷款账号 */						
	private String indiprop = "";
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
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getBuzhType() {
		return buzhType;
	}
	public void setBuzhType(String buzhType) {
		this.buzhType = buzhType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getFreeuse1() {
		return freeuse1;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	public String getFreeuse2() {
		return freeuse2;
	}
	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}
	public String getFreeuse3() {
		return freeuse3;
	}
	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public String getDrawSignType() {
		return drawSignType;
	}
	public void setDrawSignType(String drawSignType) {
		this.drawSignType = drawSignType;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getRepaymentStatus() {
		return repaymentStatus;
	}
	public void setRepaymentStatus(String repaymentStatus) {
		this.repaymentStatus = repaymentStatus;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getNewphone() {
		return newphone;
	}
	public void setNewphone(String newphone) {
		this.newphone = newphone;
	}
	public String getTranscode() {
		return transcode;
	}
	public void setTranscode(String transcode) {
		this.transcode = transcode;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getDedoptype() {
		return dedoptype;
	}
	public void setDedoptype(String dedoptype) {
		this.dedoptype = dedoptype;
	}
	public String getOldrepayaccnum() {
		return oldrepayaccnum;
	}
	public void setOldrepayaccnum(String oldrepayaccnum) {
		this.oldrepayaccnum = oldrepayaccnum;
	}
	public String getDedaccname() {
		return dedaccname;
	}
	public void setDedaccname(String dedaccname) {
		this.dedaccname = dedaccname;
	}
	public String getDedbankaccnum() {
		return dedbankaccnum;
	}
	public void setDedbankaccnum(String dedbankaccnum) {
		this.dedbankaccnum = dedbankaccnum;
	}
	public String getDedbankcode() {
		return dedbankcode;
	}
	public void setDedbankcode(String dedbankcode) {
		this.dedbankcode = dedbankcode;
	}
	public String getLoancontrno() {
		return loancontrno;
	}
	public void setLoancontrno(String loancontrno) {
		this.loancontrno = loancontrno;
	}
	public String getAccdedtype() {
		return accdedtype;
	}
	public void setAccdedtype(String accdedtype) {
		this.accdedtype = accdedtype;
	}
	public String getDedflag() {
		return dedflag;
	}
	public void setDedflag(String dedflag) {
		this.dedflag = dedflag;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMarstatus() {
		return marstatus;
	}
	public void setMarstatus(String marstatus) {
		this.marstatus = marstatus;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getHeadship() {
		return headship;
	}
	public void setHeadship(String headship) {
		this.headship = headship;
	}
	public String getEduexp() {
		return eduexp;
	}
	public void setEduexp(String eduexp) {
		this.eduexp = eduexp;
	}
	public String getFamaddr() {
		return famaddr;
	}
	public void setFamaddr(String famaddr) {
		this.famaddr = famaddr;
	}
	public String getFammonthinc() {
		return fammonthinc;
	}
	public void setFammonthinc(String fammonthinc) {
		this.fammonthinc = fammonthinc;
	}
	public String getPayerbankcode() {
		return payerbankcode;
	}
	public void setPayerbankcode(String payerbankcode) {
		this.payerbankcode = payerbankcode;
	}
	public String getPayeebankaccnum() {
		return payeebankaccnum;
	}
	public void setPayeebankaccnum(String payeebankaccnum) {
		this.payeebankaccnum = payeebankaccnum;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	public String getBal() {
		return bal;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getSealtype() {
		return sealtype;
	}
	public void setSealtype(String sealtype) {
		this.sealtype = sealtype;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getFirstflag() {
		return firstflag;
	}
	public void setFirstflag(String firstflag) {
		this.firstflag = firstflag;
	}
	public String getMonpaysum() {
		return monpaysum;
	}
	public void setMonpaysum(String monpaysum) {
		this.monpaysum = monpaysum;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
	}
	public String getProcode2() {
		return procode2;
	}
	public void setProcode2(String procode2) {
		this.procode2 = procode2;
	}
	public String getOprole() {
		return oprole;
	}
	public void setOprole(String oprole) {
		this.oprole = oprole;
	}
	public String getCalintamt() {
		return calintamt;
	}
	public void setCalintamt(String calintamt) {
		this.calintamt = calintamt;
	}
	public String getIncrebal() {
		return increbal;
	}
	public void setIncrebal(String increbal) {
		this.increbal = increbal;
	}
	public String getKeepintaccu() {
		return keepintaccu;
	}
	public void setKeepintaccu(String keepintaccu) {
		this.keepintaccu = keepintaccu;
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
	public String getProminfo() {
		return prominfo;
	}
	public void setProminfo(String prominfo) {
		this.prominfo = prominfo;
	}
	public String getLasttransdate() {
		return lasttransdate;
	}
	public void setLasttransdate(String lasttransdate) {
		this.lasttransdate = lasttransdate;
	}
	public String getIndipaymap() {
		return indipaymap;
	}
	public void setIndipaymap(String indipaymap) {
		this.indipaymap = indipaymap;
	}
	public String getKeepbal() {
		return keepbal;
	}
	public void setKeepbal(String keepbal) {
		this.keepbal = keepbal;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getIntamt() {
		return intamt;
	}
	public void setIntamt(String intamt) {
		this.intamt = intamt;
	}
	public String getPrin() {
		return prin;
	}
	public void setPrin(String prin) {
		this.prin = prin;
	}
	public String getAmt1() {
		return amt1;
	}
	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}
	public String getAmt2() {
		return amt2;
	}
	public void setAmt2(String amt2) {
		this.amt2 = amt2;
	}
	public String getBasenum() {
		return basenum;
	}
	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}
	public String getUnitprop() {
		return unitprop;
	}
	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}
	public String getIndiprop() {
		return indiprop;
	}
	public void setIndiprop(String indiprop) {
		this.indiprop = indiprop;
	}
	public String getPayeebankname() {
		return payeebankname;
	}
	public void setPayeebankname(String payeebankname) {
		this.payeebankname = payeebankname;
	}
}
