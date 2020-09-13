package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil3;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi00122ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi80003ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80003_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		List<AppApi80003ZHResult> list = new ArrayList<AppApi80003ZHResult>();
		log.debug("00057400请求00131参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00122ZHResult app00122ZHResult = new AppApi00122ZHResult();
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
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);	
		    map.put("tellcode", "dasj");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_00131_111408.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111408");
			log.debug("前置YFMAP接收中心报文——大数据平台个人明细查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_00131_111408.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00122ZHResult);
			log.debug("MAP封装成BEAN："+app00122ZHResult);
			if(!"0".equals(app00122ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00122ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app00122ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00122ZHResult.getRecode()+"  ,  描述msg : "+app00122ZHResult.getMsg());
				return modelMap;
			}
		}

		if(!CommonUtil.isEmpty(app00122ZHResult.getRsufilename())){				
			FtpUtil3 f=new FtpUtil3("bsp");
			String filpath=app00122ZHResult.getFilepath();
			f.getFileByServerForNB(filpath.replace("/bsptest", "").replace("/bsprun", ""),app00122ZHResult.getRsufilename());	    
			File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app00122ZHResult.getRsufilename());
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
			BufferedReader breader = new BufferedReader(isr);
			String line1 = breader.readLine(); 
			while (line1 != null) {// 判断读取到的字符串是否不为空
				try {							
					AppApi80003ZHResult app80003ZHResult = new AppApi80003ZHResult();
					log.debug("读取文件  ："+line1);
					String[] trs = line1.split("\\|");
//					app80003ZHResult.setDwmc(trs[0].trim());
					app80003ZHResult.setJyrq(trs[1].trim());
					String summarycode=PropertiesReader.getProperty("yingshe.properties", "summary"+trs[2].trim());
					app80003ZHResult.setZy(summarycode);
					app80003ZHResult.setFse(trs[3].trim());
					app80003ZHResult.setYe(trs[4].trim());
//					String indiacctype = PropertiesReader.getProperty("yingshe.properties","indiacctypeType"+trs[5].trim()+form.getCenterId());
//					app80003ZHResult.setIndiacctype(indiacctype);
//					app80003ZHResult.setBasenum(trs[6].trim());
					list.add(app80003ZHResult);
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

		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", list);	
		return modelMap;
	}

}
