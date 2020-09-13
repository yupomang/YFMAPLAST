package com.yondervision.yfmap.handle.handleImpl.handle00041100;

import java.io.File;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00602Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author czy
 * 贷款信息维护查询
 */
public class Handle00602_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");	
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00602 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		DaLianAppApi00602Result app00602Result= new DaLianAppApi00602Result();
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00602");//*************************************
		para.setName("贷款个人信息查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000009");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());		
		
		if(CommonUtil.isEmpty(form.getTxdkbz())){
			form.setTxdkbz("0");
		}
		
		if(Constants.method_FSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHCX.xml");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHCX.xml");
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000009");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00602</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><jkr1xm>李四</jkr1xm><jkr1sj>13100901210</jkr1sj><jkr1qtsj>13955908976</jkr1qtsj><jkr1gh>041182320019</jkr1gh><jkr1qtgh>041182990212</jkr1qtgh><jkr2xm>张晓晓</jkr2xm><jkr2sj>18604410001</jkr2sj><jkr2qtsj>18604112190</jkr2qtsj><jkr2gh>041181209900</jkr2gh><jkr2qtgh>041189250201</jkr2qtgh></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
						
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_XXWHCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00602Result);
			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00602Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00602Result.getMsg());//*************************************
			
			if(!Constants.sucess.equals(app00602Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00602Result);
				modelMap.put("recode", app00602Result.getRecode());
				modelMap.put("msg", app00602Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00602Result.getRecode()+"  ,  描述msg : "+app00602Result.getMsg());
				return modelMap;
			}
		}

		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00602Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("jkr1xm", "--".equals(app00602Result.getJkr1xm())?"":app00602Result.getJkr1xm());
		modelMap.put("jkr1sj", "--".equals(app00602Result.getJkr1sj())?"":app00602Result.getJkr1sj());
		modelMap.put("jkr1gh", "--".equals(app00602Result.getJkr1gh())?"":app00602Result.getJkr1gh());
		modelMap.put("jkr1qtsj", "--".equals(app00602Result.getJkr1qtsj())?"":app00602Result.getJkr1qtsj());
		modelMap.put("jkr1qtgh", "--".equals(app00602Result.getJkr1qtgh())?"":app00602Result.getJkr1qtgh());
		modelMap.put("jkr2xm", "--".equals(app00602Result.getJkr2xm())?"":app00602Result.getJkr2xm());
		modelMap.put("jkr2sj", "--".equals(app00602Result.getJkr2sj())?"":app00602Result.getJkr2sj());
		modelMap.put("jkr2qtsj", "--".equals(app00602Result.getJkr2qtsj())?"":app00602Result.getJkr2qtsj());
		modelMap.put("jkr2gh", "--".equals(app00602Result.getJkr2gh())?"":app00602Result.getJkr2gh());
		modelMap.put("jkr2qtgh", "--".equals(app00602Result.getJkr2qtgh())?"":app00602Result.getJkr2qtgh());
//		modelMap.put("jkr1xm", "小明");
//		modelMap.put("jkr1sj", "13812345678");
//		modelMap.put("jkr1gh", "8925909");
//		modelMap.put("jkr1qtsj", "13812345111");
//		modelMap.put("jkr1qtgh", "8925000");
//		modelMap.put("jkr2xm", "小刚");
//		modelMap.put("jkr2sj", "18612345678");
//		modelMap.put("jkr2qtsj", "18612341212");
//		modelMap.put("jkr2gh", "8125020");
//		modelMap.put("jkr2qtgh", "8125029");
		log.info(Constants.LOG_HEAD+"appApi00602 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi00601Form form1 = new AppApi00601Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		Handle00602_00041100 hand = new Handle00602_00041100();
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
