package com.yondervision.yfmap.handle.handleImpl.handle00041100;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.dalian.DaLianAppApi007Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00704_00041100 implements CtrlHandleInter{

	Logger log = Logger.getLogger("YFMAP");	
	
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {		
		AppApi00501Form form = (AppApi00501Form)form1;
		form.setPagenum("1");
		form.setPagerows("10000");
		List<DaLianAppApi007Result> list = new ArrayList<DaLianAppApi007Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String str = null;
		if(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId())==null);
		{
			System.out.println("为空");
		}
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		DaLianAppApi007Result app007Result = new DaLianAppApi007Result();		
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00201");//*************************************
		para.setName("账户明细查询");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000011");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());
		if(CommonUtil.isEmpty(form.getTxdkbz())){
			form.setTxdkbz("0");
		}
		
		if(Constants.method_FSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			
			HashMap map = new HashMap();//封装用MAP
			
			map = BeanUtil.transBean2Map(form);		
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_CXXYBH.xml", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/Workspaces/MyEclipse 10/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00041100/REQ_CXXYBH.xml", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			System.out.println("YFMAP发往FSP报文:"+xml);
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000015");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00704</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:44:00</fspfssj3><fspjssj4>2015-06-30 10:44:10</fspjssj4><fspfssj4>2015-06-30 10:44:20</fspfssj4><fspjsdsfsj>2015-06-30 10:44:30</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:40</fspfsdsfsj><bspjssj1>2015-06-30 10:44:50</bspjssj1><bspfssj1>2015-06-30 10:45:00</bspfssj1><bspjssj2>2015-06-30 10:45:10</bspjssj2><bspfssj2>2015-06-30 10:45:20</bspfssj2></head><body><detail><sddkxybh>345</sddkxybh></detail><detail><sddkxybh>34534</sddkxybh></detail></body></mi>";
			log.debug("前置YFMAP接收中心报文——个人公积金信息查询："+rexml);
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_CXXYBH.xml", rexml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/Workspaces/MyEclipse 10/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00041100/REP_CXXYBH.xml", rexml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app007Result);			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app007Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app007Result.getMsg());//*************************************
			if(!"000000".equals(app007Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
//				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app007Result);
				modelMap.clear();
				modelMap.put("recode", app007Result.getRecode());
				modelMap.put("msg", app007Result.getMsg());
				log.error("中心返回报文 状态recode :"+app007Result.getRecode()+"  ,  描述msg : "+app007Result.getMsg());
				return modelMap;
			}			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
//			String filename = (String) resultMap.get("filename");
			System.out.println("查询公积金明细，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					DaLianAppApi007Result aa007Result = new DaLianAppApi007Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						aa007Result.setSddkxybh(valus[0]);
						
						list.add(aa007Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}
		}		
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();
		List<TitleInfoBean> appapi00704Detail = null;
		for(int i=0;i<list.size();i++){
			appapi00704Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00704"+form.getCenterId()+".detail", list.get(i));			
			detail.add(appapi00704Detail);
		}	
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("detail", detail);
		
		return modelMap;
	}
	
	public static void main(String[] args) throws CenterRuntimeException, Exception{
		AppApi00501Form form1 = new AppApi00501Form();
		ModelMap modelMap = new ModelMap();
		form1.setZjlx("12");
		form1.setZjhm("213");
		
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		Handle00704_00041100 hand = new Handle00704_00041100();
		
		
		ModelMap action = hand.action(form1, modelMap);

		String string = JsonUtil.getGson().toJson(action);
		
		System.out.println(string);
		
	}

}
