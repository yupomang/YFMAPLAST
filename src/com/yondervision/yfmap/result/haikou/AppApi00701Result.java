package com.yondervision.yfmap.result.haikou;

/** 
* @ClassName: AppApi00701Result 
* @Description: 账户余额明细返回报文封装类
* @author Caozhongyan
* @date Nov 8, 2013 8:58:21 AM   
* 
*/ 
public class AppApi00701Result{
	/**
	 * 期好
	 */
	private String dqqc="";
	/**
	 * 交易日期
	 */
	private String transdate="";
	/**
	 * 还款本金
	 */
	private String hkbj="";
	/**
	 * 还款利息
	 */
	private String hklx="";
	/**
	 * 还款罚息
	 */
	private String hkfx="";
	/**
	 * 还款方式
	 */
	private String summarycode="";
	/**
	 * 金额
	 */
	private String transamt="";
	/**
	 * 还款类别
	 */
	private String loantype="";

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
	/**
	 * 响应信息
	 */
	private String msg="";
	/**
	 * 文件名
	 */
	private String filename="";
	/**
	 * 总笔数
	 */
	private String count="";
	/**
	 * 下页标识
	 */
	private String nextpage="";
	/**
	 * 当前页数
	 */
	private String thisys = "";
	/**
	 * 当前记录数
	 */
	private String thisnum = "";
	/**
	 * 总页数
	 */
	private String sumys = "";
	/**
	 * 总记录数
	 */
	private String sumnum = "";
	/**
	 * 备注1
	 */
	private String freeuse1="";
	/**
	 * 备注2
	 */
	private String freeuse2="";
	/**
	 * 备注3
	 */
	private String freeuse3="";
	
	private String startdate = "";
	private String enddate = "";
	
	private String isloanflag = "";
	private String accinstname = "";
	
	
	public String getDqqc() {
		return dqqc;
	}
	public void setDqqc(String dqqc) {
		this.dqqc = dqqc;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getSummarycode() {
		return summarycode;
	}
	public void setSummarycode(String summarycode) {
		this.summarycode = summarycode;
	}
	public String getTransamt() {
		return transamt;
	}
	public void setTransamt(String transamt) {
		this.transamt = transamt;
	}
	public String getLoantype() {
		return loantype;
	}
	public void setLoantype(String loantype) {
		this.loantype = loantype;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getNextpage() {
		return nextpage;
	}
	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
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
	public String getThisys() {
		return thisys;
	}
	public void setThisys(String thisys) {
		this.thisys = thisys;
	}
	public String getThisnum() {
		return thisnum;
	}
	public void setThisnum(String thisnum) {
		this.thisnum = thisnum;
	}
	public String getSumys() {
		return sumys;
	}
	public void setSumys(String sumys) {
		this.sumys = sumys;
	}
	public String getSumnum() {
		return sumnum;
	}
	public void setSumnum(String sumnum) {
		this.sumnum = sumnum;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getHkbj() {
		return hkbj;
	}
	public void setHkbj(String hkbj) {
		this.hkbj = hkbj;
	}
	public String getHklx() {
		return hklx;
	}
	public void setHklx(String hklx) {
		this.hklx = hklx;
	}
	public String getHkfx() {
		return hkfx;
	}
	public void setHkfx(String hkfx) {
		this.hkfx = hkfx;
	}
}
