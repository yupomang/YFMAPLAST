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
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.dto.Mi126;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.dalian.DaLianAppApi007Result;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00906Result;
import com.yondervision.yfmap.service.impl.AppApi009ServiceImpl;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author czy
 * 中心试算码表参数
 */
public class Handle00906_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
		
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form =(AppApi00101Form)form1;
		form.setCenterId(form1.getCenterId());
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		DaLianAppApi007Result app00701Result = new DaLianAppApi007Result();
		List<DaLianAppApi00906Result> hkfs = new ArrayList<DaLianAppApi00906Result>();
		List<DaLianAppApi00906Result> llbs = new ArrayList<DaLianAppApi00906Result>();
		/**/
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00906");//*************************************
		para.setName("中心试算码表参数");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000005");
		para.setYwlb(Constants.logTableYwlb_gg);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());		
		
		AppApi009ServiceImpl service009 = new AppApi009ServiceImpl();
		List<Mi126> list126 = null;
		list126 = service009.appApi00902Select(form);
		if(list126.size()>0){
			//return "";
			System.out.println("#################################   大连试算参数己取回");
			
			for(int i=0;i<list126.size();i++){
				DaLianAppApi00906Result aa00906Result = new DaLianAppApi00906Result();
				aa00906Result.setZdm(list126.get(i).getFunc().trim());
				aa00906Result.setWwzym(list126.get(i).getParamkey().trim());
				aa00906Result.setWwzy(list126.get(i).getParamvalue().trim());						
				if("hkfs".equals(list126.get(i).getFunc().trim())){
					hkfs.add(aa00906Result);
				}else if("llbs".equals(list126.get(i).getFunc().trim())){
					llbs.add(aa00906Result);
				}					
			}
			
			modelMap.clear();
			modelMap.put("recode", "000000");
			modelMap.put("msg", "成功");
			modelMap.put("hkfs", hkfs);
			modelMap.put("llbs", llbs);
			return modelMap;
			
		}
		System.out.println("#################################   大连试算参数未取回");
		if(Constants.method_FSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_MBCS.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000005");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi001</transCode><recvDate>2015-04-12</recvDate><recvTime>12:12:111</recvTime><sendSeqno>1234567890</sendSeqno><key>1</key><centerSeqno>9999911111</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body><dqys>1</dqys><dqysjls>1000</dqysjls><zjls>1000</zjls><zys>1</zys><detail><zdm>hkfs</zdm><wwzym>01</wwzym><wwzy>等额本金</wwzy></detail><detail><zdm>hkfs</zdm><wwzym>02</wwzym><wwzy>等额本息</wwzy></detail><detail><zdm>hkfs</zdm><wwzym>03</wwzym><wwzy>次还款付息</wwzy></detail><detail><zdm>hkfs</zdm><wwzym>04</wwzym><wwzy>自由还款方</wwzy></detail><detail><zdm>hkfs</zdm><wwzym>05</wwzym><wwzy>等本等息</wwzy></detail><detail><zdm>hkfs</zdm><wwzym>06</wwzym><wwzy>利随本清</wwzy></detail><detail><zdm>llbs</zdm><wwzym>01</wwzym><wwzy>1.1倍</wwzy></detail><detail><zdm>llbs</zdm><wwzym>02</wwzym><wwzy>1.2倍</wwzy></detail><detail><zdm>llbs</zdm><wwzym>03</wwzym><wwzy>1.3倍</wwzy></detail></body></mi>";
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_MBCS.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00701Result);
			log.debug("MAP封装成BEAN："+app00701Result);
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00701Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00701Result.getMsg());//*************************************			
			
			if(!Constants.sucess.equals(app00701Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
//				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00701Result);
				modelMap.put("recode", app00701Result.getRecode());
				modelMap.put("msg", app00701Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00701Result.getRecode()+"  ,  描述msg : "+app00701Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					DaLianAppApi00906Result aa00906Result = new DaLianAppApi00906Result();
					try {
						log.debug("读取文件  ："+line);	
						String[] valus = line.split("\\@\\|\\$");
						aa00906Result.setZdm(valus[0]);
						aa00906Result.setWwzym(valus[1]);
						aa00906Result.setWwzy(valus[2]);						
						if("hkfs".equals(valus[0])){
							hkfs.add(aa00906Result);
						}else if("llbs".equals(valus[0])){
							llbs.add(aa00906Result);
						}
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
		
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
//		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(), app00701Result);
		
		
		service009.appApi00901Delete(form);
		service009.appApi00903Insert(form, hkfs);
		service009.appApi00903Insert(form, llbs);
		
		/**
		DaLianAppApi00906Result d1 = new DaLianAppApi00906Result();
		d1.setZdm("hkfs");
		d1.setWwzym("01");
		d1.setWwzy("等额本金");
		hkfs.add(d1);
		DaLianAppApi00906Result d2 = new DaLianAppApi00906Result();
		d2.setZdm("hkfs");
		d2.setWwzym("02");
		d2.setWwzy("等额本息");
		hkfs.add(d2);
//		DaLianAppApi00906Result d3 = new DaLianAppApi00906Result();
//		d3.setZdm("hkfs");
//		d3.setWwzym("03");
//		d3.setWwzy("次还款付息");
//		hkfs.add(d3);
//		DaLianAppApi00906Result d4 = new DaLianAppApi00906Result();
//		d4.setZdm("hkfs");
//		d4.setWwzym("04");
//		d4.setWwzy("自由还款方");
//		hkfs.add(d3);
//		DaLianAppApi00906Result d5 = new DaLianAppApi00906Result();
//		d5.setZdm("hkfs");
//		d5.setWwzym("05");
//		d5.setWwzy("等本等息");
//		hkfs.add(d5);
//		DaLianAppApi00906Result d6 = new DaLianAppApi00906Result();
//		d6.setZdm("hkfs");
//		d6.setWwzym("06");
//		d6.setWwzy("利随本清");
//		hkfs.add(d6);
		
		DaLianAppApi00906Result a1 = new DaLianAppApi00906Result();
		a1.setZdm("llbs");
		a1.setWwzym("01");
		a1.setWwzy("1.1倍");
		llbs.add(a1);
		DaLianAppApi00906Result a2 = new DaLianAppApi00906Result();
		a2.setZdm("llbs");
		a2.setWwzym("02");
		a2.setWwzy("1.2倍");
		llbs.add(a2);
		DaLianAppApi00906Result a3 = new DaLianAppApi00906Result();
		a3.setZdm("llbs");
		a3.setWwzym("03");
		a3.setWwzy("1.3倍");
		llbs.add(a3);
		*/
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("hkfs", hkfs);
		modelMap.put("llbs", llbs);
		return modelMap;
	}
}
