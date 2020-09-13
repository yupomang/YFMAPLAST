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
import com.yondervision.yfmap.form.AppApi01033Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01034Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 业务办理-还款办结-海口
 * @author Administrator
 *
 */
public class Handle01033_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01033Form form = (AppApi01033Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01033 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		AppApi01034Result app01034Result = new AppApi01034Result();
		
		if(Constants.method_BSP.equals(send)){
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			//form.setSendSeqno(req);
			form.setSendSeqno(CommonUtil.getDate()+" "+CommonUtil.getTime());
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			
			HashMap map = BeanUtil.transBean2Map(form);
			
			/* 第1次通信   根据证件号码查询合同号*/
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文-根据证件号码查询合同号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			log.debug("中心返回报文-根据证件号码查询合同号："+rexml);
			
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
			
			map.put("flag", "1");
			map.put("actmp1024", form.getNewloanterm());
			map.put("seqno", "-"+form.getSeqno());
			/* 第1次通信   获取还款后最低保留余额*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQBLYE.txt", map, req);
			log.debug("发往中心报文-还款后最低保留余额："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128060");
			log.debug("中心返回报文-获取还款后最低保留余额："+rexml);
			
			HashMap resultMap1_2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQBLYE.txt", rexml, req);
			if(!"0".equals(resultMap1_2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1_2.get("recode"));
				modelMap.put("msg", resultMap1_2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1_2.get("recode")+"  ,  描述msg : " + resultMap1_2.get("msg"));
				return modelMap;
			}
			
			/* 第1次通信  计算实际还款信息*/
			map.put("stepseqno", "0");
			map.put("instance", "-"+form.getSeqno());
			map.put("newloanterm", "0");
			map.put("repayorder", "1");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK129131.txt", map, req);
			log.debug("发往中心报文-计算实际还款信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129131");
			log.debug("中心返回报文-计算实际还款信息："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK129131.txt", rexml, req);
			if(!"0".equals(resultMap.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap.get("recode"));
				modelMap.put("msg", resultMap.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap.get("recode")+"  ,  描述msg : " + resultMap.get("msg"));
				return modelMap;
			}
			
			BeanUtil.transMap2Bean(resultMap, app01034Result);
		}
		
		List<TitleInfoBean> appapi01034Result = null;
		appapi01034Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01034"+form.getCenterId()+".result", app01034Result);
		Iterator<TitleInfoBean> it1 = appapi01034Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi01034Result);
		//modelMap.put("result1", app01034Result);
		log.info(Constants.LOG_HEAD+"appApi01034 end.");
		
		return modelMap;
	}
	
}
