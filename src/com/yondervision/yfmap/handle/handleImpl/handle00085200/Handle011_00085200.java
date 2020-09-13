package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi01101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle011_00085200 
* @Description: 遵义贷款进度查询
* @author Caozhongyan
* @date Apr 8, 2016 4:41:28 PM   
* 
*/ 
public class Handle011_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi01101 start.");
		AppApi01101Result appapi011 = new AppApi01101Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		ZunYiAppApi01101Result app011Result = new ZunYiAppApi01101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCX.txt", map, req);
			log.debug("发往中心报文11："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128130");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><summary>申请 2015-10-01</><summary1>复审 2015-10-01</><summary2>审批 2015-10-01</><summary3>合同签订 2015-10-01</><summary4>贷款担保 2015-10-01</><summarydes>放款 2015-10-01</><transdes></><rreason></>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKJDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			if(!"0".equals(app011Result.getRecode())){
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
		}
		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		int sum = 0;
		AppApi011Result app01100 = new AppApi011Result();
		String summary = (app011Result.getSummary().equals("")||app011Result.getSummary().equals("--"))?"0":"1";
		app01100.setApprflagID(summary);
		String summsg = "0".equals(summary)?"申请":app011Result.getSummary();
		app01100.setApprflagMSG(summsg);
		list.add(app01100);		
		sum = "0".equals(summary)?sum:sum+1;
		
		AppApi011Result app01101 = new AppApi011Result();
		String summary1 = (app011Result.getSummary1().equals("")||app011Result.getSummary1().equals("--"))?"0":"1";
		app01101.setApprflagID(summary1);
		String summary1msg = "0".equals(summary1)?"复审":app011Result.getSummary1();
		app01101.setApprflagMSG(summary1msg);
		list.add(app01101);
		sum = "0".equals(summary1)?sum:sum+1;
		
		AppApi011Result app01102 = new AppApi011Result();
		String summary2 = (app011Result.getSummary2().equals("")||app011Result.getSummary2().equals("--"))?"0":"1";
		app01102.setApprflagID(summary2);
		String summary2msg = "0".equals(summary2)?"审批":app011Result.getSummary2();
		app01102.setApprflagMSG(summary2msg);
		list.add(app01102);
		sum = "0".equals(summary2)?sum:sum+1;
		
		AppApi011Result app01103 = new AppApi011Result();
		String summary3 = (app011Result.getSummary3().equals("")||app011Result.getSummary3().equals("--"))?"0":"1";
		app01103.setApprflagID(summary3);
		String summary3msg = "0".equals(summary3)?"合同签订":app011Result.getSummary3();
		app01103.setApprflagMSG(summary3msg);
		list.add(app01103);
		sum = "0".equals(summary3)?sum:sum+1;
		
		AppApi011Result app01104 = new AppApi011Result();
		String summary4 = (app011Result.getSummary4().equals("")||app011Result.getSummary4().equals("--"))?"0":"1";
		app01104.setApprflagID(summary4);
		String summary4msg = "0".equals(summary4)?"贷款担保":app011Result.getSummary4();
		app01104.setApprflagMSG(summary4msg);
		list.add(app01104);
		sum = "0".equals(summary4)?sum:sum+1;
		
		AppApi011Result app01105 = new AppApi011Result();
		String summary5 = (app011Result.getSummarydes().equals("")||app011Result.getSummarydes().equals("--"))?"0":"1";
		app01105.setApprflagID(summary5);
		String summary5msg = "0".equals(summary5)?"放款":app011Result.getSummarydes();
		app01105.setApprflagMSG(summary5msg);
		list.add(app01105);
		sum = "0".equals(summary5)?sum:sum+1;
		
		
		AppApi011Result app01106 = new AppApi011Result();
		String summary6 = (app011Result.getTransdes().equals("")||app011Result.getTransdes().equals("--"))?"0":"1";
		app01106.setApprflagID(summary6);
		String summary6msg = "0".equals(summary6)?"贷款归还":app011Result.getTransdes();
		app01106.setApprflagMSG(summary6msg);
		list.add(app01106);
		sum = "0".equals(summary6)?sum:sum+1;
		
		
		AppApi011Result app01107 = new AppApi011Result();
		String summary7 = (app011Result.getRreason().equals("")||app011Result.getRreason().equals("--"))?"0":"1";
		app01107.setApprflagID(summary7);
		String summary7msg = "0".equals(summary7)?"合同终止":app011Result.getRreason();
		app01107.setApprflagMSG(summary7msg);
		list.add(app01107);
		sum = "0".equals(summary7)?sum:sum+1;
				
		
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", String.format("%,.2f",Double.parseDouble(String.valueOf(sum*100))/8));
		modelMap.put("apprflagMSG", list.get(sum==0?0:sum-1).getApprflagMSG());
		modelMap.put("apprflagDate", "2013-11-22"); 
		modelMap.put("result", list);	
		log.info(Constants.LOG_HEAD+"appApi01101 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		System.out.println(String.format("%,.2f",3.00/7*100));
	}

}
