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
import com.yondervision.yfmap.form.AppApi01028Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01032Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 业务办理-还款-公积金还款信息
 * @author Administrator
 *
 */
public class Handle01032_00089800 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01028Form form = (AppApi01028Form)form1;
		List<AppApi01032Result> list = new ArrayList<AppApi01032Result>();
		log.info(Constants.LOG_HEAD+"appApi01032 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		AppApi01032Result AppApi01032Result = new AppApi01032Result();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			form.setFlag("2");
			HashMap map = BeanUtil.transBean2Map(form);
			/*公积金还款信息列表查询*/
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK119186.txt", map, req);
			log.debug("发往中心报文：公积金还款信息列表查询"+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119186");
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK119186.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, AppApi01032Result);
			if(!"0".equals(AppApi01032Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", AppApi01032Result.getRecode());
				modelMap.put("msg", AppApi01032Result.getMsg());
				log.error("中心返回报文：公积金还款信息列表查询- 状态recode :"+AppApi01032Result.getRecode()+"  ,  描述msg : "+AppApi01032Result.getMsg());
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
				
				System.out.println("还款业务-公积金还款信息："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					while (line != null) {// 判断读取到的字符串是否不为空
						AppApi01032Result app01032Result = new AppApi01032Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);			
							// 带看完文件补充
							// 2016-10-02~1539896~郑晓春~0.00~1600.00~77073.09~1219~201610~201610~
							// 交易日期、个人账号、姓名、金额1、金额2、金额3、月日、年月、年月、原因、类型
//							private String transdate = ""; // 交易日期
////							private String certinum = ""; // 证件号码
//							private String unitaccname = ""; // 姓名
//							private String amt1 = ""; //	金额1
//							private String amt2 = ""; //	金额2
//							private String payvouamt = ""; //	金额3
//							private String freeuse1 = ""; //	月 日
//							private String begym = ""; // 开始年月
//							private String endym = ""; // 结束年月
//							private String reason = ""; //	原因
//							private String dpbusitype = ""; // 类型
							app01032Result.setTransdate(valus[0]);
							app01032Result.setCertinum(valus[1]);
							app01032Result.setUnitaccname(valus[2]);
							app01032Result.setAmt1(valus[3]);
							app01032Result.setAmt2(valus[4]);
							app01032Result.setPayvouamt(valus[5]);
							app01032Result.setFreeuse1(valus[6]);
							app01032Result.setBegym(valus[7]);
							app01032Result.setEndym(valus[8]);
							app01032Result.setReason(valus[9]);
							//app01032Result.setDpbusitype(valus[10]);
							
							list.add(app01032Result);
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
			}
		}
		
		
//		app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
		
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01032"+form.getCenterId()+".result", (AppApi01032Result)list.get(i));	
			detail.add(result1);
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", detail);	
		
		log.info(Constants.LOG_HEAD+"appApi01032 end.");
		return modelMap;
	}
	
}
