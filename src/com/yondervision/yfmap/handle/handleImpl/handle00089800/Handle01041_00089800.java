package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import com.yondervision.yfmap.form.AppApi01036Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01025Result;
import com.yondervision.yfmap.result.haikou.AppApi01036Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 还款方式变更查询
 * @author LFX
 *
 */
public class Handle01041_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01036Form form = (AppApi01036Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01041 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		AppApi01036Result app01041Result = new AppApi01036Result();
		
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
				modelMap.put("msg", "无公积金贷款记录，无法办理此业务！");
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
			mapPool.putAll(resultMap1);
			mapPool.putAll(resultMap2);
			BeanUtil.transMap2Bean(mapPool, app01041Result);
		}
		app01041Result.setDkhkfs(PropertiesReader.getProperty("yingshe.properties","dkhkfs"+app01041Result.getDkhkfs()+form.getCenterId()));
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01041"+form.getCenterId()+".result", app01041Result);
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
			modelMap.put("result", appapi00101Result);
			modelMap.put("dkhkfs", app01041Result.getDkhkfs());
			modelMap.put("newrepaymode", d.get("newrepaymode"));
			modelMap.put("loanreason",  d.get("loanreason"));
			modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
			log.info(Constants.LOG_HEAD+"appApi01041 end.");
			return modelMap;
		}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00101Result);
			modelMap.put("dkhkfs", app01041Result.getDkhkfs());
			modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
			log.info(Constants.LOG_HEAD+"appApi01041 end.");
			return modelMap;	
	}
	
}
