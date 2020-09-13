package com.yondervision.yfmap.result.hulunbuir;

/** 
* @ClassName: AppApi00201 
* @Description: 贷款余额查询接口返回参数
* @author Caozhongyan
* @date Sep 27, 2013 10:05:20 AM   
* 
*/ 
public class HulunbuirAppApi00802Result {
	/**
	 * 开发商名称
	 */
	private String cocustname = "";		
	/**
	 * 小区名称
	 */
	private String projectname = "";	
	/**
	 * 项目协议号
	 */
	private String protonum = "";	
	/**
	 * 栋号前缀
	 */
	private String polenumpre = "";	
	/**
	 * 栋号全称
	 */
	private String polename = "";	
	/**
	 * 起始栋号
	 */
	private String begpolenum = "";	
	/**
	 * 终止栋号
	 */
	private String endpolenum = "";	
	/**
	 * 联系人
	 */
	private String linkman = "";	
	/**
	 * 电话
	 */
	private String linkmanphone = "";	
	/**
	 *	地址
	 */
	private String housesit = "";	

	public String getCocustname() {
		return cocustname;
	}

	public void setCocustname(String cocustname) {
		this.cocustname = cocustname;
	}

	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}

	public String getProtonum() {
		return protonum;
	}

	public void setProtonum(String protonum) {
		this.protonum = protonum;
	}

	public String getPolenumpre() {
		return polenumpre;
	}

	public void setPolenumpre(String polenumpre) {
		this.polenumpre = polenumpre;
	}

	public String getPolename() {
		return polename;
	}

	public void setPolename(String polename) {
		this.polename = polename;
	}

	public String getBegpolenum() {
		return begpolenum;
	}

	public void setBegpolenum(String begpolenum) {
		this.begpolenum = begpolenum;
	}

	public String getEndpolenum() {
		return endpolenum;
	}

	public void setEndpolenum(String endpolenum) {
		this.endpolenum = endpolenum;
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

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanphone() {
		return linkmanphone;
	}

	public void setLinkmanphone(String linkmanphone) {
		this.linkmanphone = linkmanphone;
	}

	public String getHousesit() {
		return housesit;
	}

	public void setHousesit(String housesit) {
		this.housesit = housesit;
	}
	
}
