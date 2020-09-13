package com.yondervision.yfmap.handle.handleImpl.handle00043200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
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
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00802_1Result;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00802_2Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00201Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi002Result;
import com.yondervision.yfmap.result.zhongshan.AppApi002Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00802_00043200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		/* 模拟返回开始  */	
		AppApi00802Form form = (AppApi00802Form)form1;
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		int pageno = Integer.parseInt(form.getPagenum())+1;
		form.setPagenum(pageno+"");
		form.setSelectValue(URLDecoder.decode(form.getSelectValue(),"UTF-8"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");	
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		JiNanAppApi00802_1Result app00802Result = new JiNanAppApi00802_1Result();
		List<JiNanAppApi00802_2Result> list = new ArrayList<JiNanAppApi00802_2Result>();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());
//			form.setYear(form.getStartdate().substring(0, 4));
			HashMap map = BeanUtil.transBean2Map(form);	
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_LPCX_LIKE.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType,"/Users/sunxl/Documents/workspace/workspaceEE/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00043200/REQ_YEMXCX.xml", map, req);
			log.debug("发往中心报文："+xml);	
				
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "510003");
			
//			String reqxml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><transCode>appapi001</transCode><recvDate>2015-07-30</recvDate><recvTime>14:30:30</recvTime><sendSeqno>12345678</sendSeqno><key></key><centerSeqno>123456</centerSeqno><recode>000000</recode><msg>success</msg></head><body><detail><transdate>2015-06-01</transdate><unitaccnum>201000492114</unitaccnum><unitaccname></unitaccname><accnum>801051750450</accnum><accname></accname><amt>123</amt><bal>12345</bal><remark>adf</remark><agentinstcode></agentinstcode><agentop></agentop><hostsernum>924</hostsernum><freeuse1></freeuse1><freeuse2>0.00</freeuse2><freeuse3></freeuse3></detail><detail><transdate>2015-06-01</transdate><unitaccnum>201000492114</unitaccnum><unitaccname></unitaccname><accnum>801051750450</accnum><accname></accname><amt>123</amt><bal>12345</bal><remark>adf</remark><agentinstcode></agentinstcode><agentop></agentop><hostsernum>924</hostsernum><freeuse1></freeuse1><freeuse2>0.00</freeuse2><freeuse3></freeuse3></detail></body></root>		";
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "/Users/sunxl/Documents/workspace/workspaceEE/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00043200/REP_YEMXCX.xml", reqxml, req);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_LPCX.xml", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			log.debug("MAP封装成BEAN："+app00802Result);
			
			if(!"00".equals(app00802Result.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询贷款楼盘，批量文件："+filename);
			if(filename!=null){

				File file = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					JiNanAppApi00802_2Result aa00802Result = new JiNanAppApi00802_2Result();
					try {
						log.debug("读取文件  ："+line);
						String[] param = line.split("\\@\\|\\$");						
						aa00802Result.setLpmc(param[0]);
						aa00802Result.setKfdw(param[1]);
						aa00802Result.setPzzh(param[2]);
						list.add(aa00802Result);
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
				modelMap.put("msg", "楼盘信息处理异常");
				return modelMap;
			}
		}
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00802Detail = null;
		for(int i=0;i<list.size();i++){
			appapi00802Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".detail", list.get(i));			
			result.add(appapi00802Detail);
		}	
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	public static void main(String[] args) throws CenterRuntimeException, IOException{
		AppApi00201Form form1 = new AppApi00201Form();
			ModelMap modelMap = new ModelMap();
			form1.setSurplusAccount("777000003123");
			form1.setCenterId("00043200");
			form1.setPagenum("0");
			form1.setPagerows("10");
			form1.setStartdate("2014-01-01");
			form1.setEnddate("2016-01-01");
			Handle00802_00043200 hand = new Handle00802_00043200();
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
