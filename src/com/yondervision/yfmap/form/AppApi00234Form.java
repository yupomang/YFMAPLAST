package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00234Form
* @Description: 个人开户
* @author luolin
* @date 2019-12-19
*
*/
public class AppApi00234Form extends AppApiCommonForm{
	/*
	unitaccnum   单位账号
	unitaccname   单位名称
	begdate  缓/停缴开始时间
	enddate  缓/停缴终止时间
	postpayflag 缓/停缴标志   1-缓缴 5-缓缴解除 2-停缴 6-停缴解除
	postpayren  缓/停缴原因
	unitlinkman  单位经办人
	unitlinkphone 经办人联系电话
	filename  图片文件名称
 */
	private String unitaccnum;
	private String unitaccname;
	private String begdate;
	private String enddate;
	private String postpayflag;
	private String postpayren;
	private String unitlinkman1;
	private String unitlinkphone2;
	private String filename;

	public String getUnitaccnum() {
		return this.unitaccnum;
	}

	public void setUnitaccnum(final String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getUnitaccname() {
		return this.unitaccname;
	}

	public void setUnitaccname(final String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getBegdate() {
		return this.begdate;
	}

	public void setBegdate(final String begdate) {
		this.begdate = begdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(final String enddate) {
		this.enddate = enddate;
	}

	public String getPostpayflag() {
		return this.postpayflag;
	}

	public void setPostpayflag(final String postpayflag) {
		this.postpayflag = postpayflag;
	}

	public String getPostpayren() {
		return this.postpayren;
	}

	public void setPostpayren(final String postpayren) {
		this.postpayren = postpayren;
	}

	public String getUnitlinkman1() {
		return this.unitlinkman1;
	}

	public void setUnitlinkman1(final String unitlinkman1) {
		this.unitlinkman1 = unitlinkman1;
	}

	public String getUnitlinkphone2() {
		return this.unitlinkphone2;
	}

	public void setUnitlinkphone2(final String unitlinkphone2) {
		this.unitlinkphone2 = unitlinkphone2;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(final String filename) {
		this.filename = filename;
	}
}