package com.yondervision.yfmap.result;

import com.yondervision.yfmap.socket.ReqHeadbean;


/** 
* @ClassName: AppApi3001Result 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 10:57:51 AM   
* 
*/ 
public class AppApi3001Result extends ReqHeadbean{
	/** 关键字 */						
	private String accnum = "";						
	/** 姓名 */						
	private String title = "";						
	/** 结息日期 */						
	private String detail = "";						
	/** 主题 */						
	private String theme = "";
	/** 渠道 */						
	private String chanel = "";
	/** 本期缴存（含转入） */						
	private String freeuse1 = "";			
	/** 本期提取 */						
	private String freeuse2 = "";						
	/** 利息 */						
	private String freeuse3 = "";
	/** 城市中心ID */						
	private String centerId = "";
	/** 城市中心 */						
	private String centreName = "";
	/** 城市中心 */						
	private String username = "";
	/** 用户 */						
	private String userId = "";
	
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	public String getCentreName() {
		return centreName;
	}
	public void setCentreName(String centreName) {
		this.centreName = centreName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
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
	public String getChanel() {
		return chanel;
	}
	public void setChanel(String chanel) {
		this.chanel = chanel;
	}
	

}
