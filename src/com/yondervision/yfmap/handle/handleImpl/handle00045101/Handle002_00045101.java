package com.yondervision.yfmap.handle.handleImpl.handle00045101;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import net.sf.json.JSONObject;

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
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00045101 implements CtrlHandleInter {
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
		List<WeiHaiAppApi002Result> list = new ArrayList<WeiHaiAppApi002Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api002"+form.getStartdate().substring(0, 4)+"SF"+form.getBodyCardNumber()+"GJJ"+form.getSurplusAccount()+".txt";
		
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		
		/**************************** 威海 201405***********************************************************/
		WeiHaiAppApi00201Result app002Result = new WeiHaiAppApi00201Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			form.setYear(form.getStartdate().substring(0, 4));
			map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REQ_YEMXCX.txt", map, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YEMXCX.txt", map, req);
			log.debug("YFMAP发往中心BSP报文："+xml);			
			
			File isFile = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP_"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT_"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "112108");
						
//			String reqxml = "<AuthCode1>0000</><AuthCode2>0000</><AuthCode3>0000</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>2644244</><ChannelSeq>398243</><ChkCode>0000</><MTimeStamp>11:05:32</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>11:05:32</><TellCode>0000</><TranChannel>00</><TranCode>112108</><TranDate>2015-12-16</><TranIP>10.22.21.26</><TranSeq>2644244</><filename>abcd.txt</>";
			
			
			log.debug("BSP返回YFMAP报文："+reqxml);
			
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REP_YEMXCX.txt", reqxml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_YEMXCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN："+app002Result);
			if(!"0".equals(app002Result.getRecode())){
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
				return modelMap;
			}
			
			if(!app002Result.getFileName().equals("")){
				String filename = app002Result.getFileName().substring(app002Result.getFileName().lastIndexOf("/"));
				/****
				 *	此处应加入FTP下载 
				 **/
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空					
					WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();					
					try {
						String[] param = line.split("~");
						line = breader.readLine();
						log.debug("读取参数个数  ："+param.length);  
						log.debug("读取文件  ："+line);						
						aa00201Result.setPrebalance(param[0]);//姓名
						aa00201Result.setPaybegindate(param[1]);//身份证
						
						aa00201Result.setSummary(param[2]);//交易日期	
						aa00201Result.setBusiamt1(param[3]);//业务类型
						aa00201Result.setBusiamt2(param[4]);//发生额
						aa00201Result.setBalance(param[5]);//余额
						aa00201Result.setBusiamt3(param[6]);//开始年月
						aa00201Result.setBusiamt4(param[7]);//截止年月									
						aa00201Result.setBusiamttotal1(param[8]);//明细状态
						
//						log.debug(param[0]+"~"+param[1]+"~"+param[2]+"~"+param[3]+"~"+param[4]+"~"+param[5]+"~"+param[6]+"~"+param[7]+"~"+param[8]);
						
//						log.debug(String.format("%,.2f",Double.valueOf(param[4])));
//						log.debug(String.format("%,.2f",Double.valueOf(param[5])));
//						
						aa00201Result.setBusiamt2(String.format("%,.2f",Double.valueOf(param[4])));
						aa00201Result.setBalance(String.format("%,.2f",Double.valueOf(param[5])));
						
						list.add(aa00201Result);
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();
			}				
		}
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		log.debug("处理title info 个数:"+list.size());
		for(int i=0;i<list.size();i++){
			appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", list.get(i));			
			result.add(appapi00101Result);
		}
		
//		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
//		while (it1.hasNext()) {
//			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
//			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//		}
	

		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		jsonObj.put("result", result);
		System.out.println(jsonObj);
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	
//	public static void main(String[] args){
//		AppApi00201Form form1 = new AppApi00201Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("P08070000258");
//		form1.setCenterId("00045101");
//		form1.setStartdate("2015-01-01");
//		form1.setEnddate("2015-12-31");
//		Handle002_00045101 hand = new Handle002_00045101();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block			
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
