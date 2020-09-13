package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.yondervision.yfmap.result.kunming.AppApi50010ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;


public class Handle50030_00087100 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi50010ZHResult app50010ZHResult = new AppApi50010ZHResult();
		
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getFullName())){
			log.debug("form.getFullName():"+form.getFullName());
			form.setFullName(aes.decrypt(form.getFullName()));
		}
		if(!CommonUtil.isEmpty(form.getBodyCardNumber())){
			log.debug("form.getBodyCardNumber():"+form.getBodyCardNumber());
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		}
		if(!CommonUtil.isEmpty(form.getTel())){
			log.debug("form.getTel():"+form.getTel());
			form.setTel(aes.decrypt(form.getTel()));
		}
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi003Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_ZXSJHMYZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GRGJJZHSFRZ.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314061");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314001</><TranDate>1</><TranIP>1</><TranSeq>1</>";
		
			log.debug("前置YFMAP接收中心报文——个人公积金身份认证："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_ZXSJHMYZ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GRGJJZHSFRZ.txt", rexml, req);
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
		modelMap.put("isHave", app50010ZHResult.getRspFlag());
		modelMap.put("superbank", app50010ZHResult.getRspInfo());
//		工行 01
//		建行 02
//		农行 03
//		中行 04
//		农信社 16
		Map bank1 = new HashMap();
		bank1.put("bankname", "工行");
		bank1.put("bankcode", "01");
		
		Map bank2 = new HashMap();
		bank2.put("bankname", "建行");
		bank2.put("bankcode", "02");
		
		Map bank3 = new HashMap();
		bank3.put("bankname", "农行");
		bank3.put("bankcode", "03");
		
		Map bank4 = new HashMap();
		bank4.put("bankname", "中行");
		bank4.put("bankcode", "04");
		
		Map bank5 = new HashMap();
		bank5.put("bankname", "农信行");
		bank5.put("bankcode", "16");
		
		Map[] banks = new HashMap[5];
		banks[0]=bank1;
		banks[1]=bank2;
		banks[2]=bank3;
		banks[3]=bank4;
		banks[4]=bank5;
		modelMap.put("banks", banks);
		return modelMap;
	}
}
