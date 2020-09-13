package com.yondervision.yfmap.handle.handleImpl.handle00087500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01002Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.baoshan.AppApi01002Result;
import com.yondervision.yfmap.result.baoshan.AppApi40115Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle01002_00087500
* @Description: 物业费提取
* @date Mar 10, 2016 2:58:15 PM   
* 
*/ 
public class Handle01002_00087500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi01002 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01002Form form = (AppApi01002Form)form1;
		log.debug("物业费提取请求参"+form);
		AES aes = new AES();
		System.out.println("surplusAccount=="+form.getSurplusAccount());
		form.setFullName(aes.decrypt(form.getFullName()));
		System.out.println("fullName=="+form.getFullName());
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		System.out.println("idcardNumber=="+form.getIdcardNumber());
		form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
		System.out.println("unitaccnum=="+form.getUnitaccnum());
		form.setBankaccnm(aes.decrypt(form.getBankaccnm()));
		System.out.println("bankaccnm=="+form.getBankaccnm());
		form.setBankaccnum(aes.decrypt(form.getBankaccnum()));
		System.out.println("bankaccnum=="+form.getBankaccnum());
		form.setBankcode(aes.decrypt(form.getBankcode()));
		System.out.println("bankcode=="+form.getBankcode());
		System.out.println("drawamt=="+form.getDrawamt());
		System.out.println("drawreason=="+form.getDrawreason());
		form.setDrawamt(money(form.getDrawamt()));
		//form.setNewpassword(aes.decrypt(form.getNewpassword()));
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		AppApi40115Result app40115Result = new AppApi40115Result();
		AppApi01002Result app01002Result = new AppApi01002Result();
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
			//buschktype[0] = ‘1’ ， 校验个人账户状态
			//buschktype[0] = ‘2’  校验个人账户状态，身份证号码，个人姓名，单位账号
			//buschktype[1] = ‘1’， 校验个人收款信息是否存在，并将大行转小行
			//buschktype[1] = ‘2’， 校验个人收款信息是否存在，并将大行转小行，传入的收款信息与系统中的收款信息校验。
			//buschktype[2] = ‘1’， 校验是否签约手机号码
			//buschktype[2] = ‘2’， 校验是否签约手机号码，传入的手机号与系统中的是否一致
			//buschktype[3] = ‘1’，校验是否生成验证码
			//buschktype[3] = ‘2’，校验是否生成验证码，校验验证码超时和是否一致
			form.setBuschktype("2222");
			HashMap map = BeanUtil.transBean2Map(form);
			String validXml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_IDENTITY_AUTH.xml", map, req);
			log.debug("物业费提取信息校验-前置YFMAP发往中心报文："+validXml);	
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String validRexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), validXml, "160096");
			log.debug("物业费提取信息校验-前置YFMAP接收中心报文："+validRexml);
			HashMap validResultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_IDENTITY_AUTH.xml", validRexml, req);
			log.debug("物业费提取信息校验-解析报文MAP："+validResultMap);
			BeanUtil.transMap2Bean(validResultMap, app40115Result);
			log.debug("物业费提取信息校验-MAP封装成BEAN："+app40115Result);
			if(!"0".equals(app40115Result.getRecode())){
				modelMap.put("recode", app40115Result.getRecode());
				modelMap.put("msg", app40115Result.getMsg());
				log.error("物业费提取信息校验-中心返回报文 状态recode :"+app40115Result.getRecode()+"  ,  描述msg : "+app40115Result.getMsg());
				return modelMap;
			}
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_TQ_WYF.xml", map, req);
			log.debug("物业费提取-前置YFMAP发往中心报文："+xml);
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "150112");
			//String rexml ="<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><tr_code>430401</tr_code><req_no></req_no><ans_no></ans_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>00</ans_code><ans_info>成功</ans_info><reserved></reserved></head><body><certinum>1234567890</certinum><accnum>98765432101</accnum><accname>测试员</accname></body></root>";
			//rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>"+rexml;
			log.debug("物业费提取-前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_TQ_WYF.xml", rexml, req);
			log.debug("物业费提取-解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app01002Result);
			log.debug("物业费提取-MAP封装成BEAN："+app01002Result);
			if(!"0".equals(app01002Result.getRecode())){
				modelMap.put("recode", app01002Result.getRecode());
				modelMap.put("msg", app01002Result.getMsg());
				log.error("物业费提取-中心返回报文 状态recode :"+app01002Result.getRecode()+"  ,  描述msg : "+app01002Result.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi01002 end.");
		return modelMap;
	}

	private String money(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = money.replace(",", "");
		return result;
	}
}
