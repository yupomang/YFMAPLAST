package com.yondervision.yfmap.handle.handleImpl.handle00075500;

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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00702_00075500 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00701Result> list = new ArrayList<AppApi00701Result>();
		log.info(Constants.LOG_HEAD+"appApi00702 start.");
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		
		AppApi002Result app002Result = new AppApi002Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_HKJHCX_"+form.getSurplusAccount()+".xml", map, req);
			log.debug("发往中心报文："+xml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_HKJHCX.xml", xml, req);
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
				int num = Integer.valueOf(form.getPagenum())*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
				Date date = new Date();
				while (line != null) {// 判断读取到的字符串是否不为空					
					AppApi00701Result aa00701Result = new AppApi00701Result();					
					try {
						String[] param = line.split("\t");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);
						if(formatter.format(date).compareTo(param[0])<=0){
							if(pnum<Integer.valueOf(form.getPagerows())){
								if(sum>num){
									aa00701Result.setOperationDate(param[0]);
									aa00701Result.setOperationType(param[1]);
									aa00701Result.setWithdrawalAmount(param[2]);
									aa00701Result.setLxAmount(param[3]);
									//aa00701Result.setFxAmount(param[4]);
									list.add(aa00701Result);
									pnum++;
								}
							}
							sum+=1;
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
//				AppApi00501Result appApi00501Result	= new AppApi00501Result();
//				appApi00501Result.setOperationDate("200"+i+"-10-01");
//				appApi00501Result.setOperationMemo("装修贷款第"+i+"期");
//				appApi00501Result.setOperationType("装修贷款");
//				appApi00501Result.setWithdrawalAmount("30000.00");
//				list.add(appApi00501Result);
//			}
//		}else if(m==2){
//			for(int i=1;i<10;i++){
//				AppApi00501Result appApi00501Result	= new AppApi00501Result();
//				appApi00501Result.setOperationDate("200"+i+"-10-01");
//				appApi00501Result.setOperationMemo("房屋翻建贷款第"+i+"期");
//				appApi00501Result.setOperationType("房屋翻建贷款");
//				appApi00501Result.setWithdrawalAmount("28000.00");
//				list.add(appApi00501Result);
//			}
//		}else{
//			for(int i=1;i<10;i++){
//				AppApi00501Result appApi00501Result	= new AppApi00501Result();
//				appApi00501Result.setOperationDate("200"+i+"-10-01");
//				appApi00501Result.setOperationMemo("其他住房消费贷款第"+i+"期");
//				appApi00501Result.setOperationType("其他住房消费贷款");
//				appApi00501Result.setWithdrawalAmount("20000.00");
//				list.add(appApi00501Result);
//			}
//		}
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00702"+form.getCenterId()+".detail", (AppApi00701Result)list.get(i));	
			detail.add(result1);
		}
		
		List<TitleInfoBean> appapi00701Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00702"+form.getCenterId()+".result", app002Result);
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		System.out.println(jsonObj);
		/* 模拟返回结束  */			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00701Result);
		modelMap.put("detail", detail);	
		log.info(Constants.LOG_HEAD+"appApi00702 end.");		
		return modelMap;
	}

}
