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
import java.util.List;
import java.util.ResourceBundle;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil3;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi03824ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle03829_00057400 implements CtrlHandleInter {
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
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi03824ZHResult app03824ZHResult = new AppApi03824ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
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
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_03829_142818.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142819");
			log.debug("前置YFMAP接收中心报文——可贷楼盘查询："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_03829_142818.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app03824ZHResult);
			log.debug("MAP封装成BEAN："+app03824ZHResult);
			if(!"0".equals(app03824ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app03824ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app03824ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app03824ZHResult.getRecode()+"  ,  描述msg : "+app03824ZHResult.getMsg());
				return modelMap;
			}
		}
		
		/*FtpUtil3 f=new FtpUtil3("bsp");
		String ff=app03824ZHResult.getActmp100();*/
		//  /bsprun/share/fil/LPXX_2018-04-09_5692721
		//生产18
//		String filname=ff.substring(18);
//		String filpath=ff.substring(0,18);
		//测试22
		/*String filname=ff.substring(22);
		String filpath=ff.substring(0,22);
		System.out.println("filname================="+filname);
		System.out.println("filpath=================="+filpath);
		f.getFileByServerForNB(filpath.replace("/bsptest", "").replace("/bsprun", ""),filname);	   
		File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filname);
		FileInputStream ffis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
		BufferedReader breader = new BufferedReader(isr);
		String line1 = breader.readLine(); 
		List list=new ArrayList();
		while (line1 != null) {// 判断读取到的字符串是否不为空
			try {							
				log.debug("读取文件  ："+line1);
				String[] trs = line1.split("\\|");
				HashMap map = new HashMap(); 
				map.put("projectname_wb", trs[0].trim());
				map.put("cocustname_wb", trs[1].trim());
				map.put("actmp1", trs[2].trim());
				map.put("actmp2", trs[3].trim());
				map.put("acTransdate", trs[4].trim());
				JSONObject jsonObject = JSONObject.fromObject(map);
				list.add(jsonObject);
				line1 = breader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		breader.close();
		isr.close();
		ffis.close();
		file.delete();*/
		String stdata0 = app03824ZHResult.getStdata();
		String stdata1 = app03824ZHResult.getStdata1();
		String stdata2 = app03824ZHResult.getStdata2();
		String stdata3 = app03824ZHResult.getStdata3();
		String stdata4 = app03824ZHResult.getStdata4();
		String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;
		String[] str = stdata.split("\n");
		log.info("stdata============"+stdata);
		List list=new ArrayList();
		for (int i = 0; i < str.length; i++) {
			if (str[i] != null) {
				String[] trs = str[i].split("\\|");
				HashMap map = new HashMap();
				map.put("projectname_wb", trs[0].trim());
				map.put("cocustname_wb", trs[1].trim());
				map.put("actmp1", trs[2].trim());
				map.put("actmp2", trs[3].trim());
				map.put("acTransdate", trs[4].trim());
				JSONObject jsonObject = JSONObject.fromObject(map);
				list.add(jsonObject);
			}

		}
		System.out.println(list);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("list", list);
		modelMap.put("num_web3", app03824ZHResult.getNum_web3());
		modelMap.put("num_web1", app03824ZHResult.getNum_web1());
		modelMap.put("num_web2", app03824ZHResult.getNum_web2());
		modelMap.put("num_web4", app03824ZHResult.getNum_web4());
		return modelMap;
	}
	
}
