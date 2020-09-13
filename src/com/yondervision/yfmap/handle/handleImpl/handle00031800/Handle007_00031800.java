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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi007Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 衡水贷款明细查询
 *
 */
public class Handle007_00031800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
//		String fname = formatter.format(date)+"api007"+"SF"+form.getBodyCardNumber()+"GJJ"+form.getSurplusAccount()+".txt";
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		form.setPagenum(String.valueOf(Integer.parseInt(form.getPagenum())+1));
		List<WeiHaiAppApi007Result> list = new ArrayList<WeiHaiAppApi007Result>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();		
		WeiHaiAppApi00601Result app006Result = new WeiHaiAppApi00601Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly()+"A00016";
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			
			
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
			log.debug("前置YFMAP接收中心报文："+rexml500);
			HashMap resultMap500 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DL.xml", rexml500, req);
			log.debug("解析报文MAP："+resultMap500);
			BeanUtil.transMap2Bean(resultMap500, app40102Result);
			log.debug("MAP封装成BEAN："+app40102Result);			
			if(!"00".equals(app40102Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app40102Result.getRecode());
				modelMap.put("msg", app40102Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40102Result.getRecode()+"  ,  描述msg : "+app40102Result.getMsg());
				return modelMap;
			}	
			
			if("null".equals(app40102Result.getAccinstcode())){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无贷款");
				log.error("中心返回报文 该用户无贷款");
				return modelMap;
			}
			
			
			
			
			System.out.println("合同编号:"+app40102Result.getAccinstcode());
			form.setFreeuse1(app40102Result.getAccinstcode());
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKYEMX.xml", map, req);
			xml = xml.substring(37);
			log.debug("发往中心报文："+xml);
			
//			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
//			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "700005").trim();
			reqxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+reqxml;
			
			log.debug("接收中心报文："+reqxml);
			
//			String reqxml = "<TranCode>600029</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:54</><MTimeStamp>2014-05-13 09:33:54</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>-4928</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>123112310</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><_DOWNFILENAME>2014-04-21/downfile/lnque003.0</><InfactSum>170870.91</><InfactPunSum>0.00</><InfactIntSum>30870.91</><InfactPrinSum>140000.00</><CurRate>4.35</><CurDayBal>0.00</><AttermDate>2017-10-17</><BeginIntDate>2002-10-18</><CosignDate>2002-09-25</><LoanTerm>180</><LoanSum>140000.00</><CerId1>370620196207240511</><AccName>迟健</><AgencyBank>14</><OweAmt>0.00</><OweTerm>0</>";
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DKYEMX.xml", reqxml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app006Result);
			log.debug("MAP封装成BEAN："+app006Result);		
			
			if(!"00".equals(app006Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app006Result.getRecode());
				modelMap.put("msg", app006Result.getMsg());
				log.error("中心返回报文 状态recode :"+app006Result.getRecode()+"  ,  描述msg : "+app006Result.getMsg());
				return modelMap;
			}			
			
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			log.debug("查询贷款明细，批量文件："+filename);
			
			if(filename!=null){
				File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
				if(app006Result.getAccinstname().equals("0")){					
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据!");
					log.error("贷款明细查询上送时间段查无数据！");
					return modelMap;					
				}
				log.debug("当前页："+form.getPagenum()+"   每页条数："+form.getPagerows());
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空					
					WeiHaiAppApi007Result app007Result = new WeiHaiAppApi007Result();					
					try {
						String[] param = line.split("\\@\\|\\$");
											
						log.debug("读取文件  ："+line);
						app007Result.setEndDate(param[0]);
						app007Result.setSeqNum(param[1]);
						app007Result.setPlanPrin(param[2]);
						app007Result.setPlanInt(param[3]);
						app007Result.setFactRetPun(param[4]);						
						list.add(app007Result);
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
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", (WeiHaiAppApi007Result)list.get(i));	
			result.add(result1);
		}
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		jsonObj.put("result", result);			
		log.debug(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}

}
