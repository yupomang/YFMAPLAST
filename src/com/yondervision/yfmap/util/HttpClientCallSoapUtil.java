package com.yondervision.yfmap.util;

import java.nio.charset.Charset;  
import org.apache.http.HttpEntity;  
import org.apache.http.client.config.RequestConfig;  
import org.apache.http.client.methods.CloseableHttpResponse;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.CloseableHttpClient;  
import org.apache.http.impl.client.HttpClientBuilder;  
import org.apache.http.util.EntityUtils;  
import org.apache.log4j.Logger;  
      
    public class HttpClientCallSoapUtil {  
        static int socketTimeout = 30000;// 请求超时时间  
        static int connectTimeout = 30000;// 传输超时时间  
        static Logger logger = Logger.getLogger(HttpClientCallSoapUtil.class);  
      
        /** 
         * 使用SOAP1.1发送消息 
         *  
         * @param postUrl 
         * @param soapXml 
         * @param soapAction 
         * @return 
         */  
        public static String doPostSoap1_1(String postUrl, String soapXml,  
                String soapAction) {  
            String retStr = "";  
            // 创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
            // HttpClient  
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
            HttpPost httpPost = new HttpPost(postUrl);  
                    //  设置请求和传输超时时间  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(socketTimeout)  
                    .setConnectTimeout(connectTimeout).build();  
            httpPost.setConfig(requestConfig);  
            try {  
                httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");  
                httpPost.setHeader("SOAPAction", soapAction);  
                StringEntity data = new StringEntity(soapXml,  
                        Charset.forName("UTF-8"));  
                httpPost.setEntity(data);  
                CloseableHttpResponse response = closeableHttpClient  
                        .execute(httpPost);  
                HttpEntity httpEntity = response.getEntity();  
                if (httpEntity != null) {  
                    // 打印响应内容  
                    retStr = EntityUtils.toString(httpEntity, "UTF-8");  
                    logger.info("response:" + retStr);  
                }  
                // 释放资源  
                closeableHttpClient.close();  
            } catch (Exception e) {  
                logger.error("exception in doPostSoap1_1", e);  
            }  
            return retStr;  
        }  
      
        /** 
         * 使用SOAP1.2发送消息 
         *  
         * @param postUrl 
         * @param soapXml 
         * @param soapAction 
         * @return 
         */  
        public static String doPostSoap1_2(String postUrl, String soapXml,  
                String soapAction) {  
            String retStr = "";  
            // 创建HttpClientBuilder  
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
            // HttpClient  
            CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
            HttpPost httpPost = new HttpPost(postUrl);  
                    // 设置请求和传输超时时间  
            RequestConfig requestConfig = RequestConfig.custom()  
                    .setSocketTimeout(socketTimeout)  
                    .setConnectTimeout(connectTimeout).build();  
            httpPost.setConfig(requestConfig);  
            try {  
                httpPost.setHeader("Content-Type",  
                        "application/soap+xml;charset=UTF-8");  
                httpPost.setHeader("SOAPAction", soapAction);  
                StringEntity data = new StringEntity(soapXml,  
                        Charset.forName("UTF-8"));  
                httpPost.setEntity(data);  
                CloseableHttpResponse response = closeableHttpClient  
                        .execute(httpPost);  
                HttpEntity httpEntity = response.getEntity();  
                if (httpEntity != null) {  
                    // 打印响应内容  
                    retStr = EntityUtils.toString(httpEntity, "UTF-8");  
                    logger.info("response:" + retStr);  
                }  
                // 释放资源  
                closeableHttpClient.close();  
            } catch (Exception e) {  
                logger.error("exception in doPostSoap1_2", e);  
            }  
            return retStr;  
        }  
      
        public static void main(String[] args) {  
            String orderSoapXml = "<?xml version = \"1.0\" ?>"  
                    + "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservices.b.com\">"  
                    + "   <soapenv:Header/>"  
                    + "   <soapenv:Body>"  
                    + "      <web:order soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"  
                    + "         <in0 xsi:type=\"web:OrderRequest\">"  
                    + "            <mobile xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</mobile>"  
                    + "            <orderStatus xsi:type=\"xsd:int\">?</orderStatus>"  
                    + "            <productCode xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">?</productCode>"  
                    + "         </in0>" + "      </web:order>"  
                    + "   </soapenv:Body>" + "</soapenv:Envelope>";  

            String postUrl = "http://localhost:8080/services/WebServiceFromB";  
            //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务  
            doPostSoap1_1(postUrl, orderSoapXml, "");  
      
            //采用SOAP1.2调用服务端，这种方式只能调用服务端为soap1.2的服务  
            //doPostSoap1_2(postUrl, orderSoapXml, "order");  
            //doPostSoap1_2(postUrl, querySoapXml, "query");  
            
/*            优点：
            1.使用httpclient作为客户端调用webservice，不用关注繁琐的webservice框架，只需找到SOAP消息格式，添加httpclient依赖就行。
            2.使用httpclient调用webservice，建议采用soap1.1方式调用，经测试使用soap1.1方式能调用soap1.1和soap1.2的服务端。
            缺点：
            唯一的缺点是，你得自己解析返回的XML，找到你关注的信息内容。*/
        }  
    } 