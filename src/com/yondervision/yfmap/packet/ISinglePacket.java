/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.Map;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * 单笔报文处理类
 * @author LinXiaolong
 *
 */
public interface ISinglePacket {

	/**
	 * 按指定路径下的指定报文模板将Map中的数据封为指定报文
	 * 
	 * @param msgTempFilePath
	 *            报文模板路径
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            封装报文所需数据
	 * @return String 封装后的报文
	 */
	public String encapsulatedPacket(String msgTempFilePath,
			String msgTempFileName, Map<String, Object> msgData) throws CenterRuntimeException;
	
	/**
	 * 按指定路径下的指定报文模板将给定报文的数据解析到Map中
	 * 
	 * @param msgTempFilePath
	 *            报文模板路径
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            待解析报文
	 * @return Map<String, String> 解析出的数据
	 */
	public Map<String, Object> analysisPacket(String msgTempFilePath,
			String msgTempFileName, String msgData) throws CenterRuntimeException;
}
