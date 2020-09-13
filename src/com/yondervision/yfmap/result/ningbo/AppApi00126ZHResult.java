package com.yondervision.yfmap.result.ningbo;

/**
 * @author 商贷基本信息查询
 */
public class AppApi00126ZHResult {

    private String amt;//金额
    private String rsufilepath;//结果文件路径
    private String rsufilename;//结果文件名
    /*	agentop 	经办柜员
        certinum	证件号码
        cleardate	结清日期
        loanamt	贷款金额
        loandate	放款日期
        loaneename	借款人姓名
        loanterm	贷款期限
        mortaddr	房屋地址
        times	次数
        transdate	交易类型*/

    public String getAmt() {
        return this.amt;
    }

    public void setAmt(final String amt) {
        this.amt = amt;
    }

    public String getRsufilepath() {
        return this.rsufilepath;
    }

    public void setRsufilepath(final String rsufilepath) {
        this.rsufilepath = rsufilepath;
    }

    public String getRsufilename() {
        return this.rsufilename;
    }

    public void setRsufilename(final String rsufilename) {
        this.rsufilename = rsufilename;
    }

    private String agentop;//标志
    private String certinum;//标志

    private String loanamt;//标志
    private String loandate;//标志
    private String loaneename;//标志

    private String mortaddr;//标志
    private String times;//标志
    private String transdate;//标志
    private String flag;//标志
    private String type;//卡类型
    private String signer;//商贷月还款额
    private String commonthrepayamt;//商贷月还款额
    private String drawreasoncode1;
    private String begdate;
    private String enddate;
    private String repaylntype;
    private String bankfhinfo;
    private String message;
    private String state;
    private String sysstate;

    /* 状态码*/
    private String recode = "";
    /* 描述*/
    private String msg = "";
    /* 流水号*/
    private String tranSeq = "";
    private String buyhouseamt;
    private String buyhousedate;
    private String cleardate;
    private String contrsigndate;
    private String houseaddr;
    private String lasttrsdate;
    private String commloansum;
    private String loanterm;
    private String realdpayamt;


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

    public String getTranSeq() {
        return tranSeq;
    }

    public void setTranSeq(String tranSeq) {
        this.tranSeq = tranSeq;
    }

    public String getBuyhouseamt() {
        return buyhouseamt;
    }

    public void setBuyhouseamt(String buyhouseamt) {
        this.buyhouseamt = buyhouseamt;
    }

    public String getBuyhousedate() {
        return buyhousedate;
    }

    public void setBuyhousedate(String buyhousedate) {
        this.buyhousedate = buyhousedate;
    }

    public String getCleardate() {
        return cleardate;
    }

    public void setCleardate(String cleardate) {
        this.cleardate = cleardate;
    }

    public String getContrsigndate() {
        return contrsigndate;
    }

    public void setContrsigndate(String contrsigndate) {
        this.contrsigndate = contrsigndate;
    }

    public String getHouseaddr() {
        return houseaddr;
    }

    public void setHouseaddr(String houseaddr) {
        this.houseaddr = houseaddr;
    }

    public String getLasttrsdate() {
        return lasttrsdate;
    }

    public void setLasttrsdate(String lasttrsdate) {
        this.lasttrsdate = lasttrsdate;
    }

    public String getCommloansum() {
        return commloansum;
    }

    public void setCommloansum(String commloansum) {
        this.commloansum = commloansum;
    }

    public String getLoanterm() {
        return loanterm;
    }

    public void setLoanterm(String loanterm) {
        this.loanterm = loanterm;
    }

    public String getRealdpayamt() {
        return realdpayamt;
    }

    public void setRealdpayamt(String realdpayamt) {
        this.realdpayamt = realdpayamt;
    }

    public String getBankfhinfo() {
        return bankfhinfo;
    }

    public void setBankfhinfo(String bankfhinfo) {
        this.bankfhinfo = bankfhinfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSysstate() {
        return sysstate;
    }

    public void setSysstate(String sysstate) {
        this.sysstate = sysstate;
    }

    public String getRepaylntype() {
        return repaylntype;
    }

    public void setRepaylntype(String repaylntype) {
        this.repaylntype = repaylntype;
    }

    public String getBegdate() {
        return begdate;
    }

    public void setBegdate(String begdate) {
        this.begdate = begdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDrawreasoncode1() {
        return drawreasoncode1;
    }

    public void setDrawreasoncode1(String drawreasoncode1) {
        this.drawreasoncode1 = drawreasoncode1;
    }

    public String getCommonthrepayamt() {
        return commonthrepayamt;
    }

    public void setCommonthrepayamt(String commonthrepayamt) {
        this.commonthrepayamt = commonthrepayamt;
    }

    public String getSigner() {
        return signer;
    }

    public void setSigner(String signer) {
        this.signer = signer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getFlag() {
        return this.flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public String getAgentop() {
        return this.agentop;
    }

    public void setAgentop(final String agentop) {
        this.agentop = agentop;
    }

    public String getCertinum() {
        return this.certinum;
    }

    public void setCertinum(final String certinum) {
        this.certinum = certinum;
    }

    public String getLoanamt() {
        return this.loanamt;
    }

    public void setLoanamt(final String loanamt) {
        this.loanamt = loanamt;
    }

    public String getLoandate() {
        return this.loandate;
    }

    public void setLoandate(final String loandate) {
        this.loandate = loandate;
    }

    public String getLoaneename() {
        return this.loaneename;
    }

    public void setLoaneename(final String loaneename) {
        this.loaneename = loaneename;
    }

    public String getMortaddr() {
        return this.mortaddr;
    }

    public void setMortaddr(final String mortaddr) {
        this.mortaddr = mortaddr;
    }

    public String getTimes() {
        return this.times;
    }

    public void setTimes(final String times) {
        this.times = times;
    }

    public String getTransdate() {
        return this.transdate;
    }

    public void setTransdate(final String transdate) {
        this.transdate = transdate;
    }
}

