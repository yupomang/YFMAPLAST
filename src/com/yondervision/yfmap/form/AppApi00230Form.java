package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00230Form
* @Description: 个人开户
* @author luolin
* @date 2019-12-19
*
*/
public class AppApi00230Form extends AppApiCommonForm{
	/*16	spt_ywbm, 业务事项编码, 入口字段
80	email, 电子邮箱, 入口字段
20	spt_xm, 姓名, 入口字段	(必填)
120	spt_fkrxm, 付款人姓名（缴存划扣户名）, 入口字段(必填)
255 spt_fkyh, 付款行（缴存划扣银行）, 入口字段(必填)
30	spt_fkyhbh, 合作银行编号, 入口字段(必填)
30	spt_fkzh, 付款账号（缴存划扣帐号）, 入口字段(必填)
4	spt_gtlx, 个体类型, 入口字段（02---(自由职业者)）(必填)
255	spt_hjszd, 户籍所在地, 入口字段(必填)
14	spt_jgh, 机构号, 入口字段(必填)
255	spt_jtdz, 家庭地址, 入口字段(必填)
4	spt_qdmc, 渠道名称编码, 入口字段
18	spt_sfzh, 身份证号码, 入口字段 (必填)
255	spt_yzbm, 邮政编码, 入口字段(必填)
11	spt_sjhm, 手机号码, 入口字段(必填)
4	spt_sqrlx, 申请人类型, 入口字段
19	spt_sqsj, 申请时间, 入口字段
10	spt_sqyjce, 申请公积金月缴存额, 入口字段(必填)
21	spt_ywbh, 业务申报号, 入口字段
64	qdapprnum, 业务申请唯一标识码, 双向
spt_jkbm     借款编码
spt_bglx  公积金贷款合同变更类型
spt_ywid  业务申请唯一标识码
apprnum      贷款受理编号
spt_fjmx     申请附件
spt_hkyh     还款银行代码
spt_hkzh     还款账号
spt_pageno   分页页码
spt_pagesize 分页大小
spt_userid   政务网用户ID

spt_fwxx房屋信息
spt_sqxx申请信息
spt_sqrxx申请人信息
spt_gtgxrxx共同关系人信息
spt_jkxx借款信息
spt_poxx配偶信息
 */
	private String apprnum;
	private String spt_fjmx;
	private String spt_hkyh;
	private String spt_hkzh;
	private String spt_pageno;//分页页码
	private String spt_pagesize;//分页大小
	private String spt_userid;
	private String spt_bglx;
	private String spt_jkbm;

	private String spt_fwxx;
	private String spt_sqxx;
	private String spt_sqrxx;
	private String spt_gtgxrxx;
	private String spt_jkxx;
	private String spt_poxx;


	public String getSpt_fwxx() {
		return this.spt_fwxx;
	}

	public void setSpt_fwxx(final String spt_fwxx) {
		this.spt_fwxx = spt_fwxx;
	}

	public String getSpt_sqxx() {
		return this.spt_sqxx;
	}

	public void setSpt_sqxx(final String spt_sqxx) {
		this.spt_sqxx = spt_sqxx;
	}

	public String getSpt_sqrxx() {
		return this.spt_sqrxx;
	}

	public void setSpt_sqrxx(final String spt_sqrxx) {
		this.spt_sqrxx = spt_sqrxx;
	}

	public String getSpt_gtgxrxx() {
		return this.spt_gtgxrxx;
	}

	public void setSpt_gtgxrxx(final String spt_gtgxrxx) {
		this.spt_gtgxrxx = spt_gtgxrxx;
	}

	public String getSpt_jkxx() {
		return this.spt_jkxx;
	}

	public void setSpt_jkxx(final String spt_jkxx) {
		this.spt_jkxx = spt_jkxx;
	}

	public String getSpt_poxx() {
		return this.spt_poxx;
	}

	public void setSpt_poxx(final String spt_poxx) {
		this.spt_poxx = spt_poxx;
	}

	public String getSpt_bglx() {
		return this.spt_bglx;
	}

	public void setSpt_bglx(final String spt_bglx) {
		this.spt_bglx = spt_bglx;
	}

	public String getSpt_jkbm() {
		return this.spt_jkbm;
	}

	public void setSpt_jkbm(final String spt_jkbm) {
		this.spt_jkbm = spt_jkbm;
	}

	public String getApprnum() {
		return this.apprnum;
	}

	public void setApprnum(final String apprnum) {
		this.apprnum = apprnum;
	}

	public String getSpt_fjmx() {
		return this.spt_fjmx;
	}

	public void setSpt_fjmx(final String spt_fjmx) {
		this.spt_fjmx = spt_fjmx;
	}

	public String getSpt_hkyh() {
		return this.spt_hkyh;
	}

	public void setSpt_hkyh(final String spt_hkyh) {
		this.spt_hkyh = spt_hkyh;
	}

	public String getSpt_hkzh() {
		return this.spt_hkzh;
	}

	public void setSpt_hkzh(final String spt_hkzh) {
		this.spt_hkzh = spt_hkzh;
	}

	public String getSpt_pageno() {
		return this.spt_pageno;
	}

	public void setSpt_pageno(final String spt_pageno) {
		this.spt_pageno = spt_pageno;
	}

	public String getSpt_pagesize() {
		return this.spt_pagesize;
	}

	public void setSpt_pagesize(final String spt_pagesize) {
		this.spt_pagesize = spt_pagesize;
	}

	public String getSpt_userid() {
		return this.spt_userid;
	}

	public void setSpt_userid(final String spt_userid) {
		this.spt_userid = spt_userid;
	}

	private String spt_ywbm;//业务事项编码
	private String spt_ywbh;//业务申报号
	private String qdapprnum;
	private String email;
	private String spt_xm;//姓名
	private String spt_fkrxm;
	private String spt_fkyh;
	private String spt_fkyhbh;
	private String spt_fkzh;
	private String spt_gtlx;
	private String spt_hjszd;
	private String spt_jgh;//机构号
	private String spt_jtdz;
	private String spt_qdmc;
	private String spt_sfzh;//身份证号码
	private String spt_yzbm;
	private String spt_sjhm;//手机号码
	private String spt_sqrlx;//申请人类型
	private String spt_sqsj;//申请时间
	private String spt_sqyjce;

	public String getSpt_ywbm() {
		return this.spt_ywbm;
	}

	public void setSpt_ywbm(final String spt_ywbm) {
		this.spt_ywbm = spt_ywbm;
	}

	public String getSpt_ywbh() {
		return this.spt_ywbh;
	}

	public void setSpt_ywbh(final String spt_ywbh) {
		this.spt_ywbh = spt_ywbh;
	}

	public String getQdapprnum() {
		return this.qdapprnum;
	}

	public void setQdapprnum(final String qdapprnum) {
		this.qdapprnum = qdapprnum;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getSpt_xm() {
		return this.spt_xm;
	}

	public void setSpt_xm(final String spt_xm) {
		this.spt_xm = spt_xm;
	}

	public String getSpt_fkrxm() {
		return this.spt_fkrxm;
	}

	public void setSpt_fkrxm(final String spt_fkrxm) {
		this.spt_fkrxm = spt_fkrxm;
	}

	public String getSpt_fkyh() {
		return this.spt_fkyh;
	}

	public void setSpt_fkyh(final String spt_fkyh) {
		this.spt_fkyh = spt_fkyh;
	}

	public String getSpt_fkyhbh() {
		return this.spt_fkyhbh;
	}

	public void setSpt_fkyhbh(final String spt_fkyhbh) {
		this.spt_fkyhbh = spt_fkyhbh;
	}

	public String getSpt_fkzh() {
		return this.spt_fkzh;
	}

	public void setSpt_fkzh(final String spt_fkzh) {
		this.spt_fkzh = spt_fkzh;
	}

	public String getSpt_gtlx() {
		return this.spt_gtlx;
	}

	public void setSpt_gtlx(final String spt_gtlx) {
		this.spt_gtlx = spt_gtlx;
	}

	public String getSpt_hjszd() {
		return this.spt_hjszd;
	}

	public void setSpt_hjszd(final String spt_hjszd) {
		this.spt_hjszd = spt_hjszd;
	}

	public String getSpt_jgh() {
		return this.spt_jgh;
	}

	public void setSpt_jgh(final String spt_jgh) {
		this.spt_jgh = spt_jgh;
	}

	public String getSpt_jtdz() {
		return this.spt_jtdz;
	}

	public void setSpt_jtdz(final String spt_jtdz) {
		this.spt_jtdz = spt_jtdz;
	}

	public String getSpt_qdmc() {
		return this.spt_qdmc;
	}

	public void setSpt_qdmc(final String spt_qdmc) {
		this.spt_qdmc = spt_qdmc;
	}

	public String getSpt_sfzh() {
		return this.spt_sfzh;
	}

	public void setSpt_sfzh(final String spt_sfzh) {
		this.spt_sfzh = spt_sfzh;
	}

	public String getSpt_yzbm() {
		return this.spt_yzbm;
	}

	public void setSpt_yzbm(final String spt_yzbm) {
		this.spt_yzbm = spt_yzbm;
	}

	public String getSpt_sjhm() {
		return this.spt_sjhm;
	}

	public void setSpt_sjhm(final String spt_sjhm) {
		this.spt_sjhm = spt_sjhm;
	}

	public String getSpt_sqrlx() {
		return this.spt_sqrlx;
	}

	public void setSpt_sqrlx(final String spt_sqrlx) {
		this.spt_sqrlx = spt_sqrlx;
	}

	public String getSpt_sqsj() {
		return this.spt_sqsj;
	}

	public void setSpt_sqsj(final String spt_sqsj) {
		this.spt_sqsj = spt_sqsj;
	}

	public String getSpt_sqyjce() {
		return this.spt_sqyjce;
	}

	public void setSpt_sqyjce(final String spt_sqyjce) {
		this.spt_sqyjce = spt_sqyjce;
	}

	private String spt_hkfs;//还款方式编码
	private String spt_jkhtbh;//借款合同编号
	private String qdmc;//渠道名称编码
	private String spt_tqhkbj;//申请提前还贷本金
	private String spt_ywid;//业务申请唯一标识码
	private String spt_bfhkfs;//部分还款方式编码
	private String spt_gjjzhhkje;//使用公积金账户还款金额
	private String spt_mlbm;//目录（事项）编码
	private String spt_mljbm;//目录（事项）基本码
	private String spt_mlmc;//目录（事项）名称
	private String spt_qzlqfs;//权证领取方式
	private String spt_sfsygjjzh;//是否优先使用公积金账户还款
	private String spt_sjrxm;//收件人姓名
	private String spt_tqhkje;//申请提前还贷金额
	private String spt_tqhklx;//申请提前还贷利息
	private String spt_zwfwbb;//政务服务版本
	private String spt_sjrdz;//收件人详细地址
	private String spt_sjrlxfs;//收件人联系方式

	public String getSpt_hkfs() {
		return this.spt_hkfs;
	}

	public void setSpt_hkfs(final String spt_hkfs) {
		this.spt_hkfs = spt_hkfs;
	}

	public String getSpt_jkhtbh() {
		return this.spt_jkhtbh;
	}

	public void setSpt_jkhtbh(final String spt_jkhtbh) {
		this.spt_jkhtbh = spt_jkhtbh;
	}

	public String getQdmc() {
		return this.qdmc;
	}

	public void setQdmc(final String qdmc) {
		this.qdmc = qdmc;
	}

	public String getSpt_tqhkbj() {
		return this.spt_tqhkbj;
	}

	public void setSpt_tqhkbj(final String spt_tqhkbj) {
		this.spt_tqhkbj = spt_tqhkbj;
	}

	public String getSpt_ywid() {
		return this.spt_ywid;
	}

	public void setSpt_ywid(final String spt_ywid) {
		this.spt_ywid = spt_ywid;
	}

	public String getSpt_bfhkfs() {
		return this.spt_bfhkfs;
	}

	public void setSpt_bfhkfs(final String spt_bfhkfs) {
		this.spt_bfhkfs = spt_bfhkfs;
	}

	public String getSpt_gjjzhhkje() {
		return this.spt_gjjzhhkje;
	}

	public void setSpt_gjjzhhkje(final String spt_gjjzhhkje) {
		this.spt_gjjzhhkje = spt_gjjzhhkje;
	}

	public String getSpt_mlbm() {
		return this.spt_mlbm;
	}

	public void setSpt_mlbm(final String spt_mlbm) {
		this.spt_mlbm = spt_mlbm;
	}

	public String getSpt_mljbm() {
		return this.spt_mljbm;
	}

	public void setSpt_mljbm(final String spt_mljbm) {
		this.spt_mljbm = spt_mljbm;
	}

	public String getSpt_mlmc() {
		return this.spt_mlmc;
	}

	public void setSpt_mlmc(final String spt_mlmc) {
		this.spt_mlmc = spt_mlmc;
	}

	public String getSpt_qzlqfs() {
		return this.spt_qzlqfs;
	}

	public void setSpt_qzlqfs(final String spt_qzlqfs) {
		this.spt_qzlqfs = spt_qzlqfs;
	}

	public String getSpt_sfsygjjzh() {
		return this.spt_sfsygjjzh;
	}

	public void setSpt_sfsygjjzh(final String spt_sfsygjjzh) {
		this.spt_sfsygjjzh = spt_sfsygjjzh;
	}

	public String getSpt_sjrxm() {
		return this.spt_sjrxm;
	}

	public void setSpt_sjrxm(final String spt_sjrxm) {
		this.spt_sjrxm = spt_sjrxm;
	}

	public String getSpt_tqhkje() {
		return this.spt_tqhkje;
	}

	public void setSpt_tqhkje(final String spt_tqhkje) {
		this.spt_tqhkje = spt_tqhkje;
	}

	public String getSpt_tqhklx() {
		return this.spt_tqhklx;
	}

	public void setSpt_tqhklx(final String spt_tqhklx) {
		this.spt_tqhklx = spt_tqhklx;
	}

	public String getSpt_zwfwbb() {
		return this.spt_zwfwbb;
	}

	public void setSpt_zwfwbb(final String spt_zwfwbb) {
		this.spt_zwfwbb = spt_zwfwbb;
	}

	public String getSpt_sjrdz() {
		return this.spt_sjrdz;
	}

	public void setSpt_sjrdz(final String spt_sjrdz) {
		this.spt_sjrdz = spt_sjrdz;
	}

	public String getSpt_sjrlxfs() {
		return this.spt_sjrlxfs;
	}

	public void setSpt_sjrlxfs(final String spt_sjrlxfs) {
		this.spt_sjrlxfs = spt_sjrlxfs;
	}
}