package com.yondervision.yfmap.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Encoder;

/**
 * http接口请求
 * @author syw
 *
 */
public class Httpclient {
	public static void main(String[] args) throws IOException {
		 //发送 POST 请求
		//String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/session", "robotId=X8u9YgR6zG4mqk0qP1k");
        //String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/answer", "sessionId=g5qu811e0o7ki2n4r3pc&ask=/root/zfgjjyw/gjjtq&type=hotaType");
        //String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/answer", "sessionId=g5qu811e0o7ki2n4r3pc&ask=07391a85b07de34f4deae7b6ca8dbc78ac7be337&type=hotaTitle");
		//String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/answer", "sessionId=aj2183jq5gvs8y8g5l43&ask=提取&type=normal");
        //String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/answer", "sessionId=g5qu811e0o7ki2n4r3pc&ask=07391a85b07de34f4deae7b6ca8dbc78ac7be337&type=about");
        //String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/askhit", "sessionId=g5qu811e0o7ki2n4r3pc&ask=提取");
        //String sr=Httpclient.sendPost("http://www.gentlesoft.com.cn/gss/apis/robotser/askhit", "sessionId=aj2183jq5gvs8y8g5l43&ask=提取");
		 String sr=Httpclient.sendPost("http://mp.wx.pangjiachen.com/weApp/exec001.json", "");
        //System.out.println(sr);
	}
	
	  /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
    	DataOutputStream out = null;
        BufferedReader in = null;
        String result = "";
        try {
        	System.out.println("&&&&&&&&&&&&&&&&"+url+param);
        	System.out.println("&&&&&&&&&&&&&&&&"+param.getBytes("utf-8"));
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");  
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            out = new DataOutputStream(conn.getOutputStream());
            // 发送请求参数
            out.write(param.getBytes("utf-8"));
            // 发送请求参数
            // out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param + "&centerId=00031500&userId=&surplusAccount="+URLEncoder.encode("0dgttgGgZibfxV+Xuh89Wg", "UTF-8")+"&deviceType=2&deviceToken=c5e640c1abf7fb25" +
            		"&currenVersion=v1.0&buzType=5431&devtoken=&channel=10";
            URL realUrl = new URL(urlNameString);
            BASE64Encoder enc=new BASE64Encoder();
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
}