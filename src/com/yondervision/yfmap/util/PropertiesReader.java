/**
 * @Title: PropertiesReader.java
 * @Package com.yondervision.common.util
 * @Description: 读取配置文件
 * Copyright: Copyright (c) 2009
 * Company: pkpm
 * @author 林小龙
 * @date 2012-4-18 下午03:04:01
 *@version 1.0
 */
package com.yondervision.yfmap.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @ClassName: PropertiesReader
 * @Description: 读取配置文件
 * @author 林小龙
 * @date 2012-4-18 下午03:04:01
 */
public class PropertiesReader {
	private static Logger logger = Logger.getLogger(PropertiesReader.class
			.getName());
	private static Map<String, Properties> mapProperties;
	static {
		mapProperties = new HashMap<String, Properties>();
	}

	private static Properties getPropertiesByName(String propertiesName) {
		Properties prpoperties = mapProperties.get(propertiesName);
		if (prpoperties != null) {
			return prpoperties;
		}
		prpoperties = new Properties();
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}

		InputStream is = classLoader.getResourceAsStream(propertiesName);
		try {
			prpoperties.load(is);
		} catch (Exception e) {
			logger.error("取配置文件[" + propertiesName + "]失败", e);
		}
		mapProperties.put(propertiesName, prpoperties);
		return prpoperties;
	}

	/**
	 * @Title: getProperty
	 * @Description:通过配置文件名和配置属性取得配置值
	 * @param @param fileName 配置文件名
	 * @param @param propertyName 配置属性
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 * @date 2012-4-18 下午03:22:58
	 */
	public static String getProperty(String fileName, String propertyName) {
		Properties prpoperties = getPropertiesByName(fileName);
		return prpoperties.getProperty(propertyName);
	}

}
