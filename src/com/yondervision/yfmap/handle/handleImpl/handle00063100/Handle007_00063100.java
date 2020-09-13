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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle007_00063100 implements CtrlHandleInter {
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
		AppApi00501Form form = (AppApi00501Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00701 start.");
		
		//----------------------------------------------------威海应用开始201405
		if(CommonUtil.isEmpty(form.getBodyCardNumber())){
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询参数缺失");
			return modelMap;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api007"+"SF"+form.getBodyCardNumber()+"GJJ"+form.getStartdate()+form.getEnddate()+".txt";
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		
		List<WeiHaiAppApi007Result> list = new ArrayList<WeiHaiAppApi007Result>();
		
		log.debug("00701 文件名:"+fname);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();		
		WeiHaiAppApi00601Result app006Result = new WeiHaiAppApi00601Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKMXCX.txt", map, req);
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
					WeiHaiAppApi007Result app007Result = new WeiHaiAppApi007Result();					
					try {
						String[] param = line.split("~");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);
						if(form.getEnddate().equals("")||form.getStartdate().equals("")){
							if(pnum<Integer.valueOf(form.getPagerows())){
								if(sum>num){	
									if(param[0].startsWith("1899")){
										app007Result.setEndDate("----");
									}else{
										app007Result.setEndDate(param[0]);
									}
									
									app007Result.setPlanPrin(String.format("%,.2f",Double.valueOf(param[1])));
									app007Result.setPlanInt(String.format("%,.2f",Double.valueOf(param[2])));
									app007Result.setFactRetPun(String.format("%,.2f",Double.valueOf(param[3])));
									app007Result.setPlanSum(String.format("%,.2f",Double.valueOf(param[4])));
									app007Result.setSeqNum(param[5]);
									
									String mary1 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateP"+param[6]).trim();
									if(mary1!=null||!mary1.equals("")){
										app007Result.setCurSeqStateP(mary1);
									}else{
										app007Result.setCurSeqStateP(param[6]);
									}
									String mary2 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateD"+param[7]).trim();
									if(mary2!=null||!mary2.equals("")){
										app007Result.setCurSeqStateD(mary2);
									}else{
										app007Result.setCurSeqStateD(param[7]);
									}
									
									app007Result.setInfactDate(param[8]);								
									app007Result.setEndLoanBal(String.format("%,.2f",Double.valueOf(param[9])));
									list.add(app007Result);
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
//							if(1==1){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
										System.out.println("当前第 "+sum+" 条记录");
										
										if(param[0].startsWith("1899")){
											app007Result.setEndDate("----");
										}else{
											app007Result.setEndDate(param[0]);
										}
										
										
										app007Result.setPlanPrin(String.format("%,.2f",Double.valueOf(param[1])));
										app007Result.setPlanInt(String.format("%,.2f",Double.valueOf(param[2])));
										app007Result.setFactRetPun(String.format("%,.2f",Double.valueOf(param[3])));
										app007Result.setPlanSum(String.format("%,.2f",Double.valueOf(param[4])));
										app007Result.setSeqNum(param[5]);
										
										String mary1 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateP"+param[6]).trim();
										if(mary1!=null||!mary1.equals("")){
											app007Result.setCurSeqStateP(mary1);
										}else{
											app007Result.setCurSeqStateP(param[6]);
										}
										String mary2 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateD"+param[7]).trim();
										if(mary2!=null||!mary2.equals("")){
											app007Result.setCurSeqStateD(mary2);
										}else{
											app007Result.setCurSeqStateD(param[7]);
										}
										
										app007Result.setInfactDate(param[8]);								
										app007Result.setEndLoanBal(String.format("%,.2f",Double.valueOf(param[9])));					
										list.add(app007Result);
										pnum++;
									}
								}else{
									System.out.println("结束记录于："+pnum);
									break;
								}
								sum+=1;
//							}
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
				String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "600029");
				
//				String reqxml = "<TranCode>600029</><TranDate>2014-04-21</><STimeStamp>2014-05-13 09:33:54</><MTimeStamp>2014-05-13 09:33:54</><BrcCode>00063000</><TellCode>6000</><ChkCode>1231</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><TranChannel>11</><TranIP>192.168.3.221</><ChannelSeq>-4928</><TranSeq>0</><BusiSeq>0</><RspCode>000000</><RspMsg>交易处理成功...</><NoteMsg></><AuthFlag>123112310</><FinancialDate>2014-01-05</><HostBank>aaa</><SubBank>aaa</><_DOWNFILENAME>2014-04-21/downfile/lnque003.0</><InfactSum>170870.91</><InfactPunSum>0.00</><InfactIntSum>30870.91</><InfactPrinSum>140000.00</><CurRate>4.35</><CurDayBal>0.00</><AttermDate>2017-10-17</><BeginIntDate>2002-10-18</><CosignDate>2002-09-25</><LoanTerm>180</><LoanSum>140000.00</><CerId1>370620196207240511</><AccName>迟健</><AgencyBank>14</><OweAmt>0.00</><OweTerm>0</>";
				HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKMXCX.txt", reqxml, req);
				log.debug("解析报文MAP："+resultMap);
				BeanUtil.transMap2Bean(resultMap, app006Result);
				log.debug("MAP封装成BEAN："+app006Result);
				if(!"0".equals(app006Result.getRecode())){
					modelMap.put("recode", app006Result.getRecode());
					modelMap.put("msg", app006Result.getMsg());
					log.error("中心返回报文 状态recode :"+app006Result.getRecode()+"  ,  描述msg : "+app006Result.getMsg());
					return modelMap;
				}
				
				if(!app006Result.getDownFileName().equals("")){
					FtpUtil f=new FtpUtil("bsp");
				    f.getFileByServer("/"+app006Result.getDownFileName());	    
				    
				    log.debug("+++++ 核心返回文件位置："+ReadProperties.getString("bsp_local_path")+app006Result.getDownFileName());
				    log.debug("+++++ 新生成文件位置："+ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				    File filerename = new File(ReadProperties.getString("bsp_local_path")+app006Result.getDownFileName());
				    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
					//--------------------------------------------------------------
					File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "UTF-8");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					int sum = 0;
					if(line != null){
						sum=1;
					}
					log.debug("当前页："+form.getPagenum()+"   每页条数："+form.getPagerows());
					int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
					int pnum = 0;
					while (line != null) {// 判断读取到的字符串是否不为空					
						WeiHaiAppApi007Result app007Result = new WeiHaiAppApi007Result();					
						try {
							String[] param = line.split("~");
							line = breader.readLine();						
							log.debug("读取文件  ："+line);
//							log.debug("读取文件  ："+param);
							if(form.getEnddate().equals("")||form.getStartdate().equals("")){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
										
										
										if(param[0].startsWith("1899")){
											app007Result.setEndDate("----");
										}else{
											app007Result.setEndDate(param[0]);
										}
										
										app007Result.setPlanPrin(String.format("%,.2f",Double.valueOf(param[1])));
										app007Result.setPlanInt(String.format("%,.2f",Double.valueOf(param[2])));
										app007Result.setFactRetPun(String.format("%,.2f",Double.valueOf(param[3])));
										app007Result.setPlanSum(String.format("%,.2f",Double.valueOf(param[4])));
										app007Result.setSeqNum(param[5]);
										
										String mary1 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateP"+param[6]).trim();
										if(mary1!=null||!mary1.equals("")){
											app007Result.setCurSeqStateP(mary1);
										}else{
											app007Result.setCurSeqStateP(param[6]);
										}
										String mary2 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateD"+param[7]).trim();
										if(mary2!=null||!mary2.equals("")){
											app007Result.setCurSeqStateD(mary2);
										}else{
											app007Result.setCurSeqStateD(param[7]);
										}
										
										app007Result.setInfactDate(param[8]);								
										app007Result.setEndLoanBal(String.format("%,.2f",Double.valueOf(param[9])));
										list.add(app007Result);
										pnum++;
									}
								}else{
									break;								
								}
								sum+=1;	
							}else{
//								if(param[0].compareTo(form.getStartdate())>0&&form.getEnddate().compareTo(param[0])>0){
									if(pnum<Integer.valueOf(form.getPagerows())){
										if(sum>num){
											if(param[0].startsWith("1899")){
												app007Result.setEndDate("----");
											}else{
												app007Result.setEndDate(param[0]);
											}
											app007Result.setPlanPrin(String.format("%,.2f",Double.valueOf(param[1])));
											app007Result.setPlanInt(String.format("%,.2f",Double.valueOf(param[2])));
											app007Result.setFactRetPun(String.format("%,.2f",Double.valueOf(param[3])));
											app007Result.setPlanSum(String.format("%,.2f",Double.valueOf(param[4])));
											app007Result.setSeqNum(param[5]);
											
											String mary1 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateP"+param[6]).trim();
											if(mary1!=null||!mary1.equals("")){
												app007Result.setCurSeqStateP(mary1);
											}else{
												app007Result.setCurSeqStateP(param[6]);
											}
											String mary2 = PropertiesReader.getProperty("yingshe.properties", "CurSeqStateD"+param[7]).trim();
											if(mary2!=null||!mary2.equals("")){
												app007Result.setCurSeqStateD(mary2);
											}else{
												app007Result.setCurSeqStateD(param[7]);
											}
											
											app007Result.setInfactDate(param[8]);								
											app007Result.setEndLoanBal(String.format("%,.2f",Double.valueOf(param[9])));
											list.add(app007Result);
											pnum++;
										}
									}else{
										break;								
									}
									sum+=1;	
//								}							
							}
												
						} catch (IOException e) {
							e.printStackTrace();						
						}
					}
					breader.close();
				}
				
				
				
			}
		}
		
		
		

		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", (WeiHaiAppApi007Result)list.get(i));	
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
