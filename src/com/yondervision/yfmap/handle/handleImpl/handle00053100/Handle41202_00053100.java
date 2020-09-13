package com.yondervision.yfmap.handle.handleImpl.handle00053100;

import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi41202Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi41201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 短信签约
 */
public class Handle41202_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi41202Form form = (AppApi41202Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi412Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		JiNanAppApi41201Result app41201Result= new JiNanAppApi41201Result();
		
		if(Constants.method_XML.equals(send)){
			AES aes = new AES();
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi412MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi412Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi412Type"+form.getCenterId()).trim());			
			form.setLoanaccnum(aes.decrypt(form.getLoanaccnum()));
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
//			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//			if(!file.exists()){
//				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "公积金账号对应文件不存在");
//				return modelMap;
//			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DXQY.xml", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_DXQY.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000010");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>75</msgtype><tr_code>X00005</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><xm>夏洛</xm><zjlx>身份证</zjlx><zjhm>220001197701011234</zjhm><gfdz>梧桐花园88号</gfdz><dkje>500000.00</dkje><nx>30</nx><hkfs>等额本金</hkfs><dkye>400000.00</dkye><zkyh>建行</zkyh><yhhkzh>2012748300917887</yhhkzh><htdqrq>2025-12-31</htdqrq><cqzbz>未入库</cqzbz><czrq>2015-01-01</czrq></body></root>";
//			String temp = "";
//			if("02".equals(form.getSignStatus()))
//			{
//				temp = "02";
//			}else{
//				temp = "01";
//			}
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>80</msgtype><tr_code>X00010</tr_code><corp_no>00053100</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2016-05-17</tr_acdt>" +
//					"<tr_time>10:00:00</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>签约成功</ans_info>" +
//					"<particular_code></particular_code><particular_info></particular_info><reserved></reserved></head>" +
//					"<body>		" +
//					"<qylx>"+form.getSignType()+"</qylx>" +
//					"<gjjzh>99999</gjjzh>" +
//					"<grxm>孙亚伟</grxm>" +
//					"<zjhm>410504198904180071</zjhm>" +
//					"<sjh>18686693936</sjh>" +
//					"<dqrq>2017-05-17</dqrq>" +
//					"<dkzh>111111</dkzh>" +
//					"<qyzt>"+temp+"</qyzt>" +
//					"</body></root>";

			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DXQY.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_DXQY.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app41201Result);
			
			
			if(!Constants.sucess.equals(app41201Result.getRecode())){
				modelMap.put("recode", app41201Result.getRecode());
				modelMap.put("msg", app41201Result.getMsg());
				log.error("中心返回报文 状态recode :"+app41201Result.getRecode()+"  ,  描述msg : "+app41201Result.getMsg());
				return modelMap;
			}
		}		
		
		//app41201Result.setLoanamt(String.format("%,.2f",Double.valueOf(app41201Result.getLoanamt())));
		//app41201Result.setLoanbal(String.format("%,.2f",Double.valueOf(app41201Result.getLoanbal())));
		
		//List<TitleInfoBean> appapi41201Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi41201"+form.getCenterId()+".result", app41201Result);
		AES aes = new AES();
		JSONObject obj = new JSONObject();
		obj.put("signType",app41201Result.getSignType());
		obj.put("surplusAccount",app41201Result.getSurplusAccount());
		obj.put("accname",app41201Result.getAccname());
		obj.put("idcard",app41201Result.getIdcard());
		obj.put("signPhoneNumber",app41201Result.getSignPhoneNumber());
		obj.put("endDate",app41201Result.getEndDate());
		obj.put("loanaccnum",aes.encrypt(app41201Result.getLoanaccnum().getBytes("UTF-8")));
		obj.put("signResultStatus",app41201Result.getSignResultStatus());
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", obj);
		log.info(Constants.LOG_HEAD+"appApi41202 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi41202Form form1 = new AppApi41202Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setSignStatus("01");
		form1.setSignType("01");
		form1.setLoanaccnum("");
		form1.setSignPhoneNumber("1333333333");
		form1.setCenterId("00053100");
		Handle41202_00053100 hand = new Handle41202_00053100();
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
