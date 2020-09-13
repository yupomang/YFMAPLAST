package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00907Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi00911HResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00911_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;
	static{
		//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	@SuppressWarnings("rawtypes")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00911HResult app00911ZHResult = new AppApi00911HResult();
		AppApi00907Form form = (AppApi00907Form)form1;
		List<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00077500请求00911参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Send"+form.getCenterId()).trim();
		
		HashMap resultMap =null;
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi009Type"+form.getCenterId()).trim());
			form.setFlag(Channel.getZzChannel(form.getChannel()));

			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_FWLXCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_FWLXCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126990");
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126990</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>E:/aaa.txt</>";
			log.debug("前置YFMAP接收中心报文："+rexml);
			
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_FWLXCX.txt", rexml, req);			
			//resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_FWLXCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00911ZHResult);
			log.debug("MAP封装成BEAN："+app00911ZHResult);
			if(!"0".equals(app00911ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00911ZHResult.getRecode());
				modelMap.put("msg", app00911ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00911ZHResult.getRecode()+"  ,  描述msg : "+app00911ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("查询个人明细，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("filename", filename);
				modelMap.put("result", "");
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil f=new FtpUtil("bsp");
				f.getFileByServer(filename);
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				//File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine(); 
				String line1 = breader.readLine();
				while (line1 != null) {// 判断读取到的字符串是否不为空
					try {
						LinkedHashMap app009_11ZHResult = new LinkedHashMap();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app009_11ZHResult.put("itemid", trs[0]);
						app009_11ZHResult.put("itemval", trs[1]);
						list.add(app009_11ZHResult);
						line1 = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				file.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查无数据");
				return modelMap;
			}

		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("result", list);
		
		return modelMap;
	}

	/*public static void main(String[] args){
		AppApi00907Form form1 = new AppApi00907Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setChannel("40");
		Handle00911_00077500 hand = new Handle00911_00077500();
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
