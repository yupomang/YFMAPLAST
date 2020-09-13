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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baoshan.AppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00091200 implements CtrlHandleInter {
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
		AppApi00201Form form = (AppApi00201Form)form1;
		List<AppApi00201Result> list = new ArrayList<AppApi00201Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		AppApi002Result app002Result = new AppApi002Result();
		HashMap resultMap =null;
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
		Date date = new Date();
		String fname = formatter1.format(date)+"api002"+form.getStartdate()+"_"+form.getEnddate()+"SF"+form.getBodyCardNumber()+"GJJ"+form.getSurplusAccount()+".txt";
		
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());

			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			if(Constants.CHANNELTYPE_APP.equals(form.getChannel())){
				form.setChannel("7");
			}else if(Constants.CHANNELTYPE_WEIXIN.equals(form.getChannel())){
				form.setChannel("9");
			}
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YEMXCX.xml", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);	
			System.out.println("前置YFMAP发往中心报文--："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430406");
			
//			String rexml ="<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430406</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>成功</ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><filename>zhmxtest.txt</filename></body></root>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			System.out.println("前置YFMAP接收中心报文输出--："+rexml);
			rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?>"+rexml;
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YEMXCX.xml", rexml, req);
			System.out.println("解析报文MAP输出："+resultMap);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!"00".equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			String filename = app002Result.getFilename();
			System.out.println("查询公积金明细，返回文件名："+filename);
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
//				File file = new File("/edoc"+"/"+app002Result.getFilename());
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();								
				log.debug(line);
				int lineNo = 1;
				
				String zhmxNextServer = PropertiesReader.getProperty("properties.properties", "ZHMX_"+form.getCenterId()+"_NEXT_SERVER");
				
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00201Result aa00201Result = new AppApi00201Result();
					try {
						String[] param = line.split("\\|");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);		
						if(lineNo >=2){
							aa00201Result.setTransseq(param[0].trim());
							aa00201Result.setTransdate(param[1]);
							aa00201Result.setSummary(param[2].trim());
							aa00201Result.setSummaryCode(param[3].trim());
							aa00201Result.setTransmon(moneyTran(param[4].trim()));
							aa00201Result.setBalance(moneyTran(param[5].trim()));
							if(zhmxNextServer.indexOf(param[3].trim())>=0){
								aa00201Result.setNextflg("1");//允许
							}else {
								aa00201Result.setNextflg("0");//不允许
							}
							list.add(aa00201Result);
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
		List<TitleInfoBean> appapi00201Result = null;
		for(int i=0;i<list.size();i++){
			appapi00201Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".detail", list.get(i));			
			result.add(appapi00201Result);
		}
		List<TitleInfoBean> appapi002Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", app002Result);	
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi002Result);
		modelMap.put("detail", result);
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
