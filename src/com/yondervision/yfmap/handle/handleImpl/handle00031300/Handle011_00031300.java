package com.yondervision.yfmap.handle.handleImpl.handle00031300;

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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * @author fengjing
 * 个人贷款进度查询
 */
public class Handle011_00031300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		List<AppApi011Result> list = new ArrayList<AppApi011Result>();
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();
		AppApi01101Result app011Result = new AppApi01101Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKJDCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_DKJDCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00006");
			//String rexml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><root><head><reqflag>1</reqflag><msgtype>76</msgtype><tr_code>X00006</tr_code><corp_no>00031300</corp_no><oreq_no>20170426180357865</oreq_no><tr_acdt>2017-04-26</tr_acdt><tr_time>18:03:57</tr_time><ans_code>0</ans_code><ans_info>交易处理成功...</ans_info><particular_code>0000</particular_code><particular_info>交易处理成功...</particular_info><reserved></reserved></head><body><sqrq>2016-04-08</sqrq><dqdkjdbs>09</dqdkjdbs><dqdkjdms>放款</dqdkjdms><detail><dkjdbs>00</dkjdbs><dkjdms>申请</dkjdms></detail><detail><dkjdbs>01</dkjdbs><dkjdms>审批</dkjdms></detail><detail><dkjdbs>02</dkjdbs><dkjdms>合同签订</dkjdms></detail><detail><dkjdbs>06</dkjdbs><dkjdms>贷款开户</dkjdms></detail><detail><dkjdbs>07</dkjdbs><dkjdms>担保办理</dkjdms></detail><detail><dkjdbs>08</dkjdbs><dkjdms>通知放款</dkjdms></detail><detail><dkjdbs>09</dkjdbs><dkjdms>放款</dkjdms></detail></body></root>";
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKJDCX.xml", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300/REP_DKJDCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			if(!Constants.sucess_ts.equals(app011Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询公积金贷款进度，文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi011Result aa01101Result = new AppApi011Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);									
						aa01101Result.setApprflagID(valus[0]);
						aa01101Result.setApprflagMSG(valus[1]);	
						//aa01101Result.setApprflagDate(valus[2]);	
						list.add(aa01101Result);
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
				modelMap.put("msg", "贷款进度信息处理异常");
				return modelMap;
			}			
		}	
		
		String temp = "";
		for(int i=0;i<list.size();i++)
		{
			AppApi011Result aa01101Result = new AppApi011Result();
			if(app011Result.getApprflag().equals(aa01101Result.getApprflagID()))
			{
				temp = i + aa01101Result.getApprflagID();
				app011Result.setApprflag(temp);
			}
			aa01101Result.setApprflagID(i+aa01101Result.getApprflagID());
			log.debug("aa01101Result.getApprflagID()："+aa01101Result.getApprflagID());		
		}
		log.debug("app011Result.getApprflag()："+app011Result.getApprflag());		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", app011Result.getApprflag());
		modelMap.put("apprflagMSG", app011Result.getApprflagMSG());
		modelMap.put("apprflagDate", app011Result.getApprfDate());
		modelMap.put("result", list);	
		return modelMap;
	}

	
	public static void main(String[] args){
		AppApi00501Form form1 = new AppApi00501Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setPagenum("0");
		form1.setPagerows("10");
		form1.setStartdate("2015-01-01");
		form1.setEnddate("2015-10-01");
		Handle011_00031300 hand = new Handle011_00031300();
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
