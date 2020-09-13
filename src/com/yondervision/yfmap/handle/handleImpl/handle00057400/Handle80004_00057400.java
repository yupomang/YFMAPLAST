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
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi00122ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.XmlUtils;

public class Handle80004_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
		//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		log.debug("00057400请求80004参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi00122ZHResult app00122ZHResult = new AppApi00122ZHResult();
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
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			System.out.println("form.getBaseInfoXml()========="+form.getBaseInfoXml());
			System.out.println("form.getAttrXml()============"+form.getAttrXml());
			System.out.println("form.getFormXml()==========="+form.getFormXml());
			String baseinfo=new String(form.getBaseInfoXml().getBytes("iso-8859-1"), "utf-8");
			String attr=new String(form.getAttrXml().getBytes("iso-8859-1"), "utf-8");
			String formx=new String(form.getFormXml().getBytes("iso-8859-1"), "utf-8");
			form.setBaseInfoXml(baseinfo);
			form.setAttrXml(attr);
			form.setFormXml(formx);
			System.out.println("baseinfo========="+baseinfo);
			System.out.println("attr============"+attr);
			System.out.println("formx=========="+formx);
						
			HashMap baseinfoMap=XmlUtils.xmlStrConvertMap(form.getBaseInfoXml().replace("\\",""),"UTF-8");
//			HashMap attrMap=XmlUtils.xmlStrConvertMap(form.getAttrXml().replace("\\",""),"UTF-8");
//			HashMap formMap=XmlUtils.xmlStrConvertMap(form.getFormXml().replace("\\",""),"UTF-8");

			System.out.println("PROJID=========="+baseinfoMap.get("PROJID").toString());
			System.out.println("SERVICECODE=========="+baseinfoMap.get("SERVICECODE").toString());
			System.out.println("PROJECTNAME=========="+baseinfoMap.get("PROJECTNAME").toString());
			System.out.println("APPLYNAME=========="+baseinfoMap.get("APPLYNAME").toString());
			System.out.println("APPLY_CARDNUMBER=========="+baseinfoMap.get("APPLY_CARDNUMBER").toString());
			System.out.println("APPLY_CARDTYPE=========="+baseinfoMap.get("APPLY_CARDTYPE").toString());
			System.out.println("TELPHONE=========="+baseinfoMap.get("TELPHONE").toString());
			System.out.println("RECEIVETIME=========="+baseinfoMap.get("RECEIVETIME").toString());

			form.setProjid(baseinfoMap.get("PROJID").toString());
			form.setServicecode(baseinfoMap.get("SERVICECODE").toString());
			form.setProjectname(baseinfoMap.get("PROJECTNAME").toString());
			form.setApplyname(baseinfoMap.get("APPLYNAME").toString());
			form.setApply_cardnumber(baseinfoMap.get("APPLY_CARDNUMBER").toString());
			form.setApply_cardtype(baseinfoMap.get("APPLY_CARDTYPE").toString());
			form.setTelphone(baseinfoMap.get("TELPHONE").toString());
			form.setReceivetime(baseinfoMap.get("RECEIVETIME").toString());
			
			HashMap map = BeanUtil.transBean2Map(form);	
		    map.put("tellcode", "wzgy");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80004_111081.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111081");
			log.debug("前置YFMAP接收中心报文——住建委业务受理号上传："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_80004_111081.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00122ZHResult);
			log.debug("MAP封装成BEAN："+app00122ZHResult);
			if(!"0".equals(app00122ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00122ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app00122ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00122ZHResult.getRecode()+"  ,  描述msg : "+app00122ZHResult.getMsg());
				return modelMap;
			}
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		return modelMap;
	}

}
