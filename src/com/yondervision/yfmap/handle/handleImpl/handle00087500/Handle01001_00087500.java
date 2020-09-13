package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baoshan.AppApi01001Result;
import com.yondervision.yfmap.result.baoshan.UserBankInfoResult;
import com.yondervision.yfmap.result.baoshan.UserBaseInfoResult;
import com.yondervision.yfmap.result.baoshan.UserSignPhoneResult;
import com.yondervision.yfmap.result.baoshan.WYFDrawAmtResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle01001_00087500
* @Description: 物业费提取相关信息获取
* @date Mar 10, 2016 2:58:15 PM   
* 
*/ 
public class Handle01001_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi01001 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01001Form form = (AppApi01001Form)form1;
		log.debug("物业费提取相关信息获取请求参"+form);
		System.out.println("surplusAccount=="+form.getSurplusAccount());
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		AppApi01001Result app01001Result = new AppApi01001Result();
		UserBaseInfoResult userbaseinfoResult = new UserBaseInfoResult();
		UserSignPhoneResult userSignPhoneResult = new UserSignPhoneResult();
		UserBankInfoResult userbankinfoResult = new UserBankInfoResult();
		WYFDrawAmtResult amtResult = new WYFDrawAmtResult();
		if(Constants.method_XML.equals(send)){//xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());			
			String req = form.getSurplusAccount()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());
			System.out.println(form.getSurplusAccount()+"   "+form.getSendDate()+"   "+form.getSendTime());
			HashMap map = BeanUtil.transBean2Map(form);
			String reqBaseInfoXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_USER_BASE_INFO.xml", map, req);
			log.debug("物业费提取相关信息获取-个人基本信息获取-前置YFMAP发往中心报文："+reqBaseInfoXml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String resBaseInfoXml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), reqBaseInfoXml, "150114");
			log.debug("物业费提取相关信息获取-个人基本信息获取-前置YFMAP接收中心报文："+resBaseInfoXml);
			HashMap resultBaseInfoMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_USER_BASE_INFO.xml", resBaseInfoXml, req);
			log.debug("物业费提取相关信息获取-个人基本信息获取-解析报文MAP："+resultBaseInfoMap);
			BeanUtil.transMap2Bean(resultBaseInfoMap, userbaseinfoResult);
			log.debug("物业费提取相关信息获取-个人基本信息获取-MAP封装成BEAN："+userbaseinfoResult);
			if(!"0".equals(userbaseinfoResult.getRecode())){
				modelMap.put("recode", userbaseinfoResult.getRecode());
				modelMap.put("msg", userbaseinfoResult.getMsg());
				app01001Result = setAppapi01001Result(userbaseinfoResult, userSignPhoneResult, userbankinfoResult, amtResult);
				modelMap.put("result", app01001Result);
				log.error("物业费提取相关信息获取-个人基本信息获取-中心返回报文 状态recode :"+userbaseinfoResult.getRecode()+"  ,  描述msg : "+userbaseinfoResult.getMsg());
				return modelMap;
			}

			String getPhoneXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_USER_SIGN_PHONE.xml", map, req);
			log.debug("物业费提取相关信息获取-获取签约手机号-前置YFMAP发往中心报文："+getPhoneXml);	
			String getPhoneRexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), getPhoneXml, "150116");
			log.debug("物业费提取相关信息获取-获取签约手机号-前置YFMAP接收中心报文："+getPhoneRexml);
			HashMap getPhoneResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_USER_SIGN_PHONE.xml", getPhoneRexml, req);
			log.debug("物业费提取相关信息获取-获取签约手机号-解析报文MAP："+getPhoneResultMap);
			BeanUtil.transMap2Bean(getPhoneResultMap, userSignPhoneResult);
			log.debug("物业费提取相关信息获取-获取签约手机号-MAP封装成BEAN："+userSignPhoneResult);
			if(!"0".equals(userSignPhoneResult.getRecode())){
				modelMap.put("recode", userSignPhoneResult.getRecode());
				modelMap.put("msg", userSignPhoneResult.getMsg());
				app01001Result = setAppapi01001Result(userbaseinfoResult, userSignPhoneResult, userbankinfoResult, amtResult);
				modelMap.put("result", app01001Result);
				log.error("物业费提取相关信息获取-获取签约手机号-中心返回报文 状态recode :"+userSignPhoneResult.getRecode()+"  ,  描述msg : "+userSignPhoneResult.getMsg());
				return modelMap;
			}

			String getBankInfoXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_USER_BANK_INFO.xml", map, req);
			log.debug("物业费提取相关信息获取-获取收款行信息-前置YFMAP发往中心报文："+getBankInfoXml);	
			String getBankInfoRexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), getBankInfoXml, "150115");
			log.debug("物业费提取相关信息获取-获取收款行信息-前置YFMAP接收中心报文："+getBankInfoRexml);
			HashMap getBankInfoResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_USER_BANK_INFO.xml", getBankInfoRexml, req);
			log.debug("物业费提取相关信息获取-获取收款行信息-解析报文MAP："+getBankInfoResultMap);
			BeanUtil.transMap2Bean(getBankInfoResultMap, userbankinfoResult);
			log.debug("物业费提取相关信息获取-获取收款行信息-MAP封装成BEAN："+userbankinfoResult);
			if(!"0".equals(userbankinfoResult.getRecode())){
				modelMap.put("recode", userbankinfoResult.getRecode());
				modelMap.put("msg", userbankinfoResult.getMsg());
				app01001Result = setAppapi01001Result(userbaseinfoResult, userSignPhoneResult, userbankinfoResult, amtResult);
				modelMap.put("result", app01001Result);
				log.error("物业费提取相关信息获取-获取收款行信息中心返回报文 状态recode :"+userbankinfoResult.getRecode()+"  ,  描述msg : "+userbankinfoResult.getMsg());
				return modelMap;
			}
			
			String getAmtXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_GET_WYFTQ_AMT.xml", map, req);
			log.debug("物业费提取相关信息获取-获取提取金额-前置YFMAP发往中心报文："+getAmtXml);	
			String getAmtRexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), getAmtXml, "150091");
			log.debug("物业费提取相关信息获取-获取提取金额-前置YFMAP接收中心报文："+getAmtRexml);
			HashMap getAmtResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_GET_WYFTQ_AMT.xml", getAmtRexml, req);
			log.debug("物业费提取相关信息获取-获取提取金额-解析报文MAP："+getAmtResultMap);
			BeanUtil.transMap2Bean(getAmtResultMap, amtResult);
			log.debug("物业费提取相关信息获取-获取提取金额-MAP封装成BEAN："+amtResult);
			if(!"0".equals(amtResult.getRecode())){
				modelMap.put("recode", amtResult.getRecode());
				modelMap.put("msg", amtResult.getMsg());
				app01001Result = setAppapi01001Result(userbaseinfoResult, userSignPhoneResult, userbankinfoResult, amtResult);
				log.error("物业费提取相关信息获取-获取提取金额-中心返回报文 状态recode :"+amtResult.getRecode()+"  ,  描述msg : "+amtResult.getMsg());
				return modelMap;
			}
			app01001Result = setAppapi01001Result(userbaseinfoResult, userSignPhoneResult, userbankinfoResult, amtResult);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", app01001Result);
		log.info(Constants.LOG_HEAD+"appApi01001 end.");
		return modelMap;
	}

	private String moneyTran(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = NumberFormat.getCurrencyInstance().format(new Double(money)).substring(1);
		return result;
	}

	private AppApi01001Result setAppapi01001Result(UserBaseInfoResult userbaseinfoResult,
			UserSignPhoneResult userSignPhoneResult,
			UserBankInfoResult userbankinfoResult, WYFDrawAmtResult amtResult) throws CenterRuntimeException, UnsupportedEncodingException{
		AppApi01001Result app01001Result = new AppApi01001Result();
		AES aes = new AES();
		app01001Result.setAccname(aes.encrypt(((CommonUtil.isEmpty(userbaseinfoResult.getAccname())?"":userbaseinfoResult.getAccname()).getBytes("UTF-8"))));
		app01001Result.setCertinum(aes.encrypt(((CommonUtil.isEmpty(userbaseinfoResult.getCertinum())?"":userbaseinfoResult.getCertinum()).getBytes("UTF-8"))));
		app01001Result.setUnitaccnum(aes.encrypt(((CommonUtil.isEmpty(userbaseinfoResult.getUnitaccnum())?"":userbaseinfoResult.getUnitaccnum()).getBytes("UTF-8"))));
		app01001Result.setPhone(aes.encrypt(((CommonUtil.isEmpty(userSignPhoneResult.getPhone())?"":userSignPhoneResult.getPhone()).getBytes("UTF-8"))));
		app01001Result.setBankaccnm(aes.encrypt(((CommonUtil.isEmpty(userbankinfoResult.getBankaccnm())?"":userbankinfoResult.getBankaccnm()).getBytes("UTF-8"))));
		app01001Result.setBankaccnum(aes.encrypt(((CommonUtil.isEmpty(userbankinfoResult.getBankaccnum())?"":userbankinfoResult.getBankaccnum()).getBytes("UTF-8"))));
		app01001Result.setBankcode(aes.encrypt(((CommonUtil.isEmpty(userbankinfoResult.getBankcode())?"":userbankinfoResult.getBankcode()).getBytes("UTF-8"))));
		app01001Result.setBankname(aes.encrypt(((CommonUtil.isEmpty(userbankinfoResult.getBankname())?"":userbankinfoResult.getBankname()).getBytes("UTF-8"))));
		if(!CommonUtil.isEmpty(amtResult.getInputamt())){
			String inputamt = moneyTran(amtResult.getInputamt());
			app01001Result.setInputamt(aes.encrypt(((CommonUtil.isEmpty(inputamt)?"":inputamt).getBytes("UTF-8"))));
		}else{
			app01001Result.setInputamt(aes.encrypt((("").getBytes("UTF-8"))));
		}
		return app01001Result;
	}
}
