package com.yondervision.yfmap.handle.handleImpl.handle00031800;

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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00201Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi002Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 衡水公积金明细
 *
 */
public class Handle002_00031800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
//    private static final ResourceBundle ReadProperties;		
//	static{
//	  //读取属性文件
//		ReadProperties = ResourceBundle.getBundle("ftp");
//	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		AppApi00201Form form = (AppApi00201Form)form1;
		List<WeiHaiAppApi002Result> list = new ArrayList<WeiHaiAppApi002Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api002"+form.getStartdate().substring(0, 4)+"SFGJJ"+form.getSurplusAccount();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		form.setPagenum(String.valueOf(Integer.parseInt(form.getPagenum())+1));
		WeiHaiAppApi00201Result app002Result = new WeiHaiAppApi00201Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly()+"A00015";
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());
			form.setYear(form.getStartdate().substring(0, 4));		
			
			
			AppApi40102Form form500 = new AppApi40102Form();
			form500.setSendDate(formatter1.format(date));
			form500.setSendTime(formatter2.format(date));
			form500.setSurplusAccount(form.getSurplusAccount());
			form500.setNewpassword("NULL");
			form500.setBodyCardNumber(form.getBodyCardNumber());
			WeiHaiAppApi40101Result app40102Result = new WeiHaiAppApi40101Result();
			HashMap map500 = BeanUtil.transBean2Map(form500);
			String xml500 = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DL.xml", map500, req);
			xml500 = xml500.substring(37);
			log.debug("前置YFMAP发往中心报文："+xml500);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml500 = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml500, "700001").trim();
			rexml500 = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+rexml500;
			log.debug("中心返回前置YFMAP报文："+rexml500);
			HashMap resultMap500 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DL.xml", rexml500, req);
			log.debug("解析报文MAP："+resultMap500);
			BeanUtil.transMap2Bean(resultMap500, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);			
			if(!"00".equals(app40102Result.getRecode())){
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}	
			
			
			
			form.setSurplusAccount(app40102Result.getAccnum());
			HashMap map = BeanUtil.transBean2Map(form);	
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YEMXCX.xml", map, req);
			xml = xml.substring(37);
			log.debug("发往中心报文："+xml);	
				
//			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
//			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "700003").trim();
			reqxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+reqxml;
			
			log.debug("核心返回YFMAP报文:"+reqxml);
			
//			String reqxml = "<TranCode>600015</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:48</><MTimeStamp>2014-05-13 09:33:48</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>-4927</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>123112310</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><AccNum>00265744</><CertiNum>370620196207240511</><UnitAccNum>000341</><AccName>迟健</><_DOWNFILENAME>2014-04-21/downfile/lnque002.0</><AmtSum1>0.00</><OpenDate>1996-07-01</><Balance>0.00</><UnitAccName>威海市旅游码头有限责任公司</><AmtSum2>7063.30</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YEMXCX.xml", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);			
			
			if(!"00".equals(app002Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
						
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询公积金明细，批量文件："+filename);
			if(filename!=null){
				File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
				if(app002Result.getAccinstname().equals("0")){				
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据!");
					log.error("公积金明细查询上送时间段查无数据！");
					return modelMap;				
				}
				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空
					WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();
					try {
						log.debug("读取文件  ："+line);
						if(!"@|$".equals(line.trim())){
							String[] param = line.trim().split("\\@\\|\\$");						
							aa00201Result.setTransdate(param[0].trim());
							aa00201Result.setBusiamt1(param[1].trim());
							aa00201Result.setSummary(param[2].trim());
							aa00201Result.setBalance(param[3].trim());
							aa00201Result.setPaybegindate(param[4].trim());
							log.debug("param[0]:"+param[0]+"    param[1]:"+param[1]+"   param[2]:"+param[2]+"   param[3]:"+param[3]+"   param[4]:"+param[4]);
							list.add(aa00201Result);
						}
						line = breader.readLine();
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
				modelMap.put("msg", "批量明细信息处理异常");
				return modelMap;
			}
		}
		System.out.println("批量记录个数:"+list.size());
		List<List<TitleInfoBean>> result = new ArrayList();		
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(result1);
			log.debug("result.add(result1):"+result1);
		}
		/***************************************************肇庆 201407***********************************/
		
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		jsonObj.put("result", result);			
		log.debug(jsonObj);
		/* 模拟返回结束  */		
		System.out.println("最终返回结果明细个数:"+result.size());
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}

}
