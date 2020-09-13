package com.yondervision.yfmap.handle;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApiCommonForm;

public interface CtrlHandleInter {
	public ModelMap action(AppApiCommonForm form,ModelMap modelMap) throws CenterRuntimeException,Exception;
	
}
