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
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00201Result;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi30309Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30309_00047201
* @Description: 预约网点及人数查询
* 
*/ 
public class Handle30309_00047201 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30309 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 业务类型查询 form 参数:"+form);
		log.debug("YFMAP 业务类型查询 form 参数:form.getValue2()"+form.getValue2());
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		BaoGangAppApi00201Result app00802Result = new BaoGangAppApi00201Result();
		
//		form.setSelectValue(URLDecoder.decode(form.getSelectValue(),"UTF-8"));
		
		List resultList = new ArrayList();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api30309YYWDRSGJJ.txt";//预约网点人数
		if(Constants.method_BSP.equals(send)){
			if("10".equals(form.getChannel()))
			{
				form.setChannel("21");
			}else if("20".equals(form.getChannel()))
			{
				form.setChannel("31");
			}
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			//form.setAppobranchid("00004711");
			HashMap map = BeanUtil.transBean2Map(form);
			if("1".equals(form.getValue2()))
			{
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQDLCX.txt", map, req);
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "147780");

//				String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>yywdrscx.txt</><sumcounts></><counts></><pageflag>1</>";
				
				
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQDLCX.txt", rexml, req);
				BeanUtil.transMap2Bean(resultMap, app00802Result);
				if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
					modelMap.clear();
					modelMap.put("recode", app00802Result.getRecode());
					modelMap.put("msg", app00802Result.getMsg());
					log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
					return modelMap;
				}	
				if(!app00802Result.getFileName().equals("")){
					FtpUtil f=new FtpUtil("bsp");
				    f.getFileByServer("/"+app00802Result.getFileName());
				    File filerename = new File(ReadProperties.getString("bsp_local_path")+app00802Result.getFileName());
				    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
					//========================================================================== 
					File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					
					String[] paraNames = line.split("~");
					/***
						Typename	大类名称
						Typenumber	大类编号
					 */
					int typename=0,typenumber=0;
					for(int i=0;i<paraNames.length;i++){
						if("Typename".equals(paraNames[i])){
							typename = i;
						}else if("Typenumber".equals(paraNames[i])){
							typenumber = i;
						}
					}
					line = breader.readLine();
					if(line == null){
						//异常无数据返回提示
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "目前不能预约");
						log.error("中心返回报文 状态recode : 999999  ,  描述msg : 目前不能预约");
						return modelMap;
					}
					while (line != null) {					
						try {
							line=line+"*123+321*";
							String[] param = line.split("~");
							log.debug("读取文件  ："+line);
							log.debug("长度  ："+param.length);
							if(!"*123+321*".equals(param[param.length-1])){
								param[param.length-1]=param[param.length-1].substring(0,param[param.length-1].indexOf("*123+321*"));
							}else{
								param[param.length-1]="";
							}
							BaoGangAppApi30309Result appApi30309Result = new BaoGangAppApi30309Result();		
							appApi30309Result.setAppobranchid(param[typenumber]);
							appApi30309Result.setAppobranchname(param[typename]);
							resultList.add(appApi30309Result);
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
			}else if("2".equals(form.getValue2()))
			{
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQYWCX.txt", map, req);
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "147781");

//				String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>yywdrscx.txt</><sumcounts></><counts></><pageflag>1</>";
				
				
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQYWCX.txt", rexml, req);
				BeanUtil.transMap2Bean(resultMap, app00802Result);
				if(!"0".equals(app00802Result.getRecode())){				
					modelMap.clear();
					modelMap.put("recode", app00802Result.getRecode());
					modelMap.put("msg", app00802Result.getMsg());
					log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
					return modelMap;
				}	
				if(!app00802Result.getFileName().equals("")){
					FtpUtil f=new FtpUtil("bsp");
				    f.getFileByServer("/"+app00802Result.getFileName());
				    File filerename = new File(ReadProperties.getString("bsp_local_path")+app00802Result.getFileName());
				    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
					//========================================================================== 
					File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					
					String[] paraNames = line.split("~");
					/***
						Vicenumber	业务编号
						Vicename	业务名称
						Maidate		维护日期
					 */
					int vicenumber=0,vicename=0,maidate=0;
					for(int i=0;i<paraNames.length;i++){
						if("Vicenumber".equals(paraNames[i])){
							vicenumber = i;
						}else if("Vicename".equals(paraNames[i])){
							vicename = i;
						}else if("Maidate".equals(paraNames[i])){
							maidate = i;
						}
					}
					line = breader.readLine();
					if(line == null){
						//异常无数据返回提示
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "目前不能预约");
						log.error("中心返回报文 状态recode : 999999  ,  描述msg : 目前不能预约");
						return modelMap;
					}
					while (line != null) {					
						try {
							line=line+"*123+321*";
							String[] param = line.split("~");
							log.debug("读取文件  ："+line);
							log.debug("长度  ："+param.length);
							if(!"*123+321*".equals(param[param.length-1])){
								param[param.length-1]=param[param.length-1].substring(0,param[param.length-1].indexOf("*123+321*"));
							}else{
								param[param.length-1]="";
							}
							BaoGangAppApi30309Result appApi30309Result = new BaoGangAppApi30309Result();				
							appApi30309Result.setAppobranchid(param[vicenumber]);
							appApi30309Result.setAppobranchname(param[vicename]);
							appApi30309Result.setAppobranchdate(param[maidate]);
							resultList.add(appApi30309Result);
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
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			log.debug("result  ："+resultList);
			modelMap.put("result", resultList);
		}
			
		log.info(Constants.LOG_HEAD+"appApi30309 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle30309_00047201 hand = new Handle30309_00047201();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
