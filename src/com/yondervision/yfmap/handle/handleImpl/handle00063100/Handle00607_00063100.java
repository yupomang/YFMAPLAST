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
* @ClassName: Handle00607_00063100 
* @Description: 冲还贷协议签订
* @author Caozhongyan
* @date 2016年7月15日 上午11:31:31   
* 
*/ 
public class Handle00607_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		
		log.debug("form:"+form);
		//需要三个参数：合同号，是否签约
//		if(CommonUtil.isEmpty(form.getCustFlag())||CommonUtil.isEmpty(form.getAccName())||CommonUtil.isEmpty(form.getLoanContrNum())||CommonUtil.isEmpty(form.getFlag())){
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "查询参数缺失");
//			return modelMap;
//		}
		
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
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKXYQR.txt");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKXYQR.txt");
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKXYQR.txt", map, req);
			log.debug("前置3发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600052");
			
//			String reqxml = "<TranCode>600052</><TranDate>2016-07-19</><STimeStamp>2016-07-19 09:39:02:109000</><MTimeStamp>2016-07-19 09:39:02:109000</><BrcCode>00063114</><TellCode>1903</><ChkCode>1903</><AuthCode1>1903</><AuthCode2>1903</><AuthCode3>1903</><TranChannel>01</><TranIP>192.168.6.101</><ChannelSeq>21910000</><TranSeq>3590</><BusiSeq>3590</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag></><FinancialDate>2016-07-19</><HostBank></><SubBank></><protoColNum>1442090087-1</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKXYQR.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
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
		return modelMap;
	}

}
