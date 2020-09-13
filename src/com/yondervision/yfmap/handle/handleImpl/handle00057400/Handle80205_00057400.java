package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.math.BigDecimal;
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
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80205_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form)form1;
		log.debug("form:"+form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/GJJQuery";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142503");
		params.put("spflag", "spflag");
		params.put("task", "other");
		params.put("accnum", form.getSurplusAccount());
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		System.out.println(result+"=====");
		 JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("success").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.getString("msg"));
		 }else{
			 modelMap.put("recode", "000000");
			 modelMap.put("msg", "成功");
			 Map map = new HashMap<String,String>();
			 map.put("name", obj.getString("accname"));
			 map.put("zhzt", obj.getString("freeuse1"));
			 map.put("dwmc", obj.getString("unitaccname"));
			 map.put("gjjjje", obj.getString("monpaysum"));
			 map.put("gjjje", obj.getString("amt"));
			 map.put("anbtjje", obj.getString("subintamt"));
			 map.put("aybtje", obj.getString("amt3"));
			 map.put("hbbtje", obj.getString("amt8"));
			 map.put("accinstcodedes", obj.getString("accinstcodedes"));
			 map.put("hjjje", new BigDecimal(obj.getString("monpaysum")).add(new BigDecimal(obj.getString("subintamt"))));
			 map.put("hjye", new BigDecimal(obj.getString("amt")).add(new BigDecimal(obj.getString("amt3"))).add(new BigDecimal(obj.getString("amt8"))));
			 modelMap.put("result", map);
		 }
		
		return modelMap;
	}
}
