package com.yondervision.yfmap.util;

import java.util.Date;

/**
 * @deprecated 记录日志文件参数
 * @author Administrator
 *
 */
public class LogPara {
	private String code = "";//交易码
	private String name = "";//交易名
	private String reqmsg = "";//发送报文
	private String repmsg = "";//接收报文
	private String startXml = "";//组报文时间
	private String endXml = "";//解报文时间
	private String sendStartTime = "";//发送文时间
	private String sendEndTime = "";//接收文时间
	private String startdate = "";//开始时间
	private String enddate = "";//接收时间
	private String key = "";//联名卡号
	private String recode = "";//响应码
	private String remsg = "";//响应时间
	private String retime = "";//耗时
	private String oneMsg = "";//业务处理1
	private String twoMsg = "";//业务处理2
	private String bspcode1 = "";
	private String bspcode2 = "";
	private String ywlb = "0";//业务类别,0个人, 1单位, 2公共
	
	private String qdly="";
	private String wwjym="";
	private String jyms="";
	private String wwfqsj="";
	
	
	public String getQdly() {
		return qdly;
	}
	public void setQdly(String qdly) {
		this.qdly = qdly;
	}
	public String getWwjym() {
		return wwjym;
	}
	public void setWwjym(String wwjym) {
		this.wwjym = wwjym;
	}
	public String getJyms() {
		return jyms;
	}
	public void setJyms(String jyms) {
		this.jyms = jyms;
	}
	public String getRemsg() {
		return remsg;
	}
	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}
	public String getOneMsg() {
		return oneMsg;
	}
	public void setOneMsg(String oneMsg) {
		this.oneMsg = oneMsg;
	}
	public String getTwoMsg() {
		return twoMsg;
	}
	public void setTwoMsg(String twoMsg) {
		this.twoMsg = twoMsg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReqmsg() {
		return reqmsg;
	}
	public void setReqmsg(String reqmsg) {
		this.reqmsg = reqmsg;
	}
	public String getRepmsg() {
		return repmsg;
	}
	public void setRepmsg(String repmsg) {
		this.repmsg = repmsg;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRecode() {
		return recode;
	}
	public void setRecode(String recode) {
		this.recode = recode;
	}
	public String getRetime() {
		return retime;
	}
	public void setRetime(String retime) {
		this.retime = retime;
	}
	public String getStartXml() {
		return startXml;
	}
	public void setStartXml(String startXml) {
		this.startXml = startXml;
	}
	public String getEndXml() {
		return endXml;
	}
	public void setEndXml(String endXml) {
		this.endXml = endXml;
	}
	public String getSendStartTime() {
		return sendStartTime;
	}
	public void setSendStartTime(String sendStartTime) {
		this.sendStartTime = sendStartTime;
	}
	public String getSendEndTime() {
		return sendEndTime;
	}
	public void setSendEndTime(String sendEndTime) {
		this.sendEndTime = sendEndTime;
	}
	public String getBspcode1() {
		return bspcode1;
	}
	public void setBspcode1(String bspcode1) {
		this.bspcode1 = bspcode1;
	}
	public String getBspcode2() {
		return bspcode2;
	}
	public void setBspcode2(String bspcode2) {
		this.bspcode2 = bspcode2;
	}
	public String getYwlb() {
		return ywlb;
	}
	public void setYwlb(String ywlb) {
		this.ywlb = ywlb;
	}
	public String getWwfqsj() {
		return wwfqsj;
	}
	public void setWwfqsj(String wwfqsj) {
		this.wwfqsj = wwfqsj;
	}
	
}
