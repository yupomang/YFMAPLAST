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
import com.yondervision.yfmap.form.AppApi80102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80105_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/UnitloginAct";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142507");
		params.put("task", "other");
		params.put("unitaccnum", form.getFullName());
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		 JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("success").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.getString("msg"));
		 }else{
			 modelMap.put("recode", "000000");
			 modelMap.put("msg", "成功");
			 Map map = new HashMap<String,String>();
			 map.put("dwmc", obj.getString("unitaccname"));
			 map.put("zhzt", obj.getString("freeuse1"));
			 map.put("gjjje", obj.getString("bal"));
			 map.put("aybtje", obj.getString("keepbal3"));
			 map.put("hbbtje", obj.getString("keepbal4"));
			 
			 map.put("gjjsyjcrs", obj.getString("num_web1"));
			 map.put("aybtsyjcrs", obj.getString("num_web9"));
			 map.put("gjjsyjcje", obj.getString("amt_wb1"));
			 map.put("aybtsyjcje", obj.getString("amt_wb9"));
			 
			 map.put("gjjbyzjrs", obj.getString("num_web2"));
			 map.put("aybtbyzjrs", obj.getString("num_web10"));
			 map.put("gjjbyzjje", obj.getString("amt_wb2"));
			 map.put("aybtbyzjje", obj.getString("amt_wb10"));
			 
			 map.put("gjjbyjsrs", obj.getString("num_web3"));
			 map.put("aybtbyjsrs", obj.getString("num_web11"));
			 map.put("gjjbyjsje", obj.getString("amt_wb3"));
			 map.put("aybtbyjsje", obj.getString("amt_wb11"));
			 
			 map.put("gjjbyjcrs", obj.getString("num_web4"));
			 map.put("aybtbyjcrs", obj.getString("num_web12"));
			 map.put("gjjbyjcje", obj.getString("amt_wb4"));
			 map.put("aybtbyjcje", obj.getString("amt_wb12"));
			 
			 modelMap.put("result", map);
			 
		 }
		
		return modelMap;
	}
}
