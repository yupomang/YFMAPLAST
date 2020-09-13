package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.ApplyPP;
import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 业务办理-销户提取提交
 */
public class Handle01026_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01026 start.");
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
		form.setSkyh(aes.decrypt(form.getSkyh()));
		form.setSkzhmc(aes.decrypt(form.getSkzhmc()));
		//测试
		//form.setSkzhmc("王小一");
		//form.setSkdwzh("182708222228");
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
	
		// 结果map
		HashMap resultMap = new HashMap();
		//存pool数据
		HashMap mapPool =  BeanUtil.transBean2Map(form1);
		if(Constants.method_BSP.equals(send)){

			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
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
				if(phoneNumber.length()!=11)
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "手机号不合法，请到柜面变更维护手机号！");
					log.error("中心返回报文 状态recode :"+modelMap.get("recode")+"  ,  描述msg : "+modelMap.get("msg"));
					return modelMap;
				}
			}
			
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
			
			map.put("grzh", resultMap1.get("grzh"));
			resultMap.put("grzh", resultMap1.get("grzh"));
			
			//2017-07-24 核心接口修改 精确个人查询信息
			map.put("summarycode", "1228");
			map.put("grzh", "");
			/* 第3次通信   个人信息查询*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLGRXXCX.txt", map, req);
			log.debug("发往中心报文-个人信息查询："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
			log.debug("中心返回报文-个人信息查询："+rexml);
			
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLGRXXCX.txt", rexml, req);
			if(!"0".equals(resultMap3.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
				return modelMap;
			}
			map.put("grzh", resultMap1.get("grzh"));
			if(CommonUtil.isEmpty(resultMap3.get("dwzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询4，交易码【119002】，查询单位账号为空。");
				log.error("查询4，交易码【119002】，查询单位账号为空。");
				return modelMap;
			} 
			//冻结
			if("1".equals(resultMap3.get("frzflag"))||"2".equals(resultMap3.get("frzflag"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该身份证姓名已被全部冻结，不能提取！");
				log.error("查询3，交易码【119002】，该身份证姓名已被全部冻结，不能提取！");
				return modelMap;
			}
			//黑名单
			if("06".equals(resultMap3.get("crelevel"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该身份证姓名已被加入黑名单，不能提取！");
				log.error("查询3，交易码【119002】，该身份证姓名已被加入黑名单，不能提取。");
				return modelMap;
			}
			//非停缴
			if(!"1".equals(resultMap3.get("indiaccstate"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "非停缴状态，不能办理此业务！");
				log.error("查询3，交易码【119002】，非停缴状态，不能办理此业务！");
				return modelMap;
			}
			double a = 0.00; 
			BigDecimal data = new BigDecimal(a); 
			BigDecimal data1 = new BigDecimal(resultMap3.get("increintaccu").toString()); 
			BigDecimal data2 = new BigDecimal(resultMap3.get("grzhye").toString()); 
			BigDecimal data3 = new BigDecimal(resultMap3.get("keepintaccu").toString()); 
			int result1 = data.compareTo(data1) ;
			int result2 = data.compareTo(data2) ;
			int result3 = data.compareTo(data3) ;
			if(result1==0&&result2==0&&result3==0)
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "余额为零，不能办理销户提取！");
				log.error("查询3，交易码【119002】，余额为零，不能办理销户提取。");
				return modelMap;
			}
			resultMap.put("indiaccstate", resultMap3.get("indiaccstate"));
			resultMap.put("xingming", resultMap3.get("xingming"));
			resultMap.put("zjlx", resultMap3.get("zjlx"));
			resultMap.put("grzhye", resultMap3.get("grzhye"));
			resultMap.put("kyye", Double.parseDouble(resultMap3.get("grzhye").toString())-Double.parseDouble(resultMap3.get("stpayamt").toString())-Double.parseDouble(resultMap3.get("frzamt").toString())+"");
			resultMap.put("tqjehj", Double.parseDouble(resultMap3.get("grzhye").toString())+Double.parseDouble(resultMap3.get("clsaccint").toString())+"");
			resultMap.put("frzflag", resultMap3.get("frzflag"));
			resultMap.put("lpaym", resultMap3.get("lpaym"));
			resultMap.put("zjhm", resultMap3.get("zjhm"));
			resultMap.put("dwzh", resultMap3.get("dwzh"));
			map.put("dwzh", resultMap3.get("dwzh"));
				
			/* 第5次通信  查询公积金贷款*/
			map.put("stepseqno", "8");

			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询提取原因："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询提取原因："+rexml);
			
			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap5.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap5.get("recode"));
				modelMap.put("msg", resultMap5.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap5.get("recode")+"  ,  描述msg : "+resultMap5.get("msg"));
				return modelMap;
			}
			if(resultMap5.containsKey("counts"))
			{
				if(CommonUtil.isEmpty(resultMap5.get("counts"))){	
				} else {
					int counts = Integer.parseInt((String)resultMap5.get("counts"));
					if(counts>0){
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "个人存在公积金贷款，不能办理此业务！");
						log.error(":个人存在公积金贷款，不能办理此业务！");
						return modelMap;
					}
				}
				if(Integer.parseInt(resultMap5.get("counts").toString())>0)
				{
					resultMap.put("isloanflag", "1");	// 有贷款
				}else
				{
					resultMap.put("isloanflag", "0");
				}
			}
			
//			
			/* 第6次通信   查询销户利息*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLXHLX.txt", map, req);
			log.debug("发往中心报文-查询销户利息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158010");
			log.debug("中心返回报文-查询销户利息："+rexml);
			
			HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLXHLX.txt", rexml, req);
			if(!"0".equals(resultMap6.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap6.get("recode"));
				modelMap.put("msg", resultMap6.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap6.get("recode")+"  ,  描述msg : "+resultMap6.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap6.get("intamt"))){	
				resultMap.put("intamt", "0");
			} else {
				resultMap.put("intamt", resultMap6.get("intamt"));
			}
			BigDecimal grzhye=new BigDecimal(resultMap3.get("grzhye").toString());
			BigDecimal intamt=new BigDecimal(resultMap.get("intamt").toString());
			BigDecimal tqjehj = grzhye.add(intamt);
			map.put("drawamt", tqjehj.toString());
			
			BigDecimal stpayamt=new BigDecimal(resultMap3.get("stpayamt").toString());
			BigDecimal frzamt=new BigDecimal(resultMap3.get("frzamt").toString());
			BigDecimal kyye = grzhye.subtract(stpayamt).subtract(frzamt);
			map.put("usebal", kyye.toString());
			
			/**
			 * 第七次通讯，销户提取
			 */
			//存入提取原因
			map.put("tqyy", form.getTqyy());
			//收款单位银行账号
			map.put("payeebankacc0", form.getSkdwzh());
			//收款单位名称
			map.put("payeebankaccnm0", form.getSkzhmc());
			//收款开户银行
			map.put("payeebankname", form.getSkyh());
			map.put("drawreason", form.getDrawreason());
			map.put("payeebankcode",form.getPayerbankcode());
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_XHTQ.txt", map, req);
//			log.debug("发往中心报文-销户提取："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158032");
//			log.debug("中心返回报文-销户提取："+rexml);
//			
//			HashMap resultMap7 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_XHTQ.txt", rexml, req);
//			
//			if(!"0".equals(resultMap7.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap7.get("recode"));
//				modelMap.put("msg", resultMap7.get("msg"));
//				log.error("中心返回报文 状态recode :"+resultMap7.get("recode")+"  ,  描述msg : "+resultMap7.get("msg"));
//				return modelMap;
//			}	
			
			/* 第8次通信  查询接口类型*/
			map.put("bankcode",form.getPayerbankcode());
			map.put("stepseqno", "7");

			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询提取原因："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询提取原因："+rexml);
			
			HashMap resultMap8 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap8.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap8.get("recode"));
				modelMap.put("msg", resultMap8.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap8.get("recode")+"  ,  描述msg : "+resultMap8.get("msg"));
				return modelMap;
			}
			map.putAll(resultMap);
			map.putAll(resultMap1);
			map.putAll(resultMap6);
			map.putAll(resultMap3);
//			map.putAll(resultMap7);
			map.put("porttype", resultMap8.get("porttype"));
			//保存前端传值，防止被查回来的数据覆盖
			map.put("grzh", resultMap1.get("grzh"));
			map.put("bankcode",form.getPayerbankcode());
			//存入提取原因
			map.put("tqyy", form.getTqyy());
			//收款单位银行账号
			map.put("payeebankacc0", form.getSkdwzh());
			//收款单位名称
			map.put("payeebankaccnm0", form.getSkzhmc());
			//收款开户银行
			map.put("payeebankname", form.getSkyh());
			map.put("drawreason", form.getDrawreason());
			map.put("payeebankcode",form.getPayerbankcode());
			map.put("drawamt", tqjehj.toString());
			map.put("instcode", resultMap3.get("accinstcode"));
			mapPool.putAll(map);
		}
		System.out.println("===开始存数据库");
		
		ApplyPP applyPP = new ApplyPP();
		//TODO 若以下部分（mapPool.get("XXX")）在每个存数据库内的变量都是一样的，则可直接写在ApplyPP中，以免代码太过冗余
		mapPool.put("_ACCNUM", mapPool.get("accnum"));
		//TODO 单位账号
		mapPool.put("_UNITACCNUM", "");
		mapPool.put("_UNITACCNAME", mapPool.get("dwmc"));
		mapPool.put("_ACCNAME", mapPool.get("xingming"));
		mapPool.put("_DEPUTYIDCARDNUM", mapPool.get("zjhm"));
		mapPool.put("_SENDOPERID", mapPool.get("zjhm"));
		int seqno  = 0;
		if(!CommonUtil.isEmpty(form.getSeqno()))
		{
			seqno = Integer.parseInt(form.getSeqno());
		}else
		{
			seqno = RspServerUtil.getSEQ("INSTANCE_SEQ.nextval");
		}
		mapPool.put("_IS", -seqno);
		//mapPool.put("_IS", -RspServerUtil.getSEQ("INSTANCE_SEQ.nextval"));
//		mapPool.put("_PORCNAME", "销户提取");
//		mapPool.put("_PROCID", "60021002");
//		mapPool.put("stepid", "36756");
//		mapPool.put("flowid", "343");
//		mapPool.put("counter", "1");
//		mapPool.put("selorgid", "08981302");
		mapPool.put("incint", resultMap.get("intamt").toString());
		mapPool.put("usebal", resultMap.get("kyye").toString());
		mapPool.put("indiaccstate1", resultMap.get("indiaccstate").toString());
		mapPool.put("selfflag", "1");
		//mapPool.put("porttype", resultMap.get("porttype").toString());
		try {
			String flag = "26"+form.getCenterId();
			HashMap<String,String> temp = applyPP.exec(mapPool,form.getApplyid(),flag);
			if("false".equals(temp.get("recode")))
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", temp.get("msg"));
				log.info(Constants.LOG_HEAD+"appApi01026 end.");
				return modelMap;
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("applayid",temp.get("msg"));
			log.info(Constants.LOG_HEAD+"appApi01026 end.");
			return modelMap;
		} catch (Exception e) {
			modelMap.clear();
			//TODO 存数据库异常错误码
			modelMap.put("recode", "999999");
			modelMap.put("msg", "销户提取存数据库异常");
			log.error("销户提取存数据库异常："+e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
	}

}
