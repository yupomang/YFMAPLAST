package com.yondervision.yfmap.handle.handleImpl.handle00075800;

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

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00401Result;
import com.yondervision.yfmap.result.AppApi00501Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle005_00075800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		log.debug("form:"+form);
		
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		form.setPagenum(String.valueOf(Integer.parseInt(form.getPagenum())+1));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Send"+form.getCenterId()).trim();
		/* 模拟返回开始  */
		List<AppApi00501Result> list = new ArrayList<AppApi00501Result>();
		AppApi002Result app002Result = new AppApi002Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_TQMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "A00019");			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_TQMXCX.xml", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);			
			
			if(!"0".equals(app002Result.getIsloanflag())){
				modelMap.put("recode", app002Result.getIsloanflag());
				modelMap.put("msg", app002Result.getAccinstname());
				log.error("中心返回报文 状态recode :"+app002Result.getIsloanflag()+"  ,  描述msg : "+app002Result.getAccinstname());
				return modelMap;
			}else{
				if(!"0000".equals(app002Result.getRecode())){
					modelMap.put("recode", app002Result.getRecode());
					modelMap.put("msg", app002Result.getMsg());
					log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
					return modelMap;
				}
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			
			if(filename!=null){
				File file = new File(filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
				if(line != null){
					sum=1;
					String[] par = line.split("\\@\\|\\$");
					if("1899-12-31".equals(par[0])){
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "查无数据!");
						log.error("公积提取明细查询无数据！");
						return modelMap;
					}
				}
				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空		
					AppApi00501Result aa00501Result = new AppApi00501Result();
					try {
						String[] param = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						aa00501Result.setOperationDate(param[0]);
						aa00501Result.setOperationMemo(param[1]);
						aa00501Result.setWithdrawalAmount(param[2]);
						list.add(aa00501Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
			}			
		}
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00501"+form.getCenterId()+".result", (AppApi00501Result)list.get(i));	
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
		return modelMap;
	}

}
