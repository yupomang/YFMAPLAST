package com.yondervision.yfmap.handle.handleImpl.handle00031300;

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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi00201Result;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi00201Result;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi002Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author fengjing
 * 个人明细查询
 */
public class Handle002_00031300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");	
		
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {		
		AppApi00201Form form = (AppApi00201Form)form1;
		
		List<ZhangJiaKouAppApi00201Result> list = new ArrayList<ZhangJiaKouAppApi00201Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("YFMAP 公积金明细查询 form 参数:"+form);
		form.setPagenum(String.valueOf((Integer.valueOf(form.getPagenum()))));//前端直接加1
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		ZhangJiaKouAppApi002Result app002Result = new ZhangJiaKouAppApi002Result();		
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			System.out.println("pagenum===="+form.getPagenum());
			System.out.println("pagerows===="+form.getPagerows());
     		String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REQ_GRMXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_YEMXCX.xml", map, req);
    		log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
		 	String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00003");
/*			String rexml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head>" +
				"<reqflag>1</reqflag>" +
					"<msgtype>73</msgtype>" +
					"<tr_code>X00003</tr_code>" +
					"<corp_no>00053100</corp_no>" +
					"<oreq_no>8020150929007169</oreq_no>" +
					"<tr_acdt>2015-09-29</tr_acdt>" +
					"<tr_time>10:25:15</tr_time>" +
				"<ans_code>000000</ans_code>" +
					"<ans_info>交易成功完成</ans_info>" +
					"<particular_code>000000</particular_code>" +
					"<particular_info>交易成功完成</particular_info>" +
					"<reserved></reserved></head>" +
					"<body>" +
					"<dqys>1</dqys>" +
					"<dqysjls>10</dqysjls>" +
					"<zjls>1</zjls>" +
					"<zys>10</zys>" +
					"<detail>" +
					"<jyrq>2015-12-01</jyrq>" +
					"<ywlx>汇缴</ywlx>" +
					"<fsje>2500.000</fsje>" +
					"<zhye>2900.000</zhye>" +
					"<jylx>正常</jylx>" +
					"</detail>" +
					"<detail>" +
					"<jyrq>2016-01-01</jyrq>" +
					"<ywlx>汇缴</ywlx>" +
					"<fsje>2500.000</fsje>" +
					"<zhye>5400.000</zhye>" +
					"<jylx>正常</jylx>" +
					"</detail>" +
					"<detail>" +
					"<jyrq>2016-02-01</jyrq>" +
					"<ywlx>汇缴</ywlx>" +
					"<fsje>2500.000</fsje>" +
					"<zhye>2900.000</zhye>" +
					"<jylx>冲账</jylx>" +
					"</detail>" +
					"<detail>" +
					"<jyrq>2016-03-01</jyrq>" +
					"<ywlx>汇缴</ywlx>" +
					"<fsje>2500.000</fsje>" +
					"<zhye>5400.000</zhye>" +
					"<jylx>正常</jylx>" +
					"</detail></body></root>";
*/
 			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_GRMXCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/tools/tomcat6.0.14/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300/REP_GRMXCX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app002Result);			
 			if(!Constants.sucess_ts.equals(app002Result.getRecode())){
			 modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询公积金明细，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					ZhangJiaKouAppApi00201Result aa00201Result = new ZhangJiaKouAppApi00201Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						aa00201Result.setJyrq(valus[0]);
						aa00201Result.setYwlx(valus[1]);
						aa00201Result.setFsje(String.format("%.2f",Double.valueOf(valus[2])));
						aa00201Result.setZhye(String.format("%.2f",Double.valueOf(valus[3])));
						aa00201Result.setJylx(valus[4]);
						list.add(aa00201Result);
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
				modelMap.put("msg", "账户明细信息处理异常");
				return modelMap;
			}
		}
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		modelMap.put("total", app002Result.getSumnum());
		modelMap.put("totalPage", app002Result.getSumys());
		modelMap.put("currentPage", app002Result.getThisys());
		modelMap.put("currentPageTotal", app002Result.getThisnum());
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00201Form form1 = new AppApi00201Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00031300");
		form1.setPagenum("10");
		Handle002_00031300 hand = new Handle002_00031300();
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
