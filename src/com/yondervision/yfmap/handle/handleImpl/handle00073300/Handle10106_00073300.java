package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi10106Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.result.baoshan.QueueBean;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.XmlUtils;

public class Handle10106_00073300 implements CtrlHandleInter {
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi10106Form form = (AppApi10106Form)form1;
		
		System.out.println("YFMAP发往排队机进行对应网点任务列表及等待人数获取");
		System.out.println("网点编码===="+form.getWebsitecode());

		List<QueueBean> resultList = new ArrayList<QueueBean>();	

		String servicesName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "queue_getJobListOfCell_servicesName_"+form.getCenterId()).trim();
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("CellID", form.getWebsitecode());
		
		String returnResStr = "";
		returnResStr = callWebServicePost(paramMap, servicesName, contentType);
		System.out.println("对应网点任务列表及等待人数获取返回结果===="+returnResStr);
		if(returnResStr.contains("errorMessage")){
			
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "连接排队机超时...");	
			return modelMap;
		}
		resultList = XmlUtils.cellWaitModelXmlStrConvertMap(returnResStr, "CellWaitModel", "UTF-8");
		System.out.println("对应网点任务列表及等待人数获取返回结果==resultList.size()=="+resultList.size());
		for(int i = 0; i < resultList.size(); i++){
			QueueBean model = resultList.get(i);
			System.out.println("获取网点任务列表及等待人数 返回值序号【"+i+"】的业务id="+model.getJobid()+
					";业务名称="+model.getJobname()+";等待人数="+model.getWaitcount()+";是否预约业务="
					+model.getType()+";Result="+model.getResult()); 
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("result", resultList);
		return modelMap;
	}
	
//	public List<CellWaitModel> webServicesClient(String xml, String url, String nameSpace, String methodName, String servicesName) throws AxisFault{
//		List<CellWaitModel> cellWaitModelList = new ArrayList<CellWaitModel>();
//		ServiceClient sc = new ServiceClient();   
//		Options opts = new Options();   
////		//确定目标服务地址   
////		opts.setTo(new EndpointReference(url));   
////		//确定调用方法    
////		opts.setAction(servicesName);   
//		
//		opts.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
//		
//		EndpointReference epfs = new EndpointReference(url);
//		    opts.setTransportInProtocol(Constants.TRANSPORT_HTTP);
//		    opts.setTo(epfs);
//		    opts.setAction(servicesName);
//		    sc.setOptions(opts);
//
//		    
//		OMFactory fac = OMAbstractFactory.getOMFactory();   
//		//指定命名空间   
//		OMNamespace omNs = fac.createOMNamespace(nameSpace, "");
//		//指定方法   
//		OMElement method = fac.createOMElement(methodName,omNs);   
//		//指定方法的参数   
//		OMElement value = fac.createOMElement("CellID",omNs);    
//		value.setText(xml);
//		method.addChild(value);    
//
//		sc.setOptions(opts);   
//		//发送请求并并得到返回结果，注意参数生成方法的分析   
//		OMElement omElement = sc.sendReceive(method);   
//		System.out.println("发送请求并并得到返回结果omElement==="+omElement);
//		cellWaitModelList = getResults(omElement);
//		
//		return cellWaitModelList;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private List<CellWaitModel> getResults(OMElement element) throws AxisFault{
//		if (element == null) {
//	    	  return null;
//	     }
//
//		Iterator iterator = element.getChildElements();
//		List<CellWaitModel> list = new ArrayList<CellWaitModel>();
//		while (iterator.hasNext()){
//			OMNode omNode = (OMNode) iterator.next();
//			if (omNode.getType() == OMNode.ELEMENT_NODE){
//				OMElement omElement = (OMElement) omNode;
//				if (omElement.getLocalName().toLowerCase().equals("cellwaitmodel")){
//					CellWaitModel person = (CellWaitModel) BeanUtil.processObject(omElement,CellWaitModel.class, 
//							null, true,new DefaultObjectSupplier());
//					list.add(person);
//				}
//			}
//		}
//		return list;
//    }
//
//	// TODO 通过HttpClient调用webservice,可行
//	public static String callWebServiceHttpClient(String soapRequest, String serviceEpr, String contentType){
//        
//        PostMethod postMethod = new PostMethod(serviceEpr);
//        //设置POST方法请求超时
//        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
//        
//        try {
//            
//            byte[] b = soapRequest.getBytes("utf-8");
//            InputStream inputStream = new ByteArrayInputStream(b, 0, b.length);
//            RequestEntity re = new InputStreamRequestEntity(inputStream, b.length, contentType);
//            postMethod.setRequestEntity(re);
//            
//            HttpClient httpClient = new HttpClient();
//            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams(); 
//            // 设置连接超时时间(单位毫秒)
//            managerParams.setConnectionTimeout(30000);
//            // 设置读数据超时时间(单位毫秒)
//            managerParams.setSoTimeout(600000); 
//            int statusCode = httpClient.executeMethod(postMethod);
//            if (statusCode != HttpStatus.SC_OK)  
//                throw new IllegalStateException("调用webservice错误 : " + postMethod.getStatusLine()); 
//            
//            String soapRequestData =  postMethod.getResponseBodyAsString();
//            inputStream.close();
//            return soapRequestData;
//        } catch (UnsupportedEncodingException e) {
//            return "errorMessage : " + e.getMessage();
//        } catch (HttpException e) {
//            return "errorMessage : " + e.getMessage();
//        } catch (IOException e) {
//            return "errorMessage : " + e.getMessage();
//        }finally{
//             postMethod.releaseConnection(); 
//        }
//    }
	
	public static String callWebServicePost(HashMap<String, String> paramMap, String serviceEpr, String contentType){
        PostMethod postMethod = new PostMethod(serviceEpr);
        postMethod.addRequestHeader("Content-Type", contentType);
        //设置POST方法请求超时
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        
        String returnResStr = "";
        try {
        	postMethod.addParameter("CellID", paramMap.get("CellID"));
            
            HttpClient httpClient = new HttpClient();
            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams(); 
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(30000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(600000); 
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK)  
                throw new IllegalStateException("调用webservice错误 : " + postMethod.getStatusLine()); 
            
            returnResStr =  postMethod.getResponseBodyAsString();
            return returnResStr;
        } catch (UnsupportedEncodingException e) {
            return "errorMessage : " + e.getMessage();
        } catch (HttpException e) {
            return "errorMessage : " + e.getMessage();
        } catch (IOException e) {
            return "errorMessage : " + e.getMessage();
        }finally{
             postMethod.releaseConnection(); 
        }
    }
	
	public static void main(String[] str){
		Handle10106_00073300 obj = new Handle10106_00073300();
		
//		String params = "08750001";
//		String url = "http://yun.linker.com.cn:8081/AppInterface.asmx?wsdl";
//		String nameSpace = "http://linker.com.cn";
//		String methodName = "getJobListOfCell";
//		String servicesName = "http://linker.com.cn/getJobListOfCell";
//		
//		XmlUtils util = new XmlUtils();
//		Map<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("CellID", params);
//		String xml = util.GetXMLFromMap(paramMap, true, "paras");
//		
//		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取开始.....");
//		List<CellWaitModel> cellWaitModelList = new ArrayList<CellWaitModel>();
//		try {
//			cellWaitModelList = obj.webServicesClient(xml, url, nameSpace, methodName, servicesName);
//		} catch (AxisFault e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for(int i = 0; i < cellWaitModelList.size(); i++){
//			CellWaitModel model = cellWaitModelList.get(i);
//			System.out.println("获取网点任务列表及等待人数 返回值序号【"+i+"】的业务id="+model.getJobID()+
//					";业务名称="+model.getJobName()+";等待人数="+model.getWaitCount()+";是否预约业务="
//					+model.getBookingTag()+";Result="+model.getResult()); 
//		}
//		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取结束.....");
		
		//通过SOAP 1.2调用webservice,可行
//		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取开始.....");
//		String serviceEpr = "http://yun.linker.com.cn:8081/AppInterface.asmx";
//		String contentType = "text/xml; charset=utf-8";
//		String soapRequest = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\"><soap12:Body><getJobListOfCell xmlns=\"http://linker.com.cn/\"><CellID>08750001</CellID></getJobListOfCell></soap12:Body></soap12:Envelope>";
//		String resStr = callWebServiceHttpClient(soapRequest, serviceEpr, contentType);
//		System.out.println("返回结果===="+resStr);
//		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取结束.....");
		
		//通过HttpPost调用webservice,可行
		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取开始.....");
		String serviceEpr = "http://yun.linker.com.cn:8081/AppInterface.asmx/getJobListOfCell";
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("CellID", "08750001");
		String resStr = callWebServicePost(paramMap, serviceEpr, contentType);
		System.out.println("返回结果===="+resStr);
		System.out.println("开始尝试向排队机进行网点任务列表及等待人数获取结束.....");
	}
}
