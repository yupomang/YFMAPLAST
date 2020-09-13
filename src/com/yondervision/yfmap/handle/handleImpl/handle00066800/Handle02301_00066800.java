package com.yondervision.yfmap.handle.handleImpl.handle00066800;

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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi02301ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi023_01ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02301_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		AppApi02301ZHResult app02301ZHResult = new AppApi02301ZHResult();
		
		AppApi020Form form = (AppApi020Form)form1;
		
		AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);
			
		if(!CommonUtil.isEmpty(form.getTsounitaccname())){
			log.debug("form.getTsounitaccname():"+form.getTsounitaccname());
			form.setTsounitaccname(aes.decrypt(form.getTsounitaccname()));
		}
		List<AppApi023_01ZHResult> list = new ArrayList<AppApi023_01ZHResult>(); 
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00066800请求02301参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
			     
		HashMap resultMap = null;

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
				form.setTellCode("9999");
				form.setBrcCode("00066888");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			form.setIspaging("1");

			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_GRZYCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_GRZYCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110031");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>110031</><TranDate>1</><TranIP>1</><TranSeq>1</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			
		    resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_GRZYCX.txt", rexml, req);
//		    resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_GRZYCX.txt", rexml, req);
		    log.debug("解析报文MAP："+resultMap);
		    
			BeanUtil.transMap2Bean(resultMap, app02301ZHResult);
			log.debug("MAP封装成BEAN："+app02301ZHResult);
			if(!"0".equals(app02301ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02301ZHResult.getRecode());
				modelMap.put("msg", app02301ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02301ZHResult.getRecode()+"  ,  描述msg : "+app02301ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("个人转移，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("result", "");
				modelMap.put("totalpage", app02301ZHResult.getTotalpage());
				modelMap.put("totalnum", app02301ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}

			if ("0".equals(app02301ZHResult.getTotalnum())) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到数据，请重新输入");	
				modelMap.put("totalpage", app02301ZHResult.getTotalpage());
				modelMap.put("totalnum", app02301ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
			    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//				File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				String line1 = breader.readLine();
				while (line1 != null) {// 判断读取到的字符串是否不为空
					
					try {
						AppApi023_01ZHResult aap023_01ZHResult = new AppApi023_01ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						aap023_01ZHResult.setInum(trs[0]);
						aap023_01ZHResult.setJzrq(trs[1]);
						aap023_01ZHResult.setTsounitaccnum(trs[2]);
						aap023_01ZHResult.setTsounitaccname(trs[3]);
						aap023_01ZHResult.setMergoaccnum(trs[4]);
						aap023_01ZHResult.setTsoaccname(trs[5]);
						aap023_01ZHResult.setTsiunitaccnum(trs[6]);
						aap023_01ZHResult.setTsiunitaccname(trs[7]);
						aap023_01ZHResult.setMergiaccnum(trs[8]);
						aap023_01ZHResult.setTsiaccname(trs[9]);
						aap023_01ZHResult.setTsobank(trs[10]);
						aap023_01ZHResult.setTsibank(trs[11]);
						aap023_01ZHResult.setAgentinstcode(trs[12]);
						aap023_01ZHResult.setAgentop(trs[13]);
						aap023_01ZHResult.setYwlsh(trs[14]);
						aap023_01ZHResult.setTrstype(trs[15]);
						aap023_01ZHResult.setTranchannel(trs[16]);
						
						list.add(aap023_01ZHResult);
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
		List<TitleInfoNameFormatBean> appapi02301Result = null;
		
		for(int i=0;i<list.size();i++){
		 appapi02301Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02301"+form.getCenterId()+".detail", list.get(i));
		 detail.add(appapi02301Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app02301ZHResult.getTotalpage());
		modelMap.put("totalnum", app02301ZHResult.getTotalnum());
		modelMap.put("filename", app02301ZHResult.getFilename());
		return modelMap;
	}
	
}
