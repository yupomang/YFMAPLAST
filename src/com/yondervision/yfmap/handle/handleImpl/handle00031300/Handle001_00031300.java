package com.yondervision.yfmap.handle.handleImpl.handle00031300;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi00101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;
/**
 * @author fengjing
 * 公积金个人账户查询
 */
public class Handle001_00031300 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		ZhangJiaKouAppApi00101Result app00101Result = new ZhangJiaKouAppApi00101Result();
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_JBXXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_YECX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00002");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head>" +
//					"<reqflag>1</reqflag>" +
//					"<msgtype>72</msgtype>" +
//					"<tr_code>X00002</tr_code>" +
//					"<corp_no>00053100</corp_no>" +
//					"<oreq_no>8020150929007169</oreq_no>" +
//					"<tr_acdt>2015-09-29</tr_acdt>" +
//					"<tr_time>10:25:15</tr_time>" +
//					"<ans_code>000000</ans_code>" +
//					"<ans_info>交易成功完成</ans_info>" +
//					"<particular_code>000000</particular_code>" +
//					"<particular_info>交易成功完成</particular_info>" +
//					"<reserved></reserved></head>" +
//					"<body>" +
//					"<dwmc>华信永道</dwmc>" +
//					"<dwzh>00010101</dwzh>" +
//					"<grxm>测试01</grxm>" +
//					"<grzh>00101010101</grzh>" +
//					"<zhzt>正常</zhzt>" +
//					"<kyye>1500.2000</kyye>" +
//					"<jcjs>2000.000</jcjs>" +
//					"<yjce>200.000</yjce>" +
//					"<lxjcy>2016年12月</lxjcy>" +
//					"<grbl>0.08</grbl>" +
//					"<dwbl>0.08</dwbl>" +
//					"<nhje>5000.000</nhje>" +
//					"<ntqe>1000.000</ntqe>" +
//					"<ncye>4000.000</ncye>" +
//					"<sbjyrq>2016年12月20号</sbjyrq>" +
//					"<khrq>2016年1月</khrq>" +
//					"</body></root>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_JBXXCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,"C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_YECX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00101Result);
			if(!Constants.sucess_ts.equals(app00101Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00101Result.getRecode());
				modelMap.put("msg", app00101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00101Result.getRecode()+"  ,  描述msg : "+app00101Result.getMsg());
				return modelMap;
			}			
		}

		app00101Result.setAccountBalance(String.format("%.2f",Double.valueOf(app00101Result.getAccountBalance())));
		//app00101Result.setFrozenAmount(String.format("%.2f",Double.valueOf(app00101Result.getFrozenAmount())));
		app00101Result.setIdcardNumber(Tools.getDesensitizationCertinum(app00101Result.getIdcardNumber()));
		app00101Result.setSurplusAccount(Tools.getDesensitizationAccnum(app00101Result.getSurplusAccount()));
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app00101Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		return modelMap;
	}
	
	public static void main(String[] args){
		String t = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00002</tr_code><corp_no>00031400</corp_no><req_no>20170114104028593</req_no><serial_no></serial_no><ans_no>8609388</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:40:28</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dwmc>承德医学院</dwmc><dwzh>201000000730</dwzh><grxm>于全鑫</grxm><grzh>314000339877</grzh><zhzt>正常</zhzt><kyye>11618.48</kyye><jcjs>6252.00</jcjs><yjce>1375.44</yjce><lxjcy>201701</lxjcy><grbl>0.100</grbl><dwbl>0.120</dwbl><nhje>9339.44</nhje><ntqe>31572.08</ntqe><ncye>33851.12</ncye><sbjyrq>2017-01-13</sbjyrq><khrq>2011-03-24</khrq></body></root>";
		HashMap resultMap = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_YECX.xml", t, "");
		String t1 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00003</tr_code><corp_no>00031400</corp_no><req_no>20170114104159270</req_no><serial_no></serial_no><ans_no>8609396</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:41:59</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>14</zjls><zys>2</zys><detail><jyrq>2017-01-13</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>11618.48</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-12-15</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>10243.04</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-11-22</jyrq><ywlx>部分提取</ywlx><fsje>31572.08</fsje><zhye>8867.60</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-11-18</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>40439.68</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-10-14</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>39064.24</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-09-13</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>37760.96</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-09-05</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>36457.68</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-07-14</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>35154.40</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-06-30</jyrq><ywlx>年度结息</ywlx><fsje>395.97</fsje><zhye>33851.12</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-06-22</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>33455.15</zhye><jylx>正常</jylx></detail></body></root>";
		HashMap resultMap1 = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_YEMXCX.xml", t1, "");
		String t2 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00009</tr_code><corp_no>00031400</corp_no><req_no>qazwww20170114104204102</req_no><serial_no></serial_no><ans_no>8609397</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:42:04</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dqys>0</dqys><dqysjls>0</dqysjls><zjls>1</zjls><zys>1</zys><detail><tqrq>dbgAtch</tqrq></detail></body></root>";
		HashMap resultMap2 = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_TQMXCX.xml", t2, "");
		String t3 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00005</tr_code><corp_no>00031400</corp_no><req_no>20170114104207029</req_no><serial_no></serial_no><ans_no>8609398</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:42:07</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dkzh>CQ201601302</dkzh><hkfs>等额本息</hkfs><dkll>3.250000</dkll><htztbz>合同签订</htztbz><dkztbz>正常</dkztbz><jqbz>未结清</jqbz><fkrq>1899-12-31</fkrq><jqrq></jqrq><dqrq>2037-01-12</dqrq><hkr>13</hkr><dkje>600000.00</dkje><dqye>0.00</dqye><yqqs>0</yqqs><yqbj>0.00</yqbj><yqlx>0.00</yqlx><yqfx>0.00</yqfx><dkqx>240</dkqx><syqx>0</syqx><fwdz>万华小区C、D区D10幢2单元9层2-904号</fwdz><jkrxm>赵娟</jkrxm><jkrzjh>150428198110150067</jkrzjh><gthkrxm>于全鑫</gthkrxm><gthkrzjh>220503198210181537</gthkrzjh></body></root>";
		HashMap resultMap3 = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_DKYECX.xml", t3, "");
		String t4 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00006</tr_code><corp_no>00031400</corp_no><req_no>20170114104209715</req_no><serial_no></serial_no><ans_no>8609399</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:42:09</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dqys>1</dqys><dqysjls>0</dqysjls><zjls>0</zjls><zys>0</zys><hkze>0.00</hkze><yhbjhj>0.00</yhbjhj><yhlxhj>0.00</yhlxhj><yhfxhj>0.00</yhfxhj><syqs>0</syqs><detail><ywrq>dbgAtch栶</ywrq></detail></body></root>";
		HashMap resultMap4 = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_DKMXCX.xml", t4, "");
		String t5 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00007</tr_code><corp_no>00031400</corp_no><req_no>qazwww20170114104235384</req_no><serial_no></serial_no><ans_no>8609402</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:42:35</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><sqrq>2016-12-20</sqrq><dqdkjdbs>02</dqdkjdbs><dqdkjdmx>合同签订</dqdkjdmx><detail><dkjdbs>00</dkjdbs><dkjdmx>申请</dkjdmx></detail><detail><dkjdbs>01</dkjdbs><dkjdmx>审批</dkjdmx></detail><detail><dkjdbs>02</dkjdbs><dkjdmx>合同签订</dkjdmx></detail><detail><dkjdbs>06</dkjdbs><dkjdmx>贷款开户</dkjdmx></detail><detail><dkjdbs>13</dkjdbs><dkjdmx>放款通知在途</dkjdmx></detail><detail><dkjdbs>13</dkjdbs><dkjdmx>放款通知在途</dkjdmx></detail><detail><dkjdbs>08</dkjdbs><dkjdmx>通知放款</dkjdmx></detail><detail><dkjdbs>09</dkjdbs><dkjdmx>放款</dkjdmx></detail><detail><dkjdbs>90</dkjdbs><dkjdmx>合同终止</dkjdmx></detail><detail><dkjdbs>99</dkjdbs><dkjdmx>合同废弃</dkjdmx></detail></body></root>";
		HashMap resultMap5 = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_DKJDCX.xml", t5, "");
		//String t6 = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><tr_code>X00003</tr_code><corp_no>00031400</corp_no><req_no>20170114104159270</req_no><serial_no></serial_no><ans_no>8609396</ans_no><next_no></next_no><tr_acdt>2017-01-14</tr_acdt><tr_time>10:41:59</tr_time><ans_code>00</ans_code><ans_info>交易处理成功...</ans_info><particular_code></particular_code><atom_tr_count></atom_tr_count><reserved></reserved><particular_info></particular_info></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>14</zjls><zys>2</zys><detail><jyrq>2017-01-13</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>11618.48</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-12-15</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>10243.04</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-11-22</jyrq><ywlx>部分提取</ywlx><fsje>31572.08</fsje><zhye>8867.60</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-11-18</jyrq><ywlx>汇缴</ywlx><fsje>1375.44</fsje><zhye>40439.68</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-10-14</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>39064.24</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-09-13</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>37760.96</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-09-05</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>36457.68</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-07-14</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>35154.40</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-06-30</jyrq><ywlx>年度结息</ywlx><fsje>395.97</fsje><zhye>33851.12</zhye><jylx>正常</jylx></detail><detail><jyrq>2016-06-22</jyrq><ywlx>汇缴</ywlx><fsje>1303.28</fsje><zhye>33455.15</zhye><jylx>正常</jylx></detail></body></root>";
		//resultMap = MessageCtrMain.analysisPacket("2","D:/tomcat_work/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031400/REP_YZ.xml", t6, "");
	}
	
}
