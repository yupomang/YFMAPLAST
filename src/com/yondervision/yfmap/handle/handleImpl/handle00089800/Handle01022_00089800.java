package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.ApplyPP;
import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01022Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.haikou.AppApi01022Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 业务办理  正常提取-正常提取提交-海口
 */
public class Handle01022_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01022Form form = (AppApi01022Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01022 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("appApi01022 form:"+form); 
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		form.setSkyh(aes.decrypt(form.getSkyh()));
		form.setSkzhmc(aes.decrypt(form.getSkzhmc()));
		form.setDrawamt(form.getSqje());
		
		
//		form.setSkzhmc("王小一");
//		form.setSkdwzh("176708222268");
//		form.setSkyh("中国银行股份有限公司海口秀华支行");
		//form.setSkzhmc("中国银行股份有限公司海口秀华支行");
//		form.setLinkrow("104641004843");
		
		/* 第1次通信，正常提取 */
		AppApi01022Result app01022Result= new AppApi01022Result();
		HashMap resultMap = new HashMap();
		String drawreasoncode1 =form.getDrawreasoncode1();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());
			String shangchuan = form.getShangchuan();
			System.out.println(shangchuan);
			JSONObject obj = JSONObject.fromObject(shangchuan);
			if(!CommonUtil.isEmpty(obj.get("rentdate")))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();//获取当前时间    
				Calendar calendar = Calendar.getInstance();    
				calendar.setTime(date);    
				calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
				//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
				Date d = calendar.getTime();//获取一年前的时间，或者一个月前的时间    
				long nowtime = sdf.parse(sdf.format(date)).getTime();
				long lastyeartime = sdf.parse(sdf.format(d)).getTime();
				long rentdatetime = sdf.parse(obj.get("rentdate").toString()).getTime();
				if(rentdatetime<lastyeartime)
				{
					modelMap.put("recode", "999999");
					modelMap.put("msg", "租房时间距当前日期不能大于一年");
					log.error("中心返回报文 状态recode :999999"+"  ,  描述msg : 租房时间距当前日期不能大于一年");
					return modelMap;
				}
				if(rentdatetime>nowtime)
				{
					modelMap.put("recode", "999999");
					modelMap.put("msg", "租房时间距当前日期不能大于一年");
					log.error("中心返回报文 状态recode :999999"+"  ,  描述msg : 租房时间距当前日期不能大于一年");
					return modelMap;
				}
			}
			Map  h = (Map)obj;
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			map.putAll(h);
			if(!CommonUtil.isEmpty(map.get("drawreasoncode2")))
			{
				form.setDrawreasoncode1(map.get("drawreasoncode2"));
			}
			String phoneNumber="";
			if(!CommonUtil.isEmpty(form.getSjhm()))
			{
				phoneNumber = form.getSjhm();
				if(!CommonUtil.getMatchingNumber(phoneNumber))
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "手机号不合法，请到柜面变更维护手机号！");
					log.error("中心返回报文 状态recode :"+modelMap.get("recode")+"  ,  描述msg : "+modelMap.get("msg"));
					return modelMap;
				}
				if(phoneNumber.length()>11)
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "手机号不合法，请到柜面变更维护手机号！");
					log.error("中心返回报文 状态recode :"+modelMap.get("recode")+"  ,  描述msg : "+modelMap.get("msg"));
					return modelMap;
				}
			}
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			/* 第一次通信  查询个人账号*/
			map.put("stepseqno", "1");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询个人账号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询个人账号："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
				return modelMap;
			}
			if(CommonUtil.isEmpty(resultMap1.get("grzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询1，交易码【158030】，查询个人账号为空。");
				log.error("查询1，交易码【158030】，查询个人账号为空。");
				return modelMap;
			}
			map.put("stepseqno", "0");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_JYYHXX.txt", map, req);
			log.debug("发往中心报文-校验银行卡信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "150012");
			log.debug("中心返回报文-校验银行卡信息："+rexml);
			
			HashMap resultMap1_1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_JYYHXX.txt", rexml, req);
			if(!"0".equals(resultMap1_1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1_1.get("recode"));
				modelMap.put("msg", resultMap1_1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1_1.get("recode")+"  ,  描述msg : " + resultMap1_1.get("msg"));
				return modelMap;
			}
			
			map.put("grzh", resultMap1.get("grzh").toString());
			
			map.put("stepseqno", "7");
			map.put("bankcode", form.getPayerbankcode());
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询个人账号："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询个人账号："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			
			if(!CommonUtil.isEmpty(form.getDrawreasoncode1()))
			{
				map.put("drawreasoncode1",form.getDrawreasoncode1());
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
				map.put("grzh", resultMap1.get("grzh").toString());
				if(CommonUtil.isEmpty(resultMap4.get("dwzh"))){	
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查询3，交易码【119002】，查询单位账号为空。");
					log.error("查询3，交易码【119002】，查询单位账号为空。");
					return modelMap;
				} 
				//冻结
				if("1".equals(resultMap4.get("frzflag"))){	
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "该身份证姓名已被全部冻结，不能提取！");
					log.error("查询3，交易码【119002】，该身份证姓名已被全部冻结，不能提取！");
					return modelMap;
				}
				//黑名单
				if("06".equals(resultMap4.get("crelevel"))){	
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "该身份证姓名已被加入黑名单，不能提取！");
					log.error("查询3，交易码【119002】，该身份证姓名已被加入黑名单，不能提取。");
					return modelMap;
				}
				//单位账号
				map.put("dwzh", resultMap4.get("dwzh").toString());
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
				
				
				//多合同标识
				map.put("procode", resultMap5.get("procode").toString());
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
			}
			//2017-07-24 核心接口修改 精确个人查询信息
			map.put("summarycode", "1228");
			map.put("grzh", "");
			/* 第4次通信   个人信息查询*/
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
			map.put("grzh", resultMap1.get("grzh").toString());
			if(CommonUtil.isEmpty(resultMap4.get("dwzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询4，交易码【119002】，查询单位账号为空。");
				log.error("查询4，交易码【119002】，查询单位账号为空。");
				return modelMap;
			} 
			//个人账号状态
			resultMap.put("indiaccstate", resultMap4.get("indiaccstate"));
			//姓名
			resultMap.put("xingming", resultMap4.get("xingming"));
			//证件类型
			resultMap.put("zjlx", resultMap4.get("zjlx"));
			//个人账户余额
			resultMap.put("grzhye", resultMap4.get("grzhye"));
			BigDecimal grzhye=new BigDecimal(resultMap4.get("grzhye").toString());
			BigDecimal stpayamt=new BigDecimal(resultMap4.get("stpayamt").toString());
			BigDecimal frzamt=new BigDecimal(resultMap4.get("frzamt").toString());
			BigDecimal kyye = grzhye.subtract(stpayamt).subtract(frzamt);
			//可用余额
			resultMap.put("kyye", kyye.toString());
			resultMap.put("usebal", kyye.toString());
			resultMap.put("sjhm", resultMap4.get("sjhm"));
			resultMap.put("instcode", resultMap4.get("accinstcode"));
//			if("002".equals(form.getDrawreason()))
//			{
//				map.put("buyhousetype", map.get("ctlflag"));
//			}else if("006".equals(form.getDrawreason()))
//			{
//				map.put("buyhousetype", "1");
//			}else if("009".equals(form.getDrawreason()))
//			{
//				map.put("buyhousedate", map.get("buyhousedate1"));
//			}else if("004".equals(form.getDrawreason()))
//			{
//				map.put("jkhtbh",form.getBodyCardNumber()+map.get("buyhousedate"));
//			}else if("011".equals(form.getDrawreason()))
//			{
//				map.put("jkhtbh",form.getBodyCardNumber()+map.get("buyhousedate"));
//			}
//			//存入提取原因
//			map.put("tqyy", form.getTqyy());
//			//收款单位银行账号
//			map.put("payeebankacc0", form.getSkdwzh());
//			//收款单位名称
//			map.put("payeebankaccnm0", form.getSkzhmc());
//			//收款开户银行
//			map.put("payeebankname", form.getSkyh());
//			map.put("drawreason", form.getDrawreason());
//			map.put("jkhtbh",form.getDrawreasoncode1());
//			map.put("payeebankcode",form.getPayerbankcode());
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YWBLZCTQTJ.txt", map, req);
//			log.debug("发往中心报文："+xml);
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158031");
//
//			log.debug("中心下传报文："+rexml);
//			
//			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLZCTQTJ.txt", rexml, req);
//			log.debug("解析报文MAP："+resultMap3);			
			BeanUtil.transMap2Bean(resultMap1, app01022Result);
			
			
//			if(!"0".equals(resultMap3.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap3.get("recode"));
//				modelMap.put("msg", resultMap3.get("msg"));
//				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
//				return modelMap;
//			}
			
			resultMap.putAll(map);
			resultMap.putAll(resultMap1);
			resultMap.putAll(resultMap4);
			resultMap.put("porttype", resultMap2.get("porttype"));
			resultMap.put("buyhousedate", map.get("buyhousedate1"));
//			resultMap.putAll(resultMap3);
		}	
		System.out.println("===开始存数据库");
		
		ApplyPP applyPP = new ApplyPP();
		//TODO 若以下部分（resultMap.get("XXX")）在每个存数据库内的变量都是一样的，则可直接写在ApplyPP中，以免代码太过冗余
		resultMap.put("_ACCNUM", resultMap.get("accnum"));
		//TODO 单位账号
		resultMap.put("_UNITACCNUM", "");
		resultMap.put("_UNITACCNAME", resultMap.get("dwmc"));
		resultMap.put("_ACCNAME", form.getXingming());
		resultMap.put("_DEPUTYIDCARDNUM", form.getBodyCardNumber());
		resultMap.put("_SENDOPERID", form.getBodyCardNumber());
		resultMap.put("zjhm", form.getBodyCardNumber());
		resultMap.put("payeebankaccnm0", form.getSkzhmc());
		resultMap.put("payeebankacc0", form.getSkdwzh());
		resultMap.put("payeebankname", form.getSkyh());
		resultMap.put("payeebankcode", form.getPayerbankcode());
		resultMap.put("selfflag", "1");
		//resultMap.put("porttype", form.getPorttype());
		int seqno  = 0;
		if(!CommonUtil.isEmpty(form.getSeqno()))
		{
			seqno = Integer.parseInt(form.getSeqno());
		}else
		{
			seqno = RspServerUtil.getSEQ("INSTANCE_SEQ.nextval");
		}
		resultMap.put("_IS", -seqno);

		try {
			//String flag = "22"+form.getCenterId();
			String flag = "22"+form.getTqyy()+form.getCenterId();
			HashMap<String,String> temp = applyPP.exec(resultMap,form.getApplyid(),flag);
			if("false".equals(temp.get("recode")))
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", temp.get("msg"));
				log.info(Constants.LOG_HEAD+"appApi01022 end.");
				return modelMap;
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("applayid",temp.get("msg"));
			log.info(Constants.LOG_HEAD+"appApi01022 end.");
			return modelMap;
		} catch (Exception e) {
			modelMap.clear();
			//TODO 存数据库异常错误码
			modelMap.put("recode", "999999");
			modelMap.put("msg", "正常提取存数据库异常");
			log.error("正常提取存数据库异常："+e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
		
	}

	public static void main(String[] args) throws Exception {
		String shangchuan = "{\"owncertinum\":\"710000198001209863\",\"houseaddr\":\"JJ经历过\",\"matename\":\"\",\"rentdate\":\"2017-05-18\",\"relation\":\"01\",\"drawreasoncode2\":\"710000198001209863租房2017\",\"housecity\":\"08981306\",\"buyhousename\":\"王五11\",\"inputamt\":\"15000.00\",\"owncertitype\":\"01\",\"rentamt\":\"56655\",\"matecertitype\":\"\",\"matecertinum\":\"\"}";
		System.out.println(shangchuan);
		JSONObject obj = JSONObject.fromObject(shangchuan);
		System.out.println(obj.get("rentdate"));
		if(!CommonUtil.isEmpty(obj.get("rentdate")))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();//获取当前时间    
			Calendar calendar = Calendar.getInstance();    
			calendar.setTime(date);    
			calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
			//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
			Date d = calendar.getTime();//获取一年前的时间，或者一个月前的时间    
			long nowtime = sdf.parse(sdf.format(date)).getTime();
			long lastyeartime = sdf.parse(sdf.format(d)).getTime();
			long rentdatetime = sdf.parse(obj.get("rentdate").toString()).getTime();
			if(rentdatetime<lastyeartime)
			{
				System.out.println("1::::::::"+rentdatetime);
			}
			if(rentdatetime>nowtime)
			{
				System.out.println("1::::::::"+rentdatetime);
			}
		}
	}
}
