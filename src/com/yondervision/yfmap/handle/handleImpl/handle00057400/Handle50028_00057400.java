package com.yondervision.yfmap.handle.handleImpl.handle00057400;


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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil2;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi50028ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
* BSP短信消息推送统计接口
* @ClassName: Handle50028_00057400 
* @Description:  BSP短信消息推送统计接口
* @author XZW
* @date 2018年3月285日 下午09:43:59   
* 
*/ 
public class Handle50028_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00201Form form = (AppApi00201Form)form1;
		System.out.println("YFMAP发往中心——BSP短信消息推送统计接口");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50028ZHResult app50028ZHResult = new AppApi50028ZHResult();
		List list = new ArrayList();
		if(Constants.method_BSP.equals(send)){
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
			
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
				form.setTranDate(form.getSendDate());
			}
			if(!CommonUtil.isEmpty(form.getChannel())){
				form.setFlag(Channel.getChannel(form.getChannel()));
			}			
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_50028_143013.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "143013");			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_50028_143013.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app50028ZHResult);
			log.debug("MAP封装成BEAN："+app50028ZHResult);
			if(!"0".equals(app50028ZHResult.getRecode())){
				modelMap.put("recode", app50028ZHResult.getRecode());
				modelMap.put("msg", app50028ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50028ZHResult.getRecode()+"  ,  描述msg : "+app50028ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get("filename");
			System.out.println("批量文件："+filename);
			
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil2 f=new FtpUtil2("bsp");
				f.getFileByServerForNB2(filename);	   
//				FtpUtil f=new FtpUtil("bsp");
//			    f.getFileByServer(filename);				    
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				String line1 = breader.readLine();
				while (line1 != null) {			
					try {							
						log.debug("读取文件  ："+line1);
						String[] trs = line1.split("\\|");
						HashMap map1 = new HashMap(); 
						map1.put("smid", trs[0].trim());
						map1.put("modelId", trs[1].trim());
						map1.put("modelName", trs[2].trim());
						map1.put("sumRecord", trs[3].trim());
						map1.put("successNum", trs[4].trim());
						map1.put("failNum", trs[5].trim());
						//model,modelid,modelname,tcount,scount,fcount
						//"modelId":"KMDX0001","failNum":75,"modelName":"短信签约通知","successNum":6235,"smid":"","sumRecord":6310
						JSONObject jsonObject = JSONObject.fromObject(map1);
						list.add(jsonObject);
						line1 = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
				
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
			
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("sum", app50028ZHResult.getSum());
		modelMap.put("success", app50028ZHResult.getSuccess());
		modelMap.put("fail", app50028ZHResult.getFail());
		JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
		modelMap.put("modelMsg", jsonArray);
		JSONArray jsonArray1 = new JSONArray();
		modelMap.put("customizeMsg", jsonArray1);
		System.out.println("=========:"+JsonUtil.getGson().toJson(modelMap));
		log.debug("=========:"+JsonUtil.getGson().toJson(modelMap));
		return modelMap;
	}

}
