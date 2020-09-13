package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi03702ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi037_02ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03702_00073300 implements CtrlHandleInter {
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
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi03702ZHResult app03702ZHResult = new AppApi03702ZHResult();
		AppApi030Form form = (AppApi030Form)form1;
		List<AppApi037_02ZHResult> list = new ArrayList<AppApi037_02ZHResult>();
		log.debug("00073300请求03702参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		HashMap resultMap =null;
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}
			
			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWZLBGCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DWZLBGCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110029");
			
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110019</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>E:/bbb.txt</><dpinstance>123456</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DWZLBGCX.txt", rexml, req);			
			//resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DWZLBGCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03702ZHResult);
			log.debug("MAP封装成BEAN："+app03702ZHResult);
			if(!"0".equals(app03702ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03702ZHResult.getRecode());
				modelMap.put("msg", app03702ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03702ZHResult.getRecode()+"  ,  描述msg : "+app03702ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("单位资料变更查询，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("dpinstance", app03702ZHResult.getDpinstance());
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
						AppApi037_02ZHResult app037_02ZHResult = new AppApi037_02ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app037_02ZHResult.setiCount(trs[0]);
						app037_02ZHResult.setDwzh(trs[1]);
						app037_02ZHResult.setDwmc(trs[2]);
						app037_02ZHResult.setChangetime(trs[3]);
						app037_02ZHResult.setFieldname(trs[4]);
						app037_02ZHResult.setBfchgval(trs[5]);
						app037_02ZHResult.setAfchgval(trs[6]);
						app037_02ZHResult.setAgentinstcode(trs[7]);
						app037_02ZHResult.setAgentop(trs[8]);
						app037_02ZHResult.setTranschannel(trs[9]);
						list.add(app037_02ZHResult);
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
		
		List<List<TitleInfoNameFormatBean>> detail = new ArrayList();
		List<TitleInfoNameFormatBean> appapi03702Result = null;
		for(int i=0;i<list.size();i++){
			appapi03702Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi03702"+form.getCenterId()+".detail", list.get(i));
			detail.add(appapi03702Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", detail);
		modelMap.put("filename", app03702ZHResult.getFilename());
		modelMap.put("dpinstance ", app03702ZHResult.getDpinstance());
		
		return modelMap;
	}
	
	/*public static void main(String[] args) throws Exception{
		AppApi030Form form1 = new AppApi030Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00073300");
		form1.setUserId("123456");
		form1.setDwzh("1q2w3e4r");
		form1.setBegdate("20161223");
		form1.setEnddate("20161223");
	
		Handle03702_00073300 hand = new Handle03702_00073300();
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
