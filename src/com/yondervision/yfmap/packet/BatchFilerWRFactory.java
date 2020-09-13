/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.SpringContextUtil;

/**
 * 创建IBatchFileReader和IBatchFileWriter实例
 * 
 * @author LinXiaolong
 * 
 */
public class BatchFilerWRFactory {

	/**
	 * 创建批量文件读取类对象，得到对象后无需设置文件名，可直接读取内容，读取完全部内容后调用close方法
	 * 
	 * @param fileReaderName
	 *            文件读取类名称
	 * @param batchFileName
	 *            批量文件名称
	 * @return 文件读取对象
	 * @throws YQZLException
	 */
	public static IBatchFileReader creatIBatchFileReader(String fileReaderName,
			String batchFileName) throws CenterRuntimeException {
		IBatchFileReader bfr = null;

		bfr = (IBatchFileReader) SpringContextUtil.getBean(fileReaderName);
		bfr.setBatchFileName(batchFileName);

		return bfr;
	}

	/**
	 * 创建批量文件写入类对象，得到对象后只需要写入批量明细数据，写入完成后调用close方法
	 * 
	 * @param fileWriterName
	 *            批量文件写入类名称
	 * @param batchFileName
	 *            批量文件名称
	 * @param headValue
	 *            批量文件头记录
	 * @return 批量文件写入对象
	 * @throws YQZLException
	 */
	public static IBatchFileWriter creatIBatchFileWriter(String fileWriterName,
			String batchFileName, String headValue) throws CenterRuntimeException {
		IBatchFileWriter bfw = null;

		bfw = (IBatchFileWriter) SpringContextUtil.getBean(fileWriterName);
		bfw.setBatchFileName(batchFileName);
		bfw.writeHead(headValue);

		return bfw;
	}

	/**
	 * 创建批量文件写入类对象，得到对象后只需要写入批量明细数据，写入完成后调用close方法
	 * 
	 * @param fileWriterName
	 *            批量文件写入类名称
	 * @param batchFileName
	 *            批量文件名称
	 * @param headTemp
	 *            批量文件头记录模板
	 * @param headData
	 *            批量文件头数据
	 * @return 批量文件写入对象
	 * @throws YQZLException
	 */
	public static IBatchFileWriter creatIBatchFileWriter(String fileWriterName,
			String batchFileName, String headTemp, Map<String, Object> headData)
			throws CenterRuntimeException {
		IBatchFileWriter bfw = null;

		bfw = (IBatchFileWriter) SpringContextUtil.getBean(fileWriterName);
		bfw.setBatchFileName(batchFileName);
		bfw.writeHead(headTemp, headData);

		return bfw;
	}

}
