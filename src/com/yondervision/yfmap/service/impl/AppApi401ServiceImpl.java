package com.yondervision.yfmap.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.YfThread;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.controller.AppApi302Contorller;
import com.yondervision.yfmap.dto.CMi401;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.service.AppApi401Service;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

	
public class AppApi401ServiceImpl{
	Logger log = Logger.getLogger("YFMAP");	
	/**
	 * 定制单笔
	 */
	public HashMap acction(CMi401 form, ModelMap modelMap, String centerid, String sendseqno)
			throws Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("AppApi401ServiceImpl:开始****处理模板信息单笔推送逻辑");
		String remsg = "";
		
		System.out.println("开始****处理定制信息单笔推送逻辑");
		HashMap map = BeanUtil.transBean2Map(form);
		//必输项发送方流水，Sendseqno，Centerid，Theme，Timing，Dsdate，Msgsource，ChannelTemplate
		
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi30292.json";
		System.out.println("YBMAPZH url ："+url);
		String rm = post.post(url, map);
		System.out.println("处理模板信息单笔推送返回信息："+rm);
		Gson gson = new Gson();
		Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
		HashMap resultMap = new HashMap();
		resultMap.put("recode", ybmapmsg.get("recode"));
		resultMap.put("msg", ybmapmsg.get("msg"));
		resultMap.put("miSeqno", ybmapmsg.get("miSeqno"));
		

		log.debug(resultMap);	
		log.debug("结束****处理模板信息单笔推送逻辑");
		return resultMap;
	}
	/**
	 * 定制批量
	 */
	public void acction1(CMi401 form, ModelMap modelMap, String centerid, String sendseqno)
			throws Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
//		log.debug("AppApi401ServiceImpl:开始****处理模板信息批量推送逻辑");
//		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004Send"+centerid).trim();
//		
//		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi3004MsgType"+centerid).trim();
//		HashMap resultMap = null;
//			
//		log.debug("MAP封装成BEAN："+form);
//		String centerName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPServerSendMessageCenterName").trim();
//		form.setCenterName(centerName);
//		String param = JsonUtil.getGson().toJson(form);
//		log.debug("AppApi401ServiceImpl:acction封装移动平台JSON数据："+param);
//
//		SimpleHttpMessageUtil post = new SimpleHttpMessageUtil();
//		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YFMAPURL").trim()+"yfmapapi3002.json";
//		log.debug("AppApi401ServiceImol:YFMAP url ："+url);
//		System.out.println("AppApi401ServiceImpl:url ："+url);
//		resultMap.put("centerId", centerid);
//		
//		String rm = post.sendPost(url, resultMap, null);
//		
//		System.out.println("AppApi401ServiceImpl:返回信息l ："+rm);
//		Gson gson = new Gson();
//		Map map = gson.fromJson(rm,new TypeToken<Map<String, Object>>() {}.getType());
//		
//		resultMap.put("recode", "000000");
//		resultMap.put("msg", "成功");
//		
//		resultMap.put("centerid", form.getCenterid());
//		resultMap.put("certinum", form.getCertinum());
//		resultMap.put("title", form.getTitle());
//		resultMap.put("theme", form.getTheme());
//		resultMap.put("tsmsg", form.getTsmsg());
//		resultMap.put("sendseqno", form.getSendseqno());
//		resultMap.put("timer", form.getTiming());
//		
//		
//		String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
//		System.out.println("定制批量文件："+filename);
//		
//		if(filename != null){
//			File file = new File(filename);
//			//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
//			FileInputStream ffis = new FileInputStream(file);
//			InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
//			BufferedReader breader = new BufferedReader(isr);
//			String line = breader.readLine();
//			
//			while (line != null) {// 判断读取到的字符串是否不为空				
//				try {						
//					log.debug("读取文件  ："+line);
//					ThemeTran(form);
//					String[] trs = line.split(";");
//					VarTran(form, trs);					
//					
//					list.add(form);
//					line = breader.readLine();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			Gson gsonbacth = new Gson();
//			String msg = gsonbacth.toJson(form);
//			int seqno = 0;
//			   
//			YfThread ybmapThread = new YfThread(); 
//			ybmapThread.setFilename(filename);
//			ybmapThread.setMsg(msg);
//			ybmapThread.setSeqno(seqno);
//			Thread thread = new Thread(ybmapThread); 
//			thread.start();
//			breader.close();
//			isr.close();
//			ffis.close();
//		}else{
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "批量明细信息处理异常");
//			return "";
//		}			
//		modelMap.clear();
//		modelMap.put("recode", "000000");
//		modelMap.put("msg", "成功");
//		log.debug("AppApi401ServiceImpl:结束****处理模板信息批量推送逻辑");
//		return "";
				
	}

	
	
	/**
	 * 模板单笔
	 */
	public HashMap acction2(CMi401 form, ModelMap modelMap, String centerid, String sendseqno)
			throws Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		System.out.println("-----------单笔模板消息YFMAP推送开始------------");
		
		ThemeTran(form);		
		System.out.println("-----------单笔模板消息YFMAP推送开始 form.getTsmsg()------------"+ form.getTsmsg());
		String[] trs = form.getTsmsg().split(";");		
		form = VarTran(form, trs);							
		System.out.println("AppApi401ServiceImpl:开始****处理模板信息单笔推送逻辑");
		HashMap map = BeanUtil.transBean2Map(form);
		//必输项发送方流水，Sendseqno，Centerid，Theme，Timing，Dsdate，Msgsource，ChannelTemplate
		
		YbmapMessageUtil post = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi30291.json";
		System.out.println("YBMAP url ："+url);
		String rm = post.post(url, map);
		System.out.println("AppApi401ServiceImpl:开始****处理模板信息单笔推送返回信息："+rm);
		Gson gson = new Gson();
		Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType());  
		HashMap resultMap = new HashMap();
		resultMap.put("recode", ybmapmsg.get("recode"));
		resultMap.put("msg", ybmapmsg.get("msg"));
		resultMap.put("miSeqno", ybmapmsg.get("miSeqno"));
		

		log.debug(resultMap);	
		log.debug("AppApi401ServiceImpl:结束****处理模板信息单笔推送逻辑");
		return resultMap;
	}
	
	/**
	 * 模板批量
	 */
	public String acction3(String centerid, String filename, String sendseqno)
			throws Exception {
		CMi401 form = new CMi401();
		form.setCenterid(centerid);
		form.setFilename(filename);
		form.setSendseqno(sendseqno);
		form.setMsgsource("30");
		
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("--------------YFMAP应用开始处理模板信息批量推送逻辑--------------");
			
		ResourceBundle ReadProperties = ResourceBundle.getBundle("ftp");
		if(filename != null){
			FtpUtil f=new FtpUtil("bsp");
		    f.getFileByServer(filename);
		    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
		    FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
		    int sum = 0;
			String line = breader.readLine();
			System.out.println("--------------YFMAP应用开始处理模板信息批量推送逻辑，读取文件第一行信息内容--------------");
			System.out.println(line);
			line = breader.readLine();
			while (line != null) {// 判断读取到的字符串是否不为空				
				try {						
					log.debug("YFMAP应用开始处理模板信息批量推送逻辑，读取文件  ：【"+line+"】");
					
					line = line + "\t1";
					String[] params = line.split("\t");
//					SMSSOURCE\tPHONENUMBER\tELEMENTS\tSENDTYPE\tSENDTIME\tISPFLAG\n
//					SMSSOURCE：模板代码
//					PHONENUMBER：手机号码
//					ELEMENTS：发送要素
//					SENDTYPE：发送类型0-实时发送 1-定时发送
//					SENDTIME：发送时间，日期格式2016-10-01 08:10:00，若为实时发送时间为空
					form.setTheme(params[0]);
					System.out.println("文件第1列要素："+params[0]);
					form.setTel(params[1]);	
					System.out.println("文件第2列要素："+params[1]);
					form.setTsmsg(params[2]);
					System.out.println("文件第3列要素："+params[2]);
					form.setTiming(params[3]);
					System.out.println("文件第4列要素："+params[3]);
					form.setDsdate(params[4]);
					System.out.println("文件第5列要素："+params[4]);
					ThemeTran(form);
					String[] trs = params[2].split(";");
					StringBuffer trsTel = new StringBuffer();
					for(int i=0;i<trs.length;i++){
						if(i==2){
							trsTel.append(form.getTel()+";"+trs[i]+";");
						}else{
							trsTel.append(trs[i]+";");
						}
					}
					System.out.println("重组要素信息："+trsTel.toString());
					String[] trss = trsTel.toString().split(";");
					
					form = VarTran(form, trss);	
					sum++;
					HashMap map = BeanUtil.transBean2Map(form);
					YbmapMessageUtil post = new YbmapMessageUtil();
					String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"appapi30291.json";
					System.out.println("YBMAP url ："+url);
					String rep = post.post(url, map);
					System.out.println("------【推量推送模板消息返回信息】------【"+sum+"】---- 消息内容 ："+rep);
					
					line = breader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			breader.close();
			isr.close();
			ffis.close();
		}else{
			System.out.println("***********************批量模板消息推送文件名不存在！！！");
			return "";
		}			
		log.debug("AppApi401ServiceImpl:结束****处理模板信息批量推送逻辑");
		return "";				
	}

//	 public class YfmapThread implements Runnable 
//	 { 
//		   private String filename; 
//		   private String msg;
//		   private int seqno;
//		   
//		   public String getFilename() {
//			   return filename;
//		   }
//		   public void setFilename(String filename) {
//			   this.filename = filename;
//		   }
//		   public int getSeqno() {
//			   return seqno;
//		   }
//		   public void setSeqno(int seqno){
//			   this.seqno = seqno;			
//		   }		
//		   public String getMsg() {
//			   return msg;
//		   }
//		   public void setMsg(String msg) {
//			   this.msg = msg;
//		   }
//		
//		   public void run() 
//		   { 
//			   System.out.println(filename);
//			   int sum = 0;
//			   for(int i=0;i<90000;i++){
//					   sum++;				   
//			      }
//			   System.out.println("线程执行完毕："+sum+"    msg:"+filename);
//			   Gson gson=new Gson();
// 		       CMi401 api3002 = gson.fromJson(msg, new TypeToken<CMi401>(){}.getType());   		
// 		       try {			   
// 			       appApi401Service.acction3(api3002, null, msg, msg);
// 		       } catch (Exception e) {
// 			       // TODO Auto-generated catch block
// 			       e.printStackTrace();
// 		    }			   
//		} 
//	 }

	 	 
	public CMi401 ThemeTran(CMi401 form)
	{
		if("00087100".equals(form.getCenterid())){
			return ThemeTran00087100(form);
		}else if("00073300".equals(form.getCenterid())){
			return ThemeTran00073300(form);
		}else if("00057400".equals(form.getCenterid())){
			return ThemeTran00057400(form);
		}
		return form;
	}
	
	public CMi401  VarTran(CMi401 form, String[] trs)
	{	
		if("00087100".equals(form.getCenterid())){
			return VarTran00087100(form ,trs);
		}else if("00073300".equals(form.getCenterid())){
			return VarTran00073300(form ,trs);
		}else if("00057400".equals(form.getCenterid())){
			return VarTran00057400(form ,trs);
		}
		return form;
	}
	
	
	public CMi401 ThemeTran00057400(CMi401 form){
		if(form.getTheme().trim().equals("18")){
			form.setTheme("18");           //短信验证码			
		}
		return form;
	}
	
	public CMi401 ThemeTran00087100(CMi401 form){
		if (form.getTheme().trim().equals("KMDX01")){
			form.setTheme("1");           //短信签约通知
		}
        if (form.getTheme().trim().equals("KMDX02")){
        	form.setTheme("2");           //提取
		}
        
        if (form.getTheme().trim().equals("KMDX04")){
        	form.setTheme("4");           //汇补缴			
		}
        if (form.getTheme().trim().equals("KMDX05")){
        	form.setTheme("5");           //贷款审批			
		}
        if (form.getTheme().trim().equals("KMDX06")){
        	form.setTheme("6");           //公积金委托扣划还贷通知			
		}
        if (form.getTheme().trim().equals("KMDX07")){
        	form.setTheme("7");          //公积金贷款还款提醒			
		}
        if (form.getTheme().trim().equals("KMDX08")){
        	form.setTheme("8");           //公积金贷款催收			
		}
        if (form.getTheme().trim().equals("KMDX09")){
        	form.setTheme("10");           //银行代扣-成功，YBMAPZH码表10为失败
		}       
        if (form.getTheme().trim().equals("KMDX10")){
        	form.setTheme("9");          //银行代扣-失败,YBMAPZH码表9为失败
		}
        if (form.getTheme().trim().equals("KMDX11")){
        	form.setTheme("11");            //公积金代扣-成功
		}        
        if (form.getTheme().trim().equals("KMDX12")){
        	form.setTheme("12");           //公积金代扣-失败			
		}
        if (form.getTheme().trim().equals("KMDX13")){
        	form.setTheme("13");           //柜台还款(网厅)			
		}
        if (form.getTheme().trim().equals("KMDX14")){
        	form.setTheme("14");          //贷款结清			
		}
        if (form.getTheme().trim().equals("KMDX15")){
        	form.setTheme("15");           //利率调整			
		}
        if (form.getTheme().trim().equals("KMDX16")){
        	form.setTheme("16");            //贷款放款			
		}       
		if(form.getTheme().trim().equals("KMDX17")){
			form.setTheme("17");           //归集托收			
		}
		if(form.getTheme().trim().equals("KMDX18")){
			form.setTheme("18");           //短信验证码			
		}
//		if(form.getTheme().trim().equals("KMDX19")){
//			form.setTheme("19");           //服务评价			
//		}
		if(form.getTheme().trim().equals("KMDX20")){
			form.setTheme("33");           //短信验证码			
		}
		return form;
	}
	
	public CMi401 ThemeTran00073300(CMi401 form){
		if ("DW0001".equals(form.getTheme().trim())){
			form.setTheme("D73310");           //单位开户
		}
		if ("DW0002".equals(form.getTheme().trim())){
			form.setTheme("D73311");           //个人基数调整
		}
		if ("DW0003".equals(form.getTheme().trim())){
			form.setTheme("D73312");           //单位比例变更
		}
		if ("DW0004".equals(form.getTheme().trim())){
			form.setTheme("D73313");           //缴存入账
		}
		if ("DW0005".equals(form.getTheme().trim())){
			form.setTheme("D73314");           //职工余额退挂账
		}
		if ("DW0006".equals(form.getTheme().trim())){
			form.setTheme("D73315");           //个人调账
		}
		if ("DW0007".equals(form.getTheme().trim())){
			form.setTheme("D73316");           //托收文件入账
		}
		if ("DW0008".equals(form.getTheme().trim())){
			form.setTheme("D73317");           //外市转入
		}
		if ("GR0001".equals(form.getTheme().trim())){
			form.setTheme("D73318");           //自由职业者开户
		}
		if ("DW0009".equals(form.getTheme().trim())){
			form.setTheme("D73319");           //单位挂账退缴
		}
		if ("DW0010".equals(form.getTheme().trim())){
			form.setTheme("D73320");           //结息
		}
		if ("DW0011".equals(form.getTheme().trim())){
			form.setTheme("D73321");           //单位催缴短信
		}
		if ("GR0002".equals(form.getTheme().trim())){
			form.setTheme("2");           //提取
		}
		if ("GR0003".equals(form.getTheme().trim())){
			form.setTheme("7");           //还款提醒
		}
		if ("GR0004".equals(form.getTheme().trim())){
			form.setTheme("10");           //贷款正常扣款
		}
		if ("GR0005".equals(form.getTheme().trim())){
			form.setTheme("8");           //贷款逾期提醒
		}
		if ("GR0006".equals(form.getTheme().trim())){
			form.setTheme("20");           //逾期划扣公积金
		}
		if ("GR0007".equals(form.getTheme().trim())){
			form.setTheme("6");           //按月对冲还贷不足提醒
		}
		if ("GR0008".equals(form.getTheme().trim())){
			form.setTheme("21");           //贴息划账提醒
		}
		if ("GR0009".equals(form.getTheme().trim())){
			form.setTheme("22");           //贷款受理
		}
		if ("GR0010".equals(form.getTheme().trim())){
			form.setTheme("5");           //贷款审批
		}
		if ("GR0011".equals(form.getTheme().trim())){
			form.setTheme("16");           //贷款发放
		}
		if ("GR0012".equals(form.getTheme().trim())){
			form.setTheme("14");           //贷款到期结清
		}
		if ("GR0013".equals(form.getTheme().trim())){
			form.setTheme("18");           //验证码
		}
			
		return form;
	}
	
	public CMi401  VarTran00087100(CMi401 form, String[] trs)
	{
		String tsmsg = "";
		//短信签约通知
		if (form.getTheme().trim().equals("1")){			
			form.setApiData1(trs[0]);//个人公积金账户后4位accnum
			form.setApiData2(trs[1]);//业务日期date
			form.setApiData3(trs[2]);//手机号	phone
			form.setApiData4(trs[3]);//姓名name
										
		}
		//提取
        if (form.getTheme().trim().equals("2")){
        	
        	form.setApiData1(trs[0]);//个人公积金账户后4位accnum
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//业务日期date
			form.setApiData5(trs[4]);//提取原因drawreason
			form.setApiData6(trs[5]);//发生额amount
			form.setApiData7(trs[6]);//当前余额balance

		}
      
      //汇补缴
        if (form.getTheme().trim().equals("4")){
        	form.setApiData1(trs[0]);//个人公积金账户后4位accnum
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//业务日期date
			form.setApiData5(trs[4]);//业务类型busitype
			form.setApiData6(trs[5]);//发生额amount
			form.setApiData7(trs[6]);//当前余额balance
		}
      //贷款审批
        if (form.getTheme().trim().equals("5")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//借款额度money
			form.setApiData5(trs[4]);//借款期限ynum
					
		}
      //公积金委托扣划还贷通知
        if (form.getTheme().trim().equals("6")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//公积金账号accnum
			form.setApiData5(trs[4]);//公积金余额balance
			form.setApiData6(trs[5]);//业务日期date			
		}
      //公积金贷款还款提醒
        if (form.getTheme().trim().equals("7")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//还款日day
			form.setApiData5(trs[4]);//月还款额money		
		}
      //公积金贷款催收
        if (form.getTheme().trim().equals("8")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//逾期期数daynum
			form.setApiData5(trs[4]);//逾期金额money	
		}
      //银行代扣-成功
        if (form.getTheme().trim().equals("10")){//YBMAPZH主编码
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//还款日期date
			form.setApiData5(trs[4]);//还款类型busitype
			form.setApiData6(trs[5]);//实还金额money
			form.setApiData7(trs[6]);//贷款本金余额balance			
		}  
      //银行代扣-失败
        if (form.getTheme().trim().equals("9")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//还款日期date
			form.setApiData5(trs[4]);//还款类型busitype
			form.setApiData6(trs[5]);//应还金额planmoney
			form.setApiData7(trs[6]);//实还金额money
			form.setApiData8(trs[7]);//欠还金额retmoney
			form.setApiData9(trs[8]);//贷款本金余额balance
		}
      //公积金代扣-成功
        if (form.getTheme().trim().equals("11")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号码phone
			form.setApiData4(trs[3]);//还款日期date
			form.setApiData5(trs[4]);//还款类型busitype
			form.setApiData6(trs[5]);//实还金额money
			form.setApiData7(trs[6]);//贷款本金余额balance		
		}      
        //公积金代扣-失败
        if (form.getTheme().trim().equals("12")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//还款日期date
			form.setApiData5(trs[4]);//还款类型busitype
			form.setApiData6(trs[5]);//应还金额planmoney
			form.setApiData7(trs[6]);//实还金额money
			form.setApiData8(trs[7]);//欠还金额retmoney
			form.setApiData9(trs[8]);//贷款本金余额balance
		}
      //柜台还款(网厅)
        if (form.getTheme().trim().equals("13")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//还款日期date
			form.setApiData5(trs[4]);//还款类型busitype
			form.setApiData6(trs[5]);//还款金额money
			form.setApiData7(trs[6]);//贷款本金金额balance
		}
      //贷款结清
        if (form.getTheme().trim().equals("14")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//结清日期date
			form.setApiData5(trs[4]);//贷款经办银行bankname
		}
        //利率调整
        if (form.getTheme().trim().equals("15")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//调整日期date
			form.setApiData5(trs[4]);//年利率rate
			form.setApiData6(trs[5]);//本期还款额money	
		}
      //贷款放款
        if (form.getTheme().trim().equals("16")){
        	form.setApiData1(trs[0]);//借款合同号后4位contractno
			form.setApiData2(trs[1]);//姓名name
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//放款日期date
			form.setApiData5(trs[4]);//放款金额amount
			form.setApiData6(trs[5]);//贷款年限loanterm
			form.setApiData7(trs[6]);//贷款日day
		}  
        //归集托收
		if(form.getTheme().trim().equals("17")){
			form.setApiData1(trs[0]);//单位账号后4位unitaccnum			
			form.setApiData2(trs[1]);//托收日期date
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//托收状态status
			form.setApiData5(trs[4]);//托收金额amount
			form.setApiData6(trs[5]);//托收月份lastpaydate	
		}
		//短信验证码
		if(form.getTheme().trim().equals("18")){
			form.setApiData1(trs[0]);//业务名称ywmc
			form.setApiData2(trs[1]);//验证码code
			form.setApiData3(trs[2]);//手机号	phone				
		}
		//服务评价
//		if(form.getTheme().trim().equals("19")){
//			form.setApiData1(trs[0]);//业务日期date
//			form.setApiData2(trs[1]);//业务网点orgname
//			form.setApiData3(trs[2]);//业务名称ywmc
//			form.setApiData4(trs[3]);//姓名
//			form.setApiData5(trs[4]);//手机号	phone					
//		}		
		if(form.getTheme().trim().equals("33")){
			//借款合同号后4位；姓名；手机号码；期数；还款日期；还款类型；应还金额；实还金额；公积金扣款；银行扣款；欠还金额；贷款本金余额；
			form.setApiData1(trs[0]);//借款合同号后4位
			form.setApiData2(trs[1]);//姓名
			form.setApiData3(trs[2]);//手机号	phone	
			form.setApiData4(trs[3]);//期数	
			form.setApiData5(trs[4]);//还款日期	
			form.setApiData6(trs[5]);//还款类型	
			form.setApiData7(trs[6]);//应还金额	
			form.setApiData8(trs[7]);//实还金额	
			form.setApiData9(trs[8]);//公积金扣款	
			
			form.setApiData10(trs[9]);//银行扣款
			form.setApiData11(trs[10]);//欠还金额
			form.setApiData12(trs[11]);//贷款本金余额
		}
		return form;
	}
	
	
	public CMi401  VarTran00073300(CMi401 form, String[] trs){
//      //自由职业者开户
//        if (form.getTheme().trim().equals("31")){
//        	form.setApiData1(trs[0]);//日期date
//			form.setApiData2(trs[1]);//个人账号grzh
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//月份month
//			form.setApiData5(trs[4]);//托收日tsrq
//			form.setApiData6(trs[5]);//账号yhkh
//
//		}
      //提取
        if (form.getTheme().trim().equals("2")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//个人公积金账号grzh
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//业务类型ywlx
			form.setApiData5(trs[4]);//金额money
			form.setApiData6(trs[5]);//余额balance
			form.setApiData7(trs[6]);//日期date
			
		}
      //还款提醒
        if (form.getTheme().trim().equals("7")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//日期date
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//金额money
			form.setApiData5(trs[4]);//合同号htbh

		}
      //贷款正常扣款
        if (form.getTheme().trim().equals("10")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//日期date
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//应还金额yhje
			form.setApiData5(trs[4]);//实还金额shje
			form.setApiData6(trs[5]);//合同号htbh
			form.setApiData7(trs[6]);//剩余本金sybj
			form.setApiData8(trs[7]);//还款类型hklx

		}
      //贷款逾期提醒
        if (form.getTheme().trim().equals("8")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//月数month
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//日期date
			form.setApiData5(trs[4]);//金额money
			form.setApiData6(trs[5]);//合同号htbh

		}
      //逾期划扣公积金
        if (form.getTheme().trim().equals("20")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//逾期金额yqje
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//日期date
			form.setApiData5(trs[4]);//扣款金额kkje

		}
      //按月对冲还贷不足提醒
        if (form.getTheme().trim().equals("6")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//日期date
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//欠还金额qhje
			form.setApiData5(trs[4]);//账号yhkh
			form.setApiData6(trs[5]);//合同号htbh
			form.setApiData7(trs[6]);//实扣公积金金额skje
			form.setApiData8(trs[7]);//应还金额yhje
			form.setApiData9(trs[8]);//本金余额bjye

		}
      //贷款受理
        if (form.getTheme().trim().equals("22")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//金额money
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//年限year
			form.setApiData5(trs[4]);//业务类型ywlx

		}
      //贷款审批
        if (form.getTheme().trim().equals("5")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//金额money
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//年限year
			form.setApiData5(trs[4]);//业务类型ywlx

		}
      //贷款发放
        if (form.getTheme().trim().equals("16")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//金额money
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//年限year
			form.setApiData5(trs[4]);//发放日期ffrq
			form.setApiData6(trs[5]);//月还款额yhke
			form.setApiData7(trs[6]);//还款日hkrq
			form.setApiData8(trs[7]);//管理部glbm
			form.setApiData9(trs[8]);//业务类型ywlx

		}
      //贷款到期结清
        if (form.getTheme().trim().equals("14")){
        	form.setApiData1(trs[0]);//姓名name
			form.setApiData2(trs[1]);//日期date
			form.setApiData3(trs[2]);//手机号phone
			form.setApiData4(trs[3]);//合同号htbh
			form.setApiData5(trs[4]);//银行bank
			form.setApiData6(trs[5]);//管理部glbm

		}
      //验证码
        if (form.getTheme().trim().equals("18")){
        	form.setApiData1(trs[0]);//业务描述ywms
			form.setApiData2(trs[1]);//验证码checkcode
			form.setApiData3(trs[2]);//手机号phone

		}
//		//单位开户
//      if (form.getTheme().trim().equals("23")){
//      	form.setApiData1(trs[0]);//开户日期khrq
//			form.setApiData2(trs[1]);//单位账号dwzh
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//月份month
//
//		}
//    //个人基数调整
//      if (form.getTheme().trim().equals("24")){
//      	form.setApiData1(trs[0]);//单位账号dwzh
//			form.setApiData2(trs[1]);//日期date
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//业务类型ywlx
//			form.setApiData5(trs[4]);//启用月份month
//			form.setApiData6(trs[5]);//人数peoplenum
//			form.setApiData7(trs[6]);//金额money
//
//		}
//    //单位比例变更
//      if (form.getTheme().trim().equals("25")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//业务类型
//			form.setApiData5(trs[4]);//启用月份
//			form.setApiData6(trs[5]);//人数
//			form.setApiData7(trs[6]);//金额
//
//		}
//    //缴存入账
//      if (form.getTheme().trim().equals("26")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//缴存月份
//			form.setApiData5(trs[4]);//人数
//			form.setApiData6(trs[5]);//金额
//
//		}
//    //职工余额退挂账
//      if (form.getTheme().trim().equals("27")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//业务类型
//			form.setApiData5(trs[4]);//缴存月份
//			form.setApiData6(trs[5]);//人数
//			form.setApiData7(trs[6]);//金额
//
//		}
//    //个人调账
//      if (form.getTheme().trim().equals("28")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//调出个人账号
//			form.setApiData5(trs[4]);//调入账号
//			form.setApiData6(trs[5]);//时间段
//			form.setApiData7(trs[6]);//金额
//
//		}
//    //托收文件入账
//      if (form.getTheme().trim().equals("29")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//缴存月份
//			form.setApiData5(trs[4]);//人数
//			form.setApiData6(trs[5]);//金额
//
//		}
//    //外市转入
//      if (form.getTheme().trim().equals("30")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//人数
//			form.setApiData5(trs[4]);//金额
//
//		}     
//    //单位挂账退缴
//      if (form.getTheme().trim().equals("32")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//金额
//			form.setApiData5(trs[4]);//账号
//
//		}
//    //结息
//      if (form.getTheme().trim().equals("33")){
//      	form.setApiData1(trs[0]);//单位账号
//			form.setApiData2(trs[1]);//日期
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//人数
//			form.setApiData5(trs[4]);//年度
//			form.setApiData6(trs[5]);//金额
//
//		}
//    //单位催缴短信
//      if (form.getTheme().trim().equals("34")){
//      	form.setApiData1(trs[0]);//日期
//			form.setApiData2(trs[1]);//单位账号
//			form.setApiData3(trs[2]);//手机号phone
//			form.setApiData4(trs[3]);//时间段
//
//		}
		return form;
	}
	
	
	public CMi401  VarTran00057400(CMi401 form, String[] trs)
	{
		//短信验证码
		if(form.getTheme().trim().equals("18")){
			form.setApiData1(trs[0]);//业务名称ywmc
			form.setApiData2(trs[1]);//验证码  Vcode
			form.setApiData3(trs[2]);//手机号	tel			
			form.setApiData4(trs[4]);//机构号	JGH			
			System.out.println("-----------业务名称ywmc"+form.getApiData1());
			System.out.println("-----------验证码Vcode"+form.getApiData2());
			System.out.println("-----------手机号	tel"+form.getApiData3());	
			System.out.println("-----------机构号	JGH"+form.getApiData4());	
		}
		return form;
	}
	
}


