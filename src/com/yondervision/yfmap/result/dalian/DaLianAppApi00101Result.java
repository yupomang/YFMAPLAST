package com.yondervision.yfmap.result.dalian;

/**
 * @author Caozhongyan
 *
 */
public class DaLianAppApi00101Result extends DalianLogResult{
	/** 公积金账户 */						
	private String surplusAccount = "";						
	/** 账户状态 */						
	private String accountStatus = "";						
	/** 账户余额 */						
	private String accountBalance = "";						
	/** 冻结金额 */						
	private String frozenAmount = "";						
	/** 冻结日期 */						
	private String frozenDate = "";						
	/** 最新业务日期 */						
	private String newOperationDate = "";						
	/** 缴至年月 */						
	private String lastestPaymentDate = "";						
	/** 当年缴存金额 */						
	private String annualPaymentSum = "";						
	/** 上年结转金额 */						
	private String carryoverAmount = "";						
	/** 缴存基数 */						
	private String paymentBase = "";						
	/** 单位缴存比例 */						
	private String companyPaymentRate = "";						
	/** 个人缴存比例 */						
	private String personalPaymentRate = "";						
	/** 单位账号 */						
	private String companyAccount = "";						
	/** 单位名称 */						
	private String companyName = "";						
	/** 账户机构 */						
	private String accountOrganization = "";						
	/** 开户日期 */						
	private String accountOpenDate = "";
	/** 姓名 */
	private String accountName = "";
	/** 银行名称 */
	private String bankName = "";
	/** 个人账号 */
	private String grzh = "";
	/**
	 * 交易代码
	 */
	private String transCode="";
	/**
	 * 返回日期
	 */
	private String sendDate="";
	/**
	 * 返回时间
	 */
	private String sendTime="";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 安全标识
	 */
	private String key="";
	/**
	 * 中心流水号
	 */
	private String centerSeqno="";
	/**
	 * 响应码
	 */
	private String recode="";
	/**
	 * 响应信息
	 */
	private String msg="";
	
	/** 公积金可用余额 */						
	private String gjjkyye = "";						
	/** 住房补贴可用余额 */						
	private String zfbtkyye = "";						
	/** 可用余额合计 */						
	private String kyyehj = "";						
	/** 公积金缴存基数 */						
	private String gjjjcjs = "";						
	/** 住房补贴 缴存积数 */						
	private String zfbtjcjs = "";						
	/** 公积金缴存比例 */						
	private String gjjjcbl = "";						
	/** 住房补贴缴存比例 */						
	private String zfbtjcbl = "";						
	/** 公积金月汇缴额 */						
	private String gjjyhje = "";						
	/** 住房补贴月汇缴额 */						
	private String zfbtyhje = "";						
	/** 月汇缴额合计 */						
	private String yhjehj = "";						
	/** 公积金最后汇缴月 */						
	private String gjjzhhjy = "";						
	/** 住房补贴最后汇缴月 */						
	private String zfbtzhhjy = "";						
							
	/**						
	 *<pre> 执行获取公积金可用余额处理 </pre>						
	 * @return gjjkyye 公积金可用余额						
	 */						
	public String getGjjkyye() {						
	    return gjjkyye;						
	}						
							
	/**						
	 *<pre> 执行设定公积金可用余额处理 </pre>						
	 * @param gjjkyye 公积金可用余额						
	 */						
	public void setGjjkyye(String gjjkyye) {						
	    this.gjjkyye = gjjkyye;						
	}						
							
	/**						
	 *<pre> 执行获取住房补贴可用余额处理 </pre>						
	 * @return zfbtkyye 住房补贴可用余额						
	 */						
	public String getZfbtkyye() {						
	    return zfbtkyye;						
	}						
							
	/**						
	 *<pre> 执行设定住房补贴可用余额处理 </pre>						
	 * @param zfbtkyye 住房补贴可用余额						
	 */						
	public void setZfbtkyye(String zfbtkyye) {						
	    this.zfbtkyye = zfbtkyye;						
	}						
							
	/**						
	 *<pre> 执行获取可用余额合计处理 </pre>						
	 * @return kyyehj 可用余额合计						
	 */						
	public String getKyyehj() {						
	    return kyyehj;						
	}						
							
	/**						
	 *<pre> 执行设定可用余额合计处理 </pre>						
	 * @param kyyehj 可用余额合计						
	 */						
	public void setKyyehj(String kyyehj) {						
	    this.kyyehj = kyyehj;						
	}						
							
	/**						
	 *<pre> 执行获取公积金缴存基数处理 </pre>						
	 * @return gjjjcjs 公积金缴存基数						
	 */						
	public String getGjjjcjs() {						
	    return gjjjcjs;						
	}						
							
	/**						
	 *<pre> 执行设定公积金缴存基数处理 </pre>						
	 * @param gjjjcjs 公积金缴存基数						
	 */						
	public void setGjjjcjs(String gjjjcjs) {						
	    this.gjjjcjs = gjjjcjs;						
	}						
							
	/**						
	 *<pre> 执行获取住房补贴 缴存积数处理 </pre>						
	 * @return zfbtjcjs 住房补贴 缴存积数						
	 */						
	public String getZfbtjcjs() {						
	    return zfbtjcjs;						
	}						
							
	/**						
	 *<pre> 执行设定住房补贴 缴存积数处理 </pre>						
	 * @param zfbtjcjs 住房补贴 缴存积数						
	 */						
	public void setZfbtjcjs(String zfbtjcjs) {						
	    this.zfbtjcjs = zfbtjcjs;						
	}						
							
	/**						
	 *<pre> 执行获取公积金缴存比例处理 </pre>						
	 * @return gjjjcbl 公积金缴存比例						
	 */						
	public String getGjjjcbl() {						
	    return gjjjcbl;						
	}						
							
	/**						
	 *<pre> 执行设定公积金缴存比例处理 </pre>						
	 * @param gjjjcbl 公积金缴存比例						
	 */						
	public void setGjjjcbl(String gjjjcbl) {						
	    this.gjjjcbl = gjjjcbl;						
	}						
							
	/**						
	 *<pre> 执行获取住房补贴缴存比例处理 </pre>						
	 * @return zfbtjcbl 住房补贴缴存比例						
	 */						
	public String getZfbtjcbl() {						
	    return zfbtjcbl;						
	}						
							
	/**						
	 *<pre> 执行设定住房补贴缴存比例处理 </pre>						
	 * @param zfbtjcbl 住房补贴缴存比例						
	 */						
	public void setZfbtjcbl(String zfbtjcbl) {						
	    this.zfbtjcbl = zfbtjcbl;						
	}						
							
	/**						
	 *<pre> 执行获取公积金月汇缴额处理 </pre>						
	 * @return gjjyhje 公积金月汇缴额						
	 */						
	public String getGjjyhje() {						
	    return gjjyhje;						
	}						
							
	/**						
	 *<pre> 执行设定公积金月汇缴额处理 </pre>						
	 * @param gjjyhje 公积金月汇缴额						
	 */						
	public void setGjjyhje(String gjjyhje) {						
	    this.gjjyhje = gjjyhje;						
	}						
							
	/**						
	 *<pre> 执行获取住房补贴月汇缴额处理 </pre>						
	 * @return zfbtyhje 住房补贴月汇缴额						
	 */						
	public String getZfbtyhje() {						
	    return zfbtyhje;						
	}						
							
	/**						
	 *<pre> 执行设定住房补贴月汇缴额处理 </pre>						
	 * @param zfbtyhje 住房补贴月汇缴额						
	 */						
	public void setZfbtyhje(String zfbtyhje) {						
	    this.zfbtyhje = zfbtyhje;						
	}						
							
	/**						
	 *<pre> 执行获取月汇缴额合计处理 </pre>						
	 * @return yhjehj 月汇缴额合计						
	 */						
	public String getYhjehj() {						
	    return yhjehj;						
	}						
							
	/**						
	 *<pre> 执行设定月汇缴额合计处理 </pre>						
	 * @param yhjehj 月汇缴额合计						
	 */						
	public void setYhjehj(String yhjehj) {						
	    this.yhjehj = yhjehj;						
	}						
							
	/**						
	 *<pre> 执行获取公积金最后汇缴月处理 </pre>						
	 * @return gjjzhhjy 公积金最后汇缴月						
	 */						
	public String getGjjzhhjy() {						
	    return gjjzhhjy;						
	}						
							
	/**						
	 *<pre> 执行设定公积金最后汇缴月处理 </pre>						
	 * @param gjjzhhjy 公积金最后汇缴月						
	 */						
	public void setGjjzhhjy(String gjjzhhjy) {						
	    this.gjjzhhjy = gjjzhhjy;						
	}						
							
	/**						
	 *<pre> 执行获取住房补贴最后汇缴月处理 </pre>						
	 * @return zfbtzhhjy 住房补贴最后汇缴月						
	 */						
	public String getZfbtzhhjy() {						
	    return zfbtzhhjy;						
	}						
							
	/**						
	 *<pre> 执行设定住房补贴最后汇缴月处理 </pre>						
	 * @param zfbtzhhjy 住房补贴最后汇缴月						
	 */						
	public void setZfbtzhhjy(String zfbtzhhjy) {						
	    this.zfbtzhhjy = zfbtzhhjy;						
	}						
							

	
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
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
	public String getCenterSeqno() {
		return centerSeqno;
	}
	public void setCenterSeqno(String centerSeqno) {
		this.centerSeqno = centerSeqno;
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
	public String getSurplusAccount() {
		return surplusAccount;
	}
	public void setSurplusAccount(String surplusAccount) {
		this.surplusAccount = surplusAccount;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getFrozenDate() {
		return frozenDate;
	}
	public void setFrozenDate(String frozenDate) {
		this.frozenDate = frozenDate;
	}
	public String getNewOperationDate() {
		return newOperationDate;
	}
	public void setNewOperationDate(String newOperationDate) {
		this.newOperationDate = newOperationDate;
	}
	public String getLastestPaymentDate() {
		return lastestPaymentDate;
	}
	public void setLastestPaymentDate(String lastestPaymentDate) {
		this.lastestPaymentDate = lastestPaymentDate;
	}
	public String getAnnualPaymentSum() {
		return annualPaymentSum;
	}
	public void setAnnualPaymentSum(String annualPaymentSum) {
		this.annualPaymentSum = annualPaymentSum;
	}
	public String getCarryoverAmount() {
		return carryoverAmount;
	}
	public void setCarryoverAmount(String carryoverAmount) {
		this.carryoverAmount = carryoverAmount;
	}
	public String getPaymentBase() {
		return paymentBase;
	}
	public void setPaymentBase(String paymentBase) {
		this.paymentBase = paymentBase;
	}
	public String getCompanyPaymentRate() {
		return companyPaymentRate;
	}
	public void setCompanyPaymentRate(String companyPaymentRate) {
		this.companyPaymentRate = companyPaymentRate;
	}
	public String getPersonalPaymentRate() {
		return personalPaymentRate;
	}
	public void setPersonalPaymentRate(String personalPaymentRate) {
		this.personalPaymentRate = personalPaymentRate;
	}
	public String getCompanyAccount() {
		return companyAccount;
	}
	public void setCompanyAccount(String companyAccount) {
		this.companyAccount = companyAccount;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAccountOrganization() {
		return accountOrganization;
	}
	public void setAccountOrganization(String accountOrganization) {
		this.accountOrganization = accountOrganization;
	}
	public String getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(String accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}						
							
	
}
