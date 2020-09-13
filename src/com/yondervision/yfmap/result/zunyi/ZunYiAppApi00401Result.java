package com.yondervision.yfmap.result.zunyi;

public class ZunYiAppApi00401Result {
	/** 返回码 */						
	private String recode = "";
	/** 返回描述 */						
	private String msg = "";
	/** 个人公积金账号 */				
	private String accnum = "";				
	/** 提取日期 */				
	private String drawdate = "";				
	/** 提取时间 */				
	private String drawtime = "";				
	/** 提取原因 */				
	private String drawreason = "";				
	/** 提取金额 */				
	private String drawamt = "";				
	/** 余额 */				
	private String bal = "";
	
	/** 当年提取金额 */						
	private String amt1       = "";						
	/** 累计提取金额 */						
	private String amt2 = "";						
	/** 提取原因中文描述 */						
	private String reason = "";						
							
	/**						
	 *<pre> 执行获取当年提取金额处理 </pre>						
	 * @return amt1       当年提取金额						
	 */						
	public String getAmt1      () {						
	    return amt1      ;						
	}						
							
	/**						
	 *<pre> 执行设定当年提取金额处理 </pre>						
	 * @param amt1       当年提取金额						
	 */						
	public void setAmt1      (String amt1      ) {						
	    this.amt1       = amt1      ;						
	}						
							
	/**						
	 *<pre> 执行获取累计提取金额处理 </pre>						
	 * @return amt2 累计提取金额						
	 */						
	public String getAmt2() {						
	    return amt2;						
	}						
							
	/**						
	 *<pre> 执行设定累计提取金额处理 </pre>						
	 * @param amt2 累计提取金额						
	 */						
	public void setAmt2(String amt2) {						
	    this.amt2 = amt2;						
	}						
							
	/**						
	 *<pre> 执行获取提取原因中文描述处理 </pre>						
	 * @return reason 提取原因中文描述						
	 */						
	public String getReason() {						
	    return reason;						
	}						
							
	/**						
	 *<pre> 执行设定提取原因中文描述处理 </pre>						
	 * @param reason 提取原因中文描述						
	 */						
	public void setReason(String reason) {						
	    this.reason = reason;						
	}						
							

					
	/**				
	 *<pre> 执行获取个人公积金账号处理 </pre>				
	 * @return accnum 个人公积金账号				
	 */				
	public String getAccnum() {				
	    return accnum;				
	}				
					
	/**				
	 *<pre> 执行设定个人公积金账号处理 </pre>				
	 * @param accnum 个人公积金账号				
	 */				
	public void setAccnum(String accnum) {				
	    this.accnum = accnum;				
	}				
					
	/**				
	 *<pre> 执行获取提取日期处理 </pre>				
	 * @return drawdate 提取日期				
	 */				
	public String getDrawdate() {				
	    return drawdate;				
	}				
					
	/**				
	 *<pre> 执行设定提取日期处理 </pre>				
	 * @param drawdate 提取日期				
	 */				
	public void setDrawdate(String drawdate) {				
	    this.drawdate = drawdate;				
	}				
					
	/**				
	 *<pre> 执行获取提取时间处理 </pre>				
	 * @return drawtime 提取时间				
	 */				
	public String getDrawtime() {				
	    return drawtime;				
	}				
					
	/**				
	 *<pre> 执行设定提取时间处理 </pre>				
	 * @param drawtime 提取时间				
	 */				
	public void setDrawtime(String drawtime) {				
	    this.drawtime = drawtime;				
	}				
					
	/**				
	 *<pre> 执行获取提取原因处理 </pre>				
	 * @return drawreason 提取原因				
	 */				
	public String getDrawreason() {				
	    return drawreason;				
	}				
					
	/**				
	 *<pre> 执行设定提取原因处理 </pre>				
	 * @param drawreason 提取原因				
	 */				
	public void setDrawreason(String drawreason) {				
	    this.drawreason = drawreason;				
	}				
					
	/**				
	 *<pre> 执行获取提取金额处理 </pre>				
	 * @return drawamt 提取金额				
	 */				
	public String getDrawamt() {				
	    return drawamt;				
	}				
					
	/**				
	 *<pre> 执行设定提取金额处理 </pre>				
	 * @param drawamt 提取金额				
	 */				
	public void setDrawamt(String drawamt) {				
	    this.drawamt = drawamt;				
	}				
					
	/**				
	 *<pre> 执行获取余额处理 </pre>				
	 * @return bal 余额				
	 */				
	public String getBal() {				
	    return bal;				
	}				
					
	/**				
	 *<pre> 执行设定余额处理 </pre>				
	 * @param bal 余额				
	 */				
	public void setBal(String bal) {				
	    this.bal = bal;				
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

}
