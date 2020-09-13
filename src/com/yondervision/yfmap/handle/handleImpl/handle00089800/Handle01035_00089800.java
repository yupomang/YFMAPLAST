package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01028Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi01031Result;
import com.yondervision.yfmap.result.haikou.AppApi01032Result;
import com.yondervision.yfmap.result.haikou.AppApi01035Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 业务办理-还款业务-数据反显
 * @author Administrator
 *
 */
public class Handle01035_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01028Form form = (AppApi01028Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01035 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		AppApi01035Result app01035Result = new AppApi01035Result();
		
		//公积金列表
		List<AppApi01031Result> list = new ArrayList<AppApi01031Result>();
		//公积金还款信息
		List<AppApi01032Result> hkxx_list = new ArrayList<AppApi01032Result>();
		String seqno = "";
		double ahdpartrepaylow = 0.0;
		double ahdrepayallamt = 0.0;
		double owetotalamt = 0.0;
		double ahdrepayamt = 0.0;
		if(Constants.method_BSP.equals(send)){
			seqno = RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"";
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
			
			Map resultMap = new HashMap();
			
			
			/* 第1次通信   根据证件号码查询合同号*/
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文-根据证件号码查询合同号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			log.debug("中心返回报文-根据证件号码查询合同号："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHTH.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
				return modelMap;
			}
			
			if(CommonUtil.isEmpty(resultMap1.get("loanaccnum"))){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "无公积金贷款记录，无法办理此业务！");
				log.error("中心返回报文 状态recode :000001  ,  描述msg : 交易129124，未查询到借款代码。");
				return modelMap;
			}
			
			
			// 借款合同号，第一次通信后查询得到
			resultMap.put("loancontrcode", resultMap1.get("loanaccnum"));
			map.put("loancontrcode", resultMap1.get("loanaccnum"));
			
			
			map.put("instance", "-"+seqno);
			/* 第1次通信   插入临时表*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HKCRB.txt", map, req);
			log.debug("发往中心报文-插入临时表："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129199");
			log.debug("中心返回报文-插入临时表："+rexml);
			
			map.put("flag", "2");
			map.put("seqno", "0");
			/* 第1次通信   获取还款后最低保留余额*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQBLYE.txt", map, req);
			log.debug("发往中心报文-还款后最低保留余额："+xml);
			
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128060");
			log.debug("中心返回报文-获取还款后最低保留余额："+rexml);
			
			HashMap resultMap1_1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQBLYE.txt", rexml, req);
			if(!"0".equals(resultMap1_1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1_1.get("recode"));
				modelMap.put("msg", resultMap1_1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1_1.get("recode")+"  ,  描述msg : " + resultMap1_1.get("msg"));
				return modelMap;
			}
			resultMap.put("amt", resultMap1_1.get("amt"));
			
			
			/* 第2次通信   提前偿还贷款查询*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK129129.txt", map, req);
			log.debug("发往中心报文-提前偿还贷款查询："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129129");
			log.debug("中心返回报文-提前偿还贷款查询："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK129129.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
				return modelMap;
			}

			
			if("0".equals(resultMap2.get("count")))map.put("flag", "0");
			if("1".equals(resultMap2.get("count")))map.put("flag", "1");
			/* 第3次通信  合同代码取贷款基本信息*/
			//map.put("flag", "");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK124002.txt", map, req);
			log.debug("发往中心报文-合同代码取贷款基本信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "124002");
			log.debug("中心返回报文-合同代码取贷款基本信息："+rexml);
			
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK124002.txt", rexml, req);
			if(!"0".equals(resultMap3.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
				return modelMap;
			}
			
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK129176.txt", map, req);
			log.debug("发往中心报文-合同代码取贷款扣款账号："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129176");
			log.debug("中心返回报文-合同代码取贷款扣款账号："+rexml);
			
			HashMap resultMap3_1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK129176.txt", rexml, req);
			if(!"0".equals(resultMap3_1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3_1.get("recode"));
				modelMap.put("msg", resultMap3_1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3_1.get("recode")+"  ,  描述msg : " + resultMap3_1.get("msg"));
				return modelMap;
			}
//			如果oweprin>ahdpartrepaylow
//			{ 提前还款最低限额= oweprin + oweint + owepun + newint; }
//			否则
//			{ 提前还款最低限额 = ahdpartrepaylow + oweint + owepun + newint ;  }
			
			if(Double.parseDouble(resultMap3.get("oweprin").toString())>Double.parseDouble(resultMap3.get("ahdpartrepaylow").toString()))
			{
				ahdpartrepaylow = Double.parseDouble(resultMap3.get("oweprin").toString())
						+Double.parseDouble(resultMap3.get("oweint").toString())
						+Double.parseDouble(resultMap3.get("owepun").toString())
						+Double.parseDouble(resultMap3.get("newint").toString());
			}else
			{
				ahdpartrepaylow = Double.parseDouble(resultMap3.get("ahdpartrepaylow").toString())
						+Double.parseDouble(resultMap3.get("oweint").toString())
						+Double.parseDouble(resultMap3.get("owepun").toString())
						+Double.parseDouble(resultMap3.get("newint").toString());
			}
			//提前全部还款金额 = undueprin + newint + owetotalamt
			//owetotalamt = oweint + owepun + oweprin;
				ahdrepayallamt = Double.parseDouble(resultMap3.get("undueprin").toString())
						+Double.parseDouble(resultMap3.get("newint").toString())
						+Double.parseDouble(resultMap3.get("oweint").toString())
						+Double.parseDouble(resultMap3.get("owepun").toString())
						+Double.parseDouble(resultMap3.get("oweprin").toString());
				
				owetotalamt = Double.parseDouble(resultMap3.get("oweint").toString())
						+Double.parseDouble(resultMap3.get("owepun").toString())
						+Double.parseDouble(resultMap3.get("oweprin").toString());
				//应还未还总金额+新产生利息
				ahdrepayamt = owetotalamt+Double.parseDouble(resultMap3.get("newint").toString());
			map.put("bankcode", resultMap3.get("agentbankcode"));
			//贷款业务传2
			map.put("flag", "2");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REQ_YWBLYHXX.txt", map, req);
			log.debug("发往中心报文："+xml);
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "149369");

			log.debug("中心下传报文："+rexml);
			
			HashMap resultMap4_1 = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLYHXX.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap1);			
			
			
			if(!"0".equals(resultMap4_1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4_1.get("recode"));
				modelMap.put("msg", resultMap4_1.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap4_1.get("recode")+"  ,  描述msg : "+resultMap4_1.get("msg"));
				return modelMap; 
			}	
			//点击公积金按钮触发
			map.put("instance", "-"+seqno);
			/* 第4次通信  公积金还款人列表查询*/
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK129130.txt", map, req);
			log.debug("发往中心报文：公积金还款人列表查询"+xml);
			
			//通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129130");
			
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK129130.txt", rexml, req);
			if(!"0".equals(resultMap4.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文状态recode :"+resultMap4.get("recode")+"  ,  描述msg : "+resultMap4.get("msg"));
				return modelMap;
			}	
			
			String filename = (String)resultMap4.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
			if(!filename.equals("")){
				
			
				FtpUtil f=new FtpUtil("bsp"+form.getCenterId());
			    f.getFileByServer("/"+filename);
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
			    //========================================================================== 
			    File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
				
				
				System.out.println("还款业务-查询还款公积金列表："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					while (line != null) {// 判断读取到的字符串是否不为空
						AppApi01031Result app01031Result = new AppApi01031Result();
						try {
							String[] valus = line.split("~");
							log.debug("读取文件  ："+line);			
							// 带看完文件补充
							// 姓名、证件类型、证件号码、个人账号、单位名称、账户余额、转账金额、序号
							app01031Result.setAccname(valus[0]);
							app01031Result.setFreeuse1(PropertiesReader.getProperty("yingshe.properties","zjlx"+valus[1]+form.getCenterId()));
							app01031Result.setCertinum(valus[2]);
							//app01031Result.setAccnum(valus[3]);
							//app01031Result.setUnitaccname(valus[4]);
							app01031Result.setBuyhouseamt(valus[3]);
							app01031Result.setEvalvalue(valus[4]);
							app01031Result.setFreeuse4(valus[6]);
							list.add(app01031Result);
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
		
			
//		app00101Result.setAccountBalance(String.format("%,.2f",Double.valueOf(app00101Result.getAccountBalance())));
			/* 第5次通信  查询个人公积金还款信息列表*/
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_YWBLHK119186.txt", map, req);
//			log.debug("发往中心报文：公积金还款信息列表查询"+xml);
//			
//			//通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "119186");
//			
//			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_YWBLHK119186.txt", rexml, req);
//			if(!"0".equals(resultMap5.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap5.get("recode"));
//				modelMap.put("msg", resultMap5.get("msg"));
//				log.error("中心返回报文：状态recode :"+resultMap5.get("recode")+"  ,  描述msg : "+resultMap5.get("msg"));
//				return modelMap;
//			}	
//			
//			String gjjhkxx_filename = (String)resultMap5.get(MessageCtrMain.RECEIVE_FILE_NAME_KEY);
//			if(!gjjhkxx_filename.equals("")){
//				
//			
//				FtpUtil f=new FtpUtil("bsp"+form.getCenterId());
//			    f.getFileByServer("/"+gjjhkxx_filename);
//			    File filerename = new File(ReadProperties.getString("bsp_local_path")+gjjhkxx_filename);
//			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+gjjhkxx_filename));
//			    //========================================================================== 
//			    File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+gjjhkxx_filename);
//				
//				System.out.println("还款业务-公积金还款信息："+gjjhkxx_filename);
//				if(file!=null){
//					FileInputStream ffis = new FileInputStream(file);
//					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
//					BufferedReader breader = new BufferedReader(isr);
//					String line = breader.readLine();
//					while (line != null) {// 判断读取到的字符串是否不为空
//						AppApi01032Result app01032Result = new AppApi01032Result();
//						try {
//							String[] valus = line.split("~");
//							log.debug("读取文件  ："+line);			
//							// 带看完文件补充
//							// 2016-10-02~1539896~郑晓春~0.00~1600.00~77073.09~1219~201610~201610~
//							// 交易日期、个人账号、姓名、金额1、金额2、金额3、月日、年月、年月、原因、类型
////							private String transdate = ""; // 交易日期
//////							private String certinum = ""; // 证件号码
////							private String unitaccname = ""; // 姓名
////							private String amt1 = ""; //	金额1
////							private String amt2 = ""; //	金额2
////							private String payvouamt = ""; //	金额3
////							private String freeuse1 = ""; //	月 日
////							private String begym = ""; // 开始年月
////							private String endym = ""; // 结束年月
////							private String reason = ""; //	原因
////							private String dpbusitype = ""; // 类型
//							app01032Result.setTransdate(valus[0]);
//							app01032Result.setCertinum(valus[1]);
//							app01032Result.setUnitaccname(valus[2]);
//							app01032Result.setAmt1(valus[3]);
//							app01032Result.setAmt2(valus[4]);
//							app01032Result.setPayvouamt(valus[5]);
//							app01032Result.setFreeuse1(valus[6]);
//							app01032Result.setBegym(valus[7]);
//							app01032Result.setEndym(valus[8]);
//							app01032Result.setReason(valus[9]);
//							//app01032Result.setDpbusitype(valus[10]);
//							
//							hkxx_list.add(app01032Result);
//							line = breader.readLine();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					breader.close();
//					isr.close();
//					ffis.close();
//					file.delete();
//					filerename.delete();
//				}
//			}
			
			resultMap.putAll(resultMap1);
			resultMap.putAll(resultMap2);
			resultMap.putAll(resultMap3);
			resultMap.putAll(resultMap3_1);
			resultMap.put("dedbankcode", resultMap3.get("agentbankcode"));
			resultMap.put("agentbankcode", resultMap4_1.get("bankname"));
			BeanUtil.transMap2Bean(resultMap, app01035Result);
		}
		app01035Result.setDkhkfs(PropertiesReader.getProperty("yingshe.properties","dkhkfs"+app01035Result.getDkhkfs()+form.getCenterId()));
		//app01035Result.setDedbankcode(app01035Result.getAgentbankcode());
		DecimalFormat df = new DecimalFormat("#.##"); 
		app01035Result.setAhdrepaylowamt(df.format(ahdpartrepaylow));
		app01035Result.setAhdrepayallamt(df.format(ahdrepayallamt));
		app01035Result.setOwetotalamt(df.format(owetotalamt));
		app01035Result.setAhdrepayamt(df.format(ahdrepayamt));
		//公积金列表返回信息解析
		List<List<TitleInfoBean>> gjjDetail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<list.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01031"+form.getCenterId()+".result35", (AppApi01031Result)list.get(i));	
			gjjDetail.add(result1);}
		
		//公积金还款信息列表解析
//		List<List<TitleInfoBean>> hkxxDetail = new ArrayList<List<TitleInfoBean>>();			
//		for(int i=0;i<hkxx_list.size();i++){
//			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
//			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01032"+form.getCenterId()+".result", (AppApi01032Result)hkxx_list.get(i));	
//			hkxxDetail.add(result1);
//		}
		
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01035"+form.getCenterId()+".result", app01035Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		modelMap.put("ahdrepaylowamt", app01035Result.getAhdrepaylowamt());
		modelMap.put("ahdrepayallamt", app01035Result.getAhdrepayallamt());
		modelMap.put("dedbankcode", app01035Result.getDedbankcode());
		modelMap.put("dedbankname", app01035Result.getAgentbankcode());
		modelMap.put("oldrepayaccnum", app01035Result.getOldrepayaccnum());
		modelMap.put("owetotalamt", app01035Result.getOwetotalamt());
		modelMap.put("ahdrepayamt", app01035Result.getAhdrepayamt());
		modelMap.put("dedaccnum", app01035Result.getDedaccnum());
		modelMap.put("amt", app01035Result.getAmt());
		//公积金还款人列表
		modelMap.put("gjjlist", gjjDetail);
		//公积金还款信息
		//modelMap.put("hkxxlist", hkxx_list);
		modelMap.put("seqno", seqno);
		log.info(Constants.LOG_HEAD+"appApi01035 end.");
		
		return modelMap;
	}
	
}
