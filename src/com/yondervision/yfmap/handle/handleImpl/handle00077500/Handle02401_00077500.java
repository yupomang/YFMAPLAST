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
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi02401ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi024_01ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02401_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;
	static{
		//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi02401ZHResult app02401ZHResult = new AppApi02401ZHResult();
		 
		List<AppApi024_01ZHResult> list = new ArrayList<AppApi024_01ZHResult>();

		AppApi020Form form = (AppApi020Form)form1;
		log.debug("00077500请求02401参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
				
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_JCRZCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_JCRZCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "117026");
			
			//String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110019</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>E:/ccc.txt</>";
			
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_JCRZCX.txt", rexml, req);
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_JCRZCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02401ZHResult);
			log.debug("MAP封装成BEAN："+app02401ZHResult);
			if(!"0".equals(app02401ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02401ZHResult.getRecode());
				modelMap.put("msg", app02401ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02401ZHResult.getRecode()+"  ,  描述msg : "+app02401ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("缴存入账查询，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app02401ZHResult.getTotalpage());
				modelMap.put("totalnum", app02401ZHResult.getTotalnum());
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
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine(); 
				String line1 = breader.readLine(); 
				while (line1 != null) {// 判断读取到的字符串是否不为空
					try {
						AppApi024_01ZHResult app024_01ZHResult = new AppApi024_01ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app024_01ZHResult.setRegnum(trs[0]);
						app024_01ZHResult.setPaymenttype(trs[1]);
						app024_01ZHResult.setRegdate(trs[2]);
						app024_01ZHResult.setDwzh(trs[3]);
						app024_01ZHResult.setDwmc(trs[4]);
						app024_01ZHResult.setBusipayamt(trs[5]);
						app024_01ZHResult.setBusibegindate(trs[6]);
						app024_01ZHResult.setBusienddate(trs[7]);
						app024_01ZHResult.setOper(trs[8]);
						app024_01ZHResult.setChrflag(trs[9]);
						list.add(app024_01ZHResult);
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
		List<TitleInfoNameFormatBean> appapi02401Result = null;
		for(int i=0;i<list.size();i++){
			appapi02401Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02401"+form.getCenterId()+".detail", list.get(i));
			detail.add(appapi02401Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", detail);
		modelMap.put("filename", app02401ZHResult.getFilename());
		
		return modelMap;
	}

	/*public static void main(String[] args) throws Exception{
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00077500");
		form1.setUserId("123456");
		form1.setAgentinstcode("1qa2ws");
		form1.setBegdate("20161212101010");
		form1.setTranstype("10");
		form1.setRegnum("111111");
		form1.setInstcode("109223445");
		form1.setEnddate("20161223101010");
		form1.setDwzh("55555");
		form1.setDptype("xyz");
		form1.setColpaytype("66");
		form1.setInaccop("345");
		form1.setInaccflag("Y");
		form1.setInputop("345");
		form1.setFactpayamt("1001.11");
		form1.setDwmc("阿斯顿飞过");
		form1.setPaymode("01");

	
		Handle02401_00077500 hand = new Handle02401_00077500();
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
