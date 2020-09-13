package com.yondervision.yfmap.result.ningbo;
/**	 
 * 贷款进度查询
 * @author qinxla
 *
 */
public class AppApi01101ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 受理日期*/
	private String appdate_wb = "";
	/** 核准日期*/
	private String approvedate_wb = "";
	/** 银行名称*/
	private String bankname = "";
	/** 借款人姓名*/
	private String loaneename = "";
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
	public String getAppdate_wb() {
		return appdate_wb;
	}
	public void setAppdate_wb(String appdate_wb) {
		this.appdate_wb = appdate_wb;
	}
	public String getApprovedate_wb() {
		return approvedate_wb;
	}
	public void setApprovedate_wb(String approvedate_wb) {
		this.approvedate_wb = approvedate_wb;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getLoaneename() {
		return loaneename;
	}
	public void setLoaneename(String loaneename) {
		this.loaneename = loaneename;
	}

}
