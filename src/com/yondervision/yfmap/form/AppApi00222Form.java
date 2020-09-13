package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00222Form
* @Description: 评价器信息接口
* @author luolin
* @date 2019-10-09
*
*/
public class AppApi00222Form {
/*
评价器信息确认接口
check_info	是	所有收件信息
istype	是	接口类型 信息确认为4
sender_userid	是	工作人员id
*/
	// 业务名称
	private String businName;
	private String channel;
	private String centerId;
	private String istype;
	private String sender_userid;
	private String check_info;

	public String getCheck_info() {
		return this.check_info;
	}

	public void setCheck_info(final String check_info) {
		this.check_info = check_info;
	}

	/*servicename	事项名称
    servcieid	事项ID
    reiveuserid	收件人ID
    receiveusername	收件人姓名
    receivedeptid	收件人部门ID
    receivedeptname	收件人部门名称
    info_unid	办件唯一码（非申报号）
    inprojid	申报号
    proname	项目名称
    original	申报来源
    qlsxbm	权力事项编码*/
	//收件信息sjinfo
	private String servicename;
	private String servcieid;
	private String reiveuserid;
	private String receiveusername;
	private String receivedeptid;
	private String receivedeptname;
	private String info_unid;
	private String inprojid;
	private String proname;
	private String original;
	private String qlsxbm;
	//app信息 app
	/*objname	申报者姓名
cardtype	申报者证件类型
cardnum	申报者证件号码
address	联系地址
postcode	邮政编码
userType	申请人类型
people	法人代表*/
	private String objname;
	private String cardtype;
	private String cardnum;
	private String address;
	private String postcode;
	private String userType;
	private String people;
	//person人员信息
	/*xm	联系人
phone	联系人手机
telephone	联系人电话
concardtype	联系人证件类型
concardnum	联系人证件号码*/
	private String xm;
	private String phone;
	private String telephone;
	private String concardtype;
	private String concardnum;
	//matlist材料信息
	/*xh	序号
matname	材料名称
sl	材料份数
recmethod	保存状态
state	接收方式*/
	private String xh;
	private String matname;
	private String sl;
	private String recmethod;
	private String state;
	/*
	获取自建系统人员信息接口
	user_unid 是 人员ID
    user_name 是 人员名字
    user_dept 是 部门名称
    user_deptId 是 部门ID
    systemName 是 系统名称
    sex 否 性别
    work_code 否 工号
    work_post 否 岗位*/
	private String user_unid;
	private String user_name;
	private String user_dept;
	private String user_deptId;
	private String systemName;
	private String sex;
	private String work_code;
	private String work_post;

	/*评价器用户登录功能
	*user_info 人员信息
	*  	type  1
	*  	serial 设备ID
	*  	sender_userid 登录人ID
	* istype 人员登录类型为1
	* */


	public String getUser_unid() {
		return this.user_unid;
	}

	public void setUser_unid(final String user_unid) {
		this.user_unid = user_unid;
	}

	public String getUser_name() {
		return this.user_name;
	}

	public void setUser_name(final String user_name) {
		this.user_name = user_name;
	}

	public String getUser_dept() {
		return this.user_dept;
	}

	public void setUser_dept(final String user_dept) {
		this.user_dept = user_dept;
	}

	public String getUser_deptId() {
		return this.user_deptId;
	}

	public void setUser_deptId(final String user_deptId) {
		this.user_deptId = user_deptId;
	}

	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(final String systemName) {
		this.systemName = systemName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(final String sex) {
		this.sex = sex;
	}

	public String getWork_code() {
		return this.work_code;
	}

	public void setWork_code(final String work_code) {
		this.work_code = work_code;
	}

	public String getWork_post() {
		return this.work_post;
	}

	public void setWork_post(final String work_post) {
		this.work_post = work_post;
	}

	public String getIstype() {
		return this.istype;
	}

	public void setIstype(final String istype) {
		this.istype = istype;
	}

	public String getSender_userid() {
		return this.sender_userid;
	}

	public void setSender_userid(final String sender_userid) {
		this.sender_userid = sender_userid;
	}

	public String getServicename() {
		return this.servicename;
	}

	public void setServicename(final String servicename) {
		this.servicename = servicename;
	}

	public String getServcieid() {
		return this.servcieid;
	}

	public void setServcieid(final String servcieid) {
		this.servcieid = servcieid;
	}

	public String getReiveuserid() {
		return this.reiveuserid;
	}

	public void setReiveuserid(final String reiveuserid) {
		this.reiveuserid = reiveuserid;
	}

	public String getReceiveusername() {
		return this.receiveusername;
	}

	public void setReceiveusername(final String receiveusername) {
		this.receiveusername = receiveusername;
	}

	public String getReceivedeptid() {
		return this.receivedeptid;
	}

	public void setReceivedeptid(final String receivedeptid) {
		this.receivedeptid = receivedeptid;
	}

	public String getReceivedeptname() {
		return this.receivedeptname;
	}

	public void setReceivedeptname(final String receivedeptname) {
		this.receivedeptname = receivedeptname;
	}

	public String getInfo_unid() {
		return this.info_unid;
	}

	public void setInfo_unid(final String info_unid) {
		this.info_unid = info_unid;
	}

	public String getInprojid() {
		return this.inprojid;
	}

	public void setInprojid(final String inprojid) {
		this.inprojid = inprojid;
	}

	public String getProname() {
		return this.proname;
	}

	public void setProname(final String proname) {
		this.proname = proname;
	}

	public String getOriginal() {
		return this.original;
	}

	public void setOriginal(final String original) {
		this.original = original;
	}

	public String getQlsxbm() {
		return this.qlsxbm;
	}

	public void setQlsxbm(final String qlsxbm) {
		this.qlsxbm = qlsxbm;
	}

	public String getObjname() {
		return this.objname;
	}

	public void setObjname(final String objname) {
		this.objname = objname;
	}

	public String getCardtype() {
		return this.cardtype;
	}

	public void setCardtype(final String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardnum() {
		return this.cardnum;
	}

	public void setCardnum(final String cardnum) {
		this.cardnum = cardnum;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(final String postcode) {
		this.postcode = postcode;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(final String userType) {
		this.userType = userType;
	}

	public String getPeople() {
		return this.people;
	}

	public void setPeople(final String people) {
		this.people = people;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(final String xm) {
		this.xm = xm;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	public String getConcardtype() {
		return this.concardtype;
	}

	public void setConcardtype(final String concardtype) {
		this.concardtype = concardtype;
	}

	public String getConcardnum() {
		return this.concardnum;
	}

	public void setConcardnum(final String concardnum) {
		this.concardnum = concardnum;
	}

	public String getXh() {
		return this.xh;
	}

	public void setXh(final String xh) {
		this.xh = xh;
	}

	public String getMatname() {
		return this.matname;
	}

	public void setMatname(final String matname) {
		this.matname = matname;
	}

	public String getSl() {
		return this.sl;
	}

	public void setSl(final String sl) {
		this.sl = sl;
	}

	public String getRecmethod() {
		return this.recmethod;
	}

	public void setRecmethod(final String recmethod) {
		this.recmethod = recmethod;
	}

	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	public String getBusinName() {
		return this.businName;
	}

	public void setBusinName(final String businName) {
		this.businName = businName;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	public String getCenterId() {
		return this.centerId;
	}

	public void setCenterId(final String centerId) {
		this.centerId = centerId;
	}
}
