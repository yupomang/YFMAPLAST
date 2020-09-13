package com.yondervision.yfmap.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yondervision.yfmap.dao.GjDwndjsDAO;
import com.yondervision.yfmap.dao.GjDwxxDAO;
import com.yondervision.yfmap.dao.GjDwzhDAO;
import com.yondervision.yfmap.dao.GjDwzhmxDAO;
import com.yondervision.yfmap.dao.GjGtdwyjcDAO;
import com.yondervision.yfmap.dto.GjDwndjs;
import com.yondervision.yfmap.dto.GjDwxx;
import com.yondervision.yfmap.dto.GjDwxxExample;
import com.yondervision.yfmap.dto.GjDwzh;
import com.yondervision.yfmap.dto.GjDwzhExample;
import com.yondervision.yfmap.dto.GjDwzhmx;
import com.yondervision.yfmap.dto.GjDwzhmxExample;
import com.yondervision.yfmap.dto.GjGtdwyjc;
import com.yondervision.yfmap.dto.GjGtdwyjcExample;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80102Form;
import com.yondervision.yfmap.form.AppApi80103Form;
import com.yondervision.yfmap.service.AppApi801Service;
import com.yondervision.yfmap.util.MachineCodeUtils;

public class AppApi801ServiceImpl implements AppApi801Service {

	public GjDwxxDAO ghDwxxDao = null;
	
	public GjDwzhDAO gjDwzhDAO = null;
	
	public GjGtdwyjcDAO gjGtdwyjcDAO = null;

	public GjDwzhmxDAO gjDwzhmxDAO = null;
	
	public GjDwndjsDAO gjDwndjsDAO = null;
	
	public void setGjDwndjsDAO(GjDwndjsDAO gjDwndjsDAO) {
		this.gjDwndjsDAO = gjDwndjsDAO;
	}

	public void setGjDwzhmxDAO(GjDwzhmxDAO gjDwzhmxDAO) {
		this.gjDwzhmxDAO = gjDwzhmxDAO;
	}

	public void setGjGtdwyjcDAO(GjGtdwyjcDAO gjGtdwyjcDAO) {
		this.gjGtdwyjcDAO = gjGtdwyjcDAO;
	}

	public void setGjDwzhDAO(GjDwzhDAO gjDwzhDAO) {
		this.gjDwzhDAO = gjDwzhDAO;
	}

	public void setGhDwxxDao(GjDwxxDAO ghDwxxDao) {
		this.ghDwxxDao = ghDwxxDao;
	}

	public Map appApi00801Select(AppApi40102Form form) throws Exception {
		Map<String,Object> map = new TreeMap();
		GjDwxxExample gjDwxxExample = new GjDwxxExample();
		GjDwxxExample.Criteria ca = gjDwxxExample.createCriteria();
		ca.andDwdmEqualTo(form.getFullName());
		List<GjDwxx> list = ghDwxxDao.selectByExample(gjDwxxExample);
		GjDwxx gjDwxx = list.get(0);
		String dwmc = gjDwxx.getDwmc();
		System.out.println(dwmc);
		map.put("dwmc", dwmc.trim());
		
		GjDwzhExample gjDwzhExample = new GjDwzhExample();
		GjDwzhExample.Criteria ca1 = gjDwzhExample.createCriteria();
		ca1.andDwzhEqualTo(form.getFullName());
		gjDwzhExample.setOrderByClause("zhlx asc");
		List<GjDwzh> list1 = gjDwzhDAO.selectByExample(gjDwzhExample);
		String zhzt = "";
		if(list1.size() != 0 && list1 != null){
			 zhzt = list1.get(0).getZhzt();
		}
		if("01".equals(zhzt.trim())){
			zhzt = "活跃";
		}
		if("02".equals(zhzt.trim())){
			zhzt = "注销";
		}
		if("03".equals(zhzt.trim())){
			zhzt = "止付";
		}
		if("04".equals(zhzt.trim())){
			zhzt = "挂失";
		}
		if("05".equals(zhzt.trim())){
			zhzt = "冻结";
		}
		map.put("zhzt", zhzt);
		
		BigDecimal gjjzh = null;
		BigDecimal aybtzh = null;
		BigDecimal zcxzhbtzh = null;
		for(int i = 0; i < list1.size(); i++){
			if("01".equals(list1.get(i).getZhlx().trim())){
				gjjzh = list1.get(i).getZhye();
			}
			if("02".equals(list1.get(i).getZhlx().trim())){
				aybtzh = list1.get(i).getZhye();
			}
			if("03".equals(list1.get(i).getZhlx().trim())){
				zcxzhbtzh = list1.get(i).getZhye();
			}
		}
		if(gjjzh == null){
			gjjzh = new BigDecimal(0);
		}
		if(aybtzh == null){
			aybtzh = new BigDecimal(0);
		}
		if(zcxzhbtzh == null){
			zcxzhbtzh = new BigDecimal(0);
		}
		map.put("gjjje", gjjzh);
		map.put("aybtje", aybtzh);
		map.put("hbbtje", zcxzhbtzh);
		//查询当前月上月的公积金账户 
		String tmonth = "";
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int bemonth = month - 1;
		if(month < 10){
			tmonth = "0" + month;
		}else{
			tmonth = String.valueOf(month);
		}
		int year = cal.get(Calendar.YEAR);
		if(month == 0){
			tmonth ="12";
			year = year - 1;
		}
		GjGtdwyjcExample gjGtdwyjcExample = new GjGtdwyjcExample();
		GjGtdwyjcExample.Criteria ca2 = gjGtdwyjcExample.createCriteria();
		gjGtdwyjcExample.setOrderByClause("jfxh asc");
		ca2.andDwdmEqualTo(form.getFullName());
		ca2.andNdEqualTo((short)year);
		ca2.andYfEqualTo(Short.parseShort(tmonth));
		List<GjGtdwyjc> list2 = gjGtdwyjcDAO.selectByExample(gjGtdwyjcExample);
		if(list2 != null && list2.size() != 0){
			if(list2.size() == 1){
				GjGtdwyjc gjGtdwyjc = list2.get(0);
				if("0".equals(gjGtdwyjc.getJfxh())){//0 公积金
					map.put("gjjsyjcrs", gjGtdwyjc.getSyjcrs());
					map.put("gjjsyjcje", gjGtdwyjc.getSyjcje());
					map.put("gjjbyzjrs", gjGtdwyjc.getByzjrs());
					map.put("gjjbyzjje",gjGtdwyjc.getByzjje());
					map.put("gjjbyjsrs", gjGtdwyjc.getByjsrs());
					map.put("gjjbyjsje", gjGtdwyjc.getByjsje());
					map.put("gjjbyjcrs", gjGtdwyjc.getByjcrs());
					map.put("gjjbyjcje", gjGtdwyjc.getByjcje());
					
					map.put("aybtsyjcrs",0);
					map.put("aybtsyjcje",0);
					map.put("aybtbyzjrs",0);
					map.put("aybtbyzjje",0);
					map.put("aybtbyjsrs",0);
					map.put("aybtbyjsje",0);
					map.put("aybtbyjcrs",0);
					map.put("aybtbyjcje",0);
				}
				if("1".equals(gjGtdwyjc.getJfxh())){//1  按月补贴
					map.put("aybtsyjcrs", gjGtdwyjc.getSyjcrs());
					map.put("aybtsyjcje", gjGtdwyjc.getSyjcje());
					map.put("aybtbyzjrs", gjGtdwyjc.getByzjrs());
					map.put("aybtbyzjje",gjGtdwyjc.getByzjje());
					map.put("aybtbyjsrs", gjGtdwyjc.getByjsrs());
					map.put("aybtbyjsje", gjGtdwyjc.getByjsje());
					map.put("aybtbyjcrs", gjGtdwyjc.getByjcrs());
					map.put("aybtbyjcje", gjGtdwyjc.getByjcje());
					
					map.put("gjjsyjcrs",0);
					map.put("gjjsyjcje",0);
					map.put("gjjbyzjrs",0);
					map.put("gjjbyzjje",0);
					map.put("gjjbyjsrs",0);
					map.put("gjjbyjsje",0);
					map.put("gjjbyjcrs",0);
					map.put("gjjbyjcje",0);
				}
			}
			if(list2.size() > 1){
				GjGtdwyjc gjGtdwyjc = list2.get(0);
				if(0 == gjGtdwyjc.getJfxh()){
					map.put("gjjsyjcrs", gjGtdwyjc.getSyjcrs());
					map.put("gjjsyjcje", gjGtdwyjc.getSyjcje());
					map.put("gjjbyzjrs", gjGtdwyjc.getByzjrs());
					map.put("gjjbyzjje", gjGtdwyjc.getByzjje());
					map.put("gjjbyjsrs", gjGtdwyjc.getByjsrs());
					map.put("gjjbyjsje", gjGtdwyjc.getByjsje());
					map.put("gjjbyjcrs", gjGtdwyjc.getByjcrs());
					map.put("gjjbyjcje", gjGtdwyjc.getByjcje());
				}else{
					map.put("gjjsyjcrs", 0);
					map.put("gjjsyjcje", 0);
					map.put("gjjbyzjrs", 0);
					map.put("gjjbyzjje", 0);
					map.put("gjjbyjsrs", 0);
					map.put("gjjbyjsje", 0);
					map.put("gjjbyjcrs", 0);
					map.put("gjjbyjcje", 0);
				}
				
				GjGtdwyjc gjGtdwyjc1 = list2.get(1);
				if(1 == gjGtdwyjc1.getJfxh()){
					map.put("aybtsyjcrs", gjGtdwyjc1.getSyjcrs());
					map.put("aybtsyjcje", gjGtdwyjc1.getSyjcje());
					map.put("aybtbyzjrs", gjGtdwyjc1.getByzjrs());
					map.put("aybtbyzjje", gjGtdwyjc1.getByzjje());
					map.put("aybtbyjsrs", gjGtdwyjc1.getByjsrs());
					map.put("aybtbyjsje", gjGtdwyjc1.getByjsje());
					map.put("aybtbyjcrs", gjGtdwyjc1.getByjcrs());
					map.put("aybtbyjcje", gjGtdwyjc1.getByjcje());
				}else{
					map.put("aybtsyjcrs", 0);
					map.put("aybtsyjcje", 0);
					map.put("aybtbyzjrs", 0);
					map.put("aybtbyzjje", 0);
					map.put("aybtbyjsrs", 0);
					map.put("aybtbyjsje", 0);
					map.put("aybtbyjcrs", 0);
					map.put("aybtbyjcje", 0);
				}
			}
		}else{
			map.put("gjjsyjcrs", 0);
			map.put("gjjsyjcje", 0);
			map.put("gjjbyzjrs", 0);
			map.put("gjjbyzjje", 0);
			map.put("gjjbyjsrs", 0);
			map.put("gjjbyjsje", 0);
			map.put("gjjbyjcrs", 0);
			map.put("gjjbyjcje", 0);
			map.put("aybtsyjcrs", 0);
			map.put("aybtsyjcje", 0);
			map.put("aybtbyzjrs", 0);
			map.put("aybtbyzjje", 0);
			map.put("aybtbyjsrs", 0);
			map.put("aybtbyjsje", 0);
			map.put("aybtbyjcrs", 0);
			map.put("aybtbyjcje", 0);
		}
		return map;
	}
	
	public List appApi00802Select(AppApi80102Form form) throws Exception {
		GjDwzhmxExample gjDwzhmxExample = new GjDwzhmxExample();
		GjDwzhmxExample.Criteria ca = gjDwzhmxExample.createCriteria();
		GjDwzhmx gjDwzhmx = new GjDwzhmx();
		gjDwzhmx.setDwzh(form.getDwzh());
		gjDwzhmx.setZhlx(form.getZhlx());
		gjDwzhmx.setJym("209988");
		int pages = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (pages-1) * rows;
		List<GjDwxx> list = gjDwzhmxDAO.selectByExampleFy(gjDwzhmx,skipResults,rows);
		return list;
	}
	
	public List appApi00803Select(AppApi80103Form form) throws Exception {
		GjDwndjs gjDwndjs = new GjDwndjs();
		gjDwndjs.setDwdm(form.getDwzh());
		gjDwndjs.setZhlx(form.getZhlx());
		gjDwndjs.setJznd(form.getJznd());
		List<GjDwndjs> list = gjDwndjsDAO.selectByExampleAll(gjDwndjs);
		return list;
	}
	
	public List appApi00804Select(AppApi40102Form form) throws Exception {
		GjDwxxExample gjDwzhExample = new GjDwxxExample();
		GjDwxxExample.Criteria ca = gjDwzhExample.createCriteria();
		System.out.println("上送密码" + MachineCodeUtils.toMd5String(form.getNewpassword()));
		ca.andDwdmEqualTo(form.getFullName());
		ca.andPasswdEqualTo(MachineCodeUtils.toMd5String(form.getNewpassword()));
		List<GjDwxx> list = ghDwxxDao.selectByExample(gjDwzhExample);
		if(list != null && list.size() != 0){
			GjDwxx gjDwxx = (GjDwxx) list.get(0);
			System.out.println("对比密码：" + gjDwxx.getPasswd());
		}
		return list;
	}
}
