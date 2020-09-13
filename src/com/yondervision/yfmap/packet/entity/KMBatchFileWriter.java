/**
 * 
 */
package com.yondervision.yfmap.packet.entity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.packet.IBatchFileWriter;
import com.yondervision.yfmap.packet.IDataConversion;
import com.yondervision.yfmap.util.XMLTools;

/**
 * @author LinXiaolong
 * 
 */
public class KMBatchFileWriter implements IBatchFileWriter {

	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

	private static final String FILE_DATA_BEGIN = "<root>";

	private static final String FILE_DATA_END = "</root>";

	private IDataConversion dc;

	private BufferedWriter bw;

	public IDataConversion getDc() {
		return dc;
	}

	public void setDc(IDataConversion dc) {
		this.dc = dc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.packet.IBatchFileWriter#close()
	 */
	public void close() {
		try {
			if (this.bw != null) {
				this.bw.write(FILE_DATA_END);
				this.bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchFileWriter#setBatchFileName(java.lang.String
	 * )
	 */
	public void setBatchFileName(String fileName) throws CenterRuntimeException {
		try {
			this.bw = new BufferedWriter(new FileWriter(fileName));
			this.bw.write(XML_HEAD + "\n" + FILE_DATA_BEGIN + "\n");
		} catch (IOException e) {
			e.printStackTrace();
//			throw new YQZLException(e, "");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchFileWriter#writeDetail(java.lang.String,
	 * java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public String writeDetail(String detailTemp,
			Map<String, Object> mapDetailData) throws CenterRuntimeException {
		String strDetailData = detailTemp;

		Document domTemp = XMLTools.getDomByString(XML_HEAD + detailTemp);
		Element eleTempRoot = domTemp.getRootElement();
		List<Element> listTemp = eleTempRoot.getChildren();
		Iterator<Element> iterTemp = listTemp.iterator();
		while (iterTemp.hasNext()) {
			Element eleTemp = (Element) iterTemp.next();
			String strTemp = eleTemp.getTextTrim();
			String strValue = this.dc.getData(mapDetailData, strTemp);
			strDetailData = strDetailData.replace(strTemp, strValue);
		}
		
		this.writeDetail(strDetailData);

		return strDetailData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yondervison.packet.IBatchFileWriter#writeDetail(java.lang.String)
	 */
	public void writeDetail(String detailValue) throws CenterRuntimeException {
		try {
			this.bw.write(detailValue + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：将一笔明细数据写入批量文件时异常。");
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.packet.IBatchFileWriter#writeHead(java.lang.String,
	 * java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public String writeHead(String headTemp, Map<String, Object> mapHeadData)
			throws CenterRuntimeException {
		String strHeadData = headTemp;

		Document domTemp = XMLTools.getDomByString(XML_HEAD + headTemp);
		Element eleTempRoot = domTemp.getRootElement();
		List<Element> listTemp = eleTempRoot.getChildren();
		Iterator<Element> iterTemp = listTemp.iterator();
		while (iterTemp.hasNext()) {
			Element eleTemp = (Element) iterTemp.next();
			String strTemp = eleTemp.getTextTrim();
			String strValue = this.dc.getData(mapHeadData, strTemp);
			strHeadData = strHeadData.replace(strTemp, strValue);
		}

		this.writeHead(strHeadData);

		return strHeadData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yondervison.packet.IBatchFileWriter#writeHead(java.lang.String)
	 */
	public void writeHead(String headValue) throws CenterRuntimeException {
		try {
			this.bw.write(headValue + "\n");
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：写入文件头异常。");
		}
	}

}
