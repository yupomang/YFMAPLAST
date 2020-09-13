package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.HTTPSendMessage;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.ningbo.GjDwzhmxResult;
import com.yondervision.yfmap.result.ningbo.GjgrmxResult;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80106_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi80102Form form = (AppApi80102Form)form1;
		log.debug("form:"+form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/UnitloginAct";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142508");
		params.put("task", "other");
		params.put("unitaccnum", form.getDwzh());
		String indiacctype = "1";
		String indiacctypems = "公积金账户";
		if(form.getZhlx().equals("01")){
			indiacctype = "1";
			indiacctypems = "公积金账户";
		}else if(form.getZhlx().equals("02")){
			indiacctype = "3";
			indiacctypems = "按月补贴账户";
		}else if(form.getZhlx().equals("03")){
			indiacctype = "2";
			indiacctypems = "货币补贴账户";
		}
		params.put("indiacctype", indiacctype);
		int pages = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		params.put("num_web1", pages+"");
		params.put("num_web2", rows+"");
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		 JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("success").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.getString("msg"));
		 }else{
			 JSONArray list = obj.getJSONArray("array");
			 List<List<TitleInfoBean>> result1 = new ArrayList();
			 List<TitleInfoBean> GjDwzhmxResult = null;
			 for(int i=0;i<list.size();i++){
				 JSONObject tempobj = list.getJSONObject(i);
				 GjDwzhmxResult temp = new GjDwzhmxResult();
				 temp.setDqye(new BigDecimal(tempobj.getString("bal")));
				 temp.setFse(new BigDecimal(tempobj.getString("basenum")));
				 temp.setJymmc(tempobj.getString("trantype"));
				 temp.setJyrq(tempobj.getString("trandate"));
				 temp.setJyzy(tempobj.getString("remark"));
				 temp.setZhlxm(indiacctypems);
				 GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi800020100057400.result", temp);			
				 result1.add(GjDwzhmxResult);
			 }
			 modelMap.put("recode", "000000");
			 modelMap.put("msg", "成功");
			 modelMap.put("result", result1);
		 }
		
		return modelMap;
	}
}
