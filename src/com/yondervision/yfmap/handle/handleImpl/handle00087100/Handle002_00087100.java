package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00201Result;
import com.yondervision.yfmap.result.kunming.AppApi00201ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi002_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle002_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	@SuppressWarnings("rawtypes")	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00201ZHResult app00201ZHResult = new AppApi00201ZHResult();
		
		AppApi00201Form form = (AppApi00201Form)form1;
		List<AppApi002_01ZHResult> list = new ArrayList<AppApi002_01ZHResult>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		HashMap resultMap =null;
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_GRGJJMXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GR_GRGJJMXCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314014");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>000000</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314014</><TranDate>5</><TranIP>2</><TranSeq>9</><filename>F:/aaa.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_GR_GRGJJMXCX.txt", rexml, req);
			
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GR_GRGJJMXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00201ZHResult);
			log.debug("MAP封装成BEAN："+app00201ZHResult);
			if(!"0".equals(app00201ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00201ZHResult.getRecode());
				modelMap.put("msg", app00201ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00201ZHResult.getRecode()+"  ,  描述msg : "+app00201ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
//			String filename = (String)resultMap.get("filename");//aaa.txt
		//	System.out.println(filename);
			System.out.println("查询公积金明细，批量文件："+filename);
			if("40".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app00201ZHResult.getTotalpage());
				modelMap.put("totalnum", app00201ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){				
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
//			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
//			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+"/"+filename));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//				File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				
				String line = breader.readLine();
//				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
//				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
//				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空
					//AppApi00201ZHResult aap00201ZHResult = new AppApi00201ZHResult();
					try {
						AppApi002_01ZHResult app002_01ZHResult = new AppApi002_01ZHResult();
						MoneyNumberTran mntran = new MoneyNumberTran();
						log.debug("读取文件  ："+line);
						line = line + "~0";
						String[] trs = line.split("~");	
						if(form.getChannel().trim().equals("50")){
							app002_01ZHResult.setTransdate(trs[0]);
							app002_01ZHResult.setSummary(Tools.getMessage(trs[1]));
							app002_01ZHResult.setCamt(mntran.moneyTran(trs[4]));
							app002_01ZHResult.setDamt(mntran.moneyTran(trs[3]));
							app002_01ZHResult.setBalance(mntran.moneyTran(trs[5]));
							app002_01ZHResult.setPaybegindate(trs[9]);
							app002_01ZHResult.setPayenddate(trs[10]);
							app002_01ZHResult.setAgentinstcode(Tools.getMessage(trs[11]));
							
						}else{
							app002_01ZHResult.setTransdate(trs[0]);
							app002_01ZHResult.setSummary(Tools.getMessage(trs[1]));
							app002_01ZHResult.setCamt(mntran.moneyTran(trs[4]));
							app002_01ZHResult.setDamt(mntran.moneyTran(trs[3]));
							app002_01ZHResult.setBalance(mntran.moneyTran(trs[5]));
							
						}
						list.add(app002_01ZHResult);
						line = breader.readLine();
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
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
		}
		List<List<TitleInfoNameFormatBean>> result = new ArrayList();	
		if(form.getChannel().trim().equals("50")){
			List<TitleInfoNameFormatBean> appapi002Result = null;
		    for(int i=0;i<list.size();i++){
			  appapi002Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00201"+form.getCenterId()+".detail", list.get(i));
			  result.add(appapi002Result);
		    }
		}else {		
		    List<TitleInfoNameFormatBean> appapi00201Result = null;
		    for(int i=0;i<list.size();i++){
			  appapi00201Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00201"+form.getCenterId()+".result", list.get(i));
			  result.add(appapi00201Result);
		    }
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("totalpage", app00201ZHResult.getTotalpage());
		modelMap.put("totalnum", app00201ZHResult.getTotalnum());
		modelMap.put("result", result);
		return modelMap;
	}

	
}
