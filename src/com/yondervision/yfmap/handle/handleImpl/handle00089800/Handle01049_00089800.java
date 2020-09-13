package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.ApplyPP;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01020Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00201Result;
import com.yondervision.yfmap.result.haikou.AppApi01024Result;
import com.yondervision.yfmap.result.haikou.AppApi01048Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author Administrator
 * 业务办理-申报撤回
 */
public class Handle01049_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01046 start.");
		log.debug("appApi01046 form:"+form); 
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		try{
			ApplyPP applyPP = new ApplyPP();
			PersistentTool.conDB();
			applyPP.backApply(form.getApplyid());
			PersistentTool.closeDB();
		}catch(Exception e)
		{
			e.printStackTrace();
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "撤回失败！");
			log.info(Constants.LOG_HEAD+"appApi01046 end.");
			return modelMap;
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		log.info(Constants.LOG_HEAD+"appApi01046 end.");
		return modelMap;
	}

}
