package com.yondervision.yfmap.result.hulunbuir;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class HulunbuirAppApi00402Result {
	/**
	 * 个人公积金帐号
	 */
	private String accnum = "";				
			
	/**
	 *帐号姓名
	 */
	private String accname = "";				
	/**
	 * 变更类型
	 */
	private String custchgtype = "";				
	/**
	 * 变更项
	 */
	private String desc = "";				
	/**
	 * 变更前值
	 */
	private String bfchgval = "";				
	/**
	 * 变更后值
	 */
	private String afchgval = "";				
	/**
	 * 变更日期
	 */
	private String transdate = "";				
	/**
	 * 经办机构
	 */
	private String agentinstcode = "";	
	/**
	 * 经办柜员
	 */
	private String agentopname = "";	
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

	public String getTransdate() {
		return transdate;
	}

	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}

	public String getCustchgtype() {
		return custchgtype;
	}

	public void setCustchgtype(String custchgtype) {
		this.custchgtype = custchgtype;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBfchgval() {
		return bfchgval;
	}

	public void setBfchgval(String bfchgval) {
		this.bfchgval = bfchgval;
	}

	public String getAfchgval() {
		return afchgval;
	}

	public void setAfchgval(String afchgval) {
		this.afchgval = afchgval;
	}

	public String getAgentinstcode() {
		return agentinstcode;
	}

	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}

	public String getAgentopname() {
		return agentopname;
	}

	public void setAgentopname(String agentopname) {
		this.agentopname = agentopname;
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
	
}
