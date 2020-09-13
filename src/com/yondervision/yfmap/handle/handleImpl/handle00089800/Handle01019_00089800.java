package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi01020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01024Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 业务办理-历史提取收款号变更
 */
public class Handle01019_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01019 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("appApi01024 form:"+form); 
		/* 模拟返回开始  */
		AppApi01024Result app01024Result= new AppApi01024Result();
		
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			HashMap resultMap = new HashMap();
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			
			//第8次通讯，查询历史收款账号
			map.put("stepseqno", "6");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询历史收款账："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询历史收款账："+rexml);
			
			HashMap resultMap8 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap8.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap8.get("recode"));
				modelMap.put("msg", resultMap8.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap8.get("recode")+"  ,  描述msg : "+resultMap8.get("msg"));
				return modelMap;
			}
			resultMap.put("payeebankname_1", resultMap8.get("bankname"));
			resultMap.put("payeebankaccnm0_1", resultMap8.get("accname"));
			resultMap.put("payeebankacc0_1", resultMap8.get("bankacc0"));
			resultMap.put("linkrow_1", resultMap8.get("linkrow"));
			resultMap.put("payerbankcode_1", resultMap8.get("bankcode"));

			
			BeanUtil.transMap2Bean(resultMap, app01024Result);
		}		
		
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("payeebankname_1", app01024Result.getPayeebankname_1());
		modelMap.put("payeebankaccnm0_1", app01024Result.getPayeebankaccnm0_1());
		modelMap.put("payeebankacc0_1", app01024Result.getPayeebankacc0_1());
		modelMap.put("linkrow_1", app01024Result.getLinkrow_1());
		modelMap.put("payerbankcode_1", app01024Result.getPayeebankcode_1());
		//modelMap.put("result", app01024Result.getActmp1024());
//		modelMap.put("detail", jsonObject);
		log.info(Constants.LOG_HEAD+"appApi01019 end.");
		return modelMap;
	}

}
