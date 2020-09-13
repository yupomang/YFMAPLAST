package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.WebApi50051ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50051_00057400  implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00201Form form = (AppApi00201Form)form1;
		log.debug("00057400请求50051参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		WebApi50051ZHResult webapi50051ZHResult = new WebApi50051ZHResult();

		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setTranDate(form.getSendDate());
			form.setSendTime(formatter2.format(date));
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			HashMap map = BeanUtil.transBean2Map(form);	
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_50051_110159.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRZHJBXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110159");
			
			log.debug("前置YFMAP接收中心报文——满意度调查："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_50051_110159.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRZHJBXXCX.txt", rexml, req);

			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, webapi50051ZHResult);
			if(!"0".equals(webapi50051ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", webapi50051ZHResult.getRecode());
				modelMap.put("msg", webapi50051ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+webapi50051ZHResult.getRecode()+"  ,  描述msg : "+webapi50051ZHResult.getMsg());
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("totalnum", webapi50051ZHResult.getTotalnum());
		modelMap.put("failsum", webapi50051ZHResult.getFailsum());
		modelMap.put("rate", Double.valueOf(webapi50051ZHResult.getRate())*100 + "%");
		return modelMap;
	}
}
