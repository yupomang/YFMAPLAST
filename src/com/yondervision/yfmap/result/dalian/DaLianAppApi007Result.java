package com.yondervision.yfmap.result.dalian;



/** 
* @ClassName: ZunYiAppApi002Result 
* @Description: 账户余额明细返回报文封装类
* @author Caozhongyan
* @date Nov 8, 2013 8:58:21 AM   
* 
*/ 
public class DaLianAppApi007Result extends DalianLogResult{
	
	
	/** 姓名*/
	private String grxm = "";
	/** 证件号码*/
	private String zjhm = "";
	/** 个人账号*/
	private String grzh = "";
	/** 贷款银行*/
	private String yhmc = "";
	/** 代扣合同号*/
	private String sdhth = "";
	/** 代扣合同账号*/
	private String sddkzh = "";
	/** 实还年份*/
	private String sddknf = "";
	/** 实还本金*/
	private String htykbj = "";
 	/** 实还利息*/
	private String htyklx = "";
	/** 实还本息*/
	private String shbxhj = "";
	/** 扣减转账还贷额*/
	private String kjzzhde = "";
	/** 应代扣额*/
	private String yhbxe = "";
	/** 代扣状态*/
	private String miaoshu = "";
	/** 代扣时间*/
	private String sjkhrq = "";
	/** 代扣金额*/
	private String skje = "";
	/** 当前页记录数*/
	private String dqyjls = "";	
	/** 当前页数*/
	private String dqys = "";
	/** 总记录数*/
	private String zjls = "";
	/** 总页数*/
	private String zys = "";
	
	/** 
	 *   查询协议编号
	 * 商贷代扣协议编号
	 */
	private String sddkxybh = "";		
	
	public String getSddkxybh() {
		return sddkxybh;
	}
	public void setSddkxybh(String sddkxybh) {
		this.sddkxybh = sddkxybh;
	}
	public String getGrxm() {
		return grxm;
	}
	public void setGrxm(String grxm) {
		this.grxm = grxm;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getSdhth() {
		return sdhth;
	}
	public void setSdhth(String sdhth) {
		this.sdhth = sdhth;
	}
	public String getSddkzh() {
		return sddkzh;
	}
	public void setSddkzh(String sddkzh) {
		this.sddkzh = sddkzh;
	}
	public String getSddknf() {
		return sddknf;
	}
	public void setSddknf(String sddknf) {
		this.sddknf = sddknf;
	}
	public String getHtykbj() {
		return htykbj;
	}
	public void setHtykbj(String htykbj) {
		this.htykbj = htykbj;
	}
	public String getHtyklx() {
		return htyklx;
	}
	public void setHtyklx(String htyklx) {
		this.htyklx = htyklx;
	}
	public String getShbxhj() {
		return shbxhj;
	}
	public void setShbxhj(String shbxhj) {
		this.shbxhj = shbxhj;
	}
	public String getKjzzhde() {
		return kjzzhde;
	}
	public void setKjzzhde(String kjzzhde) {
		this.kjzzhde = kjzzhde;
	}
	public String getYhbxe() {
		return yhbxe;
	}
	public void setYhbxe(String yhbxe) {
		this.yhbxe = yhbxe;
	}
	
	public String getMiaoshu() {
		return miaoshu;
	}
	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
	public String getSjkhrq() {
		return sjkhrq;
	}
	public void setSjkhrq(String sjkhrq) {
		this.sjkhrq = sjkhrq;
	}
	public String getSkje() {
		return skje;
	}
	public void setSkje(String skje) {
		this.skje = skje;
	}
	public String getDqyjls() {
		return dqyjls;
	}
	public void setDqyjls(String dqyjls) {
		this.dqyjls = dqyjls;
	}
	public String getDqys() {
		return dqys;
	}
	public void setDqys(String dqys) {
		this.dqys = dqys;
	}
	
	public String getZjls() {
		return zjls;
	}
	public void setZjls(String zjls) {
		this.zjls = zjls;
	}
	public String getZys() {
		return zys;
	}
	public void setZys(String zys) {
		this.zys = zys;
	}
	
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
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
	/**
	 * 备注1
	 */
	private String freeuse1="";
	/**
	 * 备注2
	 */
	private String freeuse2="";
	/**
	 * 备注3
	 */
	private String freeuse3="";
	
	private String startdate = "";
	private String enddate = "";
	
	private String isloanflag = "";
	private String accinstname = "";
	
	
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getAccinstname() {
		return accinstname;
	}
	public void setAccinstname(String accinstname) {
		this.accinstname = accinstname;
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
}
