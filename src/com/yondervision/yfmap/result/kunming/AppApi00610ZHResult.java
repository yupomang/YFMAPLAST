package com.yondervision.yfmap.result.kunming;
/**
 * 
 * @author qinxla
 * 提前还款试算取最低还款额
 */
public class AppApi00610ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	/** 提前部分还款最小额 */
	private String minamt = "";
	/** 全部还款所需额度 */
	private String allamt = "";
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
	public String getMinamt() {
		return minamt;
	}
	public void setMinamt(String minamt) {
		this.minamt = minamt;
	}
	public String getAllamt() {
		return allamt;
	}
	public void setAllamt(String allamt) {
		this.allamt = allamt;
	}
	
	
}
