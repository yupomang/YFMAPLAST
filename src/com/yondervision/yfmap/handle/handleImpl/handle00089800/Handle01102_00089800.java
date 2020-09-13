package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00611Result;
import com.yondervision.yfmap.result.haikou.AppApi01102Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author ljd
 * 提取进度查询
 */
public class Handle01102_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApiCommonForm form = (AppApiCommonForm)form1;
		log.info(Constants.LOG_HEAD+"appApi01102 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		
		AppApi01102Result app01102Result= new AppApi01102Result();
		
		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		String apprflagDate = "";
		String apprflagID = "";
		String apprflagMsg = "";
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_TQJDCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111111");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_TQJDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app01102Result);
			
			if(!"0".equals(app01102Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app01102Result.getRecode());
				modelMap.put("msg", app01102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01102Result.getRecode()+"  ,  描述msg : "+app01102Result.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoBean> appapi01102Result = null;
		appapi01102Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01102"+form.getCenterId()+".result", app01102Result);
		Iterator<TitleInfoBean> it1 = appapi01102Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		apprflagID = app01102Result.getLoancontrstate();
		
//		if("99".equals(apprflagID)){
//			//99-合同废弃
//			AppApi011Result app01199 = new AppApi011Result();
//			app01199.setApprflagID("99");
//			app01199.setApprflagMSG("合同废弃");
//			list.add(app01199);	
//			apprflagMsg = "合同废弃";
//		} else {
//			00-申请,
			AppApi011Result app01100 = new AppApi011Result();
			app01100.setApprflagID("00");
			app01100.setApprflagMSG("申请");
			list.add(app01100);	
//			01-复审,
			AppApi011Result app01101 = new AppApi011Result();
			app01101.setApprflagID("01");
			app01101.setApprflagMSG("复审");
			list.add(app01101);	
//			02-审批,
			AppApi011Result app01102 = new AppApi011Result();
			app01102.setApprflagID("02");
			app01102.setApprflagMSG("审批");
			list.add(app01102);	
//			04-开发商/借款人已签订合同,
			AppApi011Result app01104 = new AppApi011Result();
			app01104.setApprflagID("04");
			app01104.setApprflagMSG("提取1");
			list.add(app01104);	
//			05-通知开发商/借款人办理抵押,
			AppApi011Result app01105 = new AppApi011Result();
			app01105.setApprflagID("05");
			app01105.setApprflagMSG("提取2");
			list.add(app01105);	
//			06-开发商/借款人已来办理抵押,
			AppApi011Result app01106 = new AppApi011Result();
			app01106.setApprflagID("06");
			app01106.setApprflagMSG("提取3");
			list.add(app01106);
//			07-贷款担保,
			AppApi011Result app01107 = new AppApi011Result();
			app01107.setApprflagID("07");
			app01107.setApprflagMSG("提取4");
			list.add(app01107);	
//			08-请款,
			AppApi011Result app01108 = new AppApi011Result();
			app01108.setApprflagID("08");
			app01108.setApprflagMSG("提取5");
			list.add(app01108);	
//			09-放款,
			AppApi011Result app01109 = new AppApi011Result();
			app01109.setApprflagID("09");
			app01109.setApprflagMSG("提取6");
			list.add(app01109);	
			
			for(AppApi011Result app011 : list){
				apprflagMsg = app011.getApprflagMSG();
				break;
			}
//		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result1", appapi01102Result);
		
		modelMap.put("apprflagID", apprflagID);
		modelMap.put("apprflagMSG", apprflagMsg);
		//modelMap.put("apprflagDate", apprflagDate); 
		modelMap.put("result", list);
		log.info(Constants.LOG_HEAD+"appApi01102 end.");
		return modelMap;
	}
	
}
