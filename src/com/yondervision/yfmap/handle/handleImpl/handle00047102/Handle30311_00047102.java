package com.yondervision.yfmap.handle.handleImpl.handle00047102;

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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi30303Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi30305Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30309_00047102
* @Description: 预约确认
* 
*/ 
public class Handle30311_00047102 implements CtrlHandleInter {
Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30311 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约确认 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api30311WDQRGJJ.txt";//预约网点确认
		QvZhiAppApi00201Result app00802Result = new QvZhiAppApi00201Result();
		List<QvZhiAppApi30305Result> result = new ArrayList<QvZhiAppApi30305Result>();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			form.setAppobranchid("00004711");
			AES aes = new AES();
			log.debug("YFMAP 姓名参数:"+form.getValue1());
			//form.setValue1(aes.decrypt(form.getValue1()));
			//log.debug("YFMAP 姓名参数:"+form.getValue1());
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YYQR.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149004");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><applyid>2800001282101</>";
			
			log.debug("中心返回YFMAP报文："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYQR.txt", rexml, req);
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
				 *  
				 */
				int transdate=0,timestamp=0,trantype=0,vounum=0;
				for(int i=0;i<paraNames.length;i++){
					if("transdate".equals(paraNames[i])){
						transdate = i;//办理日期
					}else if("timestamp".equals(paraNames[i])){
						timestamp = i;//办理时段
					}else if("trantype".equals(paraNames[i])){
						trantype = i;//预约业务
					}else if("vounum".equals(paraNames[i])){
						vounum = i;//预约号码
					}
					
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
					modelMap.put("recode", "999999");
					modelMap.put("msg", "无数据");
					log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
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
						
						QvZhiAppApi30305Result item1 = new QvZhiAppApi30305Result();
						item1.setAppointid(param[vounum]);
						List<TitleInfoBean> beanList = new ArrayList<TitleInfoBean>();
						TitleInfoBean bean1 = new TitleInfoBean();
						bean1.setTitle("预约号");
						bean1.setInfo(param[vounum]);
						app00802Result.setAccinstname(param[vounum]);
						beanList.add(bean1);	
						
						TitleInfoBean bean4 = new TitleInfoBean();
						bean4.setTitle("预约日期");
						bean4.setInfo(param[transdate]);
						beanList.add(bean4);
						
						TitleInfoBean bean5 = new TitleInfoBean();
						bean5.setTitle("预约时段");
						bean5.setInfo((param[timestamp]));
						beanList.add(bean5);
						
						TitleInfoBean bean2 = new TitleInfoBean();
						bean2.setTitle("预约业务");
						bean2.setInfo(param[trantype].trim());
						beanList.add(bean2);
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
		modelMap.put("apponum", app00802Result.getAccinstname());//预约号
		log.info(Constants.LOG_HEAD+"appApi30311 end.");
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
		Handle30311_00047102 hand = new Handle30311_00047102();
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
