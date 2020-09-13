package com.yondervision.yfmap.handle.handleImpl.handle00053100;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi50102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.Httpclient;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50105_00053100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi50102Form form = (AppApi50102Form)form1;
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "askUrl"+form.getCenterId()).trim();
		String sr=Httpclient.sendPost(url+"/gss/apis/robotser/comment", "commenterId="+form.getSessionId()+"&content="+form.getQuestionAsk()+"&phone="+form.getPhoneNumber()+"&email="+form.getEmail()+"&source="+form.getSource());
		JSONObject jsonObject =JSONObject.fromObject(sr);  
		System.out.println("json:="+sr);
		String successful = jsonObject.getString("successful");
		System.out.println("successful:"+successful);  
		if("true".equals(successful))
		{
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");	
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
		Handle50105_00053100 hand = new Handle50105_00053100();
		ModelMap modelMap = new ModelMap();
		form.setCenterId("00053100");
		form.setSurplusAccount("123");
		form.setChannel("10");
		form.setUserId("abc");
		form.setSessionId("abc123123");
		form.setQuestionAsk("测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口测试留言接口");
		form.setPhoneNumber("");
		form.setEmail("");
		form.setSource("");
		hand.action(form, modelMap);
	}
}
