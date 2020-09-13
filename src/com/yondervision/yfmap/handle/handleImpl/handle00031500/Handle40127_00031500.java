package com.yondervision.yfmap.handle.handleImpl.handle00031500;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi40126Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 个人通讯信息变更
 */
public class Handle40127_00031500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		log.debug("个人通讯信息变更form:"+form); 
		System.out.println("gjjzh====="+form.getSurplusAccount());
		System.out.println("psword====="+form.getPwd());
		System.out.println("cardno====="+form.getCardno());
		System.out.println("smscode====="+form.getSmstmplcode());
		AES aes = new AES();
		form.setCardno(aes.decrypt(form.getCardno()));
		System.out.println("cardno===des==="+form.getCardno());
		
		TangShanAppApi40126Result app40126Result= new TangShanAppApi40126Result();
		String req_xml = "REQ_GRTXXXBG.xml";
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());	
			
			if("10".equals(form.getChannel())){
				form.setBrcCode("00031586");
				form.setFreeuse1("8601");
			}else{
				form.setBrcCode("00031585");
				form.setFreeuse1("8501");
			}
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+req_xml, map, req);
			log.debug("个人通讯信息变更发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00010");
		
			log.debug("个人通讯信息变更中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_GRTXXXBG.xml", rexml, req);
			System.out.println("个人通讯信息变更解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app40126Result);
			
			
			if(!Constants.sucess_ts.equals(app40126Result.getRecode())){
				modelMap.put("recode", app40126Result.getRecode());
				modelMap.put("msg", app40126Result.getMsg());
				log.error("个人通讯信息变更中心返回报文 状态recode :"+app40126Result.getRecode()+"  ,  描述msg : "+app40126Result.getMsg());
				return modelMap;
			}
		}		

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi40102Form form1 = new AppApi40102Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		Handle40127_00031500 hand = new Handle40127_00031500();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
