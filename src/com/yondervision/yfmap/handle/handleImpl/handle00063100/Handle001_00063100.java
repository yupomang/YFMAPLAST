package com.yondervision.yfmap.handle.handleImpl.handle00063100;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle001_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40101Result = new WeiHaiAppApi40101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_DL.txt", map, req);
			log.debug("前置3发往中心报文："+xml);
			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600002");
			
			
//			String rexml = "<TranCode>600002</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:24</><MTimeStamp>2014-05-13 09:33:24</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>1231</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>12311231</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><CertiNum>370620196207240511</><AccNum>00265744</><AccName>迟健</><OpenDate>1996-07-01</><Balance>37889.55</><PerAccState>0</><LastPayDate>2014-03-01</><UnitAccNum>000341</><UnitAccName>威海市旅游码头有限责任公司</><Sex>0</><Age>52</><MonthPayAmt>660.00</><UnitChar>5</><UnitPhone>5221847</><UnitAddr>海滨北路55#</><AccInstCode>00063100</><BaseNumber>2750.00</><IndiProp>0.120</><UnitProp>0.120</><IncreBal>1980.00</><YDrawAmt>0.00</><KeepBal>35909.55</><AccInstName>市区管理部</><IsLoanFlag>1</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_DL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}

			String state = PropertiesReader.getProperty("yingshe.properties", "Peraccstate"+app40101Result.getPeraccstate()).trim();
			if(state!=null||!state.equals("")){
				app40101Result.setPeraccstate(state);
			}
			String sex =  PropertiesReader.getProperty("yingshe.properties", "Sex"+app40101Result.getSex()).trim();
			if(sex!=null||!sex.equals("")){
				app40101Result.setSex(sex);
			}
			String isloanflag = PropertiesReader.getProperty("yingshe.properties", "Isloanflag"+app40101Result.getIsloanflag()).trim();
			if(isloanflag!=null||!isloanflag.equals("")){
				app40101Result.setIsloanflag(isloanflag);
			}
			app40101Result.setIndiprop(String.valueOf(Double.valueOf(app40101Result.getIndiprop())*100).substring(0,2)+"%");
			app40101Result.setUnitprop(String.valueOf(Double.valueOf(app40101Result.getUnitprop())*100).substring(0,2)+"%");
			app40101Result.setBalance(String.format("%,.2f",Double.valueOf(app40101Result.getBalance())));
			app40101Result.setMonthpayamt(String.format("%,.2f",Double.valueOf(app40101Result.getMonthpayamt())));
			app40101Result.setBasenumber(String.format("%,.2f",Double.valueOf(app40101Result.getBasenumber())));
			app40101Result.setYdrawamt(String.format("%,.2f",Double.valueOf(app40101Result.getYdrawamt())));
			app40101Result.setKeepbal(String.format("%,.2f",Double.valueOf(app40101Result.getKeepbal())));
			/**原-----
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType").trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key").trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type").trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YECX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YECX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			log.debug("MAP封装成BEAN："+app00101Result);
			if(!Constants.sucess.equals(app00101Result.getRecode())){
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return "";
			}
			*/
		}
		
		List<TitleInfoBean> appapi40101Result = null;
		/**原-----
		if(app00101Result.getFrozenAmount().equals("")||app00101Result.getFrozenDate().equals("")){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101.resultNoF", app00101Result);
		}else{
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101.result", app00101Result);
		}
		*/
		//以下新加201405
		appapi40101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app40101Result);
		List<TitleInfoBean> appapi40101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app40101Result);
		
		
		Iterator<TitleInfoBean> it1 = appapi40101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		Iterator<TitleInfoBean> it2 = appapi40101Detail.iterator();
		while (it2.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it2.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40101Result);
		modelMap.put("detail", appapi40101Detail);
		return modelMap;
	}

}
