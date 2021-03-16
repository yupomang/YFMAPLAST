package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00241Form;
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
public class AppApi073Contorller {
    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00341.{ext}")
    public void appapi00341(AppApi00241Form  form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        form.setBusinName("公积金贷款信息查询打印");
        log.info(Constants.LOG_HEAD+"api/appapi00341 begin.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        form.setTransdate(dateFormat.format(new Date()));
        log.info("YF---appapi00341------  ");
        log.info("appapi00341--form.getJkrxm():" + form.getJkrxm()+"appapi00341--form.getSfzh():" + form.getSfzh()+"appapi00341--form.getTxdz():" + form.getTxdz()+
                "appapi00341--form.getSjhm():" + form.getSjhm()+"appapi00341--form.getJkhtbh():" + form.getJkhtbh()+"appapi00341--form.getGfdz():" + form.getGfdz()+
                "appapi00341--form.getJkje():" + form.getJkje()+"appapi00341--form.getJkffr():" + form.getJkffr()+"appapi00341--form.getJkll():" + form.getJkll());

        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00341.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始公积金贷款信息查询打印接口：" + rm);

        System.out.println("rm ：" + rm);
        JSONObject json = JSONObject.fromObject(rm);
        log.info(Constants.LOG_HEAD + "appapi00341 end.");
        log.info("gbk");
        log.info("json.toString()==========" + json.toString());
        response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));

        return;
    }
}
