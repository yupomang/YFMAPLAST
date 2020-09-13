package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00907Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi00906ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00906_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00907Form form = (AppApi00907Form)form1;
		log.debug("00077500请求00906参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Send"+form.getCenterId()).trim();
		AppApi00906ZHResult app00906ZHResult = new AppApi00906ZHResult();
		form.setLoanDuration(String.valueOf(Integer.parseInt(form.getLoanDuration())*12));
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Type"+form.getCenterId()).trim());
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_HKSSLL.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_HKSSLL.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126991");
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126991</><TranDate>1</><TranIP>1</><TranSeq>1</><busiloanrate>0.065</><loanrate>0.045</>";
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_HKSSLL.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_HKSSLL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00906ZHResult);
			if(!"0".equals(app00906ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00906ZHResult.getRecode());
				modelMap.put("msg", app00906ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00906ZHResult.getRecode()+"  ,  描述msg : "+app00906ZHResult.getMsg());
				return modelMap;
			}
		}
		
		BigDecimal loanrate = new BigDecimal(app00906ZHResult.getLoanrate());
//		BigDecimal lt = loanrate.multiply(new BigDecimal(100));
		app00906ZHResult.setLoanrate(String.format("%.2f",loanrate));
		
		BigDecimal busiloanrate = new BigDecimal(app00906ZHResult.getBusiloanrate());
//		BigDecimal bt = busiloanrate.multiply(new BigDecimal(100));
		app00906ZHResult.setBusiloanrate(String.format("%.2f",busiloanrate));
		
		LinkedHashMap appapi00906ZHResult = new LinkedHashMap();
		appapi00906ZHResult.put("surplusLoanInterestRate", app00906ZHResult.getLoanrate());
		appapi00906ZHResult.put("commercialLoanInterestRate", app00906ZHResult.getBusiloanrate());
		 
		

		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("result", appapi00906ZHResult);
				
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi00907Form form1 = new AppApi00907Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setLoanDuration("111222");
		Handle00906_00077500 hand = new Handle00906_00077500();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}

