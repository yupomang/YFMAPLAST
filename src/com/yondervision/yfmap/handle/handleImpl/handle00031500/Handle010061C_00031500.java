package com.yondervision.yfmap.handle.handleImpl.handle00031500;

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
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi01003Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi01006Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 提取信息查询-1C-首付
 */
public class Handle010061C_00031500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01003Form form = (AppApi01003Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		TangShanAppApi01006Result app01003Result= new TangShanAppApi01006Result();
		
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			if("10".equals(form.getChannel()))
			{
				form.setTellCode("8601");
				form.setBrcCode("00031586");
			}else if("20".equals(form.getChannel()))
			{
				form.setTellCode("8501");
				form.setBrcCode("00031585");
			}else
			{
				form.setTellCode("00031500");
				form.setBrcCode("8888");
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_TQQYCX.xml", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_TQYYCX02.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000013");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>75</msgtype><tr_code>X00005</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><xm>夏洛</xm><zjlx>身份证</zjlx><zjhm>220001197701011234</zjhm><gfdz>梧桐花园88号</gfdz><dkje>500000.00</dkje><nx>30</nx><hkfs>等额本金</hkfs><dkye>400000.00</dkye><zkyh>建行</zkyh><yhhkzh>2012748300917887</yhhkzh><htdqrq>2025-12-31</htdqrq><cqzbz>未入库</cqzbz><czrq>2015-01-01</czrq></body></root>";
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_TQQYCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/server/apache-tomcat-6.0.44/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_TQYYCX02.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app01003Result);
			
			
			if(!Constants.sucess_ts.equals(app01003Result.getRecode())){
				modelMap.put("recode", app01003Result.getRecode());
				modelMap.put("msg", app01003Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01003Result.getRecode()+"  ,  描述msg : "+app01003Result.getMsg());
				return modelMap;
			}
		}		
		
		app01003Result.setBuyhousesum(String.format("%.2f",Double.valueOf(app01003Result.getBuyhousesum()))+"元");
		app01003Result.setYe(String.format("%.2f",Double.valueOf(app01003Result.getYe()))+"元");
		app01003Result.setTqje(String.format("%.2f",Double.valueOf(app01003Result.getTqje()))+"元");
		app01003Result.setHousemj(String.format("%.2f",Double.valueOf(app01003Result.getHousemj()))+"㎡");
		//app41201Result.setLoanamt(String.format("%,.2f",Double.valueOf(app41201Result.getLoanamt())));
		//app41201Result.setLoanbal(String.format("%,.2f",Double.valueOf(app41201Result.getLoanbal())));
		
		List<TitleInfoBean> m_appapi01006Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0100605"+form.getCenterId()+".m_result", app01003Result);
		List<TitleInfoBean> h_appapi01006Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi010061C"+form.getCenterId()+".h_result", app01003Result);
		List<TitleInfoBean> p_appapi01006Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi0100601"+form.getCenterId()+".p_result", app01003Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("h_result", h_appapi01006Result);
		modelMap.put("m_result", m_appapi01006Result);
		modelMap.put("p_result", p_appapi01006Result);
		log.info(Constants.LOG_HEAD+"appApi01006 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi01003Form form1 = new AppApi01003Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setDrawSignType("01");
		form1.setCenterId("00053100");
		Handle010061C_00031500 hand = new Handle010061C_00031500();
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
