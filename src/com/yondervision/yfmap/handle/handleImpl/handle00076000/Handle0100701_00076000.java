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
public class Handle0100701_00076000 implements CtrlHandleInter {
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_ZHFTQJY.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156015");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_ZHFTQJY.xml", rexml, req);
			if(!Constants.sucess.equals(resultMap.get("recode"))){
				modelMap.put("recode", resultMap.get("recode"));
				modelMap.put("msg", resultMap.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap.get("recode")+"  ,  描述msg : "+resultMap.get("msg"));
				return modelMap;
			}
			String stepseqno="1";
			if(!"1".equals(resultMap.get("flag"))&&!"2".equals(resultMap.get("flag"))&&!"3".equals(resultMap.get("flag"))&&!"4".equals(resultMap.get("flag"))){
				modelMap.put("recode","111111");
				modelMap.put("msg", "根据登记信息显示您没有房产，不允许做住房消费提取，请做租房提取");
				log.error("中心返回报文 状态recode :"+"111111"+"  ,  描述msg : 根据登记信息显示您没有房产，不允许做住房消费提取，请做租房提取");
				return modelMap;
			}
			stepseqno="2";
			form.setFlag((String)resultMap.get("flag"));
			form.setSealtype(stepseqno);
			map = BeanUtil.transBean2Map(form);
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_ZHFTQ.xml", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "156013");
			
			log.debug("中心下传报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_ZHFTQ.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app01004Result);
			if(!Constants.sucess.equals(app01004Result.getRecode())){
				modelMap.put("recode", app01004Result.getRecode());
				modelMap.put("msg", app01004Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01004Result.getRecode()+"  ,  描述msg : "+app01004Result.getMsg());
				return modelMap;
			}
		}		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", app01004Result.getMsg());
		log.info(Constants.LOG_HEAD+"appApi01007 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi01004Form form1 = new AppApi01004Form();
		ModelMap modelMap = new ModelMap();
		AES aes = new AES();
		form1.setSurplusAccount("99999");
		form1.setBankaccnm("建行");
		form1.setBankaccnum(aes.encrypt("622322243243".getBytes()));
		form1.setIdcardNumber("P1kHJ1XDoEijufeX92iOhGGAF8jE3ScDIhlLIj0iU2E=");
		form1.setBankname("孙亚伟");
		form1.setDrawSignType("01");
		form1.setSignType("02");
		form1.setCheckid("1332");
		form1.setCenterId("00053100");
		Handle0100701_00076000 hand = new Handle0100701_00076000();
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
