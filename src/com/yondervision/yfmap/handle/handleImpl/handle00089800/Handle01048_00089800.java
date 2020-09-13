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
 * 业务办理-业务状态查询
 */
public class Handle01048_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi01020Form form = (AppApi01020Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01046 start.");
		log.debug("appApi01046 form:"+form); 
		AES aes = new AES();
		form.setIdcardNumber(aes.decrypt(form.getIdcardNumber()));
		ApplyPP applyPP = new ApplyPP();
		PersistentTool.conDB();
		ResultSet rs = applyPP.queryRSP002All(form.getIdcardNumber(),form.getStartdate(), form.getEnddate(), form.getState());
		List<AppApi01048Result> l = new ArrayList<AppApi01048Result>();
		if(rs!=null)
		{
			AppApi01048Result a = new AppApi01048Result();
			a.setApplyid(rs.getString("applyid"));
			a.setApplyid_code(rs.getString("applyid"));
			a.setTitle_code(rs.getString("title"));
			a.setTitle(rs.getString("title"));
			a.setCertno(rs.getString("certno"));
			a.setCreatedate(rs.getString("createdate")+" "+rs.getString("createtime"));
			a.setState(PropertiesReader.getProperty("yingshe.properties","sbzt"+rs.getString("state")+form.getCenterId()));
			if("02".equals(rs.getString("channel")))
			{
				a.setState("网厅-"+PropertiesReader.getProperty("yingshe.properties","sbzt"+rs.getString("state")+form.getCenterId()));
			}
			a.setCounter(PropertiesReader.getProperty("yingshe.properties","counter"+rs.getString("counter")+form.getCenterId()));
			String sql1 = "select * from rsp007 where applyid = '"+a.getApplyid()+"' and state = '"+rs.getString("state")+"'";
		    System.out.println(sql1);
		    ResultSet rs1 = PersistentTool.excuteQuery(sql1);
		    if(rs1!=null)
			{
		    	 a.setReason(rs1.getString("reason"));
			}
		    a.setTitle(a.getTitle().replaceAll("租房提取", "正常提取"));
			l.add(a);
			while(rs.next())
			{
				a = new AppApi01048Result();
				a.setApplyid(rs.getString("applyid"));
				a.setApplyid_code(rs.getString("applyid"));
				a.setTitle_code(rs.getString("title"));
				a.setTitle(rs.getString("title"));
				a.setCertno(rs.getString("certno"));
				a.setCreatedate(rs.getString("createdate")+" "+rs.getString("createtime"));
				a.setState(PropertiesReader.getProperty("yingshe.properties","sbzt"+rs.getString("state")+form.getCenterId()));
				if("02".equals(rs.getString("channel")))
				{
					a.setState("网厅-"+PropertiesReader.getProperty("yingshe.properties","sbzt"+rs.getString("state")+form.getCenterId()));
				}
				a.setCounter(PropertiesReader.getProperty("yingshe.properties","counter"+rs.getString("counter")+form.getCenterId()));
				String sql2 = "select * from rsp007 where applyid = '"+a.getApplyid()+"' and state = '"+rs.getString("state")+"'";
			    System.out.println(sql2);
			    ResultSet rs2 = PersistentTool.excuteQuery(sql2);
			    if(rs2!=null)
				{
			    	 a.setReason(rs2.getString("reason"));
				}
			    a.setTitle(a.getTitle().replaceAll("租房提取", "正常提取"));
				l.add(a);
			}
		}
		PersistentTool.closeDB();
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<l.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01048"+form.getCenterId()+".result", l.get(i));	
			detail.add(result1);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", detail);
		log.info(Constants.LOG_HEAD+"appApi01046 end.");
		return modelMap;
	}

}
