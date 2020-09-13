package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.form.AppApi01050Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.result.haikou.AppApi01051Result;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author LFX
 * 业务办理-显示业务需要文件列表
 */
public class Handle01051_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		AppApi01050Form form = (AppApi01050Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01051 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		//存结果List
		List<AppApi01051Result> results = new ArrayList<AppApi01051Result>();
		String flag = form.getActionFlag()+form.getCenterId();
		String stepid = PropertiesReader.getProperty("yingshe.properties","datapool_stepid"+flag);
		String flowid = PropertiesReader.getProperty("yingshe.properties","datapool_flowid"+flag);
		String tqyy = form.getTqyy();
		String ctlflag = form.getCtlflag();
		String buyhousetype = form.getBuyhousetype();
		//提取原因
		//String tqyy = form.getTqyy();
		//String centerId = form.getCenterId();
		String centerId = "00000000";
		//拼接sql
//		String sql = "select a.*,b.* from av004 a left join av056 b  on a.FLOWID=b.FLOWID and a.MATERCODE=b.MATERCODE " 
//					+ "where  a.FLOWID='"+ flowid +"' "
//					+ "and a.STEPID='"+ stepid +"' "
//					//+ "and b.SHOWEXP_PAT like '%tqyy == "+tqyy+"%' "
//					+ "and a.CENTERID = '"+ centerId+"' "
//					+ "order by SEQNO";
		String sql = "select a.* from ( select mater.*,'' as showexp,'' as mustscanexp from " +
				"(select flowid,stepid,matercode,matername,type,seqno,'false' as addflag from av004 where centerid='"+ centerId+"' and flag='2' ) mater " +
				"left join av056  on  " +
				"mater.addflag='false' and av056.centerid='"+ centerId+"' and mater.stepid=av056.stepid and mater.matercode=av056.matercode " +
				"where mater.matercode not in (select matercode from av056 where centerid='"+ centerId+"'  ) union " +
				"select mater.flowid,mater.stepid,mater.matercode,mater.matername,mater.type,mater.seqno,'false' as addflag,showexp_pat as showexp,mustscanexp_pat as mustscanexp " +
				"from av004 mater left join av056 " +
				"on av056.flowid=mater.flowid and mater.stepid=av056.stepid and mater.matercode=av056.matercode  " +
				"where av056.centerid='"+ centerId+"' and mater.centerid='"+ centerId+"'  and mater.flag='2' " +
				"and (av056.showexp_pat like '%tqyy == "+tqyy+"%' )" +
				")  a " +
				"where a.FLOWID='"+ flowid +"'  and a.stepid='"+ stepid +"' order by a.SEQNO";
		if(!CommonUtil.isEmpty(ctlflag))
		{
			sql = "select a.* from ( select mater.*,'' as showexp,'' as mustscanexp from " +
					"(select flowid,stepid,matercode,matername,type,seqno,'false' as addflag from av004 where centerid='"+ centerId+"' and flag='2' ) mater " +
					"left join av056  on  " +
					"mater.addflag='false' and av056.centerid='"+ centerId+"' and mater.stepid=av056.stepid and mater.matercode=av056.matercode " +
					"where mater.matercode not in (select matercode from av056 where centerid='"+ centerId+"'  ) union " +
					"select mater.flowid,mater.stepid,mater.matercode,mater.matername,mater.type,mater.seqno,'false' as addflag,showexp_pat as showexp,mustscanexp_pat as mustscanexp " +
					"from av004 mater left join av056 " +
					"on av056.flowid=mater.flowid and mater.stepid=av056.stepid and mater.matercode=av056.matercode  " +
					"where av056.centerid='"+ centerId+"' and mater.centerid='"+ centerId+"'  and mater.flag='2' " +
					"and (av056.showexp_pat like '%ctlflag == "+ctlflag+"%')" +
					")  a " +
					"where a.FLOWID='"+ flowid +"'  and a.stepid='"+ stepid +"' order by a.SEQNO";
		}
		if(!CommonUtil.isEmpty(buyhousetype))
		{
			sql = "select a.* from ( select mater.*,'' as showexp,'' as mustscanexp from " +
					"(select flowid,stepid,matercode,matername,type,seqno,'false' as addflag from av004 where centerid='"+ centerId+"' and flag='2' ) mater " +
					"left join av056  on  " +
					"mater.addflag='false' and av056.centerid='"+ centerId+"' and mater.stepid=av056.stepid and mater.matercode=av056.matercode " +
					"where mater.matercode not in (select matercode from av056 where centerid='"+ centerId+"'  ) union " +
					"select mater.flowid,mater.stepid,mater.matercode,mater.matername,mater.type,mater.seqno,'false' as addflag,showexp_pat as showexp,mustscanexp_pat as mustscanexp " +
					"from av004 mater left join av056 " +
					"on av056.flowid=mater.flowid and mater.stepid=av056.stepid and mater.matercode=av056.matercode  " +
					"where av056.centerid='"+ centerId+"' and mater.centerid='"+ centerId+"'  and mater.flag='2' " +
					"and (av056.showexp_pat like '%buyhousetype == "+buyhousetype +"%')" +
					")  a " +
					"where a.FLOWID='"+ flowid +"'  and a.stepid='"+ stepid +"' order by a.SEQNO";
		}
		System.out.println("sql:"+sql);
		String isShow = "";
		String isMust = "";
//		PersistentTool.excuteQuery(sql);
		PersistentTool.conDB();
		ResultSet result = PersistentTool.excuteQuery(sql);
		//没有查到相应材料列表则返回
		if(result==null){
			PersistentTool.closeDB();
			modelMap.clear();
			modelMap.put("recode", "999999");
			modelMap.put("msg", "没有查询到对应材料列表");
			log.error(Constants.LOG_ERROR+"没有查询到对应材料列表");
			return modelMap;
		}
		do{
			AppApi01051Result temp = new AppApi01051Result();
			//temp.setCenterid(result.getString("CENTERID"));
//			if(!CommonUtil.isEmpty(result.getString("showexp")))
//			{
//				isShow = result.getString("showexp");
//				String key = CommonUtil.getKey(isShow);
//				HashMap<String,String> keymap = new HashMap<String,String>();
//				keymap.put(key, tqyy);
//				keymap.put("1", "1");
//				keymap.put("1", "2");
//				if(CommonUtil.isTrue(keymap, isShow))
//				{
//					temp.setIsShow("1");
//				}else
//				{
//					temp.setIsShow("0");
//				}
//			}
			if(!CommonUtil.isEmpty(result.getString("type")))
			{
				isMust = result.getString("type");
//				String key = CommonUtil.getKey(isMust);
//				HashMap<String,String> keymap = new HashMap<String,String>();
//				keymap.put(key, tqyy);
//				keymap.put("1", "1");
//				keymap.put("1", "2");
//				if(CommonUtil.isTrue(keymap, isMust))
//				{
//					temp.setIsMust("1");
//				}else
//				{
//					temp.setIsMust("0");
//				}
				if("1".equals(isMust))
				{
					temp.setIsMust("1");
				}else
				{
					temp.setIsMust("0");
				}
			}
			temp.setFlowid(result.getString("FLOWID"));
			temp.setStepid(result.getString("STEPID"));
			//temp.setFlag(result.getString("FLAG"));
			temp.setMatercode(result.getString("MATERCODE"));
			temp.setMatername(result.getString("MATERNAME"));
			temp.setType(result.getString("TYPE"));
			//temp.setState(result.getString("STATE"));
			//temp.setOndate(result.getString("ONDATE"));
			results.add(temp);
			}while(result.next());
		
		PersistentTool.closeDB();
		
		List<AppApi01051Result> r = new ArrayList<AppApi01051Result>();
		
		for(AppApi01051Result t:results)
		{
			if(!CommonUtil.isEmpty(isShow))
			{
				if("1".equals(t.getIsShow()))
				{
					r.add(t);
				}
			}
		}
		if(CommonUtil.isEmpty(r))
		{
			r = results;
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", r);
		log.info(Constants.LOG_HEAD+"appApi01051 end.");
		return modelMap;
	} 
	public static void main(String[] args){
		AppApi01050Form test = new AppApi01050Form();
		test.setCenterId("00089800");
		test.setActionFlag("22");
		test.setTqyy("04");
		//test.setFlowid("31");
		ModelMap modelMap = new ModelMap();
		Handle01051_00089800 temp = new Handle01051_00089800();
		try {
			temp.action(test, modelMap);
		} catch (CenterRuntimeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
