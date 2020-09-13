package com.yondervision.yfmap.form;

/** 
* @ClassName: AppApi10101Form 
* @Description: 提取明细查询请求Form
* @author Caozhongyan
* @date Sep 27, 2013 9:15:58 AM 
* 
*/ 
public class AppApi10101Form extends AppApiCommonForm {
	private String selectType;						
	/** 查询信息条件值 */						
	private String selectValue;						
	private String positionX;
	
	private String positionY;
	
	private String websiteType="";
	private String areaId="";
	private String websiteName="";
	private String address="";
	private String businessName="";
	private String pagenum="";
	private String pagerows = "";
	private String sendDate="";
	private String sendTime="";
	private String sendSeqno="";
	private String key="";
	private String buzhType="";
	private String type="";
	private String accnum="";
	private String certinum="";	
	private String freeuse1="";
	private String freeuse2="";
	private String freeuse3="";
	private String transCode="";
	private String idcardNumber="";
	private String ispaging="";//是否分页
	
	public String getIspaging() {
		return ispaging;
	}
	public void setIspaging(String ispaging) {
		this.ispaging = ispaging;
	}
	public String getPositionX() {
		return positionX;
	}
	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}
	public String getPositionY() {
		return positionY;
	}
	public void setPositionY(String positionY) {
		this.positionY = positionY;
	}						
	/**						
	 *<pre> 执行获取查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @return selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public String getSelectType() {						
	    return selectType;						
	}						
							
	/**						
	 *<pre> 执行设定查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)处理 </pre>						
	 * @param selectType 查询类型(1.模糊查询信息;2.查询全部信息;3.查询区域信息)						
	 */						
	public void setSelectType(String selectType) {						
	    this.selectType = selectType;						
	}						
							
	/**						
	 *<pre> 执行获取查询信息条件值处理 </pre>						
	 * @return selectValue 查询信息条件值						
	 */						
	public String getSelectValue() {						
	    return selectValue;						
	}						
							
	/**						
	 *<pre> 执行设定查询信息条件值处理 </pre>						
	 * @param selectValue 查询信息条件值						
	 */						
	public void setSelectValue(String selectValue) {						
	    this.selectValue = selectValue;						
	}
	public String getWebsiteType() {
		return websiteType;
	}
	public void setWebsiteType(String websiteType) {
		this.websiteType = websiteType;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPagerows() {
		return pagerows;
	}
	public void setPagerows(String pagerows) {
		this.pagerows = pagerows;
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
	public String getBuzhType() {
		return buzhType;
	}
	public void setBuzhType(String buzhType) {
		this.buzhType = buzhType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getCertinum() {
		return certinum;
	}
	public void setCertinum(String certinum) {
		this.certinum = certinum;
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
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getIdcardNumber() {
		return idcardNumber;
	}
	public void setIdcardNumber(String idcardNumber) {
		this.idcardNumber = idcardNumber;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}						

}
