package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00601Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00703Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 还款计划查询
 * @param form 请求参数
 * @param modelMap 返回数据容器
 * @return 回调页面名
 */
public class Handle00703_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00703 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00703Result> list = new ArrayList<AppApi00703Result>();
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		if(CommonUtil.isEmpty(form.getBodyCardNumber()))
		{
			modelMap.put("recode", "999999");
			modelMap.put("msg", "上传参数不正确！");
			return modelMap;
		}
		
		AppApi00703Result app00703Result = new AppApi00703Result();
		AppApi00601Result app006Result = new AppApi00601Result();
		AES aes =new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		if(Constants.method_BSP.equals(send)){////bsp通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setQtype(form.getType());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			 
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHTH.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app006Result);
			
			if(!"0".equals(app006Result.getRecode())){
				modelMap.put("recode", app006Result.getRecode());
				modelMap.put("msg", app006Result.getMsg());
				log.error("中心返回报文 状态recode :"+app006Result.getRecode()+"  ,  描述msg : "+app006Result.getMsg());
				return modelMap;
			}
			form.setLoancontractno(app006Result.getLoanaccnum());
			HashMap map_dk = new HashMap();//封装用MAP
			map_dk = BeanUtil.transBean2Map(form);		
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HKJHCX.txt", map_dk, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129122");
			 
			log.debug("中心下传报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HKJHCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00703Result);
			
			if(!"0".equals(app00703Result.getRecode())){
				modelMap.put("recode", app00703Result.getRecode());
				modelMap.put("msg", app00703Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00703Result.getRecode()+"  ,  描述msg : "+app00703Result.getMsg());
				return modelMap;
			}
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			if(!filename.equals("")){
				
			
				FtpUtil f=new FtpUtil("bsp"+form.getCenterId());
			    f.getFileByServer("/"+filename);
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
			    //========================================================================== 
			    File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
				
				
				System.out.println("查询还款计划，批量文件："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					while (line != null) {// 判断读取到的字符串是否不为空
						AppApi00703Result aa00703Result = new AppApi00703Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);					
							aa00703Result.setDqqc(valus[0]);
							aa00703Result.setEnddate1(valus[1]);
							aa00703Result.setPlanprin(String.format("%,.2f",Double.valueOf(valus[2])));
							aa00703Result.setPlanint(String.format("%,.2f",Double.valueOf(valus[3])));
							aa00703Result.setCurseqstate(valus[4]);
							aa00703Result.setPlanprin_oweprin(String.format("%,.2f",Double.valueOf(valus[5])));
							aa00703Result.setPlanint_oweint(String.format("%,.2f",Double.valueOf(valus[6])));
							aa00703Result.setSumplan(String.format("%,.2f",Double.valueOf(valus[2])+Double.valueOf(valus[3])));
							list.add(aa00703Result);
							line = breader.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					breader.close();
					isr.close();
					ffis.close();
					file.delete();
					filerename.delete();
				}
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "借款合同信息处理异常");
				return modelMap;
			}	
		}
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00703"+form.getCenterId()+".result", (AppApi00703Result)list.get(i));	
			detail.add(result1);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", detail);	
		
		log.info(Constants.LOG_HEAD+"appApi00703 end.");
		return modelMap;
	}
}
