package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00201Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi002Result;
import com.yondervision.yfmap.result.zhongshan.AppApi002Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		AppApi00201Form form = (AppApi00201Form)form1;
		List<AppApi002Result> list = new ArrayList<AppApi002Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
//		String fname = formatter.format(date)+"api002"+form.getStartdate().substring(0, 4)+"SF"+form.getBodyCardNumber()+"GJJ"+form.getSurplusAccount();
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
//		form.setPagenum(String.valueOf(Integer.parseInt(form.getPagenum())+1));
		/**************************** 肇庆 201407***********************************************************/
		WeiHaiAppApi00201Result app002Result = new WeiHaiAppApi00201Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());
//			form.setYear(form.getStartdate().substring(0, 4));
			HashMap map = BeanUtil.transBean2Map(form);	
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_YEMXCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType,"/Users/sunxl/Documents/workspace/workspaceEE/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00043200/REQ_YEMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);	
				
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "510003");
			
//			String reqxml = "<?xml version=\"1.0\" encoding=\"gb2312\"?><root><head><tr_code>510003</tr_code><corp_no>1001</corp_no><req_no>1394464698820160901154635651</req_no><serial_no>1</serial_no><ans_no></ans_no><next_no></next_no><tr_acdt>2016-09-01</tr_acdt><tr_time>034635</tr_time><ans_code>99</ans_code><ans_info>交易程序定义有错误不能执行</ans_info><particular_code></particular_code><atom_tr_count>1</atom_tr_count><reserved></reserved><particular_info></particular_info></head><body></body></root>";
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "/Users/sunxl/Documents/workspace/workspaceEE/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00043200/REP_YEMXCX.xml", reqxml, req);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_YEMXCX.xml", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			
			if(!"00".equals(app002Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			log.debug("查询公积金明细，批量文件："+filename);
			if(filename!=null){

				File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				int sum = 0;
//				System.out.println("**********  form.getPagenum():"+form.getPagenum()+"   form.getPagerows():"+form.getPagerows());
//				int num = (Integer.valueOf(form.getPagenum()))*Integer.valueOf(form.getPagerows());
				int pnum = 0;
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi002Result aa00201Result = new AppApi002Result();
					try {
						log.debug("读取文件  ："+line);
						String[] param = line.split("\\@\\|\\$");						
						aa00201Result.setTransdate(param[0]);
						aa00201Result.setPaybegindate(param[3]);
						aa00201Result.setAgentinstcode(param[4]);
						String busitype = PropertiesReader.getProperty("yingshe.properties", form.getCenterId()+"_busitype_"+param[1]);
						if(busitype!=null&&!busitype.equals("")){
							aa00201Result.setSummary(busitype.trim());
						}else{
							aa00201Result.setSummary(param[1]);
						}
						aa00201Result.setBusiamt1(param[5]);
						aa00201Result.setBusiamt2(param[6]);
						aa00201Result.setBalance(param[2]);
						aa00201Result.setAgentop(param[7]);
						list.add(aa00201Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "批量明细信息处理异常");
				return modelMap;
			}
		}
		
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	public static void main(String[] args) throws CenterRuntimeException, IOException{
		System.getProperties().list(System.out);
	}
}
