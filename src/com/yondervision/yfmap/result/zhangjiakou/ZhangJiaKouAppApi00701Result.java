package com.yondervision.yfmap.result.zhangjiakou;

/** 
* @ClassName: AppApi00701Result 
* @Description: 贷款明细返回报文封装类
* @author fengjing
* 
*/ 
public class ZhangJiaKouAppApi00701Result{
	
private String syqs;
	
	/**
	 * 交易代码
	 */
	private String transCode="";
	/**
	 * 返回日期
	 */
	private String sendDate="";
	/**
	 * 返回时间
	 */
	private String sendTime="";
	/**
	 * 发送方流水号
	 */
	private String sendSeqno="";
	/**
	 * 安全标识
	 */
	private String key="";
	/**
	 * 中心流水号
	 */
	private String centerSeqno="";
	/**
	 * 响应码
	 */
	private String recode="";
	/**
	 * 响应信息
	 */
	private String msg="";
	/**
	 * 文件名
	 */
	private String filename="";
	/**
	 * 总笔数
	 */
	private String count="";
	/**
	 * 下页标识
	 */
	private String nextpage="";
	/**
	 * 当前页数
	 */
	private String thisys = "";
	
	/**
	 * 当前记录数
	 */
	private String thisnum = "";
	/**
	 * 总页数
	 */
	private String sumys = "";
	/**
	 * 总记录数
	 */
	private String sumnum = "";

	
	public String getThisys() {
		return thisys;
	}
	public void setThisys(String thisys) {
		this.thisys = thisys;
	}
	public String getThisnum() {
		return thisnum;
	}
	public void setThisnum(String thisnum) {
		this.thisnum = thisnum;
	}
	public String getSumys() {
		return sumys;
	}
	public void setSumys(String sumys) {
		this.sumys = sumys;
	}
	public String getSumnum() {
		return sumnum;
	}
	public void setSumnum(String sumnum) {
		this.sumnum = sumnum;
	}

	public String getSyqs() {
		return syqs;
	}
	public void setSyqs(String syqs) {
		this.syqs = syqs;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendSeqno() {
		return sendSeqno;
	}
	public void setSendSeqno(String sendSeqno) {
		this.sendSeqno = sendSeqno;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getCenterSeqno() {
		return centerSeqno;
	}
	public void setCenterSeqno(String centerSeqno) {
		this.centerSeqno = centerSeqno;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getNextpage() {
		return nextpage;
	}
	public void setNextpage(String nextpage) {
		this.nextpage = nextpage;
	}
	
}
