package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00210Form
* @Description: 家庭信息住房查询
* @author luolin
* @date 2019-08-05
*
*/
public class FamilyMembersForm {

	// 业务名称

	private String name;

	private String idNo;

	public FamilyMembersForm(){

	}
	public FamilyMembersForm(String name, String idNo){
		this.name=name;
		this.idNo=idNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(final String idNo) {
		this.idNo = idNo;
	}


}
