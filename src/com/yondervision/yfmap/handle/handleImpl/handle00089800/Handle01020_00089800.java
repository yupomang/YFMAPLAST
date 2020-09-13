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
import com.yondervision.yfmap.result.haikou.AppApi01020Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 业务办理  提取 通用-查询依据提取号-海口
 * @author Administrator
 *
 */
public class Handle01020_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01020 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		AppApi01020Result app01020Result = new AppApi01020Result();
		
		if(Constants.method_BSP.equals(send)){
						
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
			HashMap resultMap = new HashMap();
			
			
			/*第1次通讯 查询提取原因*/
			map.put("stepseqno", "2");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询提取原因："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询提取原因："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap1.get("drawreason"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询2，交易码【158030】，查询提取原因为空。");
				log.error("查询2，交易码【158030】，查询提取原因为空。");
				return modelMap;
			}
			resultMap.put("drawreason", resultMap1.get("drawreason"));
			map.put("drawreason", resultMap1.get("drawreason"));
			
			
			/* 第2次通信   查询提取依据号*/
			map.put("stepseqno", "3");
			if("9".equals(form.getDrawreasoncode1()))
			{
				map.put("stepseqno", "9");
			}
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询提取依据号："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询提取依据号："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
				return modelMap;
			}		
			resultMap.put("drawreasoncode1", resultMap2.get("actmp1024"));
			/* 第3-1次通信   配偶信息查询*/
			if("01".equals(form.getRelation()))
			{
				//关系类型-本人
				map.put("relation", "01");
				//证件类型
				map.put("owncertitype", form.getZjlx());
				//证件号码
				map.put("owncertinum", form.getBodyCardNumber());
				//姓名
				map.put("buyhousename", form.getXingming());
				//证件类型
				map.put("matecertitype", "");
				//证件号码
				map.put("matecertinum", "");
				//姓名
				map.put("matename", "");
			}else
			{
				//关系类型-配偶
				map.put("relation", "02");
				//证件类型
				map.put("matecertitype", form.getZjlx());
				//证件号码
				map.put("matecertinum", form.getBodyCardNumber());
				//姓名
				map.put("matename", form.getXingming());
				//证件类型
				map.put("owncertitype", "");
				//证件号码
				map.put("owncertinum", "");
				//姓名
				map.put("buyhousename", "");
			}
			
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_POCX.txt", map, req);
			log.debug("发往中心报文-个人信息查询："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158028");
			log.debug("中心返回报文-个人信息查询："+rexml);
			
			HashMap resultMap3_2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_POCX.txt", rexml, req);
			if(!"0".equals(resultMap3_2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3_2.get("recode"));
				modelMap.put("msg", resultMap3_2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3_2.get("recode")+"  ,  描述msg : " + resultMap3_2.get("msg"));
				return modelMap;
			}
			
			resultMap.put("buyhousename", resultMap3_2.get("buyhousename"));
			resultMap.put("owncertinum", resultMap3_2.get("owncertinum"));
			resultMap.put("owncertitype", resultMap3_2.get("owncertitype"));
			resultMap.put("matename", resultMap3_2.get("matename"));
			resultMap.put("matecertinum", resultMap3_2.get("matecertinum"));
			resultMap.put("matecertitype", resultMap3_2.get("matecertitype"));
			BeanUtil.transMap2Bean(resultMap, app01020Result);
			
		}
		
		
		String actmp1024 = app01020Result.getDrawreasoncode1();
		if(CommonUtil.isEmpty(actmp1024)){
			actmp1024 = "";
		}
		actmp1024 = "{"+actmp1024+"}";
		JSONObject jsonObject = JSONObject.fromObject(actmp1024);
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
		
		app01020Result.setMatecertitype_code(app01020Result.getMatecertitype());
		app01020Result.setOwncertitype_code(app01020Result.getOwncertitype());
		if(!CommonUtil.isEmpty(app01020Result.getMatecertitype()))app01020Result.setMatecertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01020Result.getMatecertitype()+form.getCenterId()));
		if(!CommonUtil.isEmpty(app01020Result.getOwncertitype()))app01020Result.setOwncertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01020Result.getOwncertitype()+form.getCenterId()));
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("buyhousename", app01020Result.getBuyhousename());
		modelMap.put("owncertinum", app01020Result.getOwncertinum());
		modelMap.put("owncertitype", app01020Result.getOwncertitype_code());
		modelMap.put("matename", app01020Result.getMatename());
		modelMap.put("matecertinum", app01020Result.getMatecertinum());
		modelMap.put("matecertitype", app01020Result.getMatecertitype_code());
		//modelMap.put("details", list);
		log.info(Constants.LOG_HEAD+"appApi01020 end.");
		
		return modelMap;
	}
	
}
