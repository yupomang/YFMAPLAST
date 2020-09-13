package com.yondervision.yfmap.form;

/**
 * @ClassName: AcceptInParams
 * @Description: 受理接口的入参
 * @author kc
 * @date Dec 18, 2017 13:45:20 PM
 */
public class AcceptInParams {
	/** 办件流水号 */
	private String projid = "";
	/** 受理人姓名 */
	private String accept_man = "";
	/** 受理时间 */
	private String accepttime = "";
	/** 承诺工作日,数字类型 */
	private String promisevalue = "";
	/** 记录产生时间 */
	private String create_time = "";

	/** 数据版本号 */
	private String dataversion = "";
	/** 0为受理，1为不予受理 */
	private String doaction = "";
	/** 所属系统 */
	private String belongsystem = "";
	/** 部门 */
	private String cur_handledept = "";
	/** 业务系统主键 */
	private String outsystemid = "";

	public String getProjid() {
		return projid;
	}

	public void setProjid(String projid) {
		this.projid = projid;
	}

	public String getAccept_man() {
		return accept_man;
	}

	public void setAccept_man(String accept_man) {
		this.accept_man = accept_man;
	}

	public String getAccepttime() {
		return accepttime;
	}

	public void setAccepttime(String accepttime) {
		this.accepttime = accepttime;
	}

	public String getPromisevalue() {
		return promisevalue;
	}

	public void setPromisevalue(String promisevalue) {
		this.promisevalue = promisevalue;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getDataversion() {
		return dataversion;
	}

	public void setDataversion(String dataversion) {
		this.dataversion = dataversion;
	}

	public String getDoaction() {
		return doaction;
	}

	public void setDoaction(String doaction) {
		this.doaction = doaction;
	}

	public String getBelongsystem() {
		return belongsystem;
	}

	public void setBelongsystem(String belongsystem) {
		this.belongsystem = belongsystem;
	}

	public String getCur_handledept() {
		return cur_handledept;
	}

	public void setCur_handledept(String cur_handledept) {
		this.cur_handledept = cur_handledept;
	}

	public String getOutsystemid() {
		return outsystemid;
	}

	public void setOutsystemid(String outsystemid) {
		this.outsystemid = outsystemid;
	}
}
