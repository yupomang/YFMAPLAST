package com.yondervision.yfmap.messagectrl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.PropertiesReader;


public class MessageCtr5 extends AbsMessageCtr {

	/**
	 *   把特定模板类型的指定报文模板的报文解析成HashMap类型的数据。
	 */
	@Override
	public HashMap<String, String> analysisPacket(String msgTempFileName,
			String msgData, String transSeq) throws CenterRuntimeException {
		
		HashMap<String, String> mapData = new HashMap<String, String>();
		try{
			int beginIndex = 0;
			int endIndex = 0;
			String tempKey = null;
			String tempValue = null;
			System.out.println("返回报文信息状态<RspCode>000000</>:"+msgData);
			System.out.println("msgTempFileName:"+msgTempFileName);
			if(msgData.indexOf("<RspCode>000000</>")>=0){
				HashMap<String,String> mapTemp = getTemplateMap(msgTempFileName);
				do {
					beginIndex = msgData.indexOf('<');
					endIndex = msgData.indexOf('>');
					tempKey = msgData.substring(beginIndex+1, endIndex);
					System.out.println("tempKey : "+tempKey);
					System.out.println("mapTemp() : "+mapTemp.get(tempKey));
					beginIndex = endIndex;
					endIndex = msgData.indexOf("</");
					tempValue = msgData.substring(beginIndex+1, endIndex);
					msgData = msgData.substring(endIndex);
					int a = msgData.indexOf('>');
					msgData = msgData.substring(a+1);
					if(mapTemp.get(tempKey)==null){
						String centerId = PropertiesReader.getProperty("properties.properties", "YFMAPServerSendMessageCenterId").trim();
						if("00089800".equals(centerId))continue;
//						LogUtils.error(ContextUtils.COM_PAYMENT, ErrorCodeDefinition.COMM_ERROR99, ReadProperties.getInstence(ContextUtils.msgContext_PR).getProperty("message10191")+tempKey);
						throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：把特定模板类型的指定报文模板的报文解析成HashMap类型的数据异常。");
					}
					else if(mapTemp.get(tempKey).indexOf("[@")==-1)mapData.put(tempKey, tempValue);
					else this.setData(mapTemp.get(tempKey), tempValue, mapData);
				} while (msgData.indexOf('<') != -1 );
			}else{
				System.out.println("返回报文信息状态<RspCode>999999</>:"+msgData);
				do {
					beginIndex = msgData.indexOf('<');
					endIndex = msgData.indexOf('>');
					tempKey = msgData.substring(beginIndex+1, endIndex);
					System.out.println("解析异常标识名："+tempKey);
					beginIndex = endIndex;
					endIndex = msgData.indexOf("</");
					tempValue = msgData.substring(beginIndex+1, endIndex);
					System.out.println("解析异常标识值："+tempValue);
					msgData = msgData.substring(endIndex);
					int a = msgData.indexOf('>');
					msgData = msgData.substring(a+1);
					if("RspCode".equals(tempKey)){
						mapData.put("recode", tempValue);
					}else if("RspMsg".equals(tempKey)){
						mapData.put("msg", tempValue);
					}
				} while (msgData.indexOf('<') != -1 );
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999, "前置机：把特定模板类型的指定报文模板的报文解析成HashMap类型的数据异常。");
		}
		if(mapData.get("recode")!=null&&mapData.get("recode").equals("000000")){
			mapData.put("recode", "0");
		}
		return mapData;
	}
	public HashMap<String,String> getTemplateMap(String msgTempFileName) throws IOException{
    	HashMap<String,String> map = new HashMap<String,String>();
    	File file = new File(msgTempFileName);
		FileInputStream input = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader breader = new BufferedReader(isr);
		StringBuffer li = new StringBuffer();
		String tempString = null;
		while ((tempString = breader.readLine()) != null){ 
			//显示行号 
			li.append(tempString.trim()); 
		} 
		String template=li.toString().trim();
		int beginIndex = 0;
		int endIndex = 0;
		String tempKey = null;
		String tempValue = null;
		do {
			beginIndex = template.indexOf('<');
			endIndex = template.indexOf('>');
			tempKey = template.substring(beginIndex+1, endIndex);
			beginIndex = endIndex;
			endIndex = template.indexOf("</");
			tempValue = template.substring(beginIndex+1, endIndex);
			template = template.substring(endIndex);
			int a = template.indexOf('>');
			template = template.substring(a+1);
			map.put(tempKey, tempValue);
		} while (template.indexOf('<') != -1 );
    	return map;
    }
	/**
	 *   把HashMap类型的数据封装成特定模板类型的指定报文模板的报文。
	 */
	@Override
	public String encapsulatedPackets(String msgTempFileName,
			HashMap<String, String> msgData, String transSeq)
			throws CenterRuntimeException {
		
		String bos="";
		try {
			bos = replaceMessage(msgTempFileName, msgData);			
		} catch (CenterRuntimeException e) {
			throw new CenterRuntimeException(Constants.ERROR_999999, msgTempFileName+"文件异常");
		}
		return bos;
		
	}
}
