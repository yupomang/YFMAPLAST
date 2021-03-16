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
    public void appapi00339(AppApi00239Form  form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){

        form.setBusinName("公积金个人账户基本信息查询打印");
        log.info(Constants.LOG_HEAD+"api/appapi00339 begin.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        form.setTransdate(dateFormat.format(new Date()));
        log.info("YF---appapi00339------  ");
        log.info("appapi00339--form.getAccnum():" + form.getAccnum()+"appapi00339--form.getSpt_indiaccstatename():" + form.getSpt_indiaccstatename()+"appapi00339--form.getBasenum():" + form.getBasenum()+
                "appapi00339--form.getBal():" + form.getBal()+"appapi00339--form.getIndipaysum():" + form.getIndipaysum()+"appapi00339--form.getLpaym():" + form.getLpaym()+
                "appapi00339--form.getOpnaccdate():" + form.getOpnaccdate()+"appapi00339--form.getSpt_unitaccname():" + form.getSpt_unitaccname()+"appapi00339--form.getBal1():" + form.getBal1());
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80016.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始公积金个人账户基本信息查询打印接口：" + rm);

        form.setSeqno("3");
        JSONObject json2 = JSONObject.fromObject(rm);
        log.info(Constants.LOG_HEAD + "appapi00337 end.");
        log.info("gbk");
        log.info("json.toString()==========" + json2.toString());
        try {
            response.getOutputStream().write(json2.toString().getBytes(request.getCharacterEncoding()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
