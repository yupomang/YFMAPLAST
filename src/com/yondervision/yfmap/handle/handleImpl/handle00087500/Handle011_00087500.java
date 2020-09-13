package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.yondervision.yfmap.result.baoshan.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle011_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
//		AppApi01101Result appapi011 = new AppApi01101Result();
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101Result app011Result = new AppApi01101Result();
		List<AppApi011Result> list = new ArrayList<AppApi011Result>();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
		Date date = new Date();
		String fname = formatter1.format(date)+"api10101"+"SF"+form.getBodyCardNumber()+"GJJDKJDCX"+form.getSurplusAccount()+".txt";
		
		if(Constants.method_XML.equals(send)){//xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKJDCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430407");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430402</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>成功</ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><transdate>2015-05-01</transdate><aloancontrnum>12345678901</aloancontrnum><loancontrstate>05</loancontrstate><des>贷款发放</des></body></root>";
			log.debug("前置YFMAP接收中心报文："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKJDCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			if(!"0".equals(app011Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
			String filename = app011Result.getFilename();
			System.out.println("查询贷款进度查询，返回文件名："+filename);
			
			if(filename!=null && !"".equals(filename)){
				//String filename = app007Result.getFileName().substring(aa00701Result.getFileName().lastIndexOf("/"));
				/****
				 *	此处应加入FTP下载 
				 **/

				FtpUtil f=new FtpUtil("csp");
			    f.getFileByServer(filename);				    
			    File filerename = new File(ReadProperties.getString("csp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname);
//				File file = new File("/edoc"+"/"+app002Result.getFilename());
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();								
				log.debug(line);
				int lineNo = 1;
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi011Result app011 = new AppApi011Result();
					try {
						String[] param = line.split("\\|");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);		
						if(lineNo >=2){
							app011.setApprflagID(param[2].trim());
							app011.setApprflagMSG(param[3].trim());
							app011.setApprflagDate(param[4].trim());
							list.add(app011);
						}
						lineNo = lineNo + 1;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "批量明细信息处理异常");
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprnum", app011Result.getApprnum());
		modelMap.put("apprflagID", app011Result.getApprflag());
		modelMap.put("apprflagMSG", app011Result.getApprmsg());
		modelMap.put("apprflagDate", app011Result.getTransdate());
		modelMap.put("result", list);	
		return modelMap;
	}

}
