package com.yondervision.yfmap.result;

/** 
* @ClassName: AppApi00501Result 
* @Description: 提取明细查询
* @author Caozhongyan
* @date Sep 27, 2013 10:27:15 AM   
* 
*/ 
public class AppApi011Result {
	/** 审批状态 */						
	private String apprflagID = "";
	private String apprflagMSG = "";
	private String apprflagDate = "";
	public String getApprflagDate() {
		return apprflagDate;
	}
	public void setApprflagDate(String apprflagDate) {
		this.apprflagDate = apprflagDate;
	}
	public String getApprflagID() {
		return apprflagID;
	}
	public void setApprflagID(String apprflagID) {
		this.apprflagID = apprflagID;
	}
	public String getApprflagMSG() {
		return apprflagMSG;
	}
	public void setApprflagMSG(String apprflagMSG) {
		this.apprflagMSG = apprflagMSG;
	}
	
}
