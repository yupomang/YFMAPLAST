package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.HTTPSendMessage;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.util.MachineCodeUtils;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80108_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/UnitloginAct";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142506");
		params.put("task", "other");
		params.put("unitaccnum", form.getFullName());
		params.put("pwd", form.getNewpassword());
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("success").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.has("msg")?obj.getString("msg"):"登录验证失败，请联系中心");
		 }else{
			 modelMap.put("recode", "000000");
			 modelMap.put("msg", "成功");
		 }
//		 log.info("msg====="+obj.getString("msg"));
		return modelMap;
	}
}
