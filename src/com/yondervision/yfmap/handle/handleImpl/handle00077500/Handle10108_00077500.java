package com.yondervision.yfmap.handle.handleImpl.handle00077500;

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
import com.yondervision.yfmap.form.AppApi10108Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.result.baoshan.FinishQueueNo;
import com.yondervision.yfmap.result.baoshan.MyHisQueueBean;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.XmlUtils;

public class Handle10108_00077500 implements CtrlHandleInter {
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi10108Form form = (AppApi10108Form)form1;
		
		System.out.println("YFMAP发往排队机进行我的排号记录获取");
		System.out.println("身份证号===="+form.getIdcardNumber());
		
		// TODO 
//		form.setIdcardNumber("1234567890");
//		System.out.println("手工重设身份证号===="+form.getIdcardNumber());
		List<MyHisQueueBean> queuelist = new ArrayList<MyHisQueueBean>();

		String servicesName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "queue_queryPersonHistory_servicesName_"+form.getCenterId()).trim();
		String queueStatusServicesName = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "queue_queryQueueStatus_servicesName_"+form.getCenterId()).trim();
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
		
		List<FinishQueueNo> finishQueueList = new ArrayList<FinishQueueNo>();
		
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("PersonID", form.getIdcardNumber());
    	
		String queueResStr = "";
		queueResStr = callWebServicePost(paramMap, servicesName, contentType);
		System.out.println("我的排号记录返回结果===="+queueResStr);
		if(queueResStr.contains("errorMessage")){
			
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "连接排队机超时...");	
			return modelMap;
		}
		finishQueueList = XmlUtils.finishQueueXmlStrConvertMap(queueResStr, "FinishQueueNo", "UTF-8");
		
		HashMap<String, String> returnMap = new HashMap<String, String>();
		
		if(finishQueueList.size() == 0 || finishQueueList == null){
			System.out.println("没有我的排号记录....");
			modelMap.clear();
			modelMap.put("recode", "999998");
			modelMap.put("msg", "没有我的排号记录");	
			return modelMap;
		}
		
		FinishQueueNo finishQueueNo = new FinishQueueNo();
		for(int i = 0; i < finishQueueList.size(); i++){
			finishQueueNo = new FinishQueueNo();
			finishQueueNo = finishQueueList.get(i);
			
			String ticketNo = finishQueueNo.getTicketNo();
			String cellId = finishQueueNo.getCellID();
			
			
			System.out.println("YFMAP发往排队机进行我的排号记录获取中，获取对应票号的状态开始...");
			System.out.println("网点编号===="+cellId);
			System.out.println("票号===="+ticketNo);
			System.out.println("身份证号===="+form.getIdcardNumber());
			
			paramMap = new HashMap<String, String>();
			// TODO 
			paramMap.put("CellID", cellId);
			paramMap.put("TicketNo", ticketNo);
			paramMap.put("PersonID", form.getIdcardNumber());
	    	
			String tickNoStatusResStr = "";
			tickNoStatusResStr = callWebServicePostGetStatus(paramMap, queueStatusServicesName, contentType);
			System.out.println("我的排号记录获取对应票号的状态返回结果===="+tickNoStatusResStr);
			if(tickNoStatusResStr.contains("errorMessage")){
				
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "连接排队机超时...");	
				return modelMap;
			}
			returnMap = new HashMap<String, String>();
			returnMap = XmlUtils.xmlStrConvertMap(tickNoStatusResStr, "UTF-8");
			System.out.println("排队机进行排队取号返回结果==returnMap.size()=="+returnMap.size());
			if(!returnMap.isEmpty() && returnMap.size() != 0){
				if("SUCCESS".equals(returnMap.get("Result"))){
					
					MyHisQueueBean queueBean = new MyHisQueueBean();
					queueBean.setWebsitecode(returnMap.get("CellID"));
					queueBean.setWebsitename(returnMap.get("CellName"));
					queueBean.setTicketNo(ticketNo);
					queueBean.setJobid(returnMap.get("JobID"));
					queueBean.setJobname(returnMap.get("JobName"));
					queueBean.setWinno(finishQueueNo.getWinNo());
					queueBean.setGettickettime(finishQueueNo.getGetTicketTime());
					queueBean.setCalltickettime(finishQueueNo.getCallTicketTime());
					queueBean.setFinishtickettime(finishQueueNo.getFinishTicketTime());
					queueBean.setGetmethod(finishQueueNo.getGetMethod());
					//String status = PropertiesReader.getProperty("yingshe.properties", "queueStatus"+returnMap.get("Status")+form.getCenterId()).trim();
					queueBean.setStatus(returnMap.get("Status"));
					queueBean.setWaitno(returnMap.get("WaitNo"));
					queueBean.setResult(returnMap.get("Result"));
					queueBean.setCurtime(returnMap.get("CurTime"));
					
					queuelist.add(queueBean);
				}else{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", returnMap.get("Result"));
					return modelMap;
				}
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "我的排号记录获取过程中，查询排队号码当前状态信息失败");	
				return modelMap;
			}
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("result", queuelist);
		return modelMap;
	}
	
	public static String callWebServicePost(HashMap<String, String> paramMap, String serviceEpr, String contentType){
        PostMethod postMethod = new PostMethod(serviceEpr);
        postMethod.addRequestHeader("Content-Type", contentType);
        //设置POST方法请求超时
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        
        try {
        	postMethod.addParameter("PersonID", paramMap.get("PersonID"));
            
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
	
	public static String callWebServicePostGetStatus(HashMap<String, String> paramMap, String serviceEpr, String contentType){
        PostMethod postMethod = new PostMethod(serviceEpr);
        postMethod.addRequestHeader("Content-Type", contentType);
        //设置POST方法请求超时
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        
        try {
        	postMethod.addParameter("CellID", paramMap.get("CellID"));
        	postMethod.addParameter("TicketNo", paramMap.get("TicketNo"));
        	postMethod.addParameter("PersonID", paramMap.get("PersonID"));
            
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
		System.out.println("开始尝试向排队机进行我的排号记录获取开始.....");
		String serviceEpr = "http://yun.linker.com.cn:8081/AppInterface.asmx/queryPersonHistory";
		String contentType = "application/x-www-form-urlencoded; charset=utf-8";
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("PersonID", "1234567890");
		String resStr = callWebServicePost(paramMap, serviceEpr, contentType);
		System.out.println("返回结果===="+resStr);
		System.out.println("开始尝试向排队机进行我的排号记录获取结束.....");
	}
}
