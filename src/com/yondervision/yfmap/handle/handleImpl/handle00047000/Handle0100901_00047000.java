package com.yondervision.yfmap.handle.handleImpl.handle00047000;

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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01009Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.hulunbuir.HulunbuirAppApi01009Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle0100901_00047000 
* @Description: 个人资料变更
* 
*/ 
public class Handle0100901_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	 private static final ResourceBundle ReadProperties;		
		static{
		  //读取属性文件
			ReadProperties = ResourceBundle.getBundle("ftp");
		}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi0100902 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01009Form form = (AppApi01009Form)form1;
		List<HulunbuirAppApi01009Result> list = new ArrayList<HulunbuirAppApi01009Result>();
		AES aes =new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		form.setFullName(aes.decrypt(form.getFullName()));
		log.debug("公积金余额查询请求参:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		HulunbuirAppApi01009Result app40101Result = new HulunbuirAppApi01009Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if("10".equals(form.getChannel()))
			{
				form.setChannel("21");
			}else if("20".equals(form.getChannel()))
			{
				form.setChannel("31");
			}
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String fname = formatter2.format(date)+"_"+form.getSurplusAccount()+"_"+"api0100901GJJMX.txt";
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_BGXX"+form.getDrawSignType()+".txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "160015");
			
//			String rexml = "<AuthCode1>cweb</><AuthCode2>cweb</><AuthCode3>cweb</><AuthFlag>0</><BrcCode>12345678</><BusiSeq>1269</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2016-08-10 05:04:09</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-08-10 05:04:09</><TellCode>cweb</><TranChannel>00</><TranCode>112813</><TranDate>2016-08-10</><TranIP>192.168.10.20</><TranSeq>1269</><accname>刘文龙</><accname1>正常</><accnum>801003368857</><bal>10366.79</><basenum>2145.60</><certinum>220122199107225011</><indipayamt>257.47</><indipaysum>514.94</><indiprop>0.120</><lasttransdate>2016-07-01</><lpaym>201604</><opnaccdate>2014-07-01</><unitaccname>乌兰察布电业局</><unitaccnum>01030032</><unitpayamt>257.47</><unitprop>0.120</>";
			//String rexml = "<AuthCode1>9003</><AuthCode2>9003</><AuthCode3>9003</><AuthFlag></><BrcCode>00003102</><BusiSeq>171</><ChannelSeq>695491</><ChkCode>9003</><FinancialDate>2016-07-01</><HostBank></><MTimeStamp>2016-11-15 12:21:46</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-09 11:26:37</><SubBank></><TellCode>9003</><TranChannel>01</><TranCode>311602</><TranDate>2016-11-21</><TranIP>18.16.2.9</><TranSeq>171</><accname>李硕欣</><accnum>104302</><balance>61097.88</><bankname>中行包头分行营业部</><basenumber>5436.00</><cardno>6013828402007533909</><certinum>150203196809222768</><indiprop>12.00</><lastpaydate>2016年04月</><monpaysum>1306.00</><peraccstate>正常</><peryje>653.00</><prehapdate>2016年07月01日</><prop>24.00</><unitaccname>包钢集团电气有限公司</><unitaccnum>2112</><unitprop>12.00</><unityje>653.00</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_BGXX"+form.getDrawSignType()+".txt", rexml, req);
			log.debug("YFMAP接收解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!Constants.sucess_ts.equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
			if(!app40101Result.getFileName().equals("")){
				String filename = app40101Result.getFileName().split("/")[app40101Result.getFileName().split("/").length-1];
				app40101Result.setFileName(filename);
				log.debug("远程文件名filaneme====="+filename);
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+app40101Result.getFileName());
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+app40101Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();

//				int num = (Integer.valueOf(form.getPagenum())-1)*Integer.valueOf(form.getPagerows());
//				int pnum = 0;
//				int sum = 0;
//				if(line != null){
//					sum=1;
//				}
				int i = 0;
				while (line != null) {
					HulunbuirAppApi01009Result aa00201Result = new HulunbuirAppApi01009Result();
					try {
//						if(pnum<Integer.valueOf(form.getPagerows())){
//							if(sum>num){
							if(i!=0){
								String[] param = line.split("~");									
								log.debug("读取文件  ："+line);
								aa00201Result.setAcDescfieldname(param[0].trim());	
								aa00201Result.setBfchgval(param[1].trim());
								aa00201Result.setAfchgval(param[2].trim());
								list.add(aa00201Result);
//								pnum++;
								}
//							}
//						}
//						sum+=1;
						line = breader.readLine();
						i++;
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				file.delete();
			}
		}
		//app40101Result.setPersonalPaymentRate(String.format("%,.2f",Double.valueOf(app40101Result.getPersonalPaymentRate())*100)+"%");
		//app40101Result.setCompanyPaymentRate(String.format("%,.2f",Double.valueOf(app40101Result.getCompanyPaymentRate())*100)+"%");
		List<TitleInfoBean> appapi40101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01009"+form.getCenterId()+".result"+form.getDrawSignType(), app40101Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40101Result);
		log.info(Constants.LOG_HEAD+"appApi0100902 end.");
		return modelMap;
	}
	public static void main(String[] args) {
		String req = "";
		String rexml = "<AuthCode1>9003</><AuthCode2>9003</><AuthCode3>9003</><AuthFlag></><BrcCode>00003102</><BusiSeq>171</><ChannelSeq>695491</><ChkCode>9003</><FinancialDate>2016-07-01</><HostBank></><MTimeStamp>2016-11-15 12:21:46</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2016-12-09 11:26:37</><SubBank></><TellCode>9003</><TranChannel>01</><TranCode>311602</><TranDate>2016-11-21</><TranIP>18.16.2.9</><TranSeq>171</><accname>李硕欣</><accnum>104302</><balance>61097.88</><bankname>中行包头分行营业部</><basenumber>5436.00</><cardno>6013828402007533909</><certinum>150203196809222768</><indiprop>12.00</><lastpaydate>2016年04月</><monpaysum>1306.00</><peraccstate>正常</><peryje>653.00</><prehapdate>2016年07月01日</><prop>24.00</><unitaccname>包钢集团电气有限公司</><unitaccnum>2112</><unitprop>12.00</><unityje>653.00</>";
		HashMap resultMap = MessageCtrMain.analysisPacket("5", "D:/tomcat/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00047201/BSP_REP_YECX.txt", rexml, req);
		
	}
}
