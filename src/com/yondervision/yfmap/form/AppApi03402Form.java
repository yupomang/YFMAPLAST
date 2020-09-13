package com.yondervision.yfmap.form;

public class AppApi03402Form extends AppApiCommonForm{
	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";
	/** 是否分页标识 */	
	private String ispaging = "";
	/** 个人账号 */	
	private String accnum = "";
	/** 业务日期 */	
	private String busitype = "";
	/** 提取金额 */	
	private String drawamt = "";
	/** 资金交易密码 */
	private String tranpass = "";
	
	public String getTranpass() {
		return tranpass;
	}
	public void setTranpass(String tranpass) {
		this.tranpass = tranpass;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}
	public String getIspaging() {
		return ispaging;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
	}
	
	

}
