package com.yondervision.yfmap.form;
/**
 * 公积金代扣协议
 * @author LFX
 *
 */
public class AppApi01046Form extends AppApiCommonForm {
 
	/**
	 * 身份证号
	 */
     private String bodyCardNumber = "";
     /**
      * 姓名
      */
     private String xingming = "";
//     /**
//      * 扣款人证件类型
//      */
//     private String kkrzjlx = "";
//     /**
//      * 扣款人类型
//      */
//     private String kkrlx = "";
//     /**
//      * 协议类型
//      */
//     private String xylx = "";
//     
     /**
      * 扣款人姓名
      */
     private String kkrxm = "";
//     /**
//      * 添加意见
//      */
//     private String tjyy = "";
     /**
      * 扣款人证件号码
      */
     private String kkrzjhm = "";
     
     /**
      * 借款合同编号
      */
     private String jkhtbh = "";
     
     /**
      * 扣款顺序
      */
     private String kksx = "";
     
     /**
      * 扣款人详情列表
      */
     private String details = "";
     
     private String applyid;
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
	public String getKkrzjhm() {
		return kkrzjhm;
	}
	public void setKkrzjhm(String kkrzjhm) {
		this.kkrzjhm = kkrzjhm;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}
	public String getApplyid() {
		return applyid;
	}
	public void setApplyid(String applyid) {
		this.applyid = applyid;
	}
	public String getKksx() {
		return kksx;
	}
	public void setKksx(String kksx) {
		this.kksx = kksx;
	}
	public String getKkrxm() {
		return kkrxm;
	}
	public void setKkrxm(String kkrxm) {
		this.kkrxm = kkrxm;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
    
}
