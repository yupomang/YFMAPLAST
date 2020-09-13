package com.yondervision.yfmap.result.qvzhi;

public class QvZhiAppApi00101Result { 
	/** 缴存单位 */	
	private String unitaccname = "";					
	/** 缴存账号 */					
	private String unitaccid = "";	
	/** 个人公积金账号 */					
	private String accnum = "";					
	/** 姓名 */					
	private String accname = "";					
	/** 身份证号 */					
	private String certinum = "";
	/** 冻结金额 */	
	private String frzamt = "";	
	/** 冻结日期 */
	private String frzdodate = "";	
	/** 余额 */					
	private String bal = "";
	/** 最后交易日期 */					
	private String lasttransdate = "";
	/** 账户状态 */					
	private String indiaccstate = "";
	/** 备用字符 */
	private String freeuse1;
	/** 备用金额 */
	private String freeuse2;
	/** 备用日期 */
	private String freeuse3;
	/** 备用数值 */
	private String freeuse4;
	
	private String recode = "";
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
	 *<pre> 执行获取身份证号处理 </pre>					
	 * @return certinum 身份证号					
	 */					
	public String getCertinum() {					
	    return certinum;					
	}					
						
	/**					
	 *<pre> 执行设定身份证号处理 </pre>					
	 * @param certinum 身份证号					
	 */					
	public void setCertinum(String certinum) {					
	    this.certinum = certinum;					
	}					
						
	/**					
	 *<pre> 执行获取缴存单位处理 </pre>					
	 * @return unitaccname 缴存单位					
	 */					
	public String getUnitaccname() {					
	    return unitaccname;					
	}					
						
	/**					
	 *<pre> 执行设定缴存单位处理 </pre>					
	 * @param unitaccname 缴存单位					
	 */					
	public void setUnitaccname(String unitaccname) {					
	    this.unitaccname = unitaccname;					
	}

	public String getUnitaccid() {
		return unitaccid;
	}

	public void setUnitaccid(String unitaccid) {
		this.unitaccid = unitaccid;
	}

	public String getFrzamt() {
		return frzamt;
	}

	public void setFrzamt(String frzamt) {
		this.frzamt = frzamt;
	}

	public String getFrzdodate() {
		return frzdodate;
	}

	public void setFrzdodate(String frzdodate) {
		this.frzdodate = frzdodate;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getLasttransdate() {
		return lasttransdate;
	}

	public void setLasttransdate(String lasttransdate) {
		this.lasttransdate = lasttransdate;
	}

	public String getIndiaccstate() {
		return indiaccstate;
	}

	public void setIndiaccstate(String indiaccstate) {
		this.indiaccstate = indiaccstate;
	}

	public String getFreeuse1() {
		return freeuse1;
	}

	public void setFreeuse1(String freeuse1) {
		this.freeuse1 = freeuse1;
	}

	public String getFreeuse2() {
		return freeuse2;
	}

	public void setFreeuse2(String freeuse2) {
		this.freeuse2 = freeuse2;
	}

	public String getFreeuse3() {
		return freeuse3;
	}

	public void setFreeuse3(String freeuse3) {
		this.freeuse3 = freeuse3;
	}

	public String getFreeuse4() {
		return freeuse4;
	}

	public void setFreeuse4(String freeuse4) {
		this.freeuse4 = freeuse4;
	}					
						
}
