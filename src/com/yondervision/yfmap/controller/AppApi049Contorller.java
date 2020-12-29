package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.PublicDatasTokenUtil;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 张杰
 * @version V1.0
 * @date 2020/12/23 15:26
 */
@Controller
public class AppApi049Contorller {
    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00176.{ext}")
    public void appapi00176(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("新社会保险个人参保信息");
        log.info("api/appapi00176 begin.++++++++++++zhangj");
        log.info(Constants.LOG_HEAD + "api/appapi00176 begin.");
        //连接浙江省公共数据平台获取数据
        //String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        //String url = publicDatasUrl + "api/001003085/dataSharing/socialPersonalParticipationInfo.htm";
        String url = publicDatasUrl + "api/001008002016003/dataSharing/nojcPfb7efAdf854.htm";
        log.info("连接外部url:" + url);
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("AAC002", form.getCertinum());
        params.put("aac003", form.getAccname());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        log.info("api/appapi00176 running.++++++++++++zhangj");
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("社会保险个人参保信息" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appapi00176 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString());
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }

}
