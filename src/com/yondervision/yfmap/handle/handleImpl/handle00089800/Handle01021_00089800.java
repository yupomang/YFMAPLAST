package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01021Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01021Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 业务办理  提取 通用-计算提取金额-海口
 */
public class Handle01021_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01021Form form = (AppApi01021Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01021 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("appApi01021 form:"+form); 
		/* 模拟返回开始  */
		AppApi01021Result app01021Result= new AppApi01021Result();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		if(!CommonUtil.isEmpty(form.getRentdate()))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();//获取当前时间    
			Calendar calendar = Calendar.getInstance();    
			calendar.setTime(date);    
			calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
			//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
			Date d = calendar.getTime();//获取一年前的时间，或者一个月前的时间    
			long nowtime = sdf.parse(sdf.format(date)).getTime();
			long lastyeartime = sdf.parse(sdf.format(d)).getTime();
			long rentdatetime = sdf.parse(form.getRentdate()).getTime();
			if(rentdatetime<lastyeartime)
			{
				modelMap.put("recode", "999999");
				modelMap.put("msg", "租房时间距当前日期不能大于一年");
				log.error("中心返回报文 状态recode :999999"+"  ,  描述msg : 租房时间距当前日期不能大于一年");
				return modelMap;
			}
			if(rentdatetime>nowtime)
			{
				modelMap.put("recode", "999999");
				modelMap.put("msg", "租房时间距当前日期不能大于一年");
				log.error("中心返回报文 状态recode :999999"+"  ,  描述msg : 租房时间距当前日期不能大于一年");
				return modelMap;
			}
		}
		String drawreasoncode1 =form.getDrawreasoncode1();
		if(Constants.method_BSP.equals(send)){
			if(!CommonUtil.isEmpty(form.getDrawreasoncode2()))
			{
				form.setDrawreasoncode1(form.getDrawreasoncode2());
			}
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());			
			HashMap<String,String> map = BeanUtil.transBean2Map(form);
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			/* 第一次通信  查询个人账号*/
			map.put("stepseqno", "1");
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLTQXXCX.txt", map, req);
			log.debug("发往中心报文-查询个人账号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158030");
			log.debug("中心返回报文-查询个人账号："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLTQXXCX.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap1.get("recode")+"  ,  描述msg : "+resultMap1.get("msg"));
				return modelMap;
			}
			if(CommonUtil.isEmpty(resultMap1.get("grzh"))){	
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "查询1，交易码【158030】，查询个人账号为空。");
				log.error("查询1，交易码【158030】，查询个人账号为空。");
				return modelMap;
			}
			map.put("grzh", resultMap1.get("grzh").toString());
			
			if(!CommonUtil.isEmpty(form.getDrawreasoncode1()))
			{
				//2017-07-24 核心接口修改 精确个人查询信息
				map.put("summarycode", "1228");
				map.put("grzh", "");
				/* 第3次通信   个人信息查询*/
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLGRXXCX.txt", map, req);
				log.debug("发往中心报文-个人信息查询："+xml);
				
				// 通信
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119002");
				log.debug("中心返回报文-个人信息查询："+rexml);
				
				HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLGRXXCX.txt", rexml, req);
				if(!"0".equals(resultMap4.get("recode"))){				
					modelMap.clear();
					modelMap.put("recode", resultMap4.get("recode"));
					modelMap.put("msg", resultMap4.get("msg"));
					log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
					return modelMap;
				}
				map.put("grzh", resultMap1.get("grzh").toString());
				if(CommonUtil.isEmpty(resultMap4.get("dwzh"))){	
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查询3，交易码【119002】，查询单位账号为空。");
					log.error("查询3，交易码【119002】，查询单位账号为空。");
					return modelMap;
				} 
				//单位账号
				map.put("dwzh", resultMap4.get("dwzh").toString());
				
				/* 第4次通信   获取多合同标识*/
				xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQDHTBS.txt", map, req);
				log.debug("发往中心报文-获取多合同标识："+xml);
				
				// 通信
				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158000");
				log.debug("中心返回报文-获取多合同标识："+rexml);
				
				HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQDHTBS.txt", rexml, req);
				if(!"0".equals(resultMap5.get("recode"))){				
					modelMap.clear();
					modelMap.put("recode", resultMap5.get("recode"));
					modelMap.put("msg", resultMap5.get("msg"));
					log.error("中心返回报文 状态recode :" + resultMap5.get("recode")+"  ,  描述msg : " + resultMap5.get("msg"));
					return modelMap;
				}
				
				
				//多合同标识
				map.put("procode", resultMap5.get("procode").toString());
				if(!CommonUtil.isEmpty(resultMap5.get("procode")))
				{
					if(!CommonUtil.isEmpty(drawreasoncode1))
					{
						map.put("firstflag", "1");
					}else
					{
						map.put("firstflag", "2");
					}
				}
			}
			
			if("002".equals(form.getDrawreason()))
			{
				map.put("buyhousetype", form.getCtlflag());
			}else if("006".equals(form.getDrawreason()))
			{
				map.put("buyhousetype", "1");
			}else if("009".equals(form.getDrawreason()))
			{
				map.put("buyhousedate", form.getBuyhousedate1());
			}else if("004".equals(form.getDrawreason()))
			{
				map.put("drawreasoncode1",form.getBodyCardNumber()+form.getBilldate());
			}else if("011".equals(form.getDrawreason()))
			{
				map.put("drawreasoncode1",form.getBodyCardNumber()+form.getBilldate());
			}
			if("07".equals(form.getTqyy()))
			{
				map.put("buyhousetype","1");
			}else if("06".equals(form.getTqyy()))
			{
				map.put("buyhousetype","0");
			}
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YWBLJSTQJE.txt", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "158001");
			
			log.debug("中心下传报文："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLJSTQJE.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);			
			BeanUtil.transMap2Bean(resultMap, app01021Result);
			
			
			if(!"0".equals(app01021Result.getRecode())){
				modelMap.put("recode", app01021Result.getRecode());
				modelMap.put("msg", app01021Result.getMsg());
				log.error("中心返回报文 状态recode :"+app01021Result.getRecode()+"  ,  描述msg : "+app01021Result.getMsg());
				return modelMap;
			}
		}		
		
//		List<TitleInfoBean> appapi01021Result = null;
//		appapi01021Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01021"+form.getCenterId()+".result", app01021Result);
//		Iterator<TitleInfoBean> it1 = appapi01021Result.iterator();
//		while (it1.hasNext()) {
//			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
//			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
//		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("drawamt", app01021Result.getDrawamt());
		//modelMap.put("result", appapi01021Result);
		log.info(Constants.LOG_HEAD+"appApi01021 end.");
		return modelMap;
	}

	public static void main(String[] args) throws ParseException {
		String datestring = "2017-05-18";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();//获取当前时间    
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		//calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		Date d = calendar.getTime();//获取一年前的时间，或者一个月前的时间    
		long nowtime = sdf.parse(sdf.format(date)).getTime();
		long lastyeartime = sdf.parse(sdf.format(d)).getTime();
		long rentdatetime = sdf.parse(datestring).getTime();
		if(rentdatetime<lastyeartime)
		{
			System.out.println("1::::::::"+rentdatetime);
			System.out.println("1::::::::"+lastyeartime);
		}
		if(rentdatetime>nowtime)
		{
			System.out.println("2::::::::"+rentdatetime);
			System.out.println("2::::::::"+nowtime);
		}
		System.out.println("3::::::::"+rentdatetime);
		System.out.println("3::::::::"+lastyeartime);
		System.out.println("3::::::::"+nowtime);
	}
}
