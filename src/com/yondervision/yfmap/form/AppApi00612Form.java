package com.yondervision.yfmap.form;

/**
 * 异地贷款信息查询
 * @author fxliu
 *
 */
public class AppApi00612Form extends AppApiCommonForm {
	/**
	 * 借款合同号
	 */
	private String loancontrnum = "";
	
	/**
	 * 手机号码
	 */
	private String tel = "";
	

	
	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}
