package com.yondervision.yfmap.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.form.AppApi00201Form;
import com.yondervision.yfmap.form.AppApi00501Form;
import com.yondervision.yfmap.form.AppApi00601Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.form.SmsApi80507Form;
import com.yondervision.yfmap.result.AppApi300201Result;
import com.yondervision.yfmap.result.AppApi3002Result;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.service.shiyan.AppApiSearchDataService;
import com.yondervision.yfmap.util.FileUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.JsonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

@Controller
public class AppApi805Contorller {
	static Logger log = Logger.getLogger("YFMAP");
	private AppApiSearchDataService appApiSearchDataServiceImpl = null;

	public void setAppApiSearchDataServiceImpl(
			AppApiSearchDataService appApiSearchDataServiceImpl) {
		this.appApiSearchDataServiceImpl = appApiSearchDataServiceImpl;
	}

	// 个人账户查询
	@RequestMapping("/appapi80501.{ext}")
	public String appApi80501(AppApi00101Form form, ModelMap modelMap)
			throws Exception {
		log.debug("appapi80501公积金账户查询");
		AES aes = new AES();
		HashMap paramap = new HashMap();
		if (form.getBodyCardNumber() == null
				|| "".equals(form.getBodyCardNumber())
				|| "".equals(aes.decrypt(form.getBodyCardNumber()))) {
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "身份证号为空，请确认！");
			return "";
		}
		paramap.put("bodyCardNumber", aes.decrypt(form.getBodyCardNumber()));
		log.debug(JsonUtil.getGson().toJson(form));
		try {
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi00101Select(paramap);
			if (list == null || list.size() == 0) {
				modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无公积金信息");
				modelMap.put("result", "");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				HashMap map = list.get(0);
				map.put("valuedate",map.get("valuedate")==null?"":map.get("valuedate").toString().substring(0, 10));
				map.put("deposit_rate1",
						String.format("%,.2f", Double.valueOf(map.get(
								"deposit_rate1").toString()))
								+ "%");
				map.put("deposit_rate2",
						String.format("%,.2f", Double.valueOf(map.get(
								"deposit_rate2").toString()))
								+ "%");
				if ("1".equals(map.get("smsstatus"))) {
					map.put("smsstatus", "开通");
				} else {
					map.put("smsstatus", "未开通");
				}
				if ("70007".equals(map.get("status"))) {
					map.put("status", "正常");
				} else {
					map.put("status", "非正常");
				}
				List<TitleInfoBean> result = JavaBeanUtil
						.javaBeanToListTitleInfoBean(
								"appapi80501" + form.getCenterId() + ".result",
								map);
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result);
				log.debug(JsonUtil.getGson().toJson(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}

	// 个人账户明细
	@RequestMapping("/appapi80502.{ext}")
	public String appApi80502(AppApi00201Form form, ModelMap modelMap)
			throws Exception {
		try {
			log.debug(JsonUtil.getGson().toJson(form));
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("bodyCardNumber", aes.decrypt(form.getBodyCardNumber()));
			paramap.put("pagenum", form.getPagenum());
			paramap.put("pagerows", form.getPagerows());
			paramap.put("startdate", form.getStartdate());
			paramap.put("enddate", form.getEnddate());
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi00201Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无公积金明细信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				List<List<TitleInfoBean>> result = new ArrayList();
				List<TitleInfoBean> mxResult = null;
				for (int i = 0; i < list.size(); i++) {
					HashMap map = list.get(i);
					map.put("valuedate", map.get("valuedate").toString()
							.substring(0, 10));
					mxResult = JavaBeanUtil
							.javaBeanToListTitleInfoBean(
									"appapi80502" + form.getCenterId()
											+ ".result", map);
					result.add(mxResult);
				}
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result);
				log.debug(JsonUtil.getGson().toJson(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}

	// 贷款情况查询
	@RequestMapping("/appapi80503.{ext}")
	public String appApi80503(AppApi00601Form form, ModelMap modelMap)
			throws Exception {
		log.debug("appapi80503 贷款查询");
		try {
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("bodyCardNumber", aes.decrypt(form.getBodyCardNumber()));
			paramap.put("loanContrNum", form.getLoanContrNum());
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi00601Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无贷款信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
					HashMap map = list.get(0);
					map.put("begindate", map.get("begindate")==null?"":map.get("begindate").toString()
							.substring(0, 10));
					map.put("overdate", map.get("overdate")==null?"":map.get("overdate").toString()
									.substring(0, 10));
					map.put("calc_interest_date", map.get("calc_interest_date")==null?"":map.get("calc_interest_date").toString()
							.substring(0, 10));
					map.put("monthrate", String.format("%,.2f",Double.valueOf(map.get("monthrate")==null?"0":map.get("monthrate").toString()))+"%");
					List<TitleInfoBean> result = JavaBeanUtil
							.javaBeanToListTitleInfoBean(
									"appapi80503" + form.getCenterId() + ".result",
									map);
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result);
				log.debug(JsonUtil.getGson().toJson(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}

	// 个人用户贷款明细
	@RequestMapping("/appapi80504.{ext}")
	public String appApi80504(AppApi00501Form form, ModelMap modelMap)
			throws Exception {
		try {
			log.debug("appapi80504 贷款明细");
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("bodyCardNumber", aes.decrypt(form.getBodyCardNumber()));
			paramap.put("loanContrNum", form.getLoancontractno());
			paramap.put("pagenum", form.getPagenum());
			paramap.put("pagerows", form.getPagerows());
			paramap.put("startdate", form.getStartdate());
			paramap.put("enddate", form.getEnddate());
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi00701Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "该用户无贷款明细信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				List<List<TitleInfoBean>> result = new ArrayList();
				List<TitleInfoBean> mxResult = null;
				for (int i = 0; i < list.size(); i++) {
					HashMap map = list.get(i);
					map.put("calc_interest_date", map.get("calc_interest_date")==null?"":map.get("calc_interest_date").toString()
							.substring(0, 10));
					mxResult = JavaBeanUtil
							.javaBeanToListTitleInfoBean(
									"appapi80504" + form.getCenterId()
											+ ".result", map);
					result.add(mxResult);
				}
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("result", result);
				log.debug(JsonUtil.getGson().toJson(result));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}

	// 个人注册验证查询
	@RequestMapping("/appapi80505.{ext}")
	public String appApi80505(AppApi40102Form form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("appapi80505 注册验证");
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("idcardNumber", aes.decrypt(form.getIdcardNumber()));
			paramap.put("fullName", aes.decrypt(form.getFullName()));
			paramap.put("mobileNumber", form.getMobileNumber());
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl.appApi40102Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到该用户，请核对输入信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				paramap.put("app_flag", "1");
				int result = appApiSearchDataServiceImpl.appApi40102UpdateAppFlag(paramap);
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				log.debug(JsonUtil.getGson().toJson(modelMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}

	// 个人账户绑定
	@RequestMapping("/appapi80506.{ext}")
	public String appApi80506(AppApi40102Form form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("appapi80506 绑定验证");
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("idcardNumber", aes.decrypt(form.getIdcardNumber()));
			paramap.put("fullName", aes.decrypt(form.getFullName()));
			paramap.put("mobileNumber", form.getMobileNumber());
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi40102Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到该用户，请核对输入信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				if("20".equals(form.getChannel())){
					paramap.put("wx_flag", "1");
					int result = appApiSearchDataServiceImpl.appApi40102UpdateWXFlag(paramap);
				}else{
					paramap.put("app_flag", "1");
					int result = appApiSearchDataServiceImpl.appApi40102UpdateAppFlag(paramap);
				}
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				log.debug(JsonUtil.getGson().toJson(modelMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}
	// 个人账户解绑
	@RequestMapping("/appapi80507.{ext}")
	public String appApi80507(AppApi40102Form form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("appapi80507 账户解绑");
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("idcardNumber", aes.decrypt(form.getIdcardNumber()));
			paramap.put("fullName", aes.decrypt(form.getFullName()));
			paramap.put("mobileNumber", form.getMobileNumber());
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl
					.appApi40102Select(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "未查询到该用户，请核对输入信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				if("20".equals(form.getChannel())){
					paramap.put("wx_flag", "0");
					int result = appApiSearchDataServiceImpl.appApi40102UpdateWXFlag(paramap);
				}else{
					paramap.put("app_flag", "0");
					int result = appApiSearchDataServiceImpl.appApi40102UpdateAppFlag(paramap);
				}
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				log.debug(JsonUtil.getGson().toJson(modelMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}
	@RequestMapping("/appapi80508.{ext}")
	public String appApi80508(AppApi00601Form form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("appapi80508 贷款合同号查询");
			AES aes = new AES();
			HashMap paramap = new HashMap();
			paramap.put("bodyCardNumber", aes.decrypt(form.getBodyCardNumber()));
			log.debug(JsonUtil.getGson().toJson(form));
			List<HashMap> list = appApiSearchDataServiceImpl.appApiDkhthSelect(paramap);
			modelMap.clear();
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "999999");
				modelMap.put("msg", "用户无贷款信息");
			} else {
				log.debug(JsonUtil.getGson().toJson(list));
				String loanContrNum="";
				for(int i=0;i<list.size();i++){
					if(i>0)loanContrNum = loanContrNum+",";
					loanContrNum = loanContrNum+list.get(i).get("contractaccounts").toString();
				}
				modelMap.put("loanContrNum", loanContrNum);
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				log.debug(JsonUtil.getGson().toJson(modelMap));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "查询信息错误，请稍后再试");
		}
		return "";
	}
	@RequestMapping("/smsapi80501.{ext}")
	public String smsapi80501(SmsApi80507Form form, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("smsapi80501 短信服务 smssource ======"+form.getSmssource());
			log.debug("smsapi80501 短信服务 param2 ======"+form.getParam2());
			log.debug(JsonUtil.getGson().toJson(form));
			HashMap paramap = new HashMap();
			int updateflag=-1;
			modelMap.clear();
			if("050001".equals(form.getSmssource())){
				paramap.put("idcardNumber", form.getParam2());
				paramap.put("mobileNumber", form.getParam1());
				paramap.put("status","1");
				paramap.put("status1","1");
				updateflag = appApiSearchDataServiceImpl.updateSmsflag(paramap);
				if(updateflag==0){
					modelMap.put("recode", "-1");
					modelMap.put("msg", "办理业务失败，请确认信息正确");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}else if(updateflag==1){
					modelMap.put("recode", "0");
					modelMap.put("msg", "办理业务成功");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}
				return "";
			} if("050002".equals(form.getSmssource())){
				paramap.put("newMobileNumber", form.getParam1());
				paramap.put("mobileNumber", form.getParam2());
				updateflag = appApiSearchDataServiceImpl.updateSmsPhonenumber(paramap);
				if(updateflag==0){
					modelMap.put("recode", "-1");
					modelMap.put("msg", "办理业务失败，请确认信息正确");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}else if(updateflag==1){
					modelMap.put("recode", "0");
					modelMap.put("msg", "办理业务成功");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}
				return "";
			} if("050003".equals(form.getSmssource())){
//				paramap.put("idcardNumber", form.getParam1());
				paramap.put("mobileNumber", form.getParam1());
				paramap.put("status","0");
				paramap.put("status1","0");
				updateflag = appApiSearchDataServiceImpl.updateSmsflag(paramap);
				if(updateflag==0){
					modelMap.put("recode", "-1");
					modelMap.put("msg", "办理业务失败，请确认信息正确");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}else if(updateflag==1){
					modelMap.put("recode", "0");
					modelMap.put("msg", "办理业务成功");
					modelMap.put("cnt", updateflag+"");
					modelMap.put("filename","");
				}
				return "";
			}
			List<HashMap> userinfolist = new ArrayList<HashMap>();
			if(form.getParam1()!=null&&!form.getParam1().equals("")){
				paramap.put("mobileNumber", form.getParam1());
				paramap.put("status", "1");
				userinfolist = appApiSearchDataServiceImpl.appApi40102Select(paramap);
				if(userinfolist.size()==0){
					modelMap.put("recode", "-1");
					modelMap.put("msg", "该手机号未签约短信");
					modelMap.put("cnt", "0");
					modelMap.put("filename","");
				}else{
					HashMap temp = userinfolist.get(0);
					log.debug(JsonUtil.getGson().toJson(((String)temp.get("certinum")).trim()));
					paramap.put("bodyCardNumber", ((String)temp.get("certinum")).trim());
				}
			}else{
//				paramap.put("status", "1");
//				userinfolist = appApiSearchDataServiceImpl.appApi40102Select(paramap);
//				if(userinfolist.size()==0){
//					modelMap.put("recode", "-1");
//					modelMap.put("msg", "查询信息出错，请稍后重试");
//					modelMap.put("cnt", "0");
//					modelMap.put("filename","");
//				}else{
//					String[] bodyCardNumber = new String[userinfolist.size()];
//					for(int i=0;i<userinfolist.size();i++){
//						HashMap temp = userinfolist.get(i);
//						bodyCardNumber[i]= ((String)temp.get("certinum")).trim();
//					}
//					log.debug(JsonUtil.getGson().toJson(bodyCardNumber));
//					paramap.put("bodyCardNumber", bodyCardNumber);
//				}
			}
//			List<HashMap> appUserinfolist = appApiSearchDataServiceImpl.appApiFlagSelect(null);
			HashMap paramapApp = new HashMap();
//			String[] appbodyCardNumber = new String[appUserinfolist.size()];
//			for(int i=0;i<appUserinfolist.size();i++){
//				HashMap temp = appUserinfolist.get(i);
//				appbodyCardNumber[i]= ((String)temp.get("certinum")).trim();
//			}
//			log.debug(JsonUtil.getGson().toJson(appbodyCardNumber));
//			paramapApp.put("bodyCardNumber", appbodyCardNumber);
			paramapApp.put("smssource", form.getSmssource());
			String PROPERTIES_FILE_NAME = "properties.properties";
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String filepath = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "SmsFileUrl_00071900").trim()+File.separator+formatter1.format(date);
			List<LinkedHashMap> list  = null;
			List<LinkedHashMap> applist  = new ArrayList<LinkedHashMap>();
			if("010011".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiJcrzSelect(paramap);
				paramapApp.put("currdate", form.getParam2());
				applist = appApiSearchDataServiceImpl.appApiJcrzSelect(paramapApp);
			}else if("010012".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiGrtzSelect(paramap);
				paramapApp.put("currdate", form.getParam2());
				applist = appApiSearchDataServiceImpl.appApiGrtzSelect(paramapApp);
			}else if("010017".equals(form.getSmssource())){
				paramap.put("enddate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiJxgjSelect(paramap);
				paramapApp.put("enddate", form.getParam2());
				applist = appApiSearchDataServiceImpl.appApiJxgjSelect(paramapApp);
			}else if("010018".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2()+"%");
				list = appApiSearchDataServiceImpl.smsApiDwcjSelect(paramap);
				paramapApp.put("currdate", form.getParam2()+"%");
				applist = appApiSearchDataServiceImpl.appApiDwcjSelect(paramapApp);
			}else if("010020".equals(form.getSmssource())){
				list = appApiSearchDataServiceImpl.smsApiGrgjdxqySelect(paramap);
				applist = appApiSearchDataServiceImpl.appApiGrgjdxqySelect(paramapApp);
			}else if("020001".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiZctqSelect(paramap);
				paramapApp.put("currdate", form.getParam2());
				applist = appApiSearchDataServiceImpl.appApiZctqSelect(paramapApp);
			}else if("020002".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiXhtqSelect(paramap);
				paramapApp.put("currdate", form.getParam2());
				applist = appApiSearchDataServiceImpl.appApiXhtqSelect(paramapApp);
			}else if("020003".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiZctqplSelect(paramap);
			}else if("020004".equals(form.getSmssource())){
				paramap.put("currdate", form.getParam2());
				list = appApiSearchDataServiceImpl.smsApiXhtqplSelect(paramap);
			}else if("030010".equals(form.getSmssource())){
				log.debug("smsapi80501 短信服务 进入030010 ======"+form.getSmssource()+"==="+form.getParam2());
				paramap.put("currdate", form.getParam2());
				paramap.put("thisdate", form.getParam2().substring(0, 4)+"-01-31");
				paramap.put("lastdate", (Integer.parseInt(form.getParam2().substring(0, 4))-1)+"-01-01");
				list = appApiSearchDataServiceImpl.smsApiLlbdSelect(paramap);
				paramapApp.put("currdate", form.getParam2());
				paramapApp.put("thisdate", form.getParam2().substring(0, 4)+"-01-31");
				paramapApp.put("lastdate", (Integer.parseInt(form.getParam2().substring(0, 4))-1)+"-01-01");
				applist = appApiSearchDataServiceImpl.appApiLlbdSelect(paramapApp);
			}else if("050004".equals(form.getSmssource())){
				list = appApiSearchDataServiceImpl.smsApiZhxxSelect(paramap);
			}else if("050005".equals(form.getSmssource())){
				list = appApiSearchDataServiceImpl.smsApiJxxxSelect(paramap);
			}else if("050006".equals(form.getSmssource())){
				list = appApiSearchDataServiceImpl.smsApiDkxxSelect(paramap);
			}
			
			if (list == null || list.size() == 0) {
				modelMap.put("recode", "0");
				modelMap.put("msg", "无查询信息");
				modelMap.put("cnt", "0");
			} else {
				String filename = form.getSmssource()+"_"+System.currentTimeMillis()+".txt";
				log.debug(JsonUtil.getGson().toJson(list));
				log.debug("filename======"+filepath+File.separator+filename);
				boolean flag = FileUtil.makeSmsFile(filepath,filename, list,form.getSmssource());
				if(flag == false){
					modelMap.put("recode", "-1");
					modelMap.put("msg", "生成文件失败");
					modelMap.put("cnt", ""+list.size());
					modelMap.put("filename", "");
					log.debug(JsonUtil.getGson().toJson(modelMap));
				}else{
					modelMap.put("recode", "0");
					modelMap.put("msg", "查询信息成功");
					modelMap.put("cnt", ""+list.size());
					modelMap.put("filename", formatter1.format(date)+File.separatorChar+filename);
					log.debug(JsonUtil.getGson().toJson(modelMap));
				}
			}
			if(applist != null &&applist.size()>0){
				log.debug(JsonUtil.getGson().toJson("applist========="+applist));
				String sendSeqno = form.getSmssource()+System.currentTimeMillis();
				String centerid = form.getCenterId()==""?"00071900":form.getCenterId();
				sendMsg(applist,new HashMap(),5,form.getSmssource(),centerid,sendSeqno);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			modelMap.clear();
			modelMap.put("recode", "-1");
			modelMap.put("msg", "错误，请稍后再试");
		}
		return "";
	}
	public static void sendMsg(List<LinkedHashMap> applist,HashMap resultMap,int sendcount,String smssource,String centerid,String sendSeqno){
		String PROPERTIES_FILE_NAME = "properties.properties";
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim()+"yfmapapi3002.json";
		String MESSAGE_FILE_NAME = "messageContent.properties";
		String template = PropertiesReader.getProperty(MESSAGE_FILE_NAME,smssource ).trim();
		String title = template.split(";")[0];
		String content = template.split(";")[1];
		AppApi3002Result appapi3002result	= new AppApi3002Result();
		appapi3002result.setCenterId(centerid);
		List<AppApi300201Result> list300201 = new ArrayList<AppApi300201Result>();
		for(int i=0;i<applist.size();i++){					
			AppApi300201Result api300201Result = new AppApi300201Result();					
			LinkedHashMap<String,Object> map = applist.get(i);
				api300201Result.setAccnum(map.get("personal_cardno").toString());
				if(map.get("personal_cardno")!=null)map.remove("personal_cardno");
				if(map.get("fundbusiness_id")!=null)map.remove("fundbusiness_id");
				api300201Result.setTitle(title);
				String[] firststr = new String[map.size()];
				int index=0;
				for (String key : map.keySet()) {
					firststr[index] = map.get(key)==null?"":map.get(key).toString();
					index++;
				}
				api300201Result.setDetail(String.format(content, firststr));							
				list300201.add(api300201Result);
		}
		appapi3002result.setList(list300201);
		String param = JsonUtil.getGson().toJson(list300201);
		System.out.println("param========"+param);
		log.debug("AppApi805Contorller:YBMAP url ："+url);
		resultMap.put("centerId", centerid);
		resultMap.put("sendSeqno", sendSeqno);
		resultMap.put("count", list300201.size()+"");
		resultMap.put("theme", "1");
		resultMap.put("chanel", "10,20");
		resultMap.put("mslist",param);
		try{
			YbmapMessageUtil post = new YbmapMessageUtil();
			String rm = post.post(url, resultMap);
			sendcount = sendcount+1;
			Gson gson = new Gson();
			Map ybmapmsg = gson.fromJson(rm,new TypeToken<Map<String, String>>() {}.getType()); 
			if(ybmapmsg.get("recode")==null||!ybmapmsg.get("recode").equals("000000")){
				if(sendcount<5){
					 try {
						Thread.sleep (5000) ;
						sendMsg(applist, resultMap, sendcount,smssource,centerid,sendSeqno);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sendMsg(applist, resultMap, sendcount,smssource,centerid,sendSeqno);
		}
	}
}
