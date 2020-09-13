package com.yondervision.yfmap.result.zhuzhou;
/**
 *  个人账户基本信息查询
 * @author qinxla
 *
 */
public class AppApi00101ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";
	
	//---------------------简版新加
	private String status="";
	private String accnum="";
	private String fullname="";
	private String bal="";
	private String paydate="";
	
	//---------------------简版新加
	
	public String getStatus() {
		return status;
	}
	public String getAccnum() {
		return accnum;
	}
	public String getFullname() {
		return fullname;
	}
	public String getBal() {
		return bal;
	}
	public String getPaydate() {
		return paydate;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public void setBal(String bal) {
		this.bal = bal;
	}
	public void setPaydate(String paydate) {
		this.paydate = paydate;
	}
	//=======补充  开始=========
	private String ertinumchkflag = "";

	private String accexistflag     = "";
	private String agentinstcode    = "";
	private String agentop          = "";
	private String approveop        = "";
	private String attworkdate      = "";
	private String begintdate       = "";
	private String begpayym         = "";
	private String belongtodept     = "";
	private String birthday         = "";
	private String byearbal         = "";
	private String centerid         = "";
	private String certinumchkflag  = "";
	private String chkop            = "";
	private String clsaccint        = "";
	private String crelevel         = "";
	private String csny             = "";
	private String custid           = "";
	private String edudeg           = "";
	private String email            = "";
	private String flag             = "";
	private String freeuse1         = "";
	private String freeuse2         = "";
	private String freeuse3         = "";
	private String freeuse4         = "";
	private String frzamt           = "";
	private String fundsouflag      = "";
	private String gddhhm           = "";
	private String grckzhhm         = "";
	private String grckzhkhyhdm     = "";
	private String grckzhkhyhmc     = "";
	private String grzh             = "";
	private String grzhdngjye       = "";
	private String grzhsnjzye       = "";
	private String hyzk             = "";
	private String increintaccu     = "";
	private String indiacctype      = "";
	private String indibpaymap      = "";
	private String indipaymap       = "";
	private String indisoicode      = "";
	private String ipartlpaym       = "";
	private String ipartpaymap      = "";
	private String jkhtbh           = "";
	private String jtysr            = "";
	private String useflag          = "";
	private String validflag        = "";
	private String workid           = "";
	private String wwqyztbs         = "";
	private String xhrq             = "";
	private String xhyy             = "";
	private String xingbie          = "";
	private String xmqp             = "";
	private String xueli            = "";
	private String yadjamt          = "";
	private String ydrawamt         = "";
	private String ynspayamt        = "";
	private String ypayamt          = "";
	private String yunctnamt        = "";
	private String yzbm             = "";
	private String zhichen          = "";
	private String zhiwu            = "";
	private String zhiye            = "";
	private String jtzz             = "";
	private String keepintaccu      = "";
	private String lastbal          = "";
	private String lastdrawdate     = "";
	private String lasttransdate    = "";
	private String machinetime      = "";
	private String monthinc         = "";
	private String msn              = "";
	private String proptype         = "";
	private String qq               = "";
	private String smrzbs           = "";
	private String stpayamt         = "";
	private String transchannel     = "";
	private String trustdate        = "";
	private String uapartlpaym      = "";
	private String uapartpaymap     = "";
	private String unitapayamt      = "";
	private String unitastprop      = "";
	private String upartlpaym       = "";
	private String upartpaymap   = "";  
	/** 个人账户状态汉化*/
	private String des = "";
	//=======补充  结束=========
	/** 姓名*/
	private String xingming = "";
	/** 单位账号*/
	private String dwzh = "";
	/** 单位名称*/
	private String unitaccname = "";
	/** 证件类型*/
	private String zjlx = "";
	/** 证件号码*/
	private String zjhm = "";
	/** 手机号码*/
	private String sjhm = "";
	/** 开户日期*/
	private String khrq = "";
	/** 个人账号状态*/
	private String grzhzt = "";
	/** 冻结标志*/
	private String frzflag = "";
	/** 缴至年月*/
	private String jzny = "";
	/** 个人缴存基数*/
	private String grjcjs = "";
	/** 单位比例*/
	private String unitprop = "";
	/** 个人缴存比例*/
	private String indiprop = "";
	/** 月汇缴总额*/
	private String indipaysum = "";
	/** 单位月缴存额*/
	private String dwyjce = "";
	/** 个人月缴存额*/
	private String gryjce = "";
	/** 个人账户余额*/
	private String grzhye = "";
	/** 联名卡号*/
	private String cardno = "";
	/** 是否贷款*/
	private String isloanflag = "";
	
	/** 关联人1关系*/
	private String srelation1 = "";
	/** 关联人1姓名*/
	private String relname1 = "";
	/** 关联人1性别*/
	private String relsex1 = "";
	/** 关联人1身份证号*/
	private String relcertinum1 = "";
	/** 关联人1手机号*/
	private String phone1 = "";
	/** 关联人1是否中心用户*/
	private String isuser1 = "";
	/** 关联人1客户号*/
	private String relcustid1 = "";
	
	/** 关联人2关系*/
	private String srelation2 = "";
	/** 关联人2姓名*/
	private String relname2 = "";
	/** 关联人2性别*/
	private String relsex2 = "";
	/** 关联人2身份证号*/
	private String relcertinum2 = "";
	/** 关联人2手机号*/
	private String phone2 = "";
	/** 关联人2是否中心用户*/
	private String isuser2 = "";
	/** 关联人2客户号*/
	private String relcustid2 = "";
	
	/** 关联人3关系*/
	private String srelation3 = "";
	/** 关联人3姓名*/
	private String relname3 = "";
	/** 关联人3性别*/
	private String relsex3 = "";
	/** 关联人3身份证号*/
	private String relcertinum3 = "";
	/** 关联人3手机号*/
	private String phone3 = "";
	/** 关联人3是否中心用户*/
	private String isuser3 = "";
	/** 关联人3客户号*/
	private String relcustid3 = "";
	
	/** 关联人4关系*/
	private String srelation4 = "";
	/** 关联人4姓名*/
	private String relname4 = "";
	/** 关联人4性别*/
	private String relsex4 = "";
	/** 关联人4身份证号*/
	private String relcertinum4 = "";
	/** 关联人4手机号*/
	private String phone4 = "";
	/** 关联人4是否中心用户*/
	private String isuser4 = "";
	/** 关联人4客户号*/
	private String relcustid4 = "";
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
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getUnitaccname() {
		return unitaccname;
	}
	public void setUnitaccname(String unitaccname) {
		this.unitaccname = unitaccname;
	}
	public String getZjlx() {
		return zjlx;
	}
	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getSjhm() {
		return sjhm;
	}
	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}
	public String getKhrq() {
		return khrq;
	}
	public void setKhrq(String khrq) {
		this.khrq = khrq;
	}
	public String getGrzhzt() {
		return grzhzt;
	}
	public void setGrzhzt(String grzhzt) {
		this.grzhzt = grzhzt;
	}
	public String getFrzflag() {
		return frzflag;
	}
	public void setFrzflag(String frzflag) {
		this.frzflag = frzflag;
	}
	public String getJzny() {
		return jzny;
	}
	public void setJzny(String jzny) {
		this.jzny = jzny;
	}
	public String getGrjcjs() {
		return grjcjs;
	}
	public void setGrjcjs(String grjcjs) {
		this.grjcjs = grjcjs;
	}
	public String getUnitprop() {
		return unitprop;
	}
	public void setUnitprop(String unitprop) {
		this.unitprop = unitprop;
	}
	public String getIndiprop() {
		return indiprop;
	}
	public void setIndiprop(String indiprop) {
		this.indiprop = indiprop;
	}
	public String getIndipaysum() {
		return indipaysum;
	}
	public void setIndipaysum(String indipaysum) {
		this.indipaysum = indipaysum;
	}
	public String getDwyjce() {
		return dwyjce;
	}
	public void setDwyjce(String dwyjce) {
		this.dwyjce = dwyjce;
	}
	public String getGryjce() {
		return gryjce;
	}
	public void setGryjce(String gryjce) {
		this.gryjce = gryjce;
	}
	public String getGrzhye() {
		return grzhye;
	}
	public void setGrzhye(String grzhye) {
		this.grzhye = grzhye;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getIsloanflag() {
		return isloanflag;
	}
	public void setIsloanflag(String isloanflag) {
		this.isloanflag = isloanflag;
	}
	public String getSrelation1() {
		return srelation1;
	}
	public void setSrelation1(String srelation1) {
		this.srelation1 = srelation1;
	}
	public String getRelname1() {
		return relname1;
	}
	public void setRelname1(String relname1) {
		this.relname1 = relname1;
	}
	public String getRelsex1() {
		return relsex1;
	}
	public void setRelsex1(String relsex1) {
		this.relsex1 = relsex1;
	}
	public String getRelcertinum1() {
		return relcertinum1;
	}
	public void setRelcertinum1(String relcertinum1) {
		this.relcertinum1 = relcertinum1;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getIsuser1() {
		return isuser1;
	}
	public void setIsuser1(String isuser1) {
		this.isuser1 = isuser1;
	}
	public String getRelcustid1() {
		return relcustid1;
	}
	public void setRelcustid1(String relcustid1) {
		this.relcustid1 = relcustid1;
	}
	public String getSrelation2() {
		return srelation2;
	}
	public void setSrelation2(String srelation2) {
		this.srelation2 = srelation2;
	}
	public String getRelname2() {
		return relname2;
	}
	public void setRelname2(String relname2) {
		this.relname2 = relname2;
	}
	public String getRelsex2() {
		return relsex2;
	}
	public void setRelsex2(String relsex2) {
		this.relsex2 = relsex2;
	}
	public String getRelcertinum2() {
		return relcertinum2;
	}
	public void setRelcertinum2(String relcertinum2) {
		this.relcertinum2 = relcertinum2;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getIsuser2() {
		return isuser2;
	}
	public void setIsuser2(String isuser2) {
		this.isuser2 = isuser2;
	}
	public String getRelcustid2() {
		return relcustid2;
	}
	public void setRelcustid2(String relcustid2) {
		this.relcustid2 = relcustid2;
	}
	public String getSrelation3() {
		return srelation3;
	}
	public void setSrelation3(String srelation3) {
		this.srelation3 = srelation3;
	}
	public String getRelname3() {
		return relname3;
	}
	public void setRelname3(String relname3) {
		this.relname3 = relname3;
	}
	public String getRelsex3() {
		return relsex3;
	}
	public void setRelsex3(String relsex3) {
		this.relsex3 = relsex3;
	}
	public String getRelcertinum3() {
		return relcertinum3;
	}
	public void setRelcertinum3(String relcertinum3) {
		this.relcertinum3 = relcertinum3;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getIsuser3() {
		return isuser3;
	}
	public void setIsuser3(String isuser3) {
		this.isuser3 = isuser3;
	}
	public String getRelcustid3() {
		return relcustid3;
	}
	public void setRelcustid3(String relcustid3) {
		this.relcustid3 = relcustid3;
	}
	public String getSrelation4() {
		return srelation4;
	}
	public void setSrelation4(String srelation4) {
		this.srelation4 = srelation4;
	}
	public String getRelname4() {
		return relname4;
	}
	public void setRelname4(String relname4) {
		this.relname4 = relname4;
	}
	public String getRelsex4() {
		return relsex4;
	}
	public void setRelsex4(String relsex4) {
		this.relsex4 = relsex4;
	}
	public String getRelcertinum4() {
		return relcertinum4;
	}
	public void setRelcertinum4(String relcertinum4) {
		this.relcertinum4 = relcertinum4;
	}
	public String getPhone4() {
		return phone4;
	}
	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}
	public String getIsuser4() {
		return isuser4;
	}
	public void setIsuser4(String isuser4) {
		this.isuser4 = isuser4;
	}
	public String getRelcustid4() {
		return relcustid4;
	}
	public void setRelcustid4(String relcustid4) {
		this.relcustid4 = relcustid4;
	}
	public String getErtinumchkflag() {
		return ertinumchkflag;
	}
	public void setErtinumchkflag(String ertinumchkflag) {
		this.ertinumchkflag = ertinumchkflag;
	}
	public String getAccexistflag() {
		return accexistflag;
	}
	public void setAccexistflag(String accexistflag) {
		this.accexistflag = accexistflag;
	}
	public String getAgentinstcode() {
		return agentinstcode;
	}
	public void setAgentinstcode(String agentinstcode) {
		this.agentinstcode = agentinstcode;
	}
	public String getAgentop() {
		return agentop;
	}
	public void setAgentop(String agentop) {
		this.agentop = agentop;
	}
	public String getApproveop() {
		return approveop;
	}
	public void setApproveop(String approveop) {
		this.approveop = approveop;
	}
	public String getAttworkdate() {
		return attworkdate;
	}
	public void setAttworkdate(String attworkdate) {
		this.attworkdate = attworkdate;
	}
	public String getBegintdate() {
		return begintdate;
	}
	public void setBegintdate(String begintdate) {
		this.begintdate = begintdate;
	}
	public String getBegpayym() {
		return begpayym;
	}
	public void setBegpayym(String begpayym) {
		this.begpayym = begpayym;
	}
	public String getBelongtodept() {
		return belongtodept;
	}
	public void setBelongtodept(String belongtodept) {
		this.belongtodept = belongtodept;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getByearbal() {
		return byearbal;
	}
	public void setByearbal(String byearbal) {
		this.byearbal = byearbal;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getCertinumchkflag() {
		return certinumchkflag;
	}
	public void setCertinumchkflag(String certinumchkflag) {
		this.certinumchkflag = certinumchkflag;
	}
	public String getChkop() {
		return chkop;
	}
	public void setChkop(String chkop) {
		this.chkop = chkop;
	}
	public String getClsaccint() {
		return clsaccint;
	}
	public void setClsaccint(String clsaccint) {
		this.clsaccint = clsaccint;
	}
	public String getCrelevel() {
		return crelevel;
	}
	public void setCrelevel(String crelevel) {
		this.crelevel = crelevel;
	}
	public String getCsny() {
		return csny;
	}
	public void setCsny(String csny) {
		this.csny = csny;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getEdudeg() {
		return edudeg;
	}
	public void setEdudeg(String edudeg) {
		this.edudeg = edudeg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public String getFreeuse4() {
		return freeuse4;
	}
	public void setFreeuse4(String freeuse4) {
		this.freeuse4 = freeuse4;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getFrzamt() {
		return frzamt;
	}
	public void setFrzamt(String frzamt) {
		this.frzamt = frzamt;
	}
	public String getFundsouflag() {
		return fundsouflag;
	}
	public void setFundsouflag(String fundsouflag) {
		this.fundsouflag = fundsouflag;
	}
	public String getGddhhm() {
		return gddhhm;
	}
	public void setGddhhm(String gddhhm) {
		this.gddhhm = gddhhm;
	}
	public String getGrckzhhm() {
		return grckzhhm;
	}
	public void setGrckzhhm(String grckzhhm) {
		this.grckzhhm = grckzhhm;
	}
	public String getGrckzhkhyhdm() {
		return grckzhkhyhdm;
	}
	public void setGrckzhkhyhdm(String grckzhkhyhdm) {
		this.grckzhkhyhdm = grckzhkhyhdm;
	}
	public String getGrckzhkhyhmc() {
		return grckzhkhyhmc;
	}
	public void setGrckzhkhyhmc(String grckzhkhyhmc) {
		this.grckzhkhyhmc = grckzhkhyhmc;
	}
	public String getGrzh() {
		return grzh;
	}
	public void setGrzh(String grzh) {
		this.grzh = grzh;
	}
	public String getGrzhdngjye() {
		return grzhdngjye;
	}
	public void setGrzhdngjye(String grzhdngjye) {
		this.grzhdngjye = grzhdngjye;
	}
	public String getGrzhsnjzye() {
		return grzhsnjzye;
	}
	public void setGrzhsnjzye(String grzhsnjzye) {
		this.grzhsnjzye = grzhsnjzye;
	}
	public String getHyzk() {
		return hyzk;
	}
	public void setHyzk(String hyzk) {
		this.hyzk = hyzk;
	}
	public String getIncreintaccu() {
		return increintaccu;
	}
	public void setIncreintaccu(String increintaccu) {
		this.increintaccu = increintaccu;
	}
	public String getIndiacctype() {
		return indiacctype;
	}
	public void setIndiacctype(String indiacctype) {
		this.indiacctype = indiacctype;
	}
	public String getIndibpaymap() {
		return indibpaymap;
	}
	public void setIndibpaymap(String indibpaymap) {
		this.indibpaymap = indibpaymap;
	}
	public String getIndipaymap() {
		return indipaymap;
	}
	public void setIndipaymap(String indipaymap) {
		this.indipaymap = indipaymap;
	}
	public String getIndisoicode() {
		return indisoicode;
	}
	public void setIndisoicode(String indisoicode) {
		this.indisoicode = indisoicode;
	}
	public String getIpartlpaym() {
		return ipartlpaym;
	}
	public void setIpartlpaym(String ipartlpaym) {
		this.ipartlpaym = ipartlpaym;
	}
	public String getIpartpaymap() {
		return ipartpaymap;
	}
	public void setIpartpaymap(String ipartpaymap) {
		this.ipartpaymap = ipartpaymap;
	}
	public String getJkhtbh() {
		return jkhtbh;
	}
	public void setJkhtbh(String jkhtbh) {
		this.jkhtbh = jkhtbh;
	}
	public String getJtysr() {
		return jtysr;
	}
	public void setJtysr(String jtysr) {
		this.jtysr = jtysr;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	public String getValidflag() {
		return validflag;
	}
	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}
	public String getWorkid() {
		return workid;
	}
	public void setWorkid(String workid) {
		this.workid = workid;
	}
	public String getWwqyztbs() {
		return wwqyztbs;
	}
	public void setWwqyztbs(String wwqyztbs) {
		this.wwqyztbs = wwqyztbs;
	}
	public String getXhrq() {
		return xhrq;
	}
	public void setXhrq(String xhrq) {
		this.xhrq = xhrq;
	}
	public String getXhyy() {
		return xhyy;
	}
	public void setXhyy(String xhyy) {
		this.xhyy = xhyy;
	}
	public String getXingbie() {
		return xingbie;
	}
	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	public String getXmqp() {
		return xmqp;
	}
	public void setXmqp(String xmqp) {
		this.xmqp = xmqp;
	}
	public String getXueli() {
		return xueli;
	}
	public void setXueli(String xueli) {
		this.xueli = xueli;
	}
	public String getYadjamt() {
		return yadjamt;
	}
	public void setYadjamt(String yadjamt) {
		this.yadjamt = yadjamt;
	}
	public String getYdrawamt() {
		return ydrawamt;
	}
	public void setYdrawamt(String ydrawamt) {
		this.ydrawamt = ydrawamt;
	}
	public String getYnspayamt() {
		return ynspayamt;
	}
	public void setYnspayamt(String ynspayamt) {
		this.ynspayamt = ynspayamt;
	}
	public String getYpayamt() {
		return ypayamt;
	}
	public void setYpayamt(String ypayamt) {
		this.ypayamt = ypayamt;
	}
	public String getYunctnamt() {
		return yunctnamt;
	}
	public void setYunctnamt(String yunctnamt) {
		this.yunctnamt = yunctnamt;
	}
	public String getYzbm() {
		return yzbm;
	}
	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}
	public String getZhichen() {
		return zhichen;
	}
	public void setZhichen(String zhichen) {
		this.zhichen = zhichen;
	}
	public String getZhiwu() {
		return zhiwu;
	}
	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}
	public String getZhiye() {
		return zhiye;
	}
	public void setZhiye(String zhiye) {
		this.zhiye = zhiye;
	}
	public String getJtzz() {
		return jtzz;
	}
	public void setJtzz(String jtzz) {
		this.jtzz = jtzz;
	}
	public String getKeepintaccu() {
		return keepintaccu;
	}
	public void setKeepintaccu(String keepintaccu) {
		this.keepintaccu = keepintaccu;
	}
	public String getLastbal() {
		return lastbal;
	}
	public void setLastbal(String lastbal) {
		this.lastbal = lastbal;
	}
	public String getLastdrawdate() {
		return lastdrawdate;
	}
	public void setLastdrawdate(String lastdrawdate) {
		this.lastdrawdate = lastdrawdate;
	}
	public String getLasttransdate() {
		return lasttransdate;
	}
	public void setLasttransdate(String lasttransdate) {
		this.lasttransdate = lasttransdate;
	}
	public String getMachinetime() {
		return machinetime;
	}
	public void setMachinetime(String machinetime) {
		this.machinetime = machinetime;
	}
	public String getMonthinc() {
		return monthinc;
	}
	public void setMonthinc(String monthinc) {
		this.monthinc = monthinc;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getProptype() {
		return proptype;
	}
	public void setProptype(String proptype) {
		this.proptype = proptype;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getSmrzbs() {
		return smrzbs;
	}
	public void setSmrzbs(String smrzbs) {
		this.smrzbs = smrzbs;
	}
	public String getStpayamt() {
		return stpayamt;
	}
	public void setStpayamt(String stpayamt) {
		this.stpayamt = stpayamt;
	}
	public String getTranschannel() {
		return transchannel;
	}
	public void setTranschannel(String transchannel) {
		this.transchannel = transchannel;
	}
	public String getTrustdate() {
		return trustdate;
	}
	public void setTrustdate(String trustdate) {
		this.trustdate = trustdate;
	}
	public String getUapartlpaym() {
		return uapartlpaym;
	}
	public void setUapartlpaym(String uapartlpaym) {
		this.uapartlpaym = uapartlpaym;
	}
	public String getUapartpaymap() {
		return uapartpaymap;
	}
	public void setUapartpaymap(String uapartpaymap) {
		this.uapartpaymap = uapartpaymap;
	}
	public String getUnitapayamt() {
		return unitapayamt;
	}
	public void setUnitapayamt(String unitapayamt) {
		this.unitapayamt = unitapayamt;
	}
	public String getUnitastprop() {
		return unitastprop;
	}
	public void setUnitastprop(String unitastprop) {
		this.unitastprop = unitastprop;
	}
	public String getUpartlpaym() {
		return upartlpaym;
	}
	public void setUpartlpaym(String upartlpaym) {
		this.upartlpaym = upartlpaym;
	}
	public String getUpartpaymap() {
		return upartpaymap;
	}
	public void setUpartpaymap(String upartpaymap) {
		this.upartpaymap = upartpaymap;
	}
	
	
}
