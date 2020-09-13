package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi10107Form 
* @Description: 排队取号请求Form
* @author gongqi
* 
*/ 
public class AppApi10107Form extends AppApiCommonForm {
	private String websitecode = "";
	private String jobid = "";
	private String idcardNumber = "";
	private String getMethod = "";
	
	public String getWebsitecode() {
		return websitecode;
	}

	public void setWebsitecode(String websitecode) {
		this.websitecode = websitecode;
	}

	public String getJobid() {
		return jobid;
	}

	public void setJobid(String jobid) {
		this.jobid = jobid;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getGetMethod() {
		return getMethod;
	}

	public void setGetMethod(String getMethod) {
		this.getMethod = getMethod;
	}
	
}
