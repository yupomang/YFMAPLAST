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
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi50012ZHResult;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50012_00077500 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50002Form form = (AppApi50002Form)form1;
		System.out.println("YFMAP发往中心——单位注册验证");
		log.debug("00077500请求50012参数："+CommonUtil.getStringParams(form));
//		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
//			
//		form.setOpercode(aes.decrypt(form.getOpercode().trim()));
//		form.setCertinum(aes.decrypt(form.getCertinum()));
//		form.setUnitlinkphone2(aes.decrypt(form.getUnitlinkphone2()));
//		form.setOpname(aes.decrypt(form.getOpname()));
//		form.setPassword(aes.decrypt(form.getPassword()));
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50012ZHResult app50012ZHResult = new AppApi50012ZHResult();
		
		
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
//			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWGJJZHSFRZ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DWGJJZHSFRZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314005");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314005</><TranDate>1</><TranIP>1</><TranSeq>1</><RspFlag>0</><RspInfo>1</><unitaccname>2</><unitaccnum>2</><unitcustid>2</><orgcode>2</><licensenum>2</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWGJJZHSFRZ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DWGJJZHSFRZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50012ZHResult);
			log.debug("MAP封装成BEAN："+app50012ZHResult);
			if(!"0".equals(app50012ZHResult.getRecode())){
				modelMap.put("recode", app50012ZHResult.getRecode());
				modelMap.put("msg", app50012ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50012ZHResult.getRecode()+"  ,  描述msg : "+app50012ZHResult.getMsg());
				return modelMap;
			}
		}
		 List<TitleInfoNameFormatBean> appapi50012ZHResult = null;
			
			
			appapi50012ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi50007"+form.getCenterId()+".result", app50012ZHResult);
			Iterator<TitleInfoNameFormatBean> it1 = appapi50012ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
				log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
			}
		modelMap.clear();		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		
		modelMap.put("unitaccname", app50012ZHResult.getUnitaccname());
		modelMap.put("unitaccnum", app50012ZHResult.getUnitaccnum());
		modelMap.put("unitcusid", app50012ZHResult.getUnitcusid());
		modelMap.put("orgcode", app50012ZHResult.getOrgcode());
		modelMap.put("licensenum", app50012ZHResult.getLicensenum());		
		
		return modelMap;
	}
//	public static void main(String[] args) throws UnsupportedEncodingException{
//		GetMethod myget = new GetMethod(url);
//		HttpClient httpClient = new HttpClient();		
//		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);
//		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);
//		StringBuffer responsestr = new StringBuffer();
//		int re_code = httpClient.executeMethod(myget);
//		String url="http://www.baidu.com?ds=22 gg";
//		System.out.println( URLEncoder.encode(url,"UTF-8"));
//	}
}
