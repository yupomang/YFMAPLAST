package com.yondervision.yfmap.result.haikou;

/**
 *公积金代扣协议下传文件
 * @author LFX
 *
 */
public class AppApi0104601Result {
	//姓名 证件类型 证件号 扣款人类型 协议签订类型  代扣顺序  空值  seq值
	//ACCNAME	LINKMANPHONE	ACCNUM	HOSTSERNUM	UNITACCNAME	MATENAME	WORKUNIT	INSTANCE
	/**
	 * 姓名 
	 */
	private String accname = "";
	/**
	 * 证件类型 
	 */
	private String linkmanphone = "";
	/**
	 * 证件号
	 */
	private String accnum = "";
	/**
	 * 扣款人类型
	 */
	private String hostsernum = "";
	/**
	 * 协议签订类型
	 */
	private String unitaccname = "";
	/**
	 * 代扣顺序 
	 */
	private String matename = "";
	/**
	 * 空值  
	 */
	private String workunit = "";
	/**
	 * seq值
	 */
	private String instance = "";
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getLinkmanphone() {
		return linkmanphone;
	}
	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getHostsernum() {
		return hostsernum;
	}
	public void setHostsernum(String hostsernum) {
		this.hostsernum = hostsernum;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getWorkunit() {
		return workunit;
	}
	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	
}
