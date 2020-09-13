package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00228Form;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00101ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi80007ZHResult;
import com.yondervision.yfmap.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Handle00228_00057400
  implements CtrlHandleInter
{
  Logger log = Logger.getLogger("YFMAP");

  public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException, Exception {
    String PROPERTIES_FILE_NAME = "properties.properties";
    AppApi00228Form form = (AppApi00228Form)form1;
    log.debug("00057400请求00228参数：" + CommonUtil.getStringParams(form));

    String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send" + form.getCenterId()).trim();
    AppApi80007ZHResult app80007ZHResult = new AppApi80007ZHResult();

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

      form.setOrgsocode(form.getOrgsocode());
      form.setUnitaccname(form.getUnitaccname());
      form.setUnitlinkman(form.getUnitlinkman());
      form.setCertitype(form.getCertitype());
      form.setCertinum(form.getCertinum());
      form.setClosereason(form.getClosereason());
      form.setTransdate(form.getTransdate());
      System.out.println("form.getCenterId()============"+form.getCenterId());
      System.out.println("form.getOrgsocode()============"+form.getOrgsocode());
      System.out.println("form.getUnitaccname()============"+form.getUnitaccname());
      System.out.println("form.getUnitlinkman()============"+form.getUnitlinkman());
      System.out.println("form.getCertitype()============"+form.getCertitype());
      System.out.println("form.getCertinum()============"+form.getCertinum());
      System.out.println("form.getClosereason()============"+form.getClosereason());
      System.out.println("form.getTransdate()============"+form.getTransdate());
      HashMap map = BeanUtil.transBean2Map(form);
      map.put("tellcode", "dasj");
      String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REQ_00228_111504.txt", map, req);

      log.debug("前置YFMAP发往中心报文：" + xml);

      String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
      String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId()).trim();
      String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111504");

      log.debug("前置YFMAP接收中心报文——宁波公积金注销申请接收：" + rexml);
      HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REP_00228_111504.txt", rexml, req);

      this.log.debug("解析报文MAP：" + resultMap);
      BeanUtil.transMap2Bean(resultMap, app80007ZHResult);
      log.info("app80007ZHResult.getRecode()============" + app80007ZHResult.getRecode());
      //spt_success 0失败 1成功
      if (app80007ZHResult.getSpt_success().equals("0")) {
        modelMap.clear();
		//JSONArray result = new JSONArray();
		//JSONObject obj = new JSONObject();
        modelMap.put("recode", "999999");
        modelMap.put("msg",  "错误信息："+app80007ZHResult.getSpt_showmsg());
		//result.add(obj);
		//modelMap.put("recode", app80007ZHResult.getRecode());
		//modelMap.put("msg", result);
        log.error("中心返回报文 状态recode :" + app80007ZHResult.getRecode() + "  ,  描述msg : " + app80007ZHResult.getSpt_showmsg());
        return modelMap;
      }
    }

    /*List appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101" + form.getCenterId() + ".result", app80007ZHResult);
    Iterator it1 = appapi00101ZHResult.iterator();
    while (it1.hasNext()) {
      TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean)it1.next();
      log.info("title=" + titleInfoBean.getTitle() + "\tinfo=" + titleInfoBean.getInfo());
    }*/
      modelMap.clear();
      modelMap.put("recode", "000000");
      modelMap.put("msg", "成功");
/*      modelMap.put("accinstcode", app00101ZHResult.getAccinstcode());
      modelMap.put("accinstcodedes", app00101ZHResult.getAccinstcodedes());
      modelMap.put("accname", app00101ZHResult.getAccname());
      modelMap.put("certinum", app00101ZHResult.getCertinum());
      modelMap.put("unitaccname", app00101ZHResult.getUnitaccname());
      modelMap.put("freeuse1", app00101ZHResult.getFreeuse1());
      modelMap.put("basenum", app00101ZHResult.getBasenum());
      modelMap.put("amt", app00101ZHResult.getAmt());
      modelMap.put("monpaysum", app00101ZHResult.getMonpaysum());
      modelMap.put("begdate", app00101ZHResult.getBegdate());
      modelMap.put("bal", app00101ZHResult.getBal());
      modelMap.put("handset", app00101ZHResult.getHandset());
      modelMap.put("lpaym", app00101ZHResult.getLpaym());
      modelMap.put("accnum", app00101ZHResult.getAccnum());*/
      return modelMap;
  }
}