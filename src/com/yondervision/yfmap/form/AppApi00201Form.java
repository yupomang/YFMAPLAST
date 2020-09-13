package com.yondervision.yfmap.form;

public class AppApi00201Form extends AppApiCommonForm {
	private String beginDate = "";
	private String startDate = "";
	private String endDate = "";
//=====================	株洲新加
	/** 单位账号 */				
	private String unitaccnum = "";
	/** 业务流水号 */				
	private String ywlsh = "";
	/** 记账日期 */				
	private String jzrq = "";
//======================株洲新加	
	//宁波新加
	/** 账户类型 */				
	private String indiacctype = "";
	//宁波新加
	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";				
	/** 开始日期 */				
	private String startdate = "";				
	/** 结束日期 */				
	private String enddate = "";				
					
	private String year = "";
	/** 是否分页标识 */	
	private String ispaging = "";
	/**
	 * 发送日期
	 */
	private String sendDate="";
	/**
	 * 发送时间
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
	 * 业务类型
	 */
	private String buzhType="";
	/**
	 * 查询方式
	 */
	private String type="";
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
	
	private String inaccflag = ""; // 入账标志	
	private String summarycode = ""; // 摘要码	
	private String xingming = ""; // 姓名	
	private String zhlx = "";
	
	public String getZhlx() {
		return zhlx;
	}

	public void setZhlx(String zhlx) {
		this.zhlx = zhlx;
	}

	public String getIndiacctype() {
		return indiacctype;
	}

	public void setIndiacctype(String indiacctype) {
		this.indiacctype = indiacctype;
	}

	public String getUnitaccnum() {
		return unitaccnum;
	}

	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getYwlsh() {
		return ywlsh;
	}

	public void setYwlsh(String ywlsh) {
		this.ywlsh = ywlsh;
	}

	/**				
	 *<pre> 执行获取页码处理 </pre>				
	 * @return pagenum 页码				
	 */				
	public String getPagenum() {				
	    return pagenum;				
	}				
					
	/**				
	 *<pre> 执行设定页码处理 </pre>				
	 * @param pagenum 页码				
	 */				
	public void setPagenum(String pagenum) {				
	    this.pagenum = pagenum;				
	}				
					
	/**				
	 *<pre> 执行获取行数处理 </pre>				
	 * @return pagerows 行数				
	 */				
	public String getPagerows() {				
	    return pagerows;				
	}				
					
	/**				
	 *<pre> 执行设定行数处理 </pre>				
	 * @param pagerows 行数				
	 */				
	public void setPagerows(String pagerows) {				
	    this.pagerows = pagerows;				
	}				
					
	/**				
	 *<pre> 执行获取开始日期处理 </pre>				
	 * @return startdate 开始日期				
	 */				
	public String getStartdate() {				
	    return startdate;				
	}				
					
	/**				
	 *<pre> 执行设定开始日期处理 </pre>				
	 * @param startdate 开始日期				
	 */				
	public void setStartdate(String startdate) {				
	    this.startdate = startdate;				
	}				
					
	/**				
	 *<pre> 执行获取结束日期处理 </pre>				
	 * @return enddate 结束日期				
	 */				
	public String getEnddate() {				
	    return enddate;				
	}				
					
	/**				
	 *<pre> 执行设定结束日期处理 </pre>				
	 * @param enddate 结束日期				
	 */				
	public void setEnddate(String enddate) {				
	    this.enddate = enddate;				
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

	public String getBuzhType() {
		return buzhType;
	}

	public void setBuzhType(String buzhType) {
		this.buzhType = buzhType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccnum() {
		return accnum;
	}

	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}

	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getFreeuse1() {
		return freeuse1;
	}

	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}

	public String getFreeuse2() {
		return freeuse2;
	}

	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}

	public String getFreeuse3() {
		return freeuse3;
	}

	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getIspaging() {
		return ispaging;
	}

	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}

	public String getInaccflag() {
		return inaccflag;
	}

	public void setInaccflag(String inaccflag) {
		this.inaccflag = inaccflag;
	}

	public String getSummarycode() {
		return summarycode;
	}

	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}

	public String getXingming() {
		return xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public String getJzrq() {
		return jzrq;
	}

	public void setJzrq(String jzrq) {
		this.jzrq = jzrq;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	
}
