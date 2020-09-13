package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.math.BigDecimal;
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
public class Handle01043_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01043 start.");
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

			String xml;
			String rexml;
			if(!CommonUtil.isEmpty(form.getDrawreasoncode1()))
			{
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
				map.put("drawreasoncode1", form.getDrawreasoncode1());
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
				//贷款额度
				resultMap.put("loansum", resultMap5.get("loansum"));
				//贷款期限
				resultMap.put("loanterm", resultMap5.get("loanterm"));
				//贷款月还款额
//				resultMap.put("monthrepayamt", resultMap5.get("monthrepayamt"));
				resultMap.put("monthrepayamt", resultMap5.get("yrepayamt"));
				//放款日期
				resultMap.put("loandate", resultMap5.get("loandate"));
				//还款方式
				resultMap.put("repaymode", resultMap5.get("repaymode"));
				//房屋坐落
				resultMap.put("houseaddr", resultMap5.get("houseaddr"));
				//还款月数
				resultMap.put("repaymonths", resultMap5.get("repaymonths"));
			}else
			{
				/*第2次通信 查询贷款信息*/
				//TODO 整理需上送信息 :借款合同号，
				//map.put("stepseqno", "0");
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKCX.txt", map, req);
				log.debug("发往中心报文-查询贷款信息："+xml);
				
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158029");
				log.debug("中心返回报文-查询贷款信息："+rexml);
				
				HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKCX.txt", rexml, req);
				if(!"0".equals(resultMap2.get("recode"))){				
					modelMap.clear();
					modelMap.put("recode", resultMap2.get("recode"));
					modelMap.put("msg", resultMap2.get("msg"));
					log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
					return modelMap;
				}
				//贷款额度
				resultMap.put("loansum", resultMap2.get("loansum"));
				//贷款期限
				resultMap.put("loanterm", resultMap2.get("loanterm"));
				//贷款月还款额
				resultMap.put("monthrepayamt", resultMap2.get("monthrepayamt"));
				//放款日期
				resultMap.put("loandate", resultMap2.get("loandate"));
				//还款方式
				resultMap.put("repaymode", resultMap2.get("repaymode"));
				//房屋坐落
				resultMap.put("houseaddr", resultMap2.get("houseaddr"));
				//还款月数
				resultMap.put("repaymonths", resultMap2.get("repaymonths"));
			}
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
			modelMap.clear();
			//modelMap.put("app01025Result", app01025Result);
			BeanUtil.transMap2Bean(resultMap, app01025Result);
			//app01025Result.setIndiaccstate(PropertiesReader.getProperty("yingshe.properties","indiaccstate"+app01025Result.getIndiaccstate()+form.getCenterId()));
			//app01025Result.setFrzflag(PropertiesReader.getProperty("yingshe.properties","frzflag"+app01025Result.getFrzflag()+form.getCenterId()));
			//app01025Result.setZjlx(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01025Result.getZjlx()+form.getCenterId()));
		}
		
		app01025Result.setMatecertitype_code(app01025Result.getMatecertitype());
		app01025Result.setOwncertitype_code(app01025Result.getOwncertitype());
		if(!CommonUtil.isEmpty(app01025Result.getMatecertitype()))app01025Result.setMatecertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01025Result.getMatecertitype()+form.getCenterId()));
		if(!CommonUtil.isEmpty(app01025Result.getOwncertitype()))app01025Result.setOwncertitype(PropertiesReader.getProperty("yingshe.properties","zjlx"+app01025Result.getOwncertitype()+form.getCenterId()));
		app01025Result.setRepaymode(PropertiesReader.getProperty("yingshe.properties","dkhkfs"+app01025Result.getRepaymode()+form.getCenterId()));
		
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01043"+form.getCenterId()+".result", app01025Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("buyhousename", app01025Result.getBuyhousename());
		modelMap.put("owncertinum", app01025Result.getOwncertinum());
		modelMap.put("owncertitype", app01025Result.getOwncertitype_code());
		modelMap.put("matename", app01025Result.getMatename());
		modelMap.put("matecertinum", app01025Result.getMatecertinum());
		modelMap.put("matecertitype", app01025Result.getMatecertitype_code());
		modelMap.put("result", appapi00101Result);
		log.info(Constants.LOG_HEAD+"appApi01043 end.");
		
		return modelMap;
	}
	
	
}
