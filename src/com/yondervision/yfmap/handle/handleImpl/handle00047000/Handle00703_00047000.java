package com.yondervision.yfmap.handle.handleImpl.handle00047000;

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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00703Result;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi00703Result;
import com.yondervision.yfmap.result.mengdian.AppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00703_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		log.info(Constants.LOG_HEAD+"appApi00201 start.");
		AppApi00501Form form = (AppApi00501Form)form1;
		List<HulunbuirAppApi00703Result> list = new ArrayList<HulunbuirAppApi00703Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if("10".equals(form.getChannel()))
		{
			form.setChannel("21");
		}else if("20".equals(form.getChannel()))
		{
			form.setChannel("31");
		}
		Date date = new Date();
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		form.setQtype(form.getType());
		String fname = formatter2.format(date)+"_"+form.getSurplusAccount()+"_"+"api40101GJJMX.txt";
		AES aes = new AES();
		form.setPubaccnum(aes.decrypt(form.getPubaccnum()));
		HulunbuirAppApi00703Result app002Result = new HulunbuirAppApi00703Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();
			map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HKJHCX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
				
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129916");
			
//			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>YEMXCX.txt</><accnum></><accname></><sumcounts></><counts></><pageflag>1</>";
			
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_HKJHCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess_ts.equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			if(!app002Result.getFileName().equals("")){
				String filename = app002Result.getFileName().split("/")[app002Result.getFileName().split("/").length-1];
				app002Result.setFileName(filename);
				log.debug("远程文件名filaneme====="+filename);
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+filename);
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();

//				int num = (Integer.valueOf(form.getPagenum())-1)*Integer.valueOf(form.getPagerows());
//				int pnum = 0;
//				int sum = 0;
//				if(line != null){
//					sum=1;
//				}
				while (line != null) {
					HulunbuirAppApi00703Result aa00201Result = new HulunbuirAppApi00703Result();
					try {
//						if(pnum<Integer.valueOf(form.getPagerows())){
//							if(sum>num){
								String[] param = line.split("~");									
								log.debug("读取文件  ："+line);
								aa00201Result.setTermnum(param[0].trim());	
								aa00201Result.setBegdate(param[1].trim());	
								aa00201Result.setEnddate(param[2].trim());	
								aa00201Result.setPlanprin(param[3].trim());	
								aa00201Result.setPlanint(param[4].trim());	
								aa00201Result.setOweprin(param[5].trim());	
								aa00201Result.setOweint(param[6].trim());	
								aa00201Result.setOwepun(param[7].trim());	
								aa00201Result.setiAmt1(param[8].trim());	
								aa00201Result.setInitialbal(param[9].trim());
								aa00201Result.setCurseqstate(param[10].trim());						
								list.add(aa00201Result);
//								pnum++;
//							}
//						}
//						sum+=1;
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
		
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00703"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi00201 end.");
		return modelMap;
	
	}

}
