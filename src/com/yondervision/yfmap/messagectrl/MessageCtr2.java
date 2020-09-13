/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.messageCtr
 * @ClassName: MessageCtr01.java
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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.PropertiesReader;

public class MessageCtr2 extends AbsMessageCtr {
	Logger log = Logger.getLogger("YFMAP");
	/** XML报文循环节点的Attribute的name */
	private static final String DETAILS_NAME = "details";
	
	private static final String TEMP_FILE_NAME = "filename";

	/** 发送的XML报文循环节点Attribute的值 */
	private static final String SEND_DETAILS_VALUE = "send";

	/** XML报文的编码方式 */
	private static final String ENCODING ="GBK";//GBK，UTF-8  大连用GBK，肇庆也是GBK，其它为UTF-8，秦皇岛GBK,保山GBK,唐山承德GBK

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.messageCtr.AbsMessageCtr#analysisPacket(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	public HashMap<String, String> analysisPacket(String msgTempFileName, String msgData, String transSeq)
			throws CenterRuntimeException {
		Document tempDom=null;
		try {
			tempDom = this.getDomByFileName(msgTempFileName);
			Document dataDom = this.getDomByString(msgData);
			return this.analysisPacket(tempDom, dataDom, transSeq);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"创建Document对象失败");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.messageCtr.MessageCtrIntfc#encapsulatedPackets(java.lang.String,
	 *      java.util.HashMap, java.lang.String)
	 */
	public String encapsulatedPackets(String msgTempFileName, HashMap<String, String> msgData, String transSeq) throws CenterRuntimeException {
		Document tempDom;
		ByteArrayOutputStream bo  = null;
		try {
			tempDom = this.getDomByFileName(msgTempFileName);
			Element element = tempDom.getRootElement();
			element = this.encapsulatedPackets(element, msgData, transSeq).get(0);
			tempDom.setRootElement(element);
			Format format = Format.getPrettyFormat();
			format.setEncoding(MessageCtr2.ENCODING);
			//format.setLineSeparator("\n");
			XMLOutputter xmlout = new XMLOutputter(format);
			bo = new ByteArrayOutputStream();
			try {
				xmlout.output(tempDom, new PrintWriter(bo));
			} catch (IOException e) {
				e.printStackTrace();
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：把HashMap类型的数据封装成特定模板类型的指定报文模板的报文异常。");
			}
		} catch (CenterRuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw e1;
		}
		return bo.toString();
	}

	/**
	 * <pre>
	 * 把HashMap类型的数据封装成特定模板类型的指定报文模板的报文。
	 * </pre>
	 * 
	 * @param tempElement
	 *            报文模板名称
	 * @param msgData
	 *            报文中要用的数据
	 * @return 封装好的报文
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private List<Element> encapsulatedPackets(Element tempElement, HashMap<String, String> msgData, String transSeq)
			throws CenterRuntimeException {
		/*
		 * 判断是否是循环节点 
		 * (details != null) && details.equals(MessageCtr2.SEND_DETAILS_VALUE)表示此节点为多笔循环的根节点
		 */
		String details = tempElement.getAttributeValue(MessageCtr2.DETAILS_NAME);
		if ((details != null) && details.equals(MessageCtr2.SEND_DETAILS_VALUE)) {
			tempElement.removeAttribute(MessageCtr2.DETAILS_NAME);
			List<Element> newTempElement = this.encapsulatedDetailsPackets(tempElement, msgData);
			return newTempElement;
		}
		
		/*
		 * 判断是否是批量数据文件节点
		 */
		String tempFileName = tempElement.getAttributeValue(MessageCtr2.TEMP_FILE_NAME);
		if (tempFileName != null){
			String dataFileName = msgData.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			tempElement.removeAttribute(MessageCtr2.TEMP_FILE_NAME);
			this.encapsulatedTempFilePackets(dataFileName, msgData,transSeq, tempFileName);
			String sendFileName = msgData.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			tempElement.setText(sendFileName);
		}

		/*
		 * 非循环节点处理方式
		 */
		List<Element> listElement = tempElement.getChildren();
		Element tempElement2 = (Element) tempElement.clone();

		if (listElement != null && listElement.size() != 0) {
			Iterator<Element> iterElement = listElement.iterator();
			while (iterElement.hasNext()) {
				Element element = (Element) iterElement.next();
				List<Element> element1 = encapsulatedPackets(element, msgData, transSeq);
				tempElement2.removeChild(element.getQualifiedName());
				tempElement2.addContent(element1);

			}
		}
		String strTempValue = tempElement2.getTextTrim();
		String strValue = this.getData(strTempValue, msgData);
		//唐山没有值时处理为空
		if (strValue.length() > 0){
			if("--".equals(strValue))
			{
				tempElement2.setText("");
			}else
			{
				tempElement2.setText(strValue);
			}
		}
		//if (strValue.length() > 0)tempElement2.setText(strValue);
		List<Element> listTempElement2 = new LinkedList();
		listTempElement2.add(tempElement2);

		return listTempElement2;
	}

	private void encapsulatedTempFilePackets(String dataFileName, HashMap<String, String> dataMap, String transSeq, String tempFileName) throws CenterRuntimeException {
		/*
		 * 拼接数据文件完整路径
		 */
		String dataFilePath = "";
		/*= ReadProperties.getInstence().getProperty("RECEIVE_FILE_ADDRESS");
		dataFilePath = dataFilePath.replaceAll("\\\\", "/");*/
		StringBuffer fileSeparator = new StringBuffer();
		for (int i = 0; i < File.separator.length(); i++) {
			char cSpliter0 = File.separator.charAt(i);
//			fileSeparator += "\\" + cSpliter0;
			fileSeparator.append("\\" + cSpliter0);
		}
		dataFilePath = dataFilePath.replaceAll(fileSeparator.toString(), "/");
		dataFilePath = (dataFilePath.endsWith("/") ? dataFilePath : dataFilePath + "/").replaceAll("/", fileSeparator.toString());
		dataFileName = dataFilePath + dataFileName;
		
		/*
		 * 读取数据文件的分隔符、目标文件的行模板和目标文件名
		 */
		String dataSpliter = null;	//数据文件分隔符
		String tempLine = null;	//目标文件的行模板
		String sendFileName = null;	//目标文件名格式
		String tempSendFileName = null;
		try {
			tempFileName = super.getTempUrl(tempFileName);	//获取数据模板的绝对路径
			FileReader frTempFile = new FileReader(tempFileName);
			BufferedReader bfTempFile = new BufferedReader(frTempFile);
			dataSpliter = bfTempFile.readLine().trim();
			tempLine = bfTempFile.readLine().trim();
			tempSendFileName = bfTempFile.readLine().trim();
			
			sendFileName = createFileName(tempSendFileName, transSeq);
			bfTempFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * 拼接目标文件名
		 */
		SimpleDateFormat formatter = new SimpleDateFormat(MessageDataConversion.dateFormat);
		String strDate = formatter.format(new Date());
		String rootPath = "";//ReadProperties.getInstence().getProperty("RECEIVE_FROM_BANK_FILE_PATH");
		rootPath = rootPath.replaceAll("\\\\", "/");	
		rootPath = rootPath.replaceAll(fileSeparator.toString(), "/");
		rootPath = (rootPath.endsWith("/") ? rootPath : rootPath + "/").replaceAll("/", fileSeparator.toString());
		String filePath = rootPath + "send" + File.separator + strDate;
		String sendDataFileName = filePath + File.separator + sendFileName;
		File dataFile = new File(sendDataFileName);
		if (dataFile.exists()) {
			boolean or = dataFile.delete();
		}
		File fFilePath = new File(filePath);
		if(!fFilePath.exists()){
			boolean or = fFilePath.mkdirs();
		}
		dataMap.put(MessageCtrMain.RECEIVE_FILE_NAME_KEY, sendDataFileName);
		
		/*
		 * 数据映射到目标文件中
		 */
		try {
			FileReader frDataFile = new FileReader(dataFileName);
			BufferedReader bfDataFile = new BufferedReader(frDataFile);
			FileWriter fwRecvDataFile = new FileWriter(sendDataFileName, true);
			BufferedWriter bwRecvDataFile = new BufferedWriter(fwRecvDataFile);
			for(String dataLine=bfDataFile.readLine(); dataLine!=null; dataLine=bfDataFile.readLine()){
				String [] pDataLine = dataLine.split(dataSpliter);
				HashMap<String, String> mapData = new HashMap<String, String>();
				mapData.putAll(dataMap);
				for(int i=0; i<pDataLine.length; i++){
					mapData.put(String.valueOf(i+1), pDataLine[i]);
				}
				bwRecvDataFile.append(super.replaceTag(tempLine, mapData));
				bwRecvDataFile.flush();
				bwRecvDataFile.newLine();
			}
			bfDataFile.close();
			bwRecvDataFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String createFileName(String tempSendFileName, String transSeq) {
		// TODO Auto-generated method stub
		return tempSendFileName.replaceAll("[@transseq@]", transSeq);
	}

	/**
	 * <pre>
	 *  按照模板（tempDom）格式解析报文（dataDom），将解析出的数据放入HashMap中返回
	 * </pre>
	 * 
	 * @param tempDom
	 *            XML报文模板的Document对象
	 * @param dataDom
	 *            XML数据报文的Document对象
	 * @param transSeq
	 *            交易顺序号
	 * @return 解析出的数据
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, String> analysisPacket(Document tempDom, Document dataDom, String transSeq) throws CenterRuntimeException {
		try{
			HashMap<String, String> dataMap = new HashMap<String, String>();
			Element dataElement = dataDom.getRootElement();//返回该文档的根元素
			List<Element> dataList = dataElement.getChildren();//返回一个列表的所有直接子元素的嵌套（深一层）在这个元素，元素的对象。
			String strXPath = "/" + dataElement.getQualifiedName();//返回元素的全名			
			analysisPacket(tempDom, dataList, dataMap, strXPath, transSeq);
			return dataMap;
		}catch (CenterRuntimeException e){
			e.printStackTrace();
			System.out.println("按照模板（tempDom）格式解析报文（dataDom），将解析出的数据放入HashMap中返回 analysisPacket 301");
			throw e;
		}
	}

	/**
	 * <pre>
	 *  递归遍历结果报文，与模板报文匹配取数据
	 * </pre>
	 * 
	 * @param tempDom
	 *            模板报文的Document对象
	 * @param dataList
	 *            数据当前节点下的Element对象集合
	 * @param dataMap
	 *            取出数据存放的位置
	 * @param strXPath
	 *            当前节点路径
	 * @param transSeq
	 *            交易顺序号
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private void analysisPacket(Document tempDom, List<Element> dataList, HashMap<String, String> dataMap,
			String strXPath, String transSeq) throws CenterRuntimeException {
		try{
			Iterator<Element> dataIter = dataList.iterator();			
//			for(int i=0;i<dataList.size();i++){
//				log.debug(dataList.get(i).getName());
//			}
			/*
			 * 遍历数据节点
			 */
			while (dataIter.hasNext()) {
				// 数据节点的子节点				
				Element element = (Element) dataIter.next();
				// 子节点路径
				String strXPath1 = strXPath + "/" + element.getQualifiedName();
				// System.out.println(strXPath1);
				// 取得模板中对应路径的节点
				Element tempElement = this.getElementByXPath(tempDom, strXPath1);
//				log.debug("参数 : "+element.getTextTrim());
//				log.debug("模板信息 : "+tempElement.getTextTrim());
				/*
				 * 判断是否是循环节点 details!=null是次节点为多笔循环的根节点
				 */
				String details = tempElement.getAttributeValue(MessageCtr2.DETAILS_NAME);				
				if (details != null) {
					analysisDetailsPacket(tempDom, element, dataMap, strXPath1, transSeq);
					continue;
				}
				
				/*
				 * 判断是否是批量数据文件节点
				 */
				String tempFileName = tempElement.getAttributeValue(MessageCtr2.TEMP_FILE_NAME);
				if (tempFileName != null){
					String dataFileName = tempElement.getTextTrim();
					analysisTempFileDataPacket(dataFileName, dataMap, transSeq, tempFileName);
					continue;
				}
	
				/*
				 * 非循环节点处理方式
				 */
				
				String strDataValue = element.getTextTrim();
				String strTempValue = tempElement.getTextTrim();				
				this.setData(strTempValue, strDataValue, dataMap);
				List<Element> dataList1 = element.getChildren();				
				if (dataList1 != null){					
					analysisPacket(tempDom, dataList1, dataMap, strXPath1, transSeq);					
				}
			}
		}catch (Exception e){
			e.printStackTrace();			
		}
	}

	/**
	 * <pre>解析批量数据文件</pre>
	 * @param dataFileName 数据文件
	 * @param dataMap 数据临时存储空间
	 * @param transSeq 交易序号
	 * @param tempFileName 数据文件模板在<code>com/yondervison/resources/messagetemp</code>之后的路径
	 * @throws CenterRuntimeException
	 */
	private void analysisTempFileDataPacket(String dataFileName, HashMap<String, String> dataMap, String transSeq, String tempFileName) throws CenterRuntimeException {
		try{
			/*
			 * 拼接数据文件完整路径
			 */
			String dataFilePath = PropertiesReader.getProperty("properties.properties", "RECEIVE_FROM_BANK_FILE_PATH").trim();
			dataFilePath = dataFilePath.replaceAll("\\\\", "/");
			StringBuffer fileSeparator = new StringBuffer();
			for (int i = 0; i < File.separator.length(); i++) {
				char cSpliter0 = File.separator.charAt(i);
//				fileSeparator += "\\" + cSpliter0;
				fileSeparator.append("\\" + cSpliter0);
			}
			dataFilePath = dataFilePath.replaceAll(fileSeparator.toString(), "/");
			dataFilePath = (dataFilePath.endsWith("/") ? dataFilePath : dataFilePath + "/").replaceAll("/", fileSeparator.toString());
			dataFileName = dataFilePath + dataFileName;
			
			/*
			 * 读取数据文件的分隔符和目标文件的行模板
			 */
			String dataSpliter = null;	//数据文件分隔符
			String tempLine = null;	//目标文件的行模板
			try {
				tempFileName = super.getTempUrl(tempFileName);	//获取数据模板的绝对路径
				FileReader frTempFile = new FileReader(tempFileName);
				BufferedReader bfTempFile = new BufferedReader(frTempFile);
				dataSpliter = bfTempFile.readLine().trim();
				tempLine = bfTempFile.readLine().trim();
				bfTempFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			 * 拼接目标文件名
			 */
			SimpleDateFormat formatter = new SimpleDateFormat(MessageDataConversion.dateFormat);
			String strDate = formatter.format(new Date());
			String rootPath = PropertiesReader.getProperty("properties.properties", "RECEIVE_FILE_ADDRESS");
			rootPath = rootPath.replaceAll("\\\\", "/");	
			rootPath = rootPath.replaceAll(fileSeparator.toString(), "/");
			rootPath = (rootPath.endsWith("/") ? rootPath : rootPath + "/").replaceAll("/", fileSeparator.toString());
			String filePath = rootPath + "receive" + File.separator + strDate;
			String fileName = "RECV" + transSeq + ".DAT";
			String recvDataFileName = filePath + File.separator + fileName;
			File dataFile = new File(recvDataFileName);
			if (dataFile.exists()) {
				if(!dataFile.delete())
					System.out.println("删除文件或文件夹失败"+dataFile);
			}
			File fFilePath = new File(filePath);
			if(!fFilePath.exists()){
				if(!fFilePath.mkdirs())
					System.out.println("创建文件或文件夹失败:"+fFilePath);
			}
			dataMap.put(MessageCtrMain.RECEIVE_FILE_NAME_KEY, recvDataFileName);
			
			/*
			 * 数据映射到目标文件中
			 */
			try {
				FileReader frDataFile = new FileReader(dataFileName);
				BufferedReader bfDataFile = new BufferedReader(frDataFile);
				FileWriter fwRecvDataFile = new FileWriter(recvDataFileName, true);
				BufferedWriter bwRecvDataFile = new BufferedWriter(fwRecvDataFile);
				for(String dataLine=bfDataFile.readLine(); dataLine!=null; dataLine=bfDataFile.readLine()){
					String [] pDataLine = dataLine.split(dataSpliter);
					HashMap<String, String> mapData = new HashMap<String, String>();
					mapData.putAll(dataMap);
					for(int i=0; i<pDataLine.length; i++){
						mapData.put(String.valueOf(i+1), pDataLine[i]);
					}
					bwRecvDataFile.append(super.replaceTag(tempLine, mapData));
					bwRecvDataFile.flush();
					bwRecvDataFile.newLine();
				}
				bfDataFile.close();
				bwRecvDataFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}catch (CenterRuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <pre>
	 * 根据模板批量节点<code>tempElement</code>解析批量报文<code>dataElement</code>，
	 * 把结果写入文件中，将文件名存入<code>dataMap</code>中
	 * </pre>
	 * @param tempElement 报文模板相应结点的的
	 * @param dataElement 要解析报文的批量数据节点
	 * @param dataMap 解析出的数据要存放的位置
	 * @param transSeq 交易序号
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private void analysisDetailsPacket(Document tempDocument, Element dataElement, HashMap<String, String> dataMap,
			String strXPath, String transSeq) throws CenterRuntimeException {
		try{
			// 取得模板中对应路径的节点
			Element tempElement = this.getElementByXPath(tempDocument, strXPath);
			int iCountCol = Integer.parseInt(tempElement.getAttributeValue(MessageCtr2.DETAILS_NAME));
			String dataFileName = dataMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			if (dataFileName==null || dataFileName.isEmpty()) {
				SimpleDateFormat formatter = new SimpleDateFormat(MessageDataConversion.dateFormat);
				String strDate = formatter.format(new Date());
				String rootPath = PropertiesReader.getProperty("properties.properties","RECEIVE_FILE_ADDRESS");//"";
				rootPath = rootPath.replaceAll("\\\\", "/");
				StringBuffer fileSeparator = new StringBuffer();
				for (int i = 0; i < File.separator.length(); i++) {
					char cSpliter0 = File.separator.charAt(i);
//					fileSeparator += "\\" + cSpliter0;
					fileSeparator.append("\\" + cSpliter0);
				}
				rootPath = rootPath.replaceAll(fileSeparator.toString(), "/");
				rootPath = (rootPath.endsWith("/") ? rootPath : rootPath + "/").replaceAll("/", fileSeparator.toString());
				String filePath = rootPath + "receive" + File.separator + strDate;
				String fileName = "RECV" + transSeq + ".DAT";
				dataFileName = filePath + File.separator + fileName;
				File dataFile = new File(dataFileName);
				if (dataFile.exists()) {
					if(!dataFile.delete())
						System.out.println("删除文件或文件夹失败"+dataFile);
				}
				dataMap.put(MessageCtrMain.RECEIVE_FILE_NAME_KEY, dataFileName);
			}
			
			List<Element> listDataElement = dataElement.getChildren();
			Iterator<Element> iterDataElement = listDataElement.iterator();
	
			HashMap<String, String> mapData0 = new HashMap<String, String>();
			mapData0.putAll(dataMap);
			while (iterDataElement.hasNext()) {
				Element dataElement0 = (Element) iterDataElement.next();
				Element tempElement0 = this.getElementByXPath(tempDocument, strXPath+"/"+dataElement0.getQualifiedName());
				String strTemp = tempElement0.getValue();
				String strValue = dataElement0.getValue();
				super.setData(strTemp, strValue, mapData0);
				
			}
			super.writeData( dataFileName, mapData0, iCountCol, MessageCtrMain.SPLITER );
		}catch(CenterRuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <pre>
	 * 封装多笔节点的XML报文
	 * </pre>
	 * 
	 * @param tempElement
	 *            模板中的多笔节点元素
	 * @param msgData
	 *            报文数据来源
	 * @return 封装后的多笔节点元素
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private List<Element> encapsulatedDetailsPackets(final Element tempElement, final HashMap<String, String> msgData)
			throws CenterRuntimeException {
		try{
			String dataFileName = msgData.get(MessageCtrMain.SEND_FILE_NAME_KEY);
			
			File dataFile = new File(dataFileName);
			String detailsName = tempElement.getName();
			List<Element> newListElement = new LinkedList();
			List<Element> listElement = tempElement.getChildren();
			if (listElement == null || listElement.size() == 0) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：封装多笔节点的XML报文异常。");
			}
			try {
				BufferedReader brData = new BufferedReader(new FileReader(dataFile));
				int line = 0;
				while (true) {
					Iterator<Element> iterElement = listElement.iterator();
					Element newTempElement = new Element(detailsName);
					String strData = brData.readLine();
					if (strData == null) {
						break;
					}
					StringBuffer spliter = new StringBuffer();
					for (int i = 0; i < MessageCtrMain.SPLITER.length(); i++) {
						char cSpliter0 = MessageCtrMain.SPLITER.charAt(i);
//						spliter += "\\" + cSpliter0;
						spliter.append("\\" + cSpliter0);
					}
					String[] pstrData = strData.split(spliter.toString());
					HashMap<String, String> mapData = new HashMap<String, String>(pstrData.length);
					for (int i = 0; i < pstrData.length; i++) {
						mapData.put(String.valueOf(i + 1), pstrData[i]);
					}
					msgData.putAll(mapData);
					while (iterElement.hasNext()) {
						Element element = (Element) iterElement.next().clone();
						String col = element.getValue().trim();
						String value = super.getDetailsColValue(msgData, col, String.valueOf(++line));
						element.setText(value);
						newTempElement.addContent(element);
					}
					newListElement.add(newTempElement);
				}
				brData.close();
			} catch (FileNotFoundException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：封装多笔节点的XML报文，未找到文件异常。");
			} catch (IOException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：封装多笔节点的XML报文，IO异常。");
			}
			return newListElement;
		}catch(CenterRuntimeException e){
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * <pre>
	 * 通过报文路径获取text值
	 * </pre>
	 * 
	 * @param dom
	 *            Document对象
	 * @param strXPath
	 *            XML中的路径
	 * @return 对应的text值
	 */
	// private String getValueByXPath(Document dom, String strXPath) {
	// String text = null;
	// Element element = getElementByXPath(dom, strXPath);
	// text = element.getText();
	// return text;
	// }
	/**
	 * <pre>
	 * 通过报文路径获取Element对象
	 * </pre>
	 * 
	 * @param dom
	 *            Document对象
	 * @param strXPath
	 *            XML中的路径
	 * @return 对应的text值
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private Element getElementByXPath(Document dom, String strXPath) throws CenterRuntimeException {
		XPath xpath;
		Element element = null;
		try {
			xpath = XPath.newInstance(strXPath.replaceAll("\\\\", "/"));
			List list = xpath.selectNodes(dom);
			element = (Element) list.get(0);
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：通过报文路径获取Element对象XML处理异常。");
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：通过报文路径获取Element对象异常。");
		}

		return element;
	}

	/**
	 * <pre>
	 * 通过文件名获取Document对象
	 * </pre>
	 * 
	 * @param fileName
	 *            XML文件名
	 * @return Document对象
	 * @throws CenterRuntimeException
	 */
	private Document getDomByFileName(String fileName) throws CenterRuntimeException {
		File file = new File(fileName);
		if(!file.exists()){
			log.error(fileName+"文件不存在");
			CenterRuntimeException ce = new CenterRuntimeException("999999","文件不存在");
			throw ce;
		}
		Document jdom = getDomByFile(file);			
		return jdom;
			
	}

	/**
	 * <pre>
	 * 通过XML报文获取Document对象
	 * </pre>
	 * 
	 * @param xmlMessage
	 *            XML报文
	 * @return Document对象
	 * @throws CenterRuntimeException
	 * @throws UnsupportedEncodingException 
	 */
	private Document getDomByString(String xmlMessage) throws CenterRuntimeException, UnsupportedEncodingException {
		//System.out.println("xmlMessage : "+xmlMessage );
		InputStream is = new ByteArrayInputStream(xmlMessage.getBytes(MessageCtr2.ENCODING));
		Document jdom = getDomByFile(is);
		return jdom;
	}

	/**
	 * <pre>
	 * 从文件中取得Document对象
	 * </pre>
	 * 
	 * @param is
	 *            XML输入流
	 * @return Document对象
	 * @throws CenterRuntimeException
	 */
	private Document getDomByFile(InputStream is) throws CenterRuntimeException {
		Document jdom = null;
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		try {
			System.out.println("MessageCtr2:getDomByFile start 根据XML流创建DOCUMENT对象");
			jdom = builder.build(is);//根据XML流创建DOCUMENT对象
			System.out.println("MessageCtr2:getDomByFile end 根据XML流创建DOCUMENT对象");
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：从报文中取得Document对象处理异常。");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：从报文中取得Document对象IO异常。");
		}
		return jdom;
	}

	/**
	 * <pre>
	 * 通过文件获取Document对象
	 * </pre>
	 * 
	 * @param file
	 *            XML文件
	 * @return Document对象
	 * @throws CenterRuntimeException
	 */
	private Document getDomByFile(File file) throws CenterRuntimeException {
		Document jdom = null;
		SAXBuilder builder = new SAXBuilder();//xml解析器
		
		builder.setIgnoringElementContentWhitespace(true);//解析时忽略空白字符
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),ENCODING);
			jdom = builder.build(isr);//这建立一个文件从所提供的文件名
			isr.close();			
		} catch (JDOMException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 通过文件获取Document对象处理异常。");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 通过文件获取Document对象IO异常。");
		}
		return jdom;
	}
	
	public static void main(String [] args){
		MessageCtr2 mc2 = new MessageCtr2();
		String dataFileName = "aaa.txt";
		HashMap<String, String> dataMap = new HashMap<String, String>();
		String transSeq = "123";
		String tempFileName= "/REP_2QBLL1.temp";
		try {
			mc2.analysisTempFileDataPacket(dataFileName, dataMap, transSeq, tempFileName);
			String recvFileName = dataMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println(recvFileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document d = new Document();
		Element t = new Element("xml");
		t.setText("<?xml version=\"1.0\" encoding = \"GBK\"?><root>test</root>");
		d.setRootElement(t);
		Format format = Format.getPrettyFormat();
		format.setEncoding(MessageCtr2.ENCODING);
		format.setLineSeparator("\n");
		XMLOutputter xmlout = new XMLOutputter(format);
		try {
			xmlout.output(d, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String xml = d.getRootElement().getText();
		System.out.println(xml);
		
		a: for( int i=0; i<3; i++ ) {
			for( int j=0; j<5; j++ ){
				System.out.println("i=["+i+"]\nj=["+j+"]\n");
				break a;
			}
		}
//		Logger log = Logger.getLogger("YFMAP");
//		Exception e = new Exception();
//		 // 记录debug级别的信息  
//		log.debug("This is debug message.");  
//        // 记录info级别的信息  
//		log.info("This is info message.");  
//        // 记录error级别的信息  
//		log.error(e,e.fillInStackTrace());
		
	}
}
