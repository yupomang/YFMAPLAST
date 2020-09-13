package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi00301Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi003Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle003_00085200 
* @Description: 结息对账查询
* @author Caozhongyan
* @date Apr 7, 2016 11:06:39 AM   
* 
*/ 
public class Handle003_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00301 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00301Form form = (AppApi00301Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		ZunYiAppApi003Result appApi00301Result	= new ZunYiAppApi003Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_JXDZCX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
//			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "116011");
			
			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>abcd.txt</><accnum>123456789</><accname>王小二</><unitaccname>永新物流公司</><transdate>2016-03-10</><bfcalintbal>9020.10</><calintamt>780.90</><afcalintbal>9800.00</>";
			
			log.debug("中心返回YFMAP报文："+reqxml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_JXDZCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, appApi00301Result);
			log.debug("MAP封装成BEAN："+appApi00301Result);
			if(!"0".equals(appApi00301Result.getRecode())){
				modelMap.put("recode", appApi00301Result.getRecode());
				modelMap.put("msg", appApi00301Result.getMsg());
				log.error("中心返回报文 状态recode :"+appApi00301Result.getRecode()+"  ,  描述msg : "+appApi00301Result.getMsg());
				return modelMap;
			}
		}
		
		appApi00301Result.setBfcalintbal(String.format("%,.2f",Double.valueOf(appApi00301Result.getBfcalintbal())));
		appApi00301Result.setCalintamt(String.format("%,.2f",Double.valueOf(appApi00301Result.getCalintamt())));
		appApi00301Result.setAfcalintbal(String.format("%,.2f",Double.valueOf(appApi00301Result.getAfcalintbal())));
		
		List<TitleInfoBean> result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00301"+form.getCenterId()+".result", appApi00301Result);			
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi00301 end.");
		return modelMap;
	}

}
