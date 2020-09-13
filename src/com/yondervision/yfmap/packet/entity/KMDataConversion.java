/**
 * 
 */
package com.yondervision.yfmap.packet.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.yondervision.yfmap.packet.IDataConversion;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/**
 * @author LinXiaolong
 * 
 */
public class KMDataConversion implements IDataConversion {

	/** 模板标签头 */
	private static final String TAG_BEGIN = "${";
	/** 模板标签尾 */
	private static final String TAG_END = "}";
	/** 模板标签内参数的分隔符 */
	private static final String REGEX = ",";
	/** 模板标签之间分隔符 */
	private static final String TAG_SPLITER = "\\|";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.packet.IDataConversion#getData(java.util.Map,
	 * java.lang.String)
	 */
	public String getData(Map<String, Object> mapData, String tempStr) {
		if (mapData == null || tempStr == null) {
			return null;
		}

		int beginIndex = tempStr.indexOf(KMDataConversion.TAG_BEGIN);
		int endIndex = 0;
		if (beginIndex < 0) {
			return tempStr;
		}

		String[] pTempStr = tempStr.split(KMDataConversion.TAG_SPLITER);

		beginIndex = pTempStr[0].indexOf(KMDataConversion.TAG_BEGIN);
		endIndex = pTempStr[0].indexOf(KMDataConversion.TAG_END);
		//System.out.println("pTempStr[0]" + pTempStr[0]);
		String tempParam = pTempStr[0].substring(beginIndex
				+ KMDataConversion.TAG_BEGIN.length(), endIndex);
		String[] pTempParam = tempParam.split(KMDataConversion.REGEX); // 模板中的参数
		Object[] methodParams = new Object[pTempParam.length - 1]; // 调用数据格式转换需要的参数
		String methodName = pTempParam[0].trim(); // 数据格式转换方法名
		String keyName = pTempParam[1].trim(); // 数据放入Map中的key

		String value = this.get(mapData, keyName); // 源值
		String newValue = value; // 转换后的值

		methodParams[0] = value;
		for (int j = 2; j < pTempParam.length; j++) {
			methodParams[j - 1] = pTempParam[j];
		}

		try {
			//System.out.println(this.getClass().getSimpleName()+"."+methodName+"("+methodParams+")");
			newValue = (String) Tools.invokeMethod(this, methodName,
					methodParams); // 值转换
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.packet.IDataConversion#putData(java.util.Map,
	 * java.lang.String, java.lang.String)
	 */
	public void putData(Map<String, Object> mapData, String tempStr,
			String value) {
		if (mapData == null || tempStr == null || value == null) {
			return;
		}

		int beginIndex = tempStr.indexOf(KMDataConversion.TAG_BEGIN);
		int endIndex = 0;
		if (beginIndex < 0) {
			return;
		}
		//System.out.println("tempStr=" + tempStr);
		String[] pTempStr = tempStr.split(KMDataConversion.TAG_SPLITER);
		for (int i = 0; i < pTempStr.length; i++) {
			String newValue = value; // 转换后的值

			beginIndex = pTempStr[i].indexOf(KMDataConversion.TAG_BEGIN);
			endIndex = pTempStr[i].indexOf(KMDataConversion.TAG_END);
			String tempParam = pTempStr[i].substring(beginIndex
					+ KMDataConversion.TAG_BEGIN.length(), endIndex);
			String[] pTempParam = tempParam.split(KMDataConversion.REGEX); // 模板中的参数
			Object[] methodParams = new Object[pTempParam.length - 1]; // 调用数据格式转换需要的参数
			String methodName = pTempParam[0].trim(); // 数据格式转换方法名
			String keyName = pTempParam[1].trim(); // 数据放入Map中的key

			methodParams[0] = value;
			for (int j = 2; j < pTempParam.length; j++) {
				methodParams[j - 1] = pTempParam[j];
			}

			try {
				//System.out.println("methodName:"+methodName+"("+methodParams+")");
				newValue = (String) Tools.invokeMethod(this, methodName,
						methodParams); // 值转换
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			this.put(mapData, keyName, newValue);
		}
	}

	/**
	 * 取当前值
	 * @param value 源值
	 * @return 转换后的值
	 */
	public String getValue(String value) {
		return value;
	}
	
	/**
	 * 取得特定格式的时间串
	 * @param value 系统格式的当前时间
	 * @param formatStr 目标时间格式串
	 * @return 目标时间格式的时间串
	 */
	public String getDate(String value, String formatStr) {
		String newValue = null;
		try {
			Date sysDate = SimpleDateFormat.getDateTimeInstance().parse(value);
			SimpleDateFormat df = new SimpleDateFormat(formatStr);
			newValue = df.format(sysDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newValue;
	}
	
	/**
	 * 根据联行号取得银行名称
	 * @param value 联行号
	 * @return 银行名称
	 */
	public String getPayBankByBankNo(String value) {
		return "中国工商银行";
	}

	private String get(Map<String, Object> mapData, String keyName) {
		String value = null;

		int index = keyName.indexOf('.');
		if (index < 0) {
			value = (String) mapData.get(keyName);
		} else {			
			String[] pKeyName = keyName.split("\\.");
			Object oValue = mapData.get(pKeyName[0]);
			if (oValue == null) {
				return null;
			}
			value = JavaBeanUtil.getter(oValue, pKeyName[1]);
		}
		return value;
	}

	private void put(Map<String, Object> mapData, String keyName, String value) {
		int index = keyName.indexOf('.');
		if (index < 0) {
			mapData.put(keyName, value);
		} else {			
			String[] pKeyName = keyName.split("\\.");
			Object oValue = mapData.get(pKeyName[0]);
			if (oValue == null) {
				String className = PropertiesReader.getProperty(
						"config/JavaBeanClass.properties", pKeyName[0]);
				oValue = JavaBeanUtil.creatJavaBean(className);
			}
			JavaBeanUtil.setter(oValue, pKeyName[1], value);

			mapData.put(pKeyName[0], oValue);
		}

	}

}
