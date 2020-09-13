package com.yondervision.yfmap.handle.handleImpl.handle00031400;

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
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi00501Result;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi005Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle005_00031400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		log.debug("form:"+form);
		form.setPagenum(String.valueOf((Integer.valueOf(form.getPagenum())+1)));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		List<ChengDeAppApi00501Result> list = new ArrayList<ChengDeAppApi00501Result>();
		ChengDeAppApi005Result app005Result = new ChengDeAppApi005Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_TQMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00009");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head>" +
//					"<reqflag>1</reqflag>" +
//					"<msgtype>79</msgtype>" +
//					"<tr_code>X00009</tr_code>" +
//					"<corp_no>00031400</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2015-09-29</tr_acdt>" +
//					"<tr_time>10:25:15</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>交易成功完成</ans_info>" +
//					"<particular_code>000000</particular_code>" +
//					"<particular_info>交易成功完成</particular_info>" +
//					"<reserved></reserved></head>" +
//					"<body>" +
//					"<dqys>1</dqys>" +
//					"<dqysjls>10</dqysjls>" +
//					"<zjls>1</zjls>" +
//					"<zys>10</zys>" +
//					"<detail>" +
//					"<tqrq>2015-12-01</tqrq>" +
//					"<tqyy>正常提取</tqyy>" +
//					"<tqje>2500.000</tqje>" +
//					"<jylx>正常</jylx>" +
//					"</detail>" +
//					"<detail>" +
//					"<tqrq>2016-03-01</tqrq>" +
//					"<tqyy>正常提取</tqyy>" +
//					"<tqje>2400.000</tqje>" +
//					"<jylx>正常</jylx>" +
//					"</detail></body></root>";
			log.debug("发往中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_TQMXCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app005Result);
			log.debug("MAP封装成BEAN："+app005Result);
			if(!Constants.sucess_ts.equals(app005Result.getRecode())){
				modelMap.put("recode", app005Result.getRecode());
				modelMap.put("msg", app005Result.getMsg());
				log.error("中心返回报文 状态recode :"+app005Result.getRecode()+"  ,  描述msg : "+app005Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询提取明细，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					ChengDeAppApi00501Result aa00501Result = new ChengDeAppApi00501Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);			
						aa00501Result.setOperationDate(valus[0]);						
						aa00501Result.setOperationMemo(valus[1]);
						aa00501Result.setWithdrawalAmount(String.format("%.2f",Double.valueOf(valus[2])));
						aa00501Result.setJylx(valus[3]);
						list.add(aa00501Result);
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
				modelMap.put("msg", "提取明细信息处理异常");
				return modelMap;
			}
			
		}
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00501"+form.getCenterId()+".result", (ChengDeAppApi00501Result)list.get(i));	
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
		modelMap.put("total", app005Result.getSumnum());
		modelMap.put("totalPage", app005Result.getSumys());
		modelMap.put("currentPage", app005Result.getThisys());
		modelMap.put("currentPageTotal", app005Result.getThisnum());
		return modelMap;
	}

}
