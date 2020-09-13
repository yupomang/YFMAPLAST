package com.yondervision.yfmap.handle.handleImpl.handle00053100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00701Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle007_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00701 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00701Result> list = new ArrayList<AppApi00701Result>();
		form.setPagerows(String.valueOf((Integer.valueOf(form.getPagerows())+1)));
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		
		JiNanAppApi00701Result app007Result = new JiNanAppApi00701Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			form.setPagenum(String.valueOf(Integer.valueOf(form.getPagenum()+1)));
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKMXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_DKMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000003");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>76</msgtype><tr_code>X00006</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><detail><ywrq>2015-01-01</ywrq><ywlx>缴存</ywlx><bjje>2000.00</bjje><lxje>0.00</lxje><fxje>0.00</fxje></detail><detail><ywrq>2015-02-01</ywrq><ywlx>缴存</ywlx><bjje>2000.00</bjje><lxje>0.00</lxje><fxje>0.00</fxje></detail><detail><ywrq>2015-03-01</ywrq><ywlx>缴存</ywlx><bjje>2000.00</bjje><lxje>0.00</lxje><fxje>0.00</fxje></detail></body></root>";
			 
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKMXCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_DKMXCX.xml", rexml, req);
//			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app007Result);
			
			if(!Constants.sucess.equals(app007Result.getRecode())){
				modelMap.put("recode", app007Result.getRecode());
				modelMap.put("msg", app007Result.getMsg());
				log.error("中心返回报文 状态recode :"+app007Result.getRecode()+"  ,  描述msg : "+app007Result.getMsg());
				return modelMap;
			}
			
			
			
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询公积金明细，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00701Result aa00701Result = new AppApi00701Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);									
						aa00701Result.setOperationDate(valus[0]);
						aa00701Result.setOperationType(valus[1]);
						aa00701Result.setWithdrawalAmount(String.format("%,.2f",Double.valueOf(valus[2])));
						aa00701Result.setLxAmount(String.format("%,.2f",Double.valueOf(valus[3])));
						aa00701Result.setFxAmount(String.format("%,.2f",Double.valueOf(valus[4])));
						list.add(aa00701Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "借款合同信息处理异常");
				return modelMap;
			}
		}		

//		app007Result.setFreeuse1(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse1())));
//		app007Result.setFreeuse2(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse2())));
//		app007Result.setFreeuse3(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse3())));
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", (AppApi00701Result)list.get(i));	
			detail.add(result1);
		}
		
//		List<TitleInfoBean> appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", app007Result);
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
//		modelMap.put("result", appapi00701Result);
		modelMap.put("result", detail);	
//		modelMap.put("startdate", app007Result.getStartdate());	
//		modelMap.put("enddate", app007Result.getEnddate());	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}
	
	
	public static void main(String[] args){
		AppApi00501Form form1 = new AppApi00501Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setPagenum("0");
		form1.setPagerows("10");
		form1.setStartdate("2015-01-01");
		form1.setEnddate("2015-10-01");
		Handle007_00053100 hand = new Handle007_00053100();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
