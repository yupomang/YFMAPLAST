package com.yondervision.yfmap.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yondervision.yfmap.dao.Mi126DAO;
import com.yondervision.yfmap.dto.Mi126;
import com.yondervision.yfmap.dto.Mi126Example;
import com.yondervision.yfmap.form.AppApi00101Form;
import com.yondervision.yfmap.result.dalian.DaLianAppApi00906Result;
import com.yondervision.yfmap.service.AppApi009Service;
import com.yondervision.yfmap.util.CommonUtil;

public class AppApi009ServiceImpl implements AppApi009Service {
	

	
	public void appApi00901Delete(AppApi00101Form form) throws Exception {
		Mi126DAO mi126Dao = (Mi126DAO)com.yondervision.yfmap.util.SpringContextUtil.getBean("mi126Dao");		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		
		Mi126Example m126e=new Mi126Example();	
		List<String> listValue = new ArrayList();
		listValue.add("hkfs");
		listValue.add("llbs");
		com.yondervision.yfmap.dto.Mi126Example.Criteria ca= m126e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andFuncIn(listValue);
		ca.andValidflagEqualTo("1");
		
		mi126Dao.deleteByExample(m126e);
	}
	
	public List<Mi126> appApi00902Select(
			com.yondervision.yfmap.form.AppApi00101Form form) throws Exception {
		Mi126DAO mi126Dao = (Mi126DAO)com.yondervision.yfmap.util.SpringContextUtil.getBean("mi126Dao");		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		List<String> listValue = new ArrayList();
		listValue.add("hkfs");
		listValue.add("llbs");
		Mi126Example m126e=new Mi126Example();	
		
		com.yondervision.yfmap.dto.Mi126Example.Criteria ca= m126e.createCriteria();
		ca.andCenteridEqualTo(form.getCenterId());
		ca.andFuncIn(listValue);
		ca.andDatecreatedLike("%"+format1.format(new Date())+"%");
		ca.andValidflagEqualTo("1");
		
		List<Mi126> list = mi126Dao.selectByExample(m126e);
		
		return list;
	}

	public void appApi00903Insert(AppApi00101Form form, List<DaLianAppApi00906Result> list) throws Exception {
		CommonUtil cu = new CommonUtil();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Date date = new Date();
		Mi126DAO mi126Dao = (Mi126DAO)com.yondervision.yfmap.util.SpringContextUtil.getBean("mi126Dao");	
		for(int i=0;i<list.size();i++){
			String seqno = cu.genKey("MI126").toString();
			Mi126 mi126 = new Mi126();
			mi126.setSeqno(Integer.parseInt(seqno));
			mi126.setCenterid(form.getCenterId());
			mi126.setFunc(list.get(i).getZdm());
			mi126.setParamkey(list.get(i).getWwzym());
			mi126.setParamvalue(list.get(i).getWwzy());
			mi126.setValidflag("1");
			mi126.setDatecreated(format.format(date));
			mi126.setDatemodified(format.format(date));
			mi126Dao.insert(mi126);
		}
		
	}

		
	
}
