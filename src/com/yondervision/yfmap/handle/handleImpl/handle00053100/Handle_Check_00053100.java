package com.yondervision.yfmap.handle.handleImpl.handle00053100;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.common.security.DES5;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.dalian.DaLianAppApi40101Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle_Check_00053100 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap YzCheck(AppApiCommonForm form1, ModelMap modelMap, String bj){
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		System.out.println("YFMAP发往中心验证");
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode(bj);//*************************************		
		para.setStartdate(CommonUtil.getSystemDate());//*************************************		
		
		para.setName("账户明细查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000011");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		AES aes = new AES();
		if(bj.equals("appapi40102")){
			form.setBuzhType("appapi40102");
//			form.setSurplusAccount(aes.decrypt(form.getFullName()));
			para.setName("注册验证");//*************************************
		}else{
			if(bj.equals("appapi40103")){
				form.setBuzhType("appapi40103");
				form.setSurplusAccount(aes.decrypt(form.getFullName()));
				para.setName("登录验证");//*************************************
			}else if(bj.equals("appapi40104")){
				form.setBuzhType("appapi40104");
				para.setName("APP密码找回验证");//*************************************
			}else if(bj.equals("appapi40111")){
				form.setBuzhType("appapi40111");
				para.setName("手机APP用户登录并重新绑定接口");//*************************************
				if("".equals(form.getSurplusAccount())){
					form.setSurplusAccount(aes.decrypt(form.getCheckid()));
				}else{
					form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
				}
			}else if(bj.equals("appapi40107"))  
				form.setBuzhType("appapi40107");
				para.setName("APP用户名找回验证");//*************************************			
		}
		System.out.println("#### form.getBuzhType"+form.getBuzhType()+"    bj"+bj+"    form.getSurplusAccount():"+form.getSurplusAccount());
		para.setKey(form.getSurplusAccount());//*************************************

		DES5 des5 = new DES5();
		log.debug("form:"+form);
		try {
			if(bj.equals("appapi40111")){
//				form.setNewpassword(des5.PIN_Encrypt(aes.decrypt(form.getFullName()),"00000000000"));
				System.out.println("公积金查询密码getNewpassword："+aes.decrypt(form.getNewpassword()));
				System.out.println("公积金查询密码getFullName："+aes.decrypt(form.getFullName()));
				form.setNewpassword(des5.PIN_Encrypt(aes.decrypt(form.getNewpassword()),"00000000000"));
			}else{
				form.setNewpassword(des5.PIN_Encrypt(aes.decrypt(form.getNewpassword()),"00000000000"));
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "请检查安全策略");
			return modelMap;
		}
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		DaLianAppApi40101Result app40101Result = new DaLianAppApi40101Result();
		//form.setNewpassword(aes.decrypt(form.getNewpassword()));
		if(Constants.method_FSP.equals(send)){
			try{
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter2 = new SimpleDateFormat("hh:mm:ss");
				Date date = new Date();
				form.setSendDate(formatter1.format(date));
				form.setSendTime(formatter2.format(date));
				String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
				form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
				String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
				form.setSendSeqno(req);
				form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
				HashMap map = BeanUtil.transBean2Map(form);
				para.setStartXml(CommonUtil.getSystemDate());//*************************************
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YZ.xml", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);			
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
				para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000012");
//				Random random = new Random();
//				String rexml="";
////				if(bj.equals("appapi40102")){
////					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
////				}else 
//					if(bj.equals("appapi40111")){
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}else if(bj.equals("appapi40107")){
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}else if(bj.equals("appapi40104")){
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}else{
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}
//				String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>99</recode><msg>联名卡变更</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				if(random.nextBoolean()){
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>99</recode><msg>联名卡卡号</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}else{
//					rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>98</recode><msg>查询密码错误</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
//				}
//				String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi40102</transCode><sendSeqno>uservvvv20150505094436796</sendSeqno><recvDate>2015-05-05</recvDate><recvTime>09:44:36</recvTime><key>1</key><centerSeqno /><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>王太利</grxm><zjhm>220110197701021234</zjhm><grzh>232320323</grzh><grkhh>231102212</grkhh><jyrq>2015-05-06</jyrq><zxlmkkzt>正常</zxlmkkzt></body></mi>";
						//"<mi><head><transCode>appapi401</transCode><recvDate>111</recvDate><recvTime>222</recvTime><sendSeqno>333</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><grxm>1</grxm><zjhm>1</zjhm><grzh>1</grzh><grkhh>1</grkhh><jyrq>1</jyrq><zxlmkkzt>1</zxlmkkzt></body></mi>";
				
				//log.debug("前置YFMAP接收中心报文："+rexml);
				para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
				para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
				
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YZ.xml", rexml, req);
				//log.debug("解析报文MAP："+resultMap);				
				BeanUtil.transMap2Bean(resultMap, app40101Result);
				//log.debug("MAP封装成BEAN："+app40101Result);
			}catch(IOException e){
				e.printStackTrace();
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "系统异常");
				return modelMap;
			}
			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app40101Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app40101Result.getMsg());//*************************************
			
			System.out.println("日志输出########11111");
			if(!"000000".equals(app40101Result.getRecode())){
				System.out.println("日志输出########222222");
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app40101Result);
				modelMap.clear();
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
		}
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app40101Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("zjhm", app40101Result.getZjhm());
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi40102Form form1 = new AppApi40102Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		
		Handle_Check_00053100 hand = new Handle_Check_00053100();
		try {
			hand.YzCheck(form1, modelMap, "appapi40102");
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
