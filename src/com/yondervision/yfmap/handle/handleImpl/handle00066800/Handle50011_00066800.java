package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.result.zhuzhou.AppApi50011ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/** 
 * 个人实名认证
* @ClassName: Handle50011_00066800 
* @Description: TODO
* @author Caozhongyan
* @date 2016年9月5日 下午5:58:06   
* 
*/ 
public class Handle50011_00066800 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		System.out.println("YFMAP发往中心——实名认证");
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
		log.debug("00066800请求50011参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50011ZHResult app50011ZHResult = new AppApi50011ZHResult();
		
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
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRSMRZ1.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRSMRZ1.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110032");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110032</><TranDate>1</><TranIP>1</><TranSeq>1</><cardno>23</><grzh>234</><sjhm>4</><xingming>4</><yhdm>4</><zjhm>4</><flag>2</><reason>4</><sjhyzbs>4</><yhkyzbs>4</>";
			
			log.debug("前置YFMAP接收中心报文——个人实名认证："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRSMRZ1.txt", rexml, req);
//		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRSMRZ1.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50011ZHResult);
			log.debug("MAP封装成BEAN："+app50011ZHResult);
			if(!"0".equals(app50011ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50011ZHResult.getRecode());
				modelMap.put("msg", app50011ZHResult.getMsg());
				modelMap.put("level", "1");
				log.error("中心返回报文 状态recode :"+app50011ZHResult.getRecode()+"  ,  描述msg : "+app50011ZHResult.getMsg());
				return modelMap;
			}else {
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRSMRZ2.txt", map, req);
//				xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRSMRZ2.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);			
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110027");
				
//				rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110027</><TranDate>1</><TranIP>1</><TranSeq>1</><smrzbs>34</><seqno>234654</>";
				
				log.debug("前置YFMAP接收中心报文——个人实名认证："+rexml);
			    resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRSMRZ2.txt", rexml, req);
//			    resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRSMRZ2.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app50011ZHResult);
				log.debug("MAP封装成BEAN："+app50011ZHResult);
				if(!"0".equals(app50011ZHResult.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app50011ZHResult.getRecode());
					modelMap.put("msg", app50011ZHResult.getMsg());
					
					log.error("中心返回报文 状态recode :"+app50011ZHResult.getRecode()+"  ,  描述msg : "+app50011ZHResult.getMsg());
					return modelMap;
				}
			}
		
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("level", "2");
		
		modelMap.put("cardno", app50011ZHResult.getCardno());
		modelMap.put("grzh", app50011ZHResult.getGrzh());
		modelMap.put("sjhm", app50011ZHResult.getSjhm());
		modelMap.put("xingming", app50011ZHResult.getXingming());
		modelMap.put("yhdm", app50011ZHResult.getYhdm());
		modelMap.put("zjhm", app50011ZHResult.getZjhm());
		modelMap.put("flag", app50011ZHResult.getFlag());
		modelMap.put("reason", app50011ZHResult.getReason());
		modelMap.put("sjhyzbs", app50011ZHResult.getSjhyzbs());
		modelMap.put("yhkyzbs", app50011ZHResult.getYhkyzbs());
		
		modelMap.put("smrzbs", app50011ZHResult.getSmrzbs());
		modelMap.put("seqno", app50011ZHResult.getSeqno());
		
		return modelMap;
	}

}
