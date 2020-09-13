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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil3;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00122ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi00201ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi00131ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00131_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form) form1;
		List<AppApi00131ZHResult> list = new ArrayList<AppApi00131ZHResult>();

		log.debug("00057400请求00131参数：" + CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send" + form.getCenterId()).trim();
		AppApi00122ZHResult app00122ZHResult = new AppApi00122ZHResult();
		if (Constants.method_BSP.equals(send)) {
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType" + form.getCenterId()).trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key" + form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type" + form.getCenterId()).trim());
			AES aes = new AES(form.getCenterId(), form.getChannel(), form.getAppid(), null);

			if (!CommonUtil.isEmpty(form.getClientIp())
					&& !"1".equals(form.getAESFlag())) {
//				form.setClientIp(aes.decrypt(form.getClientIp()));
			}
			if (!CommonUtil.isEmpty(form.getClientIp()) && form.getChannel().trim().equals("53")) {
				log.debug("form.getClientIp(): " + form.getClientIp());
				form.setClientIp("10.33.11.35");
			}
			if ((!form.getChannel().trim().equals("40")) && (!form.getChannel().trim().equals("92")) && (!form.getChannel().trim().equals("96")) && (!form.getChannel().trim().equals("52")) && (!form.getChannel().trim().equals("91"))) {
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
//			form.setIspaging("1");

			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapichannel" + form.getChannel()).trim());
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REQ_00131_111408.txt", map, req);
			log.debug("前置YFMAP发往中心报文：" + xml);

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111508");
			log.debug("前置YFMAP接收中心报文——个人明细查询网厅：" + rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REP_00131_111408.txt", rexml, req);
			log.debug("解析报文MAP：" + resultMap);

			BeanUtil.transMap2Bean(resultMap, app00122ZHResult);
			log.debug("MAP封装成BEAN：" + app00122ZHResult);
			if (!"0".equals(app00122ZHResult.getRecode())) {
				modelMap.clear();
				modelMap.put("recode", app00122ZHResult.getRecode());
				modelMap.put("msg", "错误信息：" + app00122ZHResult.getMsg());
				log.error("中心返回报文 状态recode :" + app00122ZHResult.getRecode() + "  ,  描述msg : " + app00122ZHResult.getMsg());
				return modelMap;
			}
		}
		if ("40".equals(form.getChannel())) {
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			//modelMap.put("rsufilename", app00122ZHResult.getRsufilename());
			//modelMap.put("rsufilepath", app00122ZHResult.getRsufilepath());
			String stdata0 = app00122ZHResult.getStdata();
			String stdata1 = app00122ZHResult.getStdata1();
			String stdata2 = app00122ZHResult.getStdata2();
			String stdata3 = app00122ZHResult.getStdata3();
			String stdata4 = app00122ZHResult.getStdata4();
			String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;
			modelMap.put("stdata", stdata);
			modelMap.put("consum", app00122ZHResult.getConsum());
			modelMap.put("unitaccnum", app00122ZHResult.getUnitaccnum());
			modelMap.put("unitaccname", app00122ZHResult.getUnitaccname());
			//modelMap.put("basenum", app00122ZHResult.getBasenum());
			return modelMap;
		} else {
			String stdata0 = app00122ZHResult.getStdata();
			String stdata1 = app00122ZHResult.getStdata1();
			String stdata2 = app00122ZHResult.getStdata2();
			String stdata3 = app00122ZHResult.getStdata3();
			String stdata4 = app00122ZHResult.getStdata4();
			String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;
			String[] str = stdata.split("&&");
			log.info("str.length======" + str.length);
			for (int i = 0; i < str.length; i++) {
				System.out.println(i + " --->" + str[i]);
				if (str[i] != null) {// 判断读取到的字符串是否不为空
					AppApi00131ZHResult app00131ZHResult = new AppApi00131ZHResult();
					log.debug("读取文件  ：" + str[i]);
					String[] trs = str[i].split("\\|");
					app00131ZHResult.setUnitaccnum(trs[0].trim());
					app00131ZHResult.setTransdate(trs[1].trim());
					String summarycode = PropertiesReader.getProperty("yingshe.properties", "summary" + trs[2].trim());
					app00131ZHResult.setSummarycode(summarycode);
					app00131ZHResult.setAccrual(trs[3].trim());
					app00131ZHResult.setBal(trs[4].trim());
					String indiacctype = PropertiesReader.getProperty("yingshe.properties", "indiacctypeType" + trs[5].trim() + form.getCenterId());
					app00131ZHResult.setIndiacctype(indiacctype);
					app00131ZHResult.setBasenum(trs[6].trim());
					list.add(app00131ZHResult);
				}
			}
			List<List<TitleInfoNameFormatBean>> detail = new ArrayList();
			List<TitleInfoNameFormatBean> appapi00131Result = null;
			for (int m = 0; m < list.size(); m++) {
				appapi00131Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00131" + form.getCenterId() + ".detail", list.get(m));
				detail.add(appapi00131Result);
				log.info("m======" + m);
			}

			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("detail", detail);
			return modelMap;
		}
	}
}
		/*if(!CommonUtil.isEmpty(app00122ZHResult.getRsufilename())){
			FtpUtil3 f=new FtpUtil3("bsp");
			String filpath=app00122ZHResult.getFilepath();
			f.getFileByServerForNB(filpath.replace("/bsptest", "").replace("/bsprun", ""),app00122ZHResult.getRsufilename());	    
			File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app00122ZHResult.getRsufilename());
			//File file = new File("/wls/ddd.txt");
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
			BufferedReader breader = new BufferedReader(isr);
			//String line = breader.readLine(); 
			String line1 = breader.readLine(); 
			while (line1 != null) {// 判断读取到的字符串是否不为空
				try {							
					AppApi00131ZHResult app00131ZHResult = new AppApi00131ZHResult();
					log.debug("读取文件  ："+line1);
					String[] trs = line1.split("\\|");
					app00131ZHResult.setUnitaccnum(trs[0].trim());
					app00131ZHResult.setTransdate(trs[1].trim());
					String summarycode=PropertiesReader.getProperty("yingshe.properties", "summary"+trs[2].trim());
					app00131ZHResult.setSummarycode(summarycode);
					app00131ZHResult.setAccrual(trs[3].trim());
					app00131ZHResult.setBal(trs[4].trim());
					String indiacctype = PropertiesReader.getProperty("yingshe.properties","indiacctypeType"+trs[5].trim()+form.getCenterId());
					app00131ZHResult.setIndiacctype(indiacctype);
					app00131ZHResult.setBasenum(trs[6].trim());
					list.add(app00131ZHResult);
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
		List<List<TitleInfoNameFormatBean>> detail = new ArrayList();					
		List<TitleInfoNameFormatBean> appapi00131Result = null;
		for(int i=0;i<list.size();i++){
			appapi00131Result                           = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00131"+form.getCenterId()+".detail", list.get(i));
			detail.add(appapi00131Result);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", detail);
		return modelMap;*/

