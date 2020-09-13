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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00701Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi00701Result;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi007Result;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00701Result;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi00701Result;
import com.yondervision.yfmap.result.zhangjiakou.ZhangJiaKouAppApi007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 贷款明细查询
 */
public class Handle007_00031300 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00701 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<ZhangJiaKouAppApi007Result> list = new ArrayList<ZhangJiaKouAppApi007Result>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		
		ZhangJiaKouAppApi00701Result app007Result = new ZhangJiaKouAppApi00701Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			form.setPagenum(String.valueOf(Integer.valueOf(form.getPagenum())));//让前端改成从1开始录入
			form.setPagerows(String.valueOf((Integer.valueOf(form.getPagerows()))));
			HashMap map = new HashMap();//封装用MAP
			System.out.println("pagenum===="+form.getPagenum());
			System.out.println("pagerows===="+form.getPagerows());
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKMXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/tools/tomcat6.0.14/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300/REQ_DKMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00005");
			 
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKMXCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/tools/tomcat6.0.14/tomcat6.0.14/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00031300/REP_DKMXCX.xml", rexml, req);
//			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app007Result);
			
			if(!Constants.sucess_ts.equals(app007Result.getRecode())){
				modelMap.put("recode", app007Result.getRecode());
				modelMap.put("msg", app007Result.getMsg());
				log.error("中心返回报文 状态recode :"+app007Result.getRecode()+"  ,  描述msg : "+app007Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询贷款明细，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					ZhangJiaKouAppApi007Result aa00701Result = new ZhangJiaKouAppApi007Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);									
						aa00701Result.setOperationDate(valus[0]);
						aa00701Result.setOperationType(valus[1]);
						aa00701Result.setWithdrawalAmount(String.format("%.2f",Double.valueOf(valus[2])));
						aa00701Result.setLxAmount(String.format("%.2f",Double.valueOf(valus[3])));
						aa00701Result.setFxAmount(String.format("%.2f",Double.valueOf(valus[4])));
						list.add(aa00701Result);
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
				modelMap.put("msg", "贷款明细信息处理异常");
				return modelMap;
			}
		}		
//		app007Result.setHkze(String.format("%.2f",Double.valueOf(app007Result.getHkze())));
//		app007Result.setYhbjhj(String.format("%.2f",Double.valueOf(app007Result.getYhbjhj())));
//		app007Result.setYhlxhj(String.format("%.2f",Double.valueOf(app007Result.getYhlxhj())));
//		app007Result.setYhfxhj(String.format("%.2f",Double.valueOf(app007Result.getYhfxhj())));
//		app007Result.setFreeuse1(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse1())));
//		app007Result.setFreeuse2(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse2())));
//		app007Result.setFreeuse3(String.format("%,.2f",Double.valueOf(app007Result.getFreeuse3())));
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", (ZhangJiaKouAppApi007Result)list.get(i));	
			result.add(result1);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);	
		modelMap.put("total", app007Result.getSumnum());
		modelMap.put("totalPage", app007Result.getSumys());
		modelMap.put("currentPage", app007Result.getThisys());
		modelMap.put("currentPageTotal", app007Result.getThisnum());
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}
	
	
	public static void main(String[] args){
		AppApi00501Form form1 = new AppApi00501Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00031300");
		form1.setPagenum("0");
		form1.setPagerows("10");
		form1.setStartdate("2015-01-01");
		form1.setEnddate("2015-10-01");
		Handle007_00031300 hand = new Handle007_00031300();
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
