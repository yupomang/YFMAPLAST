package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 账户明细查询
 * @author Administrator
 *
 */
public class Handle002_00089800 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00201Form form = (AppApi00201Form)form1;
		List<AppApi00201Result> list = new ArrayList<AppApi00201Result>();
		log.info(Constants.LOG_HEAD+"appApi00201 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		AppApi00201Result app00201Result1 = new AppApi00201Result();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		//日期不能大于05-01
		int temp = CommonUtil.compare_date(form.getStartdate(),"2017-05-01","yyyy-MM-dd");
		if(temp<0)
		{
			form.setStartdate("2017-05-01");
		}
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());	
			form.setFlag("2");
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_ZHMXCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119170");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_ZHMXCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00201Result1);
			if(!"0".equals(app00201Result1.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00201Result1.getRecode());
				modelMap.put("msg", app00201Result1.getMsg());
				log.error("中心返回报文 状态recode :"+app00201Result1.getRecode()+"  ,  描述msg : "+app00201Result1.getMsg());
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
				
				
				System.out.println("账户明细文件："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					while (line != null) {// 判断读取到的字符串是否不为空
						AppApi00201Result app00201Result = new AppApi00201Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);			
							// 带看完文件补充
							// 交易日期、业务类型、发生金额、账户余额
							app00201Result.setJyrq(valus[0]);
							app00201Result.setSendSeqno(valus[1]);
							app00201Result.setYwlx(valus[5]);
							app00201Result.setFsje(valus[9]);
							app00201Result.setZhye(valus[10]);
//							app00501Result.setTransdate(valus[0]);
//							app00501Result.setTqyy(valus[1]);
//							app00501Result.setDrawamt(String.format("%,.2f",Double.valueOf(valus[2])));
							list.add(app00201Result);
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
		
		Collections.sort(list, new MyComparator());
//		app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=list.size()-1;i>=0;i--){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00201"+form.getCenterId()+".result", (AppApi00201Result)list.get(i));	
			detail.add(result1);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", detail);	
		
		log.info(Constants.LOG_HEAD+"appApi00201 end.");
		return modelMap;
	}
	class MyComparator implements Comparator {
		  public int compare(Object o1, Object o2){
			  AppApi00201Result s1 = (AppApi00201Result)o1;
			  AppApi00201Result s2 = (AppApi00201Result)o2;
		     if (s1.getJyrq().compareTo(s2.getJyrq()) != 0) //如果名字不一样
		        return s1.getJyrq().compareTo(s2.getJyrq());
		     else // 如果名字一样
		        return s1.getSendSeqno().compareTo(s2.getSendSeqno());
		  }
	}
}
