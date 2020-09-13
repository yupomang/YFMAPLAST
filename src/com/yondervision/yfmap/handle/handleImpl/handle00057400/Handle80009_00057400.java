package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.alibaba.fastjson.JSON;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi80009ZHResult;
import com.yondervision.yfmap.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class Handle80009_00057400
  implements CtrlHandleInter
{
  Logger log = Logger.getLogger("YFMAP");

  public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException, Exception {
    String PROPERTIES_FILE_NAME = "properties.properties";
    AppApi030Form form = (AppApi030Form)form1;
    this.log.debug("webapi80009 请求参数：" + CommonUtil.getStringParams(form));
    this.log.debug("webapi80009 转码前单位名称：" + form.getSpt_dwmc());
    log.debug("转码后 form 里dwmc："+ URLDecoder.decode(form.getSpt_dwmc(),"UTF-8"));
    form.setSpt_dwmc(URLDecoder.decode(form.getSpt_dwmc(),"UTF-8"));

    String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send" + form.getCenterId()).trim();
    AppApi80009ZHResult app80009ZHResult = new AppApi80009ZHResult();

    if ("2".equals(send)) {
      String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType" + form.getCenterId()).trim();
      form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key" + form.getCenterId()).trim());
      SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
      SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
      Date date = new Date();
      form.setSendDate(formatter1.format(date));
      form.setSendTime(formatter2.format(date));
      String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
      form.setSendSeqno(req);

      form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type" + form.getCenterId()).trim());

      if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91"))&&(!form.getChannel().trim().equals("93")))
      {
        form.setChannelSeq(form.getSendSeqno());
        form.setTellCode("9999");
        form.setBrcCode("05740008");
        form.setClientMAC("");
        form.setTranDate(form.getSendDate());
      }
      form.setFlag(Channel.getZzChannel(form.getChannel()));

      HashMap map = BeanUtil.transBean2Map(form);
      map.put("tellcode", "dasj");
      String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REQ_DWXXCX_111528.txt", map, req);

      this.log.debug("前置YFMAP发往中心报文：" + xml);

      String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
      String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId()).trim();
      String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111528");

      this.log.debug("前置YFMAP接收中心报文----单位信息查询：" + rexml);
      HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REP_DWXXCX_111528.txt", rexml, req);

      this.log.debug("解析报文MAP：" + resultMap);
      BeanUtil.transMap2Bean(resultMap, app80009ZHResult);
      if (!"0".equals(app80009ZHResult.getRecode())) {
        modelMap.clear();
		JSONArray result = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("errCode", app80009ZHResult.getRecode());
		obj.put("msg",  "错误信息："+app80009ZHResult.getMsg());
		result.add(obj);
		modelMap.put("recode", app80009ZHResult.getRecode());
		modelMap.put("msg", result);
        this.log.error("中心返回报文 状态recode :" + app80009ZHResult.getRecode() + "  ,  描述msg : " + app80009ZHResult.getMsg());
        return modelMap;
      }
    }

    List appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101" + form.getCenterId() + ".result", app80009ZHResult);
    Iterator it1 = appapi00101ZHResult.iterator();
    while (it1.hasNext()) {
      TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean)it1.next();
      this.log.info("title=" + titleInfoBean.getTitle() + "\tinfo=" + titleInfoBean.getInfo());
    }

      modelMap.clear();
      modelMap.put("recode", "000000");
      modelMap.put("msg", "成功");
      modelMap.put("accinstcode", app80009ZHResult.getAccinstcode());//账号机构
      modelMap.put("dwzh", app80009ZHResult.getDwzh());//单位账号
      modelMap.put("unitaccname", app80009ZHResult.getUnitaccname());//单位名称
      /*modelMap.put("cmpayamt", app80009ZHResult.getCmpayamt());//本月存缴金额
      modelMap.put ("cmpaynum", app80009ZHResult.getCmpaynum());//本月存缴人数
      modelMap.put("lpaym", app80009ZHResult.getLpaym());//缴至年月*/
      modelMap.put("spt_tyxyydm", app80009ZHResult.getSpt_tyxyydm());//单位统一信用代码
      //修改代码
      String paydata = app80009ZHResult.getStdata();

      String[] list = paydata.split("&&");
      List<com.alibaba.fastjson.JSONObject> sl = new ArrayList<com.alibaba.fastjson.JSONObject>();
      for (String ss: list) {
        StringBuffer sb = new StringBuffer();
        String[] t = ss.split(",");
        sb.append("{");
        for(int i = 0; i < t.length; i++) {
          if(i == 0) {
            sb.append("\"lpaym\":\"" + t[i] + "\",");
          }
          if(i == 1) {
            sb.append("\"cmpaynum\":\"" + t[i] + "\",");
          }
          if(i == 2) {
            sb.append("\"cmpayamt\":\"" + t[i] + "\"}");
          }
        }
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(sb.toString());
        sl.add(jsonObject);
      }
      modelMap.put("paydata",sl);

      return modelMap;
  }

}