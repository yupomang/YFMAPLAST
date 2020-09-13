package com.yondervision.yfmap.form;

public class AppApi01028Form extends AppApiCommonForm {
		//证件号码	
	//private String bodyCardNumber="";
	private String loancontrcode=""; // 合同账号
	private String flag=""; // 标志</>
	private String instance=""; // <>实例号
	private String grzh="";	//个人账号
	private String xingming = "";
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getLoancontrcode() {
		return loancontrcode;
	}
	public void setLoancontrcode(String loancontrcode) {
		this.loancontrcode = loancontrcode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
//	public String getBodyCardNumber() {
//		return bodyCardNumber;
//	}
//	public void setBodyCardNumber(String bodyCardNumber) {
//		this.bodyCardNumber = bodyCardNumber;
//	}
	
}
