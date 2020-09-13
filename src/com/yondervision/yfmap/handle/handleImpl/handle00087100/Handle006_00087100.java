package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00601ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle006_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.debug("form:"+form); 
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		
		/* 模拟返回开始  */
		AppApi00601ZHResult app00601ZHResult = new AppApi00601ZHResult();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			form.setSendTime(CommonUtil.getTime());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			
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
//			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//			if(!file.exists()){
//				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "公积金账号对应文件不存在");
//				return modelMap;
//			}
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_DKJBXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GR_DKJBXXCX.txt", map, req);			
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314015");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode></><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>314015</><TranDate>1</><TranIP>1</><TranSeq>1</><instcode>1</><agencybank>1</><loancontrnum>345345136345423341</><loansum>3245435.1</><curdaybal>676456345.1</><currate>0.1</><loanterm>1</><beginintdate>1</><attermdate>1</><retloanmode>1</><bankaccnum>1</><bankaccname>1</><leavterm>1</><plncnt>1</><oweprin>1454435</><oweint>145</><spedealint>15</><tolamt>1</><accname1>1</><accname2>1</><cerid1>45346662656256241</><cerid2>1</><unitaccnum1>1</><unitaccnum2>1</><assuretype>1</><state>1</><tolowecnt></><tolplanprin></><tolplanint></><tolinfactprin></><tolinfactint>656541</>";

			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GR_DKJBXXCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GR_DKJBXXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601ZHResult);
			
			log.debug("MAP封装成BEAN："+app00601ZHResult);
			if(!"0".equals(app00601ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601ZHResult.getRecode());
				modelMap.put("msg", app00601ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00601ZHResult.getRecode()+"  ,  描述msg : "+app00601ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00601ZHResule = null;
		String str = null;
		MoneyNumberTran mnTran = new MoneyNumberTran();
		
		
		if(form.getChannel().trim().equals("60")){
			String[] num = app00601ZHResult.getLoancontrnum().split("");
			StringBuffer nums = new StringBuffer();
			for(int i=1;i<num.length;i++){
				nums.append(num[i]+",");
			}
			str = "借款合同号"+nums.toString()+"借款人姓名"+
		app00601ZHResult.getAccname1()+","+"贷款金额"+app00601ZHResult.getLoansum()+
		"元,"+"贷款余额"+app00601ZHResult.getCurdaybal()+"元,"+"贷款期限为"+app00601ZHResult.getLoanterm()+"期,"
		+"贷款利率为百分之"+tranNumber(app00601ZHResult.getCurrate());
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", str);
			return modelMap;
		}
		
		if(!form.getChannel().trim().equals("40")){
			app00601ZHResult.setAgencybank(Tools.getMessage(app00601ZHResult.getAgencybank()));//
			app00601ZHResult.setLoancontrnum(Tools.getDesensitizationLoancontrnum(app00601ZHResult.getLoancontrnum()));
			app00601ZHResult.setLoansum(mnTran.moneyTran(app00601ZHResult.getLoansum()));
			app00601ZHResult.setCurdaybal(mnTran.moneyTran(app00601ZHResult.getCurdaybal()));
			app00601ZHResult.setCurrate(mnTran.numberTran(app00601ZHResult.getCurrate()));
			app00601ZHResult.getLoanterm();
			app00601ZHResult.getBeginintdate();
			app00601ZHResult.getAttermdate();
			app00601ZHResult.setRetloanmode(Tools.getMessage(app00601ZHResult.getRetloanmode()));
			app00601ZHResult.setOweprin(mnTran.moneyTran(app00601ZHResult.getOweprin()));
			app00601ZHResult.setOweint(mnTran.moneyTran(app00601ZHResult.getOweint()));
			app00601ZHResult.setSpedealint(mnTran.moneyTran(app00601ZHResult.getSpedealint()));
			app00601ZHResult.getAccname1();
			app00601ZHResult.setCerid1(Tools.getDesensitizationCertinum(app00601ZHResult.getCerid1()));
			app00601ZHResult.getTolowecnt();
			app00601ZHResult.setTolinfactprin(mnTran.moneyTran(app00601ZHResult.getTolinfactprin()));
			app00601ZHResult.setTolinfactint(mnTran.moneyTran(app00601ZHResult.getTolinfactint()));
		}
		
		else{
			app00601ZHResult.getAgencybank();//
			app00601ZHResult.getLoancontrnum();
			app00601ZHResult.setLoansum(mnTran.moneyTran(app00601ZHResult.getLoansum()));
			app00601ZHResult.setCurdaybal(mnTran.moneyTran(app00601ZHResult.getCurdaybal()));
			app00601ZHResult.setCurrate(mnTran.numberTran(app00601ZHResult.getCurrate()));
			app00601ZHResult.getLoanterm();
			app00601ZHResult.getBeginintdate();
			app00601ZHResult.getAttermdate();
			app00601ZHResult.getRetloanmode();
			app00601ZHResult.setOweprin(mnTran.moneyTran(app00601ZHResult.getOweprin()));
			app00601ZHResult.setOweint(mnTran.moneyTran(app00601ZHResult.getOweint()));
			app00601ZHResult.setSpedealint(mnTran.moneyTran(app00601ZHResult.getSpedealint()));
			app00601ZHResult.getAccname1();
			app00601ZHResult.getCerid1();
			app00601ZHResult.getTolowecnt();
			app00601ZHResult.setTolinfactprin(mnTran.moneyTran(app00601ZHResult.getTolinfactprin()));
			app00601ZHResult.setTolinfactint(mnTran.moneyTran(app00601ZHResult.getTolinfactint()));
		}
		appapi00601ZHResule = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00601"+form.getCenterId()+".result", app00601ZHResult);
//		List<TitleInfoNameFormatBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00601"+form.getCenterId()+".detail", app00601ZHResult);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00601ZHResule);

		return modelMap;
	}
	
	public String tranNumber(String number){
		if(number==null||"".equals(number)){
			number = "0.00";
		}
		String result = new Double(number)*100 + "";
		return result;
	}
	
}
