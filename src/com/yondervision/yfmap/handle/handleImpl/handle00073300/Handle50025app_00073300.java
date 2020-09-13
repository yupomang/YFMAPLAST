package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
import com.yondervision.yfmap.result.zhuzhou.AppApi50025ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50025app_00073300 implements CtrlHandleInter1{
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		System.out.println("YFMAP发往中心——高级认证");
		log.debug("00073300请求50025参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);

		if(!CommonUtil.isEmpty(form.getUnitaccnum())){
			log.debug("form.getUnitaccnum():"+form.getUnitaccnum());
			form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
		}
		if(!CommonUtil.isEmpty(form.getAccnum())){
			log.debug("form.getAccnum():"+form.getAccnum());
			form.setAccnum(aes.decrypt(form.getAccnum()));
		}
		if(!CommonUtil.isEmpty(form.getTel())){
			log.debug("form.getTel():"+form.getTel());
			form.setTel(aes.decrypt(form.getTel()));
		}
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getCsz())){
			log.debug("form.getCsz():"+form.getCsz());
			form.setCsz(aes.decrypt(form.getCsz()));
		}
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
		if(!CommonUtil.isEmpty(form.getCertinumType())){
			log.debug("form.getCertinumType():"+form.getCertinumType());
			form.setCertinumType(aes.decrypt(form.getCertinumType()));
		}
		if(!CommonUtil.isEmpty(form.getCardno())){
			log.debug("form.getCardno():"+form.getCardno());
			form.setCardno(aes.decrypt(form.getCardno()));
		}
		if(!CommonUtil.isEmpty(form.getZdyyhm())){
			log.debug("form.getZdyyhm():"+form.getZdyyhm());
			form.setZdyyhm(aes.decrypt(form.getZdyyhm()));
		}
					

		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50025ZHResult app50025ZHResult = new AppApi50025ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GGXXYZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GGXXYZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110097");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110097</><TranDate>1</><TranIP>1</><TranSeq>1</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GGXXYZ.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GGXXYZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50025ZHResult);
			log.debug("MAP封装成BEAN："+app50025ZHResult);
			if(!"0".equals(app50025ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50025ZHResult.getRecode());
				modelMap.put("msg", app50025ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50025ZHResult.getRecode()+"  ,  描述msg : "+app50025ZHResult.getMsg());
				return modelMap;
			}
		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}


}
