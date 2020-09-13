package com.yondervision.yfmap.handle.handleImpl.handle00075500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00201Result;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00075500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		// TODO Auto-generated method stub
		/* 模拟返回开始  */	
		AppApi00201Form form = (AppApi00201Form)form1;
		List<AppApi00201Result> list = new ArrayList<AppApi00201Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		AppApi002Result app002Result = new AppApi002Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_YEMXCX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_YEMXCX.xml", xml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!Constants.sucess.equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
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
				int num = (Integer.valueOf(form.getPagenum())-1)*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空					
					AppApi00201Result aa00201Result = new AppApi00201Result();					
					try {
						String[] param = line.split("\t");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);
//						log.debug("读取文件  ："+param);
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
							}
							sum+=1;
						}else{
							System.out.println("param[0]"+param[0].trim());
							System.out.println("form.getStartdate()  "+form.getStartdate()+"   "+"form.getEnddate()  "+form.getEnddate());
							if(param[0].trim().compareTo(form.getStartdate().trim())>0&&form.getEnddate().trim().compareTo(param[0].trim())>0){
								if(pnum<Integer.valueOf(form.getPagerows())){
									if(sum>num){
										aa00201Result.setOperationDate(param[0]);
										aa00201Result.setOperationType(param[1]);
										aa00201Result.setAmount(param[2]);
										aa00201Result.setOperationMemo(param[3]);						
										list.add(aa00201Result);
										pnum++;
									}
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
		
		
		
//		Random rand = new Random();  
//		int m = rand.nextInt(3); 
//		if(m==1){
//			for(int i=1;i<10;i++){
//				AppApi00201Result app00201Result= new AppApi00201Result();
//				app00201Result.setOperationDate("200"+i+"-01-01");
//				app00201Result.setOperationMemo("购买具有所有权的住房提取");
//				app00201Result.setOperationType("提取第"+i+"期");
//				app00201Result.setAmount(i+"30000.00");		
//				list.add(app00201Result);
//			}
//		}else if(m==2){
//			for(int i=1;i<10;i++){
//				AppApi00201Result app00201Result= new AppApi00201Result();
//				app00201Result.setOperationDate("200"+i+"-01-01");
//				app00201Result.setOperationMemo("支付本市范围内住房租金提取");
//				app00201Result.setOperationType("提取第"+i+"期");
//				app00201Result.setAmount(i+"40000.00");		
//				list.add(app00201Result);
//			}
//		}else{
//			for(int i=1;i<10;i++){
//				AppApi00201Result app00201Result= new AppApi00201Result();
//				app00201Result.setOperationDate("200"+i+"-01-01");
//				app00201Result.setOperationMemo("建造、翻建、大修本市范围内具有所有权的住房提取");
//				app00201Result.setOperationType("提取第"+i+"期");
//				app00201Result.setAmount(i+"35000.00");		
//				list.add(app00201Result);
//			}
//		}
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
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
