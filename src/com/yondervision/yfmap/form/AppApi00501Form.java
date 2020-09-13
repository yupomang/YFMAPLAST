package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi00501Form 
* @Description: 提取明细查询请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi00501Form extends AppApiCommonForm {
	/** 页码 */					
	private String pagenum = "";					
	/** 行数  */	
	private String pagerows = "";	
	/** 是否分页*/
	private String ispaging = "";
	/** 开始日期 */					
	private String startdate = "";					
	/** 结束日期 */					
	private String enddate = "";	
	private String year = "";
	/** 借款合同号 */					
	private String loancontractno = "";
	/**
	 * 借款合同号
	 * 【异地贷款】2017-06-12新加
	 */
	private String loancontrnum = "";
	private String xingming="";	
	/**
	 * 发送日期
	 */
	private String sendDate="";
	/**
	 * 发送时间
	 */
	private String sendTime="";
	/**
	 * 贷款账号
	 */
	private String pubaccnum="";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 安全标识
	 */
	private String key="";
	/**
	 * 业务类型
	 */
	private String buzhType="";
	/**
	 * 查询方式
	 */
	private String qtype="";
	/**
	 * 个人账号/联名卡号
	 */
	private String accnum="";
	/**
	 * 证件号码
	 */
	private String certinum="";	
	private String freeuse1="";
	private String freeuse2="";
	private String freeuse3="";
	private String transCode="";
	
	/**
	 * 大连：证件类型
	 */
	private String zjlx = "";
	/**
	 * 证件号码
	 */
	private String zjhm = "";
	/**
	 * 实际代扣起始年份
	 */
	private String sjdkqsnf = "";
	/**
	 * 实际代扣终止年份
	 */
	private String sjdkzznf = "";
	/**
	 * 计划起始年份
	 */
	private String jhqsnf = "";
	/**
	 * 计划终止年份
	 */
	private String jhzznf = "";
	/**
	 * 商贷代扣协议编号
	 */
	private String sddkxybh = "";
	
	/**
	 * 贴息贷款标志
	 */
	private String txdkbz = "";
	
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	
	public String getPagenum() {
		return pagenum;
	}
	public String getPagerows() {
		return pagerows;
	}
	public String getIspaging() {
		return ispaging;
	}
	public String getStartdate() {
		return startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public String getYear() {
		return year;
	}
	public String getLoancontractno() {
		return loancontractno;
	}
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public String getSendDate() {
		return sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public String getPubaccnum() {
		return pubaccnum;
	}
	public String getSendSeqno() {
		return sendSeqno;
	}
	public String getKey() {
		return key;
	}
	public String getBuzhType() {
		return buzhType;
	}
	public String getQtype() {
		return qtype;
	}
	public String getAccnum() {
		return accnum;
	}
	public String getCertinum() {
		return certinum;
	}
	public String getFreeuse1() {
		return freeuse1;
	}
	public String getFreeuse2() {
		return freeuse2;
	}
	public String getFreeuse3() {
		return freeuse3;
	}
	public String getTransCode() {
		return transCode;
	}
	public String getZjlx() {
		return zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public String getSjdkqsnf() {
		return sjdkqsnf;
	}
	public String getSjdkzznf() {
		return sjdkzznf;
	}
	public String getJhqsnf() {
		return jhqsnf;
	}
	public String getJhzznf() {
		return jhzznf;
	}
	public String getSddkxybh() {
		return sddkxybh;
	}
	public String getTxdkbz() {
		return txdkbz;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public void setLoancontractno(String loancontractno) {
		this.loancontractno = loancontractno;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public void setPubaccnum(String pubaccnum) {
		this.pubaccnum = pubaccnum;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setBuzhType(String buzhType) {
		this.buzhType = buzhType;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}
	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}
	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public void setSjdkqsnf(String sjdkqsnf) {
		this.sjdkqsnf = sjdkqsnf;
	}
	public void setSjdkzznf(String sjdkzznf) {
		this.sjdkzznf = sjdkzznf;
	}
	public void setJhqsnf(String jhqsnf) {
		this.jhqsnf = jhqsnf;
	}
	public void setJhzznf(String jhzznf) {
		this.jhzznf = jhzznf;
	}
	public void setSddkxybh(String sddkxybh) {
		this.sddkxybh = sddkxybh;
	}
	public void setTxdkbz(String txdkbz) {
		this.txdkbz = txdkbz;
	}
	
	
	
}
