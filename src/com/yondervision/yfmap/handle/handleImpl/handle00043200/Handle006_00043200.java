package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(new Date()));
		form.setSendTime(formatter2.format(new Date()));
		/* 模拟返回开始  */
		AppApi00601Result app00601Result= new AppApi00601Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "C040002");
//			String rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?><mi><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><loanaccnum>801078393844</loanaccnum><accname>陈倩敏</accname><loanamt>300000.00</loanamt><repayamt>80000.00</repayamt><curbal>220000.00</curbal><loanterm>20</loanterm><currate>3.5%</currate><repaymode>微软问</repaymode><monthrepayamt>2200.00</monthrepayamt><repayday>08</repayday><agentbankcode>中行</agentbankcode><repayint>800.00</repayint><oweamt>0.00</oweamt><oweterm>0</oweterm><repayterm>24</repayterm><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></mi>";
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			
			
			if(!"00".equals(app00601Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			
		}
		String accinstname = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_InstCode_"+app00601Result.getAccinstname()).trim();
		if(accinstname!=null&&!accinstname.equals("")){
			app00601Result.setAccinstname(accinstname.trim());
		}else{
			app00601Result.setAccinstname(app00601Result.getAccinstname());
		}
		String SecLoanFlag = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_SecLoanFlag_"+app00601Result.getFreeuse2()).trim();
		if(SecLoanFlag!=null&&!SecLoanFlag.equals("")){
			app00601Result.setFreeuse2(SecLoanFlag.trim());
		}else{
			app00601Result.setFreeuse2(app00601Result.getFreeuse2());
		}
		String RetLoanMode = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_RetLoanMode_"+app00601Result.getRepaymode()).trim();
		if(RetLoanMode!=null&&!RetLoanMode.equals("")){
			app00601Result.setRepaymode(RetLoanMode.trim());
		}else{
			app00601Result.setRepaymode(app00601Result.getRepaymode());
		}
		String LoanType = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_LoanType_"+app00601Result.getRetterms()).trim();
		if(LoanType!=null&&!LoanType.equals("")){
			app00601Result.setRetterms(LoanType.trim());
		}else{
			app00601Result.setRetterms(app00601Result.getRetterms());
		}
		String state1 = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_state1_"+app00601Result.getFreeuse3()).trim();
		if(state1!=null&&!state1.equals("")){
			app00601Result.setFreeuse3(state1.trim());
		}else{
			app00601Result.setFreeuse3(app00601Result.getFreeuse3());
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
//		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
//		modelMap.put("detail", appapi00101Detail);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

}
