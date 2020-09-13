package com.yondervision.yfmap.handle.handleImpl.handle00031500;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi00914Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00802_1Result;
import com.yondervision.yfmap.result.jinan.JiNanAppApi00802_2Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00802_1Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00802_2Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00804_1Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00804_2Result;
import com.yondervision.yfmap.result.tangshan.TangShanAppApi00914Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle00914_00031500 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00914Form form = (AppApi00914Form)form1;
		log.debug("YFMAP 贷款额度试算 form 参数:"+form);		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		TangShanAppApi00914Result app00802Result = new TangShanAppApi00914Result();
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_DKEDSS.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_KDLPCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00021");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>76</msgtype><tr_code>X00006</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>5</zjls><zys>1</zys><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>济南德润房地产开发有限公司</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>K5,K6,K11</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>6号楼,8号楼,11号楼,13号楼,14号楼,15号楼,16号楼</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>1（3-25层）,5（3-25层、1-101、1-102、1-103、1-104、1-201、1-202、1-203、</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>1（3-25层）,5（3-25层、1-101、1-102、1-103、1-104、1-201、1-202、1-203、,3号楼,4号楼</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail></body></root>";
			
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_DKEDSS.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,"C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_KDLPCX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}	
			
		}
		app00802Result.setRepaymonth(String.format("%.2f",Double.valueOf(app00802Result.getRepaymonth().trim()))+"元");
		app00802Result.setLoansum(String.format("%.2f",Double.valueOf(app00802Result.getLoansum().trim()))+"元");
		app00802Result.setHknl(String.format("%.2f",Double.valueOf(app00802Result.getHknl().trim()))+"元");
		//List<TitleInfoBean> appapi00802result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00914"+form.getCenterId()+".result", app00802Result);			
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("hknl", app00802Result.getHknl());
		modelMap.put("dkbj", app00802Result.getLoansum());
		modelMap.put("yhke", app00802Result.getRepaymonth());
//		JsonUtil.getGson().toJson(result);
//		System.out.println(JsonUtil.getGson().toJson(modelMap));
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
		Handle00914_00031500 hand = new Handle00914_00031500();
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
