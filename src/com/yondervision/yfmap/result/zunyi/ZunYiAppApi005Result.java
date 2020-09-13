package com.yondervision.yfmap.result.zunyi;

public class ZunYiAppApi005Result {
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
						
	/** 提取原因描述 */					
	private String frzreason = "";
	/** 办理机构 */					
	private String accname1 = "";
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

	public String getFrzreason() {
		return frzreason;
	}

	public void setFrzreason(String frzreason) {
		this.frzreason = frzreason;
	}

	public String getAccname1() {
		return accname1;
	}

	public void setAccname1(String accname1) {
		this.accname1 = accname1;
	}					
						

}
