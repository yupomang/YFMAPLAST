package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi50006ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50002_00087100  implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
//		String PROPERTIES_FILE_NAME = "properties.properties";
//		AppApi40102Form form = (AppApi40102Form)form1;
//		log.debug("00073300请求50002参数："+CommonUtil.getStringParams(form));
//		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
//		AppApi50006ZHResult app50006ZHResult = new AppApi50006ZHResult();
//		if(Constants.method_BSP.equals(send)){////xml通信处理
//			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
//			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
//			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			Date date = new Date();
//			form.setSendDate(formatter1.format(date));
//			form.setSendTime(formatter2.format(date));
//			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
//			form.setSendSeqno(req);
//			
//			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
//			
//			if(!form.getChannel().trim().equals("40"))
//			{
//				form.setChannelSeq(form.getSendSeqno());
//				form.setTellCode("9999");
//				form.setBrcCode("00073199");
////				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
////				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
//				form.setTranDate(form.getSendDate());
//			}
//			form.setFlag(Channel.getZzChannel(form.getChannel()));
//			
//			HashMap map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_WXJCBD.txt", map, req);
//			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_WXJCBD.txt", map, req);
//			log.debug("前置YFMAP发往中心报文："+xml);	
//
//			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
//			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
//			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "168013");
//			
//			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>";
//			
//			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_WXJCBD.txt", rexml, req);
//			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_WXJCBD.txt", rexml, req);
//			log.debug("解析报文MAP："+resultMap);
//			BeanUtil.transMap2Bean(resultMap, app50006ZHResult);
//			if(!"0".equals(app50006ZHResult.getRecode())){
//				modelMap.clear();
//				modelMap.put("recode", app50006ZHResult.getRecode());
//				modelMap.put("msg", app50006ZHResult.getMsg());
//				log.error("中心返回报文 状态recode :"+app50006ZHResult.getRecode()+"  ,  描述msg : "+app50006ZHResult.getMsg());
//				return modelMap;
//			}
//		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");

		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi50001Form form1 = new AppApi50001Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00073300");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBodyCardNumber("111111111111111111");
		form1.setCertinumType("01");
		form1.setUserId("2222222222");
	
		Handle50002_00087100 hand = new Handle50002_00087100();
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
