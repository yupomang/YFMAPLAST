package com.yondervision.yfmap.handle.handleImpl.handle00047102;

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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi01101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle011_00047102
* @Description: 贷款进度查询
* 
*/ 
public class Handle011_00047102 implements CtrlHandleInter {
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
		QvZhiAppApi00201Result app002Result = new QvZhiAppApi00201Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
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
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129121");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><summary>申请 2015-10-01</><summary1>复审 2015-10-01</><summary2>审批 2015-10-01</><summary3>合同签订 2015-10-01</><summary4>贷款担保 2015-10-01</><summarydes>放款 2015-10-01</><transdes></><rreason></>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKJDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!"0".equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
		}
		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		String apprflagDate = "";
		String apprflagID = "";
		String apprflagMsg = "";
		if(!app002Result.getFileName().equals("")){
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer("/"+app002Result.getFileName());
		    File filerename = new File(ReadProperties.getString("bsp_local_path")+app002Result.getFileName());
		    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
			//========================================================================== 
			File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			line = breader.readLine();
			String[] paraNames = line.split("~");
			apprflagID = paraNames[0];
			apprflagMsg = paraNames[1];
			apprflagDate = paraNames[2];
			//00-申请, 03-复审, 01-审批, 02-合同签订, 07-贷款担保, 08-放款通知, 09-放款
			AppApi011Result app01100 = new AppApi011Result();
			app01100.setApprflagID("100");
			app01100.setApprflagMSG("申请");
			list.add(app01100);	
			AppApi011Result app01103 = new AppApi011Result();
			app01103.setApprflagID("203");
			app01103.setApprflagMSG("复审");
			list.add(app01103);	
			AppApi011Result app01101 = new AppApi011Result();
			app01101.setApprflagID("301");
			app01101.setApprflagMSG("审批");
			list.add(app01101);	
			AppApi011Result app01102 = new AppApi011Result();
			app01102.setApprflagID("402");
			app01102.setApprflagMSG("合同签订");
			list.add(app01102);	
			AppApi011Result app01107 = new AppApi011Result();
			app01107.setApprflagID("507");
			app01107.setApprflagMSG("贷款担保");
			list.add(app01107);	
			AppApi011Result app01108 = new AppApi011Result();
			app01108.setApprflagID("608");
			app01108.setApprflagMSG("放款通知");
			list.add(app01108);	
			AppApi011Result app01109 = new AppApi011Result();
			app01109.setApprflagID("709");
			app01109.setApprflagMSG("放款");
			list.add(app01109);	
			breader.close();
			isr.close();
			ffis.close();
			file.delete();
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
