package com.yondervision.yfmap.result.kunming;

/**
 * 
 * @author 
 *  个人 -- 个人公积金明细查询
 */


public class AppApi00201ZHResult {
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 个人公积金账号*/
	private String accnum = "";
	/** 证件号码*/
	private String certinum = "";
	/** 卡号*/
	private String cardno= "";
	/** 渠道标识*/
	private String flag = "";
	/** 起始时间*/
	private String begdate = "";
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
	/** 终止时间*/
	private String enddate = "";
	/** 下传批量文件名*/
	private String filename = "";
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
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
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getBegdate() {
		return begdate;
	}
	public void setBegdate(String begdate) {
		this.begdate = begdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
//	public String getTotalpage() {
//		return totalpage;
//	}
//	public void setTotalpage(String totalpage) {
//		this.totalpage = totalpage;
//	}
//	public String getTotalnum() {
//		return totalnum;
//	}
//	public void setTotalnum(String totalnum) {
//		this.totalnum = totalnum;
//	}
	
	
}
