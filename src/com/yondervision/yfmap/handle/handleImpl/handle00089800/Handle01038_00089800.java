package com.yondervision.yfmap.handle.handleImpl.handle00089800;

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
import com.yondervision.yfmap.form.AppApi01036Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 贷款缩期办理
 * @author LFX
 *
 */
public class Handle01038_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01036Form form = (AppApi01036Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01028 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		form.setChangeReason((aes.decrypt(form.getChangeReason())));
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
		
			/* 第1次通信   提取相关信息获取*/
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文-相关信息获取："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			log.debug("中心返回报文-相关信息获取："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHTH.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
				return modelMap;
			}
			
			if(CommonUtil.isEmpty(resultMap1.get("loanaccnum"))){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "交易129124，未查询到借款代码。");
				log.error("中心返回报文 状态recode :000001  ,  描述msg : 交易129124，未查询到借款代码。");
				return modelMap;
			}
			// 借款合同号，第一次通信后查询得到
			map.put("loancontrcode", resultMap1.get("loanaccnum"));	
			
			//TODO flag标志值
			map.put("flag", "");
			/* 第2次通信  查询提取相关信息*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK124002.txt", map, req);
			log.debug("发往中心报文-提取相关信息获取："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "124002");
			log.debug("中心返回报文-提取相关信息获取："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK124002.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
				return modelMap;
			}
			String remainterms = resultMap2.get("remainterms")+"";
			System.out.println("&&&&&&&&&&&remainterms"+remainterms);
			String reduceNum = form.getReduceNum();
			if((Integer.parseInt(remainterms)-Integer.parseInt(reduceNum))<0)
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "缩期期数不能大于贷款剩余期数！");
				log.error("缩期期数不能大于贷款剩余期数！");
				return modelMap;
			}
			mapPool.putAll(resultMap1);
			mapPool.putAll(resultMap2);
			//经办银行
			mapPool.put("agentbankcode", resultMap1.get("agentbankcode"));
			//经办机构
			mapPool.put("instcode", resultMap1.get("instcode"));
		}
		
		System.out.println("===开始存数据库");
		//TODO 
		
		ApplyPP applyPP = new ApplyPP();
		mapPool.put("_ACCNUM", mapPool.get("accnum"));
		mapPool.put("_UNITACCNUM", "");
		mapPool.put("_UNITACCNAME", mapPool.get("dwmc"));
		mapPool.put("_ACCNAME", mapPool.get("jkrxm"));
		mapPool.put("_DEPUTYIDCARDNUM", mapPool.get("jkrzjh"));
		mapPool.put("_SENDOPERID", mapPool.get("jkrzjh"));
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
//		mapPool.put("_PORCNAME", "贷款期限变更-缩期");
//		mapPool.put("_PROCID", "60031012");
//		
//		mapPool.put("flowid", "344");
//		mapPool.put("counter", "1");
//		mapPool.put("selorgid", "08981303");
//		mapPool.put("stepid", "3");
		mapPool.put("exshterms", form.getReduceNum());
		mapPool.put("exttermenddate", form.getExttermenddate());
		mapPool.put("loanreason", form.getChangeReason());
		mapPool.put("afterrate", form.getCurrate());
		try {
			String flag = "38"+form.getCenterId();
			HashMap<String,String> temp = applyPP.exec(mapPool,form.getApplyid(),flag);
			if("false".equals(temp.get("recode")))
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", temp.get("msg"));
				log.info(Constants.LOG_HEAD+"appApi01038 end.");
				return modelMap;
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("applayid", temp.get("msg"));
			log.info(Constants.LOG_HEAD+"appApi01038 end.");
			return modelMap;
		} catch (Exception e) {
			modelMap.clear();
			//TODO 存数据库异常错误码
			modelMap.put("recode", "999999");
			modelMap.put("msg", "贷款缩期存数据库异常");
			log.error("贷款缩期存数据库异常："+e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
		
	}
	
}
