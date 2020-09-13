/**
 * 
 */
package com.yondervision.yfmap.controller;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.mess.YbmapMessageUtil;
import com.yondervision.yfmap.form.AppApi00225Form;
import com.yondervision.yfmap.form.AppApi00250Form;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
import com.yondervision.yfmap.util.RSAUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.yondervision.yfmap.common.Constants.PROPERTIES_FILE_NAME;

/**
 * @author luolin
 *
 */
@Controller
public class AppApi00250Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/** 指定公钥存放文件 */
	String PUBLIC_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "public_key_shengting").trim();

	/** 指定私钥存放文件 */
	String PRIVATE_KEY=PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "private_key_shengting").trim();

	@RequestMapping("/appapi00250.{ext}")
	public void appapi00250(AppApi00250Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("发起查询缴存证明信息接口");
		log.info(Constants.LOG_HEAD+"api/appapi00250 begin.");
		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/JczmSpiRequest";
		String jgh = form.getJgh();
		String para = "{\"zjlx\":\""+form.getZjlx()+"\",\"zjhm\":\"" + form.getZjhm() +"\",\"xingMing\":\""
				+ form.getXingMing() + "\",\"cxlx\":\"" + form.getCxlx() +"\"}";

/*字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是
xingMing 姓名 string 是
cxlx 查询类型 string 是 默认0
0、需要信息和文件
1、仅信息不需要文件
2、仅文件不需要信息*/

		params.put("jgh", jgh);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appapi00250 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}
	@RequestMapping("/appapi00251.{ext}")
	public void appapi00251(AppApi00250Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("推送缴存证明使用状态接口");
		log.info(Constants.LOG_HEAD+"api/appapi00251 begin.");
		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/JczmSpiRequest";
		String jgh = form.getJgh();
		String para = "{\"zjlx\":\""+form.getZjlx()+"\",\"zjhm\":\"" + form.getZjhm() +"\",\"xingMing\":\""
				+ form.getXingMing() + "\",\"ywbh\":\"" + form.getYwbh() +"\",\"zmbh\":\"" + form.getZmbh() +"\",\"sfyd\":\"" + form.getSfyd() +"\"}";

/*字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是
xingMing 姓名 string 是
ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zmbh 证明编号 string 是 异地贷款证明编号
sfyd 是否已读 string 否 0=已读  1.未读 ；默认已读，未读用于特定场景。*/

		params.put("jgh", jgh);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appapi00251 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}
	@RequestMapping("/appapi00252.{ext}")
	public void appapi00252(AppApi00250Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("推送缴存证明注销审核结果接口");
		log.info(Constants.LOG_HEAD+"api/appapi00252 begin.");
		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/JczmSpiRequest";
		String jgh = form.getJgh();
		String para = "{\"zjlx\":\""+form.getZjlx()+"\",\"zjhm\":\"" + form.getZjhm() +"\",\"xingMing\":\""
				+ form.getXingMing() + "\",\"ywbh\":\"" + form.getYwbh() +"\",\"yddkzmmh\":\""
				+ form.getYddkzmmh() +"\",\"sign\":\"" + form.getSign() +"\",\"zxzt\":\""
				+ form.getZxzt() +"\",\"zxjgxx\":\"" + form.getZxjgxx() +"\"}";

/*字段代码 字段说明 字段类型 是否必填 备注说明
ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是
xingMing 姓名 string 是
yddkzmmh 异地贷款证明编号 string 是
sign 加密签名 string 是 签名字段（对入参的所有字段进行MD5加密签名，加密算法见4.8）
zxzt 注销审核结果 string 是 1.注销成功 2.注销失败
zxjgxx 注销结果信息 string 否 如果注销失败，填写对应的原因。*/

		params.put("jgh", jgh);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appapi00252 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}

	@RequestMapping("/appapi00253.{ext}")
	public void appapi00253(AppApi00250Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("推送贷款回执信息接口");
		log.info(Constants.LOG_HEAD+"api/appapi00253 begin.");
		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/JczmSpiRequest";
		String jgh = form.getJgh();
		String para = "{\"zjlx\":\""+form.getZjlx()+"\",\"zjhm\":\"" + form.getZjhm() +"\",\"xingMing\":\""
				+ form.getXingMing() + "\",\"ywbh\":\"" + form.getYwbh() +"\",\"zmbh\":\""
				+ form.getZmbh() +"\",\"grzh\":\"" + form.getGrzh() +"\",\"cdgx\":\""
				+ form.getCdgx() + "\",\"dkshjg\":\"" + form.getDkshjg() +"\",\"dkje\":\""
				+ form.getDkje() +"\",\"dkqx\":\"" + form.getDkqx() +"\",\"dkkssj\":\""
				+ form.getDkkssj() +"\",\"dkjssj\":\"" + form.getDkjssj() +"\",\"hkfs\":\""
				+ form.getHkfs() +"\",\"yhbx\":\"" + form.getYhbx() +"\",\"dwjbr\":\""
				+ form.getDwjbr() +"\",\"lxdh\":\"" + form.getLxdh() +"\",\"hzsj\":\""
				+ form.getHzsj() +"\",\"dkzh\":\"" + form.getDkzh() +"\"}";

/*字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是 　
xingMing 姓名 string 是 　
ywbh 平台唯一业务编号 string 是 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zmbh 证明编号 string 是 缴存地开具的证明编号
grzh 公积金账号 string 是 　
cdgx 参贷关系 string 是 本人   参贷
dkshjg 贷款审核结果 string 是 准予贷款  ，不予贷款
dkje 贷款金额 number 是 　
dkqx 贷款期限 number 是 年
dkkssj 贷款开始时间 string 是 yyyyMMdd
dkjssj 贷款结束时间 string 是 yyyyMMdd
hkfs 还款方式 string 是 等额本金、等额本息、其他
yhbx 月还本息 string 是 元
dwjbr 单位经办人 string 是 　
lxdh 联系电话 string 是 　
hzsj 回执时间 string 是 yyyyMMdd　
dkzh 贷款账号 string 否 　*/

		params.put("jgh", jgh);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appapi00253 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}

	@RequestMapping("/appapi00254.{ext}")
	public void appapi00254(AppApi00250Form form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		form.setBusinName("推送贷款回执信息接口");
		log.info(Constants.LOG_HEAD+"api/appapi00254 begin.");
		long starTime=System.currentTimeMillis();
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "http://172.16.10.91:9315/gjjfw/JczmSpiRequest";
		String jgh = form.getJgh();
		String para = "{\"zjlx\":\""+form.getZjlx()+"\",\"zjhm\":\"" + form.getZjhm() +"\",\"xingMing\":\""
				+ form.getXingMing() + "\",\"ywbh\":\"" + form.getYwbh() +"\",\"zmbh\":\""
				+ form.getZmbh() +"\",\"dkzh\":\"" + form.getDkzh() +"\",\"jqrq\":\""
				+ form.getJqrq() +"\"}";

/*字段代码 字段说明 字段类型 是否必填 备注说明
zjlx 证件类型 string 是 111 身份证 001 其他证件类型
zjhm 证件号码 string 是 　
xingMing 姓名 string 是
ywbh 平台唯一业务编号 string 否 按照长三角“一网通办”编码规范定义，后续可作为查询依据之一
zmbh 证明编号 string 是 异地贷款证明编号
dkzh 贷款账号 string 是 　
jqrq 结清日期 string 是 yyyyMMdd　 　*/

		params.put("jgh", jgh);
		params.put("para", RSAUtil.encrypt(para,PUBLIC_KEY));

		log.info("para==="+para);
		log.info("RSAUtil.encrypt(para,PUBLIC_KEY)==="+RSAUtil.encrypt(para,PUBLIC_KEY));
		SimpleHttpMessageUtil simpleHttpMessageUtil= new SimpleHttpMessageUtil();
		String result = simpleHttpMessageUtil.sendPost(url, params,"utf-8");
		log.info("省厅返回result="+result);
		result =  RSAUtil.decrypt(result,PRIVATE_KEY).replace("code", "recode");
		log.info("解密后result="+result);
		long endTime=System.currentTimeMillis();

		long Time=endTime-starTime;
		System.out.println("请求耗时"+Time+"毫秒");
		JSONObject json = JSONObject.fromObject(result);

		log.info(Constants.LOG_HEAD+"appapi00254 end.");
		log.info("form.getChannel()="+form.getChannel());
		response.getOutputStream().write(json.toString().getBytes(request.getCharacterEncoding()));
		return ;
	}
}
