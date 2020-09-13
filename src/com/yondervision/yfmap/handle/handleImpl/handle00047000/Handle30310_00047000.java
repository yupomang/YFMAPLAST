package com.yondervision.yfmap.handle.handleImpl.handle00047000;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.yondervision.yfmap.form.AppApi00802Form;
import com.yondervision.yfmap.form.AppApi30309Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi00201Result;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi30310Result;
import com.yondervision.yfmap.result.baogang.BaoGangAppApi303Result;
import com.yondervision.yfmap.result.qvzhi.QvZhiAppApi30303Result;
import com.yondervision.yfmap.result.zunyi.AppApi30303Result;
import com.yondervision.yfmap.result.zunyi.ZunYiAppApi00201Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;


/** 
* @ClassName: Handle30310_00047000
* @Description: 预约网点及人数查询
* 
*/ 
public class Handle30310_00047000 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		log.info(Constants.LOG_HEAD+"appApi30310 start.");
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi30309Form form = (AppApi30309Form)form1;
		log.debug("YFMAP 预约网点及人数查询 form 参数:"+form);
		BaoGangAppApi303Result app00802Result = new BaoGangAppApi303Result();
		List<AppApi30303Result> list = new ArrayList<AppApi30303Result>();
		List<TitleInfoBean> instlist = new ArrayList<TitleInfoBean>();
		List<TitleInfoBean> datelist = new ArrayList<TitleInfoBean>();
		List<TitleInfoBean> typelist = new ArrayList<TitleInfoBean>();
		//String instcode = "01:呼伦贝尔市,02:呼伦贝尔市2";
		String instcode = "04701001:市本级,04701002:扎兰屯,04701003:牙克石,04701004:额尔古纳,04701005:根河,04701006:阿荣旗,04701007:莫旗,04701008:鄂温克,04701009:鄂伦春,04701010:陈旗,04701011:新左旗,04701012:新右旗";
		String flag = "0:上午,1:下午";
		String type = "1:提取,2:贷款";
		String[] yhbm_temp = instcode.split(",");
		for(int i=0;i<yhbm_temp.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String yhbm_t[] = yhbm_temp[i].split(":");
			if(yhbm_t.length>=0)t.setTitle(yhbm_t[0]);
			if(yhbm_t.length>=1)t.setInfo(yhbm_t[1]);
			instlist.add(t);
		}
		
		yhbm_temp = flag.split(",");
		for(int i=0;i<yhbm_temp.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String yhbm_t[] = yhbm_temp[i].split(":");
			if(yhbm_t.length>=0)t.setTitle(yhbm_t[0]);
			if(yhbm_t.length>=1)t.setInfo(yhbm_t[1]);
			datelist.add(t);
		}
		
		yhbm_temp = type.split(",");
		for(int i=0;i<yhbm_temp.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String yhbm_t[] = yhbm_temp[i].split(":");
			if(yhbm_t.length>=0)t.setTitle(yhbm_t[0]);
			if(yhbm_t.length>=1)t.setInfo(yhbm_t[1]);
			typelist.add(t);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("instlist", instlist);
		modelMap.put("datelist", datelist);
		modelMap.put("typelist", typelist);
		log.info(Constants.LOG_HEAD+"appApi30310 end.");
		return modelMap;
	}
	
	public static void main(String[] args){
		AppApi00802Form form1 = new AppApi00802Form();
		ModelMap modelMap = new ModelMap();
		form1.setSurplusAccount("99999");
		form1.setCenterId("00053100");
		form1.setLogid("113");
		form1.setSelectValue("可国人");
		form1.setPagenum("10");
		form1.setPagerows("0");
		Handle30310_00047000 hand = new Handle30310_00047000();
		try {
			hand.action(form1, modelMap);
		} catch (CenterRuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
