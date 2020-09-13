package com.yondervision.yfmap.result.ningbo;
/**
 * 单位明细查询
 * @author HYD-HSP
 *
 */
public class AppApi02101ZHResult {
	/** 补缴总额*/
	private String sendsum = "";
	
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
	/**	 第几页*/
	private String pagenum = "";
	/**	 每页显示记录数*/
	private String pagerows = "";
	/** 下传批量文件路径*/
	private String filepath = "";

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
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getSendsum() {
		return sendsum;
	}
	public void setSendsum(String sendsum) {
		this.sendsum = sendsum;
	}
	
}
