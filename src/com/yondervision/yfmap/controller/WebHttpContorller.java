package com.yondervision.yfmap.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtilZJW;
import com.yondervision.yfmap.form.AcceptInParams;
import com.yondervision.yfmap.form.TransactInParams;
import com.yondervision.yfmap.util.ExchangeHttpClient;

@Controller
@RequestMapping("/exchange")
public class WebHttpContorller {
	Logger log = Logger.getLogger("YFMAP");

	/**
	 * 收件分发接口
	 * @param baseInfoXml 申报基本信息
	 * @param attrXml 申报材料信息
	 * @param formXml 业务表单信息
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/register.{ext}", method = RequestMethod.POST)
	public void register(String baseInfoXml, String attrXml, String formXml,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "register begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP收件分发接口开始时间："+ starttime);
		log.debug("YFMAP收件分发接口参数——申报基本信息(baseInfoXml)：" + baseInfoXml);
		log.debug("YFMAP收件分发接口参数——申报材料信息(attrXml)：" + attrXml);
		log.debug("YFMAP收件分发接口参数——业务表单信息(formXml)：" + formXml);
		// 调用业务系统提供的接口
		String url = "http://172.16.10.92:8080/WService_NBGOV/UnifiedAcceptOutSystemReceive.aspx";
		String param = "ApiKey=5dee00630a6aa308a7b23762e9e1465a&baseInfoXml="
				+ baseInfoXml + "&attrXml=" + attrXml + "&formXml=";
		String result = ExchangeHttpClient.sendPost(url, param, "gb18030");
		log.debug("YFMAP收件分发接口返回结果——返回信息：" + result);
		// 返回信息
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP收件分发接口结束时间：" + endtime);
		log.info("YFMAP收件分发接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "register end.");
		return;
	}

	/**
	 * 受理接口
	 * @param acceptInParams 受理接口入参
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/accept.{ext}", method = RequestMethod.POST)
	public void accept(AcceptInParams acceptInParams,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "accept begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP受理接口开始时间："+ starttime);
		// 调用住建委行政审批系统接口
		String url = "http://172.16.10.92:8080/WService_NBGOV/UnifiedAcceptOutSystemAccept.aspx";
		// 将受理接口入参转成http发送参数,拼接参数
		String param = "ApiKey=5dee00630a6aa308a7b23762e9e1465a&PROJID="
				+ acceptInParams.getProjid() + "&ACCEPT_MAN="
				+ acceptInParams.getAccept_man() + "&AcceptTime="
				+ acceptInParams.getAccepttime() + "&PROMISEVALUE="
				+ acceptInParams.getPromisevalue() + "&CREATE_TIME="
				+ acceptInParams.getCreate_time() + "&DATAVERSION="
				+ acceptInParams.getDataversion() + "&DOACTION="
				+ acceptInParams.getDoaction() + "&BELONGSYSTEM="
				+ acceptInParams.getBelongsystem() + "&CUR_HANDLEDEPT="
				+ acceptInParams.getCur_handledept() + "&OutSystemID="
				+ acceptInParams.getOutsystemid();
		log.debug("YFMAP受理接口参数转换后——受理接口入参：" + param);
		String result = ExchangeHttpClient.sendPost(url, param, "gb18030");
		log.debug("YFMAP受理接口返回结果——返回信息：" + result);
		// 返回信息
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP受理接口结束时间：" + endtime);
		log.info("YFMAP受理接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "accept end.");
		return;
	}

	/**
	 * 办结接口
	 * @param transactInParams 办结接口入参
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/transact.{ext}", method = RequestMethod.POST)
	public void transact(TransactInParams transactInParams,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "transact begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办结接口开始时间："+ starttime);
		String url = "http://172.16.10.92:8080/WService_NBGOV/UnifiedAcceptOutSystemOver.aspx";
		// 将办结接口入参转成http发送参数,拼接参数
		String param = "ApiKey=5dee00630a6aa308a7b23762e9e1465a&PROJID="
				+ transactInParams.getProjid() + "&TRANSACT_USER="
				+ transactInParams.getTransact_user() + "&TRANSACT_TIME="
				+ transactInParams.getTransact_time() + "&TRANSACT_RESULT="
				+ transactInParams.getTransact_result() + "&TRANSACT_DESCRIBE="
				+ transactInParams.getTransact_describe() + "&RESULT_CODE="
				+ transactInParams.getResult_code() + "&REMARK="
				+ transactInParams.getRemark() + "&CREATE_TIME="
				+ transactInParams.getCreate_time() + "&DATAVERSION="
				+ transactInParams.getDataversion() + "&BELONGSYSTEM="
				+ transactInParams.getBelongsystem();
		log.debug("YFMAP办结接口参数转换后——办结接口入参：" + param);
		String result = ExchangeHttpClient.sendPost(url, param, "gb18030");
		log.debug("YFMAP办结接口返回结果——返回信息：" + result);
		// 返回信息
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办结接口结束时间：" + endtime);
		log.info("YFMAP办结接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "transact end.");
		return;
	}

	/**
	 * 办结环节接口
	 * @param key 调用接口关键字
	 * @param formjson 办结环节json
	 * @param baseInfoXml 申报基本信息
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/agentLink.{ext}", method = RequestMethod.POST)
	public void agentLink(String key, String formjson, String baseInfoXml,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "agentLink begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办结环节接口开始时间："+ starttime);
		log.debug("YFMAP办结环节接口参数——调用接口关键字(key)：" + key);
		log.debug("YFMAP办结环节接口参数——办结环节json(formjson)：" + formjson);
		log.debug("YFMAP办结环节接口参数——申报基本信息(baseInfoXml)：" + baseInfoXml);
		// 调用住建委行政审批系统接口
		String url = "http://172.16.10.92:8080/WService_NBGOV/UnifiedAcceptOutSystemOver_FrontDB.aspx";
		String param = "ApiKey=5dee00630a6aa308a7b23762e9e1465a&key=" + key
				+ "&formjson=" + formjson + "&baseInfoXml=" + baseInfoXml;
		String result = ExchangeHttpClient.sendPost(url, param, "gb18030");
		log.debug("YFMAP办结环节接口返回结果——返回信息：" + result);
		// 返回信息
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办结环节接口结束时间：" + endtime);
		log.info("YFMAP办结环节接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "agentLink end.");
		return;
	}
	
	
	/**
	 * 获取头部Authorization Token
	 * @param Type String类型。允许值: Basic, Bearer
	 * @param LoginName String用户名。
	 * @param LoginPwd String密码。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/getauth.{ext}", method = RequestMethod.POST)
	public void getauth(String Type,
									String LoginName,
									String LoginPwd,
									HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getauth begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP获取头部Authorization Token开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/Test/getauth";
		params.put("Type", "Basic");
		params.put("LoginName", "nbzjw_gjj");
		params.put("LoginPwd", "nbgjj12345");
        SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP获取头部Authorization Token返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP获取头部Authorization Token结束时间：" + endtime);
		log.info("YFMAP获取头部Authorization Token耗时：" + time);
		log.info(Constants.LOG_HEAD + "getauth end.");
		return;
	}

	/**
	 * 3_办件回传 - 00、统一受理平台：获取统一赋码申报号
	 * @param PROJID 21位申报号
	 * @param baseInfoXml 申报基本信息
	 * @param attrXml 申报材料信息
	 * @param formXml 业务表单信息
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/getPId.{ext}", method = RequestMethod.POST)
	public void getProjectId(String param,String baseInfoXml, String attrXml, String formXml,String BELONGSYSTEM,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getProjectId begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP统一受理平台：获取统一赋码申报号开始时间："+ starttime);
		log.debug("YFMAP统一受理平台：收件接口参数——param json串：" + param);
		log.debug("YFMAP统一受理平台：收件接口参数——申报基本信息(baseInfoXml)：" + baseInfoXml);
		log.debug("YFMAP统一受理平台：收件接口参数——申报材料信息(attrXml)：" + attrXml);
		log.debug("YFMAP统一受理平台：收件接口参数——业务表单信息(formXml)：" + formXml);
		
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.92:60/api/NingBoUnifiedAcceptPlatform/UAP/projid/get";
        params.put("param", param);
		log.debug("YFMAP统一受理平台：收件接口参数——param json串2：" + param);
/*     params.put("baseInfoXml", baseInfoXml);
        params.put("attrXml", attrXml);
        params.put("formXml", formXml);
        params.put("BELONGSYSTEM", BELONGSYSTEM);*/
        SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP统一受理平台：收件接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP统一受理平台：收件接口结束时间：" + endtime);
		log.info("YFMAP统一受理平台：收件接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getProjectId end.");
		return;
	}

	/**
	 * 3_办件回传 - 1、统一受理平台：收件
	 * @param PROJID 21位申报号
	 * @param baseInfoXml 申报基本信息
	 * @param attrXml 申报材料信息
	 * @param formXml 业务表单信息
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/getuprec.{ext}", method = RequestMethod.POST)
	public void getuprec(String PROJID,String baseInfoXml, String attrXml, String formXml,String BELONGSYSTEM,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "UAPReceive begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP统一受理平台：收件接口开始时间："+ starttime);
		log.debug("YFMAP统一受理平台：收件接口参数——21位申报号(PROJID)：" + PROJID);
		log.debug("YFMAP统一受理平台：收件接口参数——申报基本信息(baseInfoXml)：" + baseInfoXml);
		log.debug("YFMAP统一受理平台：收件接口参数——申报材料信息(attrXml)：" + attrXml);
		log.debug("YFMAP统一受理平台：收件接口参数——业务表单信息(formXml)：" + formXml);

		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/UAP/Receive";
        params.put("PROJID", PROJID);
        params.put("baseInfoXml", baseInfoXml);
        params.put("attrXml", attrXml);
        params.put("formXml", formXml);
        params.put("BELONGSYSTEM", BELONGSYSTEM);
        SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP统一受理平台：收件接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP统一受理平台：收件接口结束时间：" + endtime);
		log.info("YFMAP统一受理平台：收件接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "UAPReceive end.");
		return;
	}

	
	/**
	 * 3_办件回传 - 2、办件库：收件
	 * @param PROJID 21位申报号
	 * @param baseInfoXml 申报基本信息
	 * @param attrXml 申报材料信息
	 * @param formXml 业务表单信息
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/getapprec.{ext}", method = RequestMethod.POST)
	public void getapprec(String PROJID, String baseInfoXml, String attrXml, String formXml,String BELONGSYSTEM,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "ApprovalLibReceive begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：收件接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/Receive";
        params.put("PROJID", PROJID);
        params.put("baseInfoXml", baseInfoXml);
        params.put("attrXml", attrXml);
        params.put("formXml", formXml);
        params.put("BELONGSYSTEM", BELONGSYSTEM);
        SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP办件库：收件接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：收件接口结束时间：" + endtime);
		log.info("YFMAP办件库：收件接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "ApprovalLibReceive end.");
		return;
	}
	
	/**
		* 3_办件回传 - 3、统一受理平台：受理
		* @param	PROJID	String	21位申报号。
		* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
		* @param	ACCEPT_MAN	String	受理人。
		* @param	AcceptTime	DateTime	受理时间。
		* @param	PROMISEVALUE	Number	承诺工作日。
		* @param	CREATE_TIME 可选	DateTime	数据产生时间。 默认值: 当前时间
		* @param	DATAVERSION 可选	Number	数据版本。 默认值: 1
		* @param	DOACTION	Number	动作 0:受理，1:不予受理。允许值: 0, 1
		* @param	BELONGSYSTEM	String	系统对接码。
		* @param	Hander_Deptid	String	受理人员所属部门编码。
		* @param	Hander_Deptname	String	受理人员所属部门名称。
		* @param	OutSystemID	String	外部系统ID。
		* @param	AREACODE	String	区域(330201:市本级)。
		* @param	SERVICE_DEPTID	String	事项终审部门编码。
		 * @param	XK_LYDW	String	数据来源单位。
		 * @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
		 * @throws Exception
		 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/getupacc.{ext}", method = RequestMethod.POST)
	public void getupacc(	
											String 	PROJID,
											String 	SERVICECODE,
											String 	ACCEPT_MAN,
											String 	AcceptTime,
											String 	PROMISEVALUE,
											String 	CREATE_TIME,
											String 	DATAVERSION,
											String 	DOACTION,
											String 	BELONGSYSTEM,
											String 	Hander_Deptid,
											String 	Hander_Deptname,
											String 	OutSystemID,
											String 	AREACODE,
											String 	SERVICE_DEPTID,
											String 	XK_LYDW,
											String 	XK_LYDWDM,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getuapacc begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP统一受理平台：受理接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/UAP/accept";
        params.put("PROJID", PROJID);
		params.put("SERVICECODE", SERVICECODE);
		params.put("ACCEPT_MAN", ACCEPT_MAN);
		params.put("AcceptTime", AcceptTime);
		params.put("PROMISEVALUE", PROMISEVALUE);
		params.put("CREATE_TIME", CREATE_TIME);
		params.put("DATAVERSION", DATAVERSION);
		params.put("DOACTION", DOACTION);
		params.put("BELONGSYSTEM", BELONGSYSTEM);
		params.put("Hander_Deptid", Hander_Deptid);
		params.put("Hander_Deptname", Hander_Deptname);
		params.put("OutSystemID", OutSystemID);
		params.put("AREACODE", AREACODE);
		params.put("SERVICE_DEPTID", SERVICE_DEPTID);
		params.put("XK_LYDW", XK_LYDW);
		params.put("XK_LYDWDM", XK_LYDWDM);
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
        String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP统一受理平台：受理接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP统一受理平台：受理接口结束时间：" + endtime);
		log.info("YFMAP统一受理平台：受理接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getuapacc end.");
		return;
	}

	
	/**
	* 3_办件回传 - 4、办件库：受理
	* @param	PROJID	String	21位申报号。
	* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
	* @param	ACCEPT_MAN	String	受理人。
	* @param	AcceptTime	DateTime	受理时间。
	* @param	PROMISEVALUE	Number	承诺工作日。
	* @param	CREATE_TIME 可选	DateTime	数据产生时间。 默认值: 当前时间
	* @param	DATAVERSION 可选	Number	数据版本。 默认值: 1
	* @param	DOACTION	Number	动作 0:受理，1:不予受理。允许值: 0, 1
	* @param	BELONGSYSTEM	String	系统对接码。
	* @param	Hander_Deptid	String	受理人员所属部门编码。
	* @param	Hander_Deptname	String	受理人员所属部门名称。
	* @param	OutSystemID	String	外部系统ID。
	* @param	AREACODE	String	区域(330201:市本级)。
	* @param	SERVICE_DEPTID	String	事项终审部门编码。
	 * @param	XK_LYDW	String	数据来源单位。
	 * @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
 */
@RequestMapping(value = "/getappacc.{ext}", method = RequestMethod.POST)
public void getappacc(	
										String 	PROJID,
										String 	SERVICECODE,
										String 	ACCEPT_MAN,
										String 	AcceptTime,
										String 	PROMISEVALUE,
										String 	CREATE_TIME,
										String 	DATAVERSION,
										String 	DOACTION,
										String 	BELONGSYSTEM,
										String 	Hander_Deptid,
										String 	Hander_Deptname,
										String 	OutSystemID,
										String 	AREACODE,
										String 	SERVICE_DEPTID,
										String 	XK_LYDW,
										String 	XK_LYDWDM,
		HttpServletResponse response) throws Exception {
	log.info(Constants.LOG_HEAD + "getappacc begin.");
	long starttime = System.currentTimeMillis();
	log.info("YFMAP办件库：受理接口开始时间："+ starttime);
	HashMap<String, String> params = new HashMap<String, String>();
	String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/accept";
    params.put("PROJID", PROJID);
	params.put("SERVICECODE", SERVICECODE);
	params.put("ACCEPT_MAN", ACCEPT_MAN);
	params.put("AcceptTime", AcceptTime);
	params.put("PROMISEVALUE", PROMISEVALUE);
	params.put("CREATE_TIME", CREATE_TIME);
	params.put("DATAVERSION", DATAVERSION);
	params.put("DOACTION", DOACTION);
	params.put("BELONGSYSTEM", BELONGSYSTEM);
	params.put("Hander_Deptid", Hander_Deptid);
	params.put("Hander_Deptname", Hander_Deptname);
	params.put("OutSystemID", OutSystemID);
	params.put("AREACODE", AREACODE);
	params.put("SERVICE_DEPTID", SERVICE_DEPTID);
	params.put("XK_LYDW", XK_LYDW);
	params.put("XK_LYDWDM", XK_LYDWDM);
	SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
    String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
	log.debug("YFMAP办件库：受理接口返回结果：" + result);
	response.getOutputStream().write(result.getBytes("gb18030"));
	long endtime = System.currentTimeMillis();
	long time = endtime - starttime;
	log.info("YFMAP办件库：受理接口结束时间：" + endtime);
	log.info("YFMAP办件库：受理接口耗时：" + time);
	log.info(Constants.LOG_HEAD + "getappacc end.");
	return;
}


	/**
	* 3_办件回传 - 5、办件库：办理
	* @param	PROJID	String	21位申报号。
	* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
	* @param	DOUSERNAME	String	办理人姓名。
	* @param	START_TIME	DateTime	环节开始时间。
	* @param	END_TIME	DateTime	环节结束时间。
	* @param	CREATE_TIME 可选	DateTime	数据产生时间。默认值: 当前时间
	* @param	DATAVERSION 可选	Number	数据版本。默认值: 1
	* @param	DOACTION	Number	业务动作 0:提交，1:退回。允许值: 0, 1
	* @param	BELONGSYSTEM	String	系统对接码。
	* @param	Hander_Deptid	String	办理人员所属部门编码。
	* @param	Hander_Deptname	String	办理人员所属部门名称。
	* @param	OutSystemID	String	外部系统ID。
	* @param	AREACODE	String	区域(330201:市本级)。
	* @param	SERVICE_DEPTID	String	事项终审部门编码。
	* @param	XK_LYDW	String	数据来源单位。
	* @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
	* @param	Node_Name	String	办理环节名称。
	* @param	OPINION	String	审批意见。
	* @param	REMARK 可选	String	备注。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getapphan.{ext}", method = RequestMethod.POST)
	public void getapphan(	
											String 	PROJID,
											String 	SERVICECODE,
											String 	DOUSERNAME,
											String 	START_TIME,
											String 	END_TIME,
											String 	CREATE_TIME,
											String 	DATAVERSION,
											String 	DOACTION,
											String 	BELONGSYSTEM,
											String 	Hander_Deptid,
											String 	Hander_Deptname,
											String 	OutSystemID,
											String 	AREACODE,
											String 	SERVICE_DEPTID,
											String 	XK_LYDW,
											String 	XK_LYDWDM,
											String 	Node_Name,
											String 	OPINION,
											String 	REMARK,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getapphandler begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办理接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/handler";
		params.put("PROJID", PROJID);
		params.put("SERVICECODE", SERVICECODE);
		params.put("DOUSERNAME", DOUSERNAME);
		params.put("START_TIME", START_TIME);
		params.put("END_TIME", END_TIME);
		params.put("CREATE_TIME", CREATE_TIME);
		params.put("DATAVERSION", DATAVERSION);
		params.put("DOACTION", DOACTION);
		params.put("BELONGSYSTEM", BELONGSYSTEM);
		params.put("Hander_Deptid", Hander_Deptid);
		params.put("Hander_Deptname", Hander_Deptname);
		params.put("OutSystemID", OutSystemID);
		params.put("AREACODE", AREACODE);
		params.put("SERVICE_DEPTID", SERVICE_DEPTID);
		params.put("XK_LYDW", XK_LYDW);
		params.put("XK_LYDWDM", XK_LYDWDM);
		params.put("Node_Name", Node_Name);
		params.put("OPINION", OPINION);
		params.put("REMARK", REMARK);
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP办件库：办理接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：办理接口结束时间：" + endtime);
		log.info("YFMAP办件库：办理接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getapphandler end.");
		return;
	}
	
	
	/**
	* 3_办件回传 - 6、统一受理平台：办结
	* @param	PROJID	String	21位申报号。
	* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
	* @param	TRANSACT_USER	String	办结人姓名。
	* @param	TRANSACT_TIME	DateTime	办结时间。
	* @param	TRANSACT_RESULT	String	办理结果(办结、转报办结、作废办结、退件)。
	* @param	TRANSACT_DESCRIBE	String	办理结果描述(准予许可、不予许可等)。
	* @param	RESULT_CODE	String	结果编号(证照编号)。
	* @param	CREATE_TIME 可选	DateTime	数据产生时间。默认值: 当前时间
	* @param	DATAVERSION 可选	Number	数据版本。默认值: 1
	* @param	DOACTION	Number	办结动作 0：办结，1：作废办结，3：退件。允许值: 0, 1, 3
	* @param	BELONGSYSTEM	String	系统对接码。
	* @param	Hander_Deptid	String	办结人员所属部门编码。
	* @param	Hander_Deptname	String	办结人员所属部门名称。
	* @param	OutSystemID	String	外部系统ID。
	* @param	AREACODE	String	区域(330201:市本级)。
	* @param	SERVICE_DEPTID	String	事项终审部门编码。
	* @param	XK_LYDW	String	数据来源单位。
	* @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
	* @param	REMARK 可选	String	备注。
	* @param	XK_XKJGDM	String	许可机关统一社会信用代码 - 填写做出行政许可决定的各级行政许可决定机关的统一社会信用代码。。
	* @param	XK_XKWS 可选	String	行政许可决定文书名称 - 填写行政许可决定文书编号，例如“发改财金〔2015〕XXX号)”。
	* @param	XK_XKZS 可选	String	数据来源单位统一社会信用代码。
	* @param	XK_YXQZ	DateTime	有效期自 - 填写行政许可决定的开始执行日期，格式为YYYY/MM/DD。。
	* @param	XK_YXQZI	DateTime	有效期至 - 填写行政许可决定的截止日期，格式为YYYY/MM/DD，2099/12/31的含义为长期。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getupover.{ext}", method = RequestMethod.POST)
	public void getupover(	
										String 	PROJID,
										String 	SERVICECODE,
										String 	TRANSACT_USER,
										String 	TRANSACT_TIME,
										String 	TRANSACT_RESULT,
										String 	TRANSACT_DESCRIBE,
										String 	RESULT_CODE,
										String 	CREATE_TIME,
										String 	DATAVERSION,
										String 	DOACTION,
										String 	BELONGSYSTEM,
										String 	Hander_Deptid,
										String 	Hander_Deptname,
										String 	OutSystemID,
										String 	AREACODE,
										String 	SERVICE_DEPTID,
										String 	XK_LYDW,
										String 	XK_LYDWDM,
										String 	REMARK,
										String 	XK_XKJGDM,
										String 	XK_XKWS,
										String 	XK_XKZS,
										String 	XK_YXQZ,
										String 	XK_YXQZI,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getuapover begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP统一受理平台：办结接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/UAP/over";
		params.put("PROJID", PROJID);
		params.put("SERVICECODE", SERVICECODE);
		params.put("TRANSACT_USER", TRANSACT_USER);
		params.put("TRANSACT_TIME", TRANSACT_TIME);
		params.put("TRANSACT_RESULT", TRANSACT_RESULT);
		params.put("TRANSACT_DESCRIBE", TRANSACT_DESCRIBE);
		params.put("RESULT_CODE", RESULT_CODE);
		params.put("CREATE_TIME", CREATE_TIME);
		params.put("DATAVERSION", DATAVERSION);
		params.put("DOACTION", DOACTION);
		params.put("BELONGSYSTEM", BELONGSYSTEM);
		params.put("Hander_Deptid", Hander_Deptid);
		params.put("Hander_Deptname", Hander_Deptname);
		params.put("OutSystemID", OutSystemID);
		params.put("AREACODE", AREACODE);
		params.put("SERVICE_DEPTID", SERVICE_DEPTID);
		params.put("XK_LYDW", XK_LYDW);
		params.put("XK_LYDWDM", XK_LYDWDM);
		params.put("REMARK", REMARK);
		params.put("XK_XKJGDM", XK_XKJGDM);
		params.put("XK_XKWS", XK_XKWS);
		params.put("XK_XKZS", XK_XKZS);
		params.put("XK_YXQZ", XK_YXQZ);
		params.put("XK_YXQZI", XK_YXQZI);
		params.put("ResultLicenceName", "无");
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP统一受理平台：办结接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP统一受理平台：办结接口结束时间：" + endtime);
		log.info("YFMAP统一受理平台：办结接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getuapover end.");
		return;
	}
	
	
	/**
	* 3_办件回传 - 7、办件库：办结
	* @param	PROJID	String	21位申报号。
	* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
	* @param	TRANSACT_USER	String	办结人姓名。
	* @param	TRANSACT_TIME	DateTime	办结时间。
	* @param	TRANSACT_RESULT	String	办理结果(办结、转报办结、作废办结、退件)。
	* @param	TRANSACT_DESCRIBE	String	办理结果描述(准予许可、不予许可等)。
	* @param	RESULT_CODE	String	结果编号(证照编号)。
	* @param	CREATE_TIME 可选	DateTime	数据产生时间。默认值: 当前时间
	* @param	DATAVERSION 可选	Number	数据版本。默认值: 1
	* @param	DOACTION	Number	办结动作 0：办结，1：作废办结，3：退件。允许值: 0, 1, 3
	* @param	BELONGSYSTEM	String	系统对接码。
	* @param	Hander_Deptid	String	办结人员所属部门编码。
	* @param	Hander_Deptname	String	办结人员所属部门名称。
	* @param	OutSystemID	String	外部系统ID。
	* @param	AREACODE	String	区域(330201:市本级)。
	* @param	SERVICE_DEPTID	String	事项终审部门编码。
	* @param	XK_LYDW	String	数据来源单位。
	* @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
	* @param	REMARK 可选	String	备注。
	* @param	XK_XKJGDM	String	许可机关统一社会信用代码 - 填写做出行政许可决定的各级行政许可决定机关的统一社会信用代码。。
	* @param	XK_XKWS 可选	String	行政许可决定文书名称 - 填写行政许可决定文书编号，例如“发改财金〔2015〕XXX号)”。
	* @param	XK_XKZS 可选	String	数据来源单位统一社会信用代码。
	* @param	XK_YXQZ	DateTime	有效期自 - 填写行政许可决定的开始执行日期，格式为YYYY/MM/DD。。
	* @param	XK_YXQZI	DateTime	有效期至 - 填写行政许可决定的截止日期，格式为YYYY/MM/DD，2099/12/31的含义为长期。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getappo.{ext}", method = RequestMethod.POST)
	public void getappo(	
										String 	PROJID,
										String 	SERVICECODE,
										String 	TRANSACT_USER,
										String 	TRANSACT_TIME,
										String 	TRANSACT_RESULT,
										String 	TRANSACT_DESCRIBE,
										String 	RESULT_CODE,
										String 	CREATE_TIME,
										String 	DATAVERSION,
										String 	DOACTION,
										String 	BELONGSYSTEM,
										String 	Hander_Deptid,
										String 	Hander_Deptname,
										String 	OutSystemID,
										String 	AREACODE,
										String 	SERVICE_DEPTID,
										String 	XK_LYDW,
										String 	XK_LYDWDM,
										String 	REMARK,
										String 	XK_XKJGDM,
										String 	XK_XKWS,
										String 	XK_XKZS,
										String 	XK_YXQZ,
										String 	XK_YXQZI,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getappover begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办结接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/over";
		params.put("PROJID", PROJID);
		params.put("SERVICECODE", SERVICECODE);
		params.put("TRANSACT_USER", TRANSACT_USER);
		params.put("TRANSACT_TIME", TRANSACT_TIME);
		params.put("TRANSACT_RESULT", TRANSACT_RESULT);
		params.put("TRANSACT_DESCRIBE", TRANSACT_DESCRIBE);
		params.put("RESULT_CODE", RESULT_CODE);
		params.put("CREATE_TIME", CREATE_TIME);
		params.put("DATAVERSION", DATAVERSION);
		params.put("DOACTION", DOACTION);
		params.put("BELONGSYSTEM", BELONGSYSTEM);
		params.put("Hander_Deptid", Hander_Deptid);
		params.put("Hander_Deptname", Hander_Deptname);
		params.put("OutSystemID", OutSystemID);
		params.put("AREACODE", AREACODE);
		params.put("SERVICE_DEPTID", SERVICE_DEPTID);
		params.put("XK_LYDW", XK_LYDW);
		params.put("XK_LYDWDM", XK_LYDWDM);
		params.put("REMARK", REMARK);
		params.put("XK_XKJGDM", XK_XKJGDM);
		params.put("XK_XKWS", XK_XKWS);
		params.put("XK_XKZS", XK_XKZS);
		params.put("XK_YXQZ", XK_YXQZ);
		params.put("XK_YXQZI", XK_YXQZI);
		params.put("ResultLicenceName", "无");
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP办件库：办结接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：办结接口结束时间：" + endtime);
		log.info("YFMAP办件库：办结接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getappover end.");
		return;
	}
	
	
	/**
	* 3_办件回传 - 8、办件库：文档附件表
	* @param	PROJID	String	21位申报号。
	* @param	BELONGTO	String	投资项目唯一编码。
	* @param	REMARK 可选	String	备注。
	* @param	TYPE	String	附件产生阶段[01：申报阶段，02：受理阶段，03：审批阶段，04：办结阶段，05：送达阶段]。允许值: 01, 02, 03, 04, 05
	* @param	NAME	String	附件名称。
	* @param	FILEPATH	String	附件路径。
	* @param	BELONGSYSTEM	String	系统对接码。
	* @param	AREACODE	String	区域(330201:市本级)。
	* @param	CREATE_TIME 可选	DateTime	数据产生时间。默认值: 当前时间
	* @param	DATAVERSION 可选	Number	数据版本。默认值: 1
	* @param	SERVICECODE	String	权力事项编码,权力事项的基本码。
	* @param	SERVICE_DEPTID	String	事项终审部门编码。
	* @param	OutSystemID	String	外部系统ID。
	* @param	XK_LYDW	String	数据来源单位。
	* @param	XK_LYDWDM	String	数据来源单位统一社会信用代码。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getappfile.{ext}", method = RequestMethod.POST)
	public void getappfile(	
										String 	PROJID,
										String 	BELONGTO,
										String 	REMARK,
										String 	TYPE,
										String 	NAME,
										String 	FILEPATH,
										String 	BELONGSYSTEM,
										String 	AREACODE,
										String 	CREATE_TIME,
										String 	DATAVERSION,
										String 	SERVICECODE,
										String 	SERVICE_DEPTID,
										String 	OutSystemID,
										String 	XK_LYDW,
										String 	XK_LYDWDM,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getappfile begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：文档附件表接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/formfile";
		params.put("PROJID", PROJID);
		params.put("BELONGTO", BELONGTO);
		params.put("REMARK", REMARK);
		params.put("TYPE", TYPE);
		params.put("NAME", NAME);
		params.put("FILEPATH", FILEPATH);
		params.put("BELONGSYSTEM", BELONGSYSTEM);
		params.put("AREACODE", AREACODE);
		params.put("CREATE_TIME", CREATE_TIME);
		params.put("DATAVERSION", DATAVERSION);
		params.put("SERVICECODE", SERVICECODE);
		params.put("SERVICE_DEPTID", SERVICE_DEPTID);
		params.put("OutSystemID", OutSystemID);
		params.put("XK_LYDW", XK_LYDW);
		params.put("XK_LYDWDM", XK_LYDWDM);
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.debug("YFMAP办件库：文档附件表接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：文档附件表接口结束时间：" + endtime);
		log.info("YFMAP办件库：文档附件表接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getappfile end.");
		return;
	}

	/**
	* 办件中心-办件受理服务
	* @param	areaCode		String	部门行政区划编码。
	* @param	appId			String	政务总线统一分配应用`ID。
	* @param	memo 			String	备注。
	* @param	projId			String	办件统一赋码ID
	* @param	extInfo			String		扩展字段信息（可选）。
	* @param	operatorName	String	办理人员姓名。
	* @param	operatorUid	    String	办理人员编号。
	* @param	deptCode		String	受理部门编码。
	* @param	deptName 		String	受理部门名称
	* @param	gmtAccept 		String	办件受理时间
	* @param	attachMentVOs	String    附件。
	* @param	attName			String	附件名称。
	* @param	attPath			String	附件路径。
	* @param	memo			String	备注。
	* @param	promiseTime		String	承诺办结时间。
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getaccept.{ext}", method = RequestMethod.POST)
	public void getaccept(
										String 	areaCode,
										String 	appId,
										String 	memo,
										String 	projId,
										String 	extInfo,
										String 	operatorName,
										String 	operatorUid,
										String 	deptCode,
										String 	deptName,
										String 	gmtAccept,
										String 	attachMentVOs,
										String 	promiseTime,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getappfile begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办件受理服务接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8087/zwztapi/accept.do";
		params.put("areaCode", areaCode);
		params.put("appId", appId);
		params.put("memo", memo);
		params.put("projId", projId);
		params.put("extInfo", extInfo);
		params.put("operatorName", operatorName);
		params.put("operatorUid", operatorUid);
		params.put("deptCode", deptCode);
		params.put("deptName", deptName);
		params.put("gmtAccept", gmtAccept);
		params.put("attachMentVOs", attachMentVOs);
		params.put("promiseTime", promiseTime);
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		log.debug("YFMAP办件库：办件受理服务接口返回结果URL：" + url);
		log.debug("YFMAP办件库：办件受理服务接口返回结果params：" + params);
		String result = simpleHttpMessageUtil.sendPost(url, params,"");
		log.debug("YFMAP办件库：办件受理服务接口返回结果：" + result);
		log.debug("YFMAP办件库：办件受理服务接口返回结果result.getByte：" + result.getBytes("iso-8859-1"));
		result = new String(result.getBytes("iso-8859-1"),"utf-8");
		log.debug("YFMAP办件库：办件受理服务接口返回结果iso-8859-1：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		//log.debug(new String(result.getBytes("iso-8859-1"),"utf-8"));

		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：办件受理服务接口结束时间：" + endtime);
		log.info("YFMAP办件库：办件受理服务接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getappfile end.");
		return;
	}

	/**
	* 办件中心-办件完结服务
	* @param	appId			String	政务总线统一分配应用ID。
	* @param	memo 			String	备注。
	* @param	projId			String	办件统一赋码ID。
	* @param	extInfo			String	扩展字段信息（可选）。
	* @param	operatorUid		String	办理人员编号。
	* @param	operatorName	String	办理人员姓名。
	* @param	deptName 		String	办理部门名称
	* @param	deptCode	    String	办理部门编码。
	* @param	areaCode 		String	收件部门所属行政区划编码
	* @param	gmtService 		String	办结时间
	* @param	result			String  办理结果。
	* @param	resultCode		String	办件结果证照编号。
	* @param	attachMentVOs	String	附件。
	* @param	resultDesc		String	办件结果描述信息。
	 * @throws Exception
	 * @return results 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/getfinish.{ext}", method = RequestMethod.POST)
	public void getfinish(
										String 	appId,
										String 	memo,
										String 	projId,
										String 	extInfo,
										String 	operatorUid,
										String 	operatorName,
										String 	deptName,
										String 	deptCode,
										String 	areaCode,
										String 	gmtService,
										String 	result,
										String 	resultCode,
										String 	attachMentVOs,
										String 	resultDesc,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getappfile begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办件完结服务接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8087/zwztapi/finish.do";
		params.put("appId", appId);
		params.put("memo", memo);
		params.put("projId", projId);
		params.put("extInfo", extInfo);
		params.put("operatorUid", operatorUid);
		params.put("operatorName", operatorName);
		params.put("deptName", deptName);
		params.put("deptCode", deptCode);
		params.put("areaCode", areaCode);
		params.put("gmtService", gmtService);
		params.put("result", result);
		params.put("resultCode", resultCode);
		params.put("attachMentVOs", attachMentVOs);
		params.put("resultDesc", resultDesc);
		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String results = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		results = new String(results.getBytes("iso-8859-1"),"utf-8");
		log.debug("YFMAP办件库：办件完结服务接口返回结果：" + results);
		response.getOutputStream().write(results.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：办件完结服务接口结束时间：" + endtime);
		log.info("YFMAP办件库：办件完结服务接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getappfile end.");
		return;
	}

	/**
	* 办件中心-办件接收回调服务
	* @param	memo 			String	备注。
	* @param	projectName		String	申报项目具体名称。
	* @param	projectNature	String	项目性质。
	* @param	projId			String	办件统一赋码ID。
	* @param	recvDeptCode	String	收件部门所属编码。
	* @param	recvDeptName 	String	收件部门名称
	* @param	recvUserId 		String	创建用户ID
	* @param	recvUserName	String  创建用户名称。
	* @param	recvUserType	String	创建用户类型。
	* @param	relBizId		String	联办业务标识。
	* @param	suffInfoList	String	办件材料信息。
	* @param	affairType		String	办件类型
	* @param	affFormInfo		String	办件表单信息
	* @param	appId			String	政务总线统一分配应用ID。
	* @param	applicantVO		String	申请者信息
	* @param	applyOrigin		String	申报来源
	* @param	approveType		String	审批性质
	* @param	areaCode 		String	收件部门所属行政区划编码
	* @param	bizType			String	业务类型
	* @param	deptCode		String	事项终审部门所属编码
	* @param	execDeptOrgCode String	执行部门统一社会信用代码
	* @param	extInfo			String	扩展字段信息（可选）。
	* @param	gmtApply		String	申报时间
	* @param	matterCode		String	权力事项编码
	 * @throws Exception
	 * @return results 返回代码 resultmsg 返回信息
	*/
	@RequestMapping(value = "/receive.{ext}", method = RequestMethod.POST)
	public void getreceive(
										String 	memo,
										String 	projectName,
										String 	projectNature,
										String	projId,
										String 	recvDeptCode,
										String 	recvDeptName,
										String 	recvUserId,
										String 	recvUserName,
										String 	recvUserType,
										String 	relBizId,
										String 	suffInfoList,
										String 	affairType,
										String 	affFormInfo,
										String 	appId,
										String 	applicantVO,
										String 	applyOrigin,
										String 	approveType,
										String 	areaCode,
										String 	bizType,
										String 	deptCode,
										String 	execDeptOrgCode,
										String 	extInfo,
										String 	gmtApply,
										String 	matterCode,
		HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getappfile begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办件接收回调服务接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8090/api/NingBoUnifiedAcceptPlatform/BJHC/ApprovalLib/formfile";
		params.put("memo", memo);
		params.put("projectName", projectName);
		params.put("projectNature", projectNature);
		params.put("projId", projId);
		params.put("recvDeptCode", recvDeptCode);
		params.put("recvDeptName", recvDeptName);
		params.put("recvUserId", recvUserId);
		params.put("recvUserName", recvUserName);
		params.put("recvUserType", recvUserType);
		params.put("relBizId", relBizId);
		params.put("suffInfoList", suffInfoList);
		params.put("affairType", affairType);
		params.put("affFormInfo", affFormInfo);
		params.put("appId", appId);
		params.put("applicantVO", applicantVO);
		params.put("applyOrigin", applyOrigin);
		params.put("approveType", approveType);
		params.put("areaCode", areaCode);
		params.put("bizType", bizType);
		params.put("deptCode", deptCode);
		params.put("execDeptOrgCode", execDeptOrgCode);
		params.put("extInfo", extInfo);
		params.put("gmtApply", gmtApply);
		params.put("matterCode", matterCode);

		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String results = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		results = new String(results.getBytes("iso-8859-1"),"utf-8");
		log.debug("YFMAP办件库：办件接收回调服务接口返回结果：" + results);
		response.getOutputStream().write(results.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：办件接收回调服务接口结束时间：" + endtime);
		log.info("YFMAP办件库：办件接收回调服务接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getappfile end.");
		return;
	}

	/**
	 * 办件中心-办件评价服务
	 * @param	areaCode		String	部门行政区划编码。
	 * @param	areaName		String	areaCode对应的行政区域名称。
	 * @param	deptCode 		String	政府办事部门18位统一社会信用编码。
	 * @param	taskCode		String	事项统一编码
	 * @param	taskHandleItem	String	业务办理项编码 如果该事项为业务办理项则必须上传(浙江目前不传)。
	 * @param	taskName		String	事项名称。
	 * @param	subMatter	    String	事项主题 如 生育收养 户籍办理。
	 * @param	projectNo		String	办件统一编号。
	 * @param	proStatus 		String	办件状态(代办理=1 办理中=2 已完结=3)
	 * @param	proDepartCode 	String	省统一的部门编号
	 * @param	proDepart  		String  受理部门名称。
	 * @param	proManager		String	经办人名称。
	 * @param	pf				String	评价渠道(pc端=1,移动端=2,二维码=3,政务大厅平板电脑=4,政务大厅其他终端=5,短信=6,系统默认评价=7)。
	 * @param	certkeyGOV		String	公共区身份证号码字段:个人用户填写身份证号,法人填写统一社会信用代码。
	 * @param	userName		String	个人用户填写身份证号,法人填写企业名称。
	 * @param	userProp		String	评价用户属性(个人=1 法人=2)。
	 * @param	satisfaction	String	整体满意度(1非常不满意2不满意3基本满意4满意5非常满意)。
	 * @param	evalDetail		String	测评内容代码列表(多个代码值用英文逗号隔开)。
	 * @param	writingEvaluation		String	文字评价。
	 * @param	assessTime		String	评价时间(格式:yyyy-MM-dd HH:mm:ss)。
	 * @param	assessNumber 	String	评价次数(首次评价=1,二次评价=2)
	 * @param	appStatus 		String	审核状态
	 * @param	promiseTime  	String 承诺件=1,即办蒋=2。
	 * @param	acceptDate		String	收件时间(格式:yyyy-MM-dd HH:mm:ss)。
	 * @param	dataSource		String	三位数字 浙江省填133
	 * @throws Exception
	 * @return result 返回代码 resultmsg 返回信息
	 */
	@RequestMapping(value = "/report.{ext}", method = RequestMethod.POST)
	public void getofflinereport(
			String 	areaCode,
			String  areaName,
			String 	deptCode,
			String 	taskCode,
			String 	taskHandleItem,
			String 	taskName,
			String 	subMatter,
			String 	projectNo,
			String 	proStatus,
			String  proDepartCode,
			String 	proDepart,
			String 	proManager,
			String 	pf,
			String 	certkeyGOV,
			String 	userName,
			String 	userProp,
			String 	satisfaction,
			String  evalDetail,
			String 	writingEvaluation,
			String 	assessTime,
			String 	assessNumber,
			String 	appStatus,
			String 	promiseTime,
			String 	acceptDate,
			String 	dataSource,
			HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "getofflinereport begin.");
		long starttime = System.currentTimeMillis();
		log.info("YFMAP办件库：办件评价服务接口开始时间："+ starttime);
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:8087/zwztapi/offlinereport.do";
		params.put("areaCode", areaCode);
		params.put("areaName", areaName);
		params.put("deptCode", deptCode);
		params.put("taskCode", taskCode);
		params.put("taskHandleItem", taskHandleItem);
		params.put("taskName", taskName);
		params.put("subMatter", subMatter);
		params.put("projectNo", projectNo);
		params.put("proStatus", proStatus);
		params.put("proDepartCode", proDepartCode);
		params.put("proDepart", proDepart);
		params.put("proManager", proManager);
		params.put("pf", pf);
		params.put("certkeyGOV", certkeyGOV);
		params.put("userName", userName);
		params.put("userProp", userProp);
		params.put("satisfaction", satisfaction);
		params.put("evalDetail", evalDetail);
		params.put("writingEvaluation", writingEvaluation);
		params.put("assessTime", assessTime);
		params.put("assessNumber", assessNumber);
		params.put("appStatus", appStatus);
		params.put("promiseTime", promiseTime);
		params.put("acceptDate", acceptDate);
		params.put("dataSource", dataSource);

		SimpleHttpMessageUtilZJW simpleHttpMessageUtil= new SimpleHttpMessageUtilZJW();
		String result = simpleHttpMessageUtil.sendPost(url, params,"UTF-8");
		result = new String(result.getBytes("iso-8859-1"),"utf-8");
		log.debug("YFMAP办件库：评价服务接口返回结果：" + result);
		response.getOutputStream().write(result.getBytes("gb18030"));
		long endtime = System.currentTimeMillis();
		long time = endtime - starttime;
		log.info("YFMAP办件库：评价服务接口结束时间：" + endtime);
		log.info("YFMAP办件库：评价服务接口耗时：" + time);
		log.info(Constants.LOG_HEAD + "getofflinereport end.");
		return;
	}


}
