/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * 读取批量文件
 * 
 * @author LinXiaolong
 * 
 */
public interface IBatchFileReader {

	/**
	 * 取得批量文件头
	 * 
	 * @return 批量文件头
	 * @throws YQZLException
	 */
	public String getBatchHead() throws CenterRuntimeException;

	/**
	 * 判断是否还有明细数据
	 * 
	 * @return 还有明细数据返回<code>true</code>，没有明细数据返回<code>false</code>
	 * @throws YQZLException
	 */
	public boolean hasNextDetail() throws CenterRuntimeException;

	/**
	 * 读取下一条明细数据
	 * 
	 * @return 下一条明细数据，无下条明细数据返回null
	 * @throws YQZLException
	 */
	public String readNextDetail() throws CenterRuntimeException;

	/**
	 * 读取下一条明细数据并解析，调用readNextDetail()后解析数据
	 * 
	 * @param detailTemp
	 *            明细数据模板
	 * @return 读取出的明细数据，无下条明细数据返回null
	 * @throws YQZLException
	 */
	public Map<String, Object> readNextDetailData(String detailTemp)
			throws CenterRuntimeException;

	/**
	 * 设置批量文件名
	 * 
	 * @param fileName
	 *            批量文件名（全路径）
	 * @throws YQZLException
	 *             重复设置抛出异常
	 */
	public void setBatchFileName(String fileName) throws CenterRuntimeException;

	/**
	 * 完成读取
	 */
	public void close();
}
