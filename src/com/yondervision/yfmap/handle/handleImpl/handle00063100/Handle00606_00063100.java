package com.yondervision.yfmap.handle.handleImpl.handle00063100;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle00606_00063100 
* @Description: 冲还贷协议签订查询
* @author Caozhongyan
* @date 2016年7月15日 上午11:30:32   
* 
*/ 
public class Handle00606_00063100 implements CtrlHandleInter {
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
			//form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKXYCX.txt");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt");
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKXYCX.txt", map, req);
			log.debug("前置3发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600079");
//			String reqxml = "<TranCode>600079</><TranDate>2016-06-28</><CertiNum>11111111111111</><AccName>小张</><STimeStamp>2016-07-19 17:58:43:932000</><MTimeStamp>2016-07-19 17:58:43:932000</><BrcCode>00063100</><TellCode>1110</><ChkCode>1110</><AuthCode1>1110</><AuthCode2>1110</><AuthCode3>1110</><TranChannel>0</><TranIP>192.168.3.233</><ChannelSeq>10772210</><TranSeq>25729</><BusiSeq>25729</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag></><FinancialDate>2016-01-01</><HostBank></><SubBank></><CustFlag>2</><Flag>0</><LoanContrNum>0011010028</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKXYCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap.get("flag"));			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"0".equals(app00601Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			
		}
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("flag",app00601Result.getFlag());
		modelMap.put("loanContrNum",app00601Result.getLoanContrNum());
		modelMap.put("custFlag",app00601Result.getCustFlag());
		modelMap.put("accName",app00601Result.getAccName());
		return modelMap;
	}

}
