package com.yondervision.yfmap.handle.handleImpl.handle00066800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi00601ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle006_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		log.debug("00066800请求00601参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		AppApi00601ZHResult app00601ZHResult = new AppApi00601ZHResult();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			
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
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRDKJBXXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRDKJBXXCX.txt", map, req);			
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126999");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126999</><TranDate>1</><TranIP>1</><TranSeq>1</><jkrxm>543</><jkrzjh>4</><styhmc>5</><jkhtbh>54</><htdkje>5</><dkye>5</><dkqs>4</><dqjhhkje>54</><currate>5</><ydfkrq>45</><ydhkr>5</><gjjflag>5</><yqbjze>5</><yqlxze>43</><owepun>5</><ljyqqs>5</><fwzl>4</><hkzh>5</><swtyhmc>3242</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
//			String rexml ="<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag>0</><BrcCode>00073199</><BusiSeq>129342</><ChannelSeq>0</><ChkCode></><MTimeStamp>2016-12-07 02:04:11</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-07 02:04:11</><TellCode>9999</><TranChannel>4</><TranCode>126999</><TranDate>2017-04-10</><TranIP></><TranSeq>129342</><appdate>2013-01-15</><apprnum>20133000168</><dkll>2.7083000</><dkqs>300</><dkye>235179.42</><dqjhhkje>1274.85</><filename>lnprtf6999_05.2017041000129342</><fwzl>阳三石防洪村阳光家园A栋401 A栋单元 401室</><gjjflag>0</><grzh>350719334</><hkzh>606760706670</><htdkje>280000.00</><jkhtbh>2013300049</><ljyqqs>0</><loancontrstate>09</><owepun>0.00</><swtyhmc>中行株洲县支行</><totalnum>0</><totalpage>1</><xingming>胡四五</><ydfkrq>20130320</><ydhkr>20</><yqbjze>0.00</><yqlxze>0.00</><zjhm>430219198207311357</>";

			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRDKJBXXCX.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRDKJBXXCX.txt", rexml, req);			
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601ZHResult);
			
			log.debug("MAP封装成BEAN："+app00601ZHResult);
			if(!"0".equals(app00601ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601ZHResult.getRecode());
				modelMap.put("msg", app00601ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00601ZHResult.getRecode()+"  ,  描述msg : "+app00601ZHResult.getMsg());
				return modelMap;
			}
			
			
		}	
		
		List<TitleInfoNameFormatBean> appapi00601ZHResule = null;
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
			appapi00601ZHResule = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00601"+form.getCenterId()+".result", app00601ZHResult);	
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00601ZHResule);
		}else {
			
			if(!CommonUtil.isEmpty(app00601ZHResult.getJkhtbh())){
				app00601ZHResult.setJkhtbh(Tools.getZZDesensitizationLoancontrnum(app00601ZHResult.getJkhtbh()));
			}
			if(!CommonUtil.isEmpty(app00601ZHResult.getZjhm())){
				app00601ZHResult.setZjhm(Tools.getDesensitizationCertinum(app00601ZHResult.getZjhm()));
			}
			if(!CommonUtil.isEmpty(app00601ZHResult.getHkzh())){
				app00601ZHResult.setHkzh(Tools.getDesensitizationBank(app00601ZHResult.getHkzh()));
			}
			if(!CommonUtil.isEmpty(app00601ZHResult.getGjjflag())){
				if ("1".equals(app00601ZHResult.getGjjflag())) {
					app00601ZHResult.setGjjflag("是");
				} else {
					app00601ZHResult.setGjjflag("否");
				}				
			}
			
			appapi00601ZHResule = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00601"+form.getCenterId()+".result1", app00601ZHResult);	
			
			List<TitleInfoNameFormatBean> appapi00601ZHDetail = null;
			appapi00601ZHDetail = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00601"+form.getCenterId()+".detail", app00601ZHResult);			
			
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00601ZHResule);
			modelMap.put("detail", appapi00601ZHDetail);
		}
		
		return modelMap;
	}
	
}
