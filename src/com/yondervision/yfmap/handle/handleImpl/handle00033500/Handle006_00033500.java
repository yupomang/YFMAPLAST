package com.yondervision.yfmap.handle.handleImpl.handle00033500;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle006_00033500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		Date date = new Date();
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		/* 模拟返回开始  */
		AppApi00601Result app00601Result= new AppApi00601Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());
			
			log.debug("文件名：REQ_DKYECX_"+form.getSurplusAccount()+".xml");
			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml");
			if(!file.exists()){
				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml");
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "公积金账号对应文件不存在");
				return modelMap;
			}
			
			AppApi40102Form form500 = new AppApi40102Form();
			form500.setSendDate(formatter1.format(date));
			form500.setSendTime(formatter2.format(date));
			form500.setSurplusAccount(form.getSurplusAccount());
			form500.setNewpassword("NULL");
			form500.setBodyCardNumber(form.getBodyCardNumber());
			WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
			HashMap map500 = BeanUtil.transBean2Map(form500);
			String xml500 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DL.xml", map500, req);
			xml500 = xml500.substring(37);
			log.debug("前置YFMAP发往中心报文："+xml500);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml500 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml500, "700001").trim();
			rexml500 = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+rexml500;
			log.debug("前置YFMAP接收中心报文："+rexml500);
			HashMap resultMap500 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DL.xml", rexml500, req);
			log.debug("解析报文MAP："+resultMap500);
			BeanUtil.transMap2Bean(resultMap500, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);			
			if(!"00".equals(app40102Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}			
			
			if("null".equals(app40102Result.getAccinstcode())){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无贷款");
				log.error("中心返回报文 该用户无贷款");
				return modelMap;
			}
			form.setFreeuse1(app40102Result.getAccinstcode());
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_DKYE.xml", map, req);
			xml = xml.substring(37);
			log.debug("发往中心报文："+xml);
			
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "700004").trim();
			rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+rexml;
//			String rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><tr_code>700001</tr_code><corp_no>00000001</corp_no><req_no>10000000001</req_no><serial_no>1</serial_no><ans_no>1</ans_no><next_no>1</next_no><tr_acdt>1</tr_acdt><tr_time>1</tr_time><ans_code>00</ans_code><ans_info>111</ans_info><particular_code>1</particular_code><atom_tr_count></atom_tr_count><reserved>1</reserved><particular_info>1</particular_info></head><body><curbal>111</curbal><accname>222</accname><certinum>132201197012242868</certinum><cleardate>333</cleardate><loanamt>444</loanamt><loandate>555</loandate><loanstate>1</loanstate>	<loanterm>666</loanterm><remainterms>777</remainterms><repayday>888</repayday><repayint>999</repayint><repayprin>10</repayprin><loanaccnum>11</loanaccnum><dedaccnum>12</dedaccnum><loancontrnum>13</loancontrnum><oweamt>14</oweamt><repaypun>15</repaypun><loanrate>16</loanrate><owepun>17</owepun><terms>18</terms></body></root>";
			log.debug("接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKYE.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);						
			
			if(!"00".equals(app00601Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			if(app00601Result.getLoanrate()!=null){
				app00601Result.setLoanrate(String.format("%,.2f",Double.valueOf(app00601Result.getLoanrate()))+"%");
			}
			if(app00601Result.getOverdueamt()!=null){
				if("1899-12-31".equals(app00601Result.getOverdueamt())){
					app00601Result.setOverdueamt("--");
				}
			}
			
			
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("detail", appapi00101Detail);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

}
