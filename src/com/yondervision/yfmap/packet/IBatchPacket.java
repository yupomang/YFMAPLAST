/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * 批量报文处理类
 * 
 * @author LinXiaolong
 * 
 */
public interface IBatchPacket {

	/** MAP中存放批量数据文件名的key */
	public static final String DATA_FILE_NAME_KEY = "__data_file_name__";
	/** MAP中存放批量目标文件的key */
	public static final String MSG_FILE_NAME_KEY = "__msg_file_name__";
	/** 存放批量明细最大笔数的key */
	public static final String MAXIMUM_ITEMS_KEY = "__maximum_items__";
	/** 多文件名之间的分隔符 */
	public static final String FILE_NAME_SPLATER = ",";

	/**
	 * 封装XML格式的批量报文，如果批量数据条数超过<code>MAXIMUM_ITEMS_KEY</code>则拆分封装为多个报文
	 * 
	 * @param msgTempFilePath
	 *            XML报文模板路径
	 * @param msgTempFileName
	 *            XML报文模板名称
	 * @param fileTempFilePath
	 *            数据文件模板路径
	 * @param fileTempFileName
	 *            数据文件模板名称
	 * @param msgData
	 *            封装报文所需数据（其中包含<code>DATA_FILE_NAME_KEY</code>和
	 *            <code>MAXIMUM_ITEMS_KEY</code>）
	 * @return 封装好的批量报文文件名，多个文件名用<code>FILE_NAME_SPLATER</code>分隔
	 * @throws YQZLException
	 */
	public String encXMLBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, Map<String, Object> msgData)
			throws CenterRuntimeException;

	/**
	 * 封装XML加批量文件的批量报文，如果批量数据条数超过<code>MAXIMUM_ITEMS_KEY</code>则拆分封装为多个报文
	 * 
	 * @param msgTempFilePath
	 *            XML报文模板路径
	 * @param msgTempFileName
	 *            XML报文模板名称
	 * @param fileTempFilePath
	 *            批量文件模板路径
	 * @param fileTempFileName
	 *            批量文件模板名称
	 * @param mapData
	 *            封装报文所需数据（其中包含<code>DATA_FILE_NAME_KEY</code>和
	 *            <code>MAXIMUM_ITEMS_KEY</code>）
	 * @return 封装好的批量报文文件名，多个文件名用<code>FILE_NAME_SPLATER</code>分隔；批量文件名存放到
	 *         <code>mapData</code>中，key为<code>MSG_FILE_NAME_KEY</code>,多个文件名用
	 *         <code>FILE_NAME_SPLATER</code>分隔
	 * @throws YQZLException
	 */
	public String encXMLAndFileBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, Map<String, Object> mapData)
			throws CenterRuntimeException;

	/**
	 * 解析XML格式批量报文
	 * 
	 * @param msgTempFilePath
	 *            XML报文模板路径
	 * @param msgTempFileName
	 *            XML报文模板名称
	 * @param fileTempFilePath
	 *            数据文件模板路径
	 * @param fileTempFileName
	 *            数据文件模板名称
	 * @param dataFilePatch
	 *            数据文件路径
	 * @param msgData
	 *            XML报文
	 * @return 解析出的数据，其中包括<code>DATA_FILE_NAME_KEY</code>和报文中的其它信息
	 * @throws YQZLException
	 */
	public Map<String, Object> anaXMLBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, String dataFilePatch, String msgData)
			throws CenterRuntimeException;

	/**
	 * 解析XML加批量文件的报文
	 * 
	 * @param msgTempFilePath
	 *            XML报文模板路径
	 * @param msgTempFileName
	 *            XML报文模板名称
	 * @param fileTempFilePath
	 *            批量文件模板路径
	 * @param fileTempFileName
	 *            批量文件模板名称
	 * @param msgData
	 *            XML报文
	 * @param msgFileName
	 *            批量文件名（全路径）
	 * @return 解析出的数据，其中包括<code>DATA_FILE_NAME_KEY</code>和报文中的其它信息
	 * @throws YQZLException
	 */
	public Map<String, Object> anaXMLFileBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, String msgData, String msgFileName)
			throws CenterRuntimeException;
}
