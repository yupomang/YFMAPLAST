/**
 *
 */
package com.yondervision.yfmap.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yondervision.yfmap.common.security.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.XML;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApi50004Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.DES3;
import com.yondervision.yfmap.util.ExchangeHttpClient;
import com.yondervision.yfmap.util.HttpClientCallSoapUtil;
import com.yondervision.yfmap.util.ImgTools;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.PublicDatasTokenUtil;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;

@Controller
public class AppApi001Contorller {
    Logger log = Logger.getLogger("YFMAP");

    /**
     * 余额查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00101.{ext}")
    public String appApi00101(AppApi00101Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00101 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle001_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00101 end.");
        return "/index";
    }

    /**
     * 公积金信息查询简化版
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00102.{ext}")
    public String appApi00102(AppApi50001Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00102 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00102_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00102 end.");
        return "/index";
    }

    /**
     * 个人账户查询（用于反显）
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00104.{ext}")
    public String appApi00104(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00104 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00104_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00104 end.");
        return "/index";
    }


    @RequestMapping("/appapi00105.{ext}")
    public void appapi00105(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("人口信息查询");
        log.info(Constants.LOG_HEAD + "api/appApi00105 begin.");

        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);

        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);

        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getBodyCardNumber());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getBodyCardNumber());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/popInfo.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getBodyCardNumber());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters()
                + "\",\"accesscardId\":\"" + form.getBodyCardNumber() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getBodyCardNumber()
                + "\",\"projectId\":\"\"}");
        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("人口信息结果" + result);
        JSONObject json = JSONObject.fromObject(result);
        if ("00".equals(String.valueOf(json.get("code")))) {
            json.put("recode", "000000");
        } else {
            json.put("recode", json.get("code"));
        }
        log.info(Constants.LOG_HEAD + "appApi00105 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes("gb18030"));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }

    /**
     * 婚姻登记信息查询
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00106.{ext}")
    public void appapi00106(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("婚姻登记信息查询");
        log.info(Constants.LOG_HEAD + "appApi00106 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);

        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getBodyCardNumber());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getFullName());
        System.out.println("sponsorCode======" + form.getBodyCardNumber());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/marryInfo.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getBodyCardNumber());
        params.put("sex", form.getSex());
        params.put("name", form.getFullName());
        params.put("birthday", form.getBirthday());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters()
                + "\",\"accesscardId\":\"" + form.getBodyCardNumber() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getFullName() + "\",\"sponsorCode\":\"" + form.getBodyCardNumber()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

	/*	System.out.println(form.getFullName()+"转换成"+URLEncoder.encode(form.getFullName(),"utf-8") );
		String url = publicDatasUrl + "api/marryInfo.htm" + "?appKey="
				+ appKey + "&sign=" + sign + "&requestTime=" + requestTime
				+ "&cardId=" + form.getBodyCardNumber()
				+ "&sex=" + form.getSex() + "&name=" + URLEncoder.encode(form.getFullName(),"utf-8")
				+ "&birthday=" + form.getBirthday();
		log.info("婚姻登记信息查询url="+url);
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		String result = msm.sendGet(url, "UTF-8");*/
        JSONObject json = JSONObject.fromObject(result);
        if ("00".equals(String.valueOf(json.get("code")))) {
            json.put("recode", "000000");
            if (!"0".equals(String.valueOf(json.get("dataCount")))) {
                String DashujuJson = json.toString();
                log.info("大数据平台直接返回的DashujuJson=" + DashujuJson);
                JSONArray ary = (JSONArray) json.get("datas");
                log.info("datas婚姻记录数组ary=" + ary);
                String lastData = "1000-01-01";
                String tempDate = "";
                JSONArray ary1 = new JSONArray();
                for (int j = 0; j < ary.size(); j++) {//ary 循环
                    JSONObject obj = ary.getJSONObject(j);
                    tempDate = (String) obj.get("registrationDate");//获取时间字段 lastDate与tempDate比较，把大的赋值给lastDate
                    tempDate = tempDate.substring(0,10);
                    if (Long.valueOf(tempDate.replaceAll("[-]", "")) > Long
                            .valueOf(lastData.replaceAll("[-]", ""))) {
                        lastData = tempDate;
                        ary1.clear();
                        ary1.add(obj);
                    }
                }
                log.info("最新婚姻信息记录的登记日期=" + lastData);
                log.info("最新婚姻信息记录的ary1=" + ary1);
                log.info("json.getdatas=" + json.get("datas"));
                json.remove("data");//把原来的datas删掉
                json.put("datas", ary1);//把新的datas放进去
            }
        } else {
            json.put("recode", json.get("code"));
        }
        json.remove("code");//把原来的code删掉
        json.remove("dataCount");//把原来的dataCount删掉

        log.info(Constants.LOG_HEAD + "appApi00106 end.");
        log.info("form.getChannel()" + form.getChannel());


        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes("gb18030"));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }

    /**
     * 获取房屋登记信息
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00107.{ext}")
    public void appapi00107(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("获取房屋登记信息");
        log.info(Constants.LOG_HEAD + "appApi00107 begin.");
        //连接房改局
        String Get_ticketUrl = "http://100.10.10.28/DataShare/api/v1.0/auth/get_ticket?appKey=3150401";
        String Create_tokenUrl = "http://100.10.10.28/DataShare/api/v1.0/app/create_token?appKey=3150401";
        String DistrictUrl = "http://100.10.10.28/DataShare/api/v1.0/district/list";

        String GJJUrl = "http://100.10.10.28/DataShare/Private/api/v1.0/GJJ/certification";
//		String GJJUrl= "http://100.10.10.28/DataShare/api/v1.0/GJJ/certification";
        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

        String districtResult = msm.sendGet(DistrictUrl, "UTF-8");
        log.info("获取地域信息结果" + districtResult);
/*		JSONObject districtJson = JSONObject.fromObject(districtResult);
		if("0".equals(String.valueOf(districtJson.get("status")))){
			//divisionCode=districtJson.get("divistionCode").toString();
			divisionCode0=districtJson.get("result").toString();
			JSONObject divisionCode0 = JSONObject.fromObject(divisionCode);
			divisionCode0=ticketJson0.get("divisionCode").toString();
		}else{
			log.info(获取地域信息结果失败");
		}*/
        String divisionCode = null;
        divisionCode = "330212";
        String randomString = getRandomString(20);
        //第一步：获取票据参数信息
        String ticketResult = null;
        String ticket0 = null;
        String ticket = null;
        CloseableHttpClient client1 = HttpClients.createDefault();
        HttpPost post = new HttpPost(Get_ticketUrl);
        JSONObject json1 = new JSONObject();
        json1.put("verifyState", randomString);
        try {
            post.addHeader("Content-type", "application/json; charset=utf-8");
            post.setHeader("Accept", "application/json");
            post.setEntity(new StringEntity(json1.toString(), Charset.forName("UTF-8")));
            HttpResponse httpResponse = client1.execute(post);
            HttpEntity entity = httpResponse.getEntity();
            ticketResult = EntityUtils.toString(entity);
            System.out.println("status:" + httpResponse.getStatusLine());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("获取票据参数信息结果" + ticketResult);
        JSONObject ticketJson = JSONObject.fromObject(ticketResult);
        if ("0".equals(String.valueOf(ticketJson.get("status")))) {
            ticket0 = ticketJson.get("result").toString();
            JSONObject ticketJson0 = JSONObject.fromObject(ticket0);
            ticket = ticketJson0.get("ticket").toString();
        } else {
            log.info("获取票据参数信息失败");
        }

        //第二步：获取应用令牌信息
        String tokenResult = null;
        String accessToken0 = null;
        String accessToken = null;
        CloseableHttpClient client2 = HttpClients.createDefault();
        HttpPost post2 = new HttpPost(Create_tokenUrl);
        JSONObject json2 = new JSONObject();
        json2.put("appSecret", "6E2DA67C97564E66BE702A01B7395767");
        json2.put("ticket", ticket);
        json2.put("verifyState", randomString);
        try {
            post2.addHeader("Content-type", "application/json; charset=utf-8");
            post2.setHeader("Accept", "application/json");
            post2.setEntity(new StringEntity(json2.toString(), Charset.forName("UTF-8")));
            HttpResponse httpResponse = client2.execute(post2);
            HttpEntity entity = httpResponse.getEntity();
            tokenResult = EntityUtils.toString(entity);
            System.out.println("status:" + httpResponse.getStatusLine());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("获取应用令牌信息结果" + tokenResult);
        JSONObject tokenJson = JSONObject.fromObject(tokenResult);
        if ("0".equals(String.valueOf(tokenJson.get("status")))) {
            accessToken0 = tokenJson.get("result").toString();
            JSONObject tokenJson0 = JSONObject.fromObject(accessToken0);
            accessToken = tokenJson0.get("accessToken").toString();
        } else {
            log.info("获取应用令牌信息失败");
        }

        //第三步：获取房屋登记信息
        log.info("获取房屋登记信息url=" + GJJUrl);
        String result = null;
        CloseableHttpClient client3 = HttpClients.createDefault();
        HttpPost post3 = new HttpPost(GJJUrl);
        JSONObject json3 = new JSONObject();
        System.out.println(form.getBodyCardNumber() + form.getAccname() + divisionCode + form.getCertType() + form.getCertNumber());
        json3.put("idNo", form.getBodyCardNumber());
        json3.put("name", form.getAccname());
        json3.put("located", form.getLocated());
        json3.put("divisionCode", form.getDivisionCode());
        json3.put("certType", form.getCertType());
        json3.put("certNo", form.getCertNumber());
        json3.put("contractNo", form.getContractNo());

        try {
            post3.addHeader("Content-type", "application/json; charset=utf-8");
            post3.addHeader("AppKey", "3150401");
            post3.addHeader("AccessToken", accessToken);
            post3.setHeader("Accept", "application/json");
            post3.setEntity(new StringEntity(json3.toString(), Charset.forName("UTF-8")));
            HttpResponse httpResponse = client3.execute(post3);
            HttpEntity entity = httpResponse.getEntity();
            result = EntityUtils.toString(entity);
            System.out.println("status:" + httpResponse.getStatusLine());
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("获取房屋登记信息信息结果" + result);
        JSONObject json = JSONObject.fromObject(result);
        if ("0".equals(String.valueOf(json.get("status")))) {
            json.put("recode", "000000");
        } else {
            json.put("recode", json.get("code"));
        }
        log.info(Constants.LOG_HEAD + "appApi00107 end.");
        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes("gb18030"));
        } else {
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
            log.info(json.toString());
        }
        return;
    }

    @RequestMapping("/appapi00108.{ext}")
    public void appapi00108(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("户籍信息判断");
        log.info(Constants.LOG_HEAD + "api/appApi00108 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());//测试库时间不准
        log.info("requestTime1111:" + requestTime);

        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
                "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/popInfo.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&cardId=" + form.getBodyCardNumber();
        log.info("户籍信息判断url=" + url);

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        String result = msm.sendGet(url, "UTF-8");
        log.info("户籍信息判断结果" + result);
        JSONObject json = JSONObject.fromObject(result);
        String flag = null;
        if ("00".equals(String.valueOf(json.get("code")))) {
            json.put("recode", "000000");
            if (!"0".equals(String.valueOf(json.get("dataCount")))) {
                String DashujuJson = json.toString();
                log.info("大数据平台直接返回的DashujuJson=" + DashujuJson);
                JSONArray ary = (JSONArray) json.get("datas");
                log.info("datas户籍信息数组ary=" + ary);
                String lastData = "1000-01-01 15:35:28.0";
                String tempDate = "";
                JSONArray ary1 = new JSONArray();
                for (int j = 0; j < ary.size(); j++) {//ary 循环
                    JSONObject obj = ary.getJSONObject(j);
                    tempDate = (String) obj.get("tong_time");//获取时间字段tong_time与tempDate比较，把大的赋值给tong_time
                    if (Long.valueOf(tempDate.replaceAll("[-]", "").replaceAll("[:]", "").replaceAll("[.]", "").replaceAll("[ ]", "")) > Long
                            .valueOf(lastData.replaceAll("[-]", "").replaceAll("[:]", "").replaceAll("[.]", "").replaceAll("[ ]", ""))) {
                        lastData = tempDate;
                        ary1.clear();
                        ary1.add(obj);
                    }
                }
                log.info("最新户籍信息的登记日期=" + lastData);
                log.info("最新户籍信息的ary1=" + ary1);
                log.info("json.getdatas=" + json.get("datas"));
                json.remove("datas");//把原来的datas删掉
                json.remove("dataCount");//把原来的dataCount删掉
                JSON jsonnew = (JSON) ary1.get(0);
                JSONObject jsonObject = JSONObject.fromObject(jsonnew);
                log.info(" jsonObject.get(\"censusAddr\")" + jsonObject.get("censusAddr"));
                String censusAddr = jsonObject.get("censusAddr").toString();//把新的datas放进去

                if (
                        //"宁波","海曙区","江东区","江北区","鄞州区","北仑区","奉化市","慈溪市","象山县","宁海县","镇海区","余姚市"
                        isContain(censusAddr, "象山县") || isContain(censusAddr, "宁海县") || isContain(censusAddr, "镇海区") || isContain(censusAddr, "余姚市") ||
                        isContain(censusAddr, "北仑区") || isContain(censusAddr, "奉化市") || isContain(censusAddr, "慈溪市") ||
                        isContain(censusAddr, "海曙区") || isContain(censusAddr, "江东区") || isContain(censusAddr, "江北区") || isContain(censusAddr, "鄞州区")||
                        isContain(censusAddr, "宁波")
                ) {
                    flag = "0";
                } else {
                    flag = "1";
                }
                json.put("flag", flag);//把新的datas放进去
                json.remove("code");//把原来的datas删掉
            } else {
                flag = "1";
                json.remove("code");//把原来的datas删掉
                json.remove("datas");//把原来的datas删掉
                json.remove("dataCount");//把原来的dataCount删掉
                json.put("flag", flag);//把新的datas放进去
                json.put("recode", "000000");
            }
        } else {
            flag = "1";
            json.remove("code");//把原来的datas删掉
            json.remove("datas");//把原来的datas删掉
            json.remove("dataCount");//把原来的dataCount删掉
            json.put("flag", flag);//把新的datas放进去
            json.put("recode", json.get("code"));
        }
        log.info(Constants.LOG_HEAD + "appApi00108 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }

    /**
     * 企业备案信息查询接口
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00111.{ext}")
    public void appapi00111(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("企业备案信息查询接口");
        log.info(Constants.LOG_HEAD + "appapi00111 begin.");
        log.info("form.getUniscid()" + form.getUniscid());
        log.info("form.getEntname()" + form.getEntname());
        //连接企业备案信息查询接口
        String uniscidUrl = "http://172.168.15.56/sgs/GetCompInfoServlet?uniscid=" + form.getUniscid() + "&entname=" + URLEncoder.encode(form.getEntname(), "utf-8");
        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        long starTime = System.currentTimeMillis();
        String uniscidResult = msm.sendGet(uniscidUrl, "UTF-8");
        log.info("获取企业备案信息查询结果" + uniscidResult);
        //结果示例：{"code":"00","msg":"成功","datas":[{"apprdate":"2017-06-15 00:00:00.0","compform":"个人经营","dom":"浙江省宁波市北仑区大碶街道甬江南路178号","domdistrict":"330206004","dzhy_zfgjjjcdj":[],"e_inv":[],"e_pri_person":[{"cerno":"41152819930512543X","certype":"10","country":"156","email":"18858086821@139.com","lerepsign":"","mobtel":"18858086821","name":"黄明强","personid":"1","position_cn":"","pripid":"3302063000622782","telnumber":"18858086821"}],"entname":"宁波市北仑区大碶明强餐馆","estdate":"2017-06-15 00:00:00.0","industryco":"小吃服务","name":"黄明强","op":"insert","opfrom":"2017-06-15 00:00:00.0","oploc":"浙江省宁波市北仑区大碶街道甬江南路178号","opscope":"餐饮服务（凭《食品经营许可证》经营）。（依法须经批准的项目，经相关部门批准后方可开展经营活动）","opto":"","postalcode":"315806","pripid":"3302063000622782","proloc":"","regcap":"10","regcapcur":"人民币元","regno":"330206602106886","regorg":"宁波市北仑区市场监督管理局","regstate":"开业","reporttype":"个体工商户","sfgzq":"","sfgzqzx":"","uniscid":"92330206MA291QWJ4U","yiedistrict":""}]}
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("appapi00111请求省厅平台耗时" + Time + "毫秒");
        log.info(Constants.LOG_HEAD + "appapi00111 end.");
        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(uniscidResult.toString().getBytes("gb18030"));
        } else {
            response.getOutputStream().write(uniscidResult.toString().getBytes("utf-8"));
        }
        return;
    }

    /**
     * 有无住房查询
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00112.{ext}")
    public void appapi00112(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("有无住房查询");
        log.info(Constants.LOG_HEAD + "appApi00112 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
//        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/A6cNbfw1I308dzt3.htm";
        //新有无房屋接口地址 王文举 修改于2020-8-18
        String url = sjpublicDatasUrl + "api/001008002016013/dataSharing/fL2vfl4I42q4aM08.htm";
        System.out.println("url======"+url);
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("clientusercid", form.getClientusercid());
        params.put("qlrzjh", form.getQlrzjh());
        params.put("computername", form.getComputername());
        params.put("cxfw", form.getCxfw());
        params.put("clientusername", form.getClientusername());
        params.put("qlrmc", form.getQlrmc());


        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");
        log.info("zufang params appapi00112 ========"+params);
        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        long starTime = System.currentTimeMillis();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("appapi00112请求大数据平台耗时" + Time + "毫秒");


        log.info("有无住房查询结果=" + result);
        log.info("有无住房查询结果=" + result.replace("\\", ""));
        String json = result.replace("\\", "");
//		JSONObject json = JSONObject.fromObject(result .replace("\\", ""));
        log.info(Constants.LOG_HEAD + "appApi00112 end.");
        log.info("form.getChannel()" + form.getChannel());

		if(form.getChannel().trim().equals(""))
		{
			response.getOutputStream().write(json.getBytes("gb18030"));
		}else{
			log.info("utf-8");
			response.getOutputStream().write(json.replace("\"{", "{").replace("}\"", "}").getBytes("utf-8"));
		}
        return;
    }

    /**
     * 不动产房屋登记信息
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00113.{ext}")
    public void appapi00113(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("不动产房屋登记信息");
        log.info(Constants.LOG_HEAD + "appApi00113 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        String requestSecret1 =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret1);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + requestSecret1.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
//        String url = publicDatasUrl + "api/001008002016013/dataSharing/0pc9cFD2PctMdGa1.htm";
        //王文举 修改成新的接口地址 2020-8-18
        String url = publicDatasUrl + "api/001008002016013/dataSharing/ceRVQJeB1ecrZme5.htm";
        log.info("budongchan jiekou new url2="+url);
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);

        params.put("clientusercid", form.getClientusercid());
        params.put("clientusername", form.getClientusername());
        params.put("cxfw", form.getCxfw());
        params.put("qlrzjh", form.getQlrzjh());
        params.put("computername", form.getComputername());
        params.put("qlrmc", form.getQlrmc());
        params.put("zl", form.getZl());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("不动产房屋登记信息结果result=" + result);
        log.info("不动产房屋登记信息结果result=" + result.replace("\\", ""));
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));

        if (json.get("datas") != null && (!json.get("datas").toString().equals("null"))) {
            String datas = json.get("datas").toString();
            log.info("不动产房屋登记信息结果datas=" + datas);

            JSONObject datasjson = JSONObject.fromObject(datas.replace("'", "\""));

            String date1 = datasjson.get("date").toString();
            log.info("不动产房屋登记信息结果date1=" + date1);

            JSONArray ary = (JSONArray) datasjson.get("date");
            log.info("ary.size()=" + ary.size());
            if (ary.size() > 0) {
                String date2 = ary.get(0).toString();

                JSONObject date2json = JSONObject.fromObject(date2);
                String zlmodified = date2json.getString("zl").replace(",", " ").replace("，", " ");
                date2json.remove("zl");//应贷款要求，将坐落中的逗号换成空格
                date2json.put("zl", zlmodified);

                json.remove("datas");
                json.put("datas", date2json);
            }

        }


        log.info(Constants.LOG_HEAD + "appApi00113 end.");
        log.info("form.getChannel()" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes("gb18030"));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("\"{", "{").replace("}\"", "}").getBytes("utf-8"));
        }
        return;
    }


    /**
     * 退休信息查询（直连人社局，已弃用）
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00114.{ext}")
    public void appapi00114(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("退休信息查询");
        log.info(Constants.LOG_HEAD + "findRetirePersonnel begin.");
        long starttime = System.currentTimeMillis();
        log.info("YFMAP退休信息查询接口开始时间：" + starttime);
        log.debug("YFMAP退休信息查询接口参数——社会保障号(AAC002)：" + form.getAAC002());
        log.debug("YFMAP退休信息查询接口参数——姓名(AAC003)：" + form.getAAC003());
        log.debug("YFMAP退休信息查询接口参数——事项名称(AAE103)：" + form.getAAE103());
        log.debug("YFMAP退休信息查询接口参数——行政区划代码(AAB301)：" + form.getAAB301());
        log.debug("YFMAP退休信息查询接口参数——发起方报文ID(sndmsgid)：" + form.getSndmsgid());
        // 获取交易请求时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String sndtime = formatter.format(date);
        // 调用人社接口
        String url = "http://172.16.10.92/sjzxDataService/api/sjzx/getData";
        String param = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ROOT><HEAD><MSGCODE>30S011</MSGCODE>"
                + "<MSGTOKEN>08CA70AE-0190-43FC-8863-D08A8615B34E</MSGTOKEN>"
                + "<SNDCODE>330200907</SNDCODE><SNDMSGID>" + form.getSndmsgid()
                + "</SNDMSGID><USERNAME>" + form.getAAC003() + "</USERNAME><SNDTIME>"
                + sndtime + "</SNDTIME></HEAD><CONTENT><AAC002>" + form.getAAC002()
                + "</AAC002><AAC003>" + form.getAAC003() + "</AAC003><AAE103>" + form.getAAE103()
                + "</AAE103><AAB301>" + form.getAAB301() + "</AAB301></CONTENT></ROOT>";
        log.debug("YFMAP退休信息查询接口组装XML报文——XML报文：" + param);
        String result = ExchangeHttpClient.sendPost(url, param, "UTF-8");

        // 返回信息
        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(result.getBytes("gb18030"));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(result.getBytes("utf-8"));
        }
        log.debug("YFMAP退休信息查询接口返回结果——返回XML报文：" + result);
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        log.info("YFMAP退休信息查询接口结束时间：" + endtime);
        log.info("YFMAP退休信息查询接口耗时：" + time);
        log.info(Constants.LOG_HEAD + "findRetirePersonnel end.");
        return;
    }

    /**
     * 人员当前参保信息查询直连人社局，已弃用）
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi00115.{ext}")
    public void appapi00115(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("人员当前参保信息查询");
        long starttime = System.currentTimeMillis();
        log.info("YFMAP人员当前参保信息查询接口开始时间：" + starttime);
        log.debug("YFMAP人员当前参保信息查询接口参数——社会保障号(AAC002)：" + form.getAAC002());
        log.debug("YFMAP人员当前参保信息查询接口参数——姓名(AAC003)：" + form.getAAC003());
        log.debug("YFMAP人员当前参保信息查询接口参数——事项名称(AAE103)：" + form.getAAE103());
        log.debug("YFMAP人员当前参保信息查询接口参数——行政区划代码(AAB301)：" + form.getAAB301());
        log.debug("YFMAP人员当前参保信息查询接口参数——发起方报文ID(sndmsgid)：" + form.getSndmsgid());
        // 获取交易请求时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = new Date();
        String sndtime = formatter.format(date);
        // 调用人社接口
        String url = "http://172.16.10.92/sjzxDataService/api/sjzx/getData";
        String param = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ROOT><HEAD><MSGCODE>20S040</MSGCODE>"
                + "<MSGTOKEN>08CA70AE-0190-43FC-8863-D08A8615B34E</MSGTOKEN>"
                + "<SNDCODE>330200907</SNDCODE><SNDMSGID>" + form.getSndmsgid()
                + "</SNDMSGID><USERNAME>" + form.getAAC003() + "</USERNAME><SNDTIME>"
                + sndtime + "</SNDTIME></HEAD><CONTENT><AAC002>" + form.getAAC002()
                + "</AAC002><AAC003>" + form.getAAC003() + "</AAC003><AAE103>" + form.getAAE103()
                + "</AAE103><AAB301>" + form.getAAB301() + "</AAB301></CONTENT></ROOT>";
        log.debug("YFMAP人员当前参保信息查询接口接口组装XML报文——XML报文：" + param);
        String result = ExchangeHttpClient.sendPost(url, param, "UTF-8");
        // 返回信息
        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(result.getBytes("gb18030"));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(result.getBytes("utf-8"));
        }
        log.debug("YFMAP人员当前参保信息查询接口返回结果——返回XML报文：" + result);
        long endtime = System.currentTimeMillis();
        long time = endtime - starttime;
        log.info("YFMAP人员当前参保信息查询接口结束时间：" + endtime);
        log.info("YFMAP人员当前参保信息查询接口耗时：" + time);
        log.info(Constants.LOG_HEAD + "findInsuredPersonnel end.");
        return;
    }

    @RequestMapping("/appapi00116.{ext}")
    public void appapi00116(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("户籍信息判断新");
        log.info(Constants.LOG_HEAD + "api/appApi00116 begin.");

        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);

        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);

        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/001003001029/dataSharing/bnpmrbd3c4945G03.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&czrkgmsfhm=" + form.getBodyCardNumber();

//				+ "&czrkgmsfhm=" + form.getBodyCardNumber()+"&additional="+"{'powerMatters':'0000-00','subPowerMatters':'0000-0101','accesscardId':'33071918111111784523'}";
        //公民身份证号码		czrkgmsfhm
        //附加信息					additional			在办理行政权力和公共服务事项而需调用数据共享接口时，必须增加一个访问的入参：additional（附加参数），其中包含powerMatters（主权力事项编码）、subPowerMatters（子权力事项编码）、accesscardId（访问者身份信息，可以是访问者姓名、身份证号或对接接口系统的访问用户名，三选一）三部分内容；
        //具体参数传递的方式为：additional={&quot;powerMatters&quot;:&quot;许可-0000-00&quot;,&quot;subPowerMatters&quot;:&quot;许可-0000-0101&quot;,&quot;accesscardId&quot;:&quot;33071918111111784523&quot;}。前期已经对接的部门，需要按照该模式进行改造，增加输入参数。">在办理行政权力和公共服务事项而需调用数据共享接口时，必须增加一个访问的入参：additional（附加参数），其中包含powerMatters（主权力事项编码）、subPowerMatters（子权力事项编码）、accesscardId（访问者身份信息，可以是访问者姓名、身份证号或对接接口系统的访问用户名，三选一）三部分内容；具体参数传递的方式为：additional={"powerMatters":"许可-0000-00","subPowerMatters":"许可-0000-0101","accesscardId":"33071918111111784523"}。
        //前期已经对接的部门，需要按照该模式进行改造，增加输入参数。
        log.info("省公安厅居民户口簿（个人）url=" + url);

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        String result = msm.sendGet(url, "UTF-8");
        log.info("根据公民身份号码查询户口本信息结果" + result);
        JSONObject json = JSONObject.fromObject(result);
        String flag = null;
        if ("00".equals(String.valueOf(json.get("code")))) {
            json.put("recode", "000000");
            if (!"[]".equals(String.valueOf(json.get("datas")))) {
                String DashujuJson = json.get("datas").toString().replace("\\\"", "\"").replace("[", "").replace("]", "");
                log.info("大数据平台直接返回的DashujuJson=" + DashujuJson);
                JSONObject DashujuJson1 = JSONObject.fromObject(DashujuJson);
                String czrkjgssx = DashujuJson1.get("czrkjgssx").toString();
                if (isContain(czrkjgssx, "海曙") || isContain(czrkjgssx, "江东") || isContain(czrkjgssx, "江北") || isContain(czrkjgssx, "鄞州") ||
                        isContain(czrkjgssx, "镇海") || isContain(czrkjgssx, "北仑") || isContain(czrkjgssx, "宁海") ||
                        isContain(czrkjgssx, "象山") || isContain(czrkjgssx, "慈溪") || isContain(czrkjgssx, "余姚") || isContain(czrkjgssx, "奉化")) {
                    flag = "0";
                } else {
                    flag = "1";
                }
                json.put("flag", flag);
                json.remove("code");
                json.remove("data");
                json.remove("datas");
                json.put("datas", DashujuJson);
                json.remove("dataCount");
            } else {
                flag = "1";
                json.remove("code");
                json.remove("dataCount");
                json.put("flag", flag);
                json.put("recode", "000000");
            }
        } else {
            flag = "1";
            json.remove("code");
            json.remove("dataCount");
            json.put("flag", flag);
            json.put("recode", json.get("code"));
        }
        log.info(Constants.LOG_HEAD + "appApi00116 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }


    @RequestMapping("/appapi00117.{ext}")
    public void appapi00117(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市医疗保险参保人员信息查询");
        log.info(Constants.LOG_HEAD + "api/appApi00117 begin.");

        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);

        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);

        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/cXx8c8g74bVoHo46.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&name=" + URLEncoder.encode(form.getAccname(), "utf-8")
                + "&cardId=" + form.getBodyCardNumber();
        log.info("宁波市医疗保险参保人员信息查询url=" + url);

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        String result = msm.sendGet(url, "UTF-8");
        log.info("宁波市医疗保险参保人员信息查询" + result.replace("\\\"", "\""));
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        String datas = json.get("datas").toString();
        System.out.println(datas);
        JSONObject datasjson = JSONObject.fromObject(datas);
        json.remove("datas");
        json.put("datas", datasjson.get("datas"));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00117 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00118.{ext}")
    public void appapi00118(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("房管局商品房合同查询");
        log.info(Constants.LOG_HEAD + "api/appApi00118 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getBodyCardNumber());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getBodyCardNumber());
        System.out.println("projectId======");


        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016015001/dataSharing/0db05fRPUbfEe74e.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getAccname());
        params.put("idNo", form.getBodyCardNumber());
        params.put("address", form.getAddress());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getBodyCardNumber()
                + "\",\"accesscardId\":\"" + form.getBodyCardNumber() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getBodyCardNumber()
                + "\",\"projectId\":\"\"}");


        log.info("房管局商品房合同查询url=" + url);

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("房管局商品房合同查询" + result);
        log.info("房管局商品房合同查询1" + result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "\"").replace("\\r\\n", ""));
        JSONObject json = JSONObject.fromObject(result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "'").replace("\\r\\n", ""));
        String datas = json.get("datas").toString();
        log.info("datas====================" + datas);
        JSONObject datasjson = JSONObject.fromObject(datas);
        json.remove("datas");

        if (datasjson.get("result") != null) {
            JSONObject resultjson = JSONObject.fromObject(datasjson.get("result"));
            String locatedmodified = resultjson.get("located").toString().replace(",", " ").replace("，", " ");//应贷款要求，将坐落中的都好换成空格
            resultjson.remove("located");
            resultjson.put("located", locatedmodified);
            //add by ll 新增买房人数BuyerNum和卖房人数SellerNum 2019/7/9
            String contractBuyer = resultjson.get("contractBuyer").toString();
            contractBuyer = contractBuyer.replace(" ", "").substring(1, contractBuyer.length() - 1);// 去掉头尾[]
            log.info("contractBuyer====================" + contractBuyer);
            int BuyerNum = 0;
            int index ;
            if (contractBuyer.length() != 0) {
                    for (int i = contractBuyer.length(); i > -1; i--) {
                        index = contractBuyer.lastIndexOf("idNo");
                        if (index == i) {
                            contractBuyer = contractBuyer.substring(0, i);
                            System.out.println(contractBuyer + "-----" + i);
                            BuyerNum++;
                        }
                }
            }

            String contractSeller = resultjson.get("contractSeller").toString();
            contractSeller = contractSeller.replace(" ", "").substring(1, contractSeller.length() - 1);// 去掉头尾[]
            int SellerNum = 0;
            int index1 ;
            if (contractSeller.length() != 0) {
                    for (int i = contractSeller.length(); i > -1; i--) {
                        index1 = contractSeller.lastIndexOf("idNo");
                        if (index1 == i) {
                            contractSeller = contractSeller.substring(0, i);
                            System.out.println(contractSeller + "-----" + i);
                            SellerNum++;
                        }
                }
            }
            json.put("BuyerNum", BuyerNum);
            json.put("SellerNum", SellerNum);

            json.put("datas", resultjson);
        }

        json.put("status", datasjson.get("status"));
        json.put("message", datasjson.get("message"));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00118 end.");
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

    @RequestMapping("/appapi00119.{ext}")
    public void appapi00119(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省公安厅居民身份证新");
        log.info(Constants.LOG_HEAD + "api/appApi00119 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/001003001029/dataSharing/3sb8c6d027ZhDW04.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&czrkgmsfhm=" + form.getCertinum()
                + "&additional=" + URLEncoder.encode("{\"powerMatters\":\"许可0000-00\",\"subPowerMatters\":\"许可0000-0101\",\"accesscardId\": " + form.getCertinum() + ",\"materialName\":\"社会团体变更登记申请表\",\"sponsorName\":" + form.getAccname() + ",\"sponsorCode\":\"91330100799655058B\",\"projectId\":\"330000261711151100004\"}", "utf-8");
        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

        log.info("省公安厅居民身份证新url=" + url);
        String result = msm.sendGet(url, "UTF-8");

        log.info("省公安厅居民身份证新" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00119 end.");
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


    /**
     * 个人贷款审批进度查询网厅
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00120.{ext}")
    public String appApi00120(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00120 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00120_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00120 end.");
        return "/index";
    }

    /**
     * 个人贷款信息查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00121.{ext}")
    public String appApi00121(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00121 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00121_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00121 end.");
        return "/index";
    }

    /**
     * 还款明细查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00122.{ext}")
    public String appApi00122(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00122 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00122_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00122 end.");
        return "/index";
    }

    /**
     * 还款计划查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00123.{ext}")
    public String appApi00123(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00123 begin.");

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00123_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00123 end.");
        return "/index";
    }

    /**
     * 个人贷款试算页面信息获取
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00124.{ext}")
    public String appApi00124(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00124 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00124_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00124 end.");
        return "/index";
    }

    /**
     * 商贷基本信息查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00125.{ext}")
    public String appApi00125(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00125 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00125_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00125 end.");
        return "/index";
    }

    /**
     * 商贷可提取金额计算
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00126.{ext}")
    public String appApi00126(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00126 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00126_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00126 end.");
        return "/index";
    }

    /**
     * 银行卡校验
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00127.{ext}")
    public String appApi00127(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00127 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00127_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00127 end.");
        return "/index";
    }

    /**
     * 查询个人公贷合同号
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00128.{ext}")
    public String appApi00128(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00128 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00128_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00128 end.");
        return "/index";
    }

    /**
     * 查询个人是否面签
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00130.{ext}")
    public String appApi00130(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00130 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00130_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00130 end.");
        return "/index";
    }

    /**
     * 个人明细查询网厅
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00131.{ext}")
    public String appApi00131(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00131 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00131_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00131 end.");
        return "/index";
    }


    @RequestMapping("/appapi00132.{ext}")
    public void appapi00132(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("自然人、法人信用信息核查接口");
        log.info(Constants.LOG_HEAD + "api/appApi00132 begin.");

//		String url = "http://172.16.10.91:8081/xypt/services/xyhc?wsdl";//测试
        String url = "http://172.16.10.91:8083/xypt/services/xyhc?wsdl";//生产
        System.out.println("url===============" + url);
        System.out.println("form.getXtjrm()===" + form.getXtjrm());
        System.out.println("form.getMc()===" + form.getMc());
        System.out.println("form.getTyshxydm()===" + form.getTyshxydm());
        System.out.println("form.getCzrxm()===" + form.getCzrxm());
        System.out.println("form.getCzrsfzhm()===" + form.getCzrsfzhm());
        System.out.println("form.getSxmc()===" + form.getSxmc());
        System.out.println("form.getSxdm()===" + form.getSxdm());
        System.out.println("form.getHcsy()===" + form.getHcsy());
        System.out.println("form.getBmmc()===" + form.getBmmc());
        System.out.println("form.getBmdm()===" + form.getBmdm());
        System.out.println("form.getHcrq()===" + form.getHcrq());
        System.out.println("form.getTimestamp()===" + form.getTimestamp());
        System.out.println("form.getTysqsbm()===" + form.getTysqsbm());

        String xtjrmmw = DES3.encode(form.getXtjrm(), "http://www.nbcredit.gov.cn/1011");
        System.out.println("xtjrmmw============" + xtjrmmw);
        String xyhcSoapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dao=\"http://dao.xyhc.cssnb.com/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<dao:xyhc>" +
                "<arg0>" +
                "<bmdm>" + form.getBmdm() + "</bmdm>" +
                "<bmmc>" + form.getBmmc() + "</bmmc>" +
                "<czrsfzhm>" + form.getCzrsfzhm() + "</czrsfzhm>" +
                "<czrxm>" + form.getCzrxm() + "</czrxm>" +
                "<hcly></hcly>" +
                "<hcrq>" + form.getHcrq() + "</hcrq>" +
                "<hcsy>" + form.getHcsy() + "</hcsy>" +
                "<mc>" + form.getMc() + "</mc>" +
                "<sxdm>" + form.getSxdm() + "</sxdm>" +
                "<sxmc>" + form.getSxmc() + "</sxmc>" +
                "<timestamp>" + form.getTimestamp() + "</timestamp>" +
                "<tyshxydm>" + form.getTyshxydm() + "</tyshxydm>" +
                "<tysqsbm>" + form.getTysqsbm() + "</tysqsbm>" +
                "<xtjrm>" + form.getXtjrm() + "</xtjrm>" +
                "<xtjrmmw>" + xtjrmmw + "</xtjrmmw>" +
                "</arg0>" +
                "</dao:xyhc>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        System.out.println("xyhcSoapXml=========" + xyhcSoapXml);
        //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
        String rep = HttpClientCallSoapUtil.doPostSoap1_1(url, xyhcSoapXml, "");

        System.out.println("自然人、法人信用信息核查接口result0===============" + rep);
        rep = subString(rep, "<return>", "</return>");
        System.out.println("自然人、法人信用信息核查接口result================" + rep);

        JSONObject json = JSONObject.fromObject(rep.replace(",{}", ""));

        String code = json.get("code").toString();

        if ("202000".equals(code)) {
            json.put("recode", "000000");
            json.remove("code");
            System.out.println("recode赋值000000");

            String resultString = json.get("result").toString();
            System.out.println("自然人、法人信用信息核查接口resultString================" + resultString);
            String heimdString;

            if (resultString.equals("{}")) {
                heimdString = "{}";
            } else {
                JSONObject resultJson = JSONObject.fromObject(resultString);
                Object heimd = resultJson.get("heimd");
                heimdString = heimd.toString();
                System.out.println("自然人、法人信用信息核查接口heimdString================" + heimdString);
                heimdString = heimdString.substring(1, heimdString.length() - 1).replace("\"发布时间\"}]}", "\"发布时间\"}]}|");
                System.out.println("自然人、法人信用信息核查接口heimdString ||================" + heimdString);
                String[] strs = heimdString.split("\\|,");
                heimdString = strs[0];
                System.out.println("自然人、法人信用信息核查接口heimdString end================" + heimdString);
            }

            json.remove("result");
            String tysqsbm = json.get("tysqsbm").toString();
            String tysbm = json.get("tysbm").toString();
            json.remove("tysqsbm");
            json.remove("tysbm");
            json.put("code", code);
            json.put("tysqsbm", tysqsbm);
            json.put("tysbm", tysbm);
            json.put("result", heimdString.replace("{", "(").replace("}", ")").replace("\\", ""));
        } else {
            json.put("recode", json.get("code"));
        }

        log.info(Constants.LOG_HEAD + "appApi00132 end.");
        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("\\", ""));
            response.getOutputStream().write(json.toString().replace("\\", "").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("\\", "").getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00133.{ext}")
    public void appapi00133(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("信用信息核查情况反馈接口");
        log.info(Constants.LOG_HEAD + "api/appApi00133 begin.");

        //String url = "http://172.16.10.91:8081/xypt/services/hcjgfk?wsdl";//测试
        String url = "http://172.16.10.91:8083/xypt/services/hcjgfk?wsdl";//生产
        System.out.println("url===============" + url);

        System.out.println("form.getTysqsbm()===" + form.getTysqsbm());
        System.out.println("form.getMc()===" + form.getMc());
        System.out.println("form.getTyshxydm()===" + form.getTyshxydm());
        System.out.println("form.getCzrxm()===" + form.getCzrxm());
        System.out.println("form.getCzrsfzhm()===" + form.getCzrsfzhm());
        System.out.println("form.getCslbdm()===" + form.getCslbdm());
        System.out.println("form.getCslbmc()===" + form.getCslbmc());
        System.out.println("form.getCsdm()===" + form.getCsdm());
        System.out.println("form.getCsmc()===" + form.getCsmc());
        System.out.println("form.getMs()===" + form.getMs());
        System.out.println("form.getFkrq()===" + form.getFkrq());
        System.out.println("form.getTimestamp()===" + form.getTimestamp());

        String xtjrmmw = DES3.encode(form.getXtjrm(), "http://www.nbcredit.gov.cn/1011");
        System.out.println("xtjrmmw============" + xtjrmmw);
        String hcjgfkSoapXml = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dao=\"http://dao.hcjgfk.cssnb.com/\">" +
                "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<dao:hcjgfk>" +
                "<arg0>" +
                "<xtjrm>" + form.getXtjrm() + "</xtjrm>" +
                "<xtjrmmw>" + xtjrmmw + "</xtjrmmw>" +
                "<tysqsbm>" + form.getTysqsbm() + "</tysqsbm>" +
                "<tysbm>" + form.getTysbm() + "</tysbm>" +
                "<mc>" + form.getMc() + "</mc>" +
                "<tyshxydm>" + form.getTyshxydm() + "</tyshxydm>" +
                "<czrxm>" + form.getCzrxm() + "</czrxm>" +
                "<czrsfzhm>" + form.getCzrsfzhm() + "</czrsfzhm>" +
                "<cslbdm>" + form.getCslbdm() + "</cslbdm>" +
                "<cslbmc>" + form.getCslbmc() + "</cslbmc>" +
                "<csdm>" + form.getCsdm() + "</csdm>" +
                "<csmc>" + form.getCsmc() + "</csmc>" +
                "<ms>" + form.getMs() + "</ms>" +
                "<fkrq>" + form.getFkrq() + "</fkrq>" +
                "<timestamp>" + form.getTimestamp() + "</timestamp>" +
                "</arg0>" +
                "</dao:hcjgfk>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";

        System.out.println("hcjgfkSoapXml=========" + hcjgfkSoapXml);
        //采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
        String rep = HttpClientCallSoapUtil.doPostSoap1_1(url, hcjgfkSoapXml, "");
        System.out.println("信用信息核查情况反馈接口result0===============" + rep);
        rep = subString(rep, "<return>", "</return>");
        System.out.println("信用信息核查情况反馈接口result===============" + rep);

        JSONObject json = JSONObject.fromObject(rep);
        String code = json.get("code").toString();

        if ("202000".equals(String.valueOf(json.get("code")))) {
            json.put("recode", "000000");
            json.remove("code");
            System.out.println("recode赋值000000");
        } else {
            log.info("自然人、法人信用信息核查接口查询结果");
            json.put("recode", json.get("code"));
            json.remove("code");
        }
        json.put("code", code);

        log.info(Constants.LOG_HEAD + "appApi00133 end.");
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


    @RequestMapping("/appapi00134.{ext}")
    public void appapi00134(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省国土资源厅不动产权证新");
        log.info(Constants.LOG_HEAD + "api/appApi00134 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/001003001029/certificate/03eKXR52cflGW8fa.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&qlr=" + URLEncoder.encode(form.getAccname(), "utf-8")
                + "&zjh=" + form.getCertinum()
                + "&zl=" + URLEncoder.encode(form.getZl(), "utf-8")
                + "&bdcqzh=" + URLEncoder.encode(form.getBdcqzh(), "utf-8")
                + "&xzqbm=" + form.getXzqbm()
                + "&additional=" + URLEncoder.encode("{\"powerMatters\":\"许可0000-00\",\"subPowerMatters\":\"许可0000-0101\",\"accesscardId\": " + form.getCertinum() + ",\"materialName\":\"社会团体变更登记申请表\",\"sponsorName\":\"" + form.getAccname() + ",\"sponsorCode\":\"91330100799655058B\",\"projectId\":\"330000261711151100004\"}", "utf-8");
/*		URLEncoder.encode(form.getClientusername(),"utf-8")
 		权利人	        qlr	权利人（必传）
		权利人证件号	zjh	权利人证件号（必传）
		不动产坐落	zl	    不动产坐落
		不动产权字号	bdcqzh	不动产权字号
		行政区编码	xzqbm	行政区编码（必传）*/

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

        log.info("省国土资源厅不动产权证新url=" + url);
        String result = msm.sendGet(url, "UTF-8");

        log.info("省国土资源厅不动产权证新" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00134 end.");
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

    @RequestMapping("/appapi00135.{ext}")
    public void appapi00135(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("低保救助信息");
        log.info(Constants.LOG_HEAD + "api/appApi00135 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/lowestRescueInfo.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getCertinum());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

/*		String url = publicDatasUrl + "api/lowestRescueInfo.htm" + "?appKey="
				+ appKey + "&sign=" + sign + "&requestTime=" + requestTime
				+ "&cardId="+form.getCertinum();
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

		log.info("低保救助信息url="+url);
		String result = msm.sendGet(url, "UTF-8");*/

        log.info("低保救助信息" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        json.remove("data");
        log.info(Constants.LOG_HEAD + "appApi00135 end.");
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

    @RequestMapping("/appapi00136.{ext}")
    public void appapi00136(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("失业保险参保人员信息");
        log.info(Constants.LOG_HEAD + "api/appApi00136 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/unemploymentInsuredInfo.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&cardId=" + form.getCertinum()
                + "&name=" + URLEncoder.encode(form.getAccname(), "utf-8")
                + "&additional=" + URLEncoder.encode("{\"powerMatters\":\"许可0000-00\",\"subPowerMatters\":\"许可0000-0101\",\"accesscardId\": " + form.getCertinum() + ",\"materialName\":\"社会团体变更登记申请表\",\"sponsorName\":" + form.getAccname() + ",\"sponsorCode\":\"91330100799655058B\",\"projectId\":\"330000261711151100004\"}", "utf-8");

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

        log.info("失业保险参保人员信息url=" + url);
        String result = msm.sendGet(url, "UTF-8");

        log.info("失业保险参保人员信息" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00136 end.");
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

    @RequestMapping("/appapi00137.{ext}")
    public void appapi00137(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("社会保险个人参保信息");
        log.info(Constants.LOG_HEAD + "api/appApi00137 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001003085/dataSharing/socialPersonalParticipationInfo.htm";
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
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("社会保险个人参保信息" + result);
        JSONObject json = JSONObject.fromObject(result);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00137 end.");
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

    @RequestMapping("/appapi00138.{ext}")
    public void appapi00138(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省公安厅居民身份证旧");
        log.info(Constants.LOG_HEAD + "api/appApi00138 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001003001029/dataSharing/2s3sb9do8S3VDaj5.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("czrkgmsfhm", form.getCertinum());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省公安厅居民身份证旧" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00138 end.");
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

    @RequestMapping("/appapi00139.{ext}")
    public void appapi00139(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市存量房交易合同查询");
        log.info(Constants.LOG_HEAD + "api/appApi00139 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getBodyCardNumber());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getBodyCardNumber());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016015001/dataSharing/e38SRc245gDLUf44.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getAccname());
        params.put("idNo", form.getBodyCardNumber());
        params.put("address", form.getAddress());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        log.info("宁波市存量房交易合同查询" + result);
        log.info("宁波市存量房交易合同查询1" + result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "\"").replace("\\r\\n", ""));
        JSONObject json = JSONObject.fromObject(result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "'").replace("\\r\\n", ""));
        String datas = json.get("datas").toString();
        System.out.println(datas);
        JSONObject datasjson = JSONObject.fromObject(datas);
        json.remove("datas");
        json.put("datas", datasjson.get("result"));
        json.put("status", datasjson.get("status"));
        json.put("message", datasjson.get("message"));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00139 end.");
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

    @RequestMapping("/appapi00140.{ext}")
    public void appapi00140(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("购房发票查询");
        log.info(Constants.LOG_HEAD + "api/appApi00140 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016288/dataSharing/7eGmeY4T2e0uSPc8.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);

        params.put("nsrmc", form.getNsrmc());
        params.put("kprqz", form.getKprqz());
        params.put("nsrsbh", form.getNsrsbh());
        params.put("kprqq", form.getKprqq());
        params.put("fpdm", form.getFpdm());
        params.put("fphm", form.getFphm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("购房发票查询" + result);
        log.info("购房发票查询1" + result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "\"").replace("\\r\\n", ""));
        JSONObject json = JSONObject.fromObject(result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "'").replace("\\r\\n", ""));
        json.remove("data");
        json.remove("dataCount");
        String datas = json.get("datas").toString();
        json.remove("datas");
        System.out.println(datas);
        JSONObject datasjson = JSONObject.fromObject(datas);

        String truedatas = datasjson.get("datas").toString();
        String truedataCount = datasjson.get("dataCount").toString();
        json.put("dates", truedatas);
        json.put("dataCount", truedataCount);
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00140 end.");
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


    @RequestMapping("/appapi00141.{ext}")
    public void appapi00141(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波地区死亡数据共享new");
        log.info(Constants.LOG_HEAD + "api/appApi00141 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        String url = publicDatasUrl + "api/001008002016127/dataSharing/75cS9338ifpamDWf.htm" + "?appKey="
                + appKey + "&sign=" + sign + "&requestTime=" + requestTime
                + "&name=" + URLEncoder.encode(form.getAccname(), "utf-8")
                + "&idCardCode=" + form.getCertinum()
                + "&additional=" + URLEncoder.encode("{\"powerMatters\":\"许可0000-00\",\"subPowerMatters\":\"许可0000-0101\",\"accesscardId\": " + form.getCertinum() + ",\"materialName\":\"社会团体变更登记申请表\",\"sponsorName\":" + form.getAccname() + ",\"sponsorCode\":\"91330100799655058B\",\"projectId\":\"330000261711151100004\"}", "utf-8");
        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();

        log.info("宁波地区死亡数据共享newurl=" + url);

        String result = msm.sendGet(url, "UTF-8");
        log.info("宁波地区死亡数据共享new" + result);
        log.info("宁波地区死亡数据共享new1" + result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "\"").replace("\\r\\n", ""));
        JSONObject json = JSONObject.fromObject(result.replace("\\r\\n    ", "").replace("\\r\\n  ", "").replace("\\\"", "'").replace("\\r\\n", ""));
        String datas = json.get("datas").toString();
        log.info("死亡接口datas-----------" + datas);
        if (datas.length() > 0) {
            JSONObject datasjson = JSONObject.fromObject(datas);
            json.put("deathDate", datasjson.get("deathDate").toString());
            json.put("idCardCode", datasjson.get("idCardCode").toString());
            json.put("idCardType", datasjson.get("idCardType").toString());
            json.put("name", datasjson.get("name").toString());
        }

		/*json.remove("datas");
		json.remove("dataCount");
		json.remove("data");*/
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00141 end.");
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


    /**
     * 个人年度对账单查询
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00142.{ext}")
    public String appApi00142(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00142 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00142_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00142 end.");
        return "/index";
    }


    @RequestMapping("/appapi00143.{ext}")
    public void appapi00143(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("契税完税信息接口");
        log.info(Constants.LOG_HEAD + "api/appApi00143 begin.");
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
        String url = sjpublicDatasUrl + "api/001008002016288/dataSharing/dc7s3B05Xdhx8580.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("nsrqz", form.getNsrqz());
        params.put("nsrqq", form.getNsrqq());
        params.put("nsrsbh", form.getNsrsbh());
        params.put("qshm", form.getQshm());
        params.put("nsrmc", form.getNsrmc());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");
        long starTime = System.currentTimeMillis();
        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("契税完税信息接口url=" + url);
        log.info("契税完税信息接口" + result);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        //JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace("\"{", "{").replace("}\"", "}"));
        //JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace("[", "").replace("]", ""));

        JSONObject json = JSONObject.fromObject(result.replace("\\", "").replace(":\"{", ":{").replace("}\",", "},").replace("%},", "%}\","));

        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");
        log.info(Constants.LOG_HEAD + "appApi00143 end.");
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

    @RequestMapping("/appapi00144.{ext}")
    public void appapi00144(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String PROPERTIES_FILE_NAME = "properties.properties";
        form.setBusinName("省建设厅商品房买卖合同接口");
        log.info(Constants.LOG_HEAD + "api/appApi00199 begin.");

        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00144.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始****处理省建设厅商品房买卖合同接口：" + rm);
        log.info(Constants.LOG_HEAD + "appApi00144 end.");
        if (form.getChannel().trim().equals("")) {
            log.info("gbk");

            response.getOutputStream().write(rm.getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(rm.getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00145.{ext}")
    public void appapi00145(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String PROPERTIES_FILE_NAME = "properties.properties";
        form.setBusinName("省建设厅二手房转让合同接口");
        log.info(Constants.LOG_HEAD + "api/appApi00199 begin.");

        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00145.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始****处理省建设厅二手房转让合同接口：" + rm);

        log.info(Constants.LOG_HEAD + "appApi00145 end.");
        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            response.getOutputStream().write(rm.getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(rm.getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00146.{ext}")
    public void appapi00146(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("地名核准");
        log.info(Constants.LOG_HEAD + "api/appApi00146 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016008/dataSharing/YuYQq41rW0t72794.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getAddress());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("地名核准" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00146 end.");
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

    @RequestMapping("/appapi00147.{ext}")
    public void appapi00147(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("部门申报系统获取统一办件编码接口");
        log.info(Constants.LOG_HEAD + "api/appApi00147 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001003001/windowSystem/73fqu7K7bFbNdJ86.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("applyCardType", form.getApplyCardType());
        params.put("belongSystem", form.getBelongSystem());
        params.put("applyName", form.getApplyName());
        params.put("applyCardNumber", form.getApplyCardNumber());
        params.put("busType", form.getBusType());
        params.put("deptId", form.getDeptId());
        params.put("applyFrom", form.getApplyFrom());
        params.put("areaCode", form.getAreaCode());
        params.put("serviceCodeId", form.getServiceCodeId());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("部门申报系统获取统一办件编码接口" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        JSONObject datasjson = JSONObject.fromObject(datas);
        String success = datasjson.get("success").toString();
        String errorCode = datasjson.get("errorCode").toString();
        String errorMsg = datasjson.get("errorMsg").toString();
        String data = datasjson.get("data").toString();
        if (!data.equals("null")) {
            JSONObject datajson = JSONObject.fromObject(data);
            String projId = datajson.get("projId").toString();
            json.put("projId", projId);
        } else {
            json.put("projId", "");
        }

        json.remove("dataCount");
        json.remove("datas");
        json.remove("data");
        json.put("success", success);
        json.put("errorCode", errorCode);
        json.put("errorMsg", errorMsg);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00147 end.");
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

    @RequestMapping("/appapi00148.{ext}")
    public void appapi00148(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("统一编码标记废弃接口");
        log.info(Constants.LOG_HEAD + "api/appApi00147 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001003001/windowSystem/Jd3f5b95lK9cRO3d.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("belongSystem", form.getBelongSystem());
        params.put("projId", form.getProjId());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("统一编码标记废弃接口" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00148 end.");
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

    @RequestMapping("/appapi00149.{ext}")
    public void appapi00149(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("获取基本办件信息接口");
        log.info(Constants.LOG_HEAD + "api/appApi00149 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001003001/windowSystem/Wcb3r5294U1908b9.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("belongSystem", form.getBelongSystem());
        params.put("projId", form.getProjId());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("获取基本办件信息接口" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00149 end.");
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

    @RequestMapping("/appapi00150.{ext}")
    public void appapi00150(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市城镇职工个人参保证明基础信息");
        log.info(Constants.LOG_HEAD + "api/appApi00150 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/d7u41TmD8Jjb8aYc.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("aac003", form.getAAC003());
        params.put("aac002", form.getAAC002());
        params.put("aab301", form.getAAB301());
        params.put("pageSize", form.getPageSize());
        log.info("aac003"+form.getAAC003());
        log.info("aac002"+form.getAAC002());
        log.info("aab301"+form.getAAB301());
        log.info("pageSize"+form.getPageSize());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("宁波市城镇职工个人参保证明基础信息" + result);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        //JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        JSONObject json = JSONObject.fromObject(result);
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }else{
            json.put("recode", "999999");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00150 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("\\", ""));
            response.getOutputStream().write(json.toString().replace("\\", "").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00151.{ext}")
    public void appapi00151(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("单位社保缴纳基础信息查询");
        log.info(Constants.LOG_HEAD + "api/appApi00151 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/Fab1AIG18ZuP50C0.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("bae001", form.getBae001());
        params.put("aab001", form.getAab001());
        params.put("bab010", form.getBab010());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("单位社保缴纳基础信息查询" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00151 end.");
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

    @RequestMapping("/appapi00152.{ext}")
    public void appapi00152(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("劳动能力鉴定结论");
        log.info(Constants.LOG_HEAD + "api/appApi00152 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/3Dd5b1eK5ye0JT1a.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getName());
        params.put("cardId", form.getCardId());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("劳动能力鉴定结论" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00152 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("'", "\""));
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").replace("\"{", "{").replace("}\"", "}").getBytes("utf-8"));
        }
        return;
    }


    @RequestMapping("/appapi00153.{ext}")
    public void appapi00153(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("医保费用个人负担证明");
        log.info(Constants.LOG_HEAD + "api/appApi00153 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/nk3Hdm78Uc04T6r0.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);

        String value = "{\"FYQSSJ\":\"" + form.getFYQSSJ()
                + "\",\"JYLX\":\"" + form.getJYLX()
                + "\",\"FYJSSJ\":\"" + form.getFYJSSJ()
                + "\",\"SFZH\":\"" + form.getSFZH()
                + "\",\"JKLX\":\"" + form.getJKLX() + "\"}";

        System.out.println("value======" + value);

        params.put("message", value);
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("医保费用个人负担证明" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00153 end.");
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


    @RequestMapping("/appapi00154.{ext}")
    public void appapi00154(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市医疗保险参保人员信息（新）");
        log.info(Constants.LOG_HEAD + "api/appApi00154 begin.");
        //连接浙江省公共数据平台获取数据appapi10107
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/3c9ZJ9bd2jrc8ttf.htm";
        params.put("appKey", appKey);
        params.put("publicDatasUrl", publicDatasUrl);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getName());
        params.put("cardId", form.getCardId());
        log.info("appKey:" + appKey);
        log.info("sign:" + sign);
        log.info("requestTime:" + requestTime);
        log.info("publicDatasUrl:" + publicDatasUrl);
        log.info("url:" + url);
        log.info("name:" + form.getName());
        log.info("cardId:" + form.getCardId());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8").replace("\\\\\"", "\"").replace("[", "").replace("]", "");
        System.out.println(result);

        log.info("宁波市医疗保险参保人员信息（新）" + result);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");

        log.info(Constants.LOG_HEAD + "appApi00154 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
            json.remove("dataCount");
            json.remove("data");
            if (json.get("code").equals("00")) {
                json.put("recode", "000000");
            }
            json.remove("code");
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("'", "\""));
            //add by ll 只保留一条数据 2019/7/20
            String datas = json.get("datas").toString();
            System.out.println("datasold==============" + datas);
            int index = datas.lastIndexOf(",{");
            for (int i = datas.length(); i > -1; i--) {
                if (index == i) {
                    datas = datas.substring(0, i);
                    System.out.println(datas + "-----" + i);
                }
            }
            System.out.println("datasnew==============" + datas);
            json.remove("datas");
            json.put("datas", datas);
            System.out.println("json==============" + json);
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").getBytes(request.getCharacterEncoding()));
        } else {
            System.out.println("result==========" + result);
            JSONObject json = JSONObject.fromObject(result.replace("\\\\", ""));
            String datas = json.get("datas").toString();

            log.info("datasold2==========" + datas);
            //add by ll 只保留一条数据 2019/7/20
            int index = datas.lastIndexOf(",{");
            for (int i = datas.length(); i > -1; i--) {
                if (index == i) {
                    datas = datas.substring(0, i);
                    System.out.println(datas + "-----" + i);
                }
            }
            log.info("datanew2==========" + datas);
            JSONObject datasjson = JSONObject.fromObject(datas.replace("\"}", "\"}}").replace("\"datas\":}", "\"datas\":\"\"}"));
            log.info("datasjson==========" + datasjson);
            if (!CommonUtil.isEmpty(datasjson.get("datas"))) {
                JSONObject datasjson1 = JSONObject.fromObject(datasjson.get("datas"));
                log.info("datasjson1==============" + datasjson1);
                json.put("NAME", datasjson1.get("NAME"));
                json.put("CARDID", datasjson1.get("CARDID"));
                json.put("SEX", datasjson1.get("SEX"));
                json.put("INSUREDSTATUS", datasjson1.get("INSUREDSTATUS"));
                json.put("RETIREDSTATUS", datasjson1.get("RETIREDSTATUS"));
            }
            json.remove("datas");
            json.remove("dataCount");
            json.remove("data");
            if (json.get("code").equals("00")) {
                json.put("recode", "000000");
            }
            json.remove("code");
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00155.{ext}")
    public void appapi00155(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市企业养老保险参保人员信息（新）");
        log.info(Constants.LOG_HEAD + "api/appApi00155 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/fOLa4b7O4Ac6kWd4.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("name", form.getName());
        params.put("cardId", form.getCardId());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("宁波市企业养老保险参保人员信息（新）" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00155 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("'", "\""));
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\\\\", "").getBytes("utf-8"));
        }
        return;
    }

    @RequestMapping("/appapi00156.{ext}")
    public void appapi00156(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市失业人员信息");
        log.info(Constants.LOG_HEAD + "api/appApi00156 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "appKey").trim().toLowerCase();
        String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getAAC002());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAAC003());
        System.out.println("sponsorCode======" + form.getAAC002());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = publicDatasUrl + "api/001008002016011/dataSharing/1Ha10R14cFZ694U0.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("aac003", form.getAAC003());
        params.put("aac002", form.getAAC002());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("宁波市失业人员信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        datas = xml2jsonString(datas);
        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00156 end.");
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


    @RequestMapping("/appapi00157.{ext}")
    public void appapi00157(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("宁波治安户籍迁出信息");
        log.info(Constants.LOG_HEAD + "api/appApi00157 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016006/dataSharing/3d709Pj90ieK9fDc.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("gmsfhm", form.getGmsfhm());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("宁波治安户籍迁出信息" + result.replace("\\\"", "\""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        json.remove("data");
        json.put("recode", "000000");
        log.info(Constants.LOG_HEAD + "appApi00157 end.");
        log.info("form.getChannel()=" + form.getChannel());

        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            log.info("json.toString()==========" + json.toString().replace("'", "\""));
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\"{", "{").replace("}\"", "}").getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(json.toString().replace("'", "\"").replace("\"{", "{").replace("}\"", "}").getBytes("utf-8"));
        }
        return;
    }


    @RequestMapping("/appapi00158.{ext}")
    public void appapi00158(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String PROPERTIES_FILE_NAME = "properties.properties";
        form.setBusinName("省建设厅-省国土资源厅不动产权证");
        log.info(Constants.LOG_HEAD + "api/appApi00158 begin.");
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00158.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始****处理省建设厅-省国土资源厅不动产权证：" + rm);

        log.info(Constants.LOG_HEAD + "appApi00158 end.");
        if (form.getChannel().trim().equals("")) {
            log.info("gbk");
            response.getOutputStream().write(rm.getBytes(request.getCharacterEncoding()));
        } else {
            log.info("utf-8");
            response.getOutputStream().write(rm.getBytes("utf-8"));
        }
        return;
    }


    @RequestMapping("/appapi00159.{ext}")
    public void appapi00159(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("可信身份认证接口");
        log.info(Constants.LOG_HEAD + "api/appApi00159 begin.");
        String appKey = "72c0e70dd5db4fed9e500d3068886173";
        String secretKey = "2c4b33e2a1a7423fbea451c0f1e50ba6";
        long starTime = System.currentTimeMillis();
//        String url = "http://172.16.10.92:9097/kx-api/v1.0/authenticate";
        String url = "https://kexin.gat.zj.gov.cn:9097/kx-api/v1.0/authenticate";
        String authIdCard;
        authIdCard = DesUtil.encryptCodecEncode(form.getCertinum(), secretKey);
        String authName = DesUtil.encryptCodecEncode(form.getAccname(), secretKey);
        String filepath = form.getFilepath();
        String filename = form.getFilename();

        //获取压缩图片数据
        byte[] data = ImgTools.compressUnderSize(readInByteArray(new File(filepath + filename)), 25 * 1024);
        log.info("get picture data successfully" + "filepath==" + filepath + "filename==" + filename);

        //生成压缩图片
        FileUtils.writeByteArrayToFile(new File(filepath + "compressed/" + filename), data);
        log.info("compressed successfully" + "filepath==" + filepath + "filename==" + filename);

        //图片转字符串
        String picData = Base64Utils.ImageToBase64ByLocal(filepath + "compressed/" + filename).replace("\n", "");
        System.out.println("picData=====" + picData);

        String postjson = "{\"authIdCard\":\"" + authIdCard + "\"," +
                "\"authName\":\"" + authName + "\"," +
                "\"picData\":\"" + picData + "\"," +
                "\"authMode\":\"0X42\"," +
                "\"appKey\":\"" + appKey + "\"}";

        System.out.println("postjson===========" + postjson);
        log.info("rlrz 开始认证人脸识别");

//        HttpClient httpClient = new DefaultHttpClient();
        org.apache.http.client.HttpClient httpClient = null;

        HttpPost post = new HttpPost(url);
        log.info("rlrz2 开始认证人脸识别");
        StringEntity postingString = new StringEntity(postjson, "utf-8");// json传递
        postingString.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        log.info("rlrz3 开始认证人脸识别");
        try {
            httpClient = new SSLClient();
            HttpResponse response2 = httpClient.execute(post);
            String result = EntityUtils.toString(response2.getEntity());
            System.out.println("rlrz result=" + result);
            log.info("rlrz4 可信身份认证接口" + result);
            long endTime = System.currentTimeMillis();
            long Time = endTime - starTime;
            System.out.println("请求可信身份认证接口耗时" + Time + "毫秒");
            System.out.println("可信身份认证接口 cost " + Time + "milliseconds");
            JSONObject json = JSONObject.fromObject(result);
            if (json.get("code").equals("00")) {
                json.put("recode", "000000");
            }
            log.info(Constants.LOG_HEAD + "appApi00159 end.");
            log.info("form.getChannel()=" + form.getChannel());

            if (form.getChannel().trim().equals("")) {
                log.info("gbk");
                log.info("json.toString()==========" + json.toString());
                response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
            } else {
                log.info("utf-8");
                response.getOutputStream().write(json.toString().getBytes("utf-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return;
    }


    @RequestMapping("/appapi00160.{ext}")
    public void appapi00160(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("住房公积金降低缴存比例和缓缴申请表");
        log.info(Constants.LOG_HEAD + "api/appApi00160 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/dxa7druUj99ce686.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);

        params.put("jcrs", form.getJcrs());
        params.put("jcjsze", form.getJcjsze());
        params.put("dwzfgjjzh", form.getDwzfgjjzh());
        params.put("lxdh", form.getLxdh());
        params.put("materialName", form.getMaterialForm());
        params.put("areaCode", form.getAreaCode());
        params.put("sponsorCode", form.getSponsorCode());
        params.put("jcbl", form.getJcbl());
        params.put("lxr", form.getLxr());
        params.put("yb", form.getYb());
        params.put("yjcjs", form.getYjcjs());
        params.put("txdz", form.getTxdz());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("住房公积金降低缴存比例和缓缴申请表" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");
        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00160 end.");
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


    @RequestMapping("/appapi00161.{ext}")
    public void appapi00161(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省工商局营业执照");
        log.info(Constants.LOG_HEAD + "api/appApi00161 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/r0dJDWe3e2fFUb42.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("regno", form.getRegno());
        params.put("entname", form.getEntname());
        params.put("uniscid", form.getUniscid());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省工商局营业执照" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00161 end.");
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


    @RequestMapping("/appapi00162.{ext}")
    public void appapi00162(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("法定代表人信息");
        log.info(Constants.LOG_HEAD + "api/appApi00162 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/4fQYE947196eWmJb.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("registerNo", form.getRegisterNo());
        params.put("companyName", form.getCompanyName());
        params.put("uniscid", form.getUniscid());
        params.put("entType", form.getEntType());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("法定代表人信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00162 end.");
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

    @RequestMapping("/appapi00163.{ext}")
    public void appapi00163(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("企业变更信息（工商）");
        log.info(Constants.LOG_HEAD + "api/appApi00163 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/dJEetRQea6426Q31.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("regno", form.getRegno());
        params.put("entname", form.getEntname());
        params.put("uniscid", form.getUniscid());
        params.put("AltDate", form.getAltDate());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("企业变更信息（工商）" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00163 end.");
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

    @RequestMapping("/appapi00164.{ext}")
    public void appapi00164(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("出入境证件");
        log.info(Constants.LOG_HEAD + "api/appApi00164 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/b8l87wa0teN8gKE8.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getCardId());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("出入境证件" + result.replace("\\\"", "\""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00164 end.");
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

    @RequestMapping("/appapi00165.{ext}")
    public void appapi00165(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("火化信息");
        log.info(Constants.LOG_HEAD + "api/appApi00165 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/Wf4J8sVGZfce7aj0.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getCardId());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("火化信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00165 end.");
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

    @RequestMapping("/appapi00166.{ext}")
    public void appapi00166(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("公安户籍信息");
        log.info(Constants.LOG_HEAD + "api/appApi00166 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/U8tmD1UJe22fMeO3.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cardId", form.getCardId());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("公安户籍信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00166 end.");
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

    @RequestMapping("/appapi00167.{ext}")
    public void appapi00167(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("注销企业信息");
        log.info(Constants.LOG_HEAD + "api/appApi00167 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/ZTjYFf6gd30I4Z86.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("USCC", form.getTyshxydm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("注销企业信息" + result.replace("\\\"", "\""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00167 end.");
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

    @RequestMapping("/appapi00168.{ext}")
    public void appapi00168(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("商品房预(销)售许可证");
        log.info(Constants.LOG_HEAD + "api/appApi00168 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/lfWBa9ebO8sv8ddd.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("YSXKZTYBH", form.getYSXKZTYBH());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("商品房预(销)售许可证" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        String datas = json.get("datas").toString();
        json.remove("datas");

        System.out.println("datas==============" + datas);
        json.put("datas", datas);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00168 end.");
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

    @RequestMapping("/appapi00169.{ext}")
    public void appapi00169(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("获取交易部门合同影像（不动产定制）");
        log.info(Constants.LOG_HEAD + "api/appApi00169 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016015001/dataSharing/VD30Ym76Lc93199b.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("contractNo", form.getHTBH());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
//     System.out.println(result);
		log.info("获取交易部门合同影像（不动产定制）"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));

        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", "").replace("\\r\\n  ", ""));

        if (json.get("code").equals("00")) {
            String datas = json.get("datas").toString();
            JSONObject datasjson = JSONObject.fromObject(datas);
            String resultString = datasjson.get("result").toString();

            JSONObject resultjson = JSONObject.fromObject(resultString);

            String base64PDF = resultjson.get("base64PDF").toString();
            String saleContractNo = resultjson.get("saleContractNo").toString();
            String saleContractTypeName = resultjson.get("saleContractTypeName").toString();
            String errorMsg = resultjson.get("errorMsg").toString();

            if (!"null".equals(base64PDF)) {
                String saleContractPDFfilepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "saleContractPDFfilepath");
                System.out.println("saleContractPDFfilepath============" + saleContractPDFfilepath);

                String filepath = saleContractPDFfilepath + saleContractNo + ".pdf";
                System.out.println("filepath============" + filepath);

                boolean boo = Base64Utils.Base64ToImage(base64PDF, filepath);
                System.out.println("boo============" + boo);
                String imagepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "imagepath");
                System.out.println("imagepath============" + imagepath);

                int pageno = PDF2IMAGE.pdf2Image(filepath, imagepath, 300);

                ZipUtils.dozip(imagepath + saleContractNo + "/", imagepath + "/" + saleContractNo + ".zip");

                if (boo) {
                    json.put("dowloadflag", "合同影像下载成功");
                    json.put("saleContractNo", saleContractNo);
                    json.put("PDFpath", filepath);
                    json.put("count", pageno);
                    for (int i = 1; i < pageno + 1; i++) {
                        json.put("PNGpath" + String.valueOf(i), imagepath + saleContractNo + "/" + saleContractNo + "_" + String.valueOf(i) + ".png");
                    }
                } else {
                    json.put("dowloadflag", "合同影像下载失败");
                    json.put("errorMsg", errorMsg);
                }
            }

            json.remove("datas");
            json.remove("data");
            json.put("recode", "000000");
        } else {
            json.put("recode", "000000");
        }


        log.info(Constants.LOG_HEAD + "appApi00169 end.");
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

    @RequestMapping("/appapi00170.{ext}")
    public void appapi00170(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省国土资源厅不动产权证（新）---市平台");
        log.info(Constants.LOG_HEAD + "api/appApi00170 begin.");
        //连接宁波市数据接口共享平台
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getQlrzjh());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getQlrmc());
        System.out.println("sponsorCode======" + form.getQlrzjh());
        System.out.println("projectId======");

        System.out.println("qlr======" + form.getQlrmc());
        System.out.println("zjh======" + form.getQlrzjh());
        System.out.println("zl======" + form.getZl());
        System.out.println("bdcqzh======" + form.getBdcqzh());
        System.out.println("xzqbm======" + form.getXzqbm());

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/cs6cbGjex6944kH6.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("qlr", form.getQlr());
        params.put("zjh", form.getZjh());
        params.put("zl", form.getZl());
        params.put("bdcqzh", form.getBdcqzh());
        params.put("xzqbm", form.getXzqbm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省国土资源厅不动产权证（新）---市平台" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        //JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        JSONObject json = JSONObject.fromObject(result);
        String datas = json.get("datas").toString();
        //log.info("datasold===================="+datas);

        //add by ll 新增不动产权证人数 2019/7/20
        int BuyerNum = 0;
        if (datas.length() != 0) {
            BuyerNum = 1;
            System.out.println("datas.length()===" + datas.length());
            int index = datas.lastIndexOf("},{");
            for (int i = datas.length(); i > -1; i--) {
                if (index == i) {
                    System.out.println("index111=====" + index);
                    String datas1 = datas.substring(0, i);
                    System.out.println(datas + "-----" + i);
                    BuyerNum++;
                    System.out.println("BuyerNum===" + BuyerNum);
                    index = datas1.lastIndexOf("},{");
                    System.out.println("index222=====" + index);
                }
            }
        }
        System.out.println("BuyerNum==============" + BuyerNum);
        JSONArray datasjson = JSONArray.fromObject(datas);
        //System.out.println("datasjson=============="+datasjson);
        //删除字段ELC_LICENCE_FILE
        for (int i = 0; i < BuyerNum; i++) {
            JSONObject jsonObject = datasjson.getJSONObject(i);
            //log.info("ELC_LICENCE_FILE===================="+ i + "  " + jsonObject.get("ELC_LICENCE_FILE"));
            jsonObject.remove("ELC_LICENCE_FILE");
            //log.info("jsonObject===="+ i + "  " +jsonObject);
            //datas = datasjson.toString();
        }
        //json.remove("datas");
        System.out.println("datasjson11==============" + datasjson);
        json.put("datas", datasjson);
        json.put("BuyerNum", BuyerNum);
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00170 end.");
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

    /**
     * 贷款结清证明打印（补打）
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00171.{ext}")
    public String appApi00171(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00171 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00171_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00171 end.");
        return "/index";
    }

    /**
     * 个人免缴接口(网厅)
     * @param form 请求参数
     * @param modelMap 返回数据容器
     * @return 回调页面名
     */
    @RequestMapping("/appapi00172.{ext}")
    public String appApi00172(AppApi030Form form, ModelMap modelMap) throws Exception {
        log.info(Constants.LOG_HEAD + "appApi00172 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00172_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appApi00172 end.");
        return "/index";
    }

    @RequestMapping("/appapi00204.{ext}")
    public void appapi00204(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("行政单位信息");
        log.info(Constants.LOG_HEAD + "api/appApi00204 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
//		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/2H2e96ty3oq219e4.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("sydwTycode", form.getSydwTycode());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("行政单位信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00204 end.");
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

    @RequestMapping("/appapi00203.{ext}")
    public void appapi00203(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("事业单位信息");
        log.info(Constants.LOG_HEAD + "api/appApi00203 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        //		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/8u4r7Efjc00952R9.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("sydwTycode", form.getSydwTycode());


        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("事业单位信息" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00203 end.");
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

    @RequestMapping("/appapi00200.{ext}")
    public void appapi00200(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省工商局营业执照（新）");
        log.info(Constants.LOG_HEAD + "api/appApi00200 begin.");
        //连接浙江省公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
//	String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
        System.out.println("powerMatters======" + form.getPowerMatters());
        System.out.println("subPowerMatters======" + form.getSubPowerMatters());
        System.out.println("accesscardId======" + form.getCertinum());
        System.out.println("materialName======" + form.getMaterialName());
        System.out.println("sponsorName======" + form.getAccname());
        System.out.println("sponsorCode======" + form.getCertinum());
        System.out.println("projectId======");

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/qA26but95TJ5dnvc.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("uniscid", form.getUniscid());
        params.put("entname", form.getEntname());
        params.put("regno", form.getRegno());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省工商局营业执照（新）" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00200 end.");
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

    @RequestMapping("/appapi00205.{ext}")
    public void appapi00205(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("省公安厅居民户口簿（个人）");
        log.info(Constants.LOG_HEAD + "api/appApi00205 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
//		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/eR4nJy7WuBcoc114.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("czrkgmsfhm", form.getCzrkgmsfhm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省公安厅居民户口簿（个人）" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00205 end.");
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

    @RequestMapping("/appapi00206.{ext}")
    public void appapi00206(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("民政部_婚姻登记信息核验(个人)");
        log.info(Constants.LOG_HEAD + "api/appApi00206 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/nke28OEX80C290S6.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cert_num_man", form.getCert_num_man());
        params.put("name_man", form.getName_man());
        System.out.println("cert_num_man========" + form.getCert_num_man());
        System.out.println("name_man========== " + form.getName_man());

        //新方法
        String xxx = url + "?requestTime=" + requestTime + "&appKey=" + appKey + "&sign=" + sign
                + "&cert_num_man=" + form.getCert_num_man() + "&name_man=" + URLEncoder.encode(form.getName_man(), "utf-8");
        System.out.println("url=" + xxx);

        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        String result = msm.sendGet(xxx, "UTF-8");
        log.info("新结果" + result);


/*	params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCert_num_man()
			+"\",\"accesscardId\":\""+form.getCert_num_man()+"\",\"materialName\":\""+form.getMaterialName()
			+"\",\"sponsorName\":\""+form.getName_man()+"\",\"sponsorCode\":\""+form.getCert_num_man()
			+"\",\"projectId\":\"\"}");

	SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	System.out.println(result);*/

        log.info("民政部_婚姻登记信息核验(个人)" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        String datas = json.get("datas").toString();
        JSONObject datasjson = JSONObject.fromObject(datas);
        String resultString = datasjson.get("result").toString();
        JSONObject resultjson = JSONObject.fromObject(resultString);
        String resultString1 = resultjson.get("result").toString();
        datasjson.put("result", resultString1);
        System.out.println("datasjson====" + datasjson);
        json.put("datas", datasjson);

        log.info(Constants.LOG_HEAD + "appApi00206 end.");
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

    @RequestMapping("/appapi00207.{ext}")
    public void appapi00207(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("民政部_婚姻登记信息核验(双方)");
        log.info(Constants.LOG_HEAD + "api/appApi00207 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
//		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/74Pe8a8I98EfaFe9.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("cert_num_man", form.getCert_num_man());
        params.put("name_man", form.getName_man());
        params.put("cert_num_woman", form.getCert_num_woman());
        params.put("name_woman", form.getName_woman());

        //新方法
        String xxx = url + "?requestTime=" + requestTime + "&appKey=" + appKey + "&sign=" + sign
                + "&cert_num_man=" + form.getCert_num_man() + "&name_man=" + URLEncoder.encode(form.getName_man(), "utf-8")
                + "&cert_num_woman=" + form.getCert_num_woman() + "&name_woman=" + URLEncoder.encode(form.getName_woman(), "utf-8");

        log.info("url" + xxx);
        SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
        String result = msm.sendGet(xxx, "UTF-8");
        log.info("新结果" + result);

/*	params.put("additional", "{\"powerMatters\":\""+form.getPowerMatters()+"\",\"subPowerMatters\":\""+form.getSubPowerMatters()+"\",\"accesscardId\":\""+form.getCertinum()
			+"\",\"accesscardId\":\""+form.getCertinum()+"\",\"materialName\":\""+form.getMaterialName()
			+"\",\"sponsorName\":\""+form.getAccname()+"\",\"sponsorCode\":\""+form.getCertinum()
			+"\",\"projectId\":\"\"}");

	SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
	String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	System.out.println(result);*/

        log.info("民政部_婚姻登记信息核验(双方)" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        String datas = json.get("datas").toString();
        JSONObject datasjson = JSONObject.fromObject(datas);
        String resultString = datasjson.get("result").toString();
        JSONObject resultjson = JSONObject.fromObject(resultString);
        String resultString1 = resultjson.get("result").toString();
        datasjson.put("result", resultString1);
        System.out.println("datasjson====" + datasjson);
        json.put("datas", datasjson);
        log.info(Constants.LOG_HEAD + "appApi00207 end.");
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

    @RequestMapping("/appapi00208.{ext}")
    public void appapi00208(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("信用中心核查奖惩数据返回接口");
        log.info(Constants.LOG_HEAD + "api/appApi00208 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //String requestSecret =  PublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info("requestSecret" + requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        log.info("appKey" + appKey);
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        log.info("sjpublicDatasUrl" + sjpublicDatasUrl);
        //		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "publicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/bf5Eo7VYc6x8WAa6.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        //params.put("message", form.getMessage());
        //private static final String message="{\"servicecode\":\"许可-00393-002\",\"servicename\":\"测试1\",\"projid\":\"123\",\"areacode\":\"123456\",\"dept\":\"01\",\"deptname\":\"测试部门\",\"name\":\"任巍巍330227198708146819\",\"creditkey\":\"G2TN5J7M86K099EI\"}";
        //传入参数 message,json中的name字段,其他字段写死
        params.put("message", "{\"servicecode\":\"其他-02492-003\",\"servicename\":\"建造、翻建、大修自住住房申请住房公积金贷款\",\"projid\":\"" + form.getProjid() + "\",\"areacode\":\"330200\",\"dept\":\"83\",\"deptname\":\"宁波市住房公积金管理中心\",\"name\":\"" + form.getMessage() + "\",\"creditkey\":\"G2TN5J7M86K099EI\"}");
        //params.put("message","{\"servicecode\":\"许可-00393-002\",\"servicename\":\"测试1\",\"projid\":\"123\",\"areacode\":\"123456\",\"dept\":\"01\",\"deptname\":\"测试部门\",\"name\":\""+form.getMessage()+"\",\"creditkey\":\"G2TN5J7M86K099EI\"}");
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        //log.info("信用中心核查奖惩数据返回接口"+result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00208 end.");
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

    @RequestMapping("/appapi00209.{ext}")
    public void appapi00209(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        form.setBusinName("信用中心核查反馈接口");
        log.info(Constants.LOG_HEAD + "api/appApi00209 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/B2d0gcak2xpa2mm2.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("checkCode", form.getCheckCode());
        params.put("content", form.getContent());
        params.put("resultCode", form.getResultCode());
        params.put("status", form.getStatus());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");


        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("信用中心核查反馈接口" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        System.out.println("Dashuju cost " + Time + "milliseconds");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00209 end.");
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

    @RequestMapping("/appapi00210.{ext}")
    public void appapi00210(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("信用中心信用核查状态");
        log.info(Constants.LOG_HEAD + "api/appApi00210 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/Td247bE55KqUybQ5.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("checkcode", form.getCheckcode());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("信用中心信用核查状态" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00210 end.");
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


    @RequestMapping("/appapi00211.{ext}")
    public void appapi00211(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("低保救助信息(新)");
        log.info(Constants.LOG_HEAD + "api/appApi00211 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/53dlf09Xa4Lf50n3.htm";
        params.put("appKey", appKey);
        log.info("appKey" + appKey);
        params.put("sign", sign);
        log.info("sign" + sign);
        params.put("requestTime", requestTime);
        log.info("requestTime" + requestTime);
        params.put("aHAP0015", form.getaHAP0015());
        log.info("HAP0015" + form.getaHAP0015());
        params.put("bHAX0114", form.getbHAX0114());
        log.info("bHAX0114" + form.getbHAX0114());
        params.put("bHAX0115", form.getbHAX0115());
        log.info("bHAX0115" + form.getbHAX0115());
        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("低保救助信息(新)" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00211 end.");
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

    @RequestMapping("/appapi00212.{ext}")
    public void appapi00212(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("省公安厅居民身份证（新）-市平台");
        log.info(Constants.LOG_HEAD + "api/appApi00212 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info("requestSecret" + requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/vdjb19VHdIh27Rc5.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("czrkxm", form.getCzrkxm());
        params.put("czrkgmsfhm", form.getCzrkgmsfhm());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        log.info("appKey--" + appKey);
        log.info("sign--" + sign);
        log.info("requestTime--" + requestTime);
        log.info("czrkxm--" + form.getCzrkxm());
        log.info("czrkgmsfhm--" + form.getCzrkgmsfhm());
        log.info("params--" + params);
        log.info("url--" + url);

        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");

        log.info("result--" + result);

        log.info("省公安厅居民身份证（新）-市平台" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        log.info("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00212 end.");
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

    @RequestMapping("/appapi00213.{ext}")
    public void appapi00213(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("契税完税信息接口(省地税局)");
        log.info(Constants.LOG_HEAD + "api/appApi00213 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/2f9ao9fFI7x8t19a.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("syzlwz", form.getSyzlwz());
        params.put("cqmj", form.getCqmj());
        params.put("csfnsrsbh", form.getCsfnsrsbh());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

						/*String xxx=url+"?requestTime="+requestTime+"&appKey="+appKey+"&sign="+sign
								+"&syzlwz="+URLEncoder.encode(form.getSyzlwz(),"utf-8")+"&cqmj="+URLEncoder.encode(form.getCqmj(),"utf-8")
								+"&csfnsrsbh="+form.getCsfnsrsbh();
						log.info("url"+xxx);

						SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
						String result = msm.sendGet(xxx, "UTF-8");
						log.info("新结果"+result);*/

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        log.info("result: " + result);

        log.info("契税完税信息接口(省地税局)" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00213 end.");
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

    @RequestMapping("/appapi00214.{ext}")
    public void appapi00214(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("省公安厅居民户口簿电子证照接口");
        log.info(Constants.LOG_HEAD + "api/appApi00214 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/7Fb658i578DIc3ma.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("Organization_Id", form.getOrganization_Id());
        params.put("gmsfhm", form.getGmsfhm());
        params.put("User_ID", form.getUser_ID());
        params.put("xm", form.getXm());
        params.put("User_Name", form.getUser_Name());
        params.put("Organization", form.getOrganization());
        log.info("url===="+url);
        log.info("appKey===="+appKey);
        log.info("sign===="+sign);
        log.info("requestTime===="+requestTime);
        log.info("form.getOrganization_Id()===="+form.getOrganization_Id());
        log.info("form.getGmsfhm()===="+form.getGmsfhm());
        log.info("form.getUser_ID()===="+form.getUser_ID());
        log.info("form.getXm()===="+form.getXm());
        log.info("form.getUser_Name()===="+form.getUser_Name());
        log.info("form.getOrganization()===="+form.getOrganization());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("省公安厅居民户口簿电子证照接口" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");

        if (json.get("code").equals("00")) {
            String datas = json.get("datas").toString();
            JSONObject datasjson = JSONObject.fromObject(datas);
            String resultString = datasjson.get("data").toString();

            JSONObject resultjson = JSONObject.fromObject(resultString);

            String base64PDF = resultjson.get("pdfdata").toString();
            log.info("base64PDF============" + base64PDF);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String saleContractNo = dateFormat.format(new Date());
            log.info("saleContractNo============" + saleContractNo);
            //String saleContractTypeName=resultjson.get("saleContractTypeName").toString();
            String errorMsg = resultjson.get("fhms").toString();
            if (base64PDF.length() != 0) {
                log.info("存放pdf路径");
                String saleContractPDFfilepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "saleContractPDFfilepath");
                log.info("saleContractPDFfilepath============" + saleContractPDFfilepath);

                String filepath = saleContractPDFfilepath + saleContractNo + ".pdf";
                log.info("filepath============" + filepath);

                boolean boo = Base64Utils.Base64ToImage(base64PDF, filepath);
                log.info("boo============" + boo);
                String imagepath = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "imagepath");
                log.info("imagepath============" + imagepath);

                int pageno = PDF2IMAGE.pdf2Image(filepath, imagepath, 300);
                //压缩文件
                //ZipUtils.dozip(imagepath+saleContractNo+"/", imagepath+"/"+saleContractNo+".zip");

                if (boo) {
                    json.put("dowloadflag", "电子证照下载成功");
                    json.put("saleContractNo", saleContractNo);
                    json.put("PDFpath", filepath);
                    json.put("count", pageno);
                    for (int i = 1; i < pageno + 1; i++) {
                        json.put("PNGpath" + String.valueOf(i), imagepath + saleContractNo + "/" + saleContractNo + "_" + String.valueOf(i) + ".png");
                    }
                } else {
                    json.put("dowloadflag", "电子证照下载失败");
                    json.put("errorMsg", errorMsg);
                }
            }
            json.remove("datas");
            json.remove("data");
            json.put("recode", "000000");
        } else {
            json.put("recode", "000000");
        }

        log.info(Constants.LOG_HEAD + "appApi00214 end.");
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


    @RequestMapping("/appapi00215.{ext}")
    public void appapi00215(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("宁波市乡村建设规划许可证");
        log.info(Constants.LOG_HEAD + "api/appApi00215 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info("form.getCenterId()============" + form.getCenterId());
        log.info("requestSecret============" + requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016014/dataSharing/uJdPe49vGelf8oLc.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("buildUnit", form.getBuildUnit());
        params.put("qgbh", form.getQgbh());
        params.put("qsbh", form.getQsbh());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("宁波市乡村建设规划许可证" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        log.info(Constants.LOG_HEAD + "appApi00215 end.");
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

    @RequestMapping("/appapi00216.{ext}")
    public void appapi00216(AppApi50001Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        form.setBusinName("公证书证照");
        log.info(Constants.LOG_HEAD + "api/appApi00216 begin.");
        //连接市级公共数据平台获取数据
        String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
        String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
        String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();

        long starTime = System.currentTimeMillis();
        HashMap<String, String> params = new HashMap<String, String>();
        String url = sjpublicDatasUrl + "api/001008002016003/dataSharing/jt84K436Ihcw5eY0.htm";
        params.put("appKey", appKey);
        params.put("sign", sign);
        params.put("requestTime", requestTime);
        params.put("sfzh", form.getSFZH());
        log.info("appKey=============" + appKey);
        log.info("sign=============" + sign);
        log.info("requestTime=============" + requestTime);
        log.info("sfzh=============" + form.getSFZH());

        params.put("additional", "{\"powerMatters\":\"" + form.getPowerMatters() + "\",\"subPowerMatters\":\"" + form.getSubPowerMatters() + "\",\"accesscardId\":\"" + form.getCertinum()
                + "\",\"accesscardId\":\"" + form.getCertinum() + "\",\"materialName\":\"" + form.getMaterialName()
                + "\",\"sponsorName\":\"" + form.getAccname() + "\",\"sponsorCode\":\"" + form.getCertinum()
                + "\",\"projectId\":\"\"}");

        SimpleHttpMessageUtil simpleHttpMessageUtil = new SimpleHttpMessageUtil();
        String result = simpleHttpMessageUtil.sendPost(url, params, "utf-8");
        System.out.println(result);

        log.info("公证书证照" + result.replace("\\\"", "\"").replace("[", "").replace("]", ""));
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'").replace("[", "").replace("]", ""));
        json.remove("dataCount");
        json.remove("data");
        log.info("json=============" + json);

        String datas = json.get("datas").toString();
        JSONObject datasjson = JSONObject.fromObject(datas);
        String dataString = datasjson.get("data").toString();

        JSONObject datajson = JSONObject.fromObject(dataString);

        String fileUrl = datajson.get("fileUrl").toString();
        fileUrl.replace("\\\"", "");
        log.info("fileUrl============" + fileUrl);

        if (json.get("code").equals("00")) {
            json.put("recode", "000000");
        }
        json.remove("code");

        json.remove("datas");
        json.put("fileUrl", fileUrl);
        log.info(Constants.LOG_HEAD + "appApi00216 end.");
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

    @RequestMapping("/appapi00199.{ext}")
    public void appapi00199(AppApi50004Form form, ModelMap modelMap, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String PROPERTIES_FILE_NAME = "properties.properties";
        form.setBusinName("单笔短信发送接口");
        log.info(Constants.LOG_HEAD + "api/appApi00199 begin.");

        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00199.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始****处理单笔短信发送接口：" + rm);
        Gson gson = new Gson();
        Map ybmapmsg = gson.fromJson(rm, new TypeToken<Map<String, String>>() {
        }.getType());
        HashMap resultMap = new HashMap();
        resultMap.put("recode", ybmapmsg.get("recode"));
        resultMap.put("msg", ybmapmsg.get("msg"));
        resultMap.put("miSeqno", ybmapmsg.get("miSeqno"));

        System.out.println("单笔短信发送接口result===============" + resultMap.toString());
        JSONObject json = JSONObject.fromObject(resultMap);
        log.info(Constants.LOG_HEAD + "appApi00199 end.");
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


    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static boolean isContain(String s1, String s2) {
        return s1.contains(s2);
    }

    /**
     * 截取字符串str中指定字符 strStart、strEnd之间的字符串
     *
     * @param str
     * @param str
     * @param str
     * @return
     */
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }

    public static String xml2jsonString(String xml) throws IOException, org.json.JSONException {
        org.json.JSONObject xmlJSONObj = XML.toJSONObject(xml);
        return xmlJSONObj.toString();
    }

    private byte[] readInByteArray(File imgFile) {
        try {
            return IOUtils.toByteArray(new FileInputStream(imgFile));

        } catch (IOException e) {
            throw new IllegalStateException("read picture unsuccessfully", e);
        }
    }
}
