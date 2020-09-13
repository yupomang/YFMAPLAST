package com.yondervision.yfmap.handle.handleImpl.handle00063100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00401Result;
import com.yondervision.yfmap.result.AppApi00501Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		
		log.debug("form:"+form);
		
		if(CommonUtil.isEmpty(form.getBodyCardNumber())){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询参数缺失");
			return modelMap;
		}
		
		/* 模拟返回开始  */
		WeiHaiAppApi00601Result app00601Result= new WeiHaiAppApi00601Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt");
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt", map, req);
			log.debug("前置3发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600029");
			
//			String reqxml = "<TranCode>600029</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:54</><MTimeStamp>2014-05-13 09:33:54</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>-4928</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>123112310</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><_DOWNFILENAME>2014-04-21/downfile/lnque003.0</><InfactSum>170870.91</><InfactPunSum>0.00</><InfactIntSum>30870.91</><InfactPrinSum>140000.00</><CurRate>4.35</><CurDayBal>0.00</><AttermDate>2017-10-17</><BeginIntDate>2002-10-18</><CosignDate>2002-09-25</><LoanTerm>180</><LoanSum>140000.00</><CerId1>370620196207240511</><AccName>迟健</><AgencyBank>14</><OweAmt>0.00</><OweTerm>0</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKYECX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"0".equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			
			String bankName = PropertiesReader.getProperty("yingshe.properties", "AgencyBank"+app00601Result.getAgencyBank()).trim();
			if(bankName!=null||!bankName.equals("")){
				app00601Result.setAgencyBank(bankName);
			}
			app00601Result.setCurRate(app00601Result.getCurRate()+"%");
			app00601Result.setCurDayBal(String.format("%,.2f",Double.valueOf(app00601Result.getCurDayBal())));
			app00601Result.setInfactSum(String.format("%,.2f",Double.valueOf(app00601Result.getInfactSum())));
			app00601Result.setInfactPrinSum(String.format("%,.2f",Double.valueOf(app00601Result.getInfactPrinSum())));
			app00601Result.setInfactPunSum(String.format("%,.2f",Double.valueOf(app00601Result.getInfactPunSum())));
			app00601Result.setInfactIntSum(String.format("%,.2f",Double.valueOf(app00601Result.getInfactIntSum())));
			app00601Result.setOweAmt(String.format("%,.2f",Double.valueOf(app00601Result.getOweAmt())));
			app00601Result.setLoanSum(String.format("%,.2f",Double.valueOf(app00601Result.getLoanSum())));
			/**原201405----------------------
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType").trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key").trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type").trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml");
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return "";
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYECX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!Constants.sucess.equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return "";
			}
			原201405----------------------*/
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("detail", appapi00101Detail);
		return modelMap;
	}

}
