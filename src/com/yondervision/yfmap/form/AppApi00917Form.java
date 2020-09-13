package com.yondervision.yfmap.form;

/** 
* 贷款额度试算——包钢
* @ClassName: AppApi00917Form 
* @Description: TODO
* 
*/ 
public class AppApi00917Form extends AppApiCommonForm{
	
	/**
	 * 01：单身或配偶无公积金
	 * 02：配偶在包钢缴存
	 * 03：配偶在包头缴存
	 * 04：单身有共同借款人
	 */
	private String ssType;
	
	/**
	 * 配偶/共同贷款人姓名
	 */
	private String mateName;
	
	/**
	 * 配偶/共同贷款人身份证号
	 */
	private String mateIdcardNumber;
	
	/**
	 * 缴存基数
	 */
	private String jcjs;
	
	/**
	 * 缴存余额
	 */
	private String yjce;
	
	/**
	 * 账户余额
	 */
	private String zhye;
	
	private String trancode;
	
	private String age;
	
	private String sex;

	public String getSsType() {
		return ssType;
	}

	public void setSsType(String ssType) {
		this.ssType = ssType;
	}

	public String getMateName() {
		return mateName;
	}

	public void setMateName(String mateName) {
		this.mateName = mateName;
	}

	public String getMateIdcardNumber() {
		return mateIdcardNumber;
	}

	public void setMateIdcardNumber(String mateIdcardNumber) {
		this.mateIdcardNumber = mateIdcardNumber;
	}

	public String getJcjs() {
		return jcjs;
	}

	public void setJcjs(String jcjs) {
		this.jcjs = jcjs;
	}

	public String getYjce() {
		return yjce;
	}

	public void setYjce(String yjce) {
		this.yjce = yjce;
	}

	public String getZhye() {
		return zhye;
	}

	public void setZhye(String zhye) {
		this.zhye = zhye;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
