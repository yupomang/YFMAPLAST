package com.yondervision.yfmap.form;
/**
 * 贷款展期办理
 * @author LFX
 *
 */
public class AppApi01036Form extends AppApiCommonForm {
 
	/**
	 * 身份证号
	 */
     private String bodyCardNumber = "";
     /**
      * 姓名
      */
     private String xingming = "";
     /**
      * 展期期数
      */
     private String increaseNum = "";
     
     /**
      * 缩期期数
      */
     private String reduceNum = "";
     
     /**
      * 变更后利率
      */
     private String currate = "";
     
     /**
      * 还款方式
      */
     private String dkhkfs = "";
     
     /**
      * 变更后到期日期
      */
     private String exttermenddate = "";
     
     /**
      * 贷款变更原因
      */
     private String changeReason = "";

     /**
      * 申报编号
      */
     private String applyid = "";
     
	public String getBodyCardNumber() {
		return bodyCardNumber;
	}
	public void setBodyCardNumber(String bodyCardNumber) {
		this.bodyCardNumber = bodyCardNumber;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getIncreaseNum() {
		return increaseNum;
	}
	public void setIncreaseNum(String increaseNum) {
		this.increaseNum = increaseNum;
	}
	public String getReduceNum() {
		return reduceNum;
	}
	public void setReduceNum(String reduceNum) {
		this.reduceNum = reduceNum;
	}
	public String getChangeReason() {
		return changeReason;
	}
	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	public String getDkhkfs() {
		return dkhkfs;
	}
	public void setDkhkfs(String dkhkfs) {
		this.dkhkfs = dkhkfs;
	}
	public String getCurrate() {
		return currate;
	}
	public void setCurrate(String currate) {
		this.currate = currate;
	}
	public String getExttermenddate() {
		return exttermenddate;
	}
	public void setExttermenddate(String exttermenddate) {
		this.exttermenddate = exttermenddate;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	
}
