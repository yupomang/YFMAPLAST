package com.yondervision.yfmap.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

import com.yondervision.yfmap.result.baoshan.FinishQueueNo;
import com.yondervision.yfmap.result.baoshan.QueueBean;

public class XmlUtils {  
	/**
	 * 
	 * @param xml
	 * @return
	 */
	public static Map<String,Object> xml2Map(String xml){
		System.out.println("==================2=============");
		System.out.println("YPMAP XmlUtils start");
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		if(xml==null||"".equals(xml)) return map;
		try {
			StringReader sr = new StringReader(xml);
			document = saxReader.read(sr);
			map = Dom2Map(document);
			sr.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		System.out.println("YPMAP XmlUtils end:"+map);
		return map;
	}
    /**
     *    
     * @param doc
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> Dom2Map(Document doc){   
        Map<String, Object> map = new LinkedHashMap<String, Object>();   
        if(doc == null)   
            return map;   
        Element root = doc.getRootElement();   
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {   
            Element e = (Element) iterator.next();   
            //System.out.println(e.getName());   
            List list = e.elements();   
            if(list.size() > 0){   
                map.put(e.getName(), Dom2Map(e));   
            }else  
                map.put(e.getName(), e.getText());   
        }   
        return map;   
    }   
       
    /**
     * 
     * @param e
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map Dom2Map(Element e){   
        Map map = new LinkedHashMap();   
        List list = e.elements();   
        if(list.size() > 0){   
            for (int i = 0;i < list.size(); i++) {   
                Element iter = (Element) list.get(i);   
                List mapList = new ArrayList();   
                   
                if(iter.elements().size() > 0){   
                    Map m = Dom2Map(iter);   
                    if(map.get(iter.getName()) != null){   
                        Object obj = map.get(iter.getName());   
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){   
                            mapList = new ArrayList();   
                            mapList.add(obj);   
                            mapList.add(m);   
                        }   
                        if(obj.getClass().getName().equals("java.util.ArrayList")){   
                            mapList = (List) obj;   
                            mapList.add(m);   
                        }   
                        map.put(iter.getName(), mapList);   
                    }else  
                        map.put(iter.getName(), m);   
                }   
                else{   
                    if(map.get(iter.getName()) != null){   
                        Object obj = map.get(iter.getName());   
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){   
                            mapList = new ArrayList();   
                            mapList.add(obj);   
                            mapList.add(iter.getText());   
                        }   
                        if(obj.getClass().getName().equals("java.util.ArrayList")){   
                            mapList = (List) obj;   
                            mapList.add(iter.getText());   
                        }   
                        map.put(iter.getName(), mapList);   
                    }else  
                        map.put(iter.getName(), iter.getText());   
                }   
            }   
        }else  
            map.put(e.getName(), e.getText());   
        return map;   
    }   

	public static String formateXml(Document document,String encoding) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		format.setTrimText(false);
		StringWriter sw = new StringWriter();
		String value = null;
		try {
			XMLWriter writer = new XMLWriter(sw, format);
			writer.write(document);
			value = sw.toString();
			writer.close();
			sw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String formateXml(String xml,String encoding) {
		SAXReader saxReader = new SAXReader();
		Document document = null;
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding(encoding);
		format.setTrimText(false);
		StringWriter sw = new StringWriter();
		String value = null;
		StringReader sr = null;
		try {
			sr = new StringReader(xml);
			document = saxReader.read(sr);
			XMLWriter writer = new XMLWriter(sw, format);
			writer.write(document);
			value = sw.toString();
			writer.close();
			sr.close();
			sw.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String GetXMLFromMap(Map<? extends Object,? extends Object> map, boolean iMode) {
		StringBuffer sb = new StringBuffer();

		if (map == null || map.size() == 0) {
			return "";
		}
		if (iMode) {
			sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
			sb.append("<root>");
		}
		Iterator<?> iterr = map.keySet().iterator();
		while (iterr.hasNext()) {
			String key = (String) iterr.next();
			if (!(map.get(key) instanceof Map<?,?>)&&!((map.get(key)) instanceof Collection<?>)) {
				sb.append("<" + key + ">");
				sb.append(map.get(key));
				sb.append("</" + key + ">");
			}else if(((map.get(key)) instanceof Collection<?>)){
				Collection<?> collection = (Collection<?>)map.get(key);
				for(Object object : collection){
					if(object instanceof Map<?,?>){
						sb.append("<" + key + ">");
						sb.append(GetXMLFromMap((Map<?,?>) object, false));
						sb.append("</" + key + ">");
					}else{
						sb.append("<" + key + ">");
						sb.append(object.toString());	
						sb.append("</" + key+ ">");
					}
				}
			}
			if (map.get(key) instanceof Map<?,?>) {
				if (key.indexOf("|!|") > 0) {
					sb.append("<" + key.substring(0, key.indexOf("|!|"))+ ">");
					sb.append(GetXMLFromMap((Map<?,?>) map.get(key), false));
					sb.append("</" + key.substring(0, key.indexOf("|!|"))+ ">");
				} else {
					sb.append("<" + key + ">");
					sb.append(GetXMLFromMap((Map<?,?>) map.get(key), false));
					sb.append("</" + key + ">");
				}
			}
		}
		if (iMode)
			sb.append("</root>");
		return sb.toString();
	}
	
	public static String GetXMLFromMap(Map<? extends Object,? extends Object> map, boolean iMode,String rootName) {
		StringBuffer sb = new StringBuffer();

		if (map == null || map.size() == 0) {
			return "";
		}
		if (iMode) {
			sb.append("<?xml version=\"1.0\" encoding=\"gb2312\"?>");
			sb.append("<"+rootName+">");
		}

		Iterator<?> iterr = map.keySet().iterator();
		while (iterr.hasNext()) {
			String key = (String) iterr.next();
			if (!(map.get(key) instanceof Map<?,?>)&&!((map.get(key)) instanceof Collection<?>)) {
				sb.append("<" + key + ">");
				sb.append(map.get(key));
				sb.append("</" + key + ">");
			}else if(((map.get(key)) instanceof Collection<?>)){
				Collection<?> collection = (Collection<?>)map.get(key);
				for(Object object : collection){
					if(object instanceof Map<?,?>){
						sb.append("<" + key + ">");
						sb.append(GetXMLFromMap((Map<?,?>) object, false));
						sb.append("</" + key + ">");
					}else{
						sb.append("<" + key + ">");
						sb.append(object.toString());	
						sb.append("</" + key+ ">");
					}
				}
			}
			if (map.get(key) instanceof Map<?,?>) {
				if (key.indexOf("|!|") > 0) {
					sb.append("<" + key.substring(0, key.indexOf("|!|"))+ ">");
					sb.append(GetXMLFromMap((Map<?,?>) map.get(key), false));
					sb.append("</" + key.substring(0, key.indexOf("|!|"))+ ">");
				} else {
					sb.append("<" + key + ">");
					sb.append(GetXMLFromMap((Map<?,?>) map.get(key), false));
					sb.append("</" + key + ">");
				}
			}
		}
		if (iMode)
			sb.append("</"+rootName+">");
		return sb.toString();
	}
	
	// xml 解析 成map
	public static HashMap<String, String> xmlStrConvertMap(String xmlStr, String encode) throws DocumentException{
        //String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><QueueNoModel xmlns=\"http://linker.com.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><TicketNo>A008</TicketNo><WaitNo>7</WaitNo><GetTime>2016-01-11 05:23:57</GetTime></QueueNoModel>";
		InputSource in = new InputSource(new StringReader(xmlStr));
        in.setEncoding(encode);  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(in);  
        Element root = document.getRootElement();  
        List<Element> elements = root.elements(); 
        
        HashMap<String, String> returnMap = new HashMap<String, String>();
        for(Iterator<Element> it = elements.iterator();it.hasNext();){  
            Element element = it.next();  
            System.out.println(element.getName()+" : "+element.getTextTrim());
            returnMap.put(element.getName(), element.getTextTrim());
        }  
        return returnMap;
	}
	
	@SuppressWarnings("unchecked")
	public static List<QueueBean> cellWaitModelXmlStrConvertMap(String xmlStr, String rootElements, String encode) throws DocumentException{
		List<QueueBean> resultList = new ArrayList<QueueBean>();
		
		InputSource in = new InputSource(new StringReader(xmlStr));
        in.setEncoding(encode);  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(in);  
        Element root = document.getRootElement();  
        List<Element> elements = root.elements(rootElements);  
        
        for(Iterator<Element> it = elements.iterator();it.hasNext();){ 
        	QueueBean model = new QueueBean();
            Element e_pe = (Element) it.next();
            model.setWebsitecode(e_pe.element("CellID").getText());
            model.setJobid(e_pe.element("JobID").getText());
            model.setJobname(e_pe.element("JobName").getText());
            model.setResult(e_pe.element("Result").getText());
            model.setWaitcount(e_pe.element("WaitCount").getText());
            model.setType(e_pe.element("BookingTag").getText());
            resultList.add(model);
        }  
        return resultList;
	}
	
	@SuppressWarnings("unchecked")
	public static List<FinishQueueNo> finishQueueXmlStrConvertMap(String xmlStr, String rootElements, String encode) throws DocumentException{
		List<FinishQueueNo> resultList = new ArrayList<FinishQueueNo>();
		
		InputSource in = new InputSource(new StringReader(xmlStr));
        in.setEncoding(encode);  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(in);  
        Element root = document.getRootElement();  
        List<Element> elements = root.elements(rootElements);  
        
        for(Iterator<Element> it = elements.iterator();it.hasNext();){ 
        	FinishQueueNo model = new FinishQueueNo();
            Element e_pe = (Element) it.next();
            model.setCellID(e_pe.element("CellID").getText());
            model.setTicketNo(e_pe.element("TicketNo").getText());
            model.setJobName(e_pe.element("JobName").getText());
            model.setWinNo(e_pe.element("WinNo").getText());
            model.setGetTicketTime(e_pe.element("GetTicketTime").getText());
            model.setCallTicketTime(e_pe.element("CallTicketTime").getText());
            model.setFinishTicketTime(e_pe.element("FinishTicketTime").getText());
            // TODO,接口暂时未提供，ui上也暂未看见，影响程序执行，先注销
           // model.setGetMethod(e_pe.element("GetMethod").getText());
            resultList.add(model);
        }  
        return resultList;
	}
/*	public static void main(String[] args) throws Exception { 
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><QueueNoModel xmlns=\"http://linker.com.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><TicketNo>A008</TicketNo><WaitNo>7</WaitNo><GetTime>2016-01-11 05:23:57</GetTime></QueueNoModel>";
//		String encode = "UTF-8";
//		HashMap<String, String> returnMap = xmlStrConvertMap(xml, encode);
//		 System.out.println(returnMap.get("CellID"));
//		 System.out.println(returnMap.get("JobID"));
//		 System.out.println(returnMap.get("JobName"));
//		 System.out.println(returnMap.get("Result"));
//		 System.out.println(returnMap.get("TicketNo"));
//		 System.out.println(returnMap.get("WaitNo"));
//		 System.out.println(returnMap.get("GetTime"));
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ArrayOfCellWaitModel xmlns=\"http://linker.com.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CellWaitModel><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><WaitCount>7</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>2</JobID><JobName>公积金贷款业务</JobName><Result>SUCCESS</Result><WaitCount>2</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>3</JobID><JobName>预约业务</JobName><Result>SUCCESS</Result><WaitCount>0</WaitCount><BookingTag>0</BookingTag></CellWaitModel></ArrayOfCellWaitModel>";
		//String xml = "<ArrayOfCellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><WaitCount>7</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>2</JobID><JobName>公积金贷款业务</JobName><Result>SUCCESS</Result><WaitCount>2</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>3</JobID><JobName>预约业务</JobName><Result>SUCCESS</Result><WaitCount>0</WaitCount><BookingTag>0</BookingTag></CellWaitModel></ArrayOfCellWaitModel>";

		String encode = "UTF-8";
		
		List<QueueBean> resultList  = cellWaitModelXmlStrConvertMap(xml, "CellWaitModel", encode);
		for(int i = 0; i < resultList.size(); i++){
			QueueBean model = resultList.get(i);
			System.out.println("获取网点任务列表及等待人数 返回值序号【"+i+"】的业务id="+model.getJobid()+
					";业务名称="+model.getJobname()+";等待人数="+model.getWaitcount()+";是否预约业务="
					+model.getType()+";Result="+model.getResult()); 
		}
	}*/
	
	public static void main(String[] args) throws DocumentException {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ArrayOfCellWaitModel xmlns=\"http://linker.com.cn/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><CellWaitModel><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><WaitCount>7</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>2</JobID><JobName>公积金贷款业务</JobName><Result>SUCCESS</Result><WaitCount>2</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>3</JobID><JobName>预约业务</JobName><Result>SUCCESS</Result><WaitCount>0</WaitCount><BookingTag>0</BookingTag></CellWaitModel></ArrayOfCellWaitModel>";
		//String xml = "<ArrayOfCellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>1</JobID><JobName>公积金提取业务</JobName><Result>SUCCESS</Result><WaitCount>7</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>2</JobID><JobName>公积金贷款业务</JobName><Result>SUCCESS</Result><WaitCount>2</WaitCount><BookingTag>0</BookingTag></CellWaitModel><CellWaitModel><CellID>08750001</CellID><JobID>3</JobID><JobName>预约业务</JobName><Result>SUCCESS</Result><WaitCount>0</WaitCount><BookingTag>0</BookingTag></CellWaitModel></ArrayOfCellWaitModel>";

		String encode = "UTF-8";
		
		List<QueueBean> resultList  = cellWaitModelXmlStrConvertMap(xml, "CellWaitModel", encode);
		for(int i = 0; i < resultList.size(); i++){
			QueueBean model = resultList.get(i);
			System.out.println("获取网点任务列表及等待人数 返回值序号【"+i+"】的业务id="+model.getJobid()+
					";业务名称="+model.getJobname()+";等待人数="+model.getWaitcount()+";是否预约业务="
					+model.getType()+";Result="+model.getResult()); 
		}
	}
}  

