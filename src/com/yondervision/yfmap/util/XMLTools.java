/**
 * 
 */
package com.yondervision.yfmap.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/**
 * @author LinXiaolong
 * 
 */
public class XMLTools {
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
	 * @throws YQZLException
	 */
	public static String getValueByXPath(Document dom, String strXPath)
			throws CenterRuntimeException {
		String text = null;
		Element element = getElementByXPath(dom, strXPath);
		text = element.getTextTrim();
		return text;
	}

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
	 * @throws YQZLException
	 */
	@SuppressWarnings("unchecked")
	public static Element getElementByXPath(Document dom, String strXPath)
			throws CenterRuntimeException {
		XPath xpath;
		Element element = null;
		try {
			xpath = XPath.newInstance(strXPath.replaceAll("\\\\", "/"));
			List list = xpath.selectNodes(dom);
			element = (Element) list.get(0);
		} catch (JDOMException e) {
			// throw new YQZLException(e, ErrorCodeDefinition.COMM_ERROR99,
			// "�ڱ�����ȡ·��[" + strXPath + "]����");
		} catch (Exception e1) {
			System.out.println(strXPath);
			// throw new YQZLException(e1, ErrorCodeDefinition.COMM_ERROR99,
			// "�ڱ�����ȡ·��[" + strXPath + "]����");
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
	 * @throws YQZLException
	 */
	public static Document getDomByFileName(String fileName)
			throws CenterRuntimeException {
		File file = new File(fileName);
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
	 * @return Document 对象
	 * @throws YQZLException
	 */
	public static Document getDomByString(String xmlMessage)
			throws CenterRuntimeException {
		InputStream is = new ByteArrayInputStream(xmlMessage.getBytes());
		Document jdom = getDomByFile(is);
		return jdom;
	}

	/**
	 * <pre>
	 * 从输入了多去Document
	 * </pre>
	 * 
	 * @param is
	 *            XML输入流
	 * @return Document对象
	 * @throws YQZLException
	 */
	public static Document getDomByFile(InputStream is)
			throws CenterRuntimeException {
		Document jdom = null;
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		try {
			jdom = builder.build(is);
		} catch (JDOMException e) {
			e.printStackTrace();
			// throw new YQZLException(e, ErrorCodeDefinition.BUILD_XML_ERR,
			// String.valueOf(is));
		} catch (IOException e) {
			e.printStackTrace();
			// throw new YQZLException(e, ErrorCodeDefinition.BUILD_XML_ERR,
			// String.valueOf(is));
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
	 * @throws YQZLException
	 */
	public static Document getDomByFile(File file)
			throws CenterRuntimeException {
		Document jdom = null;
		SAXBuilder builder = new SAXBuilder();
		builder.setIgnoringElementContentWhitespace(true);
		try {
			jdom = builder.build(file);
		} catch (JDOMException e) {
			// throw new YQZLException(e,
			// ErrorCodeDefinition.BUILD_XML_FILE_ERR, file.getName());
		} catch (IOException e) {
			// throw new YQZLException(e,
			// ErrorCodeDefinition.BUILD_XML_FILE_ERR, file.getName());
		}
		return jdom;
	}

	/**
	 * 获取pretty格式xml
	 * 
	 * @param xml
	 * @return
	 */
	public static String getPrettyFormatXml(String xml) {
		Document dom;
		try {
			dom = XMLTools.getDomByString(xml);
			XMLOutputter out = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setExpandEmptyElements(true);
			format.setEncoding("GBK");
			format.setLineSeparator("\n");
			out.setFormat(format);
			xml = out.outputString(dom);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	public static Map<String, Object> XmlString2Map(String xmlMessage) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (xmlMessage == null)
			return map;
		Document doc = getDomByString(xmlMessage);
		Element root = doc.getRootElement();
		for (Iterator iterator = root.getChildren().iterator(); iterator
				.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List list = e.getChildren();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.getChildren();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.getChildren().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName()
								.equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	public static String GetXMLFromMap(Map map, int iMode) {
		StringBuffer sb = new StringBuffer();

		if (map == null || map.size() == 0) {
			return "";
		}
		if (0 == iMode) {
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb.append("<root>\n");
		}
		// 为了方便观看结果,将有内嵌的排在后面

		Iterator iterr = map.keySet().iterator();

		while (iterr.hasNext()) {
			String keyString = (String) iterr.next();
			if (!(map.get(keyString) instanceof Map)) {
				sb.append("<" + keyString + ">");
				sb.append(map.get(keyString));
				sb.append("</" + keyString + ">\n");
			}
		}
		iterr = map.keySet().iterator();
		while (iterr.hasNext()) {
			String key = (String) iterr.next();
			if (map.get(key) instanceof Map) {
				if (key.indexOf("|!|") > 0) {
					sb.append("<" + key.substring(0, key.indexOf("|!|"))
							+ ">\n");
					sb.append(GetXMLFromMap((Map) map.get(key), iMode + 1));
					sb.append("</" + key.substring(0, key.indexOf("|!|"))
							+ ">\n");
				} else {
					sb.append("<" + key + ">\n");
					sb.append(GetXMLFromMap((Map) map.get(key), iMode + 1));
					sb.append("</" + key + ">\n");
				}
			}
		}
		if (0 == iMode)
			sb.append("</root>\n");
		return sb.toString();
	}
}
