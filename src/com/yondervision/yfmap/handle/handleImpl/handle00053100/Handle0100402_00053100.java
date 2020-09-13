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
import com.yondervision.yfmap.form.AppApi01003Form;
import com.yondervision.yfmap.form.AppApi01004Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi01003Result;
import com.yondervision.yfmap.result.jinan.JiNanAppApi01004Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 提取签约-委托提取签约
 */
public class Handle0100402_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01004Form form = (AppApi01004Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		JiNanAppApi01004Result app01004Result= new JiNanAppApi01004Result();
		
		if(Constants.method_XML.equals(send)){
			AES aes = new AES();
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setBankaccnum(aes.decrypt(form.getBankaccnum()));
			form.setLoannum(aes.decrypt(form.getLoannum()));
			if("01".equals(form.getSignType()))
			{
				form.setSignType("1");
			}
			else if("02".equals(form.getSignType()))
			{
				form.setSignType("2");
			}
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());			
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
//			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
//			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//			if(!file.exists()){
//				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYECX.xml");
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "公积金账号对应文件不存在");
//				return modelMap;
//			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_TQQY02.xml", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_TQQY02.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000015");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>75</msgtype><tr_code>X00005</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><xm>夏洛</xm><zjlx>身份证</zjlx><zjhm>220001197701011234</zjhm><gfdz>梧桐花园88号</gfdz><dkje>500000.00</dkje><nx>30</nx><hkfs>等额本金</hkfs><dkye>400000.00</dkye><zkyh>建行</zkyh><yhhkzh>2012748300917887</yhhkzh><htdqrq>2025-12-31</htdqrq><cqzbz>未入库</cqzbz><czrq>2015-01-01</czrq></body></root>";
			
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>85</msgtype><tr_code>X00015</tr_code><corp_no>00053100</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2016-05-17</tr_acdt>" +
//					"<tr_time>10:00:00</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>签约成功</ans_info>" +
//					"<particular_code></particular_code><particular_info></particular_info><reserved></reserved></head>" +
//					"<body>" +
//					"<tqlx>"+form.getDrawSignType()+"</tqlx>" +
//					"</body></root>";

			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_TQQY02.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_TQQY02.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app01004Result);
			
			
			if(!Constants.sucess.equals(app01004Result.getRecode())){
				modelMap.put("recode", app01004Result.getRecode());
				modelMap.put("msg", app01004Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01004Result.getRecode()+"  ,  描述msg : "+app01004Result.getMsg());
				return modelMap;
			}
		}		
		
		//app41201Result.setLoanamt(String.format("%,.2f",Double.valueOf(app41201Result.getLoanamt())));
		//app41201Result.setLoanbal(String.format("%,.2f",Double.valueOf(app41201Result.getLoanbal())));
		
		//List<TitleInfoBean> appapi01003Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01003"+form.getCenterId()+".result", app01003Result);
		JSONObject obj = new JSONObject();
		obj.put("drawSignType", app01004Result.getDrawSignType());
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", obj);
		log.info(Constants.LOG_HEAD+"appApi01004 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi01004Form form1 = new AppApi01004Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setLoanflag("01");
		form1.setLoannum("62213134324");
		form1.setBankaccnum("62212421");
		form1.setDrawSignType("01");
		form1.setSignType("02");
		form1.setCheckid("1332");
		form1.setCenterId("00053100");
		Handle0100402_00053100 hand = new Handle0100402_00053100();
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
