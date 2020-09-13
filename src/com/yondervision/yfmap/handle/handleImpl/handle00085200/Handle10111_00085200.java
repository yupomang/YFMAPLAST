package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.yondervision.yfmap.form.AppApi10101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result01;
import com.yondervision.yfmap.result.zunyi.AppApi00802Result;
import com.yondervision.yfmap.result.zunyi.AppApi10101Result;
import com.yondervision.yfmap.result.zunyi.AppApi10101Result01;
import com.yondervision.yfmap.result.zunyi.Mi201;
import com.yondervision.yfmap.result.zunyi.Mi203;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi002Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00802;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle10111_00085200 
* @Description: 遵义网点查询
* @author Caozhongyan
* @date Apr 11, 2016 3:16:07 PM   
* 
*/ 
public class Handle10111_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi10111 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi10101Form form = (AppApi10101Form)form1;
		log.debug("YFMAP 网点查询 form 参数:"+form);		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		ZunYiAppApi00201Result app00802Result = new ZunYiAppApi00201Result();
		
		List<ZunYiAppApi00802> list = new ArrayList<ZunYiAppApi00802>();
		log.info(form.getSelectValue());
		form.setSelectValue(URLDecoder.decode(form.getSelectValue(),"UTF-8"));
		log.info(form.getSelectValue());
		List resultList = new ArrayList();		
		log.debug("YFMAP 网点查询 楼盘名称 参数:"+form.getSelectValue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api10111WDGJJ.txt";
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_WDCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "148020");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>wdcx.txt</><sumcounts></><counts></><pageflag>1</>";
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_WDCX.txt", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!"0".equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}	
			
			if(!app00802Result.getFileName().equals("")){
				FtpUtil f=new FtpUtil("bsp");
			    f.getFileByServer("/"+app00802Result.getFileName());
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+app00802Result.getFileName());
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname));
				//========================================================================== 
				File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				
				String[] paraNames = line.split("~");
				//可办业务类型reason,summary办理时间，BANKNAME银行名称，BANKADDR网点地址，LINKPHONE网点电话
				int area=0,BANKNAME=0,BANKADDR=0,LINKPHONE=0,reason=0,summary=0;
				for(int i=0;i<paraNames.length;i++){
					if("area".equals(paraNames[i])){
						area = i;
					}else if("bankname".equals(paraNames[i])){
						BANKNAME = i;
					}else if("bankaddr".equals(paraNames[i])){
						BANKADDR = i;
					}else if("linkphone".equals(paraNames[i])){
						LINKPHONE = i;
					}else if("reason".equals(paraNames[i])){
						reason = i;
					}else if("summary".equals(paraNames[i])){
						summary = i;
					}
				}
				line = breader.readLine();
				if(line == null){
					//异常无数据返回提示
//					modelMap.put("recode", "999999");
//					modelMap.put("msg", "无数据");
//					log.error("中心返回报文 状态recode :"+app002Result.getRecode()+"  ,  描述msg : "+app002Result.getMsg());
//					return modelMap;
				}
				
				//目前只有交易日期及摘要，缺少“业务类型、单位缴存、个人缴存、缴存合计”
				while (line != null) {					
					try {
						
						
						line=line+"*123+321*";
						String[] param = line.split("~");
						log.debug("读取文件  ："+line);
						log.debug("长度  ："+param.length);
						if(!"*123+321*".equals(param[param.length-1])){
							param[param.length-1]=param[param.length-1].substring(0,param[param.length-1].indexOf("*123+321*"));
						}else{
							param[param.length-1]="";
						}
						
						
						
						
						AppApi10101Result appApi10101Result = new AppApi10101Result();					
						List<TitleInfoBean> TitleInfoBeanContent = new ArrayList<TitleInfoBean>();
						List<TitleInfoBean> TitleInfoBeanList = new ArrayList<TitleInfoBean>();				
						Mi201 mi201 = new Mi201();
						mi201.setTel(param[LINKPHONE].isEmpty()?"--":"tel://"+param[LINKPHONE]);
						mi201.setServiceTime(param[summary].isEmpty()?"--":"time://"+param[summary]);
						mi201.setAddress(param[BANKADDR].isEmpty()?"--":"map://"+param[BANKADDR]);
						mi201.setWebsiteName(param[BANKNAME].isEmpty()?"--":param[BANKNAME]);
						mi201.setBusinessType(param[reason].isEmpty()?"--":param[reason]);
						AppApi10101Result01 appApi10101Result01 = new AppApi10101Result01();
						appApi10101Result01.setX("");
						appApi10101Result01.setY("");
						//需要进行图片地址拼接
						appApi10101Result01.setImg("");
						appApi10101Result.setMap(appApi10101Result01);				
						TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101"+form.getCenterId()+".content", mi201);				
						appApi10101Result.setContent(TitleInfoBeanContent);
						TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi10101"+form.getCenterId()+".list", mi201);
						appApi10101Result.setList(TitleInfoBeanList);				
						resultList.add(appApi10101Result);
						
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();						
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				file.delete();
			}	
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");			
		modelMap.put("result", resultList);

		return modelMap;
	}
	
	public static void main(String[] args) throws Exception{
//		AppApi00802Form form1 = new AppApi00802Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("99999");
//		form1.setCenterId("00053100");
//		form1.setLogid("113");
//		form1.setSelectValue("可国人");
//		form1.setPagenum("10");
//		form1.setPagerows("0");
//		Handle10111_00085200 hand = new Handle10111_00085200();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		String upItem = "<flag>0</><TellCode>5555</><ChkCode>5555</><BrcCode>00000000</><TranIP>10.10.10.11</><STimeStamp>08:50:31</><TranCode>148020</><TranChannel>00</><TranDate>2016-04-25</><ChannelSeq>12329</><MTimeStamp>08:50:31</><AuthFlag>0</><AuthCode1>5555</><AuthCode2>5555</><AuthCode3>5555</><linkrow></><area></><bankname>仁怀</><bankaddr></><pageflag></>"; 
		System.out.println(upItem.length());
		System.out.println(upItem.getBytes("GBK").length);
		
		
//		String[] ss="1014~~~~~上海浦东发展银行~~~".split("~");
//		for(int i=0;i<ss.length;i++)
//			System.out.println(i+" : "+ss[i]);
	}
	
}
