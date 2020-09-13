package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 * 批量下载 ---  单位缴存登记信息查询
 */
public class AppApi024_01ZHResult {
//株洲start
	/** 登记日期*/
	private String regdate = "";
	/** 单位账号*/
	private String dwzh = "";
	/** 单位名称*/
	private String dwmc = "";
//株洲end
	/** 登记号*/
	private String regnum = "";
	/** 缴款类型*/
	private String paymenttype = "";
	/** 汇缴起始日期*/
	private String busibegindate = "";
	/** 汇缴终止日期*/
	private String busienddate = "";
	/** 汇缴金额*/
	private String busipayamt = "";
	/** 缴款方式*/
	private String paytype = "";
	/** 入账标志*/
	private String chrflag = "";
	/** 交易类型*/
	private String tradetype = "";
	/** 失败原因*/
	private String failreason = "";
	/** 结算柜员*/
	private String oper = "";
	
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getRegnum() {
		return regnum;
	}
	public void setRegnum(String regnum) {
		this.regnum = regnum;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getBusibegindate() {
		return busibegindate;
	}
	public void setBusibegindate(String busibegindate) {
		this.busibegindate = busibegindate;
	}
	public String getBusienddate() {
		return busienddate;
	}
	public void setBusienddate(String busienddate) {
		this.busienddate = busienddate;
	}
	public String getBusipayamt() {
		return busipayamt;
	}
	public void setBusipayamt(String busipayamt) {
		this.busipayamt = busipayamt;
	}
	public String getPaytype() {
		return paytype;
	}
	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}
	public String getChrflag() {
		return chrflag;
	}
	public void setChrflag(String chrflag) {
		this.chrflag = chrflag;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getFailreason() {
		return failreason;
	}
	public void setFailreason(String failreason) {
		this.failreason = failreason;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	
	
}
