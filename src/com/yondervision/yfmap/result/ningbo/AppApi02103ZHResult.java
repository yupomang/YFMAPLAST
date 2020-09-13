package com.yondervision.yfmap.result.ningbo;
/**
 * 单位明细查询
 * @author HYD-HSP
 *
 */
public class AppApi02103ZHResult {
	
	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 上月缴存人数*/
	private String 	lmpaynum;
	/** 上月缴存金额*/
	private String 	lmpayamt;
	/** 本月增加缴存人数*/
	private String 	cmaddpaynum;
	/** 本月增加缴存金额*/
	private String 	cmaddpayamt;
	/** 本月减少缴存人数*/
	private String 	cmdecpaynum;
	/** 本月减少缴存金额*/
	private String 	cmdecpayamt;
	/** 本月缴存人数*/
	private String 	cmpaynum;
	/** 本月缴存金额*/
	private String 	cmpayamt;
	
	
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
	public String getLmpaynum() {
		return lmpaynum;
	}
	public void setLmpaynum(String lmpaynum) {
		this.lmpaynum = lmpaynum;
	}
	public String getLmpayamt() {
		return lmpayamt;
	}
	public void setLmpayamt(String lmpayamt) {
		this.lmpayamt = lmpayamt;
	}
	public String getCmaddpaynum() {
		return cmaddpaynum;
	}
	public void setCmaddpaynum(String cmaddpaynum) {
		this.cmaddpaynum = cmaddpaynum;
	}
	public String getCmaddpayamt() {
		return cmaddpayamt;
	}
	public void setCmaddpayamt(String cmaddpayamt) {
		this.cmaddpayamt = cmaddpayamt;
	}
	public String getCmdecpaynum() {
		return cmdecpaynum;
	}
	public void setCmdecpaynum(String cmdecpaynum) {
		this.cmdecpaynum = cmdecpaynum;
	}
	public String getCmdecpayamt() {
		return cmdecpayamt;
	}
	public void setCmdecpayamt(String cmdecpayamt) {
		this.cmdecpayamt = cmdecpayamt;
	}
	public String getCmpaynum() {
		return cmpaynum;
	}
	public void setCmpaynum(String cmpaynum) {
		this.cmpaynum = cmpaynum;
	}
	public String getCmpayamt() {
		return cmpayamt;
	}
	public void setCmpayamt(String cmpayamt) {
		this.cmpayamt = cmpayamt;
	}

}
