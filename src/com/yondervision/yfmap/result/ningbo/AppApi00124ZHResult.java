package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi00124ZHResult {

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	private String 	loantimes;//贷款次数
	private String 	usebal;//公积金认定余额
	private String 	conpaymonth;//连续缴存月分类
	private String 	basenum;//缴存基数
	private String 	bal;//当前余额
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
	public String getLoantimes() {
		return loantimes;
	}
	public void setLoantimes(String loantimes) {
		this.loantimes = loantimes;
	}
	public String getUsebal() {
		return usebal;
	}
	public void setUsebal(String usebal) {
		this.usebal = usebal;
	}
	public String getConpaymonth() {
		return conpaymonth;
	}
	public void setConpaymonth(String conpaymonth) {
		this.conpaymonth = conpaymonth;
	}
	public String getBasenum() {
		return basenum;
	}
	public void setBasenum(String basenum) {
		this.basenum = basenum;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}

	

}

