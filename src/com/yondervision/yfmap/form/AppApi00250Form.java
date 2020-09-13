package com.yondervision.yfmap.form;


/**
* @ClassName: AppApi00250Form
* @Description: 长三角“一网通办” 异地缴存证明
* @author luolin
* @date 2020-04-08
*
*/
public class AppApi00250Form{
	private String jgh;
	private String businName;
	private String ZJHM;
	private String channel;

	public String getJgh() {
		return this.jgh;
	}

	public void setJgh(final String jgh) {
		this.jgh = jgh;
	}

	public String getBusinName() {
		return this.businName;
	}

	public void setBusinName(final String businName) {
		this.businName = businName;
	}

	public String getZJHM() {
		return this.ZJHM;
	}

	public void setZJHM(final String ZJHM) {
		this.ZJHM = ZJHM;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(final String channel) {
		this.channel = channel;
	}

	/*
	5.1.[YD02-2]发起查询缴存证明信息接口
    字段代码 字段说明 字段类型 是否必填 备注说明
    zjlx 证件类型 string 是 111 身份证 001 其他证件类型
    zjhm 证件号码 string 是
    xingMing 姓名 string 是
    cxlx 查询类型 string 是 默认0
    0、需要信息和文件
    1、仅信息不需要文件
    2、仅文件不需要信息

    推送缴存证明使用状态接口
    字段代码 字段说明 字段类型 是否必填 备注说明
	zjlx 证件类型 string 是 111 身份证 001 其他证件类型
	zjhm 证件号码 string 是
	xingMing 姓名 string 是
	ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
	zmbh 证明编号 string 是 异地贷款证明编号
	sfyd 是否已读 string 否 0=已读  1.未读 ；默认已读，未读用于特定场景。

推送缴存证明注销审核结果接口
	字段代码 字段说明 字段类型 是否必填 备注说明
ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是
xingMing 姓名 string 是
yddkzmmh 异地贷款证明编号 string 是
sign 加密签名 string 是 签名字段（对入参的所有字段进行MD5加密签名，加密算法见4.8）
zxzt 注销审核结果 string 是 1.注销成功 2.注销失败
zxjgxx 注销结果信息 string 否 如果注销失败，填写对应的原因。

推送贷款回执信息接口
字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是 　
xingMing 姓名 string 是 　
ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zmbh 证明编号 string 是 缴存地开具的证明编号
grzh 公积金账号 string 是 　
cdgx 参贷关系 string 是 本人   参贷
dkshjg 贷款审核结果 string 是 准予贷款  ，不予贷款
dkje 贷款金额 number 是 　
dkqx 贷款期限 number 是 年
dkkssj 贷款开始时间 string 是 yyyyMMdd
dkjssj 贷款结束时间 string 是 yyyyMMdd
hkfs 还款方式 string 是 等额本金、等额本息、其他
yhbx 月还本息 string 是 元
dwjbr 单位经办人 string 是 　
lxdh 联系电话 string 是 　
hzsj 回执时间 string 是 yyyyMMdd　
dkzh 贷款账号 string 否 　

推送贷款结清信息接口
字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是 　
xingMing 姓名 string 是 　
ywbh 平台唯一业务编号 string 否 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zmbh 证明编号 string 是 异地贷款证明编号
dkzh 贷款账号 string 是 　
jqrq 结清日期 string 是 yyyyMMdd　
    */
	private String zjlx;
	private String zjhm;
	private String xingMing;
	private String cxlx;
	private String ywbh;
	private String zmbh;
	private String sfyd;
	private String yddkzmmh;
	private String sign;
	private String zxzt;
	private String zxjgxx;

	private String grzh;
	private String cdgx;
	private String dkshjg;
	private String dkje;
	private String dkqx;
	private String dkkssj;
	private String dkjssj;
	private String hkfs;
	private String yhbx;
	private String dwjbr;
	private String lxdh;
	private String hzsj;
	private String dkzh;

	private String jqrq;

	public String getJqrq() {
		return this.jqrq;
	}

	public void setJqrq(final String jqrq) {
		this.jqrq = jqrq;
	}

	public String getGrzh() {
		return this.grzh;
	}

	public void setGrzh(final String grzh) {
		this.grzh = grzh;
	}

	public String getCdgx() {
		return this.cdgx;
	}

	public void setCdgx(final String cdgx) {
		this.cdgx = cdgx;
	}

	public String getDkshjg() {
		return this.dkshjg;
	}

	public void setDkshjg(final String dkshjg) {
		this.dkshjg = dkshjg;
	}

	public String getDkje() {
		return this.dkje;
	}

	public void setDkje(final String dkje) {
		this.dkje = dkje;
	}

	public String getDkqx() {
		return this.dkqx;
	}

	public void setDkqx(final String dkqx) {
		this.dkqx = dkqx;
	}

	public String getDkkssj() {
		return this.dkkssj;
	}

	public void setDkkssj(final String dkkssj) {
		this.dkkssj = dkkssj;
	}

	public String getDkjssj() {
		return this.dkjssj;
	}

	public void setDkjssj(final String dkjssj) {
		this.dkjssj = dkjssj;
	}

	public String getHkfs() {
		return this.hkfs;
	}

	public void setHkfs(final String hkfs) {
		this.hkfs = hkfs;
	}

	public String getYhbx() {
		return this.yhbx;
	}

	public void setYhbx(final String yhbx) {
		this.yhbx = yhbx;
	}

	public String getDwjbr() {
		return this.dwjbr;
	}

	public void setDwjbr(final String dwjbr) {
		this.dwjbr = dwjbr;
	}

	public String getLxdh() {
		return this.lxdh;
	}

	public void setLxdh(final String lxdh) {
		this.lxdh = lxdh;
	}

	public String getHzsj() {
		return this.hzsj;
	}

	public void setHzsj(final String hzsj) {
		this.hzsj = hzsj;
	}

	public String getDkzh() {
		return this.dkzh;
	}

	public void setDkzh(final String dkzh) {
		this.dkzh = dkzh;
	}

	public String getYddkzmmh() {
		return this.yddkzmmh;
	}

	public void setYddkzmmh(final String yddkzmmh) {
		this.yddkzmmh = yddkzmmh;
	}

	public String getSign() {
		return this.sign;
	}

	public void setSign(final String sign) {
		this.sign = sign;
	}

	public String getZxzt() {
		return this.zxzt;
	}

	public void setZxzt(final String zxzt) {
		this.zxzt = zxzt;
	}

	public String getZxjgxx() {
		return this.zxjgxx;
	}

	public void setZxjgxx(final String zxjgxx) {
		this.zxjgxx = zxjgxx;
	}

	public String getYwbh() {
		return this.ywbh;
	}

	public void setYwbh(final String ywbh) {
		this.ywbh = ywbh;
	}

	public String getZmbh() {
		return this.zmbh;
	}

	public void setZmbh(final String zmbh) {
		this.zmbh = zmbh;
	}

	public String getSfyd() {
		return this.sfyd;
	}

	public void setSfyd(final String sfyd) {
		this.sfyd = sfyd;
	}

	public String getZjlx() {
		return this.zjlx;
	}

	public void setZjlx(final String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZjhm() {
		return this.zjhm;
	}

	public void setZjhm(final String zjhm) {
		this.zjhm = zjhm;
	}

	public String getXingMing() {
		return this.xingMing;
	}

	public void setXingMing(final String xingMing) {
		this.xingMing = xingMing;
	}

	public String getCxlx() {
		return this.cxlx;
	}

	public void setCxlx(final String cxlx) {
		this.cxlx = cxlx;
	}
}
