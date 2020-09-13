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
import com.yondervision.yfmap.result.haikou.AppApi00701Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 贷款明细查询
 * @param form 请求参数
 * @param modelMap 返回数据容器
 * @return 回调页面名
 */
public class Handle007_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		log.info(Constants.LOG_HEAD+"appApi00701 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00501Form form = (AppApi00501Form)form1;
		List<AppApi00701Result> list = new ArrayList<AppApi00701Result>();
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		
		AppApi00701Result app007Result = new AppApi00701Result();
		AppApi00601Result app006Result = new AppApi00601Result();
		AES aes =new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		//日期不能大于05-01
		int temp = CommonUtil.compare_date(form.getStartdate(),"2017-05-01","yyyy-MM-dd");
		if(temp<0)
		{
			form.setStartdate("2017-05-01");
		}
		if(Constants.method_BSP.equals(send)){////bsp通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
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
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKMXCX.txt", map_dk, req);
			log.debug("发往中心报文："+xml);
			ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129123");
			 
			log.debug("中心下传报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKMXCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app007Result);
			
			if(!"0".equals(app007Result.getRecode())){
				modelMap.put("recode", app007Result.getRecode());
				modelMap.put("msg", app007Result.getMsg());
				log.error("中心返回报文 状态recode :"+app007Result.getRecode()+"  ,  描述msg : "+app007Result.getMsg());
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
				
				
				System.out.println("查询公积金明细，批量文件："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					while (line != null) {// 判断读取到的字符串是否不为空
						AppApi00701Result aa00701Result = new AppApi00701Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);					
	
							aa00701Result.setDqqc(valus[0]);
							//还款日期
							aa00701Result.setTransdate(valus[1]);
							//还款业务类型
							aa00701Result.setSummarycode(valus[2]);
							//还款金额
							aa00701Result.setTransamt(String.format("%,.2f",Double.valueOf(valus[3])));
							//还款资金类型
							aa00701Result.setLoantype(valus[4]);
							list.add(aa00701Result);
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
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00701"+form.getCenterId()+".result", (AppApi00701Result)list.get(i));	
			detail.add(result1);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", detail);	
		log.info(Constants.LOG_HEAD+"appApi00701 end.");
		return modelMap;
	}
	
}
