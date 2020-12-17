package com.yondervision.yfmap.controller;

import com.alibaba.xxpt.gateway.shared.client.http.ExecutableClient;
import com.alibaba.xxpt.gateway.shared.client.http.GetClient;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.ShiJiPublicDatasTokenUtil;
import net.sf.json.JSONObject;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

/**
 * @author 张杰
 * @version V1.0
 * @date 2020/12/04 09:26
 */
@Controller
public class AppApi048Contorller {

    Logger log = Logger.getLogger("YFMAP");

    @RequestMapping("/appapi00239.{ext}")
    public void appapi00239 (AppApi50001Form form, ModelMap modelMap, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        form.setBusinName("最多跑一次公务员审批信息接口");
        log.info("BBBBBBBBBBBBB++BB----AAAAAAAAAAAAAAAAAAA");
        log.info(Constants.LOG_HEAD + "api/appapi00239 begin.");

        long starTime=System.currentTimeMillis();
        //String requestSecret = ShiJiPublicDatasTokenUtil.getPublicDataSecretWithCouchBase(form.getCenterId());
        //log.info(requestSecret);
        Date date = new Date();
        String requestTime = String.valueOf(date.getTime());
        log.info("requestTime1111:" + requestTime);
        //如果需要进入YB
        HashMap map = BeanUtil.transBean2Map(form);
        YbmapMessageUtil post = new YbmapMessageUtil();
        String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi00239.json";
        System.out.println("YBMAP url ：" + url);
        String rm = post.post(url, map);
        int begin=rm.indexOf("\"workSheetLogVo\":");
        int last=rm.indexOf(",\"csfnsrsbh\"");
        String res = rm.substring(begin,last);
        System.out.println(res);
        int begin1=res.indexOf("{");
        int last1=res.indexOf("}")+1;
        String result = res.substring(begin1,last1);
        System.out.println("钉钉公务员审批推送返回结果result：" + result);


        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++分割线20201211++++++++++++++++++++++++++");
       /* //读取配置文件，连接钉钉接口
        String api = "/bpms/openapi/procInst/executeInstTodo.json";
        ApplicationContext app = new ClassPathXmlApplicationContext("applicationContext.xml");
        //PostClient postClient = ExecutableClient.getInstance().newPostClient(api);
        ExecutableClient executableClient = (ExecutableClient) app.getBean("executableClient");

        //log.info("executableClient="+executableClient.getUri());
        log.info("appapi00239推送参数："+form.getProcInsId()+"+++++"+form.getLoginWorkNo()+"+++++"+form.getAction()+"+++++"+form.getRemark());
        GetClient getClient =
                executableClient.getInstance().newGetClient(api);
        //加参数
        String procInsId = form.getProcInsId();
        log.info("form.getProcInsId()="+form.getProcInsId());
        getClient.addParameter("procInsId", procInsId);
        String loginWorkNo = form.getLoginWorkNo();
        log.info("form.getLoginWorkNo()="+form.getLoginWorkNo());
        getClient.addParameter("loginWorkNo", loginWorkNo);
        String action = form.getAction();
        log.info("form.getAction()="+form.getAction());
        getClient.addParameter("action", action);
        String remark = form.getRemark();
        log.info("form.getRemark()="+form.getRemark());
        getClient.addParameter("remark", remark);
        String apiResult = getClient.get();


        System.out.println(apiResult);

        log.info("最多跑一次公务员审批信息接口"+apiResult.replace("\\\"", "\""));*/
        long endTime=System.currentTimeMillis();
        long Time=endTime-starTime;
        System.out.println("请求钉钉接口耗时"+Time+"毫秒");
        JSONObject json = JSONObject.fromObject(result.replace("\\\"", "'"));
        System.out.println("json.toString()=========="+json.toString().replace("'", "\""));
        //json.remove("dataCount");
        //json.remove("data");
        /*if(json.get("success").equals("true")){
            json.put("recode", "000000");
        }*/
        //json.remove("code");

        log.info(Constants.LOG_HEAD+"appapi00239 end.");
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
        log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++分割线20201211++++++++++++++++++++++++++");
        return ;






    }
}
