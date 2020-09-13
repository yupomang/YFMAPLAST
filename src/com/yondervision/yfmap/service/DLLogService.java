package com.yondervision.yfmap.service;

import com.yondervision.yfmap.result.dalian.DalianLogResult;
import com.yondervision.yfmap.util.LogPara;



public interface DLLogService {	
	
	public void updateMi125Service(LogPara para,String id,DalianLogResult result);
	
}
