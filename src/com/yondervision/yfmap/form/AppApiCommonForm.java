/**
 * 
 */
package com.yondervision.yfmap.form;

/**
 * APP接口公共Form
 * 
 * @author LinXiaolong
 * 
 */
public class AppApiCommonForm {
	//全加密标志位
	private String AESFlag = "";
	// 中心ID
	private String centerId = "";

	// 用户ID
	private String userId = "";

	// 用户类型
	private String usertype = "";

	// 设备区分
	private String deviceType = "";

	// 设备识别码
	private String deviceToken = "";

	// 当前版本
	private String currenVersion = "";

	// 10-APP,20-微信,30-门户网站,40-网上业务大厅,50-自助终端,60-服务热线,70-手机短信,80-官方微博
	private String channel = "";

	// 业务类型
	private String buzType = "";

	// 应用标识
	// yondervisionapp10,yondervisionweixin20,yondervisionwebsite30,yondervisionwebservice40,yondervisionselfservice50,yondervisiontelservice60,yondervisionsms70,yon
	private String appid = "";

	// 应用KEY
	private String appkey = "";

	// 应用TOKEN
	private String appToken = "";

	// 客户端IP地址
	private String clientIp = "";

	// 身份证 后加入
	private String bodyCardNumber = "";
	
	// 身份证 后加入 包钢
	private String idcardNumber = "";

	// 流水号 	 后加入
	private String tranSeq = "";
	// 联名卡 后加入
	private String cardnoNumber = "";
	
	// 联名卡 后加入
	private String cardno = "";

	// 公积金账户
	private String surplusAccount = "";

	private String logid = "";

	private String sendDate = "";

	private String sendTime = "";

	private String sendSeqno = "";

	private String type = "";

	private String key = "";
	
	//海南-姓名
	private String xingming;
	
	//插入网厅数据库唯一编号-海南
	private String seqno;

	/**
	 * 柜员号
	 */
	private String tellCode = "";
	/**
	 * 机构号
	 */
	private String brcCode = "";
	/**
	 * 实例号
	 */
	private String channelSeq = "";
	/**
	 * 业务日期
	 */
	private String tranDate = "";
	/**
	 * 核心渠道信息
	 */
	private String flag = "";
	/** 账户类型 1身份证号，2 */	
	private String certinumType="";
	
	/**客户端MAC地址*/
	private String clientMAC = "";
	/**UK序列号*/	
	private String UKseq="";

	//核心密码 唐山
	private String pwd;
	
	private String desensitization="";	
	private String coreflag = "";
	
	public String getCoreflag() {
		return coreflag;
	}

	public void setCoreflag(String coreflag) {
		this.coreflag = coreflag;
	}
	
	public String getAESFlag() {
		return AESFlag;
	}

	public void setAESFlag(String aESFlag) {
		AESFlag = aESFlag;
	}

	public String getDesensitization() {
		return desensitization;
	}

	public void setDesensitization(String desensitization) {
		this.desensitization = desensitization;
	}

	public String getCertinumType() {
		return certinumType;
	}
 
	public void setCertinumType(String certinumType) {
		this.certinumType = certinumType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	private String qdly = "";// 大连用渠道来源
	private String wwjym = "";// 大连用外围交易码
	private String jyms = "";// 大连用交易描述
	private String wwfqsj = "";// 大连用外围发起时间

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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * 中心ID取得<BR>
	 * 
	 * @return 中心ID
	 */
	public String getCenterId() {
		return centerId;
	}

	/**
	 * 中心ID赋值<BR>
	 * 
	 * @param centerId
	 *            中心ID
	 */
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}

	/**
	 * 用户ID取得<BR>
	 * 
	 * @return 用户ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 用户ID赋值<BR>
	 * 
	 * @param userId
	 *            用户ID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 公积金账户取得<BR>
	 * 
	 * @return 公积金账户
	 */
	public String getSurplusAccount() {
		return surplusAccount;
	}

	/**
	 * 公积金账户赋值<BR>
	 * 
	 * @param surplusAccount
	 *            公积金账户
	 */
	public void setSurplusAccount(String surplusAccount) {
		this.surplusAccount = surplusAccount;
	}

	/**
	 * 设备区分取得<BR>
	 * 
	 * @return 设备区分
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * 设备区分赋值<BR>
	 * 
	 * @param deviceType
	 *            设备区分
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * 设备识别码取得<BR>
	 * 
	 * @return 设备识别码
	 */
	public String getDeviceToken() {
		return deviceToken;
	}

	/**
	 * 设备识别码赋值<BR>
	 * 
	 * @param deviceToken
	 *            设备识别码
	 */
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	/**
	 * 当前版本取得<BR>
	 * 
	 * @return 当前版本
	 */
	public String getCurrenVersion() {
		return currenVersion;
	}

	/**
	 * 当前版本赋值<BR>
	 * 
	 * @param currenVersion
	 *            当前版本
	 */
	public void setCurrenVersion(String currenVersion) {
		this.currenVersion = currenVersion;
	}

	/**
	 * 业务区分取得<BR>
	 * 
	 * @return 业务区分
	 */
	public String getBuzType() {
		return buzType;
	}

	/**
	 * 业务区分赋值<BR>
	 * 
	 * @param buzType
	 *            业务区分
	 */
	public void setBuzType(String buzType) {
		this.buzType = buzType;
	}

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

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}

	public String getWwfqsj() {
		return wwfqsj;
	}

	public void setWwfqsj(String wwfqsj) {
		this.wwfqsj = wwfqsj;
	}

	public String getTellCode() {
		return tellCode;
	}

	public void setTellCode(String tellCode) {
		this.tellCode = tellCode;
	}

	public String getBrcCode() {
		return brcCode;
	}

	public void setBrcCode(String brcCode) {
		this.brcCode = brcCode;
	}

	public String getChannelSeq() {
		return channelSeq;
	}

	public void setChannelSeq(String channelSeq) {
		this.channelSeq = channelSeq;
	}

	public String getTranDate() {
		return tranDate;
	}

	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTranSeq() {
		return tranSeq;
	}

	public void setTranSeq(String tranSeq) {
		this.tranSeq = tranSeq;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getIdcardNumber() {
		return idcardNumber;
	}

	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}

	public String getSeqno() {
		return seqno;
	}

	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}

	public String getXingming() {
		return xingming;
	}

	public void setXingming(String xingming) {
		this.xingming = xingming;
	}

	public String getClientMAC() {
		return clientMAC;
	}

	public void setClientMAC(String clientMAC) {
		this.clientMAC = clientMAC;
	}

	public String getUKseq() {
		return UKseq;
	}

	public void setUKseq(String uKseq) {
		UKseq = uKseq;
	}
	
}
