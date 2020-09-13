package com.yondervision.yfmap.handle.handleImpl.handle00047000;

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
import com.yondervision.yfmap.form.AppApi01008Form;
import com.yondervision.yfmap.form.AppApi01009Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00101Result;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi00101Result;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi01008Result;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi01009Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle0100906_00047000 
* @Description: 浦发银行贷款授权
* 
*/ 
public class Handle0100906_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi0100906 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01009Form form = (AppApi01009Form)form1;
		AES aes =new AES();
		try{
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
		form.setFullName(aes.decrypt(form.getFullName()));
		}catch(Exception e){
			e.printStackTrace();
		}
		log.debug("浦发银行贷款授权请求参:"+JsonUtil.getGson().toJson(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		HulunbuirAppApi01009Result app40101Result = new HulunbuirAppApi01009Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("10".equals(form.getChannel()))
			{
				form.setChannel("21");
			}else if("20".equals(form.getChannel()))
			{
				form.setChannel("31");
			}
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DKSC.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "112821");
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DKSC.txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!Constants.sucess_ts.equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi0100906 end.");
		return modelMap;
	}
	public static void main(String[] args) {
		String req = "";
		String rexml = "<AuthCode1>9003</><AuthCode2>9003</><AuthCode3>9003</><AuthFlag></><BrcCode>00003102</><BusiSeq>171</><ChannelSeq>695491</><ChkCode>9003</><FinancialDate>2016-07-01</><HostBank></><MTimeStamp>2016-11-15 12:21:46</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-09 11:26:37</><SubBank></><TellCode>9003</><TranChannel>01</><TranCode>311602</><TranDate>2016-11-21</><TranIP>18.16.2.9</><TranSeq>171</><accname>李硕欣</><accnum>104302</><balance>61097.88</><bankname>中行包头分行营业部</><basenumber>5436.00</><cardno>6013828402007533909</><certinum>150203196809222768</><indiprop>12.00</><lastpaydate>2016年04月</><monpaysum>1306.00</><peraccstate>正常</><peryje>653.00</><prehapdate>2016年07月01日</><prop>24.00</><unitaccname>包钢集团电气有限公司</><unitaccnum>2112</><unitprop>12.00</><unityje>653.00</>";
		HashMap resultMap = MessageCtrMain.analysisPacket("5", "D:/tomcat/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00047201/BSP_REP_YECX.txt", rexml, req);
		
	}
}
