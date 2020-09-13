package com.yondervision.yfmap.handle.handleImpl.handle00057400;


import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.form.AppApi80007Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.ningbo.AppApi80007ZHResult;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.Channel;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class Handle80007_00057400 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	//属性文件
	private static final ResourceBundle ReadProperties;		
	static{
		//读取属性文件
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi80007Form form = (AppApi80007Form)form1;
		log.debug("00057400请求80007参数："+CommonUtil.getStringParams(form));
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Send"+form.getCenterId()).trim();
		AppApi80007ZHResult app80007ZHResult = new AppApi80007ZHResult();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Key"+form.getCenterId()).trim());
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			form.setSendDate(formatter1.format(date));
			form.setSendTime(formatter2.format(date));
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi001Type"+form.getCenterId()).trim());
			if((!form.getChannel().trim().equals("40"))&&(!form.getChannel().trim().equals("92"))&&(!form.getChannel().trim().equals("96"))&&(!form.getChannel().trim().equals("52"))&&(!form.getChannel().trim().equals("91")))
			{
				form.setChannelSeq(form.getSendSeqno());
				form.setTellCode("9999");
				form.setBrcCode("05740008");
				form.setClientMAC("");
				form.setTranDate(form.getSendDate());
			}

			form.setFlag(Channel.getZzChannel(form.getChannel()));
			System.out.println("form.getApplicantVO()========="+form.getApplicantVO());
			System.out.println("form.getSuffInfoList()============"+form.getSuffInfoList());
			System.out.println("form.getParam()============"+form.getParam());
			//String baseinfo=new String(form.getApplicantVO().getBytes("iso-8859-1"), "utf-8");
			//String attr=new String(form.getSuffInfoList().getBytes("iso-8859-1"), "utf-8");
			//String parms = new String(form.getParam().getBytes("iso-8859-1"), "utf-8");
			String parms = form.getParam();
			//form.setApplicantVO(baseinfo);
			//form.setSuffInfoList(attr);
			//form.setParam(parms);
			//System.out.println("baseinfo========="+baseinfo);
			//System.out.println("attr============"+attr);
			System.out.println("parms============"+parms);
			JSONObject obj = JSONObject.fromObject(parms);
			String projId = obj.get("projId").toString();
			System.out.println("projId============"+projId);
			form.setProjId(projId);
			String appId = obj.get("appId").toString();
			System.out.println("appId============"+appId);
			form.setAppId(appId);
			String matterCode = obj.get("matterCode").toString();
			System.out.println("matterCode============"+matterCode);
			form.setCheckcode(matterCode);
			String applicantVO = obj.get("applicantVO").toString();
			System.out.println("applicantVO============"+applicantVO);
			JSONObject obj1 = JSONObject.fromObject(applicantVO);
			String contactCardNo ;
			String contactName ;
			String contactTelNo = obj1.get("contactTelNo").toString();
			form.setHandset(contactTelNo);
			System.out.println("contactTelNo============"+contactTelNo);
			String affFormInfo = obj.get("affFormInfo").toString();
			System.out.println("affFormInfo============"+affFormInfo);
			JSONObject obj2 = JSONObject.fromObject(affFormInfo);

			/*提取 其他-02491-003  matterCode*/
			String[] tiqumatterCode = {"dd20d339-8636-4ac6-a138-3427c3ce7e7a","0f2954af-009a-4f38-98ce-02569c466041","3d81b003-870a-425e-96e6-dbfb02091b15",
					"67151bb7-30d2-43cd-883d-9837bcabdd2b","cb7d178b-b750-460a-8a82-484f0020c486","ead8f1ba-9df5-4c79-bea2-ad5fbaefaf71",
					"fae4679f-8d5d-4080-a17f-3c5f2a804b28","8da44f84-efae-436b-82bf-5a7aa59be98c"};
			List<String> tiqumatterCodeList = Arrays.asList(tiqumatterCode);
			System.out.println("提取"+tiqumatterCodeList.contains(matterCode));

			/*封存事项 其他-02493-006  matterCode*/
			String[] fengchunmatterCode = {"3160e313-4954-4c85-8031-61dffe516d48","19a325e9-4e49-4d16-864b-0f5c896a7aa8","5471ff16-99cf-4f68-b4d8-ac233e6db183",
					"61f2045c-7f64-490f-ab2e-2f2064264ce2","95a952a9-fe7c-416e-9e0d-603bc48f1abf","cd16b34e-470f-46a7-91ce-cc6846250b7c",
					"d3e16efd-7661-4b67-afca-28f09aeb13fd","f26acca2-be0d-11e7-8c42-008cfae57900"};
			List<String> fengchunmatterCodeList = Arrays.asList(fengchunmatterCode);
			System.out.println("封存"+fengchunmatterCodeList.contains(matterCode));

			/*个人开户事项 其他-02493-004  matterCode*/
			String[] kaihumatterCode = {"6c66caef-76b6-4303-8604-e512b61ae2ce","01dbd232-b505-4266-bce7-f62a90651761","8a12fbc8-8f50-44a3-971d-cf02a70aa993",
					"8ae305ad-b98b-4346-aa5a-c8ed94f33fe1","b1498e5b-a576-4510-b80d-cd88eba5a7ee","e4888217-d18b-4e43-b002-c585d49822cc",
					"326dbded-ba1c-4179-af1d-04ac3232d1e5","ec096a70-0cee-4e18-b49e-566bc83a97a7"};
			List<String> kaihumatterCodeList = Arrays.asList(kaihumatterCode);
			System.out.println("开户"+kaihumatterCodeList.contains(matterCode));

			if (kaihumatterCodeList.contains(matterCode))
			{
				String kaihu=obj2.get("员工招聘一件事整合表").toString();
				System.out.println("kaihu============"+kaihu);
				JSONArray kaihujson = JSONArray.fromObject(kaihu);
				for (int i=0;i<kaihujson.size();i++) {
					// 遍历 jsonarray 数组，把每一个对象转成 json 对象
					JSONObject job = kaihujson.getJSONObject(i);
					// 得到 每个对象中的属性值
					System.out.println("员工招聘一件事整合表job.get(\"name\")=" + job.get("name"));
					/*
					id="form_card" title="身份证号码		  		spt_sfzh
					id="form_name" title="姓名						spt_xm

					id="form_nation" title="民族					spt_race
					id="form_residence" title="户口性质				spt_hkxz
					id="form_address" title="户籍地址  				spt_hjszd

					id="form_iphone" title="手机号码				spt_sjhm  spt_yhsjhm

					id="form_postal" title="通讯地址				spt_jtdz
					id="form_comname" title="单位名称				spt_dwmc
					id="form_insurTime" title="本次参保时间			spt_sqsj
					id="form_secode" title="统一社会信用代码 		spt_tyxyydm
					*/
					if (job.get("name").toString().equals("form_card")) {
						contactCardNo = job.get("value").toString();
						System.out.println("contactCardNo============" + contactCardNo);
						form.setCertinum(contactCardNo);
					}
					if (job.get("name").toString().equals("form_name")) {
						contactName = job.get("value").toString();
						System.out.println("contactName============" + contactName);
						form.setAccname(contactName);
					}
					if (job.get("name").toString().equals("form_iphone")) {
						contactTelNo = job.get("value").toString();
						System.out.println("contactTelNo============" + contactTelNo);
						form.setHandset(contactTelNo);
					}
					if (job.get("name").toString().equals("form_nation")) {
						String spt_race = job.get("value").toString();
						System.out.println("spt_race============" + spt_race);
						form.setSpt_race(spt_race);
					}
					if (job.get("name").toString().equals("form_residence")) {
						String spt_hjszd = job.get("value").toString();
						System.out.println("spt_hjszd============" + spt_hjszd);
						form.setSpt_hjszd(spt_hjszd);
					}
					if (job.get("name").toString().equals("form_address")) {
						String spt_hkxz = job.get("value").toString();
						System.out.println("spt_hkxz============" + spt_hkxz);
						form.setSpt_hkxz(spt_hkxz);
					}
					if (job.get("name").toString().equals("form_postal")) {
						String spt_jtdz = job.get("value").toString();
						System.out.println("spt_jtdz============" + spt_jtdz);
						form.setSpt_jtdz(spt_jtdz);
					}
					if (job.get("name").toString().equals("form_comname")) {
						String spt_dwmc = job.get("value").toString();
						System.out.println("spt_dwmc============" + spt_dwmc);
						form.setSpt_dwmc(spt_dwmc);
					}
					if (job.get("name").toString().equals("form_insurTime")) {
						String spt_sqsj = job.get("value").toString();
						System.out.println("spt_sqsj============" + spt_sqsj);
						form.setSpt_sqsj(spt_sqsj);
					}
					if (job.get("name").toString().equals("form_secode")) {
						String spt_tyxyydm = job.get("value").toString();
						System.out.println("spt_tyxyydm============" + spt_tyxyydm);
						form.setSpt_tyxyydm(spt_tyxyydm);
					}
				/*id="form_account" title="公积金单位账号 		spt_dwzh
id="form_industry" title="行业" 				spt_hyfl
id="form_fund" title="公积金月缴基数（元）" 	spt_basenum
id="form_uratio" title="单位缴存比例（公积金）	spt_dwjcbl
id="form_pratio" title="个人缴存比例（公积金）	spt_grjcbl
id="form_ufund" title="公积金-单位月缴存额	  	spt_dwyjce
id="form_pfund" title="公积金-个人月缴存额		spt_gryjce
id="form_tfund" title="公积金-月缴存额			spt_yjce*/
					if (job.get("name").toString().equals("form_account")) {
						String spt_dwzh = job.get("value").toString();
						System.out.println("spt_dwzh============" + spt_dwzh);
						form.setSpt_dwzh(spt_dwzh);
					}
					if (job.get("name").toString().equals("form_industry")) {
						String spt_hyfl = job.get("value").toString();
						System.out.println("spt_hyfl============" + spt_hyfl);
						form.setSpt_hyfl(spt_hyfl);
					}
					if (job.get("name").toString().equals("form_fund")) {
						String spt_basenum = job.get("value").toString();
						System.out.println("spt_basenum============" + spt_basenum);
						form.setSpt_basenum(spt_basenum);
					}
					if (job.get("name").toString().equals("form_uratio")) {
						String spt_dwjcbl = job.get("value").toString();
						System.out.println("spt_dwjcbl============" + spt_dwjcbl);
						form.setSpt_dwjcbl(spt_dwjcbl);
					}
					if (job.get("name").toString().equals("form_pratio")) {
						String spt_grjcbl = job.get("value").toString();
						System.out.println("spt_grjcbl============" + spt_grjcbl);
						form.setSpt_grjcbl(spt_grjcbl);
					}
					if (job.get("name").toString().equals("form_ufund")) {
						String spt_dwyjce = job.get("value").toString();
						System.out.println("spt_dwyjce============" + spt_dwyjce);
						form.setSpt_dwyjce(spt_dwyjce);
					}
					if (job.get("name").toString().equals("form_pfund")) {
						String spt_gryjce = job.get("value").toString();
						System.out.println("spt_gryjce============" + spt_gryjce);
						form.setSpt_gryjce(spt_gryjce);
					}
					if (job.get("name").toString().equals("form_tfund")) {
						String spt_yjce = job.get("value").toString();
						System.out.println("spt_yjce============" + spt_yjce);
						form.setSpt_yjce(spt_yjce);
					}

				}
			}else{
				String tuixiu=obj2.get("企业职工退休一件事").toString();
				System.out.println("tuixiu============"+tuixiu);
				JSONArray tuixiujson = JSONArray.fromObject(tuixiu);
				for (int i=0;i<tuixiujson.size();i++){
					// 遍历 jsonarray 数组，把每一个对象转成 json 对象
					JSONObject job = tuixiujson.getJSONObject(i);
					// 得到 每个对象中的属性值
					System.out.println("企业职工退休一件事job.get(\"name\")="+job.get("name"));

					if (job.get("name").toString().equals("khyhmc")){
						String payeebankcode = job.get("value").toString();
						System.out.println("payeebankcode============"+payeebankcode);
						form.setPayeebankcode(payeebankcode);
					}
				/*
				id="form_nation" title="民族					spt_race
				id="form_residence" title="户口性质				spt_hkxz
				id="form_address" title="户籍地址  				spt_hjszd
				id="form_iphone" title="手机号码				spt_sjhm
				id="form_postal" title="通讯地址				spt_jtdz
				id="form_comname" title="单位名称				spt_dwmc
				id="form_insurTime" title="本次参保时间			spt_sqsj
				id="form_secode" title="统一社会信用代码 		spt_tyxyydm
				id="form_account" title="公积金单位账号 		spt_dwzh
				id="form_industry" title="行业" 				spt_hyfl
				id="form_fund" title="公积金月缴基数（元）" 	spt_basenum
				id="form_uratio" title="单位缴存比例（公积金）	spt_dwjcbl
				id="form_pratio" title="个人缴存比例（公积金）	spt_grjcbl
				id="form_ufund" title="公积金-单位月缴存额	  	spt_dwyjce
				id="form_pfund" title="公积金-个人月缴存额		spt_gryjce
				id="form_tfund" title="公积金-月缴存额			spt_yjce
*/

					// 如果借记卡银行卡号yhkh有值取该值和该银行名称khyhmc,如没有值取社保卡金融银行卡号aae008和金融银行卡号名称hangming
					if (job.get("name").toString().equals("yhkh")){
						String payeebankaccnum = job.get("value").toString();
						System.out.println("payeebankaccnum==yhkh=========="+payeebankaccnum.length());
						if(payeebankaccnum.length()==0){
							System.out.println("进来了11============");
							tuixiujson = JSONArray.fromObject(tuixiu);
							for (i=0;i<tuixiujson.size();i++){
								job = tuixiujson.getJSONObject(i);
								if (job.get("name").toString().equals("aae008")){
									System.out.println("进来了============");
									payeebankaccnum = job.get("value").toString();
									System.out.println("payeebankaccnum11============"+payeebankaccnum);
								}
								if (job.get("name").toString().equals("hangming")){
									String payeebankcode = job.get("value").toString();
									System.out.println("payeebankcode11============"+payeebankcode);
									form.setPayeebankcode(payeebankcode);
								}
							}
						}
						form.setPayeebankaccnum(payeebankaccnum);
					}
					if (job.get("name").toString().equals("aae135")){
						contactCardNo = job.get("value").toString();
						System.out.println("contactCardNo封存============" + contactCardNo);
						form.setCertinum(contactCardNo);
					}
					if (job.get("name").toString().equals("aac003")){
						contactName = job.get("value").toString();
						System.out.println("contactName============" + contactName);
						form.setAccname(contactName);
					}


				}
			}



/*id="form_nation" title="民族					spt_race
id="form_residence" title="户口性质				spt_hkxz
id="form_address" title="户籍地址  				spt_hjszd
id="form_postal" title="通讯地址				spt_jtdz
id="form_comname" title="单位名称				spt_dwmc
id="form_insurTime" title="本次参保时间			spt_sqsj
id="form_secode" title="统一社会信用代码 		spt_tyxyydm
*/

			/*
			{
	"affFormInfo": "{\"企业职工退休一件事\":[{\"name\":\"uniteServiceID\",\"name_cn\":null,\"value\":\"d0ab4780-d71f-4ca0-bdb5-a4509a764580,f26acca2-be0d-11e7-8c42-008cfae57900,8da44f84-efae-436b-82bf-5a7aa59be98c,be95c019-5d48-4a15-a180-a6c2b8299cdb,0a76b8bb-0831-4b71-98e5-f56ef412f987,141c5cdc-8cfa-4059-9195-209a5d31a38a\"},{\"name\":\"aac003\",\"name_cn\":null,\"value\":\"樊开利\"},{\"name\":\"aae135\",\"name_cn\":null,\"value\":\"330225195909269333\"},{\"name\":\"hangming\",\"name_cn\":null,\"value\":\"工商银行\"},{\"name\":\"aae008\",\"name_cn\":null,\"value\":\"6217213901009344793\"},{\"name\":\"khyhmc\",\"name_cn\":null,\"value\":\"0001\"},{\"name\":\"yhkh\",\"name_cn\":null,\"value\":\"\"},{\"name\":\"txny\",\"name_cn\":null,\"value\":\"201909\"},{\"name\":\"cbxqbm\",\"name_cn\":null,\"value\":\"330299\"},{\"name\":\"sbjgmc\",\"name_cn\":null,\"value\":\"宁波市社会保险管理局\"},{\"name\":\"sbjgdz\",\"name_cn\":null,\"value\":\"宁波市海曙区解放南路257号\"}]}",
	"affairType": "00",
	"appId": "2088000579",
	"applicantVO": {
		"address": "浙江省象山县贤庠镇青莱村６组５２户",
		"applyCardNo": "330225195909269333",
		"applyCardType": "31",
		"applyName": "樊开利",
		"applyType": "00",
		"contactCardNo": "330225195909269333",
		"contactCardType": "31",
		"contactName": "樊开利",
		"contactTelNo": "15295843127",
		"isAgent": "0",
		"legalCardNo": "",
		"legalMan": ""
	},
	"applyOrigin": "8",
	"approveType": "04",
	"areaCode": "330201",
	"bizMode": "00",
	"bizType": "1",
	"deptCode": "001008002016015",
	"execDeptOrgCode": "002939927",
	"extInfo": {
		"BANJIAN_FINISHFILES": "当场返还回单",
		"matterTimeLimitUnit": "1",
		"PROJPWD": "285587",
		"matterTimeLimit": "20"
	},
	"gmtApply": 1569379675000,
	"matterCode": "f26acca2-be0d-11e7-8c42-008cfae57900",
	"memo": "",
	"projId": "330201190925812659432",
	"projectName": "关于樊开利申请退休",
	"projectNature": "99",
	"recvDeptCode": "001008002016015",
	"recvDeptName": "市住建局",
	"recvUserId": "bb4a9da36b1e029c016b21020596520f",
	"recvUserName": "王菊",
	"recvUserType": "3",
	"relBizId": "330201190925312654264",
	"suffInfoList": [{
		"extInfo": {
			"ISTAKE": "0"
		},
		"fetchMode": "02",
		"memo": "",
		"stuffName": "职工身份证",
		"stuffNum": 1,
		"stuffType": "02",
		"stuffUniId": "8670B842CA987A9CFE52732A8935DC53"
	}, {
		"extInfo": {
			"ISTAKE": "0"
		},
		"fetchMode": "02",
		"memo": "",
		"stuffName": "个人住房公积金账户封存申请表",
		"stuffNum": 1,
		"stuffType": "02",
		"stuffUniId": "2B5CFA5E82BBD97DE2320501E807AE05"
	}, {
		"extInfo": {
			"ISTAKE": "0"
		},
		"fetchMode": "02",
		"memo": "",
		"stuffName": "经办人身份证",
		"stuffNum": 1,
		"stuffType": "02",
		"stuffUniId": "437F2EFE17CED22FFAB00AB392762A0B"
	}]
}
*/



			HashMap map = BeanUtil.transBean2Map(form);	
		    map.put("tellcode", "rsgy");

			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			String rexml ="";
			//提取调151502
			/*
			dd20d339-8636-4ac6-a138-3427c3ce7e7a
			0f2954af-009a-4f38-98ce-02569c466041
			3d81b003-870a-425e-96e6-dbfb02091b15
			67151bb7-30d2-43cd-883d-9837bcabdd2b
			cb7d178b-b750-460a-8a82-484f0020c486
			ead8f1ba-9df5-4c79-bea2-ad5fbaefaf71
			fae4679f-8d5d-4080-a17f-3c5f2a804b28
			8da44f84-efae-436b-82bf-5a7aa59be98c
			*/
			if (tiqumatterCodeList.contains(matterCode)){
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80007_151502.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);

				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "151502");
				log.info("调用提取交易151502");
			}
			//封存调151503
			/*
			3160e313-4954-4c85-8031-61dffe516d48
			19a325e9-4e49-4d16-864b-0f5c896a7aa8
			5471ff16-99cf-4f68-b4d8-ac233e6db183
			61f2045c-7f64-490f-ab2e-2f2064264ce2
			95a952a9-fe7c-416e-9e0d-603bc48f1abf
			cd16b34e-470f-46a7-91ce-cc6846250b7c
			d3e16efd-7661-4b67-afca-28f09aeb13fd
			f26acca2-be0d-11e7-8c42-008cfae57900
*/
			else if (fengchunmatterCodeList.contains(matterCode)){
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80007_151502.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);

				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "151503");
				log.info("调用封存交易151503");
			}
			//个人开户调111503
			/*6c66caef-76b6-4303-8604-e512b61ae2ce
			01dbd232-b505-4266-bce7-f62a90651761
			8a12fbc8-8f50-44a3-971d-cf02a70aa993
			8ae305ad-b98b-4346-aa5a-c8ed94f33fe1
			b1498e5b-a576-4510-b80d-cd88eba5a7ee
			e4888217-d18b-4e43-b002-c585d49822cc
			326dbded-ba1c-4179-af1d-04ac3232d1e5
			ec096a70-0cee-4e18-b49e-566bc83a97a7*/
			else if (kaihumatterCodeList.contains(matterCode)
			){
				String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_80007_111082.txt", map, req);
				log.debug("前置YFMAP发往中心报文："+xml);

				rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "111503");
				log.info("调用个人开户111503");
			}
			log.debug("前置YFMAP接收中心报文——住建委业务受理号上传："+rexml);
			HashMap resultMap = MessageCtrMain.analysisPacket(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REP_80007_111082.txt", rexml, req);
			log.debug("解析报文MAP："+resultMap);
			BeanUtil.transMap2Bean(resultMap, app80007ZHResult);
			log.debug("MAP封装成BEAN："+app80007ZHResult);
			if(!"0".equals(app80007ZHResult.getRecode())){
				modelMap.clear();
				/*modelMap.put("recode", app80007ZHResult.getRecode());
				modelMap.put("msg", "错误信息："+app80007ZHResult.getMsg());*/
				modelMap.put("result", "02");
				modelMap.put("resultmsg", "错误信息："+app80007ZHResult.getSpt_showmsg());
				log.error("中心返回报文 状态recode :"+app80007ZHResult.getRecode()+"  ,  描述msg : "+app80007ZHResult.getSpt_showmsg());
				return modelMap;
			}
		}

		modelMap.clear();
/*		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");*/
		log.info("app80007ZHResult.getSpt_success()"+app80007ZHResult.getSpt_success());
		if (app80007ZHResult.getSpt_success().equals("0")){
			modelMap.put("result", "02");
		}else{
			modelMap.put("result", "01");
		}

		modelMap.put("resultmsg", app80007ZHResult.getSpt_showmsg());
		log.info("中心返回报文 状态recode :"+app80007ZHResult.getRecode()+"  ,  描述msg : "+app80007ZHResult.getSpt_showmsg());
		return modelMap;
	}

}
