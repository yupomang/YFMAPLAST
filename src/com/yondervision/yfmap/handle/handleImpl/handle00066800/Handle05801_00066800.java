package com.yondervision.yfmap.handle.handleImpl.handle00066800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi04501ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi05801ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi058_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle05801_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi05801ZHResult app05801ZHResult = new AppApi05801ZHResult();
		AppApi058_01ZHResult app058_01ZHResult = new AppApi058_01ZHResult();
		List<AppApi058_01ZHResult> list = new ArrayList<AppApi058_01ZHResult>();
		HashMap resultMap = null;     
		log.debug("00066800请求05801参数："+CommonUtil.getStringParams(form));
		if(Constants.method_BSP.equals(send)){

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));

			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_FWPJCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_FWPJCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314045");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314045</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/ddd.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_FWPJCX.txt", rexml, req);
//		    resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_FWPJCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app05801ZHResult);
			log.debug("MAP封装成BEAN："+app05801ZHResult);
			if(!"0".equals(app05801ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app05801ZHResult.getRecode());
				modelMap.put("msg", app05801ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app05801ZHResult.getRecode()+"  ,  描述msg : "+app05801ZHResult.getMsg());
				return modelMap;
			}
		}
//	    String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
		String filename = (String)resultMap.get("filename");
		System.out.println("查询公积金明细，批量文件："+filename);
		if("40".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("totalpage", app05801ZHResult.getTotalpage());
			modelMap.put("totalnum", app05801ZHResult.getTotalnum());
			modelMap.put("filename", filename);
			return modelMap;
		}
		if(!CommonUtil.isEmpty(filename)){
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer(filename);				    
		    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//			File file = new File(filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			
			while (line != null) {// 判断读取到的字符串是否不为空
				
				try {
					AppApi058_01ZHResult aap058_01ZHResult = new AppApi058_01ZHResult();
					MoneyNumberTran mnTran = new MoneyNumberTran();
					log.debug("读取文件  ："+line);	
					String[] trs = line.split("~");
					aap058_01ZHResult.setId(trs[0]);
					aap058_01ZHResult.setTransdate(trs[1]);
//					aap058_01ZHResult.setTradetype(Tools.getCode(trs[2]));
					aap058_01ZHResult.setTrademsg(trs[2]);
					aap058_01ZHResult.setAgentinstcode(Tools.getCode(trs[3]));
					aap058_01ZHResult.setAgentinstmsg(Tools.getMessage(trs[3]));
					aap058_01ZHResult.setFreedata("");
					aap058_01ZHResult.setDetail(Tools.getMessage(trs[4]));
					aap058_01ZHResult.setCounternum(trs[5]);
					aap058_01ZHResult.setCountername(trs[6]);
					list.add(aap058_01ZHResult);
					
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			breader.close();
			isr.close();
			ffis.close();
			file.delete();
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查无数据");
			return modelMap;
		}

//	
//	List<List<TitleInfoNameFormatBean>> result = new ArrayList<List<TitleInfoNameFormatBean>>();	
//	List<TitleInfoNameFormatBean> appapi05801Result = null;
//	for(int i=0;i<list.size();i++){
//		
//		appapi05801Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi05801"+form.getCenterId()+".result", list.get(i));	
//		result.add(appapi05801Result);
//	}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
//		modelMap.put("result", result);
		modelMap.put("result", list);		

		return modelMap;
	}
	
}
