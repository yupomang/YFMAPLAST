package com.yondervision.yfmap.result.mengdian;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class AppApi40101Result {
	/**
	 * 个人公积金帐号
	 */
	private String accnum = "";				
			
	/**
	 *帐号姓名
	 */
	private String accname = "";				
	/**
	 * 单位帐号
	 */
	private String certinum = "";				
	/**
	 * 单位名称
	 */
	private String flag = "";				
	/**
	 * 交易日期
	 */
	private String loancontrnum = "";	
	public String getAccnum() {
		return accnum;
	}

	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}
	/**
	 * 响应码
	 */
	private String recode="";
	
	/**
	 * 响应信息
	 */
	private String msg="";
	private String fileName = "";	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
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

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	
}
