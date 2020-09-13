package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00501Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi002Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi005Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle005_00085200 
* @Description: 遵义提取明细查询
* @author Caozhongyan
* @date Apr 8, 2016 11:17:53 AM   
* 
*/ 
public class Handle005_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
    private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00501 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api005"+form.getStartdate().substring(0, 4)+"GJJTQMX"+form.getSurplusAccount()+".txt";
		
		List<ZunYiAppApi005Result> list = new ArrayList<ZunYiAppApi005Result>();
		ZunYiAppApi00201Result app002Result = new ZunYiAppApi00201Result();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_TQMXCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156002");
			
//			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>TQMXCX.txt</><accnum></><accname></><sumcounts></><counts></><pageflag>1</>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_TQMXCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!"0".equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			if(!app002Result.getFileName().equals("")){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+app002Result.getFileName());
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+app002Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				
				
				String[] paraNames = line.split("~");
				int drawdate=0,frzreason=0,drawamt=0,accname1=0;
				for(int i=0;i<paraNames.length;i++){
					if("drawdate".equals(paraNames[i])){
						drawdate = i;
					}else if("frzreason".equals(paraNames[i])){
						frzreason = i;
					}else if("drawamt".equals(paraNames[i])){
						drawamt = i;						
					}else if("accname1".equals(paraNames[i])){
						accname1 = i;						
					}
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
		//				modelMap.put("recode", "999999");
		//				modelMap.put("msg", "无数据");
		//				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
		//				return modelMap;
				}
				
				//目前只有交易日期及摘要，缺少“业务类型、单位缴存、个人缴存、缴存合计”
				while (line != null) {
					ZunYiAppApi005Result app00501Result = new ZunYiAppApi005Result();
					try {
						String[] param = line.split("~");									
						log.debug("读取文件  ："+line);						
						app00501Result.setDrawdate(param[drawdate].trim());
						app00501Result.setFrzreason("其他销户提取".equals(param[frzreason].trim())?"其他":param[frzreason].trim());
						app00501Result.setDrawamt(String.format("%,.2f",Double.valueOf(param[drawamt])));	
						app00501Result.setAccname1(param[accname1].trim());	
						list.add(app00501Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				file.delete();
			}			
		}
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00501"+form.getCenterId()+".result", (ZunYiAppApi005Result)list.get(i));	
			result.add(result1);
		}
				
		
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi00501 end.");
		return modelMap;
	}

}
