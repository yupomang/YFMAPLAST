package com.yondervision.yfmap.handle.handleImpl.handle00047201;

import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi30312Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30312_00047201
* @Description: 预约查询
* 
*/ 
public class Handle30312_00047201 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30312 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		BaoGangAppApi30312Result app00802Result = new BaoGangAppApi30312Result();
		
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			AES aes = new AES();
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			//form.setAppobranchid("00004711");
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YYCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "148001");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>yycx</><sumcounts></><counts></><pageflag>1</>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YYCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				String msg =app00802Result.getMsg();
				if(!CommonUtil.isEmpty(app00802Result.getMsg()))
				{
					if(app00802Result.getMsg().contains("系统日终"))
					{
						msg = "温馨提示：由于公积金业务需每日进行日终结账，结账期间微信系统将无法查询，每日结账时间为17:30—18:30，望大家避开系统结账时间查询，敬请谅解！";
					}
				}
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", msg);
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}
		}
		List<TitleInfoBean> app00802List = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi30312"+form.getCenterId()+".result", app00802Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", app00802List);
		log.info(Constants.LOG_HEAD+"appApi30312 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle30312_00047201 hand = new Handle30312_00047201();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
