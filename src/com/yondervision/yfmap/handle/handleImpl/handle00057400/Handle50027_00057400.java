package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 线下业务统计
 * 
 * @author fxliu
 *
 */
public class Handle50027_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";

		AppApi00201Form form = (AppApi00201Form) form1;

		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send" + form.getCenterId()).trim();
		if (Constants.method_BSP.equals(send)) {
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType" + form.getCenterId())
					.trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key" + form.getCenterId()).trim());
			String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(
					PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type" + form.getCenterId()).trim());

			form.setChannelSeq(form.getSendSeqno());
			form.setTellCode("8888");
			form.setBrcCode("88888888");
			form.setTranDate(form.getSendDate());
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType,
					CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REQ_50027_143012.txt", map, req);
			log.debug("柜面业务统计 - 前置YFMAP发往中心报文：" + xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP" + form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT" + form.getCenterId())
					.trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "143012");

			log.debug("柜面业务统计 - 前置YFMAP接收中心报文：" + rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,
					CommonUtil.getFullURL(Constants.msgPath + form.getCenterId()) + "/BSP_REP_50027_143012.txt", rexml, req);
			log.debug("柜面业务统计 - 解析报文MAP：" + resultMap);

			if (!"0".equals(resultMap.get("recode"))) {
				modelMap.clear();
				modelMap.put("recode", resultMap.get("recode"));
				modelMap.put("msg", resultMap.get("msg"));
				log.error("柜面业务统计 - 中心返回报文 状态recode :" + resultMap.get("recode") + "  ,  描述msg : "
						+ resultMap.get("msg"));
				return modelMap;
			}
/*			
			if(!"000000".equals(resultMap.get("bodyRecode"))) {
				modelMap.clear();
				modelMap.put("recode", resultMap.get("bodyRecode"));
				modelMap.put("msg", resultMap.get("bodyMsg"));
				return modelMap;
			}*/

			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			// 缴存业务量
			modelMap.put("jcywl", resultMap.get("jcywl"));
			// 提取业务量
			modelMap.put("tqywl", resultMap.get("tqywl"));
			// 贷款业务量
			modelMap.put("dkywl", resultMap.get("dkywl"));
		}

		return modelMap;

	}

}
