package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi02001ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle02001_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		
		log.debug("00057400请求02001参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		AppApi02001ZHResult app02001ZHResult = new AppApi02001ZHResult();

		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes = new AES();
			if(!CommonUtil.isEmpty(form.getPwd())){
				aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);	
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("60")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			log.debug("解密后form.getClientIp():"+form.getClientIp());
			
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(!CommonUtil.isEmpty(form.getPwd())){
				String pwd = "";
				//针对住建委网站加密问题修改 xzw
				if(!"1".equals(form.getAESFlag())&&!"30".equals(form.getChannel())){
					pwd = aes.decrypt(form.getPwd());
				}else{
					pwd = form.getPwd();
				}
				if(!CommonUtil.isEmpty(pwd)){
					byte[] c = DESForJava.encryption(pwd,"12345678");
					c = DESForJava.bcd_to_asc(c);
					form.setPwd(new String(c));
				}else{
					form.setPwd("");
				}
			}
			
			HashMap map = BeanUtil.transBean2Map(form);	
			if(!CommonUtil.isEmpty(form.getChannel())){
				map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			}else{
				map.put("tellcode", "9777");
			}
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWYEXXCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWYEXXCX.txt", map, req);			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142507");
			
			//String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>96978</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2017-08-29 20:23:23</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-29 20:23:23</><TellCode>cweb</><TranChannel>00</><TranCode>142507</><TranDate>2017-08-25</><TranIP>36.23.114.149</><TranSeq>96978</><accinstcode>05740008</><accinstcodedes>市本级</><amt_wb1>125642.00</><amt_wb10>0.00</><amt_wb11>1200.00</><amt_wb12>0.00</><amt_wb13>0.00</><amt_wb14>0.00</><amt_wb15>12.00</><amt_wb16>0.00</><amt_wb2>0.00</><amt_wb3>3820.00</><amt_wb4>2280.00</><amt_wb5>0.00</><amt_wb6>0.00</><amt_wb7>0.00</><amt_wb8>0.00</><amt_wb9>22951.00</><bal>28003.48</><freeuse1>正常</><indiprop>0.120</><indiprop2>0.00</><indiprop3>0.00</><indiprop4>0.00</><keepbal2>0.00</><keepbal3>2720.01</><keepbal4>0.00</><num_web1>30</><num_web10>0</><num_web11>2</><num_web12>0</><num_web13>0</><num_web14>0</><num_web15>1</><num_web16>0</><num_web2>0</><num_web3>3</><num_web4>1</><num_web5>0</><num_web6>0</><num_web7>11</><num_web8>0</><num_web9>8</><paybegym>201708</><unitaccname>宁波市住房公积金管理中心</><unitaccnum>011500001167</><unitaccstate>0</><unitprop>0.120</><unitprop2>0.00</><unitprop3>0.00</><unitprop4>0.00</>";

			log.debug("前置YFMAP接收中心报文——单位基本信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWYEXXCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWYEXXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02001ZHResult);
			log.debug("MAP封装成BEAN："+app02001ZHResult);
			if(!"0".equals(app02001ZHResult.getRecode() )){
				modelMap.clear();
				modelMap.put("recode", app02001ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02001ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02001ZHResult.getRecode()+"  ,  描述msg : "+app02001ZHResult.getMsg());
				return modelMap;
			}
		}
		
		String unitaccstate = PropertiesReader.getProperty("yingshe.properties", "unitaccstateType"+app02001ZHResult.getUnitaccstate()+form.getCenterId());
		if(unitaccstate == null||unitaccstate.equals("")){
			app02001ZHResult.setUnitaccstate("--");
		}else {
			app02001ZHResult.setUnitaccstate(unitaccstate);
		}
		
		List<TitleInfoNameFormatBean> appapi02001ZHResult = null;		
		appapi02001ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02001"+form.getCenterId()+".result", app02001ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi02001ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean titleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+titleInfoNameFormatBean.getTitle()+"\tinfo="+titleInfoNameFormatBean.getInfo());
		}
		if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
		{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");		
			modelMap.put("result", appapi02001ZHResult);		
		}else{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");			
			/** 机构代码*/
			modelMap.put("accinstcode",app02001ZHResult.getAccinstcode());
			/** 机构名称*/
			modelMap.put("accinstcodedes",app02001ZHResult.getAccinstcodedes());
			/** 一般户上月汇缴金额*/
			modelMap.put("amt_wb1",app02001ZHResult.getAmt_wb1());
			/** 一般户本月增加汇缴金额*/
			modelMap.put("amt_wb2",app02001ZHResult.getAmt_wb2());
			/** 一般户本月减少汇缴金额*/
			modelMap.put("amt_wb3",app02001ZHResult.getAmt_wb3());
			/** 一般户正常汇缴金额*/
			modelMap.put("amt_wb4",app02001ZHResult.getAmt_wb4());
			/** 一次性住房补贴户正常汇缴金额*/
			modelMap.put("amt_wb5",app02001ZHResult.getAmt_wb5());
			/** 一次性住房补贴户本月增加汇缴金额*/
			modelMap.put("amt_wb6",app02001ZHResult.getAmt_wb6());
			/** 一次性住房补贴户本月减少汇缴金额*/
			modelMap.put("amt_wb7",app02001ZHResult.getAmt_wb7());
			/** 一次性住房补贴户上月汇缴金额*/
			modelMap.put("amt_wb8",app02001ZHResult.getAmt_wb8());
			/** 按月补贴户上月汇缴金额*/
			modelMap.put("amt_wb9",app02001ZHResult.getAmt_wb9());
			/** 按月补贴户本月增加汇缴金额*/
			modelMap.put("amt_wb10",app02001ZHResult.getAmt_wb10());
			/** 按月补贴户本月减少汇缴金额*/
			modelMap.put("amt_wb11",app02001ZHResult.getAmt_wb11());
			/** 按月补贴户正常汇缴金额*/
			modelMap.put("amt_wb12",app02001ZHResult.getAmt_wb12());
			/** 补充公积金户上月汇缴金额*/
			modelMap.put("amt_wb13",app02001ZHResult.getAmt_wb13());
			/** 补充公积金户本月增加汇缴金额*/
			modelMap.put("amt_wb14",app02001ZHResult.getAmt_wb14());
			/** 补充公积金户本月减少汇缴金额*/
			modelMap.put("amt_wb15",app02001ZHResult.getAmt_wb15());
			/** 补充公积金户正常汇缴金额*/
			modelMap.put("amt_wb16",app02001ZHResult.getAmt_wb16());
			/** 单位一般户余额*/
			modelMap.put("bal",app02001ZHResult.getBal());
			/** 单位账户状态描述*/
			modelMap.put("freeuse1",app02001ZHResult.getFreeuse1());
			/** 单位一次性住房补贴户余额*/
			modelMap.put("keepbal2",app02001ZHResult.getKeepbal2());
			/** 单位按月补贴户余额*/
			modelMap.put("keepbal3",app02001ZHResult.getKeepbal3());
			/** 单位补充公积金户余额*/
			modelMap.put("keepbal4",app02001ZHResult.getKeepbal4());
			/** 一般户上月汇缴人数*/
			modelMap.put("num_web1",app02001ZHResult.getNum_web1());
			/** 一般户本月增加汇缴人数*/
			modelMap.put("num_web2",app02001ZHResult.getNum_web2());
			/** 一般户本月减少汇缴人数*/
			modelMap.put("num_web3",app02001ZHResult.getNum_web3());
			/** 一般户正常汇缴人数*/
			modelMap.put("num_web4",app02001ZHResult.getNum_web4());
			/** 一次性住房补贴户正常汇缴人数*/
			modelMap.put("num_web5",app02001ZHResult.getNum_web5());
			/** 一次性住房补贴户本月增加汇缴人数*/
			modelMap.put("num_web6",app02001ZHResult.getNum_web6());
			/** 一次性住房补贴户本月减少汇缴人数*/
			modelMap.put("num_web7",app02001ZHResult.getNum_web7());
			/** 一次性住房补贴户上月汇缴人数*/
			modelMap.put("num_web8",app02001ZHResult.getNum_web8());
			/** 按月补贴户上月汇缴人数*/
			modelMap.put("num_web9",app02001ZHResult.getNum_web9());
			/** 按月补贴户本月增加汇缴人数*/
			modelMap.put("num_web10",app02001ZHResult.getNum_web10());
			/** 按月补贴户本月减少汇缴人数*/
			modelMap.put("num_web11",app02001ZHResult.getNum_web11());
			/** 按月补贴户正常汇缴人数*/
			modelMap.put("num_web12",app02001ZHResult.getNum_web12());
			/** 补充公积金户上月汇缴人数*/
			modelMap.put("num_web13",app02001ZHResult.getNum_web13());
			/** 补充公积金户本月增加汇缴人数*/
			modelMap.put("num_web14",app02001ZHResult.getNum_web14());
			/** 补充公积金户本月减少汇缴人数*/
			modelMap.put("num_web15",app02001ZHResult.getNum_web15());
			/** 补充公积金户正常汇缴人数*/
			modelMap.put("num_web16",app02001ZHResult.getNum_web16());
			/** 缴存开始年月*/
			modelMap.put("paybegym",app02001ZHResult.getPaybegym());
			/** 单位名称*/
			modelMap.put("unitaccname",app02001ZHResult.getUnitaccname());
			/** 单位账号*/
			modelMap.put("unitaccnum",app02001ZHResult.getUnitaccnum());
			/** 单位账户状态*/
			modelMap.put("unitaccstate",app02001ZHResult.getUnitaccstate());
			/** 一般户单位比例*/
			modelMap.put("unitprop",app02001ZHResult.getUnitprop());		
			/** 一次性住房补贴户单位比例*/
			modelMap.put("unitprop2",app02001ZHResult.getUnitprop2());			
			/** 按月补贴户单位比例*/
			modelMap.put("unitprop3",app02001ZHResult.getUnitprop3());		
			/** 补充公积金户单位比例*/
			modelMap.put("unitprop4",app02001ZHResult.getUnitprop4());						
			/** 一般户个人比例*/
			modelMap.put("indiprop",app02001ZHResult.getIndiprop());			
			/** 一次性住房补贴户个人比例*/
			modelMap.put("indiprop2",app02001ZHResult.getIndiprop2());		
			/** 按月补贴户个人比例*/
			modelMap.put("indiprop3",app02001ZHResult.getIndiprop3());		
			/** 补充公积金户个人比例*/
			modelMap.put("indiprop4",app02001ZHResult.getIndiprop4());	
			/** 个人缴交最高工资*/
			modelMap.put("maxbasenum_eb",app02001ZHResult.getMaxbasenum_eb());		
			/** 个人缴交最低工资*/
			modelMap.put("minbasenum_eb",app02001ZHResult.getMinbasenum_eb());	
		}
		return modelMap;
	}
/*	public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setUnitaccnum("11111");
		form1.setPwd("123456");
		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle02001_00057400 hand = new Handle02001_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
