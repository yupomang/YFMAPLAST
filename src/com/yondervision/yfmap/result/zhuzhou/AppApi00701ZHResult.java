package com.yondervision.yfmap.result.zhuzhou;
/**
 * 贷款明细查询
 * @author qinxla
 *
 */
public class AppApi00701ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 下传批量文件名*/
	private String filename = "";
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	/**	 本金金额*/
	private String bjje = "";
	/**	 当前余额*/
	private String curbal = "";
	/**	 罚息金额*/
	private String fxje = "";
	/**	 借款合同编号*/
	private String jkhtbh = "";
	/**	利息金额 */
	private String lxje = "";
	/**	还款总金额 */
	private String repaytolamt = "";
	
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getBjje() {
		return bjje;
	}
	public void setBjje(String bjje) {
		this.bjje = bjje;
	}
	public String getCurbal() {
		return curbal;
	}
	public void setCurbal(String curbal) {
		this.curbal = curbal;
	}
	public String getFxje() {
		return fxje;
	}
	public void setFxje(String fxje) {
		this.fxje = fxje;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}
	public String getLxje() {
		return lxje;
	}
	public void setLxje(String lxje) {
		this.lxje = lxje;
	}
	public String getRepaytolamt() {
		return repaytolamt;
	}
	public void setRepaytolamt(String repaytolamt) {
		this.repaytolamt = repaytolamt;
	}
	
}
