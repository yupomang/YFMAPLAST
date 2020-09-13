package com.yondervision.yfmap.handle.handleImpl.handle00076000;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01003Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zhongshan.AppApi01006Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 提取信息查询-住房提取
 */
public class Handle0100601_00076000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01003Form form = (AppApi01003Form)form1;
		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		AppApi01006Result app01003Result= new AppApi01006Result();
		
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			AES aes= new AES();
			try{
			log.debug("身份证号："+form.getIdcardNumber());
			form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
//			form.setSurplusAccount(aes.decrypt(form.getSurplusAccount()));
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_GJJZHCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119016");
//			String rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><accnum>801078393844</accnum><accname>陈倩敏</accname><agrtaccnum>12321321</agrtaccnum><amt>2345.98</amt><bal>24567.54</bal><certinum>442000199107280546</certinum><certitype2></certitype2><dpadjamt>100.00</dpadjamt><frzamt>2000.31</frzamt><handset>12345678901</handset><indiaccstate>1</indiaccstate><matecertinum></matecertinum><matename></matename><payamt>2100.00</payamt><payeebankaccnum>1234567812321321</payeebankaccnum><payeebankname>0101</payeebankname><stpayamt>1234.01</stpayamt><unitaccnum>1232132132132</unitaccnum><unitaccname>华信永道</unitaccname><uplimitamt>30000.00</uplimitamt></body></root>";
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_GJJZHCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
//			BeanUtil.transMap2Bean(resultMap, app01003Result);
			if(!Constants.sucess.equals((String)resultMap.get("recode"))){
				modelMap.put("recode", (String)resultMap.get("recode"));
				modelMap.put("msg", (String)resultMap.get("msg"));
				log.error("中心返回报文 状态recode :"+(String)resultMap.get("recode")+"  ,  描述msg : "+(String)resultMap.get("msg"));
				return modelMap;
			}
			if(!"0".equals((String)resultMap.get("indiaccstate"))){
				modelMap.put("recode", "111111");
				modelMap.put("msg", "对不起，您的公积金账号状态不是正常状态，不能进行提取交易！");
				log.error("中心返回报文 状态recode :111111  ,  描述msg : 对不起，您的公积金账号状态不是正常状态，不能进行提取交易；indiaccstate："+(String)resultMap.get("indiaccstate"));
				return modelMap;
			}
			form.setAccnum((String)resultMap.get("accnum"));
			map = BeanUtil.transBean2Map(form);
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_ZHFTQCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119008");
//			String rexml = "<?xml version=\"1.0\" encoding=\"GBK\"?><root><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><accnum>801078393844</accnum><accname>陈倩敏</accname><agrtaccnum>12321321</agrtaccnum><amt>2345.98</amt><bal>24567.54</bal><certinum>442000199107280546</certinum><certitype2></certitype2><dpadjamt>100.00</dpadjamt><frzamt>2000.31</frzamt><handset>12345678901</handset><indiaccstate>1</indiaccstate><matecertinum></matecertinum><matename></matename><payamt>2100.00</payamt><payeebankaccnum>1234567812321321</payeebankaccnum><payeebankname>0101</payeebankname><stpayamt>1234.01</stpayamt><unitaccnum>1232132132132</unitaccnum><unitaccname>华信永道</unitaccname><uplimitamt>30000.00</uplimitamt></body></root>";
			log.debug("中心下传报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_ZHFTQCX.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app01003Result);
			if(!Constants.sucess.equals(app01003Result.getRecode())){
				modelMap.put("recode", app01003Result.getRecode());
				modelMap.put("msg", app01003Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01003Result.getRecode()+"  ,  描述msg : "+app01003Result.getMsg());
				return modelMap;
			}
		}		
		HashMap<String,String>  app01003Detail = new HashMap<String,String>();
		app01003Detail.put("accnum", app01003Result.getAccnum());
		app01003Detail.put("xingming", app01003Result.getAccname());
		app01003Detail.put("certinum", app01003Result.getCertinum());
		app01003Detail.put("inputamt", app01003Result.getMatecertinum()==null||app01003Result.getMatecertinum().equals("null")?"":app01003Result.getMatecertinum());
		app01003Detail.put("prominfo", app01003Result.getMatename()==null?"":app01003Result.getMatename());
		app01003Detail.put("procode2", app01003Result.getCertitype2()==null?"":app01003Result.getCertitype2());
		app01003Detail.put("newphone", app01003Result.getLinkphone()==null?"":app01003Result.getLinkphone());
		app01003Detail.put("unitaccnum", app01003Result.getUnitaccnum());
		app01003Detail.put("unitprop", app01003Result.getUnitaccname());
		app01003Detail.put("payeebankname", app01003Result.getPayeebankname());
		app01003Detail.put("payeebankaccnum", app01003Result.getPayeebankaccnum());
		app01003Detail.put("handset", app01003Result.getHandset());
		app01003Detail.put("bankaccnum", app01003Result.getAgrtaccnum());
		String tqje = "";
		BigDecimal bal = new BigDecimal(app01003Result.getBal());
		BigDecimal stpaymat = new BigDecimal(app01003Result.getStpayamt());
		BigDecimal frzamt = new BigDecimal(app01003Result.getFrzamt());
		BigDecimal dpadjamt = new BigDecimal(app01003Result.getDpadjamt());
		BigDecimal uplimitamt = new BigDecimal(app01003Result.getUplimitamt());
		BigDecimal amt1 = new BigDecimal(app01003Result.getPayamt()).multiply(new BigDecimal(0.3)).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal drawamt1 = bal.subtract(stpaymat).subtract(frzamt).subtract(dpadjamt).multiply(new BigDecimal(100),MathContext.DECIMAL32).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
		BigDecimal drawamt2 = amt1.multiply(new BigDecimal(100),MathContext.DECIMAL32).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
		System.out.println(drawamt1.compareTo(drawamt2));
		if(drawamt1.compareTo(drawamt2)==1){
			if(drawamt2.compareTo(uplimitamt)==1){
				tqje = uplimitamt.toString();
			}else{
				tqje = drawamt2.toString();
			}
		}else{
			if(drawamt1.compareTo(new BigDecimal(0))==-1){
				tqje="0.00";
			}else{
				if(drawamt1.compareTo(uplimitamt)==1){
					tqje = uplimitamt.toString();
				}else{
					tqje = drawamt1.toString();
				}
			}
		}
		app01003Detail.put("drawamt", tqje);
		app01003Result.setPayeename(app01003Result.getAccname());
		app01003Result.setBal(String.format("%,.2f",Double.valueOf(app01003Result.getBal())));
		app01003Result.setPayamt(String.format("%,.2f",Double.valueOf(app01003Result.getPayamt())));
		app01003Result.setTqje(String.format("%,.2f",Double.valueOf(tqje)));
		app01003Result.setPayamt1(String.format("%,.2f",amt1.doubleValue()));
		app01003Result.setIndiaccstate(PropertiesReader.getProperty("yingshe.properties","Zhzt"+app01003Result.getIndiaccstate()+form.getCenterId()));
		app01003Result.setPayeebanknameDes(PropertiesReader.getProperty("yingshe.properties","BankName"+app01003Result.getPayeebankname()+form.getCenterId()));
		app01003Result.setTqyy("住房消费提取");
		List<TitleInfoBean> appapi01003Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01006"+form.getCenterId()+".result"+form.getDrawSignType(), app01003Result);
		app01003Detail.put("drawamt",tqje);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", app01003Detail);
		modelMap.put("result", appapi01003Result);
		log.info(Constants.LOG_HEAD+"appApi0100601 end.");
		return modelMap;
	}

	public static void main(String[] args){
		AppApi01003Form form1 = new AppApi01003Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setDrawSignType("01");
		form1.setCenterId("00076000");
//		BigDecimal bal = new BigDecimal("12345.678");
//		BigDecimal stpaymat = new BigDecimal("0.00");
//		BigDecimal frzamt = new BigDecimal("12.34");
//		BigDecimal dpadjamt = new BigDecimal("2314.89");
//		BigDecimal amt1 = new BigDecimal("2100.00").multiply(new BigDecimal(0.3),MathContext.DECIMAL32);
//		BigDecimal drawamt1 = bal.subtract(stpaymat).subtract(frzamt).subtract(dpadjamt).multiply(new BigDecimal(100),MathContext.DECIMAL32).setScale(0, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100));
//		System.out.println(String.format("%,.2f",amt1.doubleValue()));
		Handle0100601_00076000 hand = new Handle0100601_00076000();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
