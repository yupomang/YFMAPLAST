package com.yondervision.yfmap.handle.handleImpl.handle00077500;

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
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi00604ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi006_04ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00604_00077500 implements CtrlHandleInter {
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
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.debug("00077500请求00604参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		List<AppApi006_04ZHResult> list = new ArrayList<AppApi006_04ZHResult>();
		
		AppApi00604ZHResult app00604ZHResult= new AppApi00604ZHResult();
		/* 模拟返回开始  */
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());	
			
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
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTHCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DKHTHCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126998");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126998</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/aaa.txt</><totalpage>0</><totalnum>2</>";

			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKHTHCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DKHTHCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00604ZHResult);
			log.debug("MAP封装成BEAN："+app00604ZHResult);
			if(!"0".equals(app00604ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00604ZHResult.getRecode());
				modelMap.put("msg", app00604ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00604ZHResult.getRecode()+"  ,  描述msg : "+app00604ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println(" 贷款合同号批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("result", "");
				modelMap.put("totalpage", app00604ZHResult.getTotalpage());
				modelMap.put("totalnum", app00604ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}

			if ("0".equals(app00604ZHResult.getTotalnum())) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查到该用户的贷款信息");	
				modelMap.put("totalpage", app00604ZHResult.getTotalpage());
				modelMap.put("totalnum", app00604ZHResult.getTotalnum());
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
				String line1 = breader.readLine();
				while (line1 != null) {// 判断读取到的字符串是否不为空	
					try {
						AppApi006_04ZHResult aap006_04ZHResult = new AppApi006_04ZHResult();
						log.debug("读取文件  ："+line1);
						System.out.println(line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						aap006_04ZHResult.setGrzh(trs[1]);
						aap006_04ZHResult.setJkhtbh(trs[2]);
						aap006_04ZHResult.setClearstate(trs[3]);
						aap006_04ZHResult.setLoancontrstate(trs[4]);
						aap006_04ZHResult.setAgentbankcode(trs[5]);
						aap006_04ZHResult.setHtdkje(trs[6]);
						aap006_04ZHResult.setdPrin(trs[7]);
						aap006_04ZHResult.setdInt(trs[8]);
						aap006_04ZHResult.setdPun(trs[9]);
						aap006_04ZHResult.setdRepaySum(trs[10]);
						aap006_04ZHResult.setdBal(trs[11]);
						aap006_04ZHResult.setDkffrq(trs[12]);
						
						list.add(aap006_04ZHResult);
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
		List<List<TitleInfoNameFormatBean>> detail = new ArrayList<List<TitleInfoNameFormatBean>>();	
		List<TitleInfoNameFormatBean> appapi00604Result = null;
		for(int i=0;i<list.size();i++){				
			appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00604"+form.getCenterId()+".detail", list.get(i));	
			detail.add(appapi00604Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app00604ZHResult.getTotalpage());
		modelMap.put("totalnum", app00604ZHResult.getTotalnum());
		modelMap.put("filename", app00604ZHResult.getFilename());
		return modelMap;
	}

}
