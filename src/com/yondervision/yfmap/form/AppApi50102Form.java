package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi50102Form 
* @Description: 智能问答
* @author syw
* 
*/ 
public class AppApi50102Form extends AppApiCommonForm{
	private String sessionId;
	/**
	 * type枚举类型为：hotaType:查询热点目录
					hotaTitle:查询热点问题
					normal:正常提问
					about:相关问题提问*/
	private String type;
	/**
	 * 	normal类型时，ask为问题内容
		hotaType类型时，ask为nodeId
		hotaTitle类型时，ask为titleId
		about类型时，ask为askId*/
	private String questionAsk;
	private String alertAsk;
	private String answerId;
	private String phoneNumber;
	private String email;
	private String source;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuestionAsk() {
		return questionAsk;
	}
	public void setQuestionAsk(String questionAsk) {
		this.questionAsk = questionAsk;
	}
	public String getAlertAsk() {
		return alertAsk;
	}
	public void setAlertAsk(String alertAsk) {
		this.alertAsk = alertAsk;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
