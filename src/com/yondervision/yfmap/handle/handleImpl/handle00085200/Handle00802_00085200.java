package com.yondervision.yfmap.handle.handleImpl.handle00085200;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result;
import com.yondervision.yfmap.result.zunyi.AppApi00801Result01;
import com.yondervision.yfmap.result.zunyi.AppApi00802Result;
import com.yondervision.yfmap.result.zunyi.Mi203;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi002Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00802;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle00802_00087100 
* @Description: 遵义楼盘查询
* @author Caozhongyan
* @date Apr 8, 2016 4:57:14 PM   
* 
*/ 
public class Handle00802_00085200 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi00802 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00802Form form = (AppApi00802Form)form1;
		log.debug("YFMAP 可贷楼盘查询 form 参数:"+form);		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		
		ZunYiAppApi00201Result app00802Result = new ZunYiAppApi00201Result();
		
		List<ZunYiAppApi00802> list = new ArrayList<ZunYiAppApi00802>();
		form.setSelectValue(URLDecoder.decode(form.getSelectValue(),"UTF-8"));
		
		List resultList = new ArrayList();		
		log.debug("YFMAP 可贷楼盘查询 楼盘名称 参数:"+form.getSelectValue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHMMSS");
		Date date = new Date();
		String fname = formatter.format(date)+"api00802LPGJJ.txt";
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_KDLPCX.txt", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128033");

//			String rexml = "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>lpcx.txt</><sumcounts></><counts></><pageflag>1</>";
			
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_KDLPCX.txt", rexml, req);
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
				
				
				int projectname=0,cocustname=0,housesit=0,protonum=0,linkman=0,linkphone=0;
				for(int i=0;i<paraNames.length;i++){
					if("projectname".equals(paraNames[i])){
						projectname = i;//楼盘名称
					}else if("cocustname".equals(paraNames[i])){
						cocustname = i;//开发商名称
					}else if("housesit".equals(paraNames[i])){
						housesit = i;//楼盘地址
					}else if("protonum".equals(paraNames[i])){
						protonum = i;//项目协议号
					}else if("linkman".equals(paraNames[i])){
						linkman = i;//联系人
					}else if("linkphone".equals(paraNames[i])){
						linkphone = i;//联系电话
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
						log.debug("长度  ："+param.length);
						if(!"*123+321*".equals(param[param.length-1])){
							param[param.length-1]=param[param.length-1].substring(0,param[param.length-1].indexOf("*123+321*"));
						}else{
							param[param.length-1]="";
						}
						
						log.debug("读取文件  ："+line);						
						AppApi00802Result appApi00802Result = new AppApi00802Result();					
						List<TitleInfoBean> TitleInfoBeanContent = new ArrayList<TitleInfoBean>();
						List<TitleInfoBean> TitleInfoBeanList = new ArrayList<TitleInfoBean>();				
						Mi203 mi203 = new Mi203();						
						mi203.setTel(param[linkphone].isEmpty()?"--":"tel://"+param[linkphone]);//电话
						mi203.setContacterName(param[linkman].isEmpty()?"--":param[linkman]);//联系人
						mi203.setAddress(param[housesit].isEmpty()?"--":"map://"+param[housesit]);//地址
						mi203.setHouseName(param[projectname].isEmpty()?"--":param[projectname]);//楼盘名
						mi203.setDeveloperName(param[cocustname].isEmpty()?"--":param[cocustname]);//开发商名
						mi203.setFreeuse1(param[cocustname].isEmpty()?"--":"num://"+param[protonum]);
						AppApi00801Result01 appApi00801Result01 = new AppApi00801Result01();
						appApi00801Result01.setX(param[protonum]);//项目号
						appApi00801Result01.setY(param[protonum]);//项目号						
						appApi00801Result01.setImg(param[protonum]);//项目号
						appApi00802Result.setMap(appApi00801Result01);
						TitleInfoBeanContent = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".content", mi203);													
						appApi00802Result.setContent(TitleInfoBeanContent);
						TitleInfoBeanList = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".list", mi203);
						appApi00802Result.setList(TitleInfoBeanList);
						resultList.add(appApi00802Result);
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
		log.info(Constants.LOG_HEAD+"appApi00802 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle00802_00085200 hand = new Handle00802_00085200();
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
