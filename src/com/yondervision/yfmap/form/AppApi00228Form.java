package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00228Form
* @Description: 宁波公积金注销申请接收
* @author luolin
* @date 2019-11-18
*
*/
public class AppApi00228Form extends AppApiCommonForm{

	/*办结时间 recordtime 办结时间
意见描述 auditmind 意见描述
统一社会信用代码 uniscid 统一社会信用代码
状态码 state 0已受理；1不予办理；2注销成功；3注销 状态码
受理时间 accepttime 受理时间*/
	private String recordtime;
	private String auditmind;
	private String uniscid;
	private String accepttime;
	private String state;

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getRecordtime() {
		return this.recordtime;
	}

	public void setRecordtime(final String recordtime) {
		this.recordtime = recordtime;
	}

	public String getAuditmind() {
		return this.auditmind;
	}

	public void setAuditmind(final String auditmind) {
		this.auditmind = auditmind;
	}

	public String getUniscid() {
		return this.uniscid;
	}

	public void setUniscid(final String uniscid) {
		this.uniscid = uniscid;
	}

	public String getAccepttime() {
		return this.accepttime;
	}

	public void setAccepttime(final String accepttime) {
		this.accepttime = accepttime;
	}

	private String businName;
	private String powerMatters;
	private String subPowerMatters;
	private String materialName;
	private String accname;

	public String getPowerMatters() {
		return this.powerMatters;
	}

	public void setPowerMatters(final String powerMatters) {
		this.powerMatters = powerMatters;
	}

	public String getSubPowerMatters() {
		return this.subPowerMatters;
	}

	public void setSubPowerMatters(final String subPowerMatters) {
		this.subPowerMatters = subPowerMatters;
	}

	public String getMaterialName() {
		return this.materialName;
	}

	public void setMaterialName(final String materialName) {
		this.materialName = materialName;
	}

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(final String accname) {
		this.accname = accname;
	}

	public String getBusinName() {
		return this.businName;
	}

	public void setBusinName(final String businName) {
		this.businName = businName;
	}

	private String centerId;

	/*统一社会信用代码	orgsocode
单位名称	unitaccname
经办人姓名	unitlinkman
经办人证件类型	certitype
经办人证件号码	certinum
经办人手机号码	linkphone
注销原因	closereason
注销日期	transdate*/
	private String orgsocode;
	private String unitaccname;
	private String unitlinkman;
	private String certitype;
	private String certinum;
	private String linkphone;
	private String closereason;
	private String transdate;

	public String getOrgsocode() {
		return this.orgsocode;
	}

	public void setOrgsocode(final String orgsocode) {
		this.orgsocode = orgsocode;
	}

	public String getUnitaccname() {
		return this.unitaccname;
	}

	public void setUnitaccname(final String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getUnitlinkman() {
		return this.unitlinkman;
	}

	public void setUnitlinkman(final String unitlinkman) {
		this.unitlinkman = unitlinkman;
	}

	public String getCertitype() {
		return this.certitype;
	}

	public void setCertitype(final String certitype) {
		this.certitype = certitype;
	}

	public String getCertinum() {
		return this.certinum;
	}

	public void setCertinum(final String certinum) {
		this.certinum = certinum;
	}

	public String getLinkphone() {
		return this.linkphone;
	}

	public void setLinkphone(final String linkphone) {
		this.linkphone = linkphone;
	}

	public String getClosereason() {
		return this.closereason;
	}

	public void setClosereason(final String closereason) {
		this.closereason = closereason;
	}

	public String getTransdate() {
		return this.transdate;
	}

	public void setTransdate(final String transdate) {
		this.transdate = transdate;
	}

	public String getCenterId() {
		return this.centerId;
	}

	public void setCenterId(final String centerId) {
		this.centerId = centerId;
	}
}
