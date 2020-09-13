package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi00201ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi002_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle002_00066800 implements CtrlHandleInter {
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
		log.debug("00066800请求00201参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		HashMap resultMap =null;
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}
			
			
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRMXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRMXCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110019");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110019</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
//			String rexml ="<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag>0</><BrcCode>00073199</><BusiSeq>137199</><ChannelSeq>0</><ChkCode></><MTimeStamp>2016-12-12 03:56:03</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-12 03:56:03</><TellCode>9999</><TranChannel>7</><TranCode>110019</><TranDate>2017-04-13</><TranIP></><TranSeq>137199</><filename>F:/asdf.txt</><totalnum>0</><totalpage>0</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_GRMXCX.txt", rexml, req);			
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRMXCX.txt", rexml, req);
			
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
			System.out.println("查询个人明细，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app00201ZHResult.getTotalpage());
				modelMap.put("totalnum", app00201ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				modelMap.put("result", "");
				return modelMap;
			}

			if ("0".equals(app00201ZHResult.getTotalnum())) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到个人明细数据");	
				modelMap.put("totalpage", app00201ZHResult.getTotalpage());
				modelMap.put("totalnum", app00201ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
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
						AppApi002_01ZHResult app002_01ZHResult = new AppApi002_01ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app002_01ZHResult.setJzrq(trs[1]);					
						app002_01ZHResult.setGjhtqywlx(trs[8]);
						app002_01ZHResult.setFse(trs[6]);
						app002_01ZHResult.setBal(trs[7]);						
						app002_01ZHResult.setBegdate(trs[9]);
						app002_01ZHResult.setEnddate(trs[10]);
						app002_01ZHResult.setAgentinstcode(trs[11]);													
						list.add(app002_01ZHResult);
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
		List<TitleInfoNameFormatBean> appapi00201Result = null;
		for(int i=0;i<list.size();i++){
			appapi00201Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00201"+form.getCenterId()+".detail", list.get(i));
			detail.add(appapi00201Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app00201ZHResult.getTotalpage());
		modelMap.put("totalnum", app00201ZHResult.getTotalnum());
		modelMap.put("filename", app00201ZHResult.getFilename());
		
		return modelMap;
	}

}
