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
import com.yondervision.yfmap.common.security.AES;
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
 * 业务办理-银行列表
 */
public class Handle01024_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01024 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("appApi01024 form:"+form); 
		/* 模拟返回开始  */
		AppApi01024Result app01024Result= new AppApi01024Result();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		
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
			//归集业务传1
			map.put("flag", "1");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YWBLYHXX.txt", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149369");

			log.debug("中心下传报文："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLYHXX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap1);			
			
			
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
				return modelMap;
			}	
			resultMap.put("actmp1024", resultMap1.get("actmp1024"));
			//第6次通讯，收款方式为为1-本人联名卡查询收款相关信息
			map.put("stepseqno", "4");

			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-收款方式为为1-本人联名卡查询收款相关信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-收款方式为为1-本人联名卡查询收款相关信息："+rexml);
			
			HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap6.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap6.get("recode"));
				modelMap.put("msg", resultMap6.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap6.get("recode")+"  ,  描述msg : "+resultMap6.get("msg"));
				return modelMap;
			}	

			resultMap.put("payeebankname_1", resultMap6.get("bankname"));
			resultMap.put("payeebankaccnm0_1", resultMap6.get("accname"));
			resultMap.put("payeebankacc0_1", resultMap6.get("cardno"));
			resultMap.put("payeebankcode_1", resultMap6.get("bankcode"));
			resultMap.put("linkrow_1", resultMap6.get("linkrow"));
			
			//返回页面值【页面payerbankcode】不为空，继续调用查询接口类型
			if(!CommonUtil.isEmpty(resultMap.get("payerbankcode_1"))){
				map.put("stepseqno", "7");
				map.put("bankcode", resultMap.get("payerbankcode_1"));
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
				log.debug("发往中心报文-收款方式为为1-本人联名卡查询接口类型："+xml);
				
				// 通信
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
				log.debug("中心返回报文-收款方式为为1-本人联名卡查询接口类型："+rexml);
				
				HashMap resultMap6_1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
				if(!"0".equals(resultMap6_1.get("recode"))){				
					modelMap.clear();
					modelMap.put("recode", resultMap6_1.get("recode"));
					modelMap.put("msg", resultMap6_1.get("msg"));
					log.error("中心返回报文 状态recode :"+resultMap6_1.get("recode")+"  ,  描述msg : "+resultMap6_1.get("msg"));
					return modelMap;
				}	
				resultMap.put("porttype_1", resultMap6.get("porttype"));
			}
		
			//第7次通讯，收款方式为4-其他收款账户查询【actmp1024】
			map.put("stepseqno", "5");

			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-收款方式为4-其他收款账户查询【actmp1024】："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-收款方式为4-其他收款账户查询【actmp1024】："+rexml);
			
			HashMap resultMap7 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap7.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap7.get("recode"));
				modelMap.put("msg", resultMap7.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap7.get("recode")+"  ,  描述msg : "+resultMap7.get("msg"));
				return modelMap;
			}	
			resultMap.put("actmp1024_ls", resultMap7.get("actmp1024"));
			
			BeanUtil.transMap2Bean(resultMap, app01024Result);
		}		
		
		// 解析银行列表 
		 String str = app01024Result.getActmp1024();
		if(CommonUtil.isEmpty(str)){
			str = "";
		}
		str = "{"+str+"}";
		JSONObject jsonObject = JSONObject.fromObject(str);
		Iterator iterator = jsonObject.keys();
		List<TitleInfoBean> appapi00101Result = new ArrayList<TitleInfoBean>();
		TitleInfoBean tt = null;
		while(iterator.hasNext()){        	
			String key = (String) iterator.next();        
			String value = jsonObject.getString(key);
			tt = new TitleInfoBean();
			tt.setTitle(key);
			tt.setInfo(value);
			appapi00101Result.add(tt);
			//System.out.println(key+"===="+value);
		}
		
		// 解析银行列表 
		 String str_ls = app01024Result.getActmp1024_ls();
		if(CommonUtil.isEmpty(str_ls)){
			str_ls = "";
		}
		str_ls = "{"+str_ls+"}";
		JSONObject jsonObject_ls = JSONObject.fromObject(str_ls);
		Iterator iterator_ls = jsonObject.keys();
		List<TitleInfoBean> appapi00101Result_ls = new ArrayList<TitleInfoBean>();
		TitleInfoBean tt_ls = null;
		while(iterator.hasNext()){        	
			String key = (String) iterator.next();        
			String value = jsonObject.getString(key);
			tt = new TitleInfoBean();
			tt.setTitle(key);
			tt.setInfo(value);
			appapi00101Result_ls.add(tt_ls);
			//System.out.println(key+"===="+value);
		}
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		//modelMap.put("result", app01024Result.getActmp1024());
		modelMap.put("detail", appapi00101Result);
		modelMap.put("detail_ls", appapi00101Result_ls);
		modelMap.put("bankaccnum", app01024Result.getBankaccnum());
		modelMap.put("bankname", app01024Result.getBankname());
		modelMap.put("payeebankname_1", app01024Result.getPayeebankname_1());
		modelMap.put("payeebankaccnm0_1", app01024Result.getPayeebankaccnm0_1());
		modelMap.put("payeebankacc0_1", app01024Result.getPayeebankacc0_1());
		modelMap.put("linkrow_1", app01024Result.getLinkrow_1());
		modelMap.put("payerbankcode_1", app01024Result.getPayeebankcode_1());
		modelMap.put("porttype_1", app01024Result.getPorttype_1());
//		modelMap.put("detail", jsonObject);
		log.info(Constants.LOG_HEAD+"appApi01024 end.");
		return modelMap;
	}

}
