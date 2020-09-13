package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author qinxla
 *  批量下载  --- 单位明细查询
 */
public class AppApi021_01ZHResult {
	
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
	
	/** 交易日期*/
	private String transdate = "";
	/** 摘要*/
	private String summary = "";
	/** 提取人姓名*/
	private String accname = "";
	/** 提取人公积金账号*/
	private String acaccnum = "";
	/** 借方发生额   / 支出*/
	private String damt = "";
	/** 贷方发生额  /  收入*/
	private String camt = "";
	/** 余额*/
	private String balance = "";
	/** 流水号*/
	private String hostsernum = "";
	/** 支付方式*/
	private String paymode = "";
	/** 交易类型*/
	private String tradetype = "";
	/** 汇缴开始日期*/
	private String paybegindate = "";
	/** 汇缴截止日期*/
	private String payenddate = "";
	/** 经办机构*/
	private String agentinstcode = "";
	/** 结算柜员*/
	private String settleop = "";
	
	
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	
	public String getAcaccnum() {
		return acaccnum;
	}
	public void setAcaccnum(String acaccnum) {
		this.acaccnum = acaccnum;
	}
	public String getDamt() {
		return damt;
	}
	public void setDamt(String damt) {
		this.damt = damt;
	}
	public String getCamt() {
		return camt;
	}
	public void setCamt(String camt) {
		this.camt = camt;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getHostsernum() {
		return hostsernum;
	}
	public void setHostsernum(String hostsernum) {
		this.hostsernum = hostsernum;
	}
	public String getPaymode() {
		return paymode;
	}
	public void setPaymode(String paymode) {
		this.paymode = paymode;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getPaybegindate() {
		return paybegindate;
	}
	public void setPaybegindate(String paybegindate) {
		this.paybegindate = paybegindate;
	}
	public String getPayenddate() {
		return payenddate;
	}
	public void setPayenddate(String payenddate) {
		this.payenddate = payenddate;
	}
	public String getAgentinstcode() {
		return agentinstcode;
	}
	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}
	public String getSettleop() {
		return settleop;
	}
	public void setSettleop(String settleop) {
		this.settleop = settleop;
	}
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
	
	
	
}
