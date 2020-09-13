package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.zhuzhou.AppApi00101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle001_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("00000077500请求00101参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();
		MoneyNumberTran mnTran = new MoneyNumberTran();             	

		if(Constants.method_BSP.equals(send)){////xml通信处理

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZHJBXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00000077500/BSP_REQ_GRZHJBXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "117709");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>117709</><TranDate>1</><TranIP>1</><TranSeq>1</><xingming>1</><dwzh>1</><unitaccname>111</><zjlx>1</><zjhm>1</><sjhm>123456789098765432</><khrq>1</><grzhzt>1</><frzflag>1</><jzny>1</><grjcjs>1</><unitprop>1</><indiprop>1</><indipaysum>1</><dwyjce>1</><gryjce>1</><grzhye>1</><cardno>1</><isloanflag>1</><srelation1>1</><relname1>1</><relsex1>1</><relcertinum1>1</><phone1>1</><isuser1>1</><relcustid1>1</><srelation2>1</><relname2>1</><relsex2>1</><relcertinum2>1</><phone2>1</><isuser2>1</><relcustid2>1</><srelation3>1</><relname3>1</><relsex3>1</><relcertinum3>1</><phone3>1</><isuser3>1</><relcustid3>1</><srelation4>1</><relname4>1</><relsex4>1</><relcertinum4>1</><phone4>1</><isuser4>1</><relcustid4>1</>";
//			String rexml ="<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag>0</><BrcCode>00073199</><BusiSeq>149591</><ChannelSeq>0</><ChkCode></><MTimeStamp>2016-12-21 05:41:37</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-21 05:41:37</><TellCode>9999</><TranChannel>4</><TranCode>117709</><TranDate>2017-06-16</><TranIP></><TranSeq>149591</><accexistflag>1</><agentinstcode>00073130</><agentop>0000</><approveop></><attworkdate>19010101</><begintdate>20160701</><begpayym>2008-09</><belongtodept></><birthday>19820731</><byearbal>24630.85</><cardno>6227002942330182100</><centerid>00073100</><certinumchkflag>1</><chkop></><clsaccint>0.00</><crelevel>0</><csny>1982-07</><custid>AP03718884</><des>正常</><dwyjce>201.00</><dwzh>0320007</><edudeg></><email></><flag>0</><freeuse1></><freeuse2>0.00</><freeuse3>1899-12-31</><freeuse4>0</><frzamt>0.00</><frzflag>0</><fundsouflag>0</><gddhhm></><grckzhhm></><grckzhkhyhdm></><grckzhkhyhmc></><grjcjs>2512.50</><gryjce>201.00</><grzh>350719334</><grzhdngjye>1608.00</><grzhsnjzye>4630.85</><grzhye>6238.85</><grzhzt>01</><hyzk></><increintaccu>69106212.00</><indiacctype>1</><indibpaymap>000000000000000000000000000000000000000000000000</><indipaymap>00000000000000000000FFFFFFFFFFFFFFFFFFFFFFFFC000</><indipaysum>402.00</><indiprop>0.080</><indisoicode></><ipartlpaym>2016-10</><ipartpaymap>000000000000000000000000000000000000000000000000</><isloanflag>1</><jkhtbh>2013300049</><jtysr>0.00</><jtzz>醴陵市湘东医院高职楼</><jzny>2016-10</><keepintaccu>1060284199.95</><khrq>20130115</><lastbal>26238.85</><lastdrawdate>20161109</><lasttransdate>20161109</><machinetime>2013-01-15 00:00:00:000000</><monthinc>2512.50</><msn></><proptype>1</><qq></><sjhm>15073319885</><smrzbs>0</><stpayamt>0.00</><transchannel></><trustdate></><uapartlpaym></><uapartpaymap></><unitaccname>湖南师范大学附属湘东医院</><unitapayamt>201.00</><unitastprop>0.000</><unitprop>0.080</><upartlpaym>2016-10</><upartpaymap>000000000000000000000000000000000000000000000000</><useflag>0</><validflag>1</><workid></><wwqyztbs>00000000000000000000</><xhrq>18991231</><xhyy></><xingbie>1</><xingming>何磊</><xmqp>HELEI</><xueli></><yadjamt>0.00</><ydrawamt>20000.00</><ynspayamt>0.00</><ypayamt>0.00</><yunctnamt>0.00</><yzbm></><zhichen></><zhiwu></><zhiye></><zjhm>430219198207311357</><zjlx>01</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZHJBXXCX.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00000077500/BSP_REP_GRZHJBXXCX.txt", rexml, req);

		    log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00101ZHResult);
			if(!"0".equals(app00101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00101ZHResult.getRecode());
				modelMap.put("msg", app00101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00101ZHResult.getRecode()+"  ,  描述msg : "+app00101ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00101ZHResult = null;
		if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
			appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result", app00101ZHResult);
//			Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
//			while (it1.hasNext()) {
//				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
//				log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00101ZHResult);
		}else {
			if(!CommonUtil.isEmpty(app00101ZHResult.getFrzflag())){
				if ("0".equals(app00101ZHResult.getFrzflag())) {
					app00101ZHResult.setFrzflag("否");
				} else {
					app00101ZHResult.setFrzflag("是");
				}
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getIsloanflag())){
				if ("0".equals(app00101ZHResult.getIsloanflag())) {
					app00101ZHResult.setIsloanflag("否");
				} else {
					app00101ZHResult.setIsloanflag("是");
				}
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getUseflag())){
				if ("0".equals(app00101ZHResult.getUseflag())) {
					app00101ZHResult.setUseflag("否");
				} else {
					app00101ZHResult.setUseflag("是");
				}
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getFlag())){
				if ("0".equals(app00101ZHResult.getFlag())) {
					app00101ZHResult.setFlag("否");
				} else {
					app00101ZHResult.setFlag("是");
				}
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getSjhm())){
				app00101ZHResult.setSjhm(Tools.getDesensitizationTel(app00101ZHResult.getSjhm()));
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getGrzh())){
				app00101ZHResult.setGrzh(Tools.getZZDesensitizationAccnum(app00101ZHResult.getGrzh()));
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getZjhm())){
				app00101ZHResult.setZjhm(Tools.getDesensitizationCertinum(app00101ZHResult.getZjhm()));
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getCardno())){
				app00101ZHResult.setCardno(Tools.getDesensitizationBank(app00101ZHResult.getCardno()));
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getDwzh())){
				app00101ZHResult.setDwzh(Tools.getZZDesensitizationUnitaccnum(app00101ZHResult.getDwzh()));
			}
			String certinumType = PropertiesReader.getProperty("yingshe.properties", "certinumType"+app00101ZHResult.getZjlx()+form.getCenterId());
			String aa = app00101ZHResult.getZjlx();
			System.out.println("======================="+aa);
			if(certinumType == null||certinumType.equals("")){
				app00101ZHResult.setZjlx("--");
			}else {
				app00101ZHResult.setZjlx(certinumType);
			}
			
			appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result1", app00101ZHResult);
//			Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
//			while (it1.hasNext()) {
//				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
//				log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");			
			modelMap.put("result", appapi00101ZHResult);
			modelMap.put("des", app00101ZHResult.getDes());
		}
			
//		modelMap.put("srelation1", getSrelation(app00101ZHResult.getSrelation1(), app00101ZHResult.getZjhm(), app00101ZHResult.getRelcertinum1()));
//		modelMap.put("relname1", app00101ZHResult.getRelname1());
//		modelMap.put("relsex1", CommonUtil.isEmpty(app00101ZHResult.getRelsex1())?"":Tools.getCode(app00101ZHResult.getRelsex1()));
//		modelMap.put("relcertinum1", app00101ZHResult.getRelcertinum1());
//		modelMap.put("phone1", app00101ZHResult.getPhone1());
//		modelMap.put("isuser1", CommonUtil.isEmpty(app00101ZHResult.getIsuser1())?"":Tools.getCode(app00101ZHResult.getIsuser1()));
//		modelMap.put("relcustid1", app00101ZHResult.getRelcustid1());
//		
//		modelMap.put("srelation2", getSrelation(app00101ZHResult.getSrelation2(), app00101ZHResult.getZjhm(), app00101ZHResult.getRelcertinum2()));
//		modelMap.put("relname2", app00101ZHResult.getRelname2());
//		modelMap.put("relsex2", CommonUtil.isEmpty(app00101ZHResult.getRelsex2())?"":Tools.getCode(app00101ZHResult.getRelsex2()));
//		modelMap.put("relcertinum2", app00101ZHResult.getRelcertinum2());
//		modelMap.put("phone2", app00101ZHResult.getPhone2());
//		modelMap.put("isuser2", CommonUtil.isEmpty(app00101ZHResult.getIsuser2())?"":Tools.getCode(app00101ZHResult.getIsuser2()));
//		modelMap.put("relcustid2", app00101ZHResult.getRelcustid2());
//		
//		modelMap.put("srelation3", getSrelation(app00101ZHResult.getSrelation3(), app00101ZHResult.getZjhm(), app00101ZHResult.getRelcertinum3()));
//		modelMap.put("relname3", app00101ZHResult.getRelname3());
//		modelMap.put("relsex3", CommonUtil.isEmpty(app00101ZHResult.getRelsex3())?"":Tools.getCode(app00101ZHResult.getRelsex3()));
//		modelMap.put("relcertinum3", app00101ZHResult.getRelcertinum3());
//		modelMap.put("phone3", app00101ZHResult.getPhone3());
//		modelMap.put("isuser3", CommonUtil.isEmpty(app00101ZHResult.getIsuser3())?"":Tools.getCode(app00101ZHResult.getIsuser3()));
//		modelMap.put("relcustid3", app00101ZHResult.getRelcustid3());
//		
//		modelMap.put("srelation4", getSrelation(app00101ZHResult.getSrelation4(), app00101ZHResult.getZjhm(), app00101ZHResult.getRelcertinum4()));
//		modelMap.put("relname4", app00101ZHResult.getRelname4());
//		modelMap.put("relsex4", CommonUtil.isEmpty(app00101ZHResult.getRelsex4())?"":Tools.getCode(app00101ZHResult.getRelsex4()));
//		modelMap.put("relcertinum4", app00101ZHResult.getRelcertinum4());
//		modelMap.put("phone4", app00101ZHResult.getPhone4());
//		modelMap.put("isuser4", CommonUtil.isEmpty(app00101ZHResult.getIsuser4())?"":Tools.getCode(app00101ZHResult.getIsuser4()));
//		modelMap.put("relcustid4", app00101ZHResult.getRelcustid4());
		return modelMap;
	}
	private String tranNumber(String number){
		if(number==null||"".equals(number)){
			number = "0.00";
		}
		String result = new Double(number)*100 + "";
		return result;
	}
	
	/**
	 * @param srelation
	 * @param crelcertinum 主身份证
	 * @param rrelcertinum 关联人身份证
	 * @return
	 */
	private String getSrelation(String srelation, String crelcertinum, String rrelcertinum){
		//1:夫妻 2:父子 3:母子 4：父女 5：母女 9：其它
		//亲属关系1-配偶2-父亲3-母亲4-子女
		if(CommonUtil.isEmpty(srelation)){
			return "";
		}
		srelation = Tools.getCode(srelation);
		
		String mainnum = "";
		if(crelcertinum.length()==18){			
			mainnum = crelcertinum.substring(6,14);
		}else if(crelcertinum.length()==15){
			mainnum = crelcertinum.substring(6,12);
			if("3".compareTo(mainnum.substring(0,1))>0){
				mainnum = "19"+mainnum;
			}else{
				mainnum = "20"+mainnum;
			}
		}else{
			return "";
		}
		
		String secondnum = "";
		if(rrelcertinum.length()==18){			
			secondnum = rrelcertinum.substring(6,14);
		}else if(rrelcertinum.length()==15){
			secondnum = rrelcertinum.substring(6,12);
			if("3".compareTo(rrelcertinum.substring(0,1))>0){
				secondnum = "19"+secondnum;
			}else{
				secondnum = "20"+secondnum;
			}
		}else{
			return "";
		}
		
		if("1".equals(srelation)){
			return "1";
		}else if("2".equals(srelation)){
			if(mainnum.compareTo(secondnum)>0){
				return "4";
			}else{
				return "2";
			}
		}else if("3".equals(srelation)){
			if(mainnum.compareTo(secondnum)>0){
				return "4";
			}else{
				return "3";
			}
		}else if("4".equals(srelation)){
			if(mainnum.compareTo(secondnum)>0){
				return "4";
			}else{
				return "2";
			}
		}else if("5".equals(srelation)){
			if(mainnum.compareTo(secondnum)>0){
				return "4";
			}else{
				return "3";
			}
		}
		return "";
	}
}
