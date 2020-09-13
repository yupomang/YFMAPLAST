package com.yondervision.yfmap.handle.handleImpl.handle00077500;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.zhuzhou.AppApi50022ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle50022_00077500 implements CtrlHandleInter1 {
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		
		AppApi50001Form form = (AppApi50001Form)form1;
		log.debug("00077500请求50022参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Send"+form.getCenterId()).trim();
		AppApi50022ZHResult app50022ZHResult = new AppApi50022ZHResult();
		
		
		if(Constants.method_BSP.equals(send)){////xml通信处理
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Key"+form.getCenterId()).trim());			
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi401Type"+form.getCenterId()).trim());			
//			System.out.println(form.getBodyCardNumber()+"   "+form.getSendDate()+"   "+form.getSendTime());
			
			if(!form.getChannel().trim().equals("40"))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("00073199");
//				form.setTellCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001TellCode"+form.getCenterId()).trim());
//				form.setBrcCode(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001BrcCode"+form.getCenterId()).trim());
				form.setTranDate(form.getSendDate());												
			}
			form.setFlag(Channel.getZzChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_SFYLSJH.txt", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REQ_SFYLSJH.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119903");

//			String rexml ="<AuthCode1>1</><AuthCode2>1</><AuthCode3>1</><AuthFlag>1</><BrcCode>1</><BusiSeq>1</><ChannelSeq>1</><ChkCode>1</><FinancialDate>1</><HostBank>1</><MTimeStamp>1</><NoteMsg>1</><RspCode>000000</><RspMsg>OK</><STimeStamp>1</><SubBank>1</><TellCode>1</><TranChannel>1</>1<TranCode></><TranDate>1</><TranIP>1</><TranSeq>1</><isExist>0</><certinum>134534534</><accname>张晓红</><phone>12345678910</><sex>1###女</><custid>498534894</><accnum>4095809358</><loanaccnum>40580985098</><loancontrnum>0943438589</><wt_transdate>2020-02-20</><wt_instcode>2</><wt_op>43</><birthday>1990-1-1</>";
			
			log.debug("前置YFMAP接收中心报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_SFYLSJH.txt", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, "D:/java/apache-tomcat-7.0.8-windows-x64/apache-tomcat-7.0.8/webapps/ydmi_csp/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00077500/BSP_REP_SFYLSJH.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			
			BeanUtil.transMap2Bean(resultMap, app50022ZHResult);
			log.debug("MAP封装成BEAN："+app50022ZHResult);
			if(!"0".equals(app50022ZHResult.getRecode())){
				modelMap.clear();
				modelMap.put("recode", app50022ZHResult.getRecode());
				modelMap.put("msg", app50022ZHResult.getMsg());
				log.error("中心返回报文 状态recode :"+app50022ZHResult.getRecode()+"  ,  描述msg : "+app50022ZHResult.getMsg());
				return modelMap;
			}
		}
		//性别 1-男性 2-女性 0-未知性别 9-未说明的性别
		String  sex = null;
		if("1".equals(app50022ZHResult.getXingbie())){
			 sex = "0";
		}else if("2".equals(app50022ZHResult.getXingbie())){
			sex = "1";
		}else{
			 sex = "2";
		}
		//通过实名认证验证标识   1-通过    0 -未通过
		String perstate = null;
		if ("1".equals(app50022ZHResult.getSmrzbs())) {
			perstate = "2";   //高级用户
		} else {
			perstate = "1";   //普通用户
		}
		modelMap.clear();		
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");	
		modelMap.put("isExist", app50022ZHResult.getValidflag());//存在标志
		modelMap.put("certinum", app50022ZHResult.getZjhm());//证件号码
		modelMap.put("accname", app50022ZHResult.getXingming());//姓名
		modelMap.put("phone", app50022ZHResult.getSjhm());//手机号
//		modelMap.put("sex", app50022ZHResult.getXingbie());
		modelMap.put("sex", sex);//性别
		modelMap.put("custid", app50022ZHResult.getCustid());//个人客户号
		modelMap.put("accnum", app50022ZHResult.getGrzh());//个人账号
		modelMap.put("loanaccnum", app50022ZHResult.getDkzh());//贷款账号
		modelMap.put("loancontrnum", app50022ZHResult.getJkhtbh());//借款合同号
		modelMap.put("birthday", app50022ZHResult.getBirthday());//出生日期
		modelMap.put("unitaccnum", app50022ZHResult.getDwzh());//单位账号
//		modelMap.put("perstate", app50022ZHResult.getSmrzbs());
		modelMap.put("perstate", perstate);//认证状态
		return modelMap;
	}

}
