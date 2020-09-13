package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.HTTPSendMessage;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80103Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.ningbo.GjDwndjsResult;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80107_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi80103Form form = (AppApi80103Form)form1;
		log.debug("form:"+form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "HTTP_URL"+form.getCenterId()).trim()+"/UnitloginAct";
		Map<String,String> params = new HashMap<String,String>();
		params.put("tranCode", "142509");
		params.put("task", "other");
		params.put("unitaccnum", form.getDwzh());
		String indiacctype = "1";
		if(form.getZhlx().equals("01")){
			indiacctype = "1";
		}else if(form.getZhlx().equals("02")){
			indiacctype = "3";
		}else if(form.getZhlx().equals("03")){
			indiacctype = "2";
		}
		params.put("indiacctype", indiacctype);
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		 JSONObject obj = JSONObject.fromObject(result);
		 if(!obj.getString("success").equals("1")){
			 modelMap.put("recode", "999999");
			 modelMap.put("msg", obj.getString("msg"));
			 modelMap.put("result", "");
		 }else{
			 List<List<TitleInfoBean>> result1 = new ArrayList();
				List<TitleInfoBean> GjDwzhmxResult = null;
//				for(int i=0;i<list.size();i++){
				GjDwndjsResult bean = new GjDwndjsResult();
				bean.setDwmc(obj.getString("unitaccname"));
				bean.setDwdm(obj.getString("unitaccnum"));
				bean.setJzrq(obj.getString("enddate").replace("-", ""));
				bean.setSnjs(new BigDecimal(obj.getString("keepintaccu")));
				bean.setSnll(new BigDecimal(obj.getString("keeprate")));
				bean.setSnlx(new BigDecimal(obj.getString("keepbal")));
				bean.setBnjs(new BigDecimal(obj.getString("increintaccu")));
				bean.setBnll(new BigDecimal(obj.getString("increrate")));
				bean.setBnlx(new BigDecimal(obj.getString("increbal")));
				bean.setJzye(new BigDecimal(obj.getString("bal")));
					GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi800030100057400.result", bean);			
					result1.add(GjDwzhmxResult);
//				}
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result1);
		 }
		
		return modelMap;
	}
}
