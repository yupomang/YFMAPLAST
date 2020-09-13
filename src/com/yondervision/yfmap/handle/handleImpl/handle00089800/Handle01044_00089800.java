package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.yondervision.yfmap.result.haikou.AppApi01021Result;
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 公积金还贷查询-页面2-计算提取金额
 * @author LFX
 *
 */
public class Handle01044_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01044 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

//		if(CommonUtil.isEmpty(form.getBodyCardNumber()) || CommonUtil.isEmpty(form.getTqyy()) ){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "上传参数不可为空。");
//			log.error("上传参数不可为空。");
//			return modelMap;
//		}
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		
		AppApi01021Result app01025Result = new AppApi01021Result();
		
		// 结果map
		HashMap resultMap = new HashMap();
		String drawreasoncode1 =form.getDrawreasoncode1();
		if(Constants.method_BSP.equals(send)){
			if(CommonUtil.isEmpty(form.getDrawreasoncode1()))
			{
				form.setDrawreasoncode1(form.getJkhtbh());
			}
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);

			/* 第1次通信   查询提取信息  drawreasoncode1、dwzh、drawreason、jkhtbh上传*/
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXGXX.txt", map, req);
//			log.debug("发往中心报文-查询提取信息："+xml);
//			
//			// 通信
//			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158000");
//			log.debug("中心返回报文-查询提取信息："+rexml);
//			
//			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXGXX.txt", rexml, req);
//			if(!"0".equals(resultMap1.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap1.get("recode"));
//				modelMap.put("msg", resultMap1.get("msg"));
//				log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
//				return modelMap;
//			}
			
			//map.put("stepseqno", "0");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKCX.txt", map, req);
			log.debug("发往中心报文-查询贷款信息："+xml);
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158029");
			log.debug("中心返回报文-查询贷款信息："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKCX.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
				return modelMap;
			}
			
			/* 第一次通信  查询个人账号*/
			map.put("stepseqno", "1");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询个人账号："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询个人账号："+rexml);
			
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap3.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap3.get("recode")+"  ,  描述msg : "+resultMap3.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap3.get("grzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询1，交易码【158030】，查询个人账号为空。");
				log.error("查询1，交易码【158030】，查询个人账号为空。");
				return modelMap;
			}
			map.put("grzh", resultMap3.get("grzh"));
			
			//2017-07-24 核心接口修改 精确个人查询信息
			map.put("summarycode", "1228");
			map.put("grzh", "");
			/* 第3次通信   个人信息查询*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLGRXXCX.txt", map, req);
			log.debug("发往中心报文-个人信息查询："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
			log.debug("中心返回报文-个人信息查询："+rexml);
			
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLGRXXCX.txt", rexml, req);
			if(!"0".equals(resultMap4.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
				return modelMap;
			}
			map.put("grzh", resultMap3.get("grzh"));
			if(CommonUtil.isEmpty(resultMap4.get("dwzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询3，交易码【119002】，查询单位账号为空。");
				log.error("查询3，交易码【119002】，查询单位账号为空。");
				return modelMap;
			} 
			//单位账号
			map.put("dwzh", resultMap4.get("dwzh"));
			//map.put("drawreasoncode1", form.getDrawreasoncode1());
			/* 第4次通信   获取多合同标识*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQDHTBS.txt", map, req);
			log.debug("发往中心报文-获取多合同标识："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158000");
			log.debug("中心返回报文-获取多合同标识："+rexml);
			
			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQDHTBS.txt", rexml, req);
			if(!"0".equals(resultMap5.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap5.get("recode"));
				modelMap.put("msg", resultMap5.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap5.get("recode")+"  ,  描述msg : " + resultMap5.get("msg"));
				return modelMap;
			}
			
			
			//将查回来的个人信息放入请求map中。
			map.putAll(resultMap4);
			//将查回来的提取信息放入请求map中。
			map.putAll(resultMap1);
			//多合同标识
			map.put("procode", resultMap5.get("procode"));
			if(!CommonUtil.isEmpty(resultMap5.get("procode")))
			{
				if(!CommonUtil.isEmpty(drawreasoncode1))
				{
					map.put("firstflag", "1");
				}else
				{
					map.put("firstflag", "2");
				}
				
			}
			//证件类型
			map.put("owncertitype", form.getOwncertitype());
			//证件号码
			map.put("owncertinum", form.getOwncertinum());
			//姓名
			map.put("buyhousename", form.getBuyhousename());
			//证件类型
			map.put("matecertitype", form.getMatecertitype());
			//证件号码
			map.put("matecertinum", form.getMatecertinum());
			//姓名
			map.put("matename", form.getMatename());
			
			map.put("buyhousetype","1");
			/*第2次通信 计算提取金额*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLJSTQJE.txt", map, req);
			log.debug("发往中心报文-计算提取金额："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158001");
			log.debug("中心返回报文-计算提取金额："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLJSTQJE.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
				return modelMap;
			}	

			//提取金额
			resultMap.put("drawamt", resultMap2.get("drawamt"));
			//提取金额
			resultMap.put("repaymonths", resultMap2.get("repaymonths"));


			//modelMap.clear();
			//modelMap.put("app01025Result", app01025Result);
			BeanUtil.transMap2Bean(resultMap, app01025Result);	
		}
		
//		List<TitleInfoBean> appapi00101Result = null;
//		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01044"+form.getCenterId()+".result", app01025Result);
//		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
//		while (it1.hasNext()) {
//			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
//			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("drawamt", app01025Result.getDrawamt());
		modelMap.put("repaymonths", app01025Result.getRepaymonths());
		log.info(Constants.LOG_HEAD+"appApi01044 end.");
		
		return modelMap;
	}
	
	
}
