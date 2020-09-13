package com.yondervision.yfmap.result.chengde;


/**
 * @author Caozhongyan
 *
 */
public class ChengDeAppApi00201Result {
	/** 交易日期 */					
	private String operationDate = "";					
	/** 业务类型 */					
	private String operationType = "";					
	/** 金额 */					
	private String amount = "";					
	/** 业务摘要 */					
	private String operationMemo = "";
	/** 发生金额 */					
	private String fsje = "";
	/** 账户余额 */					
	private String zhye = "";
	/** 交易类型 */					
	private String jylx = "";
	
	/**
	 * 响应码
	 */
	private String recode="";
	/**
	 * 响应信息
	 */
	private String msg="";
	
	private String thisys = "";//当前页数
	
	private String thisnum = "";//当前页记录数
	
	private String sumnum = "";//总记录数
	
	private String sumys = "";//总页数
						
	/**					
	 *<pre> 执行获取交易日期处理 </pre>					
	 * @return operationDate 交易日期					
	 */					
	public String getOperationDate() {					
	    return operationDate;					
	}					
						
	/**					
	 *<pre> 执行设定交易日期处理 </pre>					
	 * @param operationDate 交易日期					
	 */					
	public void setOperationDate(String operationDate) {					
	    this.operationDate = operationDate;					
	}					
						
	/**					
	 *<pre> 执行获取业务类型处理 </pre>					
	 * @return operationType 业务类型					
	 */					
	public String getOperationType() {					
	    return operationType;					
	}					
						
	/**					
	 *<pre> 执行设定业务类型处理 </pre>					
	 * @param operationType 业务类型					
	 */					
	public void setOperationType(String operationType) {					
	    this.operationType = operationType;					
	}					
						
	/**					
	 *<pre> 执行获取金额处理 </pre>					
	 * @return amount 金额					
	 */					
	public String getAmount() {					
	    return amount;					
	}					
						
	/**					
	 *<pre> 执行设定金额处理 </pre>					
	 * @param amount 金额					
	 */					
	public void setAmount(String amount) {					
	    this.amount = amount;					
	}					
						
	/**					
	 *<pre> 执行获取业务摘要处理 </pre>					
	 * @return operationMemo 业务摘要					
	 */					
	public String getOperationMemo() {					
	    return operationMemo;					
	}					
						
	/**					
	 *<pre> 执行设定业务摘要处理 </pre>					
	 * @param operationMemo 业务摘要					
	 */					
	public void setOperationMemo(String operationMemo) {					
	    this.operationMemo = operationMemo;					
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

	public String getFsje() {
		return fsje;
	}

	public void setFsje(String fsje) {
		this.fsje = fsje;
	}

	public String getZhye() {
		return zhye;
	}

	public void setZhye(String zhye) {
		this.zhye = zhye;
	}

	public String getJylx() {
		return jylx;
	}

	public void setJylx(String jylx) {
		this.jylx = jylx;
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

	public String getSumnum() {
		return sumnum;
	}

	public void setSumnum(String sumnum) {
		this.sumnum = sumnum;
	}

	public String getSumys() {
		return sumys;
	}

	public void setSumys(String sumys) {
		this.sumys = sumys;
	}
}
