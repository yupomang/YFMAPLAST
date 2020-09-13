package com.yondervision.yfmap.result.zunyi;

public class ZunYiAppApi003Result {
	/** 个人账号 */						
	private String accnum = "";						
	/** 姓名 */						
	private String accname = "";						
	/** 单位名称 */						
	private String unitaccname = "";						
	/** 结息日期 */						
	private String transdate   = "";						
	/** 结息本金 */						
	private String bfcalintbal      = "";						
	/** 结息利息 */						
	private String calintamt        = "";						
	/** 本息合计 */						
	private String afcalintbal      = "";						
	/** 返回码 */						
	private String recode = "";
	/** 返回描述 */						
	private String msg = "";						
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

	/**						
	 *<pre> 执行获取个人账号处理 </pre>						
	 * @return accnum 个人账号						
	 */						
	public String getAccnum() {						
	    return accnum;						
	}						
							
	/**						
	 *<pre> 执行设定个人账号处理 </pre>						
	 * @param accnum 个人账号						
	 */						
	public void setAccnum(String accnum) {						
	    this.accnum = accnum;						
	}						
							
	/**						
	 *<pre> 执行获取姓名处理 </pre>						
	 * @return accname 姓名						
	 */						
	public String getAccname() {						
	    return accname;						
	}						
							
	/**						
	 *<pre> 执行设定姓名处理 </pre>						
	 * @param accname 姓名						
	 */						
	public void setAccname(String accname) {						
	    this.accname = accname;						
	}						
							
	/**						
	 *<pre> 执行获取单位名称处理 </pre>						
	 * @return unitaccname 单位名称						
	 */						
	public String getUnitaccname() {						
	    return unitaccname;						
	}						
							
	/**						
	 *<pre> 执行设定单位名称处理 </pre>						
	 * @param unitaccname 单位名称						
	 */						
	public void setUnitaccname(String unitaccname) {						
	    this.unitaccname = unitaccname;						
	}						
							
	/**						
	 *<pre> 执行获取结息日期处理 </pre>						
	 * @return transdate   结息日期						
	 */						
	public String getTransdate  () {						
	    return transdate  ;						
	}						
							
	/**						
	 *<pre> 执行设定结息日期处理 </pre>						
	 * @param transdate   结息日期						
	 */						
	public void setTransdate  (String transdate  ) {						
	    this.transdate   = transdate  ;						
	}						
							
	/**						
	 *<pre> 执行获取结息本金处理 </pre>						
	 * @return bfcalintbal      结息本金						
	 */						
	public String getBfcalintbal     () {						
	    return bfcalintbal     ;						
	}						
							
	/**						
	 *<pre> 执行设定结息本金处理 </pre>						
	 * @param bfcalintbal      结息本金						
	 */						
	public void setBfcalintbal     (String bfcalintbal     ) {						
	    this.bfcalintbal      = bfcalintbal     ;						
	}						
							
	/**						
	 *<pre> 执行获取结息利息处理 </pre>						
	 * @return calintamt        结息利息						
	 */						
	public String getCalintamt       () {						
	    return calintamt       ;						
	}						
							
	/**						
	 *<pre> 执行设定结息利息处理 </pre>						
	 * @param calintamt        结息利息						
	 */						
	public void setCalintamt       (String calintamt       ) {						
	    this.calintamt        = calintamt       ;						
	}						
							
	/**						
	 *<pre> 执行获取本息合计处理 </pre>						
	 * @return afcalintbal      本息合计						
	 */						
	public String getAfcalintbal     () {						
	    return afcalintbal     ;						
	}						
							
	/**						
	 *<pre> 执行设定本息合计处理 </pre>						
	 * @param afcalintbal      本息合计						
	 */						
	public void setAfcalintbal     (String afcalintbal     ) {						
	    this.afcalintbal      = afcalintbal     ;						
	}	
}
