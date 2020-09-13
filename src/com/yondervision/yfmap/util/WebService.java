package com.yondervision.yfmap.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

public class WebService {
	public static OMElement WebServiceClient(String url, String nameSpace, String methodName, 
			String paramName, String paramValue){
		 // 指定调用WebService的URL  
		EndpointReference targetEPR = new EndpointReference(url);
		Options options = new Options();
		options.setAction(nameSpace + methodName);// 调用接口方法  
		options.setTo(targetEPR);
		
		options.setProperty(HTTPConstants.CHUNKED, "false");//设置不受限制.
		ServiceClient sender = null;
		try {
			sender = new ServiceClient();
			sender.setOptions(options);
			OMFactory fac = OMAbstractFactory.getOMFactory();
			 // 命名空间，有时命名空间不增加没事，不过最好加上，因为有时有事，你懂的  
			OMNamespace omNs = fac.createOMNamespace(nameSpace, "");
			
			OMElement method = fac.createOMElement(methodName, omNs);
			String[] param = paramName.split(",");
			String[] value = paramValue.split("&");
			for(int i=0; i<param.length; i++){
				OMElement name = fac.createOMElement(param[i], omNs);// 设置入参名称  
				name.setText(value[i].trim());//设置入参值  
				method.addChild(name);
			}
			method.build();
			System.out.println("method：" + method.toString());
			return sender.sendReceive(method);
		} catch (AxisFault e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return null;
	}
}
