package com.yondervision.yfmap.result.hulunbuir;


/**
 * @author Caozhongyan
 *
 */
public class HulunbuirAppApi00604Result {
	/** 借款合同号 */					
	private String loancontrnum = "";					
	/** 姓名 */					
	private String accname = "";					
	/** 身份证*/					
	private String certinum = "";					
	/** 借款合同内部号 */					
	private String pubaccnum = "";				
	/** 未还第一期开始日期 */					
	private String begdate = "";
	public String getBegdate() {
		return begdate;
	}

	public void setBegdate(String begdate) {
		this.begdate = begdate;
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

	public String getLoancontrnum() {
		return loancontrnum;
	}

	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getPubaccnum() {
		return pubaccnum;
	}

	public void setPubaccnum(String pubaccnum) {
		this.pubaccnum = pubaccnum;
	}

	
}
