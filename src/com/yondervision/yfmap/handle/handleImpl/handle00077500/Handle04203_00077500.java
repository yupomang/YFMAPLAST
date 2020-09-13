package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi04203ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle04203_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00077500请求04203参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi04203ZHResult app04203ZHResult = new AppApi04203ZHResult();
		AppApi04203ZHResult app04203ZHResult1 = new AppApi04203ZHResult();

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
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRJCZMDY.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_GRJCZMDY.txt", map, req);
			log.debug("前置YFMAP发往中心报文——个人缴存证明打印获取账户信息："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
			/*String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126991</><TranDate>1</><TranIP>1</><TranSeq>1</>"
				+ "<sjhm>6</><proptype>5</><lasttransdate>4</><lastdrawdate>3</><lastbal>2</><khrq>1</><keepintaccu>0</><jzny>9</><indipaysum>8</><indipaymap>7</><indibpaymap>6</>"
				+ "<indiacctype>5</><stpayamt>4</><yunctnamt>3</><ypayamt>2</><ynspayamt>1</><ydrawamt>100</><yadjamt>200</><xingming>123</><xhrq>20161212</><unitastprop>111</><unitapayamt>200</><zjhm>2</>"
				+ "<trustdate>20161111</><zjlx>02</><dwmc>aaa</><dwjcbl>000</><clsaccint>222</><centerid>00077500</><cardno>1234567890</><cardbankcode>1010</><byearbal>1</><begpayym>2</><begintdate>3</><attworkdate>4</><agentop>5</><accinstcode>6</><accbankcode>7</>"
				+ "<dwyjce>8</><increintaccu>9</><grzhzt>0</><grzhye>11</><grzhsnjzye>12</><grzhdngjye>13</><gryjce>14</><grjcjs>15</><grjcbl>16</><fundsouflag>17</><frzflag>18</><frzamt>19</><dwzh>20</><grzh>21</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人缴存证明打印获取账户信息："+rexml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRJCZMDY.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_GRJCZMDY.txt", rexml, req);
			log.debug("解析报文MAP——个人缴存证明打印获取账户信息："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app04203ZHResult);
			if(!"0".equals(app04203ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app04203ZHResult.getRecode());
				modelMap.put("msg", app04203ZHResult.getMsg());
				log.error("获取账户信息，中心返回报文 状态recode :"+app04203ZHResult.getRecode()+"  ,  描述msg : "+app04203ZHResult.getMsg());
				return modelMap;
			}
			
			Date date1 = new Date();
			form.setSendDate(formatter1.format(date1));
			form.setSendTime(formatter2.format(date1));
			String req1 = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req1);
			HashMap<String, String> map1 = BeanUtil.transBean2Map(form);
			String xml1 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRJCZMDY1.txt", map1, req1);
			//String xml1 = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_GRJCZMDY1.txt", map1, req1);
			log.debug("前置YFMAP发往中心报文——个人缴存证明打印获取文件名："+xml1);	
			
			String rexml1 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml1, "117094");
			//String rexml1 ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126991</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>E:/aaa.txt</>";
			log.debug("前置YFMAP接收中心报文——个人缴存证明打印获取文件名："+rexml1);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRJCZMDY1.txt", rexml1, req1);
			//HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_GRJCZMDY1.txt", rexml1, req1);
			log.debug("解析报文MAP——个人缴存证明打印获取文件名："+resultMap1);
			BeanUtil.transMap2Bean(resultMap1, app04203ZHResult1);
			if(!"0".equals(app04203ZHResult1.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app04203ZHResult1.getRecode());
				modelMap.put("msg", app04203ZHResult1.getMsg());
				log.error("个人缴存证明打印获取文件名，中心返回报文 状态recode :"+app04203ZHResult1.getRecode()+"  ,  描述msg : "+app04203ZHResult1.getMsg());
				return modelMap;
			}
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("filename", app04203ZHResult1.getFilename());
		modelMap.put("indipaysum", app04203ZHResult.getIndipaysum());
		modelMap.put("xingming", app04203ZHResult.getXingming());
		modelMap.put("zjhm", app04203ZHResult.getZjhm());
		modelMap.put("zjlx", app04203ZHResult.getZjlx());
		modelMap.put("dwmc", app04203ZHResult.getDwmc());
		modelMap.put("grzhzt", app04203ZHResult.getGrzhzt());
		modelMap.put("grzhye", app04203ZHResult.getGrzhye());
		modelMap.put("grjcjs", app04203ZHResult.getGrjcjs());
		modelMap.put("grjcbl", app04203ZHResult.getGrjcbl());
		modelMap.put("dwzh", app04203ZHResult.getDwzh());
		
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setAccnum("111222");
		form1.setTranscode("123456");
		form1.setTranstype("01");

		Handle04203_00077500 hand = new Handle04203_00077500();
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
