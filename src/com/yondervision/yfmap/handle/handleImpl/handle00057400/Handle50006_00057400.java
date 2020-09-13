package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.yfmap.common.util.SecurityUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
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

public class Handle50006_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
		public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
	
			String PROPERTIES_FILE_NAME = "properties.properties";
			AppApi50001Form form = (AppApi50001Form)form1;
			log.debug("00057400请求50006参数："+CommonUtil.getStringParams(form));
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
			String flag= null;
			//针对住建委网站加密问题修改 xzw
			if(!CommonUtil.isEmpty(form.getCertinumType())&& !"1".equals(request.getParameter("AESFlag"))&&!"30".equals(form.getChannel())){
				log.debug("form.getCertinumType():"+form.getCertinumType());
				form.setCertinumType(aes.decrypt(form.getCertinumType()));
			}
			//针对住建委网站加密问题修改 xzw
			if(!CommonUtil.isEmpty(form.getPassword())&& !"1".equals(request.getParameter("AESFlag"))&&!"30".equals(form.getChannel())){
				log.debug("form.getPassword():"+form.getPassword());
				form.setPassword(aes.decrypt(form.getPassword()));
			}
			
			String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
			AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();
			
			if(Constants.method_BSP.equals(send)){
				SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = new Date();
				form.setSendDate(formatter1.format(date));
				form.setSendTime(formatter2.format(date));
				String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
				form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());			
				String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
				form.setSendSeqno(req);
				form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
				
				if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91"))&&(!form.getChannel().trim().equals("93")))
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
				/*//个人登录要求加盐加密登录 ll 2019.11.4
				String oppwd = form.getPassword();
				String salt = SecurityUtil.genUUID();
				String newpwd = SecurityUtil.HMAC(salt, oppwd);
				form.setPwd(newpwd);*/
				byte[] c = DESForJava.encryption(form.getPassword(),"12345678");
				c = DESForJava.bcd_to_asc(c);
				form.setPassword(new String(c));

				//log.info("form.getPassword(): " +form.getPassword());
				//log.info("newpwd==========="+newpwd);
				log.debug("form.getAccnum(): " +form.getAccnum());
				log.debug("form.getBodyCardNumber(): " +form.getBodyCardNumber());
				log.debug("form.getTel(): " +form.getTel());
				log.debug("form.getCheckcode(): " +form.getCheckcode());
				//针对住建委网站加密问题修改 xzw
				if(!"1".equals(request.getParameter("AESFlag"))&&!"30".equals(form.getChannel())){
					form.setAccnum(aes.decrypt(form.getAccnum()));
					form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
					form.setTel(aes.decrypt(form.getTel()));
					form.setCheckcode(aes.decrypt(form.getCheckcode()));
					if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("10")){
						log.debug("form.getClientIp(): " +form.getClientIp());
						form.setClientIp("10.33.11.35");
					}
					if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("50")){
						log.debug("form.getClientIp(): " +form.getClientIp());
						form.setClientIp("10.33.11.35");
					}
					if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("60")){
						log.debug("form.getClientIp(): " +form.getClientIp());
						form.setClientIp("10.33.11.35");
					}
					if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("41")){
						log.debug("form.getClientIp(): " +form.getClientIp());
						form.setClientIp("10.33.11.35");
					}
					if(!CommonUtil.isEmpty(form.getClientIp())&&form.getChannel().trim().equals("53")){
						log.debug("form.getClientIp(): " +form.getClientIp());
						form.setClientIp("10.33.11.35");
					}
				}
				
				flag=form.getCheckflag();
				String transcode = null;
				if(form.getChannel().trim().equals("40")){
						log.debug("网厅专用登录");
						transcode="142201";		
						//flag=form.getCheckflag();
				}else {
						log.debug("其他渠道登录");
						transcode="142503";
				}
				log.debug("交易号transcode："+transcode);
				
				HashMap map = BeanUtil.transBean2Map(form);
				map.put("transcode",transcode);
				map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
				String xml1 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZHJBXXCXDL.txt", map, req);
				//String xml1 = MessageCtrMain.encapsulatedPackets(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRZHJBXXCXDL.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml1);	
	
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				long starTime=System.currentTimeMillis();
				String rexml1 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml1, transcode);
				long endTime=System.currentTimeMillis();
				long Time=endTime-starTime;
				System.out.println("核心耗时"+Time+"毫秒");
				//String rexml1 ="<AuthCode1>7710</><AuthCode2>7710</><AuthCode3>7710</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>89499</><ChannelSeq>398243</><ChkCode>7710</><MTimeStamp>2017-08-04 08:59:52</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2017-08-04 08:59:52</><TellCode>7710</><TranChannel>00</><TranCode>142201</><TranDate>2017-08-10</><TranIP>172.16.0.162</><TranSeq>89499</><accname>严苑莱</><accnum>0236892476</><certinum>330226198611300043</><signer>1</><brcCode>wwwwwwwwwwww</>";
				
				log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml1);
				HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZHJBXXCXDL.txt", rexml1, req);
				//HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, "D:/workzone/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRZHJBXXCXDL.txt", rexml1, req);
	
				log.debug("解析报文MAP："+resultMap1);
				BeanUtil.transMap2Bean(resultMap1, app00101ZHResult);
				if(!"0".equals(app00101ZHResult.getRecode())){
					modelMap.clear();
					JSONArray result = new JSONArray();
					JSONObject obj = new JSONObject();
					obj.put("errCode", app00101ZHResult.getRecode());
					obj.put("msg", "错误信息："+app00101ZHResult.getMsg());
					result.add(obj);
					modelMap.put("recode", app00101ZHResult.getRecode());
					modelMap.put("msg", result); 
					log.error("中心返回报文 状态recode :"+app00101ZHResult.getRecode()+"  ,  描述msg : "+app00101ZHResult.getMsg());
					return modelMap;
				}
			}
	
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			if("50".equals(form.getChannel())|| "51".equals(form.getChannel())  || "41".equals(form.getChannel()) || "53".equals(form.getChannel())){
				List<TitleInfoNameFormatBean> appapi00101ZHResult = null;
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
	//			if(form.getChannel().trim().equals("50")){
	//			    modelMap.put("certinum", app00101ZHResult.getCertinum());
	//			    modelMap.put("accnum", app00101ZHResult.getAccnum());
	//			    modelMap.put("accname", app00101ZHResult.getAccname());
	//			    modelMap.put("phone", app00101ZHResult.getHandset());
	//			    modelMap.put("unitaccnum", app00101ZHResult.getUnitaccnum());
	//				modelMap.put("brcCode", app00101ZHResult.getBrcCode());
	//			}
	
			    modelMap.put("certinum", app00101ZHResult.getCertinum());
			    modelMap.put("accnum", app00101ZHResult.getAccnum());
			    modelMap.put("accname", app00101ZHResult.getAccname());
			    modelMap.put("phone", app00101ZHResult.getHandset());
			    modelMap.put("unitaccnum", app00101ZHResult.getUnitaccnum());
				modelMap.put("brcCode", app00101ZHResult.getBrcCode());
				if(form.getChannel().trim().equals("53")){
					modelMap.put("signer", app00101ZHResult.getSigner());
				}
				return modelMap;
			}
			
			if(form.getChannel().trim().equals("10")){
				modelMap.clear();
			    modelMap.put("recode", "000000");
			    modelMap.put("msg", "成功");
			    modelMap.put("certinum", app00101ZHResult.getCertinum());
			    modelMap.put("accnum", app00101ZHResult.getAccnum());
			    modelMap.put("accname", app00101ZHResult.getAccname());
			    modelMap.put("phone", app00101ZHResult.getHandset());
				return modelMap;
			}
	
			
			if(!"0".equals(flag)){
				modelMap.put("certinum", app00101ZHResult.getCertinum());
				modelMap.put("accnum", app00101ZHResult.getAccnum());
				modelMap.put("accname", app00101ZHResult.getAccname());
				modelMap.put("signer", app00101ZHResult.getSigner());
				modelMap.put("brcCode", app00101ZHResult.getBrcCode());
				modelMap.put("initialpwdflag", app00101ZHResult.getInitialpwdflag());
				modelMap.put("handset", app00101ZHResult.getHandset());
				modelMap.put("phone", app00101ZHResult.getHandset());
				modelMap.put("bodyCardNumber", app00101ZHResult.getCertinum().trim());
				modelMap.put("certinumType", app00101ZHResult.getCertitype());
				modelMap.put("fullName", app00101ZHResult.getAccname());
				modelMap.put("tel", app00101ZHResult.getHandset());
			}
			return modelMap;
		}

	/*public static void main(String[] args){
		AppApi50001Form form1 = new AppApi50001Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("40");

		try{
			AES aes = new AES("00057400" ,"40" ,null ,null);
			form1.setPassword(aes.encrypt("111111".getBytes()));
			form1.setBodyCardNumber(aes.encrypt("222111111111111111".getBytes()));
			form1.setAccnum(aes.encrypt("1432423423".getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}
															                                
		Handle50006_00057400 hand = new Handle50006_00057400();
		try {
			System.out.println(JsonUtil.getGson().toJson(hand.action(form1, modelMap, null, null)));
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
