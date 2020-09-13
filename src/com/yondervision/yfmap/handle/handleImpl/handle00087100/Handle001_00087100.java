package com.yondervision.yfmap.handle.handleImpl.handle00087100;

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
import com.yondervision.yfmap.result.kunming.AppApi00101Result;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle001_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();
		MoneyNumberTran mnTran = new MoneyNumberTran();             	

		if(Constants.method_BSP.equals(send)){////xml通信处理

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRGJJYECX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GRGJJYECX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314013");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314013</><TranDate>1</><TranIP>1</><TranSeq>1</><accnum>343554155343441</><accname>1</><certinum>1233467898876543</><cardno>1</><custid>123453252344</><peraccstate>0</><phone>12345678909</><balance>12342434.42</><lastpaydate>1</><lastdrawdate>21212</><basenumber>22</><monpaysum>111</><ablprop>1</><indiprop>0.1</><unitprop>0.18</><attworkdate>1</><beginpaydate>1</><ydrawamt>221</><ypayamt>3212</><unitaccnum>13423532434151</><unitaccname>11</><accinstcode>1</><accbankcode>32</><srelation1>1</><relname1>1</><relsex1>1</><relcertinum1>1</><phone1>1</><isuser1>1</><relcustid1>1</><srelation2>1</><relname2>1</><relsex2>1</><relcertinum2>1</><phone2>1</><isuser2>1</><relcustid2>1</><srelation3>1</><relname3>1</><relsex3>1</><relcertinum3>1</><phone3>1</><isuser3>1</><relcustid3>1</><srelation4>1</><relname4>1</><relsex4>1</><relcertinum4>1</><phone4>1</><isuser4>1</><relcustid4>1</>";
		
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRGJJYECX.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GRGJJYECX.txt", rexml, req);

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
		String str = null;
		if(form.getChannel().trim().equals("60")){
			String[] num = app00101ZHResult.getAccnum().split("");
			StringBuffer nums = new StringBuffer();
			for(int i=1;i<num.length;i++){
				nums.append(num[i]+",");
			}
			str = "个人公积金账号"+nums.toString()+"姓名"+app00101ZHResult.getAccname()+","+"最后汇缴月"+
					app00101ZHResult.getLastpaydate().substring(0,4)+"年"+app00101ZHResult.getLastpaydate().substring(5,7)+"月"+"2015-10-12".substring(8,10)+"日"
					+","+"缴存基数"+app00101ZHResult.getBasenumber()+"元,"+"单位缴存比例百分之"+tranNumber(app00101ZHResult.getUnitprop())+","+"个人缴存比例百分之"+tranNumber(app00101ZHResult.getIndiprop())+","+"月缴存额"+app00101ZHResult.getMonpaysum()+"元,"+"当前余额"+app00101ZHResult.getBalance()+"元,"+"单位名称"+app00101ZHResult.getUnitaccname();
			
		}
		String unitaccnum = app00101ZHResult.getUnitaccnum();
		String certinum = app00101ZHResult.getCertinum();
		String tel = app00101ZHResult.getPhone();
		if(!form.getChannel().trim().equals("40")){
			app00101ZHResult.setAccnum(Tools.getDesensitizationAccnum(app00101ZHResult.getAccnum()));
			app00101ZHResult.setCertinum(Tools.getDesensitizationCertinum(app00101ZHResult.getCertinum()));
			app00101ZHResult.setPeraccstate(Tools.getMessage(app00101ZHResult.getPeraccstate()));
			if(!CommonUtil.isEmpty(app00101ZHResult.getPhone())){
				app00101ZHResult.setPhone(Tools.getDesensitizationTel(app00101ZHResult.getPhone()));
			}			
			app00101ZHResult.setBalance(mnTran.moneyTran(app00101ZHResult.getBalance()));
			app00101ZHResult.setBasenumber(mnTran.moneyTran(app00101ZHResult.getBasenumber()));
			app00101ZHResult.setMonpaysum(mnTran.moneyTran(app00101ZHResult.getMonpaysum()));
			app00101ZHResult.setIndiprop(mnTran.numberTran(app00101ZHResult.getIndiprop()));
			app00101ZHResult.setUnitprop(mnTran.numberTran(app00101ZHResult.getUnitprop()));
			app00101ZHResult.setUnitaccnum(Tools.getDesensitizationUnitaccnum(app00101ZHResult.getUnitaccnum()));
			app00101ZHResult.setAccinstcode(Tools.getMessage(app00101ZHResult.getAccinstcode()));			
		}else{
			app00101ZHResult.setBalance(mnTran.moneyTran(app00101ZHResult.getBalance()));
			app00101ZHResult.setBasenumber(mnTran.moneyTran(app00101ZHResult.getBasenumber()));
			app00101ZHResult.setMonpaysum(mnTran.moneyTran(app00101ZHResult.getMonpaysum()));
			app00101ZHResult.setIndiprop(mnTran.numberTran(app00101ZHResult.getIndiprop()));
			app00101ZHResult.setUnitprop(mnTran.numberTran(app00101ZHResult.getUnitprop()));
		}
		if("1899-12-31".equals(app00101ZHResult.getLastdrawdate().trim())){
			app00101ZHResult.setLastdrawdate("--");
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		if(form.getChannel().trim().equals("60")){
			modelMap.put("result", str);
		}else{
			appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result", app00101ZHResult);
//			List<TitleInfoNameFormatBean> appapi00101Detail = null;
//			appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app00101ZHResult);
			Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
				log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
			}
			modelMap.put("result", appapi00101ZHResult);
		}
		
//		modelMap.put("detail", appapi00101Detail);
		
		modelMap.put("unitaccnum", unitaccnum);
		
		modelMap.put("srelation1", getSrelation(app00101ZHResult.getSrelation1(), app00101ZHResult.getCertinum(), app00101ZHResult.getRelcertinum1()));
		modelMap.put("relname1", app00101ZHResult.getRelname1());
		modelMap.put("relsex1", CommonUtil.isEmpty(app00101ZHResult.getRelsex1())?"":Tools.getCode(app00101ZHResult.getRelsex1()));
		modelMap.put("relcertinum1", app00101ZHResult.getRelcertinum1());
		modelMap.put("phone1", app00101ZHResult.getPhone1());
		modelMap.put("isuser1", CommonUtil.isEmpty(app00101ZHResult.getIsuser1())?"":Tools.getCode(app00101ZHResult.getIsuser1()));
		modelMap.put("relcustid1", app00101ZHResult.getRelcustid1());
		
		modelMap.put("srelation2", getSrelation(app00101ZHResult.getSrelation2(), app00101ZHResult.getCertinum(), app00101ZHResult.getRelcertinum2()));
		modelMap.put("relname2", app00101ZHResult.getRelname2());
		modelMap.put("relsex2", CommonUtil.isEmpty(app00101ZHResult.getRelsex2())?"":Tools.getCode(app00101ZHResult.getRelsex2()));
		modelMap.put("relcertinum2", app00101ZHResult.getRelcertinum2());
		modelMap.put("phone2", app00101ZHResult.getPhone2());
		modelMap.put("isuser2", CommonUtil.isEmpty(app00101ZHResult.getIsuser2())?"":Tools.getCode(app00101ZHResult.getIsuser2()));
		modelMap.put("relcustid2", app00101ZHResult.getRelcustid2());
		
		modelMap.put("srelation3", getSrelation(app00101ZHResult.getSrelation3(), app00101ZHResult.getCertinum(), app00101ZHResult.getRelcertinum3()));
		modelMap.put("relname3", app00101ZHResult.getRelname3());
		modelMap.put("relsex3", CommonUtil.isEmpty(app00101ZHResult.getRelsex3())?"":Tools.getCode(app00101ZHResult.getRelsex3()));
		modelMap.put("relcertinum3", app00101ZHResult.getRelcertinum3());
		modelMap.put("phone3", app00101ZHResult.getPhone3());
		modelMap.put("isuser3", CommonUtil.isEmpty(app00101ZHResult.getIsuser3())?"":Tools.getCode(app00101ZHResult.getIsuser3()));
		modelMap.put("relcustid3", app00101ZHResult.getRelcustid3());
		
		modelMap.put("srelation4", getSrelation(app00101ZHResult.getSrelation4(), app00101ZHResult.getCertinum(), app00101ZHResult.getRelcertinum4()));
		modelMap.put("relname4", app00101ZHResult.getRelname4());
		modelMap.put("relsex4", CommonUtil.isEmpty(app00101ZHResult.getRelsex4())?"":Tools.getCode(app00101ZHResult.getRelsex4()));
		modelMap.put("relcertinum4", app00101ZHResult.getRelcertinum4());
		modelMap.put("phone4", app00101ZHResult.getPhone4());
		modelMap.put("isuser4", CommonUtil.isEmpty(app00101ZHResult.getIsuser4())?"":Tools.getCode(app00101ZHResult.getIsuser4()));
		modelMap.put("relcustid4", app00101ZHResult.getRelcustid4());
		modelMap.put("certinum", certinum);
		modelMap.put("tel", tel);
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
