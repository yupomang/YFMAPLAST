package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi50005Form;
import com.yondervision.yfmap.util.PropertiesReader;
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
 * @date 2020/12/28 15:26
 */
@Controller
public class AppApi052Contorller {

    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00177.{ext}")
    public void appapi00177(AppApi50005Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市建设用地规划许可证");
        log.info("api/appapi00177 begin.++++++++++++zhangj");
        log.info(Constants.LOG_HEAD + "api/appapi00177 begin.");

        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime22222:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016014/dataSharing/Ve90c4b8OcIdkI12.htm";
        log.info("连接外部url:" + url);
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("sbrzjh", form.getSbrzjh());
        params.put("projid", form.getProjId());
        params.put("belongto", form.getBelongto());
        params.put("sbrxm", form.getSbrxm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        log.info("api/appapi00177 running.++++++++++++zhangj");
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("宁波市建设用地规划许可证:" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appapi00177 end.");
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
