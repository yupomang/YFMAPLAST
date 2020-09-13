package com.yondervision.yfmap.handle.handleImpl.handle00047000;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi00802Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00804_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		log.info(Constants.LOG_HEAD+"appApi00804 start.");
		AppApi00802Form form = (AppApi00802Form)form1;
		List<HulunbuirAppApi00802Result> list = new ArrayList<HulunbuirAppApi00802Result>();
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
		String fname = formatter2.format(date)+"_"+form.getSurplusAccount()+"_"+"api00802LPCX.txt";
		AES aes = new AES();
//		form.setSelectValue(aes.decrypt(form.getSelectValue()));
		HulunbuirAppApi00802Result app002Result = new HulunbuirAppApi00802Result();
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_LPCXQY.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
				
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129957");
			
//			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>YEMXCX.txt</><accnum></><accname></><sumcounts></><counts></><pageflag>1</>";
			
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_LPCXQY.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess_ts.equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			
		}	
		String actmp1024 = app002Result.getHousesit();
		String[] allarea = actmp1024.split("\\,");
		List resultlist = new ArrayList();
		for(int i=0;i<allarea.length;i++){
			HashMap<String,String> map = new HashMap<String,String>();
			String allinfo[] = allarea[i].split(":");
			map.put("instcode", allinfo[0].replace("\"",""));
			map.put("instname",allinfo[1].split("-")[1].trim().replace("\"",""));
			resultlist.add(map);
		}
//		List<List<TitleInfoBean>> result = new ArrayList();
//		List<TitleInfoBean> appapi00101Result = null;
//		for(int i=0;i<list.size();i++){
//			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appApi00804"+form.getCenterId()+".result", list.get(i));			
//			result.add(appapi00101Result);
//		}
//		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", resultlist);
		log.info(Constants.LOG_HEAD+"appApi00804 end.");
		return modelMap;
	
	}

}
