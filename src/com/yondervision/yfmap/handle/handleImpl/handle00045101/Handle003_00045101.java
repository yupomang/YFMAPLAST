package com.yondervision.yfmap.handle.handleImpl.handle00045101;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.handleImpl.handle00075500.Handle003_00075500;

public class Handle003_00045101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		Handle003_00075500 handle = new Handle003_00075500();
		handle.action(form1, modelMap);
		return modelMap;
	}

}
