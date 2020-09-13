package com.yondervision.yfmap.handle.handleImpl.handle00091200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
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
import com.yondervision.yfmap.result.baoshan.AppApi00701Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle007_00091200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00701Result> list = new ArrayList<AppApi00701Result>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		HashMap resultMap = null;
		AppApi00701Result app007Result = new AppApi00701Result();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
		Date date = new Date();
		String fname = formatter1.format(date)+"api007"+form.getStartdate()+"_"+form.getEnddate()+"SF"+form.getBodyCardNumber()+"GJJDKMX"+form.getSurplusAccount()+".txt";
		
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());
			if(Constants.CHANNELTYPE_APP.equals(form.getChannel())){
				form.setChannel("7");
			}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
				form.setChannel("9");
			}
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKMXCX.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430404");
			
//			String rexml ="<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430404</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>成功</ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><amt>454344</amt><terms>156期</terms><amt1>283870</amt1><amt2>35260</amt2><filename>dkmxtest.txt</filename></body></root>";
			
			log.debug("前置YFMAP接收中心报文--："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?>"+rexml;
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_DKMXCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app007Result);
			log.debug("MAP封装成BEAN："+app007Result);
			if(!"00".equals(app007Result.getRecode())){
				modelMap.put("recode", app007Result.getRecode());
				modelMap.put("msg", app007Result.getMsg());
				log.error("中心返回报文 状态recode :"+app007Result.getRecode()+"  ,  描述msg : "+app007Result.getMsg());
				return modelMap;
			}
			
			String filename = app007Result.getFilename();
			System.out.println("查询贷款还款明细，返回文件名："+filename);
			if(filename!=null && !"".equals(filename)){
				//String filename = app007Result.getFileName().substring(aa00701Result.getFileName().lastIndexOf("/"));
				/****
				 *	此处应加入FTP下载 
				 **/
				FtpUtil f=new FtpUtil("csp");
			    f.getFileByServer(filename);				    
			    File filerename = new File(ReadProperties.getString("csp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname);
//				File file = new File("/edoc"+"/"+app007Result.getFilename());
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();								
				log.debug(line);
				int lineNo = 1;
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00701Result aa00701Result = new AppApi00701Result();
					try {
						String[] param = line.split("\\|");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);		
						if(lineNo >=2){
							//aa00701Result.setPlandate(param[0].trim());
							//aa00701Result.setPlanretprin(moneyTran(param[1].trim()));
							//aa00701Result.setPlanretint(moneyTran(param[2].trim()));
							aa00701Result.setInfactdate(param[3].trim());
							aa00701Result.setInfactretprin(moneyTran(param[0].trim()));
							aa00701Result.setInfactretint(moneyTran(param[1].trim()));
							aa00701Result.setInfactpun(moneyTran(param[2].trim()));
							list.add(aa00701Result);
						}
						lineNo = lineNo + 1;
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
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
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".detail", (AppApi00701Result)list.get(i));	
			result.add(result1);
		}
		app007Result.setTotalrepay(moneyTran(app007Result.getTotalrepay()));
		app007Result.setInfactprin_z(moneyTran(app007Result.getInfactprin_z()));
		app007Result.setInfactint_z(moneyTran(app007Result.getInfactint_z()));
		List<TitleInfoBean> appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", app007Result);	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", result);
		modelMap.put("result", appapi00701Result);	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}

	private String moneyTran(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = NumberFormat.getCurrencyInstance().format(new Double(money)).substring(1);
		return result;
	}
}
