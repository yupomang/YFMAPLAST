package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00238Form;
import com.yondervision.yfmap.form.AppApi00240Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

@Controller
public class AppApi071Contorller {
    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00338.{ext}")
    public void appapi00338(final AppApi00240Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

        form.setBusinName("公积金缴存明细查询打印");
        log.info(Constants.LOG_HEAD+"api/appapi00338 begin.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        form.setTransdate(dateFormat.format(new Date()));
        log.info("appapi00338--form.getVoucherData():" + form.getVoucherData());
        log.info("YF---appapi00338------  ");
        HashMap map = BeanUtil.transBean2Map(form);
        final YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00338.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始公积金缴存明细查询打印接口：" + rm);

        System.out.println("rm ：" + rm);
        JSONObject json = JSONObject.fromObject(rm);

        //修改多线程
        log.info("YF---appapi00338------  "+json.get("recode").toString());
        if (String.valueOf(json.get("recode")).equals("000000")){
            try {
                //Thread.sleep(5000);
                response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    /*try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    HashMap map1 = BeanUtil.transBean2Map(form);
                    String url1 = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80018.json";
                    System.out.println("YBMAP url ：" + url1);
                    String rm = post.post(url1, map1);
                    System.out.println("AppApi401ServiceImpl:开始公积金缴存明细查询打印接口：" + rm);
                }
            });

            t1.start();

        }

        log.info(Constants.LOG_HEAD + "appapi00338 end.");
        //log.info("gbk");
        //log.info("json.toString()==========" + json.toString());
        //response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));

        return;
    }
}
