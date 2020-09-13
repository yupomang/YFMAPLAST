package com.yondervision.yfmap.handle.handleImpl.handle00076000;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01003Form;
import com.yondervision.yfmap.form.AppApi01004Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.jinan.JiNanAppApi01004Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi01007Result;
import com.yondervision.yfmap.result.zhongshan.AppApi01007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 提取签约-01-住房提取
 */
public class Handle0100702_00076000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01003Form form = (AppApi01003Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi01007Result app01004Result= new AppApi01007Result();
		
		if(Constants.method_XML.equals(send)){
			AES aes = new AES();
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_ZFTQJY.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156007");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_ZFTQJY.xml", rexml, req);
			if(!Constants.sucess.equals(resultMap.get("recode"))){
				modelMap.put("recode", resultMap.get("recode"));
				modelMap.put("msg", resultMap.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap.get("recode")+"  ,  描述msg : "+resultMap.get("msg"));
				return modelMap;
			}
			log.debug(resultMap.get("flag")+"==="+(!"0".equals(resultMap.get("flag"))));
			if(!"0".equals(resultMap.get("flag"))){
				if("1".equals(resultMap.get("flag"))){
					modelMap.put("recode","111111");
					modelMap.put("msg", "根据登记信息显示您拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 根据登记信息显示您拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					return modelMap;
				}else if("2".equals(resultMap.get("flag"))){
					modelMap.put("recode","111111");
					modelMap.put("msg", "根据登记信息显示您的配偶拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 根据登记信息显示您的配偶拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					return modelMap;
				}else if("3".equals(resultMap.get("flag"))){
					modelMap.put("recode","111111");
					modelMap.put("msg", "根据登记信息显示您拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 根据登记信息显示您拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					return modelMap;
				}else if("4".equals(resultMap.get("flag"))){
					modelMap.put("recode","111111");
					modelMap.put("msg", "根据登记信息显示您的配偶拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 根据登记信息显示您的配偶拥有房产，如确认无房，请开具住房证明后携带本人身份证、联名卡和住房证明到公积金柜台办理租房提取业务。");
					return modelMap;
				}else{
					modelMap.put("recode","111111");
					modelMap.put("msg", "信息校验错误，请到柜台咨询");
					log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 信息校验错误，请到柜台咨询");
					return modelMap;
				}
			}else{
				 xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_ZFTQ.xml", map, req);
				log.debug("发往中心报文："+xml);
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156030");
				
				log.debug("中心下传报文："+rexml);
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_ZFTQ.xml", rexml, req);
				if(!Constants.sucess.equals(resultMap.get("recode"))){
					modelMap.put("recode", resultMap.get("recode"));
					modelMap.put("msg", resultMap.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap.get("recode")+"  ,  描述msg : "+resultMap.get("msg"));
					return modelMap;
				}
				BeanUtil.transMap2Bean(resultMap, app01004Result);
			}
		}		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", app01004Result.getMsg());
		log.info(Constants.LOG_HEAD+"appApi01007 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi01003Form form1 = new AppApi01003Form();
		ModelMap modelMap = new ModelMap();
		AES aes = new AES();
		form1.setSurplusAccount("99999");
//		form1.setBankaccnm("建行");
//		form1.setBankaccnum(aes.encrypt("622322243243".getBytes()));
//		form1.setIdcardNumber("P1kHJ1XDoEijufeX92iOhGGAF8jE3ScDIhlLIj0iU2E=");
//		form1.setBankname("孙亚伟");
//		form1.setDrawSignType("01");
//		form1.setSignType("02");
		form1.setCheckid("1332");
		form1.setCenterId("00053100");
		Handle0100702_00076000 hand = new Handle0100702_00076000();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
