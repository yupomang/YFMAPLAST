package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi50026ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50026_00077500 implements CtrlHandleInter1{
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00077500请求50026参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi50026ZHResult app50026ZHResult = new AppApi50026ZHResult();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YHZHYXXYZ.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_YHZHYXXYZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "192704");
			
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</><bankresdinfo>aaaaaaaaa</><message>有效</><state>正常</><sysstate>1234678</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YHZHYXXYZ.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_YHZHYXXYZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50026ZHResult);
			log.debug("MAP封装成BEAN："+app50026ZHResult);
			if(!"0".equals(app50026ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50026ZHResult.getRecode());
				modelMap.put("msg", app50026ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50026ZHResult.getRecode()+"  ,  描述msg : "+app50026ZHResult.getMsg());
				return modelMap;
			}
		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("bankresdinfo", app50026ZHResult.getBankresdinfo());
		modelMap.put("message", app50026ZHResult.getMessage());
		modelMap.put("state", app50026ZHResult.getState());
		modelMap.put("sysstate", app50026ZHResult.getSysstate());

		return modelMap;
	}

	/*public static void main(String[] args){
		AppApi50001Form form1 = new AppApi50001Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBankaccnm("12345");
		form1.setBankaccnum("21212121");
		form1.setBankcode("13579");

		Handle50026_00077500 hand = new Handle50026_00077500();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap, null, null)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
