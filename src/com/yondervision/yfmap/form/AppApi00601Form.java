package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi00101Form 
* @Description: 贷款余额
* @author Caozhongyan
* @date Nov 5, 2013 3:37:03 PM   
* 
*/ 
public class AppApi00601Form extends AppApiCommonForm {

	/** 页码 */				
	private String pagenum = "";				
	/** 行数 */				
	private String pagerows = "";
	/** 是否分页标识 */	
	private String ispaging = "";
	
	private String dealamt = "";  // 还款金额
	private String dealdate = "";  // 划款日期
	private String loancontrnum = "";
	private String loancontractno="";
	private String busitype="";//业务类型1-办结业务，2-查询业务 唐山
	public String getLoancontrnum() {
		return loancontrnum;
	}
	public void setLoancontrnum(String loancontrnum) {
		this.loancontrnum = loancontrnum;
	}
	public String getDealamt() {
		return dealamt;
	}
	public void setDealamt(String dealamt) {
		this.dealamt = dealamt;
	}
	public String getDealdate() {
		return dealdate;
	}
	public void setDealdate(String dealdate) {
		this.dealdate = dealdate;
	}
	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	private String accnum="";
	private String certinum="";	
	private String freeuse1="";
	private String freeuse2="";
	private String freeuse3="";
	private String transCode="";
	private String year="";
	private String loanContrNum = "";//合同号
	private String flag = "";//是否签约
	private String custFlag = "";//主借款人或配偶标志
	private String accName = "";
	/**
	 * 贴息贷款标志
	 */
	private String txdkbz = "";
	
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getCustFlag() {
		return custFlag;
	}
	public void setCustFlag(String custFlag) {
		this.custFlag = custFlag;
	}
	public String getLoanContrNum() {
		return loanContrNum;
	}
	public void setLoanContrNum(String loanContrNum) {
		this.loanContrNum = loanContrNum;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public String getLoancontractno() {
		return loancontractno;
	}
	public void setLoancontractno(String loancontractno) {
		this.loancontractno = loancontractno;
	}
	public String getBusitype() {
		return busitype;
	}
	public void setBusitype(String busitype) {
		this.busitype = busitype;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
	}
	public String getIspaging() {
		return ispaging;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public String getTxdkbz() {
		return txdkbz;
	}
	public void setTxdkbz(String txdkbz) {
		this.txdkbz = txdkbz;
	}
	
}
