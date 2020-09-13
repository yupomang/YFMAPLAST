package com.yondervision.yfmap.handle.handleImpl.handle00041100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00907Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00907Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 取中心还款试算
 */
public class Handle00907_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00907Form form = (AppApi00907Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		DaLianAppApi00907Result app00907Result = new DaLianAppApi00907Result();
		List<AppApi00101Result> gjjlist = new ArrayList<AppApi00101Result>();
		List<AppApi00101Result> sdlist = new ArrayList<AppApi00101Result>();
		List<TitleInfoBean> appapi00907Result = null;
		List<List<TitleInfoBean>> detailgjj = new ArrayList<List<TitleInfoBean>>();	
		List<List<TitleInfoBean>> detailsd = new ArrayList<List<TitleInfoBean>>();
		
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00907");//*************************************
		para.setName("取中心还款试算");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000007");
		para.setYwlb(Constants.logTableYwlb_gg);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());
		
		if(Constants.method_FSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKSS.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000004");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi001</transCode><recvDate>2015-04-12</recvDate><recvTime>12:12:111</recvTime><sendSeqno>1234567890</sendSeqno><key>1</key><centerSeqno>9999911111</centerSeqno><recode>000000</recode><msg>成功</msg></head><body><dqys>1</dqys><dqysjls>10000</dqysjls><zjls>1</zjls><zys>1</zys><gjjdknll>4.5%</gjjdknll><gjjdkhkze>300000.00</gjjdkhkze><gjjdklxze>110000.00</gjjdklxze><gjjdkyhke>3000.00</gjjdkyhke><sydknll>6.0%</sydknll><sydkhkze>200000.00</sydkhkze><sydklxze>80000.00</sydklxze><sydkyhke>2000.00</sydkyhke><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail><detail><hkqh>2</hkqh><gjjdkyhbj>3,000.00</gjjdkyhbj><gjjdkyhlx>1,000.00</gjjdkyhlx><gjjdkyhe>4,000.00</gjjdkyhe><gjjdkye>240,000.00</gjjdkye><sydkyhbj>2,000.00</sydkyhbj><sydkyhlx>700.00</sydkyhlx><sydkyhe>2,700.00</sydkyhe><sydkye>160,000.00</sydkye></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKSS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00907Result);
			log.debug("MAP封装成BEAN："+app00907Result);
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00907Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00907Result.getMsg());//*************************************			
			
			if(!Constants.sucess.equals(app00907Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
//				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid() ,app00907Result);
				modelMap.put("recode", app00907Result.getRecode());
				modelMap.put("msg", app00907Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00907Result.getRecode()+"  ,  描述msg : "+app00907Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00101Result gjj00907Result = new AppApi00101Result();					
					try {
						log.debug("读取文件  ："+line);	
						String[] valus = line.split("\\@\\|\\$");
						gjj00907Result.setNewOperationDate(valus[0]);
						gjj00907Result.setCarryoverAmount(String.format("%,.2f",Double.valueOf(valus[1])));
						gjj00907Result.setAccountOrganization(String.format("%,.2f",Double.valueOf(valus[2])));
						gjj00907Result.setAccountOpenDate(String.format("%,.2f",Double.valueOf(valus[3])));
						gjj00907Result.setCompanyName(String.format("%,.2f",Double.valueOf(valus[4])));
						gjjlist.add(gjj00907Result);						
						if("2".equals(form.getDklx())){
							AppApi00101Result sd00907Result = new AppApi00101Result();
							sd00907Result.setNewOperationDate(valus[0]);
							sd00907Result.setCarryoverAmount(String.format("%,.2f",Double.valueOf(valus[5])));
							sd00907Result.setAccountOrganization(String.format("%,.2f",Double.valueOf(valus[6])));
							sd00907Result.setAccountOpenDate(String.format("%,.2f",Double.valueOf(valus[7])));
							sd00907Result.setCompanyName(String.format("%,.2f",Double.valueOf(valus[8])));
							sdlist.add(sd00907Result);
						}							
						
						
						
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "借款合同信息处理异常");
				return modelMap;
			}
		}
		if("2".equals(form.getDklx())){
			BigDecimal gjjll = new BigDecimal(app00907Result.getGjjdknll());
			BigDecimal gl = gjjll.multiply(new BigDecimal(100));
			app00907Result.setGjjdknll(String.format("%,.2f",gl)+"%");
			app00907Result.setGjjdkhkze("".equals(app00907Result.getGjjdkhkze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdkhkze())));			
			app00907Result.setGjjdklxze("".equals(app00907Result.getGjjdklxze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdklxze())));
			app00907Result.setGjjdkyhke("".equals(app00907Result.getGjjdkyhke())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdkyhke())));
			
			
			BigDecimal sdll = new BigDecimal(app00907Result.getSydknll());
			BigDecimal sl = sdll.multiply(new BigDecimal(100));
			app00907Result.setSydknll(String.format("%,.2f",sl)+"%");
			app00907Result.setSydkhkze("".equals(app00907Result.getSydkhkze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getSydkhkze())));
			app00907Result.setSydklxze("".equals(app00907Result.getSydklxze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getSydklxze())));
			app00907Result.setSydkyhke("".equals(app00907Result.getSydkyhke())?"":String.format("%,.2f",Double.valueOf(app00907Result.getSydkyhke())));
			
		}else{
			BigDecimal bd = new BigDecimal(app00907Result.getGjjdknll());
			BigDecimal xx = bd.multiply(new BigDecimal(100));
			app00907Result.setGjjdknll(String.format("%,.2f",xx)+"%");
			app00907Result.setGjjdkhkze("".equals(app00907Result.getGjjdkhkze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdkhkze())));			
			app00907Result.setGjjdklxze("".equals(app00907Result.getGjjdklxze())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdklxze())));
			app00907Result.setGjjdkyhke("".equals(app00907Result.getGjjdkyhke())?"":String.format("%,.2f",Double.valueOf(app00907Result.getGjjdkyhke())));			
		}
		
		
		
		if("2".equals(form.getDklx())){			
			if("02".equals(form.getHkfs())){
				appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultsdDebj", app00907Result);
			}else{
				appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultsd", app00907Result);
			}
			
		}else{
			if("02".equals(form.getHkfs())){
				appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultgjjDebj", app00907Result);
			}else{
				appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultgjj", app00907Result);
			}
			
		}
		 
		
		
		for(int i=0;i<gjjlist.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".detailgjj", (AppApi00101Result)gjjlist.get(i));	
			detailgjj.add(result1);
		}
		
		if("2".equals(form.getDklx())){
			for(int j=0;j<sdlist.size();j++){
				List<TitleInfoBean> result2 = new ArrayList<TitleInfoBean>();
				result2 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".detailsd", (AppApi00101Result)sdlist.get(j));	
				detailsd.add(result2);
			}
		}
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
//		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00907Result);
		
		
		
//		
//		
//		if("2".equals(form.getDklx())){
//			Double gjj1 = Double.valueOf(form.getDkje())*0.0375*Double.valueOf(form.getDknx());
//			Double sd1 = Double.valueOf(form.getSdje())*0.0565*Double.valueOf(form.getDknx());
//			app00907Result.setGjjdknll("3.75%");
//			app00907Result.setGjjdkhkze(String.format("%,.2f",Double.valueOf(form.getDkje())));
//			app00907Result.setGjjdklxze(String.format("%,.2f",gjj1));
//			app00907Result.setGjjdkyhke(String.format("%,.2f",(gjj1+Double.valueOf(form.getDkje()))/(Double.valueOf(form.getDknx())*12)));
//			
//			app00907Result.setSydknll("5.65%");
//			app00907Result.setSydkhkze(String.format("%,.2f",Double.valueOf(form.getSdje())));
//			app00907Result.setSydklxze(String.format("%,.2f",sd1));
//			app00907Result.setSydkyhke(String.format("%,.2f",(sd1+Double.valueOf(form.getSdje()))/(Double.valueOf(form.getDknx())*12)));
//			
//		}else{
//			Double gjj1 = Double.valueOf(form.getDkje())*0.0375*Double.valueOf(form.getDknx());
//			app00907Result.setGjjdknll("3.75%");
//			app00907Result.setGjjdkhkze(String.format("%,.2f",Double.valueOf(form.getDkje())));
//			app00907Result.setGjjdklxze(String.format("%,.2f",gjj1));
//			app00907Result.setGjjdkyhke(String.format("%,.2f",(gjj1+Double.valueOf(form.getDkje()))/(Double.valueOf(form.getDknx())*12)));			
//		}
//		if("2".equals(form.getDklx())){
//			appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultsd", app00907Result);
//		}else{
//			appapi00907Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".resultgjj", app00907Result);
//		}
//		
		
		
		
//		Double gjjze = Double.valueOf(form.getDkje());
//		Double gjjbj = Double.valueOf(form.getDkje())/(Double.valueOf(form.getDknx())*12);
//		Double gjjlx = (Double.valueOf(form.getDkje())*0.04)/(Double.valueOf(form.getDknx())*12);
//		Double sdze = 0.00;
//		Double sdbj = 0.00;
//		Double sdlx = 0.00;
//		if("2".equals(form.getDklx())){
//			sdze = Double.valueOf(form.getSdje());
//			sdbj = Double.valueOf(form.getSdje())/(Double.valueOf(form.getDknx())*12);
//			sdlx = (Double.valueOf(form.getSdje())*0.06)/(Double.valueOf(form.getDknx())*12);
//		}
//		
//		
//		int nx = Integer.valueOf(form.getDknx())*12;
//		for(int i=1;i<=nx;i++){
//			gjjze = gjjze-gjjbj-gjjlx;
//			AppApi00101Result gjj00907Result = new AppApi00101Result();	
//			gjj00907Result.setNewOperationDate(String.valueOf(i));
//			gjj00907Result.setCarryoverAmount(String.format("%,.2f",gjjbj));
//			gjj00907Result.setAccountOrganization(String.format("%,.2f",gjjlx));
//			gjj00907Result.setAccountOpenDate(String.format("%,.2f",gjjbj+gjjlx));
//			gjj00907Result.setCompanyName(String.format("%,.2f",gjjze));
//			gjjlist.add(gjj00907Result);
//			if("2".equals(form.getDklx())){
//				sdze = sdze-sdbj-sdlx;
//				AppApi00101Result sd00907Result = new AppApi00101Result();
//				sd00907Result.setNewOperationDate(String.valueOf(i));
//				sd00907Result.setCarryoverAmount(String.format("%,.2f",sdbj));
//				sd00907Result.setAccountOrganization(String.format("%,.2f",sdlx));
//				sd00907Result.setAccountOpenDate(String.format("%,.2f",sdbj+sdlx));
//				sd00907Result.setCompanyName(String.format("%,.2f",sdze));
//				sdlist.add(sd00907Result);
//			}
//		}
		
		
		
		
		
		
		
		
		
//		
////		try {
//			// 业务处理
//			double dLoanAmount = Double.valueOf(form.getDkje().replaceAll(",", "")); // 公积金贷款金额
//			double dLoanInterestRate = Double.parseDouble("0.0375") / 12; // 公积金贷款月利率
//			int iLoanDuration = Integer.parseInt(form.getDknx()) * 12; // 还款期数
//
//			double repaymentPrincipal = 0.0; // 应还本金
//			double repaymentInterest = 0.0; // 应还利息
//			double monthRepaymentAmount = 0.0; // 月还款额
//			double loanBalance = dLoanAmount; // 贷款余额
//			
//			
//			// 业务处理
//			double sddLoanAmount = Double.valueOf(form.getSdje().equals("")?"0.00":form.getSdje()); // 公积金贷款金额
//			double sddLoanInterestRate = Double.parseDouble("0.0565") / 12; // 公积金贷款月利率
//			int sdiLoanDuration = Integer.parseInt(form.getDknx()) * 12; // 还款期数
//
//			double sdrepaymentPrincipal = 0.0; // 应还本金
//			double sdrepaymentInterest = 0.0; // 应还利息
//			double sdmonthRepaymentAmount = 0.0; // 月还款额
//			double sdloanBalance = sddLoanAmount; // 贷款余额
//
//			/*
//			 * 等额本息
//			 */
//			if ("02".equals(form.getHkfs())) {
//				for (int i = 0; i < iLoanDuration; i++) {					
//					AppApi00101Result gjj00907Result = new AppApi00101Result();
//					/*
//					 * 末期
//					 */
//					if (i == iLoanDuration - 1) {
//						// TODO 等额本息末期还款计划
//						repaymentPrincipal = Double.valueOf(String.format("%.2f",loanBalance));
//						repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
//						loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
//						monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
//								+ repaymentInterest));
//						gjj00907Result.setNewOperationDate(String.valueOf(i+1));
//						gjj00907Result.setCarryoverAmount(String.format(
//								"%,.2f", Math.abs(repaymentPrincipal)));
//						gjj00907Result.setAccountOrganization(String
//								.format("%,.2f", repaymentInterest));
//						gjj00907Result.setAccountOpenDate(String.format(
//								"%,.2f", monthRepaymentAmount));
//						gjj00907Result.setCompanyName(String.format("%,.2f",loanBalance));						
//						gjjlist.add(gjj00907Result);
//						if("2".equals(form.getDklx())){
//							AppApi00101Result sd00907Result = new AppApi00101Result();
//							sdrepaymentPrincipal = Double.valueOf(String.format("%.2f",sdloanBalance));
//							sdrepaymentInterest = Double.valueOf(String.format("%.2f",sdloanBalance * sddLoanInterestRate));
//							sdloanBalance -= Double.valueOf(String.format("%.2f",sdrepaymentPrincipal));
//							sdmonthRepaymentAmount = Double.valueOf(String.format("%.2f",sdrepaymentPrincipal
//									+ sdrepaymentInterest));
//							
//							sd00907Result.setNewOperationDate(String.valueOf(i+1));
//							sd00907Result.setCarryoverAmount(String.format(
//									"%,.2f", Math.abs(sdrepaymentPrincipal)));
//							sd00907Result.setAccountOrganization(String
//									.format("%,.2f", sdrepaymentInterest));
//							sd00907Result.setAccountOpenDate(String.format(
//									"%,.2f", sdmonthRepaymentAmount));
//							sd00907Result.setCompanyName(String.format("%,.2f",sdloanBalance));						
//							sdlist.add(sd00907Result);
//							
//						}
//						break;
//					}
//
//					/*
//					 * 非末期
//					 */
//					monthRepaymentAmount = Double.valueOf(String.format("%.2f",(dLoanAmount * dLoanInterestRate * Math
//							.pow((1 + dLoanInterestRate), iLoanDuration))
//							/ (Math.pow((1 + dLoanInterestRate), iLoanDuration) - 1)));
//					repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
//					repaymentPrincipal = Double.valueOf(String.format("%.2f",monthRepaymentAmount
//							- repaymentInterest));
//					loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
//
//					gjj00907Result.setNewOperationDate(String.valueOf(i+1));
//					gjj00907Result.setCarryoverAmount(String.format(
//							"%,.2f", Math.abs(repaymentPrincipal)));
//					gjj00907Result.setAccountOrganization(String
//							.format("%,.2f", repaymentInterest));
//					gjj00907Result.setAccountOpenDate(String.format(
//							"%,.2f", monthRepaymentAmount));
//					gjj00907Result.setCompanyName(String.format("%,.2f",loanBalance));						
//					gjjlist.add(gjj00907Result);
//					
//					if("2".equals(form.getDklx())){
//						AppApi00101Result sd00907Result = new AppApi00101Result();
//						sdrepaymentPrincipal = Double.valueOf(String.format("%.2f",(sddLoanAmount * sddLoanInterestRate * Math
//								.pow((1 + sddLoanInterestRate), sdiLoanDuration))
//								/ (Math.pow((1 + sddLoanInterestRate), sdiLoanDuration) - 1)));
//						sdrepaymentInterest = Double.valueOf(String.format("%.2f",sdloanBalance * sddLoanInterestRate));
//						sdloanBalance -= Double.valueOf(String.format("%.2f",sdrepaymentPrincipal));
//						sdmonthRepaymentAmount = Double.valueOf(String.format("%.2f",sdrepaymentPrincipal
//								+ sdrepaymentInterest));
//						
//						sd00907Result.setNewOperationDate(String.valueOf(i));
//						sd00907Result.setCarryoverAmount(String.format(
//								"%,.2f", Math.abs(sdrepaymentPrincipal)));
//						sd00907Result.setAccountOrganization(String
//								.format("%,.2f", sdrepaymentInterest));
//						sd00907Result.setAccountOpenDate(String.format(
//								"%,.2f", sdmonthRepaymentAmount));
//						sd00907Result.setCompanyName(String.format("%,.2f",sdloanBalance));						
//						sdlist.add(sd00907Result);
//						
//					}
//					
//					
//					
//				}// end for
//			}// end if
//			/*
//			 * 等额本金
//			 */
//			else if ("01".equals(form.getHkfs())) {
//				for (int i = 0; i < iLoanDuration; i++) {
//					AppApi00101Result gjj00907Result = new AppApi00101Result();
//					/*
//					 * 末期
//					 */
//					if (i == iLoanDuration - 1) {
//						// TODO 等额本息末期还款计划
//						repaymentPrincipal = Double.valueOf(String.format("%.2f",loanBalance));
//						repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
//						loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
//						monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
//								+ repaymentInterest));
//
//						gjj00907Result.setNewOperationDate(String.valueOf(i));
//						gjj00907Result.setCarryoverAmount(String.format(
//								"%,.2f", Math.abs(repaymentPrincipal)));
//						gjj00907Result.setAccountOrganization(String
//								.format("%,.2f", repaymentInterest));
//						gjj00907Result.setAccountOpenDate(String.format(
//								"%,.2f", monthRepaymentAmount));
//						gjj00907Result.setCompanyName(String.format("%,.2f",loanBalance));						
//						gjjlist.add(gjj00907Result);
//						
//						
//						if("2".equals(form.getDklx())){
//							AppApi00101Result sd00907Result = new AppApi00101Result();
//							sdrepaymentPrincipal = Double.valueOf(String.format("%.2f",sdloanBalance));
//							sdrepaymentInterest = Double.valueOf(String.format("%.2f",sdloanBalance * sddLoanInterestRate));
//							sdloanBalance -= Double.valueOf(String.format("%.2f",sdrepaymentPrincipal));
//							sdmonthRepaymentAmount = Double.valueOf(String.format("%.2f",sdrepaymentPrincipal
//									+ sdrepaymentInterest));
//							
//							sd00907Result.setNewOperationDate(String.valueOf(i));
//							sd00907Result.setCarryoverAmount(String.format(
//									"%,.2f", Math.abs(sdrepaymentPrincipal)));
//							sd00907Result.setAccountOrganization(String
//									.format("%,.2f", sdrepaymentInterest));
//							sd00907Result.setAccountOpenDate(String.format(
//									"%,.2f", sdmonthRepaymentAmount));
//							sd00907Result.setCompanyName(String.format("%,.2f",sdloanBalance));						
//							sdlist.add(sd00907Result);
//							
//						}
//						
//						
//						
//						
//						break;
//					}
//
//					/*
//					 * 非末期
//					 */
//					repaymentPrincipal = Double.valueOf(String.format("%.2f",dLoanAmount / iLoanDuration));
//					repaymentInterest = Double.valueOf(String.format("%.2f",loanBalance * dLoanInterestRate));
//					monthRepaymentAmount = Double.valueOf(String.format("%.2f",repaymentPrincipal
//							+ repaymentInterest));
//					loanBalance -= Double.valueOf(String.format("%.2f",repaymentPrincipal));
//					
//					gjj00907Result.setNewOperationDate(String.valueOf(i));
//					gjj00907Result.setCarryoverAmount(String.format(
//							"%,.2f", Math.abs(repaymentPrincipal)));
//					gjj00907Result.setAccountOrganization(String
//							.format("%,.2f", repaymentInterest));
//					gjj00907Result.setAccountOpenDate(String.format(
//							"%,.2f", monthRepaymentAmount));
//					gjj00907Result.setCompanyName(String.format("%,.2f",loanBalance));						
//					gjjlist.add(gjj00907Result);
//					
//					if("2".equals(form.getDklx())){
//						AppApi00101Result sd00907Result = new AppApi00101Result();
//						sdrepaymentPrincipal = Double.valueOf(String.format("%.2f",(sddLoanAmount * sddLoanInterestRate * Math
//								.pow((1 + sddLoanInterestRate), sdiLoanDuration))
//								/ (Math.pow((1 + sddLoanInterestRate), sdiLoanDuration) - 1)));
//						sdrepaymentInterest = Double.valueOf(String.format("%.2f",sdloanBalance * sddLoanInterestRate));
//						sdloanBalance -= Double.valueOf(String.format("%.2f",sdrepaymentPrincipal));
//						sdmonthRepaymentAmount = Double.valueOf(String.format("%.2f",sdrepaymentPrincipal
//								+ sdrepaymentInterest));
//						
//						sd00907Result.setNewOperationDate(String.valueOf(i));
//						sd00907Result.setCarryoverAmount(String.format(
//								"%,.2f", Math.abs(sdrepaymentPrincipal)));
//						sd00907Result.setAccountOrganization(String
//								.format("%,.2f", sdrepaymentInterest));
//						sd00907Result.setAccountOpenDate(String.format(
//								"%,.2f", sdmonthRepaymentAmount));
//						sd00907Result.setCompanyName(String.format("%,.2f",sdloanBalance));						
//						sdlist.add(sd00907Result);
//						
//					}
//					
//					
//				}// end for
//			} else {
//				//throw new TransRuntimeErrorException(WEB_ALERT.SYS.getValue(),"还款计划计算异常。");
//			}
////		} catch (TransRuntimeErrorException e) {
////			// 系统异常
////			throw e;
////		}
//		
//		
		
		
		
		
		
		
		
		
		
		
		
		
//		for(int i=0;i<gjjlist.size();i++){
//			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
//			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".detailgjj", (AppApi00101Result)gjjlist.get(i));	
//			detailgjj.add(result1);
//		}
//		
//		if("2".equals(form.getDklx())){
//			for(int j=0;j<sdlist.size();j++){
//				List<TitleInfoBean> result2 = new ArrayList<TitleInfoBean>();
//				result2 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00907"+form.getCenterId()+".detailsd", (AppApi00101Result)sdlist.get(j));	
//				detailsd.add(result2);
//			}
//		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00907Result);
		modelMap.put("detailgjj", detailgjj);
		modelMap.put("detailsd", detailsd);		
		return modelMap;
	}
	
	public static void main(String[] args){		
		BigDecimal bd = new BigDecimal("0.05575");
		BigDecimal xx = bd.multiply(new BigDecimal(100));
		System.out.println(String.format("%,.2f",xx)+"%");
	}
}
