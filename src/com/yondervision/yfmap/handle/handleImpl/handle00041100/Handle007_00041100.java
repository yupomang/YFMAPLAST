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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi007Result;
import com.yondervision.yfmap.service.DLLogService;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle007_00041100 implements CtrlHandleInter {
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
		
		DaLianAppApi007Result app007Result = new DaLianAppApi007Result();
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00701");//*************************************
		para.setName("历史还款查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000003");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());
		
		if(CommonUtil.isEmpty(form.getTxdkbz())){
			form.setTxdkbz("0");
		}
		
		if(Constants.method_FSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			form.setPagenum(String.valueOf(Integer.valueOf(form.getPagenum()+1)));
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000003");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00701</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>1</zjls><zys>10</zys><yhbxhj>3000.00</yhbxhj><shbxhj>2000.00</shbxhj><qhbxhj>1000.00</qhbxhj><detail><yhr>2015-01-01</yhr><yhhj>1000.00</yhhj><shr>2015-01-01</shr><shhj>1000.00</shhj><bqzt>正常归还</bqzt></detail><detail><yhr>2015-02-01</yhr><yhhj>1000.00</yhhj><shr>2015-02-01</shr><shhj>1000.00</shhj><bqzt>正常归还</bqzt></detail><detail><yhr>2015-03-01</yhr><yhhj>1000.00</yhhj><shr>2015-03-01</shr><shhj>0.00</shhj><bqzt>未还</bqzt></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKMXCX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app007Result);
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app007Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app007Result.getMsg());//*************************************			
			
			if(!Constants.sucess.equals(app007Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app007Result);
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
						aa00701Result.setFxAmount(valus[0]);
						aa00701Result.setLxAmount(String.format("%,.2f",Double.valueOf(valus[1])));
						aa00701Result.setOperationDate(valus[2]);
						aa00701Result.setOperationType(String.format("%,.2f",Double.valueOf(valus[3])));
						aa00701Result.setWithdrawalAmount(valus[4]);
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

		app007Result.setFreeuse1(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse1())));
		app007Result.setFreeuse2(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse2())));
		app007Result.setFreeuse3(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse3())));
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".detail", (AppApi00701Result)list.get(i));	
			detail.add(result1);
		}
		
		List<TitleInfoBean> appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", app007Result);
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app007Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00701Result);
		modelMap.put("detail", detail);	
		modelMap.put("startdate", app007Result.getStartdate());	
		modelMap.put("enddate", app007Result.getEnddate());	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}
	
}
