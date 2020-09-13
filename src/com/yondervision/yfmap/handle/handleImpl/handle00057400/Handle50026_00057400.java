package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.Tools;

/**
 * 业务办理统计
 * @author fxliu
 *
 */
public class Handle50026_00057400 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi00201Form form = (AppApi00201Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		HashMap resultMap = new HashMap();
		JSONArray arr = new JSONArray();

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
			
			if(!form.getChannel().trim().equals(""))
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			form.setChannelSeq(form.getSendSeqno());
			form.setTellCode("9981");
			form.setBrcCode("00057402");
			form.setTranDate(form.getSendDate());
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_50026_143010.txt", map, req);
			log.debug("业务量统计 - 前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "143010");
			
			log.debug("业务量统计 - 前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_50026_143010.txt", rexml, req);
			log.debug("业务量统计 - 解析报文MAP："+resultMap);

			if (!"0".equals(resultMap.get("recode"))) {
				modelMap.clear();
				modelMap.put("recode", resultMap.get("recode"));
				modelMap.put("msg", resultMap.get("msg"));
				log.error("业务量统计 - 中心返回报文 状态recode :" + resultMap.get("recode") + "  ,  描述msg : "
						+ resultMap.get("msg"));
				return modelMap;
			}
			
/*			if(!"0000".equals(resultMap.get("bodyRecode"))) {
				modelMap.clear();
				modelMap.put("recode", resultMap.get("bodyRecode"));
				modelMap.put("msg", resultMap.get("bodyMsg"));
				return modelMap;
			}*/
			
			String filename = (String)resultMap.get("filename");
			System.out.println("业务量统计 - 批量文件："+filename);
			
			if(!CommonUtil.isEmpty(filename)){				
//				FtpUtil f=new FtpUtil("bsp");
//			    f.getFileByServer(filename);				    
				FtpUtil2 f=new FtpUtil2("bsp");
				f.getFileByServerForNB2(filename);	   
				File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
				BufferedReader breader = new BufferedReader(isr);
				
				String line = breader.readLine();

				while (line != null) {
					try {
						JSONObject json = new JSONObject();
						String[] temp =  line.split("\\|");
//						json.put("type1", Tools.getCode(temp[1]));
//						json.put("type2", Tools.getCode(temp[2]));
//						json.put("type3", Tools.getCode(temp[3]));
//						json.put("bsptype", temp[4]);
//						json.put("detailName", temp[5]);
//						json.put("detailCount", temp[6]);
//						json.put("detailSuccess", temp[7]);
//						json.put("detailFail", temp[8]);
						json.put("type1", Tools.getCode(temp[0]));
						json.put("type2", Tools.getCode(temp[1]));
						json.put("type3", Tools.getCode(temp[2]));
						json.put("bsptype", temp[3]);
						json.put("detailName", temp[4]);
						json.put("detailCount", temp[5]);
						json.put("detailSuccess", temp[6]);
						json.put("detailFail", temp[7]);
						arr.add(json);
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
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
			
		}
		modelMap.clear();
		log.error("走到这了" + resultMap.get("recode") + "  ,  描述msg : "
				+ resultMap.get("msg"));
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("count", resultMap.get("count"));
		modelMap.put("fail", resultMap.get("fail"));
		modelMap.put("success", resultMap.get("success"));
		modelMap.put("result", arr);
		System.out.println("=========:"+JsonUtil.getGson().toJson(modelMap));
		log.debug("=========:"+JsonUtil.getGson().toJson(modelMap));
		return modelMap;
	}

}
