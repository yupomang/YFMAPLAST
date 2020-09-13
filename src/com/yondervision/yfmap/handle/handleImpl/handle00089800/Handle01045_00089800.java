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
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 公积金还贷查询-页面3-办理
 * @author LFX
 *
 */
public class Handle01045_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01025 start.");
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
//		form.setSkzhmc("王小一");
//		form.setSkdwzh("182708222228");
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();

		
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
			
			map.put("stepseqno", "7");
			map.put("bankcode", form.getPayerbankcode());
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询个人账号："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询个人账号："+rexml);
			
			HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
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
			map.put("dwzh", resultMap4.get("dwzh"));
			
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
			BigDecimal grzhye=new BigDecimal(resultMap4.get("grzhye").toString());
			BigDecimal stpayamt=new BigDecimal(resultMap4.get("stpayamt").toString());
			BigDecimal frzamt=new BigDecimal(resultMap4.get("frzamt").toString());
			BigDecimal kyye = grzhye.subtract(stpayamt).subtract(frzamt);
			resultMap.put("usebal", kyye.toString());
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
			resultMap.putAll(map);
			//提取金额
			resultMap.put("inputamt", resultMap2.get("drawamt"));
			resultMap.put("drawamt", form.getSqje());
			//进行第3次通讯，网厅-正常提取15031
			/**
			 * 第3次通讯，正常提取
			 */
//			//上送提取金额
//			map.put("drawamt",form.getSqje());
//			//收款单位银行账号
//			map.put("payeebankacc0", form.getSkdwzh());
//			//收款单位名称
//			map.put("payeebankaccnm0", form.getSkzhmc());
//			//收款开户银行
//			map.put("payeebankname", form.getSkyh());
//			//多合同标识
//			map.put("procode", resultMap5.get("procode"));
//			if(!CommonUtil.isEmpty(resultMap5.get("procode")))
//			{
//				if(!CommonUtil.isEmpty(drawreasoncode1))
//				{
//					map.put("firstflag", "1");
//				}else
//				{
//					map.put("firstflag", "2");
//				}
//				
//			}
//			//证件类型
//			map.put("owncertitype", form.getOwncertitype());
//			//证件号码
//			map.put("owncertinum", form.getOwncertinum());
//			//姓名
//			map.put("buyhousename", form.getBuyhousename());
//			//证件类型
//			map.put("matecertitype", form.getMatecertitype());
//			//证件号码
//			map.put("matecertinum", form.getMatecertinum());
//			//姓名
//			map.put("matename", form.getMatename());
//			System.out.println("procode"+map.get("procode"));
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLZCTQTJ.txt", map, req);
//			log.debug("发往中心报文-正常提取："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158031");
//			log.debug("中心返回报文-正常提取："+rexml);
//			
//			HashMap resultMap6 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLZCTQTJ.txt", rexml, req);
//			
//			if(!"0".equals(resultMap6.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap6.get("recode"));
//				modelMap.put("msg", resultMap6.get("msg"));
//				log.error("中心返回报文 状态recode :"+resultMap6.get("recode")+"  ,  描述msg : "+resultMap6.get("msg"));
//				return modelMap;
//			}	
			resultMap.put("porttype", resultMap6.get("porttype"));
			resultMap.put("instcode", resultMap4.get("accinstcode"));
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
			resultMap.put("buyhousetype", "1");
			resultMap.put("selfflag", "1");
			resultMap.put("drawreasoncode3", form.getDrawreasoncode1());
			resultMap.put("drawreasoncode2", form.getDrawreasoncode1());
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
				String flag = "45"+form.getCenterId();
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
	
	
}
