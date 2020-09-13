package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi04202ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle04202_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00077500请求04202参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi04202ZHResult app04202ZHResult = new AppApi04202ZHResult();

		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWJCZMDY.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DWJCZMDY.txt", map, req);
			log.debug("前置YFMAP发往中心报文——单位缴存证明打印获取单位账户信息："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111215");
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126991</><TranDate>1</><TranIP>1</><TranSeq>1</><dwfcrs>100</><dwjcbl>100</><dwjcje>100000.12</><dwjcrs>100</><dwjczt>1</><dwmc>aaaaa</><dwslrq>20161212</><dwzhye>50000.00</><jzny>201612</>";
			log.debug("前置YFMAP接收中心报文——单位缴存证明打印获取单位账户信息："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWJCZMDY.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DWJCZMDY.txt", rexml, req);
			log.debug("解析报文MAP——单位缴存证明打印获取单位账户信息："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app04202ZHResult);
			if(!"0".equals(app04202ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app04202ZHResult.getRecode());
				modelMap.put("msg", app04202ZHResult.getMsg());
				log.error("获取单位账户信息，中心返回报文 状态recode :"+app04202ZHResult.getRecode()+"  ,  描述msg : "+app04202ZHResult.getMsg());
				return modelMap;
			}
			
			Date date1 = new Date();
			form.setSendDate(formatter1.format(date1));
			form.setSendTime(formatter2.format(date1));
			String req1 = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req1);
			HashMap<String, String> map1 = BeanUtil.transBean2Map(form);
			map1.put("dwfcrs", app04202ZHResult.getDwfcrs());
			map1.put("dwjcbl", app04202ZHResult.getDwjcbl());
			map1.put("dwjcje", app04202ZHResult.getDwjcje());
			map1.put("dwjcrs", app04202ZHResult.getDwjcrs());
			map1.put("dwjczt", app04202ZHResult.getDwjczt());
			map1.put("dwmc", app04202ZHResult.getDwmc());
			map1.put("dwslrq", app04202ZHResult.getDwslrq());
			map1.put("dwzhye", app04202ZHResult.getDwzhye());
			map1.put("jzny", app04202ZHResult.getJzny());

			String xml1 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWJCZMDY1.txt", map1, req1);
			//String xml1 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DWJCZMDY1.txt", map1, req1);
			log.debug("前置YFMAP发往中心报文——单位缴存证明打印获取主机流水号："+xml1);	
			
			String rexml1 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml1, "117093");
			//String rexml1 ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126991</><TranDate>1</><TranIP>1</><TranSeq>1</><hostsernum>123456</>";
			log.debug("前置YFMAP接收中心报文——单位缴存证明打印获取主机流水号："+rexml1);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWJCZMDY1.txt", rexml1, req1);
			//HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DWJCZMDY1.txt", rexml1, req1);
			log.debug("解析报文MAP——单位缴存证明打印获取主机流水号："+resultMap1);
			BeanUtil.transMap2Bean(resultMap1, app04202ZHResult);
			if(!"0".equals(app04202ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app04202ZHResult.getRecode());
				modelMap.put("msg", app04202ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app04202ZHResult.getRecode()+"  ,  描述msg : "+app04202ZHResult.getMsg());
				return modelMap;
			}
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("hostsernum", app04202ZHResult.getHostsernum());

		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setDwzh("111222");
		Handle04202_00077500 hand = new Handle04202_00077500();
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
