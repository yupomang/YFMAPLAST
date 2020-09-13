package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.zhuzhou.AppApi00702ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi007_02ZHResult;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle00702_00073300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00702ZHResult app00702ZHResult = new AppApi00702ZHResult();
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi007_02ZHResult> list = new ArrayList<AppApi007_02ZHResult>();
		
		log.debug("00073300请求00702参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		HashMap resultMap = null;
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			} 

			if("40".equals(form.getChannel().trim())||"50".equals(form.getChannel().trim())){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HKJHCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_HKJHCX.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126997");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126997</><TranDate>1</><TranIP>1</><TranSeq>1</><jkhtbh>32354354234354</><xingming>34</><zjhm>34254353452</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_HKJHCX.txt", rexml, req);
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_HKJHCX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00702ZHResult);
			log.debug("MAP封装成BEAN："+app00702ZHResult);
			if(!"0".equals(app00702ZHResult.getRecode())){
				modelMap.put("recode", app00702ZHResult.getRecode());
				modelMap.put("msg", app00702ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app00702ZHResult.getRecode()+"  ,  描述msg : "+app00702ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = app00702ZHResult.getFilename();
			System.out.println("查询贷款还款计划明细，批量文件："+filename);
			System.out.println("查询公积金明细，批量文件："+filename);
			if("40".equals(form.getChannel())||"50".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app00702ZHResult.getTotalpage());
				modelMap.put("totalnum", app00702ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				modelMap.put("result", "");
				return modelMap;
			}

			if ("0".equals(app00702ZHResult.getTotalnum())) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到数据，请重新输入");	
				modelMap.put("totalpage", app00702ZHResult.getTotalpage());
				modelMap.put("totalnum", app00702ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
			    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//				File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				String line1 = breader.readLine();
				while (line1 != null) {// 判断读取到的字符串是否不为空
					AppApi007_02ZHResult app007_02ZHResult = new AppApi007_02ZHResult();
					MoneyNumberTran mnTran = new MoneyNumberTran();
					try {
						log.debug("读取文件  ："+line1);	
						line1 = line1 + "~0";
						String[] trs = line1.split("~");
						app007_02ZHResult.setiCount(trs[0]);
						app007_02ZHResult.setAcEnddate(trs[1]);
						app007_02ZHResult.setDqqc(trs[2]);
						app007_02ZHResult.setInitialbal(mnTran.moneyTran(trs[3]));
						app007_02ZHResult.setPlanprin(mnTran.moneyTran(trs[4]));
						app007_02ZHResult.setPlanint(mnTran.moneyTran(trs[5]));
						app007_02ZHResult.setdMonthrepayamt(mnTran.moneyTran(trs[6]));
						
						list.add(app007_02ZHResult);
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
		if("10".equals(form.getChannel())||"20".equals(form.getChannel())){
			app00702ZHResult.setJkhtbh(Tools.getZZDesensitizationLoancontrnum(app00702ZHResult.getJkhtbh()));
//			app00702ZHResult.setZjhm(Tools.getDesensitizationCertinum(app00702ZHResult.getZjhm()));
			
		}
		List<TitleInfoNameFormatBean> appapi00702ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00702"+form.getCenterId()+".result", app00702ZHResult);
		Iterator<TitleInfoNameFormatBean> it1 = appapi00702ZHResult.iterator();
		while (it1.hasNext()) {
			TitleInfoNameFormatBean TitleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
			log.info("title="+TitleInfoNameFormatBean.getTitle()+"\tinfo="+TitleInfoNameFormatBean.getInfo());
		}
		
		List<List<TitleInfoBean>> detail = new ArrayList();
		List<TitleInfoBean> appapi00702Detail = null;
		for(int i=0;i<list.size();i++){
			appapi00702Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00702"+form.getCenterId()+".detail", list.get(i));			
			detail.add(appapi00702Detail);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00702ZHResult);
		modelMap.put("detail", detail);
		modelMap.put("totalpage", app00702ZHResult.getTotalpage());
		modelMap.put("totalnum", app00702ZHResult.getTotalnum());
		modelMap.put("filename", app00702ZHResult.getFilename());
		return modelMap;
	}

}
