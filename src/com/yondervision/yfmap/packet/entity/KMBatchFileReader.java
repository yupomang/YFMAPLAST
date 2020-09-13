/**
 * 
 */
package com.yondervision.yfmap.packet.entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.packet.IBatchFileReader;
import com.yondervision.yfmap.packet.IDataConversion;
import com.yondervision.yfmap.util.XMLTools;

/**
 * @author LinXiaolong
 *
 */
public class KMBatchFileReader implements IBatchFileReader {
	
	private static final String DETAILS_BEGIN = "<detail>";
	
	private static final String DETAILS_END = "</detail>";
	
	private static final String HEAD_BEGIN = "<head>";
	
	private static final String HEAD_END = "</head>";
	
	private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	
	private IDataConversion dc;
	
	private String batchHead;
	
	private BufferedReader br;

	private boolean hasNextDetail = false;
	
	public IDataConversion getDc() {
		return dc;
	}

	public void setDc(IDataConversion dc) {
		this.dc = dc;
	}


	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#close()
	 */
	public void close() {
		try {
			if (this.br != null) {
				this.br.close();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#getBatchHead()
	 */
	public String getBatchHead() throws CenterRuntimeException {
		return this.batchHead;
	}

	
	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#hasNextDetail()
	 */
	public boolean hasNextDetail() throws CenterRuntimeException {
		if (this.hasNextDetail) {
			return true;
		}
		
		String line = null;
		try {
			while ( (line = br.readLine()) != null) {
				if (line.trim().equals(DETAILS_BEGIN)) {
					this.hasNextDetail = true;
					return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：判断是否还有明细数据异常。");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#readNextDetail()
	 */
	public String readNextDetail() throws CenterRuntimeException {
		String strNextDetail = null;
		if (this.hasNextDetail()) {
			try {
				StringBuffer sbNextDetail = new StringBuffer();
				sbNextDetail.append(DETAILS_BEGIN);
				String line = null;
				while ((line = this.br.readLine()) != null) {
					sbNextDetail.append(line);
					if (line.trim().equals(DETAILS_END)) {
						break;
					}
				}
				strNextDetail = sbNextDetail.toString();
				this.hasNextDetail  = false;
			} catch (IOException e) {
				e.printStackTrace();
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：读取下一条明细数据异常。");
			}
		}
		
		return strNextDetail;
	}

	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#readNextDetailData(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> readNextDetailData(String detailTemp)
			throws CenterRuntimeException {
		String nextDetailData = this.readNextDetail();
		if (nextDetailData == null) {
			return null;
		}
		
		Map<String, Object> mapData = new HashMap<String, Object>();
		
		Document domTemp = XMLTools.getDomByString(XML_HEAD + detailTemp);
		Document domData = XMLTools.getDomByString(XML_HEAD + nextDetailData);
		
		Element eleTempRoot = domTemp.getRootElement();
		Element eleDataRoot = domData.getRootElement();
		
		List<Element> listData = eleDataRoot.getChildren();
		Iterator<Element> iterData = listData.iterator();
		while (iterData.hasNext()) {
			Element eleData = (Element) iterData.next();
			Element eleTemp = eleTempRoot.getChild(eleData.getQualifiedName());
			this.getDc().putData(mapData, eleTemp.getTextTrim(), eleData.getTextTrim());
		}
		
		return mapData;
	}

	/* (non-Javadoc)
	 * @see com.yondervison.packet.IBatchFileReader#setBatchFileName(java.lang.String)
	 */
	public void setBatchFileName(String fileName) throws CenterRuntimeException {
		try {
			this.br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：设置批量文件名异常。");
		}

		String line = null;
		StringBuffer sbHead = new StringBuffer();
		boolean isHead = false;
		try {
			while ((line=this.br.readLine()) != null) {
				if (isHead) {
					sbHead.append(line);
				}
				if (line.equals(HEAD_BEGIN)) {
					sbHead.append(line);
					isHead = true;
				}
				if (line.equals(HEAD_END)) {
					this.batchHead = sbHead.toString();
					break;
				}
			}
			if (!isHead) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：设置批量文件名异常。");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：设置批量文件名异常。");
		}
	}

}
