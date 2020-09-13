/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

/**
 * @author LinXiaolong
 *
 */
public interface IDataConversion {
	
	/**
	 * 根据模板将数据放入MAP中
	 * @param mapData 数据存放
	 * @param tempStr 数据模板，支持存放多个位置
	 * @param value 数据
	 */
	public void putData(Map<String, Object> mapData, String tempStr, String value);
	
	/**
	 * 根据数据模板取得数据
	 * @param mapData 数据来源
	 * @param tempStr 数据模板，只取第一个取值方式
	 * @return 取得的数据
	 */
	public String getData(Map<String, Object> mapData, String tempStr);
	
}
