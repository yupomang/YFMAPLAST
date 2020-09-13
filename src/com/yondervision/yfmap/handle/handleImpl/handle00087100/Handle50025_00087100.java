package com.yondervision.yfmap.handle.handleImpl.handle00087100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.kunming.AppApi00201ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi5002501ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi5002502ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi5002503ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/**
* 资统业务统计分析查询
* @ClassName: Handle50025_00087100 
* @Description: 资统业务统计分析查询
* @author Caozhongyan
* @date 2016年8月25日 上午11:43:59   
* 
*/ 
public class Handle50025_00087100 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi00201Form form = (AppApi00201Form)form1;
	
		System.out.println("YFMAP发往中心——个人交易密码修改");
		
		if("10000136".equals(form.getChannel())){
			form.setChannel("10");
		}else if("20000128".equals(form.getChannel())){
			form.setChannel("20");
		}else if("40000130".equals(form.getChannel())){
			form.setChannel("40");
		}
		
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi00201ZHResult app50024ZHResult = new AppApi00201ZHResult();
		List res = new ArrayList();
		if(Constants.method_BSP.equals(send)){
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
				form.setTranDate(form.getSendDate());
			}
			if(!CommonUtil.isEmpty(form.getChannel())){
				form.setFlag(Channel.getChannel(form.getChannel()));
			}			
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_ZJYWTJ.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REQ_GRJYMMXG.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314058");
//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode>314048</><TranDate>1</><TranIP>1</><TranSeq>1</><freeuse1_out>23</><freeuse2_out>23</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_ZJYWTJ.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087100/BSP_REP_GRJYMMXG.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50024ZHResult);
			log.debug("MAP封装成BEAN："+app50024ZHResult);
			if(!"0".equals(app50024ZHResult.getRecode())){
				modelMap.put("recode", app50024ZHResult.getRecode());
				modelMap.put("msg", app50024ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50024ZHResult.getRecode()+"  ,  描述msg : "+app50024ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("查询公积金明细，批量文件："+filename);
			
			if(!CommonUtil.isEmpty(filename)){				
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				
				String line = breader.readLine();
				/**
				渠道标记	channel
				资金类业务类型标记	summary
				发生总笔数	accname
				成功笔数	damt
				失败笔数	camt
				发生金额	balance
				 */
				String channel = "";
				String summary = "";
				String accname = "";
				String damt = "";
				String camt = "";
				String balance = "";
				
				if(!CommonUtil.isEmpty(form.getFlag())){
					AppApi5002501ZHResult app50025ZHResult01 = new AppApi5002501ZHResult();					
					AppApi5002502ZHResult app50025ZHResultYWL = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWCGS = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWSBS = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWJE = new AppApi5002502ZHResult();
					app50025ZHResultYWL.setName("业务量");
					app50025ZHResultYWCGS.setName("业务成功数");
					app50025ZHResultYWSBS.setName("业务失败数");
					app50025ZHResultYWJE.setName("金额");
					List ywl = new ArrayList();
					List ywcgs = new ArrayList();
					List ywsbs = new ArrayList();
					List ywje = new ArrayList();
					while (line != null) {
						try {
							log.debug("读取文件  ："+line);
							line = line + "~0";
							String[] trs = line.split("~");
							
							if(CommonUtil.isEmpty(app50025ZHResult01.getAppname())){
								app50025ZHResult01.setAppname(Tools.getMessage(trs[0]));
							}
							AppApi5002503ZHResult value1 = new AppApi5002503ZHResult();
							value1.setName(Tools.getMessage(trs[1]));
							value1.setValue(trs[2]);
							ywl.add(value1);
							
							AppApi5002503ZHResult value2 = new AppApi5002503ZHResult();
							value2.setName(Tools.getMessage(trs[1]));
							value2.setValue(trs[3]);
							ywcgs.add(value2);
							
							AppApi5002503ZHResult value3 = new AppApi5002503ZHResult();
							value3.setName(Tools.getMessage(trs[1]));
							value3.setValue(trs[4]);
							ywsbs.add(value3);
							
							AppApi5002503ZHResult value4 = new AppApi5002503ZHResult();
							value4.setName(Tools.getMessage(trs[1]));
							BigDecimal je = new BigDecimal(trs[5]);
							value4.setValue(String.format("%,.2f",je));
							ywje.add(value4);
								
							line = breader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					app50025ZHResultYWL.setValue(ywl);
					app50025ZHResultYWCGS.setValue(ywcgs);
					app50025ZHResultYWSBS.setValue(ywsbs);
					app50025ZHResultYWJE.setValue(ywje);
					
					List ars = new ArrayList();
					ars.add(app50025ZHResultYWL);
					ars.add(app50025ZHResultYWCGS);
					ars.add(app50025ZHResultYWSBS);
					ars.add(app50025ZHResultYWJE);
					app50025ZHResult01.setData(ars);
					res.add(app50025ZHResult01);
					
				} else {
					/**
					 * 
					 * 查询所有渠道交易金额
					 * 
					 * 
					 * 
					 * **/
					AppApi5002501ZHResult app50025ZHResult01 = new AppApi5002501ZHResult();					
					AppApi5002502ZHResult app50025ZHResultYWL = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWCGS = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWSBS = new AppApi5002502ZHResult();
					AppApi5002502ZHResult app50025ZHResultYWJE = new AppApi5002502ZHResult();
					app50025ZHResultYWL.setName("业务量");
					app50025ZHResultYWCGS.setName("业务成功数");
					app50025ZHResultYWSBS.setName("业务失败数");
					app50025ZHResultYWJE.setName("金额");
					List ywl = new ArrayList();
					List ywcgs = new ArrayList();
					List ywsbs = new ArrayList();
					List ywje = new ArrayList();
					int num = 0;
					while (line != null) {
						try {
							log.debug("读取文件  ："+line);
							line = line + "~0";
							String[] trs = line.split("~");
							
							if(CommonUtil.isEmpty(app50025ZHResult01.getAppname())){
								app50025ZHResult01.setAppname(Tools.getMessage(trs[0]));
							}
							AppApi5002503ZHResult value1 = new AppApi5002503ZHResult();
							value1.setName(Tools.getMessage(trs[1]));
							value1.setValue(trs[2]);
							ywl.add(value1);
							
							AppApi5002503ZHResult value2 = new AppApi5002503ZHResult();
							value2.setName(Tools.getMessage(trs[1]));
							value2.setValue(trs[3]);
							ywcgs.add(value2);
							
							AppApi5002503ZHResult value3 = new AppApi5002503ZHResult();
							value3.setName(Tools.getMessage(trs[1]));
							value3.setValue(trs[4]);
							ywsbs.add(value3);
							
							AppApi5002503ZHResult value4 = new AppApi5002503ZHResult();
							value4.setName(Tools.getMessage(trs[1]));
							BigDecimal je = new BigDecimal(trs[5]);
							value4.setValue(String.format("%,.2f",je));
							ywje.add(value4);
							
							num++;
							
							if(num==6){
								app50025ZHResultYWL.setValue(ywl);
								app50025ZHResultYWCGS.setValue(ywcgs);
								app50025ZHResultYWSBS.setValue(ywsbs);
								app50025ZHResultYWJE.setValue(ywje);
								
								List ars = new ArrayList();
								ars.add(app50025ZHResultYWL);
								ars.add(app50025ZHResultYWCGS);
								ars.add(app50025ZHResultYWSBS);
								ars.add(app50025ZHResultYWJE);
								app50025ZHResult01.setData(ars);
								res.add(app50025ZHResult01);
								
								app50025ZHResult01 = new AppApi5002501ZHResult();					
								app50025ZHResultYWL = new AppApi5002502ZHResult();
								app50025ZHResultYWCGS = new AppApi5002502ZHResult();
								app50025ZHResultYWSBS = new AppApi5002502ZHResult();
								app50025ZHResultYWJE = new AppApi5002502ZHResult();
								app50025ZHResultYWL.setName("业务量");
								app50025ZHResultYWCGS.setName("业务成功数");
								app50025ZHResultYWSBS.setName("业务失败数");
								app50025ZHResultYWJE.setName("金额");
								ywl = new ArrayList();
								ywcgs = new ArrayList();
								ywsbs = new ArrayList();
								ywje = new ArrayList();
								num=0;
							}
							
							line = breader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
				breader.close();
				isr.close();
				ffis.close();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
			
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("rows", res);
		
		System.out.println("=========:"+JsonUtil.getGson().toJson(modelMap));
		log.debug("=========:"+JsonUtil.getGson().toJson(modelMap));
		return modelMap;
	}

}
