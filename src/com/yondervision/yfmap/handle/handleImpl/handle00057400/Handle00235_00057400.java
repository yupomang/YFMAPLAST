package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00235Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80007ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Handle00235_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00235Form form = (AppApi00235Form)form1;
		log.debug("00057400请求00235参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+"00057400").trim();

		AppApi80007ZHResult app80007ZHResult = new AppApi80007ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+"00057400").trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+"00057400").trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyyMM");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+"00057400").trim());
			//form.setFlag(Channel.getZzChannel(form.getChannel()));
			log.info("form.getJson()=======" + form.getJson());
			JSONObject obj = JSONObject.fromObject(form.getJson());
			HashMap map = BeanUtil.transBean2Map(form);
			map.put("tellcode", "gwgy");
			map.put("qdapprnum", obj.get("qdapprnum").toString());
			map.put("servicecode", obj.get("servicecode").toString());
			map.put("servicename", obj.get("servicename").toString());
			map.put("unitaccnum", obj.get("unitaccnum").toString());
			map.put("unitaccname", obj.get("unitaccname").toString());
			map.put("unitaccaddr", obj.get("unitaccaddr").toString());
			map.put("unioncode", obj.get("unioncode").toString());
			map.put("flag", obj.get("flag").toString());
			map.put("ispflag", obj.get("ispflag").toString());
			map.put("accname", obj.get("accname").toString());
			map.put("certitype", obj.get("certitype").toString());
			map.put("certinum", obj.get("certinum").toString());
			map.put("phone", obj.get("phone").toString());
			map.put("basenum", obj.get("basenum").toString());
			if (obj.get("begpym").toString().length()==0){
				map.put("begpym", "");
			}else {
				long begtime = Long.valueOf(String.valueOf(obj.get("begpym")));
				log.info("begtime==="+begtime);
				String begpym = formatter3.format(begtime);
				log.info("Long.valueOf(String.valueOf(obj.get(\"begpym\")))==="+begpym);
				map.put("begpym", begpym);
			}
			if (obj.get("endpym").toString().length()==0){
				map.put("endpym", "");
			}else {
				long endtime = Long.valueOf(String.valueOf(obj.get("endpym")));
				String endpym = formatter3.format(endtime);
				map.put("endpym", endpym);
			}
			map.put("unitlinkman", obj.get("unitlinkman").toString());
			map.put("unitlinkphone", obj.get("unitlinkphone").toString());
			map.put("instanceid", obj.get("instanceid").toString());
			map.put("xzareacode", obj.get("xzareacode").toString());

			log.info("obj.get(\"accname\").toString()=======" + obj.get("accname"));
			log.info("gb18030=======" + obj.get("accname"));
			log.info("GBK=======" + obj.get("accname"));
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+"00057400")+"/BSP_REQ_00235_140098.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+"00057400").trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+"00057400").trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "140098");
			log.debug("前置YFMAP接收中心报文——公务员一件事办理接口："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+"00057400")+"/BSP_REP_00235_140098.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);

			BeanUtil.transMap2Bean(resultMap, app80007ZHResult);
			log.debug("MAP封装成BEAN："+app80007ZHResult);
			if(!"0".equals(app80007ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "错误信息："+app80007ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app80007ZHResult.getRecode()+"  ,  描述msg : "+app80007ZHResult.getMsg());
				return modelMap;
			}
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		return modelMap;
	}

}
