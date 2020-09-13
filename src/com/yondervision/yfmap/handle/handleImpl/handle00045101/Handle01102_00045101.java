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
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi01101Result;
import com.yondervision.yfmap.result.AppApi011Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.nongkengHEB.NongKenAppApi00701Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi00601Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi007Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 农垦：个人提取计划查询
 *
 */
public class Handle01102_00045101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		/* 模拟返回开始  */		
		AppApi00501Form form = (AppApi00501Form)form1;
		AppApi01101Result appapi001 = new AppApi01101Result();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Send"+form.getCenterId()).trim();		
		AppApi01101Result app011Result = new AppApi01101Result();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fname = formatter.format(date)+"api007"+"SF"+form.getBodyCardNumber()+"GJJDKJDCX"+form.getSurplusAccount()+".txt";
		
		List<NongKenAppApi00701Result> list = new ArrayList<NongKenAppApi00701Result>();
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Key"+form.getCenterId()).trim());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi011Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REQ_TQJDCX.txt", map, req);//CommonUtil.getFullURL(Constants.msgPath+"00045101")
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+"00045101")+"/BSP_REQ_TQJDCX.txt", map, req);
			log.debug("YFMAP发往中心CSP报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP_"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT_"+form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "153004");
//			String reqxml = "<AuthCode1>0000</><AuthCode2>0000</><AuthCode3>0000</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>2645035</><ChannelSeq>398243</><ChkCode>0000</><MTimeStamp>2015-12-16 11:23:14</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>2015-12-16 11:23:14</><TellCode>0000</><TranChannel>00</><TranCode>153004</><TranDate>2015-12-16</><TranIP>10.22.21.26</><TranSeq>2645035</><filename>www.txt</>";
			log.debug("CSP返回YFMAP报文："+reqxml);
			
			
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REP_TQJDCX.txt", reqxml, req);//CommonUtil.getFullURL(Constants.msgPath+"00045101")
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+"00045101")+"/BSP_REP_TQJDCX.txt", reqxml, req);
			log.debug("解析报文MAP："+resultMap);				
			BeanUtil.transMap2Bean(resultMap, app011Result);
			log.debug("MAP封装成BEAN："+app011Result);
			if(!"0".equals(app011Result.getRecode())){
				modelMap.put("recode", app011Result.getRecode());
				modelMap.put("msg", app011Result.getMsg());
				log.error("中心返回报文 状态recode :"+app011Result.getRecode()+"  ,  描述msg : "+app011Result.getMsg());
				return modelMap;
			}
			
			if(!app011Result.getAccinstname().equals("")){
				
				String filename = app011Result.getAccinstname().substring(app011Result.getAccinstname().lastIndexOf("/"));
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);	    
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空					
					NongKenAppApi00701Result app007Result = new NongKenAppApi00701Result();					
					try {
						String[] param = line.split("~");
						line = breader.readLine();						
						log.debug("读取文件  ："+line);							
						
						app007Result.setTerms(param[0]);//贷款程序
						app007Result.setTransdate(param[1]);//审批机构
						app007Result.setHostsernum(param[2]);//审批柜员
						app007Result.setSummary(param[3]);//审批日期
//						app007Result.setRepaytolamt(String.format("%,.2f",Double.valueOf(param[2])));//偿还总金额
//						app007Result.setRepayprin(String.format("%,.2f",Double.valueOf(param[3])));//偿还本金
//						app007Result.setRepayint(String.format("%,.2f",Double.valueOf(param[4])));//偿还利息
//						app007Result.setRepaypun(String.format("%,.2f",Double.valueOf(param[5])));//偿还罚息
//						app007Result.setLoanbal(String.format("%,.2f",Double.valueOf(param[6])));//账户余额
//						app007Result.setDesstate(param[9]);//交易状态						
						list.add(app007Result);
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				if(breader!=null){
					breader.close();
				}
				if(isr!=null){
					isr.close();				
				}
				if(ffis!=null){
					ffis.close();
				}
				if(filerename!=null){
					filerename.delete();
				}
			}
		}
		
		List<List<TitleInfoBean>> result = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01102"+form.getCenterId()+".detail", (NongKenAppApi00701Result)list.get(i));	
			result.add(result1);
		}
				
		
//		JSONObject jsonObj = new JSONObject(); 
//		jsonObj.put("recode", "000000");
//		jsonObj.put("msg", "成功");
//		jsonObj.put("result", result);			
//		log.debug(jsonObj);
		
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");		
		modelMap.put("result", result);	
		log.info(Constants.LOG_HEAD+"appApi01101 end.");
		return modelMap;
	}

//	public static void main(String[] args){
//		AppApi00501Form form1 = new AppApi00501Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("P08070000258");
//		form1.setCenterId("00045101");
//		form1.setStartdate("2015-01-01");
//		form1.setEnddate("2015-12-31");
//		Handle01102_00045101 hand = new Handle01102_00045101();
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
