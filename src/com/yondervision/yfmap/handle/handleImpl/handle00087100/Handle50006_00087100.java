package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi50006ZHResult;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/**
* 个人登录
* @ClassName: Handle50006_00073300 
* @Description: TODO
* @author Caozhongyan
* @date 2016年8月25日 上午11:43:59   
* 
*/ 
public class Handle50006_00087100 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		
		log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
		log.debug("form.getFullName():"+form.getFullName());
		log.debug("form.getTel():"+form.getTel());
		log.debug("form.getPassword():"+form.getPassword());
		
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if("20".equals(form.getChannel())){
			if(CommonUtil.isEmpty(form.getPassword())){
				form.setPassword("00000000");
			}else{
				form.setPassword(aes.decrypt(form.getPassword()));
			}			
		}else if("50".equals(form.getChannel())){
			form.setPassword("00000000");
		}else{		
			if(!CommonUtil.isEmpty(form.getPassword())){
				log.debug("form.getPassword():"+form.getPassword());
	//			form.setPassword(new String(DESForJava.encryption(aes.decrypt(form.getPassword()),"12345678")));
				form.setPassword(aes.decrypt(form.getPassword()));
			}
		}
		
		if("60".equals(form.getChannel())){
			form.setTel(aes.decrypt(form.getTel()));
		}else if("10".equals(form.getChannel())){
//			form.setTel(aes.decrypt(form.getTel()));
		}else{
			form.setTel("");
		}
		log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
		log.debug("form.getFullName():"+form.getFullName());
		log.debug("###form.getTel()###:"+form.getTel());
		log.debug("form.getPassword():"+form.getPassword());
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		AppApi50006ZHResult app50006ZHResult = new AppApi50006ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZHDLYZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GRZHDLYZ.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314002");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314003</><TranDate>1</><TranIP>1</><TranSeq>1</><accname>张晓红</><certinum>4204840294940243</><phone>12345678910</><sex>1###女</><custid>498534894</><accnum>4095809358</><loanaccnum>40580985098</><loancontrnum>0943438589</><wt_transdate>2020-02-20</><wt_instcode>2</><wt_op>43</><birthday>1990-1-1</><bankcode>1###中国银行</><cardno>14558403805834</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZHDLYZ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GRZHDLYZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50006ZHResult);
			log.debug("MAP封装成BEAN："+app50006ZHResult);
			if(!"0".equals(app50006ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50006ZHResult.getRecode());
				modelMap.put("msg", app50006ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50006ZHResult.getRecode()+"  ,  描述msg : "+app50006ZHResult.getMsg());
				return modelMap;
			}
		}
//        List<TitleInfoNameFormatBean> appapi50006ZHResult = null;
//		
//		
//		appapi50006ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi50006"+form.getCenterId()+".result", app50006ZHResult);
//		Iterator<TitleInfoNameFormatBean> it1 = appapi50006ZHResult.iterator();
//		while (it1.hasNext()) {
//			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
//			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("accname", app50006ZHResult.getAccname());
		modelMap.put("certinum", app50006ZHResult.getCertinum());
		modelMap.put("phone", app50006ZHResult.getPhone());
		modelMap.put("sex", Tools.getCode(app50006ZHResult.getSex()));//0男，1女
		modelMap.put("custid", app50006ZHResult.getCustid());
		modelMap.put("accnum", app50006ZHResult.getAccnum());
		modelMap.put("loanaccnum", app50006ZHResult.getLoanaccnum());
		modelMap.put("loancontrnum", app50006ZHResult.getLoancontrnum());
		modelMap.put("wt_transdate", app50006ZHResult.getWt_transdate());
		modelMap.put("wt_instcode", app50006ZHResult.getWt_instcode());
		modelMap.put("wt_op", app50006ZHResult.getWt_op());
		modelMap.put("birthday", app50006ZHResult.getBirthday());
		modelMap.put("bankcode", Tools.getCode(app50006ZHResult.getBankcode()));
		modelMap.put("bankName", Tools.getMessage(app50006ZHResult.getBankcode()));
		modelMap.put("cardno", app50006ZHResult.getCardno());
		modelMap.put("perstate", Tools.getCode(app50006ZHResult.getPerstate()));
		modelMap.put("unitaccnum", app50006ZHResult.getUnitaccnum());
		return modelMap;
	}

}
