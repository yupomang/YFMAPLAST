package com.yondervision.yfmap.handle.handleImpl.handle00073300;

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

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.ibm.db2.jcc.b.n;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApi00609Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00605Result;
import com.yondervision.yfmap.result.kunming.AppApi00606ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi00608ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi00609ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi006_09ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00609_00073300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00609Form form = (AppApi00609Form)form1;
		log.debug("00073300请求00609参数："+CommonUtil.getStringParams(form));
		List<AppApi006_09ZHResult> list = new ArrayList<AppApi006_09ZHResult>();
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi00609ZHResult app00609ZHResult= new AppApi00609ZHResult();
		HashMap resultMap =null;
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
			HashMap map = new HashMap();
			map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_TQBFHKSS.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_TQBFHKSS.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168025");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</><TranCode>168025</><TranDate>1</><TranIP>1</><TranSeq>1</><begdate>123</><enddate>23</><filename>F:/aaa.txt</><intday>32</><O_remainterm>213</><O_remainterm1>23</><O_retloanmode>2131</><rate>213</><tolLoanInt_1>213</><tolLoanSum_1>23213</><totalpage>7</><totalnum>8</>";
			
//			rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_TQBFHKSS.txt", rexml, req);
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_TQBFHKSS.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00609ZHResult);
			log.debug("MAP封装成BEAN："+app00609ZHResult);
			if(!"0".equals(app00609ZHResult.getRecode())){
				modelMap.put("recode", app00609ZHResult.getRecode());
				modelMap.put("msg", app00609ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00609ZHResult.getRecode()+"  ,  描述msg : "+app00609ZHResult.getMsg());
				return modelMap;
			}
		}
		
		String filename = (String)resultMap.get("filename");
		System.out.println("提前部分还款查询，批量文件："+filename);
		if("40".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("totalpage", app00609ZHResult.getTotalpage());
			modelMap.put("totalnum", app00609ZHResult.getTotalnum());
			modelMap.put("filename", filename);
			modelMap.put("begdate", app00609ZHResult.getBegdate());
			modelMap.put("enddate", app00609ZHResult.getEnddate());
			modelMap.put("intday", app00609ZHResult.getIntday());
			modelMap.put("O_remainterm", app00609ZHResult.getO_remainterm());
			modelMap.put("O_remainterm1", app00609ZHResult.getO_remainterm1());
			modelMap.put("O_retloanmode", app00609ZHResult.getO_retloanmode());
			modelMap.put("rate", app00609ZHResult.getRate());
			modelMap.put("tolLoanInt_1", app00609ZHResult.getTolLoanInt_1());
			modelMap.put("tolLoanSum_1", app00609ZHResult.getTolLoanSum_1());
			modelMap.put("totalpage", app00609ZHResult.getTotalpage());
			modelMap.put("totalnum", app00609ZHResult.getTotalnum());
			
			return modelMap;
		}
		if(!CommonUtil.isEmpty(filename)){				
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer(filename);				    
			File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//			File file = new File(filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
			BufferedReader breader = new BufferedReader(isr);
			
			String line = breader.readLine();
			System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
			while (line != null) {// 判断读取到的字符串是否不为空
				//AppApi00201ZHResult aap00201ZHResult = new AppApi00201ZHResult();
				try {
					AppApi006_09ZHResult app006_09ZHResult = new AppApi006_09ZHResult();
					MoneyNumberTran mntran = new MoneyNumberTran();
					log.debug("读取文件  ："+line);
					String[] trs = line.split("~");	
					app006_09ZHResult.setSeqnum(trs[0]);
					app006_09ZHResult.setPlandate(trs[1]);
					app006_09ZHResult.setPlanprin(mntran.moneyTran(trs[2]));
					app006_09ZHResult.setPlanint(mntran.moneyTran(trs[3]));
					app006_09ZHResult.setPlansum(mntran.moneyTran(trs[4]));																	
						
					list.add(app006_09ZHResult);
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
	
	List<List<TitleInfoNameFormatBean>> result = new ArrayList<List<TitleInfoNameFormatBean>>();			
	List<TitleInfoNameFormatBean> appapi00609Result = null;
	for(int i=0;i<list.size();i++){		
		appapi00609Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00609"+form.getCenterId()+".result", list.get(i));	
		result.add(appapi00609Result);
	}
	modelMap.clear();
	modelMap.put("recode", "000000");
	modelMap.put("msg", "成功");	
	modelMap.put("totalpage", app00609ZHResult.getTotalpage());
	modelMap.put("totalnum", app00609ZHResult.getTotalnum());
	modelMap.put("result", result);
	modelMap.put("begdate", app00609ZHResult.getBegdate());
	modelMap.put("enddate", app00609ZHResult.getEnddate());
	modelMap.put("intday", app00609ZHResult.getIntday());
	modelMap.put("O_remainterm", app00609ZHResult.getO_remainterm());
	modelMap.put("O_remainterm1", app00609ZHResult.getO_remainterm1());
	modelMap.put("O_retloanmode", app00609ZHResult.getO_retloanmode());
	modelMap.put("rate", app00609ZHResult.getRate());
	modelMap.put("tolLoanInt_1", app00609ZHResult.getTolLoanInt_1());
	modelMap.put("tolLoanSum_1", app00609ZHResult.getTolLoanSum_1());
	modelMap.put("totalpage", app00609ZHResult.getTotalpage());
	modelMap.put("totalnum", app00609ZHResult.getTotalnum());
	return modelMap;
}


}

