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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00201Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00201Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author czy
 * 余额明细查询
 */
public class Handle002_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");	
		
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {		
		AppApi00201Form form = (AppApi00201Form)form1;
		form.setPagenum("1");
		form.setPagerows("10000");
		List<AppApi00201Result> list = new ArrayList<AppApi00201Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		DaLianAppApi00201Result app002Result = new DaLianAppApi00201Result();		
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00201");//*************************************
		para.setName("账户明细查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000011");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());
		System.out.println("appapi00201 公积金明细查询 【indiacctype="+form.getIndiacctype()+"】");
		if(CommonUtil.isEmpty(form.getZhlx())){
			form.setZhlx("0");
		}
		
		if(Constants.method_FSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_YEMXCX.xml", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			System.out.println("YFMAP发往FSP报文:"+xml);
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000011");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00701</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>1</zjls><zys>10</zys><detail><hkr>2015-01-01</hkr><gjjzy>汇缴</gjjzy><je>2500.00</je><zhye>32500.00</zhye></detail><detail><hkr>2015-02-01</hkr><gjjzy>汇缴</gjjzy><je>2500.00</je><zhye>35000.00</zhye></detail><detail><hkr>2015-03-01</hkr><gjjzy>汇缴</gjjzy><je>2500.00</je><zhye>37500.00</zhye></detail><detail><hkr>2015-04-01</hkr><gjjzy>汇缴</gjjzy><je>2500.00</je><zhye>40000.00</zhye></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YEMXCX.xml", rexml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			BeanUtil.transMap2Bean(resultMap, app002Result);			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app002Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app002Result.getMsg());//*************************************
			if(!Constants.sucess.equals(app002Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app002Result);
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
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
					AppApi00201Result aa00201Result = new AppApi00201Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						aa00201Result.setOperationDate(valus[0]);
						aa00201Result.setOperationType(valus[1]);
						aa00201Result.setAmount(String.format("%,.2f",Double.valueOf(valus[2])));
						aa00201Result.setOperationMemo(String.format("%,.2f",Double.valueOf(valus[3])));
						list.add(aa00201Result);
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
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}	
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app002Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00201Form form1 = new AppApi00201Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		Handle002_00041100 hand = new Handle002_00041100();
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
