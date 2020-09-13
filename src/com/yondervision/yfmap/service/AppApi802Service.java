package com.yondervision.yfmap.service;

import java.util.List;
import java.util.Map;

import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80202Form;
import com.yondervision.yfmap.form.AppApiCommonForm;

public interface AppApi802Service {
	public Map appApi00801Select(AppApiCommonForm form) throws Exception;
	
	public List appApi00802Select(AppApi80202Form form) throws Exception;
	
	public List appApi00803Select(AppApiCommonForm form) throws Exception;
	
	public List appApi00804Select(AppApi40102Form form) throws Exception;
}
