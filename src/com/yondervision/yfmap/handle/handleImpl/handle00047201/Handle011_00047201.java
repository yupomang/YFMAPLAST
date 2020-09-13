package com.yondervision.yfmap.handle.handleImpl.handle00047201;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00201Result;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi01101Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi01101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle011_00047201
* @Description: 贷款进度查询
* 
*/ 
public class Handle011_00047201 implements CtrlHandleInter {
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi01101 start.");
		AppApi01101Result appapi011 = new AppApi01101Result();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api011DKJDGJJ.txt";
		BaoGangAppApi01101Result app002Result = new BaoGangAppApi01101Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
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
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "312442");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><summary>申请 2015-10-01</><summary1>复审 2015-10-01</><summary2>审批 2015-10-01</><summary3>合同签订 2015-10-01</><summary4>贷款担保 2015-10-01</><summarydes>放款 2015-10-01</><transdes></><rreason></>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKJDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess_ts.equals(app002Result.getRecode())){
				String msg =app002Result.getMsg();
				if(!CommonUtil.isEmpty(app002Result.getMsg()))
				{
					if(app002Result.getMsg().contains("系统日终"))
					{
						msg = "温馨提示：由于公积金业务需每日进行日终结账，结账期间微信系统将无法查询，每日结账时间为17:30—18:30，望大家避开系统结账时间查询，敬请谅解！";
					}
				}
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", msg);
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
		}
		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		String apprflagDate = app002Result.getApprfDate();
		String apprflagID = app002Result.getApprflag();
		String apprflagMsg = app002Result.getApprflagMSG();
		if("02".equals(apprflagID))
		{
			apprflagID = "02";
			apprflagMsg = "初审中";
		}
		if("03".equals(apprflagID))
		{
			apprflagID = "04";
			apprflagMsg = "初审通过、提交复审";
		}
		if("04".equals(apprflagID))
		{
			apprflagID = "04";
			apprflagMsg = "初审通过、提交复审";
		}
		if("05".equals(apprflagID))
		{
			apprflagID = "05";
			apprflagMsg = "复审通过，提交审贷会";
		}
		if("06".equals(apprflagID))
		{
			apprflagID = "06";
			apprflagMsg = "审贷会通过，等候签订贷款合同";
		}
		if("07".equals(apprflagID))
		{
			apprflagID = "07";
			apprflagMsg = "合同签订完毕，办理抵押";
		}
		if("08".equals(apprflagID))
		{
			apprflagID = "09";
			apprflagMsg = "抵押登记完成";
		}
		if("09".equals(apprflagID))
		{
			apprflagID = "09";
			apprflagMsg = "抵押登记完成";
		}
		if("10".equals(apprflagID))
		{
			apprflagID = "10";
			apprflagMsg = "贷款发放";
		}
		//02:初审中04:初审通过、提交复审05:复审通过，提交审贷会06:审贷会通过，等候签订贷款合同07:合同签订完毕，办理抵押09:抵押登记完成10:贷款发放
		if("01".equals(apprflagID)||"11".equals(apprflagID)||"12".equals(apprflagID))
		{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "贷款进度不存在！");
			return modelMap;	
		}else
		{
			AppApi011Result app01102 = new AppApi011Result();
			app01102.setApprflagID("02");
			app01102.setApprflagMSG("初审中");
			list.add(app01102);	
			AppApi011Result app01104 = new AppApi011Result();
			app01104.setApprflagID("04");
			app01104.setApprflagMSG("初审通过、提交复审");
			list.add(app01104);	
			AppApi011Result app01105 = new AppApi011Result();
			app01105.setApprflagID("05");
			app01105.setApprflagMSG("复审通过，提交审贷会");
			list.add(app01105);	
			AppApi011Result app01106 = new AppApi011Result();
			app01106.setApprflagID("06");
			app01106.setApprflagMSG("审贷会通过，等候签订贷款合同");
			list.add(app01106);	
			AppApi011Result app01107 = new AppApi011Result();
			app01107.setApprflagID("07");
			app01107.setApprflagMSG("合同签订完毕，办理抵押");
			list.add(app01107);	
			AppApi011Result app01109 = new AppApi011Result();
			app01109.setApprflagID("09");
			app01109.setApprflagMSG("抵押登记完成");
			list.add(app01109);	
			AppApi011Result app01110 = new AppApi011Result();
			app01110.setApprflagID("10");
			app01110.setApprflagMSG("贷款发放");
			list.add(app01110);	
		}
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", apprflagID);
		modelMap.put("apprflagMSG", apprflagMsg);
		modelMap.put("apprflagDate", apprflagDate); 
		modelMap.put("result", list);	
		log.info(Constants.LOG_HEAD+"appApi01101 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		System.out.println(String.format("%,.2f",3.00/7*100));
	}

}
