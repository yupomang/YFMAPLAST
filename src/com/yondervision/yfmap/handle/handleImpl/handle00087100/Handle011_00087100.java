package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi01101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle011_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {

		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101ZHResult app01101ZHResult = new AppApi01101ZHResult();	
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			form.setSendTime(CommonUtil.getTime());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_DKJDCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GR_DKJDCX.txt", map, req);			
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314018");
//			String rexml = "<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode><1/><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>314018</><TranDate>1</><TranIP>1</><TranSeq>1</><apprflag>06</>";
			log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GR_DKJDCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GR_DKJDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);	
			
			BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
			log.debug("MAP封装成BEAN："+app01101ZHResult);
			
			//if(!Constants.sucess.equals(appapi011ZHResult.getRecode()))
			if(!"0".equals(app01101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app01101ZHResult.getRecode());
				modelMap.put("msg", app01101ZHResult.getMsg());			
				log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi01101ZHResult = null;
		
		appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result", app01101ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}	
		
		
		//02-初审-03/04/05-复审-00/06终审-07/08/11-抵押登记-09-放款
		String flagCode = "01";
		String flagMsg = "";
		String reflag = Tools.getCode(app01101ZHResult.getApprflag());
		if("10".indexOf(reflag)>=0){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "您申请的贷款未通过审批，请携带相关资料重新申请");
			return modelMap;
		}else if("02".indexOf(reflag)>=0){
			flagCode = "01";
			flagMsg = "初审";
		}else if("03,04,05".indexOf(reflag)>=0){
			flagCode = "02";
			flagMsg = "复审";
		}else if("00,06".indexOf(reflag)>=0){
			flagCode = "03";
			flagMsg = "终审";
		}else if("07,08,11".indexOf(reflag)>=0){
			flagCode = "04";
			flagMsg = "抵押登记";
		}else if("09".indexOf(reflag)>=0){
			flagCode = "05";
			flagMsg = "放款";
		}
		
		List<AppApi011Result> list = new ArrayList<AppApi011Result>();
		AppApi011Result app01101 = new AppApi011Result();
		app01101.setApprflagID("01");
		app01101.setApprflagMSG("初审");		
		list.add(app01101);
		
		AppApi011Result app01102 = new AppApi011Result();
		app01102.setApprflagID("02");
		app01102.setApprflagMSG("复审");		
		list.add(app01102);
		
		AppApi011Result app01103 = new AppApi011Result();
		app01103.setApprflagID("03");
		app01103.setApprflagMSG("终审");		
		list.add(app01103);	
		
		AppApi011Result app01104 = new AppApi011Result();
		app01104.setApprflagID("04");
		app01104.setApprflagMSG("抵押登记");		
		list.add(app01104);
		
		AppApi011Result app01105 = new AppApi011Result();
		app01105.setApprflagID("05");
		app01105.setApprflagMSG("放款");		
		list.add(app01105);

		String str = null;
		if(form.getChannel().trim().equals("60")){
			str = "当前贷款进度为"+flagMsg;
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", str);	
			return modelMap;
		}
		else {
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("apprflag", flagCode);
			modelMap.put("apprmsg", flagMsg);
			modelMap.put("result", list);
			return modelMap;
		}
		
	}

}
