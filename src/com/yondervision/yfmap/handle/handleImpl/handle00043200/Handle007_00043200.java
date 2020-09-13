package com.yondervision.yfmap.handle.handleImpl.handle00043200;

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

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00701Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle007_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");

	String PROPERTIES_FILE_NAME = "properties.properties";

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00501Form form = (AppApi00501Form) form1;
		AES aes= new AES();
		System.out.println("form.getCertinum====="+form.getCertinum());
		try{
		form.setCertinum(aes.decrypt(form.getCertinum()).trim());
		}catch(Exception e){
			e.printStackTrace();
		}
		// ----------------------------------------------------肇庆应用开始201407
		int pageno = Integer.parseInt(form.getPagenum())+1;
		form.setPagenum(pageno+"");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		String freeuse1 = form.getFreeuse1();
		if(freeuse1!=null&&("1").equals(freeuse1)){
			modelMap = searchHKQK( form,  modelMap);
		}else if(freeuse1!=null&&("2").equals(freeuse1)){
			modelMap = searchHKJH( form,  modelMap);
		}else{
			modelMap = searchYQ( form,  modelMap);
		}
		log.info(Constants.LOG_HEAD + "appApi00701 end.");
		return modelMap;
	}

	private ModelMap searchHKQK(AppApi00501Form form, ModelMap modelMap)throws IOException {
		List<WeiHaiAppApi00701Result> list = new ArrayList<WeiHaiAppApi00701Result>();
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007MsgType" + form.getCenterId()).trim();
		form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Key" + form.getCenterId()).trim());
		String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
		form.setSendSeqno(req);
		form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Type" + form.getCenterId()).trim());
		HashMap map = new HashMap();// 封装用MAP
		map = BeanUtil.transBean2Map(form);
		String xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REQ_DKMXCX01.xml", map, req);
		log.debug("发往中心报文：" + xml);

		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Send" + form.getCenterId()).trim();
		WeiHaiAppApi00601Result app006Result = new WeiHaiAppApi00601Result();
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_IP" + form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_PORT" + form.getCenterId()).trim();
		String reqxml = CommunicateUtil.sendMessage(send, ip,Integer.parseInt(port), xml, "C040003");
		HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REP_DKMXCX01.xml", reqxml, req);
		log.debug("解析报文MAP：" + resultMap);
		BeanUtil.transMap2Bean(resultMap, app006Result);
		log.debug("MAP封装成BEAN：" + app006Result);

		if (!"00".equals(app006Result.getRecode())) {
			modelMap.clear();
			modelMap.put("recode", app006Result.getRecode());
			modelMap.put("msg", app006Result.getMsg());
			log.error("中心返回报文 状态recode :" + app006Result.getRecode()+ "  ,  描述msg : " + app006Result.getMsg());
			return modelMap;
		}

		String filename = (String) resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
		System.out.println("查询贷款明细，批量文件：" + filename);

		if (filename != null) {
			File file = new File(filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
		
			while (line != null) {// 判断读取到的字符串是否不为空
				WeiHaiAppApi00701Result app007Result = new WeiHaiAppApi00701Result();
				try {
					String[] param = line.split("\\@\\|\\$");
					
					log.debug("读取文件  ：" + line);
					 app007Result.setEndDate(param[0]);
					 app007Result.setPlanPrin(param[1]);
					 app007Result.setPlanInt(param[2]);
					 app007Result.setFactRetPun(param[3]);
//					 app007Result.setInfactDate(param[4]);
					 String transtype = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_transtype_"+param[4]);
						if(transtype!=null&&!transtype.equals("")){
							app007Result.setInfactDate(transtype.trim());
						}else{
							app007Result.setInfactDate(param[4]);
						}
					 app007Result.setCurSeqStateP(param[5]);
					 app007Result.setCurSeqStateD(param[6]);
					list.add(app007Result);
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			breader.close();
			isr.close();
			ffis.close();
		} else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "批量明细信息处理异常");
			return modelMap;
		}
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();
		for (int i = 0; i < list.size(); i++) {
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+ form.getCenterId() + ".result",(WeiHaiAppApi00701Result) list.get(i));
			result.add(result1);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	private ModelMap searchHKJH(AppApi00501Form form, ModelMap modelMap)throws IOException {
		List<WeiHaiAppApi00701Result> list = new ArrayList<WeiHaiAppApi00701Result>();
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007MsgType" + form.getCenterId()).trim();
		form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Key" + form.getCenterId()).trim());
		String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
		form.setSendSeqno(req);
		form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Type" + form.getCenterId()).trim());
		HashMap map = new HashMap();// 封装用MAP
		map = BeanUtil.transBean2Map(form);
		String xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REQ_DKMXCX02.xml", map, req);
		log.debug("发往中心报文：" + xml);

		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Send" + form.getCenterId()).trim();
		WeiHaiAppApi00601Result app006Result = new WeiHaiAppApi00601Result();
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_IP" + form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_PORT" + form.getCenterId()).trim();
		String reqxml = CommunicateUtil.sendMessage(send, ip,Integer.parseInt(port), xml, "129811");
		HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REP_DKMXCX02.xml", reqxml, req);
		log.debug("解析报文MAP：" + resultMap);
		BeanUtil.transMap2Bean(resultMap, app006Result);
		log.debug("MAP封装成BEAN：" + app006Result);

		if (!"00".equals(app006Result.getRecode())) {
			modelMap.clear();
			modelMap.put("recode", app006Result.getRecode());
			modelMap.put("msg", app006Result.getMsg());
			log.error("中心返回报文 状态recode :" + app006Result.getRecode()+ "  ,  描述msg : " + app006Result.getMsg());
			return modelMap;
		}

		String filename = (String) resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
		System.out.println("查询贷款明细，批量文件：" + filename);

		if (filename != null) {
			File file = new File(filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			int sum = 0;
			if (line != null) {
				sum = 1;
				String[] par = line.split("\\@\\|\\$");
				if ("1899-12-31".equals(par[0])) {
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据!");
					log.error("贷款明细查询上送时间段查无数据！");
					return modelMap;
				}
			}
			int pnum = 0;
			while (line != null) {// 判断读取到的字符串是否不为空
				WeiHaiAppApi00701Result app007Result = new WeiHaiAppApi00701Result();
				try {
					String[] param = line.split("\\@\\|\\$");

					log.debug("读取文件  ：" + line);
					app007Result.setSeqNum(param[0]);
					 app007Result.setEndDate(param[1]);
					 app007Result.setInfactDate(param[2]);
					 app007Result.setPlanPrin(param[3]);
					 app007Result.setPlanInt(param[4]);
					 app007Result.setPlanSum(param[5]);
					 app007Result.setFactRetPun(param[6]);
					list.add(app007Result);
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			breader.close();
			isr.close();
			ffis.close();
		} else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "批量明细信息处理异常");
			return modelMap;
		}
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();
		for (int i = 0; i < list.size(); i++) {
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00702"+ form.getCenterId() + ".result",(WeiHaiAppApi00701Result) list.get(i));
			result.add(result1);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	private ModelMap searchYQ(AppApi00501Form form, ModelMap modelMap)throws IOException {
		List<WeiHaiAppApi00701Result> list = new ArrayList<WeiHaiAppApi00701Result>();
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007MsgType" + form.getCenterId()).trim();
		form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Key" + form.getCenterId()).trim());
		String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
		form.setSendSeqno(req);
		form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Type" + form.getCenterId()).trim());
		HashMap map = new HashMap();// 封装用MAP
		map = BeanUtil.transBean2Map(form);
		String xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REQ_DKMXCX03.xml", map, req);
		log.debug("发往中心报文：" + xml);

		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"appapi007Send" + form.getCenterId()).trim();
		WeiHaiAppApi00601Result app006Result = new WeiHaiAppApi00601Result();
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_IP" + form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"BSP_SOCKET_PORT" + form.getCenterId()).trim();
		String reqxml = CommunicateUtil.sendMessage(send, ip,Integer.parseInt(port), xml, "129811");
		HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath + form.getCenterId())+ "/REP_DKMXCX03.xml", reqxml, req);
		log.debug("解析报文MAP：" + resultMap);
		BeanUtil.transMap2Bean(resultMap, app006Result);
		log.debug("MAP封装成BEAN：" + app006Result);

		if (!"00".equals(app006Result.getRecode())) {
			modelMap.clear();
			modelMap.put("recode", app006Result.getRecode());
			modelMap.put("msg", app006Result.getMsg());
			log.error("中心返回报文 状态recode :" + app006Result.getRecode()+ "  ,  描述msg : " + app006Result.getMsg());
			return modelMap;
		}

		String filename = (String) resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
		System.out.println("查询贷款明细，批量文件：" + filename);

		if (filename != null) {
			File file = new File(filename);
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			int sum = 0;
			if (line != null) {
				sum = 1;
				String[] par = line.split("\\@\\|\\$");
				if ("1899-12-31".equals(par[0])) {
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据!");
					log.error("贷款明细查询上送时间段查无数据！");
					return modelMap;
				}
			}
			int pnum = 0;
			while (line != null) {// 判断读取到的字符串是否不为空
				WeiHaiAppApi00701Result app007Result = new WeiHaiAppApi00701Result();
				try {
					String[] param = line.split("\\@\\|\\$");

					log.debug("读取文件  ：" + line);
					app007Result.setSeqNum(param[0]);
					 app007Result.setEndDate(param[1]);
					 app007Result.setPlanPrin(param[2]);
					 app007Result.setPlanInt(param[3]);
					 app007Result.setPlanSum(param[4]);
					 app007Result.setFactRetPun(param[5]);
					list.add(app007Result);
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			breader.close();
			isr.close();
			ffis.close();
		} else {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "批量明细信息处理异常");
			return modelMap;
		}
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();
		for (int i = 0; i < list.size(); i++) {
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00703"+ form.getCenterId() + ".result",(WeiHaiAppApi00701Result) list.get(i));
			result.add(result1);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
}
