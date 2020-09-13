package com.yondervision.yfmap.util;

import java.util.HashMap;

import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.common.security.AES;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *	使用说明：
 *	类中脱敏代码：
		String needDesensitization = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "Desensitization001"+form.getCenterId());
		String desensitization = form.getDesensitization();
		if(!CommonUtil.isEmpty(desensitization.trim())){
			JSONObject rules = JSONObject.fromObject(desensitization);
			DesensitizationUtil.getInstance(rules).doDesensitization(needDesensitization, resultMap);
		}
	配置文件中配置：
		Desensitization00100087500=1004:surplusAccount&&1006:unitaccnum
 */

/** 
* @ClassName: DesensitizationUtil 
* @Description: 脱敏处理类
* @date 2017年6月20日 下午2:59:07   
*/ 
public class DesensitizationUtil {
	 String PROPERTIES_FILE_NAME = "properties.properties";
	//中心ID，将centerId设置为可变。
	public static String centerId = "00087500";
	private JSONObject rules;
	//ture-代表rules为向YB请求数据获取;false-rules为YB请求上送时的数据
	private boolean isYB;
	private DesensitizationUtil(){
		isYB = true;
		rules = getDesensitizationRules();
	}
	private DesensitizationUtil(JSONObject rules){
		isYB = false;
		this.rules = rules;
	}
	/**
	 * 通过请求YB获取所有rules
	 * @return
	 */
	public static DesensitizationUtil getInstance(){
		return new DesensitizationUtil();
		
	}
	
	/**
	 * 通过请求YB单条rule
	 * @param ruleId
	 * @return
	 */
	public static DesensitizationUtil getInstance(String ruleId){
		return new DesensitizationUtil();
		
	}
	/**
	 * 通过YB上送自带rules
	 * @return
	 */
	public static DesensitizationUtil getInstance(JSONObject rules){
		return new DesensitizationUtil(rules);
		
	}
	
	
	public  JSONObject getDesensitizationRules() {
		YbmapMessageUtil yb = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi05901.json";
		System.out.println("YBMAP url ：" + url);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("centerId", centerId);
		map.put("desensitizationId", "");
		map.put("ispageing", "0");
		map.put("pagenum", "");
		map.put("pagesize", "");
		String rm = yb.post(url, map);

		JSONObject reJson = JSONObject.fromObject(rm);
		JSONArray ruleArray = reJson.getJSONArray("rows");
		JSONObject rules = new JSONObject();
		for (int i = 0; i < ruleArray.size(); i++) {
			JSONObject temp = ruleArray.getJSONObject(i);
			rules.put(temp.get("desensitizationid"), temp);
		}
		return rules;

	}

	public  JSONObject getOneDesensitizationRule(String ruleId) {
		YbmapMessageUtil yb = new YbmapMessageUtil();
		String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "YBMAPURL").trim() + "appapi05901.json";
		System.out.println("YBMAP url ：" + url);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("centerId", centerId);
		map.put("desensitizationId", ruleId);
		map.put("ispageing", "");
		map.put("pagenum", "");
		map.put("pagesize", "");
		String rm = yb.post(url, map);
		JSONObject reJson = JSONObject.fromObject(rm);
		JSONArray ruleArray = reJson.getJSONArray("rows");
		JSONObject rules = new JSONObject();
		for (int i = 0; i < ruleArray.size(); i++) {
			JSONObject temp = ruleArray.getJSONObject(i);
			rules.put(temp.get("desensitizationid"), temp);
		}
		return rules;
	}

	/**
	 * 将needDesensitStr进行敏脱
	 * 
	 * @param needDesensitStr
	 *            需要脱敏的字符串
	 * @param firstnum
	 *            开始脱敏位置
	 * @param tailnum
	 *            末尾长度
	 */
	private String getDesensitization(String needDesensitStr, int firstnum, int tailnum, String replacechar) {
		StringBuffer value = new StringBuffer();
		int size = needDesensitStr.length();
		value.append(needDesensitStr.substring(0, firstnum));
		for (int i = 0; i < (size - firstnum - tailnum); i++) {
			value.append(replacechar);
		}
		value.append(needDesensitStr.substring(size - tailnum));
		System.out.println("字段脱敏后："+value);
		return value.toString();
	}
	
	/**
	 * 将校验是否脱敏写在具体需要处理类里
	 * 不耗时，只是需要在子类具体指定
	 * 若后续出现新增规则实时变更，则建议将needDesensitization写在配置文件中。即不需重新编译即可实现
	 * @param needDesensitization 脱敏规则#要脱敏字段|脱敏规则#要脱敏字段|  例如：1001#tel|1006#surplusAccount
	 * @param map
	 * @param rules
	 */
	public void doDesensitization(String needDesensitization,HashMap<String, String> map){
		String[] str = needDesensitization.split("&&");
		if(!isYB){
			OptYbReqRules();
		}
		
		System.out.println("rules:"+rules);
		for(int i=0;i<str.length;i++){
			String[] temp = str[i].split(":");
			JSONObject rulesDetail = rules.getJSONObject(temp[0]);
			//取到对应规则才进行敏脱，没取到就不做处理
			if(rulesDetail!=null)
			optIsExist(map,temp[1],rulesDetail);
		}
		System.out.println("处理结束后map"+map);
	}
	
	private void OptYbReqRules() {
//		JSONObject reJson = rules;
//		JSONArray ruleArray = reJson.getJSONArray("rows");
//		rules = new JSONObject();
//		for (int i = 0; i < ruleArray.size(); i++) {
//			JSONObject temp = ruleArray.getJSONObject(i);
//			rules.put(temp.get("desensitizationid"), temp);
//		}
	}
	
	private HashMap<String, String> optIsExist(HashMap<String, String> map, String key,JSONObject rulesDetail) {
		try{
			System.out.println(map);
			if(map.containsKey(key)){
			String value = (String) map.get(key);
			int firstnum = Integer.parseInt(rulesDetail.getString("firstnum"));
			int tailnum = Integer.parseInt(rulesDetail.getString("tailnum"));
			String replacechar = rulesDetail.getString("replacechar");
			//脱敏规则是否启用
			String isDesensitizationFlag = rulesDetail.optString("freeuse1");
			System.out.println("[isDesensitizationFlag]"+isDesensitizationFlag);
			if(isDesensitizationFlag.equals("是")||isDesensitizationFlag.equals("1"))
			map.put(key, getDesensitization(value, firstnum, tailnum, replacechar));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 将返回信息直接进行脱敏
	 * 缺点：随着脱敏规则的增加，所需时间越来越多
	 * FIXME 暂时不使用此方法
	 * @deprecated
	 * @param map
	 * @param rules
	 */
	void doDesensitization2(HashMap<String, String> map) {
		JSONObject json;
		// 手机号
		json = rules.getJSONObject("1001");
		optIsExist(map,"tel",json);
		// 银行卡号
		json = rules.getJSONObject("1002");
		if (map.containsKey("tel")) {

		}
		// 身份证号
		json = rules.getJSONObject("1003");
		if (map.containsKey("tel")) {

		}
		// 职工公积金账号
		json = rules.getJSONObject("1004");
		if (map.containsKey("tel")) {

		}
		// 借款合同号
		json = rules.getJSONObject("1005");
		if (map.containsKey("tel")) {

		}
		// 单位公积金账号
		json = rules.getJSONObject("1006");
		if (map.containsKey("surplusAccount")) {

		}
		// 个人客户号
		json = rules.getJSONObject("1007");
		if (map.containsKey("tel")) {

		}
		// 单位客户号
		json = rules.getJSONObject("1008");
		if (map.containsKey("unitaccnum")) {

		}
		// 贷款账号
		json = rules.getJSONObject("1008");
		if (map.containsKey("unitaccnum")) {

		}
	}
	
	String setAesAndEncrypt(String str){
		AES aes = null;
		try {
			aes = new AES("00087500", "10", null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return aes.encrypt(str.getBytes());
	}
	
//	JSstringToJsonArray(String list){
//		String[] strArr = list.split("},");
//		for(int i=0;i<strArr.length;i++){
//			String temp[] = strArr[i].split(",");
//			
//		}
//	}
}
