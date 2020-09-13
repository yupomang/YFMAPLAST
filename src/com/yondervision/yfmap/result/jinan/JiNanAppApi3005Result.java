package com.yondervision.yfmap.result.jinan;

import java.util.List;

import com.yondervision.yfmap.result.AppApi300201Result;
import com.yondervision.yfmap.result.AppApi3004Result;
import com.yondervision.yfmap.socket.ReqHeadbean;


/** 
* @ClassName: AppApi3004Result 
* @Description: 模板消息
* @author Caozhongyan
* @date Feb 5, 2015 10:57:51 AM   
* 
*/ 
public class JiNanAppApi3005Result extends ReqHeadbean{
	private String smssource="";
	private String theme="";
	/** 数量 */					
	private String num = "";					
	/** 文件名 */					
	private String fileName = "";
	/** 文件路径 */					
	private String filePath = "";	
	/** 发送方式 */					
	private String sendType = "";					
	/** 是否定时 */					
	private String timer = "";					
	/** 消息渠道 */					
	private String chanel = "";					
	/** 发送日期 */					
	private String sendDate1 = "";					
	/** 发送时间 */					
	private String sendTime1 = "";					
	/** 中心代码 */					
	private String centerId = "";					
	/** 备用字段1 */					
	private String freeuse1 = "";					
	/** 备用字段2 */					
	private String freeuse2 = "";					
	/** 备用字段3 */					
	private String freeuse3 = "";					
	/** 关键字 */			
	private String sendKey = "";			
	/** 姓名 */			
	private String accname = "";			
	/** 消息标题 */			
	private String title = "";			
	/** 要素 */			
	private String elements = "";			
	/** 消息内容 */			
	private String detail = "";			
	
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
	
	private List<AppApi3004Result> list = null;
	/**					
	 *<pre> 执行获取数量处理 </pre>					
	 * @return num 数量					
	 */					
	public String getNum() {					
	    return num;					
	}					
						
	/**					
	 *<pre> 执行设定数量处理 </pre>					
	 * @param num 数量					
	 */					
	public void setNum(String num) {					
	    this.num = num;					
	}					
						
	/**					
	 *<pre> 执行获取文件名处理 </pre>					
	 * @return fileName 文件名					
	 */					
	public String getFileName() {					
	    return fileName;					
	}					
						
	/**					
	 *<pre> 执行设定文件名处理 </pre>					
	 * @param fileName 文件名					
	 */					
	public void setFileName(String fileName) {					
	    this.fileName = fileName;					
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
	 *<pre> 执行获取是否定时处理 </pre>					
	 * @return timer 是否定时					
	 */					
	public String getTimer() {					
	    return timer;					
	}					
						
	/**					
	 *<pre> 执行设定是否定时处理 </pre>					
	 * @param timer 是否定时					
	 */					
	public void setTimer(String timer) {					
	    this.timer = timer;					
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

	public String getSmssource() {
		return smssource;
	}

	public void setSmssource(String smssource) {
		this.smssource = smssource;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getSendKey() {
		return sendKey;
	}

	public void setSendKey(String sendKey) {
		this.sendKey = sendKey;
	}

	public String getAccname() {
		return accname;
	}

	public void setAccname(String accname) {
		this.accname = accname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getElements() {
		return elements;
	}

	public void setElements(String elements) {
		this.elements = elements;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

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

	public List<AppApi3004Result> getList() {
		return list;
	}

	public void setList(List<AppApi3004Result> list) {
		this.list = list;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


}
