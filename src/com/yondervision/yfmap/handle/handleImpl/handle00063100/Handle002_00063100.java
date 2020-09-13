package com.yondervision.yfmap.handle.handleImpl.handle00063100;

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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00201Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi002Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00063100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		AppApi00201Form form = (AppApi00201Form)form1;
		List<WeiHaiAppApi002Result> list = new ArrayList<WeiHaiAppApi002Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api002"+form.getStartdate().substring(0, 4)+"SF"+form.getBodyCardNumber()+"GJJ"+form.getSurplusAccount()+".txt";
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		
		/**************************** 威海 201405***********************************************************/
		WeiHaiAppApi00201Result app002Result = new WeiHaiAppApi00201Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			form.setYear(form.getStartdate().substring(0, 4));
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YEMXCX.txt", map, req);
			log.debug("发往中心报文："+xml);			
			
			File isFile = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
			if(isFile.exists()){				
				FileInputStream ffis = new FileInputStream(isFile);
				InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
				if(line != null){
					sum=1;
				}
				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空					
					WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();					
					try {
						String[] param = line.split("~");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);
						if(form.getEnddate().equals("")||form.getStartdate().equals("")){
							if(pnum<Integer.valueOf(form.getPagerows())){
								if(sum>num){
//									aa00201Result.setPrebalance(param[0]);
//									aa00201Result.setPaybegindate(param[1]);
//									aa00201Result.setTransdate(param[2]);
//									aa00201Result.setSummary(param[3]);	
//									aa00201Result.setBusiamt1(param[4]);
//									aa00201Result.setBusiamt2(param[5]);
//									aa00201Result.setBalance(param[6]);
//									aa00201Result.setBusiamt3(param[7]);
//									aa00201Result.setBusiamt4(param[8]);									
//									aa00201Result.setBusiamttotal1(param[9]);
//									aa00201Result.setBusiamttotal2(param[10]);
									if(param[0].startsWith("1899")){
										aa00201Result.setPaybegindate("----");
									}else{
										aa00201Result.setPaybegindate(param[0].replaceAll("-", "").substring(0, 6));
									}
									
									aa00201Result.setTransdate(param[1]);
									
									String mary = PropertiesReader.getProperty("yingshe.properties", "Summary"+param[2]).trim();
									if(mary!=null||!mary.equals("")){
										aa00201Result.setSummary(mary);
									}else{
										aa00201Result.setSummary(param[2]);
									}									
									
									aa00201Result.setBusiamt1(String.format("%,.2f",Double.valueOf(param[3])));
									aa00201Result.setBusiamt2(String.format("%,.2f",Double.valueOf(param[4])));
									aa00201Result.setBalance(String.format("%,.2f",Double.valueOf(param[5])));	
									list.add(aa00201Result);
									pnum++;
								}
							}else{
								System.out.println("结束记录于："+pnum);
								break;
							}
							sum+=1;
						}else{
							System.out.println("起始记录："+num);							
//							if(param[2].trim().compareTo(form.getStartdate().trim())>0&&form.getEnddate().trim().compareTo(param[0].trim())>0){
							if(1==1){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
										System.out.println("当前第 "+sum+" 条记录");
//										aa00201Result.setPrebalance(param[0]);
//										aa00201Result.setPaybegindate(param[1]);
//										aa00201Result.setTransdate(param[2]);
//										aa00201Result.setSummary(param[3]);	
//										aa00201Result.setBusiamt1(param[4]);
//										aa00201Result.setBusiamt2(param[5]);
//										aa00201Result.setBalance(param[6]);
//										aa00201Result.setBusiamt3(param[7]);
//										aa00201Result.setBusiamt4(param[8]);									
//										aa00201Result.setBusiamttotal1(param[9]);
//										aa00201Result.setBusiamttotal2(param[10]);
										
										
										if(param[0].startsWith("1899")){
											aa00201Result.setPaybegindate("----");
										}else{
											aa00201Result.setPaybegindate(param[0].replaceAll("-", "").substring(0, 6));
										}
										
										
										aa00201Result.setTransdate(param[1]);
										
										String mary = PropertiesReader.getProperty("yingshe.properties", "Summary"+param[2]).trim();
										if(mary!=null||!mary.equals("")){
											aa00201Result.setSummary(mary);
										}else{
											aa00201Result.setSummary(param[2]);
										}	
										
										aa00201Result.setBusiamt1(String.format("%,.2f",Double.valueOf(param[3])));
										aa00201Result.setBusiamt2(String.format("%,.2f",Double.valueOf(param[4])));
										aa00201Result.setBalance(String.format("%,.2f",Double.valueOf(param[5])));	
										list.add(aa00201Result);
										pnum++;
									}
								}else{
									System.out.println("结束记录于："+pnum);
									break;
								}
								sum+=1;
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();				
			}else{
				
				String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
				String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
				String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600015");
				
				
//				String reqxml = "<TranCode>600015</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:48</><MTimeStamp>2014-05-13 09:33:48</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>-4927</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>123112310</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><AccNum>00265744</><CertiNum>370620196207240511</><UnitAccNum>000341</><AccName>迟健</><_DOWNFILENAME>2014-04-21/downfile/lnque002.0</><AmtSum1>0.00</><OpenDate>1996-07-01</><Balance>0.00</><UnitAccName>威海市旅游码头有限责任公司</><AmtSum2>7063.30</>";
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YEMXCX.txt", reqxml, req);
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
					/****
					 *	此处应加入FTP下载 
					 **/
					FtpUtil f=new FtpUtil("bsp");
				    f.getFileByServer("/"+app002Result.getFileName());				    
				    File filerename = new File(ReadProperties.getString("bsp_local_path")+app002Result.getFileName());
				    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
					//========================================================================== 
					File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					int sum = 0;
					if(line != null){
						sum=1;
					}
					System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
					int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
					int pnum = 0;
					while (line != null) {// 判断读取到的字符串是否不为空					
						WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();					
						try {
							String[] param = line.split("~");
							line = breader.readLine();						
							log.debug("读取文件  ："+line);
							if(form.getEnddate().equals("")||form.getStartdate().equals("")){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
//										aa00201Result.setPrebalance(param[0]);
//										aa00201Result.setPaybegindate(param[1]);
//										aa00201Result.setTransdate(param[2]);
//										aa00201Result.setSummary(param[3]);	
//										aa00201Result.setBusiamt1(param[4]);
//										aa00201Result.setBusiamt2(param[5]);
//										aa00201Result.setBalance(param[6]);
//										aa00201Result.setBusiamt3(param[7]);
//										aa00201Result.setBusiamt4(param[8]);									
//										aa00201Result.setBusiamttotal1(param[9]);
//										aa00201Result.setBusiamttotal2(param[10]);
										
										
										if(param[0].startsWith("1899")){
											aa00201Result.setPaybegindate("----");
										}else{
											aa00201Result.setPaybegindate(param[0].replaceAll("-", "").substring(0, 6));
										}
										
										aa00201Result.setTransdate(param[1]);
										
										String mary = PropertiesReader.getProperty("yingshe.properties", "Summary"+param[2]).trim();
										if(mary!=null||!mary.equals("")){
											aa00201Result.setSummary(mary);
										}else{
											aa00201Result.setSummary(param[2]);
										}	
										;
										aa00201Result.setBusiamt1(String.format("%,.2f",Double.valueOf(param[3])));
										aa00201Result.setBusiamt2(String.format("%,.2f",Double.valueOf(param[4])));
										aa00201Result.setBalance(String.format("%,.2f",Double.valueOf(param[5])));										
										list.add(aa00201Result);
										pnum++;
									}
								}else{
									System.out.println("结束记录于："+pnum);
									break;
								}
								sum+=1;
							}else{
								System.out.println("起始记录："+num);							
//								if(param[0].trim().compareTo(form.getStartdate().trim())>0&&form.getEnddate().trim().compareTo(param[0].trim())>0){
								if(1==1){
									if(pnum<Integer.valueOf(form.getPagerows())){
										if(sum>num){
											System.out.println("当前第 "+sum+" 条记录");
//											aa00201Result.setPrebalance(param[0]);
//											aa00201Result.setPaybegindate(param[1]);
//											aa00201Result.setTransdate(param[2]);
//											aa00201Result.setSummary(param[3]);	
//											aa00201Result.setBusiamt1(param[4]);
//											aa00201Result.setBusiamt2(param[5]);
//											aa00201Result.setBalance(param[6]);
//											aa00201Result.setBusiamt3(param[7]);
//											aa00201Result.setBusiamt4(param[8]);									
//											aa00201Result.setBusiamttotal1(param[9]);
//											aa00201Result.setBusiamttotal2(param[10]);
											
											
											if(param[0].startsWith("1899")){
												aa00201Result.setPaybegindate("----");
											}else{
												aa00201Result.setPaybegindate(param[0].replaceAll("-", "").substring(0, 6));
											}
											
											aa00201Result.setTransdate(param[1]);
											
											String mary = PropertiesReader.getProperty("yingshe.properties", "Summary"+param[2]).trim();
											if(mary!=null||!mary.equals("")){
												aa00201Result.setSummary(mary);
											}else{
												aa00201Result.setSummary(param[2]);
											}	
											
											aa00201Result.setBusiamt1(String.format("%,.2f",Double.valueOf(param[3])));
											aa00201Result.setBusiamt2(String.format("%,.2f",Double.valueOf(param[4])));
											aa00201Result.setBalance(String.format("%,.2f",Double.valueOf(param[5])));	
											list.add(aa00201Result);
											pnum++;
										}
									}else{
										System.out.println("结束记录于："+pnum);
										break;
									}
									sum+=1;
								}
							}
							
						} catch (IOException e) {
							e.printStackTrace();						
						}
					}
					breader.close();
					isr.close();
					ffis.close();
				}				
				
				
			}		
			
			
		}
		
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}
		/***************************************************威海 201405***********************************/
		
		
		
		
		/*****************************************原 201405
		AppApi002Result app002Result = new AppApi002Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType").trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key").trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type").trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YEMXCX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YEMXCX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess.equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return "";
			}
			
			if(!app002Result.getFilename().equals("")){
				File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+app002Result.getFilename());
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
				if(line != null){
					sum=1;
				}
				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空					
					AppApi00201Result aa00201Result = new AppApi00201Result();					
					try {
						String[] param = line.split("\t");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);
						if(form.getEnddate().equals("")||form.getStartdate().equals("")){
							if(pnum<Integer.valueOf(form.getPagerows())){
								if(sum>num){
									aa00201Result.setOperationDate(param[0]);
									aa00201Result.setOperationType(param[1]);
									aa00201Result.setAmount(param[2]);
									aa00201Result.setOperationMemo(param[3]);						
									list.add(aa00201Result);
									pnum++;
								}
							}else{
								System.out.println("结束记录于："+pnum);
								break;
							}
							sum+=1;
						}else{
							System.out.println("起始记录："+num);							
							if(param[0].trim().compareTo(form.getStartdate().trim())>0&&form.getEnddate().trim().compareTo(param[0].trim())>0){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
										System.out.println("当前第 "+sum+" 条记录");
										aa00201Result.setOperationDate(param[0]);
										aa00201Result.setOperationType(param[1]);
										aa00201Result.setAmount(param[2]);
										aa00201Result.setOperationMemo(param[3]);						
										list.add(aa00201Result);
										pnum++;
									}
								}else{
									System.out.println("结束记录于："+pnum);
									break;
								}
								sum+=1;
							}
						}
						
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
			}
			
		}
		
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201.result", list.get(i));			
			result.add(appapi00101Result);
		}
		原 201405**********************************/
		
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
		return modelMap;
	}

}
