package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi02204ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02204_00073300 implements CtrlHandleInter1{
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00073300请求02204参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi02204ZHResult app02204ZHResult = new AppApi02204ZHResult();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
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
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRXXGGCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRXXGGCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
			
			/*String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>192704</><TranDate>1</><TranIP>1</><TranSeq>1</>"
				+"<accbankcode>1</><accinstcode>2</><agentop>3</><attworkdate>4</><begintdate>5</><begpayym>6</><byearbal>7</><cardbankcode>8</><cardno>9</><centerid>10</><clsaccint>11</><dwjcbl>12</><dwmc>13</><dwyjce>14</>"
				+"<dwzh>15</><frzamt>16</><frzflag>17</><fundsouflag>18</><grjcbl>19</><grjcjs>20</><gryjce>21</><grzhdngjye>22</><grzhsnjzye>23</><grzhye>24</><grzhzt>25</><increintaccu>26</><indiacctype>27</>"
				+"<indibpaymap>28</><indipaymap>29</><indipaysum>30</><jzny>31</><keepintaccu>32</><khrq>33</><lastbal>34</><lastdrawdate>35</><lasttransdate>36</><proptype>37</><sjhm>38</><stpayamt>39</>"
				+"<trustdate>40</><unitapayamt>41</><unitastprop>42</><xhrq>43</><xingming>44</><yadjamt>45</><ydrawamt>46</><ynspayamt>47</><ypayamt>48</><yunctnamt>49</><zjhm>50</><zjlx>51</><grzh>52</>";
			*/
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRXXGGCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRXXGGCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02204ZHResult);
			log.debug("MAP封装成BEAN："+app02204ZHResult);
			if(!"0".equals(app02204ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02204ZHResult.getRecode());
				modelMap.put("msg", app02204ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02204ZHResult.getRecode()+"  ,  描述msg : "+app02204ZHResult.getMsg());
				return modelMap;
			}
		
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("accbankcode", app02204ZHResult.getAccbankcode());
		modelMap.put("accinstcode", app02204ZHResult.getAccinstcode());
		modelMap.put("agentop", app02204ZHResult.getAgentop());
		modelMap.put("attworkdate", app02204ZHResult.getAttworkdate());
		modelMap.put("begintdate", app02204ZHResult.getBegintdate());
		modelMap.put("begpayym", app02204ZHResult.getBegpayym());
		modelMap.put("byearbal", app02204ZHResult.getByearbal());
		modelMap.put("cardbankcode", app02204ZHResult.getCardbankcode());
		modelMap.put("cardno", app02204ZHResult.getCardno());
		modelMap.put("centerid", app02204ZHResult.getCenterid());
		modelMap.put("clsaccint", app02204ZHResult.getClsaccint());
		modelMap.put("dwjcbl", app02204ZHResult.getDwjcbl());
		modelMap.put("dwmc", app02204ZHResult.getDwmc());
		modelMap.put("dwyjce", app02204ZHResult.getDwyjce());
		modelMap.put("dwzh", app02204ZHResult.getDwzh());
		modelMap.put("frzamt", app02204ZHResult.getFrzamt());
		modelMap.put("frzflag", app02204ZHResult.getFrzflag());
		modelMap.put("fundsouflag", app02204ZHResult.getFundsouflag());
		modelMap.put("grjcbl", app02204ZHResult.getGrjcbl());
		modelMap.put("grjcjs", app02204ZHResult.getGrjcjs());
		modelMap.put("gryjce", app02204ZHResult.getGryjce());
		modelMap.put("grzhdngjye", app02204ZHResult.getGrzhdngjye());
		modelMap.put("grzhsnjzye", app02204ZHResult.getGrzhsnjzye());
		modelMap.put("grzhye", app02204ZHResult.getGrzhye());
		modelMap.put("grzhzt", app02204ZHResult.getGrzhzt());
		modelMap.put("increintaccu", app02204ZHResult.getIncreintaccu());
		modelMap.put("indiacctype", app02204ZHResult.getIndiacctype());
		modelMap.put("indibpaymap", app02204ZHResult.getIndibpaymap());
		modelMap.put("indipaymap", app02204ZHResult.getIndipaymap());
		modelMap.put("indipaysum", app02204ZHResult.getIndipaysum());
		modelMap.put("jzny", app02204ZHResult.getJzny());
		modelMap.put("keepintaccu", app02204ZHResult.getKeepintaccu());
		modelMap.put("khrq", app02204ZHResult.getKhrq());
		modelMap.put("lastbal", app02204ZHResult.getLastbal());
		modelMap.put("lastdrawdate", app02204ZHResult.getLastdrawdate());
		modelMap.put("lasttransdate", app02204ZHResult.getLasttransdate());
		modelMap.put("proptype", app02204ZHResult.getProptype());
		modelMap.put("sjhm", app02204ZHResult.getSjhm());
		modelMap.put("stpayamt", app02204ZHResult.getStpayamt());
		modelMap.put("trustdate", app02204ZHResult.getTrustdate());
		modelMap.put("unitapayamt", app02204ZHResult.getUnitapayamt());
		modelMap.put("unitastprop", app02204ZHResult.getUnitastprop());
		modelMap.put("xhrq", app02204ZHResult.getXhrq());
		modelMap.put("xingming", app02204ZHResult.getXingming());
		modelMap.put("yadjamt", app02204ZHResult.getYadjamt());
		modelMap.put("ydrawamt", app02204ZHResult.getYdrawamt());
		modelMap.put("ynspayamt", app02204ZHResult.getYnspayamt());
		modelMap.put("ypayamt", app02204ZHResult.getYpayamt());
		modelMap.put("yunctnamt", app02204ZHResult.getYunctnamt());
		modelMap.put("zjhm", app02204ZHResult.getZjhm());
		modelMap.put("zjlx", app02204ZHResult.getZjlx());
		modelMap.put("grzh", app02204ZHResult.getGrzh());

		return modelMap;
	}

	/*public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00073300");
		form1.setUserId("123456");
		form1.setChannel("40");
		form1.setTranscode("123");
		form1.setGrtype("456");
		form1.setGrzh("789");

		Handle02204_00073300 hand = new Handle02204_00073300();
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
