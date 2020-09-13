package com.yondervision.yfmap.handle.handleImpl.handle00053100;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi50102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.result.jinan.JiNanAppApi50101Result;
import com.yondervision.yfmap.util.Httpclient;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50103_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50102Form form = (AppApi50102Form)form1;
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "askUrl"+form.getCenterId()).trim();
		String sr=Httpclient.sendPost(url+"/gss/apis/robotser/askhit", "sessionId="+form.getSessionId()+"&ask="+form.getAlertAsk());
		JSONObject jsonObject =JSONObject.fromObject(sr);  
		System.out.println("json:="+sr);
		String successful = jsonObject.getString("successful");
		System.out.println("successful:"+successful);  
		JiNanAppApi50101Result jiNanAppApi50101Result = null;
		List<JiNanAppApi50101Result> jiNanAppApi50101ResultList = new ArrayList<JiNanAppApi50101Result>();
		if("true".equals(successful))
		{
			JSONArray resultArray = jsonObject.getJSONArray("result"); 
			String row = null;  
			for (int i = 0; i < resultArray.size(); i++) {  
				jiNanAppApi50101Result = new JiNanAppApi50101Result();
				row = resultArray.getString(i); 
				jiNanAppApi50101Result.setTitleName(row);
				jiNanAppApi50101ResultList.add(jiNanAppApi50101Result);
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("list", jiNanAppApi50101ResultList);
		}else
		{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "智能机器人繁忙，稍后再试！");	
		}
//		Handle_Check_00041100 check = new Handle_Check_00041100();
//		check.YzCheck(form1, modelMap, "50001");
		return modelMap;
	}
	
	public static void main(String[] args) throws CenterRuntimeException, Exception{
		AppApi50102Form form = new AppApi50102Form();
		Handle50103_00053100 hand = new Handle50103_00053100();
		ModelMap modelMap = new ModelMap();
		form.setCenterId("00053100");
		form.setSurplusAccount("123");
		form.setChannel("10");
		form.setUserId("abc");
		form.setSessionId("6ft1nd7a50p5mjk78k30");
		form.setAlertAsk("提取");
		hand.action(form, modelMap);
	}
}
