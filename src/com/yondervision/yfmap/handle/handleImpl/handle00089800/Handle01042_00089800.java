package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

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
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 公积金还贷查询-页面2
 * @author LFX
 *
 */
public class Handle01042_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01042 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

//		if(CommonUtil.isEmpty(form.getBodyCardNumber()) || CommonUtil.isEmpty(form.getTqyy()) ){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "上传参数不可为空。");
//			log.error("上传参数不可为空。");
//			return modelMap;
//		}
		long time1 = System.currentTimeMillis();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		
		AppApi01025Result app01042Result = new AppApi01025Result();
		
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
			resultMap.put("jklx",resultMap1.get("porttype"));
			
			long time2 = System.currentTimeMillis();
			System.out.println("第一次请求"+(time2-time1));
			map.put("stepseqno", "8");

//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
//			log.debug("发往中心报文-查询提取原因："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
//			log.debug("中心返回报文-查询提取原因："+rexml);
//			
//			HashMap resultMap9 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
//			if(!"0".equals(resultMap9.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap9.get("recode"));
//				modelMap.put("msg", resultMap9.get("msg"));
//				log.error("中心返回报文 状态recode :"+resultMap9.get("recode")+"  ,  描述msg : "+resultMap9.get("msg"));
//				return modelMap;
//			}
//			if(resultMap9.containsKey("counts"))
//			{
//				if(Integer.parseInt(resultMap9.get("counts").toString())<=0)
//				{
//					modelMap.clear();
//					modelMap.put("recode","999999");
//					modelMap.put("msg", "无公积金贷款记录！");
//					log.error("中心返回报文 状态recode :"+resultMap9.get("recode")+"  ,  描述msg : "+resultMap9.get("msg"));
//					return modelMap;
//				}
//			}
			
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKCX.txt", map, req);
			log.debug("发往中心报文-查询贷款信息："+xml);
			
			 rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158029");
			log.debug("中心返回报文-查询贷款信息："+rexml);
			
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKCX.txt", rexml, req);
			if(!"0".equals(resultMap4.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
				return modelMap;
			}	
			if(Integer.parseInt(resultMap4.get("num").toString())>1)
			{
				if(resultMap4.containsKey("actmp1024"))
				{
					log.error("中心返回报文 actmp1024  描述msg : "+resultMap4.get("actmp1024"));
					if(CommonUtil.isEmpty(resultMap4.get("actmp1024")))
					{
						modelMap.clear();
						modelMap.put("recode","999999");
						modelMap.put("msg", "无公积金贷款记录，无法办理此业务！");
						log.error("中心返回报文 状态recode :999999   描述msg :actmp1024 无公积金贷款记录，无法办理此业务！");
						return modelMap;
					}
				}
			}else
			{
				if(resultMap4.containsKey("jkhtbh"))
				{
					if(CommonUtil.isEmpty(resultMap4.get("jkhtbh")))
					{
						modelMap.clear();
						modelMap.put("recode","999999");
						modelMap.put("msg", "无公积金贷款记录，无法办理此业务！");
						log.error("中心返回报文 状态recode :999999   描述msg :jkhtbh 无公积金贷款记录，无法办理此业务！");
						return modelMap;
					}
				}
			}
			
			long time3 = System.currentTimeMillis();
			System.out.println("第二次请求"+(time3-time2));
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
			resultMap.put("drawreason", resultMap2.get("drawreason"));
			map.put("drawreason", resultMap2.get("drawreason"));
			
			long time4 = System.currentTimeMillis();
			System.out.println("第三次请求"+(time4-time3));
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
				modelMap.put("msg", "查询3，交易码【119002】，查询单位账号为空。");
				log.error("查询3，交易码【119002】，查询单位账号为空。");
				return modelMap;
			} 
			//冻结
			if("1".equals(resultMap3.get("frzflag"))){	
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
			
			long time5 = System.currentTimeMillis();
			System.out.println("第四次请求"+(time5-time4));
			
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKCX.txt", map, req);
//			log.debug("发往中心报文-查询贷款信息："+xml);
//			
//			 rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158029");
//			log.debug("中心返回报文-查询贷款信息："+rexml);
//			
//			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKCX.txt", rexml, req);
//			if(!"0".equals(resultMap4.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap4.get("recode"));
//				modelMap.put("msg", resultMap4.get("msg"));
//				log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
//				return modelMap;
//			}	
			long time6 = System.currentTimeMillis();
			System.out.println("第五次请求"+(time6-time5));
			//个人账号状态
			resultMap.put("indiaccstate", resultMap3.get("indiaccstate"));
			//姓名
			resultMap.put("xingming", resultMap3.get("xingming"));
			//证件类型
			resultMap.put("zjlx", resultMap3.get("zjlx"));
			//个人账号余额
			resultMap.put("grzhye", resultMap3.get("grzhye"));
			BigDecimal grzhye=new BigDecimal(resultMap3.get("grzhye").toString());
			BigDecimal stpayamt=new BigDecimal(resultMap3.get("stpayamt").toString());
			BigDecimal frzamt=new BigDecimal(resultMap3.get("frzamt").toString());
			BigDecimal kyye = grzhye.subtract(stpayamt).subtract(frzamt);
			//可用余额
			resultMap.put("kyye", kyye.toString());
			//缴至年月
			resultMap.put("lpaym", resultMap3.get("lpaym"));
			//证件号码
			resultMap.put("zjhm", resultMap3.get("zjhm"));
			//单位证明
			resultMap.put("dwzh", resultMap3.get("dwzh"));
			resultMap.put("sjhm", resultMap3.get("sjhm"));
			
			if(Integer.parseInt(resultMap4.get("num").toString())>1)
			{
				resultMap.put("loanaccnum", resultMap4.get("actmp1024"));
			}else
			{
				resultMap.put("loanaccnum", resultMap4.get("jkhtbh"));
			}
			BeanUtil.transMap2Bean(resultMap, app01042Result);
		}
		app01042Result.setIndiaccstate(PropertiesReader.getProperty("yingshe.properties","indiaccstate"+app01042Result.getIndiaccstate()+form.getCenterId()));
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01042"+form.getCenterId()+".result", app01042Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		List<TitleInfoBean> banklist = new ArrayList<TitleInfoBean>();
		String yhbm_temp[] = app01042Result.getLoanaccnum().split(",");
		for(int i=0;i<yhbm_temp.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String t1[] = yhbm_temp[i].split(":");
			if(t1.length>1)
			{
				t.setTitle(t1[0].replaceAll("\"", ""));
				t.setInfo(t1[1].replaceAll("\"", ""));
			}else
			{
				t.setTitle(t1[0].replaceAll("\"", ""));
				t.setInfo(t1[0].replaceAll("\"", ""));
			}
			
			banklist.add(t);
		}
		if(!CommonUtil.isEmpty(form.getApplyid()))
		{
			ApplyPP app = new ApplyPP();
			DataPool d = app.queryRSP002(form.getApplyid());
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("drawreason", app01042Result.getDrawreason());
			modelMap.put("porttype", app01042Result.getJklx());
			//合同号列表
			modelMap.put("loanaccnum",banklist );
			modelMap.put("zjlx", app01042Result.getZjlx());
			modelMap.put("result", appapi00101Result);
			modelMap.put("sjhm", app01042Result.getSjhm());
			modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
			log.info(Constants.LOG_HEAD+"appApi01037 end.");
			return modelMap;
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("drawreason", app01042Result.getDrawreason());
		modelMap.put("porttype", app01042Result.getJklx());
		//合同号列表
		modelMap.put("loanaccnum",banklist );
		modelMap.put("zjlx", app01042Result.getZjlx());
		modelMap.put("result", appapi00101Result);
		modelMap.put("sjhm", app01042Result.getSjhm());
		modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
		log.info(Constants.LOG_HEAD+"appApi01042 end.");
		long time7 = System.currentTimeMillis();
		System.out.println("第六次结束"+(time7-time1));
		return modelMap;
	}
	
	
}
