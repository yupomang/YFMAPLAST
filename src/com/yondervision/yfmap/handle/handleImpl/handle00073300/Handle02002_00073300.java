package com.yondervision.yfmap.handle.handleImpl.handle00073300;

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
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi002_01ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi02001ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi02002ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi020_02ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle02002_00073300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00073300请求02002参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		List<AppApi020_02ZHResult> list = new ArrayList<AppApi020_02ZHResult>();
		AppApi02002ZHResult app02002ZHResult = new AppApi02002ZHResult();		             	

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
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}			
			
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_JBRXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_JBRXXCX.txt", map, req);			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "160111");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>160111</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
			
			log.debug("前置YFMAP接收中心报文——单位基本信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_JBRXXCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_JBRXXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02002ZHResult);
			log.debug("MAP封装成BEAN："+app02002ZHResult);
			if(!"0".equals(app02002ZHResult.getRecode() )){
				modelMap.clear();
				modelMap.put("recode", app02002ZHResult.getRecode());
				modelMap.put("msg", app02002ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02002ZHResult.getRecode()+"  ,  描述msg : "+app02002ZHResult.getMsg());
				return modelMap;
			}
			String filename = (String)resultMap.get("filename");
		//	System.out.println(filename);
			System.out.println("查询经办人信息，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app02002ZHResult.getTotalpage());
				modelMap.put("totalnum", app02002ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				modelMap.put("result", "");
				return modelMap;
			}
//			if ("0".equals(app02002ZHResult.getTotalnum())) {
//				modelMap.clear();
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "未查询到数据，请重新输入");	
//				modelMap.put("totalpage", app02002ZHResult.getTotalpage());
//				modelMap.put("totalnum", app02002ZHResult.getTotalnum());
//				modelMap.put("filename", filename);
//				return modelMap;
//			}
			if(!CommonUtil.isEmpty(filename)){				
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);	    
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//				File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);				
				String line = breader.readLine(); 
				String line1 = breader.readLine(); 
				while (line1 != null) {// 判断读取到的字符串是否不为空					
					try {							
						AppApi020_02ZHResult app020_02ZHResult = new AppApi020_02ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app020_02ZHResult.setDwzh(trs[0]);					
						app020_02ZHResult.setJbrxm(trs[1]);
						app020_02ZHResult.setJbrgddhhm(trs[2]);
						app020_02ZHResult.setJbrsjhm(trs[3]);						
						app020_02ZHResult.setJbrzjlx(trs[4]);
						app020_02ZHResult.setJbrzjhm(trs[5]);
						app020_02ZHResult.setJbrdzyx(trs[6]);
						app020_02ZHResult.setJbrrzsj(trs[7]);
						app020_02ZHResult.setJbrqq(trs[8]);
						app020_02ZHResult.setJbrsfpx(trs[9]);
						list.add(app020_02ZHResult);
						line1 = breader.readLine();
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
		}
		
		List<List<TitleInfoNameFormatBean>> detail = new ArrayList();					
		List<TitleInfoNameFormatBean> appapi02002Detail = null;
		for(int i=0;i<list.size();i++){
			appapi02002Detail = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02002"+form.getCenterId()+".detail", list.get(i));
			detail.add(appapi02002Detail);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app02002ZHResult.getTotalpage());
		modelMap.put("totalnum", app02002ZHResult.getTotalnum());
		modelMap.put("filename", app02002ZHResult.getFilename());
		return modelMap;
	}
	
}
