package com.yondervision.yfmap.handle.handleImpl.handle00041100;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00603Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00602Result;
import com.yondervision.yfmap.service.impl.DLLogServiceImpl;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.LogBack;
import com.yondervision.yfmap.util.LogPara;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author czy
 * 贷款信息维护
 */
public class Handle00603_00041100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00603Form form = (AppApi00603Form)form1;
		log.info(Constants.LOG_HEAD+"appApi00603 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Send"+form.getCenterId()).trim();
		log.debug("form:"+form); 
		/* 模拟返回开始  */
		
		if("--".equals(form.getJkr1qtsj())||CommonUtil.isEmpty(form.getJkr1qtsj())){
			form.setJkr1qtsj(" ");
		}
		if("--".equals(form.getJkr2qtsj())||CommonUtil.isEmpty(form.getJkr2qtsj())){
			form.setJkr2qtsj(" ");
		}
		if("--".equals(form.getJkr1qtgh())||CommonUtil.isEmpty(form.getJkr1qtgh())){
			form.setJkr1qtgh(" ");
		}
		if("--".equals(form.getJkr2qtgh())||CommonUtil.isEmpty(form.getJkr2qtgh())){
			form.setJkr2qtgh(" ");
		}
		
		DaLianAppApi00602Result app00602Result= new DaLianAppApi00602Result();
		DLLogServiceImpl dllogServiceImpl = new DLLogServiceImpl();
		LogPara para = new LogPara();//*************************************
		para.setCode("appapi00603");//*************************************
		para.setName("贷款个人信息修改");//*************************************
		para.setStartdate(CommonUtil.getSystemDate());//*************************************
		para.setKey(form.getSurplusAccount());//*************************************
		para.setBspcode1("33000010");
		para.setYwlb(Constants.logTableYwlb_gr);
		
		para.setWwfqsj(form.getWwfqsj());
		para.setWwjym(form.getWwjym());
		para.setQdly(form.getQdly());
		para.setJyms(form.getJyms());
		
		if(CommonUtil.isEmpty(form.getTxdkbz())){
			form.setTxdkbz("0");
		}
		
		if(Constants.method_FSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+form.getLogid()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi006Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
//			File file = new File(CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHXG.xml");
//			if(!file.exists()){
//				log.error("文件不存在!"+CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHXG.xml");
//				modelMap.clear();
//				modelMap.put("recode", "999999");
//				modelMap.put("msg", "待处理报文格式文件不存在");
//				return modelMap;
//			}		
			
			
//  模拟自动转换 开始			
//			// 要解析的文件
//			String updatexmlfilename = CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHXG.xml";		
//			// 指定修改的第二级节点名称 
//			System.out.println("文件路径:"+updatexmlfilename);
//			String jkr1sj = "jkr1qtsj";
//			String jkr2sj = "jkr2qtsj";
//			String jkr1gh = "jkr1qtgh";
//			String jkr2gh = "jkr2qtgh";
//			// 文档对象 
//			Document myDoc = null;
//			try { 
//				// 解析器 
//				SAXBuilder sb = new SAXBuilder();
//				// 文档赋值 
//				myDoc = sb.build(new FileInputStream(updatexmlfilename));
//				// 根元素 
//				Element root = myDoc.getRootElement();			
//				// 第一级节点 
//				Element page = root.getChild("body");			
//				// 测试用的指定节点修改前原内容 
//				if(!form.getJkr1qtsj().equals("")){
//					String jkr1SJ = page.getChild(jkr1sj).getText(); 
//					System.out.println("借款人1其它手机:" + jkr1SJ);
//					// 修改内容 
//					page.getChild(jkr1sj).setText(form.getJkr1qtsj());
//					// 测试用的指定节点修改后的内容 
//					jkr1SJ = page.getChild(jkr1sj).getText(); 
//					System.out.println("借款人1其它手机修改:" + jkr1SJ); 
//				}
//				
//				
//				if(!form.getJkr2qtsj().equals("")){
//					// 测试用的指定节点修改前原内容 
//					String jkr2SJ = page.getChild(jkr2sj).getText(); 
//					System.out.println("借款人2其它手机:" + jkr2SJ);
//					// 修改内容 
//					page.getChild(jkr2sj).setText(form.getJkr2qtsj());
//					// 测试用的指定节点修改后的内容 
//					jkr2SJ = page.getChild(jkr2sj).getText(); 
//					System.out.println("借款人2其它手机修改:" + jkr2SJ); 
//				}
//				
//				
//				if(!form.getJkr1qtgh().equals("")){
//					// 测试用的指定节点修改前原内容 
//					String jkr1GH = page.getChild(jkr1gh).getText(); 
//					System.out.println("借款人1其它固话:" + jkr1GH);
//					// 修改内容 
//					page.getChild(jkr1gh).setText(form.getJkr1qtgh());
//					// 测试用的指定节点修改后的内容 
//					jkr1GH = page.getChild(jkr1gh).getText(); 
//					System.out.println("借款人1其它固话修改:" + jkr1GH); 
//				}
//				
//				
//				if(!form.getJkr2qtgh().equals("")){
//					// 测试用的指定节点修改前原内容 
//					String jkr2GH = page.getChild(jkr2gh).getText(); 
//					System.out.println("借款人2其它固话:" + jkr2GH);
//					// 修改内容 
//					page.getChild(jkr2gh).setText(form.getJkr2qtgh());
//					// 测试用的指定节点修改后的内容 
//					jkr2GH = page.getChild(jkr2gh).getText(); 
//					System.out.println("借款人2其它固话修改:" + jkr2GH);
//				}
//				
//				
//				//答应修改后的文档 
//				String xmlFileData = new XMLOutputter().outputString(myDoc); 
//				System.out.println("Modified XML file is : " + xmlFileData); 
//				
//				//使用FileWriter修改原始的文档 
////				FileWriter fileWriter = new FileWriter(updatexmlfilename);				
////				fileWriter.write(xmlFileData); 
////				fileWriter.close(); 
//				OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(updatexmlfilename),"UTF-8");
//				fileWriter.write(xmlFileData);
//				fileWriter.flush();
//				fileWriter.close();
//			} catch (JDOMException e) { 
//				e.printStackTrace(); 
//			} catch (NullPointerException e) { 
//				e.printStackTrace(); 
//			} catch (FileNotFoundException e) { 
//				e.printStackTrace(); 
//			} catch (IOException e) { 
//				e.printStackTrace(); 
//			} 
//	模拟自动转换 结束
			
			
			
			para.setStartXml(CommonUtil.getSystemDate());//*************************************
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_XXWHXG.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			para.setReqmsg(xml.replace("\n", "").replace("\t", ""));//*************************************
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "33000010");
//			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><mi><head><transCode>appapi00603</transCode><recvDate>1</recvDate><recvTime>1</recvTime><sendSeqno>1</sendSeqno><key>1</key><centerSeqno>1</centerSeqno><recode>000000</recode><msg>成功</msg><bspjym1>1</bspjym1><bspjym2>2</bspjym2><fspjssj1>2015-06-30 10:43:10</fspjssj1><fspfssj1>2015-06-30 10:43:20</fspfssj1><fspjssj2>2015-06-30 10:43:30</fspjssj2><fspfssj2>2015-06-30 10:43:40</fspfssj2><fspjssj3>2015-06-30 10:43:50</fspjssj3><fspfssj3>2015-06-30 10:43:59</fspfssj3><fspjssj4>2015-06-30 10:44:00</fspjssj4><fspfssj4>2015-06-30 10:44:10</fspfssj4><fspjsdsfsj>2015-06-30 10:44:20</fspjsdsfsj><fspfsdsfsj>2015-06-30 10:44:30</fspfsdsfsj><bspjssj1>2015-06-30 10:44:40</bspjssj1><bspfssj1>2015-06-30 10:44:50</bspfssj1><bspjssj2>2015-06-30 10:45:00</bspjssj2><bspfssj2>2015-06-30 10:45:10</bspfssj2></head><body></body></mi>";			
			para.setSendStartTime(CommonUtil.getSystemDate());//*************************************
			para.setRepmsg(rexml.replace("\n", "").replace("\t", ""));//*************************************
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_XXWHXG.xml", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app00602Result);
			
			para.setSendEndTime(CommonUtil.getSystemDate());//*************************************
			para.setRecode(app00602Result.getRecode().equals(Constants.sucess)?Constants.logFileAandTable_cg:Constants.logFileAandTable_xb);//*************************************
			para.setRemsg(app00602Result.getMsg());//*************************************
			
			if(!Constants.sucess.equals(app00602Result.getRecode())){
				para.setEnddate(CommonUtil.getSystemDate());//*************************************
				LogBack.logInfo(para);//*************************************
				dllogServiceImpl.updateMi125Service(para, form.getLogid(),app00602Result);
				modelMap.clear();
				modelMap.put("recode", app00602Result.getRecode());
				modelMap.put("msg", app00602Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00602Result.getRecode()+"  ,  描述msg : "+app00602Result.getMsg());
				return modelMap;
			}
		}
		
		para.setEnddate(CommonUtil.getSystemDate());//*************************************
		LogBack.logInfo(para);//*************************************
		dllogServiceImpl.updateMi125Service(para, form.getLogid(),app00602Result);
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi00603 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00603Form form1 = new AppApi00603Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00041100");
		Handle00603_00041100 hand = new Handle00603_00041100();
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
