package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yondervision.yfmap.result.ningbo.AppApi50006ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DESForJava;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50010_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00057400请求50010参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		
		if(!CommonUtil.isEmpty(form.getCertinumType())){
			log.debug("form.getCertinumType():"+form.getCertinumType());
			form.setCertinumType(aes.decrypt(form.getCertinumType()));
		}
		if(!CommonUtil.isEmpty(form.getPassword())){
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
//			form.setClientIp(aes.decrypt(form.getClientIp()));
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
			byte[] c = DESForJava.encryption(form.getPassword(),"12345678");
			c = DESForJava.bcd_to_asc(c);
			//form.setPassword(new String(c));
			form.setPwd(new String(c));
			log.debug("form.getAccnum(): " +form.getAccnum());
			log.debug("form.getBodyCardNumber(): " +form.getBodyCardNumber());
			form.setAccnum(aes.decrypt(form.getAccnum()));
			form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml1 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZHJBXXCX.txt", map, req);
			//String xml1 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_GRZHJBXXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml1);	

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml1 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml1, "142503");
			
			/*String rexml1 ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode></><BusiSeq></><ChannelSeq></><ChkCode>cweb</><MTimeStamp></><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp></><TellCode>cweb</><TranChannel></><TranCode>142501</><TranDate></><TranIP></><TranSeq>11111</>"
					+ "<accnum>11111</><accname>aaaaa</><certinum>1234567890</><actmp100>1</><actmp1024>2</><actmp200>3</><amt>4</><amt3>5</><amt7>6</><amt8>7</>"
					+ "<bal>8</><bankname>9</><bankcode>10</><basenum>11</><begdate>12</><cardstate>13</><certitype>14</><freeuse1>15</><frzamt>16</><frzflag>17</><handset>18</>"
					+ "<indiaccstate>19</><lpaym>20</><monintamt>21</><monpaysum>22</><subintamt>23</><supintamt>24</><unitaccname>25</><accinstcode>26</><accinstcodedes>27</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml1);
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZHJBXXCXWX.txt", rexml1, req);
			//HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_GRZHJBXXCX.txt", rexml1, req);

			log.debug("解析报文MAP："+resultMap1);
			BeanUtil.transMap2Bean(resultMap1, app00101ZHResult);
			if(!"0".equals(app00101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00101ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app00101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00101ZHResult.getRecode()+"  ,  描述msg : "+app00101ZHResult.getMsg());
				return modelMap;
			}
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		if("50".equals(form.getChannel())){
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
		}
		modelMap.put("certinum", app00101ZHResult.getCertinum());
		modelMap.put("accnum", app00101ZHResult.getAccnum());
		modelMap.put("accname", app00101ZHResult.getAccname());
		modelMap.put("phone", app00101ZHResult.getHandset());
		return modelMap;
	}

	/*public static void main(String[] args){
		AppApi50001Form form1 = new AppApi50001Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00073300");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setBodyCardNumber("111111111111111111");
		form1.setAccnum("33333");
		try{
			AES aes = new AES("00073300" ,"40" ,null ,null);
			form1.setPassword(aes.encrypt("111111".getBytes()));
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
