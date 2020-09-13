package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80008ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Handle00243_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+"05740000").trim();
		AppApi80008ZHResult app80008ZHResult = new AppApi80008ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+"05740000").trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+"05740000").trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+"05740000").trim());
			form.setTellCode("1111");
			form.setBrcCode("05740008");
			form.setFlag(Channel.getChannel(form.getChannel()));
			//接收请求para加密参数 需要解密
			log.debug("para======="+form.getPara());
			
			HashMap map = BeanUtil.transBean2Map(form);
			log.debug("map.toString()===="+map);
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+"05740000")+"/BSP_REQ_80008_111519.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+"05740000").trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+"05740000").trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111519");
			log.debug("前置YFMAP接收中心报文——返回申请开具证明信息接口："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+"05740000")+"/BSP_REP_80008_111519.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app80008ZHResult);
			log.debug("MAP封装成BEAN："+app80008ZHResult);

			String stdata0 = app80008ZHResult.getStdata();
			String stdata1 = app80008ZHResult.getStdata1();
			String stdata2 = app80008ZHResult.getStdata2();
			String stdata3 = app80008ZHResult.getStdata3();
			String stdata4 = app80008ZHResult.getStdata4();
			String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;

			stdata = stdata.substring(0, stdata.length() - 1);

			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
			String senddate = formatter3.format(date);
			DecimalFormat df = new DecimalFormat("0%");// ##.00% 百分比格式，后面不足2位的用0补齐
			double unitprop = Double.valueOf(app80008ZHResult.getUnitprop());
			double indiprop =  Double.valueOf(app80008ZHResult.getIndiprop());
			log.info("df.format(unitprop)=========="+df.format(unitprop));
			log.info("df.format(indiprop)=========="+df.format(indiprop));
			log.info("senddate=========="+senddate);
			log.info("app80008ZHResult.getRecode()=========="+app80008ZHResult.getRecode());
			log.info("stdata=========="+stdata);
			if ("0".equals(app80008ZHResult.getRecode())){
				String voucherData = "[{" +
						"\"unitaccnum\":\"" +app80008ZHResult.getUnitaccnum() + "\"," +
						"\"unitaccname\":\"" +app80008ZHResult.getUnitaccname() + "\"," +
						"\"accnum\":\"" +app80008ZHResult.getAccnum() + "\"," +
						"\"accname\":\"" +app80008ZHResult.getAccname() + "\"," +
						"\"certinum\":\"" +app80008ZHResult.getCertinum() + "\"," +
						"\"instname\":\"" +app80008ZHResult.getInstname() + "\"," +
						"\"indiaccstate\":\"" +app80008ZHResult.getIndiaccstate() + "\"," +
						"\"unitprop\":\"" +df.format(unitprop) + "\"," +
						"\"indiprop\":\"" +df.format(indiprop) + "\"," +
						"\"opnaccdate\":\"" +app80008ZHResult.getOpnaccdate() + "\"," +
						"\"begdate\":\"" +app80008ZHResult.getBegdate() + "\"," +
						"\"enddate\":\"" +app80008ZHResult.getEnddate() + "\"," +
						"\"senddate\":\"" +senddate + "\"," +
						"\"table1\":["+stdata+"]}]";
				log.info("voucherData=========="+voucherData);
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("voucherData", voucherData);
				return modelMap;
			}else {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", app80008ZHResult.getMsg());
				return modelMap;
			}
		}

		modelMap.clear();
		modelMap.put("recode", "999999");
		modelMap.put("msg", app80008ZHResult.getMsg());
		return modelMap;
	}
}
