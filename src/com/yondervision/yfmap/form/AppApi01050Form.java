package com.yondervision.yfmap.form;


/**
 * 图片上传
 * @author LFX
 *
 */
public class AppApi01050Form extends AppApiCommonForm {
	/**
	 * 实例号
	 */
	private String instance = "";
	/**
	 * 身份证号
	 */
     private String bodyCardNumber = "";
     
     /**
      * 材料类型
      */
     private String materCode = "";
     
     /**
      * 
      */
     private String flowid = "";
     
     /**
      * 提取原因
      */
     private String tqyy ="";
     
     /**
      * 购房提取类型
      */
     private String ctlflag ="";
     
     /**
      * 商贷提取类型
      */
     private String buyhousetype ="";
     
     private String actionFlag="";
     
     private String filenames = "";
 	 private String filess = "";
 	 private String maxnumber = "";

	public String getFilenames() {
		return filenames;
	}

	public void setFilenames(String filenames) {
		this.filenames = filenames;
	}

	public String getFiless() {
		return filess;
	}

	public void setFiless(String filess) {
		this.filess = filess;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getBodyCardNumber() {
		return bodyCardNumber;
	}

	public void setBodyCardNumber(String bodyCardNumber) {
		this.bodyCardNumber = bodyCardNumber;
	}

	public String getMaterCode() {
		return materCode;
	}

	public void setMaterCode(String materCode) {
		this.materCode = materCode;
	}
	
	public String getFlowid() {
		return flowid;
	}

	public void setFlowid(String flowid) {
		this.flowid = flowid;
	}

	public String getTqyy() {
		return tqyy;
	}

	public void setTqyy(String tqyy) {
		this.tqyy = tqyy;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getMaxnumber() {
		return maxnumber;
	}

	public void setMaxnumber(String maxnumber) {
		this.maxnumber = maxnumber;
	}

	public String getCtlflag() {
		return ctlflag;
	}

	public void setCtlflag(String ctlflag) {
		this.ctlflag = ctlflag;
	}

	public String getBuyhousetype() {
		return buyhousetype;
	}

	public void setBuyhousetype(String buyhousetype) {
		this.buyhousetype = buyhousetype;
	}

}
