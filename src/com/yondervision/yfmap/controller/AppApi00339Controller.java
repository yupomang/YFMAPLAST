package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00239Form;
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
public class AppApi00339Controller {
    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00339.{ext}")
    public void appapi00339(final AppApi00239Form  form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws InterruptedException {

        form.setBusinName("公积金个人账户基本信息查询打印");
        log.info(Constants.LOG_HEAD+"api/appapi00339 begin.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        form.setTransdate(dateFormat.format(new Date()));
        log.info("YF---appapi00339------  ");
        log.info("appapi00339--form.getAccnum():" + form.getAccnum()+"appapi00339--form.getSpt_indiaccstatename():" + form.getSpt_indiaccstatename()+"appapi00339--form.getBasenum():" + form.getBasenum()+
                "appapi00339--form.getBal():" + form.getBal()+"appapi00339--form.getIndipaysum():" + form.getIndipaysum()+"appapi00339--form.getLpaym():" + form.getLpaym()+
                "appapi00339--form.getOpnaccdate():" + form.getOpnaccdate()+"appapi00339--form.getSpt_unitaccname():" + form.getSpt_unitaccname()+"appapi00339--form.getBal1():" + form.getBal1());
        //调用yb异地贷款缴存使用证明打印接口
        final YbmapMessageUtil post = new YbmapMessageUtil();
        HashMap map1 = BeanUtil.transBean2Map(form);
        String url1 = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00339.json";
        System.out.println("YBMAP url1 ：" + url1);
        String rm1 = post.post(url1, map1);
        System.out.println("公积金个人账户基本信息查询打印接口：" + rm1);
        JSONObject json = JSONObject.fromObject(rm1);
        log.info("YF---appapi00339------  "+json.get("recode").toString());
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
                   /* try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    HashMap map = BeanUtil.transBean2Map(form);
                    String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80016.json";
                    System.out.println("YBMAP url ：" + url);
                    String rm = post.post(url, map);
                    System.out.println("AppApi401ServiceImpl:开始公积金个人账户基本信息查询打印接口：" + rm);
                }
            });

            t1.start();

        }


        log.info(Constants.LOG_HEAD + "appapi00339 end.");

        return;
    }
}
