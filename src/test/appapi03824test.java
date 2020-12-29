package test;

import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

public class appapi03824test {


    public static void appapi03824(){

        AppApi030Form form1 = new AppApi030Form();
        //form1.setBusinName("职工住房公积金缴存情况证明");
        form1.setAccnum("0238557595");
        //form1.setCertinum("330903199308240613");
        form1.setCertinum("330283198802270016");
        //form1.setCentername("宁波市住房公积金管理中心");
        form1.setUserId("stgy");
        form1.setBrcCode("05740008");
        form1.setChannel("10");
        form1.setCenterId("00057400");
        form1.setSeqno("1");
        form1.setProjectname("宁波公积金中心");
        form1.setBuzType("5914");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        form1.setTransdate(dateFormat.format(new Date()));
        form1.setQdapprnum("YTJ" + UUID.randomUUID());

        HashMap map = BeanUtil.transBean2Map(form1);
        YbmapMessageUtil post = new YbmapMessageUtil();
        //String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi03824.json";
        //String url = "http://172.16.10.96:11080/YBMAPZH/appapi03824.json";
        String url = "http://172.16.10.96:7001/YBMAPZH/appapi03824.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始异地贷款缴存使用证明打印接口：" + rm);
    }

    public static void main(String[] args) {
        appapi03824();
        //appapi00233();
    }
}
