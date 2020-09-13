package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.ydyd.pool.DataPool;
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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01023Result;
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 销户提取-反显信息获取-海口
 * @author Administrator
 *
 */
public class Handle01025_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
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
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		
		AppApi01025Result app01025Result = new AppApi01025Result();
		
		// 结果map
		HashMap resultMap = new HashMap();
		
		if(Constants.method_BSP.equals(send)){

			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
			
			
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
			map.put("grzh", resultMap1.get("grzh"));
			resultMap.put("grzh", resultMap1.get("grzh"));
			
			/* 第二次通信   查询提取原因*/
			map.put("stepseqno", "2");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询提取原因："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询提取原因："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap2.get("recode")+"  ,  描述msg : "+resultMap2.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap2.get("drawreason"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询2，交易码【158030】，查询提取原因为空。");
				log.error("查询2，交易码【158030】，查询提取原因为空。");
				return modelMap;
			}
			if(!CommonUtil.isEmpty(resultMap2.get("drawreason"))){	
				if(resultMap2.get("drawreason").equals("112"))
				{
					resultMap.put("drawreason", "异地转出销户");
				}else{
					resultMap.put("drawreason", "销户");
				}
				
			}else
			{
				resultMap.put("drawreason", resultMap2.get("drawreason"));
			}
			resultMap.put("drawreason_code", resultMap2.get("drawreason"));
			map.put("drawreason", resultMap2.get("drawreason"));
			
			
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
			resultMap.put("sjhm", resultMap3.get("sjhm"));
			BigDecimal grzhye=new BigDecimal(resultMap3.get("grzhye").toString());
			BigDecimal stpayamt=new BigDecimal(resultMap3.get("stpayamt").toString());
			BigDecimal frzamt=new BigDecimal(resultMap3.get("frzamt").toString());
			BigDecimal clsaccint=new BigDecimal(resultMap3.get("clsaccint").toString());
			BigDecimal kyye = grzhye.subtract(stpayamt).subtract(frzamt);
			//BigDecimal tqjehj = grzhye.add(clsaccint);
			resultMap.put("kyye", kyye.toString());
			//resultMap.put("tqjehj", tqjehj.toString());
			resultMap.put("frzflag", resultMap3.get("frzflag"));
			resultMap.put("lpaym", resultMap3.get("lpaym"));
			resultMap.put("zjhm", resultMap3.get("zjhm"));
			resultMap.put("dwzh", resultMap3.get("dwzh"));
			map.put("dwzh", resultMap3.get("dwzh"));
				
			/* 第4次通信   查询*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLDWXXCX.txt", map, req);
			log.debug("发往中心报文-单位信息查询："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119001");
			log.debug("中心返回报文-单位信息查询："+rexml);
			
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLDWXXCX.txt", rexml, req);
			if(!"0".equals(resultMap4.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
				return modelMap;
			}
			// 如果单位名称存在存入结果map
			if( resultMap4.containsKey("dwmc")){
				resultMap.put("dwmc",resultMap4.get("dwmc"));
			} else {
				resultMap.put("dwmc","");
			}
			
			/* 第5次通信  查询公积金贷款*/
			//2017-05-19
			//map.put("stepseqno", "7");
			map.put("stepseqno", "8");
			String xml5 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询公积金贷款："+xml5);
			
			// 通信
			String rexml5 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml5, "158030");
			log.debug("中心返回报文-查询公积金贷款："+rexml5);
			
			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml5, req);
			if(!"0".equals(resultMap5.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap5.get("recode"));
				modelMap.put("msg", resultMap5.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap5.get("recode")+"  ,  描述msg : "+resultMap5.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap5.get("counts"))){	
				resultMap.put("isloanflag", "0");
				resultMap.put("counts", "0");
			} else {
				int counts = Integer.parseInt((String)resultMap5.get("counts"));
				resultMap5.put("counts", resultMap1.get("counts"));
				if(counts>0){
					resultMap.put("isloanflag", "是");	// 有贷款
				}else{
					resultMap.put("isloanflag", "否");
				}
			}
			
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
				log.error("中心返回报文 状态recode :"+resultMap2.get("recode")+"  ,  描述msg : "+resultMap2.get("msg"));
				return modelMap;
			}	
			if(CommonUtil.isEmpty(resultMap6.get("intamt"))){	
				resultMap.put("intamt", "0");
			} else {
				resultMap.put("intamt", resultMap6.get("intamt"));
			}
			BigDecimal intamt=new BigDecimal(resultMap.get("intamt").toString());
			BigDecimal tqjehj = grzhye.add(intamt);
			resultMap.put("tqjehj", tqjehj.toString());
			
//			/*第7次通讯，查询历史收款账号*/
//			map.put("stepseqno", "6");
//			map.put("zjhm", form.getIdcardNumber());
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
//			log.debug("发往中心报文-查询历史收款账："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
//			log.debug("中心返回报文-查询历史收款账："+rexml);
//			
//			HashMap resultMap7 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
//			if(!"0".equals(resultMap7.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap7.get("recode"));
//				modelMap.put("msg", resultMap7.get("msg"));
//				log.error("中心返回报文 状态recode :"+resultMap7.get("recode")+"  ,  描述msg : "+resultMap7.get("msg"));
//				return modelMap;
//			}	
			/**
			 * TODO 传回来的信息变量名取名，，，
			 * 【bankname】赋值给【页面payeebankname】
			 * 【accname】赋值给【页面payeebankaccnm0】
	         * 【页面bankacc0】赋值给【页面payeebankacc0】
	         * 【linkrow】赋值给【页面linkrow】
	         * 【bankcode】赋值给【页面payerbankcode】
			 */
			modelMap.clear();
			//modelMap.put("app01025Result", app01025Result);
			BeanUtil.transMap2Bean(resultMap, app01025Result);
			app01025Result.setIndiaccstate(PropertiesReader.getProperty("yingshe.properties","indiaccstate"+app01025Result.getIndiaccstate()+form.getCenterId()));
			app01025Result.setFrzflag(PropertiesReader.getProperty("yingshe.properties","frzflag"+app01025Result.getFrzflag()+form.getCenterId()));
			app01025Result.setZjlx(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01025Result.getZjlx()+form.getCenterId()));
			
		}
		
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01025"+form.getCenterId()+".result", app01025Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		if(!CommonUtil.isEmpty(form.getApplyid()))
		{
			ApplyPP app = new ApplyPP();
			DataPool d = app.queryRSP002(form.getApplyid());
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("drawreason", app01025Result.getDrawreason_code());
			modelMap.put("result", appapi00101Result);
			modelMap.put("sjhm", app01025Result.getSjhm());
			modelMap.put("tqyy", d.get("tqyy"));
			modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
			log.info(Constants.LOG_HEAD+"appApi01037 end.");
			return modelMap;
		}
		
		//modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("drawreason", app01025Result.getDrawreason_code());
		modelMap.put("result", appapi00101Result);
		modelMap.put("sjhm", app01025Result.getSjhm());
		modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
		log.info(Constants.LOG_HEAD+"appApi01025 end.");
		
		return modelMap;
	}
	
}
