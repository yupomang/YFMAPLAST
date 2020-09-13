package com.yondervision.yfmap.handle.handleImpl.handle00031400;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi00802_1Result;
import com.yondervision.yfmap.result.chengde.ChengDeAppApi00802_2Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 楼盘信息查询
 */
public class Handle00802_00031400 implements CtrlHandleInter {
	
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings("unchecked")
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi00802Form form = (AppApi00802Form)form1;
		log.debug("YFMAP 可贷楼盘查询 form 参数:"+form);		
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		if(CommonUtil.isEmpty(form.getPagenum())){
			form.setPagenum("0");
		}
		if(CommonUtil.isEmpty(form.getPagerows())){
			form.setPagerows("10");
		}
		System.out.println("appapi00802=====form.getSelectValue()==before=="+form.getSelectValue());
		if("10".equals(form.getChannel())){
			form.setSelectValue(new String(form.getSelectValue().getBytes("iso8859-1"),"UTF-8"));
		}
		System.out.println("appapi00802=====form.getSelectValue()==after=="+form.getSelectValue());
		
		form.setPagenum(String.valueOf((Integer.valueOf(form.getPagenum())+1)));
		ChengDeAppApi00802_1Result app00802Result=new ChengDeAppApi00802_1Result(); // 表头
		List<ChengDeAppApi00802_2Result> list = new ArrayList<ChengDeAppApi00802_2Result>();
		System.out.println("YFMAP 可贷楼盘查询 楼盘名称 参数:"+form.getSelectValue());
		if(Constants.method_XML.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/REQ_LPCX.xml", map, req);
//			String xml = MessageCtrMain.encapsulatedPackets(msgType, "C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REQ_KDLPCX.xml", map, req);
			log.debug("发往中心报文："+xml);
			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "FSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "X00007");
//			String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype>76</msgtype><tr_code>X00006</tr_code><corp_no>00053100</corp_no><oreq_no>8020150929007169</oreq_no><tr_acdt>2015-09-29</tr_acdt><tr_time>10:25:15</tr_time><ans_code>000000</ans_code><ans_info>交易成功完成</ans_info><particular_code>000000</particular_code><particular_info>交易成功完成</particular_info><reserved></reserved></head><body><dqys>1</dqys><dqysjls>10</dqysjls><zjls>5</zjls><zys>1</zys><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>济南德润房地产开发有限公司</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>K5,K6,K11</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>6号楼,8号楼,11号楼,13号楼,14号楼,15号楼,16号楼</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>1（3-25层）,5（3-25层、1-101、1-102、1-103、1-104、1-201、1-202、1-203、</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail><detail><lpmc>德润天玺B1地块</lpmc><wdmc>兴业银行分行个贷中心</wdmc><kfdw>1（3-25层）,5（3-25层、1-101、1-102、1-103、1-104、1-201、1-202、1-203、,3号楼,4号楼</kfdw><pzzh>2（商业除外）,4,7,9（商业除外）</pzzh></detail></body></root>";
			
			
			
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"REP_LPCX.xml", rexml, req);
//			HashMap resultMap = MessageCtrMain.analysisPacket(msgType,"C:/Program Files/Tomcat 6.0/webapps/YFMAP/WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/00053100/REP_KDLPCX.xml", rexml, req);
			BeanUtil.transMap2Bean(resultMap, app00802Result);
			if(!Constants.sucess_ts.equals(app00802Result.getRecode())){				
				modelMap.clear();
				modelMap.put("recode", app00802Result.getRecode());
				modelMap.put("msg", app00802Result.getMsg());
				log.error("中心返回报文 状态recode :"+app00802Result.getRecode()+"  ,  描述msg : "+app00802Result.getMsg());
				return modelMap;
			}	
			
			String filename = (String)resultMap.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			System.out.println("查询楼盘信息，批量文件："+filename);
			if(filename!=null){
				File filemx = new File(filename);//File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+fname);
				FileInputStream ffis = new FileInputStream(filemx);
				InputStreamReader isr = new InputStreamReader(ffis, "GBK");
				BufferedReader breader = new BufferedReader(isr);
				String line = breader.readLine();
				while (line != null) {// 判断读取到的字符串是否不为空
					ChengDeAppApi00802_2Result aa00802Result = new ChengDeAppApi00802_2Result();
					try {
						String[] valus = line.split("\\@\\|\\$");
						log.debug("读取文件  ："+line);
						if(valus.length>0)aa00802Result.setLpmc(valus[0]);
						if(valus.length>1)aa00802Result.setKfsmc(valus[1]);
						if(valus.length>2)aa00802Result.setLpdz(valus[2]);
						if(valus.length>3)aa00802Result.setLxr(valus[3]);
						if(valus.length>3)aa00802Result.setLxdh(valus[4]);
						if(valus.length>3)aa00802Result.setKdld(valus[5]);
						list.add(aa00802Result);
						line = breader.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				breader.close();
				isr.close();
				ffis.close();
				filemx.delete();
			}else{
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "楼盘查询信息处理异常");
				return modelMap;
			}			
			
		}
		
		
		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00802Detail = null;
		for(int i=0;i<list.size();i++){
			appapi00802Detail = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi00802"+form.getCenterId()+".detail", list.get(i));			
			result.add(appapi00802Detail);
		}	
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		System.out.println("total=="+app00802Result.getZjls());
		System.out.println("totalPage=="+app00802Result.getZys());
		System.out.println("currentPage=="+app00802Result.getDqys());
		System.out.println("currentPageTotal=="+app00802Result.getDqysjls());
		modelMap.put("total", app00802Result.getZjls());
		modelMap.put("totalPage", app00802Result.getZys());
		modelMap.put("currentPage", app00802Result.getDqys());
		modelMap.put("currentPageTotal",  app00802Result.getDqysjls());
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00031300");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle00802_00031400 hand = new Handle00802_00031400();
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
