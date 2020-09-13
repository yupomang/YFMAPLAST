package com.yondervision.yfmap.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.yondervision.yfmap.dao.DaGrjbDAO;
import com.yondervision.yfmap.dao.GjGrmxDAO;
import com.yondervision.yfmap.dao.GjGrzhDAO;
import com.yondervision.yfmap.dao.XdSpxxDAO;
import com.yondervision.yfmap.dto.DaGrjb;
import com.yondervision.yfmap.dto.DaGrjbExample;
import com.yondervision.yfmap.dto.GjGrmx;
import com.yondervision.yfmap.dto.GjGrzh;
import com.yondervision.yfmap.dto.GjGrzhExample;
import com.yondervision.yfmap.dto.XdSpxxExample;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.form.AppApi80202Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.result.ningbo.DaGrjbResult;
import com.yondervision.yfmap.service.AppApi802Service;
import com.yondervision.yfmap.util.MachineCodeUtils;

public class AppApi802ServiceImpl implements AppApi802Service {

	public DaGrjbDAO daGrjbDAO = null;
	
	public GjGrzhDAO gjGrzhDAO = null;
	
	public GjGrmxDAO gjGrmxDAO = null;
	
	public XdSpxxDAO xdSpxxDAO = null;
	
	public void setXdSpxxDAO(XdSpxxDAO xdSpxxDAO) {
		this.xdSpxxDAO = xdSpxxDAO;
	}

	public void setGjGrmxDAO(GjGrmxDAO gjGrmxDAO) {
		this.gjGrmxDAO = gjGrmxDAO;
	}

	public void setGjGrzhDAO(GjGrzhDAO gjGrzhDAO) {
		this.gjGrzhDAO = gjGrzhDAO;
	}

	public DaGrjbDAO getDaGrjbDAO() {
		return daGrjbDAO;
	}

	public void setDaGrjbDAO(DaGrjbDAO daGrjbDAO) {
		this.daGrjbDAO = daGrjbDAO;
	}

	public Map appApi00801Select(AppApiCommonForm form) throws Exception {
		Map map = new TreeMap<String, Object>();
		DaGrjb daGrjb = new DaGrjb();
		daGrjb.setGrsb(form.getSurplusAccount());
		List<DaGrjbResult> list = daGrjbDAO.selectByExampleGrjb(daGrjb);
		DaGrjbResult daGrjbResult = list.get(0);
		map.put("name", daGrjbResult.getZgxm().trim());
		map.put("dwmc", daGrjbResult.getDwmc().trim());
		map.put("gjjjje", daGrjbResult.getYjje());
		map.put("anbtjje", daGrjbResult.getBtje());
		map.put("hjjje", daGrjbResult.getYjje().add(daGrjbResult.getBtje()));
		String zhzt = daGrjbResult.getGrzt();
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
		
		GjGrzhExample gjGrzhExample = new GjGrzhExample();
		GjGrzhExample.Criteria ca = gjGrzhExample.createCriteria();
		ca.andGrsbEqualTo(form.getSurplusAccount());
		List<GjGrzh> list1 = gjGrzhDAO.selectByExample(gjGrzhExample);
		BigDecimal gjjzh = null;
		BigDecimal aybtzh = null;
		BigDecimal zcxzhbtzh = null;
		for(int i = 0; i < list1.size(); i++){
			if("01".equals(list1.get(i).getZhlx().trim())){
				gjjzh = list1.get(i).getHjye();
			}
			if("02".equals(list1.get(i).getZhlx().trim())){
				aybtzh = list1.get(i).getHjye();
			}
			if("03".equals(list1.get(i).getZhlx().trim())){
				zcxzhbtzh = list1.get(i).getHjye();
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
		map.put("hjye",gjjzh.add(aybtzh).add(zcxzhbtzh));
		return map;
	}

	public List appApi00802Select(AppApi80202Form form) throws Exception {
		
		GjGrmx gjGrmx = new GjGrmx();
		gjGrmx.setGrsb(form.getSurplusAccount());
		gjGrmx.setZhlx(form.getZhlx());
		int pages = Integer.valueOf(form.getPagenum());
		int rows = Integer.valueOf(form.getPagerows());
		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
		rows = rows==0 ? Integer.valueOf(10) : rows;
		int skipResults = (pages-1) * rows;
		List list = gjGrmxDAO.selectByExampleFy(gjGrmx, skipResults, rows);
		return list;
	}

	public List appApi00803Select(AppApiCommonForm form) throws Exception {
		XdSpxxExample xdSpxxExample = new XdSpxxExample();
		XdSpxxExample.Criteria ca = xdSpxxExample.createCriteria();
		ca.andGrsbmEqualTo(form.getSurplusAccount());
		List list = xdSpxxDAO.selectByExample(xdSpxxExample);
		return list;
	}

	public List appApi00804Select(AppApi40102Form form) throws Exception {
//		if(!"".equals(form.getSurplusAccount())){
//			DaGrjbExample daGrjbExample = new DaGrjbExample();
//			DaGrjbExample.Criteria ca = daGrjbExample.createCriteria();
//			ca.andGrsbEqualTo(form.getSurplusAccount());
//			ca.andPasswdEqualTo(form.getPassword());
//			List list = daGrjbDAO.selectByExample(daGrjbExample);
//			if(list == null || list.size() == 0){
//				return "1";
//			}
//		}
//		if(!"".equals(form.getIdcardNumber())){
		DaGrjbExample daGrjbExample1 = new DaGrjbExample();
		DaGrjbExample.Criteria ca1 = daGrjbExample1.createCriteria();
		ca1.andSfzhEqualTo(form.getIdcardNumber());
		System.out.println("上送密码" + MachineCodeUtils.toMd5String(form.getPassword()));
		ca1.andPasswdEqualTo(MachineCodeUtils.toMd5String(form.getPassword()));
		List list1 = daGrjbDAO.selectByExample(daGrjbExample1);
		if(list1 != null && list1.size() != 0){
			DaGrjb daGrjb = (DaGrjb) list1.get(0);
			System.out.println("对比密码：" + daGrjb.getPasswd());
		}
		return list1;
	}

}
