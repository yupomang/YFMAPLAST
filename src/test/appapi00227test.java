package test;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00225Form;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import org.apache.log4j.Logger;

import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

public class appapi00227test {
    Logger log = Logger.getLogger("YFMAP");
    public static void appapi00227 ( ){

        AppApi00225Form form = new AppApi00225Form();
        form.setBusinName("异地贷款缴存使用证明打印");
        form.setAccnum("0238557595");
        form.setBrccode("05740008");
        form.setCertinum("330283198802270016");
        form.setChannel("10");
        form.setCenterId("00057400");
        form.setCentername("宁波市住房公积金管理中心");
        form.setProjectname("杭州");
        form.setUserid("stgy");
        form.setZJHM("330283198802270016");
        form.setBuzType("5002");
        System.out.println("api/appApi00227 begin.");

        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        //String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00227.json";
        String url = "http://172.16.10.96:11080/YBMAPZH/appapi00227.json";
        //String url = "http://172.16.10.96:7001/YBMAPZH/appapi00227.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始异地贷款缴存使用证明打印接口：" + rm);
    }

    public static void appapi00233(){
        AppApi030Form form = new AppApi030Form();
        System.out.println("api/appApi00233 begin.");
        long starTime = System.currentTimeMillis();
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        //String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80010.json";
        //String url = "http://172.16.10.96:11080/YBMAPZH/webapi80010.json";
        String url = "http://172.16.10.96:7001/YBMAPZH/webapi80010.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始异地贷款缴存使用证明打印接口：" + rm);
    }

    public static void main(String[] args) {
        appapi00227();
        //appapi00233();
    }
}
