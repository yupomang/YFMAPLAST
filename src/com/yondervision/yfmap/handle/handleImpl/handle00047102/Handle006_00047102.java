package com.yondervision.yfmap.handle.handleImpl.handle00047102;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi00601Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00601Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle006_00047102
* @Description: 128029-个人贷款信息查询
* 
*/ 
public class Handle006_00047102 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00601 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00601Form form = (AppApi00601Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		QvZhiAppApi00601Result app00601Result= new QvZhiAppApi00601Result();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_DKYECX.txt", map, req);
			log.debug("YFMAP发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129126"); 
			
//			String reqxml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>OK</><STimeStamp>2013-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>128029</><TranDate>2014-12-11</><TranIP>10.22.21.26</><TranSeq>90</><fileName></><certinum>1</><accname>王小二1</><loancontrstate>1</><loanamt>250000.00</><loanterm>120</><repaymode>1</><agentbankcode>1</><loandate>2015-03-21</><repayday>10</><enddate>2025-03-21</><repayprinsum>1000.00</><repayintsum>1</><loanbal>240000.00</><loancontrnum>29182098129121212</><apprloanamt>1</><loaneeaccname>1</><loanrate>3.5</><handset>1</><repayplansum>1</><repayplanprin>1</><repayplanint>1</><oweprin>0.00</><oweint>0.00</><owepun>1</><repaypirn>1</><repayint>1</><repaysum>1</><owesum>1</><begdate>1</><apprenddate>1</><monthrepayamt>1</><loanhousenum>1</><loancontrcode>1</><remainterm>1</><owecnt>0</><termnum>10</><cleardate>1</><loanrelation>1</><seqnum>10</><famt0>2000.00</><famt1>1590.00</><famt2>1890.00</><famt3>2152.00</><famt4>2519.00</><famt5>2988.00</><famt6>1198.00</><famt7>9882.00</><famt8>2812.00</><summary>公积金等额本金</><summary1>建设银行</>";
			log.debug("中心返回YFMAP报文："+reqxml);
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKYECX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00601Result);
			
			log.debug("MAP封装成BEAN："+app00601Result);
			if(!"0".equals(app00601Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00601Result.getRecode());
				modelMap.put("msg", app00601Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00601Result.getRecode()+"  ,  描述msg : "+app00601Result.getMsg());
				return modelMap;
			}
		}
		
		//String.format("%,.2f",Double.valueOf(appApi00401Result.getAmt1()))
		
		app00601Result.setLoanamt(String.format("%,.2f",Double.valueOf(app00601Result.getLoanamt())));
		app00601Result.setPlandedprin(String.format("%,.2f",Double.valueOf(app00601Result.getPlandedprin())));
		app00601Result.setCurbal(String.format("%,.2f",Double.valueOf(app00601Result.getCurbal())));
		app00601Result.setMonthrepayamt(String.format("%,.2f",Double.valueOf(app00601Result.getMonthrepayamt())));
		app00601Result.setOveroweint(String.format("%,.2f",Double.valueOf(app00601Result.getOveroweint())));
		app00601Result.setOweint(String.format("%,.2f",Double.valueOf(app00601Result.getOweint())));
		app00601Result.setOweprin(String.format("%,.2f",Double.valueOf(app00601Result.getOweprin())));		
		app00601Result.setOwepun(String.format("%,.2f",Double.valueOf(app00601Result.getOwepun())));
		app00601Result.setFamt(String.format("%,.2f",Double.valueOf(app00601Result.getFamt())));
		app00601Result.setLoanrate(String.format("%,.2f",Double.valueOf(app00601Result.getLoanrate()))+"%");		
		
		List<TitleInfoBean> appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".result", app00601Result);
		//List<TitleInfoBean> appapi00101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00601"+form.getCenterId()+".detail", app00601Result);
		
		/* 模拟返回结束  */	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
//		modelMap.put("detail", appapi00101Detail);
		log.info(Constants.LOG_HEAD+"appApi00601 end.");
		return modelMap;
	}

	public static void main(String[] args) {
		QvZhiAppApi00601Result app00601Result= new QvZhiAppApi00601Result();
		app00601Result.setPlandedprin(String.format("%,.2f",Double.valueOf("174770.01")));
		System.out.println(app00601Result.getPlandedprin());
	}
}
