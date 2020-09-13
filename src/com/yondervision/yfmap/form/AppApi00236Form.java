package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00236Form
* @Description: 宁波市高层次人才认定
* @author luolin
* @date 2020.03.26
*
*/
public class AppApi00236Form extends AppApiCommonForm{
	/*
	身份证 aac002 身份证号码
姓名 aac003 姓名

 */
	private String aac002;
	private String aac003;

	public String getAac002() {
		return this.aac002;
	}

	public void setAac002(final String aac002) {
		this.aac002 = aac002;
	}

	public String getAac003() {
		return this.aac003;
	}

	public void setAac003(final String aac003) {
		this.aac003 = aac003;
	}
}