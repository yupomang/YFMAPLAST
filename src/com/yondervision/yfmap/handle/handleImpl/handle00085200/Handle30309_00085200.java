package com.yondervision.yfmap.handle.handleImpl.handle00085200;

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
import com.yondervision.yfmap.result.zunyi.AppApi30302Result;
import com.yondervision.yfmap.result.zunyi.DateCountBean;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30309_00085200 
* @Description: 预约网点及人数查询
* @author Caozhongyan
* @date Apr 12, 2016 3:41:14 PM   
* 
*/ 
public class Handle30309_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30309 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约网点及人数查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		ZunYiAppApi00201Result app00802Result = new ZunYiAppApi00201Result();
		
		List<AppApi30302Result> list = new ArrayList<AppApi30302Result>();
//		form.setSelectValue(URLDecoder.decode(form.getSelectValue(),"UTF-8"));
		
		List resultList = new ArrayList();		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api30309YYWDRSGJJ.txt";//预约网点人数
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YYWDRSCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()+"YY").trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()+"YY").trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128031");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>yywdrscx.txt</><sumcounts></><counts></><pageflag>1</>";
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYWDRSCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!"0".equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}	
			if(!app00802Result.getFileName().equals("")){
				FtpUtil f=new FtpUtil("wt");
			    f.getFileByServer("/"+app00802Result.getFileName());
			    File filerename = new File(ReadProperties.getString("wt_local_path")+app00802Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("wt_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("wt_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				
				String[] paraNames = line.split("~");
				/***
				 *  bankcode	银行网点编号
					bankname	网点名称
					reservedate1	预约日期
					remncount1	可预约人数
					reservedate2	预约日期
					remncount2	可预约人数
					reservedate3	预约日期
					remncount3	可预约人数
				 */
				int bankcode=0,bankname=0,reservedate1=0,remncount1=0,reservedate2=0,remncount2=0,reservedate3=0,remncount3=0;
				for(int i=0;i<paraNames.length;i++){
					if("bankcode".equals(paraNames[i])){
						bankcode = i;
					}else if("bankname".equals(paraNames[i])){
						bankname = i;
					}else if("reservedate1".equals(paraNames[i])){
						reservedate1 = i;
					}else if("remncount1".equals(paraNames[i])){
						remncount1 = i;
					}else if("reservedate2".equals(paraNames[i])){
						reservedate2 = i;
					}else if("remncount2".equals(paraNames[i])){
						remncount2 = i;
					}else if("reservedate3".equals(paraNames[i])){
						reservedate3 = i;
					}else if("remncount3".equals(paraNames[i])){
						remncount3 = i;
					}
					
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "该管理部目前不能预约");
					log.error("中心返回报文 状态recode : 999999  ,  描述msg : 该管理部目前不能预约");
					return modelMap;
				}
				
				//目前只有交易日期及摘要，缺少“业务类型、单位缴存、个人缴存、缴存合计”
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
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						//**********************************接口不完整，需要进一步入理。
						

						AppApi30302Result appApi30309Result = new AppApi30302Result();					
						appApi30309Result.setAppobranchid(param[bankcode]);
						appApi30309Result.setAppobranchname(param[bankname]);
						
						DateCountBean bean1 = new DateCountBean();
						bean1.setAppodate(param[reservedate1]);
						bean1.setAppocnt(param[remncount1]);
						
						DateCountBean bean2 = new DateCountBean();
						bean2.setAppodate(param[reservedate2]);
						bean2.setAppocnt(param[remncount2]);
						
						DateCountBean bean3 = new DateCountBean();
						bean3.setAppodate(param[reservedate3]);//网点编码
						bean3.setAppocnt(param[remncount3]);//网点名称
						List<DateCountBean> listdatecount = new ArrayList<DateCountBean>();
						listdatecount.add(bean1);//时间1
						listdatecount.add(bean2);//时间2
						listdatecount.add(bean3);//时间3
						appApi30309Result.setContent(listdatecount);
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
		modelMap.put("result", resultList);
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
		Handle30309_00085200 hand = new Handle30309_00085200();
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
