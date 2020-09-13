/**
 * 
 */
package com.yondervision.yfmap.packet.entity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.packet.BatchFilerWRFactory;
import com.yondervision.yfmap.packet.IBatchFileReader;
import com.yondervision.yfmap.packet.IBatchFileWriter;
import com.yondervision.yfmap.packet.IBatchPacket;
import com.yondervision.yfmap.packet.IDataConversion;
import com.yondervision.yfmap.packet.ISinglePacket;
import com.yondervision.yfmap.util.XMLTools;

/**
 * @author LinXiaolong
 * 
 */
public class XMLBatchPacket implements IBatchPacket {

	private IDataConversion dc;

	private ISinglePacket isp;

	private String encoding;

	private String xmlDetailsElementName;

	private String batchFileReaderName;

	private String batchFileWriteName;

	public IDataConversion getDc() {
		return dc;
	}

	public void setDc(IDataConversion dc) {
		this.dc = dc;
	}

	public ISinglePacket getIsp() {
		return isp;
	}

	public void setIsp(ISinglePacket isp) {
		this.isp = isp;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getXmlDetailsElementName() {
		return xmlDetailsElementName;
	}

	public void setXmlDetailsElementName(String xmlDetailsElementName) {
		this.xmlDetailsElementName = xmlDetailsElementName;
	}

	public String getBatchFileReaderName() {
		return batchFileReaderName;
	}

	public void setBatchFileReaderName(String batchFileReaderName) {
		this.batchFileReaderName = batchFileReaderName;
	}

	public String getBatchFileWriteName() {
		return batchFileWriteName;
	}

	public void setBatchFileWriteName(String batchFileWriteName) {
		this.batchFileWriteName = batchFileWriteName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchPacket#anaXMLBatchPacket(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> anaXMLBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, String dataFilePatch, String msgData)
			throws CenterRuntimeException {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put(IBatchPacket.MAXIMUM_ITEMS_KEY, "200");

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
		this.anaXmlBatchPacket(tempDom, dataList, dataMap, strXPath,
				fileTempFilePath, fileTempFileName, dataFilePatch);

		return dataMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchPacket#anaXMLFileBatchPacket(java.lang.String
	 * , java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public Map<String, Object> anaXMLFileBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, String msgData, String msgFileName)
			throws CenterRuntimeException {

		/*
		 * 解析报文
		 */
		Map<String, Object> mapData = this.isp.analysisPacket(msgTempFilePath,
				msgTempFileName, msgData);

		/*
		 * 将批量数据文件名放入mapData
		 */
		mapData.put(IBatchPacket.DATA_FILE_NAME_KEY, msgFileName);

		return mapData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchPacket#encXMLAndFileBatchPacket(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.util.Map)
	 */
	public String encXMLAndFileBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, Map<String, Object> mapData)
			throws CenterRuntimeException {

		/*
		 * 封装XML报文，批量数据文件与批量数据模板格式相同，无需转换
		 */
		String msgData = this.isp.encapsulatedPacket(msgTempFilePath,
				msgTempFileName, mapData);

		return msgData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchPacket#encXMLBatchPacket(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Map)
	 */
	public String encXMLBatchPacket(String msgTempFilePath,
			String msgTempFileName, String fileTempFilePath,
			String fileTempFileName, Map<String, Object> msgData)
			throws CenterRuntimeException {
		if (!msgTempFilePath.endsWith(File.separator)) {
			msgTempFilePath += File.separator;
		}
		Document tempDom = XMLTools.getDomByFileName(msgTempFilePath
				+ msgTempFileName);
		Element element = tempDom.getRootElement();
		element = this.encXMLBatchPackets(element, fileTempFilePath,
				fileTempFileName, msgData).get(0);
		tempDom.setRootElement(element);
		Format format = Format.getPrettyFormat();
		format.setEncoding(this.getEncoding());
		format.setLineSeparator("\n");
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			xmlout.output(tempDom, bo);
		} catch (IOException e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：封装XML格式的批量报文异常");
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
	 * @param fileTempFilePath
	 *            批量数据模板文件路径
	 * @param fileTempFileName
	 *            批量数据模板文件名称
	 * @param dataFilePatch
	 *            批量数据文件路径
	 * @throws YQZLException
	 */
	@SuppressWarnings("unchecked")
	private void anaXmlBatchPacket(Document tempDom, List<Element> dataList,
			Map<String, Object> mapData, String strXPath,
			String fileTempFilePath, String fileTempFileName,
			String dataFilePatch) throws CenterRuntimeException {

		int count = 0;
		Iterator<Element> dataIter = dataList.iterator();

		IBatchFileWriter ibw = null;

		StringBuffer sbBatchFileName = new StringBuffer();

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
			Element tempElement = XMLTools
					.getElementByXPath(tempDom, strXPath1);

			/*
			 * 解析批量数据节点
			 */
			if (element.getQualifiedName().equals(this.xmlDetailsElementName)) {
				if (!fileTempFilePath.endsWith(File.separator)) {
					fileTempFilePath = fileTempFilePath + File.separator;
				}

				String batchTempFileName = fileTempFilePath + fileTempFileName;
				
				File file = new File(dataFilePatch);
				if(!file.exists()){
					if(!file.mkdirs())
						System.out.println("创建文件或文件夹失败:"+file);
				}
				
				IBatchFileReader ibr = BatchFilerWRFactory
						.creatIBatchFileReader(this.getBatchFileReaderName(),
								batchTempFileName);
				if (count
						% Integer.parseInt(mapData.get(
								IBatchPacket.MAXIMUM_ITEMS_KEY).toString()) == 0) {
					String batchFileName = this.createBatchFileName();
					if (ibw != null) {
						ibw.close();
						sbBatchFileName.append(IBatchPacket.FILE_NAME_SPLATER);
					}
					sbBatchFileName.append(batchFileName);

					ibw = BatchFilerWRFactory.creatIBatchFileWriter(this
							.getBatchFileWriteName(), dataFilePatch+"/"+batchFileName, ibr
							.getBatchHead(), mapData);
					
					// yin.hl保存文件名
					mapData.put(IBatchPacket.DATA_FILE_NAME_KEY, batchFileName);
				}

				count++;

				List<Element> listDetailElement = element.getChildren();
				Map<String, Object> mapDetailData = new HashMap<String, Object>();

				// 解析明细数据
				anaXmlBatchPacket(tempDom, listDetailElement, mapDetailData,
						strXPath1, fileTempFilePath, fileTempFileName,
						dataFilePatch);

				// 写入明细数据
				ibw.writeDetail(ibr.readNextDetail(), mapDetailData);

				ibr.close();
				continue;
			}

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
				anaXmlBatchPacket(tempDom, dataList1, mapData, strXPath1,
						fileTempFilePath, fileTempFileName, dataFilePatch);
		}
		if (ibw != null) {
			ibw.close();
		}
		mapData
				.put(IBatchPacket.DATA_FILE_NAME_KEY, sbBatchFileName
						.toString());

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
	 * @throws YQZLException
	 */
	@SuppressWarnings("unchecked")
	private List<Element> encXMLBatchPackets(Element tempElement,
			String fileTempFilePath, String fileTempFileName,
			Map<String, Object> mapData) throws CenterRuntimeException {

		/*
		 * 封装批量数据节点
		 */
		if (tempElement.getQualifiedName().equals(this.xmlDetailsElementName)) {
			if (!fileTempFilePath.endsWith(File.separator)) {
				fileTempFilePath = fileTempFilePath + File.separator;
			}

			String batchTempFileName = fileTempFilePath + fileTempFileName;

			IBatchFileReader ibrData = BatchFilerWRFactory
					.creatIBatchFileReader(this.getBatchFileReaderName(),
							mapData.get(IBatchPacket.DATA_FILE_NAME_KEY)
									.toString());
			IBatchFileReader ibrTemp = BatchFilerWRFactory
					.creatIBatchFileReader(this.getBatchFileReaderName(),
							batchTempFileName);
			String detailTemp = ibrTemp.readNextDetail();

			List<Element> listTempElement2 = new LinkedList();

			while (ibrData.hasNextDetail()) {
				Map<String, Object> mapDetailData = ibrData
						.readNextDetailData(detailTemp);
				mapDetailData.putAll(mapData);
				/*
				 * 明细数据节点封装
				 */
				List<Element> listElement = tempElement.getChildren();
				Element tempElement2 = (Element) tempElement.clone();

				if (listElement != null && listElement.size() != 0) {
					Iterator<Element> iterElement = listElement.iterator();
					while (iterElement.hasNext()) {
						Element element = (Element) iterElement.next();
						// modified by yin.hl mapData参数改成了mapDetailData
						List<Element> element1 = encXMLBatchPackets(element,
								fileTempFilePath, fileTempFileName, mapDetailData);
						tempElement2.removeChild(element.getQualifiedName());
						tempElement2.addContent(element1);

					}
				}
				String strTempValue = tempElement2.getTextTrim();
				String strValue = this.dc.getData(mapData, strTempValue);
				if (strTempValue.length() > 0)
					tempElement2.setText(strValue);

				listTempElement2.add(tempElement2);
			}

			return listTempElement2;
		}

		/*
		 * 非批量节点封装
		 */
		List<Element> listElement = tempElement.getChildren();
		Element tempElement2 = (Element) tempElement.clone();

		if (listElement != null && listElement.size() != 0) {
			Iterator<Element> iterElement = listElement.iterator();
			while (iterElement.hasNext()) {
				Element element = (Element) iterElement.next();
				List<Element> element1 = encXMLBatchPackets(element,
						fileTempFilePath, fileTempFileName, mapData);
				tempElement2.removeChild(element.getQualifiedName());
				tempElement2.addContent(element1);

			}
		}
		String strTempValue = tempElement2.getTextTrim();
		String strValue = this.dc.getData(mapData, strTempValue);
		//yin.hl strValue.length 改成了 strTempValue.length
		if (strTempValue.length() > 0)
			tempElement2.setText(strValue);
		List<Element> listTempElement2 = new LinkedList();
		listTempElement2.add(tempElement2);

		return listTempElement2;
	}

	private synchronized String createBatchFileName() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return df.format(new Date()) + ".data";
	}
}
