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
import com.yondervision.yfmap.form.AppApi01036Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.IdCard;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 贷款展期办理
 * @author LFX
 *
 */
public class Handle01036_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01036Form form = (AppApi01036Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01036 start.");
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
			
			//计算展期不能大于贷款期限
			map.put("flag", "4");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKHKSSFX.txt", map, req);
			log.debug("试算反显1 发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
			log.debug("试算反显1 解析报文MAP："+resultMap4);
			if(!"0".equals(resultMap4.get("recode"))){
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap4.get("recode")+"  ,  描述msg : "+resultMap4.get("msg"));
				return modelMap;
			}
			String age = resultMap4.get("paramretval")+"";
			String a[] = age.split(",");
			String maxage1 =  "60";
			String maxage2 =  "55";
			String maxage = "30";
			String s = IdCard.getGenderByIdCard(form.getBodyCardNumber());
			int nowage = IdCard.getAgeByIdCard(form.getBodyCardNumber());
			for(int i=0;i<a.length;i++)
			{
				String yhbm_t[] = a[i].split("=");
				if("1".equals(yhbm_t[0]))
				{
					maxage1 = yhbm_t[1];
				}else if("2".equals(yhbm_t[0]))
				{
					maxage2 = yhbm_t[1];
				}
			}
			if("1".equals(s))
			{
				maxage = Integer.parseInt(maxage1) -nowage+5+"";
			}else if("2".equals(s))
			{
				maxage = Integer.parseInt(maxage2) -nowage+5+"";
			}
			System.out.println("&&&&&&&&&&&maxage"+maxage);
			if(Integer.parseInt(maxage)>30)maxage = "30";
			String increaseNum = form.getIncreaseNum();
			String remainterms = resultMap2.get("remainterms")+"";
			System.out.println("&&&&&&&&&&&remainterms"+remainterms);
			String maxdkqs = (Integer.parseInt(maxage) * 12) +"";
			if((Integer.parseInt(increaseNum)+Integer.parseInt(remainterms))>Integer.parseInt(maxdkqs))
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "展期期数不能大于最长贷款年限！");
				log.error("展期期数不能大于最长贷款年限！");
				return modelMap;
			}
//			map.put("jkhtbh", resultMap2.get("jkhtbh"));
//			//利率类型
//			map.put("lllx", resultMap2.get("ratetype"));
//			
//			/*第3次通信  查询合同退休类型和变贷款利率*/
//			
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_TXLXHTLL128062.txt", map, req);
//			log.debug("发往中心报文-查询合同退休类型和变贷款利率："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128062");
//			log.debug("中心返回报文-查询合同退休类型和变贷款利率："+rexml);
//			
//			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_TXLXHTLL128062.txt", rexml, req);
//			if(!"0".equals(resultMap3.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap3.get("recode"));
//				modelMap.put("msg", resultMap3.get("msg"));
//				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
//				return modelMap;
//			}
			mapPool.putAll(map);
			mapPool.putAll(resultMap1);
			mapPool.putAll(resultMap2);
			//经办银行
			mapPool.put("agentbankcode", resultMap1.get("agentbankcode"));
			//经办机构
			mapPool.put("instcode", resultMap1.get("instcode"));
//			mapPool.putAll(resultMap3);
		}
		
		System.out.println("===开始存数据库");
		
		ApplyPP applyPP = new ApplyPP();
		//TODO 若以下部分（mapPool.get("XXX")）在每个存数据库内的变量都是一样的，则可直接写在ApplyPP中，以免代码太过冗余
		mapPool.put("_ACCNUM", mapPool.get("accnum"));
		//TODO 单位账号
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
//		mapPool.put("_PORCNAME", "贷款期限变更-展期");
//		mapPool.put("_PROCID", "60031011");
//		
//		mapPool.put("flowid", "296");
//		mapPool.put("counter", "1");
//		mapPool.put("selorgid", "08981302");
//		mapPool.put("stepid", "3");
		mapPool.put("exshterms", form.getIncreaseNum());
		mapPool.put("exttermenddate", form.getExttermenddate());
		mapPool.put("loanreason", form.getChangeReason());
		mapPool.put("afterrate", form.getCurrate());
		try {
			String flag = "36"+form.getCenterId();
			HashMap<String,String> temp = applyPP.exec(mapPool,form.getApplyid(),flag);
			if("false".equals(temp.get("recode")))
			{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", temp.get("msg"));
				log.info(Constants.LOG_HEAD+"appApi01036 end.");
				return modelMap;
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("applayid",temp.get("msg"));
			log.info(Constants.LOG_HEAD+"appApi01036 end.");
			return modelMap;
		} catch (Exception e) {
			modelMap.clear();
			//TODO 存数据库异常错误码
			modelMap.put("recode", "999999");
			modelMap.put("msg", "贷款展期存数据库异常");
			log.error("贷款展期存数据库异常："+e.getMessage());
			e.printStackTrace();
			return modelMap;
		}
		
	}
	
}
