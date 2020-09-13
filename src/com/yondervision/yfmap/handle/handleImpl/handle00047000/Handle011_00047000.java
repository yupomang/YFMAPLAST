package com.yondervision.yfmap.handle.handleImpl.handle00047000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi00402Result;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi01101Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi01101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle011_00047000
* @Description: 贷款进度查询
* 
*/ 
public class Handle011_00047000 implements CtrlHandleInter {
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi01101 start.");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api011DKJDGJJ.txt";
		HulunbuirAppApi01101Result app002Result = new HulunbuirAppApi01101Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setPubaccnum(aes.decrypt(form.getPubaccnum()));
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
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCX.txt", map, req);
			log.debug("发往中心报文11："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129923");
			
//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116004</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><summary>申请 2015-10-01</><summary1>复审 2015-10-01</><summary2>审批 2015-10-01</><summary3>合同签订 2015-10-01</><summary4>贷款担保 2015-10-01</><summarydes>放款 2015-10-01</><transdes></><rreason></>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKJDCX.txt", rexml, req);
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
		String apprflagID = "";
		String apprflagMsg = "";
		String apprflagDate = "";
		String loancontrnum = "";
		if(!app002Result.getFileName().equals("")){
			String filename = app002Result.getFileName().split("/")[app002Result.getFileName().split("/").length-1];
			app002Result.setFileName(filename);
			log.debug("远程文件名filaneme====="+filename);
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer("/"+filename);
		    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
		    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
			//========================================================================== 
			File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			while (line != null) {
				try {
						String[] param = line.split("~");									
						log.debug("读取文件  ："+line);
						apprflagID = param[4].trim();	
						apprflagMsg = param[9].trim();
						loancontrnum = param[0].trim();
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();						
				}
			}
			breader.close();
			isr.close();
			ffis.close();
			file.delete();
		}
		ArrayList<HulunbuirAppApi01101Result> list = new ArrayList<HulunbuirAppApi01101Result>();
		if("90".equals(apprflagID)||"99".equals(apprflagID))
		{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "贷款进度不存在！");
			return modelMap;	
		}else
		{
			HulunbuirAppApi01101Result app01102 = new HulunbuirAppApi01101Result();
			app01102.setApprflag("000");
			app01102.setApprflagMSG("申请");
			list.add(app01102);	
			HulunbuirAppApi01101Result app01104 = new HulunbuirAppApi01101Result();
			app01104.setApprflag("103");
			app01104.setApprflagMSG("复审");
			list.add(app01104);	
			HulunbuirAppApi01101Result app01105 = new HulunbuirAppApi01101Result();
			app01105.setApprflag("201");
			app01105.setApprflagMSG("审批");
			list.add(app01105);	
			HulunbuirAppApi01101Result app01106 = new HulunbuirAppApi01101Result();
			app01106.setApprflag("302");
			app01106.setApprflagMSG("合同签订");
			list.add(app01106);	
			HulunbuirAppApi01101Result app01107 = new HulunbuirAppApi01101Result();
			app01107.setApprflag("407");
			app01107.setApprflagMSG("贷款担保");
			list.add(app01107);	
			HulunbuirAppApi01101Result app01109 = new HulunbuirAppApi01101Result();
			app01109.setApprflag("508");
			app01109.setApprflagMSG("放款通知");
			list.add(app01109);	
			HulunbuirAppApi01101Result app01110 = new HulunbuirAppApi01101Result();
			app01110.setApprflag("609");
			app01110.setApprflagMSG("放款");
			list.add(app01110);	
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", apprflagID);
		modelMap.put("apprflagMSG", apprflagMsg);
		modelMap.put("apprflagDate", apprflagDate); 
		modelMap.put("loancontrnum", loancontrnum); 
		modelMap.put("result", list);	
		log.info(Constants.LOG_HEAD+"appApi01101 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		System.out.println(String.format("%,.2f",3.00/7*100));
	}

}
