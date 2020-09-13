package com.yondervision.yfmap.result.zhangjiakou;

/** 
* @ClassName: AppApi00501Result 
* @Description: 提取明细查询
* @author Caozhongyan
* @date Sep 27, 2013 10:27:15 AM   
* 
*/ 
public class ZhangJiaKouAppApi007Result {
	/** 最新业务日期 */						
	private String operationDate = "";						
	/** 业务类型 */						
	private String operationType = "";						
	/** 金额 */						
	private String withdrawalAmount = "";	
	/** 利息金额 */						
	private String lxAmount = "";
	/** 罚息金额 */						
	private String fxAmount = "";
	
	/** 期数*/						
	private String qs = "";
							
	/**						
	 *<pre> 执行获取最新业务日期处理 </pre>						
	 * @return operationDate 最新业务日期						
	 */						
	public String getOperationDate() {						
	    return operationDate;						
	}						
							
	/**						
	 *<pre> 执行设定最新业务日期处理 </pre>						
	 * @param operationDate 最新业务日期						
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
	 * @return withdrawalAmount 金额						
	 */						
	public String getWithdrawalAmount() {						
	    return withdrawalAmount;						
	}						
							
	/**						
	 *<pre> 执行设定金额处理 </pre>						
	 * @param withdrawalAmount 金额						
	 */						
	public void setWithdrawalAmount(String withdrawalAmount) {						
	    this.withdrawalAmount = withdrawalAmount;						
	}

	public String getLxAmount() {
		return lxAmount;
	}

	public void setLxAmount(String lxAmount) {
		this.lxAmount = lxAmount;
	}

	public String getFxAmount() {
		return fxAmount;
	}

	public void setFxAmount(String fxAmount) {
		this.fxAmount = fxAmount;
	}

	public String getQs() {
		return qs;
	}

	public void setQs(String qs) {
		this.qs = qs;
	}						

}
