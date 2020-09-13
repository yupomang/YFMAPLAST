package com.yondervision.yfmap.service;

import java.util.List;

import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00906Result;


public interface AppApi009Service {
	
	/**
	 * @deprecated 删除历史同步数据
	 * @param form
	 * @throws Exception
	 */
	public void appApi00901Delete(AppApi00101Form form) throws Exception;
	
	/**
	 * @deprecated 同步数据查询
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List appApi00902Select(AppApi00101Form form) throws Exception;

	/**
	 * @deprecated 同步数据入库
	 * @param form
	 * @throws Exception
	 */
	public void appApi00903Insert(AppApi00101Form form, List<DaLianAppApi00906Result> list) throws Exception;
}
