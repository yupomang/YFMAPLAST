package com.yondervision.yfmap.result.ningbo;

/**
 * 
 * @author 
 *销户提取计算可提取金额  
 */
public class AppApi80007ZHResult {
	private String spt_data;

	public String getSpt_data() {
		return this.spt_data;
	}

	public void setSpt_data(final String spt_data) {
		this.spt_data = spt_data;
	}

	/* 状态码*/
	private String recode = "";
	/* 描述*/
	private String msg = "";	
	/* 流水号*/
	private String tranSeq = "";
	/* 返回信息*/
	private String spt_showmsg = "";
	/* 返回成功标志*/
	private String spt_success = "";

	public String getSpt_success() {
		return this.spt_success;
	}

	public void setSpt_success(final String spt_success) {
		this.spt_success = spt_success;
	}

	public String getSpt_showmsg() {
		return this.spt_showmsg;
	}

	public void setSpt_showmsg(final String spt_showmsg) {
		this.spt_showmsg = spt_showmsg;
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
	public String getTranSeq() {
		return tranSeq;
	}
	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}
	/** 业务处理状态 */				
	private String resultStatus = "";
	/** 错误码信息 */				
	private String resultMsg = "";
	/** 服务请求ID */				
	private String resultId = "";
	/** 返回数据 */				
	private String data = "";
	/** 结果码 */				
	private String resultCode = "";
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getResultId() {
		return resultId;
	}
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	private String rsufilename="";//结果文件名
	private String spt_errormsg="";//失败错误日志
	private String spt_datacount="";//总结果记录数
	private String stdata="";//省厅下传数据
	private String spt_ywid="";//业务申请唯一标识码

	public String getRsufilename() {
		return this.rsufilename;
	}

	public void setRsufilename(final String rsufilename) {
		this.rsufilename = rsufilename;
	}

	public String getSpt_errormsg() {
		return this.spt_errormsg;
	}

	public void setSpt_errormsg(final String spt_errormsg) {
		this.spt_errormsg = spt_errormsg;
	}

	public String getSpt_datacount() {
		return this.spt_datacount;
	}

	public void setSpt_datacount(final String spt_datacount) {
		this.spt_datacount = spt_datacount;
	}

	public String getStdata() {
		return this.stdata;
	}

	public void setStdata(final String stdata) {
		this.stdata = stdata;
	}

	public String getSpt_ywid() {
		return this.spt_ywid;
	}

	public void setSpt_ywid(final String spt_ywid) {
		this.spt_ywid = spt_ywid;
	}
}

