package com.yondervision.yfmap.result.zhuzhou;
/**
 * 单位基本信息查询
 * @author HYD-HSP
 *
 */
public class AppApi02001ZHResult {

	/** 状态码*/
	private String recode = "";
	/** 描述*/
	private String msg = "";	
	/** 下传批量文件名*/
	private String filename = "";
	/**	 总页数*/
	private String totalpage = "";
	/**	 总记录条数*/
	private String totalnum = "";
	
	/**	 单位缴存人数 */
	private String  dwjcrs = "";
	/**	 缴至年月 */
	private String  jzny = "";
	/**	 单位封存人数 */
	private String  dwfcrs = "";
	/**	 单位缴存状态 */
	private String  dwjczt = "";
	
	/**	 单位名称 */
	private String  dwmc = "";
	/**	 单位地址 */
	private String  dwdz = "";
	/**	 所在地区 */
	private String  unitareacode = "";
	private String  dwyb = "";
	/**	 单位性质 */
	private String  unitkind = "";
	/**	 机构类型 */
	private String  orgtype = "";
	/**	 主管部门 */
	private String  mngdept = "";
	/**	 主管部门负责人 */
	private String  mngdepter = "";
	/**	 主管部门负责人联系方式 */
	private String  mngdepterphone = "";
	/**	 组织机构代码 */
	private String  zzjgdm = "";
	/**	 同意信用代码 */
	private String  cocietycode = "";
	/**	 所有制性质 */
	private String  ownershipkind = "";
	/**	 单位隶属关系 */
	private String  dwlsgx = "";
	/**	 单位经济类型 */
	private String  dwjjlx = "";
	/**	 单位所属行业 */
	private String  dwsshy = "";
	/**	 单位设立日期 */
	private String  dwslrq = "";
	/**	 单位发薪日 */
	private String  dwfxr = "";
	/**	 单位法定代表人 */
	private String  dwfrdbxm = "";
	/**	 单位法定代表人手机号 */
	private String  dwfrdbsjhm = "";
	/**	 单位法定人证件号码 */
	private String  dwfrdbzjhm = "";
	/**	 单位法定人证件类型 */
	private String  dwfrdbzjlx = "";
	/**	 单位银行账户名称 */
	private String  basebankaccname = "";
	/**	 单位法定人银行账号 */
	private String  basebankaccnum = "";
	/**	单位社保卡  */
	private String  insurancenum = "";
	/**	在职人数  */
	private String  workernum = "";
	/**	是否有劳务派遣用工   */
	private String  isserdispach = "";
	/**	是否托收  */
	private String  cstcollflag = "";
	/**	托收账户银行  */
	private String tszhyh = "";
	/**	托收账户名称  */
	private String tszhhm = "";
	/**	托收账号  */
	private String tszh = "";
	
	//=======补充  开始=========
	private String hproppaynum = "";
	private String indihigprop = "";
	private String indinorprop = "";
	private String indispeprop = "";
	private String lastbal = "";
	private String licensenum = "";
	private String salbankaccnum = "";
	private String salbankcode = "";
	private String selipartlpaym = "";
	private String selipartpaymap = "";
	private String sellpaym = "";
	private String selpaymap = "";
	private String unmspell = "";
	private String validflag = "";
	private String selupartlpaym = "";
	private String selupartpaymap = "";
	private String sproppayamt = "";
	private String sproppaynum = "";
	private String stpayamt = "";
	private String styhdm = "";
	private String styhmc = "";
	private String subcode = "";
	private String supunitaccnum = "";
	private String supunittype = "";
	private String transchannel = "";
	private String unitacctype = "";
	private String unitastprop = "";
	private String unithigprop = "";
	private String unitlinkman = "";
	private String unitlinkphone = "";
	private String unitlinkphone2 = "";
	private String unitnorprop = "";
	private String unitsoicode = "";
	private String unitspeprop = "";
	private String unmfirstspell = "";
	private String linkmancertinum = "";
	private String linkmancertitype = "";
	private String linkmanemail = "";
	private String linkmanmsn = "";
	private String linkmanqq = "";
	private String lmfinpayamt = "";
	private String lmfinpaynum = "";
	private String lmselpayamt      = "";
	private String lmselpaynum      = "";
	private String machinetime      = "";
	private String norproptype      = "";
	private String nproppayamt      = "";
	private String nproppaynum      = "";
	private String opnaccdate       = "";
	private String peoplenum        = "";
	private String postpayflag      = "";
	private String prebal           = "";
	private String prodcode         = "";
	private String propflag         = "";
	private String propreduendym    = "";
	private String remark           = "";
	private String rounddigit       = "";
	private String roundmode        = "";
	private String salbankaccnm     = "";
	private String dwjcje           = "";
	private String finipartpaymap   = "";
	private String finlpaym         = "";
	private String finpaymap        = "";
	private String finuapartlpaym   = "";
	private String finuapartpaymap  = "";
	private String finupartlpaym    = "";
	private String finupartpaymap   = "";
	private String freeuse1         = "";
	private String freeuse2         = "";
	private String freeuse3         = "";
	private String freeuse4         = "";
	private String accbankcode      = "";
	private String accexistflag     = "";
	private String accinstcode      = "";
	private String agentdept        = "";
	private String agentinstcode    = "";
	private String agentop          = "";
	private String appronum         = "";
	private String approveop        = "";
	private String autpayamt        = "";
	private String bankaccnm        = "";
	private String bankaccnum       = "";
	private String bankcode         = "";
	private String baseuppflag      = "";
	private String begpayym         = "";
	private String centerid         = "";
	private String chkop            = "";
	private String cmfinpayamt      = "";
	private String cmfinpaynum      = "";
	private String cmselpayamt      = "";
	private String cmselpaynum      = "";
	private String wwqyztbs         = "";
	private String dwzhye           = "";
	private String dwzhzt           = "";
	private String entflag          = "";
	private String finalflag        = "";
	private String crefilnum        = "";
	private String cydpsum          = "";
	private String dkffe            = "";
	private String dkye             = "";
	private String dwdzxx           = "";
	private String finipartlpaym    = "";
	private String frzamt           = "";
	private String frzflag          = "";
	private String fundsouflag      = "";
	private String hproppayamt      = "";
	private String unitcustid       = "";
	private String dwzh             = "";
	//=======补充  结束=========
	
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
	public String getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}
	public String getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(String totalnum) {
		this.totalnum = totalnum;
	}
	public String getDwmc() {
		return dwmc;
	}
	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}
	public String getDwdz() {
		return dwdz;
	}
	public void setDwdz(String dwdz) {
		this.dwdz = dwdz;
	}
	public String getUnitareacode() {
		return unitareacode;
	}
	public void setUnitareacode(String unitareacode) {
		this.unitareacode = unitareacode;
	}
	public String getDwyb() {
		return dwyb;
	}
	public void setDwyb(String dwyb) {
		this.dwyb = dwyb;
	}
	public String getUnitkind() {
		return unitkind;
	}
	public void setUnitkind(String unitkind) {
		this.unitkind = unitkind;
	}
	public String getOrgtype() {
		return orgtype;
	}
	public void setOrgtype(String orgtype) {
		this.orgtype = orgtype;
	}
	public String getMngdept() {
		return mngdept;
	}
	public void setMngdept(String mngdept) {
		this.mngdept = mngdept;
	}
	public String getMngdepter() {
		return mngdepter;
	}
	public void setMngdepter(String mngdepter) {
		this.mngdepter = mngdepter;
	}
	public String getMngdepterphone() {
		return mngdepterphone;
	}
	public void setMngdepterphone(String mngdepterphone) {
		this.mngdepterphone = mngdepterphone;
	}
	public String getZzjgdm() {
		return zzjgdm;
	}
	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}
	public String getCocietycode() {
		return cocietycode;
	}
	public void setCocietycode(String cocietycode) {
		this.cocietycode = cocietycode;
	}
	public String getOwnershipkind() {
		return ownershipkind;
	}
	public void setOwnershipkind(String ownershipkind) {
		this.ownershipkind = ownershipkind;
	}
	public String getDwlsgx() {
		return dwlsgx;
	}
	public void setDwlsgx(String dwlsgx) {
		this.dwlsgx = dwlsgx;
	}
	public String getDwjjlx() {
		return dwjjlx;
	}
	public void setDwjjlx(String dwjjlx) {
		this.dwjjlx = dwjjlx;
	}
	public String getDwsshy() {
		return dwsshy;
	}
	public void setDwsshy(String dwsshy) {
		this.dwsshy = dwsshy;
	}
	public String getDwslrq() {
		return dwslrq;
	}
	public void setDwslrq(String dwslrq) {
		this.dwslrq = dwslrq;
	}
	public String getDwfxr() {
		return dwfxr;
	}
	public void setDwfxr(String dwfxr) {
		this.dwfxr = dwfxr;
	}
	public String getDwfrdbxm() {
		return dwfrdbxm;
	}
	public void setDwfrdbxm(String dwfrdbxm) {
		this.dwfrdbxm = dwfrdbxm;
	}
	public String getDwfrdbsjhm() {
		return dwfrdbsjhm;
	}
	public void setDwfrdbsjhm(String dwfrdbsjhm) {
		this.dwfrdbsjhm = dwfrdbsjhm;
	}
	public String getDwfrdbzjhm() {
		return dwfrdbzjhm;
	}
	public void setDwfrdbzjhm(String dwfrdbzjhm) {
		this.dwfrdbzjhm = dwfrdbzjhm;
	}
	public String getDwfrdbzjlx() {
		return dwfrdbzjlx;
	}
	public void setDwfrdbzjlx(String dwfrdbzjlx) {
		this.dwfrdbzjlx = dwfrdbzjlx;
	}
	public String getBasebankaccname() {
		return basebankaccname;
	}
	public void setBasebankaccname(String basebankaccname) {
		this.basebankaccname = basebankaccname;
	}
	public String getBasebankaccnum() {
		return basebankaccnum;
	}
	public void setBasebankaccnum(String basebankaccnum) {
		this.basebankaccnum = basebankaccnum;
	}
	public String getInsurancenum() {
		return insurancenum;
	}
	public void setInsurancenum(String insurancenum) {
		this.insurancenum = insurancenum;
	}
	public String getWorkernum() {
		return workernum;
	}
	public void setWorkernum(String workernum) {
		this.workernum = workernum;
	}
	public String getIsserdispach() {
		return isserdispach;
	}
	public void setIsserdispach(String isserdispach) {
		this.isserdispach = isserdispach;
	}
	public String getCstcollflag() {
		return cstcollflag;
	}
	public void setCstcollflag(String cstcollflag) {
		this.cstcollflag = cstcollflag;
	}
	public String getTszhyh() {
		return tszhyh;
	}
	public void setTszhyh(String tszhyh) {
		this.tszhyh = tszhyh;
	}
	public String getTszhhm() {
		return tszhhm;
	}
	public void setTszhhm(String tszhhm) {
		this.tszhhm = tszhhm;
	}
	public String getTszh() {
		return tszh;
	}
	public void setTszh(String tszh) {
		this.tszh = tszh;
	}
	public String getHproppaynum() {
		return hproppaynum;
	}
	public void setHproppaynum(String hproppaynum) {
		this.hproppaynum = hproppaynum;
	}
	public String getIndihigprop() {
		return indihigprop;
	}
	public void setIndihigprop(String indihigprop) {
		this.indihigprop = indihigprop;
	}
	public String getIndinorprop() {
		return indinorprop;
	}
	public void setIndinorprop(String indinorprop) {
		this.indinorprop = indinorprop;
	}
	public String getIndispeprop() {
		return indispeprop;
	}
	public void setIndispeprop(String indispeprop) {
		this.indispeprop = indispeprop;
	}
	public String getLastbal() {
		return lastbal;
	}
	public void setLastbal(String lastbal) {
		this.lastbal = lastbal;
	}
	public String getLicensenum() {
		return licensenum;
	}
	public void setLicensenum(String licensenum) {
		this.licensenum = licensenum;
	}
	public String getSalbankaccnum() {
		return salbankaccnum;
	}
	public void setSalbankaccnum(String salbankaccnum) {
		this.salbankaccnum = salbankaccnum;
	}
	public String getSalbankcode() {
		return salbankcode;
	}
	public void setSalbankcode(String salbankcode) {
		this.salbankcode = salbankcode;
	}
	public String getSelipartlpaym() {
		return selipartlpaym;
	}
	public void setSelipartlpaym(String selipartlpaym) {
		this.selipartlpaym = selipartlpaym;
	}
	public String getSelipartpaymap() {
		return selipartpaymap;
	}
	public void setSelipartpaymap(String selipartpaymap) {
		this.selipartpaymap = selipartpaymap;
	}
	public String getSellpaym() {
		return sellpaym;
	}
	public void setSellpaym(String sellpaym) {
		this.sellpaym = sellpaym;
	}
	public String getSelpaymap() {
		return selpaymap;
	}
	public void setSelpaymap(String selpaymap) {
		this.selpaymap = selpaymap;
	}
	public String getUnmspell() {
		return unmspell;
	}
	public void setUnmspell(String unmspell) {
		this.unmspell = unmspell;
	}
	public String getValidflag() {
		return validflag;
	}
	public void setValidflag(String validflag) {
		this.validflag = validflag;
	}
	public String getSelupartlpaym() {
		return selupartlpaym;
	}
	public void setSelupartlpaym(String selupartlpaym) {
		this.selupartlpaym = selupartlpaym;
	}
	public String getSelupartpaymap() {
		return selupartpaymap;
	}
	public void setSelupartpaymap(String selupartpaymap) {
		this.selupartpaymap = selupartpaymap;
	}
	public String getSproppayamt() {
		return sproppayamt;
	}
	public void setSproppayamt(String sproppayamt) {
		this.sproppayamt = sproppayamt;
	}
	public String getSproppaynum() {
		return sproppaynum;
	}
	public void setSproppaynum(String sproppaynum) {
		this.sproppaynum = sproppaynum;
	}
	public String getStpayamt() {
		return stpayamt;
	}
	public void setStpayamt(String stpayamt) {
		this.stpayamt = stpayamt;
	}
	public String getStyhdm() {
		return styhdm;
	}
	public void setStyhdm(String styhdm) {
		this.styhdm = styhdm;
	}
	public String getStyhmc() {
		return styhmc;
	}
	public void setStyhmc(String styhmc) {
		this.styhmc = styhmc;
	}
	public String getSubcode() {
		return subcode;
	}
	public void setSubcode(String subcode) {
		this.subcode = subcode;
	}
	public String getSupunitaccnum() {
		return supunitaccnum;
	}
	public void setSupunitaccnum(String supunitaccnum) {
		this.supunitaccnum = supunitaccnum;
	}
	public String getSupunittype() {
		return supunittype;
	}
	public void setSupunittype(String supunittype) {
		this.supunittype = supunittype;
	}
	public String getTranschannel() {
		return transchannel;
	}
	public void setTranschannel(String transchannel) {
		this.transchannel = transchannel;
	}
	public String getUnitacctype() {
		return unitacctype;
	}
	public void setUnitacctype(String unitacctype) {
		this.unitacctype = unitacctype;
	}
	public String getUnitastprop() {
		return unitastprop;
	}
	public void setUnitastprop(String unitastprop) {
		this.unitastprop = unitastprop;
	}
	public String getUnithigprop() {
		return unithigprop;
	}
	public void setUnithigprop(String unithigprop) {
		this.unithigprop = unithigprop;
	}
	public String getUnitlinkman() {
		return unitlinkman;
	}
	public void setUnitlinkman(String unitlinkman) {
		this.unitlinkman = unitlinkman;
	}
	public String getUnitlinkphone() {
		return unitlinkphone;
	}
	public void setUnitlinkphone(String unitlinkphone) {
		this.unitlinkphone = unitlinkphone;
	}
	public String getUnitlinkphone2() {
		return unitlinkphone2;
	}
	public void setUnitlinkphone2(String unitlinkphone2) {
		this.unitlinkphone2 = unitlinkphone2;
	}
	public String getUnitnorprop() {
		return unitnorprop;
	}
	public void setUnitnorprop(String unitnorprop) {
		this.unitnorprop = unitnorprop;
	}
	public String getUnitsoicode() {
		return unitsoicode;
	}
	public void setUnitsoicode(String unitsoicode) {
		this.unitsoicode = unitsoicode;
	}
	public String getUnitspeprop() {
		return unitspeprop;
	}
	public void setUnitspeprop(String unitspeprop) {
		this.unitspeprop = unitspeprop;
	}
	public String getUnmfirstspell() {
		return unmfirstspell;
	}
	public void setUnmfirstspell(String unmfirstspell) {
		this.unmfirstspell = unmfirstspell;
	}
	public String getLinkmancertinum() {
		return linkmancertinum;
	}
	public void setLinkmancertinum(String linkmancertinum) {
		this.linkmancertinum = linkmancertinum;
	}
	public String getLinkmancertitype() {
		return linkmancertitype;
	}
	public void setLinkmancertitype(String linkmancertitype) {
		this.linkmancertitype = linkmancertitype;
	}
	public String getLinkmanemail() {
		return linkmanemail;
	}
	public void setLinkmanemail(String linkmanemail) {
		this.linkmanemail = linkmanemail;
	}
	public String getLinkmanmsn() {
		return linkmanmsn;
	}
	public void setLinkmanmsn(String linkmanmsn) {
		this.linkmanmsn = linkmanmsn;
	}
	public String getLinkmanqq() {
		return linkmanqq;
	}
	public void setLinkmanqq(String linkmanqq) {
		this.linkmanqq = linkmanqq;
	}
	public String getLmfinpayamt() {
		return lmfinpayamt;
	}
	public void setLmfinpayamt(String lmfinpayamt) {
		this.lmfinpayamt = lmfinpayamt;
	}
	public String getLmfinpaynum() {
		return lmfinpaynum;
	}
	public void setLmfinpaynum(String lmfinpaynum) {
		this.lmfinpaynum = lmfinpaynum;
	}
	public String getLmselpayamt() {
		return lmselpayamt;
	}
	public void setLmselpayamt(String lmselpayamt) {
		this.lmselpayamt = lmselpayamt;
	}
	public String getLmselpaynum() {
		return lmselpaynum;
	}
	public void setLmselpaynum(String lmselpaynum) {
		this.lmselpaynum = lmselpaynum;
	}
	public String getMachinetime() {
		return machinetime;
	}
	public void setMachinetime(String machinetime) {
		this.machinetime = machinetime;
	}
	public String getNorproptype() {
		return norproptype;
	}
	public void setNorproptype(String norproptype) {
		this.norproptype = norproptype;
	}
	public String getNproppayamt() {
		return nproppayamt;
	}
	public void setNproppayamt(String nproppayamt) {
		this.nproppayamt = nproppayamt;
	}
	public String getNproppaynum() {
		return nproppaynum;
	}
	public void setNproppaynum(String nproppaynum) {
		this.nproppaynum = nproppaynum;
	}
	public String getOpnaccdate() {
		return opnaccdate;
	}
	public void setOpnaccdate(String opnaccdate) {
		this.opnaccdate = opnaccdate;
	}
	public String getPeoplenum() {
		return peoplenum;
	}
	public void setPeoplenum(String peoplenum) {
		this.peoplenum = peoplenum;
	}
	public String getPostpayflag() {
		return postpayflag;
	}
	public void setPostpayflag(String postpayflag) {
		this.postpayflag = postpayflag;
	}
	public String getPrebal() {
		return prebal;
	}
	public void setPrebal(String prebal) {
		this.prebal = prebal;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public String getPropflag() {
		return propflag;
	}
	public void setPropflag(String propflag) {
		this.propflag = propflag;
	}
	public String getPropreduendym() {
		return propreduendym;
	}
	public void setPropreduendym(String propreduendym) {
		this.propreduendym = propreduendym;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRounddigit() {
		return rounddigit;
	}
	public void setRounddigit(String rounddigit) {
		this.rounddigit = rounddigit;
	}
	public String getRoundmode() {
		return roundmode;
	}
	public void setRoundmode(String roundmode) {
		this.roundmode = roundmode;
	}
	public String getSalbankaccnm() {
		return salbankaccnm;
	}
	public void setSalbankaccnm(String salbankaccnm) {
		this.salbankaccnm = salbankaccnm;
	}
	public String getDwjcje() {
		return dwjcje;
	}
	public void setDwjcje(String dwjcje) {
		this.dwjcje = dwjcje;
	}
	public String getFinipartpaymap() {
		return finipartpaymap;
	}
	public void setFinipartpaymap(String finipartpaymap) {
		this.finipartpaymap = finipartpaymap;
	}
	public String getFinlpaym() {
		return finlpaym;
	}
	public void setFinlpaym(String finlpaym) {
		this.finlpaym = finlpaym;
	}
	public String getFinpaymap() {
		return finpaymap;
	}
	public void setFinpaymap(String finpaymap) {
		this.finpaymap = finpaymap;
	}
	public String getFinuapartlpaym() {
		return finuapartlpaym;
	}
	public void setFinuapartlpaym(String finuapartlpaym) {
		this.finuapartlpaym = finuapartlpaym;
	}
	public String getFinuapartpaymap() {
		return finuapartpaymap;
	}
	public void setFinuapartpaymap(String finuapartpaymap) {
		this.finuapartpaymap = finuapartpaymap;
	}
	public String getFinupartlpaym() {
		return finupartlpaym;
	}
	public void setFinupartlpaym(String finupartlpaym) {
		this.finupartlpaym = finupartlpaym;
	}
	public String getFinupartpaymap() {
		return finupartpaymap;
	}
	public void setFinupartpaymap(String finupartpaymap) {
		this.finupartpaymap = finupartpaymap;
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
	public String getAccbankcode() {
		return accbankcode;
	}
	public void setAccbankcode(String accbankcode) {
		this.accbankcode = accbankcode;
	}
	public String getAccexistflag() {
		return accexistflag;
	}
	public void setAccexistflag(String accexistflag) {
		this.accexistflag = accexistflag;
	}
	public String getAccinstcode() {
		return accinstcode;
	}
	public void setAccinstcode(String accinstcode) {
		this.accinstcode = accinstcode;
	}
	public String getAgentdept() {
		return agentdept;
	}
	public void setAgentdept(String agentdept) {
		this.agentdept = agentdept;
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
	public String getAppronum() {
		return appronum;
	}
	public void setAppronum(String appronum) {
		this.appronum = appronum;
	}
	public String getApproveop() {
		return approveop;
	}
	public void setApproveop(String approveop) {
		this.approveop = approveop;
	}
	public String getAutpayamt() {
		return autpayamt;
	}
	public void setAutpayamt(String autpayamt) {
		this.autpayamt = autpayamt;
	}
	public String getBankaccnm() {
		return bankaccnm;
	}
	public void setBankaccnm(String bankaccnm) {
		this.bankaccnm = bankaccnm;
	}
	public String getBankaccnum() {
		return bankaccnum;
	}
	public void setBankaccnum(String bankaccnum) {
		this.bankaccnum = bankaccnum;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getBaseuppflag() {
		return baseuppflag;
	}
	public void setBaseuppflag(String baseuppflag) {
		this.baseuppflag = baseuppflag;
	}
	public String getBegpayym() {
		return begpayym;
	}
	public void setBegpayym(String begpayym) {
		this.begpayym = begpayym;
	}
	public String getCenterid() {
		return centerid;
	}
	public void setCenterid(String centerid) {
		this.centerid = centerid;
	}
	public String getChkop() {
		return chkop;
	}
	public void setChkop(String chkop) {
		this.chkop = chkop;
	}
	public String getCmfinpayamt() {
		return cmfinpayamt;
	}
	public void setCmfinpayamt(String cmfinpayamt) {
		this.cmfinpayamt = cmfinpayamt;
	}
	public String getCmfinpaynum() {
		return cmfinpaynum;
	}
	public void setCmfinpaynum(String cmfinpaynum) {
		this.cmfinpaynum = cmfinpaynum;
	}
	public String getCmselpayamt() {
		return cmselpayamt;
	}
	public void setCmselpayamt(String cmselpayamt) {
		this.cmselpayamt = cmselpayamt;
	}
	public String getCmselpaynum() {
		return cmselpaynum;
	}
	public void setCmselpaynum(String cmselpaynum) {
		this.cmselpaynum = cmselpaynum;
	}
	public String getWwqyztbs() {
		return wwqyztbs;
	}
	public void setWwqyztbs(String wwqyztbs) {
		this.wwqyztbs = wwqyztbs;
	}
	public String getDwzhye() {
		return dwzhye;
	}
	public void setDwzhye(String dwzhye) {
		this.dwzhye = dwzhye;
	}
	public String getDwzhzt() {
		return dwzhzt;
	}
	public void setDwzhzt(String dwzhzt) {
		this.dwzhzt = dwzhzt;
	}
	public String getEntflag() {
		return entflag;
	}
	public void setEntflag(String entflag) {
		this.entflag = entflag;
	}
	public String getFinalflag() {
		return finalflag;
	}
	public void setFinalflag(String finalflag) {
		this.finalflag = finalflag;
	}
	public String getCrefilnum() {
		return crefilnum;
	}
	public void setCrefilnum(String crefilnum) {
		this.crefilnum = crefilnum;
	}
	public String getCydpsum() {
		return cydpsum;
	}
	public void setCydpsum(String cydpsum) {
		this.cydpsum = cydpsum;
	}
	public String getDkffe() {
		return dkffe;
	}
	public void setDkffe(String dkffe) {
		this.dkffe = dkffe;
	}
	public String getDkye() {
		return dkye;
	}
	public void setDkye(String dkye) {
		this.dkye = dkye;
	}
	public String getDwdzxx() {
		return dwdzxx;
	}
	public void setDwdzxx(String dwdzxx) {
		this.dwdzxx = dwdzxx;
	}
	public String getFinipartlpaym() {
		return finipartlpaym;
	}
	public void setFinipartlpaym(String finipartlpaym) {
		this.finipartlpaym = finipartlpaym;
	}
	public String getFrzamt() {
		return frzamt;
	}
	public void setFrzamt(String frzamt) {
		this.frzamt = frzamt;
	}
	public String getFrzflag() {
		return frzflag;
	}
	public void setFrzflag(String frzflag) {
		this.frzflag = frzflag;
	}
	public String getFundsouflag() {
		return fundsouflag;
	}
	public void setFundsouflag(String fundsouflag) {
		this.fundsouflag = fundsouflag;
	}
	public String getHproppayamt() {
		return hproppayamt;
	}
	public void setHproppayamt(String hproppayamt) {
		this.hproppayamt = hproppayamt;
	}
	public String getUnitcustid() {
		return unitcustid;
	}
	public void setUnitcustid(String unitcustid) {
		this.unitcustid = unitcustid;
	}
	public String getDwzh() {
		return dwzh;
	}
	public void setDwzh(String dwzh) {
		this.dwzh = dwzh;
	}
	public String getDwjcrs() {
		return dwjcrs;
	}
	public void setDwjcrs(String dwjcrs) {
		this.dwjcrs = dwjcrs;
	}
	public String getJzny() {
		return jzny;
	}
	public void setJzny(String jzny) {
		this.jzny = jzny;
	}
	public String getDwfcrs() {
		return dwfcrs;
	}
	public void setDwfcrs(String dwfcrs) {
		this.dwfcrs = dwfcrs;
	}
	public String getDwjczt() {
		return dwjczt;
	}
	public void setDwjczt(String dwjczt) {
		this.dwjczt = dwjczt;
	}
	
}
