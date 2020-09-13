package com.yondervision.yfmap.handle.handleImpl.handle00045101;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00401Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi002Result;
import com.yondervision.yfmap.result.AppApi00401Result;
import com.yondervision.yfmap.result.AppApi00501Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.nongkengHEB.NongKenAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 个人贷款信息查询
 *
 */
public class Handle006_00045101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		NongKenAppApi00601Result app00601Result= new NongKenAppApi00601Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);

//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REQ_DKYECX.txt", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKYECX.txt", map, req);
			log.debug("前置YFMAP发往中心BSP报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP_"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT_"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "120322");
			log.debug("BSP返回前置YFMAP报文："+reqxml);
//			String reqxml = "<AuthCode1>0000</><AuthCode2>0000</><AuthCode3>0000</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>2644451</><ChannelSeq>398243</><ChkCode>0000</><MTimeStamp>11:10:12</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>11:10:12</><TellCode>0000</><TranChannel>00</><TranCode>120322</><TranDate>2015-12-16</><TranIP>10.22.21.26</><TranSeq>2644451</><cleardate></><clearstatedes>未结清</><commloanflagdes>否</><dedtypedes>银行</><enddate>2020-07-06</><guarmodedes>抵押+保险</><houseaddr>肇源农场幸福区5幢4单元3层302号房</><instcodedes>总局直属管理部</><loanamt>80000.00</><loanbal>41073.96</><loanclosedate></><loancontrcode>DK0020102050</><loancontrstatede>放款</><loandate>2010-07-06</><loanrate>4.250000</><loanstatedes>正常</><loanterm>120</><monthrepayamt>823.25</><owecnt>0</><oweint>0.00</><oweprin>0.00</><owepun>0.000000</><remark>王晓明</><repaycycledes>月</><repayday>6</><repaymodedes>等额本息</><repaytolamt>1355.56</><tolowecnt>2</>";
			
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REP_DKYECX.txt", reqxml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_DKYECX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"0".equals(app00601Result.getRecode())){
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
			app00601Result.setLoanamt(String.format("%,.2f",Double.valueOf(app00601Result.getLoanamt())));
			app00601Result.setLoanbal(String.format("%,.2f",Double.valueOf(app00601Result.getLoanbal())));
			app00601Result.setLoanrate(String.format("%,.2f",Double.valueOf(app00601Result.getLoanrate()))+"%");
			app00601Result.setMonthrepayamt(String.format("%,.2f",Double.valueOf(app00601Result.getMonthrepayamt())));
			app00601Result.setRepaytolamt(String.format("%,.2f",Double.valueOf(app00601Result.getRepaytolamt())));
			app00601Result.setOweprin(String.format("%,.2f",Double.valueOf(app00601Result.getOweprin())));
			app00601Result.setOweint(String.format("%,.2f",Double.valueOf(app00601Result.getOweint())));			
		}
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);

		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		Iterator<TitleInfoBean> it2 = appapi00101Detail.iterator();
		while (it2.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it2.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		jsonObj.put("result", appapi00101Result);
		jsonObj.put("detail", appapi00101Detail);
		log.debug(jsonObj);
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("detail", appapi00101Detail);
		return modelMap;
	}
//	public static void main(String[] args){
//		AppApi00601Form form1 = new AppApi00601Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("P08070000258");
//		form1.setCenterId("00045101");
//		Handle006_00045101 hand = new Handle006_00045101();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block			
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
