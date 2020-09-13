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
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00501Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 提取明细查询
 * @author Administrator
 *
 */
public class Handle005_00089800 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00501Result> list = new ArrayList<AppApi00501Result>();
		log.info(Constants.LOG_HEAD+"appApi00501 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Send"+form.getCenterId()).trim();
		AppApi00501Result app00501Result1 = new AppApi00501Result();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		//日期不能大于05-01
		int temp = CommonUtil.compare_date(form.getStartdate(),"2017-05-01","yyyy-MM-dd");
		if(temp<0)
		{
			form.setStartdate("2017-05-01");
		}
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi005Type"+form.getCenterId()).trim());	
			form.setFlag("2");
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_TQMXCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158048");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_TQMXCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00501Result1);
			if(!"0".equals(app00501Result1.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00501Result1.getRecode());
				modelMap.put("msg", app00501Result1.getMsg());
				log.error("中心返回报文 状态recode :"+app00501Result1.getRecode()+"  ,  描述msg : "+app00501Result1.getMsg());
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
						AppApi00501Result app00501Result = new AppApi00501Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);					
							app00501Result.setTransdate(valus[0]);
							app00501Result.setTqyy(valus[1]);
							app00501Result.setDrawamt(String.format("%,.2f",Double.valueOf(valus[2])));
							list.add(app00501Result);
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
		
		
//		app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00501"+form.getCenterId()+".result", (AppApi00501Result)list.get(i));	
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
