package com.yondervision.yfmap.result;

import java.util.List;

import com.yondervision.yfmap.socket.ReqHeadbean;


/** 
* @ClassName: AppApi3004Result 
* @Description: 模板消息
* @author Caozhongyan
* @date Feb 5, 2015 10:57:51 AM   
* 
*/ 
public class AppApi3004Result extends ReqHeadbean{
	/** 模板编号 */			
	private String smssource = "";			
	/** 关键字 */			
	private String sendKey = "";			
	/** 姓名 */			
	private String accname = "";			
	/** 消息标题 */			
	private String title = "";			
	/** 要素 */			
	private String elements = "";			
	/** 发送方式 */			
	private String sendType = "";
	/** 定时标记 */			
	private String timer = "";	
	/** 发送日期 */			
	private String sendDate1 = "";			
	/** 发送时间 */			
	private String sendTime1 = "";			
	/** 中心代码 */			
	private String centerId = "";			
	/** 消息内容 */			
	private String detail = "";			
	/** 消息主题 */			
	private String theme = "";			
	/** 消息渠道 */			
	private String chanel = "";			
	/** 备用字段1 */			
	private String freeuse1 = "";			
	/** 备用字段2 */			
	private String freeuse2 = "";			
	/** 备用字段3 */			
	private String freeuse3 = "";
	
	/** 城市名称 */	
	private String centerName="";

	
	/** 身份证 */			
	private String certinum = "";
	/** 内容 */			
	private String tsmsg = "";
	/** 定时时间 */			
	private String dsdate = "";
	/** 数据来源 */			
	private String msgsource = "";
	/** 操作员ID */			
	private String loginid = "";
	
	public String getCertinum() {
		return certinum;
	}

	public void setCertinum(String certinum) {
		this.certinum = certinum;
	}

	public String getTsmsg() {
		return tsmsg;
	}

	public void setTsmsg(String tsmsg) {
		this.tsmsg = tsmsg;
	}

	public String getDsdate() {
		return dsdate;
	}

	public void setDsdate(String dsdate) {
		this.dsdate = dsdate;
	}
	
	public String getMsgsource() {
		return msgsource;
	}

	public void setMsgsource(String msgsource) {
		this.msgsource = msgsource;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
			
				
			
	/**			
	 *<pre> 执行获取模板编号处理 </pre>			
	 * @return smssource 模板编号			
	 */			
	public String getSmssource() {			
	    return smssource;			
	}			
				
	/**			
	 *<pre> 执行设定模板编号处理 </pre>			
	 * @param smssource 模板编号			
	 */			
	public void setSmssource(String smssource) {			
	    this.smssource = smssource;			
	}			
				
	/**			
	 *<pre> 执行获取关键字处理 </pre>			
	 * @return sendKey 关键字			
	 */			
	public String getSendKey() {			
	    return sendKey;			
	}			
				
	/**			
	 *<pre> 执行设定关键字处理 </pre>			
	 * @param sendKey 关键字			
	 */			
	public void setSendKey(String sendKey) {			
	    this.sendKey = sendKey;			
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
	 *<pre> 执行获取消息标题处理 </pre>			
	 * @return title 消息标题			
	 */			
	public String getTitle() {			
	    return title;			
	}			
				
	/**			
	 *<pre> 执行设定消息标题处理 </pre>			
	 * @param title 消息标题			
	 */			
	public void setTitle(String title) {			
	    this.title = title;			
	}			
				
	/**			
	 *<pre> 执行获取要素处理 </pre>			
	 * @return elements 要素			
	 */			
	public String getElements() {			
	    return elements;			
	}			
				
	/**			
	 *<pre> 执行设定要素处理 </pre>			
	 * @param elements 要素			
	 */			
	public void setElements(String elements) {			
	    this.elements = elements;			
	}			
				
	/**			
	 *<pre> 执行获取发送方式处理 </pre>			
	 * @return sendType 发送方式			
	 */			
	public String getSendType() {			
	    return sendType;			
	}			
				
	/**			
	 *<pre> 执行设定发送方式处理 </pre>			
	 * @param sendType 发送方式			
	 */			
	public void setSendType(String sendType) {			
	    this.sendType = sendType;			
	}			
				
	/**			
	 *<pre> 执行获取发送日期处理 </pre>			
	 * @return sendDate1 发送日期			
	 */			
	public String getSendDate1() {			
	    return sendDate1;			
	}			
				
	/**			
	 *<pre> 执行设定发送日期处理 </pre>			
	 * @param sendDate1 发送日期			
	 */			
	public void setSendDate1(String sendDate1) {			
	    this.sendDate1 = sendDate1;			
	}			
				
	/**			
	 *<pre> 执行获取发送时间处理 </pre>			
	 * @return sendTime1 发送时间			
	 */			
	public String getSendTime1() {			
	    return sendTime1;			
	}			
				
	/**			
	 *<pre> 执行设定发送时间处理 </pre>			
	 * @param sendTime1 发送时间			
	 */			
	public void setSendTime1(String sendTime1) {			
	    this.sendTime1 = sendTime1;			
	}			
				
	/**			
	 *<pre> 执行获取中心代码处理 </pre>			
	 * @return centerId 中心代码			
	 */			
	public String getCenterId() {			
	    return centerId;			
	}			
				
	/**			
	 *<pre> 执行设定中心代码处理 </pre>			
	 * @param centerId 中心代码			
	 */			
	public void setCenterId(String centerId) {			
	    this.centerId = centerId;			
	}			
				
	/**			
	 *<pre> 执行获取消息内容处理 </pre>			
	 * @return detail 消息内容			
	 */			
	public String getDetail() {			
	    return detail;			
	}			
				
	/**			
	 *<pre> 执行设定消息内容处理 </pre>			
	 * @param detail 消息内容			
	 */			
	public void setDetail(String detail) {			
	    this.detail = detail;			
	}			
				
	/**			
	 *<pre> 执行获取消息主题处理 </pre>			
	 * @return theme 消息主题			
	 */			
	public String getTheme() {			
	    return theme;			
	}			
				
	/**			
	 *<pre> 执行设定消息主题处理 </pre>			
	 * @param theme 消息主题			
	 */			
	public void setTheme(String theme) {			
	    this.theme = theme;			
	}			
				
	/**			
	 *<pre> 执行获取消息渠道处理 </pre>			
	 * @return chanel 消息渠道			
	 */			
	public String getChanel() {			
	    return chanel;			
	}			
				
	/**			
	 *<pre> 执行设定消息渠道处理 </pre>			
	 * @param chanel 消息渠道			
	 */			
	public void setChanel(String chanel) {			
	    this.chanel = chanel;			
	}			
				
	/**			
	 *<pre> 执行获取备用字段1处理 </pre>			
	 * @return freeuse1 备用字段1			
	 */			
	public String getFreeuse1() {			
	    return freeuse1;			
	}			
				
	/**			
	 *<pre> 执行设定备用字段1处理 </pre>			
	 * @param freeuse1 备用字段1			
	 */			
	public void setFreeuse1(String freeuse1) {			
	    this.freeuse1 = freeuse1;			
	}			
				
	/**			
	 *<pre> 执行获取备用字段2处理 </pre>			
	 * @return freeuse2 备用字段2			
	 */			
	public String getFreeuse2() {			
	    return freeuse2;			
	}			
				
	/**			
	 *<pre> 执行设定备用字段2处理 </pre>			
	 * @param freeuse2 备用字段2			
	 */			
	public void setFreeuse2(String freeuse2) {			
	    this.freeuse2 = freeuse2;			
	}			
				
	/**			
	 *<pre> 执行获取备用字段3处理 </pre>			
	 * @return freeuse3 备用字段3			
	 */			
	public String getFreeuse3() {			
	    return freeuse3;			
	}			
				
	/**			
	 *<pre> 执行设定备用字段3处理 </pre>			
	 * @param freeuse3 备用字段3			
	 */			
	public void setFreeuse3(String freeuse3) {			
	    this.freeuse3 = freeuse3;			
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}			

	

}
