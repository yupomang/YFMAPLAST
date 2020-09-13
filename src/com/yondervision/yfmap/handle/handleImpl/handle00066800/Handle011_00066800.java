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
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.result.zhuzhou.AppApi007_02ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi01101ZHResult;
import com.yondervision.yfmap.result.zhuzhou.AppApi011_01ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

public class Handle011_00066800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {

		AppApi00501Form form = (AppApi00501Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00066800请求01101参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101ZHResult app01101ZHResult = new AppApi01101ZHResult();	
		List<AppApi011_01ZHResult> list = new ArrayList<AppApi011_01ZHResult>();
		
		HashMap resultMap = null;
		if(Constants.method_BSP.equals(send)){//xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
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
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			} 

			if(form.getChannel().trim().equals("40")||form.getChannel().trim().equals("50")){
				form.setIspaging("0");
				form.setPagenum("1");
				form.setPagerows("9999999");
			}
			HashMap map = BeanUtil.transBean2Map(form);	
			/**
			 * 贷款进度查询
			 * 查询机渠道调用交易码为126993的交易
			 * 其他渠道调用交易码为126999的交易
			 */
			if (form.getChannel().trim().equals("50")) {
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCXCXJ.txt", map, req);
//				String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DKJDCXCXJ.txt", map, req);			
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();			
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126993");
				
//				String rexml = "<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126993</><TranDate>1</><TranIP>1</><TranSeq>1</><jkhtbh>4</><xingming>4</><filename>F:/aaa.txt</><totalpage>2</><totalnum>2</>";
				
				log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
				
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKJDCXCXJ.txt", rexml, req);
//				resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DKJDCXCXJ.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);	
				
				BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
				log.debug("MAP封装成BEAN："+app01101ZHResult);
				
				if(!"0".equals(app01101ZHResult.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app01101ZHResult.getRecode());
					modelMap.put("msg", app01101ZHResult.getMsg());			
					log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
					return modelMap;
				}					
				List<TitleInfoNameFormatBean> appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result1", app01101ZHResult);
				Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
				while (it1.hasNext()) {
					TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
					log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
				}	
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", appapi01101ZHResult);
				modelMap.put("totalpage", app01101ZHResult.getTotalpage());
				modelMap.put("totalnum", app01101ZHResult.getTotalnum());
				modelMap.put("filename", app01101ZHResult.getFilename());
				
			} else {
				
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKJDCX.txt", map, req);
//				String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REQ_DKJDCX.txt", map, req);			
				log.debug("发往中心报文："+xml);
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();			
				String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "126999");
				
//				String rexml = "<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><TellCode>1</><TranChannel>1</><TranCode>126999</><TranDate>1</><TranIP>1</><TranSeq>1</><loancontrstate>23</><appdate>3</><jkhtbh>4</><htdkje>4</><hkzh>4</><gjjflag>4</><fwzl>4</><apprnum>4</><dkll>4</><dkqs>4</><dqjhhkje>4</><dkye>4</><swtyhmc>4</><xingming>4</><ydfkrq>4</><ydhkr>4</><yqbjze>4</><yqlxze>4</><zjhm>4</><filename>F:/aaa.txt</><owepun>4</><ljyqqs>4</><totalpage>2</><totalnum>2</>";
//				String rexml = "<AuthCode1></><AuthCode2></><AuthCode3></><AuthFlag>0</><BrcCode>00073199</><BusiSeq>131819</><ChannelSeq>0</><ChkCode></><MTimeStamp>2016-12-08 10:07:24</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-08 10:07:24</><TellCode>9999</><TranChannel>4</><TranCode>126999</><TranDate>2017-04-13</><TranIP></><TranSeq>131819</><appdate>2012-03-26</><apprnum>20124000198</><dkll>2.7083000</><dkqs>180</><dkye>99945.41</><dqjhhkje>943.46</><filename>F:/aaa.txt</><fwzl>攸县城关镇西阁居委会中天印象中天印象1栋 1单元 501室</><gjjflag></><grzh>1110139957</><hkzh>583358966394</><htdkje>165000.00</><jkhtbh>2012033</><ljyqqs>0</><loancontrstate>09</><owepun>0.00</><swtyhmc>中行株洲县支行</><totalnum>1</><totalpage>1</><xingming>欧阳高辉</><ydfkrq>20120327</><ydhkr>26</><yqbjze>0.00</><yqlxze>0.00</><zjhm>110101198001200017</>";
				
				log.debug("前置YFMAP接收中心报文——贷款进度查询："+rexml);
				
				resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKJDCX.txt", rexml, req);
//				resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00073300/BSP_REP_DKJDCX.txt", rexml, req);
				log.debug("解析报文MAP："+resultMap);	
				
				BeanUtil.transMap2Bean(resultMap, app01101ZHResult);
				log.debug("MAP封装成BEAN："+app01101ZHResult);
				
				if(!"0".equals(app01101ZHResult.getRecode())){
					modelMap.clear();
					modelMap.put("recode", app01101ZHResult.getRecode());
					modelMap.put("msg", app01101ZHResult.getMsg());			
					log.error("中心返回报文 状态recode :"+app01101ZHResult.getRecode()+"  ,  描述msg : "+app01101ZHResult.getMsg());
					return modelMap;
				}	
				if(form.getChannel().trim().equals("40")){
					List<TitleInfoNameFormatBean> appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result", app01101ZHResult);
					Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
					while (it1.hasNext()) {
						TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
						log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
					}	
					modelMap.clear();
					modelMap.put("recode", "000000");
					modelMap.put("msg", "成功");
					modelMap.put("result", appapi01101ZHResult);
					modelMap.put("totalpage", app01101ZHResult.getTotalpage());
					modelMap.put("totalnum", app01101ZHResult.getTotalnum());
					modelMap.put("filename", app01101ZHResult.getFilename());
				}else {
					String filename = app01101ZHResult.getFilename();
					System.out.println("查询贷款进度查询，批量文件："+filename);

					if ("0".equals(app01101ZHResult.getTotalnum())) {
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "未查询到数据，请重新输入");	
						modelMap.put("totalpage", app01101ZHResult.getTotalpage());
						modelMap.put("totalnum", app01101ZHResult.getTotalnum());
						modelMap.put("filename", filename);
						return modelMap;
					}
					if(!CommonUtil.isEmpty(filename)){
						FtpUtil f=new FtpUtil("bsp");
					    f.getFileByServer(filename);				    
					    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
//						File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
						FileInputStream ffis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(ffis, "GBK");
						BufferedReader breader = new BufferedReader(isr);
						String line = breader.readLine();
						String line1 = breader.readLine();
						while (line1 != null) {// 判断读取到的字符串是否不为空
							AppApi011_01ZHResult app011_01ZHResult = new AppApi011_01ZHResult();
							MoneyNumberTran mnTran = new MoneyNumberTran();
							try {
								log.debug("读取文件  ："+line1);	
								line1 = line1 + "~0";
								String[] trs = line1.split("~");
								app011_01ZHResult.setDkzt(trs[2]);
								app011_01ZHResult.setAcDkzt(trs[3]);
								app011_01ZHResult.setAcState(trs[4]);
								app011_01ZHResult.setJkhtbh(trs[1]);						
								if (trs[5].length()>10) {
									app011_01ZHResult.setDate(trs[5].substring(0, 10));
								}else {
									app011_01ZHResult.setDate(trs[5]);
								}
								list.add(app011_01ZHResult);
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
					List<TitleInfoNameFormatBean> appapi01101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".result", app01101ZHResult);
					Iterator<TitleInfoNameFormatBean> it1 = appapi01101ZHResult.iterator();
					while (it1.hasNext()) {
						TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean) it1.next();
						log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
					}				
					List<List<TitleInfoNameFormatBean>> detail = new ArrayList();
					List<TitleInfoNameFormatBean> appapi00702Detail = null;
					for(int i=0;i<list.size();i++){
						appapi00702Detail = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi01101"+form.getCenterId()+".detail", list.get(i));			
						detail.add(appapi00702Detail);
					}					
					modelMap.clear();
					modelMap.put("recode", "000000");
					modelMap.put("msg", "成功");
					modelMap.put("result", appapi01101ZHResult);
					modelMap.put("detail", detail);
					modelMap.put("totalpage", app01101ZHResult.getTotalpage());
					modelMap.put("totalnum", app01101ZHResult.getTotalnum());
					modelMap.put("filename", app01101ZHResult.getFilename());
					
				}			
		    }	
			
		}
	
	return modelMap;
	
  }
	
}
