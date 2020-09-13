/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.messageCtr
 * @ClassName: AbsMessageCtr.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: 林小龙
 * @Create Date：2011-6-17 
 * @Modified By：
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
 ***********************************************************/
package com.yondervision.yfmap.messagectrl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.CommonUtil;

/**
 * <pre>报文处理多例抽象类</pre>
 * @PackageName: com.yondervision.mi.messagectrl
 * @FileName: AbsMessageCtr.java
 * @author 林小龙
 */
public abstract class AbsMessageCtr {
	Logger log = Logger.getLogger("YFMAP");
	/** 存放AbsMessageCtr实例的HashMap */
	private final static HashMap<String, AbsMessageCtr> MAP_ABS_MESSAGE_CTR = new HashMap<String, AbsMessageCtr>();
	/** 实例累名称，模板编号：&ltmsgTemp&gt */
	private final static String CLASS_NAME_TEMP = "com.yondervision.yfmap.messagectrl.MessageCtr<msgTemp>";
	/** 模板标记头 */
	private final String TAG_BEGIN = "[@";
	/** 模板标记尾 */
	private final String TAG_END = "@]";
	/** 模板标签分隔符 */
	private final String REGEX = ",";
	/** 模板标签字段个数 */
	private final int TAG_COUNT = 5;

	/**
	 * <pre>
	 *    把HashMap类型的数据封装成特定模板类型的指定报文模板的报文。
	 * </pre>
	 * 
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            报文中要用的数据
	 * @return 封装好的报文
	 * @throws CenterRuntimeException
	 */
	public abstract String encapsulatedPackets(String msgTempFileName, HashMap<String, String> msgData, String transSeq) throws CenterRuntimeException ;

	/**
	 * <pre>
	 *    把特定模板类型的指定报文模板的报文解析成HashMap类型的数据。
	 * </pre>
	 * 
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            报文
	 * @param transSeq
	 *            交易顺序号
	 * @return 报文中解析出的数据
	 * @throws YQZLException
	 */
	public abstract HashMap<String, String> analysisPacket(String msgTempFileName, String msgData, String transSeq) throws CenterRuntimeException ;

	/**
	 * <pre>
	 *     创建/获取AbsMessageCtr的实例。
	 * </pre>
	 * 
	 * @param msgType
	 *            报文模板类型
	 * @return AbsMessageCtr的实例
	 * @throws CenterRuntimeException
	 */
	public static AbsMessageCtr getInstance(String msgType) throws CenterRuntimeException {
		AbsMessageCtr absMessageCtr = MAP_ABS_MESSAGE_CTR.get(msgType);
		if (absMessageCtr == null) {
			String className = CLASS_NAME_TEMP.replace("<msgTemp>", msgType);
			try {
				absMessageCtr = (AbsMessageCtr) Class.forName(className).newInstance();
				MAP_ABS_MESSAGE_CTR.put(msgType, absMessageCtr);
			} catch (InstantiationException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsMessageCtr的实例异常。");
			} catch (IllegalAccessException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsMessageCtr的实例异常。");
			} catch (ClassNotFoundException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsMessageCtr的实例异常。");
			}
		}

		return absMessageCtr;
	}

	protected String replaceMessage(String filename, HashMap<String, String> msgData) throws CenterRuntimeException {
		StringBuffer sMessage = new StringBuffer();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			while (true) {
				String temp = bf.readLine();
				if (temp==null) {
					break;
				}
				temp = temp+"\n";
				temp = replaceTag(temp, msgData);
				sMessage.append(temp);
			}
			bf.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sMessage.toString();
	}

	/**
	 * <pre>替换报文标签</pre>
	 * 
	 * @param temp 要替换标签的一段字符串
	 * @param msgData 数据来源
	 * @return 替换后的字符串
	 * @throws CenterRuntimeException
	 */
	protected String replaceTag(String temp, HashMap<String, String> msgData) throws CenterRuntimeException {
		if (temp == null) {
			return null;
		}

		while (true) {

			int beginIndex = temp.indexOf(this.TAG_BEGIN);
			if (beginIndex == -1)
				break;
			int endIndex = temp.indexOf(this.TAG_END) + this.TAG_END.length();
			String strTag = temp.substring(beginIndex, endIndex);
			String strTemp = strTag.substring(this.TAG_BEGIN.length(), strTag.length()-this.TAG_END.length());
			String[] pTemp = strTemp.split(this.REGEX);
			if (pTemp.length != this.TAG_COUNT) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 替换报文标签，模板标签字段个数异常。");
			}
			try {
				String value = (String) MessageDataConversion.invokeStaticMethod(pTemp[0], // 方法名
						msgData, // 封装报文所需数据
						pTemp[1], // 目标字段对应的KEY
						Integer.parseInt(pTemp[2]), // 目标字段长度
						Integer.parseInt(pTemp[3]), // 补齐方式
						pTemp[4].charAt(0) // 填充符
						);
				temp = temp.replace(strTag, value);
			} catch (Exception e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 替换报文标签异常。");
			}
		}

		return temp;
	}
	
	/**
	 * <pre>封装报文时用于在HashMap中取数据</pre>
	 * @param temp 报文模板中的配置内容
	 * @param mapData 存放报文所需数据的HashMap
	 * @return 匹配串为标签时返回取出的数据，否则返回匹配串
	 * @throws CenterRuntimeException
	 */
	protected String getData(String temp, HashMap<String, String> mapData) throws CenterRuntimeException {
		if (temp==null) {
			return null;
		}
		int beginIndex = temp.indexOf(this.TAG_BEGIN);
		if (beginIndex == -1)
			return temp;
		int endIndex = temp.indexOf(this.TAG_END) + this.TAG_END.length();
		String strTag = temp.substring(beginIndex, endIndex);
		String strTemp = strTag.substring(this.TAG_BEGIN.length(), strTag.length()-this.TAG_END.length());
		String[] pTemp = strTemp.split(this.REGEX);
		if (pTemp.length != this.TAG_COUNT) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 封装报文时用于在HashMap中取数据，模板标签字段个数异常。");
		}
		try {
			String value = (String) MessageDataConversion.invokeStaticMethod(pTemp[0], // 方法名
					mapData, // 封装报文所需数据
					pTemp[1], // 目标字段对应的KEY
					Integer.parseInt(pTemp[2]), // 目标字段长度
					Integer.parseInt(pTemp[3]), // 补齐方式
					pTemp[4].charAt(0) // 填充符
					);
			if (MessageCtrMain.SEND_FILE_NAME_KEY.equals(strTag)) {
				value = value.replaceAll("\\\\", "/");
				value = value.substring(value.lastIndexOf("/")+1);
			}
			
			temp = temp.replace(strTag, value);
			//唐山没有值时处理
			if(CommonUtil.isEmpty(temp))temp="--";
		} catch (Exception e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 封装报文时用于在HashMap中取数据异常。");
		}
		return temp;
	}
	
	/**
	 * <pre>解析报文时将报文中的数据放入HashMap中</pre>
	 * @param temp 报文模板中的配置内容
	 * @param value 报文中的内容
	 * @param mapData 数据存放的mapData
	 * @throws CenterRuntimeException
	 */
	protected void setData(String temp,String value, HashMap<String, String> mapData) throws CenterRuntimeException {
		if (temp==null) {
			return;
		}
		int beginIndex = temp.indexOf(this.TAG_BEGIN);
		if (beginIndex == -1)
			return;
		int endIndex = temp.indexOf(this.TAG_END) + this.TAG_END.length();
		String strTag = temp.substring(beginIndex, endIndex);
		String strTemp = strTag.substring(this.TAG_BEGIN.length(), strTag.length()-this.TAG_END.length());
		String[] pTemp = strTemp.split(this.REGEX);
		if (pTemp.length != this.TAG_COUNT) {
			log.error("解析报文时将报文中的数据放入HashMap中，模板"+strTag+"标签字段个数异常。");
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 解析报文时将报文中的数据放入HashMap中，模板标签字段个数异常。");
		}
		try {
			mapData.put(pTemp[1], value);
			value = (String) MessageDataConversion.invokeStaticMethod(pTemp[0], // 方法名
					mapData, // 封装报文所需数据
					pTemp[1], // 目标字段对应的KEY
					Integer.parseInt(pTemp[2]), // 目标字段长度
					Integer.parseInt(pTemp[3]), // 补齐方式
					pTemp[4].charAt(0) // 填充符
					);			
//			log.debug("报文中信息封装到MAP中 : "+pTemp[1]+"  :  "+value);
			mapData.put(pTemp[1], value);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 解析报文时将报文中的数据放入HashMap中异常。");
		}
	}

	@SuppressWarnings("unchecked")
	protected String getDetailsColValue(HashMap mapDataFileCols, String tempColValue, String fileLine) throws CenterRuntimeException {
		String dataColValue = null;
		int beginIndex = tempColValue.indexOf(this.TAG_BEGIN);
		if (beginIndex == -1)
			return tempColValue;		
		int endIndex = tempColValue.indexOf(this.TAG_END) + this.TAG_END.length();
		String strTag = tempColValue.substring(beginIndex, endIndex);
		String strTemp = strTag.substring(this.TAG_BEGIN.length(), strTag.length()-this.TAG_END.length());
		String[] pTemp = strTemp.split(this.REGEX);
		if (pTemp.length != this.TAG_COUNT) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 报文封装，模板标签字段个数异常。");
		}
		int iCol = Integer.parseInt(pTemp[1]);
		if (iCol>mapDataFileCols.size()) {
			String fileColLen = String.valueOf(mapDataFileCols.size());
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： getDetailsColValue 报文封装异常。");
		}
		try {
			String value = (String) MessageDataConversion.invokeStaticMethod(pTemp[0], // 方法名
					mapDataFileCols, // 封装报文所需数据
					pTemp[1], // 目标字段对应的KEY
					Integer.parseInt(pTemp[2]), // 目标字段长度
					Integer.parseInt(pTemp[3]), // 补齐方式
					pTemp[4].charAt(0) // 填充符
					);
			if (MessageCtrMain.SEND_FILE_NAME_KEY.equals(strTag)) {
				value = value.replaceAll("\\\\", "/");
				value = value.substring(value.lastIndexOf("/")+1);
			}
			
			dataColValue = tempColValue.replace(strTag, value);
			
		} catch (Exception e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： getDetailsColValue 报文封装异常。");
		}
		return dataColValue;
	}

	/**
	 * <pre>将<code>mapData</code>中的数据续写到文件<code>dataFileName</code>中的新一列</pre>
	 * @param dataFileName 要写入的文件名
	 * @param mapData 要写入的数据，key值对应要写入的列
	 * @param countCol 写入总列数
	 * @param spliter 列做分隔符
	 * @throws CenterRuntimeException
	 */
	protected void writeData(String dataFileName, HashMap<String, String> mapData, int countCol, String spliter) throws CenterRuntimeException{
		File dataFile = new File(dataFileName);
		File dirFile = dataFile.getParentFile();
		if (!dirFile.exists()) {
			boolean bCreateDir = dirFile.mkdirs();
			if (!bCreateDir) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 内存中数据换换报文模板中标签异常。");
			}
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 内存中数据换换报文模板中标签IO异常。");
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile, true));
			for (int i = 0; i < countCol; i++) {
				//*************************20150421修改大连处理注掉，其他中心这里第几位请注意。
				//String colValue = mapData.get(String.valueOf(i+1));
				//*************************20150421修改大连处理注掉，其他中心这里第几位请注意。
				String colValue = mapData.get(String.valueOf(i));
				if (colValue==null) colValue = "";
				bw.append(colValue);
				bw.append(spliter);
				bw.flush();
			}
			
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 内存中数据换换报文模板中标签IO异常。");
		}
	}
	
	/**
	 * <pre>取得报文模板绝对路径</pre>
	 * @param relPath 报文模板在com/yondervison/resources/messagetemp之后的路径
	 * @return 报文模板绝对路径
	 * @throws CenterRuntimeException 
	 */
	protected String getTempUrl(String relPath) throws CenterRuntimeException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		relPath = relPath.replaceAll("\\\\", "/");
		if (!relPath.startsWith("/")) {
			relPath = "/" + relPath;
		}
		String strTempUrl = null;
		try {
			strTempUrl = classLoader.getResource(MessageCtrMain.TEMP_PATH+relPath).toURI().getPath();
		} catch (URISyntaxException e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 取得报文模板绝对路径异常。");
		}
		return strTempUrl;
	}
}
