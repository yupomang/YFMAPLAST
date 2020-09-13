package com.yondervision.yfmap.handle.handleImpl.handle00041100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00101Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi007Result;
import com.yondervision.yfmap.service.DLLogService;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle012_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
		
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00201Form form = (AppApi00201Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		DaLianAppApi007Result app00701Result = new DaLianAppApi007Result();
		List<AppApi00101Result> list = new ArrayList<AppApi00101Result>();
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi01201");//*************************************
		para.setName("维修基金查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000008");
		para.setYwlb("0");
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());		
		
		if(Constants.method_FSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_WXJJCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000008");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi01201</transCode><recvDate>2015-04-12</recvDate><recvTime>12:12:111</recvTime><sendSeqno>1234567890</sendSeqno><key>1</key><centerSeqno>9999911111</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>1</zjls><zys>10</zys><detail><niandu>2015</niandu><zhanghao>2212120</zhanghao><mingcheng>小一</mingcheng><zjhm>220110197710101231</zjhm><wxjjlxje>50000.00</wxjjlxje><yue>60000.00</yue><dwwxjj>70000.00</dwwxjj><grwxjj>40000.00</grwxjj><qxsj>2015-02-01</qxsj><zzsj>2015-10-21</zzsj><dizhi>大连市甘井子区20181号10楼1011室</dizhi><mianji>150</mianji><glh>201102010001</glh></detail><detail><niandu>2014</niandu><zhanghao>2212121</zhanghao><mingcheng>小二</mingcheng><zjhm>220110197710101232</zjhm><wxjjlxje>50000.00</wxjjlxje><yue>60000.00</yue><dwwxjj>70000.00</dwwxjj><grwxjj>40000.00</grwxjj><qxsj>2014-02-01</qxsj><zzsj>2014-10-21</zzsj><dizhi>大连市甘井子区20181号10楼1012室</dizhi><mianji>150</mianji><glh>201102010001</glh></detail><detail><niandu>2013</niandu><zhanghao>2212122</zhanghao><mingcheng>小三</mingcheng><zjhm>220110197710101233</zjhm><wxjjlxje>50000.00</wxjjlxje><yue>60000.00</yue><dwwxjj>70000.00</dwwxjj><grwxjj>40000.00</grwxjj><qxsj>2013-02-01</qxsj><zzsj>2013-10-21</zzsj><dizhi>大连市甘井子区20181号10楼1013室</dizhi><mianji>150</mianji><glh>201102010001</glh></detail><detail><niandu>2012</niandu><zhanghao>2212123</zhanghao><mingcheng>小四</mingcheng><zjhm>220110197710101234</zjhm><wxjjlxje>50000.00</wxjjlxje><yue>60000.00</yue><dwwxjj>70000.00</dwwxjj><grwxjj>40000.00</grwxjj><qxsj>2012-02-01</qxsj><zzsj>2012-10-21</zzsj><dizhi>大连市甘井子区20181号10楼1014室</dizhi><mianji>150</mianji><glh>201102010001</glh></detail><detail><niandu>2011</niandu><zhanghao>2212124</zhanghao><mingcheng>小五</mingcheng><zjhm>220110197710101235</zjhm><wxjjlxje>50000.00</wxjjlxje><yue>60000.00</yue><dwwxjj>70000.00</dwwxjj><grwxjj>40000.00</grwxjj><qxsj>2011-02-01</qxsj><zzsj>2011-10-21</zzsj><dizhi>大连市甘井子区20181号10楼1015室</dizhi><mianji>150</mianji><glh>201102010001</glh></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_WXJJCX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00701Result);
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00701Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00701Result.getMsg());//*************************************
			
			if(!Constants.sucess.equals(app00701Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00701Result);
				modelMap.clear();
				modelMap.put("recode", app00701Result.getRecode());
				modelMap.put("msg", app00701Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00701Result.getRecode()+"  ,  描述msg : "+app00701Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("********************* appapi01201.json filename:"+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00101Result aa01201Result = new AppApi00101Result();
					try {
						log.debug("读取文件  ："+line);	
						String[] valus = line.split("\\@\\|\\$");
						aa01201Result.setNewOperationDate(valus[0]);
						aa01201Result.setCarryoverAmount(valus[1]);
						aa01201Result.setAccountOrganization(valus[2]);
						aa01201Result.setAccountOpenDate(valus[3]);						
						aa01201Result.setCompanyName(String.format("%,.2f",Double.valueOf(valus[4])));
						aa01201Result.setCompanyAccount(String.format("%,.2f",Double.valueOf(valus[5])));
						aa01201Result.setSurplusAccount(String.format("%,.2f",Double.valueOf(valus[6])));
						aa01201Result.setAccountStatus(String.format("%,.2f",Double.valueOf(valus[7])));
						aa01201Result.setAccountBalance(valus[8]);
						aa01201Result.setPaymentBase(valus[9]);						
						aa01201Result.setCompanyPaymentRate(valus[10]);
						aa01201Result.setAnnualPaymentSum(valus[11]);
						aa01201Result.setLastestPaymentDate(valus[12]);
						list.add(aa01201Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "借款合同信息处理异常");
				return modelMap;
			}
		}
		
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();
//		if(list.size()==0){
//			AppApi00101Result aa01201Result = new AppApi00101Result();
//			aa01201Result.setNewOperationDate("2015");
//			aa01201Result.setCarryoverAmount("2212120");
//			aa01201Result.setAccountOrganization("李一兵");
//			aa01201Result.setAccountOpenDate("220110197710101231");						
//			aa01201Result.setCompanyName("50,000.00");
//			aa01201Result.setCompanyAccount("60,000.00");
//			aa01201Result.setSurplusAccount("70,000.00");
//			aa01201Result.setAccountStatus("40,000.00");
//			aa01201Result.setAccountBalance("2015-02-01");
//			aa01201Result.setPaymentBase("2015-10-21");						
//			aa01201Result.setCompanyPaymentRate("大连市甘井子区20181号10楼1011室");
//			aa01201Result.setAnnualPaymentSum("150");
//			aa01201Result.setLastestPaymentDate("201102010001");
//			list.add(aa01201Result);
//			
//			AppApi00101Result aa01201Result1 = new AppApi00101Result();
//			aa01201Result1.setNewOperationDate("2014");
//			aa01201Result1.setCarryoverAmount("2212121");
//			aa01201Result1.setAccountOrganization("李一兵");
//			aa01201Result1.setAccountOpenDate("220110197710101232");						
//			aa01201Result1.setCompanyName("50,000.00");
//			aa01201Result1.setCompanyAccount("60,000.00");
//			aa01201Result1.setSurplusAccount("70,000.00");
//			aa01201Result1.setAccountStatus("40,000.00");
//			aa01201Result1.setAccountBalance("2014-02-01");
//			aa01201Result1.setPaymentBase("2014-10-21");						
//			aa01201Result1.setCompanyPaymentRate("大连市甘井子区20181号10楼1012室");
//			aa01201Result1.setAnnualPaymentSum("150");
//			aa01201Result1.setLastestPaymentDate("201102010001");
//			list.add(aa01201Result1);
//			
//			AppApi00101Result aa01201Result2 = new AppApi00101Result();
//			aa01201Result2.setNewOperationDate("2013");
//			aa01201Result2.setCarryoverAmount("2212121");
//			aa01201Result2.setAccountOrganization("李一兵");
//			aa01201Result2.setAccountOpenDate("220110197710101233");						
//			aa01201Result2.setCompanyName("50,000.00");
//			aa01201Result2.setCompanyAccount("60,000.00");
//			aa01201Result2.setSurplusAccount("70,000.00");
//			aa01201Result2.setAccountStatus("40,000.00");
//			aa01201Result2.setAccountBalance("2013-02-01");
//			aa01201Result2.setPaymentBase("2013-10-21");						
//			aa01201Result2.setCompanyPaymentRate("大连市甘井子区20181号10楼1013室");
//			aa01201Result2.setAnnualPaymentSum("150");
//			aa01201Result2.setLastestPaymentDate("201102010001");
//			list.add(aa01201Result2);
//			
//			AppApi00101Result aa01201Result3 = new AppApi00101Result();
//			aa01201Result3.setNewOperationDate("2012");
//			aa01201Result3.setCarryoverAmount("2212121");
//			aa01201Result3.setAccountOrganization("李一兵");
//			aa01201Result3.setAccountOpenDate("220110197710101234");						
//			aa01201Result3.setCompanyName("50,000.00");
//			aa01201Result3.setCompanyAccount("60,000.00");
//			aa01201Result3.setSurplusAccount("70,000.00");
//			aa01201Result3.setAccountStatus("40,000.00");
//			aa01201Result3.setAccountBalance("2012-02-01");
//			aa01201Result3.setPaymentBase("2012-10-21");						
//			aa01201Result3.setCompanyPaymentRate("大连市甘井子区20181号10楼1014室");
//			aa01201Result3.setAnnualPaymentSum("150");
//			aa01201Result3.setLastestPaymentDate("201102010001");
//			list.add(aa01201Result3);
//		}
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01201"+form.getCenterId()+".detail", (AppApi00101Result)list.get(i));	
			result.add(result1);
		}
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00701Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00201Form form1 = new AppApi00201Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		form1.setPagenum("0");
		Handle012_00041100 hand = new Handle012_00041100();
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
