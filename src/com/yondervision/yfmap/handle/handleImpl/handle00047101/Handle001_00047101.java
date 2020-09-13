package com.yondervision.yfmap.handle.handleImpl.handle00047101;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle001_00085200 
* @Description: 公积金信息查询
* @author Caozhongyan
* @date Apr 7, 2016 11:07:04 AM   
* 
*/ 
public class Handle001_00047101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi00101 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		AES aes =new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		log.debug("遵义公积金余额查询请求参:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101Result app40101Result = new AppApi00101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_YECX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
//			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "112813");
			
			String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>12345678</><BusiSeq>1269</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-08-10 05:04:09</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-08-10 05:04:09</><TellCode>cweb</><TranChannel>00</><TranCode>112813</><TranDate>2016-08-10</><TranIP>192.168.10.20</><TranSeq>1269</><accname>刘文龙</><accname1>正常</><accnum>801003368857</><bal>10366.79</><basenum>2145.60</><certinum>220122199107225011</><indipayamt>257.47</><indipaysum>514.94</><indiprop>0.120</><lasttransdate>2016-07-01</><lpaym>201604</><opnaccdate>2014-07-01</><unitaccname>乌兰察布电业局</><unitaccnum>01030032</><unitpayamt>257.47</><unitprop>0.120</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_YECX.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
		}
		app40101Result.setPersonalPaymentRate(String.format("%,.2f",Double.valueOf(app40101Result.getPersonalPaymentRate())*100)+"%");
		app40101Result.setCompanyPaymentRate(String.format("%,.2f",Double.valueOf(app40101Result.getCompanyPaymentRate())*100)+"%");
		List<TitleInfoBean> appapi40101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app40101Result);
		Iterator<TitleInfoBean> it1 = appapi40101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40101Result);
		log.info(Constants.LOG_HEAD+"appApi00101 end.");
		return modelMap;
	}
}
