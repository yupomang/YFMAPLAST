package com.yondervision.yfmap.handle.handleImpl.handle00047101;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40116Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.mengdian.AppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle40121_00047101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi40114 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40116Form form = (AppApi40116Form)form1;
		AES aes =new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		log.debug(""+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi40101Result app40101Result = new AppApi40101Result();
		Map<String,String> result_map = new HashMap<String,String>();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DLQJC.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "112805");
//			String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>12345678</><BusiSeq>543</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-08-09 11:38:12</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-08-09 11:38:12</><TellCode>cweb</><TranChannel>00</><TranCode>112805</><TranDate>2016-08-09</><TranIP>192.168.10.20</><TranSeq>543</><actmp1024>/bsprun/00000000/fil/150104197207150539_543</>";
			log.debug("中心返回前置YFMAP报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DLQJC.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}else{
				String fname = formatter1.format(date)+"_"+form.getIdcardNumber()+"_"+"api40101DLQJC.txt";
				String filename = app40101Result.getFileName().split("/")[app40101Result.getFileName().split("/").length-1];
				app40101Result.setFileName(filename);
				log.debug("远程文件名filaneme====="+filename);
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+app40101Result.getFileName());
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+app40101Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				String surplusAccount = "";
				int i = 0;
				if(line!=null&&!("").equals(line)){
					while (line != null) {
						try {
							if(i!=0){
								String[] param = line.split("~");
								surplusAccount = surplusAccount.length()==0?param[0]:surplusAccount+","+param[0];
							}
							line = breader.readLine();
							i++;
						} catch (IOException e) {
							e.printStackTrace();						
						}
					}
					result_map.put("surplusAccount", surplusAccount);
					result_map.put("idcardNumber", form.getIdcardNumber());
					breader.close();
					isr.close();
					ffis.close();
					file.delete();
				}else{
					modelMap.put("recode", "000000");
					modelMap.put("msg", "该身份证号无公积金");
					log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
					return modelMap;
				}
			}		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result_map);
		log.info(Constants.LOG_HEAD+"appApi40114 end.");
		return modelMap;
	}


}
