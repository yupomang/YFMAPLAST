package com.yondervision.yfmap.handle.handleImpl.handle00031500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.yondervision.yfmap.result.AppApi00201Result;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00601Result;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 贷款合同号查询
 */
public class Handle00604_00031500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		DaLianAppApi00601Result app00601Result= new DaLianAppApi00601Result();
		List<AppApi00601Result> list = new ArrayList<AppApi00601Result>();
		
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKHTHCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_DKYECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00022");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>75</msgtype><tr_code>X00005</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><xm>夏洛</xm><zjlx>身份证</zjlx><zjhm>220001197701011234</zjhm><gfdz>梧桐花园88号</gfdz><dkje>500000.00</dkje><nx>30</nx><hkfs>等额本金</hkfs><dkye>400000.00</dkye><zkyh>建行</zkyh><yhhkzh>2012748300917887</yhhkzh><htdqrq>2025-12-31</htdqrq><cqzbz>未入库</cqzbz><czrq>2015-01-01</czrq></body></root>";
			
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKHTHCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_DKYECX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			
			if(!Constants.sucess_ts.equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询贷款合同号，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00601Result aa00601Result = new AppApi00601Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						aa00601Result.setLoanaccnum(valus[0]);
						list.add(aa00601Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "合同号信息处理异常");
				return modelMap;
			}
		}
		List<TitleInfoBean> result = new ArrayList();
		List<TitleInfoBean> appapi00604Result = null;
		for(int i=0;i<list.size();i++){
			appapi00604Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00604"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00604Result.get(0));
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD+"appApi00604 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi00601Form form1 = new AppApi00601Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		Handle00604_00031500 hand = new Handle00604_00031500();
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
