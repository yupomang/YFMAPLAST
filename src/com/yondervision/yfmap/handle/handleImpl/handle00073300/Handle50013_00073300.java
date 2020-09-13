package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.yondervision.yfmap.form.AppApi50002Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi02001ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi50013ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50013_00073300 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50002Form form = (AppApi50002Form)form1;
		System.out.println("YFMAP发往中心——单位登录");
		log.debug("00073300请求50013参数："+CommonUtil.getStringParams(form));
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
		if(!CommonUtil.isEmpty(form.getPassword())){
		log.debug("form.getPassword():"+form.getPassword());
		form.setPassword(aes.decrypt(form.getPassword()));
	    }
		if(!CommonUtil.isEmpty(form.getUnitaccnum())){
			log.debug("form.getUnitaccnum():"+form.getUnitaccnum());
			form.setUnitaccnum(aes.decrypt(form.getUnitaccnum()));
		}
		if(!CommonUtil.isEmpty(form.getDlfs())){
			log.debug("form.getDlfs():"+form.getDlfs());
			form.setDlfs(aes.decrypt(form.getDlfs()));
		}
		if(!CommonUtil.isEmpty(form.getUnitcustid())){
			log.debug("form.getUnitcustid():"+form.getUnitcustid());
			form.setUnitcustid(aes.decrypt(form.getUnitcustid()));
		}
		if(!CommonUtil.isEmpty(form.getZsbh())){
			log.debug("form.getZsbh():"+form.getZsbh());
			form.setZsbh(aes.decrypt(form.getZsbh()));
		}
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50013ZHResult app50013ZHResult = new AppApi50013ZHResult();
		AppApi02001ZHResult app02001ZHResult = new AppApi02001ZHResult();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
//			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWDL.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DWDL.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110022");

//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110022</><TranDate>1</><TranIP>1</><TranSeq>1</><dwzh>45</><jbrcsqx>345</><jbrfsqx>435</><jbrsfpx>435</><unitcustid>45</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWDL.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DWDL.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50013ZHResult);
			log.debug("MAP封装成BEAN："+app50013ZHResult);
			if(!"0".equals(app50013ZHResult.getRecode())){
				modelMap.put("recode", app50013ZHResult.getRecode());
				modelMap.put("msg", app50013ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50013ZHResult.getRecode()+"  ,  描述msg : "+app50013ZHResult.getMsg());
				return modelMap;
			}else {
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWJBXXCX.txt", map, req);
//				xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DWJBXXCX.txt", map, req);			
				log.debug("前置YFMAP发往中心报文："+xml);			

				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110368");				
//				rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110368</><TranDate>1</><TranIP>1</><TranSeq>1</><dwmc>45</><dwdz>45</><unitareacode>45</><dwyb>345</><unitkind>435</><orgtype>435</><mngdept>435</><mngdepter>324</><mngdepterphone>32432132</><zzjgdm>2342134</><cocietycode>324321</><ownershipkind>32432</><dwlsgx>3432</><dwjjlx>34324</><dwsshy>34324</><dwslrq>34</><dwfxr>34</><dwfrdbxm>343</><dwfrdbsjhm>3434</><dwfrdbzjhm>3432</><dwfrdbzjlx>3432</><basebankaccname>34324</><basebankaccnum>343</><insurancenum>34</><workernum>3432</><isserdispach>34</><cstcollflag>3432</><tszhyh>3432</><tszhhm>344</><tszh>34</>";
				
				log.debug("前置YFMAP接收中心报文——单位基本信息查询："+rexml);
			    resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWJBXXCX.txt", rexml, req);
//				resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DWJBXXCX.txt", rexml, req);
				
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app02001ZHResult);
				log.debug("MAP封装成BEAN："+app02001ZHResult);
				if(!"0".equals(app02001ZHResult.getRecode() )){
					modelMap.clear();
					modelMap.put("recode", app02001ZHResult.getRecode());
					modelMap.put("msg", app02001ZHResult.getMsg());
					log.error("中心返回报文 状态recode :"+app02001ZHResult.getRecode()+"  ,  描述msg : "+app02001ZHResult.getMsg());
					return modelMap;
				}
			}
		}
		
		modelMap.clear();		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		
		modelMap.put("dwzh", app50013ZHResult.getDwzh());
		modelMap.put("jbrcsqx", app50013ZHResult.getJbrcsqx());
		modelMap.put("jbrfsqx", app50013ZHResult.getJbrfsqx());
		modelMap.put("jbrsfpx", app50013ZHResult.getJbrsfpx());
		modelMap.put("unitcustid", app50013ZHResult.getUnitcustid());
		
//		modelMap.put("dwzh", app02001ZHResult.getDwzh());
		modelMap.put("dwmc", app02001ZHResult.getDwmc());
		modelMap.put("dwdz", app02001ZHResult.getDwdz());
		modelMap.put("unitareacode", app02001ZHResult.getUnitareacode());
		modelMap.put("dwyb", app02001ZHResult.getDwyb());
		modelMap.put("unitkind", app02001ZHResult.getUnitkind());
		modelMap.put("orgtype", app02001ZHResult.getOrgtype());
		modelMap.put("mngdept", app02001ZHResult.getMngdept());
		modelMap.put("mngdepter", app02001ZHResult.getMngdepter());
		modelMap.put("mngdepterphone", app02001ZHResult.getMngdepterphone());
		modelMap.put("zzjgdm", app02001ZHResult.getZzjgdm());
		modelMap.put("cocietycode", app02001ZHResult.getCocietycode());
		modelMap.put("ownershipkind", app02001ZHResult.getOwnershipkind());
		modelMap.put("dwlsgx", app02001ZHResult.getDwlsgx());
		modelMap.put("dwjjlx", app02001ZHResult.getDwjjlx());
		modelMap.put("dwsshy", app02001ZHResult.getDwsshy());
		modelMap.put("dwslrq", app02001ZHResult.getDwslrq());
		modelMap.put("dwfxr", app02001ZHResult.getDwfxr());
		modelMap.put("dwfrdbxm", app02001ZHResult.getDwfrdbxm());
		modelMap.put("dwfrdbsjhm", app02001ZHResult.getDwfrdbsjhm());
		modelMap.put("dwfrdbzjhm", app02001ZHResult.getDwfrdbzjhm());
		modelMap.put("dwfrdbzjlx", app02001ZHResult.getDwfrdbzjlx());
		modelMap.put("basebankaccname", app02001ZHResult.getBasebankaccname());
		modelMap.put("basebankaccnum", app02001ZHResult.getBasebankaccnum());
		modelMap.put("insurancenum", app02001ZHResult.getInsurancenum());
		modelMap.put("workernum", app02001ZHResult.getWorkernum());
		modelMap.put("isserdispach", app02001ZHResult.getIsserdispach());
		modelMap.put("cstcollflag", app02001ZHResult.getCstcollflag());
		modelMap.put("tszhyh", app02001ZHResult.getTszhyh());
		modelMap.put("tszhhm", app02001ZHResult.getTszhhm());
		modelMap.put("tszh", app02001ZHResult.getTszh());
		modelMap.put("dwzhye", app02001ZHResult.getDwzhye());
		modelMap.put("accbankcode", app02001ZHResult.getAccbankcode());
		modelMap.put("dwzhzt", app02001ZHResult.getDwzhzt());
		modelMap.put("prebal", app02001ZHResult.getPrebal());
		modelMap.put("dkye", app02001ZHResult.getDkye());
		modelMap.put("dkffe", app02001ZHResult.getDkffe());
		modelMap.put("peoplenum", app02001ZHResult.getPeoplenum());
		modelMap.put("opnaccdate", app02001ZHResult.getOpnaccdate());
		modelMap.put("dwjcje", app02001ZHResult.getDwjcje());
		modelMap.put("unitnorprop", app02001ZHResult.getUnitnorprop());
		modelMap.put("indinorprop", app02001ZHResult.getIndinorprop());
		
		
		modelMap.put("unitaccnum", app02001ZHResult.getDwzh());
		modelMap.put("unitaccname", app02001ZHResult.getDwmc());
		modelMap.put("opname", app02001ZHResult.getDwfrdbxm());
		modelMap.put("unitaddr", app02001ZHResult.getDwdz());
		modelMap.put("unitphone", app02001ZHResult.getDwfrdbsjhm());
		modelMap.put("exitdate", app02001ZHResult.getDwslrq());
		modelMap.put("unitcustid", app02001ZHResult.getUnitcustid());
		modelMap.put("dwjcrs", app02001ZHResult.getDwjcrs());
		modelMap.put("jzny", app02001ZHResult.getJzny());
		modelMap.put("dwfcrs", app02001ZHResult.getDwfcrs());
		modelMap.put("dwjczt", app02001ZHResult.getDwjczt());
		return modelMap;
	}
	
}
