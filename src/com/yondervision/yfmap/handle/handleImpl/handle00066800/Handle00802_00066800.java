package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.zhuzhou.AppApi00802ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi008_02ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/** 
* @ClassName: Handle00802_00066800 
* @Description: 综合服务查询核心系统楼盘数据 
* 
*/ 
public class Handle00802_00066800 implements CtrlHandleInter {
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
		log.debug("00066800请求00802参数："+CommonUtil.getStringParams(form));	
		
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
				
		List<AppApi008_02ZHResult> list = new ArrayList<AppApi008_02ZHResult>();				
				
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HZLPCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_HZLPCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "127305");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>127305</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_HZLPCX.txt", rexml, req);
			
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_HZLPCX.txt", rexml, req);
			
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
		if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("result", "");
			modelMap.put("totalpage", app00802ZHResult.getTotalpage());
			modelMap.put("totalnum", app00802ZHResult.getTotalnum());
			modelMap.put("filename", filename);
			return modelMap;
		}

		if ("0".equals(app00802ZHResult.getTotalnum())) {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "未查询到合作楼盘数据");	
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
			String line1 = breader.readLine();
			while (line1 != null) {// 判断读取到的字符串是否不为空
				
				try {
					AppApi008_02ZHResult app008_02ZHResult = new AppApi008_02ZHResult();
					MoneyNumberTran mntran = new MoneyNumberTran();
					log.debug("读取文件  ："+line1);
					line1 = line1 + "~0";
					String[] trs = line1.split("~");						
					app008_02ZHResult.setCocustname(trs[1]);
					app008_02ZHResult.setProjectname(trs[2]);
					app008_02ZHResult.setHousesit(trs[3]);
					app008_02ZHResult.setPolenum(trs[4]);
					list.add(app008_02ZHResult);
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
	List<TitleInfoNameFormatBean> appapi00802Result = null;
	for(int i=0;i<list.size();i++){
		appapi00802Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00802"+form.getCenterId()+".detail", list.get(i));
		detail.add(appapi00802Result);
	}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app00802ZHResult.getTotalpage());
		modelMap.put("totalnum", app00802ZHResult.getTotalnum());
		modelMap.put("filename", app00802ZHResult.getFilename());
		return modelMap;
	}
		
}
