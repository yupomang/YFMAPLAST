/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.messageCtr
 * @ClassName: MessageDataConversion.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: ��С��
 * @Create Date��2011-6-17 
 * @Modified By��
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
 ***********************************************************/
package com.yondervision.yfmap.messagectrl;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * 报文封装和解析时的数据获取方法都在本类中，本类方法要求统一的格式
 * 
 * @MethodDefined public static String methodName( HashMap<String, String> map,
 *                String valueName, int length, int filledWay, char filler )
 * @PackageName: com.yondervison.messageCtr
 * @FileName: MessageDataConversion.java
 * @author 林小龙
 */
public class MessageDataConversion {

public static final char FILLER = ' ';
	
	public static final String dateFormatXH = "yyMMdd";

	public static final String dateFormat = "yyyy-MM-dd";

	public static final String timeFormat = "HH:mm:ss";

	public static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	
	private static final String CLASS_NAME = "com.yondervision.yfmap.messagectrl.MessageDataConversion";

	/**
	 * <pre>
	 *  从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成yyyyMMdd格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getDate8(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = "yyyyMMdd";
		String strSrcDate = map.get(valueName);
		String sourceFormat = null;
		if (strSrcDate.trim().length()==23) {
			sourceFormat = MessageDataConversion.dateTimeFormat;
		}
		if (strSrcDate.trim().length()==10) {
			sourceFormat = MessageDataConversion.dateFormat;
		}
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}
	
	/**
	 * <pre>
	 *  从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成yyyyMMddhhmmssSSS格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getDate17(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = "yyyyMMddhhmmssSSS";
		String strSrcDate = map.get(valueName);
		String sourceFormat = null;
		if (strSrcDate.trim().length()==23) {
			sourceFormat = MessageDataConversion.dateTimeFormat;
		}
		if (strSrcDate.trim().length()==10) {
			sourceFormat = MessageDataConversion.dateFormat;
		}
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}

	
	/**
	 * <pre>
	 * 从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成yyyy-MM-dd格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getDate10_1(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String targetFormat = "yyyy-MM-dd";
		String strSrcDate = map.get(valueName);
		String sourceFormat = null;
		if (strSrcDate.trim().length()==23) {
			sourceFormat = MessageDataConversion.dateTimeFormat;
		}
		if (strSrcDate.trim().length()==10) {
			sourceFormat = MessageDataConversion.dateFormat;
		}
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}

	/**
	 * <pre>
	 * 从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成yyyy/MM/dd格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getDate10_2(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String targetFormat = "yyyy/MM/dd";
		String strSrcDate = map.get(valueName);
		String sourceFormat = MessageDataConversion.dateTimeFormat;
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}

	/**
	 * <pre>
	 * 从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成HHmmss格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getTime6(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = "HHmmss";
		String strSrcDate = map.get(valueName);
		String sourceFormat = MessageDataConversion.dateTimeFormat;
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}

	/**
	 * <pre>
	 * 从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成HHmmssSSS格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getTime9(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = "HHmmssSSS";
		String strSrcDate = map.get(valueName);
		String sourceFormat = MessageDataConversion.dateTimeFormat;
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}
	
	/**
	 * <pre>
	 * 从HashMap中取值将yyyy-MM-dd HH:mm:ss.SSS格式转成HHmmssSSS000格式的日期
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getTime12(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = "HHmmssSSS000";
		String strSrcDate = map.get(valueName);
		String sourceFormat = MessageDataConversion.dateTimeFormat;
		String dateString = yqFormate(strSrcDate, sourceFormat, targetFormat);
		dateString = addCharacter(dateString, length, filledWay, filler);

		return dateString;
	}

	/**
	 * <pre>
	 * 从HashMap中取值将以元为单位保留两位小数的金额转成以元为单位保留两位小数的金额，并参数校验长度
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getAmtYuan(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strAmt = map.get(valueName);
		strAmt = String.format("%.2f", Double.valueOf(strAmt));
		strAmt = addCharacter(strAmt, length, filledWay, filler);
		return strAmt;
	}

	/**
	 * <pre>
	 * 从HashMap中取值将以元为单位保留两位小数的金额转成以分为单位不带小数点的金额，并参数校验长度
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getAmtFen(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String strAmt = map.get(valueName);
		strAmt = String.format("%.2f", Double.valueOf(strAmt));
		strAmt = strAmt.replace(".", "");
		strAmt = addCharacter(strAmt, length, filledWay, filler);
		return strAmt;
	}

	/**
	 * <pre>
	 * 从HashMap中取值并参数校验长度
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getVariable(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strValue = map.get(valueName);
		strValue = addCharacter(strValue, length, filledWay, filler);
		return strValue;
	}

	/**
	 * <pre>
	 * 参数校验长度原样返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String getFixedValue(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strValue = addCharacter(valueName, length, filledWay, filler);
		return strValue;
	}

	/**
	 * <pre>
	 * 将yyyyMMdd格式的值转成yyyy-MM-dd格式的日期返回 
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setDate8(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = MessageDataConversion.dateFormat;
		String sourceFormat = "yyyyMMdd";
		String strSrcDate = map.get(valueName);
		// 判断字段是否超长
		if (isTooLong(strSrcDate, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strSrcDate+"报文转换字段超长"+length+"。");
		// 日期格式转换
		String date = MessageDataConversion.yqFormate(strSrcDate, sourceFormat, targetFormat);
		return date;
	}

	/**
	 * <pre>
	 * 将yyyy-MM-dd格式的值转成yyyy-MM-dd格式的日期返回 
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setDate10_1(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String targetFormat = MessageDataConversion.dateFormat;
		String sourceFormat = "yyyy-MM-dd";
		String strSrcDate = map.get(valueName);
		// 判断字段是否超长
		if (isTooLong(strSrcDate, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strSrcDate+"报文转换字段超长"+length+"。");
		// 日期格式转换
		String date = MessageDataConversion.yqFormate(strSrcDate, sourceFormat, targetFormat);
		return date;
	}

	/**
	 * <pre>
	 * 将yyyy/MM/dd格式的值转成yyyy-MM-dd格式的日期返回 
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setDate10_2(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String targetFormat = MessageDataConversion.dateFormat;
		String sourceFormat = "yyyy/MM/dd";
		String strSrcDate = map.get(valueName);
		// 判断字段是否超长
		if (isTooLong(strSrcDate, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strSrcDate+"报文转换字段超长"+length+"。");
		// 日期格式转换
		String date = MessageDataConversion.yqFormate(strSrcDate, sourceFormat, targetFormat);
		return date;
	}

	/**
	 * <pre>
	 * 将HHmmss格式的值转成HH:mm:ss格式的时间返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setTime6(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = MessageDataConversion.timeFormat;
		String sourceFormat = "HHmmss";
		String strSrcDate = map.get(valueName);
		// 判断字段是否超长
		if (isTooLong(strSrcDate, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strSrcDate+"报文转换字段超长"+length+"。");
		// 日期格式转换
		String date = MessageDataConversion.yqFormate(strSrcDate, sourceFormat, targetFormat);
		return date;
	}

	/**
	 * <pre>
	 * 将HHmmssSSS格式的值转成HH:mm:ss格式的时间返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setTime9(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String targetFormat = MessageDataConversion.timeFormat;
		String sourceFormat = "HHmmssSSS";
		String strSrcDate = map.get(valueName);
		// 判断字段是否超长
		if (isTooLong(strSrcDate, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strSrcDate+"报文转换字段超长"+length+"。");
		// 日期个数转换
		String date = MessageDataConversion.yqFormate(strSrcDate, sourceFormat, targetFormat);
		return date;
	}

	/**
	 * <pre>
	 * 将以元为单位保留两位小数的金额转成以元为单位保留两位小数的金额返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setAmeYuan(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strAmt = map.get(valueName);
		//判断字段是否超长
		if (isTooLong(strAmt, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strAmt+"报文转换字段超长"+length+"。");
		//格式化金额
		strAmt = String.format("%.2f", Double.valueOf(strAmt));
		return strAmt;
	}

	/**
	 * <pre>
	 * 将以分为单位保留两位小数的金额转成以元为单位保留两位小数的金额返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setAmtFen(HashMap<String, String> map, String valueName, int length, int filledWay, char filler)
			throws CenterRuntimeException {
		String strAmt = map.get(valueName);
		//判断字段是否超长
		if (isTooLong(strAmt, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strAmt+"报文转换字段超长"+length+"。");
		//格式化金额
		strAmt = String.format( "%.2f", (Double.valueOf(strAmt)/100) );
		return strAmt;
	}

	/**
	 * <pre>
	 * 把值返回
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setVariable(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strValue = map.get(valueName);
		strValue = strValue==null||"".equals(strValue) ? "" : strValue;
		//判断字段是否超长
		if (isTooLong(strValue, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strValue+"报文转换字段超长"+length+"。");
		return strValue;
	}

	/**
	 * <pre>
	 * 判断模板中数据与返回数据是否一致，不一致报错
	 * </pre>
	 * 
	 * @param map
	 *            数据来源HashMap
	 * @param valueName
	 *            数据在HashMap中的key
	 * @param length
	 *            目标数据长度
	 * @param filledWay
	 *            补齐方——0=不补齐,1=左补齐,2=右补齐
	 * @param filler
	 *            填充符
	 * @return 处理后的值
	 */
	public static String setFixedValue(HashMap<String, String> map, String valueName, int length, int filledWay,
			char filler) throws CenterRuntimeException {
		String strVlaue = map.get(valueName);
		//判断字段是否超长
		if (isTooLong(strVlaue, length))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+strVlaue+"报文转换字段超长"+length+"。");
		if(strVlaue.trim().equals(valueName.trim()))
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：模板中数据 "+strVlaue+" 与返回数据"+valueName+"是否一致。");
		return null;
	}
	
	/**
	 * <pre>通过方法名调用本类中的方法</pre>
	 * @param methodname 方法名
	 * @param objects 参数：HashMap, String, int, int, char
	 * @return 方法执行结果
	 * @throws Exception
	 */
	public static Object invokeStaticMethod(String methodname,
			Object... objects) throws Exception {
		Class<?>[] objClass = {HashMap.class, String.class, int.class, int.class, char.class};
		Class<?> proxy = Class.forName(MessageDataConversion.CLASS_NAME);
		Method method = proxy.getDeclaredMethod(methodname, objClass);
		return method.invoke(proxy, objects);
	}
	
	/**
	 * <pre>判断字符串的字节数是否超过限制</pre>
	 * @param dateString 字符串
	 * @param length 最大长度
	 * @return 判断结果：true=超长;false=不超长
	 */
	private static boolean isTooLong(String dateString, int length){
		dateString = dateString==null ? "" : dateString;
		int lessLength = length - dateString.getBytes().length;
		// 判断字段是否超长
		if (lessLength < 0)
			return true;
		return false;
	}
	
	/**
	 * <pre>
	 * 将源时间串转换成目标格式的时间串
	 * </pre>
	 * 
	 * @param strSrcDate
	 *            源时间串
	 * @param sourceFormat
	 *            源时间串的格式
	 * @param targetFormat
	 *            目标时间串的格式
	 * @return 目标时间串
	 */
	private static String yqFormate(String strSrcDate, String sourceFormat, String targetFormat) throws CenterRuntimeException {
		String dateString = null;
		SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
		Date currentTime = null;
		try {
			currentTime = formatter.parse(strSrcDate);
		} catch (ParseException e) {
			// 时间串与给定格式不符合时取当前时间
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：时间串与给定格式不符合时取当前时间。");
		}
		SimpleDateFormat formatter1 = new SimpleDateFormat(targetFormat);
		dateString = formatter1.format(currentTime);

		return dateString;
	}
	
	/**
	 * <pre>
	 * 为给定字符串补齐位数
	 * </pre>
	 * 
	 * @param dateString
	 *            源字符串
	 * @param length
	 *            目标长度
	 * @param filledWay
	 *            补齐方式0=不补齐；1=左补齐；2=右补齐
	 * @param filler
	 *            填充符 ASCII码中的字符
	 * @return 补齐后的字符串
	 * @throws YQZLException
	 */
	private static String addCharacter(String dateString, int length, int filledWay, char filler) throws CenterRuntimeException {
		dateString = dateString==null ? "" : dateString;
		int lessLength = length - dateString.getBytes().length;
		// 判断字段是否超长
		if (lessLength < 0)
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：参数"+dateString+"报文转换字段超长"+length+"。");
		// 判断填充符号是否超长
		if (filler>128)
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：填充符号超长。");
		StringBuffer sFiller = new StringBuffer();
		switch (filledWay) {
		case 0: // 不补位
			sFiller.append(dateString);
			break;

		case 1: // 左补位
			for (int i = 0; i < lessLength; i++) {
				sFiller.append(filler);
			}
			sFiller.append(dateString);
			break;

		case 2: // 右补位
			sFiller.append(dateString);
			for (int i = 0; i < lessLength; i++) {
				sFiller.append(filler);
			}
			break;

		default:
			sFiller.append(dateString);
			break;
		}

		return sFiller.toString();
	}

	
	
	public static void main(String[] args) throws ParseException {

		// SimpleDateFormat formatter = new
		// SimpleDateFormat(MessageDataConversion.dateTimeFormat);
		// Date currentTime = formatter.parse("2011-06-27 11:07:27.132");
		// formatter = new SimpleDateFormat(MessageDataConversion.dateFormat);
		// String dateString = formatter.format(currentTime);
		// System.out.println(dateString);
		/*try {
			System.out.println(addCharacter("111", 10, 2, '0'));
		} catch (YQZLException e) {
			// TODO Auto-generated catch block
			String[] strs = e.getMeg();
			for (int i = 0; i < strs.length; i++) {
				System.out.println(strs[i]);
			}
			e.setPaymentTN("111");
			e.printLog();
			e.printStackTrace();
		}*/
		Object [] objects = null;
		try {
			MessageDataConversion.invokeStaticMethod("setFixedValue", objects);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
