package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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

import com.ibm.db2.jcc.a.a;
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
import com.yondervision.yfmap.result.ningbo.AppApi02101ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi021_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle02101_00057400 implements CtrlHandleInter {
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

		log.debug("00057400请求02101参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();		
		AppApi02101ZHResult app02101ZHResult = new AppApi02101ZHResult();
		 
		List<AppApi021_01ZHResult> list = new ArrayList<AppApi021_01ZHResult>();
		log.debug("form:"+form);
		HashMap resultMap = null;
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
			AES aes = new AES(form.getCenterId() ,form.getChannel() ,form.getAppid() ,null);		
//			form.setClientIp(aes.decrypt(form.getClientIp()));
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
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("10")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if(!"1".equals(form.getAESFlag()) && (!CommonUtil.isEmpty(form.getClientIp()))&&form.getChannel().trim().equals("50")){
				log.debug("form.getClientIp(): " +form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}
			
			HashMap map = BeanUtil.transBean2Map(form);	
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel"+form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DWDZDCX.txt", map, req);
			//String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REQ_DWDZDCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142508");
			
			//String rexml ="<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>05740008</><BusiSeq>31505828</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-12-13 11:04:03</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-13 11:04:03</><TellCode>cweb</><TranChannel>00</><TranCode>142508</><TranDate>2016-12-13</><TranIP>172.16.0.161</><TranSeq>31505828</><actmp100>E://eee.txt</><num_web1>1</><num_web2>10</><num_web3>6</><num_web4>1</>";
			
			log.debug("前置YFMAP接收中心报文——单位公积金明细查询："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DWDZDCX.txt", rexml, req);
			//resultMap = MessageCtrMain.analysisPacket(msgType, "E:/mcq/workspaceEclipse/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00057400/BSP_REP_DWDZDCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app02101ZHResult);
			log.debug("MAP封装成BEAN："+app02101ZHResult);
			if(!"0".equals(app02101ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app02101ZHResult.getRecode());
				modelMap.put("msg", app02101ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app02101ZHResult.getRecode()+"  ,  描述msg : "+app02101ZHResult.getMsg());
				return modelMap;
			}
		}
		String filename = (String)resultMap.get("filename");
		System.out.println("查询公积金明细，批量文件："+filename);
		if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("result", "");
			modelMap.put("filename", filename);
			modelMap.put("totalpage", app02101ZHResult.getTotalpage());
			modelMap.put("totalnum", app02101ZHResult.getTotalnum());
			modelMap.put("pagenum", app02101ZHResult.getPagenum());
			modelMap.put("pagerows", app02101ZHResult.getPagerows());
			return modelMap;
		}

		if ("0".equals(app02101ZHResult.getTotalnum())) {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "未查询到数据，请重新输入");	
			modelMap.put("totalpage", app02101ZHResult.getTotalpage());
			modelMap.put("totalnum", app02101ZHResult.getTotalnum());
			modelMap.put("pagenum", app02101ZHResult.getPagenum());
			modelMap.put("pagerows", app02101ZHResult.getPagerows());
			modelMap.put("filename", filename);
			return modelMap;
		}
		if(!CommonUtil.isEmpty(filename)){
			FtpUtil f=new FtpUtil("bsp");
			f.getFileByServerForNB(filename);
			File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
			//File file = new File("/wls/eee.txt");
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			//String line = breader.readLine();
			String line1 = breader.readLine();
			System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
			while (line1 != null) {// 判断读取到的字符串是否不为空
				try {
					AppApi021_01ZHResult app021_01ZHResult = new AppApi021_01ZHResult();
					MoneyNumberTran mnTran = new MoneyNumberTran();
					log.debug("读取文件  ："+line1);
					line1 = line1 + " ";
					String[] trs = line1.split("\\|");
					app021_01ZHResult.setJzrq(trs[0].trim());
					app021_01ZHResult.setSummary(trs[1].trim());
					app021_01ZHResult.setFse(trs[2].trim());
					app021_01ZHResult.setBal(trs[3].trim());
					app021_01ZHResult.setRemark(trs[4].trim());
					list.add(app021_01ZHResult);
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
	
		List<List<TitleInfoNameFormatBean>> detail = new ArrayList<List<TitleInfoNameFormatBean>>();	
		List<TitleInfoNameFormatBean> appapi02101Result = null;
		for(int i=0;i<list.size();i++){		
			appapi02101Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi02101"+form.getCenterId()+".detail", list.get(i));	
			detail.add(appapi02101Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", detail);
		modelMap.put("filename", app02101ZHResult.getFilename());
		modelMap.put("totalpage", app02101ZHResult.getTotalpage());
		modelMap.put("totalnum", app02101ZHResult.getTotalnum());
		modelMap.put("pagenum", app02101ZHResult.getPagenum());
		modelMap.put("pagerows", app02101ZHResult.getPagerows());
		return modelMap;
	}
	
	/*public static void main(String[] args){
		AppApi020Form form1 = new AppApi020Form();
		ModelMap modelMap = new ModelMap();
		form1.setCenterId("00057400");
		form1.setUserId("123456");
		form1.setChannel("10");
		form1.setUnitaccnum("aaaaa");
		form1.setIndiacctype("1");
		Handle02101_00057400 hand = new Handle02101_00057400();
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
