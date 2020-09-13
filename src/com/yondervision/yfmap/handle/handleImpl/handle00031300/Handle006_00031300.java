package com.yondervision.yfmap.handle.handleImpl.handle00031300;

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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author fengjing
 *  贷款账户基本信息查询
 */
public class Handle006_00031300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		ZhangJiaKouAppApi00601Result app00601Result= new ZhangJiaKouAppApi00601Result();
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
//			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//			if(!file.exists()){
//				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "公积金账号对应文件不存在");
//				return modelMap;
//			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKJBXXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_DKYECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00004");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?>" +
//					"<root><head>" +
//					"<reqflag>1</reqflag>" +
//					"<msgtype>75</msgtype>" +
//					"<tr_code>X00005</tr_code>" +
//					"<corp_no>00053100</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2015-09-29</tr_acdt>" +
//					"<tr_time>10:25:15</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>交易成功完成</ans_info>" +
//					"<particular_code>000000</particular_code>" +
//					"<particular_info>交易成功完成</particular_info>" +
//					"<reserved></reserved></head>" +
//					"<body>" +
//					"<dkzh>0601010101</dkzh>" +
//			   		"<hkfs>等额本金</hkfs>" +
//			   		"<dkll>0.0325</dkll>" +
//			   		"<htztbz>正常</htztbz>" +
//			   		"<dkztbz>正常</dkztbz>" +
//			   		"<jqbz>未结清</jqbz>" +
//			   		"<fkrq>2016-01-01</fkrq>" +
//			   		"<jqrq></jqrq>" +
//			   		"<dqrq>2036-01-01</dqrq>" +
//			   		"<hkr>2016-01-15</hkr>" +
//			   		"<dkje>200000.000</dkje>" +
//			   		"<dqye>150000.000</dqye>" +
//			   		"<yqqs>0</yqqs>" +
//					"<yqbj>0.00</yqbj>" +
//					"<yqlx>0.00</yqlx>" +
//					"<yqfx>0.00</yqfx>" +
//					"<dkqx>240</dkqx>" +
//					"<syqx>180</syqx>" +
//			   		"<fwdz>中吉大厦中吉大厦中吉大厦中吉大厦中吉大厦</fwdz>" +
//			   		"<jkrxm>测试01</jkrxm>" +
//			   		"<jkrzjh>0101011002002</jkrzjh>" +
//			   		"<gtjkrxm>测试02</gtjkrxm>" +
//			   		"<gtjkrzjh>02020202020202</gtjkrzjh>  " +
//					"</body></root>";
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKJBXXCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_DKYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			
			if(!Constants.sucess_ts.equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
		}		
		
		app00601Result.setLoanamt(String.format("%.2f",Double.valueOf(app00601Result.getLoanamt())));               //贷款金额
		app00601Result.setLoanabaltotle(String.format("%.2f",Double.valueOf(app00601Result.getLoanabaltotle())));   //贷款余额
		app00601Result.setLoanrate(String.format("%.2f",Double.valueOf(app00601Result.getLoanrate()))+"%");         //贷款利率
		app00601Result.setMonthrepayment(String.format("%.2f",Double.valueOf(app00601Result.getMonthrepayment())));//月还款额
		app00601Result.setOverdueamt(String.format("%.2f",Double.valueOf(app00601Result.getOverdueamt())));//逾期金额
		List<TitleInfoBean> appapi00601Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00601Result);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi00601Form form1 = new AppApi00601Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		Handle006_00031300 hand = new Handle006_00031300();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
