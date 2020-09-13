package com.yondervision.yfmap.handle.handleImpl.handle00087500;

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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.AppApi00801Result;
import com.yondervision.yfmap.result.AppApi00801Result01;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baoshan.Appapi008Result;
import com.yondervision.yfmap.result.baoshan.BuildingInfo;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00802_00087500 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		ResourceBundle ReadProperties = ResourceBundle.getBundle("ftp");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00802Form form = (AppApi00802Form)form1;
		log.debug("YFMAP 可贷楼盘查询 form 参数:"+form);	
		System.out.println("收到selectType==："+form.getSelectType());
		System.out.println("收到selectValue==："+form.getSelectValue());
		//log.debug("项目名称或楼盘名称待查信息："+form.getSelectValue());
//		if("1".equals(form.getSelectType())){
//			System.out.println("1111收到selectValue==："+form.getSelectValue());
//			form.setSelectType(new String(form.getSelectType().getBytes("iso8859-1"),"UTF-8"));
//			System.out.println("转码后项目名称或楼盘名称待查信息："+form.getSelectValue());
//		}
		
		List<BuildingInfo> buildList = new ArrayList<BuildingInfo>();
		
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Send"+form.getCenterId()).trim();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		Date date = new Date();
		String fname = formatter.format(date)+"api00802"+"SF"+"GJJLPCX"+".txt";

		Appapi008Result app008Result = new Appapi008Result();
		if(Constants.method_XML.equals(send)){////xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi002Type"+form.getCenterId()).trim());			
			HashMap map = new HashMap();//封装用MAP			
			map = BeanUtil.transBean2Map(form);		
			String xml = "";
			String resxml = "";
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			if("1".equals(form.getSelectType())){
				// 模糊查询
				//resxml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430011</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info></ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><devname></devname><freeuse1></freeuse1><centernm></centernm><instname></instname><prominfo>Test-WX-KeDaiLouPanChaXun2016-02-17</prominfo></body></root>";
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_LPCX_LIKE.xml", map, req);
			}else if("2".equals(form.getSelectType())){
				// 查询全部
				//resxml = "<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>430011</tr_code><corp_no></corp_no><req_no></req_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info></ans_info><particular_code></particular_code><particular_info></particular_info><reserved></reserved><ans_no></ans_no></head><body><devname></devname><freeuse1></freeuse1><centernm></centernm><instname></instname><prominfo>Test-WX-KeDaiLouPanChaXun2016-02-17</prominfo></body></root>";
			}else if("3".equals(form.getSelectType())){
				// 查询区域信息
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_LPCX.xml", map, req);
			}
			log.debug("YFMAP发往中心CSP报文："+xml);	
			resxml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "430409");
			log.debug("CSP返回YFMAP报文："+resxml);
			
			//HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/tomcat6.0.14/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00087500/REP_LPCX.xml", resxml, req);//CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REP_LPCX.xml", resxml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app008Result);
			log.debug("MAP封装成BEAN："+app008Result);
			if(!"0".equals(app008Result.getRecode())){
				modelMap.put("recode", app008Result.getRecode());
				modelMap.put("msg", app008Result.getMsg());
				log.error("中心返回报文 状态recode :"+app008Result.getRecode()+"  ,  描述msg : "+app008Result.getMsg());
				return modelMap;
			}
			String filename = app008Result.getFileName();
			log.debug("楼盘查询返回文件名=="+app008Result.getFileName());
			if(!app008Result.getFileName().equals("")){
				/****
				 *	此处应加入FTP下载 
				 **/

				FtpUtil f=new FtpUtil("csp");
			    f.getFileByServer(filename);				    
			    File filerename = new File(ReadProperties.getString("csp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("csp_local_path")+form.getSendDate()+"/"+fname);
//				File file = new File("/edoc"+"/"+form.getSelectValue()+app008Result.getFileName());
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();								
				log.debug(line);
				int lineNo = 1;
				while (line != null) {// 判断读取到的字符串是否不为空					
					BuildingInfo aa00802Result = new BuildingInfo();
					
					try {
							String[] param = line.split("\\|");
							line = breader.readLine();						
							log.debug("读取文件  ："+line);		
							if(lineNo >=2){
								//项目名称~地址~所属区域~开发商名称~
								aa00802Result.setProjectName(param[0].trim());//项目名称
								aa00802Result.setCocustname(param[2].trim());//开发商名称
								aa00802Result.setHousesit(param[1].trim());//项目地址
								//aa00802Result.setPolenums(param[4].trim());//栋号列表
								buildList.add(aa00802Result);
							}
							lineNo = lineNo + 1;
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();
			}				
		}
		List<AppApi00801Result> result = new ArrayList<AppApi00801Result>();
		List<TitleInfoBean> appapi00802ListResult = null;
		List<TitleInfoBean> appapi00802ContentResult = null;
		for(int i=0;i<buildList.size();i++){
			AppApi00801Result resultTmp = new AppApi00801Result();
			appapi00802ListResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".list", buildList.get(i));			
			appapi00802ContentResult = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".content", buildList.get(i));	
			resultTmp.setMap(new AppApi00801Result01());
			resultTmp.setList(appapi00802ListResult);
			resultTmp.setContent(appapi00802ContentResult);
			result.add(resultTmp);
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00045101");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle00802_00087500 hand = new Handle00802_00087500();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
