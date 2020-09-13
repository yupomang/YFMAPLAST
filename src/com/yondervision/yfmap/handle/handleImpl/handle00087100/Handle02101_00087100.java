package com.yondervision.yfmap.handle.handleImpl.handle00087100;

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

import com.ibm.db2.jcc.a.a;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi02101ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi021_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle02101_00087100 implements CtrlHandleInter {
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
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		AppApi02101ZHResult app02101ZHResult = new AppApi02101ZHResult();
		 
		List<AppApi021_01ZHResult> list = new ArrayList<AppApi021_01ZHResult>();
		log.debug("form:"+form);
		HashMap resultMap = null;
		if(Constants.method_BSP.equals(send)){

			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			
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
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DW_DWMXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_DW_DWMXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314020");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>314020</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/bbb.txt</><totalpage>1</><totalnum>1</>";
			
			log.debug("前置YFMAP接收中心报文——单位公积金明细查询："+rexml);
		    resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DW_DWMXCX.txt", rexml, req);
//		    resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_DW_DWMXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02101ZHResult);
			log.debug("MAP封装成BEAN："+app02101ZHResult);
			if(!"0".equals(app02101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02101ZHResult.getRecode());
				modelMap.put("msg", app02101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02101ZHResult.getRecode()+"  ,  描述msg : "+app02101ZHResult.getMsg());
				return modelMap;
			}
		
	}
		String filename = (String)resultMap.get("filename");
		System.out.println("查询公积金明细，批量文件："+filename);
		if("40".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("filename", filename);
			modelMap.put("totalpage", app02101ZHResult.getTotalpage());
			modelMap.put("totalnum", app02101ZHResult.getTotalnum());			
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
			System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
			while (line != null) {// 判断读取到的字符串是否不为空
				try {
					AppApi021_01ZHResult app021_01ZHResult = new AppApi021_01ZHResult();
					MoneyNumberTran mnTran = new MoneyNumberTran();
					log.debug("读取文件  ："+line);
					line = line + "~0";
					String[] trs = line.split("~");
					app021_01ZHResult.setTransdate(trs[0]);
					app021_01ZHResult.setSummary(Tools.getMessage(trs[1]));
					app021_01ZHResult.setAccname(trs[2]);
					app021_01ZHResult.setAcaccnum(trs[3]);
					app021_01ZHResult.setDamt(mnTran.moneyTran(trs[4]));
					app021_01ZHResult.setCamt(mnTran.moneyTran(trs[5]));
					app021_01ZHResult.setBalance(mnTran.moneyTran(trs[6]));
					app021_01ZHResult.setHostsernum(trs[7]);
					app021_01ZHResult.setPaymode(Tools.getMessage(trs[8]));
					app021_01ZHResult.setTradetype(Tools.getMessage(trs[9]));
					app021_01ZHResult.setPaybegindate(trs[10]);
					app021_01ZHResult.setPayenddate(trs[11]);
					app021_01ZHResult.setAgentinstcode(Tools.getMessage(trs[12]));
					app021_01ZHResult.setSettleop(trs[13]);					
					
					list.add(app021_01ZHResult);
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
	
	List<List<TitleInfoNameFormatBean>> result = new ArrayList<List<TitleInfoNameFormatBean>>();	
	List<TitleInfoNameFormatBean> appapi02101Result = null;
	for(int i=0;i<list.size();i++){		
		appapi02101Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02101"+form.getCenterId()+".result", list.get(i));	
		result.add(appapi02101Result);
	}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		modelMap.put("filename", app02101ZHResult.getFilename());
		modelMap.put("totalpage", app02101ZHResult.getTotalpage());
		modelMap.put("totalnum", app02101ZHResult.getTotalnum());
		return modelMap;
	}
	
}
