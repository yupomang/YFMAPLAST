package com.yondervision.yfmap.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;

/** 
* @ClassName: BeanUtil 
* @Description: TODO
* @author Caozhongyan
* @date Oct 26, 2013 11:41:38 AM   
* 
*/ 
public class BeanUtil {
	// Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
	public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
		if (map == null || obj == null) {  
	       return;  
	    }  
	    try {  
	            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
	
	// Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            AES aes = new AES();
            String vv;
            int i=0;
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
                
                if (map.containsKey(key)) {  
                    Object value = map.get(key); 
                    String value1 = (String)value;
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();
//                    System.out.println("参数："+(i++)+"   开始用时"+CommonUtil.getSystemDateNumOnly());
                    vv=aes.encrypt(value1.getBytes());//所有下传参数加密处理
//                    System.out.println("参数："+(i++)+"   结束用时"+CommonUtil.getSystemDateNumOnly());
//                    setter.invoke(obj, vv);  
                    setter.invoke(obj, value1);
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }    
        return; 
    }  

 // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static HashMap<String, String> transBean2Map(Object obj) {  
  
       if(obj == null){  
            return null;  
       }          
       HashMap<String, String> map = new HashMap<String, String>();
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName(); 
               // 过滤class属性  
            if (!key.equals("class")) {  
                // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    map.put(key, (String) value);  
                }  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
        return map; 
    }  

    public static void main(String[] args){
    	AppApiCommonForm form = new AppApiCommonForm();
    	form.setBuzType("12132");
    	form.setCenterId("222121");
    	form.setCurrenVersion("888");
    	form.setDeviceToken("1");
    	HashMap<String, String> map = transBean2Map(form);  
    	   
//	     System.out.println("--- transBean2Map Map Info: ");  
//	     for (Map.Entry<String, Object> entry : map.entrySet()) {  
//	         System.out.println(entry.getKey() + ": " + entry.getValue());  
//	     }  
	     
//	     SimpleDateFormat formatter = new SimpleDateFormat(MessageDataConversion.dateTimeFormat);
//			String strDateTime = formatter.format(new Date());
			String seqNo = "06310011107120000034";// 交易序号
//			HashMap<String, String> msgData = new HashMap<String, String>();
//			msgData.put("YQ03301", seqNo);
//			msgData.put("YQ03310", strDateTime);
//			msgData.put("YQ03306", "3");
//			msgData.put("YQ03307", "323423.38");
			
			try {
//				String tempFileName = "D:/eclipse/project/yqzlteam/project/yqzl/src/com/yondervison/resources/messagetemp/06310/01/REQ_2GRPEXCH2.xml";
//				E:\workspace\yqzl\WebRoot\WEB-INF\classes\com\yondervison\resources\messagetemp\06310\01
//				String tempFileName = "E:/workspace/yqzl/WebRoot/WEB-INF/classes/com/yondervison/resources/messagetemp/06310/01/AAA.xml";
				String tempFileName = "com/yondervision/mi/config/messagetemp/00075500/REQ_2QENTDIS1.xml";
				String xmlMessage = MessageCtrMain.encapsulatedPackets("2", tempFileName, map, seqNo);
				System.out.println(xmlMessage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	     
//	     List<AppApi00201Result> list = new ArrayList<AppApi00201Result>();
//	     AppApi00201Result a1 = new AppApi00201Result();
//	     a1.setAmount("11111");
//	     a1.setOperationDate("11111");
//	     a1.setOperationMemo("11111");
//	     a1.setOperationType("11111");
//	     list.add(a1);
//	     
//	     AppApi00201Result a2 = new AppApi00201Result();
//	     a2.setAmount("22222");
//	     a2.setOperationDate("22222");
//	     a2.setOperationMemo("22222");
//	     a2.setOperationType("22222");
//	     list.add(a2);
//	     AppApi00201Result a3 = new AppApi00201Result();
//	     a3.setAmount("22222");
//	     a3.setOperationDate("22222");
//	     a3.setOperationMemo("22222");
//	     a3.setOperationType("22222");
//	     list.add(a3);
//	     Map<String, Object> map2 = transBean2Map(list); 
//	     System.out.println("--- transBean2Map Map Info: ");  
//	     for (Map.Entry<String, Object> entry : map2.entrySet()) {  
//	         System.out.println(entry.getKey() + ": " + entry.getValue());  
//	     }  
//	     List<AppApi00201Result> list2 = new ArrayList<AppApi00201Result>();
//	     transMap2Bean(map2, list2);
//	     System.out.println(list2.size());
    }
}	
