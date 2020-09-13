package com.yondervision.yfmap.form;


public class AppApi01103Form extends AppApiCommonForm {
	private String bodyCardNumber=""; // -身份证号
	private String cardnoNumber=""; // -联名卡号
	private String openflag=""; // -开通标志
	private String channelflag=""; // -渠道标志
	public String getBodyCardNumber() {
		return bodyCardNumber;
	}
	public void setBodyCardNumber(String bodyCardNumber) {
		this.bodyCardNumber = bodyCardNumber;
	}
	public String getCardnoNumber() {
		return cardnoNumber;
	}
	public void setCardnoNumber(String cardnoNumber) {
		this.cardnoNumber = cardnoNumber;
	}
	public String getOpenflag() {
		return openflag;
	}
	public void setOpenflag(String openflag) {
		this.openflag = openflag;
	}
	public String getChannelflag() {
		return channelflag;
	}
	public void setChannelflag(String channelflag) {
		this.channelflag = channelflag;
	}
	
}
