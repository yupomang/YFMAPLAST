package test;

import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.common.security.RSASignature;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class httptest {

    //白天只能连接生产环境，晚上才能连接测试环境，并且测试完，需要couchbase删除缓存
    public static final String POST_URL = "http://10.33.11.35:7001/YFMAPZH/";
/*    private static final String appKey_V = "ba7f3a3dd146c4613102d0a16be5107b";
    private static final String appId_V = "yondervisionwebsite30";
    private static final String clientIp_V="172.10.0.1";*/
    public static String httpURLConnectionPOST6020(

    ) {
        try {
            URL url = new URL(POST_URL + "getaccept.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.addRequestProperty("headpara","fullPara,centerId,channel");
            //String userId ="330726198810170330";
            /*AesTest aes = null;
            try {
                aes = new AesTest();
            } catch (Exception e) {
                e.printStackTrace();
            }*/



            String Para ="areaCode=315000&appId=12&memo=12&projId=12&extInfo=&operatorName=公积金测试&operatorUid=12&deptCode=001&deptName=公积金中心&gmtAccept=2019-08-12 12:00:00&attachMentVOs=&memo=&promiseTime=2019-08-12 16:00:00"
                    ;


            //System.out.println("para:"+Para.getBytes("UTF-8"));
            //String fullPara =Para;
            // 用于数字签名
            //String parm ="fullPara="+fullPara+"&centerId=00057400&channel=30";
            // 用于发送http报文
            //String parm1 ="fullPara="+ fullPara.replace("+", "%2B")+ "&AESFlag=1&centerId=00057400&channel=30";
            System.out.println("fullPara:"+Para);
            //connection.addRequestProperty("headparaMD5", RSASignature.sign(EncryptionByMD5.getMD5(parm.getBytes()),RSASignature.RSA_PRIVATE));
            //connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            connection.connect();
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            dataout.writeBytes(Para);
            dataout.flush();
            dataout.close();
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bf.readLine()) != null) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();
            connection.disconnect();
            String result=sb.toString();
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args){
        for(int i=0;i<1;i++) {
            long starTime = System.currentTimeMillis();
            httpURLConnectionPOST6020();
            long endTime = System.currentTimeMillis();
            long Time = endTime - starTime;
            System.out.println("请求住建部大数据平台耗时" + Time + "毫秒");
        }
    }
}
