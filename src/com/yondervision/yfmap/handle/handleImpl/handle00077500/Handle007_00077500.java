package com.yondervision.yfmap.handle.handleImpl.handle00077500;

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
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.zhuzhou.AppApi00701ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi007_01ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle007_00077500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi00701ZHResult app007ZHResult = new AppApi00701ZHResult();
		
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi007_01ZHResult> list = new ArrayList<AppApi007_01ZHResult>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00077500请求00701参数："+CommonUtil.getStringParams(form));
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
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKMXCX.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_DKMXCX.txt", map, req);
			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126996");
			
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126996</><TranDate>1</><TranIP>1</><TranSeq>1</><bjje>435</><curbal>4543</><fxje>45</><jkhtbh>345</><lxje>43</><repaytolamt>35</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
//			String rexml ="<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag>0</><BrcCode>00073199</><BusiSeq>133262</><ChannelSeq>0</><ChkCode></><MTimeStamp>2016-12-09 07:01:43</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-09 07:01:43</><TellCode>9999</><TranChannel>4</><TranCode>126996</><TranDate>2017-04-13</><TranIP></><TranSeq>133262</><bjje>636.18</><curbal>235179.42</><filename>F:/aaa.txt</><fxje>0.00</><jkhtbh>2013300049</><lxje>234.18</><repaytolamt>870.36</><totalnum>1</><totalpage>1</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKMXCX.txt", rexml, req);
//			resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_DKMXCX.txt", rexml, req);
			
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app007ZHResult);
			log.debug("MAP封装成BEAN："+app007ZHResult);
			if(!"0".equals(app007ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app007ZHResult.getRecode());
				modelMap.put("msg", app007ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app007ZHResult.getRecode()+"  ,  描述msg : "+app007ZHResult.getMsg());
				return modelMap;
			}
			
		}
		if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
			List<TitleInfoNameFormatBean> appapi00701ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00701"+form.getCenterId()+".result", app007ZHResult);
			Iterator<TitleInfoNameFormatBean> it1 = appapi00701ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean TitleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
				log.info("title="+TitleInfoNameFormatBean.getTitle()+"\tinfo="+TitleInfoNameFormatBean.getInfo());
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00701ZHResult);
			modelMap.put("filename", app007ZHResult.getFilename());
			modelMap.put("totalpage", app007ZHResult.getTotalpage());
			modelMap.put("totalnum", app007ZHResult.getTotalnum());
		}else {
			String filename = (String)resultMap.get("filename");
			System.out.println("查询贷款明细，批量文件："+filename);
			System.out.println("查询公积金明细，批量文件："+filename);

			if ("0".equals(app007ZHResult.getTotalnum())) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到该用户的贷款明细数据");	
				modelMap.put("totalpage", app007ZHResult.getTotalpage());
				modelMap.put("totalnum", app007ZHResult.getTotalnum());
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
				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
				while (line1 != null) {// 判断读取到的字符串是否不为空					
					try {
						AppApi007_01ZHResult aap007_01ZHResult = new AppApi007_01ZHResult();
						log.debug("读取文件  ："+line1);
						line1 = line1 + "~0";
						//String[] param = line.split("\\@\\|\\$");	
						String[] trs = line1.split("~");
						aap007_01ZHResult.setJzrq(trs[1]);
						aap007_01ZHResult.setAcYmonth(trs[2]);
						aap007_01ZHResult.setSummarycode(trs[3]);
						aap007_01ZHResult.setHkje(trs[4]);
						aap007_01ZHResult.setBjje(trs[5]);
						aap007_01ZHResult.setLxje(trs[6]);
						aap007_01ZHResult.setFxje(trs[7]);
						aap007_01ZHResult.setDkye(trs[8]);
						
						list.add(aap007_01ZHResult);
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
			
			List<TitleInfoNameFormatBean> appapi00701ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00701"+form.getCenterId()+".result", app007ZHResult);
			Iterator<TitleInfoNameFormatBean> it1 = appapi00701ZHResult.iterator();
			while (it1.hasNext()) {
				TitleInfoNameFormatBean TitleInfoNameFormatBean = (TitleInfoNameFormatBean) it1.next();
				log.info("title="+TitleInfoNameFormatBean.getTitle()+"\tinfo="+TitleInfoNameFormatBean.getInfo());
			}
			List<List<TitleInfoNameFormatBean>> detail = new ArrayList<List<TitleInfoNameFormatBean>>();	
			List<TitleInfoNameFormatBean> appapi00701Result = null;
			for(int i=0;i<list.size();i++){				
				appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00701"+form.getCenterId()+".detail", list.get(i));	
				detail.add(appapi00701Result);
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", appapi00701ZHResult);
			modelMap.put("detail", detail);
			modelMap.put("filename", app007ZHResult.getFilename());
			modelMap.put("totalpage", app007ZHResult.getTotalpage());
			modelMap.put("totalnum", app007ZHResult.getTotalnum());
		}
		
		return modelMap;
	}
}
