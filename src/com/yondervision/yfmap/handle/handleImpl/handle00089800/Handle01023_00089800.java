package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.yondervision.yfmap.result.haikou.AppApi01023Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 业务办理  提取 通用-查询个人信息-海口
 * @author Administrator
 *
 */
public class Handle01023_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01023 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		long aaaa = System.currentTimeMillis();
		if(CommonUtil.isEmpty(form.getBodyCardNumber()) || CommonUtil.isEmpty(form.getTqyy()) ){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "上传参数不可为空。");
			log.error("上传参数不可为空。");
			return modelMap;
		}
		
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		
		AppApi01023Result app01023Result = new AppApi01023Result();
		List<TitleInfoBean>  cityList= new ArrayList<TitleInfoBean>();
		// 结果map
		HashMap resultMap = new HashMap();
		
		if(Constants.method_BSP.equals(send)){
						
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			form.setFlag("1");
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
			resultMap.put("drawreason", resultMap2.get("drawreason"));
			map.put("drawreason", resultMap2.get("drawreason"));
			
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
			map.put("grzh", resultMap1.get("grzh"));
			if(CommonUtil.isEmpty(resultMap4.get("dwzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询4，交易码【119002】，查询单位账号为空。");
				log.error("查询4，交易码【119002】，查询单位账号为空。");
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
			resultMap.put("sjhm", resultMap4.get("sjhm"));
			map.put("dwzh", resultMap4.get("dwzh"));
				
			if(!CommonUtil.isEmpty(form.getDrawreasoncode1()))
			{
				/* 第5次通信   查询提取信息*/
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQDHTBS.txt", map, req);
				log.debug("发往中心报文-查询提取信息："+xml);
				
				// 通信
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158000");
				log.debug("中心返回报文-查询提取信息："+rexml);
				
				HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQDHTBS.txt", rexml, req);
				if(!"0".equals(resultMap5.get("recode"))){				
					modelMap.clear();
					modelMap.put("recode", resultMap5.get("recode"));
					modelMap.put("msg", resultMap5.get("msg"));
					log.error("中心返回报文 状态recode :" + resultMap5.get("recode")+"  ,  描述msg : " + resultMap5.get("msg"));
					return modelMap;
				}
				//公贷商贷使用
				resultMap5.put("monthrepayamt", resultMap5.get("yrepayamt"));
				resultMap.putAll(resultMap5);
				resultMap.put("drawreasoncode2", resultMap.get("drawreasoncode1"));
			}

			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_CXJG.txt", map, req);
			log.debug("发往中心报文-租房城市："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149372");
			log.debug("中心返回报文-租房城市："+rexml);
			
			HashMap resultMap7 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_CXJG.txt", rexml, req);
			if(!"0".equals(resultMap7.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap7.get("recode"));
				modelMap.put("msg", resultMap7.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap7.get("recode")+"  ,  描述msg : "+resultMap7.get("msg"));
				return modelMap;
			}	
			//resultMap.put("actmp1024_ls", resultMap7.get("actmp1024"));
			// 解析城市 
			 String str = resultMap7.get("actmp1024").toString();
			if(CommonUtil.isEmpty(str)){
				str = "";
			}
			str = "{"+str+"}";
			JSONObject jsonObject = JSONObject.fromObject(str);
			Iterator iterator = jsonObject.keys();
			
			TitleInfoBean tt = null;
			while(iterator.hasNext()){        	
				String key = (String) iterator.next();        
				String value = jsonObject.getString(key);
				tt = new TitleInfoBean();
				tt.setTitle(key);
				tt.setInfo(value);
				cityList.add(tt);
				//System.out.println(key+"===="+value);
			}
			resultMap.put("zjhm", resultMap4.get("zjhm"));
			if(!CommonUtil.isEmpty(resultMap.get("buyhouseamt")))
			{
				resultMap.put("rentamt", resultMap.get("buyhouseamt"));
			}
			if(!CommonUtil.isEmpty(resultMap.get("buyhousedate")))
			{
				resultMap.put("rentdate", resultMap.get("buyhousedate"));
			}
			BeanUtil.transMap2Bean(resultMap, app01023Result);
		}
		//通过依据号返回参数 titleInfo
		List<TitleInfoBean> resultAll = new ArrayList<TitleInfoBean>();
		Iterator iter = resultMap.entrySet().iterator();
		while (iter.hasNext()) {
			TitleInfoBean t = new TitleInfoBean();
			Map.Entry entry = (Map.Entry) iter.next();
			t.setTitle(entry.getKey().toString());
			if(entry.getValue()!=null)t.setInfo(entry.getValue().toString());
			//System.out.println(entry.getKey().toString()+"&&&&"+entry.getValue().toString());
			resultAll.add(t);
		}
		app01023Result.setZjlx_code(app01023Result.getZjlx());
		app01023Result.setIndiaccstate(PropertiesReader.getProperty("yingshe.properties","indiaccstate"+app01023Result.getIndiaccstate()+form.getCenterId()));
		app01023Result.setZjlx(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01023Result.getZjlx()+form.getCenterId()));
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01023"+form.getCenterId()+".result", app01023Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		app01023Result.setMatecertitype_code(app01023Result.getMatecertitype());
		app01023Result.setOwncertitype_code(app01023Result.getOwncertitype());
		if(!CommonUtil.isEmpty(app01023Result.getMatecertitype()))app01023Result.setMatecertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01023Result.getMatecertitype()+form.getCenterId()));
		if(!CommonUtil.isEmpty(app01023Result.getOwncertitype()))app01023Result.setOwncertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01023Result.getOwncertitype()+form.getCenterId()));
		
		if(!CommonUtil.isEmpty(form.getApplyid()))
		{
			ApplyPP app = new ApplyPP();
			DataPool d = app.queryRSP002(form.getApplyid());
			List<TitleInfoBean> resultAll1 = new ArrayList<TitleInfoBean>();
			Iterator iter1 = d.entrySet().iterator();
			while (iter1.hasNext()) {
				TitleInfoBean t = new TitleInfoBean();
				Map.Entry entry = (Map.Entry) iter1.next();
				t.setTitle(entry.getKey().toString());
				if(entry.getValue()!=null)t.setInfo(entry.getValue().toString());
				//System.out.println(entry.getKey().toString()+"&&&&"+entry.getValue().toString());
				resultAll1.add(t);
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("buyhousename", app01023Result.getBuyhousename());
			modelMap.put("owncertinum", app01023Result.getOwncertinum());
			modelMap.put("owncertitype", app01023Result.getOwncertitype_code());
			modelMap.put("matename", app01023Result.getMatename());
			modelMap.put("matecertinum", app01023Result.getMatecertinum());
			modelMap.put("matecertitype", app01023Result.getMatecertitype_code());
			modelMap.put("drawreason", app01023Result.getDrawreason());
			modelMap.put("result", appapi00101Result);
			modelMap.put("result_all", resultAll1);
			modelMap.put("sjhm", app01023Result.getSjhm());
			modelMap.put("zjlx", app01023Result.getZjlx_code());
			modelMap.put("cityList", cityList);
			modelMap.put("tqyy", d.get("tqyy"));
			modelMap.put("ctlflag", d.get("ctlflag"));
			modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
			log.info(Constants.LOG_HEAD+"appApi01037 end.");
			return modelMap;
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		//modelMap.put("result1", app01023Result);
		modelMap.put("buyhousename", app01023Result.getBuyhousename());
		modelMap.put("owncertinum", app01023Result.getOwncertinum());
		modelMap.put("owncertitype", app01023Result.getOwncertitype_code());
		modelMap.put("matename", app01023Result.getMatename());
		modelMap.put("matecertinum", app01023Result.getMatecertinum());
		modelMap.put("matecertitype", app01023Result.getMatecertitype_code());
		modelMap.put("drawreason", app01023Result.getDrawreason());
		modelMap.put("result", appapi00101Result);
		modelMap.put("result_all", resultAll);
		modelMap.put("sjhm", app01023Result.getSjhm());
		modelMap.put("zjlx", app01023Result.getZjlx_code());
		modelMap.put("cityList", cityList);
		modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
		long aaaa1 = System.currentTimeMillis();
		log.info(Constants.LOG_HEAD+"appApi01023 end.time="+(aaaa1-aaaa));
		
		
		return modelMap;
	}

}
