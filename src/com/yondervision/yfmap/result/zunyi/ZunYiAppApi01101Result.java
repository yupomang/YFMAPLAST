package com.yondervision.yfmap.result.zunyi;

/** 
* @ClassName: AppApi00501Result 
* @Description: 贷款进度查询
* @author Caozhongyan
* @date Sep 27, 2013 10:27:15 AM   
* 
*/ 
public class ZunYiAppApi01101Result {	
	/** 返回码 */						
	private String recode = "";
	/** 返回描述 */						
	private String msg = "";
	/** 申请 */						
	private String summary = "";						
	/** 复审 */						
	private String summary1 = "";						
	/** 审批 */						
	private String summary2 = "";						
	/** 合同签订 */						
	private String summary3 = "";						
	/** 贷款担保 */						
	private String summary4 = "";						
	/** 放款 */						
	private String summarydes = "";	
	/** 贷款归还 */						
	private String transdes = "";
	/** 合同终止 */						
	private String rreason = "";						
							
	/**						
	 *<pre> 执行获取申请处理 </pre>						
	 * @return summary 申请						
	 */						
	public String getSummary() {						
	    return summary;						
	}						
							
	/**						
	 *<pre> 执行设定申请处理 </pre>						
	 * @param summary 申请						
	 */						
	public void setSummary(String summary) {						
	    this.summary = summary;						
	}						
							
	/**						
	 *<pre> 执行获取复审处理 </pre>						
	 * @return summary1 复审						
	 */						
	public String getSummary1() {						
	    return summary1;						
	}						
							
	/**						
	 *<pre> 执行设定复审处理 </pre>						
	 * @param summary1 复审						
	 */						
	public void setSummary1(String summary1) {						
	    this.summary1 = summary1;						
	}						
							
	/**						
	 *<pre> 执行获取审批处理 </pre>						
	 * @return summary2 审批						
	 */						
	public String getSummary2() {						
	    return summary2;						
	}						
							
	/**						
	 *<pre> 执行设定审批处理 </pre>						
	 * @param summary2 审批						
	 */						
	public void setSummary2(String summary2) {						
	    this.summary2 = summary2;						
	}						
							
	/**						
	 *<pre> 执行获取合同签订处理 </pre>						
	 * @return summary3 合同签订						
	 */						
	public String getSummary3() {						
	    return summary3;						
	}						
							
	/**						
	 *<pre> 执行设定合同签订处理 </pre>						
	 * @param summary3 合同签订						
	 */						
	public void setSummary3(String summary3) {						
	    this.summary3 = summary3;						
	}						
							
	/**						
	 *<pre> 执行获取贷款担保处理 </pre>						
	 * @return summary4 贷款担保						
	 */						
	public String getSummary4() {						
	    return summary4;						
	}						
							
	/**						
	 *<pre> 执行设定贷款担保处理 </pre>						
	 * @param summary4 贷款担保						
	 */						
	public void setSummary4(String summary4) {						
	    this.summary4 = summary4;						
	}						
							
	/**						
	 *<pre> 执行获取放款处理 </pre>						
	 * @return summarydes 放款						
	 */						
	public String getSummarydes() {						
	    return summarydes;						
	}						
							
	/**						
	 *<pre> 执行设定放款处理 </pre>						
	 * @param summarydes 放款						
	 */						
	public void setSummarydes(String summarydes) {						
	    this.summarydes = summarydes;						
	}						
							
	/**						
	 *<pre> 执行获取合同终止处理 </pre>						
	 * @return rreason 合同终止						
	 */						
	public String getRreason() {						
	    return rreason;						
	}						
							
	/**						
	 *<pre> 执行设定合同终止处理 </pre>						
	 * @param rreason 合同终止						
	 */						
	public void setRreason(String rreason) {						
	    this.rreason = rreason;						
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

	public String getTransdes() {
		return transdes;
	}

	public void setTransdes(String transdes) {
		this.transdes = transdes;
	}		
}
