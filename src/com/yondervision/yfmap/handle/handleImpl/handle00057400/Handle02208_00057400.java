package com.yondervision.yfmap.handle.handleImpl.handle00057400;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi02208ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02208_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi020Form form = (AppApi020Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi02208ZHResult app02208ZHResult = new AppApi02208ZHResult();
		if(Constants.method_BSP.equals(send)){

			String msgType = PropertiesReader. getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))){
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
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
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWYW_DWNDYSBGSXZ.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "191297");
			
			log.debug("前置YFMAP接收中心报文——单位年度验审报告书下载："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWYW_DWNDYSBGSXZ.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02208ZHResult);
			log.debug("MAP封装成BEAN："+app02208ZHResult);
			if(!"0".equals(app02208ZHResult.getRecode())){
				modelMap.put("recode", app02208ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app02208ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02208ZHResult.getRecode()+"  ,  描述msg : "+app02208ZHResult.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("unitaccnum",app02208ZHResult.getUnitaccnum());
		modelMap.put("unitaccname",app02208ZHResult.getUnitaccname());
		modelMap.put("orgcode",app02208ZHResult.getOrgcode());
		modelMap.put("cpcode",app02208ZHResult.getCpcode());
		modelMap.put("leglaccname",app02208ZHResult.getLeglaccname());
		modelMap.put("unitprop",app02208ZHResult.getUnitprop());
		modelMap.put("lvunitkind",app02208ZHResult.getLvunitkind());
		modelMap.put("createdate",app02208ZHResult.getCreatdate());
		modelMap.put("opendate",app02208ZHResult.getOpendate());
		modelMap.put("leglphone",app02208ZHResult.getLeglphone());
		modelMap.put("linkman",app02208ZHResult.getLinkman());
		modelMap.put("department1",app02208ZHResult.getDepartment1());
		modelMap.put("linkphone",app02208ZHResult.getLinkphone());
		modelMap.put("unitaddr",app02208ZHResult.getUnitaddr());
		modelMap.put("zip",app02208ZHResult.getZip());
		modelMap.put("peoplenum",app02208ZHResult.getPeoplenum());
		modelMap.put("paypeonum",app02208ZHResult.getPaypeonum());
		modelMap.put("flag",app02208ZHResult.getFlag());
		modelMap.put("year",app02208ZHResult.getYear());
		modelMap.put("year1",app02208ZHResult.getYear1());
		modelMap.put("mannum",app02208ZHResult.getMannum());
		modelMap.put("pernum",app02208ZHResult.getPernum());
		modelMap.put("paymannum",app02208ZHResult.getPaymannum());
		modelMap.put("unitmannum",app02208ZHResult.getUnitmannum());
		modelMap.put("unioncode",app02208ZHResult.getUnioncode());
		return modelMap;
	}
}
