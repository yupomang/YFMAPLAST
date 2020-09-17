/**
 *
 */
package com.yondervision.yfmap.controller;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.form.*;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

/**
 *
 * @author luolin
 *
 *
 */
@Controller
public class AppApi00230Contorller {
    Logger log = Logger.getLogger("YFMAP");

    /**
     *  宁波公积金注销申请接收
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00230.{ext}")
    public String appapi00230(AppApi00230Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00230 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00230_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00230 end.");
        return "";
    }

    /**
     *  宁波公积金自助机贷款合同变更
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00231.{ext}")
    public String appapi00231(AppApi00230Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00231 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00231_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00231 end.");
        return "";
    }

    /**
     *  贷款申请前查询
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00232.{ext}")
    public String appapi00232(AppApi00230Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00232 begin.");
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00232_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00232 end.");
        return "";
    }

    @RequestMapping("/appapi00233.{ext}")
    public void appapi00233(AppApi030Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("住房公积金个人缴存明细(省平台)核心调用");
        log.info(Constants.LOG_HEAD + "api/appApi00233 begin.");

        long starTime = System.currentTimeMillis();
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80010.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("AppApi401ServiceImpl:开始异地贷款缴存使用证明打印接口：" + rm);
		/*Gson gson = new Gson();
		Map ybmapmsg = gson.fromJson(rm, new TypeToken<Map<String, String>>() {
		}.getType());*/

		/*int index = rm.lastIndexOf("}{");
		System.out.println("index ：" + index);
		for(int i = rm.length();i>-1;i--){
			if(index == i){
				rm = rm.substring(0,i+1);
				System.out.println(rm+"-----"+i);

			}
		}

		System.out.println("rm ：" + rm);*/

        JSONObject json = JSONObject.fromObject(rm);

        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("请求大数据平台耗时" + Time + "毫秒");
        response.getOutputStream().write(json.toString().getBytes());

        log.info(Constants.LOG_HEAD + "appApi00233 end.");
        log.info("form.getChannel()=" + form.getChannel());

        return;
    }

    @RequestMapping("/appapi00234.{ext}")
    public String appapi00234(AppApi00234Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "api/appApi00234 begin.");
        //log.info("form.getFile()wenjianliu=======" + form.getFile());
        log.info("form.getFilename()wenjianming=======" + form.getFilename());
        //网厅传参加密文件流file字段调用解密方法生成图片
        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + form.getCenterId() + ".Handle00234_" + form.getCenterId()).newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00234 end.");
        return "";
    }

    /**
     *  贷款申请前查询
     * @param form
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/appapi00235.{ext}")
    public String appapi00235(AppApi00235Form form, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(Constants.LOG_HEAD + "appapi00235 begin.");
        log.info("form1====="+(CommonUtil.getStringParams(form)));
        //获取请求json
        StringBuffer sb = new StringBuffer() ;
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s  ;
        while((s=br.readLine())!=null){
            sb.append(s) ;
        }
        String str =sb.toString();
        System.out.println("请求json=====" + str);
        //request.getParameter("json");
        log.info("request.getParameter(\"json\")====="+request.getParameter("json"));//使用getParameter方法会中文乱码异常 须使用getInputStream方法
        //json=%7B%22unitlinkman%22%3A%22%E8%81%94%E7%B3%BB%E4%BA%BA%22%2C%22unioncode%22%3A%22113300000024821119%22%2C%22flag%22%3A%224%22%2C%22qdapprnum%22%3A%22233580db-a6be-45d3-b372-af2e36ce7ad6%22%2C%22unitaccnum%22%3A%22%22%2C%22certinum%22%3A%22330211197801231023%22%2C%22unitaccname%22%3A%22%E8%81%94%E7%B3%BB%E4%BA%BA%22%2C%22transdate%22%3A%22%22%2C%22instanceid%22%3A%22%22%2C%22servicecode%22%3A%22%22%2C%22unitaccaddr%22%3A%22%E8%81%94%E7%B3%BB%E4%BA%BA%22%2C%22phone%22%3A%22%22%2C%22unitlinkphone%22%3A%2213211112222%22%2C%22ispflag%22%3A%221%22%2C%22basenum%22%3A%22%22%2C%22servicename%22%3A%22%22%2C%22begpym%22%3A%22%22%2C%22certitype%22%3A%2201%22%2C%22accname%22%3A%22%E6%B5%8B%E8%AF%95%E4%BA%BA%22%2C%22endpym%22%3A%221585670400000%22%7D
        //截取请求json
        int len = str.length();
        str = str.substring(5,len);
        form.setJson(URLDecoder.decode(str,"UTF-8"));
        log.info("form2====="+(CommonUtil.getStringParams(form)));

        CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle" + "00057400" + ".Handle00235_" + "00057400").newInstance();
        business.action(form, modelMap);
        log.info(Constants.LOG_HEAD + "appapi00235 end.");
        return "";
    }
    
    @RequestMapping("/appapi00236.{ext}")
        public void appapi00236(AppApi00236Form form,  HttpServletRequest request, HttpServletResponse response) throws Exception{
            //form.setBusinName("宁波市高层次人才认定");
            log.info(Constants.LOG_HEAD+"api/appApi00236 begin.");
    		//连接市级公共数据平台获取数据
    		String requestSecret =  ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
    		log.info(requestSecret);
    		Date date = new Date();
    		String requestTime = String.valueOf(date.getTime());
    		log.info("requestTime1111:" + requestTime);
    		String appKey = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "sjappKey").trim().toLowerCase();
    		String sjpublicDatasUrl = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "sjpublicDatasUrl").trim();
    		String sign = EncryptionByMD5.getMD5((appKey + requestSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
    	
    		long starTime=System.currentTimeMillis();
    		HashMap<String, String> params = new HashMap<String, String>();
    		String url = sjpublicDatasUrl + "api/001008002016011/dataSharing/kRxO1yK1MdT53ec3.htm";
    		params.put("appKey", appKey);
    		params.put("sign", sign);
    		params.put("requestTime", requestTime);
    		/*身份证 aac002 身份证号码
               姓名 aac003 姓名*/
    		params.put("aac002", form.getAac002());
            params.put("aac003", form.getAac003());
            log.info("url==="+url+"?"+"appKey="+appKey+"&"+"sign="+sign+"&"+"requestTime="+requestTime+"&"+"aac002="+form.getAac002()+"&"+"aac003="+form.getAac003());

    		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
    		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
    		System.out.println(result);
    	
    		log.info("宁波市高层次人才认定"+result.replace("\\\"", "\""));
    		long endTime=System.currentTimeMillis();
    		long Time=endTime-starTime;
    		System.out.println("请求大数据平台耗时"+Time+"毫秒");
    		JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
    		json.remove("dataCount");
    		json.remove("data");
    		if(json.get("code").equals("00")){
    			json.put("recode", "000000");
    		}
    		json.remove("code");
    	
    		log.info(Constants.LOG_HEAD+"appApi00236 end.");
    		log.info("form.getChannel()="+form.getChannel());
    	
    		if(form.getChannel().trim().equals(""))
    		{
    		    log.info("gbk");
    		    log.info("json.toString()=========="+json.toString().replace("'", "\""));
    		    response.getOutputStream().write(json.toString().replace("'", "\"").getBytes(request.getCharacterEncoding()));
    		}else{
    		    log.info("utf-8");
    		    response.getOutputStream().write(json.toString().replace("'", "\"").getBytes("utf-8"));
    			}
    			return ;
    		}

    @RequestMapping("/appapi00237.{ext}")
    public void appapi00237(AppApi030Form form,AppApi00225Form form1, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("长三角异地缴存证明明细打印核心调用");
        log.info(Constants.LOG_HEAD + "api/appApi00237 begin.");
        long starTime = System.currentTimeMillis();
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        //调用yb异地贷款缴存使用证明明细打印接口
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "webapi80008.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        System.out.println("异地贷款缴存使用证明明细打印接口：" + rm);
        JSONObject json = JSONObject.fromObject(rm);
        //String filepath = json.get("filepath").toString();
        //測試環境地址，下面2处一起改
//        String filepath = "http://172.16.10.96:7001/YBMAPZH/webapi90001.json?filepath=/wls/saleContractPDF/pdf/";
        //生產環境地址
        String filepath = "http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/pdf/";
        //filepath = filepath.replace("http://61.153.144.77:7006/YBMAPZH/",PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim());
        String filename = json.get("filename").toString();
        String file = filepath+filename;
        //调用yb异地贷款缴存使用证明打印接口
        HashMap map1 = BeanUtil.transBean2Map(form1);
        String url1 = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00227.json";
        System.out.println("YBMAP url1 ：" + url1);
        String rm1 = post.post(url1, map1);
        System.out.println("异地贷款缴存使用证明打印接口：" + rm1);
        JSONObject json1 = JSONObject.fromObject(rm1);
        String filepath1 = json1.get("filepath").toString();
//        filepath1 = filepath1.replace("http://61.153.144.77:7001/YBMAPZH/",PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim());
        String filename1 = json1.get("filename").toString();
        String file1 = filepath1+filename1;
        System.out.println("file：" + file.replace("http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=",""));
        System.out.println("file1：" + file1.replace("http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=",""));
        file = file.replace("http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=","");
        file1 =  file1.replace("http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=","");
        //调用yb长三角异地缴存证明明细打印接口
        String url2 = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00237.json";
        AppApi00237Form form2 = new AppApi00237Form();
        form2.setFile(file);
        form2.setFile1(file1);
        HashMap map2 = BeanUtil.transBean2Map(form2);
        String rm2 = post.post(url2, map2);
        System.out.println("长三角异地缴存证明明细打印：" + rm2);

		/*Gson gson = new Gson();
		Map ybmapmsg = gson.fromJson(rm, new TypeToken<Map<String, String>>() {
		}.getType());*/

		int index = rm2.lastIndexOf("}{");
		System.out.println("index：" + index);
		for(int i = rm2.length();i>-1;i--){
			if(index == i){
				rm2 = rm2.substring(0,i+1);
			}
		}
		System.out.println("rm2 ：" + rm2);
        JSONObject json2 = JSONObject.fromObject(rm2);
		if (rm2.length()!=0){
		    //测试环境地址
//            String name = "http://61.153.144.77:7006/YBMAPZH/webapi90001.json?filepath="+json2.get("filepath");
            //生产环境地址 下面还有1处
            String name = "http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath="+json2.get("filepath");
            json2.put("filepath",name.replace("lianhedaying"+filename1+".txt",""));
            json2.put("filename",name.replace("http://61.153.144.77:7001/YBMAPZH/webapi90001.json?filepath=/ispshare/ftpdir/",""));
//            json2.put("recode","000000");
        }else {
		    json2.remove("filepath");
		    json2.put("errmsg","未查到缴存证明和缴存明细 请确认信息有无填写错误");
            json2.put("recode","999999");
        }
        System.out.println("json2.toString() ：" + json2);
        long endTime = System.currentTimeMillis();
        long Time = endTime - starTime;
        System.out.println("三个接口共耗时" + Time + "毫秒");
        response.getOutputStream().write(json2.toString().getBytes(request.getCharacterEncoding()));
        log.info(Constants.LOG_HEAD + "appApi00237 end.");
        log.info("form.getChannel()=" + form.getChannel());
        return;

    }

}
