package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.result.zhuzhou.AppApi50006ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/**
* 个人登录
* @ClassName: Handle50006_00066800 
* @Description: TODO
* @author Caozhongyan
* @date 2016年8月25日 上午11:43:59   
* 
*/ 
public class Handle50006_00066800 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00066800请求50006参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
//		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
//			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
//			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
//		}
//		if(!CommonUtil.isEmpty(form.getCheckcode())){
//			log.debug("form.getCheckcode():"+form.getCheckcode());
//			form.setCheckcode(aes.decrypt(form.getCheckcode()));
//		}
		if(!CommonUtil.isEmpty(form.getCertinumType())){
			log.debug("form.getCertinumType():"+form.getCertinumType());
			form.setCertinumType(aes.decrypt(form.getCertinumType()));
		}
//		if(!CommonUtil.isEmpty(form.getZdyyhm())){
//			log.debug("form.getZdyyhm():"+form.getZdyyhm());
//			form.setZdyyhm(aes.decrypt(form.getZdyyhm()));
//		}
		if(!"50".equals(form.getChannel())&&!"20".equals(form.getChannel())){
			if(!CommonUtil.isEmpty(form.getPassword())){
				log.debug("form.getPassword():"+form.getPassword());
				form.setPassword(aes.decrypt(form.getPassword()));
			}
		}else{
			if("50".equals(form.getChannel())){
				if("4".equals(aes.decrypt(form.getLogintype()))){
					form.setCardnoNumber(form.getBodyCardNumber());
					form.setBodyCardNumber("");
				}
				
			}
			form.setPassword("");
		}
		
		
//		if("20".equals(form.getChannel())){
//			form.setPassword("00000000");
//		}else if("50".equals(form.getChannel())){
//			form.setPassword("00000000");
//		}else{		
//			if(!CommonUtil.isEmpty(form.getPassword())){
//				log.debug("form.getPassword():"+form.getPassword());
//	//			form.setPassword(new String(DESForJava.encryption(aes.decrypt(form.getPassword()),"12345678")));
//				form.setPassword(aes.decrypt(form.getPassword()));
//			}
//		}
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		AppApi50006ZHResult app50006ZHResult = new AppApi50006ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRDL.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRDL.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110021");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>110021</><TranDate>1</><TranIP>1</><TranSeq>1</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRDL.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRDL.txt", rexml, req);
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

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		//modelMap.put("result", app50006ZHResult);
		modelMap.put("certinum", app50006ZHResult.getZjhm());
		modelMap.put("certinumType", app50006ZHResult.getZjlx());
		
		modelMap.put("accnum", app50006ZHResult.getAccnum());
		modelMap.put("loanaccnum", app50006ZHResult.getLoanaccnum());
		modelMap.put("loancontrnum", app50006ZHResult.getLoancontrnum());
		
		
		//是否认证用户 0-否1-是
		if("1".equals(app50006ZHResult.getIsgjyh())){
			app50006ZHResult.setIsgjyh("2");
		}else{
			app50006ZHResult.setIsgjyh("1");
		}
		
		//性别 1-男性 2-女性 0-未知性别 9-未说明的性别
		String  sex = null;
		if("1".equals(app50006ZHResult.getXingbie())){
			 sex = "0";
		}else if("2".equals(app50006ZHResult.getXingbie())){
			sex = "1";
		}else{
			 sex = "2";
		}
		app50006ZHResult.setBirthday(app50006ZHResult.getBirthday().substring(0, 4)+ "-" +app50006ZHResult.getBirthday().substring(4, 6)+ "-"+app50006ZHResult.getBirthday().substring(6, 8));
		
		modelMap.put("accname", app50006ZHResult.getXingming());
		modelMap.put("accnum", app50006ZHResult.getAccnum());
		modelMap.put("phone", app50006ZHResult.getSjhm());
		modelMap.put("sex", sex);
		modelMap.put("custid", app50006ZHResult.getCustid());
		modelMap.put("birthday", app50006ZHResult.getBirthday());
		modelMap.put("perstate", app50006ZHResult.getIsgjyh());
		modelMap.put("unitaccnum", app50006ZHResult.getDwzh());
		
		modelMap.put("grckzhhm", app50006ZHResult.getGrckzhhm());
		modelMap.put("grckzhkhyhdm", app50006ZHResult.getGrckzhkhyhdm());
		modelMap.put("grckzhkhyhmc", app50006ZHResult.getGrckzhkhyhmc());
		modelMap.put("grjcjs", app50006ZHResult.getGrjcjs());
		
		modelMap.put("des", app50006ZHResult.getDes());
		modelMap.put("gryjce", app50006ZHResult.getGryjce());
		modelMap.put("grzhye", app50006ZHResult.getGrzhye());
		modelMap.put("grzhzt", app50006ZHResult.getGrzhzt ());
		modelMap.put("indiacctype", app50006ZHResult.getIndiacctype());
		modelMap.put("indiprop", app50006ZHResult.getIndiprop());
		modelMap.put("indipaysum", app50006ZHResult.getIndipaysum());
		modelMap.put("khrq", app50006ZHResult.getKhrq());
		modelMap.put("grjcjs", app50006ZHResult.getGrjcjs());
		
		modelMap.put("sjhm", app50006ZHResult.getSjhm());
		modelMap.put("unitaccname", app50006ZHResult.getUnitaccname());
		modelMap.put("unitprop", app50006ZHResult.getUnitprop());
		modelMap.put("zjlx", app50006ZHResult.getZjlx());
		modelMap.put("zjhm", app50006ZHResult.getZjhm());
		modelMap.put("jkhth", app50006ZHResult.getJkhth());
		modelMap.put("grzh", app50006ZHResult.getAccnum());
		modelMap.put("isgjyh", app50006ZHResult.getIsgjyh());
		
		modelMap.put("xingbie", app50006ZHResult.getXingbie());
		modelMap.put("xingming", app50006ZHResult.getXingming());
		modelMap.put("dwzh", app50006ZHResult.getDwzh());
		modelMap.put("dwyjce", app50006ZHResult.getDwyjce());
		modelMap.put("xmqp", app50006ZHResult.getXmqp());
		modelMap.put("yhqdbs", app50006ZHResult.getYhqdbs());
		
		modelMap.put("flag", app50006ZHResult.getFlag());
		modelMap.put("isloanflag", app50006ZHResult.getIsloanflag());
		modelMap.put("useflag", app50006ZHResult.getUseflag());
		return modelMap;
	}

}
