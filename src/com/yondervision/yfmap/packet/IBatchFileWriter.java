/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * 写入批量文件
 * 
 * @author LinXiaolong
 * 
 */
public interface IBatchFileWriter {

	/**
	 * 将一笔明细数据写入批量文件，封装数据后调用writeDetail(String detailValue)
	 * 
	 * @param detailTemp
	 *            明细记录模板
	 * @param detailData
	 *            明细数据
	 * @return 写入文件的明细记录
	 * @throws YQZLException
	 */
	public String writeDetail(String detailTemp, Map<String, Object> detailData)
			throws CenterRuntimeException;

	/**
	 * 将一条明细记录写入批量文件
	 * 
	 * @param detailValue
	 *            明细记录
	 * @throws YQZLException
	 */
	public void writeDetail(String detailValue) throws CenterRuntimeException;

	/**
	 * 写入文件头，封装数据后调用writeHead(String headValue)
	 * 
	 * @param headTemp
	 *            文件头模板
	 * @param headData
	 *            文件头数据
	 * @return 文件头
	 * @throws YQZLException
	 */
	public String writeHead(String headTemp, Map<String, Object> headData)
			throws CenterRuntimeException;

	/**
	 * 写入文件头
	 * 
	 * @param headValue
	 *            文件头
	 * @throws YQZLException
	 */
	public void writeHead(String headValue) throws CenterRuntimeException;
	
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
	 * 写入完成
	 */
	public void close();
}
