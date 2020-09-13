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
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi50013ZHResult;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50013_00087100 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50002Form form = (AppApi50002Form)form1;
		System.out.println("YFMAP发往中心——单位登录");
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
//			
		form.setOpercode(aes.decrypt(form.getOpercode().trim()));
//		form.setCertinum(aes.decrypt(form.getCertinum()));
//		form.setUnitlinkphone2(aes.decrypt(form.getUnitlinkphone2()));
//		form.setOpname(aes.decrypt(form.getOpname()));
		form.setPassword(aes.decrypt(form.getPassword()));
//		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50013ZHResult app50013ZHResult = new AppApi50013ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
//			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWZHDLYZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_DWZHDLYZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314006");
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314006</><TranDate>1</><TranIP>1</><TranSeq>1</><unitaccnum>4</><unitaccname>6</><opname>4</><unitaddr>3</><unitphone>23434235</><exitdate>4</><unitcustid>4</><orgcode>223</><licensenum>4</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWZHDLYZ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_DWZHDLYZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50013ZHResult);
			log.debug("MAP封装成BEAN："+app50013ZHResult);
			if(!"0".equals(app50013ZHResult.getRecode())){
				modelMap.put("recode", app50013ZHResult.getRecode());
				modelMap.put("msg", app50013ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50013ZHResult.getRecode()+"  ,  描述msg : "+app50013ZHResult.getMsg());
				return modelMap;
			}
		}
		
//		List<TitleInfoNameFormatBean> appapi50013ZHResult = null;
//		appapi50013ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi50013"+form.getCenterId()+".result", app50013ZHResult);
//		Iterator<TitleInfoNameFormatBean> it1 = appapi50013ZHResult.iterator();
//		while (it1.hasNext()) {
//			TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
//			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//		}
		modelMap.clear();
		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		
		modelMap.put("unitaccnum", app50013ZHResult.getUnitaccnum());
		modelMap.put("unitaccname", app50013ZHResult.getUnitaccname());
		modelMap.put("opname", app50013ZHResult.getOpname());
		modelMap.put("unitaddr", app50013ZHResult.getUnitaddr());
		modelMap.put("unitphone", app50013ZHResult.getUnitphone());
		modelMap.put("exitdate", app50013ZHResult.getExitdate());
		modelMap.put("unitcustid", app50013ZHResult.getUnitcustid());
		modelMap.put("orgcode", app50013ZHResult.getOrgcode());
		modelMap.put("licensenum", app50013ZHResult.getLicensenum());
		modelMap.put("wt_transdate", app50013ZHResult.getWt_transdate());
		modelMap.put("wt_instcode", app50013ZHResult.getWt_instcode());
		modelMap.put("wt_op", app50013ZHResult.getWt_op());
		return modelMap;
	}
	
}
