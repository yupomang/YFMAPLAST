package com.yondervision.yfmap.result.baogang;


/**
 *
 */
public class BaoGangAppApi303Result {
	/** 材料名称 */ //预约号-呼伦贝尔					
	private String matname = "";
	/** 材料内容 *///已预约人数-呼伦贝尔					
	private String vicecontent = "";
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

	public String getMatname() {
		return matname;
	}

	public void setMatname(String matname) {
		this.matname = matname;
	}

	public String getVicecontent() {
		return vicecontent;
	}

	public void setVicecontent(String vicecontent) {
		this.vicecontent = vicecontent;
	}

}
