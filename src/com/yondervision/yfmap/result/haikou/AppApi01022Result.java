package com.yondervision.yfmap.result.haikou;

public class AppApi01022Result{
	
	private String afbal="";	// <afbal>提取后余额</afbal>
	private String apprnum="";	// <apprnum>审批号</apprnum>
	private String prin="";	// <prin>本金</prin>
	private String firstflag="";	// <firstflag>提取依据是否首次办理</firstflag>
	private String increbal="";	// <increbal>增量余额</increbal>
	private String increintaccu="";	// <increintaccu>增量利息积数</increintaccu>
	private String indipaymap="";	// <indipaymap>个人汇缴位图</indipaymap>
	private String inputamt="";	// <inputamt>初审金额</inputamt>
	private String keepbal="";	// <keepbal>存量余额</keepbal>
	private String keepintaccu="";	// <keepintaccu>存量利息积数</keepintaccu>
	private String lasttransdate="";	// <lasttransdate>上笔交易日期</lasttransdate>
	private String monpaysum="";	// <monpaysum>月应汇缴额</monpaysum>
	private String oprole="";	// <oprole>柜员角色</oprole>
	private String procode="";	// <procode>提取依据编码</procode>
	private String procode2="";	// <procode2>提取原因的编号2</procode2>
	private String prodcode="";	// <prodcode>产品代码</prodcode>
	private String prominfo="";	//  <prominfo>提示信息</prominfo>
	private String drawamt="";	//  <drawamt>提取金额</drawamt>
	private String repaymonths="";	// <repaymonths>还款月数</repaymonths>
	private String seqno="";	// <seqno>序号</seqno>
	private String useflag="";	//  <useflag>使用标志</useflag>
	private String calintamt="";	// <calintamt>结息金额</calintamt>
	private String ctlflag="";	//  <ctlflag>控制标志</ctlflag>

	/**
	 * 交易代码
	 */
	private String transCode="";
	/**
	 * 返回日期
	 */
	private String sendDate="";
	/**
	 * 返回时间
	 */
	private String sendTime="";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 安全标识
	 */
	private String key="";
	/**
	 * 中心流水号
	 */
	private String centerSeqno="";
	/**
	 * 响应码
	 */
	private String recode="";
	
	private String isloanflag = "";
	private String accinstname = "";
	/**
	 * 响应信息
	 */
	private String msg="";
	
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
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getAccinstname() {
		return accinstname;
	}
	public void setAccinstname(String accinstname) {
		this.accinstname = accinstname;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDrawamt() {
		return drawamt;
	}
	public void setDrawamt(String drawamt) {
		this.drawamt = drawamt;
	}
	public String getRepaymonths() {
		return repaymonths;
	}
	public void setRepaymonths(String repaymonths) {
		this.repaymonths = repaymonths;
	}
	public String getAfbal() {
		return afbal;
	}
	public void setAfbal(String afbal) {
		this.afbal = afbal;
	}
	public String getApprnum() {
		return apprnum;
	}
	public void setApprnum(String apprnum) {
		this.apprnum = apprnum;
	}
	public String getPrin() {
		return prin;
	}
	public void setPrin(String prin) {
		this.prin = prin;
	}
	public String getFirstflag() {
		return firstflag;
	}
	public void setFirstflag(String firstflag) {
		this.firstflag = firstflag;
	}
	public String getIncrebal() {
		return increbal;
	}
	public void setIncrebal(String increbal) {
		this.increbal = increbal;
	}
	public String getIncreintaccu() {
		return increintaccu;
	}
	public void setIncreintaccu(String increintaccu) {
		this.increintaccu = increintaccu;
	}
	public String getIndipaymap() {
		return indipaymap;
	}
	public void setIndipaymap(String indipaymap) {
		this.indipaymap = indipaymap;
	}
	public String getInputamt() {
		return inputamt;
	}
	public void setInputamt(String inputamt) {
		this.inputamt = inputamt;
	}
	public String getKeepbal() {
		return keepbal;
	}
	public void setKeepbal(String keepbal) {
		this.keepbal = keepbal;
	}
	public String getKeepintaccu() {
		return keepintaccu;
	}
	public void setKeepintaccu(String keepintaccu) {
		this.keepintaccu = keepintaccu;
	}
	public String getLasttransdate() {
		return lasttransdate;
	}
	public void setLasttransdate(String lasttransdate) {
		this.lasttransdate = lasttransdate;
	}
	public String getMonpaysum() {
		return monpaysum;
	}
	public void setMonpaysum(String monpaysum) {
		this.monpaysum = monpaysum;
	}
	public String getOprole() {
		return oprole;
	}
	public void setOprole(String oprole) {
		this.oprole = oprole;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getProcode2() {
		return procode2;
	}
	public void setProcode2(String procode2) {
		this.procode2 = procode2;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getProminfo() {
		return prominfo;
	}
	public void setProminfo(String prominfo) {
		this.prominfo = prominfo;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	public String getCalintamt() {
		return calintamt;
	}
	public void setCalintamt(String calintamt) {
		this.calintamt = calintamt;
	}
	public String getCtlflag() {
		return ctlflag;
	}
	public void setCtlflag(String ctlflag) {
		this.ctlflag = ctlflag;
	}
}
