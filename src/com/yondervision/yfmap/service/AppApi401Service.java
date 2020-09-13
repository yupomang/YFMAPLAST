package com.yondervision.yfmap.service;

import java.io.OutputStream;

import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.dto.CMi401;


public interface AppApi401Service {

	//定制单笔
	public String acction(CMi401 form, ModelMap modelMap, String centerid, String sendseqno) throws Exception;
	
	//定制批量
	public String acction1(CMi401 form, ModelMap modelMap, String centerid, String seqno) throws Exception;
    
	//模板单笔
	public String acction2(CMi401 form, ModelMap modelMap, String centerid, String sendseqno) throws Exception;
    
	//模板批量
	public String acction3(CMi401 form, ModelMap modelMap, String centerid, String sendseqno) throws Exception;

	
	

}
