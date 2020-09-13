package com.yondervision.yfmap.result.kunming;


/**
 * @author Caozhongyan
 *
 */
public class AppApi00201Result {
	/** 交易日期 */					
	private String operationDate = "";	
	/** 单位名称*/					
	private String unitaccname = "";					
	/** 单位帐号 */					
	private String unitaccnum = "";					
	/** 业务类型 */					
	private String operationType = "";					
	/** 借贷标志 */					
	private String dcflag = "";				
	/** 发生额*/					
	private String amount = "";					
	/** 余额 */					
	private String balance = "";					
	/** 提取类型 */					
	private String gettype = "";					
	/** 汇缴开始年月*/					
	private String paybegindate  = "";				
	/** 汇缴结束年月*/					
	private String payenddate  = "";	
						
					
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

	public String getUnitaccname() {
		return unitaccname;
	}

	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}

	public String getUnitaccnum() {
		return unitaccnum;
	}

	public void setUnitaccnum(String unitaccnum) {
		this.unitaccnum = unitaccnum;
	}

	public String getDcflag() {
		return dcflag;
	}

	public void setDcflag(String dcflag) {
		this.dcflag = dcflag;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getGettype() {
		return gettype;
	}

	public void setGettype(String gettype) {
		this.gettype = gettype;
	}

	public String getPaybegindate() {
		return paybegindate;
	}

	public void setPaybegindate(String paybegindate) {
		this.paybegindate = paybegindate;
	}

	public String getPayenddate() {
		return payenddate;
	}

	public void setPayenddate(String payenddate) {
		this.payenddate = payenddate;
	}					
}
