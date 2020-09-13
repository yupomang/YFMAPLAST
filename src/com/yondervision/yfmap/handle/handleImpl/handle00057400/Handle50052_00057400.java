package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil2;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.WebApi50052ZHResult;
import com.yondervision.yfmap.result.ningbo.WebApi50052_01ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50052_00057400  implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	@SuppressWarnings("rawtypes")	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		WebApi50052ZHResult webapi50052ZHResult = new WebApi50052ZHResult();
		
		AppApi00201Form form = (AppApi00201Form)form1;
		List<WebApi50052_01ZHResult> list = new ArrayList<WebApi50052_01ZHResult>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("00057400请求50052参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		HashMap resultMap =null;
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			
			if(CommonUtil.isEmpty(form.getIspaging())){
				form.setIspaging("1");
			}
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_50052_110160.txt", map, req);			
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "110160");
						
			log.debug("前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_50052_110160.txt", rexml, req);						
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, webapi50052ZHResult);
			log.debug("MAP封装成BEAN："+webapi50052ZHResult);
			if(!"0".equals(webapi50052ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", webapi50052ZHResult.getRecode());
				modelMap.put("msg", webapi50052ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+webapi50052ZHResult.getRecode()+"  ,  描述msg : "+webapi50052ZHResult.getMsg());
				return modelMap;
			}
			String filename = (String)resultMap.get("rsufilename");
			System.out.println("查询个人明细，批量文件："+filename);
			if(!CommonUtil.isEmpty(filename)){	
				FtpUtil2 f=new FtpUtil2("bsp");
				f.getFileByServerForNB2(filename);	   
//				FtpUtil f=new FtpUtil("bsp");
//				f.getFileByServerForNB(filename);	    
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				//File file = new File("/wls/ddd.txt");
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				//String line = breader.readLine(); 
				String line1 = breader.readLine(); 
				while (line1 != null) {// 判断读取到的字符串是否不为空
					try {							
						WebApi50052_01ZHResult webapi50052_01ZHResult = new WebApi50052_01ZHResult();
						log.debug("读取文件  ："+line1);
						String[] trs = line1.split("\\|");
						webapi50052_01ZHResult.setTransdate(trs[0].trim());
						webapi50052_01ZHResult.setParamdes(trs[1].trim());
						webapi50052_01ZHResult.setTotalnum(trs[2].trim());
						list.add(webapi50052_01ZHResult);
						line1 = breader.readLine();
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
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("detail", list);
		modelMap.put("consum", webapi50052ZHResult.getConsum());
		return modelMap;
	}

}
