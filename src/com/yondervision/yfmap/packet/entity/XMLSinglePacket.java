package com.yondervision.yfmap.packet.entity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.packet.IDataConversion;
import com.yondervision.yfmap.packet.ISinglePacket;
import com.yondervision.yfmap.util.XMLTools;

/**
 * 单笔XML报文封装
 * 
 * @author LinXiaolong
 * 
 */
public class XMLSinglePacket implements ISinglePacket {
	
	private IDataConversion dc;
	
	private String encoding;

	public IDataConversion getDc() {
		return dc;
	}

	public void setDc(IDataConversion dc) {
		this.dc = dc;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.ISinglePacket#analysisPacket(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> analysisPacket(String msgTempFilePath,
			String msgTempFileName, String msgData) throws CenterRuntimeException {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		/*
		 * 创建模板和待解析报文的DOM对象
		 */
		if (!msgTempFilePath.endsWith(File.separator)) {
			msgTempFilePath += File.separator;
		}
		Document tempDom = XMLTools.getDomByFileName(msgTempFilePath
				+ msgTempFileName);
		Document msgDom = XMLTools.getDomByString(msgData);
		
		/*
		 * 解析报文
		 */
		Element dataElement = msgDom.getRootElement();
		List<Element> dataList = dataElement.getChildren();
		String strXPath = "/" + dataElement.getQualifiedName();
		analysisPacket(tempDom, dataList, dataMap, strXPath);

		return dataMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.ISinglePacket#encapsulatedPacket(java.lang.String,
	 * java.lang.String, java.util.Map)
	 */
	public String encapsulatedPacket(String msgTempFilePath,
			String msgTempFileName, Map<String, Object> msgData)
			throws CenterRuntimeException {
		if (!msgTempFilePath.endsWith(File.separator)) {
			msgTempFilePath += File.separator;
		}
		Document tempDom = XMLTools.getDomByFileName(msgTempFilePath
				+ msgTempFileName);
		Element element = tempDom.getRootElement();
		element = this.encapsulatedPackets(element, msgData).get(0);
		tempDom.setRootElement(element);
		Format format = Format.getPrettyFormat();
		format.setEncoding(this.getEncoding());
		format.setTextMode(TextMode.TRIM);
		// 展开空标记，成对出现
		format.setExpandEmptyElements(true);
//		format.setLineSeparator("\n");
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			xmlout.output(tempDom, bo);
		} catch (IOException e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：按指定路径下的指定报文模板将Map中的数据封为指定报文");
		}

		return bo.toString();
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
	 * @param mapData
	 *            取出数据存放的位置
	 * @param strXPath
	 *            当前节点路径
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private void analysisPacket(Document tempDom, List<Element> dataList, Map<String, Object> mapData,
			String strXPath) throws CenterRuntimeException {
		Iterator<Element> dataIter = dataList.iterator();
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
			Element tempElement = XMLTools.getElementByXPath(tempDom, strXPath1);

			/*
			 * 解析节点数据
			 */
			String strDataValue = element.getTextTrim();
			String strTempValue = tempElement.getTextTrim();
			this.dc.putData(mapData, strTempValue, strDataValue);
			
			/*
			 * 有子节点则递归调用
			 */
			List<Element> dataList1 = element.getChildren();
			if (dataList1 != null)
				analysisPacket(tempDom, dataList1, mapData, strXPath1);
		}
	}
	
	/**
	 * <pre>
	 * 把Map类型的数据封装成特定模板类型的指定报文模板的报文。
	 * </pre>
	 * 
	 * @param tempElement
	 *            报文模板名称
	 * @param mapData
	 *            报文中要用的数据
	 * @return 封装好的报文
	 * @throws CenterRuntimeException
	 */
	@SuppressWarnings("unchecked")
	private List<Element> encapsulatedPackets(Element tempElement, Map<String, Object> mapData)
			throws CenterRuntimeException {
		/*
		 * 节点封装
		 */
		List<Element> listElement = tempElement.getChildren();
		Element tempElement2 = (Element) tempElement.clone();

		if (listElement != null && listElement.size() != 0) {
			Iterator<Element> iterElement = listElement.iterator();
			while (iterElement.hasNext()) {
				Element element = (Element) iterElement.next();
				List<Element> element1 = encapsulatedPackets(element, mapData);
				tempElement2.removeChild(element.getQualifiedName());
				tempElement2.addContent(element1);

			}
		}
		String strTempValue = tempElement2.getTextTrim();
		String strValue = this.dc.getData(mapData, strTempValue);
		// modified by yin.hl
		if (strTempValue.length() > 0){
			tempElement2.setText(strValue);
		}
		
		List<Element> listTempElement2 = new LinkedList();
		listTempElement2.add(tempElement2);

		return listTempElement2;
	}

}