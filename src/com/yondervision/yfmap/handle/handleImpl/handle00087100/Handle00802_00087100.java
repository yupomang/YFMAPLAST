package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
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
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00802ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi008_02ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result01;
import com.yondervision.yfmap.result.zunyi.AppApi00802Result;
import com.yondervision.yfmap.result.zunyi.Mi203;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi002Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00802;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/** 
* @ClassName: Handle00802_00073300 
* @Description: 综合服务查询核心系统楼盘数据 
* 
*/ 
public class Handle00802_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi00802 start.");
			
		AppApi00802ZHResult app00802ZHResult = new AppApi00802ZHResult();
		
		AppApi00802Form form = (AppApi00802Form)form1;
		log.debug("YFMAP 可贷楼盘查询 form 参数:"+form);		
		
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
				
		List<AppApi008_02ZHResult> list = new ArrayList<AppApi008_02ZHResult>();				
				
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HZLPCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_HZLPCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314029");
			
//			String rexml ="<AuthCode1>12</><AuthCode2>12</><AuthCode3>11</><AuthFlag>23</><BrcCode>3</><BusiSeq>4</><ChannelSeq>54</><ChkCode>3</><FinancialDate>3</><HostBank>87</><MTimeStamp>9</><NoteMsg>9</><RspCode>0</><RspMsg>1</><STimeStamp>8</><SubBank>3</><TellCode>2</><TranChannel>6</><TranCode>314029</><TranDate>5</><TranIP>2</><TranSeq>9</><filename>F:/aaa.txt</><totalpage>7</><totalnum>8</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_HZLPCX.txt", rexml, req);
			
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_HZLPCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00802ZHResult);
			log.debug("MAP封装成BEAN："+app00802ZHResult);
			if(!"0".equals(app00802ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00802ZHResult.getRecode());
				modelMap.put("msg", app00802ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00802ZHResult.getRecode()+"  ,  描述msg : "+app00802ZHResult.getMsg());
				return modelMap;
			}
		
		String filename = (String)resultMap.get("filename");//aaa.txt
		System.out.println("查询公积金明细，批量文件："+filename);
		System.out.println("查询公积金明细，批量文件："+filename);
		if("40".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("totalpage", app00802ZHResult.getTotalpage());
			modelMap.put("totalnum", app00802ZHResult.getTotalnum());
			modelMap.put("filename", filename);
			return modelMap;
		}
		if(!CommonUtil.isEmpty(filename)){
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer(filename);				    
		    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//			File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
//			System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
//			int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
//			int pnum = 0;
			while (line != null) {// 判断读取到的字符串是否不为空
				//AppApi00201ZHResult aap00201ZHResult = new AppApi00201ZHResult();
				try {
					AppApi008_02ZHResult app008_02ZHResult = new AppApi008_02ZHResult();
					MoneyNumberTran mntran = new MoneyNumberTran();
					log.debug("读取文件  ："+line);
					line = line + "~0";
					String[] trs = line.split("~");						
					app008_02ZHResult.setEmpunitname(trs[0]);
					app008_02ZHResult.setProjectname(trs[1]);
					app008_02ZHResult.setHousesit(trs[2]);
					app008_02ZHResult.setInstcode(Tools.getMessage(trs[3]));
					app008_02ZHResult.setPolenum(trs[4]);
					list.add(app008_02ZHResult);
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
	List<TitleInfoNameFormatBean> appapi00201Result = null;
	for(int i=0;i<list.size();i++){
		appapi00201Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00802"+form.getCenterId()+".result", list.get(i));
		result.add(appapi00201Result);
	}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("result", result);
		modelMap.put("totalpage", app00802ZHResult.getTotalpage());
		modelMap.put("totalnum", app00802ZHResult.getTotalnum());
		return modelMap;
	}
		
}
