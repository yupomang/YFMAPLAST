package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.AppApi50001Form;
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
 * @date 2020/10/26 14:51
 */
@Controller
public class AppApi046Contorller {

    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00175.{ext}")
    public void appapi00175(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("新契税完税信息接口");
        log.info("AAAAAAAAAAAAAAAAAAAAAAAAA----AAAAAAAAAAAAAAAAAAA");
        log.info(Constants.LOG_HEAD + "api/appapi00175 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        //System.out.println("powerMatters======"+form.getPowerMatters());
        //System.out.println("subPowerMatters======"+form.getSubPowerMatters());
        //System.out.println("accesscardId======"+form.getCertinum());
        //System.out.println("materialName======"+form.getMaterialName());
        //System.out.println("sponsorName======"+form.getAccname());
        //System.out.println("sponsorCode======"+form.getCertinum());
        //System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        //String url = sjpublicDatasUrl + "api/001008002016288/dataSharing/dc7s3B05Xdhx8580.htm";
        String url = sjpublicDatasUrl + "api/001008002016288/dataSharing/ee1S7D9OXN1bayae.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        //params.put("nsrqz", form.getNsrqz());
        //params.put("nsrqq", form.getNsrqq());
        params.put("nsrsbh", form.getNsrsbh());//纳税人识别号
        params.put("dzsphm", form.getDzsphm());//电子税票号码
        params.put("nsrmc", form.getNsrmc());//纳税人名称
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");//附加信息 additional
        long starTime = System.currentTimeMillis();
        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");//发送参数至宁波市契税完税信息接口进行处理
        System.out.println(result);

        log.info("新契税完税信息接口url=" + url);
        log.info("新契税完税信息接口" + result);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        //JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace("\"{", "{").replace("}\"", "}"));
        //JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace("[", "").replace("]", ""));

        JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace(":\"{", ":{").replace("}\",", "},").replace("%},", "%}\",").replace(":\"[",":[").replace("}]\"","}]"));


        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00175 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("'", "\""));
            response.getOutputStream().write(json.toString().replace("'", "\"").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").getBytes("utf-8"));
        }
        return;
    }
}
