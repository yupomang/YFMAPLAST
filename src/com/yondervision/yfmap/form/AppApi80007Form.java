package com.yondervision.yfmap.form;

public class AppApi80007Form extends AppApiCommonForm {
	private String param;
	/** 办件统一赋码ID */				
	private String projId = "";				
	/** 政务总线统一分配应用ID */				
	private String appId = "";	
	/** 备注 */				
	private String memo = "";
	/** 扩展字段信息 */	
	private String extInfo = "";
	/** 事项唯一编码 */				
	private String matterCode = "";
	/** 事项终审部门所属编码 */				
	private String deptCode = "";
	/** 业务类型 */				
	private String bizType = "";
	/** 办件类型 */				
	private String affairType = "";
	/** 联办业务标识 */				
	private String relBizId = "";
	/** 申请者信息 */				
	private String applicantVO = "";
	/** 申报项目具体名称 */				
	private String projectName = "";
	/** 项目性质 */				
	private String projectNature = "";
	/** 收件部门所属编码 */				
	private String recvDeptCode = "";
	/** 收件部门名称 */				
	private String recvDeptName = "";
	/** 收件部门所属行政区划编码 */				
	private String areaCode = "";
	/** 执行部门统一社会信用代码 */				
	private String execDeptOrgCode = "";
	/** 创建用户类型 */				
	private String recvUserType = "";
	/** 创建用户ID */				
	private String recvUserId = "";
	/** 创建用户名称 */				
	private String recvUserName = "";
	/** 申报来源 */				
	private String applyOrigin = "";
	/** 审批性质 */				
	private String approveType = "";
	/** 申报时间 */				
	private String gmtApply = "";
	/** 办件表单信息 */				
	private String affFormInfo = "";
	/** 办件材料信息 */				
	private String suffInfoList = "";
	/** 事项目录编码 */				
	private String matterDir = "";
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getExtInfo() {
		return extInfo;
	}
	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}
	public String getMatterCode() {
		return matterCode;
	}
	public void setMatterCode(String matterCode) {
		this.matterCode = matterCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getAffairType() {
		return affairType;
	}
	public void setAffairType(String affairType) {
		this.affairType = affairType;
	}
	public String getRelBizId() {
		return relBizId;
	}
	public void setRelBizId(String relBizId) {
		this.relBizId = relBizId;
	}
	public String getApplicantVO() {
		return applicantVO;
	}
	public void setApplicantVO(String applicantVO) {
		this.applicantVO = applicantVO;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectNature() {
		return projectNature;
	}
	public void setProjectNature(String projectNature) {
		this.projectNature = projectNature;
	}
	public String getRecvDeptCode() {
		return recvDeptCode;
	}
	public void setRecvDeptCode(String recvDeptCode) {
		this.recvDeptCode = recvDeptCode;
	}
	public String getRecvDeptName() {
		return recvDeptName;
	}
	public void setRecvDeptName(String recvDeptName) {
		this.recvDeptName = recvDeptName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getExecDeptOrgCode() {
		return execDeptOrgCode;
	}
	public void setExecDeptOrgCode(String execDeptOrgCode) {
		this.execDeptOrgCode = execDeptOrgCode;
	}
	public String getRecvUserType() {
		return recvUserType;
	}
	public void setRecvUserType(String recvUserType) {
		this.recvUserType = recvUserType;
	}
	public String getRecvUserId() {
		return recvUserId;
	}
	public void setRecvUserId(String recvUserId) {
		this.recvUserId = recvUserId;
	}
	public String getRecvUserName() {
		return recvUserName;
	}
	public void setRecvUserName(String recvUserName) {
		this.recvUserName = recvUserName;
	}
	public String getApplyOrigin() {
		return applyOrigin;
	}
	public void setApplyOrigin(String applyOrigin) {
		this.applyOrigin = applyOrigin;
	}
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	public String getGmtApply() {
		return gmtApply;
	}
	public void setGmtApply(String gmtApply) {
		this.gmtApply = gmtApply;
	}
	public String getAffFormInfo() {
		return affFormInfo;
	}
	public void setAffFormInfo(String affFormInfo) {
		this.affFormInfo = affFormInfo;
	}
	public String getSuffInfoList() {
		return suffInfoList;
	}
	public void setSuffInfoList(String suffInfoList) {
		this.suffInfoList = suffInfoList;
	}
	public String getMatterDir() {
		return matterDir;
	}
	public void setMatterDir(String matterDir) {
		this.matterDir = matterDir;
	}
	/** 业务处理状态 */				
	private String resultStatus = "";
	/** 错误码信息 */				
	private String resultMsg = "";
	/** 服务请求ID */				
	private String resultId = "";
	/** 返回数据 */				
	private String data = "";
	/** 结果码 */				
	private String resultCode = "";
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
	/*银行卡的所属银行:payeebankcode
卡号:payeebankaccnum
个人账号:accnum
姓名:accname
证件类型:certitype
证件号码:certinum
冻结状态:frzflag
提取原因:drawreason
渠道流水号：qdapprnum
单位账号:unitaccnum
手机号码:handset
可提取金额:inputamt
事项编码:checkcode*/
	private String payeebankcode ;
	private String payeebankaccnum ;
	private String accnum ;
	private String accname ;
	private String certitype ;
	private String certinum ;
	private String frzflag ;
	private String drawreason ;
	private String qdapprnum ;
	private String unitaccnum ;
	private String handset ;
	private String inputamt ;
	private String checkcode ;

/*
id="form_nation" title="民族					spt_race
id="form_residence" title="户口性质				spt_hkxz
id="form_address" title="户籍地址  				spt_hjszd
id="form_iphone" title="手机号码				spt_sjhm
id="form_postal" title="通讯地址				spt_jtdz
id="form_comname" title="单位名称				spt_dwmc
id="form_insurTime" title="本次参保时间			spt_sqsj
id="form_secode" title="统一社会信用代码 		spt_tyxyydm
id="form_account" title="公积金单位账号 		spt_dwzh
id="form_industry" title="行业" 				spt_hyfl
id="form_fund" title="公积金月缴基数（元）" 	spt_basenum
id="form_uratio" title="单位缴存比例（公积金）	spt_dwjcbl
id="form_pratio" title="个人缴存比例（公积金）	spt_grjcbl
id="form_ufund" title="公积金-单位月缴存额	  	spt_dwyjce
id="form_pfund" title="公积金-个人月缴存额		spt_gryjce
id="form_tfund" title="公积金-月缴存额			spt_yjce
*/
	private String spt_race ;
	private String spt_hkxz ;
	private String spt_hjszd ;
	private String spt_jtdz ;
	private String spt_dwmc ;
	private String spt_sqsj ;
	private String spt_tyxyydm ;
	private String spt_dwzh ;
	private String spt_hyfl ;
	private String spt_basenum ;
	private String spt_dwjcbl ;
	private String spt_grjcbl ;
	private String spt_dwyjce ;
	private String spt_gryjce ;
	private String spt_yjce ;

	public String getSpt_race() {
		return this.spt_race;
	}

	public void setSpt_race(final String spt_race) {
		this.spt_race = spt_race;
	}

	public String getSpt_hkxz() {
		return this.spt_hkxz;
	}

	public void setSpt_hkxz(final String spt_hkxz) {
		this.spt_hkxz = spt_hkxz;
	}

	public String getSpt_hjszd() {
		return this.spt_hjszd;
	}

	public void setSpt_hjszd(final String spt_hjszd) {
		this.spt_hjszd = spt_hjszd;
	}

	public String getSpt_jtdz() {
		return this.spt_jtdz;
	}

	public void setSpt_jtdz(final String spt_jtdz) {
		this.spt_jtdz = spt_jtdz;
	}

	public String getSpt_dwmc() {
		return this.spt_dwmc;
	}

	public void setSpt_dwmc(final String spt_dwmc) {
		this.spt_dwmc = spt_dwmc;
	}

	public String getSpt_sqsj() {
		return this.spt_sqsj;
	}

	public void setSpt_sqsj(final String spt_sqsj) {
		this.spt_sqsj = spt_sqsj;
	}

	public String getSpt_tyxyydm() {
		return this.spt_tyxyydm;
	}

	public void setSpt_tyxyydm(final String spt_tyxyydm) {
		this.spt_tyxyydm = spt_tyxyydm;
	}

	public String getSpt_dwzh() {
		return this.spt_dwzh;
	}

	public void setSpt_dwzh(final String spt_dwzh) {
		this.spt_dwzh = spt_dwzh;
	}

	public String getSpt_hyfl() {
		return this.spt_hyfl;
	}

	public void setSpt_hyfl(final String spt_hyfl) {
		this.spt_hyfl = spt_hyfl;
	}

	public String getSpt_basenum() {
		return this.spt_basenum;
	}

	public void setSpt_basenum(final String spt_basenum) {
		this.spt_basenum = spt_basenum;
	}

	public String getSpt_dwjcbl() {
		return this.spt_dwjcbl;
	}

	public void setSpt_dwjcbl(final String spt_dwjcbl) {
		this.spt_dwjcbl = spt_dwjcbl;
	}

	public String getSpt_grjcbl() {
		return this.spt_grjcbl;
	}

	public void setSpt_grjcbl(final String spt_grjcbl) {
		this.spt_grjcbl = spt_grjcbl;
	}

	public String getSpt_dwyjce() {
		return this.spt_dwyjce;
	}

	public void setSpt_dwyjce(final String spt_dwyjce) {
		this.spt_dwyjce = spt_dwyjce;
	}

	public String getSpt_gryjce() {
		return this.spt_gryjce;
	}

	public void setSpt_gryjce(final String spt_gryjce) {
		this.spt_gryjce = spt_gryjce;
	}

	public String getSpt_yjce() {
		return this.spt_yjce;
	}

	public void setSpt_yjce(final String spt_yjce) {
		this.spt_yjce = spt_yjce;
	}

	public String getCheckcode() {
		return this.checkcode;
	}

	public void setCheckcode(final String checkcode) {
		this.checkcode = checkcode;
	}

	public String getPayeebankcode() {
		return this.payeebankcode;
	}

	public void setPayeebankcode(final String payeebankcode) {
		this.payeebankcode = payeebankcode;
	}

	public String getPayeebankaccnum() {
		return this.payeebankaccnum;
	}

	public void setPayeebankaccnum(final String payeebankaccnum) {
		this.payeebankaccnum = payeebankaccnum;
	}

	public String getAccnum() {
		return this.accnum;
	}

	public void setAccnum(final String accnum) {
		this.accnum = accnum;
	}

	public String getAccname() {
		return this.accname;
	}

	public void setAccname(final String accname) {
		this.accname = accname;
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

	public String getFrzflag() {
		return this.frzflag;
	}

	public void setFrzflag(final String frzflag) {
		this.frzflag = frzflag;
	}

	public String getDrawreason() {
		return this.drawreason;
	}

	public void setDrawreason(final String drawreason) {
		this.drawreason = drawreason;
	}

	public String getQdapprnum() {
		return this.qdapprnum;
	}

	public void setQdapprnum(final String qdapprnum) {
		this.qdapprnum = qdapprnum;
	}

	public String getUnitaccnum() {
		return this.unitaccnum;
	}

	public void setUnitaccnum(final String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getHandset() {
		return this.handset;
	}

	public void setHandset(final String handset) {
		this.handset = handset;
	}

	public String getInputamt() {
		return this.inputamt;
	}

	public void setInputamt(final String inputamt) {
		this.inputamt = inputamt;
	}



	public String getParam() {
		return this.param;
	}

	public void setParam(final String param) {
		this.param = param;
	}


}
