package com.yondervision.yfmap.service;

import java.util.List;
import java.util.Map;

import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80102Form;
import com.yondervision.yfmap.form.AppApi80103Form;


public interface AppApi801Service {
	
	public Map appApi00801Select(AppApi40102Form form) throws Exception;
	
	public List appApi00802Select(AppApi80102Form form) throws Exception;

	public List appApi00803Select(AppApi80103Form form)throws Exception;
	
	public List appApi00804Select(AppApi40102Form form)throws Exception;
}
