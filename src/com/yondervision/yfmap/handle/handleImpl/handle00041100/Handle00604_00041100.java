package com.yondervision.yfmap.handle.handleImpl.handle00041100;

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
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00601Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author CZY
 * 借款合同号查询
 */
public class Handle00604_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.info(Constants.LOG_HEAD+"Handle00604_00041100 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form);
		DaLianAppApi00601Result app00601Result= new DaLianAppApi00601Result();
		List<AppApi00601Result> list = new ArrayList<AppApi00601Result>();
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00604");//*************************************
		para.setName("借款合同号查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000002");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());		
		
		if(Constants.method_FSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_JKHTCX.xml");//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			if(!file.exists()){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "待处理报文格式文件不存在");
				return modelMap;
			}		
			para.setStartXml(CommonUtil.getSystemDate());//*************************************			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_JKHTCX.xml", map, req);
			System.out.println("大连贷款信息维护交易00604发往FSP报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000002");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00602</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><detail><jkhth>20110921201</jkhth></detail><detail><jkhth>20110921202</jkhth></detail><detail><jkhth>20110921203</jkhth></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_JKHTCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);			
			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00601Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00601Result.getMsg());//*************************************			
			
			if(!Constants.sucess.equals(app00601Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00601Result);
				modelMap.clear();
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
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
					AppApi00601Result aa00601Result = new AppApi00601Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						System.out.println("Handle00604读取文件#####  ："+line);									
						aa00601Result.setLoanaccnum(valus[0]);
						aa00601Result.setTxdkbz(valus[1]);
						list.add(aa00601Result);
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
//		List<TitleInfoBean> result = new ArrayList();
//		List<TitleInfoBean> appapi00604Result = null;
//		for(int i=0;i<list.size();i++){
//			appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", list.get(i));			
//			result.add(appapi00604Result.get(0));
//		}
		
		
		
		
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00601Result);
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();
		boolean oldret = false;
		List<TitleInfoBean> result2 = new ArrayList();
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			
			if("1".equals(form.getDeviceType())){//1-IOS,2-ANDROID,3-IOS_PAD
				if(form.getCurrenVersion().compareTo("1.0.2")==1){
					result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", (AppApi00601Result)list.get(i));
				}else{
					List<TitleInfoBean> appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".resultold", (AppApi00601Result)list.get(i));
					oldret = true;
					result2.add(appapi00604Result.get(0));
				}
			}else if("2".equals(form.getDeviceType())){
				if(form.getCurrenVersion().compareTo("1.0.4")==1){
					result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", (AppApi00601Result)list.get(i));
				}else{
					List<TitleInfoBean> appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".resultold", (AppApi00601Result)list.get(i));
					oldret = true;
					result2.add(appapi00604Result.get(0));
				}
			}else{
				if(form.getCurrenVersion().compareTo("1.0.1")==1){
					result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", (AppApi00601Result)list.get(i));
				}else{
					List<TitleInfoBean> appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".resultold", (AppApi00601Result)list.get(i));
					oldret = true;
					result2.add(appapi00604Result.get(0));
				}
			}
			//result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", (AppApi00601Result)list.get(i));	
			detail.add(result1);			
		}
		
		
		System.out.println("Handle00604 data : "+JsonUtil.getGson().toJson(detail));
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		if(oldret){
			modelMap.put("result", result2);
		}else{
			modelMap.put("result", detail);
		}
		
		log.info(Constants.LOG_HEAD+"Handle00604_00041100 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00601Form form1 = new AppApi00601Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		Handle00604_00041100 hand = new Handle00604_00041100();
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
