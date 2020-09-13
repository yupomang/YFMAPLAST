package com.yondervision.yfmap.socket;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;

/** 
* @ClassName: XmlTool 
* @Description: TODO
* @author Caozhongyan
* @date Feb 5, 2015 9:45:14 AM   
* 
*/ 
public class XmlTool {
	
	
	
	/**
	* <pre> 取得请求报文头 </pre>
	* @param @param req
	* @param @return
	* @param @throws YQZLException    
	*/
	public ReqHeadbean getReqHeadbean(String xmlMessage) throws CenterRuntimeException{
		SAXReader saxReader = new SAXReader();
		Document document = null;
		ReqHeadbean rhb = new ReqHeadbean();
		try {
			StringReader sr = new StringReader(xmlMessage);
			document = saxReader.read(sr);
			Element root = document.getRootElement();
			Element head = root.element("head");//请求头			
			Element transCode = (Element) head.selectNodes("transCode").get(0);
			rhb.setTransCode(transCode.getTextTrim());			
			Element sendDate = (Element) head.selectNodes("sendDate").get(0);
			rhb.setSendDate(transCode.getTextTrim());
			Element sendTime = (Element) head.selectNodes("sendTime").get(0);
			rhb.setSendTime(sendTime.getTextTrim());
			Element sendSeqno = (Element) head.selectNodes("sendSeqno").get(0);
			rhb.setSendSeqno(sendSeqno.getTextTrim());
			Element key = (Element) head.selectNodes("key").get(0);
			rhb.setKey(key.getTextTrim());
			Element longinip = (Element) head.selectNodes("longinip").get(0);
			rhb.setLonginip(longinip.getTextTrim());
			Element buzType = (Element) head.selectNodes("buzType").get(0);
			rhb.setBuzType(buzType.getTextTrim());			
			sr.close();
		} catch (DocumentException e) {	
			e.printStackTrace();
			CenterRuntimeException yfmape = new CenterRuntimeException("","解析核心报文头HEAD异常");
			throw yfmape;
		}
		return rhb;
	}
	
	/**
	* <pre> 描述 </pre>
	* @param @param xml
	* @param @param lablename
	* @param @return
	* @param @throws YQZLException    
	*/
	public String getBodyLable(String xml,String lablename) throws CenterRuntimeException{
		SAXReader saxReader = new SAXReader();
		Document document = null;		
		String valueString = null;
		try {
			StringReader sr = new StringReader(xml);
			document = saxReader.read(sr);
			Element root = document.getRootElement();			
			Element body = root.element("body");//请求体
			Element reqflag = (Element) body.selectNodes(lablename).get(0);
			valueString = reqflag.getTextTrim();			
			sr.close();
		} catch (DocumentException e) {
			e.printStackTrace();
			CenterRuntimeException yqzle = new CenterRuntimeException("","报文体中无["+lablename+"]标签");
			throw yqzle;
		}	
		return valueString;
	}
	
	/**
	* <pre> 描述 </pre>
	* @param @param xml
	* @param @param lablename
	* @param @return
	* @param @throws YQZLException    
	*/
	public String getHeadLable(String xml,String lablename) throws CenterRuntimeException{
		SAXReader saxReader = new SAXReader();
		Document document = null;
		ReqHeadbean rhb = new ReqHeadbean();
		String valueString = null;
		try {
			StringReader sr = new StringReader(xml);
			document = saxReader.read(sr);
			Element root = document.getRootElement();			
			Element body = root.element("head");//请求体
			Element reqflag = (Element) body.selectNodes(lablename).get(0);
			valueString = reqflag.getTextTrim();
			sr.close();
		} catch (DocumentException e) {
			e.printStackTrace();
			CenterRuntimeException yqzle = new CenterRuntimeException("999999","报文头中无["+lablename+"]标签");
			throw yqzle;
		}	
		return valueString;
	}
	
	/**
	* <pre> CSP返回报文封装 </pre>
	* @param @param rh
	* @param @param ans_code
	* @param @param ans_info
	* @param @param particular
	* @param @return    
	*/
	public String getReturnBankXML(ReqHeadbean rh,String ans_code,String ans_info,String particular){
		StringBuffer msg = new StringBuffer();
//		msg.append("<?xml version=\"1.0\" encoding = \"GBK\"?><root>");
//		msg.append("<head><reqflag>1</reqflag><msgtype>"+rh.getmsgtype()+"</msgtype>");
//		msg.append("<tr_code>"+rh.gettr_code()+"</tr_code>");
//		msg.append("<corp_no>"+rh.getcorp_no()+"</corp_no>");
//		msg.append("<oreq_no>"+rh.getreq_no()+"</oreq_no>");
//		msg.append("<tr_acdt>"+rh.gettr_acdt()+"</tr_acdt>");
//		msg.append("<tr_time>"+rh.gettr_time()+"</tr_time>");
//		msg.append("<ans_code>"+ans_code+"</ans_code>");
//		msg.append("<ans_info>"+ans_info+"</ans_info>");
//		msg.append("<particular_code>"+particular+"</particular_code>");
//		msg.append("<particular_info>"+ans_info+"</particular_info><reserved></reserved></head>");
//		if("A00013".equals(rh.gettr_code())){
//			//有需要封装报文体
//		}else if("A00014".equals(rh.gettr_code())){
//			//有需要封装报文体
//		}else if("A00021".equals(rh.gettr_code())){
//			msg.append("<body><free_use1></free_use1><free_use2></free_use2><free_use3></free_use3>");
//			msg.append("<free_use4></free_use4><free_use5></free_use5><free_use6></free_use6></body>");
//		}
//		msg.append("</root>");
		return msg.toString();
	}
	
	public String getFilePath(String bankcode){
		if("01".equals(bankcode)){
			return "icbc/";
		}else if("02".equals(bankcode)){
			return "abc/";
		}else if("03".equals(bankcode)){
			return "boc/";
		}else if("04".equals(bankcode)){
			return "ccb/";
		}else if("05".equals(bankcode)){
			return "bcm/";
		}else if("08".equals(bankcode)){
			return "psbc/";
		}else if("10".equals(bankcode)){
			return "bohb/";
		}else if("60".equals(bankcode)){
			return "cmbc/";
		}else if("71".equals(bankcode)){
			return "cib/";
		}else if("72".equals(bankcode)){
			return "cmb/";
		}
		return "";
	}
	

//	public static void main(String[] args){
//		String xxString = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><reqflag>0</reqflag><msgtype>08</msgtype><tr_code>A00021</tr_code><corp_no>0300</corp_no><user_no>0300</user_no><req_no>SJZ_RZ2012-12-19-31</req_no><oreq_no>0311031212190000040G</oreq_no><tr_acdt>2012-12-19</tr_acdt><tr_time>202347</tr_time><channel>20</channel><sign>03</sign><key></key><reserved></reserved></head><body><ogl_date1>2012-12-19</ogl_date1><ogl_date>2012-12-14</ogl_date><opertype>05</opertype><sumamt>386.00</sumamt><sumrec>50</sumrec><filename>RzRet-03-2012-12-19-31.xml</filename><md5></md5><retcode>0000</retcode><retmary></retmary></body></root>";
//		xmlTool_sjz too=new xmlTool_sjz();
//		try {
//			System.out.println(too.getHeadLable(xxString, "sign"));
//			System.out.println(too.getBodyLable(xxString, "filename"));
//		} catch (YQZLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
}
