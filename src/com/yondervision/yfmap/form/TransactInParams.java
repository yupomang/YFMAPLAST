package com.yondervision.yfmap.form;

/**
 * @ClassName: TransactInParams
 * @Description: 办结接口的入参
 * @author kc
 * @date Dec 18, 2017 13:45:20 PM
 */
public class TransactInParams {
	/** 办件流水号 */
	private String projid = "";
	/** 办结人姓名 */
	private String transact_user = "";
	/** 办结时间 */
	private String transact_time = "";
	/** 办理结果 */
	private String transact_result = "";
	/** 办结说明 */
	private String transact_describe = "";
	/** 结果编号 */
	private String result_code = "";
	/** 备注 */
	private String remark = "";
	/** 记录产生时间 */
	private String create_time = "";
	/** 数据版本号 */
	private String dataversion = "";
	/** 所属系统 */
	private String belongsystem = "";

	public String getProjid() {
		return projid;
	}

	public void setProjid(String projid) {
		this.projid = projid;
	}

	public String getTransact_user() {
		return transact_user;
	}

	public void setTransact_user(String transact_user) {
		this.transact_user = transact_user;
	}

	public String getTransact_time() {
		return transact_time;
	}

	public void setTransact_time(String transact_time) {
		this.transact_time = transact_time;
	}

	public String getTransact_result() {
		return transact_result;
	}

	public void setTransact_result(String transact_result) {
		this.transact_result = transact_result;
	}

	public String getTransact_describe() {
		return transact_describe;
	}

	public void setTransact_describe(String transact_describe) {
		this.transact_describe = transact_describe;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getBelongsystem() {
		return belongsystem;
	}

	public void setBelongsystem(String belongsystem) {
		this.belongsystem = belongsystem;
	}

}
