package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.ningbo.AppApi00101ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

public class Handle80002_00057400
  implements CtrlHandleInter
{
  Logger log = Logger.getLogger("YFMAP");

  public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException, Exception {
    String PROPERTIES_FILE_NAME = "properties.properties";
    AppApi030Form form = (AppApi030Form)form1;
    this.log.debug("00057400请求00101参数：" + CommonUtil.getStringParams(form));

    String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send" + form.getCenterId()).trim();
    AppApi00101ZHResult app00101ZHResult = new AppApi00101ZHResult();

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
      String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REQ_GRZHJBXXCXFX.txt", map, req);

      this.log.debug("前置YFMAP发往中心报文：" + xml);

      String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
      String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId()).trim();
      String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "142503");

      this.log.debug("前置YFMAP接收中心报文——个人账户查询（用于反显）：" + rexml);
      HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(new StringBuilder("WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/").append(form.getCenterId()).toString()) + "/BSP_REP_GRZHJBXXCXFX.txt", rexml, req);

      this.log.debug("解析报文MAP：" + resultMap);
      BeanUtil.transMap2Bean(resultMap, app00101ZHResult);
      if (!"0".equals(app00101ZHResult.getRecode())) {
        modelMap.clear();
		JSONArray result = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("errCode", app00101ZHResult.getRecode());
		obj.put("msg",  "错误信息："+app00101ZHResult.getMsg());
		result.add(obj);
		modelMap.put("recode", app00101ZHResult.getRecode());
		modelMap.put("msg", result);
        this.log.error("中心返回报文 状态recode :" + app00101ZHResult.getRecode() + "  ,  描述msg : " + app00101ZHResult.getMsg());
        return modelMap;
      }
    }

    List appapi00101ZHResult = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00101" + form.getCenterId() + ".result", app00101ZHResult);
    Iterator it1 = appapi00101ZHResult.iterator();
    while (it1.hasNext()) {
      TitleInfoNameFormatBean titleInfoBean = (TitleInfoNameFormatBean)it1.next();
      this.log.info("title=" + titleInfoBean.getTitle() + "\tinfo=" + titleInfoBean.getInfo());
    }
      modelMap.clear();
      modelMap.put("recode", "000000");
      modelMap.put("msg", "成功");
      modelMap.put("accinstcode", app00101ZHResult.getAccinstcode());
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
      modelMap.put("accnum", app00101ZHResult.getAccnum());
//      modelMap.put("indiaccstate", app00101ZHResult.getIndiaccstate());
//      modelMap.put("actmp100", app00101ZHResult.getActmp100());
//      modelMap.put("actmp1024", app00101ZHResult.getActmp1024());
//      modelMap.put("actmp200", app00101ZHResult.getActmp200());
//      modelMap.put("amt3", app00101ZHResult.getAmt3());
//      modelMap.put("amt7", app00101ZHResult.getAmt7());
//      modelMap.put("amt8", app00101ZHResult.getAmt8());
//      modelMap.put("bankname", app00101ZHResult.getBankname());
//      modelMap.put("bankcode", app00101ZHResult.getBankcode());
//      modelMap.put("cardstate", app00101ZHResult.getCardstate());
//      modelMap.put("certitype", app00101ZHResult.getCertitype());
//      modelMap.put("frzamt", app00101ZHResult.getFrzamt());
//      modelMap.put("frzflag", app00101ZHResult.getFrzflag());
//      modelMap.put("monintamt", app00101ZHResult.getMonintamt());
//      modelMap.put("subintamt", app00101ZHResult.getSubintamt());
//      modelMap.put("supintamt", app00101ZHResult.getSupintamt());
//      modelMap.put("unitaccnum", app00101ZHResult.getUnitaccnum());
//      modelMap.put("cardno", app00101ZHResult.getCardno());
      return modelMap;
  }
}