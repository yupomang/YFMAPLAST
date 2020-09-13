package com.yondervision.yfmap.handle.handleImpl.handle00047000;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01103Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi01101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle01104_00047000
* @Description:贷款资格审查
* 
*/ 
public class Handle01104_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi01104 start.");
		HulunbuirAppApi01101Result app002Result = new HulunbuirAppApi01101Result();
		AppApi01103Form form = (AppApi01103Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("10".equals(form.getChannel()))
			{
				form.setChannel("21");
			}else if("20".equals(form.getChannel()))
			{
				form.setChannel("31");
			}
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_ZGSC.txt", map, req);
			log.debug("发往中心报文11："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129953");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><summary>申请 2015-10-01</><summary1>复审 2015-10-01</><summary2>审批 2015-10-01</><summary3>合同签订 2015-10-01</><summary4>贷款担保 2015-10-01</><summarydes>放款 2015-10-01</><transdes></><rreason></>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_ZGSC.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess_ts.equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", app002Result.getMsg());
		modelMap.put("dkflag", app002Result.getApprflag());
		modelMap.put("dkinfo", app002Result.getApprflagMSG().replaceAll("~", "/r/n"));
		modelMap.put("dbflag", app002Result.getSumcounts());
		modelMap.put("dbinfo", app002Result.getActmp1024().replaceAll("~", "/r/n"));
		log.info(Constants.LOG_HEAD+"appApi01104 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		System.out.println(String.format("%,.2f",3.00/7*100));
	}

}
