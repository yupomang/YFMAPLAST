package com.yondervision.yfmap.result.haikou;

/**
 * 业务办理    销户提取 返现信息查询
 * @author ljd
 *
 */
public class AppApi01025Result {

	private String counts="";
	private String isloanflag=""; //是否有贷款
	private String intamt="";
	private String frzflag="";//冻结标志
	private String grzh = ""; // 个人账号
	private String drawreason = ""; // 提取原因的值
	private String drawreason_code = ""; // 提取原因的值
	private String drawreasoncode1 = ""; // 依据号的值，依据号drawreasoncode1
	private String indiaccstate = ""; // 个人账户状态的值；
	private String afterindiaccstate = ""; // 提取后个人账户状态
	private String xingming = ""; // 姓名的值；
	private String zjlx = ""; // 证件类型的值；
	private String zjhm = ""; // 证件号码；
	private String lpaym = ""; // 缴至年月；
	private String grzhye = ""; // 个人账户余额的值；
	private String dwzh = ""; // 单位账号的值。
	private String dwmc = ""; // 单位名称
	private String clsaccint="";//销户利息
	private String kyye ="";//可用余额
	private String tqjehj = ""; //提取金额合计
	private String jklx = ""; //接口类型
	private String loanaccnum;
	private String buyhousename = ""; //购房人姓名
	private String owncertinum = ""; //所有权人证件号码
	private String owncertitype = ""; //所有权人证件类型
	private String owncertitype_code = ""; //所有权人证件类型
	private String matename = ""; //配偶姓名
	private String matecertinum = ""; //配偶证件号码
	private String matecertitype = ""; //配偶证件类型
	private String matecertitype_code = ""; //配偶证件类型
	private String linkrow;
	private String loansum;
	private String loanterm;
	private String monthrepayamt;
	private String loandate;
	private String repaymode;
	private String houseaddr;
	private String repaymonths;
	private String sjhm;
	
	
	/**
	 * 交易代码
	 */
	private String transCode = "";
	/**
	 * 返回日期
	 */
	private String sendDate = "";
	/**
	 * 返回时间
	 */
	private String sendTime = "";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno = "";
	/**
	 * 安全标识
	 */
	private String key = "";
	/**
	 * 中心流水号
	 */
	private String centerSeqno = "";
	/**
	 * 响应码
	 */
	private String recode = "";
	/**
	 * 响应信息
	 */
	private String msg = "";
	
	
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getDrawreason() {
		return drawreason;
	}
	public void setDrawreason(String drawreason) {
		this.drawreason = drawreason;
	}
	public String getIndiaccstate() {
		return indiaccstate;
	}
	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getGrzhye() {
		return grzhye;
	}
	public void setGrzhye(String grzhye) {
		this.grzhye = grzhye;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getIntamt() {
		return intamt;
	}
	public void setIntamt(String intamt) {
		this.intamt = intamt;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCenterSeqno() {
		return centerSeqno;
	}
	public void setCenterSeqno(String centerSeqno) {
		this.centerSeqno = centerSeqno;
	}
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFrzflag() {
		return frzflag;
	}
	public void setFrzflag(String frzflag) {
		this.frzflag = frzflag;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getLpaym() {
		return lpaym;
	}
	public void setLpaym(String lpaym) {
		this.lpaym = lpaym;
	}
	public String getClsaccint() {
		return clsaccint;
	}
	public void setClsaccint(String clsaccint) {
		this.clsaccint = clsaccint;
	}
	public String getKyye() {
		return kyye;
	}
	public void setKyye(String kyye) {
		this.kyye = kyye;
	}
	public String getTqjehj() {
		return tqjehj;
	}
	public void setTqjehj(String tqjehj) {
		this.tqjehj = tqjehj;
	}
	public String getAfterindiaccstate() {
		return afterindiaccstate;
	}
	public void setAfterindiaccstate(String afterindiaccstate) {
		this.afterindiaccstate = afterindiaccstate;
	}
	public String getJklx() {
		return jklx;
	}
	public void setJklx(String jklx) {
		this.jklx = jklx;
	}
	public String getBuyhousename() {
		return buyhousename;
	}
	public void setBuyhousename(String buyhousename) {
		this.buyhousename = buyhousename;
	}
	public String getOwncertinum() {
		return owncertinum;
	}
	public void setOwncertinum(String owncertinum) {
		this.owncertinum = owncertinum;
	}
	public String getOwncertitype() {
		return owncertitype;
	}
	public void setOwncertitype(String owncertitype) {
		this.owncertitype = owncertitype;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatecertitype() {
		return matecertitype;
	}
	public void setMatecertitype(String matecertitype) {
		this.matecertitype = matecertitype;
	}
	public String getLinkrow() {
		return linkrow;
	}
	public void setLinkrow(String linkrow) {
		this.linkrow = linkrow;
	}
	public String getLoanaccnum() {
		return loanaccnum;
	}
	public void setLoanaccnum(String loanaccnum) {
		this.loanaccnum = loanaccnum;
	}
	public String getLoansum() {
		return loansum;
	}
	public void setLoansum(String loansum) {
		this.loansum = loansum;
	}
	public String getLoanterm() {
		return loanterm;
	}
	public void setLoanterm(String loanterm) {
		this.loanterm = loanterm;
	}
	public String getMonthrepayamt() {
		return monthrepayamt;
	}
	public void setMonthrepayamt(String monthrepayamt) {
		this.monthrepayamt = monthrepayamt;
	}
	public String getLoandate() {
		return loandate;
	}
	public void setLoandate(String loandate) {
		this.loandate = loandate;
	}
	public String getRepaymode() {
		return repaymode;
	}
	public void setRepaymode(String repaymode) {
		this.repaymode = repaymode;
	}
	public String getHouseaddr() {
		return houseaddr;
	}
	public void setHouseaddr(String houseaddr) {
		this.houseaddr = houseaddr;
	}
	public String getRepaymonths() {
		return repaymonths;
	}
	public void setRepaymonths(String repaymonths) {
		this.repaymonths = repaymonths;
	}
	public String getDrawreason_code() {
		return drawreason_code;
	}
	public void setDrawreason_code(String drawreason_code) {
		this.drawreason_code = drawreason_code;
	}
	public String getOwncertitype_code() {
		return owncertitype_code;
	}
	public void setOwncertitype_code(String owncertitype_code) {
		this.owncertitype_code = owncertitype_code;
	}
	public String getMatecertitype_code() {
		return matecertitype_code;
	}
	public void setMatecertitype_code(String matecertitype_code) {
		this.matecertitype_code = matecertitype_code;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getDrawreasoncode1() {
		return drawreasoncode1;
	}
	public void setDrawreasoncode1(String drawreasoncode1) {
		this.drawreasoncode1 = drawreasoncode1;
	}
	
}
