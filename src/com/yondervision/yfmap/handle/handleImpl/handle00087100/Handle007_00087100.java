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
import java.util.List;
import java.util.ResourceBundle;

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
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00701Result;
import com.yondervision.yfmap.result.kunming.AppApi00701ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi007_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle007_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00701ZHResult app007ZHResult = new AppApi00701ZHResult();
		
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi007_01ZHResult> list = new ArrayList<AppApi007_01ZHResult>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		HashMap resultMap = null;
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());	
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
            form.setFlag(Channel.getChannel(form.getChannel()));
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			} 
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GR_HKMXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GR_HKMXCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314016");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>000000</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314016</><TranDate>5</><TranIP>2</><TranSeq>9</><filename>F:/ccc.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GR_HKMXCX.txt", rexml, req);
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GR_HKMXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app007ZHResult);
			log.debug("MAP封装成BEAN："+app007ZHResult);
			if(!"0".equals(app007ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app007ZHResult.getRecode());
				modelMap.put("msg", app007ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app007ZHResult.getRecode()+"  ,  描述msg : "+app007ZHResult.getMsg());
				return modelMap;
			}
			
//		    String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			String filename = (String)resultMap.get("filename");
			System.out.println("查询公积金明细，批量文件："+filename);
			System.out.println("查询公积金明细，批量文件："+filename);
			if("40".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app007ZHResult.getTotalpage());
				modelMap.put("totalnum", app007ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
			    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//				File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空
					
					try {
						AppApi007_01ZHResult aap007_01ZHResult = new AppApi007_01ZHResult();
						MoneyNumberTran mnTran = new MoneyNumberTran();
						log.debug("读取文件  ："+line);
						line = line + "~0";
						//String[] param = line.split("\\@\\|\\$");	
						String[] trs = line.split("~");
						aap007_01ZHResult.setInfactdate(trs[6]);
						aap007_01ZHResult.setSeqnum(trs[0]);
						aap007_01ZHResult.setTranstype(Tools.getMessage(trs[1]));
						aap007_01ZHResult.setInfactprin(mnTran.moneyTran(trs[8]));//本金
						aap007_01ZHResult.setInfactint(mnTran.moneyTran(trs[9]));//利息
						aap007_01ZHResult.setInfactsum(mnTran.moneyTran(trs[10]));//合计
						aap007_01ZHResult.setInfactpun(mnTran.moneyTran(trs[7]));//罚息
						
						list.add(aap007_01ZHResult);
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
		List<List<TitleInfoNameFormatBean>> result = new ArrayList<List<TitleInfoNameFormatBean>>();	
		List<TitleInfoNameFormatBean> appapi00701Result = null;
		for(int i=0;i<list.size();i++){
			
			appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00701"+form.getCenterId()+".result", list.get(i));	
			result.add(appapi00701Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		modelMap.put("totalpage", app007ZHResult.getTotalpage());
		modelMap.put("totalnum", app007ZHResult.getTotalnum());
//		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}
}
