package com.yondervision.yfmap.handle.handleImpl.handle00083800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.mengdian.AppApi00201Result;
import com.yondervision.yfmap.result.weihai.WeiHaiAppApi002Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle002_00083800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	private static final ResourceBundle ReadProperties;
	static {
		// 读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}

	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		/* 模拟返回开始 */
		log.info(Constants.LOG_HEAD + "appApi00201 start.");
		AppApi00201Form form = (AppApi00201Form) form1;
		List<WeiHaiAppApi002Result> list = new ArrayList<WeiHaiAppApi002Result>();
		String PROPERTIES_FILE_NAME = "properties.properties";
		log.debug("form:" + form);
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
				"appapi002Send" + form.getCenterId()).trim();

		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		form.setSendDate(formatter1.format(date));
		form.setSendTime(formatter2.format(date));
		String fname = formatter2.format(date) + "_" + form.getSurplusAccount()
				+ "_" + "api40101GJJMX.txt";

		AppApi00201Result app002Result = new AppApi00201Result();
		if (Constants.method_BSP.equals(send)) {// //xml通信处理
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"appapi002MsgType" + form.getCenterId()).trim();
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"appapi002Key" + form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId() + CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"appapi002Type" + form.getCenterId()).trim());
			HashMap map = new HashMap();
			map = BeanUtil.transBean2Map(form);
			String xml = MessageCtrMain.encapsulatedPackets(
					msgType,
					CommonUtil.getFullURL(Constants.msgPath
							+ form.getCenterId())
							+ "/REQ_YEMXCX.txt", map, req);
			log.debug("YFMAP发往中心报文：" + xml);

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"BSP_SOCKET_IP" + form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"BSP_SOCKET_PORT" + form.getCenterId()).trim();
			String reqxml = CommunicateUtil.sendMessage(send, ip,
					Integer.parseInt(port), xml, "430005");

			// String reqxml =
			// "<AuthCode1>IVR0</><AuthCode2>IVR0</><AuthCode3>IVR0</><AuthFlag>0</><BrcCode>00000000</><BusiSeq>90</><ChannelSeq>398243</><ChkCode>cweb</><MTimeStamp>2013-07-24 09:55:28:967000</><NoteMsg></><RspCode>000000</><RspMsg>成功</><STimeStamp>2015-07-24 09:55:28:966000</><TellCode>cweb</><TranChannel>00</><TranCode>116005</><TranDate>2016-04-06</><TranIP>10.22.21.26</><TranSeq>90</><fileName>YEMXCX.txt</><accnum></><accname></><sumcounts></><counts></><pageflag>1</>";

			log.debug("中心返回YFMAP报文：" + reqxml);

			HashMap resultMap = MessageCtrMain.analysisPacket(
					msgType,
					CommonUtil.getFullURL(Constants.msgPath
							+ form.getCenterId())
							+ "/REP_YEMXCX.txt", reqxml, req);
			log.debug("解析报文MAP：" + resultMap);
			BeanUtil.transMap2Bean(resultMap, app002Result);
			log.debug("MAP封装成BEAN：" + app002Result);
			if (!"0".equals(app002Result.getRecode())) {
				modelMap.put("recode", app002Result.getRecode());
				modelMap.put("msg", app002Result.getMsg());
				log.error("中心返回报文 状态recode :" + app002Result.getRecode()
						+ "  ,  描述msg : " + app002Result.getMsg());
				return modelMap;
			}
			String filename = "GRMX_" + form.getCertinum() + "_"
					+ formatter1.format(System.currentTimeMillis());
			app002Result.setFileName(filename);
			log.debug("远程文件名filaneme=====" + filename);
			FtpUtil f = new FtpUtil("g1");
			f.getFileByServer("/" + app002Result.getFileName());
			File file = new File(ReadProperties.getString("g1_local_path")
					+ app002Result.getFileName());
			FileInputStream ffis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(ffis, "GBK");
			BufferedReader breader = new BufferedReader(isr);
			String line = breader.readLine();
			int i = 0;
			while (line != null) {
				WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();
				try {
					if (i != 0) {
						
						String[] param = line.split("\\|");
						log.debug("读取文件  ：" + line);
						if (formatter1.parse(param[0]).after(formatter1.parse(form.getStartdate()))&&
								formatter1.parse(param[0]).before(formatter1.parse(form.getEnddate()))){
						aa00201Result.setTransdate(param[0]);
						aa00201Result.setSummary(param[1]);
						aa00201Result.setBusiamt1(param[2]);
						aa00201Result.setBalance(param[3]);
						aa00201Result.setPaybegindate(param[4]);
						list.add(aa00201Result);
						}
					
					}
					line = breader.readLine();
					i++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			breader.close();
			isr.close();
			ffis.close();
//			file.delete();
		}

		List<List<TitleInfoBean>> result = new ArrayList();
		List<TitleInfoBean> appapi00101Result = null;
		for (int i = 0; i < list.size(); i++) {
			appapi00101Result = JavaBeanUtil
					.javaBeanToListTitleInfoBean(
							"appapi00201" + form.getCenterId() + ".result",
							list.get(i));
			result.add(appapi00101Result);
		}

		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", result);
		log.info(Constants.LOG_HEAD + "appApi00201 end.");
		return modelMap;

	}
	public static void main(String[] args) throws IOException, ParseException {
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		List<WeiHaiAppApi002Result> list = new ArrayList<WeiHaiAppApi002Result>();
		File file = new File("C:\\a");
		FileInputStream ffis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(ffis, "GBK");
		BufferedReader breader = new BufferedReader(isr);
		String line = breader.readLine();
		int i = 0;
		while (line != null) {
			WeiHaiAppApi002Result aa00201Result = new WeiHaiAppApi002Result();
			try {
				if (i != 0) {
					String[] param = line.split("\\|");
					if (formatter1.parse(param[0]).after(formatter1.parse("2013-05-01"))&&
							formatter1.parse(param[0]).before(formatter1.parse("2015-05-01"))){
					aa00201Result.setTransdate(param[0]);
					aa00201Result.setSummary(param[1]);
					aa00201Result.setBusiamt1(param[2]);
					aa00201Result.setBalance(param[3]);
					aa00201Result.setPaybegindate(param[4]);
					list.add(aa00201Result);
					}
				
				}
				line = breader.readLine();
				i++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		breader.close();
		isr.close();
		ffis.close();
	}

}
