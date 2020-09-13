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
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle001_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("00057400请求00101参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();

		if(Constants.method_BSP.equals(send)){////xml通信处理
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
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("50")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("60")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("53")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(!CommonUtil.isEmpty(form.getPwd())){
				String pwd = "";
				//针对住建委网站加密问题修改 xzw
				if("1".equals(form.getAESFlag())||"30".equals(form.getChannel())){
					pwd = form.getPwd();
				}else{
					pwd = aes.decrypt(form.getPwd());
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
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZHJBXXCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRZHJBXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142503");
			
			/*String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</>"
					+ "<accnum>11111</><accname>0237550054</><certinum>341201198001200016</><actmp100>1</><actmp1024>2</><actmp200>3</><amt>4</><amt3>5</><amt7>6</><amt8>7</>"
					+ "<bal>8</><bankname>9</><bankcode>10</><basenum>11</><begdate>12</><cardstate>13</><certitype>14</><freeuse1>15</><frzamt>16</><frzflag>17</><handset>18</>"
					+ "<indiaccstate>19</><lpaym>20</><monintamt>21</><monpaysum>22</><subintamt>23</><supintamt>24</><unitaccname>25</><accinstcode>26</><accinstcodedes>27</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZHJBXXCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRZHJBXXCX.txt", rexml, req);

			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00101ZHResult);
			if(!"0".equals(app00101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00101ZHResult.getRecode());
				modelMap.put("msg", app00101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00101ZHResult.getRecode()+"  ,  描述msg : "+app00101ZHResult.getMsg());
				return modelMap;
			}
		}
		
		List<TitleInfoNameFormatBean> appapi00101ZHResult = null;
		/*if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
			appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result", app00101ZHResult);
			Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
				log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00101ZHResult);
		}else {
			if(!CommonUtil.isEmpty(app00101ZHResult.getFrzflag())){
				if ("0".equals(app00101ZHResult.getFrzflag())) {
					app00101ZHResult.setFrzflag("否");
				} else {
					app00101ZHResult.setFrzflag("是");
				}
			}
			if(!CommonUtil.isEmpty(app00101ZHResult.getSjhm())){
				app00101ZHResult.setSjhm(Tools.getDesensitizationTel(app00101ZHResult.getSjhm()));
			}*/
			String cardstate = PropertiesReader.getProperty("yingshe.properties", "cardstateType"+app00101ZHResult.getCardstate()+form.getCenterId());
			if(cardstate == null||cardstate.equals("")){
				app00101ZHResult.setCardstate("--");
			}else {
				app00101ZHResult.setCardstate(cardstate);
			}
			
			String certinumType = PropertiesReader.getProperty("yingshe.properties", "certinumType"+app00101ZHResult.getCertitype()+form.getCenterId());
			if(certinumType == null||certinumType.equals("")){
				app00101ZHResult.setCertitype("--");
			}else {
				app00101ZHResult.setCertitype(certinumType);
			}
			
			String frzflag = PropertiesReader.getProperty("yingshe.properties", "frzflagType"+app00101ZHResult.getFrzflag()+form.getCenterId());
			if(frzflag == null||frzflag.equals("")){
				app00101ZHResult.setFrzflag("--");
			}else {
				app00101ZHResult.setFrzflag(frzflag);
			}
			
			String indiaccstate = PropertiesReader.getProperty("yingshe.properties", "indiaccstateType"+app00101ZHResult.getIndiaccstate()+form.getCenterId());
			if(indiaccstate == null||indiaccstate.equals("")){
				app00101ZHResult.setIndiaccstate("--");
			}else {
				app00101ZHResult.setIndiaccstate(indiaccstate);
			}
			
			//脱敏app 微信
			if("10".equals(form.getChannel()) || "20".equals(form.getChannel())
					|| "30".equals(form.getChannel()) || "50".equals(form.getChannel()) || "53".equals(form.getChannel())){
				app00101ZHResult.setAccname("*"+app00101ZHResult.getAccname().substring(1));
				String certinum = app00101ZHResult.getCertinum();
				int len = certinum.length();
				String star = "";
				if(len>=7){
					for(int i=0; i<len-7; i++){
						star += "*";
					}
					app00101ZHResult.setCertinum(certinum.substring(0, 3)+star+certinum.substring(len-4));
				}	
				String handset = app00101ZHResult.getHandset();
				len = handset.length();
				star = "";
				if(len>=7){
					for(int i=0; i<len-7; i++){
						star += "*";
					}
					app00101ZHResult.setHandset(handset.substring(0, 3)+star+handset.substring(len-4));
				}
			}
			if("10".equals(form.getChannel()) || "20".equals(form.getChannel())){
				appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101_1"+form.getCenterId()+".result", app00101ZHResult);
			}else{
				appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101"+form.getCenterId()+".result", app00101ZHResult);
			}
			Iterator<TitleInfoNameFormatBean> it1 = appapi00101ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
				log.info ("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
			}
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");		
				modelMap.put("result", appapi00101ZHResult);		
			}else{
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");			
				modelMap.put("accname", app00101ZHResult.getAccname());
				modelMap.put("certinum", app00101ZHResult.getCertinum());
				modelMap.put("actmp100", app00101ZHResult.getActmp100());
				modelMap.put("actmp1024", app00101ZHResult.getActmp1024());
				modelMap.put("actmp200", app00101ZHResult.getActmp200());
				modelMap.put("amt", app00101ZHResult.getAmt());
				modelMap.put("amt3", app00101ZHResult.getAmt3());
				modelMap.put("amt7", app00101ZHResult.getAmt7());
				modelMap.put("amt8", app00101ZHResult.getAmt8());
				modelMap.put("bal", app00101ZHResult.getBal());
				modelMap.put("bankname", app00101ZHResult.getBankname());
				modelMap.put("bankcode", app00101ZHResult.getBankcode());
				modelMap.put("basenum", app00101ZHResult.getBasenum());
				modelMap.put("begdate", app00101ZHResult.getBegdate());
				modelMap.put("cardstate", app00101ZHResult.getCardstate());
				modelMap.put("certitype", app00101ZHResult.getCertitype());
				modelMap.put("freeuse1", app00101ZHResult.getFreeuse1());
				modelMap.put("frzamt", app00101ZHResult.getFrzamt());
				modelMap.put("frzflag", app00101ZHResult.getFrzflag());
				modelMap.put("handset", app00101ZHResult.getHandset());
				modelMap.put("indiaccstate", app00101ZHResult.getIndiaccstate());
				modelMap.put("lpaym", app00101ZHResult.getLpaym());
				modelMap.put("monintamt", app00101ZHResult.getMonintamt());
				modelMap.put("monpaysum", app00101ZHResult.getMonpaysum());
				modelMap.put("subintamt", app00101ZHResult.getSubintamt());
				modelMap.put("supintamt", app00101ZHResult.getSupintamt());
				modelMap.put("unitaccname", app00101ZHResult.getUnitaccname());
				modelMap.put("accinstcode", app00101ZHResult.getAccinstcode());
				modelMap.put("accinstcodedes", app00101ZHResult.getAccinstcodedes());
			}

		return modelMap;
	}
	/*public static void main(String[] args){
		AppApi00101Form form1 = new AppApi00101Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBodyCardNumber("999999888888");
		form1.setAccnum("1234567890");
		form1.setPwd("123456");
		try{
			AES aes = new AES("00073300" ,"40" ,null ,null);
			form1.setPwd(aes.encrypt("123456".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
	
		Handle001_00057400 hand = new Handle001_00057400();
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
