package com.yondervision.yfmap.handle.handleImpl.handle00045101;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.handle.handleImpl.handle00041100.Handle001_00041100;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi40101Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: Handle001_00045101 
* @Description: 哈尔滨农垦——公积金查询
* @author Caozhongyan
* @date Dec 10, 2015 11:39:20 PM   
* 
*/ 
public class Handle001_00045101 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00101Form form = (AppApi00101Form)form1;
		log.debug("form:"+form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		WeiHaiAppApi40101Result app40101Result = new WeiHaiAppApi40101Result();
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);		
//			String xml = MessageCtrMain.encapsulatedPackets(msgType,"E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REQ_YECX.txt", map, req);// CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())
			String xml = MessageCtrMain.encapsulatedPackets(msgType,CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REQ_YECX.txt", map, req); 
			log.debug("YFMAP发往中心BSP报文："+xml);			
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP_"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT_"+form.getCenterId()).trim();
			
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "112107");
//			String rexml = "<AuthCode1>0000</><AuthCode2>0000</><AuthCode3>0000</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>2643997</><ChannelSeq>398243</><ChkCode>0000</><MTimeStamp>10:55:08</><NoteMsg></><RspCode>000000</><RspMsg>交易处理成功...</><STimeStamp>10:55:08</><TellCode>0000</><TranChannel>00</><TranCode>112107</><TranDate>2015-12-16</><TranIP>10.22.21.26</><TranSeq>2643997</><accname>赵永乐</><accnum>P08070000258</><bal>12323.63</><basenum>1500.00</><certinum>230622198201038110</><indiprop>12.000</><loanstatedes>正常</><lpaym>201509</><monpaysum>360.00</><opnaccdate>2010-04-26</><times>0</><unitaccname>黑龙江省肇源农场住房公积金管理中心</><unitaccnum>U0080700</><unitprop>12.000</><ydrawamt>0.00</>";
			
			log.debug("BSP返回中心YFMAP报文："+rexml);
			
//			
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "E:/peixun/YFMAP/WebRoot/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00045101"+"/BSP_REP_YECX.txt", rexml, req);//CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form1.getCenterId())+"/BSP_REP_YECX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app40101Result);
			log.debug("MAP封装成BEAN："+app40101Result);
			if(!"0".equals(app40101Result.getRecode())){
				modelMap.put("recode", app40101Result.getRecode());
				modelMap.put("msg", app40101Result.getMsg());
				log.error("中心返回报文 状态recode :"+app40101Result.getRecode()+"  ,  描述msg : "+app40101Result.getMsg());
				return modelMap;
			}
			app40101Result.setIncrebal(String.format("%,.2f",Double.valueOf(app40101Result.getIndiprop())+Double.valueOf(app40101Result.getUnitprop()))+"%");
			log.debug("缴存比例合值:"+app40101Result.getUnitprop());
			app40101Result.setIndiprop(String.format("%,.2f",Double.valueOf(app40101Result.getIndiprop()))+"%");
			app40101Result.setUnitprop(String.format("%,.2f",Double.valueOf(app40101Result.getUnitprop()))+"%");
			log.debug("个人缴存比例合值:"+app40101Result.getIndiprop());
			log.debug("单位缴存比例合值:"+app40101Result.getUnitprop()); 
			 
			app40101Result.setBasenumber(String.format("%,.2f",Double.valueOf(app40101Result.getBasenumber())));
			app40101Result.setMonthpayamt(String.format("%,.2f",Double.valueOf(app40101Result.getMonthpayamt())));			
			app40101Result.setBalance(String.format("%,.2f",Double.valueOf(app40101Result.getBalance())));
			app40101Result.setYdrawamt(String.format("%,.2f",Double.valueOf(app40101Result.getYdrawamt())));			
		}
		List<TitleInfoBean> appapi40101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".result", app40101Result);
		List<TitleInfoBean> appapi40101Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00101"+form.getCenterId()+".detail", app40101Result);
		Iterator<TitleInfoBean> it1 = appapi40101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		Iterator<TitleInfoBean> it2 = appapi40101Detail.iterator();
		while (it2.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it2.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}		
		
		JSONObject jsonObj = new JSONObject(); 
		jsonObj.put("recode", "000000");
		jsonObj.put("msg", "成功");
		jsonObj.put("result", appapi40101Result);
		jsonObj.put("detail", appapi40101Detail);
		log.debug(jsonObj);
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi40101Result);
		modelMap.put("detail", appapi40101Detail);
		
		return modelMap;
	}
	
	
//	public static void main(String[] args){
//		AppApi00101Form form1 = new AppApi00101Form();
//		ModelMap modelMap = new ModelMap();
//		form1.setSurplusAccount("P08070000258");
//		form1.setCenterId("00045101");
//		Handle001_00045101 hand = new Handle001_00045101();
//		try {
//			hand.action(form1, modelMap);
//		} catch (CenterRuntimeException e) {
//			// TODO Auto-generated catch block			
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

}
