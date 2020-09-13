package com.yondervision.yfmap.result.zhongshan;

/** 
* @ClassName: AppApi01006Result
* @Description: 提取信息查询接口返回参数
* @author syw
* 
*/ 
public class AppApi01006Result {
	/**公积金帐号*/
	private String accnum;
	
	/**姓名*/
	private String accname;

	/**协议人账号*/
	private String agrtaccnum;
	/**金额*/
	private String amt;
	/**账户余额*/
	private String bal;
	
	/**证件类型*/
	private String certitype2;
	
	/**证件号码*/
	private String certinum;
	/**保留金额*/
	private String dpadjamt;
	/**冻结金额*/
	private String frzamt;

	/**电话*/
	private String handset;
	/**账户状态*/
	private String indiaccstate;
	/**联系电话*/
	private String linkphone;
	/**配偶证件号码*/
	private String matecertinum;
	/**配偶姓名*/
	private String matename;
	/**近三个月缴存额*/
	private String payamt;
	/**近三个月缴存额的30%*/
	private String payamt1;
	/**收款账号*/
	private String payeebankaccnum;
	/**收款银行代码*/
	private String payeebankname;
	/**收款账号*/
	private String payeename;
	/**收款银行*/
	private String payeebanknameDes;
	/**止付金额*/
	private String stpayamt;
	
	/**单位帐号*/
	private String unitaccnum;
	
	/**单位名称*/
	private String unitaccname;
	/**上限额*/
	private String uplimitamt;
	/**上限额*/
	private String tqyy;
	/**上限额*/
	private String tqje;

	
	/**签约状态*/
	private String signStatus;
	
	/**提取签约类型*/
	private String drawSignType;
	
	/**
	 * 
	 */
	private String freeuse1 = "";				
	/**
	 * 
	 */
	private String freeuse2 = "";
	/**
	 * 
	 */
	private String freeuse3 = "";

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
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getUnitaccnum() {
		return unitaccnum;
	}
	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getAccname() {
		return accname;
	}
	public void setAccname(String accname) {
		this.accname = accname;
	}
	public String getAgrtaccnum() {
		return agrtaccnum;
	}
	public void setAgrtaccnum(String agrtaccnum) {
		this.agrtaccnum = agrtaccnum;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getBal() {
		return bal;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public String getCertitype2() {
		return certitype2;
	}
	public void setCertitype2(String certitype2) {
		this.certitype2 = certitype2;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}
	public String getDpadjamt() {
		return dpadjamt;
	}
	public void setDpadjamt(String dpadjamt) {
		this.dpadjamt = dpadjamt;
	}
	public String getFrzamt() {
		return frzamt;
	}
	public void setFrzamt(String frzamt) {
		this.frzamt = frzamt;
	}
	public String getHandset() {
		return handset;
	}
	public void setHandset(String handset) {
		this.handset = handset;
	}
	public String getIndiaccstate() {
		return indiaccstate;
	}
	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public String getMatecertinum() {
		return matecertinum;
	}
	public void setMatecertinum(String matecertinum) {
		this.matecertinum = matecertinum;
	}
	public String getMatename() {
		return matename;
	}
	public void setMatename(String matename) {
		this.matename = matename;
	}
	public String getPayamt() {
		return payamt;
	}
	public void setPayamt(String payamt) {
		this.payamt = payamt;
	}
	public String getPayamt1() {
		return payamt1;
	}
	public void setPayamt1(String payamt1) {
		this.payamt1 = payamt1;
	}
	public String getPayeebankaccnum() {
		return payeebankaccnum;
	}
	public void setPayeebankaccnum(String payeebankaccnum) {
		this.payeebankaccnum = payeebankaccnum;
	}
	public String getPayeebankname() {
		return payeebankname;
	}
	public void setPayeebankname(String payeebankname) {
		this.payeebankname = payeebankname;
	}
	public String getStpayamt() {
		return stpayamt;
	}
	public void setStpayamt(String stpayamt) {
		this.stpayamt = stpayamt;
	}
	public String getUplimitamt() {
		return uplimitamt;
	}
	public void setUplimitamt(String uplimitamt) {
		this.uplimitamt = uplimitamt;
	}
	public String getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}
	public String getDrawSignType() {
		return drawSignType;
	}
	public void setDrawSignType(String drawSignType) {
		this.drawSignType = drawSignType;
	}
	public String getPayeebanknameDes() {
		return payeebanknameDes;
	}
	public void setPayeebanknameDes(String payeebanknameDes) {
		this.payeebanknameDes = payeebanknameDes;
	}
	public String getTqyy() {
		return tqyy;
	}
	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}
	public String getTqje() {
		return tqje;
	}
	public void setTqje(String tqje) {
		this.tqje = tqje;
	}
	public String getPayeename() {
		return payeename;
	}
	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}
	
}
