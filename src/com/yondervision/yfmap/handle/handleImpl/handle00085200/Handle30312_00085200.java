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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.AppApi30305Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30309_00085200 
* @Description: 预约查询
* @author Caozhongyan
* @date Apr 12, 2016 3:41:14 PM   
* 
*/ 
public class Handle30312_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30312 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		ZunYiAppApi00201Result app00802Result = new ZunYiAppApi00201Result();
		
		List<AppApi30305Result> result = new ArrayList<AppApi30305Result>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api30310WDRSGJJ.txt";//预约网点人数
		
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YYCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()+"YY").trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()+"YY").trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "C00006");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>yycx</><sumcounts></><counts></><pageflag>1</>";
			
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYCX.txt", rexml, req);
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
				 *  
				 */
				int applyid=0,type=0,reservedate=0,bankcode=0,reservetime=0;
				for(int i=0;i<paraNames.length;i++){
					if("applyid".equals(paraNames[i])){
						applyid = i;//预约号
					}else if("type".equals(paraNames[i])){
						type = i;//类型
					}else if("reservedate".equals(paraNames[i])){
						reservedate = i;//预约日期
					}else if("bankcode".equals(paraNames[i])){
						bankcode = i;//名称
					}else if("reservetime".equals(paraNames[i])){
						reservetime = i;//reservetime
					}
					
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
//					modelMap.put("recode", "999999");
//					modelMap.put("msg", "无数据");
//					log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
//					return modelMap;
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
						
						AppApi30305Result item1 = new AppApi30305Result();
						item1.setAppointid(param[type]);
						List<TitleInfoBean> beanList = new ArrayList<TitleInfoBean>();
						TitleInfoBean bean1 = new TitleInfoBean();
						bean1.setTitle("预约号");
						bean1.setInfo(param[applyid]);
						beanList.add(bean1);	
						
						TitleInfoBean bean2 = new TitleInfoBean();
						bean2.setTitle("预约业务");
						log.debug("type :"+type+" ,value :"+param[type].trim());
						bean2.setInfo("0".equals(param[type].trim())?"提取预约":"贷款预约");
						beanList.add(bean2);
						
						TitleInfoBean bean3 = new TitleInfoBean();
						bean3.setTitle("网点名称");
						bean3.setInfo(param[bankcode]);
						beanList.add(bean3);
						
						TitleInfoBean bean4 = new TitleInfoBean();
						bean4.setTitle("预约日期");
						bean4.setInfo(param[reservedate]);
						beanList.add(bean4);
						
						TitleInfoBean bean5 = new TitleInfoBean();
						bean5.setTitle("预约时段");
						bean5.setInfo("0".equals(param[reservetime])?"上午":"下午");
						beanList.add(bean5);
						
						item1.setAppointmes(beanList);
						result.add(item1);
						
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
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi30312 end.");
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
		Handle30312_00085200 hand = new Handle30312_00085200();
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
