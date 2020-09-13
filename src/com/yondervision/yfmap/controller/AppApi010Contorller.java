/**
 * 
 */
package com.yondervision.yfmap.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.security.Base64;
import com.yondervision.yfmap.form.AppApi01001Form;
import com.yondervision.yfmap.form.AppApi01002Form;
import com.yondervision.yfmap.form.AppApi01003Form;
import com.yondervision.yfmap.form.AppApi01004Form;
import com.yondervision.yfmap.form.AppApi01005Form;
import com.yondervision.yfmap.form.AppApi01008Form;
import com.yondervision.yfmap.form.AppApi01009Form;
import com.yondervision.yfmap.form.AppApi01020Form;
import com.yondervision.yfmap.form.AppApi01021Form;
import com.yondervision.yfmap.form.AppApi01022Form;
import com.yondervision.yfmap.form.AppApi01028Form;
import com.yondervision.yfmap.form.AppApi01033Form;
import com.yondervision.yfmap.form.AppApi01036Form;
import com.yondervision.yfmap.form.AppApi01046Form;
import com.yondervision.yfmap.form.AppApi01050Form;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.util.PropertiesReader;

/** 
* @ClassName: AppApi010Contorller 
* @Description: 物业费提取Controller
* @author gongq
* 
*/ 
@Controller
public class AppApi010Contorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 物业费提取相关信息获取
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01001.{ext}")
	public String appApi01001(AppApi01001Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01001 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01001_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01001 end.");
		return "";
	}
	
	/**
	 * 物业费提取
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01002.{ext}")
	public String appApi01002(AppApi01002Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01002 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01002_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01002 end.");
		return "";
	}
	
	/**
	 * 提取签约信息查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01003.{ext}")
	public String appApi01003(AppApi01003Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01003 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01003"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01003 end.");
		return "";
	}
	
	/**
	 * 提取签约
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01004.{ext}")
	public String appApi01004(AppApi01004Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01004 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01004"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01004 end.");
		return "";
	}	
	
	
	/**
	 * @deprecated 提取规则检查（校验提取规则(允许提取次数等)，满足会直接发送短信验证码）
	 * @param form
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/appapi01005.{ext}")
	public String appApi01005(AppApi01005Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01005 begin.");
				
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01005_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01005 end.");
		return "";
	}
	
	/**
	 * 提取签约查询 唐山
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01006.{ext}")
	public String appApi01006(AppApi01003Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01006 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01006"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01006 end.");
		return "";
	}	
	
	/**
	 * 提取签约 唐山
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01007.{ext}")
	public String appApi01007(AppApi01003Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01007 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01007"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01007 end.");
		return "";
	}	
	
	/**
	 * 变更登记查询 唐山
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01008.{ext}")
	public String appApi01008(AppApi01008Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01008 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01008"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01008 end.");
		return "";
	}	
	
	/**
	 * 变更登记办理
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01009.{ext}")
	public String appApi01009(AppApi01009Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01009 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01009"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01009 end.");
		return "";
	}	
	
	/**
	 * 短信签约
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01010.{ext}")
	public String appApi01010(AppApi01009Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appApi01010 begin.");	
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01010"+form.getDrawSignType()+"_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appApi01010 end.");
		return "";
	}
	
	/**
	 * 业务办理  历史提取变更
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01019.{ext}")
	public String appApi01019(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01020 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01020_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01020 end.");
		return "";
	}
	
	/**
	 * 业务办理  提取 通用-查询依据提取号-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01020.{ext}")
	public String appApi01020(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01020 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01020_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01020 end.");
		return "";
	}
	
	/**
	 * 业务办理  提取 通用-计算提取金额-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01021.{ext}")
	public String appApi01021(AppApi01021Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01021 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01021_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01021 end.");
		return "";
	}
	
	/**
	 * 业务办理  正常提取-正常提取提交-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01022.{ext}")
	public String appApi01022(AppApi01022Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01022 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01022 end.");
		return "";
	}
	
	/**
	 * 业务办理  提取 通用-查询个人信息-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01023.{ext}")
	public String appApi01023(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01023 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01023_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01023 end.");
		return "";
	}
	
	/**
	 * 公积金还贷提取-银行列表获取-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01024.{ext}")
	public String appApi01024(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01024 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01024_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01024 end.");
		return "";
	}

	/**
	 * 销户提取-反显信息获取-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01025.{ext}")
	public String appApi01025(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01025 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01025_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01025 end.");
		return "";
	}
	
	/**
	 * 销户提取-提交-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01026.{ext}")
	public String appApi01026(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01026 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01026_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01026 end.");
		return "";
	}
	
	/**
	 * 业务办理-正常提取-查询提取相关信息-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01027.{ext}")
	public String appApi01027(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01027 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01027_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01027 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-根据证件号码查询合同号-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01028.{ext}")
	public String appApi01028(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01028 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01028_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01028 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-提前偿还贷款查询 -海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01029.{ext}")
	public String appApi01029(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01029 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01029_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01029 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-合同号取贷款基本信息-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01030.{ext}")
	public String appApi01030(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01030 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01030_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01030 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-查询还款公积金列表-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01031.{ext}")
	public String appApi01031(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01031 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01031_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01031 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-公积金还款信息息-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01032.{ext}")
	public String appApi01032(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01032 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01032_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01032 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-计算实际还款信息-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01033.{ext}")
	public String appApi01033(AppApi01033Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01033 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01033_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01033 end.");
		return "";
	}
	/**
	 * 业务办理-还款-还款办结-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01034.{ext}")
	public String appApi01034(AppApi01033Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01034 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01034_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01034 end.");
		return "";
	}
	
	/**
	 * 业务办理-还款-数据反显-海口
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01035.{ext}")
	public String appApi01035(AppApi01028Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01035 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01035_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01035 end.");
		return "";
	}
	
	/**
	 * 贷款展期办理
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01036.{ext}")
	public String appApi01036(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01036 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01036_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01036 end.");
		return "";
	}
	
	/**
	 * 贷款展期查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01037.{ext}")
	public String appApi01037(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01037 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01037_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01037 end.");
		return "";
	}
	
	/**
	 * 贷款缩期办理
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01038.{ext}")
	public String appApi01038(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01038 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01038_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01038 end.");
		return "";
	}
	
	/**
	 * 贷款缩期查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01039.{ext}")
	public String appApi01039(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01039 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01039_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01039 end.");
		return "";
	}
	
	/**
	 * 还款方式变更办理
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01040.{ext}")
	public String appApi01040(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01040 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01040_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01040 end.");
		return "";
	}
	
	/**
	 * 还款方式变更查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01041.{ext}")
	public String appApi01041(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01041 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01041_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01041 end.");
		return "";
	}
	
	/**
	 * 公积金还贷提取-查询-页面1
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01042.{ext}")
	public String appApi01042(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01042 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01042_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01042 end.");
		return "";
	}
	
	/**
	 * 公积金还贷提取-查询-页面2-根据合同号查询信息
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01043.{ext}")
	public String appApi01043(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01043 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01043_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01043 end.");
		return "";
	}
	/**
	 * 公积金还贷提取-查询-页面2-计算可提取金额
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01044.{ext}")
	public String appApi01044(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01044 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01044_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01044 end.");
		return "";
	}
	/**
	 * 公积金还贷提取-查询-办理
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01045.{ext}")
	public String appApi01045(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01045 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01045_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01045 end.");
		return "";
	}
	
	/**
	 * 公积金代扣协议维护查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01046.{ext}")
	public String appApi01046(AppApi01036Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01046 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01046_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01046 end.");
		return "";
	}
	
	/**
	 * 公积金代扣协议维护提交
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01047.{ext}")
	public String appApi01047(AppApi01046Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01047 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01047_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01047 end.");
		return "";
	}
	
	/**
	 * 申报查询
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01048.{ext}")
	public String appApi01048(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01048 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01048_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01048 end.");
		return "";
	}
	
	/**
	 * 申报撤回
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01049.{ext}")
	public String appApi01049(AppApi01020Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01049 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01049_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01049 end.");
		return "";
	}
	
	/**
	 * 图片上传
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01050.{ext}")
	public String appApi01050(@RequestParam(value="files",required = false)  MultipartFile[] files,AppApi01050Form form,  ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01050 begin.");
		String filenames = "";
		String filess = "";
		// 将文件读成流，转base64
		int filecount = files==null?0:files.length;
		
		for(int i = 0; i<filecount; i++) {
			log.debug("fileName---------->" + files[i].getOriginalFilename());   
           
                //int pre = (int) System.currentTimeMillis();   
                try {   
                	 String filePath=PropertiesReader.getProperty("ftp.properties","bsp_local_path")+File.separator+files[i].getOriginalFilename();
                	 files[i].transferTo(new File(filePath));
//                	 File fileName = new File(filePath);
//        			// 文件全路径
//        			String filename = filePath;
//                    // 拿到输出流，同时重命名上传的文件   
//        			ByteArrayOutputStream output = new ByteArrayOutputStream();
//                    //以写字节的方式写文件   
//                    byte[] buffer = new byte[1024 * 1024];
//                    byte[] img = null; 
//            		int bytesum = 0;
//            		int byteread = 0;
//            		 // 拿到上传文件的输入流   
//            		InputStream stream = files[i].getInputStream();
//            		while ((byteread = stream.read(buffer)) != -1) {
//            			bytesum += byteread;
//            			output.write(buffer, 0, byteread);
//            		}
//            		img = output.toByteArray();
//            		String file = Base64.encode(img);
//            		output.close();
//            		stream.close(); 
//                    int finaltime = (int) System.currentTimeMillis();   
//                    log.debug("文件读取时间：" + (finaltime-pre));   
//                    filenames = filenames + "~";
//                    filenames = filenames + filename;
                    filess = filess + "~";
                    filess = filess + filePath;
                    
                } catch (Exception e) { 
                	e.printStackTrace();
                }  
                
        }
		if(filess.length()>1){
			form.setFiless(filess.substring(1));
		}else
		{
			form.setFiless(filess);
		}
		
//		if(files!=null){
//			form.setFilenames(filenames.substring(1));
//			form.setFiless(filess.substring(1));
//		}
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01050_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01050 end.");
		return "";
	}
	
	/**
	 * 显示业务需要文件列表
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01051.{ext}")
	public String appApi01051(AppApi01050Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01051 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01051_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01051 end.");
		return "";
	}
	
	/**
	 * 显示业务需要文件列表
	 * @param form 请求参数
	 * @param modelMap 返回数据容器
	 * @return 回调页面名
	 */
	@RequestMapping("/appapi01052.{ext}")
	public String appApi01052(AppApi01050Form form,  ModelMap modelMap) throws Exception{
		log.info(Constants.LOG_HEAD+"appapi01052 begin.");
		CtrlHandleInter business = (CtrlHandleInter) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle01052_"+form.getCenterId()).newInstance();
		business.action(form, modelMap);
		log.info(Constants.LOG_HEAD+"appapi01052 end.");
		return "";
	}
}
