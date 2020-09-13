package com.yondervision.yfmap.handle.handleImpl.handle00077500;

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
import com.yondervision.yfmap.result.zhuzhou.AppApi50010ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
 * 个人身份认证——注册
* @ClassName: Handle50010_00077500 
* @Description: TODO
* @author Caozhongyan
* @date 2016年9月5日 下午5:57:46   
* 
*/ 
public class Handle50010_00077500 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00077500请求50010参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi50010ZHResult app50010ZHResult = new AppApi50010ZHResult();
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));
		}
//		if(!CommonUtil.isEmpty(form.getZdyyhm())){
//			log.debug("form.getZdyyhm():"+form.getZdyyhm());
//			form.setZdyyhm(aes.decrypt(form.getZdyyhm()));
//		}
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
		if(!CommonUtil.isEmpty(form.getCertinumType())){
			log.debug("form.getCertinumType():"+form.getCertinumType());
			form.setCertinumType(aes.decrypt(form.getCertinumType()));
		}
		if(!CommonUtil.isEmpty(form.getTel())){
			log.debug("form.getTel():"+form.getTel());
			form.setTel(aes.decrypt(form.getTel()));
		}
		if(!CommonUtil.isEmpty(form.getCheckcode())){
			log.debug("form.getCheckcode():"+form.getCheckcode());
			form.setCheckcode(aes.decrypt(form.getCheckcode()));
		}
		if(!CommonUtil.isEmpty(form.getPassword())){
			log.debug("form.getPassword():"+form.getPassword());
			form.setPassword(aes.decrypt(form.getPassword()));
		}
		if(!CommonUtil.isEmpty(form.getNewpassword())){
			log.debug("form.getNewpassword():"+form.getNewpassword());
			form.setNewpassword(aes.decrypt(form.getNewpassword()));
		}
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Type"+form.getCenterId()).trim());			
			
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRXXZC.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_GRXXZC.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168011");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>3</><TellCode>23</><TranChannel>3</><TranCode>168011</><TranDate>3</><TranIP>3</><TranSeq>23</>";
		
			log.debug("前置YFMAP接收中心报文——个人公积金身份认证："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_GRXXZC.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_GRXXZC.txt.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app50010ZHResult);
			log.debug("MAP封装成BEAN："+app50010ZHResult);
			if(!"0".equals(app50010ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50010ZHResult.getRecode());
				modelMap.put("msg", app50010ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50010ZHResult.getRecode()+"  ,  描述msg : "+app50010ZHResult.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}
}
