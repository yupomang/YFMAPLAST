package com.yondervision.yfmap.handle.handleImpl.handle00073300;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi10107Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.result.baoshan.QueueTicketInfo;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.XmlUtils;

public class Handle10107_00073300 implements CtrlHandleInter {
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi10107Form form = (AppApi10107Form)form1;
		
		System.out.println("YFMAP发往排队机进行排队取号");
		System.out.println("网点编码===="+form.getWebsitecode());
		System.out.println("业务ID===="+form.getJobid());
		System.out.println("身份证号===="+form.getIdcardNumber());
		System.out.println("取号方式===="+form.getGetMethod());

//		// TODO
//		form.setIdcardNumber("1234567890");
//		System.out.println("手工重设身份证号===="+form.getIdcardNumber());
		
		QueueTicketInfo returnResult = new QueueTicketInfo();

		String servicesName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "queue_getNewQueue_servicesName_"+form.getCenterId()).trim();
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
				
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("CellID", form.getWebsitecode());
		paramMap.put("JobID", form.getJobid());
		paramMap.put("PersonID", form.getIdcardNumber());
		paramMap.put("GetMethod", form.getGetMethod());
    	
		String returnResStr = "";
		returnResStr = callWebServicePost(paramMap, servicesName, contentType);
		System.out.println("排队机进行排队取号返回结果===="+returnResStr);
		if(returnResStr.contains("errorMessage")){
			
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "连接排队机超时...");	
			return modelMap;
		}
		HashMap<String, String> returnMap = new HashMap<String, String>();
		returnMap = XmlUtils.xmlStrConvertMap(returnResStr, "UTF-8");
		System.out.println("排队机进行排队取号返回结果==returnMap.size()=="+returnMap.size());
		if(!returnMap.isEmpty() && returnMap.size() != 0){
			if("SUCCESS".equals(returnMap.get("Result"))){
				returnResult.setWebsitecode(returnMap.get("CellID"));
				returnResult.setJobid(returnMap.get("JobID"));
				returnResult.setJobname(returnMap.get("JobName"));
				returnResult.setResult(returnMap.get("Result"));
				returnResult.setTicketNo(returnMap.get("TicketNo"));
				returnResult.setWaitcount(returnMap.get("WaitNo"));
				returnResult.setGetTime(returnMap.get("GetTime"));
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", returnMap.get("Result"));
				return modelMap;
			}
		}else{
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "获取排队号码失败");	
			return modelMap;
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("result", returnResult);
		return modelMap;
	}
	
	public static String callWebServicePost(HashMap<String, String> paramMap, String serviceEpr, String contentType){
        PostMethod postMethod = new PostMethod(serviceEpr);
        postMethod.addRequestHeader("Content-Type", contentType);
        //设置POST方法请求超时
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        
        try {
        	postMethod.addParameter("CellID", paramMap.get("CellID"));
        	postMethod.addParameter("JobID", paramMap.get("JobID"));
        	postMethod.addParameter("PersonID", paramMap.get("PersonID"));
        	postMethod.addParameter("GetMethod", paramMap.get("GetMethod"));
            
            HttpClient httpClient = new HttpClient();
            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams(); 
            // 设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(30000);
            // 设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(600000); 
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK)  
                throw new IllegalStateException("调用webservice错误 : " + postMethod.getStatusLine()); 
            
            String soapRequestData =  postMethod.getResponseBodyAsString();
            return soapRequestData;
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
		
		//通过HttpPost调用webservice,可行
		System.out.println("开始尝试向排队机进行排队取号开始.....");
		String serviceEpr = "http://yun.linker.com.cn:8081/AppInterface.asmx/getNewQueue";
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("CellID", "08750001");
		paramMap.put("JobID", "1");
		paramMap.put("PersonID", "220120197110101231");//1234567890
		paramMap.put("GetMethod", "02");
		String resStr = callWebServicePost(paramMap, serviceEpr, contentType);
		System.out.println("返回结果===="+resStr);
		System.out.println("开始尝试向排队机进行排队取号结束.....");
	}
}
