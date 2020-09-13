package com.yondervision.yfmap.handle.handleImpl.handle00057400;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi030Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80006ZHResult;
import com.yondervision.yfmap.result.ningbo.AppApi80008ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Handle80008_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
	//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi030Form form = (AppApi030Form)form1;
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi80008ZHResult app80008ZHResult = new AppApi80008ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("hhmmss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));

			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());			
			form.setTellCode("stgy");
			form.setTellCode("1994");
			form.setBrcCode("05740008");
			form.setFlag(Channel.getChannel(form.getChannel()));
			
			HashMap map = BeanUtil.transBean2Map(form);		
			map.put("tellcode", "stgy");

			log.debug("map.toString()===="+map);
		    String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80008_111519.txt", map, req);
			log.debug("前置YFMAP发往中心报文："+xml);			
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111519");
			log.debug("前置YFMAP接收中心报文——贷款信息查询接口："+rexml);
		    HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_80008_111519.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app80008ZHResult);
			log.debug("MAP封装成BEAN："+app80008ZHResult);
			/*00001496999999RS0 jb9999991
			<accname>王琰慧</><accnum>0237724982</><begdate></><certinum>330682199408031287</><consum>17</><enddate></><indiaccstate>正常</>
			<indiprop>0.080000</><instname>市本级</><opnaccdate>2017-08-16</>
			<stdata>1|2019-01-22|汇缴[201901]至[201901]|201901|720.00|13012.07&&2|2019-02-22|汇缴[201902]至[201902]|201902|720.00|13732.07&&3|2019-03-21|汇缴[201903]至[201903]|201903|720.00|14452.07&&4|2019-04-22|汇缴[201904]至[201904]|201904|720.00|15172.07&&5|2019-05-21|汇缴[201905]至[201905]|201905|720.00|15892.07&&6|2019-06-20|汇缴[201906]至[201906]|201906|720.00|16612.07&&7|2019-06-30|结息|189912|184.82|16796.89&&8|2019-07-06|一般提取: [王琰慧] 1.00||1.00|16794.89&&9|2019-07-06|一般提取: [王琰慧] 1.00||1.00|16795.89&&10|2019-07-23|汇缴[201907]至[201907]|201907|742.00|17536.89&&11|2019-08-21|汇缴[201908]至[201908]|201908|742.00|18278.89&&12|2019-09-23|汇缴[201909]至[201909]|201909|742.00|19020.89&&</><stdata1>13|2019-10-15|一般提取: [王琰慧] 1.00||1.00|19019.89&&14|2019-10-23|汇缴[201910]至[201910]|201910|742.00|19761.89&&15|2019-10-25|一般提取: [王琰慧] 300.00||300.00|19461.89&&16|2019-11-25|汇缴[201911]至[201911]|201911|742.00|20203.89&&17|2019-12-20|汇缴[201912]至[201912]|201912|742.00|20945.89&&</>
			<stdata2></><stdata3></><stdata4></><unitaccname>宁波易才人力资源咨询有限公司</><unitaccnum>010100002496</><unitprop>0.080000</>
			<JobDate>2020-01-06</><CenterId>00000000</><PersonId>Y01543</><AuthId></><ClientIP>10.33.12.12</><RspCode>000000</><RspMsg>处理成功...</>
			<NoteMsg></><JobSeq>549515</>],长度[1554]
			 */
			String stdata0 = app80008ZHResult.getStdata();
			String stdata1 = app80008ZHResult.getStdata1();
			String stdata2 = app80008ZHResult.getStdata2();
			String stdata3 = app80008ZHResult.getStdata3();
			String stdata4 = app80008ZHResult.getStdata4();
			String stdata = stdata0 + stdata1 + stdata2 + stdata3 + stdata4;
			//去掉stdata最后一个逗号
			stdata = stdata.substring(0, stdata.length() - 1);

			SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd");
			String senddate = formatter3.format(date);
			DecimalFormat df = new DecimalFormat("0%");// ##.00% 百分比格式，后面不足2位的用0补齐
			double unitprop = Double.valueOf(app80008ZHResult.getUnitprop());
			double indiprop =  Double.valueOf(app80008ZHResult.getIndiprop());
			log.info("df.format(unitprop)=========="+df.format(unitprop));
			log.info("df.format(indiprop)=========="+df.format(indiprop));
			log.info("senddate=========="+senddate);
			log.info("app80008ZHResult.getRecode()=========="+app80008ZHResult.getRecode());
			log.info("stdata=========="+stdata);
			//[{"banji":"一年一班","renshu":"36",kaoshi:"期末","table1":["001~米小圈~98~100","002~米大圈~90~91"]}]
			if ("0".equals(app80008ZHResult.getRecode())){
				String voucherData = "[{" +
						"\"unitaccnum\":\"" +app80008ZHResult.getUnitaccnum() + "\"," +
						"\"unitaccname\":\"" +app80008ZHResult.getUnitaccname() + "\"," +
						"\"accnum\":\"" +app80008ZHResult.getAccnum() + "\"," +
						"\"accname\":\"" +app80008ZHResult.getAccname() + "\"," +
						"\"certinum\":\"" +app80008ZHResult.getCertinum() + "\"," +
						"\"instname\":\"" +app80008ZHResult.getInstname() + "\"," +
						"\"indiaccstate\":\"" +app80008ZHResult.getIndiaccstate() + "\"," +
						"\"unitprop\":\"" +df.format(unitprop) + "\"," +
						"\"indiprop\":\"" +df.format(indiprop) + "\"," +
						"\"opnaccdate\":\"" +app80008ZHResult.getOpnaccdate() + "\"," +
						"\"begdate\":\"" +app80008ZHResult.getBegdate() + "\"," +
						"\"enddate\":\"" +app80008ZHResult.getEnddate() + "\"," +
						"\"senddate\":\"" +senddate + "\"," +
						"\"table1\":["+stdata+"]}]";
				log.info("voucherData=========="+voucherData);
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("voucherData", voucherData);
				return modelMap;
			}else {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", app80008ZHResult.getMsg());
				return modelMap;
			}
			/*if(!"0".equals(app80008ZHResult.getRecode())){
				if(!CommonUtil.isEmpty(app80008ZHResult.getMsg())){
					if("/".equals(app80008ZHResult.getMsg().substring(0,1))){
						FtpUtil f=new FtpUtil("bsp");
						f.getFileByServerForNB(app80008ZHResult.getMsg());
						File file = new File(ReadProperties.getString("bsp_local_path")+"/"+app80008ZHResult.getMsg());
						FileInputStream ffis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(ffis, "GBK");//
						BufferedReader breader = new BufferedReader(isr);
						String line1 = breader.readLine(); 
						JSONArray result = new JSONArray();
						while (line1 != null) {// 判断读取到的字符串是否不为空
							JSONObject obj = new JSONObject();
							log.debug("读取文件  ："+line1);
							String[] trs = line1.split("\\|");
							StringBuilder sb = new StringBuilder();
							for (int i = 0; i < trs.length; i++) {
								if (i == 0) {
									obj.put("errCode", trs[i].trim());
									continue;
								} 
								sb.append(trs[i].trim()+",");
							}
							String msg = sb.toString();
							msg = msg.substring(0, msg.length()-1);
							obj.put("msg", "错误信息："+msg);
							result.add(obj);
							line1 = breader.readLine();
						}
						breader.close();
						isr.close();
						ffis.close();
						file.delete();
						modelMap.clear();
						modelMap.put("recode", app80008ZHResult.getRecode());
						modelMap.put("msg", result);
						log.error("中心返回报文 状态recode :"+app80008ZHResult.getRecode()+"  ,  描述msg : "+result.toString());
					}else{
						modelMap.clear();
						modelMap.put("recode", app80008ZHResult.getRecode());
						modelMap.put("msg", "错误信息："+app80008ZHResult.getMsg());
						log.error("中心返回报文 状态recode :"+app80008ZHResult.getRecode()+"  ,  描述msg : "+app80008ZHResult.getMsg());
					}					
					return modelMap;
				}else{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查无数据");
					return modelMap;
				}
			}*/
		}

		modelMap.clear();
		modelMap.put("recode", "999999");
		modelMap.put("msg", app80008ZHResult.getMsg());
		return modelMap;
	}
}
