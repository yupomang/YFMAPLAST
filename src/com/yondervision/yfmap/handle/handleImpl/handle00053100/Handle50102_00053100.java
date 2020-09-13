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

public class Handle50102_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50102Form form = (AppApi50102Form)form1;
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "askUrl"+form.getCenterId()).trim();
		String sr=Httpclient.sendPost(url+"/gss/apis/robotser/answer", "sessionId="+form.getSessionId()+"&ask="+form.getQuestionAsk()+"&type="+form.getType()+"&regionId="+form.getAnswerId());
		JSONObject jsonObject =JSONObject.fromObject(sr);  
		System.out.println("json:="+sr);
		String successful = jsonObject.getString("successful");
		String answerTxt = "";
		String sessionId = "";
		String answerType = "";
		String resultId = "";
		System.out.println("successful:"+successful);  
		JiNanAppApi50101Result jiNanAppApi50101Result = null;
		List<JiNanAppApi50101Result> jiNanAppApi50101ResultList = new ArrayList<JiNanAppApi50101Result>();
		if("true".equals(successful))
		{
			JSONObject result = JSONObject.fromObject(jsonObject.getString("result"));
			answerType = result.getString("answerType");
			answerTxt = result.getString("answerTxt");
			sessionId = result.getString("sessionId");
			resultId = result.getString("id");
			if("txt_reply".equals(answerType))
			{
				JSONArray titlesArray = result.getJSONArray("about");
				JSONObject row = null;  
				for (int i = 0; i < titlesArray.size(); i++) {  
					jiNanAppApi50101Result = new JiNanAppApi50101Result();
					row = titlesArray.getJSONObject(i); 
					jiNanAppApi50101Result.setTitleId(row.getString("id"));
					jiNanAppApi50101Result.setTitleName(row.getString("name"));
					jiNanAppApi50101Result.setType(row.getString("type"));
					jiNanAppApi50101Result.setRegionId(row.getString("regionId"));
					jiNanAppApi50101ResultList.add(jiNanAppApi50101Result);
				}
				
			}else if("naviIndex".equals(answerType))
			{
				JSONArray titlesArray = result.getJSONArray("navi");
				JSONObject row = null;  
				for (int i = 0; i < titlesArray.size(); i++) {  
					jiNanAppApi50101Result = new JiNanAppApi50101Result();
					row = titlesArray.getJSONObject(i); 
					jiNanAppApi50101Result.setTitleId(row.getString("id"));
					jiNanAppApi50101Result.setTitleName(row.getString("name"));
					jiNanAppApi50101Result.setType(row.getString("type"));
					jiNanAppApi50101Result.setRegionId(row.getString("regionId"));
					jiNanAppApi50101ResultList.add(jiNanAppApi50101Result);
				}
			}
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
			modelMap.put("answerTxt", answerTxt);	
			modelMap.put("sessionId", sessionId);	
			modelMap.put("answerType", answerType);	
			modelMap.put("resultId", resultId);	
			modelMap.put("aboutlist", jiNanAppApi50101ResultList);
			System.out.println(modelMap.toString());
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
		Handle50102_00053100 hand = new Handle50102_00053100();
		ModelMap modelMap = new ModelMap();
		form.setCenterId("00053100");
		form.setSurplusAccount("123");
		form.setChannel("10");
		form.setUserId("abc");
		form.setSessionId("abc123123");
		form.setQuestionAsk("测试");
		form.setType("");
		form.setAnswerId("");
		//form.setQuestionAsk("/root/zfgjjyw/gjjtq");
		//form.setType("hotaType");
		hand.action(form, modelMap);
	}
}
