package com.yondervision.yfmap.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import com.yondervision.yfmap.dao.Mi125DAO;
import com.yondervision.yfmap.dto.Mi125;
import com.yondervision.yfmap.dto.Mi125Example;
import com.yondervision.yfmap.dto.Mi125Example.Criteria;
import com.yondervision.yfmap.result.dalian.DalianLogResult;
import com.yondervision.yfmap.service.DLLogService;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.LogPara;

public class DLLogServiceImpl implements DLLogService {
	
	public void updateMi125Service(LogPara para, String id, DalianLogResult result){
		Mi125DAO mi125Dao = (Mi125DAO)com.yondervision.yfmap.util.SpringContextUtil.getBean("mi125Dao");		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		Mi125 mi125 = new Mi125();
		mi125.setBspjym1(para.getBspcode1());
		
		try {
			mi125.setQdly(para.getQdly());
			mi125.setWwjym(para.getWwjym());
			mi125.setJyms(para.getJyms());
			mi125.setWwfqsj(format.parse(para.getWwfqsj()));			
			Date date = new Date();
			mi125.setId(Integer.valueOf(id));				
			mi125.setJyrq(date);
			mi125.setBspjym1("");
			mi125.setBspjym2("");
			mi125.setWwfssj(format.parse(para.getSendStartTime()));		
			mi125.setWwjssj(format.parse(para.getSendEndTime()));
			mi125.setWwjssj1(format.parse(para.getEnddate()));
			mi125.setBz(para.getRecode());
			mi125.setSbyy(para.getRemsg());
			mi125.setYwlb(para.getYwlb());
			String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";			
			SimpleDateFormat timeformat = new SimpleDateFormat(defaultDatePattern);
			if(!"--".equals(result.getBspjym1())){
				mi125.setBspjym1(result.getBspjym1());
			}
			if(!"--".equals(result.getBspjym2())){
				mi125.setBspjym2(result.getBspjym2());
			}			
			if(!"--".equals(result.getBspfssj1())){
				mi125.setBspfssj1(timeformat.parse(result.getBspfssj1()));
			}
			if(!"--".equals(result.getBspjssj1())){
				mi125.setBspjssj1(timeformat.parse(result.getBspjssj1()));
			}
			if(!"--".equals(result.getBspfssj2())){
				mi125.setBspfssj2(timeformat.parse(result.getBspfssj2()));
			}
			if(!"--".equals(result.getBspjssj2())){
				mi125.setBspjssj2(timeformat.parse(result.getBspjssj2()));
			}			
			if(!"--".equals(result.getFspfssj1())){
				mi125.setFspfssj1(timeformat.parse(result.getFspfssj1()));
			}
			if(!"--".equals(result.getFspfssj2())){
				mi125.setFspfssj2(timeformat.parse(result.getFspfssj2()));
			}
			if(!"--".equals(result.getFspfssj3())){
				mi125.setFspfssj3(timeformat.parse(result.getFspfssj3()));
			}
			if(!"--".equals(result.getFspfssj4())){
				mi125.setFspfssj4(timeformat.parse(result.getFspfssj4()));
			}			
			if(!"--".equals(result.getFspjssj1())){
				mi125.setFspjssj1(timeformat.parse(result.getFspjssj1()));
			}
			if(!"--".equals(result.getFspjssj2())){
				mi125.setFspjssj2(timeformat.parse(result.getFspjssj2()));
			}
			if(!"--".equals(result.getFspjssj3())){
				mi125.setFspjssj3(timeformat.parse(result.getFspjssj3()));
			}
			if(!"--".equals(result.getFspjssj4())){
				mi125.setFspjssj4(timeformat.parse(result.getFspjssj4()));
			}			
			if(!"--".equals(result.getFspfsdsfsj())){
				mi125.setFspfsdsfsj(timeformat.parse(result.getFspfsdsfsj()));
			}
			if(!"--".equals(result.getFspjsdsfsj())){
				mi125.setFspjsdsfsj(timeformat.parse(result.getFspjsdsfsj()));
			}			
//			Mi125Example m125e=new Mi125Example();	
//			Criteria ca= m125e.createCriteria();
//			ca.andIdEqualTo(Integer.valueOf(id));
//			mi125Dao.updateByExampleSelective(mi125, m125e);
			
			JSONObject jsonObj = new JSONObject(); 
			
			jsonObj.put("mi125", mi125);
			
			System.out.println(jsonObj);
			
			mi125Dao.insert(mi125);
		} catch (ParseException e) {
			//不处理
		}
	}

	

}
