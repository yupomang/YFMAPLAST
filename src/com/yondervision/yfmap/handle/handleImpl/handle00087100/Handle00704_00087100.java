package com.yondervision.yfmap.handle.handleImpl.handle00087100;

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
import com.yondervision.yfmap.result.TitleInfoNameFormatBean;
import com.yondervision.yfmap.result.kunming.AppApi00703ZHResult;
import com.yondervision.yfmap.result.kunming.AppApi00704_1ZHResult;
import com.yondervision.yfmap.result.kunming.MoneyNumberTran;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 异地还款计划查询--314069
 * @author fxliu
 *
 */
public class Handle00704_00087100 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	//属性文件
    private static final ResourceBundle ReadProperties;		
	static{
	  //读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00703ZHResult app00704ZHResult = new AppApi00703ZHResult();
		AppApi00501Form form = (AppApi00501Form)form1;
		AES aes = new AES(form.getCenterId(),form.getChannel(),form.getAppid(),form.getAppkey());	
		form.setLoancontrnum(aes.decrypt(form.getLoancontrnum()));
		form.setBodyCardNumber(aes.decrypt(form.getBodyCardNumber()));
		List<AppApi00704_1ZHResult> list = new ArrayList<AppApi00704_1ZHResult>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Send"+form.getCenterId()).trim();
		HashMap resultMap = null;
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi007Type"+form.getCenterId()).trim());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("8888");
				form.setBrcCode("88888888");
				form.setTranDate(form.getSendDate());
			}
			
			HashMap map = new HashMap();//封装用MAP
			map = BeanUtil.transBean2Map(form);		
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_OffSITE_lOAN_REPAYPLAN.txt", map, req);
			log.debug("异地还款计划查询-前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "314069");
			
			
			log.debug("异地还款计划查询-前置YFMAP接收中心报文："+rexml);
			resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_OffSITE_lOAN_REPAYPLAN.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app00704ZHResult);
			log.debug("MAP封装成BEAN："+app00704ZHResult);
			if(!"0".equals(app00704ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app00704ZHResult.getRecode());
				modelMap.put("msg", app00704ZHResult.getMsg());
				log.error("异地还款计划查询-中心返回报文 状态recode :"+app00704ZHResult.getRecode()+"  ,  描述msg : "+app00704ZHResult.getMsg());
				return modelMap;
			}
			
			String filename = app00704ZHResult.getFilename();
			System.out.println("异地还款计划查询，批量文件："+filename);
			System.out.println("异地还款计划查询，批量文件："+filename);
			if("40".equals(form.getChannel())){
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");	
				modelMap.put("totalpage", app00704ZHResult.getTotalpage());
				modelMap.put("totalnum", app00704ZHResult.getTotalnum());
				modelMap.put("filename", filename);
				return modelMap;
			}
			if(!CommonUtil.isEmpty(filename)){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer(filename);				    
			    File file = new File(ReadProperties.getString("bsp_local_path")+"/"+filename);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					AppApi00704_1ZHResult app007_04ZHResult = new AppApi00704_1ZHResult();
					MoneyNumberTran mnTran = new MoneyNumberTran();
					try {
						log.debug("读取文件  ："+line);	
						String[] trs = line.split("~");
//						应还日期
						app007_04ZHResult.setPayDate(trs[0]);
//						应还本金
						app007_04ZHResult.setPrincipal(mnTran.moneyTran(trs[1]));
//						应还利息
						app007_04ZHResult.setInterest(mnTran.moneyTran(trs[2]));
//						应还合计
						app007_04ZHResult.setTotalMoney(mnTran.moneyTran(trs[3]));
						list.add(app007_04ZHResult);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				file.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查无数据");
				return modelMap;
			}
			
		}
		
		List<List<TitleInfoNameFormatBean>> result = new ArrayList();
		List<TitleInfoNameFormatBean> appapi00704Result = null;
		
		for(int i=0;i<list.size();i++){
			appapi00704Result = JavaBeanUtil.javaBeanToListTitleInfoNameFormatBean("appapi00704"+form.getCenterId()+".result", list.get(i));	
			result.add(appapi00704Result);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		modelMap.put("totalpage", app00704ZHResult.getTotalpage());
		modelMap.put("totalnum", app00704ZHResult.getTotalnum());
		return modelMap;
	}
	public static void main(String[] args) throws Exception {
		AppApi00501Form form = new AppApi00501Form();
		form.setCenterId("00087100");
		form.setChannel("20");
		AES aes = new AES(form.getCenterId(),form.getChannel(),null,null);
		form.setBodyCardNumber("");
		form.setLoancontrnum("");
		
		Handle00704_00087100 handle = new Handle00704_00087100();
		handle.action(form, new ModelMap());
	}
}
