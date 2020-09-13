package com.yondervision.yfmap.handle.handleImpl.handle00057400;

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
import com.yondervision.yfmap.dto.GjGrmx;
import com.yondervision.yfmap.dto.GjGrmxExample;
import com.yondervision.yfmap.dto.XdSpxx;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80207_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi40102Form form = (AppApi40102Form) form1;
		log.debug("form:" + form);
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,"HTTP_URL" + form.getCenterId()).trim()+"/GJJQuery";
		Map<String, String> params = new HashMap<String, String>();
		params.put("tranCode", "142815");
		params.put("task", "other");
		params.put("spflag", "spflag");
		params.put("certinum", form.getIdcardNumber());
		HTTPSendMessage sendmess = new HTTPSendMessage();
		String result = sendmess.send(request, response, url, params);
		JSONObject obj = JSONObject.fromObject(result);
		if (!obj.getString("success").equals("1")) {
			modelMap.put("recode", "999999");
			modelMap.put("msg", obj.getString("msg"));
			 modelMap.put("result", "");
		} else {
			XdSpxx bean = new XdSpxx();
			bean.setJkr(obj.getString("loaneename"));
			bean.setWhrq(obj.getString("appdate_wb"));
			bean.setShqk(obj.getString("approvedate_wb"));
			bean.setFwdz(obj.getString("bankname"));
			bean.setZjhm(form.getIdcardNumber());
			List<List<TitleInfoBean>> result1 = new ArrayList();
			List<TitleInfoBean> GjDwzhmxResult = null;
			// for(int i=0;i<list.size();i++){
				GjDwzhmxResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi8020300057400.result", bean);
				result1.add(GjDwzhmxResult);
			// }
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("result", result1);
		}
		return modelMap;
	}
}
