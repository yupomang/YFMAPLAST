package com.yondervision.yfmap.handle.handleImpl.handle00047102;

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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00201Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi007Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi002Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle007_00047102 
* @Description: 贷款明细查询
* 
*/ 
public class Handle007_00047102 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00701 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<QvZhiAppApi007Result> list = new ArrayList<QvZhiAppApi007Result>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api00701DKMXGJJ.txt";
		QvZhiAppApi00201Result app002Result = new QvZhiAppApi00201Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKMXCX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129129");
			
//			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>DKMXCX.txt</><certinum></><accname></><loancontrnum></><sumcounts></><counts></><pageflag>1</>";
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKMXCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!"0".equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			if(!app002Result.getFileName().equals("")){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+app002Result.getFileName());
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+app002Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				
				String[] paraNames = line.split("~");
				int loanaccnum=0,accname=0,certinum=0,loanamt=0,remainterm=0,curbal=0,loandate=0,enddate=0,transdate=0,termnum=0,
						summarycode=0,transstate=0,debitamt=0,creditamt=0,dkye=0,curenddate=0,repaydate=0,planprin=0,planint=0,owepun=0,initialbal=0;
				for(int i=0;i<paraNames.length;i++){
					if("loanaccnum".equals(paraNames[i])){
						loanaccnum = i;
					}else if("accname".equals(paraNames[i])){
						accname = i;
					}else if("certinum".equals(paraNames[i])){
						certinum = i;
					}else if("loanamt".equals(paraNames[i])){
						loanamt = i;
					}else if("remainterm".equals(paraNames[i])){
						remainterm = i;
					}else if("curbal".equals(paraNames[i])){
						curbal = i;
					}else if("loandate".equals(paraNames[i])){
						loandate = i;
					}else if("enddate".equals(paraNames[i])){
						enddate = i;
					}else if("transdate".equals(paraNames[i])){
						transdate = i;
					}else if("termnum".equals(paraNames[i])){
						termnum = i;
					}else if("summarycode".equals(paraNames[i])){
						summarycode = i;
					}else if("transstate".equals(paraNames[i])){
						transstate = i;
					}else if("debitamt".equals(paraNames[i])){
						debitamt = i;
					}else if("creditamt".equals(paraNames[i])){
						creditamt = i;
					}else if("dkye".equals(paraNames[i])){
						dkye = i;
					}else if("curenddate".equals(paraNames[i])){
						curenddate = i;
					}else if("repaydate".equals(paraNames[i])){
						repaydate = i;
					}else if("planprin".equals(paraNames[i])){
						planprin = i;
					}else if("planint".equals(paraNames[i])){
						planint = i;
					}else if("owepun".equals(paraNames[i])){
						owepun = i;
					}else if("initialbal".equals(paraNames[i])){
						initialbal = i;
					}				
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
//					modelMap.put("recode", "999999");
//					modelMap.put("msg", "无数据");
//					log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
//					return modelMap;
				}
				
				//目前只有交易日期及摘要，缺少“业务类型、单位缴存、个人缴存、缴存合计”
				while (line != null) {
					QvZhiAppApi007Result api00701Result = new QvZhiAppApi007Result();
					try {
						String[] param = line.split("~");									
						log.debug("读取文件  ："+line);	
						//String.format("%,.2f",Double.valueOf(app00601Result.getOwecnt()))
						api00701Result.setTermnum(param[termnum].trim());
						api00701Result.setTransdate(param[transdate].trim());
						api00701Result.setTransstate(param[transstate].trim());
						api00701Result.setSummarycode(param[summarycode].trim());
						api00701Result.setCreditamt(param[creditamt].trim());
						//贷款余额本金余额
						api00701Result.setPlanprin(param[dkye].trim());
//						api00701Result.setSummary(param[summary].trim());						
//						api00701Result.setPlansum(param[plansum].trim());
//						api00701Result.setRepayprin(param[repayprin].trim());						
//						api00701Result.setRepayint(param[repayint].trim());
//						api00701Result.setRepaypun(param[repaypun].trim());
//						api00701Result.setRepaysum(param[repaysum].trim());						
//						api00701Result.setAmt5(param[amt5].trim());
//						api00701Result.setLoanbal(param[loanbal].trim());						
						list.add(api00701Result);
						line = breader.readLine();
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

		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", list.get(i));	
			result.add(result1);
		}
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}

}