package com.yondervision.yfmap.handle.handleImpl.handle00083800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle011_00083800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		/* 模拟返回开始  */		
		AppApi00501Form form = (AppApi00501Form)form1;
		/****************************  肇庆 201407 开始*/
		AppApi01101Result appapi001 = new AppApi01101Result();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101Result app011Result = new AppApi01101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKJDCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430026");
			
//			String reqxml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><transdate>2015-01-01</transdate><apprflag>06</apprflag><apprmsg>dage</apprmsg><freeuse1></freeuse1><freeuse2></freeuse2><freeuse3></freeuse3></body></root>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKJDCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			
			
			if(!"0".equals(app011Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
			
		}

		ArrayList<AppApi011Result> list = new ArrayList<AppApi011Result>();
		String num = "";
		String val = "";
		for(int i=1;i<11;i++){
			if(i<10){
				num = "0"+i;
			}else{
				num = ""+i;
			}
			if(PropertiesReader.getProperty("yingshe.properties", "Apprflag"+num+form.getCenterId())==null)
				continue;
			val = PropertiesReader.getProperty("yingshe.properties", "Apprflag"+num+form.getCenterId()).trim();
			if(list.size()==0){				
				AppApi011Result app011 = new AppApi011Result();
				app011.setApprflagID(num);
				app011.setApprflagMSG(val);
				app011.setApprflagDate("2014-01-11");
				list.add(app011);				
				System.out.println("状态码:"+num+"   描述:"+val+"  时间：2014-01-11");
			}
			boolean isflag = false;
			if(!val.equals("")){
				for(int j=0;j<list.size();j++){
					System.out.println("list.get(j).getApprflagMSG():"+list.get(j).getApprflagMSG());
					System.out.println("val:"+val);
					if(list.get(j).getApprflagMSG().equals(val)){						
						isflag = true;
						break;
					}
				}
				if(isflag==false){
					AppApi011Result app011 = new AppApi011Result();
					app011.setApprflagID(num);
					app011.setApprflagMSG(val);
					app011.setApprflagDate("2013-01-11");
					list.add(app011);					
					System.out.println("状态码:"+num+"   描述:"+val+"  时间：2014-01-11");
				}
			}			
		}
		
		/*原 201405 结束***************************************************************************/
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("apprflagID", app011Result.getApprflag());
		modelMap.put("apprflagMSG", app011Result.getApprflagMSG());
		modelMap.put("apprflagDate", app011Result.getApprfDate());
		modelMap.put("result", list);	
		
		return modelMap;
	}

}
